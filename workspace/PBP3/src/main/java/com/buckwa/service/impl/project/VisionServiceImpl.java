package com.buckwa.service.impl.project;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.project.VisionDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.ImagePath;
import com.buckwa.domain.project.Vision;
import com.buckwa.service.intf.project.VisionService;
import com.buckwa.util.BuckWaConstants;

@Service("visionService") 
public class VisionServiceImpl implements VisionService {
	private static Logger logger = LoggerFactory.getLogger(VisionServiceImpl.class);
	
	@Autowired
	private VisionDao visionDao;
  
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse update(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			Vision vision = (Vision)request.get("vision"); 
			 visionDao.update(vision);	
			 response.setSuccessCode("S002");	 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	 
		return response;
	}

 

	@Override
	public BuckWaResponse getVisionByProjectId(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" VisionServiceImpl.getVisionById");
			String projectId =  request.get("projectId")+"";			
			Vision vision = visionDao.getVisionByProjectId(projectId);						
			if(vision!=null){
				response.addResponse("vision",vision);
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
	public BuckWaResponse updateImagePath(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		 			
			Vision vision = (Vision)request.get("vision");	 
			visionDao.updateImagePath(vision);	
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
	public BuckWaResponse updateRefenceFilePath(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		 			
			Vision vision = (Vision)request.get("vision");	 
			logger.info("  ## updateRefenceFilePath:"+vision.getFilePathList());
			visionDao.updateRefenceFilePath(vision);	
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
	public BuckWaResponse removeImage(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		 			
			ImagePath imagePath = (ImagePath)request.get("imagePath");	 
			visionDao.removeImagePath(imagePath);	
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
	public BuckWaResponse removeFile(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		 			
			ImagePath imagePath = (ImagePath)request.get("imagePath");	 
			visionDao.removeImagePath(imagePath);	
			response.setSuccessCode("S002");	 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
}
