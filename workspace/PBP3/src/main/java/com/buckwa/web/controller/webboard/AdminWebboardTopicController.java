package com.buckwa.web.controller.webboard;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.validator.webboard.ReplyMessageValidator;
import com.buckwa.domain.validator.webboard.TopicValidator;
import com.buckwa.domain.webboard.Message;
import com.buckwa.domain.webboard.Topic;
import com.buckwa.service.intf.webboard.WebboardTopicService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.webboard.WebboardConstants;

@Controller
@RequestMapping("/admin/webboard/topic")
@SessionAttributes(types = Topic.class)
public class AdminWebboardTopicController {
	private static Logger logger = LoggerFactory.getLogger(AdminWebboardTopicController.class);
	@Autowired
	private WebboardTopicService  webboardTopicService;
	
	@ModelAttribute("topic") 
	public Topic topic() { 
		logger.info(" Call @ModelAttribute Topic ");
		return new Topic(); 
	} 	
	
	@RequestMapping("init.htm")
	public ModelAndView init( ) {
		logger.info(" # TopicManagementController.init ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("adminwebboardtopicList");		
		try{	
			Topic topic = new Topic();		 	 	
			PagingBean bean = new PagingBean(); 
			mav.addObject("pagingBean", bean);	
			mav.addObject("topic", topic);	 
			int offset = 0;
			bean.setOffset(offset);	
			bean.put("topic", new Topic()); 
			
			BuckWaRequest request = new BuckWaRequest();		 
			request.put("pagingBean", bean); 
			request.put("topic", topic);
			
			BuckWaResponse response = webboardTopicService.getAllByOffsetAndCategoryName(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode()); 
			}		
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("adminwebboardtopicCreate");
		try{	
			Topic topic = new Topic();		 
			mav.addObject("topic", topic);	
		
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createTopic(@ModelAttribute Topic topic, BindingResult result) {	
		ModelAndView mav = new ModelAndView();
		try{			
			logger.info(" createTopic topic xx: "+BeanUtils.getBeanString(topic));
			new TopicValidator().validate(topic, result);			
			if (result.hasErrors()) {				
				mav.setViewName("adminwebboardtopicCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				topic.setStatus("1");
				topic.setCreateBy(BuckWaUtils.getUserNameFromContext());
				request.put("topic", topic);
				logger.info(" createTopic topic:"+BeanUtils.getBeanString(topic));
				BuckWaResponse response = webboardTopicService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					mav.addObject("topic", new Topic());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("adminwebboardtopicCreateSuccess");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("adminwebboardtopicCreate");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	@RequestMapping(value="search.htm", method = RequestMethod.GET)
	public ModelAndView searchGet(HttpServletRequest httpRequest ,@ModelAttribute PagingBean bean) {
		logger.info(" # TopicManagementController.search");
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("adminwebboardtopicList");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("topic", new Topic());	
			request.put("pagingBean", bean);	
			bean.put("topic", new Topic());			 
			request.put("topic", new Topic());
			BuckWaResponse response = webboardTopicService.getAllByOffsetAndCategoryName(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			 
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="search.htm", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute Topic topic,@ModelAttribute PagingBean bean) {
		logger.info(" # TopicManagementController.search topic:"+BeanUtils.getBeanString(topic));
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("adminwebboardtopicList");
		try{			 
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);			 
			BuckWaRequest request = new BuckWaRequest();		
			request.put("pagingBean", bean);	
			bean.put("topic", topic); 
			request.put("topic", topic);
			BuckWaResponse response = webboardTopicService.getAllByOffsetAndCategoryName(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);	
				mav.addObject("doSearch","true");
			}else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			 
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("topicId") String topicId) {
		logger.info(" # TopicManagementController.initEdit topicId:"+topicId);
		ModelAndView mav = new ModelAndView();
		try{	
		BuckWaRequest request = new BuckWaRequest();
		request.put("topicId", topicId);	
		BuckWaResponse response = webboardTopicService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			logger.info("  Success");
			Topic topic = (Topic)response.getResObj("topic");
			logger.info(" topic return :"+BeanUtils.getBeanString(topic));
			mav.addObject("topic", topic);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("adminwebboardtopicEdit");
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Topic topic, BindingResult result) {		
		logger.info(" # TopicManagementController.submitEdit topic:"+BeanUtils.getBeanString(topic));
		ModelAndView mav = new ModelAndView();
		try{
			logger.info(" topic:"+BeanUtils.getBeanString(topic));
			new TopicValidator().validate(topic, result);
			
			if (result.hasErrors()) {
				logger.info("  Validate Error");
				mav.setViewName("adminwebboardtopicEdit");
			}else {
	
				logger.info("  Validate Success , Do create Topic ");
				BuckWaRequest request = new BuckWaRequest();
				request.put("topic", topic);
				BuckWaResponse response = webboardTopicService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					logger.info("  Success");					
					mav.addObject("topic", new Topic());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("adminwebboardtopicEdit");					
					
				}else {
					logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("adminwebboardtopicEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("topicId") String topicId,HttpServletRequest httpRequest,@ModelAttribute Topic topic,@ModelAttribute PagingBean bean) {
		logger.info(" # TopicManagementController.delete topicId:"+topicId);
		ModelAndView mav = new ModelAndView();		
		try{
			mav.setViewName("adminwebboardtopicList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("topicId", topicId);	
			BuckWaResponse response = webboardTopicService.deleteById(request);
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				mav.addObject("successCode","S004"); 		 				
			}else {	
				mav.addObject("errorCode", response.getErrorCode()); 
				mav.addObject("pagingBean", bean);	
			}	
			
			// Search Again
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);					 	
			request.put("pagingBean", bean);	
			bean.put("topic", topic);	
			
			request.put("topic", topic);
			response = webboardTopicService.getAllByOffsetAndCategoryName(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {			 
				mav.addObject("errorCode", response.getErrorCode()); 
			}		
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
	
		return mav;
	}	
	
	
	@RequestMapping(value="viewTopic.htm", method = RequestMethod.GET)
	public ModelAndView viewTopic(@RequestParam("topicId") String topicId) {
		logger.info(" # TopicManagementController.viewTopic topicId:"+topicId);
		ModelAndView mav = new ModelAndView();
		try{	
		BuckWaRequest request = new BuckWaRequest();
		request.put("topicId", topicId); 
		request.put("messageStatus", WebboardConstants.WEBBOARD_ALL);
		BuckWaResponse response = webboardTopicService.viewTopic(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			logger.info("  Success");
			Topic topic = (Topic)response.getResObj("topic");
			logger.info(" topic return :"+BeanUtils.getBeanString(topic));
			mav.addObject("topic", topic);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("adminViewTopic");
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	@RequestMapping(value="replyMessage.htm", method = RequestMethod.POST)
	public ModelAndView replyMessage(@ModelAttribute Topic topic, BindingResult result) {	
		ModelAndView mav = new ModelAndView();
		try{			
			logger.info(" createTopic topic xx: "+BeanUtils.getBeanString(topic));
			new ReplyMessageValidator().validate(topic, result);			
			if (result.hasErrors()) {				
				mav.setViewName("viewTopic");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				topic.setStatus("1");
				
				Message newMessage = new Message();
				newMessage.setMessageDetail(topic.getReplyMessage());
				newMessage.setTopicId(topic.getTopicId());
				newMessage.setStatus("1");				
				newMessage.setCreateBy(BuckWaUtils.getUserNameFromContext());
				request.put("message", newMessage);
				logger.info(" replyMessage newMessage:"+BeanUtils.getBeanString(newMessage));
				BuckWaResponse response = webboardTopicService.replyMessage(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
					 topic = (Topic)response.getResObj("topic");
					mav.addObject("topic", topic);
					topic.setReplyMessage("");
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("viewTopic");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("viewTopic");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	
	
	
	
	@RequestMapping(value="disable.htm", method = RequestMethod.GET)
	public ModelAndView disable(@RequestParam("topicId") String topicId,HttpServletRequest httpRequest,@ModelAttribute Topic topic,@ModelAttribute PagingBean bean) {
		logger.info(" # TopicManagementController.disable topicId:"+topicId);
		ModelAndView mav = new ModelAndView();		
		try{
			mav.setViewName("adminwebboardtopicList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("topicId", topicId);	
			BuckWaResponse response = webboardTopicService.disable(request);
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				mav.addObject("successCode","S004"); 		 				
			}else {	
				mav.addObject("errorCode", response.getErrorCode()); 
				mav.addObject("pagingBean", bean);	
			}	
			
			// Search Again
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);					 	
			request.put("pagingBean", bean);	
			bean.put("topic", topic);		
			
			request.put("topic", topic);
			response = webboardTopicService.getAllByOffsetAndCategoryName(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {			 
				mav.addObject("errorCode", response.getErrorCode()); 
			}		
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
	
		return mav;
	}	
	
	@RequestMapping(value="enable.htm", method = RequestMethod.GET)
	public ModelAndView enable(@RequestParam("topicId") String topicId,HttpServletRequest httpRequest,@ModelAttribute Topic topic,@ModelAttribute PagingBean bean) {
		logger.info(" # TopicManagementController.enable topicId:"+topicId);
		ModelAndView mav = new ModelAndView();		
		try{
			mav.setViewName("adminwebboardtopicList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("topicId", topicId);	
			BuckWaResponse response = webboardTopicService.enable(request);
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				mav.addObject("successCode","S004"); 		 				
			}else {	
				mav.addObject("errorCode", response.getErrorCode()); 
				mav.addObject("pagingBean", bean);	
			}	
			
			// Search Again
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);					 	
			request.put("pagingBean", bean);	
			bean.put("topic", topic);		
			
			request.put("topic", topic);
			response = webboardTopicService.getAllByOffsetAndCategoryName(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {			 
				mav.addObject("errorCode", response.getErrorCode()); 
			}		
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
	
		return mav;
	}	
	
	@RequestMapping(value="disableMessage.htm", method = RequestMethod.GET)
	public ModelAndView disbleMessage(@RequestParam("topicId") String topicId,@RequestParam("messageId") String messageId) {
		logger.info(" # TopicManagementController.disbleMessage messageId:"+messageId);
		ModelAndView mav = new ModelAndView();
		try{	
		BuckWaRequest request = new BuckWaRequest();
		request.put("messageId", messageId);  
		BuckWaResponse response = webboardTopicService.disableMessage(request); 
		
		request.put("topicId", topicId); 
		request.put("messageStatus", WebboardConstants.WEBBOARD_ALL);
		response = webboardTopicService.viewTopic(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			logger.info("  Success");
			Topic topic = (Topic)response.getResObj("topic");
			logger.info(" topic return :"+BeanUtils.getBeanString(topic));
			mav.addObject("topic", topic);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("adminViewTopic");
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	@RequestMapping(value="enableMessage.htm", method = RequestMethod.GET)
	public ModelAndView enableMessage(@RequestParam("topicId") String topicId,@RequestParam("messageId") String messageId) {
		logger.info(" # TopicManagementController.enableMessage messageId:"+messageId);
		ModelAndView mav = new ModelAndView();
		try{	
		BuckWaRequest request = new BuckWaRequest();
		request.put("messageId", messageId);  
		BuckWaResponse response = webboardTopicService.enableMessage(request); 
		
		request.put("topicId", topicId); 
		request.put("messageStatus", WebboardConstants.WEBBOARD_ALL);
		response = webboardTopicService.viewTopic(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			logger.info("  Success");
			Topic topic = (Topic)response.getResObj("topic");
			logger.info(" topic return :"+BeanUtils.getBeanString(topic));
			mav.addObject("topic", topic);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("adminViewTopic");
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	
}