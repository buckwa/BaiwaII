package com.buckwa.web.controller.pbp.report;

public class ReportUtil {
	
	public static String getMaxValuePad(String maxIn){
		
		String returnvalue = "";
		try{
			
			Double maxDouble =Double.parseDouble(maxIn);
			maxDouble = maxDouble+200.00;
			returnvalue=maxDouble+"";
		}catch(Exception ex){
			returnvalue = maxIn;
			ex.printStackTrace();
		}
		
		return returnvalue;
	}
	
	public static String getIntervalValue(String maxIn){
		
		String returnvalue = "";
		try{
			
			
			
			Double maxDouble =Double.parseDouble(maxIn);
			maxDouble = maxDouble+200.00;
			
			if(maxDouble<500){
				returnvalue="100";
			}else if(maxDouble>=500&&maxDouble<1000){
				returnvalue="200";
			}else if(maxDouble>=1000&&maxDouble<4000){
				returnvalue="300";
			}
			else if(maxDouble>=4000&&maxDouble<10000){
				returnvalue="600";
			}else if(maxDouble>=10000&&maxDouble<20000){
				returnvalue="1200";
			}else if(maxDouble>=20000&&maxDouble<40000){
				returnvalue="2000";
			}
			else if(maxDouble>=40000&&maxDouble<80000){
				returnvalue="5000";
			}else if(maxDouble>=80000&&maxDouble<120000){
				returnvalue="8000";
			}
			 
		}catch(Exception ex){
			returnvalue = maxIn;
			ex.printStackTrace();
		}
		
		return returnvalue;
	}

}
