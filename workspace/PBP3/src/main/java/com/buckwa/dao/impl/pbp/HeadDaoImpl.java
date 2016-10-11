package com.buckwa.dao.impl.pbp;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pbp.FacultyDao;
import com.buckwa.dao.intf.pbp.HeadDao;
import com.buckwa.dao.intf.pbp.PBPWorkTypeDao;
import com.buckwa.domain.pbp.AcademicKPI;
import com.buckwa.domain.pbp.AcademicKPIAttribute;
import com.buckwa.domain.pbp.AcademicKPIAttributeValue;
import com.buckwa.domain.pbp.AcademicKPIUserMapping;
import com.buckwa.domain.pbp.AcademicKPIUserMappingWrapper;
import com.buckwa.domain.pbp.AcademicPerson;
import com.buckwa.domain.pbp.AcademicYearEvaluateRound;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.domain.pbp.report.DepartmentReport;
import com.buckwa.domain.pbp.report.DepartmentWorkTypeReport;
import com.buckwa.domain.pbp.report.MinMaxBean;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.school.SchoolConstants;
import com.buckwa.util.school.SchoolUtil;

@Repository("headDao")
public class HeadDaoImpl implements HeadDao {
	private static Logger logger = LoggerFactory.getLogger(HeadDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
	private GenerateCodeUtil  generateCodeUtil;
	
	@Autowired
	private PBPWorkTypeDao pBPWorkTypeDao;
	
	@Autowired
	private FacultyDao  facultyDao;
	
	
	
	@Override
	public String getDepartmentMean( String academicYear ,String facultyCode,String departmentCode) {	 
 
		
		String sql =" select  ROUND( AVG(mark_total),2 ) from report_worktype_department where faculty_code ='"+facultyCode+"' and department_code='"+departmentCode+"' and academic_year="+academicYear;
		logger.info("  getDepartmentMean sql:"+sql);
		String returnStr = (String)this.jdbcTemplate.queryForObject(	sql , String.class); 
		logger.info("  returnStr:"+returnStr);
		
	    return returnStr;
		 
	}
	
	@Override
	public MinMaxBean getDepartmentMeanByWorkTypeCode( String academicYear ,String facultyCode,String departmentCode,String worktypecode) {	 
		
		String markcolumn  ="";
		if ("1".equals(worktypecode)) {
			markcolumn ="mark_1";
		} else if ("2".equals(worktypecode)) {
			markcolumn ="mark_2";
		} else if ("3".equals(worktypecode)) {
			markcolumn ="mark_3";
		} else if ("4".equals(worktypecode)) {
			markcolumn ="mark_4";
		} else if ("5".equals(worktypecode)) {
			markcolumn ="mark_5";
		}
 
		MinMaxBean minmaxBean = new MinMaxBean();
		
		String sql =" select  ROUND( AVG("+markcolumn+"),2 ) from report_worktype_department where faculty_code ='"+facultyCode+"' and department_code='"+departmentCode+"' and academic_year="+academicYear;
		logger.info("  getDepartmentMean sql:"+sql);
		String returnStr = (String)this.jdbcTemplate.queryForObject(	sql , String.class); 
		logger.info("  returnStr:"+returnStr);
		
		minmaxBean.setMeanValue(returnStr);
		
		
		
		String sqlMinMax =" select *  from pbp_work_type where academic_year ='"+academicYear+"' and faculty_code='"+facultyCode+"' and code='"+worktypecode+"' " ;   
		logger.info("  getByAcademicYearAndFactulty sql:"+sql); 
		PBPWorkTypeWrapper pBPWorkTypeWrapper = new PBPWorkTypeWrapper();	 	 
		try{
			PBPWorkType pbpWorkType  = this.jdbcTemplate.queryForObject(sqlMinMax,	new PBPWorkTypeMapper() );	 
			if(pbpWorkType.isMinHourCal()){
				minmaxBean.setMinValue(pbpWorkType.getMinHour()+"");
				minmaxBean.setMinDesc(pbpWorkType.getMinHour()+"");
			}else{
				minmaxBean.setMinValue("-2");
				minmaxBean.setMinDesc("-");
			}
			
			if(pbpWorkType.isMaxHourCal()){
				minmaxBean.setMaxValue(pbpWorkType.getMaxHour()+"");
				minmaxBean.setMaxDesc(pbpWorkType.getMaxHour()+"");
			}else{
				minmaxBean.setMaxValue("-2");
				minmaxBean.setMaxDesc("-");
			}
			
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		
	    return minmaxBean;
		 
	}
	
	private class PBPWorkTypeMapper implements RowMapper<PBPWorkType> {   						
        @Override
		public PBPWorkType mapRow(ResultSet rs, int rowNum) throws SQLException {
        	PBPWorkType domain = new PBPWorkType(); 
        	domain.setWorkTypeId(rs.getLong("work_type_id"));
			domain.setCode(rs.getString("code"));
			domain.setName(rs.getString("name"));
			domain.setDescription(rs.getString("description"));
			domain.setMinPercent(rs.getBigDecimal("min_percent"));
			domain.setMinHour(rs.getBigDecimal("min_hour"));
			domain.setMaxPercent(rs.getBigDecimal("max_percent"));
			domain.setMaxHour(rs.getBigDecimal("max_hour"));
			domain.setAcademicYear(rs.getString("academic_year"));
			domain.setLimitBase(rs.getBigDecimal("limit_base"));
			domain.setFacultyCode(rs.getString("faculty_code"));
			 
			domain.setMinHourCal(rs.getBoolean("min_hour_cal"));
			domain.setMaxHourCal(rs.getBoolean("max_hour_cal"));
		 
		return domain;
    }
	}
	
		
 
	@Override
	public AcademicKPIUserMappingWrapper getByHeadAcademicYear( String headUserName ,String academicYear,String status) {	 
		
		AcademicKPIUserMappingWrapper   academicKPIUserMappingWrapper = new AcademicKPIUserMappingWrapper(); 
 		String sqlDepartment = " select d.* from department d 	inner join person_pbp p on (d.name=p.head_department) 	where p.email='"+headUserName+"' and p.academic_year='"+academicYear+"'  and d.academic_year='"+academicYear+"'";

		//logger.info("  getByHeadAcademicYear sqlDepartment:"+sqlDepartment);
		Department department=null;
		try{
			department = this.jdbcTemplate.queryForObject(sqlDepartment,	new DepartmentMapper() );	
			logger.info("  Department :"+department.getCode()+":"+department.getName());
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
		if(department!=null){
			
			// Get User belong to department 
			String sqlacademicPerson = "  select * from person_pbp where department_desc ='"+department.getName()+"'  and  academic_year='"+academicYear+"'";
			//logger.info("  getByHeadAcademicYear sqlacademicPerson:"+sqlacademicPerson);
			List<AcademicPerson> academicPersonList  = this.jdbcTemplate.query(sqlacademicPerson,	new AcademicPersonMapper() );
			
			
			int departmentTotalApproved = 0;
		    int departmentTotalNotApprove = 0;
			int totalByDepartment =0;
			
			int loop =1;
			for(AcademicPerson personTmp:academicPersonList){
				
				// Get KPI User Mapping 
				logger.info("  Loop :"+loop+++":"+personTmp.getThaiName()+" "+personTmp.getThaiSurname());
				
				String employeeType = personTmp.getEmployeeTypeNo();
				
				String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+academicYear+"' and evaluate_type='"+employeeType+"'"   ;  
				///logger.info(" sqlRound:"+sqlRound);
				 AcademicYearEvaluateRound  academicYearEvaluateRound   = this.jdbcTemplate.queryForObject(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
				
				 //logger.info(" academicYearEvaluateRound:"+BeanUtils.getBeanString(academicYearEvaluateRound));
				
				 long startTime =0l;
				 long endTime =0l;
				 
				 Timestamp startTimeStamp = null;
				 Timestamp endTimeStamp = null;
				 String round ="1";
				 if(employeeType.equalsIgnoreCase("1")){
					 
					 long round1EndLong = academicYearEvaluateRound.getRound1EndDate().getTime();
					 
					 //if(round1EndLong-System.currentTimeMillis()>0){
						 //logger.info(" round 1 Start From:"+academicYearEvaluateRound.getRound1StartDate()+" to "+academicYearEvaluateRound.getRound1EndDate()+" doday :"+new Timestamp(System.currentTimeMillis()) +" so use round 1");
						 startTime = academicYearEvaluateRound.getRound1StartDate().getTime();
						 startTimeStamp = academicYearEvaluateRound.getRound1StartDate();
						 
						// endTime = academicYearEvaluateRound.getRound1EndDate().getTime();
						// endTimeStamp = academicYearEvaluateRound.getRound1EndDate();						 
					// }else{
						// logger.info(" round 1 Start From:"+academicYearEvaluateRound.getRound1StartDate()+" to "+academicYearEvaluateRound.getRound1EndDate()+" doday :"+new Timestamp(System.currentTimeMillis()) +" so use round 2");
					//	 round ="2";
					//	 startTime = academicYearEvaluateRound.getRound2StartDate().getTime();
					//	 startTimeStamp = academicYearEvaluateRound.getRound2StartDate();
						 
						 endTime = academicYearEvaluateRound.getRound2EndDate().getTime();
						 endTimeStamp = academicYearEvaluateRound.getRound2EndDate();
						 
					// }
					 
 
					 
				 }else{
					 
					 startTime = academicYearEvaluateRound.getRound1StartDate().getTime();
					 startTimeStamp = academicYearEvaluateRound.getRound1StartDate();
					 
					 endTime = academicYearEvaluateRound.getRound1EndDate().getTime();
					 endTimeStamp = academicYearEvaluateRound.getRound1EndDate(); 
					 
				 }
						 
				 logger.info(" Employee Type:"+employeeType+ " Start Date:"+startTimeStamp+"   End Date:"+endTimeStamp +" round:"+round);
			 	
				 
				//String kpiMapping = " select * from academic_kpi_user_mapping where user_name='"+personTmp.getEmail()+"'  and  academic_year='"+academicYear+"'";
					String kpiMapping = " select * from academic_kpi_user_mapping where user_name='"+personTmp.getEmail()+"'  and  academic_year='"+academicYear+"' and create_date BETWEEN '"+startTimeStamp+"' AND '"+endTimeStamp+"'"; 
				//logger.info("  getByHeadAcademicYear kpiMapping:"+kpiMapping);
				List<AcademicKPIUserMapping> academicKPIUserMappingList  = this.jdbcTemplate.query(kpiMapping,	new AcademicKPIUserMappingMapper() );

				int totalNotApprove =0;
				int totalApproved =0;
				int totalByPerson =0;
				
				if(academicKPIUserMappingList!=null&&academicKPIUserMappingList.size()>0){
					
					for(AcademicKPIUserMapping mappingTmp:academicKPIUserMappingList){
						  
						if("CREATE".equalsIgnoreCase(mappingTmp.getStatus())){
							totalNotApprove++;
						
							departmentTotalNotApprove++;
						}else if(SchoolConstants.STATUS_CREATE_CO_TEACH.equalsIgnoreCase(mappingTmp.getStatus())){ 
							totalNotApprove++;
							
							departmentTotalNotApprove++;
						}
						else if("APPROVED".equalsIgnoreCase(mappingTmp.getStatus())){ 
							
							totalApproved++;
							departmentTotalApproved++;
						}
						
//						String sqlkpi =" select *  from academic_kpi where academic_kpi_id ="+mappingTmp.getAcademicKPIId() ; 
//						logger.info(" sqlkpi:"+sqlkpi);
//						 
//						
//						try{
//							 AcademicKPI  academicKPI  = this.jdbcTemplate.queryForObject(sqlkpi,	new AcademicKPIMapper() );
//							 mappingTmp.setAcademicKPI(academicKPI);
//							 
//							 
//								String sqlAttributeValue =" select *  from academic_kpi_attribute_value where kpi_user_mapping_id ="+mappingTmp.getKpiUserMappingId() ; 
//								List<AcademicKPIAttributeValue> academicKPIAttributeValueList = new ArrayList();
//								try{
//									logger.info(" sqlAttributeValue:"+sqlAttributeValue);
//									academicKPIAttributeValueList = this.jdbcTemplate.query(sqlAttributeValue,	new AcademicKPIAttributeValueMapper() );
//									
//								}catch (org.springframework.dao.EmptyResultDataAccessException ex){
//									ex.printStackTrace();
//								} 
//								
//								mappingTmp.setAcademicKPIAttributeValueList(academicKPIAttributeValueList);
//								
//								
//								
//							//	String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_code ="+mappingTmp.getAcademicKPICode()+" and academic_year='"+mappingTmp.getAcademicYear()+"'" ; 
//								String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_id ="+mappingTmp.getAcademicKPIId()+" and academic_year='"+mappingTmp.getAcademicYear()+"'" ; 
//
//								List<AcademicKPIAttribute> academicKPIAttributeList = new ArrayList();
//								try{
//									logger.info(" sqlAttribute:"+sqlAttribute);
//									academicKPIAttributeList = this.jdbcTemplate.query(sqlAttribute,	new AcademicKPIAttributeMapper() );
//									
//								}catch (org.springframework.dao.EmptyResultDataAccessException ex){
//									ex.printStackTrace();
//								} 									
//								
//								mappingTmp.setAcademicKPIAttributeList(academicKPIAttributeList);
//							 
//						}catch (org.springframework.dao.EmptyResultDataAccessException ex){
//							ex.printStackTrace();
//						} 
						
						
						// departmentTotalApproved   =departmentTotalApproved+ totalApproved;
					   //  departmentTotalNotApprove  =  departmentTotalNotApprove+totalNotApprove;
					     
//					     logger.info(" departmentTotalApproved:"+departmentTotalApproved+"     departmentTotalNotApprove:"+departmentTotalNotApprove);
					     
					     totalByPerson= totalByPerson+totalApproved+totalNotApprove;
					     

					}
					
					totalByPerson = totalApproved+totalNotApprove;
					personTmp.setTotalApproved(totalApproved);
					personTmp.setTotalNotApprove(totalNotApprove);
					personTmp.setTotal(totalByPerson);
					
					
				}

				
				
				personTmp.setAcademicKPIUserMappingList(academicKPIUserMappingList);
				
				
				
			}
			
			department.setAcademicPersonList(academicPersonList);
			
			
			academicKPIUserMappingWrapper.setTotalApproved(departmentTotalApproved);
			academicKPIUserMappingWrapper.setTotalNotApprove(departmentTotalNotApprove);
			totalByDepartment = totalByDepartment+departmentTotalApproved+departmentTotalNotApprove;
			
			academicKPIUserMappingWrapper.setTotal(totalByDepartment);
			
		}
		

		
		academicKPIUserMappingWrapper.setDepartment(department);
		
		return academicKPIUserMappingWrapper;
	}
	
	 
	
	@Override
	public AcademicKPIUserMappingWrapper getByUserAcademicYear( String headUserName ,String academicYear,String status,String userName){	  
		
		AcademicKPIUserMappingWrapper   academicKPIUserMappingWrapper = new AcademicKPIUserMappingWrapper(); 
		// Get Department belong to head
		
		String sqlDepartment = " select d.* from department d 	inner join person_pbp p on (d.name=p.department_desc) 	where p.email='"+headUserName+"' and p.academic_year='"+academicYear+"'  and d.academic_year='"+academicYear+"'";
		logger.info("  getByHeadAcademicYear sqlDepartment:"+sqlDepartment);
		Department department=null;
		try{
			department = this.jdbcTemplate.queryForObject(sqlDepartment,	new DepartmentMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
		if(department!=null){
			
			// Get User belong to department 
			String sqlacademicPerson = "  select * from person_pbp where department_desc ='"+department.getName()+"'  and  academic_year='"+academicYear+"'";
			logger.info("  getByHeadAcademicYear sqlacademicPerson:"+sqlacademicPerson);
			List<AcademicPerson> academicPersonList  = this.jdbcTemplate.query(sqlacademicPerson,	new AcademicPersonMapper() );
			
			
			int departmentTotalApproved = 0;
		    int departmentTotalNotApprove = 0;
			int totalByDepartment =0;
			
			
			List<AcademicPerson> newAcademicPersonList = new ArrayList();
			
			for(AcademicPerson personTmp:academicPersonList){
				if(personTmp.getEmail().equalsIgnoreCase(userName)){
					newAcademicPersonList.add(personTmp);
				}
			}
			
			for(AcademicPerson personTmp:newAcademicPersonList){
				
				// Get KPI User Mapping 
				
				String employeeType = personTmp.getEmployeeTypeNo();
				
				String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+academicYear+"' and evaluate_type='"+employeeType+"'"   ;  
				logger.info(" sqlRound:"+sqlRound);
				 AcademicYearEvaluateRound  academicYearEvaluateRound   = this.jdbcTemplate.queryForObject(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
				
				// logger.info(" academicYearEvaluateRound:"+BeanUtils.getBeanString(academicYearEvaluateRound));
				
				 long startTime =0l;
				 long endTime =0l;
				 
				 Timestamp startTimeStamp = null;
				 Timestamp endTimeStamp = null;
				 String round ="1";
				 if(employeeType.equalsIgnoreCase("1")){
					 
					 long round1EndLong = academicYearEvaluateRound.getRound1EndDate().getTime();
					 
					// if(round1EndLong-System.currentTimeMillis()>0){
						 logger.info(" round 1 Start From:"+academicYearEvaluateRound.getRound1StartDate()+" to "+academicYearEvaluateRound.getRound1EndDate()+" doday :"+new Timestamp(System.currentTimeMillis()) +" so use round 1");
						 startTime = academicYearEvaluateRound.getRound1StartDate().getTime();
						 startTimeStamp = academicYearEvaluateRound.getRound1StartDate();
						 
					//	 endTime = academicYearEvaluateRound.getRound1EndDate().getTime();
					//	 endTimeStamp = academicYearEvaluateRound.getRound1EndDate();						 
					// }else{
					//	 logger.info(" round 1 Start From:"+academicYearEvaluateRound.getRound1StartDate()+" to "+academicYearEvaluateRound.getRound1EndDate()+" doday :"+new Timestamp(System.currentTimeMillis()) +" so use round 2");
						 round ="2";
					//	 startTime = academicYearEvaluateRound.getRound2StartDate().getTime();
					//	 startTimeStamp = academicYearEvaluateRound.getRound2StartDate();
						 
						 endTime = academicYearEvaluateRound.getRound2EndDate().getTime();
						 endTimeStamp = academicYearEvaluateRound.getRound2EndDate();
						 
					// } 
				 }else{
					 
					 startTime = academicYearEvaluateRound.getRound1StartDate().getTime();
					 startTimeStamp = academicYearEvaluateRound.getRound1StartDate();
					 
					 endTime = academicYearEvaluateRound.getRound1EndDate().getTime();
					 endTimeStamp = academicYearEvaluateRound.getRound1EndDate(); 
					 
				 }
						 
				 logger.info(" Employee Type:"+employeeType+ " Start Date:"+startTimeStamp+"   End Date:"+endTimeStamp +" round:"+round);
			 	
				 
				//String kpiMapping = " select * from academic_kpi_user_mapping where user_name='"+personTmp.getEmail()+"'  and  academic_year='"+academicYear+"'";
					String kpiMapping = " select * from academic_kpi_user_mapping where user_name='"+personTmp.getEmail()+"'  and  academic_year='"+academicYear+"' and create_date BETWEEN '"+startTimeStamp+"' AND '"+endTimeStamp+"'"; 
				logger.info("  getByHeadAcademicYear kpiMapping:"+kpiMapping);
				List<AcademicKPIUserMapping> academicKPIUserMappingList  = this.jdbcTemplate.query(kpiMapping,	new AcademicKPIUserMappingMapper() );

				int totalNotApprove =0;
				int totalApproved =0;
				int totalByPerson =0;
				
				if(academicKPIUserMappingList!=null&&academicKPIUserMappingList.size()>0){
					
					for(AcademicKPIUserMapping mappingTmp:academicKPIUserMappingList){
						  
						if("CREATE".equalsIgnoreCase(mappingTmp.getStatus())){
							totalNotApprove++;
						
							departmentTotalNotApprove++;
						}else if("APPROVED".equalsIgnoreCase(mappingTmp.getStatus())){ 
							
							totalApproved++;
							departmentTotalApproved++;
						}
						
						String sqlkpi =" select *  from academic_kpi where academic_kpi_id ="+mappingTmp.getAcademicKPIId() ; 
						logger.info(" sqlkpi:"+sqlkpi);
						 
						
						try{
							 AcademicKPI  academicKPI  = this.jdbcTemplate.queryForObject(sqlkpi,	new AcademicKPIMapper() );
							 mappingTmp.setAcademicKPI(academicKPI);
							 
							 
								String sqlAttributeValue =" select *  from academic_kpi_attribute_value where kpi_user_mapping_id ="+mappingTmp.getKpiUserMappingId() ; 
								List<AcademicKPIAttributeValue> academicKPIAttributeValueList = new ArrayList();
								try{
									logger.info(" sqlAttributeValue:"+sqlAttributeValue);
									academicKPIAttributeValueList = this.jdbcTemplate.query(sqlAttributeValue,	new AcademicKPIAttributeValueMapper() );
									
								}catch (org.springframework.dao.EmptyResultDataAccessException ex){
									ex.printStackTrace();
								} 
								
								mappingTmp.setAcademicKPIAttributeValueList(academicKPIAttributeValueList);
								
								
								
							//	String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_code ="+mappingTmp.getAcademicKPICode()+" and academic_year='"+mappingTmp.getAcademicYear()+"'" ; 
								String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_id ="+mappingTmp.getAcademicKPIId()+" and academic_year='"+mappingTmp.getAcademicYear()+"'" ; 
								
								List<AcademicKPIAttribute> academicKPIAttributeList = new ArrayList();
								try{
									logger.info(" sqlAttribute:"+sqlAttribute);
									academicKPIAttributeList = this.jdbcTemplate.query(sqlAttribute,	new AcademicKPIAttributeMapper() );
									
								}catch (org.springframework.dao.EmptyResultDataAccessException ex){
									ex.printStackTrace();
								} 									
								
								mappingTmp.setAcademicKPIAttributeList(academicKPIAttributeList);
							 
						}catch (org.springframework.dao.EmptyResultDataAccessException ex){
							ex.printStackTrace();
						} 
						
						
						// departmentTotalApproved   =departmentTotalApproved+ totalApproved;
					   //  departmentTotalNotApprove  =  departmentTotalNotApprove+totalNotApprove;
					     
					     logger.info(" departmentTotalApproved:"+departmentTotalApproved+"     departmentTotalNotApprove:"+departmentTotalNotApprove);
					     
					     totalByPerson= totalByPerson+totalApproved+totalNotApprove;
					     

					}
					
					totalByPerson = departmentTotalApproved+departmentTotalNotApprove;
					personTmp.setTotalApproved(totalApproved);
					personTmp.setTotalNotApprove(totalNotApprove);
					personTmp.setTotal(totalByPerson);
					
					
				}

				
				
				personTmp.setAcademicKPIUserMappingList(academicKPIUserMappingList);
				
				
				
			}
			
			department.setAcademicPersonList(newAcademicPersonList);
			
			
			academicKPIUserMappingWrapper.setTotalApproved(departmentTotalApproved);
			academicKPIUserMappingWrapper.setTotalNotApprove(departmentTotalNotApprove);
			totalByDepartment = totalByDepartment+departmentTotalApproved+departmentTotalNotApprove;
			
			academicKPIUserMappingWrapper.setTotal(totalByDepartment);
			
		}
		

		
		academicKPIUserMappingWrapper.setDepartment(department);
		
		return academicKPIUserMappingWrapper;
	}
	
	
	@Override
	public AcademicKPIUserMappingWrapper listApproveByAcademicYear( String headUserName ,String academicYear,String status,String userName){	  
		
		AcademicKPIUserMappingWrapper   academicKPIUserMappingWrapper = new AcademicKPIUserMappingWrapper(); 
		// Get Department belong to head
		
		String sqlDepartment = " select d.* from department d 	inner join person_pbp p on (d.name=p.department_desc) 	where p.email='"+headUserName+"' and p.academic_year='"+academicYear+"'  and d.academic_year='"+academicYear+"'";
	 
		Department department=null;
		try{
			department = this.jdbcTemplate.queryForObject(sqlDepartment,	new DepartmentMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
		if(department!=null){
			
			// Get User belong to department 
			String sqlacademicPerson = "  select * from person_pbp where department_desc ='"+department.getName()+"'  and  academic_year='"+academicYear+"'";
			 
			List<AcademicPerson> academicPersonList  = this.jdbcTemplate.query(sqlacademicPerson,	new AcademicPersonMapper() );
			
			
			int departmentTotalApproved = 0;
		    int departmentTotalNotApprove = 0;
			int totalByDepartment =0;
			
			
			List<AcademicPerson> newAcademicPersonList = new ArrayList();
			
			for(AcademicPerson personTmp:academicPersonList){
				if(personTmp.getEmail().equalsIgnoreCase(userName)){
					newAcademicPersonList.add(personTmp);
				}
			}
			
			String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+academicYear+"' and evaluate_type='"+2+"'"   ;  
			 AcademicYearEvaluateRound  academicYearEvaluateRound   = this.jdbcTemplate.queryForObject(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
			
			 for(AcademicPerson personTmp:newAcademicPersonList){
				
				// Get KPI User Mapping 
				
				String employeeType = personTmp.getEmployeeTypeNo();
				
				//String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+academicYear+"' and evaluate_type='"+employeeType+"'"   ;  
				//logger.info(" sqlRound:"+sqlRound);
				 //AcademicYearEvaluateRound  academicYearEvaluateRound   = this.jdbcTemplate.queryForObject(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
				
				// logger.info(" academicYearEvaluateRound:"+BeanUtils.getBeanString(academicYearEvaluateRound));
				
				 long startTime =0l;
				 long endTime =0l;
				 
				 Timestamp startTimeStamp = null;
				 Timestamp endTimeStamp = null;
 
					 startTime = academicYearEvaluateRound.getRound1StartDate().getTime();
					 startTimeStamp = academicYearEvaluateRound.getRound1StartDate();
					 
					 endTime = academicYearEvaluateRound.getRound1EndDate().getTime();
					 endTimeStamp = academicYearEvaluateRound.getRound1EndDate(); 
					 
	 
				 
				//String kpiMapping = " select * from academic_kpi_user_mapping where user_name='"+personTmp.getEmail()+"'  and  academic_year='"+academicYear+"'";
					String kpiMapping = " select * from academic_kpi_user_mapping where user_name='"+personTmp.getEmail()+"'  and  academic_year='"+academicYear+"' and create_date BETWEEN '"+startTimeStamp+"' AND '"+endTimeStamp+"'"; 
				logger.info("  getByHeadAcademicYear kpiMapping:"+kpiMapping);
				List<AcademicKPIUserMapping> academicKPIUserMappingList  = this.jdbcTemplate.query(kpiMapping,	new AcademicKPIUserMappingMapper() );

				int totalNotApprove =0;
				int totalApproved =0;
				int totalByPerson =0;
				
				if(academicKPIUserMappingList!=null&&academicKPIUserMappingList.size()>0){
					
					for(AcademicKPIUserMapping mappingTmp:academicKPIUserMappingList){
						  
						if("CREATE".equalsIgnoreCase(mappingTmp.getStatus())){
							totalNotApprove++;
						
							departmentTotalNotApprove++;
						}else if("APPROVED".equalsIgnoreCase(mappingTmp.getStatus())){ 
							
							totalApproved++;
							departmentTotalApproved++;
						}
						
						String sqlkpi =" select *  from academic_kpi where academic_kpi_id ="+mappingTmp.getAcademicKPIId() ; 
						logger.info(" sqlkpi:"+sqlkpi);
						 
						
						try{
							 AcademicKPI  academicKPI  = this.jdbcTemplate.queryForObject(sqlkpi,	new AcademicKPIMapper() );
							 mappingTmp.setAcademicKPI(academicKPI);
							 
							 
								String sqlAttributeValue =" select *  from academic_kpi_attribute_value where kpi_user_mapping_id ="+mappingTmp.getKpiUserMappingId() ; 
								List<AcademicKPIAttributeValue> academicKPIAttributeValueList = new ArrayList();
								try{
									logger.info(" sqlAttributeValue:"+sqlAttributeValue);
									academicKPIAttributeValueList = this.jdbcTemplate.query(sqlAttributeValue,	new AcademicKPIAttributeValueMapper() );
									
								}catch (org.springframework.dao.EmptyResultDataAccessException ex){
									ex.printStackTrace();
								} 
								
								mappingTmp.setAcademicKPIAttributeValueList(academicKPIAttributeValueList);
								
								
								
							//	String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_code ="+mappingTmp.getAcademicKPICode()+" and academic_year='"+mappingTmp.getAcademicYear()+"'" ; 
								String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_id ="+mappingTmp.getAcademicKPIId()+" and academic_year='"+mappingTmp.getAcademicYear()+"'" ; 
								
								List<AcademicKPIAttribute> academicKPIAttributeList = new ArrayList();
								try{
									logger.info(" sqlAttribute:"+sqlAttribute);
									academicKPIAttributeList = this.jdbcTemplate.query(sqlAttribute,	new AcademicKPIAttributeMapper() );
									
								}catch (org.springframework.dao.EmptyResultDataAccessException ex){
									ex.printStackTrace();
								} 									
								
								mappingTmp.setAcademicKPIAttributeList(academicKPIAttributeList);
							 
						}catch (org.springframework.dao.EmptyResultDataAccessException ex){
							ex.printStackTrace();
						} 
						
						
						// departmentTotalApproved   =departmentTotalApproved+ totalApproved;
					   //  departmentTotalNotApprove  =  departmentTotalNotApprove+totalNotApprove;
					     
					     logger.info(" departmentTotalApproved:"+departmentTotalApproved+"     departmentTotalNotApprove:"+departmentTotalNotApprove);
					     
					     totalByPerson= totalByPerson+totalApproved+totalNotApprove;
					     

					}
					
					totalByPerson = departmentTotalApproved+departmentTotalNotApprove;
					personTmp.setTotalApproved(totalApproved);
					personTmp.setTotalNotApprove(totalNotApprove);
					personTmp.setTotal(totalByPerson);
					
					
				}

				
				
				personTmp.setAcademicKPIUserMappingList(academicKPIUserMappingList);
				
				
				
			}
			
			department.setAcademicPersonList(newAcademicPersonList);
			
			
			academicKPIUserMappingWrapper.setTotalApproved(departmentTotalApproved);
			academicKPIUserMappingWrapper.setTotalNotApprove(departmentTotalNotApprove);
			totalByDepartment = totalByDepartment+departmentTotalApproved+departmentTotalNotApprove;
			
			academicKPIUserMappingWrapper.setTotal(totalByDepartment);
			
		}
		

		
		academicKPIUserMappingWrapper.setDepartment(department);
		
		return academicKPIUserMappingWrapper;
	}
	
	 
	
	 
	@Override
	public Department getDepartmentMark( String headUserName ,String academicYear ) {	
		 
		
		//String sqlDepartment = " select d.* from department d 	inner join person_pbp p on (d.name=p.department_desc) 	where p.email='"+headUserName+"'";
		String sqlDepartment = " select d.* from department d 	inner join person_pbp p on (d.name=p.head_department) 	where p.email='"+headUserName+"' and p.academic_year='"+academicYear+"'  and d.academic_year='"+academicYear+"'";

		logger.info("  getDepartmentMark sqlDepartment:"+sqlDepartment);
		Department department=null;
		try{
			department = this.jdbcTemplate.queryForObject(sqlDepartment,	new DepartmentMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
			logger.info(" sqlDepartment:"+sqlDepartment);
		}
		if(department!=null){ 
			// Get User belong to department 
			String sqlacademicPerson = "  select * from person_pbp where department_desc ='"+department.getName()+"'   and  academic_year='"+academicYear+"'";
			logger.info("  getByHeadAcademicYear sqlacademicPerson:"+sqlacademicPerson);
			List<AcademicPerson> academicPersonList  = this.jdbcTemplate.query(sqlacademicPerson,	new AcademicPersonMapper() );  
			
			
			if(academicPersonList!=null){
				
				logger.info("   ### Found Total Person belong to Department :"+academicPersonList.size());
			}
			
			BigDecimal totalMark = new BigDecimal(0.00);
			int personloop =0;
			for(AcademicPerson personTmp:academicPersonList){ 
				// Get KPI User Mapping  
				personloop++;
				logger.info("   ### Start Person Loop "+personloop);
				
				String employeeType = personTmp.getEmployeeTypeNo();
				
				String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+academicYear+"' and evaluate_type='"+employeeType+"'"   ;  
				//logger.info(" sqlRound:"+sqlRound);
				 AcademicYearEvaluateRound  academicYearEvaluateRound   = this.jdbcTemplate.queryForObject(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
				
				// logger.info(" academicYearEvaluateRound:"+BeanUtils.getBeanString(academicYearEvaluateRound));
				
	 
				 String round ="1";
				 if(employeeType.equalsIgnoreCase("1")){
					 
					 long round1EndLong = academicYearEvaluateRound.getRound1EndDate().getTime();
					 
					 if(round1EndLong-System.currentTimeMillis()>0){
						// logger.info(" round 1 Start From:"+academicYearEvaluateRound.getRound1StartDate()+" to "+academicYearEvaluateRound.getRound1EndDate()+" doday :"+new Timestamp(System.currentTimeMillis()) +" so use round 1");
				 				 
					 }else{
						// logger.info(" round 1 Start From:"+academicYearEvaluateRound.getRound1StartDate()+" to "+academicYearEvaluateRound.getRound1EndDate()+" doday :"+new Timestamp(System.currentTimeMillis()) +" so use round 2");
						 round ="2";		 
						 
					 } 
					 
				 } 		
				 
				 
			    String facultyCode = facultyDao.getFacultyByCodeByAcademicYearAndName(academicYear, personTmp.getFacultyDesc());
				 
				personTmp.setpBPWorkTypeWrapper(pBPWorkTypeDao.getCalculateByAcademicYear(academicYear, personTmp.getEmail(),round,employeeType,facultyCode)); 
				totalMark = totalMark.add(personTmp.getpBPWorkTypeWrapper().getTotalMark()).setScale(2);
				
				personTmp.setTotalMark(totalMark);
			}
			
		 
			
			department.setAcademicPersonList(academicPersonList); 
		}
	 
		return department;
	}
	
	
	@Override
	public Department getDepartmentMarkAllYear( String headUserName ,String academicYear ) {	
		 
		
		//String sqlDepartment = " select d.* from department d 	inner join person_pbp p on (d.name=p.department_desc) 	where p.email='"+headUserName+"'";
		String sqlDepartment = " select d.* from department d 	inner join person_pbp p on (d.name=p.head_department) 	where p.email='"+headUserName+"' and p.academic_year='"+academicYear+"'  and d.academic_year='"+academicYear+"'";

		logger.info("  getDepartmentMark sqlDepartment:"+sqlDepartment);
		Department department=null;
		try{
			department = this.jdbcTemplate.queryForObject(sqlDepartment,	new DepartmentMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
			logger.info(" sqlDepartment:"+sqlDepartment);
		}
		if(department!=null){ 
			// Get User belong to department 
			String sqlacademicPerson = "  select * from person_pbp where department_desc ='"+department.getName()+"'   and  academic_year='"+academicYear+"'";
			logger.info("  getByHeadAcademicYear sqlacademicPerson:"+sqlacademicPerson);
			List<AcademicPerson> academicPersonList  = this.jdbcTemplate.query(sqlacademicPerson,	new AcademicPersonMapper() );  
			
			
			if(academicPersonList!=null){
				
				logger.info("   ### Found Total Person belong to Department :"+academicPersonList.size());
			}
			
			BigDecimal totalMark = new BigDecimal(0.00);
			int personloop =0;
			for(AcademicPerson personTmp:academicPersonList){ 
				// Get KPI User Mapping  
				personloop++;
				logger.info("   ### Start Person Loop "+personloop);
				
				String employeeType = personTmp.getEmployeeTypeNo();
				
				String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+academicYear+"' and evaluate_type='"+employeeType+"'"   ;  
				//logger.info(" sqlRound:"+sqlRound);
				 AcademicYearEvaluateRound  academicYearEvaluateRound   = this.jdbcTemplate.queryForObject(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
				
	 
				 
				 
			    String facultyCode = facultyDao.getFacultyByCodeByAcademicYearAndName(academicYear, personTmp.getFacultyDesc());
				 
				personTmp.setpBPWorkTypeWrapper(pBPWorkTypeDao.getCalculateByAcademicYearAllYear(academicYear, personTmp.getEmail(),employeeType,facultyCode)); 
				totalMark = totalMark.add(personTmp.getpBPWorkTypeWrapper().getTotalMark()).setScale(2);
				
				personTmp.setTotalMark(totalMark);
			}
			
		 
			
			department.setAcademicPersonList(academicPersonList); 
		}
	 
		return department;
	}
	
	
	
	
	@Override
	public Department getDepartmentMarkByDepCode( String facCode,String depCode ,String academicYear ) {	
		 
		String sqlDepartment = " select  * from department where faculty_code='"+facCode+"' and code ='"+depCode+"'" ;
		//String sqlDepartment = " select d.* from department d 	inner join person_pbp p on (d.name=p.department_desc) 	where p.email='"+headUserName+"'";
		//String sqlDepartment = " select d.* from department d 	inner join person_pbp p on (d.name=p.head_department) 	where p.email='"+headUserName+"' and p.academic_year='"+academicYear+"'  and d.academic_year='"+academicYear+"'";

		logger.info(" getDepartmentMarkByDepCode sql:"+sqlDepartment);
		Department department=null;
		try{
			department = this.jdbcTemplate.queryForObject(sqlDepartment,	new DepartmentMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
			logger.info(" sqlDepartment:"+sqlDepartment);
		}
		if(department!=null){ 
			// Get User belong to department 
			String sqlacademicPerson = "  select * from person_pbp where department_desc ='"+department.getName()+"'   and  academic_year='"+academicYear+"'";
			logger.info("  getByHeadAcademicYear sqlacademicPerson:"+sqlacademicPerson);
			List<AcademicPerson> academicPersonList  = this.jdbcTemplate.query(sqlacademicPerson,	new AcademicPersonMapper() );  
			
			
			if(academicPersonList!=null){
				
				logger.info("   ### Found Total Person belong to Department :"+academicPersonList.size());
			}
			
			BigDecimal totalMark = new BigDecimal(0.00);
			int personloop =0;
			for(AcademicPerson personTmp:academicPersonList){ 
				// Get KPI User Mapping  
				personloop++;
				logger.info("   ### Start Person Loop "+personloop);
				
				String employeeType = personTmp.getEmployeeTypeNo();
				
				String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+academicYear+"' and evaluate_type='"+employeeType+"'"   ;  
				//logger.info(" sqlRound:"+sqlRound);
				 AcademicYearEvaluateRound  academicYearEvaluateRound   = this.jdbcTemplate.queryForObject(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
				
				// logger.info(" academicYearEvaluateRound:"+BeanUtils.getBeanString(academicYearEvaluateRound));
				
	 
				 String round ="1";
				 if(employeeType.equalsIgnoreCase("1")){
					 
					 long round1EndLong = academicYearEvaluateRound.getRound1EndDate().getTime();
					 
					 if(round1EndLong-System.currentTimeMillis()>0){
						// logger.info(" round 1 Start From:"+academicYearEvaluateRound.getRound1StartDate()+" to "+academicYearEvaluateRound.getRound1EndDate()+" doday :"+new Timestamp(System.currentTimeMillis()) +" so use round 1");
				 				 
					 }else{
						// logger.info(" round 1 Start From:"+academicYearEvaluateRound.getRound1StartDate()+" to "+academicYearEvaluateRound.getRound1EndDate()+" doday :"+new Timestamp(System.currentTimeMillis()) +" so use round 2");
						 round ="2";		 
						 
					 } 
					 
				 } 		
				 
				 
			    String facultyCode = facultyDao.getFacultyByCodeByAcademicYearAndName(academicYear, personTmp.getFacultyDesc());
				 
				personTmp.setpBPWorkTypeWrapper(pBPWorkTypeDao.getCalculateByAcademicYear(academicYear, personTmp.getEmail(),round,employeeType,facultyCode)); 
				totalMark = totalMark.add(personTmp.getpBPWorkTypeWrapper().getTotalMark()).setScale(2);
			}
			
		 
			
			department.setAcademicPersonList(academicPersonList); 
		}
	 
		return department;
	}
	
	@Override
	public Department getDepartmentMarkByUser( String userName ,String academicYear ) {	
		 
		
		//String sqlDepartment = " select d.* from department d 	inner join person_pbp p on (d.name=p.department_desc) 	where p.email='"+headUserName+"'";
		String sqlDepartment = " select d.* from department d 	inner join person_pbp p on (d.name=p.department_desc) 	where p.email='"+userName+"' and p.academic_year='"+academicYear+"'  and d.academic_year='"+academicYear+"'";

		logger.info("  getDepartmentMark sqlDepartment:"+sqlDepartment);
		Department department=null;
		try{
			department = this.jdbcTemplate.queryForObject(sqlDepartment,	new DepartmentMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
		if(department!=null){ 
			// Get User belong to department 
			String sqlacademicPerson = "  select * from person_pbp where department_desc ='"+department.getName()+"'   and  academic_year='"+academicYear+"'";
			logger.info("  getByHeadAcademicYear sqlacademicPerson:"+sqlacademicPerson);
			List<AcademicPerson> academicPersonList  = this.jdbcTemplate.query(sqlacademicPerson,	new AcademicPersonMapper() );  
			
			BigDecimal totalMark = new BigDecimal(0.00);
			for(AcademicPerson personTmp:academicPersonList){ 
				// Get KPI User Mapping  
				
				String employeeType = personTmp.getEmployeeTypeNo();
				
				String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+academicYear+"' and evaluate_type='"+employeeType+"'"   ;  
				//logger.info(" sqlRound:"+sqlRound);
				 AcademicYearEvaluateRound  academicYearEvaluateRound   = this.jdbcTemplate.queryForObject(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
				
				// logger.info(" academicYearEvaluateRound:"+BeanUtils.getBeanString(academicYearEvaluateRound));
				
	 
				 String round ="1";
				 if(employeeType.equalsIgnoreCase("1")){
					 
					 long round1EndLong = academicYearEvaluateRound.getRound1EndDate().getTime();
					 
					 if(round1EndLong-System.currentTimeMillis()>0){
						// logger.info(" round 1 Start From:"+academicYearEvaluateRound.getRound1StartDate()+" to "+academicYearEvaluateRound.getRound1EndDate()+" doday :"+new Timestamp(System.currentTimeMillis()) +" so use round 1");
				 				 
					 }else{
						// logger.info(" round 1 Start From:"+academicYearEvaluateRound.getRound1StartDate()+" to "+academicYearEvaluateRound.getRound1EndDate()+" doday :"+new Timestamp(System.currentTimeMillis()) +" so use round 2");
						 round ="2";		 
						 
					 } 
					 
				 } 		
				 
				    String facultyCode = facultyDao.getFacultyByCodeByAcademicYearAndName(academicYear, personTmp.getFacultyDesc());
				personTmp.setpBPWorkTypeWrapper(pBPWorkTypeDao.getCalculateByAcademicYear(academicYear, personTmp.getEmail(),round,employeeType,facultyCode)); 
				totalMark = totalMark.add(personTmp.getpBPWorkTypeWrapper().getTotalMark()).setScale(2);
			}
			
		 
			
			department.setAcademicPersonList(academicPersonList); 
		}
		 
		
		return department;
	}
	
	
	
	
	
	@Override
	public List<DepartmentWorkTypeReport> getReportWorkTypeDepartment( String workType ,Department department ) {
		List<DepartmentWorkTypeReport> returnList = new ArrayList();
				
		String getWorkTypeReportDepartmentSQL =" select *  from report_worktype_department where department_code  ='"+department.getCode()+"' and faculty_code='"+department.getFacultyCode()+"'"
				+ " and academic_year="+department.getAcademicYear() /*+" order by person_name ";*/ +"  order by CAST(mark_total AS DECIMAL(9,2))  desc "   ;  
		logger.info(" getWorkTypeReportDepartmentSQL:"+getWorkTypeReportDepartmentSQL);
		returnList  = this.jdbcTemplate.query(getWorkTypeReportDepartmentSQL,	new DepartmentWorkTypeReportMapper() );	
		
		return returnList;
	}
	
	
	public void saveDepartmentReportSummary(Department department) {
		logger.info(" #saveDepartmentReportSummary.create # ");		
		final Department finalDepartment = department;
		
		List<PBPWorkType> pbpWorkTypeList =finalDepartment.getpBPWorkTypeList();
		
		final String depCode =finalDepartment.getCode();
		final String depName =finalDepartment.getName();
		final Faculty faculty = finalDepartment.getFaculty();
		final String facName = faculty.getName();
		final String facCode = faculty.getCode();
		final String academicYear = finalDepartment.getAcademicYear();
		
		
		logger.info(" facCode:"+facCode+ "  facName:"+facName+" depCode:"+depCode+" depName:"+depName+" academicYear:"+academicYear);
		
		String sqlcheckDelete = " delete from report_department where academic_year="+academicYear+" and faculty_code="+facCode+" and department_code="+depCode;
	  
		jdbcTemplate.update(sqlcheckDelete);
 
 
					logger.info(" Inser new");
					
					final DepartmentReport depReport1  = new DepartmentReport();
					depReport1.setAcademicYear(academicYear);
					depReport1.setFacultyCode(facCode);
					depReport1.setFacultyName(facName);
					depReport1.setDepartmentCode(depCode);;
					depReport1.setDepartmentName(depName);
					
					
					int loop1 =1;
					BigDecimal markTotal = new BigDecimal(0.00);
					for(final PBPWorkType tmp:pbpWorkTypeList){						
						if(loop1==1){
							//depReport1.setMark1(tmp.getTotalInPercentCompareBaseWorkTypeAVG()+"");
							depReport1.setMark1(tmp.getTotalInWorkType()+"");
							depReport1.setTypeCode1(tmp.getCode());
							depReport1.setTypeName1(tmp.getShortDesc());
							markTotal = markTotal.add(tmp.getTotalInWorkType());
						}else if(loop1==2){
							//depReport1.setMark2(tmp.getTotalInPercentCompareBaseWorkTypeAVG()+"");
							depReport1.setMark2(tmp.getTotalInWorkType()+"");
							depReport1.setTypeCode2(tmp.getCode());
							depReport1.setTypeName2(tmp.getShortDesc());
							markTotal = markTotal.add(tmp.getTotalInWorkType());
						}else if(loop1==3){
							//depReport1.setMark3(tmp.getTotalInPercentCompareBaseWorkTypeAVG()+"");
							depReport1.setMark3(tmp.getTotalInWorkType()+"");
							depReport1.setTypeCode3(tmp.getCode());
							depReport1.setTypeName3(tmp.getShortDesc());
							markTotal = markTotal.add(tmp.getTotalInWorkType());
						}else if(loop1==4){
							//depReport1.setMark4(tmp.getTotalInPercentCompareBaseWorkTypeAVG()+"");
							depReport1.setMark4(tmp.getTotalInWorkType()+"");
							depReport1.setTypeCode4(tmp.getCode());
							depReport1.setTypeName4(tmp.getShortDesc());
							markTotal = markTotal.add(tmp.getTotalInWorkType());
						} else if(loop1==5){
							//depReport1.setMark5(tmp.getTotalInPercentCompareBaseWorkTypeAVG()+"");
							depReport1.setMark5(tmp.getTotalInWorkType()+"");
							depReport1.setTypeCode5(tmp.getCode());
							depReport1.setTypeName5(tmp.getShortDesc());
							markTotal = markTotal.add(tmp.getTotalInWorkType());
						}
						
						//markTotal = markTotal.add(tmp.getTotalInPercentCompareBaseWorkTypeAVG());
						//markTotal = markTotal.add(tmp.getTotalInWorkType());
						logger.info(" Loop:"+loop1+" Mark1:"+depReport1.getMark1()+" Mark2:"+depReport1.getMark2()+" Mark3:"+depReport1.getMark3()+" Mark4:"+depReport1.getMark4()+" Mark5:"+depReport1.getMark5()+" MarkTotal:"+depReport1.getMarkTotal());

						loop1++;
					}
			 
					logger.info(" Final report_department Mark1:"+depReport1.getMark1()+" Mark2:"+depReport1.getMark2()+" Mark3:"+depReport1.getMark3()+" Mark4:"+depReport1.getMark4()+" Mark5:"+depReport1.getMark5()+" MarkTotal:"+depReport1.getMarkTotal());

					depReport1.setMarkTotal(markTotal+"");
					
					
			jdbcTemplate.update(new PreparedStatementCreator() {  
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
					PreparedStatement ps = connection.prepareStatement("" +						
							"  insert into report_department ("
							+ "academic_year, "
							+ "faculty_code,"
							+ "faculty_name,"
							+ "department_code,"
							+ "department_name,"
							+ "work_type_code1,"
							+ "work_type_name1,"
							+ "work_type_code2,"
							+ "work_type_name2,"
							+ "work_type_code3,"
							+ "work_type_name3,"
							+ "work_type_code4,"
							+ "work_type_name4,"
							+ "work_type_code5,"
							+ "work_type_name5,"
							+ "mark_1,"
							+ "mark_2,"
							+ "mark_3,"
							+ "mark_4,"
							+ "mark_5,mark_total)   values (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" +
						 "", Statement.RETURN_GENERATED_KEYS);   
					ps.setString(1,depReport1.getAcademicYear());
					ps.setString(2,depReport1.getFacultyCode());
					ps.setString(3,depReport1.getFacultyName());
					ps.setString(4,depReport1.getDepartmentCode());
					ps.setString(5,depReport1.getDepartmentName());
					ps.setString(6,depReport1.getTypeCode1());
					ps.setString(7,depReport1.getTypeName1());
					ps.setString(8,depReport1.getTypeCode2());
					ps.setString(9,depReport1.getTypeName2());
					ps.setString(10,depReport1.getTypeCode3());
					ps.setString(11,depReport1.getTypeName3());
					ps.setString(12,depReport1.getTypeCode4());
					ps.setString(13,depReport1.getTypeName4());
					ps.setString(14,depReport1.getTypeCode5());
					ps.setString(15,depReport1.getTypeName5());
					ps.setString(16,depReport1.getMark1());
					ps.setString(17,depReport1.getMark2());
					ps.setString(18,depReport1.getMark3());
					ps.setString(19,depReport1.getMark4());
					ps.setString(20,depReport1.getMark5());
					ps.setString(21,depReport1.getMarkTotal());
					 
					return ps;  
					}
				} ); 
			
			
			
 
		
		
		
		
		// Start insert report_worktype_department
		
		List<AcademicPerson> academicPersonList = finalDepartment.getAcademicPersonList();

		
		for(AcademicPerson academicPerson :academicPersonList){
			
			try{
				

			String thaiFirstLast = academicPerson.getThaiName()+" "+academicPerson.getThaiSurname() ;
			
			pbpWorkTypeList =academicPerson.getpBPWorkTypeWrapper().getpBPWorkTypeList();
			
			logger.info(" facCode:"+facCode+ "  facName:"+facName+" depCode:"+depCode+" depName:"+depName+" academicYear:"+academicYear+" thaiFirstLast:"+thaiFirstLast);
			  
			String sqlcheckworktypeDelete = " delete from report_worktype_department where academic_year="+academicYear+" and faculty_code="+facCode+" and department_code="+depCode+" and person_name='"+thaiFirstLast+"'";
			
			
			jdbcTemplate.update(sqlcheckworktypeDelete);
			
		 
				logger.info(" Inser new report_worktype_department");
				
				final DepartmentWorkTypeReport depReport2  = new DepartmentWorkTypeReport();
				depReport2.setAcademicYear(academicYear);
				depReport2.setFacultyCode(facCode);
				depReport2.setFacultyName(facName);
				depReport2.setDepartmentCode(depCode);;
				depReport2.setDepartmentName(depName);
				depReport2.setPersonName(thaiFirstLast);
				
				int loop =1;
				BigDecimal totalMarkBig = new BigDecimal(0.00);
				if(pbpWorkTypeList!=null){
					
				
				for(final PBPWorkType tmp:pbpWorkTypeList){
						 
						String shortDesc ="";
						StringTokenizer st = new StringTokenizer(tmp.getName(), " ");
						int numberOfSt =1;
				        while (st.hasMoreElements()) { 
				        
				        	String stStr = st.nextElement().toString();
				        	logger.info(" numberOfSt:"+numberOfSt+"  stStr:"+ stStr);
				            if(numberOfSt==1){
				            	shortDesc = stStr;
				            }
				            if(numberOfSt==2){
				            	//axisLables = axisLables +" "
				            	//st.nextElement();
				            }
				            numberOfSt++;
				        } 	
				        
				        tmp.setShortDesc(shortDesc);
					 
					
					
					if(loop==1){
						//depReport2.setMark1(tmp.getTotalInPercentCompareBaseWorkType()+"");
						depReport2.setMark1(tmp.getTotalInWorkType()+"");
						depReport2.setTypeCode1(tmp.getCode());
						depReport2.setTypeName1(tmp.getShortDesc());
						//totalMarkBig = totalMarkBig.add(tmp.getTotalInPercentCompareBaseWorkType());
						totalMarkBig = totalMarkBig.add(tmp.getTotalInWorkType());
					}else if(loop==2){
						//depReport2.setMark2(tmp.getTotalInPercentCompareBaseWorkType()+"");
						depReport2.setMark2(tmp.getTotalInWorkType()+"");
						depReport2.setTypeCode2(tmp.getCode());
						depReport2.setTypeName2(tmp.getShortDesc());
						//totalMarkBig = totalMarkBig.add(tmp.getTotalInPercentCompareBaseWorkType());
						totalMarkBig = totalMarkBig.add(tmp.getTotalInWorkType());
					}else if(loop==3){
						//depReport2.setMark3(tmp.getTotalInPercentCompareBaseWorkType()+"");
						depReport2.setMark3(tmp.getTotalInWorkType()+"");
						depReport2.setTypeCode3(tmp.getCode());
						depReport2.setTypeName3(tmp.getShortDesc());
						//totalMarkBig = totalMarkBig.add(tmp.getTotalInPercentCompareBaseWorkType());
						totalMarkBig = totalMarkBig.add(tmp.getTotalInWorkType());
					}else if(loop==4){
						//depReport2.setMark4(tmp.getTotalInPercentCompareBaseWorkType()+"");
						depReport2.setMark4(tmp.getTotalInWorkType()+"");
						depReport2.setTypeCode4(tmp.getCode());
						depReport2.setTypeName4(tmp.getShortDesc());
						//totalMarkBig = totalMarkBig.add(tmp.getTotalInPercentCompareBaseWorkType());
						totalMarkBig = totalMarkBig.add(tmp.getTotalInWorkType());
					}
					else if(loop==5){
						//depReport2.setMark5(tmp.getTotalInPercentCompareBaseWorkType()+"");
						depReport2.setMark5(tmp.getTotalInWorkType()+"");
						depReport2.setTypeCode5(tmp.getCode());
						depReport2.setTypeName5(tmp.getShortDesc());
						//totalMarkBig = totalMarkBig.add(tmp.getTotalInPercentCompareBaseWorkType());
						totalMarkBig = totalMarkBig.add(tmp.getTotalInWorkType());
					}
					
					logger.info(" Loop:"+loop+" Mark1:"+depReport2.getMark1()+" Mark2:"+depReport2.getMark2()+" Mark3:"+depReport2.getMark3()+" Mark4:"+depReport2.getMark4()+" Mark5:"+depReport2.getMark5()+" MarkTotal:"+depReport2.getMarkTotal());
					loop++;
				}
				 
				}
				depReport2.setMarkTotal(totalMarkBig+"");
				
				jdbcTemplate.update(new PreparedStatementCreator() {  
					public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
						PreparedStatement ps = connection.prepareStatement("" +						
								"  insert into report_worktype_department ("
								+ "academic_year, "
								+ "faculty_code,"
								+ "faculty_name,"
								+ "department_code,"
								+ "department_name,"
								+ "work_type_code1,"
								+ "work_type_name1,"
								+ "work_type_code2,"
								+ "work_type_name2,"
								+ "work_type_code3,"
								+ "work_type_name3,"
								+ "work_type_code4,"
								+ "work_type_name4,"
								+ "work_type_code5,"
								+ "work_type_name5,"
								+ "mark_1,"
								+ "mark_2,"
								+ "mark_3,"
								+ "mark_4,"
								+ "mark_5,"
								+ "mark_total,"
								+ "person_name"
								+ ")   values (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" +
							 "", Statement.RETURN_GENERATED_KEYS);   
						ps.setString(1,depReport2.getAcademicYear());
						ps.setString(2,depReport2.getFacultyCode());
						ps.setString(3,depReport2.getFacultyName());
						ps.setString(4,depReport2.getDepartmentCode());
						ps.setString(5,depReport2.getDepartmentName());
						ps.setString(6,depReport2.getTypeCode1());
						ps.setString(7,depReport2.getTypeName1());
						ps.setString(8,depReport2.getTypeCode2());
						ps.setString(9,depReport2.getTypeName2());
						ps.setString(10,depReport2.getTypeCode3());
						ps.setString(11,depReport2.getTypeName3());
						ps.setString(12,depReport2.getTypeCode4());
						ps.setString(13,depReport2.getTypeName4());
						ps.setString(14,depReport2.getTypeCode5());
						ps.setString(15,depReport2.getTypeName5());
						ps.setString(16,depReport2.getMark1());
						ps.setString(17,depReport2.getMark2());
						ps.setString(18,depReport2.getMark3());
						ps.setString(19,depReport2.getMark4());
						ps.setString(20,depReport2.getMark5());
						ps.setString(21,depReport2.getMarkTotal());
						ps.setString(22,depReport2.getPersonName());
						 
						 
						return ps;  
						}
					} ); 		
			
	 
			
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		
		
		
		/// Save Report Faculty
		
		
		
		
	
			
			
 	
 
	}
	
	
 
	private class DepartmentMapper implements RowMapper<Department> {   						
        @Override
		public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Department domain = new Department(); 
        	domain.setDepartmentId(rs.getLong("department_id"));
			domain.setName(rs.getString("name"));		
			domain.setCode(rs.getString("code"));	
			domain.setDescription(rs.getString("description"));	
			domain.setStatus(rs.getString("status"));	
			domain.setAcademicYear(rs.getString("academic_year"));
			domain.setFacultyCode(rs.getString("faculty_code"));	
		return domain;
    }
	}
	
	private class FacultyMapper implements RowMapper<Faculty> {   						
        @Override
		public Faculty mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Faculty domain = new Faculty(); 
        	domain.setFacultyId(rs.getLong("faculty_id"));
			domain.setName(rs.getString("name"));		
			domain.setCode(rs.getString("code"));	
			domain.setDescription(rs.getString("description"));	
			domain.setStatus(rs.getString("status"));	
			domain.setAcademicYear(rs.getString("academic_year"));
		return domain;
    }
	}
	
	/*
	private static class AcademicPersonMapper implements RowMapper<AcademicPerson> {
		@Override
		public AcademicPerson mapRow(ResultSet rs, int rowNum) throws SQLException {
			AcademicPerson domain = new AcademicPerson();
			domain.setPersonId(rs.getLong("person_id"));
			domain.setEmployeeId(rs.getString("employee_id"));
			domain.setCitizenId(rs.getString("citizen_id"));
			domain.setThaiName(rs.getString("thai_name"));
			domain.setThaiSurname(rs.getString("thai_surname"));
			domain.setSex(rs.getString("sex"));
			domain.setBirthdate(rs.getDate("birthdate"));
			domain.setRateNo(rs.getString("rate_no"));
			domain.setEmployeeType(rs.getString("employee_type"));
			domain.setPosition(rs.getString("position"));
			domain.setLevel(rs.getString("level"));
			domain.setWorkLine(rs.getString("work_line"));
			domain.setSalary(rs.getBigDecimal("salary"));
			domain.setWorkTelNo(rs.getString("work_tel_no"));
			domain.setBelongTo(rs.getString("belong_to"));
			domain.setFaculty(rs.getString("faculty"));
			domain.setWorkingDate(rs.getDate("working_date"));
			domain.setAssignDate(rs.getDate("assign_date"));
			domain.setRetireDate(rs.getDate("retire_Date"));
			domain.setMaxInsignia(rs.getString("max_insignia"));
			domain.setMaxEducation(rs.getString("max_education"));
			domain.setTaxNo(rs.getString("tax_no"));
			domain.setMarriedStatus(rs.getString("married_status"));
			domain.setWorkNumber(rs.getString("work_number"));
			domain.setInsureNo(rs.getString("insure_no"));
			domain.setFund(rs.getString("fund"));
			domain.setAddress(rs.getString("address"));
			domain.setZipCode(rs.getString("zip_code"));
			domain.setTelNo(rs.getString("tel_no"));
			domain.setEmail(rs.getString("email"));
			domain.setWorkingStatus(rs.getString("working_status"));
			domain.setPicture(rs.getString("picture"));
			domain.setWorklineCode(rs.getString("workline_code"));
			domain.setBirthdateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(domain.getBirthdate()));
			//domain.setUserId(rs.getLong("user_id"));
			domain.setFullName(BuckWaUtils.getFullName(domain.getThaiName(), domain.getThaiSurname()));
			domain.setPersonType(rs.getString("person_type"));
			
			domain.setFacultyDesc(rs.getString("faculty_desc"));
			domain.setDepartmentDesc(rs.getString("department_desc"));
			domain.setIsDean(rs.getString("is_dean"));
			domain.setIsHead(rs.getString("is_head"));
			return domain;
		}
	}
	*/
	private static class AcademicPersonMapper implements RowMapper<AcademicPerson> {
		@Override
		public AcademicPerson mapRow(ResultSet rs, int rowNum) throws SQLException {
			AcademicPerson domain = new AcademicPerson();
			domain.setPersonId(rs.getLong("person_id"));
			//domain.setEmployeeId(rs.getString("employee_id"));
			//domain.setCitizenId(rs.getString("citizen_id"));
			
			domain.setThaiName(rs.getString("thai_name").trim());
			domain.setThaiSurname(rs.getString("thai_surname"));
	
			domain.setEmail(rs.getString("email"));
			domain.setFacultyDesc(rs.getString("faculty_desc"));
			domain.setDepartmentDesc(rs.getString("department_desc"));
			domain.setIsDean(rs.getString("is_dean"));
			domain.setIsHead(rs.getString("is_head"));
			domain.setIsPresident(rs.getString("is_president"));
			domain.setEmployeeType(rs.getString("employee_type"));
			domain.setRegId(rs.getString("reg_id"));
			return domain;
		}
	}
	
	
	private class AcademicKPIUserMappingMapper implements RowMapper<AcademicKPIUserMapping> {   						
        @Override
		public AcademicKPIUserMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicKPIUserMapping domain = new AcademicKPIUserMapping(); 
        	
        	
        	domain.setKpiUserMappingId(rs.getLong("kpi_user_mapping_id"));
        	domain.setAcademicKPIId(rs.getLong("academic_kpi_id"));
			domain.setName(rs.getString("name"));		 
			domain.setWorkTypeCode(rs.getString("work_type_code"));  
			domain.setAcademicYear(rs.getString("academic_year"));
			domain.setAcademicKPICode(rs.getString("academic_kpi_code"));
			domain.setStatus(rs.getString("status"));
		 
		return domain;
    }
	}
	
	private class AcademicKPIAttributeValueMapper implements RowMapper<AcademicKPIAttributeValue> {   						
        @Override
		public AcademicKPIAttributeValue mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicKPIAttributeValue domain = new AcademicKPIAttributeValue(); 
        	 
        	domain.setAcademicKPIMappingId(rs.getLong("kpi_user_mapping_id"));
			domain.setName(rs.getString("name"));		
			domain.setValue(rs.getString("value"));	 
		 
			return domain;
    }
	}
	
	private class AcademicKPIMapper implements RowMapper<AcademicKPI> {   						
        @Override
		public AcademicKPI mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicKPI domain = new AcademicKPI(); 
        	domain.setAcademicKPIId(rs.getLong("academic_kpi_id"));
			domain.setName(rs.getString("name"));		
			domain.setCode(rs.getString("code"));	
			domain.setWorkTypeCode(rs.getString("work_type_code"));
			domain.setDescription(rs.getString("description"));	
			domain.setStatus(rs.getString("status"));	
			domain.setAcademicYear(rs.getString("academic_year"));
			domain.setMark(rs.getBigDecimal("mark"));
			domain.setUnitCode(rs.getString("unit_code"));
			domain.setUnitDesc(schoolUtil.getUnitDescMyCode(rs.getString("unit_code"), rs.getString("academic_year")));
		 
		return domain;
    }
	}
	
	private class AcademicKPIAttributeMapper implements RowMapper<AcademicKPIAttribute> {   						
        @Override
		public AcademicKPIAttribute mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicKPIAttribute domain = new AcademicKPIAttribute(); 
        	domain.setAcademicKPIAtributeId(rs.getLong("kpi_attribute_id"));
        	domain.setAcademicKPIId(rs.getLong("academic_kpi_id"));
			domain.setName(rs.getString("name"));		
			domain.setCode(rs.getString("code"));	
			domain.setAcademicKPICode(rs.getString("academic_kpi_code"));
			domain.setIsCalculate(rs.getString("is_calculate")); 
			domain.setIsValidateNumber(rs.getString("is_validate_number"));
			domain.setAcademicYear(rs.getString("academic_year"));
		    domain.setMandatory(rs.getString("mandatory"));
		 
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
        	domain.setPersonName(rs.getString("person_name"));
        	domain.setMark1(rs.getString("mark_1"));
        	domain.setMark2(rs.getString("mark_2"));
        	domain.setMark3(rs.getString("mark_3"));
        	domain.setMark4(rs.getString("mark_4"));
        	domain.setMark5(rs.getString("mark_5"));
        	domain.setMarkTotal(rs.getString("mark_total"));
 
		 
		return domain;
    }
	}
	
	private class AcademicYearEvaluateRoundMapper implements RowMapper<AcademicYearEvaluateRound> {   						
        @Override
		public AcademicYearEvaluateRound mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicYearEvaluateRound domain = new AcademicYearEvaluateRound(); 
        	domain.setRoundId(rs.getLong("round_id"));
			domain.setRound1StartDate(rs.getTimestamp("round1_start_date"));		
			domain.setRound1EndDate(rs.getTimestamp("round1_end_date"));
			domain.setRound1StartDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getTimestamp("round1_start_date")));
			domain.setRound1EndDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getTimestamp("round1_end_date")));
			
			domain.setRound1Status(rs.getString("round1_status"));
			
			domain.setRound2StartDate(rs.getTimestamp("round2_start_date"));
			domain.setRound2EndDate(rs.getTimestamp("round2_End_date"));
			domain.setRound2StartDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getTimestamp("round2_start_date")));
			domain.setRound2EndDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getTimestamp("round2_End_date")));
			domain.setRound2Status(rs.getString("round2_status"));
			
			domain.setAcademicYear(rs.getString("academic_year"));
			domain.setEvaluateType(rs.getString("evaluate_type"));
			domain.setEvaluateTypeDesc(rs.getString("evaluate_type_desc"));
			
		return domain;
    }
	}
 
}
