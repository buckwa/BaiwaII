package com.buckwa.domain.validator.pbp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.pbp.AcademicYearEvaluateRound;
public class AcademicYearEvaluateRoundEditValidator {
	
	public void validate(AcademicYearEvaluateRound  domain , Errors errors) {
		
		if("1".equals(domain.getEvaluateType())){
			
			 if (StringUtils.isBlank(domain.getRound1StartDateStr())) {
				 	errors.rejectValue("round1StartDateStr", "required", "required");
			  } 
				 
			 if (StringUtils.isBlank(domain.getRound1EndDateStr())) {
				 	errors.rejectValue("round1EndDateStr", "required", "required");
			  } 
			 
			 if (StringUtils.isBlank(domain.getRound2StartDateStr())) {
				 	errors.rejectValue("round2StartDateStr", "required", "required");
			  } 
				 
			 if (StringUtils.isBlank(domain.getRound2EndDateStr())) {
				 	errors.rejectValue("round2EndDateStr", "required", "required");
			  } 
			 
				try{
					SimpleDateFormat dateTimeFormate = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
				 
					Date r1StartDate  = dateTimeFormate.parse(domain.getRound1StartDateStr());
					Date r1EndDate  = dateTimeFormate.parse(domain.getRound1EndDateStr());
					
					Date r2StartDate  = dateTimeFormate.parse(domain.getRound2StartDateStr());
					Date r2EndDate  = dateTimeFormate.parse(domain.getRound2EndDateStr());
					
					domain.setRound1StartDate(new Timestamp(r1StartDate.getTime()));
					domain.setRound1EndDate(new Timestamp(r1EndDate.getTime()));
					
					domain.setRound2StartDate(new Timestamp(r2StartDate.getTime()));
					domain.setRound2EndDate(new Timestamp(r2EndDate.getTime()));
					
					
				}catch(Exception ex){
					ex.printStackTrace(); 
				}	 
		}
		
		
		if("2".equals(domain.getEvaluateType())){
			
			 if (StringUtils.isBlank(domain.getRound1StartDateStr())) {
				 	errors.rejectValue("round1StartDateStr", "required", "required");
			  } 
				 
			 if (StringUtils.isBlank(domain.getRound1EndDateStr())) {
				 	errors.rejectValue("round1EndDateStr", "required", "required");
			  } 
			 
				try{
					SimpleDateFormat dateTimeFormate = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
				 
					Date r1StartDate  = dateTimeFormate.parse(domain.getRound1StartDateStr());
					Date r1EndDate  = dateTimeFormate.parse(domain.getRound1EndDateStr());
 
					domain.setRound1StartDate(new Timestamp(r1StartDate.getTime()));
					domain.setRound1EndDate(new Timestamp(r1EndDate.getTime()));
					
		 
					
					
				}catch(Exception ex){
					ex.printStackTrace(); 
				}	 	 
			
		}
		
		

		 

	 
	}

 

}
