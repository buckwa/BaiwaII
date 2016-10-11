package com.buckwa.service.impl.project;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.project.TestCaseDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.project.TestCase;
import com.buckwa.domain.project.TestWrapper;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.service.intf.project.TestCaseService;
import com.buckwa.util.BuckWaConstants;


@Service("testCaseService")
public class TestCaseServiceImpl implements TestCaseService {
	private static Logger logger = LoggerFactory.getLogger(TestCaseServiceImpl.class);
	@Autowired
	private TestCaseDao testCaseDao;

	@Override
	public BuckWaResponse getAllByModuleId(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" ###TestCaseServiceImpl.getAllByModuleId:"+request.get("moduleId"));
			String moduleId =  request.get("moduleId")+"";		 
			List<TestCase>  testCastList = testCaseDao.getAllByModuleId(moduleId);	 
			response.addResponse("testCastList",testCastList);	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}

	@Override
	public BuckWaResponse getAllByProjectId(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String id =  request.get("projectId")+"";					 
			List<TestWrapper>  testWrapperList = testCaseDao.getAllByProjectId(id);	 
			response.addResponse("testWrapperList",testWrapperList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
 
	@Override
	public BuckWaResponse getAllWrapByModuleId(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String id =  request.get("moduleId")+"";					 
			List<TestWrapper>  testWrapperList = testCaseDao.getAllWrapByModuleId(id);	 
			response.addResponse("testWrapperList",testWrapperList);				
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
			String id = request.get("testCaseId")+"";					 
			TestCase  testcase = testCaseDao.getById(id);	 
			response.addResponse("testCase",testcase);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	
	@Override
	public BuckWaResponse prepareTestCaseByDetailDesign(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String moduleId =  request.get("moduleId")+""; 
			String detailDesignId =  request.get("detailDesignId")+"";  
			TestCase testcase = testCaseDao.prepareTestCaseByDetailDesign (moduleId,detailDesignId);						
			if(testcase!=null){
				response.addResponse("testcase",testcase);
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
	public BuckWaResponse create(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			TestCase testcase = (TestCase)request.get("testcase");	 
			testCaseDao.create(testcase);  
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
