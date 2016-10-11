package com.buckwa.domain.project;

import com.buckwa.domain.BaseDomain;

public class BusinessRule extends BaseDomain{
	
	private Long businessRuleId;
	private String code;
	private String name;
	private String detail;
	private Long projectId;
	public Long getBusinessRuleId() {
		return businessRuleId;
	}
	public void setBusinessRuleId(Long businessRuleId) {
		this.businessRuleId = businessRuleId;
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
	

}
