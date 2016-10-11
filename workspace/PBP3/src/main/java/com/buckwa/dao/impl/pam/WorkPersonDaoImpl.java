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

import com.buckwa.dao.intf.pam.WorkPersonDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.WorkPerson;
import com.buckwa.util.BuckWaDateUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("workPersonDao")
public class WorkPersonDaoImpl implements WorkPersonDao {
	
	private static Logger logger = LoggerFactory.getLogger(WorkPersonDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public WorkPerson create(WorkPerson obj){
		logger.info(" #WorkPersonDaoImpl.create # ");
		final WorkPerson finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into work_person (" +
								"name," +
								"user_id," +
								"work_template_id,"+
								"isClassRoom,"+
								"year_id,"+
								"semester_id,"+
								"create_by," +
								"created_date" +
								") " +
								"values (?,?,?,?,?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, finalObj.getName().trim());
				ps.setLong(2, finalObj.getUserid());
				ps.setLong(3, finalObj.getWorkTemplateId());
				ps.setInt(4, finalObj.getIsClassRoom());
				ps.setLong(5, finalObj.getYearId()==null?0:finalObj.getYearId());
				ps.setLong(6, finalObj.getSemesterId()==null?0:finalObj.getSemesterId());
				ps.setString(7, finalObj.getCreateBy());
				ps.setTimestamp(8, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		}, keyHolder);
		
		finalObj.setWorkPersonId(keyHolder.getKey().longValue());
		
		return finalObj;
	}
	
	@Override
	public WorkPerson update(WorkPerson obj){
		logger.info(" #WorkPersonDaoImpl.update # ");
		final WorkPerson finalObj = obj;
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ " update work_person set " +
								"name=?," +
								"update_by=?," +
								"updated_date=?" +
								"where work_person_id=? ");
				ps.setString(1, finalObj.getName().trim());
				ps.setString(2, finalObj.getUpdateBy());
				ps.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
				ps.setLong(4, finalObj.getWorkPersonId());
				return ps;
			}
		});
		
		return finalObj;
	}
	
	@Override
	public void delete(String workPersonId) {	
		this.jdbcTemplate.update(" delete from work_person where work_person_id = "+workPersonId);
	}	
	
	@Override
	public WorkPerson getById(String id) {		 		
		StringBuffer sb = new StringBuffer();
		sb.append("  select p.*,s.name as semesterName,y.name as yearName from work_person p left outer join year y on y.year_id=p.year_id left outer join semester s on s.semester_id = p.semester_id   where p.status='A' and p.work_person_id = "+id);
		WorkPerson obj = null;
		try {
			obj = this.jdbcTemplate.queryForObject(sb.toString(),new WorkPersonMapper() );			
		} catch (EmptyResultDataAccessException e) {

		}
		return obj;
	}	
	
	@Override
	public List<WorkPerson> getByClassRoomYearSemester(String classRoom,String yearId,String semesterId) {		 		
		StringBuffer sb = new StringBuffer();
		sb.append("  select *,null as semesterName,null as yearName from work_person where status='A' and isClassRoom = "+classRoom+ " and year_id = "+ yearId + " and semester_id="+semesterId);
		WorkPerson obj = null;
		try {
			List<WorkPerson> resultList =  this.jdbcTemplate.query(sb.toString(),new WorkPersonMapper() );
			return resultList;
		} catch (EmptyResultDataAccessException e) {

		}
		return null;
	}	
	
	@Override
	public List<WorkPerson> getByClassRoomYearSemester(String classRoom,String yearId,String semesterId,String userList){
		StringBuffer sb = new StringBuffer();
		sb.append("  select *,null as semesterName,null as yearName from work_person " +
				"where status='A' and isClassRoom = "+classRoom+ " and year_id = "+ yearId + " and semester_id="+semesterId+"" +
				" and user_id in ("+userList+")");
		WorkPerson obj = null;
		try {
			List<WorkPerson> resultList =  this.jdbcTemplate.query(sb.toString(),new WorkPersonMapper() );
			return resultList;
		} catch (EmptyResultDataAccessException e) {

		}
		return null;
	}
	
	@Override
	public boolean checkNameAlready(String id,String userid,String name){
		StringBuffer sb = new StringBuffer();
		sb.append("  select count(work_person_id) from work_person where user_id="+userid+" and status='A' and name='"+name.trim()+"' and work_person_id != "+id);
		WorkPerson obj = null;
		try {
			int count = this.jdbcTemplate.queryForObject(sb.toString(),Integer.class);	
			if(count==0)
				return false;
		} catch (EmptyResultDataAccessException e) {

		}
		return true;
	}
	
	private class WorkPersonMapper implements RowMapper<WorkPerson> {
		@Override
		public WorkPerson mapRow(ResultSet rs, int rowNum) throws SQLException {
			WorkPerson domain = new WorkPerson();
			domain.setWorkPersonId(rs.getLong("work_person_id"));
			domain.setUserid(rs.getLong("user_id"));
			domain.setName(rs.getString("name"));
			domain.setWorkTemplateId(rs.getLong("work_template_id"));
			if(rs.getString("yearName")!=null)
				domain.setYearStr(rs.getString("yearName"));
			if(rs.getString("semesterName")!=null)
				domain.setSemesterStr(rs.getString("semesterName"));
			domain.setIsClassRoom(rs.getInt("isClassRoom"));
			return domain;
		}
	}
	
	public PagingBean getAllByOffset(PagingBean pagingBean) {	 
		WorkPerson workPerson = (WorkPerson)pagingBean.get("workPerson");		
		List<WorkPerson> returnList = new ArrayList<WorkPerson>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  work_person  r  \n"); 
		sqltotalsb.append(" where 1=1 and r.user_id = "+workPerson.getUserid() + " and r.work_template_id="+workPerson.getWorkTemplateId());		
		if(StringUtils.hasText(workPerson.getName())){
			sqltotalsb.append(" and r.name like  '%"+StringEscapeUtils.escapeSql(workPerson.getName().trim())+"%'");
		}
		
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" * from work_person r  \n");
		sb.append(" where 1=1 and r.user_id = "+workPerson.getUserid() + " and r.work_template_id="+workPerson.getWorkTemplateId());	
		if(StringUtils.hasText(workPerson.getName())){
			sb.append(" and r.name like  '%"+StringEscapeUtils.escapeSql(workPerson.getName().trim())+"%'");
		}
		
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<WorkPerson>() {
				public WorkPerson mapRow(ResultSet rs, int rowNum) throws SQLException {
					WorkPerson workPerson = new WorkPerson();
					workPerson.setWorkPersonId(rs.getLong("work_person_id"));
					workPerson.setName(rs.getString("name"));
					workPerson.setWorkTemplateId(rs.getLong("work_template_id"));
					workPerson.setIsClassRoom(rs.getInt("isClassRoom"));
					workPerson.setYear(BuckWaDateUtils.getYear(BuckWaDateUtils.sqlDateToutilDate(rs.getDate("created_date"))));				 
					return workPerson;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	
	@Override
	public List<WorkPerson> getAllByWorkTemplate(WorkPerson workPerson) {
		String sql = " select p.*,s.name as semesterName,y.name as yearName from work_person p left outer join year y on y.year_id=p.year_id left outer join semester s on s.semester_id = p.semester_id where p.user_id=? and p.work_template_id=?";
		List<WorkPerson> resultList = this.jdbcTemplate.query(sql,
			new RowMapper<WorkPerson>() {
				public WorkPerson mapRow(ResultSet rs, int rowNum) throws SQLException {
					WorkPerson workPerson = new WorkPerson();
					workPerson.setWorkPersonId(rs.getLong("work_person_id"));
					workPerson.setName(rs.getString("name"));
					workPerson.setWorkTemplateId(rs.getLong("work_template_id"));
					workPerson.setIsClassRoom(rs.getInt("isClassRoom"));
					workPerson.setYear(BuckWaDateUtils.getYear(BuckWaDateUtils.sqlDateToutilDate(rs.getDate("created_date"))));	
					workPerson.setYearStr(rs.getString("yearName"));
					workPerson.setSemesterStr(rs.getString("semesterName"));
					return workPerson;
				}
			},
			new Object[] {
				workPerson.getUserid(),
				workPerson.getWorkTemplateId()
			}
		);
		return resultList;
	}
	
}

