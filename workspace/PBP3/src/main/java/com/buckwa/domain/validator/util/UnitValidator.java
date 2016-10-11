package com.buckwa.domain.validator.util;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.util.Unit;


public class UnitValidator {

	public void validate(Unit domain, Errors errors) {
 
		if (StringUtils.isBlank(domain.getName())) {
			errors.rejectValue("name", "required", "required");
		}		 
 
	}


}
