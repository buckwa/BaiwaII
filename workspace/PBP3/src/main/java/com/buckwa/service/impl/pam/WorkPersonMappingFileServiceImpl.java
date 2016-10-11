package com.buckwa.service.impl.pam;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pam.WorkPersonMappingFileDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.WorkPersonMappingFile;
import com.buckwa.service.intf.pam.WorkPersonMappingFileService;
import com.buckwa.util.BuckWaConstants;

/*
@Author : Taechapon Himarat (Su)
@Create : Sep 25, 2012 1:56:37 AM
 */
@Service("workPersonMappingFileService")
public class WorkPersonMappingFileServiceImpl implements WorkPersonMappingFileService {
	
	private static Logger logger = LoggerFactory.getLogger(WorkPersonMappingFileServiceImpl.class);
	
	
	@Autowired
	private WorkPersonMappingFileDao workPersonMappingFileDao;
	
	@Override
	public BuckWaResponse delete(BuckWaRequest request) {
		logger.info("WorkPersonServiceImpl.delete");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			WorkPersonMappingFile workPersonMappingFile = (WorkPersonMappingFile)request.get("workPersonMappingFile");
			workPersonMappingFileDao.delete(workPersonMappingFile);
			
			response.setSuccessCode(BuckWaConstants.MSGCODE_DELETE_SUCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

}
