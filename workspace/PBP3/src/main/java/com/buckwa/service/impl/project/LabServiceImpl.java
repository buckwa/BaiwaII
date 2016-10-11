package com.buckwa.service.impl.project;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.project.LabDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Lab;
import com.buckwa.service.intf.project.LabService;
import com.buckwa.util.BuckWaConstants;

@Service("labService")
 
public class LabServiceImpl implements LabService {
	private static Logger logger = LoggerFactory.getLogger(LabServiceImpl.class);
	
	@Autowired
	private LabDao labDao;
 
	
	@Override
	public BuckWaResponse getAllByProjectId(BuckWaRequest request ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info("");
			String projectId = request.get("projectId")+"";	
			List<Lab>  returnList = labDao.getAllLabByProjectId(projectId);	 
			response.addResponse("labList",returnList);				
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
				 
			List<Lab>  returnList = labDao.getAllLabByLabCategoryId(moduleId);	 
			response.addResponse("labList",returnList);				
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
			
			Lab lab = (Lab)request.get("lab");	 
			labDao.create(lab);
		  
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
			Lab lab = (Lab)request.get("lab");
 
			 labDao.update(lab);	
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
			PagingBean returnBean = labDao.getByOffset(pagingBean);			
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
			logger.info(" LabServiceImpl.getLabById");
			String labId =  request.get("labId")+"";			
			Lab lab = labDao.getLabById(labId);						
			if(lab!=null){
				response.addResponse("lab",lab);
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
			String labId =  request.get("labId")+"";	 
			 labDao.deleteLabById(labId);				 
			
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
			Lab usecase = (Lab)request.get("lab");	 
			labDao.updateFilePath(usecase);	
			response.setSuccessCode("S002");	 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	
}
