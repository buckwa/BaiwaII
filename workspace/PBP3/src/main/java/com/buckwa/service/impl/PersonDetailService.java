package com.buckwa.service.impl;

import javax.print.attribute.standard.PresentationDirection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.buckwa.dao.impl.PersonDetialDao;
import com.buckwa.domain.UserSession;
import com.buckwa.web.util.AcademicYearUtil;

@Service("personDetailService")
public class PersonDetailService {

	
	
	@Autowired
	private PersonDetialDao personDetialDao;
	
	public UserSession GetUserSession(String username){
		
		UserSession userSession =new UserSession();
		userSession= personDetialDao.getUsername(username);
		
		return userSession;
	}
	
	

}
