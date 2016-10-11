package com.buckwa.domain.validator.pbp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.pbp.AcademicYear;
public class AcademicYearDateEditValidator {
	
	public void validate(AcademicYear  domain , Errors errors) {
		 if (StringUtils.isBlank(domain.getStartDateStr())) {
		 	errors.rejectValue("startDateStr", "required", "required");
		 } 
		 
		 if (StringUtils.isBlank(domain.getEndDateStr())) {
			 	errors.rejectValue("endDateStr", "required", "required");
		  } 
		 
			try{
				SimpleDateFormat dateTimeFormate = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
			 
				Date startDate  = dateTimeFormate.parse(domain.getStartDateStr());
				Date endDate  = dateTimeFormate.parse(domain.getEndDateStr()); 
				domain.setStartDate(new Timestamp(startDate.getTime()));
				domain.setEndDate(new Timestamp(endDate.getTime()));
			}catch(Exception ex){
				ex.printStackTrace();
				 
				 
			}
	 
	}


}
