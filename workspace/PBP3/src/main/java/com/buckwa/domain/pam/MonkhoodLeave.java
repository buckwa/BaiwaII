package com.buckwa.domain.pam;

import java.util.Date;

import com.buckwa.domain.BaseDomain;

/*
 @Author : Wichien Prommese(Pe)
 @Create : Aug 5, 2012 2:20:20 PM
 */
public class MonkhoodLeave extends BaseDomain {

	private Long leaveMonkhoodId;
	private String docNo;
	private int amountDay;
	private String contactBy;
	private Date fromDate;
	private Date toDate;
	private Long leaveByPersonId;
	private String fromDateStr;
	private String toDateStr;
	private int canMonkhoodDay;
	private LeaveComment leaveComment;
	private int leaveBalance;
	private int ever;
	private String location;
	private String at;
	private String defindDateStr;
	private Date defindDate;
	private String stay;
	private String everStr;
	private String reason;
	private String location1;
	 private int flagExpired;
	 
	
	
	
	public int getFlagExpired() {
		return flagExpired;
	}

	public void setFlagExpired(int flagExpired) {
		this.flagExpired = flagExpired;
	}

	public String getLocation1() {
		return location1;
	}

	public void setLocation1(String location1) {
		this.location1 = location1;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getEverStr() {
		return everStr;
	}

	public void setEverStr(String everStr) {
		this.everStr = everStr;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAt() {
		return at;
	}

	public void setAt(String at) {
		this.at = at;
	}

	public String getDefindDateStr() {
		return defindDateStr;
	}

	public void setDefindDateStr(String defindDateStr) {
		this.defindDateStr = defindDateStr;
	}

	public Date getDefindDate() {
		return defindDate;
	}

	public void setDefindDate(Date defindDate) {
		this.defindDate = defindDate;
	}

	public String getStay() {
		return stay;
	}

	public void setStay(String stay) {
		this.stay = stay;
	}

	public int getEver() {
		return ever;
	}

	public void setEver(int ever) {
		this.ever = ever;
	}

	public int getLeaveBalance() {
		return leaveBalance;
	}

	public void setLeaveBalance(int leaveBalance) {
		this.leaveBalance = leaveBalance;
	}

	public LeaveComment getLeaveComment() {
		return leaveComment;
	}

	public void setLeaveComment(LeaveComment leaveComment) {
		this.leaveComment = leaveComment;
	}

	public Long getLeaveMonkhoodId() {
		return leaveMonkhoodId;
	}

	public void setLeaveMonkhoodId(Long leaveMonkhoodId) {
		this.leaveMonkhoodId = leaveMonkhoodId;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public int getAmountDay() {
		return amountDay;
	}

	public void setAmountDay(int amountDay) {
		this.amountDay = amountDay;
	}

	public String getContactBy() {
		return contactBy;
	}

	public void setContactBy(String contactBy) {
		this.contactBy = contactBy;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getLeaveByPersonId() {
		return leaveByPersonId;
	}

	public void setLeaveByPersonId(Long leaveByPersonId) {
		this.leaveByPersonId = leaveByPersonId;
	}

	public String getFromDateStr() {
		return fromDateStr;
	}

	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr() {
		return toDateStr;
	}

	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}

	public int getCanMonkhoodDay() {
		return canMonkhoodDay;
	}

	public void setCanMonkhoodDay(int canMonkhoodDay) {
		this.canMonkhoodDay = canMonkhoodDay;
	}

}
