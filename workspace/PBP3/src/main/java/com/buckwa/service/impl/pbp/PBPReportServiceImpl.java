package com.buckwa.service.impl.pbp;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pbp.PBPReportDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.report.RadarPlotReport;
import com.buckwa.service.intf.pbp.PBPReportService;
import com.buckwa.util.BuckWaConstants;

@Service("pbpReportService")
 
public class PBPReportServiceImpl implements PBPReportService {
	private static Logger logger = LoggerFactory.getLogger(PBPReportServiceImpl.class);
	
	@Autowired
	private PBPReportDao pbpReportDao;
 

	@Override	
	public BuckWaResponse getInstituteReportByAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear = (String)request.get("academicYear");
			 List<RadarPlotReport> radarPlotReportList= (  List<RadarPlotReport>)pbpReportDao.getInstituteReportByAcademicYear(academicYear);		 
			 response.addResponse("radarPlotReportList",radarPlotReportList);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	@Override	
	public BuckWaResponse getFacultyReportByAcademicYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{				 
			
			String academicYear = (String)request.get("academicYear");
			String facultyCode = (String)request.get("facultyCode");
			
			 List<RadarPlotReport> radarPlotReportList= (  List<RadarPlotReport>)pbpReportDao.getFacultyReportByAcademicYear(academicYear,facultyCode);		 
			 response.addResponse("radarPlotReportList",radarPlotReportList);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
}
