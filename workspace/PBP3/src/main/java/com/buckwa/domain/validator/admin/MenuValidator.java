package com.buckwa.domain.validator.admin;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.admin.Menu;


public class MenuValidator {

	public void validate(Menu domain, Errors errors) {
		 
		if (StringUtils.isBlank(domain.getCode())) {
			errors.rejectValue("code", "required", "required");
		}
		if (StringUtils.isBlank(domain.getName())) {
			errors.rejectValue("name", "required", "required");
		}		 
		if (StringUtils.isBlank(domain.getUrl())) {	
			errors.rejectValue("url", "required", "required");
		}
	}


}
