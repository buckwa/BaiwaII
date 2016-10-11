package com.buckwa.domain.validator.util;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.util.Category;


public class CategoryValidator {

	public void validate(Category domain, Errors errors) {
 
		if (StringUtils.isBlank(domain.getName())) {
			errors.rejectValue("name", "required", "required");
		}		 
 
	}


}
