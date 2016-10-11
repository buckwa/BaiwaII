package com.buckwa.domain.validator.project;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.project.Project;


public class ProjectValidator {

	public void validate(Project domain, Errors errors) {

		if (StringUtils.isBlank(domain.getProjectName())) {
			errors.rejectValue("projectName", "required", "required");
		}

	}


}
