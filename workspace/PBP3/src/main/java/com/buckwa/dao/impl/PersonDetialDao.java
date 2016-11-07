package com.buckwa.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.buckwa.domain.UserSession;

@Repository("personDetailDao")
public class PersonDetialDao {
	private static  Logger logger = LoggerFactory.getLogger(PersonDetialDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;	

	@SuppressWarnings("unchecked")
	public UserSession getUsername(String username) {
		// TODO Wongwithit Dav
		String sql = "SELECT p.* , b.* ,p.faculty AS faculty_code FROM person p  INNER JOIN buckwauser b ON p.email = b.USERNAME   WHERE  username = '"+username+"' and enable =1 ";   
		
		
		
        logger.info(" loadUserByUsername:"+sql);	
        
		//UserSession userSession = null;
		UserSession userSession =(UserSession) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(UserSession.class));
		
		
		return userSession;
	}

}
