package com.buckwa.dao.impl.pam;

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

import com.buckwa.dao.intf.pam.WorkTemplateDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.WorkTemplate;
import com.buckwa.util.BuckWaDateUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("workTemplateDao")
public class WorkTemplateDaoImpl implements WorkTemplateDao {
	
	private static Logger logger = LoggerFactory.getLogger(WorkTemplateDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public WorkTemplate create(WorkTemplate obj){
		logger.info(" #WorkTemplateDaoImpl.create # ");
		final WorkTemplate finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into work_template (" +
								"year," +
								"employee_type," +
								"name," +
								"group_id," +
								"created_by," +
								"created_date" +
								") " +
								"values (?,?,?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, finalObj.getYear()!=null?finalObj.getYear():0);
				ps.setString(2, finalObj.getEmployeeType());
				ps.setString(3, finalObj.getName());
				ps.setInt(4, finalObj.getGroupId());
				ps.setString(5, finalObj.getCreateBy());
				ps.setTimestamp(6, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		}, keyHolder);
		
		finalObj.setWorkTemplateId(keyHolder.getKey().longValue());
		
		return finalObj;
	}
	
	@Override
	public WorkTemplate update(WorkTemplate obj){
		logger.info(" #WorkTemplateDaoImpl.update # ");
		final WorkTemplate finalObj = obj;
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ " update work_template set " +
								"year=?," +
								"employee_type=?," +
								"name=?," +
								"group_id=?," +
								"updated_by=?," +
								"updated_date=?" +
								"where work_template_id=? ");
				ps.setInt(1, finalObj.getYear()!=null?finalObj.getYear():0);
				ps.setString(2, finalObj.getEmployeeType());
				ps.setString(3, finalObj.getName());
				ps.setInt(4, finalObj.getGroupId());
				ps.setString(5, finalObj.getUpdateBy());
				ps.setTimestamp(6, new java.sql.Timestamp(new Date().getTime()));
				ps.setLong(7, finalObj.getWorkTemplateId());
				return ps;
			}
		});
		
		return finalObj;
	}
	
	@Override
	public void delete(String workTemplateId) {	
		this.jdbcTemplate.update(" delete from work_template where work_template_id = "+workTemplateId);
	}	
	
	@Override
	public WorkTemplate getById(String id) {		 		
		StringBuffer sb = new StringBuffer();
		sb.append("  select * from work_template where status='A' and work_template_id = "+id);
		WorkTemplate obj = null;
		try {
			obj = this.jdbcTemplate.queryForObject(sb.toString(),new WorkTemplateMapper() );			
		} catch (EmptyResultDataAccessException e) {

		}
		return obj;
	}
	
	@Override
	public WorkTemplate getByClassRoom() {		 		
		StringBuffer sb = new StringBuffer();
		sb.append("  select * from work_template where status='A' and isClassRoom = 1");
		WorkTemplate obj = null;
		try {
			obj = this.jdbcTemplate.queryForObject(sb.toString(),new WorkTemplateMapper() );			
		} catch (EmptyResultDataAccessException e) {

		}
		return obj;
	}	
	
	@Override
	public boolean checkNameAlready(String id,String name){
		StringBuffer sb = new StringBuffer();
		sb.append("  select count(work_template_id) from work_template where status='A' and name='"+name.trim()+"' and work_template_id != "+id);
		WorkTemplate obj = null;
		try {
			int count = this.jdbcTemplate.queryForObject(sb.toString(),Integer.class);	
			if(count==0)
				return false;
		} catch (EmptyResultDataAccessException e) {

		}
		return true;
	}
	
	
	
	public PagingBean getAllRoleByOffset(PagingBean pagingBean) {	 
		WorkTemplate workTemplate = (WorkTemplate)pagingBean.get("workTemplate");		
		List<WorkTemplate> returnList = new ArrayList<WorkTemplate>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  work_template  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(workTemplate.getName())){
			sqltotalsb.append(" and r.name like  '%"+StringEscapeUtils.escapeSql(workTemplate.getName().trim())+"%'");
		}
		
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" * from work_template r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(workTemplate.getName())){
			sb.append(" and r.name like  '%"+StringEscapeUtils.escapeSql(workTemplate.getName().trim())+"%'");
		}
		
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<WorkTemplate>() {
				public WorkTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
					WorkTemplate workTemplate = new WorkTemplate();
					workTemplate.setWorkTemplateId(rs.getLong("work_template_id"));
					workTemplate.setName(rs.getString("name"));
					workTemplate.setGroupId(rs.getInt("group_id"));
					workTemplate.setIsClassRoom(rs.getInt("isClassRoom"));
					workTemplate.setYear(BuckWaDateUtils.getYear(BuckWaDateUtils.sqlDateToutilDate(rs.getDate("created_date"))));				 
					return workTemplate;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	
	@Override
	public List<WorkTemplate> getByGroupId(String groupId){
		List<WorkTemplate> returnList = new ArrayList<WorkTemplate>();
		String sql = " select * from work_template r where r.status='A' and r.group_id="+StringEscapeUtils.escapeSql(groupId);
		logger.info(" sql:" + sql);
		returnList = this.jdbcTemplate.query(sql, new WorkTemplateMapper());
		return returnList;
	}
	
	@Override
	public List<WorkTemplate> getByGroupIdNonTimeTable(String groupId){
		List<WorkTemplate> returnList = new ArrayList<WorkTemplate>();
		String sql = " select * from work_template r where r.isClassRoom=0 and r.status='A' and r.group_id="+StringEscapeUtils.escapeSql(groupId);
		logger.info(" sql:" + sql);
		returnList = this.jdbcTemplate.query(sql, new WorkTemplateMapper()); 
		return returnList;
	}
	
	@Override
	public List<WorkTemplate> getByEmployeeTypeNonTimeTable(String employeeType){
		List<WorkTemplate> returnList = new ArrayList<WorkTemplate>();
		String sql = " select r.* from work_template r  inner join kpicategory kg on kg.kpicategory_id = r.group_id  where r.isClassRoom=0 and r.status='A' and kg.code='"+StringEscapeUtils.escapeSql(employeeType)+"'";
		returnList = this.jdbcTemplate.query(sql, new WorkTemplateMapper()); 
		return returnList;
	}
	
	@Override
	public List<WorkTemplate> getByPersonTypeNonTimeTable(String personType){
		List<WorkTemplate> returnList = new ArrayList<WorkTemplate>();
		String sql = " select r.* from work_template r  inner join kpicategory kg on kg.kpicategory_id = r.group_id  where r.isClassRoom=0 and r.status='A' and kg.code='"+StringEscapeUtils.escapeSql(personType)+"'";
		returnList = this.jdbcTemplate.query(sql, new WorkTemplateMapper()); 
		return returnList;
	}

	
	private class WorkTemplateMapper implements RowMapper<WorkTemplate> {
		@Override
		public WorkTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
			WorkTemplate domain = new WorkTemplate();
			domain.setWorkTemplateId(rs.getLong("work_template_id"));
			domain.setYear(rs.getInt("year"));
			domain.setEmployeeType(rs.getString("employee_type"));
			domain.setName(rs.getString("name"));
			domain.setGroupId(rs.getInt("group_id"));
			domain.setIsClassRoom(rs.getInt("isClassRoom"));
			return domain;
		}
	}
	
}

