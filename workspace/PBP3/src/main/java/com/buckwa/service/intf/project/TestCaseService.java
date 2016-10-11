package com.buckwa.service.intf.project;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

public interface TestCaseService {	
	public BuckWaResponse getAllByModuleId(BuckWaRequest request);	
	public BuckWaResponse getAllByProjectId(BuckWaRequest request);		
	public BuckWaResponse getById(BuckWaRequest request);		
	public BuckWaResponse prepareTestCaseByDetailDesign(BuckWaRequest request);		
	public BuckWaResponse create(BuckWaRequest request);
	
	public BuckWaResponse getAllWrapByModuleId(BuckWaRequest request);
 
}
