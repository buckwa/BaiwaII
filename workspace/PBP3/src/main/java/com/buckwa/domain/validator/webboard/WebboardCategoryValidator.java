package com.buckwa.domain.validator.webboard;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.webboard.WebboardCategory;


public class WebboardCategoryValidator {

	public void validate(WebboardCategory domain, Errors errors) {
 
		if (StringUtils.isBlank(domain.getName())) {
			errors.rejectValue("name", "required", "required");
		}		 
 
	}


}
