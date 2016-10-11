package com.buckwa.web.controller.pbp;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
 
 
@Controller
@RequestMapping("/president") 
public class PresidentController { 
	private static Logger logger = LoggerFactory.getLogger(PresidentController.class);
	
	

	
	
	@RequestMapping(value = "/init.htm", method = RequestMethod.GET)
	public ModelAndView barChart() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		//mav.addObject("user", new User());	
		mav.setViewName("presidentInit");
		logger.info(" End  ");
		return mav;
	}
 

	
	@RequestMapping(value = "/instituteReport.htm", method = RequestMethod.GET)
	public ModelAndView instituteReport() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		//mav.addObject("user", new User());	
		mav.setViewName("instituteReport");
		logger.info(" End  ");
		return mav;
	}
 
}
