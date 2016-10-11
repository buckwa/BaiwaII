package com.buckwa.service.impl.project;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.project.DetailDesignDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.DetailDesign;
import com.buckwa.domain.project.Project;
import com.buckwa.service.intf.project.DetailDesignService;
import com.buckwa.util.BuckWaConstants;

@Service("detailDesignService")
 
public class DetailDesignServiceImpl implements DetailDesignService {
	private static Logger logger = LoggerFactory.getLogger(DetailDesignServiceImpl.class);
	
	@Autowired
	private DetailDesignDao detailDesignDao;
 
	
	@Override
	public BuckWaResponse getAllByProjectId(BuckWaRequest request ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info("");
			String projectId = request.get("projectId")+"";	
			List<DetailDesign>  returnList = detailDesignDao.getAllByProjectId(projectId);	 
			response.addResponse("detailDesignList",returnList);				
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
				 
			List<DetailDesign>  returnList = detailDesignDao.getAllDetailDesignByModuleId(moduleId);	 
			response.addResponse("detailDesignList",returnList);				
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
			DetailDesign detailDesign = (DetailDesign)request.get("detailDesign");		
 
		 detailDesignDao.create(detailDesign);
		 

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
			DetailDesign detailDesign = (DetailDesign)request.get("detailDesign");
 
			 detailDesignDao.update(detailDesign);	
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
			PagingBean returnBean = detailDesignDao.getByOffset(pagingBean);			
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
			logger.info(" DetailDesignServiceImpl.getDetailDesignById");
			String detailDesignId =  request.get("detailDesignId")+"";			
			DetailDesign detailDesign = detailDesignDao.getDetailDesignById(detailDesignId);						
			if(detailDesign!=null){
				response.addResponse("detailDesign",detailDesign);
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
			String detailDesignId =  request.get("detailDesignId")+"";
	 
			 detailDesignDao.deleteDetailDesignById(detailDesignId);	
			 
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getProjectByProjectId(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String projectId = request.get("projectId")+"";
	 
			Project project =  detailDesignDao.getProjectByProjectId(projectId);	
			 if(project!=null){
			 response.addResponse("project",project);
			 } 
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
		
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse uploadScreen(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		 			
			DetailDesign detailDesign = (DetailDesign)request.get("detailDesign");	 
			detailDesignDao.uploadScreen(detailDesign);	
			response.setSuccessCode("S002");	 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	@Override
	public BuckWaResponse prepareDetailDesignByUseCase(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String moduleId =  request.get("moduleId")+""; 
			String usecaseId = (String)request.get("usecaseId"); 
			
			 		
			DetailDesign detailDesign = detailDesignDao.prepareDetailDesignByUseCase(moduleId,usecaseId);						
			if(detailDesign!=null){
				response.addResponse("detailDesign",detailDesign);
			} 
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse updateFilePath(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		 			
			DetailDesign detailDesign = (DetailDesign)request.get("detailDesign");	 
			detailDesignDao.updateFilePath(detailDesign);	
			response.setSuccessCode("S002");	 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	
	
}
