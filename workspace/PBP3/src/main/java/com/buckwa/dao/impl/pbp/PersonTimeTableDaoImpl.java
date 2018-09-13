package com.buckwa.dao.impl.pbp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.pbp.PersonTimetableDao;
import com.buckwa.domain.admin.Role;
import com.buckwa.domain.pbp.AcademicKPIUserMappingWrapper;
import com.buckwa.domain.pbp.report.PersonReport;
import com.buckwa.domain.pbp.report.TeacherReport;
import com.buckwa.domain.pbp.report.TimeTableReport;
import com.buckwa.util.BeanUtils;

@Repository("personTimetableDao")
public class PersonTimeTableDaoImpl implements PersonTimetableDao {
	private static Logger logger = LoggerFactory.getLogger(PersonTimeTableDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
  
 



	@Override
	public List<TimeTableReport> getTimeTable( String academicYear ,String userName,String semester) {	 
		
		
		List<TimeTableReport> returnList = new ArrayList<TimeTableReport>();
		
		
		AcademicKPIUserMappingWrapper   academicKPIUserMappingWrapper = new AcademicKPIUserMappingWrapper(); 
		// Get Department belong to head
		
		String sqlName = " select p.email,p.thai_name , p.thai_surname ,  concat(concat(p.thai_name,' '),p.thai_surname) as fullName ,p.reg_id"+
				" from person_pbp p  "+
				" where p.email='"+userName+"' AND academic_year ='"+academicYear+"'   ";
		
		List<PersonReport> personReportList = new ArrayList();
		try{
			logger.info(" sqlName:"+sqlName);
			personReportList = this.jdbcTemplate.query(sqlName,	new PersonReportMapper() );
			
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		} 	
		
		if(personReportList==null||personReportList.size()==0){
			logger.info(" ############## Not Found User Name:"+userName+"  in pbp_person table ############");
		}else{
			
			PersonReport zeroPerson = personReportList.get(0);
			
			
			/*
			String fullName = zeroPerson.getThaiName().trim()+" "+zeroPerson.getThanSurName().trim();
			
			String sqlTeacher = " select * from staff s where s.full_name='"+fullName+"'";
			
			TeacherReport teacherReport = null;
			try{
				logger.info(" sqlTeacher:"+sqlTeacher);
				teacherReport = this.jdbcTemplate.queryForObject(sqlTeacher,	new TeacherReportMapper() );
				
			}catch (org.springframework.dao.EmptyResultDataAccessException ex){
				ex.printStackTrace();
			} 
			
			if(teacherReport==null){
				logger.info(" ############## Not Found Teacher name :"+fullName+"  in staff table ############");
			}else{
				
				logger.info(" ##############   Found Teacher name :"+fullName+"  in staff table ############");
				*/
				/*
				String sqlTimeTable = " select t.subject_id,t.year,s.section_cd,s.semester,staffmap.teacher_id as teacher_code,staff.full_name, t.eng_name,t.thai_name "+
						" from time_table t "+
						" left join section s on ( t.time_table_cd = s.time_table_id) "+
						" left join staff_mapping staffmap on (s.section_id=staffmap.section_id) "+
						" left join staff staff on (staffmap.teacher_id = staff.code) "+
						" where t.year ="+academicYear+" and staffmap.teacher_id="+teacherReport.getCode()+" and t.semester="+semester+" "+
						" order by t.subject_id ,s.section_cd ";
	
				
				*/
				
				String sqlTimeTable = " SELECT * FROM time_table t where t.year='"+academicYear+"' and t.semester='"+semester+"' and t.teacher_id='"+zeroPerson.getRegId()+"'";
				logger.info(" ##############   sqlTimeTable:"+sqlTimeTable);
				try{
					logger.info(" sqlTimeTable:"+sqlTimeTable);
					returnList = this.jdbcTemplate.query(sqlTimeTable,	new TimeTableReportMapper() );
					
					/*
					for(TimeTableReport reportTmp:returnList){
						
						String teacherId = reportTmp.getTeacherId();
						logger.info(" teacherId:"+teacherId+ " regId:"+zeroPerson.getRegId());
						if(!zeroPerson.getRegId().equalsIgnoreCase(teacherId)){
							logger.info(" Set empty");
							reportTmp.setThaiName(" ");
						}else{
							logger.info("  Return Full Name");
						}
					}
					*/
				}catch (org.springframework.dao.EmptyResultDataAccessException ex){
					ex.printStackTrace();
				} 
				
				
			//} 
			
					
		} 
 
		
		return returnList;
	}
	
	 
	
	 
	@Override
	public List<TimeTableReport> getTimeTableShare( String academicYear ,String subjectId,String semester) {	 
		
		
		List<TimeTableReport> returnList = new ArrayList<TimeTableReport>();
		
	 try{
				
				String sqlTimeTable = " SELECT * FROM time_table t where t.year='"+academicYear+"' and t.semester='"+semester+"' and t.subject_id like '%"+subjectId+"%' GROUP BY t.sect ";
				logger.info(" ##############   sqlTimeTable:"+sqlTimeTable);
			 
					logger.info(" sqlTimeTable:"+sqlTimeTable);
					returnList = this.jdbcTemplate.query(sqlTimeTable,	new TimeTableReportMapper() );
		 
				}catch (org.springframework.dao.EmptyResultDataAccessException ex){
					ex.printStackTrace();
				} 
 

		
		return returnList;
	}
	
	
	
	@Override
	public TimeTableReport getTimeTableById( String timetableId ) {	  
		TimeTableReport returnObj =  new TimeTableReport(); 
	 try{
				
				String sqlTimeTable = " SELECT * FROM time_table t where t.time_table_cd='"+timetableId+"' ";
				logger.info(" ##############   sqlTimeTable:"+sqlTimeTable); 
				logger.info(" sqlTimeTable:"+sqlTimeTable);
				returnObj = this.jdbcTemplate.queryForObject(sqlTimeTable,	new TimeTableReportMapper() ); 
				}catch (org.springframework.dao.EmptyResultDataAccessException ex){
					ex.printStackTrace();
				}  
		return returnObj;
	}
	
	
	@Override
	public void updateTimeTable(TimeTableReport domain) {
		logger.info(" # updateTimeTable domain: "+BeanUtils.getBeanString(domain));
		this.jdbcTemplate.update( "update  time_table "
				+ "set subject_id=?"
				+ ",eng_name=?"		
				+ ",thai_name=?"
				+ ",lect_or_prac=?"
				+ ",degree=?"	
				+ ",credit=?"	
				+ ",student_total=?"	
				+ ",section=?"				
				+ ",teach_time=?"	
				+ ",teach_time2=?"	
				+ ",teach_day=?"
				+ ",lect_hr=?"
				+ ",prac_hr=?"
				+ "  where time_table_cd=? ", domain.getTimetableId(),
				domain.getEngName(),
				domain.getThaiName(),
				domain.getLecOrPrac(),
				domain.getDegree(),
				domain.getCredit(),
				domain.getTotalStudent(),
				domain.getSecNo(),
				domain.getTeachTime1(),
				domain.getTeachTime2(),
				domain.getTeachDay(),
				domain.getTeachHrEdit(),
				domain.getTeachHrEdit(),
				domain.getTimetableId()); 
	
	}
	
	@Override
	public void createTimeTable(TimeTableReport domain) {
		logger.info(" start ");		
		final TimeTableReport finalDomain = domain;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into time_table (subject_id, eng_name,thai_name,lect_or_prac,degree,credit,student_total,section,teacher_id,year,semester,teach_day,teach_time,teach_time2,lect_hr,prac_hr) values (?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalDomain.getSubjectCode());
				ps.setString(2,finalDomain.getEngName());
				ps.setString(3,finalDomain.getThaiName());
				ps.setString(4,finalDomain.getLecOrPrac());
				ps.setString(5,finalDomain.getDegree());
				ps.setString(6,finalDomain.getCredit());
				ps.setString(7,finalDomain.getTotalStudent());
				ps.setString(8,finalDomain.getSecNo());
				ps.setString(9,finalDomain.getTeacherId());			
				ps.setString(10,finalDomain.getAcademicYear());	
				ps.setString(11,finalDomain.getSemester());	
				ps.setString(12,finalDomain.getTeachDay());	
				ps.setString(13,finalDomain.getTeachTime1());	
				ps.setString(14,finalDomain.getTeachTime2());	
				ps.setString(15,finalDomain.getTeachHrEdit());
				ps.setString(16,finalDomain.getTeachHrEdit());
				return ps;  
				}
			}, 	keyHolder); 	
		
		logger.info(" End key  :"+keyHolder.getKey());	
 	
	}

	
	/*
	 * 
	 * 
	 * DROP TABLE IF EXISTS `pbp2`.`time_table`;
CREATE TABLE  `pbp2`.`time_table` (
  `time_table_cd` int(11) NOT NULL AUTO_INCREMENT,
  `faculty_id` varchar(2) DEFAULT NULL,
  `dept_id` varchar(2) DEFAULT NULL,
  `curr_id` varchar(45) DEFAULT NULL,
  `curr2_id` varchar(3) DEFAULT NULL,
  `subject_id` varchar(8) DEFAULT NULL,
  `semester` varchar(8) DEFAULT NULL,
  `year` varchar(4) DEFAULT NULL,
  `class` varchar(2) DEFAULT NULL,
  `program` varchar(4) DEFAULT NULL,
  `lect_or_prac` char(1) DEFAULT NULL,
  `priority` int(1) DEFAULT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `thai_name` varchar(450) DEFAULT NULL,
  `eng_name` varchar(450) DEFAULT NULL,
  `department_id` varchar(45) DEFAULT NULL,
  `section` varchar(45) DEFAULT NULL,
  `teacher_id` varchar(45) DEFAULT NULL,
  `teach_day` varchar(45) DEFAULT NULL,
  `teach_time` varchar(45) DEFAULT NULL,
  `teach_time2` varchar(45) DEFAULT NULL,
  `subject_type` varchar(45) DEFAULT NULL,
  `sect` varchar(45) DEFAULT NULL,
  `closed` varchar(45) DEFAULT NULL,
  `student_total` varchar(45) DEFAULT NULL,
  `degree` varchar(45) DEFAULT NULL,
  `credit` varchar(45) DEFAULT NULL,
  `lect_hr` varchar(45) DEFAULT NULL,
  `prac_hr` varchar(45) DEFAULT NULL,
  `from_source` varchar(45) DEFAULT NULL,
  `is_co_teach` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`time_table_cd`)
) ENGINE=MyISAM AUTO_INCREMENT=292090 DEFAULT CHARSET=utf8;
			errors.rejectValue("subjectCode", "required.field", "Required field");
			errors.rejectValue("subjectName", "required.field", "Required field");
			errors.rejectValue("lecOrPrac", "required.field", "Required field");
			errors.rejectValue("teachHr", "required.field", "Required field");
			errors.rejectValue("credit", "required.field", "Required field");
			errors.rejectValue("degreeStr", "required.field", "Required field");
			errors.rejectValue("totalStudent", "required.field", "Required field");
			errors.rejectValue("secNo", "required.field", "Required field");
			errors.rejectValue("teachDayStr", "required.field", "Required field");
			errors.rejectValue("teachTimeFromTo", "required.field", "Required field");
	 	
DROP TABLE IF EXISTS `pbp2`.`time_table`;
CREATE TABLE  `pbp2`.`time_table` (
  `time_table_cd` int(11) NOT NULL AUTO_INCREMENT,
  `faculty_id` varchar(2) DEFAULT NULL,
  `dept_id` varchar(2) DEFAULT NULL,
  `curr_id` varchar(45) DEFAULT NULL,
  `curr2_id` varchar(3) DEFAULT NULL,
  `subject_id` varchar(8) DEFAULT NULL,
  `semester` varchar(8) DEFAULT NULL,
  `year` varchar(4) DEFAULT NULL,
  `class` varchar(2) DEFAULT NULL,
  `program` varchar(4) DEFAULT NULL,
  `lect_or_prac` char(1) DEFAULT NULL,
  `priority` int(1) DEFAULT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `thai_name` varchar(450) DEFAULT NULL,
  `eng_name` varchar(450) DEFAULT NULL,
  `department_id` varchar(45) DEFAULT NULL,
  `section` varchar(45) DEFAULT NULL,
  `teacher_id` varchar(45) DEFAULT NULL,
  `teach_day` varchar(45) DEFAULT NULL,
  `teach_time` varchar(45) DEFAULT NULL,
  `teach_time2` varchar(45) DEFAULT NULL,
  `subject_type` varchar(45) DEFAULT NULL,
  `sect` varchar(45) DEFAULT NULL,
  `closed` varchar(45) DEFAULT NULL,
  `student_total` varchar(45) DEFAULT NULL,
  `degree` varchar(45) DEFAULT NULL,
  `credit` varchar(45) DEFAULT NULL,
  `lect_hr` varchar(45) DEFAULT NULL,
  `prac_hr` varchar(45) DEFAULT NULL,
  `from_source` varchar(45) DEFAULT NULL,
  `is_co_teach` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`time_table_cd`)
) ENGINE=MyISAM AUTO_INCREMENT=292090 DEFAULT CHARSET=utf8;
	 */
	private class TimeTableReportMapper implements RowMapper<TimeTableReport> {  
	 
        @Override
		public TimeTableReport mapRow(ResultSet rs, int rowNum) throws SQLException {
        	TimeTableReport domain = new TimeTableReport(); 
        	//domain.setDepartmentId(rs.getLong("department_id"));
        	//t.subject_id,t.year,s.section_cd,s.semester,staffmap.teacher_id as teacher_code,staff.full_name
        	domain.setTimetableId(rs.getString("time_table_cd"));
        	domain.setSubjectName(rs.getString("eng_name")==null?" ":rs.getString("eng_name"));
        	domain.setSubjectCode(rs.getString("subject_id"));
        	domain.setAcademicYear(rs.getString("year"));
        	domain.setSecNo(rs.getString("section"));
        	domain.setSemester(rs.getString("semester"));
        	//domain.setTeacherName(rs.getString("full_name"));
        	
        	domain.setEngName(rs.getString("eng_name")==null?" ":rs.getString("eng_name"));
        	domain.setThaiName(rs.getString("thai_name")==null?" ":rs.getString("thai_name").trim());
        	
        	//if(rs.getString("thai_name")==null||rs.getString("thai_name").trim().length()==0){
        	//	domain.setThaiName(rs.getString("eng_name")==null?" ":rs.getString("eng_name"));
        	//}
//        	domain.setCount("20");
        	
        	domain.setTotalStudent(rs.getString("student_total"));
        	domain.setDegree(rs.getString("degree"));
        	domain.setTeachTime1(rs.getString("teach_time"));
        	domain.setTeachTime2(rs.getString("teach_time2"));
        	domain.setTeachDay(rs.getString("teach_day"));
        	domain.setCredit(rs.getString("credit"));
        	domain.setLecOrPrac(rs.getString("lect_or_prac"));;
        	domain.setLectHr(rs.getString("lect_hr"));
        	domain.setPracHr(rs.getString("prac_hr"));
        	domain.setTeacherId(rs.getString("teacher_id"));
        	domain.setFacultyId(rs.getString("faculty_id"));
        	domain.setDeptId(rs.getString("dept_id"));
        	domain.setIsTA(rs.getString("is_ta"));
        	domain.setRemark(rs.getString("remark"));
        	domain.setIsProjectBase(rs.getString("is_project_base"));
        	domain.setTeachtimeStr(rs.getString("teachtime_str"));
		return domain;
    }
	}
	 

	private class PersonReportMapper implements RowMapper<PersonReport> {   						
        @Override
		public PersonReport mapRow(ResultSet rs, int rowNum) throws SQLException {
        	PersonReport domain = new PersonReport(); 
        	domain.setThaiName(rs.getString("thai_name").trim());
			 domain.setThanSurName(rs.getString("thai_surname"));
			 domain.setRegId(rs.getString("reg_id"));
		return domain;
    }
	}
 
	private class TeacherReportMapper implements RowMapper<TeacherReport> {   						
        @Override
		public TeacherReport mapRow(ResultSet rs, int rowNum) throws SQLException {
        	TeacherReport domain = new TeacherReport(); 
        	domain.setCode(rs.getString("code"));
			 domain.setFullName(rs.getString("full_name"));
		return domain;
    }
	}
}
