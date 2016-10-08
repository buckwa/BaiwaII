package baiwa.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import baiwa.config.SecurityConfig;

@Controller
public class CommonController {
	Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	@RequestMapping(value = "/login.htm", method = RequestMethod.GET)
	public ModelAndView login(
		@RequestParam(value = "warning", required = false) String warning,
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout,
		HttpServletRequest request) {
		
		logger.info("login");
		
		ModelAndView mav = new ModelAndView();
		if (warning != null) {
			mav.addObject("warning", "Wanning");
		}
		if (error != null) {
			mav.addObject("error", "Invalid Username or Password");
		}
		if (logout != null) {
			mav.addObject("msg", "You've been logged out successfully.");
		}
		
		mav.setViewName("login");
		
		return mav;
	}
	
	@RequestMapping(value = "/home.htm")
	public ModelAndView home() {
		
		ModelAndView mav = new ModelAndView();
//		
//		// check if user is login
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (!(auth instanceof AnonymousAuthenticationToken)) {
//			UserDetails userDetail = (UserDetails) auth.getPrincipal();
//			mav.addObject("username", userDetail.getUsername());
//		}
		
		mav.setViewName("home");
		
		return mav;
	}
	
	
	@RequestMapping(value = "/403.htm")
	public ModelAndView accesssDenied() {
		
		ModelAndView mav = new ModelAndView();
//		
//		// check if user is login
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (!(auth instanceof AnonymousAuthenticationToken)) {
//			UserDetails userDetail = (UserDetails) auth.getPrincipal();
//			mav.addObject("username", userDetail.getUsername());
//		}
		
		mav.setViewName("403");
		
		return mav;
	}
}
