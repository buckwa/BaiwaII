package com.buckwa.service.impl.pam;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pam.YearDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Year;
import com.buckwa.service.intf.pam.KpiYearMappingService;
import com.buckwa.service.intf.pam.YearService;
import com.buckwa.util.BuckWaConstants;

@Service("yearService")
 
public class YearServiceImpl implements YearService {
	private static Logger logger = LoggerFactory.getLogger(YearServiceImpl.class);
	
	@Autowired
	private YearDao yearDao;
	
	@Autowired
	KpiYearMappingService kpiYearMappingService;
	
	@Override
	public BuckWaResponse getAll() {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List<Year> yearList = yearDao.getAll();			
			response.addResponse("yearList",yearList);				
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
			Year year = (Year)request.get("year");		
			
			boolean isYearNameExist = yearDao.isExist(year.getName());
			if(isYearNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);
				Long yearId = yearDao.create(year);				
				year.setYearId(yearId);
				 
				request = new BuckWaRequest();
				request.put("year",year);
				kpiYearMappingService.initialMappingByYear(request);
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
	public BuckWaResponse update(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			Year year = (Year)request.get("year");
			boolean isYearNameExist = yearDao.isExistForUpdate(year.getName(),year.getYearId());
			if(isYearNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				yearDao.update(year);	
				response.setSuccessCode(BuckWaConstants.MSGCODE_EDIT_SUCESS);	
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
			PagingBean returnBean = yearDao.getByOffset(pagingBean);			
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
			logger.info(" YearServiceImpl.getYearById");
			String yearId =  request.get("yearId")+"";			
			Year year = yearDao.getById(yearId);						
			if(year!=null){
				response.addResponse("year",year);
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
			String yearId = (String)request.get("yearId");
			
			// Check Is Year are using
			boolean isYearAlreadyUsege = yearDao.isAlreadyUsege(yearId);
			if(isYearAlreadyUsege){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");	
			}else{
				yearDao.deleteById(yearId);
				response.setSuccessCode(BuckWaConstants.MSGCODE_DELETE_SUCESS);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getYearCurrent(){
		BuckWaResponse response = new BuckWaResponse();
		try{					
			int year = yearDao.getYearCurrent();						
			response.addResponse("year",year);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
}
