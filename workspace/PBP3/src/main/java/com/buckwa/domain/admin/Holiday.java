package com.buckwa.domain.admin;

import com.buckwa.domain.BaseDomain;
import com.buckwa.util.BuckWaDateUtils;

public class Holiday  extends BaseDomain  {
	
	private String holidayId;
	private String yearId;
	private String holidayName;
	private String holidayDesc;
	private String holidayDate;
	private boolean enable;
	private String holidayDateStr;
	private String yearStr;
	
	
	
	
	public String getYearStr() {
		return yearStr;
	}
	public void setYearStr(String yearStr) {
		this.yearStr = yearStr;
	}
	public void setHolidayDateStr(String holidayDateStr) {
		this.holidayDateStr = holidayDateStr;
	}
	public String getHolidayDateStr() {
		return BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(BuckWaDateUtils.parseDate(holidayDate));
	}
	public String getHolidayId() {
		return holidayId;
	}
	public void setHolidayId(String holidayId) {
		this.holidayId = holidayId;
	}
	public String getYearId() {
		return yearId;
	}
	public void setYearId(String yearId) {
		this.yearId = yearId;
	}
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	public String getHolidayDesc() {
		return holidayDesc;
	}
	public void setHolidayDesc(String holidayDesc) {
		this.holidayDesc = holidayDesc;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getHolidayDate() {
		return holidayDate;
	}
	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
	}

}
