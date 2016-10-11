package com.buckwa.service.intf.project;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

public interface DetailDesignService {
	
	 
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse update(BuckWaRequest request);
	
 
	public BuckWaResponse getAllByProjectId(BuckWaRequest request);	
	public BuckWaResponse getAllByModuleId(BuckWaRequest request);	
	public BuckWaResponse getByOffset(BuckWaRequest request);	
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse deleteById(BuckWaRequest request);
	
	
	public BuckWaResponse getProjectByProjectId(BuckWaRequest request);
	
	
	
	public BuckWaResponse uploadScreen(BuckWaRequest request);	
	
	public BuckWaResponse prepareDetailDesignByUseCase(BuckWaRequest request);
	
	public BuckWaResponse updateFilePath(BuckWaRequest request);
	
}
