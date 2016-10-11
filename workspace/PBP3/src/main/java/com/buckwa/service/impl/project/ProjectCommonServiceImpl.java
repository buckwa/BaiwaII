package com.buckwa.service.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.project.ProjectCommonDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.service.intf.project.ProjectCommonService;
import com.buckwa.util.BuckWaConstants;



@Service("projectCommonService")
public class ProjectCommonServiceImpl implements ProjectCommonService{

	@Autowired
	private ProjectCommonDao projectCommonDao;
	
	
	@Override
	public BuckWaResponse getLatestBusinessRuleNo(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();		
		try{						
			String projectId = request.get("projectId")+"";			
			String returnValue = projectCommonDao.getLatestBusinessRuleNoByProjectId(projectId);
			response.addResponse("returnValue", returnValue);
		}catch(Exception ex){			
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}		
		return response;
	}
	
	@Override
	public BuckWaResponse getLatestActorNo(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();		
		try{						
			String projectId = request.get("projectId")+"";			
			String returnValue = projectCommonDao.getLatestActorNoByProjectId(projectId);
			response.addResponse("returnValue", returnValue);
		}catch(Exception ex){			
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}		
		return response;
	}
	
	@Override
	public BuckWaResponse getLatestUseCaseNo(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();		
		try{						
			String projectId = request.get("projectId")+"";		
			String returnValue = projectCommonDao.getLatestUseCaseNo(projectId);
			response.addResponse("returnValue", returnValue);
		}catch(Exception ex){			
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}		
		return response;
	}
	@Override
	public BuckWaResponse getLatestModuleNo(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();		
		try{						
			String projectId = request.get("projectId")+"";		
			String returnValue = projectCommonDao.getLatestModuleNo(projectId);
			response.addResponse("returnValue", returnValue);
		}catch(Exception ex){			
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}		
		return response;
	} 
	
	
	@Override
	public BuckWaResponse getLatestDetailDesignNo(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();		
		try{						
			String projectId = request.get("projectId")+"";		
			String returnValue = projectCommonDao.getLatestDetailDesignNo(projectId);
			response.addResponse("returnValue", returnValue);
		}catch(Exception ex){			
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}		
		return response;
	} 
	
	@Override
	public BuckWaResponse getLatestTestCaseNo(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();		
		try{						
			String projectId = request.get("projectId")+"";		
			String returnValue = projectCommonDao.getLatestTestCaseNo(projectId);
			response.addResponse("returnValue", returnValue);
		}catch(Exception ex){			
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}		
		return response;
	} 
	
	
	@Override
	public BuckWaResponse getLatestLabNo(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();		
		try{						
		 
			 String returnValue = projectCommonDao.getLatestLabNo();
			 response.addResponse("returnValue", returnValue);
		}catch(Exception ex){			
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}		
		return response;
	} 
	
	@Override
	public BuckWaResponse getLabCategoryNo(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();		
		try{						
		 
			 String returnValue = projectCommonDao.getLabCategoryNo();
			 response.addResponse("returnValue", returnValue);
		}catch(Exception ex){			
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}		
		return response;
	} 	
	
	
	
	
	@Override
	public BuckWaResponse getLatestMessageNo(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();		
		try{						
			String projectId = request.get("projectId")+"";		
			String returnValue = projectCommonDao.getLatestMessageNo(projectId);
			response.addResponse("returnValue", returnValue);
		}catch(Exception ex){			
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}		
		return response;
	} 
	
	@Override
	public BuckWaResponse getLatestUtilNo(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();		
		try{						
			String projectId = request.get("projectId")+"";		
			String returnValue = projectCommonDao.getLatestUtilNo(projectId);
			response.addResponse("returnValue", returnValue);
		}catch(Exception ex){			
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}		
		return response;
	} 
}
