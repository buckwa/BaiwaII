package com.buckwa.service.impl.pam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pam.KpiTemplateDao;
import com.buckwa.dao.intf.pam.KpiTreeDao;
import com.buckwa.dao.intf.pam.PersonEvaluateMappingDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.EstimateUser;
import com.buckwa.domain.pam.KpiPersonEvaluateMapping;
import com.buckwa.domain.pam.PersonEvaluateMapping;
import com.buckwa.domain.pam.Semester;
import com.buckwa.domain.pam.nodetree.EvaluateKpiTree;
import com.buckwa.service.intf.pam.PersonEvaluateMappingService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;

/**
 *
@Author : Kroekpong Sakulchai
@Create : Aug 23, 2012 8:09:00 PM
 *
 **/

@Service("PersonMappingEvaluateService")
public class PersonEvaluateMappingServiceImpl implements PersonEvaluateMappingService{

	private static Logger logger = LoggerFactory.getLogger(PersonEvaluateMappingServiceImpl.class);
	
	@Autowired
	private PersonEvaluateMappingDao personMappingEvaluateDao;
	
	@Autowired
	private KpiTemplateDao kpiTemplateDao;
	
	@Autowired
	private KpiTreeDao kpiTreeDao;

//	@Override
//	public BuckWaResponse getEvaluateStatusByWorkLineCode(BuckWaRequest request) {
//		
//			logger.info("PersonMappingEvaluateServiceImpl >>");
//			
//			BuckWaResponse response = new BuckWaResponse();
//			
//			try {
//				
//				String worklineCode = (String) request.get("worklineCode");
//				logger.info("worklineCode : "+worklineCode);
//				
//				String  evaluateStatus = personMappingEvaluateDao.getEvaluateStatusByWorkLineCode(worklineCode);
//				response.addResponse("evaluateStatus", evaluateStatus);
//				
//				logger.info("getEvaluateStatusByWorkLineCode >> Status : "+evaluateStatus);
//			}
//			catch (Exception ex) {
//				ex.printStackTrace();
//				response.setStatus(BuckWaConstants.FAIL);
//				response.setErrorCode(BuckWaConstants.ERROR_E001);
//			}
//			return response;
//	}
	
