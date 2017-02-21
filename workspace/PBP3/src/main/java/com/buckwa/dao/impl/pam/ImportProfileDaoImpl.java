package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pam.ImportProfileDao;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pam.Section;
import com.buckwa.domain.pam.Staff;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.school.SchoolUtil;

/**
 * 
 @Author : Kroekpong Sakulchai (Ball)
 @Create : Aug 6, 2012 13:37:14 PM
 *
 **/

@Repository("importProfileDao")

public class ImportProfileDaoImpl implements ImportProfileDao {

	private static Logger logger = LoggerFactory.getLogger(ImportProfileDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SchoolUtil schoolUtil;
	@Override
	public Long create(final Person person) {
		logger.info(" <<<---- Insert Into Person PBP ---->>> ");
		
		KeyHolder keyHolder = new GeneratedKeyHolder(); 
		
		final StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO person_pbp ( ");
		sb.append( "  person_id,       " );
		sb.append( "  employee_id,    " );
		sb.append( "  citizen_id,     " );
		sb.append( "  thai_name,      " );
		sb.append( "  thai_surname,   " );
		sb.append( "  sex,            " );
		sb.append( "  birth_date,      " ); // Date
		sb.append( "  rate_no,        " );
		sb.append( "  employee_type,  " );
		sb.append( "  academic_rank,  " );
		sb.append( "  level,          " );
		sb.append( "  workline_code,   " );
		sb.append( "  salary,         " ); // Bigdecimal
		sb.append( "  work_tel_no,    " );
		sb.append( "  belong_to,      " );
		sb.append( "  faculty_desc,   " );
		sb.append( "  department_desc,   " );
		sb.append( "  working_date,   " ); // Date
		sb.append( "  assign_date,    " ); // Date
		sb.append( "  retire_date,    " ); // Date
		sb.append( "  max_insignia,   " );
		sb.append( "  max_education,  " );
		sb.append( "  tax_no,         " );
		sb.append( "  married_status, " );
		sb.append( "  work_number,    " );
		sb.append( "  insure_no,      " );
		sb.append( "  fund,           " );
		sb.append( "  address,        " );
		sb.append( "  zip_code,       " );
		sb.append( "  tel_no,         " );
		sb.append( "  email,          " );
		sb.append( "  working_status, " );
		sb.append( "  picture,        " );
		sb.append( "  person_type,    " );
		sb.append( "  status,         " );
		sb.append( "  create_by,      " );
		sb.append( "  create_date,    " );
		sb.append( "  update_by,      " );
		sb.append( "  update_date,   " );
		sb.append( "  academic_year,   " );
		sb.append( "  reg_id   " );
		sb.append( "   )   " );
		sb.append( " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " );
		sb.append( "  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " );
		sb.append( "  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " );
		sb.append( "  ?, ?," );
		sb.append( " 'A',?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP,?,? ) " );

		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
				PreparedStatement ps = connection.prepareStatement(	sb.toString() , Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1, person.getEmployeeId()  );
				ps.setString( 2 , person.getCitizenId());
				ps.setString( 3 , person.getThaiName());
				ps.setString( 4 , person.getThaiSurname());
				ps.setString( 5 , person.getSex());
				ps.setDate( 6 , BuckWaDateUtils.utilDateToSqlDate(person.getBirthdate()));
				ps.setString( 7 , person.getRateNo());
				ps.setString( 8 , person.getEmployeeType());
				ps.setString( 9 , person.getAcademicRank());
				ps.setString( 10, person.getLevel());
				ps.setString( 11, person.getWorkLine());
				ps.setBigDecimal( 12, person.getSalary());
				ps.setString( 13, person.getWorkTelNo());
				ps.setString( 14, person.getBelongTo());
				ps.setString( 15, person.getFaculty());
				ps.setString( 16, person.getDepartment());
				ps.setDate( 17, BuckWaDateUtils.utilDateToSqlDate(person.getWorkingDate()));
				ps.setDate( 18, BuckWaDateUtils.utilDateToSqlDate(person.getAssignDate()));
				ps.setDate( 19, BuckWaDateUtils.utilDateToSqlDate(person.getRetireDate()));
				ps.setString( 20, person.getMaxInsignia());
				ps.setString( 21, person.getMaxEducation());
				ps.setString( 22, person.getTaxNo());
				ps.setString( 23, person.getMarriedStatus());
				ps.setString( 24, person.getWorkNumber());
				ps.setString( 25, person.getInsureNo());
				ps.setString( 26, person.getFund());
				ps.setString( 27, person.getAddress());
				ps.setString( 28, person.getZipCode());
				ps.setString( 29, person.getTelNo());
				ps.setString( 30, person.getEmail());
				ps.setString( 31, person.getWorkingStatus());
				ps.setString( 32, person.getPicture());
				ps.setString( 33, person.getPersonType());
				ps.setString( 34, person.getCreateBy());
				ps.setString( 35, person.getUpdateBy());
				ps.setString( 36, person.getAcademicYear());
				ps.setString( 37, person.getRegId());
				return ps;  
			}
		};
		
		Long returnId = null;
		
		jdbcTemplate.update(preparedStatementCreator,keyHolder); 	
		
		if(null != keyHolder){
			returnId = keyHolder.getKey().longValue();	
		}
		
		return returnId;
	}
	
	
	
