package com.buckwa.domain.pam;

import java.util.Date;

import com.buckwa.domain.BaseDomain;
import com.buckwa.util.BuckWaDateUtils;
import java.sql.Timestamp;

public class Semester extends BaseDomain {
	
	private Long semesterId;
	private Long yearId;
	private String yearName;
	private String name;
	private String description;
	private Timestamp startDate;
	private Timestamp endDate;
	private String startDateStr;
	private String endDateStr;
	
	
 

	public Long getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(Long semesterId) {
		this.semesterId = semesterId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getYearId() {
		return yearId;
	}

	public void setYearId(Long yearId) {
		this.yearId = yearId;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYearName() {
		return yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	public String getStartDateStr() {
		if(this.startDate!=null){
			return BuckWaDateUtils.getShortThaiMonthFromDate(this.startDate);
		}else{
			return "";
		}
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		if(this.endDate!=null){
			return BuckWaDateUtils.getShortThaiMonthFromDate(this.endDate);
		}else{
			return "";
		}
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

}
