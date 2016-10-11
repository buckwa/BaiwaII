package com.buckwa.domain.validator.pam;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.pam.PersonalLeave;
import com.buckwa.domain.pam.VacationLeave;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 6, 2012 3:47:06 PM
 */
public class PersonalLeaveValidator implements Validator{
	
	public boolean supports(Class aClass) {
		return PersonalLeave.class.equals(aClass);
	}
	
	public void validate(Object obj, Errors errors) {
		
		PersonalLeave domain = (PersonalLeave)obj;
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fromDateStr", "required.field", "Required field");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toDateStr", "required.field", "Required field");
		
	
		if (domain.getAmountDay()<=0) {
			errors.rejectValue("amountDay", "required.field", "Required");
		}else{
			if(domain.getLeaveBalance()<domain.getAmountDay())
				errors.rejectValue("amountDay", "amountDay.invalid", "Days of leave invalid!!!.");
		}
		
		
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

