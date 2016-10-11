package com.buckwa.web.controller.tech;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.admin.Role;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.framework.sms.SMSDomain;
import com.buckwa.framework.sms.SMSService;
import com.buckwa.util.BeanUtils;

@Controller
@RequestMapping("/tech/sms") 
@SessionAttributes("smsDomain")
public class SMSController { 
	private static Logger logger = LoggerFactory.getLogger(SMSController.class);
 
	
	@Autowired
	private SMSService smsService;
 
	@RequestMapping(value = "initSMS.htm", method = RequestMethod.GET)
	public ModelAndView initSMS(HttpServletRequest httpRequest ) {
		logger.info(" Start");
		ModelAndView mav = new ModelAndView();		 
		mav.setViewName("initSMS"); 
		try {  
 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	 
		@RequestMapping(value = "initTestSMS.htm", method = RequestMethod.GET)
		public ModelAndView initTestSMS(HttpServletRequest httpRequest ) {
			logger.info(" Start");
			ModelAndView mav = new ModelAndView();
			PagingBean bean = new PagingBean();
			mav.setViewName("initTestSMS"); 
			try {  
				
				SMSDomain smsDomain = new SMSDomain();
				smsDomain.setSendToNo("0900074023");
				smsDomain.setSmsBody(" Please insert SMS Message here !!");
				mav.addObject("smsDomain",smsDomain );
				 
				
	 
			} catch (Exception ex) {
				ex.printStackTrace();
				mav.addObject("errorCode", "E001");
			}
			return mav;
		}
		
		
	 
		@RequestMapping(value="testSMS.htm", method = RequestMethod.POST)
		public ModelAndView testSMS(  HttpServletRequest httpRequest,@ModelAttribute SMSDomain smsDomain, BindingResult result) {
		 
			
			//SMSDomain smsDomain = new SMSDomain();
			//smsDomain.setSendToNo("0900074023");
			//smsDomain.setSmsBody(" Please insert SMS Message here !!");
			
			
			logger.info(" Start smsDomain:"+BeanUtils.getBeanString(smsDomain));
			ModelAndView mav = new ModelAndView();		 
			mav.setViewName("initTestSMS"); 
			try {  						
				smsService.sendSMS(smsDomain);				
	 
			} catch (Exception ex) {
				ex.printStackTrace();
				mav.addObject("errorCode", "E001");
			}
			return mav;
		}		
				 
}
