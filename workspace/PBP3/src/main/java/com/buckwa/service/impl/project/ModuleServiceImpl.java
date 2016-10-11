package com.buckwa.service.impl.project;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.project.ModuleDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Module;
import com.buckwa.service.intf.project.ModuleService;
import com.buckwa.util.BuckWaConstants;

@Service("moduleService")
 
public class ModuleServiceImpl implements ModuleService {
	private static Logger logger = LoggerFactory.getLogger(ModuleServiceImpl.class);
	
	@Autowired
	private ModuleDao moduleDao;
 
	
	@Override
	public BuckWaResponse getAll( ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info("");
				 
			List<Module>  returnList = moduleDao.getAllModule();	 
			response.addResponse("moduleList",returnList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	@Override
	public BuckWaResponse getAllByProjectId(BuckWaRequest request ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String projectId = request.get("projectId")+"";		
				 
			List<Module>  returnList = moduleDao.getAllModuleByProjectId(projectId);	 
			response.addResponse("moduleList",returnList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	@Override
	public BuckWaResponse getAllModuleByProjectIdCountUseCase(BuckWaRequest request ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String projectId = request.get("projectId")+"";		
				 
			List<Module>  returnList = moduleDao.getAllModuleByProjectIdCountUseCase(projectId);	 
			response.addResponse("moduleList",returnList);	
			response.addResponse("totalCount",getTotalCount(returnList));	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	@Override
	public BuckWaResponse getAllModuleByProjectIdCountDetailDesign(BuckWaRequest request ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String projectId = request.get("projectId")+"";		
				 
			List<Module>  returnList = moduleDao.getAllModuleByProjectIdCountDetailDesign(projectId);	 
			response.addResponse("moduleList",returnList);	
			response.addResponse("totalCount",getTotalCount(returnList));	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	@Override
	public BuckWaResponse getAllModuleByProjectIdCountTestcase(BuckWaRequest request ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String projectId = request.get("projectId")+"";						 
			List<Module>  returnList = moduleDao.getAllModuleByProjectIdCountTestcase(projectId);	 
			response.addResponse("moduleList",returnList);			
			response.addResponse("totalCount",getTotalCount(returnList));			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	private Long getTotalCount (List<Module>  returnListIn){
		Long returnVal  =0l;
		try{
			for(Module tmp:returnListIn){
				Long counttmp = tmp.getChildCount();
				returnVal=returnVal+counttmp;
			}			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnVal;
	
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse create(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			Module module = (Module)request.get("module");		
 
		 moduleDao.create(module);
		 

		}catch(DuplicateKeyException dx){			
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	


	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse update(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			Module module = (Module)request.get("module");
 
			 moduleDao.update(module);	
			 response.setSuccessCode("S002");	
		 
						
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	 
		return response;
	}


	@Override
	public BuckWaResponse getByOffset(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info("");
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");			 
			PagingBean returnBean = moduleDao.getByOffset(pagingBean);			
			response.addResponse("pagingBean",returnBean);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}


	@Override
	public BuckWaResponse getById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" ModuleServiceImpl.getModuleById:"+request.get("moduleId"));
			String moduleId =  request.get("moduleId")+"";		
			String projectId =  request.get("projectId")+"";	
			Module module = moduleDao.getModuleById(moduleId,projectId);						
			if(module!=null){
				response.addResponse("module",module);
			}else{
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getByIdandDetailDesign(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" ModuleServiceImpl.getByIdandDetailDesign:"+request.get("moduleId"));
			String moduleId =  request.get("moduleId")+"";		
			String projectId =  request.get("projectId")+"";		
			Module module = moduleDao.getModuleandDetailDesignById(moduleId,projectId);						
			if(module!=null){
				response.addResponse("module",module);
			} 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

	@Override
	public BuckWaResponse getModuleandTestCaseById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" ModuleServiceImpl.getModuleandTestCaseById:"+request.get("moduleId")+" projectId:"+request.get("projectId"));
			String moduleId =  request.get("moduleId")+"";		
			String projectId =  request.get("projectId")+"";		
			Module module = moduleDao.getModuleandTestCaseById(moduleId,projectId);						
			if(module!=null){
				response.addResponse("module",module);
			} 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	
	@Override
	public BuckWaResponse deleteById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String moduleId =  request.get("moduleId")+""; 
			// Check Is Module are using
			boolean isModuleAlreadyUsege = moduleDao.isModuleAlreadyUsege(moduleId);
			if(isModuleAlreadyUsege){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");	
			}else{
				moduleDao.deleteModuleById(moduleId);	
			} 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

}
