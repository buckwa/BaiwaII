package com.buckwa.service.intf.pam;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
/*
@Author : Teerawoot Charoenporn(Tor)
@Create : Aug 6, 2012 12:52:13 AM
 */
public interface KpiYearMappingService {
	
	public BuckWaResponse getAll();
	public BuckWaResponse getBySemester(BuckWaRequest request);
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse update(BuckWaRequest request);
	public BuckWaResponse updateStatus(BuckWaRequest request);
	public BuckWaResponse getByOffset(BuckWaRequest request);	
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse deleteById(BuckWaRequest request);
	
	
	public BuckWaResponse initialMappingByYear(BuckWaRequest request);
	
	 
	
	
	public BuckWaResponse createNewMapping(BuckWaRequest request);
	
	
	 
}
