package com.buckwa.dao.impl.pbp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pbp.DeanDao;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.report.DepartmentWorkTypeReport;
import com.buckwa.domain.pbp.report.FacultyReportLevel;
import com.buckwa.domain.pbp.report.FacultyWorkTypeReport;

@Repository("deanDao")
public class DeanDaoImpl implements DeanDao {
	private static Logger logger = LoggerFactory.getLogger(DeanDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
 
	
	
	@Override
	public List<FacultyReportLevel> getFacultyReportLevel( String workType ,Faculty faculty ) {
		List<FacultyReportLevel> returnList = new ArrayList();
				
		 
		String getFacultyReportLevel =" select *  from report_department where faculty_code  ='"+faculty.getCode()+"'   and academic_year="+faculty.getAcademicYear()
				//+" order by department_code ";
				+ " order by CAST(mark_total AS DECIMAL(9,2)) desc";
		logger.info(" getFacultyReportLevel:"+getFacultyReportLevel);
		List<DepartmentWorkTypeReport> tmpList  = this.jdbcTemplate.query(getFacultyReportLevel,	new DepartmentWorkTypeReportMapper() );	
		
		for(DepartmentWorkTypeReport tmp:tmpList){
			FacultyReportLevel returnTmp = new FacultyReportLevel();
			
			returnTmp.setDepartmentName(tmp.getDepartmentName());
			returnTmp.setMark(tmp.getMarkTotal());
		 
			
					returnList.add(returnTmp);
		}
  
		
		return returnList;
	}
	
 
	
	@Override
	public List<DepartmentWorkTypeReport> getReportWorkTypeFaculty( String workType ,Faculty faculty ) {
		List<DepartmentWorkTypeReport> returnList = new ArrayList();				
		String getWorkTypeReportDepartmentSQL =" select *  from report_department where faculty_code  ='"+faculty.getCode()+"' and academic_year="+faculty.getAcademicYear()
				//+" order by department_code "
				+ " order by CAST(mark_" + workType + " AS DECIMAL(9,2)) desc";
		logger.info(" getWorkTypeReportDepartmentSQL:"+getWorkTypeReportDepartmentSQL);
		returnList  = this.jdbcTemplate.query(getWorkTypeReportDepartmentSQL,	new DepartmentWorkTypeReportMapper() );			
		return returnList;
	}
	
	
	private class FacultyWorkTypeReportMapper implements RowMapper<FacultyWorkTypeReport> {   						
        @Override
		public FacultyWorkTypeReport mapRow(ResultSet rs, int rowNum) throws SQLException {
        	FacultyWorkTypeReport domain = new FacultyWorkTypeReport(); 
        	
        	domain.setAcademicYear(rs.getString("academic_year"));
        	domain.setTypeName1(rs.getString("work_type_name1"));
        	domain.setTypeCode1(rs.getString("work_type_code1"));
        	
        	domain.setTypeName2(rs.getString("work_type_name2"));
        	domain.setTypeCode2(rs.getString("work_type_code2"));
        	
        	domain.setTypeName3(rs.getString("work_type_name3"));
        	domain.setTypeCode3(rs.getString("work_type_code3"));
        	
        	domain.setTypeName4(rs.getString("work_type_name4"));
        	domain.setTypeCode4(rs.getString("work_type_code4"));
        	
        	domain.setTypeName5(rs.getString("work_type_name5"));
        	domain.setTypeCode5(rs.getString("work_type_code5"));
        	
        	domain.setFacultyName(rs.getString("faculty_name"));
        	domain.setDepartmentName(rs.getString("department_name"));
        	//domain.setPersonName(rs.getString("person_name"));
        	domain.setMark1(rs.getString("mark_1"));
        	domain.setMark2(rs.getString("mark_2"));
        	domain.setMark3(rs.getString("mark_3"));
        	domain.setMark4(rs.getString("mark_4"));
        	domain.setMark5(rs.getString("mark_5"));
        	//domain.setMarkTotal(rs.getString("mark_total"));
 
		 
		return domain;
    }
	}
	
	private class FacultyReportLevelMapper implements RowMapper<FacultyReportLevel> {   						
        @Override
		public FacultyReportLevel mapRow(ResultSet rs, int rowNum) throws SQLException {
        	FacultyReportLevel domain = new FacultyReportLevel(); 
        	domain.setFacultyCode(rs.getString("faculty_code"));
			domain.setFacultyName(rs.getString("faculty_name"));		
			domain.setDepartmentCode(rs.getString("department_code"));	
			domain.setDepartmentName(rs.getString("department_name"));	
			domain.setMark(rs.getString("department_mark"));	
			domain.setAcademicYear(rs.getString("academic_year"));
		return domain;
    }
	}
	private class DepartmentWorkTypeReportMapper implements RowMapper<DepartmentWorkTypeReport> {   						
        @Override
		public DepartmentWorkTypeReport mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DepartmentWorkTypeReport domain = new DepartmentWorkTypeReport(); 
        	
        	domain.setAcademicYear(rs.getString("academic_year"));
        	domain.setTypeName1(rs.getString("work_type_name1"));
        	domain.setTypeCode1(rs.getString("work_type_code1"));
        	
        	domain.setTypeName2(rs.getString("work_type_name2"));
        	domain.setTypeCode2(rs.getString("work_type_code2"));
        	
        	domain.setTypeName3(rs.getString("work_type_name3"));
        	domain.setTypeCode3(rs.getString("work_type_code3"));
        	
        	domain.setTypeName4(rs.getString("work_type_name4"));
        	domain.setTypeCode4(rs.getString("work_type_code4"));
        	
        	domain.setTypeName5(rs.getString("work_type_name5"));
        	domain.setTypeCode5(rs.getString("work_type_code5"));
        	
        	domain.setFacultyName(rs.getString("faculty_name"));
        	domain.setDepartmentName(rs.getString("department_name"));
        	//domain.setPersonName(rs.getString("person_name"));
        	domain.setMark1(rs.getString("mark_1"));
        	domain.setMark2(rs.getString("mark_2"));
        	domain.setMark3(rs.getString("mark_3"));
        	domain.setMark4(rs.getString("mark_4"));
        	domain.setMark5(rs.getString("mark_5"));
        	domain.setMarkTotal(rs.getString("mark_total"));
 
		 
		return domain;
    }
	}
	
	@Override
	public List<DepartmentWorkTypeReport> getReportWorkTypeCompareFaculty(Faculty faculty) {
		List<DepartmentWorkTypeReport> returnList = new ArrayList<DepartmentWorkTypeReport>();				
		String getWorkTypeReportDepartmentSQL = "select * from report_department where faculty_code  ='"+faculty.getCode()+"' and academic_year="+faculty.getAcademicYear()
			+ " order by department_code ";
		logger.info(" getReportWorkTypeCompareFacultySQL:"+getWorkTypeReportDepartmentSQL);
		returnList  = this.jdbcTemplate.query(getWorkTypeReportDepartmentSQL, new DepartmentWorkTypeReportMapper());			
		return returnList;
	}
 
}
