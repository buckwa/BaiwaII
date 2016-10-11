package com.buckwa.domain.admin;

import java.util.List;

import com.buckwa.domain.pam.LeaveType;
import com.buckwa.domain.pam.SummaryLeave;
import com.buckwa.util.BuckWaDateUtils;
import com.sun.istack.internal.NotNull;

public class LeaveYearQuota  extends LeaveType  {
	
	
	private Long leaveYearQuotaId;
	private Integer year;
	private String yearStr;
	private List<LeaveQuota> leaveQuoList;
	private boolean isYearAlready;
	private String createDateStr;
	private List<LeaveType> leaveTypeList;
	private boolean isCanChange;
	private List<SummaryLeave> summaryLeaveList;
	private String action;
	
	
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<SummaryLeave> getSummaryLeaveList() {
		return summaryLeaveList;
	}
	public void setSummaryLeaveList(List<SummaryLeave> summaryLeaveList) {
		this.summaryLeaveList = summaryLeaveList;
	}
	public boolean isCanChange() {
		return isCanChange;
	}
	public void setCanChange(boolean isCanChange) {
		this.isCanChange = isCanChange;
	}
	public List<LeaveType> getLeaveTypeList() {
		return leaveTypeList;
	}
	public void setLeaveTypeList(List<LeaveType> leaveTypeList) {
		this.leaveTypeList = leaveTypeList;
	}
	public String getCreateDateStr() {
		return BuckWaDateUtils.getCustomFormat_thai_from_date(createDate,"dd MMM yyyy");
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public boolean isYearAlready() {
		return isYearAlready;
	}
	public void setYearAlready(boolean isYearAlready) {
		this.isYearAlready = isYearAlready;
	}
	public List<LeaveQuota> getLeaveQuoList() {
		return leaveQuoList;
	}
	public void setLeaveQuoList(List<LeaveQuota> leaveQuoList) {
		this.leaveQuoList = leaveQuoList;
	}
	public Long getLeaveYearQuotaId() {
		return leaveYearQuotaId;
	}
	public void setLeaveYearQuotaId(Long leaveYearQuotaId) {
		this.leaveYearQuotaId = leaveYearQuotaId;
	}
	public Integer getYear() {
		return year;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getYearStr() {
		return yearStr;
	}
	public void setYearStr(String yearStr) {
		this.yearStr = yearStr;
	}
	
	
	
}
