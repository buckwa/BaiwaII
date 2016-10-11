package com.buckwa.service.impl.pbp;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pbp.PresidentDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.report.DepartmentWorkTypeReport;
import com.buckwa.domain.pbp.report.FacultyReportLevel;
import com.buckwa.service.intf.pbp.PresidentService;
import com.buckwa.util.BuckWaConstants;

@Service("presidentService")
 
public class PresidentServiceImpl implements PresidentService {
	private static Logger logger = LoggerFactory.getLogger(PresidentServiceImpl.class);
	
	@Autowired
	private PresidentDao presidentDao;
 


	
	@Override	
	public BuckWaResponse getFacultyReportLevel(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				  
			Faculty faculty = (Faculty)request.get("faculty"); 
			String academicYear = (String)request.get("academicYear"); 
			
			List<FacultyReportLevel> facultyReportLevelList= ( List<FacultyReportLevel>)presidentDao.getFacultyReportLevel(academicYear, faculty);
		 
			 response.addResponse("facultyReportLevelList",facultyReportLevelList);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	@Override	
	public BuckWaResponse getReportWorkTypeFaculty(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				  
			Faculty faculty = (Faculty)request.get("faculty"); 
			String workType = (String)request.get("workType"); 
			
			List<DepartmentWorkTypeReport> facultyWorkTypeReportList= ( List<DepartmentWorkTypeReport>)presidentDao.getReportWorkTypeFaculty(workType, faculty);
		 
			 response.addResponse("facultyWorkTypeReportList",facultyWorkTypeReportList);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}


	@Override
	public BuckWaResponse getReportWorkTypeCompareFaculty(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();

		try {
			Faculty faculty = (Faculty) request.get("faculty");

			List<DepartmentWorkTypeReport> facultyWorkTypeReportList = (List<DepartmentWorkTypeReport>) presidentDao.getReportWorkTypeCompareFaculty(faculty);

			response.addResponse("facultyWorkTypeReportList", facultyWorkTypeReportList);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}

		return response;
	}
	
}
