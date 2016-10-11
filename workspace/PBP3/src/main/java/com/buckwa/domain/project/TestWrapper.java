package com.buckwa.domain.project;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class TestWrapper extends BaseDomain{
 
	private DetailDesign detailDesign;
	private List<TestCase> testCaseList;
	public DetailDesign getDetailDesign() {
		return detailDesign;
	}
	public void setDetailDesign(DetailDesign detailDesign) {
		this.detailDesign = detailDesign;
	}
	public List<TestCase> getTestCaseList() {
		return testCaseList;
	}
	public void setTestCaseList(List<TestCase> testCaseList) {
		this.testCaseList = testCaseList;
	}
	
	
	
	 
}
