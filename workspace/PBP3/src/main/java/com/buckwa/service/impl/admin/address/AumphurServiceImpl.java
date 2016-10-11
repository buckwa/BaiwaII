package com.buckwa.service.impl.admin.address;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.admin.address.AumphurDao;
import com.buckwa.domain.admin.address.Aumphur;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.service.intf.admin.address.AumphurService;
import com.buckwa.util.BuckWaConstants;

@Service("aumphurService")
 
public class AumphurServiceImpl implements AumphurService {
	private static Logger logger = LoggerFactory.getLogger(AumphurServiceImpl.class);
	
	@Autowired
	private AumphurDao aumphurDao;
	@Override
	public BuckWaResponse getAll() {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List<Aumphur> aumphurList = aumphurDao.getAll();			
			response.addResponse("aumphurList",aumphurList);				
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
			Aumphur aumphur = (Aumphur)request.get("aumphur");		
			
			boolean isAumphurNameExist = aumphurDao.isExist(aumphur.getName());
			if(isAumphurNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				aumphurDao.create(aumphur);
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
			Aumphur aumphur = (Aumphur)request.get("aumphur");
			boolean isAumphurNameExist = aumphurDao.isExistForUpdate(aumphur.getName(),aumphur.getAumphurId());
			if(isAumphurNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				aumphurDao.update(aumphur);	
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
			PagingBean returnBean = aumphurDao.getByOffset(pagingBean);			
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
			logger.info(" AumphurServiceImpl.getAumphurById");
			String aumphurId =  request.get("aumphurId")+"";			
			Aumphur aumphur = aumphurDao.getById(aumphurId);						
			if(aumphur!=null){
				response.addResponse("aumphur",aumphur);
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
			String aumphurId = (String)request.get("aumphurId");
			
			// Check Is Aumphur are using
			boolean isAumphurAlreadyUsege = aumphurDao.isAlreadyUsege(aumphurId);
			if(isAumphurAlreadyUsege){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");	
			}else{
				aumphurDao.deleteById(aumphurId);	
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
}
