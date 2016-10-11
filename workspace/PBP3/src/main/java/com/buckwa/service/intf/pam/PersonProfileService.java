package com.buckwa.service.intf.pam;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

/*
@Author : Taechapon Himarat (Su)
@Create : Aug 5, 2012 1:57:42 PM
 */
public interface PersonProfileService {
	
	public BuckWaResponse getAll();
	public BuckWaResponse getByOffset(BuckWaRequest request);
	public BuckWaResponse getByOffsetWithWorkline(BuckWaRequest request);
	public BuckWaResponse getByUsername(BuckWaRequest request);
	public BuckWaResponse getByUserId(BuckWaRequest request);
	public BuckWaResponse updateByUsername(BuckWaRequest request);
//	public BuckWaResponse updateWorklineByUsername(BuckWaRequest request);
	public BuckWaResponse getByWorkLineCode(BuckWaRequest request);
	public BuckWaResponse getPersonByNotInTimeTable(BuckWaRequest request);
	public BuckWaResponse getPersonByUserIdList(BuckWaRequest request);
	public BuckWaResponse getPersonEstimateUser(Long estimateGroupId);
	public BuckWaResponse getPersonEstimateAllUser();
	public BuckWaResponse getByPersonId(BuckWaRequest request);
	public BuckWaResponse getEmployeeTypeList();
	public BuckWaResponse getFacultyIdByUsername(BuckWaRequest request);
}