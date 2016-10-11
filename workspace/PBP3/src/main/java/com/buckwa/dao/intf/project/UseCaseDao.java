package com.buckwa.dao.intf.project;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.UseCase;
import com.buckwa.domain.project.Vision;

public interface UseCaseDao {
	

 
	public void update(UseCase useCase);
	public void create(UseCase useCase);
	public PagingBean getByOffset(PagingBean pagingBean);
	public List<UseCase> getAllUseCaseByProjectId(String id);
	public List<UseCase> getAllUseCaseByModuleId(String moduleId);
	
	public UseCase getUseCaseById(String useCaseId);
	public void deleteUseCaseById(String useCaseId);
 
	public boolean isUseCaseAlreadyUsege(String useCaseId);
	
	
	public void updateFilePath(UseCase usecase);
	 
 
	
}
