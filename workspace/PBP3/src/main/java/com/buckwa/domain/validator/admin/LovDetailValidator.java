package com.buckwa.domain.validator.admin;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.common.LovDetail;


public class LovDetailValidator {

	public void validate(LovDetail domain, Errors errors) {
		 
		if (StringUtils.isBlank(domain.getCode())) {
			errors.rejectValue("code", "required", "required");
		}
		if (StringUtils.isBlank(domain.getName())) {
			errors.rejectValue("name", "required", "required");
		}		 
 
	}


}
