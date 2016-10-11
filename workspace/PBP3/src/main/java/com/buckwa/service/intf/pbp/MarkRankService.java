package com.buckwa.service.intf.pbp;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;


public interface MarkRankService {
	public BuckWaResponse getByAcademicYear(BuckWaRequest request);
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse addNew(BuckWaRequest request);
	public BuckWaResponse delete(BuckWaRequest request);
	public BuckWaResponse edit(BuckWaRequest request);
	
	public BuckWaResponse getByAcademicYearRound(BuckWaRequest request);
	public BuckWaResponse addNewRound(BuckWaRequest request);
	
	public BuckWaResponse editRound(BuckWaRequest request);
	public BuckWaResponse getByRound(BuckWaRequest request);
	
	
}

