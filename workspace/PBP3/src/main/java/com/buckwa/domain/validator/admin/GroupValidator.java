package com.buckwa.domain.validator.admin;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.admin.Group;


public class GroupValidator {

	public void validate(Group role, Errors errors) {

		if (StringUtils.isBlank(role.getGroupName())) {
			errors.rejectValue("groupName", "required", "required");
		}

	}


}
