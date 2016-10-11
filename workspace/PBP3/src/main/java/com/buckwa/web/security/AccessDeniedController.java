package com.buckwa.web.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessDeniedController { 	
	
	/*
    @RequestMapping(value = "/securityAccessDenied.htm")
    public String processAccessDeniedException(){
       logger.info("Access Denied Handler");
        return "redirect:/securityAccessDeniedView.htm";
    }    
    */

    @RequestMapping(value = "/securityAccessDenied.htm")
    public ModelAndView displayAccessDeniedView(){ 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("securityAccessDenied");	 
		return mav;
    }
    
    @RequestMapping(value = "/securityAdminAccessDenied.htm")
    public ModelAndView securityAdminAccessDenied(){ 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("securityAdminAccessDenied");	 
		return mav;
    }
    
    @RequestMapping(value = "/securityUserAccessDenied.htm")
    public ModelAndView securityUserAccessDenied(){ 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("securityUserAccessDenied");	 
		return mav;
    }
    
    @RequestMapping(value = "/securityAnonymousAccessDenied.htm")
    public ModelAndView securityAnonymousAccessDenied(){ 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("securityAnonymousAccessDenied");	 
		return mav;
    }
    
    @RequestMapping(value = "/securityDeanAccessDenied.htm")
    public ModelAndView securityDeanAccessDenied(){ 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("securityDeanAccessDenied");	 
		return mav;
    }
 
    
    
}
