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
@RequestMapping("/tech/config") 
@SessionAttributes("smsDomain")
public class ConfigurationController { 
	private static Logger logger = LoggerFactory.getLogger(ConfigurationController.class);
 
	
	@Autowired
	private SMSService smsService;
 
	@RequestMapping(value = "installMySQLOnCentOS.htm", method = RequestMethod.GET)
	public ModelAndView initSMS(HttpServletRequest httpRequest ) {
		logger.info(" Start");
		ModelAndView mav = new ModelAndView();		 
		mav.setViewName("installMySQLOnCentOS"); 
		try {  
 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
  
}
