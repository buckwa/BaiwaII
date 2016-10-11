package com.buckwa.service.intf.pbp;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;


public interface PresidentService {
 
	public BuckWaResponse getFacultyReportLevel(BuckWaRequest request);
	
	public BuckWaResponse getReportWorkTypeFaculty(BuckWaRequest request);
	
	public BuckWaResponse getReportWorkTypeCompareFaculty(BuckWaRequest request);
}

