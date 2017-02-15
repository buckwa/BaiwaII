package com.buckwa.dao.impl.pam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pam.PersonProfileDao;
import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Person;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.MySqlUtil;
import com.buckwa.web.util.AcademicYearUtil;

/*
 @Author : Taechapon Himarat (Su)
 @Create : Aug 5, 2012 2:08:31 PM
 */
@Repository("personProfileDao")
public class PersonProfileDaoImpl implements PersonProfileDao {

	private static Logger logger = LoggerFactory.getLogger(PersonProfileDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	
	private   String SQL_GET_ALL = 
		" SELECT p.person_id, " +
		"   p.employee_id, " +
		"   p.citizen_id, " +
		"   p.thai_name, " +
		"   p.thai_surname, " +
		"   p.sex, " +
		"   p.birthdate, " +
		"   p.rate_no, " +
		"   p.employee_type, " +
		"   p.position, " +
		"   p.LEVEL, " +
		"   p.work_line, " +
		"   p.salary, " +
		"   p.work_tel_no, " +
		"   p.belong_to, " +
		"   p.faculty, " +
		"   p.working_date, " +
		"   p.assign_date, " +
		"   p.retire_date, " +
		"   p.max_insignia, " +
		"   p.max_education, " +
		"   tax_no, " +
		"   p.married_status, " +
		"   p.work_number, " +
		"   p.insure_no, " +
		"   p.fund, " +
		"   p.address, " +
		"   p.zip_code, " +
		"   p.tel_no, " +
		"   p.email, " +
		"   p.working_status, " +
		"   p.picture," +
		"   p.person_type, " +
		"   ( " +
		"     SELECT wpm.workline_code " +
		"     FROM workline_person_mapping wpm " +
		"     WHERE wpm.person_id = p.person_id " +
		"   ) workline_code, " +
		"   p.STATUS, " +
		"   b.user_id " +
		" FROM person p " +
		" INNER JOIN buckwauser b ON p.email = b.USERNAME ";
	
	
	/*
	 * 
  `person_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `thai_name` varchar(255) DEFAULT NULL,
  `thai_surname` varchar(255) DEFAULT NULL,
  `rate_no` varchar(20) DEFAULT NULL,
  `employee_type` varchar(255) DEFAULT NULL,
  `max_education` varchar(255) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `picture` varchar(50) DEFAULT NULL,
  `person_type` smallint(5) unsigned DEFAULT '0',
  `status` varchar(1) DEFAULT 'A',
  `create_by` varchar(45) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(45) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `department_desc` varchar(200) DEFAULT NULL,
  `title_name` varchar(45) DEFAULT NULL,
  `academic_rank` varchar(200) DEFAULT NULL,
  `faculty_desc` varchar(200) DEFAULT NULL,
	 */
	 

	

	
	
	@Override
	public List<Person> getAll() {
		logger.info("- start");
		
		  String SQL_GET_ALL_PBPL = 
				" SELECT p.person_id, " +
				"   p.thai_name, " +
				"   p.thai_surname, " +
				"   p.rate_no, " +
				"   p.employee_type, " +
				"   p.max_education, " +
				"   p.email, " +
				"   p.picture, " +
				"   p.person_type, " +
				"   p.status, " +
				"   p.create_by, " +
				"   p.create_date, " +
				"   p.update_by, " +
				"   p.update_date, " +
				"   p.department_desc, " +
				"   p.title_name, " +
				"   p.academic_rank, " +
				"   p.faculty_desc  " +
				 
				" FROM person_pbp p where p.academic_year='" +academicYearUtil.getAcademicYear()+"'" +
				" INNER JOIN buckwauser b ON p.email = b.USERNAME ";
		//List<Person> resultList = this.jdbcTemplate.query(SQL_GET_ALL, personMapper);
		List<Person> resultList = this.jdbcTemplate.query(SQL_GET_ALL_PBPL, personPBPMapper);
		if (logger.isDebugEnabled()) {
			logger.info("resultList.size(): " + resultList.size());
		}
		return resultList;
	}
	
	@Override
	public List<Person> getPersonByNotInTimeTable(Long yearId,Long semesterId){
		logger.info("- start");
		List<Person> resultList=null;
		try {
			String sql = " SELECT p.person_id, p.employee_id, p.citizen_id, p.thai_name, p.thai_surname, " +
			"   p.sex, p.birthdate, p.rate_no, p.employee_type, p.position, p.level, p.work_line, " +
			"   p.salary, p.work_tel_no, p.belong_to, p.faculty, p.working_date, p.assign_date, " +
			"   p.retire_date, p.max_insignia, p.max_education, tax_no, p.married_status, " +
			"   p.work_number, p.insure_no, p.fund, p.address, p.zip_code, p.tel_no, p.email, " +
			"   (SELECT wpm.workline_code FROM workline_person_mapping wpm WHERE wpm.person_id=p.person_id) workline_code , " +
			"   p.status , b.user_id " +
			" FROM person p inner join buckwauser b on p.email = b.USERNAME " +
			" where b.user_id not in " +
			" ( " +
				" select * from ( " +
				" select pt.user_id from pam_teacher pt " +
				" inner join pam_teacher_teach tt on tt.teacher_id = pt.code " +
				" where pt.year_id="+yearId+" and pt.semester_id="+semesterId+" and pt.user_id is not null " +
				" union " +
				" select pt.user_id from pam_teacher pt " +
				" inner join pam_master_teacher_teach tt on tt.teacher_id = pt.code " +
				" where pt.year_id="+yearId+" and pt.semester_id="+semesterId+" and pt.user_id is not null " +
				" ) A " +
			" ) order by p.thai_name ";
			
			resultList = this.jdbcTemplate.query(sql, new RowMapper<Person>() {
				public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
					Person domain = new Person();
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
					domain.setUserId(rs.getLong("user_id"));
					domain.setFullName(BuckWaUtils.getFullName(domain.getThaiName(), domain.getThaiSurname()));
					return domain;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		
		return resultList;
	}
	
	
	@Override
	public PagingBean getByOffset(PagingBean pagingBean) {
		logger.info("- start");
		
		StringBuilder sql = new StringBuilder();
		List<Object> params = new LinkedList<Object>();
		
		Person person = (Person) pagingBean.get("person");
		
		sql.append(SQL_GET_ALL);
		if (!StringUtils.isEmpty(person.getThaiName())) {
			sql.append(" AND p.thai_name LIKE ? ");
			params.add("%" + StringEscapeUtils.escapeSql(person.getThaiName().trim()) + "%");
		}
		int rows_totalItem = jdbcTemplate.queryForObject(MySqlUtil.genTotalCountSql(sql.toString()), params.toArray(),Integer.class);
		pagingBean.setTotalItems(rows_totalItem);
		
		logger.info(" sql:"+sql);
		
		List<Person> returnList = this.jdbcTemplate.query(MySqlUtil.genPagingSql(sql.toString(), pagingBean), personMapper, params.toArray());
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	
	@Override
	public Person getByUsername(String email,String academicYear) {
		//logger.info("- start");
		
		  String SQL_GET_ALL_PBPL = 
				" SELECT p.person_id, " +
				"   trim(p.thai_name) as thai_name, " +
				"   p.thai_surname, " +
				"   p.rate_no, " +
				"   p.employee_type, " +
				"   p.max_education, " +
				"   p.email, " +
				"   p.picture, " +
				"   p.person_type, " +
				"   p.status, " +
				"   p.create_by, " +
				"   p.create_date, " +
				"   p.update_by, " +
				"   p.update_date, " +
				"   p.department_desc, " +
				"   p.title_name, " +
				"   p.academic_rank, " +
				"   p.working_date, " +
				"   p.birth_date, " +
				"   p.reg_id, " +
				"   p.faculty_desc,  " +
				"   belong_to  " +
				" FROM person_pbp p "+
				" INNER JOIN buckwauser b ON p.email = b.USERNAME ";
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_GET_ALL_PBPL);
		sql.append(" WHERE p.email = '"+email+"'");
		sql.append(" AND p.status = 'A' and p.academic_year='" +academicYear+"'");
		//logger.info("  ######## sql:"+sql.toString());
		Person result=null;
		try {
			
			result = this.jdbcTemplate.queryForObject(sql.toString(), personPBPMapper );
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(" ERROR " + e.getMessage());
		}

		try {
			String facultyCodeSQL =" select code   from faculty where academic_year="+academicYear+" and name ='"+result.getFacultyDesc()+"'";
			//logger.info("  ######## facultyCodeSQL:"+facultyCodeSQL);
			List<String> facultyCodeReturnList =  this.jdbcTemplate.query(facultyCodeSQL, new CodeMapper() );
			if(facultyCodeReturnList!=null&&facultyCodeReturnList.size()>0){
				String facultyCode = facultyCodeReturnList.get(0);
				result.setFacultyCode(facultyCode);
				logger.info("  ########  Found Faculty Code :"+facultyCode+"  name:"+result.getFacultyDesc());
				
				result.setAcademicYearList(academicYearUtil.getAcademicYearList());
			}else{
				logger.info("  ####### Not Found Faculty Code for:"+result.getFacultyDesc());
			}
			
		} catch (EmptyResultDataAccessException e) {
			logger.info(" ERROR " + e.getMessage());
		}
		
	//	BuckWaUser buckwaUser = (BuckWaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	//	buckwaUser.setPersonProfile(result);
		 
		
		return result;
	}
	
	@Override
	public Person getByWorkLineCode(String worklineCode) {
		logger.info("- start");
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_GET_ALL);
		sql.append(" INNER JOIN workline_person_mapping wpm ");
		sql.append("     ON wpm.person_id = p.person_id ");
		sql.append("     AND wpm.workline_code = ?");
		sql.append("     AND wpm.status = 'A' ");
		Person result=null;
		try {
			result = this.jdbcTemplate.queryForObject(sql.toString(), personMapper, new Object[] {
				worklineCode
			});
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Person getByUserId(Long userid,String academicYear) {
		//logger.info("- start user id:"+userid);
		
		 String SQL_GET_BYUSERID = 	" SELECT a.*,b.user_id FROM person_pbp a inner join buckwauser b on a.email = b.username  and b.user_id="+userid+" and a.academic_year='"+academicYear+"'";
		
		// logger.info(" SQL_GET_BYUSERID :"+SQL_GET_BYUSERID);
		Person result=null;
		try {
			result = this.jdbcTemplate.queryForObject(SQL_GET_BYUSERID, personPBPMapper );
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	@Override
	public Person getByPersonId(Long personId,String academicYear) {
		logger.info("- start personId:"+personId);
		
		 String SQL_GET_BYUSERID =   " SELECT a.*,b.user_id  FROM person_pbp a inner join buckwauser b on a.email = b.username  and a.person_id="+personId+" and a.academic_year='"+academicYear+"'";
		 logger.info(" SQL_GET_BYUSERID :"+SQL_GET_BYUSERID);
 
		Person result=null;
		try {
			result = this.jdbcTemplate.queryForObject(SQL_GET_BYUSERID, personPBPMapper );
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	
	private static final String SQL_UPDATE_PERSON = 
		" UPDATE person_pbp " +
		" SET thai_name = ?, " +
		"     thai_surname = ?, " +
		"     citizen_id = ?, " +
		"     sex = ?, " +
		"     birth_date  = ?, " +
		"     rate_no = ?, " +
		"     employee_type = ?, " +
		"     academic_rank = ?, " +
		"     level = ?, " +
		"     workline_code = ?, " +
		"     salary = ?, " +
		"     work_tel_no = ?, " +
		"     belong_to = ?, " +
		"     faculty_desc = ?, " +
		"     department_desc = ?, " +
		"     working_date = ?, " +
		"     assign_date = ?, " +
		"     retire_date = ?, " +
		"     max_insignia = ?, " +
		"     max_education = ?, " +
		"     tax_no = ?, " +
		"     married_status = ?, " +
		"     work_number = ?, " +
		"     insure_no = ?, " +
		"     fund = ?, " +
		"     address = ?, " +
		"     zip_code = ?, " +
		"     work_tel_no = ?, " +
		"     picture = ?, " +
		"     update_by = ?, " +
		"     update_date = CURRENT_TIMESTAMP " +
		" WHERE person_id = ? ";
	
	@Override
	public void update(Person domain) {
		int result = jdbcTemplate.update(SQL_UPDATE_PERSON, new Object[] {
			domain.getThaiName(),
			domain.getThaiSurname(),
			domain.getCitizenId(),
			domain.getSex(),
			domain.getBirthdate(),
			domain.getRateNo(),
			domain.getEmployeeType(),
			domain.getAcademicRank(),
			domain.getLevel(),
			domain.getWorkLine(),
			domain.getSalary(),
			domain.getWorkTelNo(),
			domain.getBelongTo(),
			domain.getFaculty(),
			domain.getDepartment(),
			domain.getWorkingDate(),
			domain.getAssignDate(),
			domain.getRetireDate(),
			domain.getMaxInsignia(),
			domain.getMaxEducation(),
			domain.getTaxNo(),
			domain.getMarriedStatus(),
			domain.getWorkNumber(),
			domain.getInsureNo(),
			domain.getFund(),
			domain.getAddress(),
			domain.getZipCode(),
			domain.getTelNo(),
			domain.getPicture(),
			domain.getEmail(),
			domain.getPersonId()
		});
		
		if (logger.isDebugEnabled()) {
			logger.info("result: " + result);
		}
	}
	
	
	
	
	
	
	private static final String SQL_UPDATE_PBP_PERSON = 
			" UPDATE person_pbp " +
			" SET rate_no = ?, " +
			"     academic_rank = ?, " +
			"     max_education = ? " +
			" WHERE person_id = ? ";
		
		@Override
		public void updatePBPPerson(Person domain) {
			int result = jdbcTemplate.update(SQL_UPDATE_PBP_PERSON, new Object[] {
				domain.getRateNo(),
				domain.getAcademicRank(),
				domain.getMaxEducation(),
		 
				domain.getPersonId()
			});
			
			if (logger.isDebugEnabled()) {
				logger.info("result: " + result);
			}
		}


//	@Override
//	public void updateWorkline(Person person) {
//		final StringBuilder sql = new StringBuilder()
//			.append("UPDATE person ")
//			.append("SET workline_code = ?, ")
//			.append("	update_by = ?, ")
//			.append("	update_date = CURRENT_TIMESTAMP ")
//			.append("WHERE email = ? ")
//			.append("AND status = 'A'");
//		
//		jdbcTemplate.update(sql.toString(), new Object[] {
//				person.getWorklineCode(),
//				person.getEmail(),
//				person.getEmail()
//		});
//	}
	
	@Override
	public PagingBean getByOffsetWithWorkline(PagingBean pagingBean) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new LinkedList<Object>();
		
		Person person = (Person) pagingBean.get("person");
		
		sql.append("SELECT ");
		sql.append("  p.person_id, ");
		sql.append("  p.thai_name, ");
		sql.append("  p.thai_surname, ");
		sql.append("  p.email, ");
		sql.append("  (SELECT wpm.workline_code FROM workline_person_mapping wpm WHERE wpm.person_id=p.person_id) workline_code, ");
		sql.append("  w.workline_name, ");
		sql.append("  p.status ");
		sql.append("FROM ");
		sql.append("  person p ");
		sql.append("LEFT JOIN workline w ON w.workline_code = workline_code ");
	  
		if (!StringUtils.isEmpty(person.getEmail())) {
			sql.append("WHERE ");
			sql.append(" p.thai_name LIKE ? ");
			params.add("%" + StringEscapeUtils.escapeSql(person.getEmail().trim()) + "%");
			
			sql.append(" OR p.thai_surname LIKE ? ");
			params.add("%" + StringEscapeUtils.escapeSql(person.getEmail().trim()) + "%");
			
			sql.append(" OR p.email LIKE ? ");
			params.add("%" + StringEscapeUtils.escapeSql(person.getEmail().trim()) + "%");
		}
		sql.append(" HAVING p.status = 'A'");
		
		int rows_totalItem = jdbcTemplate.queryForObject(MySqlUtil.genTotalCountSql(sql.toString()), params.toArray(),Integer.class);
		pagingBean.setTotalItems(rows_totalItem);
		
		logger.info(" sql:"+sql+params);
		
		List<Person> returnList = this.jdbcTemplate.query(MySqlUtil.genPagingSql(sql.toString(), pagingBean), personWorklineMapper, params.toArray());
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	@Override
	public List<Person> getPersonEstimateUser(Long estimateGroupId){
		logger.info("getPersonEstimateGroup");
		List<Person> resultList=null;
		try {
			String sql = " SELECT p.person_id,p.thai_name, p.thai_surname,p.faculty, " +
			"   b.user_id,A.estimate_group_id,A.name as estimateGroupName " +
			"   FROM person p inner join buckwauser b on p.email = b.USERNAME " +
			"   left outer join " +
			"   ( " +
			"  		select eg.estimate_group_id,eg.name,eu.user_id from estimate_group eg inner join estimate_user eu on eg.estimate_group_id = eu.estimate_group_id where eg.estimate_group_id !="+estimateGroupId+" " +
			"	) A on A.user_id=b.user_id";
			resultList = this.jdbcTemplate.query(sql, personEstimateUserMapper);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		
		return resultList;
	}
	

	@Override
	public List<Person> getPersonEstimateAllByuser(){
		logger.info("getPersonEstimateAllByuser");
		List<Person> resultList=null;
		try {
			String sql  = " SELECT p.person_id,p.thai_name, p.thai_surname,p.faculty, " +
			"   b.user_id " +
			"   FROM person p inner join buckwauser b on p.email = b.USERNAME ";
			resultList = this.jdbcTemplate.query(sql, personEstimateAllUserMapper);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		
		return resultList;
	}
	
	
	
	private static final PersonEstimateAllUserMapper personEstimateAllUserMapper = new PersonEstimateAllUserMapper();
	private static class PersonEstimateAllUserMapper implements RowMapper<Person> {
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			Person domain = new Person();
			domain.setPersonId(rs.getLong("person_id"));
			domain.setThaiName(rs.getString("thai_name"));
			domain.setThaiSurname(rs.getString("thai_surname"));
			domain.setUserId(rs.getLong("user_id"));
			domain.setFaculty(rs.getString("faculty"));
			domain.setFullName(BuckWaUtils.getFullName(domain.getThaiName(), domain.getThaiSurname()));
			return domain;
		}
	}
	
	private static final PersonEstimateUserMapper personEstimateUserMapper = new PersonEstimateUserMapper();
	private static class PersonEstimateUserMapper implements RowMapper<Person> {
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			Person domain = new Person();
			domain.setPersonId(rs.getLong("person_id"));
			domain.setThaiName(rs.getString("thai_name"));
			domain.setThaiSurname(rs.getString("thai_surname"));
			domain.setUserId(rs.getLong("user_id"));
			domain.setFaculty(rs.getString("faculty"));
			domain.setFullName(BuckWaUtils.getFullName(domain.getThaiName(), domain.getThaiSurname()));
			domain.setEstimateGroupName(rs.getString("estimateGroupName"));
			return domain;
		}
	}
	
	private static final PersonWorklineMapper personWorklineMapper = new PersonWorklineMapper();
	private static class PersonWorklineMapper implements RowMapper<Person> {
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			Person domain = new Person();
			domain.setPersonId(rs.getLong("person_id"));
			domain.setThaiName(rs.getString("thai_name"));
			domain.setThaiSurname(rs.getString("thai_surname"));
			domain.setEmail(rs.getString("email"));
			domain.setWorklineCode(rs.getString("workline_code"));
			domain.setWorklineName(rs.getString("workline_name"));
			return domain;
		}
	}
	
	private static final PersonMapper personMapper = new PersonMapper();
	private static class PersonMapper implements RowMapper<Person> {
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			Person domain = new Person();
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
			
			String imagePath = rs.getString("picture");
			if(imagePath==null||imagePath.length()==0){
				
				imagePath = "/project/pbp/profile_picture/NoPicture.jpg";
			}
			
			domain.setPicture(imagePath);
			
		 
			domain.setWorklineCode(rs.getString("workline_code"));
			domain.setBirthdateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(domain.getBirthdate()));
			domain.setUserId(rs.getLong("user_id"));
			domain.setFullName(BuckWaUtils.getFullName(domain.getThaiName(), domain.getThaiSurname()));
			domain.setPersonType(rs.getString("person_type"));
			logger.info(" ### Picture :"+rs.getShort("picture"));
			return domain;
		}
	}
	
	
	
	private static final PersonPBPMapper personPBPMapper = new PersonPBPMapper();
	private static class PersonPBPMapper implements RowMapper<Person> {
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			/*
			 * 
		  `person_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
		  `thai_name` varchar(255) DEFAULT NULL,
		  `thai_surname` varchar(255) DEFAULT NULL,
		  `rate_no` varchar(20) DEFAULT NULL,
		  `employee_type` varchar(255) DEFAULT NULL,
		  `max_education` varchar(255) DEFAULT NULL,
		  `email` varchar(100) DEFAULT NULL,
		  `picture` varchar(50) DEFAULT NULL,
		  `person_type` smallint(5) unsigned DEFAULT '0',
		  `status` varchar(1) DEFAULT 'A',
		  `create_by` varchar(45) DEFAULT NULL,
		  `create_date` datetime DEFAULT NULL,
		  `update_by` varchar(45) DEFAULT NULL,
		  `update_date` datetime DEFAULT NULL,
		  `department_desc` varchar(200) DEFAULT NULL,
		  `title_name` varchar(45) DEFAULT NULL,
		  `academic_rank` varchar(200) DEFAULT NULL,
		  `faculty_desc` varchar(200) DEFAULT NULL,
			 */
			Person domain = new Person();
			domain.setPersonId(rs.getLong("person_id"));
			domain.setThaiName(rs.getString("thai_name"));
			domain.setThaiSurname(rs.getString("thai_surname"));
			domain.setRateNo(rs.getString("rate_no"));
			domain.setEmployeeType(rs.getString("employee_type"));
			
			domain.setEmployeeTypeNo(rs.getString("employee_type")); // set for employeeType // FIXME
			domain.setMaxEducation(rs.getString("max_education"));
			domain.setEmail(rs.getString("email"));
			
			String imagePath = rs.getString("picture");
			if(imagePath==null||imagePath.length()==0){
				
				imagePath = "/project/pbp/profile_picture/NoPicture.jpg";
			}
			
			domain.setPicture(imagePath);
			//domain.setPicture(rs.getString("picture"));
			domain.setPersonType(rs.getString("person_type"));
			domain.setStatus(rs.getString("status"));
//			domain.setPosition(rs.getString("academic_rank"));
			domain.setBelongTo(rs.getString("belong_to"));
			domain.setDepartmentDesc(rs.getString("department_desc"));
			domain.setTitleName(rs.getString("title_name"));
			domain.setAcademicRank(rs.getString("academic_rank"));
			domain.setFacultyDesc(rs.getString("faculty_desc"));
			domain.setRegId(rs.getString("reg_id"));
			domain.setWorkingDate(rs.getDate("working_date"));
			domain.setBirthdate(rs.getDate("birth_date"));

			//logger.info(" ### picture:"+rs.getString("picture"));
			logger.info(" ###   "+rs.getString("email")+":"+rs.getString("thai_name")+" "+rs.getString("thai_surname")+" : "+rs.getString("faculty_desc")+" :"+rs.getString("department_desc"));
			return domain;
		}
	}
	
	
	private static final String SQL_GET_PERSONTYPE_BY_FACULTY =
		" SELECT person_type AS value " +
		" FROM person " +
		" WHERE faculty = ? " +
		" GROUP BY person_type ";
	
	@Override
	public List<String> getPersonTypeListByFaculty(String facultyId) {
		logger.info("- start");
		
		List<String> resultList = this.jdbcTemplate.query(SQL_GET_PERSONTYPE_BY_FACULTY.toString(), new Object[] {
			facultyId
		}, stringValueMapper);
		
		if (logger.isDebugEnabled()) {
			logger.info("sql: " + SQL_GET_PERSONTYPE_BY_FACULTY);
			logger.info("facultyId: " + facultyId);
			if (null != resultList) {
				logger.info("resultList: " + resultList.size());
				logger.info("resultList.size(): " + Arrays.toString(resultList.toArray()));
			}
		}
		
		return resultList;
	}
	
	
	@Override
	public String getFacultyIdByUsername(String username) {
		logger.info("- start");
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT faculty AS value ");
		sql.append(" FROM person ");
		sql.append(" WHERE email = ? ");
		
		String facultyId = this.jdbcTemplate.queryForObject(sql.toString(), new Object[] {
			username
		}, stringValueMapper);
		
		if (logger.isDebugEnabled()) {
			logger.info("sql: " + sql);
			logger.info("username: " + username);
			logger.info("facultyId: " + facultyId);
		}
		
		return facultyId;
	}
	
	
	@Override
	public List<String> getUserIdListByFacultyAndPersonType(List<String> facultyIdList, String personType) {
		logger.info("- start");
		
		StringBuilder sql = new StringBuilder();
		List<String> params = new ArrayList<String>();
		
		sql.append(" SELECT u.user_id AS value ");
		sql.append(" FROM person p ");
		sql.append("   INNER JOIN buckwauser u ON p.email = u.username ");
		sql.append(" WHERE 1 = 1 ");
		sql.append("   AND p.faculty IN ( ");
		for (int i = 0; i < facultyIdList.size(); i++) {
			sql.append("?,");
			params.add(facultyIdList.get(i));
		}
		if (facultyIdList != null && !facultyIdList.isEmpty()) {
			sql.deleteCharAt(sql.length() - 1);
		}
		sql.append(" ) ");
		sql.append("   AND p.person_type = ? ");
		params.add(personType);
		
		List<String> resultList = this.jdbcTemplate.query(sql.toString(), params.toArray(),	stringValueMapper);
		
		if (logger.isDebugEnabled()) {
			logger.info("sql: " + sql);
			logger.info("facultyId: " + Arrays.toString(facultyIdList.toArray()));
			logger.info("personType: " + personType);
			if (null != resultList) {
				logger.info("resultList: " + resultList.size());
				logger.info("resultList.size(): " + Arrays.toString(resultList.toArray()));
			}
		}
		
		return resultList;
	}
	

	private static final StringMapper stringValueMapper = new StringMapper();
	private static class StringMapper implements RowMapper<String> {
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String value = String.valueOf(rs.getLong("value"));
			return value;
		}
	}
	
	private static class CodeMapper implements RowMapper<String> {
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String value =  rs.getString("code");
			return value;
		}
	}

	
	@Override
	public void updateLeaveAccumulate(Person domain) {
		  String SQL_UPDATE_ACCUMULATE = 
				" UPDATE person " +
				" SET leave_accumulate = ?, " +		 
				"     update_date = CURRENT_TIMESTAMP " +
				" WHERE person_id = ? ";
		int result = jdbcTemplate.update(SQL_UPDATE_ACCUMULATE, new Object[] {
			domain.getLeaveAccumulate(),	 
			domain.getPersonId()
		});
		
		if (logger.isDebugEnabled()) {
			logger.info("result: " + result);
		}
	}
	
	
}
