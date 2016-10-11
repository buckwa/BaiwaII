package com.buckwa.service.impl.pam;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.pam.KpiCategoryDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.KpiCategory;
import com.buckwa.service.intf.pam.KpiCategoryService;
import com.buckwa.util.BuckWaConstants;

@Service("kpiCategoryService")
 
public class KpiCategoryServiceImpl implements KpiCategoryService {
	private static Logger logger = LoggerFactory.getLogger(KpiCategoryServiceImpl.class);
	
	@Autowired
	private KpiCategoryDao kpiCategoryDao;
	@Override
	public BuckWaResponse getAll() {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List<KpiCategory> kpiCategoryList = kpiCategoryDao.getAll();			
			response.addResponse("kpiCategoryList",kpiCategoryList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

	@Override
 
	public BuckWaResponse create(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			KpiCategory kpiCategory = (KpiCategory)request.get("kpiCategory");		
			boolean isKpiCategoryNameExist = kpiCategoryDao.isExist(kpiCategory.getName());
			if(isKpiCategoryNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);	
				kpiCategoryDao.create(kpiCategory);
			}

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
			KpiCategory kpiCategory = (KpiCategory)request.get("kpiCategory");
			boolean isKpiCategoryNameExist = kpiCategoryDao.isExistForUpdate(kpiCategory.getName(),kpiCategory.getKpiCategoryId());
			if(isKpiCategoryNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				kpiCategoryDao.update(kpiCategory);	
				response.setSuccessCode("S002");	
			}
						
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
			PagingBean returnBean = kpiCategoryDao.getByOffset(pagingBean);			
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
			logger.info(" KpiCategoryServiceImpl.getKpiCategoryById");
			String kpiCategoryId =  request.get("kpiCategoryId")+"";			
			KpiCategory kpiCategory = kpiCategoryDao.getById(kpiCategoryId);						
			if(kpiCategory!=null){
				response.addResponse("kpiCategory",kpiCategory);
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
			String kpiCategoryId = (String)request.get("kpiCategoryId");
			
			// Check Is KpiCategory are using
			boolean isKpiCategoryAlreadyUsege = kpiCategoryDao.isAlreadyUsege(kpiCategoryId);
			if(isKpiCategoryAlreadyUsege){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");	
			}else{
				kpiCategoryDao.deleteById(kpiCategoryId);	
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	public BuckWaResponse getKpiCategoryIdByYearIdAndPersonType(BuckWaRequest request) {
		logger.info("- start");
		
		BuckWaResponse response = new BuckWaResponse();
		try {					
			String yearId     = (String) request.get("yearId");
			String personType = (String) request.get("personType");
			
			String kpiCategoryId = kpiCategoryDao.getKpiCategoryIdByYearIdAndPersonType(yearId, personType);
			response.addResponse("kpiCategoryId", kpiCategoryId);
		}
		catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		
		return response;
	}
}
