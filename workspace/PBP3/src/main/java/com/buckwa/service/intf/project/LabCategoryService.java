package com.buckwa.service.intf.project;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

public interface LabCategoryService {
	
	 
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse update(BuckWaRequest request);
	
	public BuckWaResponse getAll();	
	public BuckWaResponse getAllByProjectId(BuckWaRequest request);	
	public BuckWaResponse getByOffset(BuckWaRequest request);	
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse getByIdandDetailDesign(BuckWaRequest request);
	public BuckWaResponse deleteById(BuckWaRequest request);
	
	
	
	public BuckWaResponse getLabCategoryandTestCaseById(BuckWaRequest request);
	
	public BuckWaResponse getAllLabCategoryByProjectIdCountLab(BuckWaRequest request);
	
	public BuckWaResponse getAllLabCategoryByProjectIdCountDetailDesign(BuckWaRequest request);
	
	
	public BuckWaResponse getAllLabCategoryByProjectIdCountTestcase(BuckWaRequest request);
	
}
