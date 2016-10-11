package com.buckwa.dao.impl.pbp;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pbp.PBPReportDao;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.report.DepartmentWorkTypeReport;
import com.buckwa.domain.pbp.report.FacultyReport;
import com.buckwa.domain.pbp.report.InstituteReport;
import com.buckwa.domain.pbp.report.RadarPlotReport;

@Repository("pbpReportDao")
public class PBPReportDaoImpl implements PBPReportDao {
	private static Logger logger = LoggerFactory.getLogger(PBPReportDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public List<RadarPlotReport> getInstituteReportByAcademicYear(  String academicYear ) {	
		 
		List<RadarPlotReport> returnList = new ArrayList<RadarPlotReport >();
		
 		String sql = " SELECT * FROM report_faculty r where r.academic_year="+academicYear;
		logger.info("  getInstituteReportByAcademicYear  sql:"+sql);
		Department department=null;
		try{
			List<DepartmentWorkTypeReport> departmentReportList = this.jdbcTemplate.query(sql,	new DepartmentWorkTypeReportMapper() );	
			
			RadarPlotReport radartmp1 = new RadarPlotReport();
			RadarPlotReport radartmp2 = new RadarPlotReport();
			RadarPlotReport radartmp3 = new RadarPlotReport();
			RadarPlotReport radartmp4 = new RadarPlotReport();
			RadarPlotReport radartmp5 = new RadarPlotReport();
			
			
			BigDecimal typ1Sum = new BigDecimal(0.00);
			BigDecimal typ2Sum = new BigDecimal(0.00);
			BigDecimal typ3Sum = new BigDecimal(0.00);
			BigDecimal typ4Sum = new BigDecimal(0.00);
			BigDecimal typ5Sum = new BigDecimal(0.00);
			
			String facCode = "";
			String facName = "";
			
			int loop1 =1;
			for(DepartmentWorkTypeReport depTmp:departmentReportList){
				
				facCode = depTmp.getFacultyCode(); 
				facName =depTmp.getFacultyName();
			 					
					//if(loop1==1){
						//radartmp1.setAxisValue(depTmp.getMark1());	
						radartmp1.setAxisName(depTmp.getTypeName1());
						typ1Sum = typ1Sum.add(new BigDecimal(depTmp.getMark1()));
						
					//}else if(loop1==2){
						//radartmp2.setAxisValue(depTmp.getMark2());	
						radartmp2.setAxisName(depTmp.getTypeName2());
						typ2Sum = typ2Sum.add(new BigDecimal(depTmp.getMark2()));
					//}else if(loop1==3){
						//radartmp3.setAxisValue(depTmp.getMark3());	
						radartmp3.setAxisName(depTmp.getTypeName3());
						typ3Sum = typ3Sum.add(new BigDecimal(depTmp.getMark3()));
					//}else if(loop1==4){
						//radartmp4.setAxisValue(depTmp.getMark4());	
						radartmp4.setAxisName(depTmp.getTypeName4());
						typ4Sum = typ4Sum.add(new BigDecimal(depTmp.getMark4()));
					//} else if(loop1==5){
						//radartmp5.setAxisValue(depTmp.getMark5());	
						radartmp5.setAxisName(depTmp.getTypeName5());
						typ5Sum = typ5Sum.add(new BigDecimal(depTmp.getMark5()));
					//}
					
					logger.info(" "+depTmp.getDepartmentName()+" ="+depTmp.getMark1()+":"+depTmp.getMark2()+" :"+depTmp.getMark3()+" :"+depTmp.getMark4()+" :"+depTmp.getMark5() +" =="+typ1Sum+":"+typ2Sum+" :"+typ3Sum+" :"+typ4Sum+" :"+typ5Sum); 
					loop1++;
			}					
				
			
			logger.info(" "+facName+" ="+typ1Sum+":"+typ2Sum+" :"+typ3Sum+" :"+typ4Sum+" :"+typ5Sum);
			
			
			BigDecimal type1AVG = typ1Sum.divide(new BigDecimal(loop1),2,BigDecimal.ROUND_HALF_UP);
			BigDecimal type2AVG = typ2Sum.divide(new BigDecimal(loop1),2,BigDecimal.ROUND_HALF_UP);
			BigDecimal type3AVG = typ3Sum.divide(new BigDecimal(loop1),2,BigDecimal.ROUND_HALF_UP);
			BigDecimal type4AVG = typ4Sum.divide(new BigDecimal(loop1),2,BigDecimal.ROUND_HALF_UP);
			BigDecimal type5AVG = typ5Sum.divide(new BigDecimal(loop1),2,BigDecimal.ROUND_HALF_UP);

	 
			radartmp1.setAxisValue(type1AVG+"");
			radartmp2.setAxisValue(type2AVG+"");
			radartmp3.setAxisValue(type3AVG+"");
			radartmp4.setAxisValue(type4AVG+"");
			radartmp5.setAxisValue(type5AVG+"");
	 
			
			returnList.add(radartmp1);
			returnList.add(radartmp2);
			returnList.add(radartmp3);
			returnList.add(radartmp4);
			returnList.add(radartmp5);
			
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
		 
	 
		 
		
		return returnList;
	}
	
 /*
	@Override
	public List<RadarPlotReport> getInstituteReportByAcademicYear(  String academicYear ) {	
		 
		List<RadarPlotReport> returnList = new ArrayList<RadarPlotReport >();
		
 		String sql = " SELECT * FROM report_institute r where r.academic_year="+academicYear;
		logger.info("  getInstituteReportByAcademicYear  sql:"+sql);
		Department department=null;
		try{
			InstituteReport instituteReport = this.jdbcTemplate.queryForObject(sql,	new InstituteReportMapper() );	
			
			RadarPlotReport radartmp1 = new RadarPlotReport();
			RadarPlotReport radartmp2 = new RadarPlotReport();
			RadarPlotReport radartmp3 = new RadarPlotReport();
			RadarPlotReport radartmp4 = new RadarPlotReport();
			RadarPlotReport radartmp5 = new RadarPlotReport();
			
			radartmp1.setAxisName(instituteReport.getTypeName1());	        
			radartmp1.setAxisValue(instituteReport.getMark1() );
			
			radartmp2.setAxisName(instituteReport.getTypeName2());	        
			radartmp2.setAxisValue(instituteReport.getMark2() );
			
			radartmp3.setAxisName(instituteReport.getTypeName3());	        
			radartmp3.setAxisValue(instituteReport.getMark3() );
			
			radartmp4.setAxisName(instituteReport.getTypeName4());	        
			radartmp4.setAxisValue(instituteReport.getMark4() );
			
			radartmp5.setAxisName(instituteReport.getTypeName5());	        
			radartmp5.setAxisValue(instituteReport.getMark5() );
			
			returnList.add(radartmp1);
			returnList.add(radartmp2);
			returnList.add(radartmp3);
			returnList.add(radartmp4);
			returnList.add(radartmp5);
			
		}catch (Exception ex){
			ex.printStackTrace();
		}
		 
	 
		 
		
		return returnList;
	}
	
	*/
	 
		@Override
		public List<RadarPlotReport> getFacultyReportByAcademicYear(  String academicYear,String faculty_code ) {	
			 
			List<RadarPlotReport> returnList = new ArrayList<RadarPlotReport >();
			
	 		String sql = " SELECT * FROM report_department r where r.academic_year="+academicYear+" and faculty_code="+faculty_code;
			logger.info("  getFacultyReportByAcademicYear  sql:"+sql);
			Department department=null;
			try{
				List<DepartmentWorkTypeReport> departmentReportList = this.jdbcTemplate.query(sql,	new DepartmentWorkTypeReportMapper() );	
				
				RadarPlotReport radartmp1 = new RadarPlotReport();
				RadarPlotReport radartmp2 = new RadarPlotReport();
				RadarPlotReport radartmp3 = new RadarPlotReport();
				RadarPlotReport radartmp4 = new RadarPlotReport();
				RadarPlotReport radartmp5 = new RadarPlotReport();
				
				
				BigDecimal typ1Sum = new BigDecimal(0.00);
				BigDecimal typ2Sum = new BigDecimal(0.00);
				BigDecimal typ3Sum = new BigDecimal(0.00);
				BigDecimal typ4Sum = new BigDecimal(0.00);
				BigDecimal typ5Sum = new BigDecimal(0.00);
				
				String facCode = "";
				String facName = "";
				
				int loop1 =1;
				for(DepartmentWorkTypeReport depTmp:departmentReportList){
					
					facCode = depTmp.getFacultyCode(); 
					facName =depTmp.getFacultyName();
				 					
						//if(loop1==1){
							//radartmp1.setAxisValue(depTmp.getMark1());	
							radartmp1.setAxisName(depTmp.getTypeName1());
							typ1Sum = typ1Sum.add(new BigDecimal(depTmp.getMark1()));
							
						//}else if(loop1==2){
							//radartmp2.setAxisValue(depTmp.getMark2());	
							radartmp2.setAxisName(depTmp.getTypeName2());
							typ2Sum = typ2Sum.add(new BigDecimal(depTmp.getMark2()));
						//}else if(loop1==3){
							//radartmp3.setAxisValue(depTmp.getMark3());	
							radartmp3.setAxisName(depTmp.getTypeName3());
							typ3Sum = typ3Sum.add(new BigDecimal(depTmp.getMark3()));
						//}else if(loop1==4){
							//radartmp4.setAxisValue(depTmp.getMark4());	
							radartmp4.setAxisName(depTmp.getTypeName4());
							typ4Sum = typ4Sum.add(new BigDecimal(depTmp.getMark4()));
						//} else if(loop1==5){
							//radartmp5.setAxisValue(depTmp.getMark5());	
							radartmp5.setAxisName(depTmp.getTypeName5());
							typ5Sum = typ5Sum.add(new BigDecimal(depTmp.getMark5()));
						//}
						
						logger.info(" "+depTmp.getDepartmentName()+" ="+depTmp.getMark1()+":"+depTmp.getMark2()+" :"+depTmp.getMark3()+" :"+depTmp.getMark4()+" :"+depTmp.getMark5() +" =="+typ1Sum+":"+typ2Sum+" :"+typ3Sum+" :"+typ4Sum+" :"+typ5Sum); 
						loop1++;
				}					
					
				
				logger.info(" "+facName+" ="+typ1Sum+":"+typ2Sum+" :"+typ3Sum+" :"+typ4Sum+" :"+typ5Sum);
				
				
				BigDecimal type1AVG = typ1Sum.divide(new BigDecimal(loop1),2,BigDecimal.ROUND_HALF_UP);
				BigDecimal type2AVG = typ2Sum.divide(new BigDecimal(loop1),2,BigDecimal.ROUND_HALF_UP);
				BigDecimal type3AVG = typ3Sum.divide(new BigDecimal(loop1),2,BigDecimal.ROUND_HALF_UP);
				BigDecimal type4AVG = typ4Sum.divide(new BigDecimal(loop1),2,BigDecimal.ROUND_HALF_UP);
				BigDecimal type5AVG = typ5Sum.divide(new BigDecimal(loop1),2,BigDecimal.ROUND_HALF_UP);

		 
				radartmp1.setAxisValue(type1AVG+"");
				radartmp2.setAxisValue(type2AVG+"");
				radartmp3.setAxisValue(type3AVG+"");
				radartmp4.setAxisValue(type4AVG+"");
				radartmp5.setAxisValue(type5AVG+"");
		 
				
				returnList.add(radartmp1);
				returnList.add(radartmp2);
				returnList.add(radartmp3);
				returnList.add(radartmp4);
				returnList.add(radartmp5);
				
			}catch (org.springframework.dao.EmptyResultDataAccessException ex){
				ex.printStackTrace();
			}
			 
		 
			 
			
			return returnList;
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
 

	private class InstituteReportMapper implements RowMapper<InstituteReport> {   						
        @Override
		public InstituteReport mapRow(ResultSet rs, int rowNum) throws SQLException {
        	InstituteReport domain = new InstituteReport(); 
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
        	domain.setMark1(rs.getString("mark_1"));
        	domain.setMark2(rs.getString("mark_2"));
        	domain.setMark3(rs.getString("mark_3"));
        	domain.setMark4(rs.getString("mark_4"));
        	domain.setMark5(rs.getString("mark_5"));
        	 
		 
		return domain;
    }
	}
  
	

	private class FacultyReportMapper implements RowMapper<FacultyReport> {   						
        @Override
		public FacultyReport mapRow(ResultSet rs, int rowNum) throws SQLException {
        	FacultyReport domain = new FacultyReport(); 
        	domain.setFacultyCode(rs.getString("faculty_code"));
        	domain.setFacultyName(rs.getString("faculty_name"));
        	
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
        	domain.setMark1(rs.getString("mark_1"));
        	domain.setMark2(rs.getString("mark_2"));
        	domain.setMark3(rs.getString("mark_3"));
        	domain.setMark4(rs.getString("mark_4"));
        	domain.setMark5(rs.getString("mark_5"));
        	 
		 
		return domain;
    }
	}
  
 
}
