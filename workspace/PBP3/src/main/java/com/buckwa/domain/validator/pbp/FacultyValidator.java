package com.buckwa.domain.validator.pbp;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.admin.Role;
import com.buckwa.domain.pbp.Faculty;


public class FacultyValidator {

	public void validate(Faculty domain, Errors errors) {
		if (StringUtils.isBlank(domain.getName())) {
			errors.rejectValue("name", "required", "required");
		}
	 
	}


}