	@Override
	public Long createPBP(final Person person) {
		logger.info(" <<<---- Insert Into Person ---->>> ");
		
		final String currentAcademicYearFinal = schoolUtil.getCurrentAcademicYear();	 
				  
		/*
		1. �ѧ�Ѵ	: facultyDesc :person.faculty_desc :������ǡ�����ʵ��
		2. ��ǹ�ҹ: departmentDesc :person.department_desc	: �Ң��Ԫ����ǡ����ä��Ҥ�
		3. ������:employeeType :person.employee_type:����Ҫ���
		4. �ӹ�˹��:titleName :person.title_name:���
		5. ����:firstName :person.thai_name:��
		6. ���ʡ��:lastName	 :person.thai_surname:����Ѳ��ѭ��
		7. �Ţ����ѵ��:rateNo :person.rate_no:107
		8. ���˹觧ҹ:academicRank :person.academic_rank:�ͧ��ʵ�Ҩ����
		9. �زԡ���֡��:maxEducation :person.max_education:�.�͡
		10. Email :person.email:1@kmitl.ac.th
			 */
		
		KeyHolder keyHolder = new GeneratedKeyHolder(); 
		
		final StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO person_pbp ( ");
		sb.append( "  faculty_desc,       " );
		sb.append( "  department_desc,       " );
		sb.append( "  employee_type,       " );
		sb.append( "  title_name,       " );
		sb.append( "  thai_name,       " );
		sb.append( "  thai_surname,       " );
		sb.append( "  rate_no,       " );
		sb.append( "  academic_rank,       " );
		sb.append( "  max_education,       " );
		sb.append( "  email,       " );
		sb.append( "  picture  ,    " );
		
		
		sb.append( "  working_date,       " );
		sb.append( "  birth_date,       " );
 
		sb.append( "  create_by,      " );
		sb.append( "  create_date,    " );
		sb.append( "  update_by,      " );
		sb.append( "  academic_year,       " );
		

		
		sb.append( "  update_date,       " );
		sb.append( "  reg_id   ,     " );
		sb.append( "  citizen_id        " );
		
		sb.append( " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?,?,  " );
 
		sb.append( " ?, CURRENT_TIMESTAMP, ?,?, CURRENT_TIMESTAMP,?,? ) " );

		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
				PreparedStatement ps = connection.prepareStatement(	sb.toString() , Statement.RETURN_GENERATED_KEYS);  
				
				ps.setString(1, person.getFacultyDesc()  );
				ps.setString( 2 , person.getDepartmentDesc());
				ps.setString( 3 , person.getEmployeeType());
				ps.setString( 4 , person.getTitleName());
				ps.setString( 5 , person.getThaiName());
				ps.setString( 6 , person.getThaiSurname());
				ps.setString( 7 , person.getRateNo());
				 
				ps.setString( 8 , person.getAcademicRank());
				ps.setString( 9 , person.getMaxEducation());
				ps.setString( 10 , person.getEmail());
				ps.setString( 11, person.getPicture());
				
				System.out.println(" person.getWorkingDate():"+person.getWorkingDate()+"     person.getBirthdate():"+person.getBirthdate());
				ps.setDate( 12, new java.sql.Date(person.getWorkingDate().getTime()));
				ps.setDate( 13, new java.sql.Date(person.getBirthdate().getTime()));
				
				ps.setString( 14, person.getCreateBy());
				ps.setString( 15, person.getUpdateBy());
				ps.setString( 16, currentAcademicYearFinal );
				ps.setString( 17, person.getRegId() );
				ps.setString( 18, person.getCitizenId() );
				return ps;  
			}
		};
		
