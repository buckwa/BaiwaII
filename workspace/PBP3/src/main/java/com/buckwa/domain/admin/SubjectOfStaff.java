package com.buckwa.domain.admin;

import com.buckwa.domain.BaseDomain;

public class SubjectOfStaff extends BaseDomain {

	private static final long serialVersionUID = -2726902026336375755L;

	private String subjectId;
	private String subjectName;
	private String year;
	private String term;
	private String sectionId;
	private String sectionCd;
	private String timeTableId;
	private String staffFlage;

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionCd() {
		return sectionCd;
	}

	public void setSectionCd(String sectionCd) {
		this.sectionCd = sectionCd;
	}

	public String getTimeTableId() {
		return timeTableId;
	}

	public void setTimeTableId(String timeTableId) {
		this.timeTableId = timeTableId;
	}

	public String getStaffFlage() {
		return staffFlage;
	}

	public void setStaffFlage(String staffFlage) {
		this.staffFlage = staffFlage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
