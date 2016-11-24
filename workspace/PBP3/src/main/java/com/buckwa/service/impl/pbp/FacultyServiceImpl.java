package com.buckwa.service.impl.pbp;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.pbp.FacultyDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.AcademicPerson;
import com.buckwa.domain.pbp.ChainOfCommandWrapper;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.FacultyWrapper;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;

@Service("facultyService")
 
public class FacultyServiceImpl implements FacultyService {
	private static Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
	
	@Autowired
	private FacultyDao facultyDao;
 

	@Override	
	public BuckWaResponse getByAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear = (String)request.get("academicYear");
			FacultyWrapper facultyWrapper= ( FacultyWrapper)facultyDao.getByAcademicYear(academicYear);
		 
			 response.addResponse("facultyWrapper",facultyWrapper);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	@Override	
	public BuckWaResponse getAllMarkByAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear = (String)request.get("academicYear");
			FacultyWrapper facultyWrapper= ( FacultyWrapper)facultyDao.getAllMarkByAcademicYear(academicYear);
		 
			 response.addResponse("facultyWrapper",facultyWrapper);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getFacultyListByAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear = (String)request.get("academicYear");
			 List<Faculty> facultyList= (  List<Faculty>)facultyDao.getFacultyListByAcademicYear(academicYear);
		 
			 response.addResponse("facultyList",facultyList);
 	
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
			
			String facultyId = (String)request.get("facultyId");
			Faculty faculty= ( Faculty)facultyDao.getById(facultyId);
		 
