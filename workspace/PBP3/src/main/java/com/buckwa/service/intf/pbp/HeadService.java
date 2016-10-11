package com.buckwa.service.intf.pbp;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;


public interface HeadService {
	public BuckWaResponse getByHeadAcademicYear(BuckWaRequest request);
	public BuckWaResponse getDepartmentMark(BuckWaRequest request);
	public BuckWaResponse getDepartmentMarkAllYear(BuckWaRequest request);
	
	public BuckWaResponse getDepartmentMarkByDepCode(BuckWaRequest request);
	
	public BuckWaResponse getByUserAcademicYear(BuckWaRequest request);
	public BuckWaResponse getDepartmentMarkByUser(BuckWaRequest request);
	
	public BuckWaResponse saveDepartmentReportSummary(BuckWaRequest request);
	
	public BuckWaResponse getReportWorkTypeDepartment(BuckWaRequest request);
	public BuckWaResponse markDepartmentRecal(BuckWaRequest request);
	
	
	public BuckWaResponse getDepartmentMean(BuckWaRequest request);
	public BuckWaResponse getDepartmentMeanByWorkTypeCode(BuckWaRequest request);
}

