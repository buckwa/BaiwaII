package com.buckwa.domain.project;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class TestCase extends BaseDomain{
	
	private Long testcaseId;
	private String code;
	private String name;
	private String summary; 
	private Long detailDesignId;
	
	
	private Long moduleId;
	private String moduleName;
	private Long usecaseId;
	private String usecaseName;
	private Long projectId;
	
	
	private List<TestCaseDetail> testcaseDetailList;
	
	
	public Long getTestcaseId() {
		return testcaseId;
	}
	public void setTestcaseId(Long testcaseId) {
		this.testcaseId = testcaseId;
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public Long getDetailDesignId() {
		return detailDesignId;
	}
	public void setDetailDesignId(Long detailDesignId) {
		this.detailDesignId = detailDesignId;
	}
	public List<TestCaseDetail> getTestcaseDetailList() {
		return testcaseDetailList;
	}
	public void setTestcaseDetailList(List<TestCaseDetail> testcaseDetailList) {
		this.testcaseDetailList = testcaseDetailList;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public Long getUsecaseId() {
		return usecaseId;
	}
	public void setUsecaseId(Long usecaseId) {
		this.usecaseId = usecaseId;
	}
	public String getUsecaseName() {
		return usecaseName;
	}
	public void setUsecaseName(String usecaseName) {
		this.usecaseName = usecaseName;
	}
	
	
	
	
	 
}