	@Override
	public BuckWaResponse getEvaluateStatusByPersonIdYearIdByTerm(BuckWaRequest request) {
		
		logger.info("getEvaluateStatusByPersonIdYearIdByTerm >>");
		
		BuckWaResponse response = new BuckWaResponse();
		
		try {
			
			String personId = (String) request.get("personId");
			String yearId = (String) request.get("yearId");
			String evaluateTerm = (String) request.get("evaluateTerm");
			logger.info("personId : "+personId);
			logger.info("yearId : "+yearId);
			logger.info("evaluateTerm : "+evaluateTerm);
			
			String  evaluateStatus = personMappingEvaluateDao.getEvaluateStatusByPersonIdYearIdByTerm(personId, yearId, evaluateTerm);
			response.addResponse("evaluateStatus", evaluateStatus);
			
			logger.info("getEvaluateStatusByPersonIdYearIdByTerm >> Status : "+evaluateStatus);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}
//	@Override
//	public BuckWaResponse getEvaluateStatusByPersonIdYearIdSemesterId(BuckWaRequest request) {
//		
//		logger.info("PersonMappingEvaluateServiceImpl >>");
//		
//		BuckWaResponse response = new BuckWaResponse();
//		
//		try {
//			
//			String personId = (String) request.get("personId");
//			String yearId = (String) request.get("yearId");
//			String semesterId = (String) request.get("semesterId");
//			logger.info("personId : "+personId);
//			logger.info("yearId : "+yearId);
//			logger.info("semesterId : "+semesterId);
//			
//			String  evaluateStatus = personMappingEvaluateDao.getEvaluateStatusByPersonIdYearIdSemesterId(personId, yearId, semesterId);
//			response.addResponse("evaluateStatus", evaluateStatus);
//			
//			logger.info("getEvaluateStatusByPersonIdYearIdSemesterId >> Status : "+evaluateStatus);
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//			response.setStatus(BuckWaConstants.FAIL);
//			response.setErrorCode(BuckWaConstants.ERROR_E001);
//		}
//		return response;
//	}


	@Override
	public BuckWaResponse createOrUpdatePersonEvaluate(BuckWaRequest request) {

		logger.info("createOrUpdateByPersonId >>");
		
		BuckWaResponse response = new BuckWaResponse();
		PersonEvaluateMapping evaluateMapping = new PersonEvaluateMapping();
		
		try {
			
			evaluateMapping= (PersonEvaluateMapping) request.get("evaluateMapping");
			logger.info("PersonEvaluateMapping : "+evaluateMapping);
			
			int personEvaluateId = personMappingEvaluateDao.createOrUpdatePersonEvaluate(evaluateMapping);
			response.addResponse("personEvaluateId", personEvaluateId);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}
	
	@Override
	public BuckWaResponse endEvaluateSession(BuckWaRequest request) {
		
		logger.info("endEvaluateSession >>");
		
		BuckWaResponse response = new BuckWaResponse();
		
		try {
			
			String personId = (String) request.get("personId");
			logger.info("personId : "+personId);
			
			personMappingEvaluateDao.endEvaluateSession(personId.trim());
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}


	@Override
	public BuckWaResponse getAllEvaluatePersonByOffset(BuckWaRequest request) {

		logger.info("## getAllEvaluatePersonByWorkLineCode");
		
		BuckWaResponse response = new BuckWaResponse();
		
		try {
			
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");			 
			PagingBean returnBean = personMappingEvaluateDao.getAllEvaluatePersonByOffset(pagingBean);
			
			response.addResponse("pagingBean", returnBean);
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}


	@Override
	public BuckWaResponse getPersonEvaluateTotalScore(BuckWaRequest request) {

		BuckWaResponse response = new BuckWaResponse();
		
		try {
			String personId = (String)request.get("personId");	
			BigDecimal totalScore = personMappingEvaluateDao.getPersonEvaluateTotalScore(personId);
			
			response.addResponse("totalScore", totalScore);
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		
		return response;
	}


	@Override
	public BuckWaResponse getKPIPersonEvaluateList(BuckWaRequest request) {

		BuckWaResponse response = new BuckWaResponse();
		List<KpiPersonEvaluateMapping> kpiDefaultScoreList = new ArrayList<KpiPersonEvaluateMapping>();
		try {
			Integer yearId = null;
			Integer semesterId = null;
			
			String personId = (String)request.get("personId");	
			if(BeanUtils.isNotEmpty(request.get("evaluateYear"))){
				yearId = (Integer)request.get("evaluateYear");	
			}	
			if(BeanUtils.isNotEmpty(request.get("evaluateTerm"))){
				semesterId = (Integer)request.get("evaluateTerm");	
			}
			
			kpiDefaultScoreList = personMappingEvaluateDao.getKPIPersonEvaluate(personId, yearId, semesterId);
			
			response.addResponse("kpiDefaultScoreList", kpiDefaultScoreList);
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}
	
//	@Override
//	public BuckWaResponse getEvaluateKpiEstimateScore(BuckWaRequest request) {
//		BuckWaResponse response = new BuckWaResponse();
//		
//		try {
//			String personId = (String)request.get("personId");	
//			Integer evaluateTerm = (Integer) request.get("evaluateTerm");	
//			
//			List<KpiPersonEvaluateMapping> kpiEstimateScoreList = new ArrayList<KpiPersonEvaluateMapping>();
//			
//			kpiEstimateScoreList = personMappingEvaluateDao.getEvaluateKpiEstimateScore(personId,evaluateTerm);
//			
//			response.addResponse("kpiEstimateScoreList", kpiEstimateScoreList);
//			
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//			response.setStatus(BuckWaConstants.FAIL);
//			response.setErrorCode(BuckWaConstants.ERROR_E001);
//		}
//		
//		return response;
//	}
//	
	@Override
	public BuckWaResponse getPersonEvaluateYearAndSemester(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		try {
			String personId = (String)request.get("personId");	

			Semester semester = new Semester();
			
			semester = personMappingEvaluateDao.getPersonEvaluateYearAndSemester(personId);
			
			response.addResponse("semester", semester);
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		
		return response;
	}
	
	@Override
	public BuckWaResponse updateEvaluateKpiEstimateScore(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		try {
			String evaluateKpiId = (String)request.get("evaluateKpiId");	
			BigDecimal estimateScore = (BigDecimal) request.get("estimateScore");	
			
			personMappingEvaluateDao.updateEvaluateKpiEstimateScore(evaluateKpiId, estimateScore);
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		
		return response;
	}

	
	@Override
	public BuckWaResponse getKPIEvaluateByYearAndGroupId(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String yearId = (String)request.get("yearId");	
			String groupId = (String)request.get("groupId");	
			EvaluateKpiTree evaluateKpiTree = personMappingEvaluateDao.getKPIEvaluateByYearAndGroupId(yearId,groupId);		 
			response.addResponse("evaluateKpiTree",evaluateKpiTree);	 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}


	@Override
	public BuckWaResponse createEvaluateKpiMapping(BuckWaRequest request) {
		
		BuckWaResponse response = new BuckWaResponse();
		
		try{	
			
			EvaluateKpiTree evaluateKpiTree = (EvaluateKpiTree) request.get("evaluateKpiTree");	
			String yearId = (String)request.get("yearId");	
			String categoryId = (String)request.get("groupId");	
			String personId = (String)request.get("personId");	
			Integer personEvaluateId = (Integer)request.get("personEvaluateId");	
			
			boolean isExist = false;
//			boolean isExist = kpiYearMappingDao.isExistForCreate(kpiYearMapping);
			if(isExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{	 
				
				
				evaluateKpiTree.getRootElement().getData().setYearId(new Long(yearId));
				evaluateKpiTree.getRootElement().getData().setCategoryId(new Long(categoryId));
				evaluateKpiTree.getRootElement().getData().setPersonId(personId);
				evaluateKpiTree.getRootElement().getData().setPersonMapEvaluateId(personEvaluateId.toString());
				
				Long returnId = personMappingEvaluateDao.createEvaluateKpiTree(evaluateKpiTree);
				 
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
	public BuckWaResponse getUnderEstimateUserListByUserId(BuckWaRequest request) {
		logger.info("getUnderEstimateUserListByUserId");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			Long userId =  (Long)request.get("userId");
			
//			EstimateGroup estimateGroup = new EstimateGroup();					
				List<EstimateUser> estimateUserList = personMappingEvaluateDao.getUnderEstimateUserListByUserId(userId);
//				estimateGroup.setEstimateUserList(estimateUserList);
				
				response.addResponse("estimateUserList",estimateUserList);
				response.setSuccessCode("S002");
				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
}
