package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pam.EstimateByUserDao;
import com.buckwa.domain.pam.EstimateByUser;
import com.buckwa.domain.pam.Person;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("estimateByUserDao")
public class EstimateByUserDaoImpl implements EstimateByUserDao {
	
	private static Logger logger = LoggerFactory.getLogger(EstimateByUserDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public EstimateByUser create(EstimateByUser obj) throws Exception{
		logger.info(" #EstimateByUserDaoImpl.create # ");
		final EstimateByUser finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into estimate_by_user (" +
								"estimate_group_id," +
								"user_id,"+
								"type,"+
								"create_by," +
								"created_date" +
								") " +
								"values (?,?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, finalObj.getEstimateGroupId());
				ps.setLong(2, finalObj.getUserId());
				ps.setInt(3, finalObj.getType());
				ps.setString(4, finalObj.getCreateBy());
				ps.setTimestamp(5, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		}, keyHolder);
		
		finalObj.setEstimateByUserId(keyHolder.getKey().longValue());
		
		return finalObj;
	}
	
	@Override
	public void delete(Long estimateGroupId) throws Exception{	
		this.jdbcTemplate.update(" delete from estimate_by_user where estimate_group_id = "+estimateGroupId);
	}	
	

	
	@Override
	public List<EstimateByUser> estimateByUserList(Long estimateGroupId,int type,boolean view) {		 		
		logger.info(" #EstimateByUserDaoImpl.estimateByUserList(Long estimateGroupId,boolean view) # ");
		String sql = " SELECT p.person_id,p.thai_name, p.thai_surname, " +
		"   A.user_id,A.estimate_group_id,A.name as estimateGroupName " +
		"   FROM person p inner join buckwauser b on p.email = b.USERNAME " +
		"   left outer join " +
		"   ( " +
		"  		select eg.estimate_group_id,eg.name,eu.user_id from estimate_group eg inner join estimate_by_user eu on eg.estimate_group_id = eu.estimate_group_id where eu.type="+type+" and eu.estimate_group_id ="+estimateGroupId+" " +
		"	) A on A.user_id=b.user_id";
		if(view){
			sql = " SELECT p.person_id,p.thai_name, p.thai_surname, " +
			"   A.user_id,A.estimate_group_id,A.name as estimateGroupName " +
			"   FROM person p inner join buckwauser b on p.email = b.USERNAME " +
			"   inner join " +
			"   ( " +
			"  		select eg.estimate_group_id,eg.name,eu.user_id from estimate_group eg inner join estimate_by_user eu on eg.estimate_group_id = eu.estimate_group_id  where eu.type="+type+" and  eu.estimate_group_id ="+estimateGroupId+" " +
			"	) A on A.user_id=b.user_id";
		}
		List<EstimateByUser> resultList = null;
		try {
			resultList = this.jdbcTemplate.query(sql,new RowMapper<EstimateByUser>() {
				public EstimateByUser mapRow(ResultSet rs, int rowNum) throws SQLException {
					EstimateByUser domain = new EstimateByUser();
					domain.setEstimateGroupId(rs.getLong("estimate_group_id"));
					domain.setUserId(rs.getLong("user_id"));
					Person person = new Person();
					person.setFullName(BuckWaUtils.getFullName(rs.getString("thai_name"), rs.getString("thai_surname")));
					domain.setPerson(person);
					return domain;
				}
				});			
		} catch (EmptyResultDataAccessException e) {

		}
		return resultList;
	}
	
	@Override
	public List<Person> getEstimateUserList(Long byUserId){
		logger.info(" #EstimateByUserDaoImpl.getEstimateUserList(Long byUserId) # ");
		String sql = 
			" SELECT p.person_id, p.employee_id, p.citizen_id, p.thai_name, p.thai_surname, " +
			"   p.sex, p.birthdate, p.rate_no, p.employee_type, p.position, p.level, p.work_line, " +
			"   p.salary, p.work_tel_no, p.belong_to, p.faculty, p.working_date, p.assign_date, " +
			"   p.retire_date, p.max_insignia, p.max_education, tax_no, p.married_status, " +
			"   p.work_number, p.insure_no, p.fund, p.address, p.zip_code, p.tel_no, p.email, " +
			"   p.status , bu.user_id " +
			" from person p "+
			" inner join buckwauser bu on bu.username = p.email "+
			" inner join estimate_user eu on eu.user_id = bu.user_id "+
			" inner join estimate_by_user ebu on eu.estimate_group_id = ebu.estimate_group_id "+
			" where ebu.user_id="+byUserId;

		List<Person> resultList = null;
		try {
			resultList = this.jdbcTemplate.query(sql,personMapper);			
		} catch (EmptyResultDataAccessException e) {
			
		}
		return resultList;
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
			domain.setBirthdateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(domain.getBirthdate()));
			domain.setUserId(rs.getLong("user_id"));
			domain.setFullName(BuckWaUtils.getFullName(domain.getThaiName(), domain.getThaiSurname()));
			return domain;
		}
	}
	
	
	
}

