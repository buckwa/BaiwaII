package com.buckwa.service.intf.pam;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

/**
 *
@Author : Kroekpong Sakulchai
@Create : Aug 23, 2012 8:08:03 PM
 *
 **/
public interface PersonEvaluateMappingService {
	
//	public BuckWaResponse getEvaluateStatusByWorkLineCode(BuckWaRequest request);
//	public BuckWaResponse getEvaluateStatusByPersonIdYearIdSemesterId(BuckWaRequest request);
	public BuckWaResponse getEvaluateStatusByPersonIdYearIdByTerm(BuckWaRequest request);
	public BuckWaResponse getAllEvaluatePersonByOffset(BuckWaRequest request);
	public BuckWaResponse createOrUpdatePersonEvaluate(BuckWaRequest request);
	public BuckWaResponse endEvaluateSession(BuckWaRequest request);
	public BuckWaResponse getPersonEvaluateTotalScore(BuckWaRequest request);
//	public BuckWaResponse getEvaluateKpiEstimateScore(BuckWaRequest request);
	public BuckWaResponse getKPIPersonEvaluateList(BuckWaRequest request);
	
	public BuckWaResponse getKPIEvaluateByYearAndGroupId(BuckWaRequest request);
	public BuckWaResponse createEvaluateKpiMapping(BuckWaRequest request);
	public BuckWaResponse updateEvaluateKpiEstimateScore(BuckWaRequest request);
	public BuckWaResponse getPersonEvaluateYearAndSemester(BuckWaRequest request);
	public BuckWaResponse getUnderEstimateUserListByUserId(BuckWaRequest request);
	
	
}
