package com.buckwa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.PresentationDirection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.buckwa.dao.impl.PersonDetialDao;
import com.buckwa.domain.UserSession;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.PagingMessage;
import com.buckwa.domain.webboard.Message;
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
	public List<Message> getMessageByUser(String username){
		List<Message> messagelist = new ArrayList<>();
		
		messagelist = personDetialDao.getMessageByUser(username);
		
		return messagelist;
		
	}
	public List<Message> getMessageByKPIId(String username, String kpiId){
		List<Message> messagelist = new ArrayList<>();
		
		messagelist = personDetialDao.getMessageByKPIId(username,kpiId);
		
		return messagelist;
	}
	public List<Message> getMessageByHead(String username,String Department){
		List<Message> messagelist = new ArrayList<>();
		System.out.println("GetMessageByHead is " +username+" and department is"+ Department );
		
		messagelist = personDetialDao.getMessageByHead(username,Department);
		
		return messagelist;
	}
	public int countMessage (String criteria){
		int totaMessage = personDetialDao.countMessage(criteria);
		return totaMessage;
	}
	
	public List<Message> getMessageByHeadAll(PagingMessage request){
		List<Message> messagelist = new ArrayList<>();
		
		messagelist = personDetialDao.getMessageByHeadAll(request);
		
		return messagelist;
		
	}
	public BuckWaResponse updateflagMessage(String MessageID){
		BuckWaResponse response  = new BuckWaResponse();
		personDetialDao.updateFlagMessage(MessageID);
		return response;
		
	}
	

}
