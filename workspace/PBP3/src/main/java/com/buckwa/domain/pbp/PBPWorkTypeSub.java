package com.buckwa.domain.pbp;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class PBPWorkTypeSub extends BaseDomain{
	
	private Long workTypeId;
	private Long workTypeSubId;
	
	private List<AcademicKPI> academicKPIList; 
	private String academicYear; 
	
	public List<AcademicKPI> getAcademicKPIList() {
		return academicKPIList;
	}
	public void setAcademicKPIList(List<AcademicKPI> academicKPIList) {
		this.academicKPIList = academicKPIList;
	}
	public Long getWorkTypeId() {
		return workTypeId;
	}
	public void setWorkTypeId(Long workTypeId) {
		this.workTypeId = workTypeId;
	}
	public Long getWorkTypeSubId() {
		return workTypeSubId;
	}
	public void setWorkTypeSubId(Long workTypeSubId) {
		this.workTypeSubId = workTypeSubId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	

}
