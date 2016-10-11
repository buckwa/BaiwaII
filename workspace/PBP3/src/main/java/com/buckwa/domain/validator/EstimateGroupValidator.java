package com.buckwa.domain.validator;


import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.pam.EstimateGroup;
import com.buckwa.domain.pam.FileLocation;
import com.buckwa.domain.pam.WorkPerson;
import com.buckwa.domain.pam.WorkPersonAttr;


public class EstimateGroupValidator implements Validator{
	
	public boolean supports(Class<?> aClass) {
		return EstimateGroup.class.equals(aClass);
	}
	
	public void validate(Object obj, Errors errors) {
		
		EstimateGroup domain = (EstimateGroup)obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.field", "Required field");
		
		if (domain.isNameAlready()) {
			errors.rejectValue("name", "error.alreadyExist", "Name already exist!!!");
		}
	}
}
