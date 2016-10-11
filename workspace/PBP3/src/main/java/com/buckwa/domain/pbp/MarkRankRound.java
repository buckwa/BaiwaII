package com.buckwa.domain.pbp;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class MarkRankRound extends BaseDomain{
	
	 private String employeeType; 
	 private String round;
	 private String academicYear;
	 private List<MarkRank> markRankList;
	 
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
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public List<MarkRank> getMarkRankList() {
		return markRankList;
	}
	public void setMarkRankList(List<MarkRank> markRankList) {
		this.markRankList = markRankList;
	}
	 
	 
}
