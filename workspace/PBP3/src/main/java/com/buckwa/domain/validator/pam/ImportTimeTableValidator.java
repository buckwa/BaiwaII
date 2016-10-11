package com.buckwa.domain.validator.pam;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.buckwa.domain.pam.ImportData;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.BuckWaConstants;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 6, 2012 3:47:06 PM
 */
public class ImportTimeTableValidator implements Validator{
	
	public boolean supports(Class aClass) {
		return ImportData.class.equals(aClass);
	}
	
	public void validate(Object obj, Errors errors) {
		
		ImportData domain = (ImportData)obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "yearId", "required.field", "Required field");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "semesterId", "required.field", "Required field");
		
	}
	
	public void validate(Object obj,PathUtil pathUtil, Errors errors) {
		
		ImportData domain = (ImportData)obj;
		
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "yearId", "required.field", "Required field");
//		
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "semesterId", "required.field", "Required field");
		
		//check file upload
		MultipartFile  teacherTeachFileMaster = domain.getFileDataTeachTable();
		
		
		
		//teacher teach file master
		if (teacherTeachFileMaster==null||teacherTeachFileMaster.getSize() ==0) {
			errors.rejectValue("fileDataTeacherTeachMaster", "required.field", "Required");
		}else{
			String file = teacherTeachFileMaster.getOriginalFilename();
			String fileExtension = file.substring(file.lastIndexOf("."));
			if(!".xls".equals(fileExtension)){
				errors.rejectValue("fileDataTeacherTeachMaster", "error.filetypesql", "File size is too large");
			}
		}
		
		
		
		
	}
}

