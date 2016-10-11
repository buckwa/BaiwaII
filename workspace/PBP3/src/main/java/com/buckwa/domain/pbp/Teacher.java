package com.buckwa.domain.pbp;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class Teacher extends BaseDomain{
	
	 private String facultyCode;
	 private String teacherIdStr;
	 private String academicYear;
	public String getFacultyCode() {
		return facultyCode;
	}
	public void setFacultyCode(String facultyCode) {
		this.facultyCode = facultyCode;
	}
	public String getTeacherIdStr() {
		return teacherIdStr;
	}
	public void setTeacherIdStr(String teacherIdStr) {
		this.teacherIdStr = teacherIdStr;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	 
	 
	 
	 
	 
	 
}
