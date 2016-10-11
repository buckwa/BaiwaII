package com.buckwa.dao.intf.project;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.LabCategory;

public interface LabCategoryDao {
	

 
	public void update(LabCategory labCategory);
	public void create(LabCategory labCategory);
	public PagingBean getByOffset(PagingBean pagingBean);
	public List<LabCategory> getAllLabCategory();
	public List<LabCategory> getAllLabCategoryByProjectId(String projectId);
	public LabCategory getLabCategoryById(String labCategoryId,String projectId);
	public LabCategory getLabCategoryandDetailDesignById(String labCategoryId,String projectId);
	
	public void deleteLabCategoryById(String labCategoryId);
 
	public boolean isLabCategoryAlreadyUsege(String labCategoryId);
	 
	public LabCategory getLabCategoryandTestCaseById(String labCategoryId,String projectId);
	
	
	public List<LabCategory> getAllLabCategoryByProjectIdCountLab(String projectId);
	
	public List<LabCategory> getAllLabCategoryByProjectIdCountDetailDesign(String projectId);
	
	public List<LabCategory> getAllLabCategoryByProjectIdCountTestcase(String projectId);
	
	
	
	
}
