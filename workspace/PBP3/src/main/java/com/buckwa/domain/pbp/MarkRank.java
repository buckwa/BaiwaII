package com.buckwa.domain.pbp;

import com.buckwa.domain.BaseDomain;

public class MarkRank extends BaseDomain{
	
	private Long markRankId;
	private int markFrom;
	private int markTo;
	private int salaryLevel;
	private String academicYear;
	
	private String employeeType;
	private String round;
	
	
	public Long getMarkRankId() {
		return markRankId;
	}
	public void setMarkRankId(Long markRankId) {
		this.markRankId = markRankId;
	}
	public int getMarkFrom() {
		return markFrom;
	}
	public void setMarkFrom(int markFrom) {
		this.markFrom = markFrom;
	}
	public int getMarkTo() {
		return markTo;
	}
	public void setMarkTo(int markTo) {
		this.markTo = markTo;
	}
	public int getSalaryLevel() {
		return salaryLevel;
	}
	public void setSalaryLevel(int salaryLevel) {
		this.salaryLevel = salaryLevel;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public String getRound() {
		return round;
	}
	public void setRound(String round) {
		this.round = round;
	}
	
	

}
