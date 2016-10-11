package com.buckwa.domain.validator.project;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.project.DetailDesign;

public class DetailDesignValidator {


	public void validate(DetailDesign domain, Errors errors) {

		if (StringUtils.isBlank(domain.getName())) {
			errors.rejectValue("name", "required", "required");
		}
 
	}
}
