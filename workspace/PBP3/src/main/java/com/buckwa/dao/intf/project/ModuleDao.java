package com.buckwa.dao.intf.project;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Module;

public interface ModuleDao {
	

 
	public void update(Module module);
	public void create(Module module);
	public PagingBean getByOffset(PagingBean pagingBean);
	public List<Module> getAllModule();
	public List<Module> getAllModuleByProjectId(String projectId);
	public Module getModuleById(String moduleId,String projectId);
	public Module getModuleandDetailDesignById(String moduleId,String projectId);
	
	public void deleteModuleById(String moduleId);
 
	public boolean isModuleAlreadyUsege(String moduleId);
	 
	public Module getModuleandTestCaseById(String moduleId,String projectId);
	
	
	public List<Module> getAllModuleByProjectIdCountUseCase(String projectId);
	
	public List<Module> getAllModuleByProjectIdCountDetailDesign(String projectId);
	
	public List<Module> getAllModuleByProjectIdCountTestcase(String projectId);
	
	
	
	
}
