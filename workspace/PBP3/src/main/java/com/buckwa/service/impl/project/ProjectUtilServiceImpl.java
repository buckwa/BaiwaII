package com.buckwa.service.impl.project;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.project.ProjectUtilDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Util;
import com.buckwa.service.intf.project.ProjectUtilService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.project.ProjectConstant;

@Service("projectUtilService")
 
public class ProjectUtilServiceImpl implements ProjectUtilService {
	private static Logger logger = LoggerFactory.getLogger(ProjectUtilServiceImpl.class);
	
	@Autowired
	private ProjectUtilDao projectUtilDao;
 
	
	@Override
	public BuckWaResponse getAll(BuckWaRequest request ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info("");
			List<Util>  returnList = new ArrayList();
			String utilType  = (String)request.get("type");
			if(ProjectConstant.PROJECT_UTIL_TYPE_D.equalsIgnoreCase(utilType)){
				returnList  = projectUtilDao.getAllUtil_D();
			}else{
				returnList  = projectUtilDao.getAllUtil_N();
			}
				 
			response.addResponse("utilList",returnList);				
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
			Util util = (Util)request.get("util");		
 
		 projectUtilDao.create(util);
		 

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
			Util util = (Util)request.get("util");
 
			 projectUtilDao.update(util);	
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
			PagingBean returnBean = projectUtilDao.getByOffset(pagingBean);			
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
			logger.info(" UtilServiceImpl.getUtilById:"+request.get("utilId"));
			String utilId =  request.get("utilId")+"";			
			Util util = projectUtilDao.getUtilById(utilId);						
			if(util!=null){
				response.addResponse("util",util);
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
			logger.info(" UtilServiceImpl.getByIdandDetailDesign:"+request.get("utilId"));
			String utilId =  request.get("utilId")+"";			
			Util util = projectUtilDao.getUtilandDetailDesignById(utilId);						
			if(util!=null){
				response.addResponse("util",util);
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
			String utilId =  request.get("utilId")+"";
			
			// Check Is Util are using
			boolean isUtilAlreadyUsege = projectUtilDao.isUtilAlreadyUsege(utilId);
			if(isUtilAlreadyUsege){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");	
			}else{
				projectUtilDao.deleteUtilById(utilId);	
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
}
