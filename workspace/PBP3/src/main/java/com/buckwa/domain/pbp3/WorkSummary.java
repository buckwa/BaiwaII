package com.buckwa.domain.pbp3;

import java.util.List;

import com.buckwa.domain.pbp.PBPWorkType;

public class WorkSummary {	
	
	private String academicYear;
	private String round;
	private String totalMark;
	 
	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public String getTotalMark() {
		return totalMark;
	}

	public void setTotalMark(String totalMark) {
		this.totalMark = totalMark;
	}

	public List<PBPWorkType> getpBPWorkTypeList() {
		return pBPWorkTypeList;
	}

	public void setpBPWorkTypeList(List<PBPWorkType> pBPWorkTypeList) {
		this.pBPWorkTypeList = pBPWorkTypeList;
	}

	private List<PBPWorkType> pBPWorkTypeList;
 
	
	
	 
	
	
	 
}
