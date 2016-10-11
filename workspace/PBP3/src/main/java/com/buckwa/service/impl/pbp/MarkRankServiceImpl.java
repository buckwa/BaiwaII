package com.buckwa.service.impl.pbp;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.pbp.MarkRankDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.MarkRank;
import com.buckwa.domain.pbp.MarkRankWrapper;
import com.buckwa.service.intf.pbp.MarkRankService;
import com.buckwa.util.BuckWaConstants;

@Service("markRankService")
 
public class MarkRankServiceImpl implements MarkRankService {
	private static Logger logger = LoggerFactory.getLogger(MarkRankServiceImpl.class);
	
	@Autowired
	private MarkRankDao markRankDao;
 

	@Override	
	public BuckWaResponse getByAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear = (String)request.get("academicYear");
			MarkRankWrapper markRankWrapper= ( MarkRankWrapper)markRankDao.getByAcademicYear(academicYear);
		 
			 response.addResponse("markRankWrapper",markRankWrapper);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getByRound(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear = (String)request.get("academicYear");
			MarkRankWrapper markRankWrapper= ( MarkRankWrapper)markRankDao.getByRound(academicYear);
		 
			 response.addResponse("markRankWrapper",markRankWrapper);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	
	
	@Override	
	public BuckWaResponse getByAcademicYearRound(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear = (String)request.get("academicYear");
			String employeeType = (String)request.get("employeeType");
			String round = (String)request.get("round");
			MarkRankWrapper markRankWrapper= ( MarkRankWrapper)markRankDao.getByAcademicYearRound(academicYear,employeeType,round);
		 
			 response.addResponse("markRankWrapper",markRankWrapper);
 	
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
			MarkRank markRank = (MarkRank)request.get("markRank");		
			
			boolean isNameExist = markRankDao.isExist(markRank);
			if(isNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				markRankDao.create(markRank);
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
	public BuckWaResponse edit(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			MarkRankWrapper markRankWrapper = (MarkRankWrapper)request.get("markRankWrapper");		
		 
				markRankDao.edit(markRankWrapper);
				response.setSuccessCode("S002"); 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse editRound(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			MarkRankWrapper markRankWrapper = (MarkRankWrapper)request.get("markRankWrapper");		
		 
				markRankDao.editRound(markRankWrapper);
				response.setSuccessCode("S002"); 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse delete(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			String markRankId =  request.get("markRankId")+"";		
	 
				markRankDao.delete(markRankId);
				response.setSuccessCode("S004");
			 

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
	public BuckWaResponse addNew(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			MarkRank markRank = (MarkRank)request.get("markRank");		
 
				markRankDao.addNew(markRank); 
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
	public BuckWaResponse addNewRound(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			MarkRank markRank = (MarkRank)request.get("markRank");		
		 
			 markRankDao.addNewRound(markRank ); 
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
