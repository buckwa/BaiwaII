package com.buckwa.dao.impl.pbp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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

import com.buckwa.dao.intf.pbp.AcademicKPIDao;
import com.buckwa.dao.intf.pbp.AcademicYearDao;
import com.buckwa.dao.intf.pbp.FacultyDao;
import com.buckwa.domain.pam.Semester;
import com.buckwa.domain.pbp.AcademicKPI;
import com.buckwa.domain.pbp.AcademicKPIAttribute;
import com.buckwa.domain.pbp.AcademicKPIWrapper;
import com.buckwa.domain.pbp.AcademicPerson;
import com.buckwa.domain.pbp.AcademicYear;
import com.buckwa.domain.pbp.AcademicYearEvaluateRound;
import com.buckwa.domain.pbp.AcademicYearWrapper;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.FacultyWrapper;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.web.util.AcademicYearUtil;

@Repository("academicYearDao")
public class AcademicYearDaoImpl implements AcademicYearDao {
	private static Logger logger = LoggerFactory.getLogger(AcademicYearDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private FacultyDao facultyDao;
	
	@Autowired
	private AcademicKPIDao academicKPIDao;
	
	@Autowired
	private AcademicYearUtil academicYearUtil;
 
	@Override
	public AcademicYearWrapper getCurrentAcademicYear( ) {		
		
		String yearThai = academicYearUtil.getSystemYearThai();
		logger.info(" yearThai:"+yearThai);
		String sql =" select *  from academic_year where year_status ='Y'" ; 
		logger.info(" sql:"+sql);
		AcademicYear academicYear = this.jdbcTemplate.queryForObject(sql,	new AcademicYearMapper() );				
		AcademicYearWrapper academicYearWrapper = new AcademicYearWrapper();
		academicYearWrapper.setAcademicYear(academicYear);
		
		long timeEnd = academicYear.getEndDate().getTime();
		long systemTime = System.currentTimeMillis();
		
		if(timeEnd>systemTime){
			academicYear.setCanEdit("N");
		}else{
			academicYear.setCanEdit("Y");
		}
	
		
		String currentAcademicYearStr = academicYear.getName();
		int nextYear = Integer.parseInt(currentAcademicYearStr)+1;
		int preYear = Integer.parseInt(currentAcademicYearStr)-1;
		academicYearWrapper.setNextAcademicYear(nextYear+"");
		academicYearWrapper.setPreviousAcademicYear(preYear+"");
		
		if(academicYear!=null){
			
			String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+currentAcademicYearStr+"'"   ;  
			logger.info(" sqlRound:"+sqlRound);
			List<AcademicYearEvaluateRound> academicYearEvaluateRoundList  = this.jdbcTemplate.query(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
			
			for(AcademicYearEvaluateRound roundTmp: academicYearEvaluateRoundList){
				long round1EndTime = roundTmp.getRound1EndDate().getTime();
				 systemTime = System.currentTimeMillis();
				
				if(round1EndTime>systemTime){
					roundTmp.setCanEditRound1("N");
				}else{
					roundTmp.setCanEditRound1("Y");
				}
				
				if(roundTmp.getRound2EndDate()!=null){
					roundTmp.setCanEditRound2("N");
					long round2EndTime = roundTmp.getRound2EndDate().getTime();
					if(round2EndTime<systemTime){
						roundTmp.setCanEditRound2("Y");
					}else{
						roundTmp.setCanEditRound2("N");
					}
					
				}
			}
			academicYearWrapper.setAcademicYearEvaluateRoundList(academicYearEvaluateRoundList);
		}
		 
		return academicYearWrapper;
	}
	
	@Override
	public String getCurrentEvalulateRoundStr( String userName,String academicYear) {		
		
		String returnStr ="";
		
		
		
		String sqlacademicPerson = "  select * from person_pbp   where email ='"+userName+"'  and  academic_year='"+academicYear+"'";		 
		AcademicPerson academicPerson  = this.jdbcTemplate.queryForObject(sqlacademicPerson,	new AcademicPersonMapper() ); 
		
		String employeeType = academicPerson.getEmployeeTypeNo();
		
		String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+academicYear+"' and evaluate_type='"+employeeType+"'"   ;  
	//	logger.info(" sqlRound:"+sqlRound);
		 AcademicYearEvaluateRound  academicYearEvaluateRound   = this.jdbcTemplate.queryForObject(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
		
		// logger.info(" academicYearEvaluateRound:"+BeanUtils.getBeanString(academicYearEvaluateRound));
		
		 long startTime =0l;
		 long endTime =0l;
		 
		 Timestamp startTimeStamp = null;
		 Timestamp endTimeStamp = null;
		 String round ="1";
		 if(employeeType.equalsIgnoreCase("1")){
			 
			 long round1EndLong = academicYearEvaluateRound.getRound1EndDate().getTime();
			 
			 if(round1EndLong-System.currentTimeMillis()>0){  
				 
				 returnStr="ข้าราชการ รอบ 1: "+academicYearEvaluateRound.getRound1StartDateShortThaiStr()+" - "+academicYearEvaluateRound.getRound1EndDateShortThaiStr();
			 }else{
				 returnStr="ข้าราชการ รอบ 2: "+academicYearEvaluateRound.getRound2StartDateShortThaiStr()+" - "+academicYearEvaluateRound.getRound2EndDateShortThaiStr();
			 }
			 

			 
		 }else{
			 returnStr="พนักงาน รอบ:  "+academicYearEvaluateRound.getRound1StartDateShortThaiStr()+" - "+academicYearEvaluateRound.getRound1EndDateShortThaiStr();

			 
		 }
				 
 
		return returnStr;
	}
	


	@Override
	public AcademicYearWrapper getFullAcademicYear( ) {		
		
		String yearThai = academicYearUtil.getSystemYearThai();
		//logger.info(" yearThai:"+yearThai);
		String sql =" select *  from academic_year where year_status ='Y'" ; 
		//logger.info(" sql:"+sql);
		AcademicYear academicYear = this.jdbcTemplate.queryForObject(sql,	new AcademicYearMapper() );				
		AcademicYearWrapper academicYearWrapper = new AcademicYearWrapper();
		academicYearWrapper.setAcademicYear(academicYear);
		
		long timeEnd = academicYear.getEndDate().getTime();
		long systemTime = System.currentTimeMillis();
		
		if(timeEnd>systemTime){
			academicYear.setCanEdit("N");
		}else{
			academicYear.setCanEdit("Y");
		}
	
		
		String currentAcademicYearStr = academicYear.getName();
		int nextYear = Integer.parseInt(currentAcademicYearStr)+1;
		int preYear = Integer.parseInt(currentAcademicYearStr)-1;
		academicYearWrapper.setNextAcademicYear(nextYear+"");
		academicYearWrapper.setPreviousAcademicYear(preYear+"");
		
		if(academicYear!=null){
			
			String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+currentAcademicYearStr+"'"   ;  
			//logger.info(" sqlRound:"+sqlRound);
			List<AcademicYearEvaluateRound> academicYearEvaluateRoundList  = this.jdbcTemplate.query(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
			
			for(AcademicYearEvaluateRound roundTmp: academicYearEvaluateRoundList){
				long round1EndTime = roundTmp.getRound1EndDate().getTime();
				 systemTime = System.currentTimeMillis();
				
				if(round1EndTime>systemTime){
					roundTmp.setCanEditRound1("N");
				}else{
					roundTmp.setCanEditRound1("Y");
				}
				
				if(roundTmp.getRound2EndDate()!=null){
					roundTmp.setCanEditRound2("N");
					long round2EndTime = roundTmp.getRound2EndDate().getTime();
					if(round2EndTime<systemTime){
						roundTmp.setCanEditRound2("Y");
					}else{
						roundTmp.setCanEditRound2("N");
					}
					
				}
			}
			academicYearWrapper.setAcademicYearEvaluateRoundList(academicYearEvaluateRoundList);
		}
		
	 
		
		
		return academicYearWrapper;
	}
	
	
	@Override
	public AcademicYearWrapper getByAcademicYear( final String academicYearStr) {		 	
		
		
		
		String checkExistSql = " select count(*) as total_item  from academic_year where name ='"+academicYearStr+"'" ; 
		int  rows_totalItem = jdbcTemplate.queryForObject(checkExistSql.toString(),Integer.class); 
		
		if(rows_totalItem==0){
			//logger.info(" academicYearStr :"+academicYearStr +"  Not Found , Create New");
			// Insert New
			String sql =" select *  from academic_year where year_status ='Y'" ; 
			//logger.info(" sql:"+sql);
			final AcademicYear academicYearCurrent = this.jdbcTemplate.queryForObject(sql,	new AcademicYearMapper() );		
			
			final Long startTimeNexYear = academicYearCurrent.getStartDate().getTime()+(365*24*60*60*1000);
			final Long endTimeNexYear = academicYearCurrent.getEndDate().getTime()+(365*24*60*60*1000);
			KeyHolder keyHolder = new GeneratedKeyHolder(); 		
			jdbcTemplate.update(new PreparedStatementCreator() {  
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
					PreparedStatement ps = connection.prepareStatement("" +						
							"  insert into academic_year (name, start_date,end_date,year_status) values (?, ?,?,?)" +
						 "", Statement.RETURN_GENERATED_KEYS);   
					ps.setString(1,academicYearStr);
					ps.setTimestamp(2,new Timestamp(startTimeNexYear));
					ps.setTimestamp(3,new Timestamp(endTimeNexYear));
					ps.setString(4,"N");		 			
					return ps;  
					}
				}, 	keyHolder); 	
			Long returnid =  keyHolder.getKey().longValue();	
			
		}
		
		String sql =" select *  from academic_year where name ='"+academicYearStr+"'" ; 
		//logger.info(" sql:"+sql);
		AcademicYear academicYear = this.jdbcTemplate.queryForObject(sql,	new AcademicYearMapper() );				
		AcademicYearWrapper academicYearWrapper = new AcademicYearWrapper();
		academicYearWrapper.setAcademicYear(academicYear);
		
		String currentAcademicYearStr = academicYear.getName();
		int nextYear = Integer.parseInt(currentAcademicYearStr)+1;
		int preYear = Integer.parseInt(currentAcademicYearStr)-1;
		academicYearWrapper.setNextAcademicYear(nextYear+"");
		academicYearWrapper.setPreviousAcademicYear(preYear+"");
		
		
		return academicYearWrapper;
	}	


	@Override
	public AcademicYear getByYear(   String academicYearStr) {		 	
 
			String sql =" select *  from academic_year where name ='"+academicYearStr+"'" ;  
			//logger.info(" sql:"+sql);
			 AcademicYear academicYear  = this.jdbcTemplate.queryForObject(sql,	new AcademicYearMapper() );		
			 
					
					String sqlSemester =" select *  from semester where academic_year='"+academicYear.getName()+"'"; 
					//logger.info(" sqlSemester:"+sql);
					List<Semester> semesterList = this.jdbcTemplate.query(sqlSemester,	new SemesterMapper() );	
					
					academicYear.setSemesterList(semesterList);
		 
		return academicYear;
	}	
	
	
	@Override
	public List<AcademicYear> getAll(   ) {	
		
		 List<AcademicYear> academicYearList =new ArrayList();
 
			String sql =" select *  from academic_year ";  
			logger.info(" sql:"+sql);
			academicYearList  = this.jdbcTemplate.query(sql,	new AcademicYearMapper() );		
			
			 
		return academicYearList;
	}	


	@Override
	public AcademicYearEvaluateRound getEvaluateRoundByYear(   String academicYearStr,String evaluateType) {		 	
 
			String sql =" select *  from academic_evaluate_round where academic_year  ='"+academicYearStr+"' and evaluate_type='"+evaluateType+"'" ;  
			logger.info(" sql:"+sql);
			AcademicYearEvaluateRound academicYearEvaluateRound  = this.jdbcTemplate.queryForObject(sql,	new AcademicYearEvaluateRoundMapper() );	 
			 
		return academicYearEvaluateRound;
	}	


	
	
	@Override
	public void editDateAcademicYear(final AcademicYear domain ) {
		logger.info(" #editDateAcademicYear");
		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  update  academic_year set  start_date=?,end_date=? where name=?  " +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setTimestamp(1,domain.getStartDate());
				ps.setTimestamp(2,domain.getEndDate());
				ps.setString(3,domain.getName());
			 	 			
				return ps;  
				}
			} ); 
	 
	}
	
