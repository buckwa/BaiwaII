package com.buckwa.service.intf.pam;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

public interface KpiTemplateService {
	
	public BuckWaResponse  create(BuckWaRequest request);
	public BuckWaResponse  update(BuckWaRequest request);
	public BuckWaResponse  remove(BuckWaRequest request);
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse  getByParentId(BuckWaRequest request); 
	
	public BuckWaResponse  getTreeById(BuckWaRequest request);
	public BuckWaResponse createTree(BuckWaRequest request);
	public BuckWaResponse  updateTree(BuckWaRequest request);
	public BuckWaResponse  getRecursive(BuckWaRequest request);
	
	public BuckWaResponse getAllTree(BuckWaRequest request);
 
	
	public BuckWaResponse getTreeByRootId(BuckWaRequest request);
	
	public BuckWaResponse getTreeByCategoryId(BuckWaRequest request);

}
