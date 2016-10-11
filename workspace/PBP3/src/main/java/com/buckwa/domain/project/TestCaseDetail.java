package com.buckwa.domain.project;

import com.buckwa.domain.BaseDomain;

public class TestCaseDetail extends BaseDomain{
	
	private Long testcaseDetailId;
	private String code;
	private String name;
	private String step;
	private String expected;
	private String result;
	private String testcaseStatus;
	private String testResultStatus;
	
	private Long testcaseId;
	private Long projectId;
	private Long moduleId;
	public Long getTestcaseDetailId() {
		return testcaseDetailId;
	}
	public void setTestcaseDetailId(Long testcaseDetailId) {
		this.testcaseDetailId = testcaseDetailId;
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
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getExpected() {
		return expected;
	}
	public void setExpected(String expected) {
		this.expected = expected;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTestcaseStatus() {
		return testcaseStatus;
	}
	public void setTestcaseStatus(String testcaseStatus) {
		this.testcaseStatus = testcaseStatus;
	}
	public String getTestResultStatus() {
		return testResultStatus;
	}
	public void setTestResultStatus(String testResultStatus) {
		this.testResultStatus = testResultStatus;
	}
	public Long getTestcaseId() {
		return testcaseId;
	}
	public void setTestcaseId(Long testcaseId) {
		this.testcaseId = testcaseId;
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
	
	
	
}
