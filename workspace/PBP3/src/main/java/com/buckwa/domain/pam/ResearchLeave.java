package com.buckwa.domain.pam;

import java.util.Date;
import java.util.List;

import com.buckwa.domain.BaseDomain;

/*
 @Author : Wichien Prommese(Pe)
 @Create : Aug 5, 2012 2:20:20 PM
 */
public class ResearchLeave extends BaseDomain {

	private Long leaveResearchId;
	private String docNo;
	private String researchTypeCode;
	private String degree;
	private String education;
	private String country;
	private String bycapital;
	private String course;
	private String location;
	private int amountYear;
	private int amountDay;
	private int amountMonth;
	private String contactBy;
	private String contactNo;
	private Date fromDate;
	private Date toDate;
	private Long leaveByPersonId;
	private String fromDateStr;
	private String toDateStr;
	private List<ResearchType> researchTypes;
	private Long leaveFlowId;
	private String flowStatus;
	private String study;
	private String countryRequired;
	private LeaveComment leaveComment;
	private int leaveBalance;
    private int flagExpired;
    
    

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

	public String getCountryRequired() {
		return countryRequired;
	}

	public void setCountryRequired(String countryRequired) {
		this.countryRequired = countryRequired;
	}

	public String getStudy() {
		return study;
	}

	public void setStudy(String study) {
		this.study = study;
	}

	public String getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	public Long getLeaveFlowId() {
		return leaveFlowId;
	}

	public void setLeaveFlowId(Long leaveFlowId) {
		this.leaveFlowId = leaveFlowId;
	}

	public List<ResearchType> getResearchTypes() {
		return researchTypes;
	}

	public void setResearchTypes(List<ResearchType> researchTypes) {
		this.researchTypes = researchTypes;
	}

	public Long getLeaveResearchId() {
		return leaveResearchId;
	}

	public void setLeaveResearchId(Long leaveResearchId) {
		this.leaveResearchId = leaveResearchId;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getResearchTypeCode() {
		return researchTypeCode;
	}

	public void setResearchTypeCode(String researchTypeCode) {
		this.researchTypeCode = researchTypeCode;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBycapital() {
		return bycapital;
	}

	public void setBycapital(String bycapital) {
		this.bycapital = bycapital;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getAmountYear() {
		return amountYear;
	}

	public void setAmountYear(int amountYear) {
		this.amountYear = amountYear;
	}

	public int getAmountDay() {
		return amountDay;
	}

	public void setAmountDay(int amountDay) {
		this.amountDay = amountDay;
	}

	public int getAmountMonth() {
		return amountMonth;
	}

	public void setAmountMonth(int amountMonth) {
		this.amountMonth = amountMonth;
	}

	public String getContactBy() {
		return contactBy;
	}

	public void setContactBy(String contactBy) {
		this.contactBy = contactBy;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
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

}
