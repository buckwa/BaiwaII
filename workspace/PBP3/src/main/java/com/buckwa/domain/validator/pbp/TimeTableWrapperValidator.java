package com.buckwa.domain.validator.pbp;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.pbp.TimeTableWrapper;


public class TimeTableWrapperValidator {

	public void validate(TimeTableWrapper domain, Errors errors) {
		if (StringUtils.isBlank(domain.getSubjectId())) {
			errors.rejectValue("subjectId", "required", "required");
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
