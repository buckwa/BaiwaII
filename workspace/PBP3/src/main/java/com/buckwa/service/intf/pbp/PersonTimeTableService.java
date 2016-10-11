package com.buckwa.service.intf.pbp;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;


public interface PersonTimeTableService {
	public BuckWaResponse getTimeTable(BuckWaRequest request);
	public BuckWaResponse getTimeTableShsre(BuckWaRequest request);
	
	public BuckWaResponse addShareSubject(BuckWaRequest request);
	
	public BuckWaResponse getTimeTableById(BuckWaRequest request);
	
	public BuckWaResponse updateTimeTable(BuckWaRequest request);
	public BuckWaResponse createTimeTable(BuckWaRequest request);
	
	 
}

