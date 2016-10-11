package com.buckwa.domain.validator.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.admin.User;

public class ChangePasswordValidator {

	public void validate(User user, Errors errors) {		
		
		if (StringUtils.isBlank(user.getUsername())) {
			errors.rejectValue("username", "required", "Required. ");
		}
		if (StringUtils.isBlank(user.getCurrentPassword())) {
			errors.rejectValue("currentPassword", "required", "Required.");
		}			
		
		if (StringUtils.isBlank(user.getNewPassword())) {
			errors.rejectValue("newPassword", "required", "Required.");
		}		
		if (StringUtils.isBlank(user.getPasswordConfirmation())) {			 
			errors.rejectValue("passwordConfirmation", "required",	"Required.");
		}		
		if (!user.getNewPassword().equals(user.getPasswordConfirmation())) {			 
			user.setNewPassword(null);
			user.setPasswordConfirmation(null);				
			errors.rejectValue("passwordConfirmation", "required",	"Passwords do not match. ");
		}	
	}

}
