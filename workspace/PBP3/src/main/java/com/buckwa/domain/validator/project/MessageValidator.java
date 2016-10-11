package com.buckwa.domain.validator.project;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.webboard.Message;

 

public class MessageValidator {


	public void validate(Message domain, Errors errors) {

		if (StringUtils.isBlank(domain.getMessageDetail())) {
			errors.rejectValue("messageDetail", "required", "required");
		}

	}
}
