package com.buckwa.domain.validator.project;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.project.Team;


public class TeamValidator {

	public void validate(Team domain, Errors errors) {

		if (domain.getGroups()==null||domain.getGroups().length==0) {
			//errors.rejectValue("groups", "required", "required");
		}

	}


}
