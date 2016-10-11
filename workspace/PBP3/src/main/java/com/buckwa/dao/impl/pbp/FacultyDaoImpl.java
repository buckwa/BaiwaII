package com.buckwa.dao.impl.pbp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import com.buckwa.dao.impl.uitl.RejectSubjectUtil;
import com.buckwa.dao.intf.admin.GroupDao;
import com.buckwa.dao.intf.pbp.AcademicKPIDao;
import com.buckwa.dao.intf.pbp.FacultyDao;
import com.buckwa.dao.intf.pbp.PBPWorkTypeDao;
import com.buckwa.domain.pbp.AcademicKPI;
import com.buckwa.domain.pbp.AcademicKPIAttribute;
import com.buckwa.domain.pbp.AcademicKPIAttributeValue;
import com.buckwa.domain.pbp.AcademicKPIUserMapping;
import com.buckwa.domain.pbp.AcademicPerson;
import com.buckwa.domain.pbp.ChainOfCommandWrapper;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.FacultyWrapper;
import com.buckwa.domain.pbp.report.RadarPlotReport;
import com.buckwa.domain.pbp.report.TimeTableReport;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.school.SchoolConstants;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.ws.newws.oxm.GetSubject;
import com.buckwa.ws.newws.oxm.GetSubjectResponse;
import com.buckwa.ws.newws.oxm.GetTeacherofSubject;
import com.buckwa.ws.newws.oxm.GetTeacherofSubjectResponse;
import com.buckwa.ws.newws.oxm.Subject;

