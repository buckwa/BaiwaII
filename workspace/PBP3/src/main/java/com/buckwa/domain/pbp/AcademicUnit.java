package com.buckwa.domain.pbp;

import com.buckwa.domain.BaseDomain;

public class AcademicUnit  extends BaseDomain{
	
	private Long academicUnitId;	
	private String academicYear;
	public Long getAcademicUnitId() {
		return academicUnitId;
	}
	public void setAcademicUnitId(Long academicUnitId) {
		this.academicUnitId = academicUnitId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	

}
