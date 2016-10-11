package com.buckwa.domain.validator.pbp;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;

import com.buckwa.domain.pbp.report.TimeTableReport;

public class TimeTableValidator {
	private static Logger logger = LoggerFactory.getLogger(TimeTableValidator.class);
 
	public void validate(TimeTableReport domain, Errors errors) {	 
		
		if (StringUtils.isBlank(domain.getSubjectCode())) {
			errors.rejectValue("subjectCode", "required.field", "Required field");
		}
		if (StringUtils.isBlank(domain.getThaiName())) {
			errors.rejectValue("thaiName", "required.field", "Required field");
		}		
		if (StringUtils.isBlank(domain.getEngName())) {
			errors.rejectValue("engName", "required.field", "Required field");
		}			
//		if (StringUtils.isBlank(domain.getLecOrPrac())) {
//			errors.rejectValue("lecOrPrac", "required.field", "Required field");
//		}       
		  
//		if (StringUtils.isBlank(domain.getTeachHr())) {
//			errors.rejectValue("teachHr", "required.field", "Required field");
//		}		  
//		if (StringUtils.isBlank(domain.getCredit())) {
//			errors.rejectValue("credit", "required.field", "Required field");
//		}		
//		if (StringUtils.isBlank(domain.getDegreeStr())) {
//			errors.rejectValue("degreeStr", "required.field", "Required field");
//		}		
//		if (StringUtils.isBlank(domain.getTotalStudent())) {
//			errors.rejectValue("totalStudent", "required.field", "Required field");
//		}		
//		if (StringUtils.isBlank(domain.getSecNo())) {
//			errors.rejectValue("secNo", "required.field", "Required field");
//		}		
//		if (StringUtils.isBlank(domain.getTeachDayStr())) {
//			errors.rejectValue("teachDayStr", "required.field", "Required field");
//		}		
//		if (StringUtils.isBlank(domain.getTeachDayStr())) {
//			errors.rejectValue("teachTimeFromTo", "required.field", "Required field");
//		}		
// 
		
	}

}
