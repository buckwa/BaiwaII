package com.buckwa.domain.validator.pam;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;

import com.buckwa.domain.pam.nodetree.Kpi;
import com.buckwa.domain.validator.pbp.EditImportWorkValidator;
import com.buckwa.util.BeanUtils;


public class KpiTreeValidator {
	private static Logger logger = LoggerFactory.getLogger(KpiTreeValidator.class);
	public void validate(Kpi domain, Errors errors) {
		logger.info(" Domain validate:"+BeanUtils.getBeanString(domain));

		
		//unitDesc = null, kpiId = null, markType = 0, categoryId = null, uploadScore = null, mark = 0, number = null, 
		//		code = null, kpiTemplateId = null, parentId = null, isLeave = null, name = asfdasdf, markTypeDesc = null,
		//		yearId = null, childOrder = 0, unitId = null, ]
		//		10/12/2012 14:54:22:556 INFO  KpiTreeDaoImpl.update():6
				
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
