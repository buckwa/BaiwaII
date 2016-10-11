package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

public class Staff extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3275499912699048924L;
	private Long staffId;
	private String staffCode;
	private String fullName;
	private String sectionId;

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
