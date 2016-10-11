package com.buckwa.domain.validator.admin.address;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.admin.address.Aumphur;


public class AumphurValidator {

	public void validate(Aumphur domain, Errors errors) {
		if (StringUtils.isBlank(domain.getName())) {
			errors.rejectValue("name", "required", "required");
		}
		if (StringUtils.isBlank(domain.getCode())) {
			errors.rejectValue("code", "required", "required");
		}
 
	}


}
