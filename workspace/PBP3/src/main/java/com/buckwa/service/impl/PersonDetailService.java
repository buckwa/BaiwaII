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
	public List<Message> getMessageByUser(String username, String year){
		List<Message> messagelist = new ArrayList<>();
		
		messagelist = personDetialDao.getMessageByUser(username,year);
		
		return messagelist;
		
	}
	public List<Message> getMessageByKPIId(String username, String kpiId, String year){
		List<Message> messagelist = new ArrayList<>();
		
		messagelist = personDetialDao.getMessageByKPIId(username,kpiId,year);
		
		return messagelist;
	}
	public List<Message> getMessageByHead(String username,String Department,String year){
		List<Message> messagelist = new ArrayList<>();
		System.out.println("GetMessageByHead is " +username+" and department is"+ Department );
		
		messagelist = personDetialDao.getMessageByHead(username,Department,year);
		
		return messagelist;
	}
	public int countMessage (String criteria, String year){
		int totaMessage = personDetialDao.countMessage(criteria,year);
		return totaMessage;
	}
	
	public List<Message> getMessageByHeadAll(PagingMessage request, String year){
		List<Message> messagelist = new ArrayList<>();
		
		messagelist = personDetialDao.getMessageByHeadAll(request,year);
		
		return messagelist;
		
	}
	public BuckWaResponse updateflagMessage(String MessageID){
		BuckWaResponse response  = new BuckWaResponse();
		personDetialDao.updateFlagMessage(MessageID);
		return response;
		
	}
	

}
