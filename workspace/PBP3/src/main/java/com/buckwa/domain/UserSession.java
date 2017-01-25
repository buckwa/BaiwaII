package com.buckwa.domain;

import java.util.List;

import com.buckwa.domain.BaseDomain;
import com.buckwa.domain.pbp.AcademicYear;

public class UserSession  {
	
 private String userName;
 private String firstName;
 private String lastName;
 private String currentAcademicYear;
 
 private String facultyCode;
 private String facultyName;
 private String departmentCode;
 private String departmentName;
 private String person_id;
 private Boolean isHead;
 private Boolean isDean;
 private Boolean isAdmin;

private List<AcademicYear> academicYearList;
 
 
public String getUserId() {
	return person_id;
}
public void setUserId(String userId) {
	this.person_id = userId;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getCurrentAcademicYear() {
	return currentAcademicYear;
}
public void setCurrentAcademicYear(String currentAcademicYear) {
	this.currentAcademicYear = currentAcademicYear;
}
public String getFacultyCode() {
	return facultyCode;
}
public void setFacultyCode(String facultyCode) {
	this.facultyCode = facultyCode;
}
public String getDepartmentCode() {
	return departmentCode;
}
public void setDepartmentCode(String departmentCode) {
	this.departmentCode = departmentCode;
}
public List<AcademicYear> getAcademicYearList() {
	return academicYearList;
}
public void setAcademicYearList(List<AcademicYear> academicYearList) {
	this.academicYearList = academicYearList;
}
public String getFacultyName() {
	return facultyName;
}
public void setFacultyName(String facultyName) {
	this.facultyName = facultyName;
}
public Boolean getIsHead() {
	return isHead;
}
public void setIsHead(Boolean isHead) {
	this.isHead = isHead;
}
public Boolean getIsDean() {
	return isDean;
}
public void setIsDean(Boolean isDean) {
	this.isDean = isDean;
}
public String getDepartmentName() {
	return departmentName;
}
public void setDepartmentName(String departmentName) {
	this.departmentName = departmentName;
}

public Boolean getIsAdmin() {
	return isAdmin;
}
public void setIsAdmin(Boolean isAdmin) {
	this.isAdmin = isAdmin;
}
 

}
