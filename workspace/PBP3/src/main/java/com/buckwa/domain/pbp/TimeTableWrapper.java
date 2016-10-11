package com.buckwa.domain.pbp;

import com.buckwa.domain.BaseDomain;

public class TimeTableWrapper extends BaseDomain{
	
	private String academicYear;
	private String semester;
	private String subjectId;
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
 
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	
	
	

}
