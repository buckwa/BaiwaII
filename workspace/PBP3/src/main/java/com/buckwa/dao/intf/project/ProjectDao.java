package com.buckwa.dao.intf.project;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Project;

public interface ProjectDao {	

	public Project getProject(Project project);
	public void update(Project project);
	public void create(Project project);
	public List<Project> getAllProject();	 
	public Project getProjectById(String projectId);
	public PagingBean getAllProjectByOffset(PagingBean pagingBean);
	
	public void deleteProjectById(String projectId) ;
	public String getProjectIdFromProjectName(String projectName);
	
	
	public boolean isProjectAlreadyUsege(String projectId);
	public boolean isProjectExist(String projectName);
	
	public boolean isProjectExistForUpdate(String projectName,String id);
	
	
	public List<Project> getAllProjectByUser(String userName);	
	
	
	public GrantedAuthority [] getGrantedAuthorityByUser (String userName,String projectId);	
}
