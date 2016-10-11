package com.buckwa.service.impl.pam;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pam.SubjectDao;
import com.buckwa.dao.intf.pam.TeacherTeachDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Subject;
import com.buckwa.domain.pam.TeachTable;
import com.buckwa.service.intf.pam.TeacherTeachService;
import com.buckwa.util.BuckWaConstants;

/*
 @Author : Wichien Prommese(Pe)
 @Create : Aug 5, 2012 10:05:04 AM
 */
@Service("teacherTeachService")

public class TeacherTeachServiceImpl implements TeacherTeachService {

	private static Logger logger = LoggerFactory.getLogger(TeacherTeachServiceImpl.class);

	@Autowired
	private TeacherTeachDao teacherTeachDao;
	
	@Autowired
	private SubjectDao subjectDao;
	
	@Override
	public BuckWaResponse getTeachTableList(BuckWaRequest request){
		logger.info("TeacherTeachServiceImpl.getTeachTableList");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			Long userId =  (Long)request.get("userId");
			Long yearId =  (Long)request.get("yearId");
			List<TeachTable> teachTableList = teacherTeachDao.getTeachTableList(userId, yearId)	;			
			response.addResponse("teachTableList",teachTableList);
			response.setSuccessCode("S002");
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getTeachTableById(BuckWaRequest request){
		logger.info("TeacherTeachServiceImpl.getTeachTableById");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			Long teachTableId =  (Long)request.get("teachTableId");
			String degree = (String)request.get("degree");
			Long yearId =  (Long)request.get("yearId");
			Long semesterId =  (Long)request.get("semesterId");
			TeachTable  obj= teacherTeachDao.getTeachTable(teachTableId,degree,yearId,semesterId)	;			
			response.addResponse("TeachTable",obj);
			response.setSuccessCode("S002");
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getSubjectByOffset(BuckWaRequest request){
		logger.info("getSubjectByOffset");
		BuckWaResponse response = new BuckWaResponse();
		try {
			PagingBean pagingBean = (PagingBean) request.get("pagingBean");	
			PagingBean returnBean = subjectDao.getAllSubjectByOffset(pagingBean);
			response.addResponse("pagingBean", returnBean);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getSubjectById(BuckWaRequest request){
		logger.info("getSubjectById");
		BuckWaResponse response = new BuckWaResponse();
		try {
			Long subjectId = (Long)request.get("subjectId");
			String degree = (String)request.get("degree");
			Subject obj = subjectDao.getSubjectById(subjectId,degree);
			response.addResponse("subject", obj);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}
}
