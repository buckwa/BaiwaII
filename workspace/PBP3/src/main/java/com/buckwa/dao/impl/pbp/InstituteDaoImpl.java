package com.buckwa.dao.impl.pbp;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.buckwa.dao.intf.pbp.InstituteDao;
import com.buckwa.dao.intf.pbp.PBPWorkTypeDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pbp.AcademicPerson;
import com.buckwa.domain.pbp.AcademicYearEvaluateRound;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.domain.pbp.report.DepartmentReport;
import com.buckwa.domain.pbp.report.DepartmentWorkTypeReport;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.service.intf.pbp.HeadService;
import com.buckwa.service.intf.pbp.PBPWorkTypeService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.school.SchoolUtil;

@Repository("instituteDao")
public class InstituteDaoImpl implements InstituteDao {
	private static Logger logger = LoggerFactory.getLogger(InstituteDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
	private FacultyService  facultyService;
	
	@Autowired
	private PBPWorkTypeDao pBPWorkTypeDao;
 
	@Autowired
	private HeadService headService;	
	@Autowired
	private PBPWorkTypeService pBPWorkTypeService;
	
	@Autowired
	private FacultyDao  facultyDao;
 
	@Override
	public void recalculate( String academicYear ) {	 
		
		BuckWaRequest request = new BuckWaRequest();
		
	//	logger.info(" ################################# Start Calculat Person #####################################");
		
		
		  jdbcTemplate.update("  delete from report_department  ");  ;
		  jdbcTemplate.update(" delete from report_faculty  ");  ;
		  jdbcTemplate.update(" delete from report_faculty_level  ");  ;
		//  jdbcTemplate.update(" delete from report_faculty_worktype  ");  ;
		  jdbcTemplate.update(" delete from report_institute  ");  ;
		  jdbcTemplate.update(" delete from report_worktype_department  ");  ;
 
			
		
			 request = new BuckWaRequest(); 
			request.put("academicYear",academicYear);
			request.put("status",""); 
			//List<Department> departmentListX = getDepartmentMark(academicYear);
			
			List<Department> departmentListX = getDepartmentMarkAllYear(academicYear);
			
		 
			if(departmentListX!=null&&departmentListX.size()>0){	
				
			 for(Department departmentx:departmentListX){
			
			 String facultyCode = departmentx.getFacultyCode();
				 
			 try{
				 departmentx.setAcademicYear(academicYear);
				 request = new BuckWaRequest(); 
				 
				request.put("academicYear",academicYear);
				request.put("facultyCode",facultyCode);
				//BuckWaResponse response = pBPWorkTypeService.getByAcademicYear(request);
				BuckWaResponse response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper"); 
					List<PBPWorkType> pBPWorkTypeList = pBPWorkTypeWrapper.getpBPWorkTypeList();				
					for(PBPWorkType typeTmp:pBPWorkTypeList){						 
						String shortDesc ="";
						StringTokenizer st = new StringTokenizer(typeTmp.getName(), " ");
						int numberOfSt =1;
				        while (st.hasMoreElements()) { 				        
				        	String stStr = st.nextElement().toString();				        	 
				            if(numberOfSt==1){
				            	shortDesc = stStr;
				            }
				            numberOfSt++;
				        } 	
				        
				        typeTmp.setShortDesc(shortDesc);
				        
				        
				 
				        // Sum total mark
				     List<AcademicPerson>  academicPersonListMark = departmentx.getAcademicPersonList();
				     BigDecimal totalMark = new BigDecimal(0.00);
				     
				     int totalPerson =0;
				     for(AcademicPerson personTmp: academicPersonListMark){				    	 
				    	List<PBPWorkType> pBPWorkTypeListTotalMark	= personTmp.getpBPWorkTypeWrapper().getpBPWorkTypeList();				    	
					      for(PBPWorkType totalMarkTmp:pBPWorkTypeListTotalMark){
					    	  if(typeTmp.getWorkTypeId().intValue()==totalMarkTmp.getWorkTypeId().intValue()){ 
									totalMark = totalMark.add(totalMarkTmp.getTotalInWorkType());								 
					    	  }					    	  
					      }
					      totalPerson++;  					       
				     }
				     
				     if(totalPerson==0){
				    	 totalPerson=1; 
				     }
				     
				     
				   
				     BigDecimal TotalInWorkTypeAVG = totalMark.divide(new BigDecimal(totalPerson) ,2, BigDecimal.ROUND_HALF_UP);
				     logger.info(" ## Department Name :"+departmentx.getName()+"  totalmark:"+totalMark+"  totalperson:"+totalPerson+" AVG:"+TotalInWorkTypeAVG);
				     typeTmp.setTotalAllWorkType(totalMark); 
				     typeTmp.setTotalInWorkType(totalMark);
				   //  typeTmp.setTotalInWorkType(TotalInWorkTypeAVG);
				 
					}
					
					departmentx.setpBPWorkTypeList(pBPWorkTypeList);
					
					// Save Department Summary to DB
					
					
					request.put("academicYear",academicYear);
					request.put("facultyCode",departmentx.getFacultyCode());
					response =  facultyService.getFacultyByCodeAndYear(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						Faculty faculty = (Faculty)response.getResObj("faculty");  
					 
						departmentx.setFaculty(faculty);
						
						request.put("department",departmentx);
						headService.saveDepartmentReportSummary(request);
					}
					
				}
				}catch(Exception ex){
					ex.printStackTrace();
					
					logger.info(" ############# Error on calculate department:"+departmentx.getCode()+":"+departmentx.getName()+"  So Skip");
				} 
				 			
			
			}
			}
			

			
	//	}
		
		
 
	/*	
		request.put("academicYear", academicYear);
		BuckWaResponse response =facultyService.getAllFaculty(request);
	 
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			List<Faculty> facultyList =  (List<Faculty>)response.getResObj("facultyList");
			
			
			for(Faculty facTmp:facultyList){
				logger.info(" ######################## Start Recalculate Faculty :"+facTmp.getName()+" ###########################");
				List<Department> departmentList = facTmp.getDepartmentList();
				
				
				final String facultyCode = facTmp.getCode();
				final String facultyName = facTmp.getName();
				final String academiYearStr = academicYear;
				
				
				 String facultyTypeCode1 = "";
				 String facultyTypeCode2 = "";
				 String facultyTypeCode3 = "";
				 String facultyTypeCode4 = "";
				 String facultyTypeCode5 = "";
				
				 String facultyTypeName1 = "";
				 String facultyTypeName2 = "";
				 String facultyTypeName3 = "";
				 String facultyTypeName4 = "";
				 String facultyTypeName5 = "";		
				
				
 				BigDecimal sumtype1 = new BigDecimal(0.00);
 				BigDecimal sumtype2 = new BigDecimal(0.00);
 				BigDecimal sumtype3 = new BigDecimal(0.00);
 				BigDecimal sumtype4 = new BigDecimal(0.00);
 				BigDecimal sumtype5 = new BigDecimal(0.00);	
 				
 				
 				BigDecimal facultysumtype1 = new BigDecimal(0.00);
 				BigDecimal facultysumtype2 = new BigDecimal(0.00);
 				BigDecimal facultysumtype3 = new BigDecimal(0.00);
 				BigDecimal facultysumtype4 = new BigDecimal(0.00);
 				BigDecimal facultysumtype5 = new BigDecimal(0.00);	
 				
 				BigDecimal facultyMarkLevel = new BigDecimal(0.00);
 				
 				
 				
				
				int departmentLoop =1;
				for(final Department depTmp:departmentList){
					logger.info(" ##   Department :"+depTmp.getName());
					

					final String depCode = depTmp.getCode();
					final String depName = depTmp.getName();
					String sqlcheckworktype = " select * from report_department where academic_year="+academicYear+" and faculty_code="+facTmp.getCode()+" and department_code="+depTmp.getCode();

					List<DepartmentWorkTypeReport> departmentWorkTypeReportList = new ArrayList();
					
	 				logger.info(" getWorkTypeReportDepartmentSQL:"+sqlcheckworktype);
	 				departmentWorkTypeReportList  = this.jdbcTemplate.query(sqlcheckworktype,	new DepartmentWorkTypeReportMapper() );	 
	 				if(departmentWorkTypeReportList!=null&&departmentWorkTypeReportList.size()>0){
	 					
		 				BigDecimal type1 = new BigDecimal(0.00);
		 				BigDecimal type2 = new BigDecimal(0.00);
		 				BigDecimal type3 = new BigDecimal(0.00);
		 				BigDecimal type4 = new BigDecimal(0.00);
		 				BigDecimal type5 = new BigDecimal(0.00);	 
		 				
		 				 String typeCode1 = "";
		 				 String typeCode2 = "";
		 				 String typeCode3 = "";
		 				 String typeCode4 = "";
		 				 String typeCode5 = "";
		 				
		 				 String typeName1 = "";
		 				 String typeName2 = "";
		 				 String typeName3 = "";
		 				 String typeName4 = "";
		 				 String typeName5 = "";		 				
		 				
		 				BigDecimal depMarkLevel = new BigDecimal(0.00);
						for(final DepartmentWorkTypeReport departmentWorkTypeReportTmp:departmentWorkTypeReportList){	 
							type1 = type1.add(new BigDecimal(departmentWorkTypeReportTmp.getMark1()));
							type2 = type2.add(new BigDecimal(departmentWorkTypeReportTmp.getMark2()));
							type3 = type3.add(new BigDecimal(departmentWorkTypeReportTmp.getMark3()));
							type4 = type4.add(new BigDecimal(departmentWorkTypeReportTmp.getMark4()));
							type5 = type5.add(new BigDecimal(departmentWorkTypeReportTmp.getMark5()));
							
							sumtype1 = sumtype1.add(new BigDecimal(departmentWorkTypeReportTmp.getMark1()));
							sumtype2 = sumtype2.add(new BigDecimal(departmentWorkTypeReportTmp.getMark2()));
							sumtype3 = sumtype3.add(new BigDecimal(departmentWorkTypeReportTmp.getMark3()));
							sumtype4 = sumtype4.add(new BigDecimal(departmentWorkTypeReportTmp.getMark4()));
							sumtype5 = sumtype5.add(new BigDecimal(departmentWorkTypeReportTmp.getMark5()));
							
							facultysumtype1 = facultysumtype1.add(new BigDecimal(departmentWorkTypeReportTmp.getMark1()));
							facultysumtype2 = facultysumtype2.add(new BigDecimal(departmentWorkTypeReportTmp.getMark2()));
							facultysumtype3 = facultysumtype3.add(new BigDecimal(departmentWorkTypeReportTmp.getMark3()));
							facultysumtype4 = facultysumtype4.add(new BigDecimal(departmentWorkTypeReportTmp.getMark4()));
							facultysumtype5 = facultysumtype5.add(new BigDecimal(departmentWorkTypeReportTmp.getMark5()));
							
							
							depMarkLevel = depMarkLevel.add(new BigDecimal(departmentWorkTypeReportTmp.getMark1()))
									.add( new BigDecimal(departmentWorkTypeReportTmp.getMark2()))
									.add( new BigDecimal(departmentWorkTypeReportTmp.getMark3()))
									.add( new BigDecimal(departmentWorkTypeReportTmp.getMark4()))
									.add( new BigDecimal(departmentWorkTypeReportTmp.getMark5()));
							
							facultyMarkLevel = facultyMarkLevel.add(depMarkLevel);
							
							
							typeCode1 = departmentWorkTypeReportTmp.getTypeCode1();
							typeCode2 = departmentWorkTypeReportTmp.getTypeCode2();
							typeCode3 = departmentWorkTypeReportTmp.getTypeCode3();
							typeCode4 = departmentWorkTypeReportTmp.getTypeCode4();
							typeCode5 = departmentWorkTypeReportTmp.getTypeCode5();
							
							typeName1 = departmentWorkTypeReportTmp.getTypeName1();
							typeName2 = departmentWorkTypeReportTmp.getTypeName2();
							typeName3 = departmentWorkTypeReportTmp.getTypeName3();
							typeName4 = departmentWorkTypeReportTmp.getTypeName4();
							typeName5 = departmentWorkTypeReportTmp.getTypeName5(); 
							
							// Update report_faculty_level 
							 
						}						
						
						BigDecimal depMarkLevelAVG = depMarkLevel.divide(new BigDecimal(departmentWorkTypeReportList.size()),2,BigDecimal.ROUND_HALF_UP);
						
						String checkFacultyLevel =" delete from report_faculty_level where faculty_code="+facTmp.getCode()+" and department_code="+depTmp.getCode()+" and academic_year="+academicYear;
						
						jdbcTemplate.update(checkFacultyLevel);	

						
						final String markLevel = depMarkLevelAVG+"";

						
						jdbcTemplate.update(new PreparedStatementCreator() {  
							public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
								PreparedStatement ps = connection.prepareStatement("" +						
										"  insert into report_faculty_level ("
										+ "academic_year, "
										+ "faculty_code,"
										+ "faculty_name,"
										+ "department_code,"
										+ "department_name,"											 
										+ "department_mark)   values (?, ?,?,?,?,? )" +
									 "", Statement.RETURN_GENERATED_KEYS);   
								ps.setString(1,academiYearStr);
								ps.setString(2,facultyCode);
								ps.setString(3,facultyName);
								ps.setString(4,depCode);
								ps.setString(5,depName);
								ps.setString(6,markLevel); 
								return ps;  
								}
							} ); 						
						
						 
						
						
						
						BigDecimal type1AVG = type1.divide(new BigDecimal(departmentWorkTypeReportList.size()),2,BigDecimal.ROUND_HALF_UP);
						BigDecimal type2AVG = type2.divide(new BigDecimal(departmentWorkTypeReportList.size()),2,BigDecimal.ROUND_HALF_UP);
						BigDecimal type3AVG = type3.divide(new BigDecimal(departmentWorkTypeReportList.size()),2,BigDecimal.ROUND_HALF_UP);
						BigDecimal type4AVG = type4.divide(new BigDecimal(departmentWorkTypeReportList.size()),2,BigDecimal.ROUND_HALF_UP);
						BigDecimal type5AVG = type5.divide(new BigDecimal(departmentWorkTypeReportList.size()),2,BigDecimal.ROUND_HALF_UP);
						
						
						
						
						// Update Report Faculty
						
						
						
						
						
						

					
						
						final String finaltypeCode1 = typeCode1;
						final String finaltypeCode2 = typeCode2;
		 				final String finaltypeCode3 = typeCode3;
		 				final String finaltypeCode4 = typeCode4;
		 				final String finaltypeCode5 = typeCode5;
		 				
		 				final String finaltypeName1 = typeName1;
		 				final String finaltypeName2 = typeName2;
		 				final String finaltypeName3 = typeName3;
		 				final String finaltypeName4 = typeName4;
		 				final String finaltypeName5 = typeName5;		
		 				
		 				
		 		
						 facultyTypeCode1 = typeCode1;
						 facultyTypeCode2 = typeCode2;
						 facultyTypeCode3 = typeCode3;
						 facultyTypeCode4 = typeCode4;
						 facultyTypeCode5 = typeCode5;
						
						 facultyTypeName1 = typeName1;
						 facultyTypeName2 = typeName2;
						 facultyTypeName3 = typeName3;
						 facultyTypeName4 = typeName4;
						 facultyTypeName5 = typeName5;				 				
		 				
		 				
		 				
		 				BigDecimal markTotal = type1AVG.add(type2AVG).add(type3AVG).add(type4AVG).add(type5AVG);
		 				
		 				final String finalMark1 = type1AVG+"";
		 				final String finalMark2 = type2AVG+"";
		 				final String finalMark3 = type3AVG+"";
		 				final String finalMark4 = type4AVG+"";
		 				final String finalMark5 = type5AVG+"";
		 				final String finalMarkTotal =markTotal+"";
		 				
	
						
						
						
		 				final String fsumtype1=sumtype1+""  ;
		 				final String  fsumtype2=sumtype2 +"" ;
		 				final String  fsumtype3=sumtype3+""   ;
		 				final String  fsumtype4=sumtype4+""  ;
		 				final String  fsumtype5=sumtype5 +""  ;	
		 				

		 				
						String checkFacultyLevelxxd =" delete from report_worktype_faculty where faculty_code="+facultyCode+" and academic_year="+academicYear;
						
						jdbcTemplate.update(checkFacultyLevelxxd);
						

						
						jdbcTemplate.update(new PreparedStatementCreator() {  
							public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
								PreparedStatement ps = connection.prepareStatement("" +						
										"  insert into report_worktype_faculty ("
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
										+ "mark_5)   values (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" +
									 "", Statement.RETURN_GENERATED_KEYS);   
								ps.setString(1,academiYearStr);
								ps.setString(2,facultyCode);
								ps.setString(3,facultyName);
								ps.setString(4,depCode);
								ps.setString(5,depName);
								ps.setString(6,finaltypeCode1);
								ps.setString(7,finaltypeName1);
								ps.setString(8,finaltypeCode2);
								ps.setString(9,finaltypeName2);
								ps.setString(10,finaltypeCode3);
								ps.setString(11,finaltypeName3);
								ps.setString(12,finaltypeCode4);
								ps.setString(13,finaltypeName4);
								ps.setString(14,finaltypeCode5);
								ps.setString(15,finaltypeName5);
								ps.setString(16,fsumtype1);
								ps.setString(17,fsumtype2);
								ps.setString(18,fsumtype3);
								ps.setString(19,fsumtype4);
								ps.setString(20,fsumtype5);
								 
								 
								return ps;  
								}
							} ); 		 
	 				}  
	 				
	 				departmentLoop++;
				}  
				
				
				
				// Update Report Faculty Level
				
				
				final String finalfacultyTypeCode1 = facultyTypeCode1;
				final String  finalfacultyTypeCode2 = facultyTypeCode2;
				final String  finalfacultyTypeCode3 = facultyTypeCode3;
				final String  finalfacultyTypeCode4 = facultyTypeCode4;
				final String  finalfacultyTypeCode5 =facultyTypeCode5;
				
				final String  finalfacultyTypeName1=  facultyTypeName1;
				final String  finalfacultyTypeName2 = facultyTypeName2;
				final String  finalfacultyTypeName3 =facultyTypeName3;
				final String  finalfacultyTypeName4 = facultyTypeName4;
				final String  finalfacultyTypeName5 =facultyTypeName5;
				 
				 
				final BigDecimal finalfacultysumtype1 = facultysumtype1;
				final BigDecimal finalfacultysumtype2 = facultysumtype2;
				final BigDecimal finalfacultysumtype3 = facultysumtype3;
				final BigDecimal finalfacultysumtype4 = facultysumtype4;
				final BigDecimal finalfacultysumtype5 = facultysumtype5;
				
				
				final BigDecimal type1AVG = finalfacultysumtype1.divide(new BigDecimal(departmentLoop),2,BigDecimal.ROUND_HALF_UP);
				final BigDecimal type2AVG = finalfacultysumtype2.divide(new BigDecimal(departmentLoop),2,BigDecimal.ROUND_HALF_UP);
				final BigDecimal type3AVG = finalfacultysumtype3.divide(new BigDecimal(departmentLoop),2,BigDecimal.ROUND_HALF_UP);
				final BigDecimal type4AVG = finalfacultysumtype4.divide(new BigDecimal(departmentLoop),2,BigDecimal.ROUND_HALF_UP);
				final BigDecimal type5AVG = finalfacultysumtype5.divide(new BigDecimal(departmentLoop),2,BigDecimal.ROUND_HALF_UP);
				final BigDecimal finalfacultyMarkLevelAVG = facultyMarkLevel.divide(new BigDecimal(departmentLoop),2,BigDecimal.ROUND_HALF_UP);
				 
				
				
				String checkFacultyLevelxx =" delete from report_faculty where faculty_code="+facultyCode+" and academic_year="+academicYear;				
				jdbcTemplate.update(checkFacultyLevelxx);	
				
				jdbcTemplate.update(new PreparedStatementCreator() {  
					public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
						PreparedStatement ps = connection.prepareStatement("" +						
								"  insert into report_faculty ("
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
						ps.setString(1,academiYearStr);
						ps.setString(2,facultyCode);
						ps.setString(3,facultyName);
						ps.setString(4,"");
						ps.setString(5,"");
						ps.setString(6,finalfacultyTypeCode1);
						ps.setString(7,finalfacultyTypeName1);
						ps.setString(8,finalfacultyTypeCode2);
						ps.setString(9,finalfacultyTypeName2);
						ps.setString(10,finalfacultyTypeCode3);
						ps.setString(11,finalfacultyTypeName3);
						ps.setString(12,finalfacultyTypeCode4);
						ps.setString(13,finalfacultyTypeName4);
						ps.setString(14,finalfacultyTypeCode5);
						ps.setString(15,finalfacultyTypeName5);
						ps.setString(16,type1AVG+"");
						ps.setString(17,type2AVG+"");
						ps.setString(18,type3AVG+"");
						ps.setString(19,type4AVG+"");
						ps.setString(20,type5AVG+"");
						ps.setString(21,finalfacultyMarkLevelAVG+"");						 
						return ps;  
						}
					} ); 						
							
				
				
				
			}	 

			
		}
		 */
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
					
					BigDecimal type1Sum = new BigDecimal(0.00);
					BigDecimal type2Sum = new BigDecimal(0.00);
					BigDecimal type3Sum = new BigDecimal(0.00);
					BigDecimal type4Sum = new BigDecimal(0.00);
					BigDecimal type5Sum = new BigDecimal(0.00);
					
					
					int loop1 =1;
					for(final PBPWorkType tmp:pbpWorkTypeList){
						
						
						if(loop1==1){
							depReport1.setMark1(tmp.getTotalInWorkType()+"");
							depReport1.setTypeCode1(tmp.getCode());
							depReport1.setTypeName1(tmp.getShortDesc());
							type1Sum = type1Sum.add(tmp.getTotalInWorkType());
						}else if(loop1==2){
							depReport1.setMark2(tmp.getTotalInWorkType()+"");
							depReport1.setTypeCode2(tmp.getCode());
							depReport1.setTypeName2(tmp.getShortDesc());
							type2Sum = type2Sum.add(tmp.getTotalInWorkType());
						}else if(loop1==3){
							depReport1.setMark3(tmp.getTotalInWorkType()+"");
							depReport1.setTypeCode3(tmp.getCode());
							depReport1.setTypeName3(tmp.getShortDesc());
							type3Sum = type3Sum.add(tmp.getTotalInWorkType());
						}else if(loop1==4){
							depReport1.setMark4(tmp.getTotalInWorkType()+"");
							depReport1.setTypeCode4(tmp.getCode());
							depReport1.setTypeName4(tmp.getShortDesc());
							type4Sum = type4Sum.add(tmp.getTotalInWorkType());
						}
						else if(loop1==5){
							depReport1.setMark5(tmp.getTotalInWorkType()+"");
							depReport1.setTypeCode5(tmp.getCode());
							depReport1.setTypeName5(tmp.getShortDesc());
							type5Sum = type5Sum.add(tmp.getTotalInWorkType());
						}
						
						
						loop1++;
					}
			 
	  		
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
							+ "mark_5)   values (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" +
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
					 
					 
					return ps;  
					}
				} ); 
			
			
			// Insert Report department Sum  
			
			final String ftype1Sum =  type1Sum +"" ;
			final String ftype2Sum =  type2Sum +"" ;
			final String ftype3Sum =  type3Sum +"" ;
			final String ftype4Sum =  type4Sum +"" ;
			final String ftype5Sum =  type5Sum +"" ;
			
			jdbcTemplate.update(new PreparedStatementCreator() {  
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
					PreparedStatement ps = connection.prepareStatement("" +						
							"  insert into report_department_sum ("
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
							+ "mark_5)   values (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" +
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
					ps.setString(16,ftype1Sum);
					ps.setString(17,ftype2Sum);
					ps.setString(18,ftype3Sum);
					ps.setString(19,ftype4Sum);
					ps.setString(20,ftype5Sum);
					 
					 
					return ps;  
					}
				} ); 
		
		
		
		
		// Start insert report_worktype_department
		
		List<AcademicPerson> academicPersonList = finalDepartment.getAcademicPersonList();

		
		for(AcademicPerson academicPerson :academicPersonList){
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
						depReport2.setMark1(tmp.getTotalInWorkType()+"");
						depReport2.setTypeCode1(tmp.getCode());
						depReport2.setTypeName1(tmp.getShortDesc());
						totalMarkBig = totalMarkBig.add(tmp.getTotalInWorkType());
					}else if(loop==2){
						depReport2.setMark2(tmp.getTotalInWorkType()+"");
						depReport2.setTypeCode2(tmp.getCode());
						depReport2.setTypeName2(tmp.getShortDesc());
						totalMarkBig = totalMarkBig.add(tmp.getTotalInWorkType());
					}else if(loop==3){
						depReport2.setMark3(tmp.getTotalInWorkType()+"");
						depReport2.setTypeCode3(tmp.getCode());
						depReport2.setTypeName3(tmp.getShortDesc());
						totalMarkBig = totalMarkBig.add(tmp.getTotalInWorkType());
					}else if(loop==4){
						depReport2.setMark4(tmp.getTotalInWorkType()+"");
						depReport2.setTypeCode4(tmp.getCode());
						depReport2.setTypeName4(tmp.getShortDesc());
						totalMarkBig = totalMarkBig.add(tmp.getTotalInWorkType());
					}
					else if(loop==5){
						depReport2.setMark5(tmp.getTotalInWorkType()+"");
						depReport2.setTypeCode5(tmp.getCode());
						depReport2.setTypeName5(tmp.getShortDesc());
						totalMarkBig = totalMarkBig.add(tmp.getTotalInWorkType());
					}
					
					
					loop++;
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
			
	 
			
			
		}
		
		
		
		
		/// Save Report Faculty
		
		
		
		
	
			
			
 	
 
	}
	
	
	 
	private List<Department> getDepartmentMark( String academicYear ) {	
		 
		List<Department> departmentList=new ArrayList<Department>();
		//String sqlDepartment = " select d.* from department d 	inner join person_pbp p on (d.name=p.department_desc) 	where p.email='"+headUserName+"'";
		String sqlDepartment = " select * from department 	 where academic_year="+academicYear;

	//	logger.info("  getDepartmentMark sqlDepartment:"+sqlDepartment);
		
		try{
			departmentList = this.jdbcTemplate.query(sqlDepartment,	new DepartmentMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
			//logger.info(" sqlDepartment:"+sqlDepartment);
		}
		if(departmentList!=null&&departmentList.size()>0){ 
			
			for(Department department:departmentList){
				// Get User belong to department 
				String sqlacademicPerson = "  select * from person_pbp where department_desc ='"+department.getName()+"'   and  academic_year='"+academicYear+"'";
				//logger.info("  getByHeadAcademicYear sqlacademicPerson:"+sqlacademicPerson);
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

			
		 
			
			
		}
		 
		
		return departmentList;
	}
	
	private List<Department> getDepartmentMarkAllYear( String academicYear ) {	
		 
		List<Department> departmentList=new ArrayList<Department>();
		//String sqlDepartment = " select d.* from department d 	inner join person_pbp p on (d.name=p.department_desc) 	where p.email='"+headUserName+"'";
		String sqlDepartment = " select * from department 	 where academic_year="+academicYear;

	//	logger.info("  getDepartmentMark sqlDepartment:"+sqlDepartment);
		
		try{
			departmentList = this.jdbcTemplate.query(sqlDepartment,	new DepartmentMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
			//logger.info(" sqlDepartment:"+sqlDepartment);
		}
		if(departmentList!=null&&departmentList.size()>0){ 
			
			for(Department department:departmentList){
				
				// Get User belong to department 
				String sqlacademicPerson = "  select * from person_pbp where department_desc ='"+department.getName()+"'   and  academic_year='"+academicYear+"'";
				//logger.info("  getByHeadAcademicYear sqlacademicPerson:"+sqlacademicPerson);
				List<AcademicPerson> academicPersonList  = this.jdbcTemplate.query(sqlacademicPerson,	new AcademicPersonMapper() );  
				
				if(academicPersonList!=null&&academicPersonList.size()>0){
					
				
				
					BigDecimal totalMark = new BigDecimal(0.00);
					
					AcademicPerson zeroPerson = academicPersonList.get(0);
					
					 logger.info(" ######### Start Calculate Faculty:"+ zeroPerson.getFacultyDesc()+" Department:"+department.getName()+" Total Person:"+academicPersonList.size());
					 for(AcademicPerson personTmp:academicPersonList){ 
						 logger.info(personTmp.getEmail()+" "+personTmp.getThaiName()+" "+personTmp.getThaiSurname());
					 }
					 
					 
					for(AcademicPerson personTmp:academicPersonList){ 
						// Get KPI User Mapping  
						
						String employeeType = personTmp.getEmployeeTypeNo();
 
						 String facultyCode = facultyDao.getFacultyByCodeByAcademicYearAndName(academicYear, personTmp.getFacultyDesc());
						personTmp.setpBPWorkTypeWrapper(pBPWorkTypeDao.getCalculateByAcademicYearAllYear(academicYear, personTmp.getEmail(),employeeType,facultyCode)); 
						totalMark = totalMark.add(personTmp.getpBPWorkTypeWrapper().getTotalMark()).setScale(2);
					}		
					
					department.setAcademicPersonList(academicPersonList); 
				
				}
				
			}
 
			
			
		}
		 
		
		return departmentList;
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
	
	
	 
	private   class PersonPBPMapper implements RowMapper<Person> {
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			
 
			Person domain = new Person();
			domain.setPersonId(rs.getLong("person_id"));
			domain.setThaiName(rs.getString("thai_name"));
			domain.setThaiSurname(rs.getString("thai_surname"));
			domain.setRateNo(rs.getString("rate_no"));
			domain.setEmployeeType(rs.getString("employee_type"));
			domain.setMaxEducation(rs.getString("max_education"));
			domain.setEmail(rs.getString("email"));
			domain.setPicture(rs.getString("picture"));
			domain.setPersonType(rs.getString("person_type"));
			domain.setStatus(rs.getString("status"));
			domain.setDepartmentDesc(rs.getString("department_desc"));
			domain.setTitleName(rs.getString("title_name"));
			domain.setAcademicRank(rs.getString("academic_rank"));
			domain.setFacultyDesc(rs.getString("faculty_desc"));
			domain.setRegId(rs.getString("reg_id"));
			logger.info(" ### picture:"+rs.getString("picture"));
         
			return domain;
		}
	}
	

}
