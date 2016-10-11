package com.buckwa.domain.validator.webboard;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.webboard.Topic;


public class ReplyMessageValidator {

	public void validate(Topic domain, Errors errors) {
 
		if (StringUtils.isBlank(domain.getReplyMessage())) {
			errors.rejectValue("replyMessage", "required", "Reply message cannot be left blank");
		}		 
 
	}


}
