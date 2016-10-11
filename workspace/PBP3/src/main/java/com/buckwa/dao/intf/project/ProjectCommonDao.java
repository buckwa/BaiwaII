package com.buckwa.dao.intf.project;

public interface ProjectCommonDao {

	public String getLatestBusinessRuleNo();
	public String getLatestBusinessRuleNoByProjectId(String projectId);
	public String getLatestActorNoByProjectId(String projectId);
	
	public String getLatestUseCaseNo(String projectId);
	public String getLatestModuleNo(String projectId);
	
	public String getLatestUtilNo(String projectId);
	public String getLatestMessageNo(String projectId);
	
	public String getLatestDetailDesignNo(String projectId);
	
	public String getLatestTestCaseNo(String projectId);
	
	public String getLatestLabNo();
	public String getLabCategoryNo();
	 
	
}
