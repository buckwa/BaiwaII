package com.buckwa.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver; 
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		logger.info(" ################ General Exception #########");
		logger.info(" "+ex);
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("general-error");
		
		return mav;
		//return super.resolveException(request, response, handler, ex);
	}
	
 
}
 