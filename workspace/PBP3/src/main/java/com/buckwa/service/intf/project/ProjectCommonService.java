package com.buckwa.service.intf.project;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

public interface ProjectCommonService {
	
	public BuckWaResponse getLatestBusinessRuleNo(BuckWaRequest request);
	public BuckWaResponse getLatestActorNo(BuckWaRequest request);
	public BuckWaResponse getLatestUseCaseNo(BuckWaRequest request);
	public BuckWaResponse getLatestModuleNo(BuckWaRequest request);
	public BuckWaResponse getLatestDetailDesignNo(BuckWaRequest request);	 
	
	public BuckWaResponse getLatestMessageNo(BuckWaRequest request);
	public BuckWaResponse getLatestUtilNo(BuckWaRequest request);
	
	public BuckWaResponse getLatestTestCaseNo(BuckWaRequest request);
	
	public BuckWaResponse getLatestLabNo(BuckWaRequest request);
	public BuckWaResponse getLabCategoryNo(BuckWaRequest request);
	
	
	
}
