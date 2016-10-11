package com.buckwa.service.intf.pam;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 9:32:08 AM
 */
public interface TeacherTeachService {
	public BuckWaResponse getTeachTableList(BuckWaRequest request);
	public BuckWaResponse getTeachTableById(BuckWaRequest request);
	public BuckWaResponse getSubjectByOffset(BuckWaRequest request);
	public BuckWaResponse getSubjectById(BuckWaRequest request);
}

