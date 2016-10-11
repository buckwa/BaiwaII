package com.buckwa.domain.pbp;

import java.util.List;

public class AcademicKPIWrapper {

	private List<PBPWorkType> pBPWorkTypeList;
	private List<AcademicKPI> academicKPIList;
	private PBPWorkType pBPWorkType;

	private String academicYear;

	private String currentWorkTypeCode;

	private String workTypeCode;

	private String index;
	private String color;
	
	private List<AcademicYear> academicYearList;
	
	private List<Faculty> facultyList ;
	private String facultyCodeSelect;
	private String facultyName;
	private String workTypeName;

	public String getWorkTypeCode() {
		return workTypeCode;
	}

	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}

	public String getCurrentWorkTypeCode() {
		return currentWorkTypeCode;
	}

	public void setCurrentWorkTypeCode(String currentWorkTypeCode) {
		this.currentWorkTypeCode = currentWorkTypeCode;
	}

	public PBPWorkType getpBPWorkType() {
		return pBPWorkType;
	}

	public void setpBPWorkType(PBPWorkType pBPWorkType) {
		this.pBPWorkType = pBPWorkType;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public List<PBPWorkType> getpBPWorkTypeList() {
		return pBPWorkTypeList;
	}

	public void setpBPWorkTypeList(List<PBPWorkType> pBPWorkTypeList) {
		this.pBPWorkTypeList = pBPWorkTypeList;
	}

	public List<AcademicKPI> getAcademicKPIList() {
		return academicKPIList;
	}

	public void setAcademicKPIList(List<AcademicKPI> academicKPIList) {
		this.academicKPIList = academicKPIList;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<AcademicYear> getAcademicYearList() {
		return academicYearList;
	}

	public void setAcademicYearList(List<AcademicYear> academicYearList) {
		this.academicYearList = academicYearList;
	}

	public List<Faculty> getFacultyList() {
		return facultyList;
	}

	public void setFacultyList(List<Faculty> facultyList) {
		this.facultyList = facultyList;
	}

	public String getFacultyCodeSelect() {
		return facultyCodeSelect;
	}

	public void setFacultyCodeSelect(String facultyCodeSelect) {
		this.facultyCodeSelect = facultyCodeSelect;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public String getWorkTypeName() {
		return workTypeName;
	}

	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}

}
