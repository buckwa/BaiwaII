package com.buckwa.service.impl.pam;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.admin.LovHeaderDao;
import com.buckwa.dao.intf.pam.PersonProfileDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.LovDetail;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pam.PersonMapping;
import com.buckwa.service.intf.pam.PersonProfileService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.PAMConstants;

/*
@Author : Taechapon Himarat (Su)
@Create : Aug 5, 2012 2:04:20 PM
 */
@Service("personProfileService")
public class PersonProfileServiceImpl implements PersonProfileService {
	
	private static Logger logger = LoggerFactory.getLogger(PersonProfileServiceImpl.class);
	
	@Autowired
	private PersonProfileDao personProfileDao;
	
 
	
	@Autowired
	private LovHeaderDao lovHeaderDao;
	
	
	@Override
	public BuckWaResponse getAll() {
		logger.info("- start");
		
		BuckWaResponse response = new BuckWaResponse();
		try {		
			List<Person> personList = personProfileDao.getAll();			
			response.addResponse("personList", personList);			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);
		
		return response;
	}
	
	
	@Override
	public BuckWaResponse getByOffset(BuckWaRequest request) {
		logger.info("- start");
		
		BuckWaResponse response = new BuckWaResponse();
		try {
			PagingBean pagingBean = (PagingBean) request.get("pagingBean");
			PagingBean returnBean = personProfileDao.getByOffset(pagingBean);
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
	public BuckWaResponse getEmployeeTypeList(){
		logger.info("- start");
		
		BuckWaResponse response = new BuckWaResponse();
		try {
			List<LovDetail> employeeTypeList  = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_EMPLOYEE_TYPE);
			response.addResponse("employeeTypeList", employeeTypeList);
			response.setStatus(BuckWaConstants.SUCCESS);
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getByUserId(BuckWaRequest request){
		logger.info("- start");
		
		BuckWaResponse response = new BuckWaResponse();
		try {
			String userid =  String.valueOf(request.get("userid"));
			String academicYear = (String) request.get("academicYear");
			Person person = personProfileDao.getByUserId(Long.parseLong(userid),academicYear);
			if (person != null) {
				
				List<LovDetail> lovSexList           = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_SEX);
				List<LovDetail> lovEmployeeTypeList  = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_EMPLOYEE_TYPE);
				List<LovDetail> lovPositionList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_POSITION);
				List<LovDetail> lovWorkLineList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_WORK_LINE);
				List<LovDetail> lovFacultyList       = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_FACULTY);
				List<LovDetail> lovInsigniaList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_INSIGNIA);
				List<LovDetail> lovMarriedStatusList = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_MARRIED_STATUS);
				List<LovDetail> lovEducationList     = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_EDUCATION);
				List<LovDetail> lovWorkingStatusList = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_WORKING_STATUS);
				
				person.setLovSexList(lovSexList);
				person.setLovEmployeeTypeList(lovEmployeeTypeList);
				person.setLovPositionList(lovPositionList);
				person.setLovWorkLineList(lovWorkLineList);
				person.setLovFacultyList(lovFacultyList);
				person.setLovInsigniaList(lovInsigniaList);
				person.setLovMarriedStatusList(lovMarriedStatusList);
				person.setLovEducationList(lovEducationList);
				person.setLovWorkingStatusList(lovWorkingStatusList);
				
				response.addResponse("person", person);
				response.setStatus(BuckWaConstants.SUCCESS);
			}
			else {
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode(BuckWaConstants.ERROR_E001);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getByPersonId(BuckWaRequest request){
		logger.info("- start");
		
		BuckWaResponse response = new BuckWaResponse();
		try {
			String personId =  String.valueOf(request.get("personId"));
			String academicYear = (String) request.get("academicYear");
			Person person = personProfileDao.getByPersonId(Long.parseLong(personId),academicYear);
			if (person != null) {
				
				List<LovDetail> lovSexList           = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_SEX);
				List<LovDetail> lovEmployeeTypeList  = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_EMPLOYEE_TYPE);
				List<LovDetail> lovPositionList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_POSITION);
				List<LovDetail> lovWorkLineList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_WORK_LINE);
				List<LovDetail> lovFacultyList       = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_FACULTY);
				List<LovDetail> lovInsigniaList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_INSIGNIA);
				List<LovDetail> lovMarriedStatusList = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_MARRIED_STATUS);
				List<LovDetail> lovEducationList     = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_EDUCATION);
				List<LovDetail> lovWorkingStatusList = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_WORKING_STATUS);
				
				person.setLovSexList(lovSexList);
				person.setLovEmployeeTypeList(lovEmployeeTypeList);
				person.setLovPositionList(lovPositionList);
				person.setLovWorkLineList(lovWorkLineList);
				person.setLovFacultyList(lovFacultyList);
				person.setLovInsigniaList(lovInsigniaList);
				person.setLovMarriedStatusList(lovMarriedStatusList);
				person.setLovEducationList(lovEducationList);
				person.setLovWorkingStatusList(lovWorkingStatusList);
				
				response.addResponse("person", person);
				response.setStatus(BuckWaConstants.SUCCESS);
			}
			else {
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode(BuckWaConstants.ERROR_E001);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}

	@Override
	public BuckWaResponse getByOffsetWithWorkline(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			logger.info("PaperServiceImpl.getByOffset");
			PagingBean pagingBean = (PagingBean) request.get("pagingBean");
			PagingBean returnBean = personProfileDao.getByOffsetWithWorkline(pagingBean);
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
	public BuckWaResponse getByUsername(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		PagingBean pagingBean = new PagingBean();
		pagingBean.setLimitItemFrom(0);
		pagingBean.setLimitItemTo(3);
		
		try {
			//logger.info(" PersonProfileServiceImpl.getByUsername");
			String username =  String.valueOf(request.get("username"));
			String academicYear =  String.valueOf(request.get("academicYear"));
			//logger.info(" PersonProfileServiceImpl.getByUsername  username:"+username+"  academicYear:"+academicYear);
			Person person = personProfileDao.getByUsername(username,academicYear);
			if (person != null) {
 
				response.addResponse("person", person);
				response.setStatus(BuckWaConstants.SUCCESS);
			} else {
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E022");
			}
			
	 
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		//	logger.info(" PersonProfileServiceImpl.getByUsername Error !!:"+e.getMessage());
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getByWorkLineCode(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		PagingBean pagingBean = new PagingBean();
		pagingBean.setLimitItemFrom(0);
		pagingBean.setLimitItemTo(3);
		
		try {
			logger.info(" PersonProfileServiceImpl.getByWorkLineCode");
			String worklineCode =  String.valueOf(request.get("worklineCode"));
			Person person = personProfileDao.getByWorkLineCode(worklineCode);
			if (person != null) {
				response.addResponse("person", person);
				response.setStatus(BuckWaConstants.SUCCESS);
			}
			else {
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode(BuckWaConstants.ERROR_E001);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}
	
	
	@Override
	public BuckWaResponse updateByUsername(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			logger.info(" PersonProfileServiceImpl.updateByUsername");
			Person domain = (Person) request.get("domain");
			personProfileDao.updatePBPPerson(domain);
			response.setStatus(BuckWaConstants.SUCCESS);
			response.setSuccessCode(BuckWaConstants.MSGCODE_EDIT_SUCESS);
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}
	
//	@Override
//	public BuckWaResponse updateWorklineByUsername(BuckWaRequest request) {
//		BuckWaResponse response = new BuckWaResponse();
//		try {
//			logger.info(" PersonProfileServiceImpl.updateWorklineByUsername");
//			Person domain = (Person) request.get("person");
//			personProfileDao.updateWorkline(domain);
//			response.setStatus(BuckWaConstants.SUCCESS);
//			response.setSuccessCode(BuckWaConstants.MSGCODE_EDIT_SUCESS);
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//			response.setStatus(BuckWaConstants.FAIL);
//			response.setErrorCode(BuckWaConstants.ERROR_E001);
//		}
//		return response;
//	}
	
	@Override
	public BuckWaResponse getPersonByNotInTimeTable(BuckWaRequest request){
		BuckWaResponse response = new BuckWaResponse();
		logger.info("PersonProfileServiceImpl.getPersonByNotInTimeTable");
		try {
			Long yearId = (Long) request.get("yearId");
			Long semesterId = (Long) request.get("semesterId");
			List<Person> personList   = personProfileDao.getPersonByNotInTimeTable(yearId,semesterId);
			response.addResponse("personList", personList);
			response.setStatus(BuckWaConstants.SUCCESS);
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getPersonEstimateAllUser(){
		BuckWaResponse response = new BuckWaResponse();
		logger.info("PersonProfileServiceImpl.getPersonEstimateUser");
		try {
			List<Person> personList   = personProfileDao.getPersonEstimateAllByuser();
			response.addResponse("personList", personList);
			response.setStatus(BuckWaConstants.SUCCESS);
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getPersonEstimateUser(Long estimateGroupId){
		BuckWaResponse response = new BuckWaResponse();
		logger.info("PersonProfileServiceImpl.getPersonEstimateByUser");
		try {
			List<Person> personList   = personProfileDao.getPersonEstimateUser(estimateGroupId);
			response.addResponse("personList", personList);
			response.setStatus(BuckWaConstants.SUCCESS);
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}
	
	
	
	@Override
	public BuckWaResponse getPersonByUserIdList(BuckWaRequest request){
		logger.info("- start");
		
		BuckWaResponse response = new BuckWaResponse();
		try {
			
			String academicYear = (String) request.get("academicYear");
			List<PersonMapping> personMappingList = (List) request.get("personMappingList");
			List<Person> personList = new ArrayList<Person>();
			if(personMappingList!=null && personMappingList.size()>0){
				for(PersonMapping obj  : personMappingList){
					
					Person person = personProfileDao.getByUserId(obj.getUserId1(),academicYear);
					personList.add(person);
				}
			}
			response.addResponse("personList", personList);
			response.setStatus(BuckWaConstants.SUCCESS);
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}


	@Override
	public BuckWaResponse getFacultyIdByUsername(BuckWaRequest request) {
		logger.info("- start");
		
		BuckWaResponse response = new BuckWaResponse();
		try {
			String username = (String) request.get("username");
			String facultyId = personProfileDao.getFacultyIdByUsername(username);
			response.addResponse("facultyId", facultyId);
			response.setStatus(BuckWaConstants.SUCCESS);
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode(BuckWaConstants.ERROR_E001);
		}
		return response;
	}
}
