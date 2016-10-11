package com.buckwa.domain.validator.pam;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.pam.Year;


public class YearValidator  implements Validator{
	
	public boolean supports(Class aClass) {
		return Year.class.equals(aClass);
	}
 
	public void validate(Object obj, Errors errors) {
		Year domain = (Year) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",         "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDateStr", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDateStr",   "required.field", "Required field");
		
		// FIXME add validate before, after date
	}


}
