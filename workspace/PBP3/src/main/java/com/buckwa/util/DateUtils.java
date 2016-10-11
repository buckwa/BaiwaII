package com.buckwa.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
	
	private static SimpleDateFormat ddmmyy_thai = new SimpleDateFormat("dd/MM/yy", new Locale("th", "TH"));
	private static SimpleDateFormat ddmmyyyy_thai = new SimpleDateFormat("dd/MM/yyyy", new Locale("th", "TH"));
	private static SimpleDateFormat YY_thai = new SimpleDateFormat("yy", new Locale("th", "TH"));
	private static SimpleDateFormat MM_thai = new SimpleDateFormat("MM", new Locale("th", "TH"));
 
	public static String getdd_mm_yy_fromDate(){
		String returnValue = "";
		try{
	        Date currentDate = new Date(System.currentTimeMillis());	    
	        returnValue = ddmmyy_thai.format(currentDate);	
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return returnValue;		
	}	
	 
	public static String getYYStringFromDateThai(){
		String returnValue = "";
		try{
	        Date currentDate = new Date(System.currentTimeMillis());	    
	        returnValue = YY_thai.format(currentDate);	
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return returnValue;		
	}
	
	public static String getMMStringFromDateThai(){
		
		try{
			   
			return MM_thai.format(new Date(System.currentTimeMillis()));
	        
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
		
		return "00";		
	}
}
