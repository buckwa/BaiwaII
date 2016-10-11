package com.buckwa.domain.validator.project;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.project.Util;

public class UtilValidator {


	public void validate(Util domain, Errors errors) {

		if (StringUtils.isBlank(domain.getDetail())) {
			errors.rejectValue("detail", "required", "required");
		}

	}
}
