package com.buckwa.domain;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class UserSession  {
	
 private String userName;
 private String firstName;
 private String lastName;
 private String currentAcademicYear;
 
 
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
 
 

}
