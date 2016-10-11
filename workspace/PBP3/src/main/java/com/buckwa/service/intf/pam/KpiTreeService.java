package com.buckwa.service.intf.pam;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

public interface KpiTreeService {
	
	public BuckWaResponse  create(BuckWaRequest request);
	public BuckWaResponse  update(BuckWaRequest request);
	public BuckWaResponse  remove(BuckWaRequest request);
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse  getByParentId(BuckWaRequest request); 	
	public BuckWaResponse  getTreeById(BuckWaRequest request);
	public BuckWaResponse  getTemplateByYearAndGroupId(BuckWaRequest request);
	
	public BuckWaResponse createTree(BuckWaRequest request);
	public BuckWaResponse  updateTree(BuckWaRequest request);
	public BuckWaResponse  getRecursive(BuckWaRequest request);	
	public BuckWaResponse getAllTree(BuckWaRequest request);
	
	public BuckWaResponse getNodeTreeByYearandEmpType(BuckWaRequest request);
	public BuckWaResponse getNodeTreeByYear(BuckWaRequest request);
	
	public BuckWaResponse editLevel1(BuckWaRequest request);
	public BuckWaResponse updateWeight(BuckWaRequest request);
	
	
	public BuckWaResponse updateTarget(BuckWaRequest request);
	
	public BuckWaResponse updateLevel(BuckWaRequest request);
	
	
	
 

}
