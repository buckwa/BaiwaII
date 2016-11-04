package baiwa.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import baiwa.entity.UserAttempt;

@Repository("userAttemptDao")
public class UserAttemptDao {
	
	@Autowired JdbcTemplate jdbcTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public UserAttempt findByUsername (String username){
		username = username+"@kmitl.ac.th";
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT user_id , USERNAME, password ");
		sql.append(" FROM buckwauser ");
		sql.append(" WHERE username =  ? ");
		UserAttempt  userAttempt = null;
		try {
		userAttempt = (UserAttempt)jdbcTemplate.queryForObject(sql.toString(),new Object[] { username 
				},new BeanPropertyRowMapper<UserAttempt>(UserAttempt.class)) ;
		
		}catch (EmptyResultDataAccessException e) 
		{
			logger.warn("Can not find data with username: " + username);
		}
		logger.info("Returning user=" + userAttempt);
		return userAttempt;
		
	}

}
