package com.buckwa.service.intf.pbp;

import java.util.List;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.AcademicPerson;


public interface AcademicKPIUserMappingService {
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse approve(BuckWaRequest request);
	public BuckWaResponse update(BuckWaRequest request);
	public BuckWaResponse delete(BuckWaRequest request);
	public BuckWaResponse changeKPI(BuckWaRequest request);
	public BuckWaResponse unApprove(BuckWaRequest request);
	public List<AcademicPerson> assignHead(BuckWaRequest request);
	public List<AcademicPerson> assignHeadN(BuckWaRequest request);
	public BuckWaResponse assignHeadDelete(BuckWaRequest request);
	public BuckWaResponse assignHeadEdit(BuckWaRequest request);
}

