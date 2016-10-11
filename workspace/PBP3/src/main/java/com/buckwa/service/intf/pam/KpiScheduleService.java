package com.buckwa.service.intf.pam;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

/*
@Author : Taechapon Himarat (Su)
@Create : Sep 30, 2012 9:52:23 AM
 */
public interface KpiScheduleService {
	
	public BuckWaResponse getByOffset(BuckWaRequest request);	
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse update(BuckWaRequest request);
	public BuckWaResponse delete(BuckWaRequest request);
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse getByYearName(BuckWaRequest request);
	
}
