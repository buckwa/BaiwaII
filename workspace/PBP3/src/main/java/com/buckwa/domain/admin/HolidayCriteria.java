package com.buckwa.domain.admin;

public class HolidayCriteria  extends Holiday {
	
	private String isDurationDate;
	private String minDate;
	private String maxDate;
	
	public String getMinDate() {
		return minDate;
	}
	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}
	public String getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
	public String getIsDurationDate() {
		return isDurationDate;
	}
	public void setIsDurationDate(String isDurationDate) {
		if(isDurationDate!=null && isDurationDate.equalsIgnoreCase("true")){
			this.isDurationDate = "true";
		}else{
			this.isDurationDate = "false";
		}
		
	}

}
