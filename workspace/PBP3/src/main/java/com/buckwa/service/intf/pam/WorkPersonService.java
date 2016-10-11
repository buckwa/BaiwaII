package com.buckwa.service.intf.pam;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 9:32:08 AM
 */
public interface WorkPersonService {
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse update(BuckWaRequest request);
	public BuckWaResponse delete(BuckWaRequest request);
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse getByOffset(BuckWaRequest request);
	public BuckWaResponse getByWorkTemplate(BuckWaRequest request);
	public BuckWaResponse checkNameAlready(BuckWaRequest request);
	public BuckWaResponse getWorkPersonAttrById(BuckWaRequest request);
	public BuckWaResponse createClassRoom(BuckWaRequest request);
	public BuckWaResponse mappingUserClassRoom(BuckWaRequest request);
	
}

