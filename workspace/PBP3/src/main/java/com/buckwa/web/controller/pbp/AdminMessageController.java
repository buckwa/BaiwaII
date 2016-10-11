package com.buckwa.web.controller.pbp;

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
import com.buckwa.domain.validator.project.MessageValidator;
import com.buckwa.domain.webboard.Message;
import com.buckwa.service.intf.CommonService;
import com.buckwa.service.intf.pbp.MessageService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;

@Controller
@RequestMapping("/admin/message")
@SessionAttributes(types = Message.class)
public class AdminMessageController {	
	private static Logger logger = LoggerFactory.getLogger(AdminMessageController.class);	
	@Autowired
	private MessageService messageService;		
	@Autowired
	private CommonService commonService;		
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav = gotoMessageList(mav);	
		return mav;
	}	
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView init() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("messageCreate");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		Message message = new Message();
		 
		mav.addObject("message", message);	
		return mav;
	}		

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createMessage(@ModelAttribute Message message, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{			
			new MessageValidator().validate(message, result);			
			if (result.hasErrors()) {				
				mav.setViewName("messageCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				
				message.setCreateBy(BuckWaUtils.getUserNameFromContext());
				request.put("message", message);
				BuckWaResponse response = messageService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					mav.addObject("message", new Message());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav = gotoMessageList(mav);					
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("messageCreate");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	private ModelAndView gotoMessageList(ModelAndView mav) {
		PagingBean bean = new PagingBean();
		
		
		
		Message message = new Message();
		
		
		mav.addObject("message",message );	
		mav.addObject("pagingBean", bean);		
		mav.setViewName("adminMessageList");
		
		try{
		// Search with initial
	 
		int offset = 0;	
		bean.setOffset(offset);	 
		BuckWaRequest request = new BuckWaRequest();		
		mav.addObject("message", message);	
		request.put("pagingBean", bean);	
		bean.put("message", message);			 
		BuckWaResponse response = messageService.getByOffset(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			logger.info(" Success ");
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
	
	@RequestMapping(value="search.htm", method = RequestMethod.GET)
	public ModelAndView searchGet(HttpServletRequest httpRequest ,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("adminMessageList");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("message", new Message());	
			request.put("pagingBean", bean);	
			bean.put("message", new Message());			 
			BuckWaResponse response = messageService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
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
	
	
	@RequestMapping(value="search.htm", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute Message message,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("adminMessageList");
		try{		
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);						 
			BuckWaRequest request = new BuckWaRequest();		
			request.put("pagingBean", bean);	
			bean.put("message", message);			
			BuckWaResponse response = messageService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);	
				mav.addObject("doSearch","true");
			}else {			
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			 
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("messageId") String messageId) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request = new BuckWaRequest();
		request.put("messageId", messageId);	
		BuckWaResponse response = messageService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			Message message = (Message)response.getResObj("message");			
		 
			mav.addObject("message", message);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("adminMessageView");

		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Message message, BindingResult result) {		
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{		
			new MessageValidator().validate(message, result);
			
			if (result.hasErrors()) {			
				mav.setViewName("adminMessageView");
			}else {
				BuckWaRequest request = new BuckWaRequest();
				request.put("message", message);
				BuckWaResponse response = messageService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					Message newMessage =  new Message();
					  
					mav.addObject("message",message);					
					mav.addObject("successCode", response.getSuccessCode()); 
					mav = gotoMessageList(mav);						
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("messageEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("messageId") String messageId,HttpServletRequest httpRequest,@ModelAttribute Message message,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{
			mav.setViewName("adminMessageList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("messageId", messageId);	
			BuckWaResponse response = messageService.deleteById(request);
			
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
			bean.put("message", message);		
			response = messageService.getByOffset(request);
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
	
}
