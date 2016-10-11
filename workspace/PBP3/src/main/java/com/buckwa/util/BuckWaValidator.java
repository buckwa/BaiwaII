package com.buckwa.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BuckWaValidator {
	
	public static boolean isValidEmail (String emailIn){
		boolean isValid = false;  
		try{
			String EMAIL_PATTERN =    "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; 
			Pattern pattern= Pattern.compile(EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(emailIn);	 
			isValid =matcher.matches();
		}catch (Exception ex){
			ex.printStackTrace();
		}		
		return isValid;
	}
	
	public static boolean isPhoneNumberValid(String phoneNumber){   
		 boolean isValid = false;   
		 /* Phone Number formats: (nnn)nnn-nnnn; nnnnnnnnnn; nnn-nnn-nnnn  
		     ^\\(?</STRONG> : May start with an option "(" .  
		     (\\d{3})</STRONG>: Followed by 3 digits.  
		     \\)?</STRONG> : May have an optional ")"  
		     [- ]?</STRONG> : May have an optional "-" after the first 3 digits or after optional ) character.  
		     (\\d{3})</STRONG> : Followed by 3 digits.  
		      [- ]? </STRONG>: May have another optional "-" after numeric digits.  
		     (\\d{4})$</STRONG> : ends with four digits.  
		 
		          Examples: Matches following phone numbers:  
		          (123)456-7890, 123-456-7890, 1234567890, (123)-456-7890  
		 
		 */  
		 //Initialize reg ex for phone number. </CODECOMMENTS>   
		 String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";   
		 CharSequence inputStr = phoneNumber;   
		 Pattern pattern = Pattern.compile(expression);   
		 Matcher matcher = pattern.matcher(inputStr);   
		 if(matcher.matches()){   
		 isValid = true;   
		 }   
		 return isValid;   
		 }  
	
	
	public static boolean isSSNValid(String ssn){   
		 boolean isValid = false;   
		  /*SSN format xxx-xx-xxxx, xxxxxxxxx, xxx-xxxxxx; xxxxx-xxxx:  
		         ^\\d{3}: Starts with three numeric digits.  
		     [- ]?: Followed by an optional "-"  
		     \\d{2}: Two numeric digits after the optional "-"  
		     [- ]?: May contain an optional second "-" character.  
		    \\d{4}: ends with four numeric digits.  
		  
		         Examples: 879-89-8989; 869878789 etc.  
		 */  
		  
		 //Initialize reg ex for SSN. </CODECOMMENTS>   
		 String expression = "^\\d{3}[- ]?\\d{2}[- ]?\\d{4}$";   
		 CharSequence inputStr = ssn;   
		 Pattern pattern = Pattern.compile(expression);   
		  Matcher matcher = pattern.matcher(inputStr);   
		 if(matcher.matches()){   
			 isValid = true;   
		 }   
		 return isValid;   
		 }  
	
	public static boolean isNumeric(String number){   
		 boolean isValid = false;   
	   
		 /*Number: A numeric value will have following format:  
		          ^[-+]?: Starts with an optional "+" or "-" sign.  
		      [0-9]*: May have one or more digits.  
		     \\.?</STRONG> : May contain an optional "." (decimal point) character.  
		     [0-9]+$</STRONG> : ends with numeric digit.  
		 */  
		   
		 //Initialize reg ex for numeric data.   
		 String expression = "^[-+]?[0-9]*\\.?[0-9]+$";   
		 CharSequence inputStr = number;   
		 Pattern pattern = Pattern.compile(expression);   
		 Matcher matcher = pattern.matcher(inputStr);   
		 if(matcher.matches()){   
		 isValid = true;   
		 }   
		 return isValid;   
		 } 	
}
