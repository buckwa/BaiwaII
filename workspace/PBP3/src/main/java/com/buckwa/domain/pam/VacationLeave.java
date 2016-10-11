package com.buckwa.domain.pam;

import java.util.Date;

import com.buckwa.domain.BaseDomain;
import com.buckwa.util.BuckWaDateUtils;

/*
 @Author : Wichien Prommese(Pe)
 @Create : Aug 5, 2012 2:20:20 PM
 */
public class VacationLeave extends BaseDomain {

	private Long leaveVacationId;
	private String docNo;
	private int amountDay;
	private int leaveBalance;
	private String contactBy;
	private Date fromDate;
	private Date toDate;
	private Long leaveByPersonId;
	private String fromDateStr;
	private String toDateStr;
	private int canVacationDay;
	private LeaveComment leaveComment;
	private int flagExpired;
	private int foreign;
	private String foreignAt;
	private String foreignFull;
	
	
	
	public String getForeignFull() {
		return foreignFull;
	}

	public void setForeignFull(String foreignFull) {
		this.foreignFull = foreignFull;
	}

	public String getForeignAt() {
		return foreignAt;
	}

	public void setForeignAt(String foreignAt) {
		this.foreignAt = foreignAt;
	}

	public int getForeign() {
		return foreign;
	}

	public void setForeign(int foreign) {
		this.foreign = foreign;
	}

	public int getFlagExpired() {
		return flagExpired;
	}

	public void setFlagExpired(int flagExpired) {
		this.flagExpired = flagExpired;
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

	public Long getLeaveVacationId() {
		return leaveVacationId;
	}

	public void setLeaveVacationId(Long leaveVacationId) {
		this.leaveVacationId = leaveVacationId;
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

	public int getCanVacationDay() {
		return canVacationDay;
	}

	public void setCanVacationDay(int canVacationDay) {
		this.canVacationDay = canVacationDay;
	}

}
