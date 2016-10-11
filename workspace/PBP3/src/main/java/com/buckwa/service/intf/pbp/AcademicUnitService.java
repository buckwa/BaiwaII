package com.buckwa.service.intf.pbp;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;


public interface AcademicUnitService {
	public BuckWaResponse getByAcademicYear(BuckWaRequest request);
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse update(BuckWaRequest request);
	
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse deleteById(BuckWaRequest request);
	public BuckWaResponse addNew(BuckWaRequest request);
	public BuckWaResponse edit(BuckWaRequest request);
	
}

