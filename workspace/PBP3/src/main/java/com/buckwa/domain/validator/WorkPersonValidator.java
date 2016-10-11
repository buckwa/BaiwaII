package com.buckwa.domain.validator;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.pam.FileLocation;
import com.buckwa.domain.pam.WorkPerson;
import com.buckwa.domain.pam.WorkPersonAttr;
import com.buckwa.domain.pam.WorkTemplateAttr;


public class WorkPersonValidator implements Validator{
	
	public boolean supports(Class<?> aClass) {
		return WorkPerson.class.equals(aClass);
	}
	
	public void validate(Object obj, Errors errors) {
		
		WorkPerson domain = (WorkPerson)obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.field", "Required field");
		
		if (domain.isNameAlready()) {
			errors.rejectValue("name", "error.alreadyExist", "Name already exist!!!");
		}
		
		// Create
		if (domain.getWorkPersonId() == null) {
			for (WorkTemplateAttr templateAttr : domain.getWorkTemplateAttrMappingList()) {
				int value = 0;
				for (WorkPersonAttr attr : domain.getWorkPersonAttrList()) {
					if (templateAttr.getLabel().equals(attr.getLabel())) {
						value = Integer.parseInt(attr.getValue());
						break;
					}
				}
				int count = 0;
				for (FileLocation fileLocation : domain.getFileLocationList()) {
					if (templateAttr.getWorkTemplateAttrId().equals(fileLocation.getWorkPersonAttrId())) {
						count++;
					}
				}
				if (value != count) {
					errors.rejectValue("workPersonAttrMappingId", "error.workPersonAttrMapping", "File don't related");
					break;
				}
			}
		}
		
		// Edit
		else {
			for (WorkPersonAttr attr : domain.getWorkPersonAttrList()) {
				if (1 == attr.getIsFile() && StringUtils.hasText(attr.getValue())) {
					int value = Integer.parseInt(attr.getValue());
					int count = 0;
					for (FileLocation fileLocation : domain.getFileLocationList()) {
						if (attr.getWorkPersonAttrId().equals(fileLocation.getWorkPersonAttrId())) {
							count++;
						}
					}
					if (value != count) {
						errors.rejectValue("workPersonAttrMappingId", "error.workPersonAttrMapping", "File don't related");
						break;
					}
				}
			}
		}
		
	}
}
