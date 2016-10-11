package com.buckwa.domain.validator.pam;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.pam.CancelLeave;
import com.buckwa.domain.pam.VacationLeave;
import com.buckwa.util.BuckWaDateUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 6, 2012 3:47:06 PM
 */
public class CancelLeaveValidator {
	
	public void validate(CancelLeave domain, Errors errors) {
		
		//Validate object is empty
		if (StringUtils.isBlank(domain.getFromDateStr())) {
			errors.rejectValue("fromDateStr", "required", "Required");
		}
		
		if (StringUtils.isBlank(domain.getToDateStr())) {
			errors.rejectValue("toDateStr", "required", "Required");
		}
		
		if (domain.getAmountDay()<=0) {
			errors.rejectValue("amountDay", "required", "Required");
		}
		
		
		//validate From Date and To Date
		if (!StringUtils.isBlank(domain.getFromDateStr())&&!StringUtils.isBlank(domain.getToDateStr())){
			Date dateFrom = BuckWaDateUtils.parseDate(domain.getFromDateStr());
			Date dateTo = BuckWaDateUtils.parseDate(domain.getToDateStr());
			
			//case Date to must more than dateFrom
			if(dateFrom.after(dateTo)){
				errors.rejectValue("fromDateStr", "required", "From Date must not exceed To Date");
			}else if(dateTo.before(dateFrom)){
				errors.rejectValue("toDateStr", "required", "To Date must less than From Date");
			}
		}
		
 
	}
}

