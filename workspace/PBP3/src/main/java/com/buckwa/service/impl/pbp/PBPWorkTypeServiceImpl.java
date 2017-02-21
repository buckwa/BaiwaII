package com.buckwa.service.impl.pbp;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.pbp.PBPWorkTypeDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeSub;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.domain.pbp.report.RadarPlotReport;
import com.buckwa.service.intf.pbp.PBPWorkTypeService;
import com.buckwa.util.BuckWaConstants;

@Service("pBPWorkTypeService")
 
public class PBPWorkTypeServiceImpl implements PBPWorkTypeService {
	private static Logger logger = LoggerFactory.getLogger(PBPWorkTypeServiceImpl.class);
	
	@Autowired
	private PBPWorkTypeDao pBPWorkTypeDao;
 

//	@Override	
//	public BuckWaResponse getByAcademicYear(BuckWaRequest request) {
//		BuckWaResponse response = new BuckWaResponse();
//		try{				 
//			
//			String academicYear = (String)request.get("academicYear");
//			PBPWorkTypeWrapper pBPWorkTypeWrapper= ( PBPWorkTypeWrapper)pBPWorkTypeDao.getByAcademicYear(academicYear);
//		 
//			 response.addResponse("pBPWorkTypeWrapper",pBPWorkTypeWrapper);
// 	
//		}catch(Exception ex){
//			ex.printStackTrace();
//			response.setStatus(BuckWaConstants.FAIL);
//			response.setErrorCode("E001");			
//		}
//	 
//		return response;
//	}
	 
	
	@Override	
	public BuckWaResponse getByAcademicYearFacultyCode(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear = (String)request.get("academicYear");
			String facultyCode = (String)request.get("facultyCode");
			PBPWorkTypeWrapper pBPWorkTypeWrapper= ( PBPWorkTypeWrapper)pBPWorkTypeDao.getByAcademicYearFacultyCode(academicYear,facultyCode);
		 
			 response.addResponse("pBPWorkTypeWrapper",pBPWorkTypeWrapper);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getRadarPlotPersonMark(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String username = (String)request.get("username");
			String academicYear = (String)request.get("academicYear");
			String round = (String)request.get("round");
			List<RadarPlotReport> radarPlotReportList= ( List<RadarPlotReport>)pBPWorkTypeDao.getRadarPlotPersonMark(username,academicYear,round);
		 
			if(radarPlotReportList==null||radarPlotReportList.size()==0){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E025");	
			}else{
				response.addResponse("radarPlotReportList",radarPlotReportList);
			}
			
			 
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getRadarPlotPersonMarkByYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String username = (String)request.get("username");
			String academicYear = (String)request.get("academicYear");
		 
			List<RadarPlotReport> radarPlotReportList= ( List<RadarPlotReport>)pBPWorkTypeDao.getRadarPlotPersonMarkByYear(username,academicYear);
		 
			 response.addResponse("radarPlotReportList",radarPlotReportList);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	@Override	
	public BuckWaResponse getRadarPlotPersonMarkE(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String username = (String)request.get("username");
			String academicYear = (String)request.get("academicYear");
			String round = (String)request.get("round");
			List<RadarPlotReport> radarPlotReportList= ( List<RadarPlotReport>)pBPWorkTypeDao.getRadarPlotPersonMarkE(username,academicYear,round);
		 
			 response.addResponse("radarPlotReportList",radarPlotReportList);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
				
	
	
	@Override	
	public BuckWaResponse getCalculateByAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear = (String)request.get("academicYear");
			String userName = (String)request.get("userName");
			String round = (String)request.get("round");
			String employeeType = (String)request.get("employeeType");
			String facultyCode = (String)request.get("facultyCode");
			

			if(employeeType.equalsIgnoreCase("ข้าราชการ")){
				employeeType ="1";
				
			}
			else{
				employeeType ="2";
			}
			
			
			PBPWorkTypeWrapper pBPWorkTypeWrapper= ( PBPWorkTypeWrapper)pBPWorkTypeDao.getCalculateByAcademicYear(academicYear,userName,round,employeeType,facultyCode);
		 
			 response.addResponse("pBPWorkTypeWrapper",pBPWorkTypeWrapper);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	

	@Override	
	public BuckWaResponse getExsistCalculateByAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear = (String)request.get("academicYear");
			String userName = (String)request.get("userName");
			String round = (String)request.get("round");
			String employeeType = (String)request.get("employeeType");
			String facultyCode = (String)request.get("facultyCode");
			
			PBPWorkTypeWrapper pBPWorkTypeWrapper= ( PBPWorkTypeWrapper)pBPWorkTypeDao.getExsistCalculateByAcademicYear(academicYear,userName,round,employeeType,facultyCode);
		 
			System.out.println("  Fist Login:"+pBPWorkTypeWrapper.getIsFirstLogin());
			if("Y".equalsIgnoreCase(pBPWorkTypeWrapper.getIsFirstLogin())){
				response.setErrorCode("E001");	
			} 
			 response.addResponse("pBPWorkTypeWrapper",pBPWorkTypeWrapper);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getCurretnEvalulate(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear = (String)request.get("academicYear");
	 
			String employeeType = (String)request.get("employeeType");
		 
			
			String round= ( String)pBPWorkTypeDao.getCurretnEvalulate(academicYear ,employeeType);
	 
			 response.addResponse("round",round);
 	
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
			
			String id =  request.get("workTypeId")+"";
			PBPWorkType  pBPWorkType  = ( PBPWorkType)pBPWorkTypeDao.getById(id);
		 
			 response.addResponse("pBPWorkType",pBPWorkType);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getByCodeAcademicFacultyCode(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String code =  request.get("workTypeCode")+"";
			String academicYear =  request.get("academicYear")+"";
			String facultyCode =  request.get("facultyCode")+"";
			PBPWorkType  pBPWorkType  = ( PBPWorkType)pBPWorkTypeDao.getByCodeAcademicFacultyCode (code,academicYear,facultyCode);
		 
			 response.addResponse("pBPWorkType",pBPWorkType);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getSub(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear =  request.get("workTypeId")+"";
			PBPWorkType pBPWorkType = ( PBPWorkType)pBPWorkTypeDao.getSub(academicYear); 
			 response.addResponse("pBPWorkType",pBPWorkType);
 	
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
			PBPWorkType pBPWorkType = (PBPWorkType)request.get("pBPWorkType");		
			
			boolean isNameExist = pBPWorkTypeDao.isExist(pBPWorkType);
			if(isNameExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				pBPWorkTypeDao.create(pBPWorkType);
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
	public BuckWaResponse edit(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)request.get("pBPWorkTypeWrapper");		
		 
				pBPWorkTypeDao.edit(pBPWorkTypeWrapper);
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
	public BuckWaResponse delete(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			String pBPWorkTypeId =  request.get("pBPWorkTypeId")+"";		
	 
				pBPWorkTypeDao.delete(pBPWorkTypeId);
				response.setSuccessCode("S004");
			 

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
	public BuckWaResponse addNew(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			PBPWorkType pBPWorkType = (PBPWorkType)request.get("pBPWorkType");		
 
				pBPWorkTypeDao.addNew(pBPWorkType);
			 
		 

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
	public BuckWaResponse addNewSub(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			PBPWorkTypeSub pBPWorkTypeSub = (PBPWorkTypeSub)request.get("pBPWorkTypeSub");		
 
			 pBPWorkTypeDao.addNewSub(pBPWorkTypeSub);
			 
		 

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
	public BuckWaResponse editSub(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			PBPWorkType pBPWorkType= (PBPWorkType)request.get("pBPWorkType");		
		 
				pBPWorkTypeDao.editSub(pBPWorkType);
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
	public BuckWaResponse deleteSub(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			String pBPWorkTypeIdSub =  request.get("workTypeSubId")+"";		
	 
				pBPWorkTypeDao.deleteSub(pBPWorkTypeIdSub);
				response.setSuccessCode("S004");
			 

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
	public BuckWaResponse getCheckAcademicKPI(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear = (String)request.get("academicYear");
	 
			String employeeType = (String)request.get("employeeType");
		 
			
			String round= ( String)pBPWorkTypeDao.getCurretnEvalulate(academicYear ,employeeType);
	 
			 response.addResponse("round",round);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
 
}
