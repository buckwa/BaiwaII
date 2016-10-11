package com.buckwa.domain.validator.project;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.project.Lab;

public class LabValidator {


	public void validate(Lab domain, Errors errors) {

		if (StringUtils.isBlank(domain.getName())) {
			errors.rejectValue("name", "required", "required");
		}

		if (StringUtils.isBlank(domain.getBasicFlow())) {
			errors.rejectValue("basicFlow", "required", "required");
		}
	}
}
