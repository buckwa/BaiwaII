package com.buckwa.domain.validator.pam;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.buckwa.domain.pam.nodetree.KpiTemplate;


public class KpiTemplateValidator {

	public void validate(KpiTemplate domain, Errors errors) {

		if (StringUtils.isBlank(domain.getName())) {
			errors.rejectValue("name",  "required.field", "Required");
		}
		
		if(domain.getMarkType()!=null&&domain.getMarkType().equals("1")){ 
			if ( domain.getUnitId()==null)  {
				errors.rejectValue("unitId",  "required.field", "Required");
			} 
		}
		
		
		if(!(domain.getMarkType()!=null&&domain.getMarkType().equals("2"))){ 
			if ( domain.getMark()==null)  {
				errors.rejectValue("mark",  "required.field", "Required");
			} 
		}		

	}


}
