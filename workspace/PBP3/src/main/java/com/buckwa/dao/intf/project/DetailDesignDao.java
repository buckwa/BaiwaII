package com.buckwa.dao.intf.project;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.DetailDesign;
import com.buckwa.domain.project.Project;
import com.buckwa.domain.project.Vision;

public interface DetailDesignDao {
	

 
	public void update(DetailDesign detailDesign);
	public void create(DetailDesign detailDesign);
	public PagingBean getByOffset(PagingBean pagingBean);
	 	
	public List<DetailDesign> getAllByProjectId(String projectId);
	
	public List<DetailDesign> getAllDetailDesignByModuleId(String moduleId);
	
	public DetailDesign getDetailDesignById(String detailDesignId);
	public void deleteDetailDesignById(String detailDesignId);
 
	public boolean isDetailDesignAlreadyUsege(String detailDesignId);
	
	public Project getProjectByProjectId(String projectId);
	
	public void uploadScreen(DetailDesign detailDesign);
	
	public DetailDesign prepareDetailDesignByUseCase(String moduleId,String usecaseId);
	
	public void updateFilePath(DetailDesign detailDesign);
	
	
	
	 
 
	
}
