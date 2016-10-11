package com.buckwa.service.intf.project;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

public interface ModuleService {
	
	 
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse update(BuckWaRequest request);
	
	public BuckWaResponse getAll();	
	public BuckWaResponse getAllByProjectId(BuckWaRequest request);	
	public BuckWaResponse getByOffset(BuckWaRequest request);	
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse getByIdandDetailDesign(BuckWaRequest request);
	public BuckWaResponse deleteById(BuckWaRequest request);
	
	
	
	public BuckWaResponse getModuleandTestCaseById(BuckWaRequest request);
	
	public BuckWaResponse getAllModuleByProjectIdCountUseCase(BuckWaRequest request);
	
	public BuckWaResponse getAllModuleByProjectIdCountDetailDesign(BuckWaRequest request);
	
	
	public BuckWaResponse getAllModuleByProjectIdCountTestcase(BuckWaRequest request);
	
}
