package com.buckwa.service.impl.project;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.project.BusinessRuleDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.BusinessRule;
import com.buckwa.service.intf.project.BusinessRuleService;
import com.buckwa.util.BuckWaConstants;

@Service("businessRuleService")
 
public class BusinessRuleServiceImpl implements BusinessRuleService {
	private static Logger logger = LoggerFactory.getLogger(BusinessRuleServiceImpl.class);
	
	@Autowired
	private BusinessRuleDao businessRuleDao;
 
	
	@Override
	public BuckWaResponse getAll( ) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info("");
				 
			List<BusinessRule>  returnList = businessRuleDao.getAllBusinessRule();	 
			response.addResponse("businessRuleList",returnList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}

	
	@Override
	public BuckWaResponse getAllByProjectId( BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info("");
			String projectId = request.get("projectId")+"";
			
			List<BusinessRule>  returnList = businessRuleDao.getAllBusinessRuleByProjectId(projectId);	 
			response.addResponse("businessRuleList",returnList);				
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
			BusinessRule businessRule = (BusinessRule)request.get("businessRule");		
 
		 businessRuleDao.create(businessRule);
		 

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
			BusinessRule businessRule = (BusinessRule)request.get("businessRule");
 
			 businessRuleDao.update(businessRule);	
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
			PagingBean returnBean = businessRuleDao.getByOffset(pagingBean);			
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
			logger.info(" BusinessRuleServiceImpl.getBusinessRuleById");
			String businessRuleId =  request.get("businessRuleId")+"";			
			BusinessRule businessRule = businessRuleDao.getBusinessRuleById(businessRuleId);						
			if(businessRule!=null){
				response.addResponse("businessRule",businessRule);
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
	public BuckWaResponse deleteById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String businessRuleId =  request.get("businessRuleId")+"";
			
			// Check Is BusinessRule are using
			boolean isBusinessRuleAlreadyUsege = businessRuleDao.isBusinessRuleAlreadyUsege(businessRuleId);
			if(isBusinessRuleAlreadyUsege){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");	
			}else{
				businessRuleDao.deleteBusinessRuleById(businessRuleId);	
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
}
