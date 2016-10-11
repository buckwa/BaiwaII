package com.buckwa.service.impl.pbp;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pbp.AcademicYearDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.AcademicYear;
import com.buckwa.domain.pbp.AcademicYearEvaluateRound;
import com.buckwa.domain.pbp.AcademicYearWrapper;
import com.buckwa.service.intf.pbp.AcademicYearService;
import com.buckwa.util.BuckWaConstants;

@Service("academicYearService")
 
public class AcademicYearServiceImpl implements AcademicYearService {
	private static Logger logger = LoggerFactory.getLogger(AcademicYearServiceImpl.class);
	
	@Autowired
	private AcademicYearDao academicYearDao;
 

	@Override	
	public BuckWaResponse getCurrentAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			

				AcademicYearWrapper academicYearWrapper= ( AcademicYearWrapper)academicYearDao.getCurrentAcademicYear();
				 
				 response.addResponse("academicYearWrapper",academicYearWrapper);				
			 

 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getFullAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			

				AcademicYearWrapper academicYearWrapper= ( AcademicYearWrapper)academicYearDao.getFullAcademicYear();
				 
				 response.addResponse("academicYearWrapper",academicYearWrapper);				
			 

 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}	
	
	
	@Override
	public BuckWaResponse getByAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String acedemicYear =(String)request.get("academicYear");
			AcademicYearWrapper academicYearWrapper= ( AcademicYearWrapper)academicYearDao.getByAcademicYear(acedemicYear);
			 
			 response.addResponse("academicYearWrapper",academicYearWrapper);			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	@Override
	public BuckWaResponse getCurrentEvalulateRoundStr(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String acedemicYear =(String)request.get("academicYear");
			String userName =(String)request.get("username");
			String currentRoundStr= ( String)academicYearDao.getCurrentEvalulateRoundStr(userName, acedemicYear);
			 
			 response.addResponse("currentRoundStr",currentRoundStr);			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	@Override
	public BuckWaResponse getByYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String acedemicYear =(String)request.get("academicYear");
			AcademicYear  academicYear = ( AcademicYear )academicYearDao.getByYear(acedemicYear);
			 
			 response.addResponse("academicYear",academicYear);			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse ajustYearIncrease(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			 
			if(academicYearDao.checkAlreadyAdjust()){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E001");	
				
			}else{
			AcademicYearWrapper academicYearWrapper= ( AcademicYearWrapper)academicYearDao.ajustYearIncrease(); 
			 response.addResponse("academicYearWrapper",academicYearWrapper);	
			 response.setSuccessCode("S008");	
			}
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override	
	public BuckWaResponse editDateAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			AcademicYear acedemicYear =(AcademicYear)request.get("academicYear");
			 academicYearDao.editDateAcademicYear(acedemicYear);
			 response.setSuccessCode("S002");	
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse editDateEvaluateRound(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			AcademicYearEvaluateRound academicYearEvaluateRound =(AcademicYearEvaluateRound)request.get("academicYearEvaluateRound");
			 academicYearDao.editDateEvaluateRound(academicYearEvaluateRound);
			 response.setSuccessCode("S002");	
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}

	@Override	
	public BuckWaResponse getEvaluateRoundByYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String acedemicYear = request.get("academicYear")+"";
			String evaluateType = request.get("evaluateType")+"";
			AcademicYearEvaluateRound academicYearEvaluateRound= academicYearDao.getEvaluateRoundByYear(acedemicYear,evaluateType);
			response.addResponse("academicYearEvaluateRound",academicYearEvaluateRound);	
			 response.setSuccessCode("S002");	
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}

	
	
}
