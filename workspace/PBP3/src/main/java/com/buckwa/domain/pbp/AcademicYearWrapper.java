package com.buckwa.domain.pbp;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class AcademicYearWrapper extends BaseDomain{
	
	private AcademicYear academicYear;
	private List<AcademicYear> academicYearList;
	private String currentAcademicYear;
	private String nextAcademicYear;
	private String previousAcademicYear;
	private String isNext;
	
	private String academicYearSelect;
	
	private List<AcademicYearEvaluateRound> academicYearEvaluateRoundList;
	
	
	
	public List<AcademicYearEvaluateRound> getAcademicYearEvaluateRoundList() {
		return academicYearEvaluateRoundList;
	}
	public void setAcademicYearEvaluateRoundList(
			List<AcademicYearEvaluateRound> academicYearEvaluateRoundList) {
		this.academicYearEvaluateRoundList = academicYearEvaluateRoundList;
	}
	public AcademicYear getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}
	public List<AcademicYear> getAcademicYearList() {
		return academicYearList;
	}
	public void setAcademicYearList(List<AcademicYear> academicYearList) {
		this.academicYearList = academicYearList;
	}
	public String getCurrentAcademicYear() {
		return currentAcademicYear;
	}
	public void setCurrentAcademicYear(String currentAcademicYear) {
		this.currentAcademicYear = currentAcademicYear;
	}
	public String getNextAcademicYear() {
		return nextAcademicYear;
	}
	public void setNextAcademicYear(String nextAcademicYear) {
		this.nextAcademicYear = nextAcademicYear;
	}
	public String getPreviousAcademicYear() {
		return previousAcademicYear;
	}
	public void setPreviousAcademicYear(String previousAcademicYear) {
		this.previousAcademicYear = previousAcademicYear;
	}
	public String getIsNext() {
		return isNext;
	}
	public void setIsNext(String isNext) {
		this.isNext = isNext;
	}
	public String getAcademicYearSelect() {
		return academicYearSelect;
	}
	public void setAcademicYearSelect(String academicYearSelect) {
		this.academicYearSelect = academicYearSelect;
	}
	
	
	
	
	 
}
