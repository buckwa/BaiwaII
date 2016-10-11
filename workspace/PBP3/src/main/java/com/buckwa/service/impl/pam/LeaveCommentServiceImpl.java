package com.buckwa.service.impl.pam;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.pam.LeaveCommentDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.LeaveComment;
import com.buckwa.service.intf.pam.LeaveCommentService;
import com.buckwa.util.BuckWaConstants;

/*
 @Author : Wichien Prommese(Pe)
 @Create : Aug 5, 2012 10:05:04 AM
 */
@Service("leaveCommentService")
public class LeaveCommentServiceImpl implements LeaveCommentService {

	private static Logger logger = LoggerFactory.getLogger(LeaveCommentServiceImpl.class);

	@Autowired
	private LeaveCommentDao leaveCommentDao;
		
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse create(BuckWaRequest request) {
		logger.info("LeaveCommentServiceImpl.create");
		BuckWaResponse response = new BuckWaResponse();
		try {
			LeaveComment obj = (LeaveComment) request.get("leaveComment");
			leaveCommentDao.create(obj);
			response.addResponse("leaveComment", obj);
			response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);
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
	public BuckWaResponse getComments(BuckWaRequest request) {
		logger.info("LeaveCommentServiceImpl.getById");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String docno =  request.get("docno")+"";		
			String leaveTypeCode =  request.get("leaveTypeCode")+"";		
			List<LeaveComment> obj = leaveCommentDao.getComments(docno, leaveTypeCode);					
			if(obj!=null){
				response.addResponse("leaveComments",obj);
				response.setSuccessCode("S002");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
}
