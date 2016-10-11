package com.buckwa.service.impl.project;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.project.UseCaseDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.UseCase;
import com.buckwa.domain.project.Vision;
import com.buckwa.service.intf.project.UseCaseService;
import com.buckwa.util.BuckWaConstants;

@Service("useCaseService")
 
public class UseCaseServiceImpl implements UseCaseService {
	private static Logger logger = LoggerFactory.getLogger(UseCaseServiceImpl.class);
	
	@Autowired
	private UseCaseDao useCaseDao;
 
	
	@Override
	public BuckWaResponse getAllByProjectId(BuckWaRequest request ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info("");
			String projectId = request.get("projectId")+"";	
			List<UseCase>  returnList = useCaseDao.getAllUseCaseByProjectId(projectId);	 
			response.addResponse("useCaseList",returnList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	@Override
	public BuckWaResponse getAllByModuleId(BuckWaRequest request ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String moduleId =  request.get("moduleId")+"";	
				 
			List<UseCase>  returnList = useCaseDao.getAllUseCaseByModuleId(moduleId);	 
			response.addResponse("useCaseList",returnList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse create(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	 
			
			UseCase useCase = (UseCase)request.get("useCase");	 
			useCaseDao.create(useCase);
		  
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
			UseCase useCase = (UseCase)request.get("useCase");
 
			 useCaseDao.update(useCase);	
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
			PagingBean returnBean = useCaseDao.getByOffset(pagingBean);			
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
			logger.info(" UseCaseServiceImpl.getUseCaseById");
			String useCaseId =  request.get("useCaseId")+"";			
			UseCase useCase = useCaseDao.getUseCaseById(useCaseId);						
			if(useCase!=null){
				response.addResponse("useCase",useCase);
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
			String useCaseId =  request.get("useCaseId")+"";	 
			 useCaseDao.deleteUseCaseById(useCaseId);				 
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse updateFilePath(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		 			
			UseCase usecase = (UseCase)request.get("useCase");	 
			useCaseDao.updateFilePath(usecase);	
			response.setSuccessCode("S002");	 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	
}
