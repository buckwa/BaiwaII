package com.buckwa.service.impl.pbp;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pbp.AcademicKPIUserMappingDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.AcademicKPIUserMappingWrapper;
import com.buckwa.domain.pbp.AcademicPerson;
import com.buckwa.service.intf.pbp.AcademicKPIUserMappingService;
import com.buckwa.util.BuckWaConstants;

@Service("academicKPIUserMappingService")
 
public class AcademicKPIUserMappingServiceImpl implements AcademicKPIUserMappingService {
	private static Logger logger = LoggerFactory.getLogger(AcademicKPIUserMappingServiceImpl.class);
	
	@Autowired
	private AcademicKPIUserMappingDao academicKPIUserMappingDao;
 

	@Override	
	public BuckWaResponse getById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String kpiUserMappingId =  request.get("kpiUserMappingId")+"";
			AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper= ( AcademicKPIUserMappingWrapper)academicKPIUserMappingDao.getById(kpiUserMappingId);
		 
			 response.addResponse("academicKPIUserMappingWrapper",academicKPIUserMappingWrapper);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse approve(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String headUserName =  request.get("headUserName")+"";
			String kpiUserMappingId =  request.get("kpiUserMappingId")+"";
			 academicKPIUserMappingDao.approve(kpiUserMappingId,headUserName);
		  
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse update(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			String filename =  (String) request.get("filename");
			AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper =  (AcademicKPIUserMappingWrapper)request.get("academicKPIUserMappingWrapper");
			// academicKPIUserMappingDao.update(academicKPIUserMappingWrapper); 
			 academicKPIUserMappingDao.update2(academicKPIUserMappingWrapper,filename); 
			 response.setSuccessCode("S002");	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	@Override	
	public BuckWaResponse changeKPI(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper =  (AcademicKPIUserMappingWrapper)request.get("academicKPIUserMappingWrapper");
			 academicKPIUserMappingDao.changeKPI(academicKPIUserMappingWrapper); 
			 response.setSuccessCode("S002");	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	@Override	
	public BuckWaResponse delete(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String kpiUserMappingId =  (String)request.get("kpiUserMappingId");
			 academicKPIUserMappingDao.delete(kpiUserMappingId); 
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	
	@Override	
	public BuckWaResponse unApprove(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String kpiUserMappingId =  (String)request.get("kpiUserMappingId");
			 academicKPIUserMappingDao.unApprove(kpiUserMappingId); 
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	@Override	
	public List<AcademicPerson> assignHead(BuckWaRequest request) {
		List<AcademicPerson> response = null;
		try{				 
			
			String department =  (String)request.get("department");
			String academicYear =  (String)request.get("academicYear");
			
			response=academicKPIUserMappingDao.assignHeadDao(department, academicYear);
 	
		}catch(Exception ex){
			ex.printStackTrace();
//			response.setStatus(BuckWaConstants.FAIL);
//			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public List<AcademicPerson> assignHeadN(BuckWaRequest request) {
		List<AcademicPerson> response =null;
		try{				 
			
			String department =  (String)request.get("department");
			String academicYear =  (String)request.get("academicYear");
			
			response =academicKPIUserMappingDao.assignHeadNDao(department, academicYear);
			
			
 	
		}catch(Exception ex){
			ex.printStackTrace();
//			response.setStatus(BuckWaConstants.FAIL);
//			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse assignHeadDelete(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String username =  (String)request.get("username");
			 academicKPIUserMappingDao.assignHeadDeleteDao(username); 
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse assignHeadEdit(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String username =  (String)request.get("username");
			 academicKPIUserMappingDao.assignHeadUpdateDao(username); 
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
}
