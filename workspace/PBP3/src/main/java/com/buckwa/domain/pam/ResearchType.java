package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 1:24:00 PM
 */
public class ResearchType extends BaseDomain{
	
	private Long researchTypeId;
	private String code;
	private String name;
	
	
	public Long getResearchTypeId() {
		return researchTypeId;
	}
	public void setResearchTypeId(Long researchTypeId) {
		this.researchTypeId = researchTypeId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}

