package com.buckwa.domain.validator.pam;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.pam.ResearchLeave;
import com.buckwa.domain.pam.VacationLeave;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 6, 2012 3:47:06 PM
 */
public class ResearchLeaveValidator implements Validator{
	
	public boolean supports(Class aClass) {
		return ResearchLeave.class.equals(aClass);
	}
	
	public void validate(Object obj, Errors errors) {
		
		ResearchLeave domain = (ResearchLeave)obj;
		
		//Validate object is empty
		if(BuckWaConstants.L_RESEARCH_TYPE_R001.equals(domain.getResearchTypeCode())){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "study", "required.field", "Required field");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "degree", "required.field", "Required field");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "education", "required.field", "Required field");
		}else{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "course", "required.field", "Required field");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "required.field", "Required field");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bycapital", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fromDateStr", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toDateStr", "required.field", "Required field");
		
		int dayTotal = (domain.getAmountYear()*365)+(domain.getAmountMonth()*30)+domain.getAmountDay();
		if(dayTotal==0)
			errors.rejectValue("amountDay", "required.field", "Required");
		else if(domain.getLeaveBalance()<dayTotal)
			errors.rejectValue("amountDay", "amountDay.invalid", "Total of leave invalid!!!.");
		
		
		//validate From Date and To Date
		if (!StringUtils.isBlank(domain.getFromDateStr())&&!StringUtils.isBlank(domain.getToDateStr())){
			Date dateFrom = BuckWaDateUtils.parseDate(domain.getFromDateStr());
			Date dateTo = BuckWaDateUtils.parseDate(domain.getToDateStr());
			
			//case Date to must more than dateFrom
			if(dateFrom.after(dateTo)){
				errors.rejectValue("fromDateStr", "fromDateToDate.invalid", "From Date must not exceed To Date");
			}else if(dateTo.before(dateFrom)){
				errors.rejectValue("toDateStr", "fromDateToDate.invalid", "To Date must less than From Date");
			}
		}
	}
}

