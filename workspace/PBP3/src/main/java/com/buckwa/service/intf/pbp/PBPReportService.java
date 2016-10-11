package com.buckwa.service.intf.pbp;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;


public interface PBPReportService {
	 
	public BuckWaResponse getInstituteReportByAcademicYear(BuckWaRequest request);
	
	public BuckWaResponse getFacultyReportByAcademicYear(BuckWaRequest request);
}

