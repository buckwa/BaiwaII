package com.buckwa.dao.impl.pbp;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.buckwa.domain.pbp.AcademicKPI;
import com.buckwa.domain.pbp.AcademicKPIAttribute;
import com.buckwa.domain.pbp.Faculty;

@Repository("generateCodeUtil")
public class GenerateCodeUtil   {
	private static Logger logger = LoggerFactory.getLogger(GenerateCodeUtil.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
 
	 
	
	 
	public int getNextAcademicUnitCode(String academicYear) {
		 int returnValue = 0;
		try{
			String sqltmp = " select  max(code) as maxCode from academic_unit ";
			logger.info(" getNextAcademicUnitCode:"+sqltmp);
			String code = this.jdbcTemplate.queryForObject(sqltmp,new CodeMapper());
	         
			int inVal = Integer.parseInt(code);
			returnValue = inVal+1;
			
			logger.info(" getNextAcademicUnitCode max Value:"+code+  "  Next Code:"+returnValue);
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	public String getNextFacultyCode(String academicYear) {
		 String returnValue = "0";
		try{
			String sqltmp = " select  max(code) as maxCode from faculty where academic_year='"+academicYear+"'";
			logger.info(" getNextFacultyCode:"+sqltmp);
			String code = this.jdbcTemplate.queryForObject(sqltmp,new CodeMapper());
	         
			int inVal = Integer.parseInt(code);
			int newVal = inVal+1;
			
			returnValue =""+newVal;
			if(returnValue.length()==1){
				returnValue ="0"+returnValue;
			}
			
			logger.info(" getNextFacultyCode max Value:"+code+  "  Next Code:"+returnValue);
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	public String getNextDepartmentCode(Faculty faculty) {
		 String returnValue = "";
		try{
			String sqltmp = " select  max(code) as maxCode from department where academic_year='"+faculty.getAcademicYear()+"' and faculty_code="+faculty.getCode();
			logger.info(" getNextDepartmentCode:"+sqltmp);
			String code = this.jdbcTemplate.queryForObject(sqltmp,new CodeMapper());
	         

			int inVal = Integer.parseInt(code);
			int newVal = inVal+1;
			
			returnValue =""+newVal;
			if(returnValue.length()==1){
				returnValue ="0"+returnValue;
			}
			logger.info(" getNextDepartmentCode max Value:"+code+  "  Next Code:"+returnValue);
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}		
	public int getNextWorkTypeCode(String academicYear) {
		 int returnValue = 0;
		try{
			String sqltmp = " select  max(code) as maxCode from pbp_work_type where academic_year='"+academicYear+"'";
			logger.info(" getNextWorkTypeCode:"+sqltmp);
			String code = this.jdbcTemplate.queryForObject(sqltmp,new CodeMapper());
	         
			int inVal = Integer.parseInt(code);
			returnValue = inVal+1;
			
			logger.info(" getNextWorkTypeCode max Value:"+code+  "  Next Code:"+returnValue);
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	public int getNextWorkTypeSubCode(String workTypeId) {
		 int returnValue = 0;
		try{
			String sqltmp = " select  max(code) as maxCode from pbp_work_type_sub where work_type_id="+workTypeId;
			logger.info(" getNextWorkTypeSubCode:"+sqltmp);
			String code = this.jdbcTemplate.queryForObject(sqltmp,new CodeMapper());
	         
			int inVal = Integer.parseInt(code);
			returnValue = inVal+1;
			
			logger.info(" getNextWorkTypeSubCode max Value:"+code+  "  Next Code:"+returnValue);
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	public int getNextaAcademicKPI(AcademicKPI academicKPI) {
		 int returnValue = 0;
		try{
			//String sqltmp = " select  max(code) as maxCode from academic_kpi where work_type_code="+academicKPI.getWorkTypeCode()+" and academic_year='"+academicKPI.getAcademicYear()+"'";
			String sqltmp = " select  max(code) as maxCode from academic_kpi where academic_year='"+academicKPI.getAcademicYear()+"'";

			logger.info(" getNextaAcademicKPI:"+sqltmp);
			String code = this.jdbcTemplate.queryForObject(sqltmp,new CodeMapper());
	         
			int inVal = Integer.parseInt(code);
			returnValue = inVal+1;
			
			logger.info(" getNextaAcademicKPI max Value:"+code+  "  Next Code:"+returnValue);
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	public int getNextAttributeCode(AcademicKPIAttribute academicKPIAttribute) {
		 int returnValue = 0;
		try{
			String sqltmp = " select  max(code) as maxCode from academic_kpi_attribute where academic_kpi_code="+academicKPIAttribute.getAcademicKPICode()+" and academic_year='"+academicKPIAttribute.getAcademicYear()+"'";
			logger.info(" getNextAttributeCode:"+sqltmp);
			String code = this.jdbcTemplate.queryForObject(sqltmp,new CodeMapper());
	         
			int inVal = Integer.parseInt(code);
			returnValue = inVal+1;
			
			logger.info(" getNextAttributeCode max Value:"+code+  "  Next Code:"+returnValue);
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
 
	
	
	private class CodeMapper implements RowMapper<String> {   						
        @Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        	 
        	 String returnValue = rs.getString("maxCode");
        	 if(returnValue==null){
        		 return "0";
        	 }
			 
		return returnValue;
    }
	}
	
	 
 
}
