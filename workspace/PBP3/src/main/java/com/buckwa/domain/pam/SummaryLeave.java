package com.buckwa.domain.pam;

import java.util.Date;

import com.buckwa.domain.BaseDomain;

/*
 @Author : Wichien Prommese(Pe)
 @Create : Aug 5, 2012 2:20:20 PM
 */
public class SummaryLeave extends BaseDomain {

	private Long summaryLeaveId;
	private String leaveTypeCode;
	private int year;
	private int total;
	private int amount;
	private Long userId;
	private String leaveName;
	private int balance;
	private int flag=0;
	private int totalTmp;
	private int accumulate;
	private int workYear;
	private int flagAccumalate;
	
	
	
	public int getFlagAccumalate() {
		return flagAccumalate;
	}
	public void setFlagAccumalate(int flagAccumalate) {
		this.flagAccumalate = flagAccumalate;
	}
	public int getWorkYear() {
		return workYear;
	}
	public void setWorkYear(int workYear) {
		this.workYear = workYear;
	}
	public int getAccumulate() {
		return accumulate;
	}
	public void setAccumulate(int accumulate) {
		this.accumulate = accumulate;
	}
	public int getTotalTmp() {
		return totalTmp;
	}
	public void setTotalTmp(int totalTmp) {
		this.totalTmp = totalTmp;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getLeaveName() {
		return leaveName;
	}
	public void setLeaveName(String leaveName) {
		this.leaveName = leaveName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getSummaryLeaveId() {
		return summaryLeaveId;
	}
	public void setSummaryLeaveId(Long summaryLeaveId) {
		this.summaryLeaveId = summaryLeaveId;
	}
	public String getLeaveTypeCode() {
		return leaveTypeCode;
	}
	public void setLeaveTypeCode(String leaveTypeCode) {
		this.leaveTypeCode = leaveTypeCode;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	

}
