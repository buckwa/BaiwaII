package com.buckwa.service.impl.project;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.project.LabCategoryDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.LabCategory;
import com.buckwa.service.intf.project.LabCategoryService;
import com.buckwa.util.BuckWaConstants;

@Service("labCategoryService")
 
public class LabCategoryServiceImpl implements LabCategoryService {
	private static Logger logger = LoggerFactory.getLogger(LabCategoryServiceImpl.class);
	
	@Autowired
	private LabCategoryDao labCategoryDao;
 
	
	@Override
	public BuckWaResponse getAll( ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info("");
				 
			List<LabCategory>  returnList = labCategoryDao.getAllLabCategory();	 
			response.addResponse("labCategoryList",returnList);				
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
				 
			List<LabCategory>  returnList = labCategoryDao.getAllLabCategoryByProjectId(projectId);	 
			response.addResponse("labCategoryList",returnList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	@Override
	public BuckWaResponse getAllLabCategoryByProjectIdCountLab(BuckWaRequest request ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String projectId = request.get("projectId")+"";		
				 
			List<LabCategory>  returnList = labCategoryDao.getAllLabCategoryByProjectIdCountLab(projectId);	 
			response.addResponse("labCategoryList",returnList);	
			response.addResponse("totalCount",getTotalCount(returnList));	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	@Override
	public BuckWaResponse getAllLabCategoryByProjectIdCountDetailDesign(BuckWaRequest request ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String projectId = request.get("projectId")+"";		
				 
			List<LabCategory>  returnList = labCategoryDao.getAllLabCategoryByProjectIdCountDetailDesign(projectId);	 
			response.addResponse("labCategoryList",returnList);	
			response.addResponse("totalCount",getTotalCount(returnList));	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	@Override
	public BuckWaResponse getAllLabCategoryByProjectIdCountTestcase(BuckWaRequest request ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String projectId = request.get("projectId")+"";						 
			List<LabCategory>  returnList = labCategoryDao.getAllLabCategoryByProjectIdCountTestcase(projectId);	 
			response.addResponse("labCategoryList",returnList);			
			response.addResponse("totalCount",getTotalCount(returnList));			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	private Long getTotalCount (List<LabCategory>  returnListIn){
		Long returnVal  =0l;
		try{
			for(LabCategory tmp:returnListIn){
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
			LabCategory labCategory = (LabCategory)request.get("labCategory");		
 
		 labCategoryDao.create(labCategory);
		 

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
			LabCategory labCategory = (LabCategory)request.get("labCategory");
 
			 labCategoryDao.update(labCategory);	
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
			PagingBean returnBean = labCategoryDao.getByOffset(pagingBean);			
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
			logger.info(" LabCategoryServiceImpl.getLabCategoryById:"+request.get("labCategoryId"));
			String labCategoryId =  request.get("labCategoryId")+"";		
			String projectId =  request.get("projectId")+"";	
			LabCategory labCategory = labCategoryDao.getLabCategoryById(labCategoryId,projectId);						
			if(labCategory!=null){
				response.addResponse("labCategory",labCategory);
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
			logger.info(" LabCategoryServiceImpl.getByIdandDetailDesign:"+request.get("labCategoryId"));
			String labCategoryId =  request.get("labCategoryId")+"";		
			String projectId =  request.get("projectId")+"";		
			LabCategory labCategory = labCategoryDao.getLabCategoryandDetailDesignById(labCategoryId,projectId);						
			if(labCategory!=null){
				response.addResponse("labCategory",labCategory);
			} 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

	@Override
	public BuckWaResponse getLabCategoryandTestCaseById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" LabCategoryServiceImpl.getLabCategoryandTestCaseById:"+request.get("labCategoryId")+" projectId:"+request.get("projectId"));
			String labCategoryId =  request.get("labCategoryId")+"";		
			String projectId =  request.get("projectId")+"";		
			LabCategory labCategory = labCategoryDao.getLabCategoryandTestCaseById(labCategoryId,projectId);						
			if(labCategory!=null){
				response.addResponse("labCategory",labCategory);
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
			String labCategoryId =  request.get("labCategoryId")+""; 
			// Check Is LabCategory are using
			boolean isLabCategoryAlreadyUsege = labCategoryDao.isLabCategoryAlreadyUsege(labCategoryId);
			if(isLabCategoryAlreadyUsege){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");	
			}else{
				labCategoryDao.deleteLabCategoryById(labCategoryId);	
			} 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

}
