package com.buckwa.domain.validator.pam;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.pam.KpiCategory;


public class KpiCategoryValidator implements Validator{
	
	public boolean supports(Class aClass) {
		return KpiCategory.class.equals(aClass);
	}
	
	public void validate(Object obj, Errors errors) {
		KpiCategory domain = (KpiCategory) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.field", "Required field");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "required.field", "Required field");
	}

}
