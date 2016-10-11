package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pam.ClassRoomProcessDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.ClassRoomProcess;
import com.buckwa.domain.pam.Person;
import com.buckwa.util.BuckWaDateUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("classRoomProcessDao")
public class ClassRoomProcessDaoImpl implements ClassRoomProcessDao {
	
	private static Logger logger = LoggerFactory.getLogger(ClassRoomProcessDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public ClassRoomProcess create(ClassRoomProcess obj){
		logger.info(" #ClassRoomProcessDaoImpl.create # ");
		final ClassRoomProcess finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection 
						.prepareStatement(
								""
								+ "  insert into classroom_procress (" +
								"year_id," +
								"semester_id," +
								"flag,"+
								"create_by," +
								"created_date" +
								") " +
								"values (?,?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, finalObj.getYearId());
				ps.setLong(2, finalObj.getSemesterId());
				ps.setInt(3, finalObj.getFlag());
				ps.setString(4, finalObj.getCreateBy());
				ps.setTimestamp(5, new java.sql.Timestamp(new Date().getTime()));
				return ps;
			}
		}, keyHolder);
		
		finalObj.setClassRoomProcessId(keyHolder.getKey().longValue());
		
		return finalObj;
	}
	
	@Override
	public ClassRoomProcess update(ClassRoomProcess obj){
		logger.info(" #ClassRoomProcessDaoImpl.update # ");
		final ClassRoomProcess finalObj = obj;
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ " update classroom_procress set " +
								"year_id=?," +
								"semester_id=?," +
								"flag=?," +
								"update_by=?," +
								"updated_date=?" +
								"where classroom_procress_id=? ");
				
				ps.setLong(1, finalObj.getYearId());
				ps.setLong(2, finalObj.getSemesterId());
				ps.setInt(3, finalObj.getFlag());
				ps.setString(4, finalObj.getUpdateBy());
				ps.setTimestamp(5, new java.sql.Timestamp(new Date().getTime()));
				ps.setLong(6, finalObj.getClassRoomProcessId());
				return ps;
			}
		});
		
		return finalObj;
	}
	
	@Override
	public ClassRoomProcess getById(String id) {		 		
		StringBuffer sb = new StringBuffer();
		sb.append("  select r.*,y.name as yearName,s.name as semesterName from classroom_procress r inner join year y on y.year_id = r.year_id inner join semester s on s.semester_id=r.semester_id where r.status='A' and r.classroom_procress_id = "+id);
		ClassRoomProcess obj = null;
		try {
			obj = this.jdbcTemplate.queryForObject(sb.toString(),new ClassRoomProcessMapper() );			
		} catch (EmptyResultDataAccessException e) {

		}
		return obj;
	}	
	
	
	private class ClassRoomProcessMapper implements RowMapper<ClassRoomProcess> {
		@Override
		public ClassRoomProcess mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClassRoomProcess domain = new ClassRoomProcess();
			domain.setClassRoomProcessId(rs.getLong("classroom_procress_id"));
			domain.setYearId(rs.getLong("year_id"));
			domain.setSemesterId(rs.getLong("semester_id"));
			domain.setFlag(rs.getInt("flag"));
			domain.setCreateBy(rs.getString("create_by"));
			domain.setUpdateBy(rs.getString("update_by"));
			domain.setCreateDateStr(BuckWaDateUtils.get_current_ddMMMyyyyhhmmss_thai_from_date(rs.getTimestamp("created_date")));	
			domain.setUpdateDateStr(BuckWaDateUtils.get_current_ddMMMyyyyhhmmss_thai_from_date(rs.getTimestamp("updated_date")));
			domain.setYear(rs.getString("yearName"));
			domain.setSemester(rs.getString("semesterName"));
			return domain;
		}
	}
	
	public PagingBean getAllByOffset(PagingBean pagingBean) {	 
		List<ClassRoomProcess> returnList = new ArrayList<ClassRoomProcess>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  classroom_procress  r  inner join year y on y.year_id = r.year_id inner join semester s on s.semester_id=r.semester_id \n"); 
		sqltotalsb.append(" where 1=1 ");		
		
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" r.*,y.name as yearName,s.name as semesterName from classroom_procress r  inner join year y on y.year_id = r.year_id inner join semester s on s.semester_id=r.semester_id \n");
		sb.append(" where 1=1 ");	
		
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<ClassRoomProcess>() {
				public ClassRoomProcess mapRow(ResultSet rs, int rowNum) throws SQLException {
					ClassRoomProcess domain = new ClassRoomProcess();
					domain.setYearId(rs.getLong("year_id"));
					domain.setSemesterId(rs.getLong("semester_id"));
					domain.setFlag(rs.getInt("flag"));
					domain.setCreateBy(rs.getString("create_by"));
					domain.setUpdateBy(rs.getString("update_by"));
					domain.setCreateDateStr(BuckWaDateUtils.get_current_ddMMMyyyyhhmmss_thai_from_date(rs.getTimestamp("created_date")));	
					domain.setUpdateDateStr(BuckWaDateUtils.get_current_ddMMMyyyyhhmmss_thai_from_date(rs.getTimestamp("updated_date")));
					domain.setYear(rs.getString("yearName"));
					domain.setSemester(rs.getString("semesterName"));
					domain.setClassRoomProcessId(rs.getLong("classroom_procress_id"));
					return domain;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	@Override
	public List<Person> getUserList(Long yearId,Long semesterId){
		List<Person> resultList=null;
		try {
			String sql = "select * from ( "+
			 "select distinct pt.teacher_id,pt.name from pam_teacher pt "+
			 "inner join pam_teacher_teach tt on tt.teacher_id=pt.code "+
			 "where user_id is null and pt.year_id ="+yearId+" and pt.semester_id="+semesterId+" "+
			 "union "+
			 "select distinct pt.teacher_id,pt.name from pam_teacher pt "+
			 "inner join pam_master_teacher_teach tt on tt.teacher_id=pt.code "+
			 "where user_id is null and pt.year_id ="+yearId+" and pt.semester_id="+semesterId+" "+
			 ") A order by A.name asc ";
			resultList = this.jdbcTemplate.query(sql, personMapper);
		} catch (EmptyResultDataAccessException e) {

		}
		
		return resultList;
	}
	
	private static final PersonMapper personMapper = new PersonMapper();
	private static class PersonMapper implements RowMapper<Person> {
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			Person domain = new Person();
			domain.setUserId(rs.getLong("teacher_id"));
			domain.setFullName(rs.getString("name"));
			return domain;
		}
	}
	
	@Override
	public void importSubject(Long yearId,Long semesterId,String createBy) throws Exception{
		try{
			this.jdbcTemplate
			.update(
					"insert into pam_subject(year_id,semester_id,code,name_th,name_en,credit,lect_hr,prac_hr,self_hr,detail,create_by,created_date)  " +
					"select ?,?,subject_id,subject_tname,subject_ename,credit,lect_hr,prac_hr,self_hr,detail,?,? from subject ",
					yearId, semesterId,createBy,new java.sql.Timestamp(new Date().getTime()));
			
			this.jdbcTemplate
			.update(
					"insert into pam_master_subject(year_id,semester_id,code,name_th,name_en,credit,lect_hr,prac_hr,self_hr,detail,faculty_id,credit_cal,last_thesis,prerequisite2,lock_ed,create_by,created_date)  " +
					"select ?,?,subject_id,subject_tname,subject_ename,credit,lect_hr,prac_hr,self_hr,detail,faculty_id,credit_cal,last_thesis,prerequisite2,lock_ed,?,? from master_subject ",
					yearId, semesterId,createBy,new java.sql.Timestamp(new Date().getTime()));
			
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
	}
	@Override
	public void importTeacher()  throws Exception{
		try{
			this.jdbcTemplate.update("insert into pam_teacher(code,name) select teacher_id,teacher_tname from teacher");
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public void importTeacher(Long yearId,Long semesterId)  throws Exception{
		try{
			this.jdbcTemplate.update("insert into pam_teacher(code,name,year_id,semester_id) select teacher_id,teacher_tname,"+yearId+" as year,"+semesterId+" as semesterId from teacher");
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public boolean updateTeacher(String name,Long userId){
		try{
			this.jdbcTemplate
			.update(
					"update pam_teacher set user_id=? where name=?",userId, name);
			return true;
		}catch(Exception e){
			
		}
		return false;
	}
	
	
	
	@Override
	public void importTeachTable(Long yearId,Long semesterId,String createBy) throws Exception{
		try{
			this.jdbcTemplate
			.update(
					"insert into pam_teach_table(subject_code,semester_id,year_id,faculty_id,dept_id,curr_id,curr2_id,class,program,section,`limit`, "+
					"room,building,room_no,building_no,teach_day,teach_time,teach_time2,mexam_day,mexam_date,mexam_time,mexam_time2,exam_day, "+
					"exam_date,exam_time,exam_time2,exam_type,lect_or_prac,remark,subj_type,subj_owner,sect,closed,lastupdate, "+
					"operator,`lock`,rule_id,create_by,created_date) "+
					"select subject_id,?,?,faculty_id,dept_id,curr_id, "+
					"curr2_id,class,program,section,`limit`,room,building,room_no,building_no,teach_day, "+
					"teach_time,teach_time2,mexam_day,mexam_date,mexam_time,mexam_time2,exam_day,exam_date, "+
					"exam_time,exam_time2,exam_type,lect_or_prac,remark,subj_type,subj_owner,sect,closed,lastupdate,operator,`lock`,rule_id,?,?  "+
					"from teach_table ",
					semesterId, yearId,createBy,new java.sql.Timestamp(new Date().getTime()));
			
			
			this.jdbcTemplate
			.update(
					"insert into pam_master_teach_table(subject_code,semester_id,year_id,faculty_id,dept_id,curr_id,branch_id,class,admis_type,term_admis, "+
					"section,`limit`,room,building,teach_day,teach_time,teach_time2,mexam_day,mexam_date,mexam_time, "+
					"mexam_time2,exam_day,exam_date,exam_time,exam_time2,exam_type,lect_or_prac,remark,subj_type,closed,`timestamp`, "+
					"create_by,created_date) "+
					"select subject_id,?,?,faculty_id,dept_id,curr_id,branch_id,class,admis_type,term_admis, "+
					"section,`limit`,room,building,teach_day,teach_time,teach_time2,mexam_day,mexam_date,mexam_time, "+
					"mexam_time2,exam_day,exam_date,exam_time,exam_time2,exam_type,lect_or_prac,remark,subj_type,closed,`timestamp`,?,? "+
					"from master_teach_table where admis_type=1  ",
					semesterId, yearId,createBy,new java.sql.Timestamp(new Date().getTime()));
			
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public void importTeacherTeach(Long yearId,Long semesterId,String createBy)  throws Exception{
		try{
			this.jdbcTemplate
			.update(
					"insert into pam_teacher_teach(subject_code,semester_id,year_id,teacher_id,faculty_id,dept_id,curr_id,curr2_id,class,program,section,lect_or_prac, "+
					"priority,time_stamp,create_by,created_date) "+
					"select subject_id,?,?,teacher_id,faculty_id,dept_id,curr_id, "+
					"curr2_id,class,program,section,lect_or_prac,priority,time_stamp,?,? "+ 
					"from teacher_teach",
					semesterId, yearId,createBy,new java.sql.Timestamp(new Date().getTime()));
			
			
			this.jdbcTemplate
			.update(
					"insert into pam_master_teacher_teach(subject_code,semester_id,year_id,teacher_id,faculty_id,dept_id,curr_id,branch_id,class,admis_type,term_admis, "+
					"section,lect_or_prac,priority,`timestamp`,create_by,created_date) "+
					"select subject_id,?,?,teacher_id,faculty_id,dept_id,curr_id,branch_id,class,admis_type,term_admis, "+
					"section,lect_or_prac,priority,`timestamp`,?,? "+ 
					"from master_teacher_teach where admis_type=1",
					semesterId, yearId,createBy,new java.sql.Timestamp(new Date().getTime()));
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public boolean removeSubject(Long semesterId,Long yearId){
		try{
			this.jdbcTemplate
			.update(
					"delete from pam_subject where year_id =? and semester_id =?",yearId, semesterId);
			
			this.jdbcTemplate
			.update(
					"delete from pam_master_subject where year_id =? and semester_id =?",yearId, semesterId);
			
		/*	this.jdbcTemplate
			.update(
					"delete from pam_subject ");
			
			this.jdbcTemplate
			.update(
					"delete from pam_master_subject ");*/
			
			return true;
		}catch(Exception e){
			
		}
		return false;
	}
	@Override
	public boolean removeTeacher(){
		try{
			this.jdbcTemplate
			.update(
					"delete from pam_teacher where code in (select teacher_id from teacher)");
			return true;
		}catch(Exception e){
			
		}
		return false;
	}
	@Override
	public boolean removeTeacher(Long semesterId,Long yearId){
		try{
			this.jdbcTemplate
			.update(
					"delete from pam_teacher where code in (select teacher_id from teacher) and year_id="+yearId+" and semester_id="+semesterId+" ");
			return true;
		}catch(Exception e){
			
		}
		return false;
	}
	@Override
	public boolean removeTeachTable(Long semesterId,Long yearId){
		try{
			this.jdbcTemplate
			.update(
					"delete from pam_teach_table where year_id =? and semester_id =?",yearId, semesterId);
			
			this.jdbcTemplate
			.update(
					"delete from pam_master_teach_table where year_id =? and semester_id =?",yearId, semesterId);
			return true;
		}catch(Exception e){
			
		}
		return false;
	}
	@Override
	public boolean removeTeacherTeach(Long semesterId,Long yearId){
		
		try{
			this.jdbcTemplate
			.update(
					"delete from pam_teacher_teach where year_id =? and semester_id =? ",yearId, semesterId);
			
			this.jdbcTemplate
			.update(
					"delete from pam_master_teacher_teach where year_id =? and semester_id =? ",yearId, semesterId);
			return true;
		}catch(Exception e){
			
		}
		return false;
		
	}
	
	@Override
	public boolean clearFlagClassRoomProcess(Long semesterId,Long yearId){
		
		try{
			this.jdbcTemplate
			.update(
					"update classroom_procress set flag=2 where year_id=? and semester_id=?",yearId, semesterId);
			return true;
		}catch(Exception e){
			
		}
		return false;
		
	}
	
}

