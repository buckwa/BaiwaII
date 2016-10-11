package com.buckwa.domain.admin;

import java.util.List;

import com.buckwa.domain.pam.LeaveType;

public class LeaveQuota  extends LeaveType  {
	
	/*
	 table: leave_type 
	 ----------------------------------
	 	leaveQuota -> leave_quota
	 */
	
	/**
	 * 
	 */
	
	private Long leaveQuotaId;
	private int year;
	private String leaveTypeCode;
	private Integer quota;
	private String leaveQuota;
	private String yearStr;
	private Long leaveYearQuotaId;
	private String leaveTypeName;
	
	


	public String getLeaveTypeName() {
		return leaveTypeName;
	}

	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}

	public Long getLeaveYearQuotaId() {
		return leaveYearQuotaId;
	}

	public void setLeaveYearQuotaId(Long leaveYearQuotaId) {
		this.leaveYearQuotaId = leaveYearQuotaId;
	}

	public String getYearStr() {
		return yearStr;
	}

	public void setYearStr(String yearStr) {
		this.yearStr = yearStr;
	}

	public Long getLeaveQuotaId() {
		return leaveQuotaId;
	}

	public void setLeaveQuotaId(Long leaveQuotaId) {
		this.leaveQuotaId = leaveQuotaId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getLeaveTypeCode() {
		return leaveTypeCode;
	}

	public void setLeaveTypeCode(String leaveTypeCode) {
		this.leaveTypeCode = leaveTypeCode;
	}

	public Integer getQuota() {
		return quota;
	}

	public void setQuota(Integer quota) {
		this.quota = quota;
	}

	public String getLeaveQuota() {
		return leaveQuota;
	}

	public void setLeaveQuota(String leaveQuota) {
		this.leaveQuota = leaveQuota;
	}
	
}
