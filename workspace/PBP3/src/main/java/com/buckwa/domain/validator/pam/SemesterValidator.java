package com.buckwa.domain.validator.pam;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.pam.Semester;


public class SemesterValidator implements Validator {

	public boolean supports(Class aClass) {
		return Semester.class.equals(aClass);
	}
	
	public void validate(Object obj, Errors errors) {
		Semester domain = (Semester)obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",         "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDateStr", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDateStr",   "required.field", "Required field");
		
		// FIXME add validate before, after date
	}
	


}
