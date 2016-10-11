package com.buckwa.domain.validator.admin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;

import com.buckwa.domain.admin.User;
import com.buckwa.util.BuckWaValidator;
import com.buckwa.web.controller.admin.RoleManagementController;

public class UserValidator {
	private static Logger logger = LoggerFactory.getLogger(UserValidator.class);
 
	public void validate(User user, Errors errors) {	
	      String sPhoneNumber = "605-8889999";
	      //String sPhoneNumber = "605-88899991";
	      //String sPhoneNumber = "605-888999A";
	 
	      Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
	      Matcher matcher = pattern.matcher(sPhoneNumber);
	 
	      if (matcher.matches()) {
	    	  logger.info("Phone Number Valid");
	      }	  else     {
	    	  logger.info("Phone Number must be in the form XXX-XXXXXXX");
	      }

	      
		if (StringUtils.isBlank(user.getFirst_name())) {
			errors.rejectValue("first_name", "required", "Required. ");
		}       
		if (StringUtils.isBlank(user.getLast_name())) {
			errors.rejectValue("last_name", "required", "Required. ");
		}	      
		
		if (StringUtils.isBlank(user.getUsername())) {
			errors.rejectValue("username", "required", "Required. ");
		}else{
			if (!BuckWaValidator.isValidEmail(user.getUsername())) {	
				errors.rejectValue("username", "required",	" Invalid Email  ");
			}
		}
		
		if (StringUtils.isBlank(user.getPassword())) {
			errors.rejectValue("password", "required", "Required. ");
		}		
		if (StringUtils.isBlank(user.getPasswordConfirmation())) {			 
			errors.rejectValue("passwordConfirmation", "required",	"Required. ");
		}		
		if (!user.getPassword().equals(user.getPasswordConfirmation())) {			 
			user.setPassword(null);
			user.setPasswordConfirmation(null);				
			errors.rejectValue("passwordConfirmation", "required",	"Passwords do not match. ");
		}	
		
		/*
		if (StringUtils.isBlank(user.getPasswordConfirmation())) {			 
			//errors.rejectValue("email", "required",	"Required. ");
		}else{
			if (!BuckWaValidator.isValidEmail(user.getEmail())) {	
				errors.rejectValue("email", "required",	" Invalid Email  ");
			}
			
		}
		
		*/
		
	}

}
