package com.buckwa.service.impl.webboard;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.webboard.WebboardTopicDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.util.Unit;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.domain.webboard.Message;
import com.buckwa.domain.webboard.Topic;
import com.buckwa.service.intf.webboard.WebboardTopicService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.webboard.WebboardConstants;


@Service("webboardTopicService") 
public class WebboardTopicServiceImpl implements WebboardTopicService {
	private static Logger logger = LoggerFactory.getLogger(WebboardTopicServiceImpl.class);
	@Autowired
	private WebboardTopicDao webboardTopicDao;
	
 
	
	
	@Override
	public BuckWaResponse getAll() {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List<Topic> topicList = webboardTopicDao.getAll();			
			response.addResponse("topicList",topicList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse create(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			Topic topic = (Topic)request.get("topic");					
			boolean isTopicNameExist = webboardTopicDao.isExist(topic.getTopicHeader());
			logger.info(" isTopicNameExist:"+isTopicNameExist);
			if(isTopicNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				webboardTopicDao.create(topic);
			}

		}catch(DuplicateKeyException dx){			
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	


	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse update(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			Topic topic = (Topic)request.get("topic");
			boolean isTopicNameExist = webboardTopicDao.isExistForUpdate( topic.getTopicHeader(),topic.getTopicId());
			if(isTopicNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				webboardTopicDao.update(topic);	
				response.setSuccessCode("S002");	
			}
						
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	 
		return response;
	}


	@Override
	public BuckWaResponse getByOffset(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			 
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");			 
			PagingBean returnBean = webboardTopicDao.getAllByOffset(pagingBean);		
 
			 
			response.addResponse("pagingBean",returnBean);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}

	@Override
	public BuckWaResponse getAllByOffsetAndCategoryName(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{								 
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");		
			Topic topic = (Topic)request.get("topic");	
			//String categoryName =  request.get("categoryName")==null?"":(String)request.get("categoryName");
			//String status =  request.get("status")==null?"":(String)request.get("status"); 
			PagingBean returnBean = webboardTopicDao.getAllByOffsetAndCategoryName(pagingBean,topic);				 
			response.addResponse("pagingBean",returnBean);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}


	@Override
	public BuckWaResponse getById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" TopicServiceImpl.getTopicById");
			String id =  request.get("topicId")+"";			
			Topic topic = webboardTopicDao.getById(id);						
			if(topic!=null){
				response.addResponse("topic",topic);
			}else{
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

	
	@Override
	public BuckWaResponse getTopicByArtifaceId(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" TopicServiceImpl.getTopicById");
			String artifaceId = request.get("artifaceId")+"";	
			String artifaceType = request.get("artifaceType")+"";	
			Topic topic = webboardTopicDao.getTopicByArtifaceId(artifaceId,artifaceType);						
			if(topic!=null){
				response.addResponse("topic",topic);
			}else{
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	@Override
	public BuckWaResponse deleteById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String id =  request.get("topicId")+""; 
			webboardTopicDao.deleteById(id);	 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse disable(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String id = request.get("topicId")+""; 
			webboardTopicDao.disable(id);	 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse enable(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String id =  request.get("topicId")+""; 
			webboardTopicDao.enable(id);	 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	
	@Override
	public BuckWaResponse disableMessage(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
		 
			String messageId = request.get("messageId")+""; 
			webboardTopicDao.disableMessage(messageId);	 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	
	@Override
	public BuckWaResponse enableMessage(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			 
			String messageId = request.get("messageId")+""; 
			webboardTopicDao.enableMessage(messageId);	 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	@Override
	public BuckWaResponse viewTopic(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" TopicServiceImpl.getTopicById");
			String id =  request.get("topicId")+"";	
			String messageStatus =  request.get("messageStatus")==null?WebboardConstants.WEBBOARD_ALL:(String)request.get("messageStatus");
			Topic topic = webboardTopicDao.viewTopic(id,messageStatus);						
			if(topic!=null){
				response.addResponse("topic",topic);
			}else{
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse replyMessage(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" TopicServiceImpl.getTopicById");
			Message message = (Message)request.get("message");	
			Topic topicReturn = webboardTopicDao.replyMessage(message);						
			if(topicReturn!=null){
				response.addResponse("topic",topicReturn);
			}else{
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse replyPBPMessage(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" ");
			Message message = (Message)request.get("message");	
			 webboardTopicDao.replyPBPMessage(message);						
			 logger.info(" replyMessage Success");
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
 
 
	@Override
	public BuckWaResponse list(BuckWaRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Topic> getAllTopic() {
		List returnList = new ArrayList();
		try{		
			returnList = webboardTopicDao.getAll();						
			
		}catch(Exception ex){
			ex.printStackTrace();		
		}

		return returnList;
	}

}