@Repository("facultyDao")
public class FacultyDaoImpl implements FacultyDao {
	private static Logger logger = LoggerFactory.getLogger(FacultyDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
	private GenerateCodeUtil  generateCodeUtil;
	
	
	@Autowired
	private PBPWorkTypeDao  pBPWorkTypeDao;
	
 
	@Autowired
	private GroupDao groupDao;
 
	@Autowired
	private WebServiceTemplate  subjectServiceWSTemplate  ;	
	
	@Autowired
	private WebServiceTemplate  teachTableWSTemplate  ;	
	
	@Autowired
	private WebServiceTemplate  subjectServiceWSTemplateChum  ;	
	
	@Autowired
	private WebServiceTemplate  teachTableWSTemplateChum  ;	
	@Autowired
	private RejectSubjectUtil rejectSubjectUtil;
	//subjectServiceWSTemplate
	
	@Autowired
	private AcademicKPIDao academicKPIDao;
	
	@Override
	public FacultyWrapper getByAcademicYear( String getByAcademicYear) {		 		
		String sql =" select *  from faculty where academic_year ='"+getByAcademicYear+"'" ; 
		logger.info(" sql:"+sql);
		List<Faculty> facultyList  =null;
		
		try{
			
			facultyList = this.jdbcTemplate.query(sql,	new FacultyMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
					
	 
		FacultyWrapper facultyWrapper = new FacultyWrapper(); 
		
		
		if(facultyList!=null&&facultyList.size()>0){
			
			for(Faculty facultyTmp:facultyList){
				String sqlDepartment =" select *  from department where faculty_code ='" +facultyTmp.getCode()+"' and academic_year='"+facultyTmp.getAcademicYear()+"'"; 
//				logger.info(" sqlDepartment:"+sqlDepartment);
				facultyTmp.setDean (getDeanByFacultyId(facultyTmp.getFacultyId()+"",getByAcademicYear))  ;
				List<Department> departmentList  = this.jdbcTemplate.query(sqlDepartment,	new DepartmentMapper() );
				for(Department depTmp:departmentList){
					depTmp.setHead (getHeadByDepartmentId(depTmp.getDepartmentId()+"",getByAcademicYear))  ;
					//depTmp.setHead (getHeadByDepartmentDesc(depTmp.getDepartmentId()+"",getByAcademicYear,depTmp.getName()))  ;
					
				}
				
				facultyTmp.setDepartmentList(departmentList);
			}

		} 
		facultyWrapper.setFacultyList(facultyList);
		facultyWrapper.setPresident(getPresident(getByAcademicYear));
		
		return facultyWrapper;
	}
	
	
	@Override
	public FacultyWrapper getAllMarkByAcademicYear( String academicYear) {		 		
		String sql =" select *  from faculty where academic_year ='"+academicYear+"'" ; 
		logger.info(" sql:"+sql);
		List<Faculty> facultyList  =null;
		
		try{
			
			facultyList = this.jdbcTemplate.query(sql,	new FacultyMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
					
	 
		FacultyWrapper facultyWrapper = new FacultyWrapper(); 
		
		
		if(facultyList!=null&&facultyList.size()>0){
			
			for(Faculty facultyTmp:facultyList){
				String sqlDepartment =" select *  from department where faculty_code ='" +facultyTmp.getCode()+"' and academic_year='"+facultyTmp.getAcademicYear()+"'"; 
//				logger.info(" sqlDepartment:"+sqlDepartment);
				facultyTmp.setDean (getDeanByFacultyId(facultyTmp.getFacultyId()+"",academicYear))  ;
				List<Department> departmentList  = this.jdbcTemplate.query(sqlDepartment,	new DepartmentMapper() );
				for(Department depTmp:departmentList){
					//depTmp.setHead (getHeadByDepartmentId(depTmp.getDepartmentId()+"",getByAcademicYear))  ;
					//depTmp.setHead (getHeadByDepartmentDesc(depTmp.getDepartmentId()+"",getByAcademicYear,depTmp.getName()))  ;
		 			
						List<AcademicPerson> headList = new ArrayList();
						
						String sqlAcademicPerson = " select * from person_pbp where department_desc='"+depTmp.getName()+"' and academic_year='"+depTmp.getAcademicYear()+"'";
						logger.info(" sqlAcademicPerson sql:"+sqlAcademicPerson);
					    
						List<AcademicPerson> academicPersonList  = this.jdbcTemplate.query(sqlAcademicPerson,	new AcademicPersonMapper() );
						
						
						for(AcademicPerson personTmp:academicPersonList){
							String employeeTypeNo =personTmp.getEmployeeTypeNo();
							String email = personTmp.getEmail();
							if("2".equalsIgnoreCase(employeeTypeNo)){
								// พนักงาน
								 
								
								List<RadarPlotReport> markList =pBPWorkTypeDao.getRadarPlotPersonMark(email,academicYear,"1");
								if(markList!=null&markList.size()>0){
									int loopmark =1;
									for(RadarPlotReport markTmp:markList){
										if(loopmark==1){
											personTmp.setMark1(markTmp.getAxisValue());
										}else if(loopmark==2){
											personTmp.setMark2(markTmp.getAxisValue());
										}else if (loopmark==3){
											personTmp.setMark3(markTmp.getAxisValue());
										}else if (loopmark==4){
											personTmp.setMark4(markTmp.getAxisValue());
										}else if (loopmark==5){
											personTmp.setMark5(markTmp.getAxisValue());
										} 
										loopmark++;
									}
								}
								
								
							}else{
								// ข้าราชการ รวมสองรอบ
								List<RadarPlotReport> markList1 =pBPWorkTypeDao.getRadarPlotPersonMark(email,academicYear,"1");
								
								List<RadarPlotReport> markList2 =pBPWorkTypeDao.getRadarPlotPersonMark(email,academicYear,"2");
								
								
								for(int i=1;i<=5;i++){
									if(i==1){
										RadarPlotReport r1 = markList1.get(0);
										RadarPlotReport r2 = markList2.get(0);
										BigDecimal tmpBig = new BigDecimal(r1.getAxisValue()).add(new BigDecimal(r2.getAxisValue()));
										personTmp.setMark1(tmpBig+"");
									}else if(i==2){
										RadarPlotReport r1 = markList1.get(1);
										RadarPlotReport r2 = markList2.get(1);
										BigDecimal tmpBig = new BigDecimal(r1.getAxisValue()).add(new BigDecimal(r2.getAxisValue()));
										personTmp.setMark2(tmpBig+"");
									}else if (i==3){
										RadarPlotReport r1 = markList1.get(2);
										RadarPlotReport r2 = markList2.get(2);
										BigDecimal tmpBig = new BigDecimal(r1.getAxisValue()).add(new BigDecimal(r2.getAxisValue()));
										personTmp.setMark3(tmpBig+"");
									}else if (i==4){
										RadarPlotReport r1 = markList1.get(3);
										RadarPlotReport r2 = markList2.get(3);
										BigDecimal tmpBig = new BigDecimal(r1.getAxisValue()).add(new BigDecimal(r2.getAxisValue()));
										personTmp.setMark4(tmpBig+"");
									}else if (i==5){
										RadarPlotReport r1 = markList1.get(4);
										RadarPlotReport r2 = markList2.get(4);
										BigDecimal tmpBig = new BigDecimal(r1.getAxisValue()).add(new BigDecimal(r2.getAxisValue()));
										personTmp.setMark5(tmpBig+"");
									} 
									
								} 
								
							} 
							
						} 
						depTmp.setAcademicPersonList(academicPersonList);
 
					
				}
				
				facultyTmp.setDepartmentList(departmentList);
			}

		} 
		facultyWrapper.setFacultyList(facultyList);
		facultyWrapper.setPresident(getPresident(academicYear));
		
		return facultyWrapper;
	}
	
	
	@Override
	public List<Faculty> getFacultyListByAcademicYear( String getByAcademicYear) {		 		
		String sql =" select *  from faculty where academic_year ='"+getByAcademicYear+"'" ; 
	//	logger.info(" sql:"+sql);
		List<Faculty> facultyList  =null;
		
		try{
			facultyList = this.jdbcTemplate.query(sql,	new FacultyMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
 
		 
		
		return facultyList;
	}
	
	@Override
	public Faculty getById( String facultyId) {		 		
		String sql =" select *  from faculty where faculty_id ="+facultyId+"" ; 
		logger.info(" sql:"+sql);
		 Faculty  faculty   =null;
		
		try{
			faculty = this.jdbcTemplate.queryForObject(sql,	new FacultyMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
		
		if(faculty!=null){			
			 
				String sqlDepartment =" select *  from department where faculty_id =" +faculty.getFacultyId(); 
				
				List<Department> departmentList  = this.jdbcTemplate.query(sqlDepartment,	new DepartmentMapper() );
				
				faculty.setDepartmentList(departmentList);
		} 
		 
		return faculty;
	}
	
	
	@Override
	public List<Faculty> getAllFaculty( String academicYear) {		 		
		String sql =" select *  from faculty where academic_year ="+academicYear+"" ; 
		logger.info(" sql:"+sql);
		 List<Faculty>  facultyList   =null;
		
		try{
			facultyList = this.jdbcTemplate.query(sql,	new FacultyMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
		
		
		if(facultyList!=null){	
			
			for(Faculty facTmp:facultyList){
				 
				 
					String sqlDepartment =" select *  from department where faculty_code =" +facTmp.getCode()+" and academic_year="+facTmp.getAcademicYear(); 
					
					List<Department> departmentList  = this.jdbcTemplate.query(sqlDepartment,	new DepartmentMapper() );
					
					if(departmentList!=null){
						facTmp.setDepartmentList(departmentList);		
					}
					 
			}

		} 
		 
		return facultyList;
	}
	
	
	
	@Override
	public Faculty getFacultyByCodeAndYear( String depCode,String academicYear) {	
		
		
		String sql =" select *  from faculty where code ="+depCode+" and academic_year="+academicYear ; 
		logger.info(" sql:"+sql);
		 Faculty  faculty   =null;
		
		try{
			faculty = this.jdbcTemplate.queryForObject(sql,	new FacultyMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
		
	 
		logger.info(" sql faculty:"+BeanUtils.getBeanString(faculty));
		return faculty;
	}
	
	@Override
	public String getFacultyByCodeByAcademicYearAndName( String academicYear,String name) {	
		
		String returnValue="";
		String sql =" select *  from faculty where name ='"+name+"' and academic_year="+academicYear ; 
//		logger.info(" sql:"+sql);
		 Faculty  faculty   =null;
		
		try{
			faculty = this.jdbcTemplate.queryForObject(sql,	new FacultyMapper() );	
			returnValue = faculty.getCode();
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
		
	 
		//logger.info(" sql faculty:"+BeanUtils.getBeanString(faculty));
		return returnValue;
	}
	
	@Override
	public void assignKPIInit(  ) {	
		
		//1. select kpi engineering
		//2. loop kpi select attribute
		
		
		String sqlacademickpi =" select *  from academic_kpi   " ; 
		logger.info("   sqlacademickpi:"+sqlacademickpi);
		List<AcademicKPI> academicKPIList  =null;
		
		try{
			academicKPIList = this.jdbcTemplate.query(sqlacademickpi,	new AcademicKPIMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		} 
		
		logger.info(" Found Academic kpi size :"+academicKPIList.size());
		
		
		
		for(AcademicKPI kpitmp:academicKPIList){ 
			
			String sqlAttribute =" select *  from academic_kpi_attribute where academic_kpi_id ="+kpitmp.getAcademicKPIId()  ; 
			List<AcademicKPIAttribute> academicKPIAttributeList = new ArrayList();
			try{
				academicKPIAttributeList = this.jdbcTemplate.query(sqlAttribute,	new AcademicKPIAttributeMapper() );	
			}catch (org.springframework.dao.EmptyResultDataAccessException ex){
				ex.printStackTrace();
			}  
			
			kpitmp.setAcademicKPIAttributeList(academicKPIAttributeList);
			
		}
		
	 
		String sql =" select *  from faculty  where academic_year="+2558 ; 
		logger.info(" sql:"+sql);
		List <Faculty > facultyList   =null;
		
		try{
			facultyList = this.jdbcTemplate.query(sql,	new FacultyMapper() );	
			 
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
		
		logger.info(" Found Faculty Sise:"+facultyList.size());
		for(Faculty facultyTmp:facultyList){
			logger.info(" code :"+facultyTmp.getCode());
		}
		
		for(Faculty facultyTmp:facultyList){
			final String facultyCode =facultyTmp.getCode();
			if(facultyCode.equalsIgnoreCase("01")){
				
			}else{
				
				logger.info(" Start Insert Faculty :"+facultyCode);
				
				
				try{
				for(final AcademicKPI finalDomain:academicKPIList){ 
					final int nexCode = 0;
					KeyHolder keyHolder = new GeneratedKeyHolder(); 		
					jdbcTemplate.update(new PreparedStatementCreator() {  
						public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
							PreparedStatement ps = connection.prepareStatement("" +						
									"  insert into academic_kpi (name, code,work_type_code,mark,academic_year,unit_code,rule_code,order_no,description,faculty_code,from_reg) values (?,?, ?,?,?,?,?,?,?,?,?)" +
								 "", Statement.RETURN_GENERATED_KEYS);   
							ps.setString(1,finalDomain.getName());
							ps.setInt(2,Integer.parseInt(finalDomain.getCode()));
							ps.setInt(3,new Integer(finalDomain.getWorkTypeCode()));	
							ps.setBigDecimal(4, finalDomain.getMark());
							ps.setString(5,finalDomain.getAcademicYear());
							ps.setInt(6,new Integer(finalDomain.getUnitCode()));	
							ps.setString(7,finalDomain.getMultiplyValue());
							ps.setInt(8, finalDomain.getOrderNo()==null?new Integer(1):new Integer(finalDomain.getOrderNo()));
							ps.setString(9,finalDomain.getDescription());
							ps.setString(10,facultyCode);
							ps.setString(11,finalDomain.getFromRegis());
							return ps;  
							}
						}, 	keyHolder); 	
					final Long returnid =  keyHolder.getKey().longValue();	
					
					
					List<AcademicKPIAttribute> attributeList = finalDomain.getAcademicKPIAttributeList();
					
					for(final AcademicKPIAttribute attbuttmp:attributeList){ 
						 
						jdbcTemplate.update(new PreparedStatementCreator() {  
							public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
								PreparedStatement ps = connection.prepareStatement("" +						
										"  insert into academic_kpi_attribute (name, code,academic_kpi_code,is_calculate,academic_year,academic_kpi_id,mandatory,is_validate_number) values (?,?, ?,?,?,?,?,?  )" +
									 "", Statement.RETURN_GENERATED_KEYS);   
								ps.setString(1,attbuttmp.getName());
								ps.setInt(2,nexCode);
								ps.setInt(3,new Integer(attbuttmp.getAcademicKPICode()));	 
								ps.setString(4,attbuttmp.getIsCalculate()); 
								
								ps.setString(5,attbuttmp.getAcademicYear()); 
								ps.setLong(6,returnid); 
								ps.setString(7,attbuttmp.getMandatory()); 
								ps.setString(8,attbuttmp.getIsValidateNumber()); 
								
								return ps;  
								}
							} ); 	
						 				
						
					}
					
					
				}
				
				}catch(Exception ex){
					ex.printStackTrace();
				}

				
				
			}
			
			
			
		}
		
		
	}	
	
	 
	
	
	
 
	
	@Override
	public ChainOfCommandWrapper getByFaculty(String facultyId,String academicYear) {
		// TODO Auto-generated method stub
		
		ChainOfCommandWrapper chainOfCommandWrapper = new ChainOfCommandWrapper();
		
		String sql = " select * from  faculty where faculty_id="+facultyId+"";
		System.out.print(" getByFaculty sql:"+sql);
		Faculty faculty=null;
		try{
			faculty = this.jdbcTemplate.queryForObject(sql,	new FacultyMapper() );	
		}catch (org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
		if(faculty!=null){
 		
				 
				String sqlDepartment =" select *  from department where faculty_id =" +facultyId;
				System.out.print(" getByFaculty sqlDepartment:"+sqlDepartment);
				List<Department> departmentList  = this.jdbcTemplate.query(sqlDepartment,	new DepartmentMapper() );
				
				List<AcademicPerson> deanList = new ArrayList();
				if(departmentList!=null&&departmentList.size()>0){
					
					for(Department depTmp: departmentList){
						List<AcademicPerson> headList = new ArrayList();
						
						String sqlAcademicPerson = " select * from person_pbp where department_desc='"+depTmp.getName()+"' and academic_year='"+academicYear+"'";
						logger.info(" sqlAcademicPerson sql:"+sqlAcademicPerson);
						List<AcademicPerson> academicPersonList  = this.jdbcTemplate.query(sqlAcademicPerson,	new AcademicPersonMapper() );
						
						if(academicPersonList!=null&&academicPersonList.size()>0){
							for(AcademicPerson personTmp:academicPersonList){
								String isHead = personTmp.getIsHead();
								String isDean = personTmp.getIsDean();
								if("Y".equalsIgnoreCase(isHead)){
									headList.add(personTmp);
								}
								if("Y".equalsIgnoreCase(isDean)){
									deanList.add(personTmp);
								} 
							}
						}
						
						depTmp.setAcademicPersonList(academicPersonList);
						depTmp.setHeadList(headList);
					} 
					
				} 
				faculty.setDepartmentList(departmentList); 
				faculty.setDeanList(deanList);
				chainOfCommandWrapper.setFaculty(faculty);
		}
		 
		 
		return chainOfCommandWrapper;
	}


	@Override
	public ChainOfCommandWrapper getByDepartment(String departmentId ,String academicYear) {
				ChainOfCommandWrapper chainOfCommandWrapper = new ChainOfCommandWrapper();
		 
				String sqlDepartment =" select *  from department where department_id=" +departmentId+""; 
				
				Department department=null;
				try{
					department = this.jdbcTemplate.queryForObject(sqlDepartment,	new DepartmentMapper() );	
				}catch (org.springframework.dao.EmptyResultDataAccessException ex){
					ex.printStackTrace();
				}
				if(department!=null){
					
					List<AcademicPerson> headList = new ArrayList();
					
					String sqlAcademicPerson = " select * from person_pbp where department_desc='"+department.getName()+"' and academic_year='"+academicYear+"'";
					logger.info(" sqlAcademicPerson sql:"+sqlAcademicPerson);
				    
					List<AcademicPerson> academicPersonList  = this.jdbcTemplate.query(sqlAcademicPerson,	new AcademicPersonMapper() );
					
					if(academicPersonList!=null&&academicPersonList.size()>0){
						for(AcademicPerson personTmp:academicPersonList){
							String isHead = personTmp.getIsHead();
							String isDean = personTmp.getIsDean();
							if("Y".equalsIgnoreCase(isHead)){
								headList.add(personTmp);
							}
						 
						}
					}
					
					department.setAcademicPersonList(academicPersonList);
					department.setHeadList(headList);					
					chainOfCommandWrapper.setDepartment(department);
				}
 
		 
		return chainOfCommandWrapper;
	}
	
	
	
	@Override
	public AcademicPerson getDeanByFacultyId(String facultyId,String academicYear) {
		// TODO Auto-generated method stub
		
	     AcademicPerson dean = null;
	     
	    String sql =" select * from person_pbp a 	     left join faculty f on(a.dean_faculty=f.name) 	     where  a.is_dean='Y'  and f.faculty_id="+facultyId+" and a.academic_year='"+academicYear+"'";
	    
	  //  logger.info(" getDeanByFacultyId sql:"+sql);
	    
	    try{
	    	
	    	List<AcademicPerson> academicPersonList  = this.jdbcTemplate.query(sql,	new AcademicPersonMapper() );
	    	if(academicPersonList!=null&&academicPersonList.size()>0){
	    		dean = academicPersonList.get(0);
	    	}
	    }catch(Exception ex){
	    	dean = new AcademicPerson();
	    	ex.printStackTrace();
	    }
		 
	   // logger.info(" Found Dean :"+BeanUtils.getBeanString(dean));
		return dean;
	}
	
	@Override
	public AcademicPerson getHeadByDepartmentId(String departmentId,String academicYear) {
		// TODO Auto-generated method stub
		
		
		Department department = getDepartmentById(departmentId);
		
		 AcademicPerson head =getHeadByDepartmentDesc( academicYear,department.getName());
	     
 
	   // logger.info(" Found Head :"+BeanUtils.getBeanString(head));
		return head;
	}
	
	
 
	public AcademicPerson getHeadByDepartmentDesc( String academicYear,String departmentDesc) {
		// TODO Auto-generated method stub
		
	     AcademicPerson dean = null;
	     
	    String sql =" select * from person_pbp a 	          where  a.is_head='Y'  and a.head_department='"+departmentDesc+"' and a.academic_year='"+academicYear+"'";
	    //logger.info(" getHeadByDepartmentDesc sql:"+sql);
	    try{
	    	
	    	List<AcademicPerson> academicPersonList  = this.jdbcTemplate.query(sql,	new AcademicPersonMapper() );
	    	if(academicPersonList!=null&&academicPersonList.size()>0){
	    		dean = academicPersonList.get(0);
	    	}
	    }catch(Exception ex){
	    	dean = new AcademicPerson();
	    	ex.printStackTrace();
	    }
		 
	   // logger.info(" Found Head :"+BeanUtils.getBeanString(dean));
		return dean;
	}
	
	
	
	@Override
	public void assignDean(String oldDean,String newDean,String academicYear,String facultyDesc) {
		// TODO Auto-generated method stub
 
	    try{
	    	String sqlResetOldDean  ="update person_pbp set is_dean='N' where email='" +oldDean+"' and academic_year="+academicYear;
	    	logger.info(" sqlResetOldDean:"+sqlResetOldDean);
			this.jdbcTemplate.update(sqlResetOldDean); 
			// Step 2 Delete All User - Group Mapping
			this.jdbcTemplate.update( "delete from   buckwausergroup   where username=? ", oldDean);

			// Step 3. Create User - Group Mapping
			
			String userGroup =groupDao.getGroupIdFromGroupName("GROUP_USER");
		 
			logger.info(" Found Group Id  userGroup:"+userGroup+"   headGroup:"+oldDean);
			String[] groupIdArray = new String[1];
			groupIdArray[0] =userGroup;
		 
			if (groupIdArray != null && groupIdArray.length > 0) {
				for (int i = 0; i < groupIdArray.length; i++) {
					String groupIdStr = groupIdArray[i];
					this.jdbcTemplate .update( "insert into buckwausergroup (username, group_id) values (?, ?)", oldDean, groupIdStr);
				}
			}
	    }catch(Exception ex){
	    	 
	    	ex.printStackTrace();
	    }
	    
	    try{
	    	String newDeanSQL  ="update person_pbp set is_dean='Y' ,dean_faculty ='"+facultyDesc+"' where email='" +newDean+"' and academic_year="+academicYear;
	    	logger.info(" newDeanSQL:"+newDeanSQL);
			this.jdbcTemplate.update(newDeanSQL); 
			
			// Step 2 Delete All User - Group Mapping
			this.jdbcTemplate.update( "delete from   buckwausergroup   where username=? ", newDean);

			// Step 3. Create User - Group Mapping
			
			String userGroup =groupDao.getGroupIdFromGroupName("GROUP_USER");
			String headGroup =groupDao.getGroupIdFromGroupName("GROUP_DEAN");
			logger.info(" Found Group Id  userGroup:"+userGroup+"   headGroup:"+headGroup);
			String[] groupIdArray = new String[2];
			groupIdArray[0] =userGroup;
			groupIdArray[1] =headGroup;
			if (groupIdArray != null && groupIdArray.length > 0) {
				for (int i = 0; i < groupIdArray.length; i++) {
					String groupIdStr = groupIdArray[i];
					this.jdbcTemplate .update( "insert into buckwausergroup (username, group_id) values (?, ?)", newDean, groupIdStr);
				}
			}
		 
	    }catch(Exception ex){
	    	 
	    	ex.printStackTrace();
	    }
	 
	 
		 
	}
	
	@Override
	public void assignHead(String oldDean,String newDean,String academicYear,String departmentDesc) {
		// TODO Auto-generated method stub
 
	    try{
	    	String sqlResetOldDean  ="update person_pbp set is_head='N' where email='" +oldDean+"' and academic_year="+academicYear;
	    	logger.info(" sqlResetOldHead:"+sqlResetOldDean);
			this.jdbcTemplate.update(sqlResetOldDean); 
			
			
			
			// Step 2 Delete All User - Group Mapping
			this.jdbcTemplate.update( "delete from   buckwausergroup   where username=? ", oldDean);

			// Step 3. Create User - Group Mapping
			
			String userGroup =groupDao.getGroupIdFromGroupName("GROUP_USER");
		 
			logger.info(" Found Group Id  userGroup:"+userGroup+"   headGroup:"+oldDean);
			String[] groupIdArray = new String[1];
			groupIdArray[0] =userGroup;
		 
			if (groupIdArray != null && groupIdArray.length > 0) {
				for (int i = 0; i < groupIdArray.length; i++) {
					String groupIdStr = groupIdArray[i];
					this.jdbcTemplate .update( "insert into buckwausergroup (username, group_id) values (?, ?)", oldDean, groupIdStr);
				}
			}
	    }catch(Exception ex){	    	 
	    	ex.printStackTrace();
	    }
	    
	    try{
	    	String newDeanSQL  ="update person_pbp set is_head='Y' ,head_department ='"+departmentDesc+"' where email='" +newDean+"' and academic_year="+academicYear;
	    	logger.info(" newHeadSQL:"+newDeanSQL);
			this.jdbcTemplate.update(newDeanSQL); 
	    }catch(Exception ex){	    	 
	    	ex.printStackTrace();
	    }
	    
	    
		// Step 2 Delete All User - Group Mapping
		this.jdbcTemplate.update( "delete from   buckwausergroup   where username=? ", newDean);

		// Step 3. Create User - Group Mapping
		
		String userGroup =groupDao.getGroupIdFromGroupName("GROUP_USER");
		String headGroup =groupDao.getGroupIdFromGroupName("GROUP_HEAD");
		logger.info(" Found Group Id  userGroup:"+userGroup+"   headGroup:"+headGroup);
		String[] groupIdArray = new String[2];
		groupIdArray[0] =userGroup;
		groupIdArray[1] =headGroup;
		if (groupIdArray != null && groupIdArray.length > 0) {
			for (int i = 0; i < groupIdArray.length; i++) {
				String groupIdStr = groupIdArray[i];
				this.jdbcTemplate .update( "insert into buckwausergroup (username, group_id) values (?, ?)", newDean, groupIdStr);
			}
		}
	 
	 
		 
	}
	
	
	
	@Override
	public AcademicPerson getPresident(String academicYear) {
		// TODO Auto-generated method stub
		
	     AcademicPerson dean = null;
	     
	   // String sql =" select * from person_pbp a 	     left join department f on(a.department_desc=f.name) 	     where  a.is_president='Y' and a.academic_year="+academicYear;
	    String sql =" select * from person_pbp a 	  	     where  a.is_president='Y' and a.academic_year="+academicYear;

	    logger.info(" getPresident sql:"+sql);
	    try{
	    	
	    	List<AcademicPerson> academicPersonList  = this.jdbcTemplate.query(sql,	new AcademicPersonMapper() );
	    	if(academicPersonList!=null&&academicPersonList.size()>0){
	    		dean = academicPersonList.get(0);
	    	}
	    }catch(Exception ex){
	    	dean = new AcademicPerson();
	    	ex.printStackTrace();
	    }
		 
//	    logger.info(" Found President :"+BeanUtils.getBeanString(dean));
		return dean;
	}
	
	/*

	@Override
	public void assignPresident(String oldDean,String newDean) {
		// TODO Auto-generated method stub
 
	    try{
	    	String sqlResetOldDean  ="update person_pbp set is_president='N' where email='" +oldDean+"'";
	    	logger.info(" sqlResetOldPresident:"+sqlResetOldDean);
			this.jdbcTemplate.update(sqlResetOldDean); 
	    }catch(Exception ex){
	    	 
	    	ex.printStackTrace();
	    }
	    
	    try{
	    	String newDeanSQL  ="update person_pbp set is_president='Y' where email='" +newDean+"'";
	    	logger.info(" newPresidentSQL:"+newDeanSQL);
			this.jdbcTemplate.update(newDeanSQL); 
	    }catch(Exception ex){
	    	 
	    	ex.printStackTrace();
	    }
	 
	 
		 
	}
	
	
	*/
	
	@Override
	public void assignPresident(String oldDean,String newDean,String academicYear) {
		// TODO Auto-generated method stub
 
	    try{
	    	String sqlResetOldDean  ="update person_pbp set is_president='N' where email='" +oldDean+"' and academic_year="+academicYear;
	    	logger.info(" sqlResetOldPresident:"+sqlResetOldDean);
			this.jdbcTemplate.update(sqlResetOldDean); 
			
			
			
			// Step 2 Delete All User - Group Mapping
			this.jdbcTemplate.update( "delete from   buckwausergroup   where username=? ", oldDean);

			// Step 3. Create User - Group Mapping
			
			String userGroup =groupDao.getGroupIdFromGroupName("GROUP_USER");
		 
			logger.info(" Found Group Id  userGroup:"+userGroup+"   headGroup:"+oldDean);
			String[] groupIdArray = new String[1];
			groupIdArray[0] =userGroup;
		 
			if (groupIdArray != null && groupIdArray.length > 0) {
				for (int i = 0; i < groupIdArray.length; i++) {
					String groupIdStr = groupIdArray[i];
					this.jdbcTemplate .update( "insert into buckwausergroup (username, group_id) values (?, ?)", newDean, groupIdStr);
				}
			}
	    }catch(Exception ex){	    	 
	    	ex.printStackTrace();
	    }
	    
	    try{
	    	String newDeanSQL  ="update person_pbp set is_president='Y' where email='" +newDean+"' and academic_year="+academicYear;
	    	logger.info(" newPresidentSQL:"+newDeanSQL);
			this.jdbcTemplate.update(newDeanSQL); 
	    }catch(Exception ex){	    	 
	    	ex.printStackTrace();
	    }
	    
	    
		// Step 2 Delete All User - Group Mapping
		this.jdbcTemplate.update( "delete from   buckwausergroup   where username=? ", newDean);

		// Step 3. Create User - Group Mapping
		
		String userGroup =groupDao.getGroupIdFromGroupName("GROUP_USER");
		String headGroup =groupDao.getGroupIdFromGroupName("GROUP_PRESIDENT");
		logger.info(" Found Group Id  userGroup:"+userGroup+"   PresidentGroup:"+headGroup);
		String[] groupIdArray = new String[2];
		groupIdArray[0] =userGroup;
		groupIdArray[1] =headGroup;
		if (groupIdArray != null && groupIdArray.length > 0) {
			for (int i = 0; i < groupIdArray.length; i++) {
				String groupIdStr = groupIdArray[i];
				this.jdbcTemplate .update( "insert into buckwausergroup (username, group_id) values (?, ?)", newDean, groupIdStr);
			}
		}
	 
	 
		 
	}
	

	@Override
	public boolean isNameExist(String name) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalRole  from faculty t  where t.name='"+StringEscapeUtils.escapeSql(name)+"'";
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
	public void create(Faculty faculty) {
		logger.info(" #RoleDaoImpl.create # ");		
		final Faculty finalfaculty = faculty;
		final String academicYear = faculty.getAcademicYear();
		final String nexCode = generateCodeUtil.getNextFacultyCode(academicYear);
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into faculty (name, academic_year,enable,code) values (?, ?,?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalfaculty.getName());
				ps.setString(2,finalfaculty.getAcademicYear());
				ps.setBoolean(3,true);		 	
				ps.setString(4, nexCode);
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
		
		
		// Initial KPI
 
	}
	
 
	
	@Override
	public boolean isDepartmentExist(Faculty faculty) {
		 boolean returnValue = false;
		try{
			Department departmentCreate =faculty.getDepartment();
			String sqltmp = "select count(*) as totalItem  from department t  where t.name='"+StringEscapeUtils.escapeSql(departmentCreate.getName())+"' and faculty_code='"+faculty.getCode()+"' and academic_year='"+faculty.getAcademicYear()+"'";
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
	public void createDepartment(Faculty faculty) {
		logger.info(" #RoleDaoImpl.create # ");		
		final Faculty finalfaculty = faculty;
		final Department departmentCreate =faculty.getDepartment();
		final String academicYear = schoolUtil.getCurrentAcademicYear();
		final String nexCode = generateCodeUtil.getNextDepartmentCode(faculty);
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into department (name, academic_year,code,faculty_code,faculty_id) values (?, ?,?,?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,departmentCreate.getName());
				ps.setString(2,finalfaculty.getAcademicYear()); 
				ps.setString(3, nexCode);
				ps.setString(4, finalfaculty.getCode());
				ps.setLong(5, finalfaculty.getFacultyId());
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
 
	}
	
 
	
	@Override
	public void updateFaculty(Faculty faculty) {
		logger.info(" # updateFaculty : "+BeanUtils.getBeanString(faculty));		
		final Faculty finalfaculty = faculty; 
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  update faculty set name=? where faculty_id=? " +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalfaculty.getName());
				ps.setLong(2,finalfaculty.getFacultyId()); 
			 ;
				return ps;  
				}
			}, 	keyHolder); 	
	 
 
	}
		
	
	@Override
	public void updateFacultyWS(List<com.buckwa.ws.newws.oxm.Faculty> facultyList) {
		//logger.info(" #  : "+BeanUtils.getBeanString(facultyList));	
		
		for(com.buckwa.ws.newws.oxm.Faculty tmp:facultyList){
			
			
			final com.buckwa.ws.newws.oxm.Faculty finalfaculty = tmp; 
			
			// Check aready exist
			final String academicYear = schoolUtil.getCurrentAcademicYear();
			boolean isExist = isFacultyWSExist(finalfaculty,academicYear);
			
			if(!isExist){
				
				//final int nexCode = generateCodeUtil.getNextFacultyCode(academicYear);
				KeyHolder keyHolder = new GeneratedKeyHolder(); 		
				jdbcTemplate.update(new PreparedStatementCreator() {  
					public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
						PreparedStatement ps = connection.prepareStatement("" +						
								"  insert into faculty (name, academic_year,enable,code,icl_code,eng_name,abbreviation) values (?, ?,?,?,?,?,?)" +
							 "", Statement.RETURN_GENERATED_KEYS);   
						ps.setString(1,finalfaculty.getFacultyTname());
						ps.setString(2,academicYear);
						ps.setBoolean(3,true);		 	
						ps.setString(4, finalfaculty.getFacultyId());
						ps.setString(5, finalfaculty.getIclFacultyId());
						ps.setString(6, finalfaculty.getFacultyEname());
						ps.setString(7, finalfaculty.getBrief());
						return ps;  
						}
					}, 	keyHolder); 	
				Long returnid =  keyHolder.getKey().longValue();	
				
			}				
			}
			


	  
	}
	
	
	
	
	@Override
	public void updateFacultyWSChum(List<com.buckwa.ws.chum.oxm.Faculty> facultyList) {
		//logger.info(" #  : "+BeanUtils.getBeanString(facultyList));	
		
		for(com.buckwa.ws.chum.oxm.Faculty tmp:facultyList){
			
			
			final com.buckwa.ws.chum.oxm.Faculty finalfaculty = tmp; 
			
			// Check aready exist
			final String academicYear = schoolUtil.getCurrentAcademicYear();
			boolean isExist = isFacultyWSExistChum(finalfaculty,academicYear);
			
			if(!isExist){
				
				//final int nexCode = generateCodeUtil.getNextFacultyCode(academicYear);
				KeyHolder keyHolder = new GeneratedKeyHolder(); 		
				jdbcTemplate.update(new PreparedStatementCreator() {  
					public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
						PreparedStatement ps = connection.prepareStatement("" +						
								"  insert into faculty (name, academic_year,enable,code,icl_code,eng_name,abbreviation) values (?, ?,?,?,?,?,?)" +
							 "", Statement.RETURN_GENERATED_KEYS);   
						ps.setString(1,finalfaculty.getFacultyTname());
						ps.setString(2,academicYear);
						ps.setBoolean(3,true);		 	
						ps.setString(4, finalfaculty.getFacultyId());
						ps.setString(5, finalfaculty.getIclFacultyId());
						ps.setString(6, finalfaculty.getFacultyEname());
						ps.setString(7, finalfaculty.getBrief());
						return ps;  
						}
					}, 	keyHolder); 	
				Long returnid =  keyHolder.getKey().longValue();	
				
			}				
			} 
	  
	}
	
	
 
	
	private boolean isFacultyWSExist (com.buckwa.ws.newws.oxm.Faculty factultyws,String academicYear){
		boolean returnValue = false;
		try{ 
			/*
			String sqltmp = "select count(*) as totalItem  from faculty t  where t.code='"+StringEscapeUtils.escapeSql(factultyws.getFacultyId())+
					"' and icl_code='"+factultyws.getIclFacultyId()+
					 "' and name like %"+factultyws.getFacultyTname()+"%"+
					"' and academic_year="+academicYear;
			*/
			String sqltmp = "select count(*) as totalItem  from faculty t  where  t.name like '%"+factultyws.getFacultyTname()+"%'"+
					" and t.academic_year="+academicYear;
		//	logger.info(" # sqltmp : "+sqltmp );	
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
				logger.info(" Found Faculty :"+factultyws.getFacultyTname()+"   , Do nothing !!");
			}else{
				logger.info(" Not Found Faculty :"+factultyws.getFacultyTname()+"   , Create New !!");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
	}
	
	private boolean isFacultyWSExistChum (com.buckwa.ws.chum.oxm.Faculty factultyws,String academicYear){
		boolean returnValue = false;
		try{ 
			/*
			String sqltmp = "select count(*) as totalItem  from faculty t  where t.code='"+StringEscapeUtils.escapeSql(factultyws.getFacultyId())+
					"' and icl_code='"+factultyws.getIclFacultyId()+
					 "' and name like %"+factultyws.getFacultyTname()+"%"+
					"' and academic_year="+academicYear;
			*/
			String sqltmp = "select count(*) as totalItem  from faculty t  where  t.name like '%"+factultyws.getFacultyTname()+"%'"+
					" and t.academic_year="+academicYear;
		//	logger.info(" # sqltmp : "+sqltmp );	
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
				logger.info(" Found Faculty :"+factultyws.getFacultyTname()+"   , Do nothing !!");
			}else{
				logger.info(" Not Found Faculty :"+factultyws.getFacultyTname()+"   , Create New !!");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
	}
	
	
	
	@Override
	public void updateDepartmentWS(List<com.buckwa.ws.newws.oxm.Department> departmentList) {
		//logger.info(" #  : "+BeanUtils.getBeanString(departmentList));	
		
		for(com.buckwa.ws.newws.oxm.Department tmp:departmentList){
			
			
			final com.buckwa.ws.newws.oxm.Department finaldepartment = tmp; 
			
			// Check aready exist
			final String academicYear = schoolUtil.getCurrentAcademicYear();
			boolean isExist = isDepartmentWSExist(finaldepartment,academicYear);
			
			if(!isExist){
				
				//final int nexCode = generateCodeUtil.getNextDepartmentCode(academicYear);
				KeyHolder keyHolder = new GeneratedKeyHolder(); 
				jdbcTemplate.update(new PreparedStatementCreator() {  
					public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
						PreparedStatement ps = connection.prepareStatement("" +						
								"  insert into department (name, academic_year,code,faculty_code,eng_name) values (?, ?,?,?,?)" +
							 "", Statement.RETURN_GENERATED_KEYS);   
						ps.setString(1,finaldepartment.getDepartmentName());
						ps.setString(2,academicYear); 
						ps.setString(3, finaldepartment.getDepartmentId());
						ps.setString(4, finaldepartment.getFacultyId());
						ps.setString(5, finaldepartment.getDepartmentEname());
						return ps;  
						}
					}, 	keyHolder); 	
				Long returnid =  keyHolder.getKey().longValue();	
				
			}				
			}
			


	  
	}
	
	
	@Override
	public void updateDepartmentWSChum(List<com.buckwa.ws.chum.oxm.Department> departmentList) {
		//logger.info(" #  : "+BeanUtils.getBeanString(departmentList));	
		
		for(com.buckwa.ws.chum.oxm.Department tmp:departmentList){
			
			
			final com.buckwa.ws.chum.oxm.Department finaldepartment = tmp; 
			
			// Check aready exist
			final String academicYear = schoolUtil.getCurrentAcademicYear();
			boolean isExist = isDepartmentWSExistChum(finaldepartment,academicYear);
		 
			if(!isExist){
				
				//final int nexCode = generateCodeUtil.getNextDepartmentCode(academicYear);
				KeyHolder keyHolder = new GeneratedKeyHolder(); 
				jdbcTemplate.update(new PreparedStatementCreator() {  
					public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
						PreparedStatement ps = connection.prepareStatement("" +						
								"  insert into department (name, academic_year,code,faculty_code,eng_name) values (?, ?,?,?,?)" +
							 "", Statement.RETURN_GENERATED_KEYS);   
						ps.setString(1,finaldepartment.getDepartmentName());
						ps.setString(2,academicYear); 
						ps.setString(3, finaldepartment.getDepartmentId());
						ps.setString(4, finaldepartment.getFacultyId());
						ps.setString(5, finaldepartment.getDepartmentEname());
						return ps;  
						}
					}, 	keyHolder); 	
				Long returnid =  keyHolder.getKey().longValue();	
				
			}				
			}
			


	  
	}
	
	
	
	
	@Override
	public void updateTeachTableWS(int semester,List<com.buckwa.ws.newws.oxm.TeachTable> teachTableList,int degree,String facultyCode,String academicYear) {
		//logger.info(" #  : "+BeanUtils.getBeanString(departmentList));	
		
		for(com.buckwa.ws.newws.oxm.TeachTable tmp:teachTableList){ 
			final com.buckwa.ws.newws.oxm.TeachTable finalTeachtable = tmp;  
			
		//	 logger.info(" ############ Subject Id : "+finalTeachtable.getSubjectId()+ " Total Student:"+finalTeachtable.getStudentTotal() );
			if(finalTeachtable.getTeacherId()==null||finalTeachtable.getSubjectId()==null||finalTeachtable.getSemester()==null||finalTeachtable.getLectOrPrac()==null
					||finalTeachtable.getStudentTotal()==null||finalTeachtable.getStudentTotal().intValue()==0){ 				
				 logger.info(" ############ Subject Id : "+finalTeachtable.getSubjectId()+" ############ Found Null  or total Student 0 Skipp----------- : "  );
			     
//				 if(finalTeachtable.getStudentTotal()==null){
//					 
//					 
//				 }else if(finalTeachtable.getStudentTotal().intValue()==0){
//					 
//					 
//				 }
			
			}else{ 
			// Check aready exist
			//final String academicYear = schoolUtil.getCurrentAcademicYear();
			boolean isExist = isTeachtableWSExist(finalTeachtable,academicYear);
			
			if(!isExist){  
				
				try{
				// Get ThaiName,EngName,Credit
			 
					 if(tmp.getSubjectId()!=null){
					
					//	 if(tmp.getSubjectId().equals("01236044")){
					
						 
					 
					 GetSubject getSubjectRequest = new GetSubject();
					 getSubjectRequest.setSubjectId(tmp.getSubjectId());
					 getSubjectRequest.setDegree(degree);
					 getSubjectRequest.setDetail(true);
					// logger.info(" ###########  SubjectId:"+tmp.getSubjectId());
					 
					 
						GetSubjectResponse returnObjSubject =(GetSubjectResponse)subjectServiceWSTemplate.marshalSendAndReceive(getSubjectRequest, new WebServiceMessageCallback() {
							public void doWithMessage(WebServiceMessage messageSubject) throws IOException, TransformerException {
								try {
									SOAPMessage soapMessageSubject = ((SaajSoapMessage)messageSubject).getSaajMessage();				 
							       ByteArrayOutputStream out = new ByteArrayOutputStream();
							     // soapMessageSubject.writeTo(out);
						        //   logger.info(" soapMessageSubject SOAP Request Payload: " + new String(out.toByteArray()));
							         
								} catch(Exception e) {
									e.printStackTrace();
								}
							}
						});	 
						
						
						
						 GetTeacherofSubject getTeacherofSubject = new GetTeacherofSubject();
						 getTeacherofSubject.setSubjectId(tmp.getSubjectId());
						 getTeacherofSubject.setDegree(degree);
						 getTeacherofSubject.setSemester(Integer.parseInt(finalTeachtable.getSemester()));
						 getTeacherofSubject.setYear(finalTeachtable.getYear());
						 
						 
						 GetTeacherofSubjectResponse getTeacherofSubjectResponse =(GetTeacherofSubjectResponse)teachTableWSTemplate.marshalSendAndReceive(getTeacherofSubject, new WebServiceMessageCallback() {
								public void doWithMessage(WebServiceMessage messageSubject) throws IOException, TransformerException {
									try {
										SOAPMessage soapMessageSubject = ((SaajSoapMessage)messageSubject).getSaajMessage();				 
								       ByteArrayOutputStream out = new ByteArrayOutputStream();
								     //  soapMessageSubject.writeTo(out);
							          // logger.info(" soapMessageSubject SOAP Request Payload: " + new String(out.toByteArray()));
								         
									} catch(Exception e) {
										e.printStackTrace();
									}
								}
							});	 
						 
						String isCoTeach = "N";
						//List<String> teacherIdList =getTeacherofSubjectResponse.getLect().getTeacherId();
						//logger.info(" ## Teacher List :"+teacherIdList);
						if(getTeacherofSubjectResponse.getLect()!=null){
							if(getTeacherofSubjectResponse.getLect().getTeacherId()!=null&&getTeacherofSubjectResponse.getLect().getTeacherId().size()>1){
								
								//logger.info("  ### Check Is Co Teach SubjectId:"+tmp.getSubjectId()+" Degree:"+degree+" Found Lect List Size: "+getTeacherofSubjectResponse.getLect().getTeacherId().size()+" So  -----> Y");
								isCoTeach="Y";
							}							
						}

						//List<String> teacherIdPracList =getTeacherofSubjectResponse.getLect().getTeacherId();
						//logger.info(" ## Teacher List :"+teacherIdList);
//						if(getTeacherofSubjectResponse.getPrac()!=null){
//							if(getTeacherofSubjectResponse.getPrac().getTeacherId()!=null&&getTeacherofSubjectResponse.getPrac().getTeacherId().size()>1){
//								isCoTeach="Y";
//								logger.info("  ### Check Is Co Teach SubjectId:"+tmp.getSubjectId()+" Degree:"+degree+"  Found Prac List Size: "+getTeacherofSubjectResponse.getPrac().getTeacherId().size()+" So  -----> Y");
//							}							
//						}	
//						
						
						
						
						final String finalIsCoTeach = isCoTeach;
						
						
						
						List<Subject> subJectListReturnList  =returnObjSubject.getSubject();
						if(subJectListReturnList!=null&&subJectListReturnList.size()>0){
							
							if(subJectListReturnList.size()>1){
							//	logger.info(" ############################################ Incorrect Size:"+ subJectListReturnList.size());
							}
							final Subject subject=subJectListReturnList.get(0);
							
							
							boolean isInRejectList = checkInRejectList(subject.getSubjectId());
							
							String subjectRemark ="";
							if(isInRejectList){
								subjectRemark ="นำเข้าผลงานเอง";
							}
							
							int pracHr = 0;
							
							try{
								pracHr =Integer.parseInt(subject.getPracHr());
							}catch(Exception ex){
								ex.printStackTrace();
							}
							
							if(pracHr>=8){
								subjectRemark ="นำเข้าผลงานเอง";
								isInRejectList = true;
								
								logger.info(" "+finalTeachtable.getSubjectId()+" :"+subject.getSubjectEname()+"  PracHr > 8 -----------------> Eject");
							}
							
							final String finalRemark = subjectRemark;
							
							logger.info(" "+finalTeachtable.getSubjectId()+" :"+subject.getSubjectEname()+" Co-Teach:"+finalIsCoTeach +" finalRemark:"+finalRemark+" isInRejectList:"+isInRejectList);
							
							//final int nexCode = generateCodeUtil.getNextDepartmentCode(academicYear);
							KeyHolder keyHolder = new GeneratedKeyHolder(); 
							jdbcTemplate.update(new PreparedStatementCreator() {  
								public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
									PreparedStatement ps = connection.prepareStatement("" +						
											"  insert into time_table ("
											+ "faculty_id,"
											+ "dept_id,"
											+ "curr_id,"
											+ "subject_id,"
											+ "semester,"
											+ "year,"
											+ "class,"
											+ "section,"
											+ "teacher_id,"
											+ "lect_or_prac,"
											+ "teach_day,"
											+ "teach_time,"
											+ "teach_time2,"
											+ "student_total,"
											+ "degree ,"
											+ "thai_name,"
											+ "eng_name,"
											+ "lect_hr,"
											+ "prac_hr,"
											+ "credit, from_source, is_co_teach,remark  )"
											+ "values (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" +
										 "", Statement.RETURN_GENERATED_KEYS);   
									ps.setString(1,finalTeachtable.getFacultyId());
									ps.setString(2,finalTeachtable.getDepartmentId()); 
									ps.setString(3, finalTeachtable.getCurrId());
									ps.setString(4, finalTeachtable.getSubjectId());
									ps.setString(5, finalTeachtable.getSemester());
									ps.setString(6, finalTeachtable.getYear());
									ps.setString(7, finalTeachtable.getClazz());
									ps.setString(8, finalTeachtable.getSection());
									ps.setString(9, finalTeachtable.getTeacherId());
									ps.setString(10, finalTeachtable.getLectOrPrac());
									ps.setString(11, finalTeachtable.getTeachDay());
									ps.setString(12, finalTeachtable.getTeachTime());
									ps.setString(13, finalTeachtable.getTeachTime2());
									ps.setString(14, finalTeachtable.getStudentTotal()+"");
									ps.setString(15, finalTeachtable.getDegree()+"");
									
									ps.setString(16, subject.getSubjectTname()+"");
									ps.setString(17, subject.getSubjectEname()+"");
									ps.setString(18, subject.getLectHr()+"");
									ps.setString(19, subject.getPracHr()+"");
									ps.setString(20, subject.getCredit()+"");
									ps.setString(21, SchoolConstants.FROM_SOUCE_REG);
									ps.setString(22, finalIsCoTeach);
									ps.setString(23, finalRemark);
									
									return ps;  
									}
								}, 	keyHolder); 	
							Long returnid =  keyHolder.getKey().longValue();								
							
							jdbcTemplate.update("commit");
							
							
							//logger.info(" ############################### Start Create KPI User Mapping #########################");
							// Check Found Skip
				 
							
							if(isInRejectList){
								//key exists, skip
								logger.info(" ############################### Check Reject True , So skip #########################");
							}else{
								logger.info(" ############################### Check Reject False , So Create Work #########################");
								
								AcademicKPIUserMapping academicKPIUserMapping =  getMatchingKPITeachTable(tmp,subject,finalIsCoTeach,academicYear,facultyCode);
								 
								
								academicKPIUserMapping.setIsCoTeach(finalIsCoTeach);
								academicKPIUserMapping.setFromSource(SchoolConstants.FROM_SOUCE_REG);	
								 
								
								academicKPIUserMapping.setStatus("APPROVED");
								
								//academicKPIDao.importwork(academicKPIUserMapping);
								academicKPIUserMapping.setAcademicYear(academicYear);
								academicKPIDao.importworkTimeTable(semester,academicKPIUserMapping);								
								
							}
							 
							

							
						}
						
					//	 }// End debug Subject
				 }else{
					 logger.info(" ### SubjectId null , skip on Create ");
				 }
				}catch(Exception exx){
					exx.printStackTrace();
				}
				 }	 
			} 
		} 
		
		
	}
	
	@Override
	public void updateTeachTableWSChum(int semester,List<com.buckwa.ws.chum.oxm.TeachTable> teachTableList,int degree,String facultyCode,String academicYear,String regId) {
		//logger.info(" #  : "+BeanUtils.getBeanString(departmentList));	
		
		for(com.buckwa.ws.chum.oxm.TeachTable tmp:teachTableList){ 
			final com.buckwa.ws.chum.oxm.TeachTable finalTeachtable = tmp;  
			
		//	 logger.info(" ############ Subject Id : "+finalTeachtable.getSubjectId()+ " Total Student:"+finalTeachtable.getStudentTotal() );
			if(finalTeachtable.getTeacherId()==null||finalTeachtable.getSubjectId()==null||finalTeachtable.getSemester()==null||finalTeachtable.getLectOrPrac()==null
					||finalTeachtable.getStudentTotal()==null||finalTeachtable.getStudentTotal().intValue()==0){ 				
				// logger.info(" ############ Subject Id : "+finalTeachtable.getSubjectId()+" ############ Found Null  or total Student 0 Skipp----------- : "  );
			}else{ 
			// Check aready exist
			//final String academicYear = schoolUtil.getCurrentAcademicYear();
				
				 
				String newRegId = regId;
	 
				
			boolean isExist = isTeachtableWSExistChum(finalTeachtable,academicYear,newRegId);
		 
			if(!isExist){  
				
				try{
				// Get ThaiName,EngName,Credit
			 
					 if(tmp.getSubjectId()!=null){
					
					//	 if(tmp.getSubjectId().equals("01236044")){
					
						 
					 
						 com.buckwa.ws.chum.oxm.GetSubject getSubjectRequest = new com.buckwa.ws.chum.oxm.GetSubject();
					 getSubjectRequest.setSubjectId(tmp.getSubjectId());
					 getSubjectRequest.setDegree(degree);
					 getSubjectRequest.setDetail(true);
					// logger.info(" ###########  SubjectId:"+tmp.getSubjectId());
					 
					 
					 com.buckwa.ws.chum.oxm.GetSubjectResponse returnObjSubject =(com.buckwa.ws.chum.oxm.GetSubjectResponse)subjectServiceWSTemplateChum.marshalSendAndReceive(getSubjectRequest, new WebServiceMessageCallback() {
							public void doWithMessage(WebServiceMessage messageSubject) throws IOException, TransformerException {
								try {
									SOAPMessage soapMessageSubject = ((SaajSoapMessage)messageSubject).getSaajMessage();				 
							       ByteArrayOutputStream out = new ByteArrayOutputStream();
							     // soapMessageSubject.writeTo(out);
						        //   logger.info(" soapMessageSubject SOAP Request Payload: " + new String(out.toByteArray()));
							         
								} catch(Exception e) {
									e.printStackTrace();
								}
							}
						});	 
						
						
						
					 com.buckwa.ws.chum.oxm.GetTeacherofSubject getTeacherofSubject = new com.buckwa.ws.chum.oxm.GetTeacherofSubject();
						 getTeacherofSubject.setSubjectId(tmp.getSubjectId());
						 getTeacherofSubject.setDegree(degree);
						 getTeacherofSubject.setSemester(Integer.parseInt(finalTeachtable.getSemester()));
						 getTeacherofSubject.setYear(finalTeachtable.getYear());
						 
						 
						 com.buckwa.ws.chum.oxm.GetTeacherofSubjectResponse getTeacherofSubjectResponse =(com.buckwa.ws.chum.oxm.GetTeacherofSubjectResponse)teachTableWSTemplateChum.marshalSendAndReceive(getTeacherofSubject, new WebServiceMessageCallback() {
								public void doWithMessage(WebServiceMessage messageSubject) throws IOException, TransformerException {
									try {
										SOAPMessage soapMessageSubject = ((SaajSoapMessage)messageSubject).getSaajMessage();				 
								       ByteArrayOutputStream out = new ByteArrayOutputStream();
								     //  soapMessageSubject.writeTo(out);
							          // logger.info(" soapMessageSubject SOAP Request Payload: " + new String(out.toByteArray()));
								         
									} catch(Exception e) {
										e.printStackTrace();
									}
								}
							});	 
						 
						String isCoTeach = "N";
						//List<String> teacherIdList =getTeacherofSubjectResponse.getLect().getTeacherId();
						//logger.info(" ## Teacher List :"+teacherIdList);
						if(getTeacherofSubjectResponse.getLect()!=null){
							if(getTeacherofSubjectResponse.getLect().getTeacherId()!=null&&getTeacherofSubjectResponse.getLect().getTeacherId().size()>1){
								
								//logger.info("  ### Check Is Co Teach SubjectId:"+tmp.getSubjectId()+" Degree:"+degree+" Found Lect List Size: "+getTeacherofSubjectResponse.getLect().getTeacherId().size()+" So  -----> Y");
								isCoTeach="Y";
							}							
						}

						//List<String> teacherIdPracList =getTeacherofSubjectResponse.getLect().getTeacherId();
						//logger.info(" ## Teacher List :"+teacherIdList);
//						if(getTeacherofSubjectResponse.getPrac()!=null){
//							if(getTeacherofSubjectResponse.getPrac().getTeacherId()!=null&&getTeacherofSubjectResponse.getPrac().getTeacherId().size()>1){
//								isCoTeach="Y";
//								logger.info("  ### Check Is Co Teach SubjectId:"+tmp.getSubjectId()+" Degree:"+degree+"  Found Prac List Size: "+getTeacherofSubjectResponse.getPrac().getTeacherId().size()+" So  -----> Y");
//							}							
//						}	
//						
						
						
						
						final String finalIsCoTeach = isCoTeach;
						
						
						
						List<com.buckwa.ws.chum.oxm.Subject> subJectListReturnList  =returnObjSubject.getSubject();
						if(subJectListReturnList!=null&&subJectListReturnList.size()>0){
							
							if(subJectListReturnList.size()>1){
							//	logger.info(" ############################################ Incorrect Size:"+ subJectListReturnList.size());
							}
							final com.buckwa.ws.chum.oxm.Subject subject=subJectListReturnList.get(0);
							 
							
							boolean isInRejectList = checkInRejectList(subject.getSubjectId());
							
							String subjectRemark ="";
							if(isInRejectList){
								subjectRemark ="นำเข้าผลงานเอง";
							}
							
							int pracHr = 0;
							
							try{
								pracHr =Integer.parseInt(subject.getPracHr());
							}catch(Exception ex){
								ex.printStackTrace();
							}
							
							if(pracHr>=8){
								subjectRemark ="นำเข้าผลงานเอง";
								isInRejectList = true;
								
								logger.info(" "+finalTeachtable.getSubjectId()+" :"+subject.getSubjectEname()+"  PracHr > 8 -----------------> Eject");
							}
							
							final String finalRemark = subjectRemark;
							
					 
							
							finalTeachtable.setTeacherId(newRegId);
							
							logger.info(" "+finalTeachtable.getSubjectId()+" :"+subject.getSubjectEname()+" Co-Teach:"+finalIsCoTeach +" finalRemark:"+finalRemark+" isInRejectList:"+isInRejectList);
							
							//final int nexCode = generateCodeUtil.getNextDepartmentCode(academicYear);
							KeyHolder keyHolder = new GeneratedKeyHolder(); 
							jdbcTemplate.update(new PreparedStatementCreator() {  
								public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
									PreparedStatement ps = connection.prepareStatement("" +						
											"  insert into time_table ("
											+ "faculty_id,"
											+ "dept_id,"
											+ "curr_id,"
											+ "subject_id,"
											+ "semester,"
											+ "year,"
											+ "class,"
											+ "section,"
											+ "teacher_id,"
											+ "lect_or_prac,"
											+ "teach_day,"
											+ "teach_time,"
											+ "teach_time2,"
											+ "student_total,"
											+ "degree ,"
											+ "thai_name,"
											+ "eng_name,"
											+ "lect_hr,"
											+ "prac_hr,"
											+ "credit, from_source, is_co_teach,remark  )"
											+ "values (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" +
										 "", Statement.RETURN_GENERATED_KEYS);   
									ps.setString(1,finalTeachtable.getFacultyId());
									ps.setString(2,finalTeachtable.getDepartmentId()); 
									ps.setString(3, finalTeachtable.getCurrId());
									ps.setString(4, finalTeachtable.getSubjectId());
									ps.setString(5, finalTeachtable.getSemester());
									ps.setString(6, finalTeachtable.getYear());
									ps.setString(7, finalTeachtable.getClazz());
									ps.setString(8, finalTeachtable.getSection());
									ps.setString(9, finalTeachtable.getTeacherId());
									ps.setString(10, finalTeachtable.getLectOrPrac());
									ps.setString(11, finalTeachtable.getTeachDay());
									ps.setString(12, finalTeachtable.getTeachTime());
									ps.setString(13, finalTeachtable.getTeachTime2());
									ps.setString(14, finalTeachtable.getStudentTotal()+"");
									ps.setString(15, finalTeachtable.getDegree()+"");
									
									ps.setString(16, subject.getSubjectTname()+"");
									ps.setString(17, subject.getSubjectEname()+"");
									ps.setString(18, subject.getLectHr()+"");
									ps.setString(19, subject.getPracHr()+"");
									ps.setString(20, subject.getCredit()+"");
									ps.setString(21, SchoolConstants.FROM_SOUCE_REG_CHUM);
									ps.setString(22, finalIsCoTeach);
									ps.setString(23, finalRemark);
									
									return ps;  
									}
								}, 	keyHolder); 	
							Long returnid =  keyHolder.getKey().longValue();								
							
							jdbcTemplate.update("commit");
							
							
							//logger.info(" ############################### Start Create KPI User Mapping #########################");
							// Check Found Skip
				 
							
							if(isInRejectList){
								//key exists, skip
								logger.info(" ############################### Check Reject True , So skip #########################");
							}else{
								logger.info(" ############################### Check Reject False , So Create Work #########################");
								
								AcademicKPIUserMapping academicKPIUserMapping =  getMatchingKPITeachTableChum(tmp,newRegId,subject,finalIsCoTeach,academicYear,facultyCode);
								 
								
								academicKPIUserMapping.setIsCoTeach(finalIsCoTeach);
								academicKPIUserMapping.setFromSource(SchoolConstants.FROM_SOUCE_REG_CHUM);	
								 
								
								academicKPIUserMapping.setStatus("APPROVED");
								
								//academicKPIDao.importwork(academicKPIUserMapping);
								academicKPIDao.importworkTimeTable(semester,academicKPIUserMapping);								
								
							}
							 
							

							
						}
						
					//	 }// End debug Subject
				 }else{
					 logger.info(" ### SubjectId null , skip on Create ");
				 }
				}catch(Exception exx){
					exx.printStackTrace();
				}
				 }	 
			} 
		} 
		
		
	}
	
	private boolean checkInRejectList(String subjectId){
		boolean returnValue = false;
		
		//Map<String, String> fruits = new HashMap<>();
		
		Map<String, String> rejectMap =rejectSubjectUtil.getRejectMap();
		//fruits.put("01236071", "01236071");
		//fruits.put("01136006", "01136006");
		 
		logger.info(" ### SubjectId  For Check Reject "+subjectId);
		
		Set keySet =rejectMap.keySet();
		
		Iterator iter =keySet.iterator();
		
		//while(iter.hasNext()){
			//System.out.println(iter.next());
		//}
		if(rejectMap.containsKey(subjectId)){
			//key exists
			logger.info(" ### SubjectId "+subjectId+ " In Reject List , So Reject");
			returnValue =true;
		}else{
			
		}
		
		return returnValue;
	}
	
	
	@Override
	public void addShareSubject( TimeTableReport timeTableReport,String facultyCode ) {
		//logger.info(" #  : "+BeanUtils.getBeanString(departmentList));	
		
		 
			final TimeTableReport timeTableReportFinal = timeTableReport; 
			com.buckwa.ws.newws.oxm.TeachTable tmp = new com.buckwa.ws.newws.oxm.TeachTable();
			
			tmp.setDegree(new Integer(timeTableReport.getDegree()));
			tmp.setLectOrPrac(timeTableReport.getLecOrPrac());
			tmp.setYear(timeTableReport.getAcademicYear());
			tmp.setTeacherId( timeTableReport.getTeacherId());
			
			if(timeTableReport.getTotalStudent()==null||timeTableReport.getTotalStudent().equalsIgnoreCase("null")){
				tmp.setStudentTotal(new Integer(0));
			}else{
				tmp.setStudentTotal(new Integer(timeTableReport.getTotalStudent()));
			}
			
			
			
			if(timeTableReport.getTeacherId()==null||timeTableReport.getSubjectCode()==null||timeTableReport.getSemester()==null||timeTableReport.getLecOrPrac()==null){ 				
				 logger.info(" ############ Found Null  Skipp----------- : "  );
			}else{ 
			// Check aready exist
			final String academicYear = schoolUtil.getCurrentAcademicYear();
			boolean isExist = isTeachtableWSExistAddShare(timeTableReport,academicYear);
			
			if(!isExist){  
 					 if(timeTableReportFinal.getSubjectCode()!=null){
					 
					 GetSubject getSubjectRequest = new GetSubject();
					 getSubjectRequest.setSubjectId(timeTableReportFinal.getSubjectCode());
					 getSubjectRequest.setDegree(new Integer(timeTableReport.getDegree()));
					 getSubjectRequest.setDetail(true);
 					 
						GetSubjectResponse returnObjSubject =(GetSubjectResponse)subjectServiceWSTemplate.marshalSendAndReceive(getSubjectRequest, new WebServiceMessageCallback() {
							public void doWithMessage(WebServiceMessage messageSubject) throws IOException, TransformerException {
								try {
									SOAPMessage soapMessageSubject = ((SaajSoapMessage)messageSubject).getSaajMessage();				 
							       ByteArrayOutputStream out = new ByteArrayOutputStream();
							       soapMessageSubject.writeTo(out);
						           logger.info(" soapMessageSubject SOAP Request Payload: " + new String(out.toByteArray()));
							         
								} catch(Exception e) {
									e.printStackTrace();
								}
							}
						});	 
						
						List<Subject> subJectListReturnList  =returnObjSubject.getSubject();
						if(subJectListReturnList!=null&&subJectListReturnList.size()>0){
							
							if(subJectListReturnList.size()>1){
								logger.info(" ############################################ Incorrect Size:"+ subJectListReturnList.size());
							}
							final Subject subject=subJectListReturnList.get(0);
							
				 
							
							
							//final int nexCode = generateCodeUtil.getNextDepartmentCode(academicYear);
							KeyHolder keyHolder = new GeneratedKeyHolder(); 
							jdbcTemplate.update(new PreparedStatementCreator() {  
								public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
									PreparedStatement ps = connection.prepareStatement("" +						
											"  insert into time_table ("
											+ "faculty_id,"
											+ "dept_id,"
											+ "curr_id,"
											+ "subject_id,"
											+ "semester,"
											+ "year,"
											+ "class,"
											+ "section,"
											+ "teacher_id,"
											+ "lect_or_prac,"
											+ "teach_day,"
											+ "teach_time,"
											+ "teach_time2,"
											+ "student_total,"
											+ "degree ,"
											+ "thai_name,"
											+ "eng_name,"
											+ "lect_hr,"
											+ "prac_hr,"
											+ "credit )"
											+ "values (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" +
										 "", Statement.RETURN_GENERATED_KEYS);   
									ps.setString(1,timeTableReportFinal.getFacultyId());
									ps.setString(2,timeTableReportFinal.getDeptId()); 
									ps.setString(3, "");
									ps.setString(4, timeTableReportFinal.getSubjectCode());
									ps.setString(5, timeTableReportFinal.getSemester());
									ps.setString(6, timeTableReportFinal.getAcademicYear());
									ps.setString(7, "");
									ps.setString(8, timeTableReportFinal.getSecNo());
									ps.setString(9, timeTableReportFinal.getTeacherId());
									ps.setString(10, timeTableReportFinal.getLecOrPrac());
									ps.setString(11, timeTableReportFinal.getTeachDay());
									ps.setString(12, timeTableReportFinal.getTeachTime1());
									ps.setString(13, timeTableReportFinal.getTeachTime2());
									ps.setString(14, timeTableReportFinal.getTotalStudent());
									ps.setString(15, timeTableReportFinal.getDegree()+"");
									
									ps.setString(16, subject.getSubjectTname()+"");
									ps.setString(17, subject.getSubjectEname()+"");
									ps.setString(18, subject.getLectHr()+"");
									ps.setString(19, subject.getPracHr()+"");
									ps.setString(20, subject.getCredit()+"");
									return ps;  
									}
								}, 	keyHolder); 	
							Long returnid =  keyHolder.getKey().longValue();								
							
							jdbcTemplate.update("commit");
							
							
							logger.info(" ############################### Start Create KPI User Mapping #########################");
							
							AcademicKPIUserMapping academicKPIUserMapping =  getMatchingKPITeachTable(tmp,subject,"N",academicYear,facultyCode);
							
							academicKPIUserMapping.setStatus("CREATED");
							
							academicKPIDao.importwork(academicKPIUserMapping);
							
						}
						
					
				 }else{
					 logger.info(" ### SubjectId null , skip on Create ");
				 }
				 }	 
			 
		} 
		
		
		

		
	}
	
	private AcademicKPIUserMapping getMatchingKPI(com.buckwa.ws.newws.oxm.TeachTable timetable, Subject subject,final String finalIsCoTeach,String academicYear,String facultyCode){
		AcademicKPIUserMapping returnObj = new AcademicKPIUserMapping();;
		
		try{
			
			String specialP1 = timetable.getDegree()+"";
			String specialP2 = timetable.getLectOrPrac();			
			String totalStudent = "0";
			if(timetable.getStudentTotal()==null){
				
			}else {
				totalStudent = timetable.getStudentTotal()+"";
			}
			 
			logger.info(" ###### specialP1:"+specialP1+"   specialP2:"+specialP2+"   totalStudent:"+totalStudent);
			
			
//			String sqlKPI  = " select * from academic_kpi k where k.special_p1='"+specialP1+"' and k.special_p2='"+specialP2+"'"
//					+ " and   total_student_to >= "+totalStudent+" AND total_student_from <="+totalStudent+ "  and k.academic_year ='"+timetable.getYear()+"' and faculty_code="+facultyCode;


			
			String sqlKPI  = " select * from academic_kpi k where k.special_p1='"+specialP1+"' and k.special_p2='"+specialP2+"'"
					+ " and    k.academic_year ='"+timetable.getYear()+"' and faculty_code="+facultyCode;

			
			//String sql =" select *  from academic_kpi where academic_year ='"+getByAcademicYear+"'" ; 
			logger.info(" sql sqlKPI:"+sqlKPI);
			List<AcademicKPI> academicKPIList  =null;
			
			try{
				academicKPIList = this.jdbcTemplate.query(sqlKPI,	new AcademicKPIMapper() );	
				logger.info(" ########## Found Academic kpi list :"+academicKPIList);
				if(academicKPIList!=null&&academicKPIList.size()>0){
					
					AcademicKPI zeroKPI = academicKPIList.get(0);
					
					
					String userName =schoolUtil.getUserNameFromRegId(timetable.getTeacherId(),academicYear);
					
					
					returnObj.setAcademicKPICode(zeroKPI.getCode());
					returnObj.setAcademicYear(zeroKPI.getAcademicYear());
					returnObj.setUserName(userName);
					returnObj.setAcademicKPIId(zeroKPI.getAcademicKPIId());
					returnObj.setWorkTypeCode(zeroKPI.getWorkTypeCode());
					returnObj.setName(zeroKPI.getName());
					returnObj.setRatio(100);
					 
					
					//String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_code ="+zeroKPI.getCode()+" and academic_year='"+zeroKPI.getAcademicYear()+"'" ; 
			
					String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_id ="+zeroKPI.getAcademicKPIId()+" and academic_year='"+zeroKPI.getAcademicYear()+"'" ; 
					
					List<AcademicKPIAttribute> academicKPIAttributeList = new ArrayList();
					try{
						logger.info(" sqlAttribute:"+sqlAttribute);
						academicKPIAttributeList = this.jdbcTemplate.query(sqlAttribute,	new AcademicKPIAttributeMapper() );
						
						logger.info(" ########## Found academicKPIAttributeList size: :"+academicKPIAttributeList.size());
						
						List<AcademicKPIAttributeValue> academicKPIAttributeValueList = new ArrayList();
						
						 for(AcademicKPIAttribute attTmp:academicKPIAttributeList ){	
								logger.info(" ##### attTmp.getName():"+attTmp.getName());
							 
							 if("ชื่อวิชา".equalsIgnoreCase(attTmp.getName())){	
								 AcademicKPIAttributeValue valueTmp = new AcademicKPIAttributeValue();
								 attTmp.setValue(subject.getSubjectTname());

								
								 attTmp.setIsCalculate("Y");
								 valueTmp.setValue(subject.getSubjectId()+":"+subject.getSubjectTname());
								 if( subject.getSubjectTname()==null|| subject.getSubjectTname().length()==0){
									 valueTmp.setValue(subject.getSubjectId()+":"+subject.getSubjectEname());
								 }
								
								 valueTmp.setName(attTmp.getName());
								 
								 academicKPIAttributeValueList.add(valueTmp);							 
							 }
							 if("หน่วยกิต".equalsIgnoreCase(attTmp.getName())){		
								 AcademicKPIAttributeValue valueTmp = new AcademicKPIAttributeValue();
								 attTmp.setValue(subject.getCredit());
								 attTmp.setIsCalculate("N");
								 valueTmp.setValue(subject.getCredit());
								 valueTmp.setName(attTmp.getName());
								 
								 academicKPIAttributeValueList.add(valueTmp);
							 }
							 if("จำนวนชั่วโมง".equalsIgnoreCase(attTmp.getName())){		
								 AcademicKPIAttributeValue valueTmp = new AcademicKPIAttributeValue();
								 
								 if("ท".equalsIgnoreCase(timetable.getLectOrPrac())){
									 attTmp.setValue(subject.getLectHr());
									 attTmp.setIsCalculate("Y");
									 valueTmp.setValue(subject.getLectHr());
									 valueTmp.setName(attTmp.getName());
								 }else{
									 attTmp.setValue(subject.getPracHr());
									 attTmp.setIsCalculate("Y");
									 valueTmp.setValue(subject.getPracHr());
									 valueTmp.setName(attTmp.getName());								 
								 }
								 
								 academicKPIAttributeValueList.add(valueTmp);
							 }
							 
							 if("สัดส่วน(%)".equalsIgnoreCase(attTmp.getName())){	
								
								 
								 if("Y".equals(finalIsCoTeach)){
									 AcademicKPIAttributeValue valueTmp = new AcademicKPIAttributeValue();
									 
								     if(attTmp.getValue()==null){
								    	 valueTmp.setValue("100");
								     }else{
								    	 valueTmp.setValue("50");
								     }
									 
									 valueTmp.setName(attTmp.getName());
									 attTmp.setIsCalculate("Y");
									 academicKPIAttributeValueList.add(valueTmp);										 
								 }
						 
							 }							 
							 
							
						 }
						 
						 returnObj.setAcademicKPIAttributeValueList(academicKPIAttributeValueList);
						
						 logger.info("  academicKPIAttributeValueList size:"+academicKPIAttributeValueList);
					}catch (org.springframework.dao.EmptyResultDataAccessException ex){
						ex.printStackTrace();
					} 									 
					
					zeroKPI.setAcademicKPIAttributeList(academicKPIAttributeList);
					
					logger.info(" ########## KPI Zero:"+ BeanUtils.getBeanString(zeroKPI));
				}
				
			}catch (org.springframework.dao.EmptyResultDataAccessException ex){
				ex.printStackTrace();
			} 
			 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnObj;
		
	}
	
	private AcademicKPIUserMapping getMatchingKPITeachTable(com.buckwa.ws.newws.oxm.TeachTable timetable, Subject subject,final String finalIsCoTeach,String academicYear,String facultyCode){
		AcademicKPIUserMapping returnObj = new AcademicKPIUserMapping();;
		
		try{
			
			String specialP1 = timetable.getDegree()+"";
			String specialP2 = timetable.getLectOrPrac();			
			String totalStudent = "0";
			if(timetable.getStudentTotal()==null){
				
			}else {
				totalStudent = timetable.getStudentTotal()+"";
			}
			 
			//logger.info(" ###### specialP1:"+specialP1+"   specialP2:"+specialP2+"   totalStudent:"+totalStudent);
			
			
//			String sqlKPI  = " select * from academic_kpi k where k.special_p1='"+specialP1+"' and k.special_p2='"+specialP2+"'"
//					+ " and   total_student_to >= "+totalStudent+" AND total_student_from <="+totalStudent+ "  and k.academic_year ='"+timetable.getYear()+"' and faculty_code="+facultyCode;


			
			String sqlKPI  = " select * from academic_kpi k where k.special_p1='"+specialP1+"' and k.special_p2='"+specialP2+"'"
					+ " and    k.academic_year ='"+timetable.getYear()+"' and faculty_code="+facultyCode;

			
			//String sql =" select *  from academic_kpi where academic_year ='"+getByAcademicYear+"'" ; 
			logger.info(" sql sqlKPI:"+sqlKPI);
			List<AcademicKPI> academicKPIList  =null;
			
			try{
				academicKPIList = this.jdbcTemplate.query(sqlKPI,	new AcademicKPIMapper() );	
				//logger.info(" ########## Found Academic kpi list :"+academicKPIList);
				if(academicKPIList!=null&&academicKPIList.size()>0){
		 
					// Add new for should kpi that use total student calculate
					 
					AcademicKPI zeroKPI  = academicKPIList.get(0);
				 
						for(AcademicKPI kpiTmp:academicKPIList){							
							String isUseStudentCalculate =kpiTmp.getSpecialP4();
							int studentTotalFromReg =timetable.getStudentTotal();
							
							//System.out.println(" TotalStudent From :"+kpiTmp.getTotalStudentFrom()+" totalStudentTo:"+kpiTmp.getTotalStudentTo()+"  isUseStudentCalculate:"+isUseStudentCalculate+"  studentTotalFromReg:"+studentTotalFromReg);
							
							if(kpiTmp.getTotalStudentFrom()!=null&&kpiTmp.getTotalStudentTo()!=null){
								
								try{
									
									int totalStudentFromInt  = Integer.parseInt(kpiTmp.getTotalStudentFrom());
									int totalStudentToInt  = Integer.parseInt(kpiTmp.getTotalStudentTo());
									
									
									
									if(isUseStudentCalculate!=null&&isUseStudentCalculate.indexOf("1")!=-1){
										if(studentTotalFromReg>=totalStudentFromInt&&studentTotalFromReg<=totalStudentToInt){
											zeroKPI = kpiTmp;
											break;
										}
									}
									
								}catch(Exception ex){
									//ex.printStackTrace();
								}
								
							}
 
						}						
					 
					
					
					
					
					
					
					String userName =schoolUtil.getUserNameFromRegId(timetable.getTeacherId(),academicYear);
					
					
					returnObj.setAcademicKPICode(zeroKPI.getCode());
					returnObj.setAcademicYear(zeroKPI.getAcademicYear());
					returnObj.setUserName(userName);
					returnObj.setAcademicKPIId(zeroKPI.getAcademicKPIId());
					returnObj.setWorkTypeCode(zeroKPI.getWorkTypeCode());
					returnObj.setName(zeroKPI.getName());
					returnObj.setRatio(100);
					 
					
					//String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_code ="+zeroKPI.getCode()+" and academic_year='"+zeroKPI.getAcademicYear()+"'" ; 
			
					String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_id ="+zeroKPI.getAcademicKPIId()+" and academic_year='"+zeroKPI.getAcademicYear()+"'" ; 
					
					List<AcademicKPIAttribute> academicKPIAttributeList = new ArrayList();
					try{
						logger.info(" sqlAttribute:"+sqlAttribute);
						academicKPIAttributeList = this.jdbcTemplate.query(sqlAttribute,	new AcademicKPIAttributeMapper() );
						
						//logger.info(" ########## Found academicKPIAttributeList size: :"+academicKPIAttributeList.size());
						
						List<AcademicKPIAttributeValue> academicKPIAttributeValueList = new ArrayList();
						
						 for(AcademicKPIAttribute attTmp:academicKPIAttributeList ){	
							//	logger.info(" ##### attTmp.getName():"+attTmp.getName());
							 
							 if("ชื่อวิชา".equalsIgnoreCase(attTmp.getName())){	
								 AcademicKPIAttributeValue valueTmp = new AcademicKPIAttributeValue();
								 attTmp.setValue(subject.getSubjectTname());

								
								 attTmp.setIsCalculate("Y");
								 valueTmp.setValue(subject.getSubjectId()+":"+subject.getSubjectEname());
								 if( subject.getSubjectTname()==null|| subject.getSubjectTname().length()==0){
									 valueTmp.setValue(subject.getSubjectId()+":"+subject.getSubjectEname());
								 }
								
								 valueTmp.setName(attTmp.getName());
								 
								 academicKPIAttributeValueList.add(valueTmp);							 
							 }
							 if("หน่วยกิต".equalsIgnoreCase(attTmp.getName())){		
								 AcademicKPIAttributeValue valueTmp = new AcademicKPIAttributeValue();
								 attTmp.setValue(subject.getCredit());
								 attTmp.setIsCalculate("N");
								 valueTmp.setValue(subject.getCredit());
								 valueTmp.setName(attTmp.getName());
								 
								 academicKPIAttributeValueList.add(valueTmp);
							 }
							 if("จำนวนชั่วโมง".equalsIgnoreCase(attTmp.getName())){		
								 AcademicKPIAttributeValue valueTmp = new AcademicKPIAttributeValue();
								 
								 if("ท".equalsIgnoreCase(timetable.getLectOrPrac())){
									 attTmp.setValue(subject.getLectHr());
									 attTmp.setIsCalculate("Y");
									 valueTmp.setValue(subject.getLectHr());
									 valueTmp.setName(attTmp.getName());
								 }else{
									 attTmp.setValue(subject.getPracHr());
									 attTmp.setIsCalculate("Y");
									 valueTmp.setValue(subject.getPracHr());
									 valueTmp.setName(attTmp.getName());								 
								 }
								 
								 academicKPIAttributeValueList.add(valueTmp);
							 }
							 
							 if("สัดส่วน(%)".equalsIgnoreCase(attTmp.getName())){	
								
								 
								 if("Y".equals(finalIsCoTeach)){
									 AcademicKPIAttributeValue valueTmp = new AcademicKPIAttributeValue();
									 
								     if(attTmp.getValue()==null){
								    	 valueTmp.setValue("100");
								     }else{
								    	 valueTmp.setValue("50");
								     }
									 
									 valueTmp.setName(attTmp.getName());
									 attTmp.setIsCalculate("Y");
									 academicKPIAttributeValueList.add(valueTmp);										 
								 }
						 
							 }							 
							 
							
						 }
						 
						 returnObj.setAcademicKPIAttributeValueList(academicKPIAttributeValueList);
						
						// logger.info("  academicKPIAttributeValueList size:"+academicKPIAttributeValueList);
					}catch (org.springframework.dao.EmptyResultDataAccessException ex){
						ex.printStackTrace();
					} 									 
					
					zeroKPI.setAcademicKPIAttributeList(academicKPIAttributeList);
					
					//logger.info(" ########## KPI Zero:"+ BeanUtils.getBeanString(zeroKPI));
				}
				
			}catch (org.springframework.dao.EmptyResultDataAccessException ex){
				ex.printStackTrace();
			} 
			 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnObj;
		
	}
	
	private AcademicKPIUserMapping getMatchingKPITeachTableChum(com.buckwa.ws.chum.oxm.TeachTable timetable,String teacherId, com.buckwa.ws.chum.oxm.Subject subject,final String finalIsCoTeach,String academicYear,String facultyCode){
		AcademicKPIUserMapping returnObj = new AcademicKPIUserMapping();;
		
		try{
			
			String specialP1 = timetable.getDegree()+"";
			String specialP2 = timetable.getLectOrPrac();			
			String totalStudent = "0";
			if(timetable.getStudentTotal()==null){
				
			}else {
				totalStudent = timetable.getStudentTotal()+"";
			}
			 
			//logger.info(" ###### specialP1:"+specialP1+"   specialP2:"+specialP2+"   totalStudent:"+totalStudent);
			
			
//			String sqlKPI  = " select * from academic_kpi k where k.special_p1='"+specialP1+"' and k.special_p2='"+specialP2+"'"
//					+ " and   total_student_to >= "+totalStudent+" AND total_student_from <="+totalStudent+ "  and k.academic_year ='"+timetable.getYear()+"' and faculty_code="+facultyCode;


			
			String sqlKPI  = " select * from academic_kpi k where k.special_p1='"+specialP1+"' and k.special_p2='"+specialP2+"'"
					+ " and    k.academic_year ='"+timetable.getYear()+"' and faculty_code="+facultyCode;

			
			//String sql =" select *  from academic_kpi where academic_year ='"+getByAcademicYear+"'" ; 
			//logger.info(" sql sqlKPI:"+sqlKPI);
			List<AcademicKPI> academicKPIList  =null;
			
			try{
				academicKPIList = this.jdbcTemplate.query(sqlKPI,	new AcademicKPIMapper() );	
				//logger.info(" ########## Found Academic kpi list :"+academicKPIList);
				if(academicKPIList!=null&&academicKPIList.size()>0){
		 
					// Add new for should kpi that use total student calculate
					 
					AcademicKPI zeroKPI  = academicKPIList.get(0);
				 
						for(AcademicKPI kpiTmp:academicKPIList){							
							String isUseStudentCalculate =kpiTmp.getSpecialP4();
							int studentTotalFromReg =timetable.getStudentTotal();
							
							//System.out.println(" TotalStudent From :"+kpiTmp.getTotalStudentFrom()+" totalStudentTo:"+kpiTmp.getTotalStudentTo()+"  isUseStudentCalculate:"+isUseStudentCalculate+"  studentTotalFromReg:"+studentTotalFromReg);
							
							if(kpiTmp.getTotalStudentFrom()!=null&&kpiTmp.getTotalStudentTo()!=null){
								
								try{
									
									int totalStudentFromInt  = Integer.parseInt(kpiTmp.getTotalStudentFrom());
									int totalStudentToInt  = Integer.parseInt(kpiTmp.getTotalStudentTo());
									
									
									
									if(isUseStudentCalculate!=null&&isUseStudentCalculate.indexOf("1")!=-1){
										if(studentTotalFromReg>=totalStudentFromInt&&studentTotalFromReg<=totalStudentToInt){
											zeroKPI = kpiTmp;
											break;
										}
									}
									
								}catch(Exception ex){
									//ex.printStackTrace();
								}
								
							}
 
						}						
					 
					
					
					
					
					
					
					String userName =schoolUtil.getUserNameFromRegId(teacherId,academicYear);
					
					
					returnObj.setAcademicKPICode(zeroKPI.getCode());
					returnObj.setAcademicYear(zeroKPI.getAcademicYear());
					returnObj.setUserName(userName);
					returnObj.setAcademicKPIId(zeroKPI.getAcademicKPIId());
					returnObj.setWorkTypeCode(zeroKPI.getWorkTypeCode());
					returnObj.setName(zeroKPI.getName());
					returnObj.setRatio(100);
					 
					
					//String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_code ="+zeroKPI.getCode()+" and academic_year='"+zeroKPI.getAcademicYear()+"'" ; 
			
					String sqlAttribute  =" select *  from academic_kpi_attribute  where academic_kpi_id ="+zeroKPI.getAcademicKPIId()+" and academic_year='"+zeroKPI.getAcademicYear()+"'" ; 
					
					List<AcademicKPIAttribute> academicKPIAttributeList = new ArrayList();
					try{
						//logger.info(" sqlAttribute:"+sqlAttribute);
						academicKPIAttributeList = this.jdbcTemplate.query(sqlAttribute,	new AcademicKPIAttributeMapper() );
						
						//logger.info(" ########## Found academicKPIAttributeList size: :"+academicKPIAttributeList.size());
						
						List<AcademicKPIAttributeValue> academicKPIAttributeValueList = new ArrayList();
						
						 for(AcademicKPIAttribute attTmp:academicKPIAttributeList ){	
							//	logger.info(" ##### attTmp.getName():"+attTmp.getName());
							 
							 if("ชื่อวิชา".equalsIgnoreCase(attTmp.getName())){	
								 AcademicKPIAttributeValue valueTmp = new AcademicKPIAttributeValue();
								 attTmp.setValue(subject.getSubjectTname());

								
								 attTmp.setIsCalculate("Y");
								 valueTmp.setValue(subject.getSubjectId()+":"+subject.getSubjectEname());
								 if( subject.getSubjectTname()==null|| subject.getSubjectTname().length()==0){
									 valueTmp.setValue(subject.getSubjectId()+":"+subject.getSubjectEname());
								 }
								
								 valueTmp.setName(attTmp.getName());
								 
								 academicKPIAttributeValueList.add(valueTmp);							 
							 }
							 if("หน่วยกิต".equalsIgnoreCase(attTmp.getName())){		
								 AcademicKPIAttributeValue valueTmp = new AcademicKPIAttributeValue();
								 attTmp.setValue(subject.getCredit());
								 attTmp.setIsCalculate("N");
								 valueTmp.setValue(subject.getCredit());
								 valueTmp.setName(attTmp.getName());
								 
								 academicKPIAttributeValueList.add(valueTmp);
							 }
							 if("จำนวนชั่วโมง".equalsIgnoreCase(attTmp.getName())){		
								 AcademicKPIAttributeValue valueTmp = new AcademicKPIAttributeValue();
								 
								 if("ท".equalsIgnoreCase(timetable.getLectOrPrac())){
									 attTmp.setValue(subject.getLectHr());
									 attTmp.setIsCalculate("Y");
									 valueTmp.setValue(subject.getLectHr());
									 valueTmp.setName(attTmp.getName());
								 }else{
									 attTmp.setValue(subject.getPracHr());
									 attTmp.setIsCalculate("Y");
									 valueTmp.setValue(subject.getPracHr());
									 valueTmp.setName(attTmp.getName());								 
								 }
								 
								 academicKPIAttributeValueList.add(valueTmp);
							 }
							 
							 if("สัดส่วน(%)".equalsIgnoreCase(attTmp.getName())){	
								
								 
								 if("Y".equals(finalIsCoTeach)){
									 AcademicKPIAttributeValue valueTmp = new AcademicKPIAttributeValue();
									 
								     if(attTmp.getValue()==null){
								    	 valueTmp.setValue("100");
								     }else{
								    	 valueTmp.setValue("50");
								     }
									 
									 valueTmp.setName(attTmp.getName());
									 attTmp.setIsCalculate("Y");
									 academicKPIAttributeValueList.add(valueTmp);										 
								 }
						 
							 }							 
							 
							
						 }
						 
						 returnObj.setAcademicKPIAttributeValueList(academicKPIAttributeValueList);
						
						// logger.info("  academicKPIAttributeValueList size:"+academicKPIAttributeValueList);
					}catch (org.springframework.dao.EmptyResultDataAccessException ex){
						ex.printStackTrace();
					} 									 
					
					zeroKPI.setAcademicKPIAttributeList(academicKPIAttributeList);
					
					//logger.info(" ########## KPI Zero:"+ BeanUtils.getBeanString(zeroKPI));
				}
				
			}catch (org.springframework.dao.EmptyResultDataAccessException ex){
				ex.printStackTrace();
			} 
			 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnObj;
		
	}
	
 
	
	private boolean isDepartmentWSExist (com.buckwa.ws.newws.oxm.Department departmentws,String academicYear){
		boolean returnValue = false;
		try{
			
			String sqltmp = "select count(*) as totalItem  from department t  where t.code='"+StringEscapeUtils.escapeSql(departmentws.getDepartmentId())+"' and faculty_code='"+departmentws.getFacultyId()+"' and academic_year="+academicYear;
			//logger.info(" # sqltmp : "+sqltmp );	
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			//	logger.info(" Found Departement :"+departmentws.getDepartmentName()+"   , Do nothing !!");
			}else{
			//	logger.info(" Not Found Department :"+departmentws.getDepartmentName()+"   , Create New !!");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
	}
	
	private boolean isDepartmentWSExistChum (com.buckwa.ws.chum.oxm.Department departmentws,String academicYear){
		boolean returnValue = false;
		try{
			
			String sqltmp = "select count(*) as totalItem  from department t  where t.code='"+StringEscapeUtils.escapeSql(departmentws.getDepartmentId())+"' and faculty_code='"+departmentws.getFacultyId()+"' and academic_year="+academicYear;
			//logger.info(" # sqltmp : "+sqltmp );	
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			//	logger.info(" Found Departement :"+departmentws.getDepartmentName()+"   , Do nothing !!");
			}else{
			//	logger.info(" Not Found Department :"+departmentws.getDepartmentName()+"   , Create New !!");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
	}

	
	private boolean isTeachtableWSExist (com.buckwa.ws.newws.oxm.TeachTable teachtablews,String academicYear){
		boolean returnValue = false;
		try{
			
			if(teachtablews.getTeacherId()==null||teachtablews.getSubjectId()==null||teachtablews.getSemester()==null||teachtablews.getLectOrPrac()==null){
				returnValue=true;
				
				// logger.info(" ############ Found Null  isTeachtableWSExist : "+BeanUtils.getBeanString(teachtablews) );
			}else{
			
			String sqltmp = "select count(*) as totalItem  from time_table t  where  1=1 "
					//+ "t.faculty_id='"+StringEscapeUtils.escapeSql(teachtablews.getDepartmentId())+"'"
					//+ " and t.dept_id='"+teachtablews.getDepartmentId()+"' "
					//+ " and t.curr_id='"+teachtablews.getCurrId()+"' "
					+ " and t.teacher_id='"+teachtablews.getTeacherId()+"' "
					+ " and t.subject_id='"+teachtablews.getSubjectId()+"' "
					+ " and t.semester='"+teachtablews.getSemester()+"' "
					+ " and t.year='"+teachtablews.getYear()+"' "
					+ " and t.section='"+teachtablews.getSection()+"' " 
					+ " and t.lect_or_prac='"+teachtablews.getLectOrPrac()+"' ";
					
					//+ " and t.teach_time='"+teachtablews.getTeachTime()+"' "
					//+ " and t.teach_time2='"+teachtablews.getTeachTime2()+"' ";
					 
			
			
			logger.info(" # sqltmp Check Dup : "+sqltmp );	
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			//logger.info(" ##### found lond:"+found);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
				 
				//logger.info(" #  Found Dup Do noting !!! " );
			}else{
				
				//logger.info(" #  Not Found Dup Insert New !!! " );
			}
			
			}
			
		}catch(Exception ex){
			//logger.info(" #  Check Dup Exception Do noting !!! " );
			returnValue =true;
			ex.printStackTrace();
		}
		
		return returnValue;
	}
	private boolean isTeachtableWSExistChum (com.buckwa.ws.chum.oxm.TeachTable teachtablews,String academicYear,String newRegId){
		boolean returnValue = false;
		try{
			
			if(teachtablews.getTeacherId()==null||teachtablews.getSubjectId()==null||teachtablews.getSemester()==null||teachtablews.getLectOrPrac()==null){
				returnValue=true;
				
				// logger.info(" ############ Found Null  isTeachtableWSExist : "+BeanUtils.getBeanString(teachtablews) );
			}else{
			
			String sqltmp = "select count(*) as totalItem  from time_table t  where  1=1 "
					//+ "t.faculty_id='"+StringEscapeUtils.escapeSql(teachtablews.getDepartmentId())+"'"
					//+ " and t.dept_id='"+teachtablews.getDepartmentId()+"' "
					//+ " and t.curr_id='"+teachtablews.getCurrId()+"' "
					+ " and t.teacher_id='"+newRegId+"' "
					+ " and t.subject_id='"+teachtablews.getSubjectId()+"' "
					+ " and t.semester='"+teachtablews.getSemester()+"' "
					+ " and t.year='"+teachtablews.getYear()+"' "
					+ " and t.section='"+teachtablews.getSection()+"' " 
					+ " and t.lect_or_prac='"+teachtablews.getLectOrPrac()+"' ";
					
					//+ " and t.teach_time='"+teachtablews.getTeachTime()+"' "
					//+ " and t.teach_time2='"+teachtablews.getTeachTime2()+"' ";
					 
			
			
			logger.info(" # sqltmp Check Dup : "+sqltmp );	
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			//logger.info(" ##### found lond:"+found);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
				 
				logger.info(" #  Found Dup Do noting !!! " );
			}else{
				
				logger.info(" #  Not Found Dup Insert New !!! " );
			}
			
			}
			
		}catch(Exception ex){
			//logger.info(" #  Check Dup Exception Do noting !!! " );
			returnValue =true;
			ex.printStackTrace();
		}
		
		return returnValue;
	}

	private boolean isTeachtableWSExistAddShare (TimeTableReport timetableReport,String academicYear){
		boolean returnValue = false;
		try{
			
			if(timetableReport.getTeacherId()==null||timetableReport.getSubjectCode()==null||timetableReport.getSemester()==null||timetableReport.getLecOrPrac()==null){
				returnValue=true;
				
				 logger.info(" ############ Found Null  timetableReport : "+BeanUtils.getBeanString(timetableReport) );
			}else{
			
			String sqltmp = "select count(*) as totalItem  from time_table t  where  1=1 "
					//+ "t.faculty_id='"+StringEscapeUtils.escapeSql(teachtablews.getDepartmentId())+"'"
					//+ " and t.dept_id='"+teachtablews.getDepartmentId()+"' "
					//+ " and t.curr_id='"+teachtablews.getCurrId()+"' "
					+ " and t.teacher_id='"+timetableReport.getTeacherId()+"' "
					+ " and t.subject_id='"+timetableReport.getSubjectCode()+"' "
					+ " and t.semester='"+timetableReport.getSemester()+"' "
					+ " and t.year='"+timetableReport.getAcademicYear()+"' "
					+ " and t.section='"+timetableReport.getSecNo()+"' " 
					+ " and t.teacher_id='"+timetableReport.getTeacherId()+"' " 
					+ " and t.lect_or_prac='"+timetableReport.getLecOrPrac()+"' ";
					
	 
			
			
			logger.info(" # sqltmp : "+sqltmp );	
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			logger.info(" ##### found lond:"+found);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
				 
				logger.info(" #  Found Do noting !!! " );
			}else{
				
				logger.info(" #  Not Found Insert New !!! " );
			}
			
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnValue;
	}
	
	
	
	@Override
	public void deleteTimeTableAndMapping(String academicYear) {
		// TODO Auto-generated method stub
		this.jdbcTemplate.update("delete  from academic_kpi_user_mapping where from_source ='REG' and  academic_year="+academicYear); 
		this.jdbcTemplate.update("delete  from academic_kpi_attribute_value where from_source ='REG' and  academic_year="+academicYear); 
		this.jdbcTemplate.update("delete  from time_table where from_source ='REG' and  year="+academicYear); 
		
	}
	
	@Override
	public void deleteTimeTableAndMappingRegId(String academicYear,String teacherId,String userName) {
		// TODO Auto-generated method stub
		
		String sql1 ="delete  from academic_kpi_user_mapping where from_source ='REG' and  academic_year="+academicYear+" and user_name='"+userName+"'";
		String sql2 ="delete  from time_table where from_source ='REG' and  year="+academicYear+" and teacher_id="+teacherId;
		
		System.out.println(" sql1:"+sql1);
		System.out.println(" sql2:"+sql2);
		
		this.jdbcTemplate.update(sql1); 
		//this.jdbcTemplate.update("delete  from academic_kpi_attribute_value where from_source ='REG' and  academic_year="+academicYear); 
		this.jdbcTemplate.update(sql2); 
		
	}
	
	
	@Override
	public void deleteTimeTableAndMappingChum(String academicYear) {
		// TODO Auto-generated method stub
		this.jdbcTemplate.update("delete  from academic_kpi_user_mapping where from_source ='REG_CHUM' and  academic_year="+academicYear); 
		this.jdbcTemplate.update("delete  from academic_kpi_attribute_value where from_source ='REG_CHUM' and  academic_year="+academicYear); 
		this.jdbcTemplate.update("delete  from time_table where from_source ='REG_CHUM' and  year="+academicYear); 
		
	}

	@Override
	public void deleteFacultyById(String facultyId) {
		// TODO Auto-generated method stub
		this.jdbcTemplate.update("delete  from faculty where  faculty_id="+facultyId); 
		this.jdbcTemplate.update("delete  from department where faculty_id="+facultyId);  
	}
	
	@Override
	public void deleteDepartmentById(String id) {
		// TODO Auto-generated method stub
		this.jdbcTemplate.update("delete  from department where  department_id="+id); 
		 
	}

 
	@Override
	public Department  getDepartmentById(String departmentId) {	 
		String sqlDepartment =" select *  from department where department_id =" +departmentId;				
		Department  department   = this.jdbcTemplate.queryForObject(sqlDepartment,	new DepartmentMapper() ); 		 
		return department;
	}
	
	
	@Override
	public Department  getDepartmentByUserName(String username,String academicYear) {	 
		 	
		String sqlDepartment =" select d.* from department d "+
		" inner join person_pbp p on (d.name=p.department_desc) "+
		" where p.email='"+username+"' and d.academic_year="+academicYear +" and p.academic_year="+academicYear;		
		
		logger.info(" sqlDepartment:"+sqlDepartment);
		Department  department   = this.jdbcTemplate.queryForObject(sqlDepartment,	new DepartmentMapper() ); 		 
		return department;
	}
	
	@Override
	public Department  getDepartmentByHeadUserName(String username,String academicYear) {	 
		 	
		String sqlDepartment =" select d.* from department d "+
		" inner join person_pbp p on (d.name=p.head_department) "+
		" where p.email='"+username+"' and d.academic_year="+academicYear +" and p.academic_year="+academicYear;		
		
		logger.info(" sqlDepartment:"+sqlDepartment);
		Department  department   = this.jdbcTemplate.queryForObject(sqlDepartment,	new DepartmentMapper() ); 		 
		return department;
	}
	
	
	
	@Override
	public Faculty  getFacultyByUserNameandYear(String username,String academicYear) {	 
		 	
		String facSQL =" select d.* from faculty d "+
		" inner join person_pbp p on (d.name=p.faculty_desc) "+
		" where p.email='"+username+"' and p.academic_year="+academicYear + "  and d.academic_year="+academicYear;		
		logger.info(" facSQL:"+facSQL);
		Faculty  faculty   = this.jdbcTemplate.queryForObject(facSQL,	new FacultyMapper() ); 	
		
		if(faculty!=null){
			
			String sqlDepartment =" select d.* from department d where  d.academic_year="+academicYear+"  and faculty_code="+faculty.getCode();	
			
			
					List<Department>  departmentList   = this.jdbcTemplate.query(sqlDepartment,	new DepartmentMapper() ); 	
					faculty.setDepartmentList(departmentList);;
		}
		
		return faculty;
	}
	@Override
	public Faculty  getFacultyByDeanUserNameandYear(String username,String academicYear) {	 
		 	
		String facSQL =" select d.* from faculty d "+
		" inner join person_pbp p on (d.name=p.dean_faculty) "+
		" where p.email='"+username+"' and p.academic_year="+academicYear + "  and d.academic_year="+academicYear;		
		logger.info(" facSQL:"+facSQL);
		Faculty  faculty   = this.jdbcTemplate.queryForObject(facSQL,	new FacultyMapper() ); 	
		
		if(faculty!=null){
			
			String sqlDepartment =" select d.* from department d where  d.academic_year="+academicYear+"  and faculty_code="+faculty.getCode();	
			
			
					List<Department>  departmentList   = this.jdbcTemplate.query(sqlDepartment,	new DepartmentMapper() ); 	
					faculty.setDepartmentList(departmentList);;
		}
		
		return faculty;
	}
	
	
	

	@Override
	public void updateDepartment(Department department)  {
		logger.info(" # updateDepartment : "+BeanUtils.getBeanString(department));		
		final Department finaldepartment = department; 
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  update department set name=? where department_id=? " +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finaldepartment.getName());
				ps.setLong(2,finaldepartment.getDepartmentId()); 
			 ;
				return ps;  
				}
			}, 	keyHolder); 	
 
		 
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
			domain.setMultiplyValue(rs.getString("rule_code"));
			domain.setOrderNo(rs.getString("order_no"));
			
			domain.setSpecialP1(rs.getString("special_p1"));
			domain.setSpecialP2(rs.getString("special_p2"));
			domain.setSpecialP3(rs.getString("special_p3"));
			domain.setSpecialP4(rs.getString("special_p4"));
			domain.setTotalStudentFrom(rs.getString("total_student_from"));
			domain.setTotalStudentTo(rs.getString("total_student_to"));
			domain.setFacultyCode(rs.getString("faculty_code"));
			domain.setFromRegis(rs.getString("from_reg"));
			//logger.info(" ###### Multiply Value:"+rs.getString("rule_code"));
		 
			
			try{
			domain.setUnitDesc(schoolUtil.getUnitDescMyCode(rs.getString("unit_code"), rs.getString("academic_year")));
		}catch(org.springframework.dao.EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}
		 
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
}
