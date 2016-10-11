package com.buckwa.domain.pbp;

import com.buckwa.domain.BaseDomain;

public class AcademicRule  extends BaseDomain{
	
	private Long academicRuleId;
	private String academicYear;
	public Long getAcademicRuleId() {
		return academicRuleId;
	}
	public void setAcademicRuleId(Long academicRuleId) {
		this.academicRuleId = academicRuleId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	
	
}
