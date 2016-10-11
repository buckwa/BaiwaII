package com.buckwa.domain.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.pam.WorkTemplate;
import com.buckwa.domain.pam.WorkTemplateAttr;


public class WorkTemplateValidator implements Validator{
	
	public boolean supports(Class aClass) {
		return WorkTemplate.class.equals(aClass);
	}
	
	public void validate(Object obj, Errors errors) {
		
		WorkTemplate domain = (WorkTemplate)obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.field", "Required field");
		
		
		if(domain.getGroupId()==0){
			errors.rejectValue("groupId", "required.field", "Required field");
		}
		
		if(domain.getWorkTemplateAttrList()!=null && domain.getWorkTemplateAttrList().size()>0){
			int index = 0;
			for(WorkTemplateAttr wTA : domain.getWorkTemplateAttrList()){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workTemplateAttrList["+index+"].label", "required.field", "Required field");
				index++;
			}
		}

		if (domain.isNameAlready()) {
			errors.rejectValue("name", "error.alreadyExist", "Name already exist!!!");
		}
	}
}
