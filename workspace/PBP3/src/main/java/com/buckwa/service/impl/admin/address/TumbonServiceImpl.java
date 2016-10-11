package com.buckwa.service.impl.admin.address;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.admin.address.TumbonDao;
import com.buckwa.domain.admin.address.Tumbon;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.service.intf.admin.address.TumbonService;
import com.buckwa.util.BuckWaConstants;

@Service("tumbonService")
 
public class TumbonServiceImpl implements TumbonService {
	private static Logger logger = LoggerFactory.getLogger(TumbonServiceImpl.class);
	
	@Autowired
	private TumbonDao tumbonDao;
	@Override
	public BuckWaResponse getAll() {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List<Tumbon> tumbonList = tumbonDao.getAll();			
			response.addResponse("tumbonList",tumbonList);				
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
			Tumbon tumbon = (Tumbon)request.get("tumbon");		
			
			boolean isTumbonNameExist = tumbonDao.isExist(tumbon.getName());
			if(isTumbonNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				tumbonDao.create(tumbon);
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
			Tumbon tumbon = (Tumbon)request.get("tumbon");
			boolean isTumbonNameExist = tumbonDao.isExistForUpdate(tumbon.getName(),tumbon.getTumbonId());
			if(isTumbonNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				tumbonDao.update(tumbon);	
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
			PagingBean returnBean = tumbonDao.getByOffset(pagingBean);			
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
			logger.info(" TumbonServiceImpl.getTumbonById");
			String tumbonId =  request.get("tumbonId")+"";			
			Tumbon tumbon = tumbonDao.getById(tumbonId);						
			if(tumbon!=null){
				response.addResponse("tumbon",tumbon);
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
			String tumbonId = (String)request.get("tumbonId");
			
			// Check Is Tumbon are using
			boolean isTumbonAlreadyUsege = tumbonDao.isAlreadyUsege(tumbonId);
			if(isTumbonAlreadyUsege){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");	
			}else{
				tumbonDao.deleteById(tumbonId);	
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
}
