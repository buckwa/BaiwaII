package com.buckwa.service.intf.pam;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 9:32:08 AM
 */
public interface LeaveCommentService {
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse getComments(BuckWaRequest request);
}

