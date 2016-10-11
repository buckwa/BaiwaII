package com.buckwa.service.intf.admin;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

public interface MenuService {
	
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse list(BuckWaRequest request);
	public BuckWaResponse getAll();	 
	public BuckWaResponse update(BuckWaRequest request);
	public BuckWaResponse getByOffset(BuckWaRequest request);	
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse deleteById(BuckWaRequest request);
	public BuckWaResponse moveDown(BuckWaRequest request);
	public BuckWaResponse moveUp(BuckWaRequest request);
}
