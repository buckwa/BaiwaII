package com.buckwa.dao.intf.project;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Lab;
import com.buckwa.domain.project.Vision;

public interface LabDao {
	

 
	public void update(Lab lab);
	public void create(Lab lab);
	public PagingBean getByOffset(PagingBean pagingBean);
	public List<Lab> getAllLabByProjectId(String id);
	public List<Lab> getAllLabByLabCategoryId(String moduleId);
	
	public Lab getLabById(String labId);
	public void deleteLabById(String labId);
 
	public boolean isLabAlreadyUsege(String labId);
	
	
	public void updateFilePath(Lab lab);
	 
 
	
}
