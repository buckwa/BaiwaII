package com.buckwa.domain.validator.userregistration;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.admin.User;
import com.buckwa.util.BuckWaValidator;

public class ForgotPASSValidator {

 
	public void validate(User user, Errors errors) {	
 
		if (StringUtils.isBlank(user.getEmail())) {			 
			errors.rejectValue("email", "required",	"Required. ");
		}else{
			if (!BuckWaValidator.isValidEmail(user.getEmail())) {	
				errors.rejectValue("email", "required",	" Invalid Email  ");
			}else{			
				// Call Check email exist
			}			
		} 
	}

}
