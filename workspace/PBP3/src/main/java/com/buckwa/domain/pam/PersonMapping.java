package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

public class PersonMapping extends BaseDomain  {
	
	private Long yearId;
	private Long semesterId;
	private Long userId1;
	private Long userId2;
	private String fullName1;
	private String fullName2;
	
	
	public Long getYearId() {
		return yearId;
	}
	public void setYearId(Long yearId) {
		this.yearId = yearId;
	}
	public Long getSemesterId() {
		return semesterId;
	}
	public void setSemesterId(Long semesterId) {
		this.semesterId = semesterId;
	}
	public Long getUserId1() {
		return userId1;
	}
	public void setUserId1(Long userId1) {
		this.userId1 = userId1;
	}
	public Long getUserId2() {
		return userId2;
	}
	public void setUserId2(Long userId2) {
		this.userId2 = userId2;
	}
	public String getFullName1() {
		return fullName1;
	}
	public void setFullName1(String fullName1) {
		this.fullName1 = fullName1;
	}
	public String getFullName2() {
		return fullName2;
	}
	public void setFullName2(String fullName2) {
		this.fullName2 = fullName2;
	}
	
	
	
	
	

}
