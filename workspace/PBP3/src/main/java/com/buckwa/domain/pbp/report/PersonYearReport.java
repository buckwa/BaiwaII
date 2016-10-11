package com.buckwa.domain.pbp.report;

import java.io.Serializable;
import java.util.List;

import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pbp.AcademicYear;

public class PersonYearReport implements Serializable{
	
	private List<AcademicYear> academicYearList;
	private String academicYearSelect;
	private String academicYear;	
	private Person person;
	private String evaluateRound;
	
	
	public List<AcademicYear> getAcademicYearList() {
		return academicYearList;
	}
	public void setAcademicYearList(List<AcademicYear> academicYearList) {
		this.academicYearList = academicYearList;
	}
	public String getAcademicYearSelect() {
		return academicYearSelect;
	}
	public void setAcademicYearSelect(String academicYearSelect) {
		this.academicYearSelect = academicYearSelect;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getEvaluateRound() {
		return evaluateRound;
	}
	public void setEvaluateRound(String evaluateRound) {
		this.evaluateRound = evaluateRound;
	}
 
	
	
	
	 
}
