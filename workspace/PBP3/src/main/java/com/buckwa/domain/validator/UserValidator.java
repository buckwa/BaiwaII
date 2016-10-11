package com.buckwa.domain.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.buckwa.domain.admin.User;
import com.buckwa.util.BuckWaValidator;
import com.buckwa.web.controller.admin.RoleManagementController;

public class UserValidator {
	private static Logger logger = LoggerFactory.getLogger(UserValidator.class);
 
	public void validate(User user, Errors errors) {	
//	      String sPhoneNumber = "605-8889999";
//	      String sPhoneNumber = "605-88899991";
//	      String sPhoneNumber = "605-888999A";
	 
//	      Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
//	      Matcher matcher = pattern.matcher(sPhoneNumber);
//	 
//	      if (matcher.matches()) {
//	    	  logger.info("Phone Number Valid");
//	      }	  else     {
//	    	  logger.info("Phone Number must be in the form XXX-XXXXXXX");
//	      }

		
		if (StringUtils.isBlank(user.getUsername())) {
			errors.rejectValue("username", "required.field", "Required field");
		}
		
		// Validate only create user
		if (user.getPerson().getPersonId() == null) {
			if (StringUtils.isBlank(user.getPassword())) {
			errors.rejectValue("password", "required.field", "Required field");
			}		
			if (StringUtils.isBlank(user.getPasswordConfirmation())) {			 
				errors.rejectValue("passwordConfirmation", "required.field",	"Required field");
			}		
			if (!user.getPassword().equals(user.getPasswordConfirmation())) {			 
				user.setPassword(null);
				user.setPasswordConfirmation(null);				
				errors.rejectValue("passwordConfirmation", "required",	"Passwords do not match. ");
			}
		}
		
//		if (StringUtils.isBlank(user.getPasswordConfirmation())) {			 
//			//errors.rejectValue("email", "required",	"Required. ");
//		}else{
//			if (!BuckWaValidator.isValidEmail(user.getEmail())) {	
//				errors.rejectValue("email", "required",	" Invalid Email  ");
//			}
//			
//		}
		
		
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.employeeId", "required.field", "Required field");
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.citizenId", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.thaiName", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.thaiSurname", "required.field", "Required field");
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.sex", "required.field", "Required field");
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.birthdateStr", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.rateNo", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.employeeType", "required.field", "Required field");
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.position", "required.field", "Required field");
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.level", "required.field", "Required field");
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.workLine", "required.field", "Required field");
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.salary", "required.field", "Required field");
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.workTelNo", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.faculty", "required.field", "Required field");
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.workingDateStr", "required.field", "Required field");
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.assignDateStr", "required.field", "Required field");
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.maxInsignia", "required.field", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.maxEducation", "required.field", "Required field");
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.marriedStatus", "required.field", "Required field");
	//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person.workingStatus", "required.field", "Required field");
		
	}

}
