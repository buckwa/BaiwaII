package com.buckwa.domain.validator.project;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.project.Vision;

public class VisionValidator {


	public void validate(Vision domain, Errors errors) {

		if (StringUtils.isBlank(domain.getSummary())) {
			errors.rejectValue("summary", "required", "required");
		}

	 
	}
}
