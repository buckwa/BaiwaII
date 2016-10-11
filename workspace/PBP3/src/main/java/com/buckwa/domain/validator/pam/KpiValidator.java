package com.buckwa.domain.validator.pam;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.pam.Kpi;


public class KpiValidator implements Validator{
	
	public boolean supports(Class aClass) {
		return Kpi.class.equals(aClass);
	}
	
	public void validate(Object obj, Errors errors) {
		
		Kpi domain = (Kpi) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.field", "Required field");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "required.field", "Required field");
	}

}
