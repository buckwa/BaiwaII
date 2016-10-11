package com.buckwa.service.impl.project;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.project.ProjectDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Project;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.service.intf.project.ProjectService;
import com.buckwa.util.BuckWaConstants;

@Service("projectService") 
public class ProjectServiceImpl implements ProjectService {	
	private static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
	@Autowired
	private ProjectDao projectDao;
 
	@Override
	public BuckWaResponse getAll() {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List projectList = projectDao.getAllProject();			
			response.addResponse("projectList",projectList);						
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		response.setSuccessCode("S001");
		return response;
	}

	
	
	
	@Override
	public BuckWaResponse getAllByUser(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			
			String userName = (String)request.get("userName");	
			List projectList = projectDao.getAllProjectByUser(userName);			
			response.addResponse("projectList",projectList);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		response.setSuccessCode("S001");
		return response;
	}

	@Override
	public BuckWaResponse getAuthorityByUser(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			
			String userName = (String)request.get("username");	
			String projectId =  request.get("projectId")+"";
			
			GrantedAuthority [] aList = projectDao.getGrantedAuthorityByUser(userName,projectId);			
			response.addResponse("authorityList",aList);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		response.setSuccessCode("S001");
		return response;
	}
	
	



	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse create(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{
			Project project = (Project)request.get("project"); 
			boolean iseExist = projectDao.isProjectExist(project.getProjectName());
			if(iseExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				projectDao.create(project);
				response.setSuccessCode("S002");	
			}						
			
		}catch(DuplicateKeyException dx){
			//dx.printStackTrace();				
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		
		// Success return S001
		response.setSuccessCode("S001");
		return response;
	}
	


	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse update(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	 
			Project project = (Project)request.get("project"); 
			boolean iseExist = projectDao.isProjectExistForUpdate(project.getProjectName(),project.getProjectId()+"");
			if(iseExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				projectDao.update(project);
				response.setSuccessCode("S002");	
			}		
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		
		// Success return S001
		response.setSuccessCode("S001");
		return response;
	}


	@Override
	public BuckWaResponse getByOffset(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
 
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");			 
			PagingBean returnBean = projectDao.getAllProjectByOffset(pagingBean);			
			response.addResponse("pagingBean",returnBean);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		
		// Success return S001
		response.setSuccessCode("S001");
		return response;
	}


	@Override
	public BuckWaResponse getById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			
			logger.info(" projectServiceImpl.getprojectById");
			String projectId = (String)request.get("projectId");			
			Project project = projectDao.getProjectById(projectId);						
			if(project!=null){
				response.addResponse("project",project);
			}else{
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		
		// Success return S001
		response.setSuccessCode("S001");
		return response;
	}
	@Override
	public BuckWaResponse deleteById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String id =  request.get("projectId")+"";
 
			// Check Is using
			boolean isUsage = projectDao.isProjectAlreadyUsege(id);
			if(isUsage){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");	
			}else{
				projectDao.deleteProjectById(id);	
			}			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		response.setSuccessCode("S001");
		return response;
	}
}
