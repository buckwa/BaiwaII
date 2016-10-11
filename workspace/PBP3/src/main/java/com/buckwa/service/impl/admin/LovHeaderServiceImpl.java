package com.buckwa.service.impl.admin;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.admin.LovHeaderDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.LovDetail;
import com.buckwa.domain.common.LovHeader;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.service.intf.admin.LovHeaderService;
import com.buckwa.util.BuckWaConstants;


@Service("lovHeaderService") 
public class LovHeaderServiceImpl implements LovHeaderService {
	
	private static Logger logger = LoggerFactory.getLogger(LovHeaderServiceImpl.class);
	
	@Autowired
	private LovHeaderDao lovHeaderDao;
	
	
	@Override
	public BuckWaResponse getAll() {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List<LovHeader> lovHeaderList = lovHeaderDao.getAll();			
			response.addResponse("lovHeaderList",lovHeaderList);				
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
			LovHeader lovHeader = (LovHeader)request.get("lovHeader");					
			boolean isLovHeaderNameExist = lovHeaderDao.isExist(lovHeader.getCode(),lovHeader.getName());
			if(isLovHeaderNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				lovHeaderDao.create(lovHeader);
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
	public BuckWaResponse createLOVDetail(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			LovDetail lovDetail = (LovDetail)request.get("lovDetail");					
			boolean isLovHeaderNameExist = lovHeaderDao.isDetailExist(lovDetail.getCode(),lovDetail.getName(),lovDetail.getLovId());
			if(isLovHeaderNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				lovHeaderDao.createLOVDetail(lovDetail);
				
				LovHeader lovHeader = lovHeaderDao.getById(lovDetail.getHeaderId()+"");						
				if(lovHeader!=null){
					response.addResponse("lovHeader",lovHeader);
				} 
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
			LovHeader lovHeader = (LovHeader)request.get("lovHeader");
			boolean isLovHeaderNameExist = lovHeaderDao.isExistForUpdate(lovHeader.getCode(),lovHeader.getName(),lovHeader.getLovHeaderId());
			if(isLovHeaderNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				lovHeaderDao.update(lovHeader);	
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
			 
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");			 
			PagingBean returnBean = lovHeaderDao.getAllByOffset(pagingBean);		
			
			
			List<LovHeader> lovHeaderList = returnBean.getCurrentPageItem();
			 
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
			logger.info(" LovHeaderServiceImpl.getLovHeaderById");
			String id = request.get("lovHeaderId")+"";			
			LovHeader lovHeader = lovHeaderDao.getById(id);						
			if(lovHeader!=null){
				response.addResponse("lovHeader",lovHeader);
			} 
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	

	@Override
	public BuckWaResponse getDetailById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" LovHeaderServiceImpl.getDetailById");
			String id = request.get("lovDetailId")+"";			
			LovDetail lovDetail = lovHeaderDao.getDetailById(id);						
			if(lovDetail!=null){
				response.addResponse("lovDetail",lovDetail);
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
			String id =  request.get("lovHeaderId")+"";
	 
			lovHeaderDao.deleteById(id);	
			 
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse deleteDetailById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String id =  request.get("lovDetailId")+"";
	 
			lovHeaderDao.deleteDetailById(id);	
			 
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse updateDetail(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			LovHeader lovHeader = (LovHeader)request.get("lovHeader");
			//boolean isLovHeaderNameExist = lovHeaderDao.isExistForUpdate(lovHeader.getCode(),lovHeader.getName(),lovHeader.getLovHeaderId());
			//if(isLovHeaderNameExist){
			//	response.setStatus(BuckWaConstants.FAIL);
			//	response.setErrorCode("E002");		
			//}else{
				lovHeaderDao.updateDetail(lovHeader);	
				response.setSuccessCode("S002");	
		//	}
						
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	 
		return response;
	}
	
	
	@Override
	public BuckWaResponse getDetailsByCode(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {					
			logger.info(" LovHeaderServiceImpl.getDetailsByCode");
			String code = request.get("code").toString();			
			List<LovDetail> lovDetailList = lovHeaderDao.getDetailListByCode(code);						
			if (lovDetailList!=null){
				response.addResponse("lovDetailList", lovDetailList);
			} 
		}
		catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
 
	
}
