package com.buckwa.service.impl.pam;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pam.SubjectDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.Subject;
import com.buckwa.service.intf.pam.SubjectService;
import com.buckwa.util.BuckWaConstants;

/*
 @Author : Wichien Prommese(Pe)
 @Create : Aug 5, 2012 10:05:04 AM
 */
@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {

	private static Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

	@Autowired
	private SubjectDao subjectDao;
	
	
	@Override
	public BuckWaResponse getAll(BuckWaRequest request) {
		logger.info("SubjectServiceImpl.getAll");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			Long yearId =  (Long)request.get("yearId");			
			Long semesterId =  (Long)request.get("semesterId");			
			List<Subject> objList= subjectDao.getAll(yearId,semesterId);
			response.addResponse("subjectList",objList);
			response.setSuccessCode("S002");
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getAllByTeacherList(BuckWaRequest request) {
		logger.info("SubjectServiceImpl.userList");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			Long yearId =  (Long)request.get("yearId");			
			Long semesterId =  (Long)request.get("semesterId");			
			String teacherList =  (String)request.get("userList");			
			List<Subject> objList= subjectDao.getAll(yearId,semesterId,teacherList);
			response.addResponse("subjectList",objList);
			response.setSuccessCode("S002");
		}catch(Exception ex){
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
}
