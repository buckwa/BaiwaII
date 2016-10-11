package com.buckwa.dao.impl.pbp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.buckwa.service.intf.pbp.SchoolUtilDao;

@Repository("schoolUtilDao")
public class SchoolUtilDaoImpl implements SchoolUtilDao {
	private static Logger logger = LoggerFactory.getLogger(SchoolUtilDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String getCurrentAcademicYear () {  
			String currentAcademicYearSQL =" SELECT name FROM academic_year where year_status='Y' ";								 
			String currentAcademicYear = (String)this.jdbcTemplate.queryForObject(	currentAcademicYearSQL , String.class); 
		return currentAcademicYear;
	}
	
	
	
	public String getCourseNameByCourseId (Long courseId) {  
		 
		String sql =" SELECT name FROM course where course_id="+courseId;	
		logger.info("  getCourseNameByCourseId sql: "+sql);
		String returnStr = (String)this.jdbcTemplate.queryForObject(	sql , String.class); 
	
	return returnStr;
}
	
	
	public String getDepartmentByUserName (String  regId,String academic_year) {  
		 String userName = null;		 
		 try{
		String sql =" SELECT department_desc FROM person_pbp where email like '%"+regId+"%' and academic_year="+academic_year;	
		logger.info("  getDepartmentByUserName sql: "+sql);
		userName = (String)this.jdbcTemplate.queryForObject(	sql , String.class); 
	 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	     return userName;
	}
	
	public String getDepartmentByHeadUserName (String  regId,String academic_year) {  
		 String userName = null;		 
		 try{
		String sql =" SELECT head_department FROM person_pbp where email like '%"+regId+"%' and academic_year="+academic_year;	
		logger.info("  getDepartmentByUserName sql: "+sql);
		userName = (String)this.jdbcTemplate.queryForObject(	sql , String.class); 
	 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	     return userName;
	}
 
	public String getHeadUserNamebyDepartmentDesc (String  facultyDesc,String  departmentDesc,String academicYear) {  
		 String code = null;		 
		 try{
			 String sql ="  select email from person_pbp where head_department ='"+departmentDesc+"' and faculty_desc ='"+facultyDesc+"' and academic_year="+academicYear;
	//	String sql =" select code from department where name ='"+departmentName+"' and academic_year="+academicYear;	
		logger.info("  getHeadUserNamebyDepartmentDesc sql: "+sql);
		code = (String)this.jdbcTemplate.queryForObject(	sql , String.class); 
	 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	     return code;
	}
	
	
	
	public String getDepartmentCodeByDepartmentName (String  departmentName,String academicYear) {  
		 String code = null;		 
		 try{
		String sql =" select code from department where name ='"+departmentName+"' and academic_year="+academicYear;	
		logger.info("  getDepartmentCodeByDepartmentName sql: "+sql);
		code = (String)this.jdbcTemplate.queryForObject(	sql , String.class); 
	 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	     return code;
	}
	
	public String getFacultyCodeByFacultyName (String  facultyName,String academicYear) {  
		 String code = null;		 
		 try{
		 
		String sql =" select code from faculty where name ='"+facultyName+"' and academic_year="+academicYear;	
		logger.info("  getFacultyCodeByFacultyName sql: "+sql);
		code = (String)this.jdbcTemplate.queryForObject(	sql , String.class); 
	 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	     return code;
	}
	
	
	public String getFacultyCodeByDepartmentName (String  departmentName,String academicYear) {  
		 String code = null;		 
		 try{
		 
		String sql =" select faculty_code from department where name ='"+departmentName+"' and academic_year="+academicYear;	
		logger.info("  getFacultyCodeByDepartmentName sql: "+sql);
		code = (String)this.jdbcTemplate.queryForObject(	sql , String.class); 
	 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	     return code;
	}
	
	
	public String getFacutyByUserName (String  regId,String academicYear) {  
		 String userName = null;		 
		 try{
		String sql =" SELECT faculty_desc FROM person_pbp where email like '%"+regId+"%' and academic_year="+academicYear;	
		logger.info("  getFacutyByUserName sql: "+sql);
		userName = (String)this.jdbcTemplate.queryForObject(	sql , String.class); 
	 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	     return userName;
}
	
	public String getFacutyByDeanUserName (String  regId,String academicYear) {  
		 String userName = null;		 
		 try{
		String sql =" SELECT dean_faculty FROM person_pbp where email like '%"+regId+"%' and academic_year="+academicYear;	
		logger.info("  getFacutyByUserName sql: "+sql);
		userName = (String)this.jdbcTemplate.queryForObject(	sql , String.class); 
	 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	     return userName;
}
	
	
	public String getUserNameFromRegId (String  regId,String academicYear) {  
		 String userName = null;
		 
		 try{
		String sql =" SELECT email FROM person_pbp where reg_id='"+regId+"' and academic_year="+academicYear;	
		logger.info("  getUserNameFromRegId sql: "+sql);
		userName = (String)this.jdbcTemplate.queryForObject(	sql , String.class); 
	 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	     return userName;
}
	
	
	
	public String getFacultyCodeFromRegId (String  regId,String academicYear) {  
		 String userName = null;
		 
		 try{
			 String sql ="   select f.code from faculty f 			 left join person_pbp p on (p.faculty_desc=f.name)			 where p.reg_id='"+regId+"' and p.academic_year="+academicYear;	
		//String sql =" SELECT email FROM person_pbp where reg_id='"+regId+"' and academic_year="+academicYear;	
		logger.info("  getUserNameFromRegId sql: "+sql);
		userName = (String)this.jdbcTemplate.queryForObject(	sql , String.class); 
	 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	     return userName;
}
	
	
	public String getRegIdFromUserName (String  email,String academicYear) {  
		 String userName = null;
		 
		 try{
		String sql =" SELECT reg_id FROM person_pbp where email='"+email+"' and academic_year="+academicYear;	
		//logger.info("  getRegIdFromUserName sql: "+sql);
		userName = (String)this.jdbcTemplate.queryForObject(	sql , String.class); 
	 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	     return userName;
}
	
	public String getUnitDescMyCode (String code,String acedemicYear) {  
		String returnStr ="";
		
		try{
		//String sql =" SELECT name FROM academic_unit where code='"+code+"' and academic_year='"+acedemicYear+"'";	
			String sql =" SELECT name FROM academic_unit where code='"+code+"'" ;	
		//logger.info("  getMarkDescMyCode sql: "+sql);
		 returnStr = (String)this.jdbcTemplate.queryForObject(	sql , String.class);
		
		}catch(org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
	
		return returnStr;
	}
	
	public List<Map<String, Object>> getAllMapUnitDesc() {  
		List<Map<String, Object>> map = new ArrayList<>();
		try{
			String sql =" SELECT code, name FROM academic_unit ";
			map = jdbcTemplate.queryForList(sql);
			
		}catch(org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
		return map;
	}
	
	public static void main(String arg[]){
		
		ApplicationContext context =  new ClassPathXmlApplicationContext("jdbc_main_connect.xml");
		SchoolUtilDaoImpl impl = new SchoolUtilDaoImpl();
		impl.setJdbcTemplate( (JdbcTemplate) context.getBean("jdbcTemplateMain"));
		
		List<Map<String, Object>> mapList = impl.getAllMapUnitDesc();
		
		Map<Object, String> map = new HashMap<>();
		for (Map<String, Object> m : mapList) {
			map.put(m.get("code")+"", (String)m.get("name"));
		}
		
		System.out.println(map.get("1"));
		
		
	}
 
}
	
 