			 response.addResponse("faculty",faculty);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	@Override	
	public BuckWaResponse getByFaculty(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String facultyId = (String)request.get("facultyId");
			String academicYear = (String)request.get("academicYear");
			ChainOfCommandWrapper chainOfCommandWrapper=  facultyDao.getByFaculty(facultyId,academicYear);
		 
			 response.addResponse("chainOfCommandWrapper",chainOfCommandWrapper);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getAllFaculty(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			 
			String academicYear = (String)request.get("academicYear");
			List<Faculty> facultyList=  facultyDao.getAllFaculty(academicYear);
		 
			 response.addResponse("facultyList",facultyList);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	
	@Override	
	public BuckWaResponse getDepartmentByUserNameandYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String username = (String)request.get("username");
			String academicYear = (String)request.get("academicYear");
			Department department=  facultyDao.getDepartmentByUserName(username,academicYear);
		 
			 response.addResponse("department",department);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	@Override	
	public BuckWaResponse getDepartmentByHeadUserNameandYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String username = (String)request.get("username");
			System.out.println(" getDepartmentByHeadUserNameandYear headName service username :"+username);
			String academicYear = (String)request.get("academicYear");
			Department department=  facultyDao.getDepartmentByHeadUserName(username,academicYear);
		 
			 response.addResponse("department",department);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getFacultyByUserNameandYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String username = (String)request.get("username");
			String academicYear = (String)request.get("academicYear");
			Faculty faculty=  facultyDao.getFacultyByUserNameandYear(username,academicYear);
		 
			 response.addResponse("faculty",faculty);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getFacultyByDeanUserNameandYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String username = (String)request.get("username");
			String academicYear = (String)request.get("academicYear");
			Faculty faculty=  facultyDao.getFacultyByDeanUserNameandYear(username,academicYear);
		 
			 response.addResponse("faculty",faculty);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	
	@Override	
	public BuckWaResponse getByDepartment(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String departmentId = (String)request.get("departmentId");
			String academicYear = (String)request.get("academicYear");
			
			ChainOfCommandWrapper chainOfCommandWrapper=  facultyDao.getByDepartment(departmentId ,academicYear);
		 
			 response.addResponse("chainOfCommandWrapper",chainOfCommandWrapper);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getFacultyByCodeAndYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String depCode = (String)request.get("facultyCode");
			String academicYear = (String)request.get("academicYear");
			
			Faculty faculty=  facultyDao.getFacultyByCodeAndYear(depCode ,academicYear);
			logger.info(" sql faculty:"+BeanUtils.getBeanString(faculty));
			 response.addResponse("faculty",faculty);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getFacultyByCodeByAcademicYearAndName(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String facultyName = (String)request.get("facultyName");
			String academicYear = (String)request.get("academicYear");
			
			String facultyCode=  facultyDao.getFacultyByCodeByAcademicYearAndName(academicYear ,facultyName);
			logger.info(" sql faculty:"+BeanUtils.getBeanString(facultyCode));
			 response.addResponse("facultyCode",facultyCode);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	
	@Override	
	public BuckWaResponse getDeanByFacultyId(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String facultyId = (String)request.get("facultyId");
			String academicYear = (String)request.get("academicYear");
			AcademicPerson academicPerson=  facultyDao.getDeanByFacultyId(facultyId,academicYear);
		 
			 response.addResponse("academicPerson",academicPerson);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	@Override	
	public BuckWaResponse getHeadByDepartmentId(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String departmentId = (String)request.get("departmentId");
			String academicYear = (String)request.get("academicYear");
		 
			AcademicPerson academicPerson=  facultyDao.getHeadByDepartmentId(departmentId,academicYear);
		 
			 response.addResponse("academicPerson",academicPerson);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse assignDean(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String oldDean = (String)request.get("oldDean");
			String newDean = (String)request.get("newDean");
			String academicYear = (String)request.get("academicYear");
			String facultyDesc = (String)request.get("facultyDesc");
			logger.info("assignDean  oldDean:"+oldDean+"  newDean:"+newDean+" department:"+facultyDesc);
			 facultyDao.assignDean(oldDean,newDean,academicYear,facultyDesc);
		 
		 
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse assignHead(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String oldDean = (String)request.get("oldHead");
			String newDean = (String)request.get("newHead");
			String academicYear = (String)request.get("academicYear");
			String departmentDesc = (String)request.get("departmentDesc");
			logger.info(" oldHead:"+oldDean+"  newHead:"+newDean+" department:"+departmentDesc);
			 facultyDao.assignHead(oldDean,newDean,academicYear,departmentDesc);
		 
		 
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse assignPresident(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String oldDean = (String)request.get("oldPresident");
			String newDean = (String)request.get("newPresident");
			String academicYear = (String)request.get("academicYear");
			 facultyDao.assignPresident(oldDean,newDean,academicYear);
		 
		 
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getPresident(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
		
			String academicYear = (String)request.get("academicYear");
			AcademicPerson academicPerson=  facultyDao.getPresident(academicYear);
		 
			 response.addResponse("academicPerson",academicPerson);
 	
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
			Faculty faculty = (Faculty)request.get("faculty");		
			
			boolean isNameExist = facultyDao.isNameExist(faculty.getName());
			if(isNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				facultyDao.create(faculty);
				response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);
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
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse createDepartment(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			Faculty faculty = (Faculty)request.get("faculty");		
			
			boolean isNameExist = facultyDao.isDepartmentExist(faculty );
			if(isNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				facultyDao.createDepartment(faculty);
				response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);
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
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse updateFaculty(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			Faculty faculty = (Faculty)request.get("faculty");	
			facultyDao.updateFaculty(faculty);
			response.setErrorCode(BuckWaConstants.MSGCODE_EDIT_SUCESS);	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		} 
		return response;
	}	
	
 
	@Override	
	public BuckWaResponse deleteFacultyById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String facultyId = (String)request.get("facultyId");
			facultyDao.deleteFacultyById(facultyId); 
			response.setSuccessCode(BuckWaConstants.MSGCODE_DELETE_SUCESS); 
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
 
	@Override	
	public BuckWaResponse getDepartmentById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String departmentId = (String)request.get("departmentId");
			Department department= ( Department)facultyDao.getDepartmentById(departmentId); 
			response.addResponse("department",department);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
 
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse updateDepartment(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			Department department = (Department)request.get("department");	
			facultyDao.updateDepartment(department);
			response.setErrorCode(BuckWaConstants.MSGCODE_EDIT_SUCESS);	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		} 
		return response;
	}
	
 
	@Override	
	public BuckWaResponse deleteDepartmentById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String departmentId = (String)request.get("departmentId");
			facultyDao.deleteDepartmentById(departmentId); 
			response.setSuccessCode(BuckWaConstants.MSGCODE_DELETE_SUCESS); 
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	@Override	
	public BuckWaResponse getFacultyByDeanUserNameandYearNew(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String username = (String)request.get("username");
			String academicYear = (String)request.get("academicYear");
			List<AcademicPerson> faculty =  facultyDao.getFacultyByDeanUserNameandYearNew(username,academicYear);
		 
			 response.addResponse("faculty",faculty);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
}
