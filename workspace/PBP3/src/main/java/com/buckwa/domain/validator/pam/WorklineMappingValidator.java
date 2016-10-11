package com.buckwa.domain.validator.pam;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.pam.WorklineMapping;

/*
@Author : Teerawoot Charoenporn(Tor)
@Create : Aug 25, 2012 5:53:54 PM
 */
public class WorklineMappingValidator implements Validator{

	@Override
	public boolean supports(Class aClass) {
		return WorklineMapping.class.equals(aClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "worklineCode", "required.field", "Required field");
		
	}

}