	@Override
	public void editDateEvaluateRound(final AcademicYearEvaluateRound domain ) {
		logger.info(" #academicYearEvaluateRound");
  
   		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  update  academic_evaluate_round set  round1_start_date=?,round1_end_date=?,round1_status=?,round2_start_date=?,round2_end_date=?," +
						"round2_status=?,academic_year=?,evaluate_type=?,evaluate_type_desc=? where round_id=?  " +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setTimestamp(1,domain.getRound1StartDate());
				ps.setTimestamp(2,domain.getRound1EndDate());
				ps.setString(3, domain.getRound1Status());
		
				ps.setTimestamp(4,domain.getRound2StartDate());
				ps.setTimestamp(5,domain.getRound2EndDate());
				ps.setString(6, domain.getRound2Status());
		
				ps.setString(7,domain.getAcademicYear());
				ps.setString(8,domain.getEvaluateType());
				ps.setString(9,domain.getEvaluateTypeDesc());
				ps.setLong(10,domain.getRoundId());			
				return ps;  
				}
			} ); 
	 
	}
	
	
	
	
	
	
	
	
	@Override
	public boolean checkAlreadyAdjust( ) {
		logger.info(" #checkAlreadyAdjust");
		
		boolean alreadyAdjust = false;
		String currentAcademicYearSQL =" SELECT name FROM academic_year where year_status='Y' ";								 
		final String currentAcademicYear = (String)this.jdbcTemplate.queryForObject(	currentAcademicYearSQL , String.class);
		final int newAcademicYearInt = Integer.parseInt(currentAcademicYear)+1; 
  
   		try{
   			
   			String sql =" select *  from academic_year where name  ='"+newAcademicYearInt+"'"   ;  
   			logger.info(" sql:"+sql);
   			AcademicYear academicYear = this.jdbcTemplate.queryForObject(sql,	new AcademicYearMapper() );		
   			
   			alreadyAdjust = true;
   		}catch(Exception ex){
   			ex.printStackTrace();
   		}
		 
		
   		return alreadyAdjust;
	}
 
 
	
	@Override
	public AcademicYearWrapper ajustYearIncrease( ) {
		logger.info(" #ajustYearIncrease");
		String currentAcademicYearSQL =" SELECT name FROM academic_year where year_status='Y' ";								 
		final String currentAcademicYear = (String)this.jdbcTemplate.queryForObject(	currentAcademicYearSQL , String.class);
		final int newAcademicYearInt = Integer.parseInt(currentAcademicYear)+1; 
   	//	this.jdbcTemplate.update("update  academic_year set  year_status='N'"); 
   	//	this.jdbcTemplate.update("update  academic_year set  year_status='Y'  where name='"+newAcademicYearInt+"' ");
		
   		
		String sql =" select *  from academic_year where year_status ='Y'" ; 
		logger.info(" sql:"+sql);
		AcademicYear academicYear = this.jdbcTemplate.queryForObject(sql,	new AcademicYearMapper() );				
		AcademicYearWrapper academicYearWrapper = new AcademicYearWrapper();
		academicYearWrapper.setAcademicYear(academicYear);
		
		String currentAcademicYearStr = academicYear.getName();
		int nextYear = Integer.parseInt(currentAcademicYearStr)+1;
		int preYear = Integer.parseInt(currentAcademicYearStr)-1;
		academicYearWrapper.setNextAcademicYear(nextYear+"");
		academicYearWrapper.setPreviousAcademicYear(preYear+"");
		
		
		// ############## Duplicate Master Data #####
		// 1.academic_evaluate_round		
		// 2. pbp_person
		// 3 . faculty,department
	
		
		
		
		// ######## Start academic_year, academic_evaluate_round
		String sql_delete_academic_evaluate_round =" delete from  academic_evaluate_round where academic_year = '"+newAcademicYearInt+"' ";
		logger.info(" sql_delete_academic_evaluate_round:"+sql_delete_academic_evaluate_round);	
		jdbcTemplate.update( sql_delete_academic_evaluate_round);	
		
		
		String sqlRound =" select *  from academic_evaluate_round where academic_year  ='"+currentAcademicYearStr+"'"   ;  
		//logger.info(" sqlRound:"+sqlRound);
		List<AcademicYearEvaluateRound> academicYearEvaluateRoundList  = this.jdbcTemplate.query(sqlRound,	new AcademicYearEvaluateRoundMapper() );	
		
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  INSERT INTO academic_year (name, start_date,end_date,year_status) 	SELECT abs(name)+1, DATE_ADD(start_date, INTERVAL 1 YEAR)  , DATE_ADD(end_date, INTERVAL 1 YEAR) ,year_status FROM   academic_year  WHERE  name = ?" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,currentAcademicYear);
		 	 			
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();	
		
		
		
		
		logger.info(" ## New Id for new acedemic year:"+newAcademicYearInt +" :"+returnid);
		
		this.jdbcTemplate.update("update  academic_year set  year_status='N' where name ='"+currentAcademicYearStr+"'"); 
		
		String sqlAcademicYear =" select *  from academic_year where name ='"+newAcademicYearInt+"'" ;  
		logger.info(" sqlAcademicYear:"+sqlAcademicYear);
		 AcademicYear academicYearNew  = this.jdbcTemplate.queryForObject(sqlAcademicYear,	new AcademicYearMapper() );	
		
		academicYearWrapper.setAcademicYear(academicYearNew);
		
		//for(AcademicYearEvaluateRound tmp:academicYearEvaluateRoundList){
		final String sql_round = 	"  INSERT INTO academic_evaluate_round (round1_start_date,round1_end_date,round2_start_date,round2_end_date, academic_year,evaluate_type,evaluate_type_desc) "+
				"  SELECT  DATE_ADD(round1_start_date, INTERVAL 1 YEAR),DATE_ADD(round1_end_date, INTERVAL 1 YEAR),DATE_ADD(round2_start_date, INTERVAL 1 YEAR), "+
				"  DATE_ADD(round2_end_date, INTERVAL 1 YEAR),abs(academic_year)+1 ,evaluate_type, evaluate_type_desc FROM   academic_evaluate_round WHERE  academic_year =? ";
		logger.info(" sql_round:"+sql_round);
			
			jdbcTemplate.update(new PreparedStatementCreator() {  
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
					PreparedStatement ps = connection.prepareStatement(sql_round, Statement.RETURN_GENERATED_KEYS);   
					ps.setString(1,currentAcademicYear);
			 		 			
					return ps;  
					}
				}, 	keyHolder); 
			
		//}
   		//studentCourseMapping();
		
		// ######## End  academic_year, academic_evaluate_round
			
			// ######## Start  pbp_person
			
			
			
		// Delete person if exist
			String sql_delete_person =" delete from  person_pbp where academic_year = '"+newAcademicYearInt+"' ";
			logger.info(" sql_delete_person:"+sql_delete_person);	
			jdbcTemplate.update( sql_delete_person);	
			
		String sql_person ="	INSERT INTO person_pbp (thai_name,thai_surname,rate_no,employee_type,max_education,email,picture,person_type,status,department_desc, "+
				" 	title_name,academic_rank,faculty_desc,is_president,is_dean,is_head,academic_year ) "+
				" 	select thai_name,thai_surname,rate_no,employee_type,max_education,email,picture,person_type,status,department_desc, "+
				" 	title_name,academic_rank,faculty_desc,is_president,is_dean,is_head,'"+newAcademicYearInt+"' "+
				" 	from person_pbp where academic_year='"+currentAcademicYear+"'";
		
		logger.info(" sql_person:"+sql_person);	
		jdbcTemplate.update( sql_person);	
		 
			// ######## End  pbp_person
			
			
			
		// ######## Start faculty, department ######################
		
		
	// Delete person if exist
		String sql_delete_faculty =" delete from  faculty where academic_year = '"+newAcademicYearInt+"' ";
		logger.info(" sql_delete_faculty:"+sql_delete_faculty);	
		jdbcTemplate.update( sql_delete_faculty);	
		
		String sql_delete_department =" delete from  department where academic_year = '"+newAcademicYearInt+"' ";
		logger.info(" sql_delete_department:"+sql_delete_department);	
		jdbcTemplate.update( sql_delete_department);	
			
			FacultyWrapper facultyWrapper =facultyDao.getByAcademicYear(currentAcademicYear);
			
			List<Faculty> facultyList = facultyWrapper.getFacultyList();
			final String sql_faculty = 	" insert into faculty (name,code,academic_year,enable) values (?,?,?,true)";
			logger.info(" sql_round:"+sql_faculty);			
			for(final Faculty tmp:facultyList){
				
				jdbcTemplate.update(new PreparedStatementCreator() {  
					public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
						PreparedStatement ps = connection.prepareStatement(sql_faculty, Statement.RETURN_GENERATED_KEYS);   
						ps.setString(1,tmp.getName());
						ps.setString(2,tmp.getCode());
						ps.setString(3,newAcademicYearInt+"");
				 		 			
						return ps;  
						}
					}, 	keyHolder); 
				
				
				final Long returnid_faculty =  keyHolder.getKey().longValue();
				
				

				
				List<Department > departmentList = tmp.getDepartmentList();
				if(departmentList!=null&&departmentList.size()>0){
					final String sql_department = 	" insert into department (name,code,academic_year,faculty_code,faculty_id) values (?,?,?,?,?)";
					logger.info(" sql_department:"+sql_department);	
					for(final Department tmpdepartment:departmentList){ 
							jdbcTemplate.update(new PreparedStatementCreator() {  
								public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
									PreparedStatement ps = connection.prepareStatement(sql_department, Statement.RETURN_GENERATED_KEYS);   
									ps.setString(1,tmpdepartment.getName());
									ps.setString(2,tmpdepartment.getCode());
									ps.setString(3,newAcademicYearInt+"");
									ps.setString(4,tmp.getCode()); 		
									ps.setLong(5, returnid_faculty);
									return ps;  
									}
								}, 	keyHolder); 					
						
					}
					
				}
				
				
				
			}
			
			
		// ######## Start faculty, department ######################
			
			
			// ######## Start  academic_kpi,academic_kpi_attribute
			
			String sql_delete_academic_kpi =" delete from  academic_kpi where academic_year = '"+newAcademicYearInt+"' ";
			logger.info(" sql_delete_academic_kpi:"+sql_delete_academic_kpi);	
			jdbcTemplate.update( sql_delete_academic_kpi);	
			
			String sql_delete_academic_kpi_attribute =" delete from  academic_kpi_attribute where academic_year = '"+newAcademicYearInt+"' ";
			logger.info(" sql_delete_academic_kpi_attribute:"+sql_delete_academic_kpi_attribute);	
			jdbcTemplate.update( sql_delete_academic_kpi_attribute);	
			
			
			AcademicKPIWrapper getAllByAcademicYear = academicKPIDao.getAllByAcademicYear(currentAcademicYear);
			
			List<PBPWorkType > pbpWorkTypeList = getAllByAcademicYear.getpBPWorkTypeList();
			
			for(PBPWorkType worktmpx: pbpWorkTypeList){
				
				List<AcademicKPI> academicKPIList = worktmpx.getAcademicKPIList();
				for(final AcademicKPI kpiTmp:academicKPIList){
					
					final String sql_kpi = 	" insert into academic_kpi (code,name,unit_code,mark,work_type_code,academic_year,rule_code) values (?,?,?,?,?,?,?)";
					logger.info(" sql_kpi:"+sql_kpi);	
					
					jdbcTemplate.update(new PreparedStatementCreator() {  
						public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
							PreparedStatement ps = connection.prepareStatement(sql_kpi, Statement.RETURN_GENERATED_KEYS);   
							ps.setString(1,kpiTmp.getCode());
							ps.setString(2,kpiTmp.getName());
							ps.setString(3,kpiTmp.getUnitCode());
							ps.setBigDecimal(4,kpiTmp.getMark()); 		
							ps.setString(5, kpiTmp.getWorkTypeCode());
							ps.setString(6, newAcademicYearInt+"");
							ps.setString(7, kpiTmp.getMultiplyValue());
							return ps;  
							}
						}, 	keyHolder); 	
					
					
					
					
					final Long returnid_kpi =  keyHolder.getKey().longValue();
					
					
					List<AcademicKPIAttribute> academicKPIAttributeList =kpiTmp.getAcademicKPIAttributeList();
					
					for(final AcademicKPIAttribute attTmp:academicKPIAttributeList){
						
						final String sql_Attribute = 	" insert into academic_kpi_attribute (code,academic_kpi_code,name, is_calculate,academic_year,academic_kpi_id) values (?,?,?,?,?,?)";
						logger.info(" sql_Attribute:"+sql_Attribute);	
						
						jdbcTemplate.update(new PreparedStatementCreator() {  
							public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
								PreparedStatement ps = connection.prepareStatement(sql_Attribute, Statement.RETURN_GENERATED_KEYS);   
								ps.setString(1,attTmp.getCode());
								ps.setString(2,attTmp.getAcademicKPICode());
								ps.setString(3,attTmp.getName());
								ps.setString(4,attTmp.getIsCalculate());  
								ps.setString(5, newAcademicYearInt+"");
								ps.setLong(6, returnid_kpi);
								return ps;  
								}
							}, 	keyHolder); 	
						
					}
					
					
				}
				
			}
			
  
		
		 
			// ######## End  academic_kpi,academic_kpi_attribute		
		
		
		// ######## Start  pbp_work_type
		
			String sql_delete_pbp_work_type =" delete from  pbp_work_type where academic_year = '"+newAcademicYearInt+"' ";
			logger.info(" sql_delete_pbp_work_type:"+sql_delete_pbp_work_type);	
			jdbcTemplate.update( sql_delete_pbp_work_type);		
			
		
	String sql_pbp_work_type ="	insert into pbp_work_type (name,code,min_percent,min_hour,max_percent,max_hour,limit_base,academic_year)  "+				 
			" 	select name,code,min_percent,min_hour,max_percent,max_hour,limit_base,'"+newAcademicYearInt+"'"+
			" 	from pbp_work_type where academic_year='"+currentAcademicYear+"'"; 
	
	logger.info(" sql_pbp_work_type:"+sql_pbp_work_type);	
	jdbcTemplate.update( sql_pbp_work_type);	
	 
		// ######## End  academic_kpi	
	
	
	// ######## Start  mark_rank
	
	String sql_delete_mark_rank =" delete from  mark_rank where academic_year = '"+newAcademicYearInt+"' ";
	logger.info(" sql_delete_mark_rank:"+sql_delete_mark_rank);	
	jdbcTemplate.update( sql_delete_mark_rank);		
	
	
