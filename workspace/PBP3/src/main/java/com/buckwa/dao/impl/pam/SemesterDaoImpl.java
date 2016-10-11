package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.pam.SemesterDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Semester;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaDateUtils;

@Repository("semesterDao")
public class SemesterDaoImpl implements SemesterDao {
	private static Logger logger = LoggerFactory.getLogger(SemesterDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Semester> getAll() {
		List<Semester> returnList = new ArrayList<Semester>(); 
		String sql =" select semester_id, name, start_date, end_date, year_id from semester " ;	
		logger.info(" sql:"+sql);
		returnList = this.jdbcTemplate.query(sql, new SemesterMapper());
		return returnList;
	}
	 
 

	@Override
	public void update(Semester semester) {
		logger.info("  semester: " + BeanUtils.getBeanString(semester));
		this.jdbcTemplate.update(
			" update semester set name=?, " +
			"   start_date=?, " +
			"   end_date=? " +
			" where semester_id=? ",
			new Object[]{
				semester.getName(),
				semester.getStartDate(),
				semester.getEndDate(),
				semester.getSemesterId()
			}
		); 
 	 	
	}
 
	
	@Override
	public Long create(Semester semester) {
		logger.info("   ");
		final Semester finalSemester = semester;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement(
					" insert into semester (name, year_id, start_date, end_date) values (?, ?, ?, ?)"
					, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1,finalSemester.getName());
				ps.setLong(2, finalSemester.getYearId());
				logger.info("Start date >> "+finalSemester.getStartDate());
				logger.info("End date >> "+finalSemester.getEndDate());
//				ps.setDate(3, BuckWaDateUtils.utilDateToSqlDate(finalSemester.getStartDate()));
//				ps.setDate(4, BuckWaDateUtils.utilDateToSqlDate(finalSemester.getEndDate()));
				ps.setTimestamp(3, new Timestamp(finalSemester.getStartDate().getTime()));
				ps.setTimestamp(4,  new Timestamp(finalSemester.getEndDate().getTime()));
				return ps;  
				}
			}, 	keyHolder);
		Long returnid =  keyHolder.getKey().longValue();
		
	   return returnid;
	}

	
	public PagingBean getByOffset(PagingBean pagingBean) {	 
		Semester semester = (Semester)pagingBean.get("semester");		
		List<Semester> returnList = new ArrayList<Semester>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from semester  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(semester.getName())){
			sqltotalsb.append(" and r.name like  '%"+StringEscapeUtils.escapeSql(semester.getName().trim())+"%'");
		}
		 
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" r.semester_id , r.name  from semester r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(semester.getName())){
			sb.append(" and r.name like  '%"+StringEscapeUtils.escapeSql(semester.getName().trim())+"%'");
		}
		
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Semester>() {
				public Semester mapRow(ResultSet rs, int rowNum) throws SQLException {
					Semester semester = new Semester();				 
					semester.setSemesterId(rs.getLong("semester_id"));
					semester.setName(rs.getString("name"));
					 			 
				return semester;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	
	
	public List<Semester> getByYearId(Long id) {	 
		List<Semester> returnList = new ArrayList<Semester>();
        String sql ="select * from semester where year_id="+id + " ORDER BY name ASC ";
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Semester>() {
				public Semester mapRow(ResultSet rs, int rowNum) throws SQLException {
					Semester semester = new Semester();				 
					semester.setSemesterId(rs.getLong("semester_id"));
					semester.setYearId(rs.getLong("year_id"));
					semester.setName(rs.getString("name"));
					semester.setStartDate(rs.getTimestamp("start_date"));
					semester.setEndDate(rs.getTimestamp("end_date"));
					semester.setStartDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getTimestamp("start_date")));
					semester.setEndDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getTimestamp("end_date")));
					 			 
				return semester;
				}
				});
		 

		return returnList;
	}
	
	@Override
	public Semester getById(String id) {		 		
		String sql =" select semester_id, name, start_date, end_date, year_id from semester where semester_id = "+id;
		Semester semester = this.jdbcTemplate.queryForObject(sql,	new SemesterMapper() );				
 		
		return semester;
	}	

	
	@Override
	public void deleteById(String id) {	 
		this.jdbcTemplate.update(" delete from  semester where semester_id ="+id);			 
	}
	
 
	
	@Override
	public boolean isAlreadyUsege(String id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from where semester_id ="+id;			
			logger.info(" sql:"+sqltmp);
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	@Override
	public boolean isExist(String name,String yearId) {
		boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as total  from semester t  where t.name='"+StringEscapeUtils.escapeSql(name)+"' and t.year_id='"+yearId+"' ";
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	
	@Override
	public boolean isExistForUpdate(String name, Long yearId) {
		boolean returnValue = false;
//		try{
//			String sqltmp = "select count(*) as total  from semester t where t.name='"+StringEscapeUtils.escapeSql(name)+"'  and  t.year_id = (select year_id from semester  where semester_id ="+id+") "	;
//			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
//			if(found!=null&&found.intValue()>0){
//				returnValue = true;
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}			
		return returnValue;
	}
	
	
	private class SemesterMapper implements RowMapper<Semester> {
        @Override
		public Semester mapRow(ResultSet rs, int rowNum) throws SQLException {
			Semester domain = new Semester();
			domain.setSemesterId(rs.getLong("semester_id"));
			domain.setName(rs.getString("name"));
			domain.setStartDate(rs.getTimestamp("start_date"));
			domain.setEndDate(rs.getTimestamp("end_date"));
			domain.setYearId(rs.getLong("year_id"));
			return domain;
	    }
	}
	
}
