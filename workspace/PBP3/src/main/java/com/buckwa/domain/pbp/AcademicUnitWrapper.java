package com.buckwa.domain.pbp;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class AcademicUnitWrapper  extends BaseDomain{
	private List<AcademicUnit> academicUnitList;
	
	private String academicYear;

	public List<AcademicUnit> getAcademicUnitList() {
		return academicUnitList;
	}

	public void setAcademicUnitList(List<AcademicUnit> academicUnitList) {
		this.academicUnitList = academicUnitList;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	
	
	

}
