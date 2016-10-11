package com.buckwa.dao.intf.project;

import java.util.List;

import com.buckwa.domain.project.TestCase;
import com.buckwa.domain.project.TestWrapper;

public interface TestCaseDao {
	public List<TestCase> getAllByModuleId(String moduleId);
	public List<TestWrapper> getAllByProjectId(String projectId);	
	public List<TestWrapper> getAllWrapByModuleId(String moduleId);		
	public TestCase getById(String id);	
	public TestCase  prepareTestCaseByDetailDesign(String moduleId,String id);
	public void create(TestCase testcase);
 
	
}
