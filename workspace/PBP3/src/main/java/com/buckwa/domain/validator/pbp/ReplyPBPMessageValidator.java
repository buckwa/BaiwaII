package com.buckwa.domain.validator.pbp;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.pbp.AcademicKPIUserMappingWrapper;


public class ReplyPBPMessageValidator {

	public void validate(AcademicKPIUserMappingWrapper domain, Errors errors) {
 
		if (StringUtils.isBlank(domain.getReplyMessage())) {
			errors.rejectValue("replyMessage", "required", "Reply message cannot be left blank");
		}		 
 
	}


}
