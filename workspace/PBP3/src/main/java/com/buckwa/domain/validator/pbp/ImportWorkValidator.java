package com.buckwa.domain.validator.pbp;


import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
 
import com.buckwa.domain.pbp.AcademicKPI;
import com.buckwa.domain.pbp.AcademicKPIAttribute;
import com.buckwa.domain.pbp.AcademicKPIAttributeValue;

 
public class ImportWorkValidator {
	private static Logger logger = LoggerFactory.getLogger(ImportWorkValidator.class);
	
	
	public void validate(AcademicKPI domain, Errors errors) {
	
		
		List<AcademicKPIAttribute> academicKPIAttributeList =domain.getAcademicKPIAttributeList();
 
		
		for(AcademicKPIAttribute tmp:academicKPIAttributeList){
			logger.info("  AcademicKPIAttributeValue Update :"+tmp.getValue());
			if(StringUtils.isBlank(tmp.getValue())){
				logger.info(" ## Error in  EditImportWorkValidator");
				errors.rejectValue("errorDesc", "required", "กรุณากรอกค่าที่จำเป็นให้ครบ");
				break;
			}
			
			String attName =tmp.getName();
			String percentName ="สัดส่วน";
			if(attName.indexOf(percentName)!=-1){
				logger.info(" ## Validate percent :"+tmp.getValue());	
				
			 
				try{
				BigDecimal percenBig  = new BigDecimal(tmp.getValue());
				BigDecimal maxPercent = new BigDecimal(100);
				BigDecimal zeroPercent = new BigDecimal(0);
				
				if( percenBig.compareTo(maxPercent) > 0){
					errors.rejectValue("errorDesc", "required", "กรุณากรอกค่าตัวเลขในข้อมูลสัดส่วนไม่เกิน 100");	
					break;
				 }
				if( zeroPercent.compareTo(percenBig) > 0){
					errors.rejectValue("errorDesc", "required", "กรุณากรอกค่าตัวเลขในข้อมูลสัดส่วนมากกว่า 0");	
					break;
				 }				
				}catch(Exception ex){
					errors.rejectValue("errorDesc", "required", "กรุณากรอกค่าตัวเลขในข้อมูลสัดส่วน");
				
					ex.printStackTrace();
					break;
				}
				
		
				
				
			}
	 
			
		} 
		
 
	}


}
