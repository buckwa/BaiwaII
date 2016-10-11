package com.buckwa.service.impl.pbp;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.pbp.AcademicUnitDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.AcademicUnit;
import com.buckwa.domain.pbp.AcademicUnitWrapper;
import com.buckwa.domain.pbp.MarkRank;
import com.buckwa.domain.pbp.MarkRankWrapper;
import com.buckwa.service.intf.pbp.AcademicUnitService;
import com.buckwa.util.BuckWaConstants;

@Service("academicUnitService")
 
public class AcademicUnitServiceImpl implements AcademicUnitService {
	private static Logger logger = LoggerFactory.getLogger(AcademicUnitServiceImpl.class);
	
	@Autowired
	private AcademicUnitDao academicUnitDao;
 

	@Override	
	public BuckWaResponse getByAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear = (String)request.get("academicYear");
			AcademicUnitWrapper academicUnitWrapper= ( AcademicUnitWrapper)academicUnitDao.getByAcademicYear(academicYear);
		 
			 response.addResponse("academicUnitWrapper",academicUnitWrapper);
 	
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
			AcademicUnit academicUnit = (AcademicUnit)request.get("academicUnit");		
			
			boolean isNameExist = academicUnitDao.isNameExist(academicUnit );
			if(isNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				academicUnitDao.create(academicUnit);
				response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);
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
			AcademicUnit academicUnit = (AcademicUnit)request.get("academicUnit");	
			boolean isRoleNameExist = academicUnitDao.isExistForUpdate(academicUnit);
			if(isRoleNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				academicUnitDao.update(academicUnit);	
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
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse edit(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			AcademicUnitWrapper academicUnitWrapper = (AcademicUnitWrapper)request.get("academicUnitWrapper");		
		 
			academicUnitDao.edit(academicUnitWrapper);
			response.setSuccessCode("S002");
			 
 			
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
			 
			String academicUnitId =  request.get("academicUnitId")+"";			
			AcademicUnit academicUnit = academicUnitDao.getById(academicUnitId);						
			if(academicUnit!=null){
				response.addResponse("academicUnit",academicUnit);
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
			String academicUnitId =  request.get("academicUnitId")+""; 
			 
			boolean isAlreadyUsege = academicUnitDao.isAlreadyUsege(academicUnitId);
			if(isAlreadyUsege){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");	
			}else{
				academicUnitDao.deleteById(academicUnitId);	
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
	public BuckWaResponse addNew(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			AcademicUnit academicUnit = (AcademicUnit)request.get("academicUnit");		
 
			academicUnitDao.addNew(academicUnit); 
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
	
 
}