		Long returnId = null;
		
		jdbcTemplate.update(preparedStatementCreator,keyHolder); 	
		
		if(null != keyHolder){
			returnId = keyHolder.getKey().longValue();	
		}
		
		return returnId;
	}

	
	
	
	
	
	@Override
	public Long createStaff(final Staff person) {
		logger.info(" <<<---- Insert Into Person ---->>> ");
		
 
		KeyHolder keyHolder = new GeneratedKeyHolder(); 
		
		final StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO staff ( ");
		sb.append( "  code,       " );
		sb.append( "  full_name )      " );
	 
		sb.append( " VALUES (?, ? ) " );
  
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
				PreparedStatement ps = connection.prepareStatement(	sb.toString() , Statement.RETURN_GENERATED_KEYS);  
			 
				ps.setString(1, person.getStaffCode()  );
				ps.setString( 2 , person.getFullName()); 
				
				return ps;  
			}
		};
		
		Long returnId = null;
		
		jdbcTemplate.update(preparedStatementCreator,keyHolder); 	
		
		if(null != keyHolder){
			returnId = keyHolder.getKey().longValue();	
		}
		
		return returnId;
	}

	
	@Override
	public int findByUsername(String username) {
		
		int  rows_totalItem = 0;
		String sqlIsUserExist = " select count(*) as total_item  from  person_pbp where email= '"+StringEscapeUtils.escapeSql(username.trim())+"' " ;		 
		String sqlIsBuckwaUserExist = " select count(*) as total_item  from  buckwauser where email= '"+StringEscapeUtils.escapeSql(username.trim())+"' " ;		 
		
		rows_totalItem = jdbcTemplate.queryForObject(sqlIsUserExist,Integer.class); 
		
		if(rows_totalItem == 0){
			rows_totalItem = jdbcTemplate.queryForObject(sqlIsBuckwaUserExist,Integer.class);  
		}
		
		logger.info("User Name Count : "+rows_totalItem);
		
		return rows_totalItem;
	}

	@Override
	public boolean isExistPerson(String username) {
		String sqlIsUserExist = " select count(*) as total_item  from  person_pbp where email= '"+StringEscapeUtils.escapeSql(username.trim())+"' " ;	
		int rows_totalItem = jdbcTemplate.queryForObject(sqlIsUserExist,Integer.class); 
		return ( rows_totalItem > 0)? true : false;
	}
	
	
	
	@Override
	public int findByStaffCode(String username) {
		
		int  rows_totalItem = 0;
		String sqlIsUserExist = " select count(*) as total_item  from  person_pbp where email= '"+StringEscapeUtils.escapeSql(username.trim())+"' " ;		 
	 
		rows_totalItem = jdbcTemplate.queryForObject(sqlIsUserExist,Integer.class); 
		
		if(rows_totalItem == 0){
			rows_totalItem = jdbcTemplate.queryForObject(sqlIsUserExist,Integer.class);  
		}
		
		logger.info("User Name Count : "+rows_totalItem);
		
		return rows_totalItem;
	}
	
	private static final String SQL_UPDATE_PERSON = 
		" UPDATE person " +
		" SET thai_name = ?, " +
		"     thai_surname = ?, " +
		"     sex = ?, " +
		"     birthdate = ?, " +
		"     rate_no = ?, " +
		"     employee_type = ?, " +
		"     position = ?, " +
		"     work_line = ?, " +
		"     level = ?, " +
		"     salary = ?, " +
		"     faculty = ?, " +
		"     assign_date = ?, " +
		"     working_date = ?, " +
		"     work_tel_no = ?, " +
		"     married_status = ?, " +
		"     max_insignia = ?, " +
		"     max_education = ?, " +
		"     working_status = ?, " +
		"     picture = ?, " +
		"     person_type = ?, " +
		"     update_by = ?, " +
		"     update_date = CURRENT_TIMESTAMP , " +
		"     citizen_id = ? " +
		" WHERE email = ? ";
	
	 
	

	
	@Override
	public void update(Person person) {
		int result = jdbcTemplate.update(SQL_UPDATE_PERSON, new Object[] {
				person.getThaiName(),
				person.getThaiSurname(),
				person.getSex(),
				person.getBirthdate(),
				person.getRateNo(),
				person.getEmployeeType(),
				person.getPosition(),
				person.getWorkLine(),
				person.getLevel(),
				person.getSalary(),
				person.getFaculty(),
				person.getAssignDate(),
				person.getWorkingDate(),
				person.getWorkTelNo(),
				person.getMarriedStatus(),
				person.getMaxInsignia(),
				person.getMaxEducation(),
				person.getWorkingStatus(),
				person.getPicture(),
				person.getPersonType(),
				person.getUpdateBy(),
				person.getCitizenId(),
				person.getEmail()
			});
			
			if (logger.isDebugEnabled()) {
				logger.info("result: " + result);
			}
	}
	
	
	
	@Override
	public void updatePBPNew(final Person person) {
		logger.info(" <<<---- update pbp Person ---->>> ");
		
		final String currentAcademicYearFinal = schoolUtil.getCurrentAcademicYear();	 
 
		
	//	KeyHolder keyHolder = new GeneratedKeyHolder(); 
		
		final StringBuilder sb = new StringBuilder();
		sb.append(" update person_pbp  set  ");
		sb.append( "  faculty_desc =? ,       " );
		sb.append( "  department_desc =?,       " );
		sb.append( "  employee_type =?,       " );
		sb.append( "  title_name =?,       " );
		sb.append( "  thai_name =?,       " );
		sb.append( "  thai_surname =?,       " );
		sb.append( "  rate_no =?,       " );
		sb.append( "  academic_rank =?,       " );
		sb.append( "  max_education =?,       " );
		sb.append( "  reg_id =?,       " ); 
		sb.append( "  working_date =?,       " );
		sb.append( "  birth_date =? ,      " );
		sb.append( "  citizen_id =?       " );
		sb.append( "  where email =?  and academic_year=?   " );
 

		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
				PreparedStatement ps = connection.prepareStatement(	sb.toString() , Statement.RETURN_GENERATED_KEYS);  
				
				ps.setString(1, person.getFacultyDesc()  );
				ps.setString( 2 , person.getDepartmentDesc());
				ps.setString( 3 , person.getEmployeeType());
				ps.setString( 4 , person.getTitleName());
				ps.setString( 5 , person.getThaiName());
				ps.setString( 6 , person.getThaiSurname());
				ps.setString( 7 , person.getRateNo());
				 
				ps.setString( 8 , person.getAcademicRank());
				ps.setString( 9 , person.getMaxEducation());
				ps.setString( 10, person.getRegId() );
				ps.setDate( 11, new java.sql.Date(person.getWorkingDate().getTime()));
				ps.setDate( 12, new java.sql.Date(person.getBirthdate().getTime()));
				ps.setString( 13 , person.getCitizenId());
				ps.setString( 14 , person.getEmail());
				ps.setString( 15, currentAcademicYearFinal );
	 
	 
				return ps;  
			}
		};
		
		Long returnId = null;
		
		jdbcTemplate.update(preparedStatementCreator); 	
		
		//if(null != keyHolder){
		//	returnId = keyHolder.getKey().longValue();	
		//}
		
		//return returnId;
	}

	
	private static final String SQL_UPDATE_PERSON_PBP = 
			" UPDATE person_pbp " +
			" SET faculty_desc = ?, " +
			"     department_desc = ?, " +
			"     employee_type = ?, " +
			"     title_name = ?, " +
			"     thai_name = ?, " +
			"     rate_no = ?, " +
			"     academic_rank = ?, " +
			"     max_education = ?, " +  
			"     working_date = ?, " + 
			"     birth_date = ?, " + 
			"     reg_id = ?, " + 
			"     update_date = CURRENT_TIMESTAMP " +
			" WHERE email = ? ";
	
	@Override
	public void updatePBP(Person person) {
		int result = jdbcTemplate.update(SQL_UPDATE_PERSON_PBP, new Object[] {
				person.getFacultyDesc(),
				person.getDepartmentDesc(),
				person.getEmployeeType(),
				person.getTitleName(),
				person.getThaiName(),
				person.getRateNo(),
				person.getAcademicRank(),
				person.getMaxEducation() ,
				person.getRegId() ,
				person.getWorkingDate(),
				person.getBirthdate(),
				person.getEmail()
				
			});
			
			if (logger.isDebugEnabled()) {
				logger.info("result: " + result);
			}
	}
	
	@Override
	public void updateStaff(Staff person) {
		
		String updateStaffSQL  ="  update staff set full_name =? where cod='"+person.getStaffCode()+"'";
		int result = jdbcTemplate.update(updateStaffSQL, new Object[] {
				 
				person.getFullName(),
			
		 
			});
			
			if (logger.isDebugEnabled()) {
				logger.info("result: " + result);
			}
	}
	
	@Override
	public void updateTotalStudent(Section  domain) {
		
		String updateStaffSQL  ="  update section set total_student ="+domain.getTotalStudent() +"  " +
				"where academic_year='"+domain.getAcademicYear()+"' and semester ='"+domain.getSemester()+"'  and section_cd='"+domain.getSectionNumber()+"'  ";
		
		logger.info(" sql:"+updateStaffSQL);
		int result = jdbcTemplate.update(updateStaffSQL );
			
	 
	}
	
	
	
	// ####################### Academic Person ###################
	
	
	@Override
	public Long createAcademicPerson(final Person person) {
		logger.info(" <<<---- Insert Into AcademicPerson ---->>> ");
		
		KeyHolder keyHolder = new GeneratedKeyHolder(); 
		
		final StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO academic_person ( ");
		sb.append( "  person_id,       " );
		sb.append( "  employee_id,    " );
		sb.append( "  citizen_id,     " );
		sb.append( "  thai_name,      " );
		sb.append( "  thai_surname,   " );
		sb.append( "  sex,            " );
		sb.append( "  birthdate,      " ); // Date
		sb.append( "  rate_no,        " );
		sb.append( "  employee_type,  " );
		sb.append( "  position,       " );
		sb.append( "  level,          " );
		sb.append( "  work_line,      " );
		sb.append( "  salary,         " ); // Bigdecimal
		sb.append( "  work_tel_no,    " );
		sb.append( "  belong_to,      " );
		sb.append( "  faculty,        " );
		sb.append( "  working_date,   " ); // Date
		sb.append( "  assign_date,    " ); // Date
		sb.append( "  retire_date,    " ); // Date
		sb.append( "  max_insignia,   " );
		sb.append( "  max_education,  " );
		sb.append( "  tax_no,         " );
		sb.append( "  married_status, " );
		sb.append( "  work_number,    " );
		sb.append( "  insure_no,      " );
		sb.append( "  fund,           " );
		sb.append( "  address,        " );
		sb.append( "  zip_code,       " );
		sb.append( "  tel_no,         " );
		sb.append( "  email,          " );
		sb.append( "  working_status, " );
		sb.append( "  picture,        " );
		sb.append( "  person_type,    " );
		sb.append( "  status,         " );
		sb.append( "  create_by,      " );
		sb.append( "  create_date,    " );
		sb.append( "  update_by,      " );
		sb.append( "  update_date ,   " );
		sb.append( "  faculty_desc ,   " );
		sb.append( "  department_desc  ,  " );
		sb.append( "  academic_year )   " );
		sb.append( " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " );
		sb.append( "  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " );
		sb.append( "  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " );
		sb.append( "  ?, ?," );
		sb.append( " 'A',?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP ,?,?,?) " );

		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {
				PreparedStatement ps = connection.prepareStatement(	sb.toString() , Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1, person.getEmployeeId()  );
				ps.setString( 2 , person.getCitizenId());
				ps.setString( 3 , person.getThaiName());
				ps.setString( 4 , person.getThaiSurname());
				ps.setString( 5 , person.getSex());
				ps.setDate( 6 , BuckWaDateUtils.utilDateToSqlDate(person.getBirthdate()));
				ps.setString( 7 , person.getRateNo());
				ps.setString( 8 , person.getEmployeeType());
				ps.setString( 9 , person.getPosition());
				ps.setString( 10, person.getLevel());
				ps.setString( 11, person.getWorkLine());
				ps.setBigDecimal( 12, person.getSalary());
				ps.setString( 13, person.getWorkTelNo());
				ps.setString( 14, person.getBelongTo());
				ps.setString( 15, person.getFaculty());
				ps.setDate( 16, BuckWaDateUtils.utilDateToSqlDate(person.getWorkingDate()));
				ps.setDate( 17, BuckWaDateUtils.utilDateToSqlDate(person.getAssignDate()));
				ps.setDate( 18, BuckWaDateUtils.utilDateToSqlDate(person.getRetireDate()));
				ps.setString( 19, person.getMaxInsignia());
				ps.setString( 20, person.getMaxEducation());
				ps.setString( 21, person.getTaxNo());
				ps.setString( 22, person.getMarriedStatus());
				ps.setString( 23, person.getWorkNumber());
				ps.setString( 24, person.getInsureNo());
				ps.setString( 25, person.getFund());
				ps.setString( 26, person.getAddress());
				ps.setString( 27, person.getZipCode());
				ps.setString( 28, person.getTelNo());
				ps.setString( 29, person.getEmail());
				ps.setString( 30, person.getWorkingStatus());
				ps.setString( 31, person.getPicture());
				ps.setString( 32, person.getPersonType());
				ps.setString( 33, person.getCreateBy());
				ps.setString( 34, person.getUpdateBy());
				ps.setString( 35, person.getFacultyDesc());
				ps.setString( 36, person.getDepartmentDesc());
				ps.setString( 37, person.getAcademicYear());
				return ps;  
			}
		};
		
		Long returnId = null;
		
		jdbcTemplate.update(preparedStatementCreator,keyHolder); 	
		
		if(null != keyHolder){
			returnId = keyHolder.getKey().longValue();	
		}
		
		return returnId;
	}

	
	@Override
	public int findAcademicPersonByUsername(String username,String academicYear) {
		
		int  rows_totalItem = 0;
		String sqlIsAcademicUserExist = " select count(*) as total_item  from  academic_person where email= '"+StringEscapeUtils.escapeSql(username.trim())+"' and academic_year='"+academicYear+"'" ;		 
		
		rows_totalItem = jdbcTemplate.queryForObject(sqlIsAcademicUserExist,Integer.class); 
		

		
		logger.info(" Academic User Name Count : "+rows_totalItem);
		
		return rows_totalItem;
	}

	
	private static final String SQL_UPDATE_ACADEMIC_PERSON = 
		" UPDATE academic_person " +
		" SET thai_name = ?, " +
		"     thai_surname = ?, " +
		"     sex = ?, " +
		"     birthdate = ?, " +
		"     rate_no = ?, " +
		"     employee_type = ?, " +
		"     position = ?, " +
		"     work_line = ?, " +
		"     level = ?, " +
		"     salary = ?, " +
		"     faculty = ?, " +
		"     assign_date = ?, " +
		"     working_date = ?, " +
		"     work_tel_no = ?, " +
		"     married_status = ?, " +
		"     max_insignia = ?, " +
		"     max_education = ?, " +
		"     working_status = ?, " +
		"     picture = ?, " +
		"     person_type = ?, " +
		"     update_by = ?, " +
		"     faculty_desc= ?, " +
		"     department_desc = ?, " +
		"     academic_year = ?, " +
		"     update_date = CURRENT_TIMESTAMP " +
		" WHERE email = ? ";
	
	@Override
	public void updateAcademicPerson(Person person) {
		int result = jdbcTemplate.update(SQL_UPDATE_ACADEMIC_PERSON, new Object[] {
				person.getThaiName(),
				person.getThaiSurname(),
				person.getSex(),
				person.getBirthdate(),
				person.getRateNo(),
				person.getEmployeeType(),
				person.getPosition(),
				person.getWorkLine(),
				person.getLevel(),
				person.getSalary(),
				person.getFaculty(),
				person.getAssignDate(),
				person.getWorkingDate(),
				person.getWorkTelNo(),
				person.getMarriedStatus(),
				person.getMaxInsignia(),
				person.getMaxEducation(),
				person.getWorkingStatus(),
				person.getPicture(),
				person.getPersonType(),
				person.getUpdateBy(),
				person.getFacultyDesc(),
				person.getDepartmentDesc(),				
				person.getAcademicYear(),
				person.getEmail()
			});
			
			if (logger.isDebugEnabled()) {
				logger.info("result: " + result);
			}
	}
	
	
}
