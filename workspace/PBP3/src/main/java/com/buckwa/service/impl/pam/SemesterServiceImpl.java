package com.buckwa.service.impl.pam;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.pam.SemesterDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Semester;
import com.buckwa.service.intf.pam.SemesterService;
import com.buckwa.util.BuckWaConstants;

@Service("semesterService")
 
public class SemesterServiceImpl implements SemesterService {
	private static Logger logger = LoggerFactory.getLogger(SemesterServiceImpl.class);
	
	@Autowired
	private SemesterDao semesterDao;
	@Override
	public BuckWaResponse getAll() {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List<Semester> semesterList = semesterDao.getAll();			
			response.addResponse("semesterList",semesterList);				
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
			Semester semester = (Semester)request.get("semester");		
			
			boolean issemesterNameExist = semesterDao.isExist(semester.getName(),Long.toString(semester.getYearId()));
			if(issemesterNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);
				semesterDao.create(semester);
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
			Semester semester = (Semester)request.get("semester");
			boolean issemesterNameExist = semesterDao.isExistForUpdate(semester.getName(),semester.getSemesterId());
			if(issemesterNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				semesterDao.update(semester);	
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
			PagingBean returnBean = semesterDao.getByOffset(pagingBean);			
			response.addResponse("pagingBean",returnBean);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}

	@Override
	public BuckWaResponse getByYearId(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String yearId = request.get("yearId")+"";		
			logger.info("BuckWaResponse getByYearId Year ID : >> "+yearId);
			List<Semester> returnList= semesterDao.getByYearId(new Long(yearId));			
			response.addResponse("semesterList",returnList);				
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
			logger.info(" semesterServiceImpl.getsemesterById");
			String semesterId =  request.get("semesterId")+"";			
			Semester semester = semesterDao.getById(semesterId);						
			if(semester!=null){
				response.addResponse("semester",semester);
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
			String semesterId = (String)request.get("semesterId");
			
			// Check Is semester are using
//			boolean issemesterAlreadyUsege = semesterDao.isAlreadyUsege(semesterId);
//			if(issemesterAlreadyUsege){
//				response.setStatus(BuckWaConstants.FAIL);
//				response.setErrorCode("E010");	
//			}else{
				semesterDao.deleteById(semesterId);
				response.setSuccessCode(BuckWaConstants.MSGCODE_DELETE_SUCESS);
//			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
}
