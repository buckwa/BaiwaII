package com.buckwa.service.intf.pbp;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;


public interface AcademicKPIUserMappingService {
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse approve(BuckWaRequest request);
	public BuckWaResponse update(BuckWaRequest request);
	public BuckWaResponse delete(BuckWaRequest request);
	public BuckWaResponse changeKPI(BuckWaRequest request);
	public BuckWaResponse unApprove(BuckWaRequest request);
}

