package com.buckwa.domain.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.buckwa.domain.admin.LeaveQuota;
import com.buckwa.domain.admin.LeaveYearQuota;
import com.buckwa.util.BuckWaDateUtils;


public class LeaveYearQuotaValidator implements Validator{
	
	public boolean supports(Class aClass) {
		return LeaveYearQuota.class.equals(aClass);
	}
 
	public void validate(Object obj, Errors errors) {
		LeaveYearQuota leaveYearQuota = (LeaveYearQuota) obj;
 
		if(leaveYearQuota.getLeaveQuoList()!=null && leaveYearQuota.getLeaveQuoList().size()>0){
			int index = 0;
			for(LeaveQuota leaveQuota : leaveYearQuota.getLeaveQuoList()){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "leaveQuoList["+index+"].quota", "required.field", "Required field");
				index++;
			}
		}
	}
}
