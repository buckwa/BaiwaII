package com.buckwa.domain.pbp;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class MarkRankWrapper extends BaseDomain{
	
	private MarkRank markRank;
	private List<MarkRank> markRankList;
	
	private String academicYear;
	
	private List<MarkRankRound> markRankRoundList;
	 private String employeeType; 
	 private String round;
	
	public MarkRank getMarkRank() {
		return markRank;
	}
	public void setMarkRank(MarkRank markRank) {
		this.markRank = markRank;
	}
	public List<MarkRank> getMarkRankList() {
		return markRankList;
	}
	public void setMarkRankList(List<MarkRank> markRankList) {
		this.markRankList = markRankList;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public List<MarkRankRound> getMarkRankRoundList() {
		return markRankRoundList;
	}
	public void setMarkRankRoundList(List<MarkRankRound> markRankRoundList) {
		this.markRankRoundList = markRankRoundList;
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
