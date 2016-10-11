package com.buckwa.domain.validator.admin;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.admin.Role;


public class RoleValidator {

	public void validate(Role role, Errors errors) {
		if (StringUtils.isBlank(role.getRoleName())) {
			errors.rejectValue("roleName", "required", "required");
		}
		if (StringUtils.isBlank(role.getRoleDesc())) {
			errors.rejectValue("roleDesc", "required", "required");
		}

/*
		String telephone = owner.getTelephone();
		if (!StringUtils.hasLength(telephone)) {
			errors.rejectValue("telephone", "required", "required");
		}
		else {
			for (int i = 0; i < telephone.length(); ++i) {
				if ((Character.isDigit(telephone.charAt(i))) == false) {
					errors.rejectValue("telephone", "nonNumeric", "non-numeric");
					break;
				}
			}
		}
			*/
	}


}
