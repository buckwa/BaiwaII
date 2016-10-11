package com.buckwa.service.impl.pam;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.pam.ClassRoomProcessDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.ClassRoomProcess;
import com.buckwa.domain.pam.Person;
import com.buckwa.service.intf.pam.ClassRoomProcessService;
import com.buckwa.util.BuckWaConstants;

/*
 @Author : Wichien Prommese(Pe)
 @Create : Aug 5, 2012 10:05:04 AM
 */
@Service("classRoomProcessService")

public class ClassRoomProcessServiceImpl implements ClassRoomProcessService {

	private static Logger logger = LoggerFactory.getLogger(ClassRoomProcessServiceImpl.class);

	@Autowired
	private ClassRoomProcessDao classRoomProcessDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse create(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			
			ClassRoomProcess obj = (ClassRoomProcess) request.get("classRoomProcess");
			
			//update Class Room Process old
			classRoomProcessDao.clearFlagClassRoomProcess(obj.getSemesterId(), obj.getYearId());
			
			//remove pam subject,teacher,teach_table,teacher_teach
			classRoomProcessDao.removeTeacherTeach(obj.getSemesterId(), obj.getYearId());
			classRoomProcessDao.removeTeachTable(obj.getSemesterId(), obj.getYearId());
			classRoomProcessDao.removeTeacher(obj.getSemesterId(), obj.getYearId());
			classRoomProcessDao.removeSubject(obj.getSemesterId(), obj.getYearId());
			
			//import pam subject,teacher,teach_table,teacher_teach 
			classRoomProcessDao.importTeacher(obj.getYearId(), obj.getSemesterId());
			classRoomProcessDao.importSubject(obj.getYearId(), obj.getSemesterId(), obj.getCreateBy());
			classRoomProcessDao.importTeacherTeach(obj.getYearId(), obj.getSemesterId(), obj.getCreateBy());
			classRoomProcessDao.importTeachTable(obj.getYearId(), obj.getSemesterId(), obj.getCreateBy());
			
			//insert class room process
			classRoomProcessDao.create(obj);
			
			
		} catch (DuplicateKeyException dx) {
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getById(BuckWaRequest request) {
		logger.info("ClassRoomProcessServiceImpl.getById");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String classRoomProcessId =  request.get("classRoomProcessId")+"";			
			ClassRoomProcess obj = classRoomProcessDao.getById(classRoomProcessId);						
			response.addResponse("classRoomProcess",obj);
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
	public BuckWaResponse update(BuckWaRequest request) {
		logger.info("ClassRoomProcessServiceImpl.update");
		BuckWaResponse response = new BuckWaResponse();
		try{	
			ClassRoomProcess obj = (ClassRoomProcess)request.get("classRoomProcess");
			classRoomProcessDao.update(obj);
			response.setSuccessCode(BuckWaConstants.MSGCODE_EDIT_SUCESS);
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
			PagingBean returnBean = classRoomProcessDao.getAllByOffset(pagingBean);			
			response.addResponse("pagingBean",returnBean);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	@Override
	public BuckWaResponse getUserList(BuckWaRequest request){
		BuckWaResponse response = new BuckWaResponse();
		try{					
			Long yearId = (Long)request.get("yearId");
			Long semesterId = (Long)request.get("semesterId");
			List<Person> personList   = classRoomProcessDao.getUserList(yearId,semesterId);
			response.addResponse("personList", personList);
			response.setStatus(BuckWaConstants.SUCCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
}
