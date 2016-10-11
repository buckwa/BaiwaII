package com.buckwa.domain.pbp;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class ChainOfCommand  extends BaseDomain{
	
	private List<Faculty> facultyList;
	private String academicYear;
	public List<Faculty> getFacultyList() {
		return facultyList;
	}
	public void setFacultyList(List<Faculty> facultyList) {
		this.facultyList = facultyList;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	
	

}
