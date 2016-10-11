package com.buckwa.domain.validator.pam;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.pam.Workline;

/*
@Author : Teerawoot Charoenporn(Tor)
@Create : Aug 14, 2012 1:06:21 AM
 */
public class WorklineValidator implements Validator{
	
	public boolean supports(Class aClass) {
		return Workline.class.equals(aClass);
	}
	
	public void validate(Object obj, Errors errors) {
		
		Workline domain = (Workline)obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "worklineName", "required.field", "Required field");
		
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "worklineCode", "required.field", "Required field");
	}
}
