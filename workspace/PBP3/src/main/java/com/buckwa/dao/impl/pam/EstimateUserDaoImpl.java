package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.pam.EstimateGroupDao;
import com.buckwa.dao.intf.pam.EstimateUserDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.EstimateUser;
import com.buckwa.domain.pam.EstimateUser;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pam.WorkPerson;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("estimateUserDao")
public class EstimateUserDaoImpl implements EstimateUserDao {
	
	private static Logger logger = LoggerFactory.getLogger(EstimateUserDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public EstimateUser create(EstimateUser obj) throws Exception{
		logger.info(" #EstimateUserDaoImpl.create # ");
		final EstimateUser finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into estimate_user (" +
								"estimate_group_id," +
								"user_id,"+
								"create_by," +
								"created_date" +
								") " +
								"values (?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, finalObj.getEstimateGroupId());
				ps.setLong(2, finalObj.getUserId());
				ps.setString(3, finalObj.getCreateBy());
				ps.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		}, keyHolder);
		
		finalObj.setEstimateUserId(keyHolder.getKey().longValue());
		
		return finalObj;
	}
	
	@Override
	public void delete(Long estimateGroupId) throws Exception{	
		logger.info(" #EstimateUserDaoImpl.delete # ");
		this.jdbcTemplate.update(" delete from estimate_user where estimate_group_id = "+estimateGroupId);
	}	
	
	@Override
	public List<EstimateUser> estimateUserList(Long estimateGroupId,boolean view) {		 		
		logger.info(" #EstimateUserDaoImpl.estimateUserList # ");
		StringBuffer sb = new StringBuffer();
		String sql = " SELECT p.person_id,p.thai_name, p.thai_surname, " +
		"   A.user_id,A.estimate_group_id " +
		"   FROM person p inner join buckwauser b on p.email = b.USERNAME " +
		"   left outer join " +
		"   ( " +
		"  		select eg.estimate_group_id,eg.name,eu.user_id from estimate_group eg inner join estimate_user eu on eg.estimate_group_id = eu.estimate_group_id where eu.estimate_group_id ="+estimateGroupId+" " +
		"	) A on A.user_id=b.user_id";
		if(view){
			sql = " SELECT p.person_id,p.thai_name, p.thai_surname, " +
			"   A.user_id,A.estimate_group_id " +
			"   FROM person p inner join buckwauser b on p.email = b.USERNAME " +
			"   inner join " +
			"   ( " +
			"  		select eg.estimate_group_id,eg.name,eu.user_id from estimate_group eg inner join estimate_user eu on eg.estimate_group_id = eu.estimate_group_id where eu.estimate_group_id ="+estimateGroupId+" " +
			"	) A on A.user_id=b.user_id";
		}
		List<EstimateUser> resultList = null;
		try {
			resultList = this.jdbcTemplate.query(sql,new RowMapper<EstimateUser>() {
				public EstimateUser mapRow(ResultSet rs, int rowNum) throws SQLException {
					EstimateUser domain = new EstimateUser();
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
	
	
	
}

