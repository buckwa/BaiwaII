package com.buckwa.domain.validator.project;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.project.TestCase;

public class TestCaseValidator {


	public void validate(TestCase domain, Errors errors) {

		if (StringUtils.isBlank(domain.getName())) {
			errors.rejectValue("name", "required", "required");
		}
 
	}
}
