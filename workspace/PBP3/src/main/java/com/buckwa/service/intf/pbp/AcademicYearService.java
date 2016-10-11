package com.buckwa.service.intf.pbp;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;


public interface AcademicYearService {
	public BuckWaResponse getCurrentAcademicYear(BuckWaRequest request);
	public BuckWaResponse getFullAcademicYear(BuckWaRequest request);
	public BuckWaResponse getByAcademicYear(BuckWaRequest request);
	public  BuckWaResponse ajustYearIncrease(BuckWaRequest request);
	public BuckWaResponse getByYear(BuckWaRequest request);
	
	public BuckWaResponse editDateAcademicYear(BuckWaRequest request);
	
	public BuckWaResponse getEvaluateRoundByYear(BuckWaRequest request);
	public BuckWaResponse editDateEvaluateRound(BuckWaRequest request);
	
	public BuckWaResponse getCurrentEvalulateRoundStr(BuckWaRequest request);


	
}

