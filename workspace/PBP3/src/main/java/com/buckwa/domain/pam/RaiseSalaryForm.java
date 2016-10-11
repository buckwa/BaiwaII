package com.buckwa.domain.pam;

import java.util.List;

/*
@Author : Taechapon Himarat (Su)
@Create : Aug 20, 2012 11:04:14 PM
 */
public class RaiseSalaryForm {
	
	private List<Year> yearList;
	private List<Semester> semesterList;
	private int yearId;
	private int semesterId;
	
	
	public List<Year> getYearList() {
		return yearList;
	}
	
	public void setYearList(List<Year> yearList) {
		this.yearList = yearList;
	}
	
	public List<Semester> getSemesterList() {
		return semesterList;
	}
	
	public void setSemesterList(List<Semester> semesterList) {
		this.semesterList = semesterList;
	}

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public int getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}
	
}
