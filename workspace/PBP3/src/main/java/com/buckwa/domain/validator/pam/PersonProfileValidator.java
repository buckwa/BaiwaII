package com.buckwa.domain.validator.pam;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.pam.Person;
import com.buckwa.util.BuckWaDateUtils;

/*
@Author : Taechapon Himarat (Su)
@Create : Aug 25, 2012 9:23:44 PM
 */
public class PersonProfileValidator implements Validator {
	
	public boolean supports(Class aClass) {
		return Person.class.equals(aClass);
	}
	
	public void validate(Object obj, Errors errors) {
		
		Person domain = (Person) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeId", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "citizenId", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "thaiName", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "thaiSurname", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sex", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthdateStr", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rateNo", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeType", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "position", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "level", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workLine", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salary", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workTelNo", "required.field", "Required field");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "belongTo", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "faculty", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workingDateStr", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "assignDateStr", "required.field", "Required field");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "retireDateStr", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "maxInsignia", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "maxEducation", "required.field", "Required field");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taxNo", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "marriedStatus", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workingStatus", "required.field", "Required field");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workNumber", "required.field", "Required field");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "insureNo", "required.field", "Required field");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fund", "required.field", "Required field");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "required.field", "Required field");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipCode", "required.field", "Required field");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telNo", "required.field", "Required field");
		
		//validate Assign Date and Retire Date
		if (!StringUtils.isBlank(domain.getAssignDateStr())
			&& !StringUtils.isBlank(domain.getRetireDateStr())) {
			
			Date assignDate = BuckWaDateUtils.parseDate(domain.getAssignDateStr());
			Date retireDate = BuckWaDateUtils.parseDate(domain.getRetireDateStr());
			
			//case Date to must more than dateFrom
			if(assignDate.after(retireDate)){
				errors.rejectValue("assignDateStr", "fromDateToDate.invalid", "From Date must not exceed To Date");
			}else if(retireDate.before(assignDate)){
				errors.rejectValue("retireDateStr", "fromDateToDate.invalid", "To Date must less than From Date");
			}
		}
 
	}
	
}