String sql_mark_rank ="	insert into mark_rank (mark_from,mark_to,salary_level,employee_type,round,academic_year)  "+				 
		" 	select mark_from,mark_to,salary_level,employee_type,round,'"+newAcademicYearInt+"'"+
		" 	from mark_rank where academic_year='"+currentAcademicYear+"'"; 

logger.info(" sql_mark_rank:"+sql_mark_rank);	
jdbcTemplate.update( sql_mark_rank);	
 
	// ######## End  mark_rank		
	
 
		

		
   		return academicYearWrapper;
	}
 
	
	private class AcademicYearMapper implements RowMapper<AcademicYear> {   						
        @Override
		public AcademicYear mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AcademicYear domain = new AcademicYear(); 
        	domain.setAcademicYearId(rs.getLong("academic_year_id"));
			domain.setName(rs.getString("name"));		
			domain.setStartDate(rs.getTimestamp("start_date"));
			domain.setEndDate(rs.getTimestamp("end_date"));	
			domain.setStartDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getTimestamp("start_date")));
			domain.setEndDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(rs.getTimestamp("end_date")));
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
	private class SemesterMapper implements RowMapper<Semester> {
        @Override
		public Semester mapRow(ResultSet rs, int rowNum) throws SQLException {
			Semester domain = new Semester();
			domain.setSemesterId(rs.getLong("semester_id"));
			domain.setName(rs.getString("name"));
			domain.setStartDate(rs.getTimestamp("start_date"));
			domain.setEndDate(rs.getTimestamp("end_date"));
			domain.setYearId(rs.getLong("year_id"));
			return domain;
	    }
	}
	/*
	 * DROP TABLE IF EXISTS `pbp`.`academic_evaluate_round`;
CREATE TABLE  `pbp`.`academic_evaluate_round` (
  `round_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `` datetime DEFAULT NULL,
  `round1_end_date` datetime DEFAULT NULL,
  `` varchar(45) DEFAULT NULL,
  `round2_start_date` datetime DEFAULT NULL,
  `round2_end_date` datetime DEFAULT NULL,
  `round2_status` varchar(45) DEFAULT NULL,
  `` varchar(45) DEFAULT NULL,
  `` varchar(45) DEFAULT NULL,
  `` varchar(100) DEFAULT NULL, 
  PRIMARY KEY (`round_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
}
