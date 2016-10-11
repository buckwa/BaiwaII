package com.buckwa.domain.validator.pbp;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.pbp.Department;


public class DepartmentValidator {

	public void validate(Department domain, Errors errors) {
		if (StringUtils.isBlank(domain.getName())) {
			errors.rejectValue("name", "required", "required");
		}
	 
	}


}
