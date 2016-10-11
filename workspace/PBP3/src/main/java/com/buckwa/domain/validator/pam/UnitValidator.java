package com.buckwa.domain.validator.pam;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.pam.Unit;


public class UnitValidator implements Validator{
	
	public boolean supports(Class aClass) {
		return Unit.class.equals(aClass);
	}
	
	public void validate(Object obj, Errors errors) {
		Unit domain = (Unit) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.field", "Required field");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "required.field", "Required field");
	}

}
