package com.buckwa.service.impl.admin.address;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.admin.address.ProvinceDao;
import com.buckwa.domain.admin.address.Province;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.service.intf.admin.address.ProvinceService;
import com.buckwa.util.BuckWaConstants;

@Service("provinceService")
 
public class ProvinceServiceImpl implements ProvinceService {
	private static Logger logger = LoggerFactory.getLogger(ProvinceServiceImpl.class);
	
	@Autowired
	private ProvinceDao provinceDao;
	@Override
	public BuckWaResponse getAll() {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List<Province> provinceList = provinceDao.getAll();			
			response.addResponse("provinceList",provinceList);				
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
			Province province = (Province)request.get("province");		
			
			boolean isProvinceNameExist = provinceDao.isExist(province.getName());
			if(isProvinceNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				provinceDao.create(province);
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
			Province province = (Province)request.get("province");
			boolean isProvinceNameExist = provinceDao.isExistForUpdate(province.getName(),province.getProvinceId());
			if(isProvinceNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				provinceDao.update(province);	
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
			PagingBean returnBean = provinceDao.getByOffset(pagingBean);			
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
			logger.info(" ProvinceServiceImpl.getProvinceById");
			String provinceId =  request.get("provinceId")+"";			
			Province province = provinceDao.getById(provinceId);						
			if(province!=null){
				response.addResponse("province",province);
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
			String provinceId = (String)request.get("provinceId");
			
			// Check Is Province are using
			boolean isProvinceAlreadyUsege = provinceDao.isAlreadyUsege(provinceId);
			if(isProvinceAlreadyUsege){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");	
			}else{
				provinceDao.deleteById(provinceId);	
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
}
