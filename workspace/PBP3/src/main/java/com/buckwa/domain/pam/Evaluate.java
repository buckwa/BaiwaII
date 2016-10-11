package com.buckwa.domain.pam;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.buckwa.domain.BaseDomain;

public class Evaluate extends BaseDomain implements Serializable{

	private String evaluateId;
	private String citizenId;
	private String firstName;
	private String lastName;
	private String userName;
	private Timestamp evalDate;
	private String yearName;

	
	
	public String getYearName() {
		return yearName;
	}
	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	private List<PersonEvaluateMapping> personEvaluateMappingList;
	
	public String getEvaluateId() {
		return evaluateId;
	}
	public void setEvaluateId(String evaluateId) {
		this.evaluateId = evaluateId;
	}
	public String getCitizenId() {
		return citizenId;
	}
	public void setCitizenId(String citizenId) {
		this.citizenId = citizenId;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Timestamp getEvalDate() {
		return evalDate;
	}
	public void setEvalDate(Timestamp evalDate) {
		this.evalDate = evalDate;
	}
	public List<PersonEvaluateMapping> getPersonEvaluateMappingList() {
		return personEvaluateMappingList;
	}
	public void setPersonEvaluateMappingList(
			List<PersonEvaluateMapping> personEvaluateMappingList) {
		this.personEvaluateMappingList = personEvaluateMappingList;
	}
	
	@Override
	public String toString() {
		return "Evaluate [evaluateId=" + evaluateId + ", citizenId="
				+ citizenId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", userName=" + userName + ", evalDate="
				+ evalDate + ", personEvaluateMappingList="
				+ personEvaluateMappingList + "]";
	}
	

}
