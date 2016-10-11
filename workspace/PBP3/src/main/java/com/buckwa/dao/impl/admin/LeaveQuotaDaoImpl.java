package com.buckwa.dao.impl.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.admin.LeaveQuotaDao;
import com.buckwa.domain.admin.LeaveQuota;
import com.buckwa.domain.admin.LeaveYearQuota;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.util.BuckWaDateUtils;


@Repository("LeaveQuotaDao")
public class LeaveQuotaDaoImpl implements LeaveQuotaDao{
	
	private static Logger logger = LoggerFactory.getLogger(LeaveQuotaDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	@Override
	public LeaveQuota create(LeaveQuota obj){
		logger.info(" #LeaveQuotaDaoImpl.create # ");
		final LeaveQuota finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "insert into leave_quota (" +
								"quota," +
								"leave_type_code," +
								"create_by," +
								"created_date" +
								") " +
								"values (?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, finalObj.getQuota()!=null?finalObj.getQuota():0);
				ps.setString(2, finalObj.getLeaveTypeCode());
				ps.setString(3, finalObj.getCreateBy());
				ps.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		}, keyHolder);
		
		finalObj.setLeaveQuotaId(keyHolder.getKey().longValue());
		
		return finalObj;
	}
	
	@Override
	public LeaveQuota update(LeaveQuota obj){
		logger.info(" #LeaveQuotaDaoImpl.update # ");
		final LeaveQuota finalObj = obj;
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ " update leave_quota set " +
								"quota," +
								"leave_type_code," +
								"update_by=?," +
								"updated_date=?" +
								"where leave_quota_id=? ");
				ps.setInt(1, finalObj.getQuota()!=null?finalObj.getQuota():0);
				ps.setString(2, finalObj.getLeaveTypeCode());
				ps.setString(3, finalObj.getUpdateBy());
				ps.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));
				ps.setLong(5, finalObj.getLeaveQuotaId());
				return ps;
			}
		});
		
		return finalObj;
	}
	
	@Override
	public LeaveYearQuota createYearQuota(LeaveYearQuota obj){
		logger.info(" #LeaveQuotaDaoImpl.createYear # ");
		final LeaveYearQuota finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "insert into leave_year_quota (" +
								"year," +
								"create_by," +
								"created_date" +
								") " +
								"values (?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, finalObj.getYear());
				ps.setString(2, finalObj.getCreateBy());
				ps.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		}, keyHolder);
		
		finalObj.setLeaveYearQuotaId(keyHolder.getKey().longValue());
		
		return finalObj;
	}
	
	@Override
	public LeaveYearQuota updateYearQuota(LeaveYearQuota obj){
		logger.info(" #LeaveQuotaDaoImpl.updateYear # ");
		final LeaveYearQuota finalObj = obj;
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ " update leave_year_quota set " +
								"year=?," +
								"update_by=?," +
								"updated_date=?" +
								"where leave_year_quota_id=? ");
				ps.setInt(1, finalObj.getYear());
				ps.setString(2, finalObj.getUpdateBy());
				ps.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
				ps.setLong(4, finalObj.getLeaveYearQuotaId());
				return ps;
			}
		});
		
		return finalObj;
	}
	
	@Override
	public void deleteYearQuota(String leaveYearQuotaId){
		this.jdbcTemplate.update(" delete from leave_year_quota where leave_year_quota_id = "+leaveYearQuotaId);
	}
	
	@Override
	public LeaveYearQuota getYearQuotaById(String leaveYearQuotaId) {	
		StringBuffer sb = new StringBuffer();
		sb.append("  select * from leave_year_quota where status='A' and leave_year_quota_id = "+leaveYearQuotaId);
		LeaveYearQuota obj = null;
		try {
			obj = this.jdbcTemplate.queryForObject(sb.toString(),new LeaveYearQuotaMapper() );			
		} catch (EmptyResultDataAccessException e) {

		}
		return obj;
		
	}	
	
	@Override
	public void deleteAll() {	
		this.jdbcTemplate.update(" delete from leave_quota");
	}
	
	@Override
	public void deleteAllLeaveQuota(String leaveYearQuotaId) {	
		this.jdbcTemplate.update(" delete from leave_quota where leave_year_quota_id = "+leaveYearQuotaId);
	}	
	
	@Override
	public List<LeaveQuota> getByLeaveYearQuotaId(String leaveYearQuotaId) {	
		StringBuffer sb = new StringBuffer();
		List<LeaveQuota> returnList = new ArrayList<LeaveQuota>();
		sb.append("  select a.*,c.name,b.year from leave_quota a inner join leave_year_quota b on a.leave_year_quota_id = b.leave_year_quota_id " +
				"inner join leave_type c on c.code = a.leave_type_code " +
				"where b.status='A' and a.leave_year_quota_id = "+leaveYearQuotaId);
		try {
			returnList = this.jdbcTemplate.query(sb.toString(),new LeaveQuotaMapper() );	
		} catch (EmptyResultDataAccessException e) {

		}
		return returnList;
		
	}	
	
	@Override
	public List<LeaveQuota> getByYear(String year) {	
		StringBuffer sb = new StringBuffer();
		List<LeaveQuota> returnList = new ArrayList<LeaveQuota>();
		sb.append("  select a.*,c.name from leave_quota a " +
				"inner join leave_type c on c.code = a.leave_type_code " +
				"where a.status='A' and a.year = "+year);
		try {
			returnList = this.jdbcTemplate.query(sb.toString(),new LeaveQuotaMapper() );	
		} catch (EmptyResultDataAccessException e) {

		}
		return returnList;
		
	}	
	
	@Override
	public List<LeaveQuota> getAll() {	
		StringBuffer sb = new StringBuffer();
		List<LeaveQuota> returnList = new ArrayList<LeaveQuota>();
		sb.append("  select a.*,c.name from leave_quota a " +
				"inner join leave_type c on c.code = a.leave_type_code " +
				"where a.status='A'");
		try {
			returnList = this.jdbcTemplate.query(sb.toString(),new LeaveQuotaMapper() );	
		} catch (EmptyResultDataAccessException e) {

		}
		return returnList;
		
	}	
	
	@Override
	public boolean checkYearAlready(String id,String year){
		StringBuffer sb = new StringBuffer();
		sb.append("  select count(leave_year_quota_id) from leave_year_quota where status='A' and year="+StringEscapeUtils.escapeSql(year)+" and leave_year_quota_id!="+id);
		try {
			int count = this.jdbcTemplate.queryForObject(sb.toString(),Integer.class);	
			if(count==0)
				return false;
		} catch (EmptyResultDataAccessException e) {

		}
		return true;
	}
	
	private class LeaveQuotaMapper implements RowMapper<LeaveQuota> {
		@Override
		public LeaveQuota mapRow(ResultSet rs, int rowNum) throws SQLException {
			LeaveQuota domain = new LeaveQuota();
			domain.setLeaveQuotaId(rs.getLong("leave_quota_id"));
			domain.setLeaveTypeCode(rs.getString("leave_type_code"));
			//domain.setLeaveYearQuotaId(rs.getLong("leave_year_quota_id"));
			//domain.setYear(rs.getInt("year"));
			domain.setQuota(rs.getInt("quota"));
			domain.setCreateDate(rs.getTimestamp("created_date"));
			domain.setLeaveTypeName(rs.getString("name"));
			return domain;
		}
	}
	
	private class LeaveYearQuotaMapper implements RowMapper<LeaveYearQuota> {
		@Override
		public LeaveYearQuota mapRow(ResultSet rs, int rowNum) throws SQLException {
			LeaveYearQuota domain = new LeaveYearQuota();
			domain.setLeaveYearQuotaId(rs.getLong("leave_year_quota_id"));
			domain.setYear(rs.getInt("year"));
			domain.setCreateDate(rs.getTimestamp("created_date"));	
			domain.setCreateDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(domain.getCreateDate()));
			return domain;
		}
	}
	
	public PagingBean getAllByOffset(PagingBean pagingBean) {	 
		LeaveYearQuota leaveYearQuota = (LeaveYearQuota)pagingBean.get("leaveYearQuota");		
		List<LeaveYearQuota> returnList = new ArrayList<LeaveYearQuota>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  leave_year_quota  r "); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(leaveYearQuota.getYearStr())){
			sqltotalsb.append(" and r.year = "+StringEscapeUtils.escapeSql(leaveYearQuota.getYearStr().trim()));
		}
		
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select");
		sb.append(" * from leave_year_quota r");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(leaveYearQuota.getYearStr())){
			sb.append(" and r.year = "+StringEscapeUtils.escapeSql(leaveYearQuota.getYearStr().trim()));
		}
		
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(sql,new LeaveYearQuotaMapper());
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
}
