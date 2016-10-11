package com.buckwa.domain.validator.webboard;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.webboard.Topic;


public class TopicValidator {

	public void validate(Topic domain, Errors errors) {
 
		if (StringUtils.isBlank(domain.getTopicDetail())) {
			errors.rejectValue("topicDetail", "required", "required");
		}		 
 
	}


}
