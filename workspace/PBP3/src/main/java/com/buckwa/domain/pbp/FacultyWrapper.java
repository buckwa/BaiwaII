package com.buckwa.domain.pbp;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class FacultyWrapper extends BaseDomain{
	
	private List<Faculty> facultyList;
	private Faculty faculty;
	private String academicYear;
	private String academicYearSelect;
	private AcademicPerson president;
	
	private List<AcademicYear> academicYearList;
	
	
	public String getAcademicYearSelect() {
		return academicYearSelect;
	}
	public void setAcademicYearSelect(String academicYearSelect) {
		this.academicYearSelect = academicYearSelect;
	}
	public AcademicPerson getPresident() {
		return president;
	}
	public void setPresident(AcademicPerson president) {
		this.president = president;
	}
	public List<Faculty> getFacultyList() {
		return facultyList;
	}
	public void setFacultyList(List<Faculty> facultyList) {
		this.facultyList = facultyList;
	}
	public Faculty getFaculty() {
		return faculty;
	}
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public List<AcademicYear> getAcademicYearList() {
		return academicYearList;
	}
	public void setAcademicYearList(List<AcademicYear> academicYearList) {
		this.academicYearList = academicYearList;
	}
	
	
	
	

}
