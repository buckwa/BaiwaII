package com.buckwa.service.impl.pbp;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pbp.HeadDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.AcademicKPIUserMappingWrapper;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.report.DepartmentWorkTypeReport;
import com.buckwa.domain.pbp.report.MinMaxBean;
import com.buckwa.service.intf.pbp.HeadService;
import com.buckwa.util.BuckWaConstants;

@Service("headService")
 
public class HeadServiceImpl implements HeadService {
	private static Logger logger = LoggerFactory.getLogger(HeadServiceImpl.class);
	
	@Autowired
	private HeadDao headDao;
 

	@Override	
	public BuckWaResponse getByHeadAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				 
			
			String headUserName = (String)request.get("headUserName");
			String academicYear = (String)request.get("academicYear");
			String status = (String)request.get("status");
			
			AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper= ( AcademicKPIUserMappingWrapper)headDao.getByHeadAcademicYear(headUserName,academicYear,status);
		 
			 response.addResponse("academicKPIUserMappingWrapper",academicKPIUserMappingWrapper);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	@Override	
	public BuckWaResponse saveDepartmentReportSummary(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				 
			
			Department department = (Department)request.get("department");
		 
		 headDao.saveDepartmentReportSummary(department);
		 
	 
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	
	@Override	
	public BuckWaResponse getByUserAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				 
			
	
			String academicYear = (String)request.get("academicYear");
			String userName = (String)request.get("userName");
			String employeeType = (String)request.get("employeeType");
			AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper= ( AcademicKPIUserMappingWrapper)headDao.getByUserAcademicYearByUserNew(academicYear, userName, employeeType);
		 
			 response.addResponse("academicKPIUserMappingWrapper",academicKPIUserMappingWrapper);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	@Override	
	public BuckWaResponse getByUserAcademicYearNew(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				 
			
			String headUserName = (String)request.get("headUserName");
			String academicYear = (String)request.get("academicYear");
			String status = (String)request.get("status");
			String code = (String)request.get("code");
			String facultyCode = (String)request.get("facultyCode");
			String department_desc = (String)request.get("department_desc");
			String employeeType = (String)request.get("employeeType");
			
			AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper= ( AcademicKPIUserMappingWrapper)headDao.getByUserAcademicYearNew(headUserName,academicYear,status,code,facultyCode,department_desc,employeeType);
		 
			 response.addResponse("academicKPIUserMappingWrapper",academicKPIUserMappingWrapper);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	@Override	
	public BuckWaResponse getDepartmentMark(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				  
			String academicYear = (String)request.get("academicYear");
			String headUserName = (String)request.get("headUserName");
			
			Department department= ( Department)headDao.getDepartmentMark(headUserName, academicYear);
		 
			 response.addResponse("department",department);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getDepartmentMarkAllYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		 
		try{				  
			String academicYear = (String)request.get("academicYear");
			String headUserName = (String)request.get("headUserName");			
			Department department= ( Department)headDao.getDepartmentMarkAllYear(headUserName, academicYear);		 
			 response.addResponse("department",department); 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	
	@Override	
	public BuckWaResponse getDepartmentMarkByDepCode(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				  
			String academicYear = (String)request.get("academicYear");
			String facCode = (String)request.get("facCode");
			String depCode = (String)request.get("depCode");
			
			Department department= ( Department)headDao.getDepartmentMarkByDepCode(facCode,depCode, academicYear);
		 
			 response.addResponse("department",department);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	@Override	
	public BuckWaResponse markDepartmentRecal(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				  
			String academicYear = (String)request.get("academicYear");
			String headUserName = (String)request.get("headUserName");
			
			Department department= ( Department)headDao.getDepartmentMark(headUserName, academicYear);
			response.setSuccessCode("S100");	
			 response.addResponse("department",department);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
		
	
	
	@Override	
	public BuckWaResponse getDepartmentMarkByUser(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				  
			String academicYear = (String)request.get("academicYear");
			String userName = (String)request.get("userName");
			
			Department department= ( Department)headDao.getDepartmentMarkByUser(userName, academicYear);
		 
			 response.addResponse("department",department);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	@Override	
	public BuckWaResponse getDepartmentMean(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				  
			String academicYear = (String)request.get("academicYear");
			String facultyCode = (String)request.get("facultyCode");
			String departmentCode = (String)request.get("departmentCode");
			
			String meanValue= ( String)headDao.getDepartmentMean(academicYear, facultyCode,departmentCode);
		 
			 response.addResponse("meanValue",meanValue);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	@Override	
	public BuckWaResponse getDepartmentMeanByWorkTypeCode(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				  
			String academicYear = (String)request.get("academicYear");
			String facultyCode = (String)request.get("facultyCode");
			String departmentCode = (String)request.get("departmentCode");
			String worktypecode = (String)request.get("worktypeCode");
			
			MinMaxBean minMaxBean= ( MinMaxBean)headDao.getDepartmentMeanByWorkTypeCode(academicYear, facultyCode,departmentCode,worktypecode);
		 
			 response.addResponse("minMaxBean",minMaxBean);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	
	@Override	
	public BuckWaResponse getReportWorkTypeDepartment(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				  
			Department department = (Department)request.get("department"); 
			String workType = (String)request.get("workType"); 
			
			List<DepartmentWorkTypeReport> departmentWorkTypeReportList= ( List<DepartmentWorkTypeReport>)headDao.getReportWorkTypeDepartment(workType, department);
		 
			 response.addResponse("departmentWorkTypeReportList",departmentWorkTypeReportList);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	@Override	
	public BuckWaResponse getByHeadAcademicYearCount(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				 
			
			String headUserName = (String)request.get("headUserName");
			String academicYear = (String)request.get("academicYear");
			String status = (String)request.get("status");
			String employeeType = (String)request.get("employeeType");
			AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper= ( AcademicKPIUserMappingWrapper)headDao.getByHeadAcademicYearCount(headUserName,academicYear,status,employeeType);
		 
			 response.addResponse("academicKPIUserMappingWrapper",academicKPIUserMappingWrapper);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	
}
