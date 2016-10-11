package com.buckwa.domain.validator.pbp;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.pam.Person;
import com.buckwa.util.BuckWaDateUtils;

/*
@Author : Taechapon Himarat (Su)
@Create : Aug 25, 2012 9:23:44 PM
 */
public class EditPersonProfilePBPValidator implements Validator {
	
	public boolean supports(Class aClass) {
		return Person.class.equals(aClass);
	}
	
	public void validate(Object obj, Errors errors) {
		
		Person domain = (Person) obj;
		if (StringUtils.isBlank(domain.getRateNo())) {
			errors.rejectValue("rateNo", "required", "required");
		}
	  
 
	}
	
}
