package com.buckwa.domain.validator.pam;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.buckwa.domain.pam.KpiSchedule;
import com.buckwa.domain.pam.Year;

/*
@Author : Taechapon Himarat (Su)
@Create : Oct 6, 2012 9:30:24 PM
 */
public class KpiScheduleValidator {
	
	public boolean supports(Class<?> aClass) {
		return Year.class.equals(aClass);
	}
 
	public void validate(Object obj, Errors errors) {
		KpiSchedule kpiSchedule = (KpiSchedule) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "yearName",                     "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "teacherUploadStartDate1Str",   "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "teacherUploadEndDate1Str",     "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "teacherEvaluateStartDate1Str", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "teacherEvaluateEndDate1Str",   "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "teacherUploadStartDate2Str",   "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "teacherUploadEndDate2Str",     "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "teacherEvaluateStartDate2Str", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "teacherEvaluateEndDate2Str",   "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "staffUploadStartDateStr",      "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "staffUploadEndDateStr",        "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "staffEvaluateStartDateStr",    "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "staffEvaluateEndDateStr",      "required.field", "Required field");
		
		// FIXME add validate before, after date
	}
	
}
