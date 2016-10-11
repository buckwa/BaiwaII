package com.buckwa.service.intf.pam;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 9:32:08 AM
 */
public interface WorkTemplateService {
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse update(BuckWaRequest request);
	public BuckWaResponse delete(BuckWaRequest request);
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse getByOffset(BuckWaRequest request);
	public BuckWaResponse checkNameAlready(BuckWaRequest request);
	public BuckWaResponse getByGroupId(BuckWaRequest request);
	public BuckWaResponse getIsFileListByWorkTemplateId(BuckWaRequest request);
    public BuckWaResponse getByClassRoom();
    public BuckWaResponse getByEmployeeType(BuckWaRequest request);
    public BuckWaResponse getByPersonType(BuckWaRequest request);
    
}

