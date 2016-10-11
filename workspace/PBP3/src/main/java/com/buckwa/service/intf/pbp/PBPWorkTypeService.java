package com.buckwa.service.intf.pbp;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;


public interface PBPWorkTypeService {
	//public BuckWaResponse getByAcademicYear(BuckWaRequest request);
	 
	
	public BuckWaResponse getCalculateByAcademicYear(BuckWaRequest request);
	public BuckWaResponse getExsistCalculateByAcademicYear(BuckWaRequest request);
	
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse addNew(BuckWaRequest request);
	public BuckWaResponse delete(BuckWaRequest request);
	public BuckWaResponse edit(BuckWaRequest request);
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse getSub(BuckWaRequest request);
	public BuckWaResponse addNewSub(BuckWaRequest request);
	public BuckWaResponse deleteSub(BuckWaRequest request);
	public BuckWaResponse editSub(BuckWaRequest request);
	
	
	public BuckWaResponse getByCodeAcademicFacultyCode(BuckWaRequest request);
	
	public BuckWaResponse getByAcademicYearFacultyCode(BuckWaRequest request);
	
	public BuckWaResponse getRadarPlotPersonMark(BuckWaRequest request);
	
	public BuckWaResponse getRadarPlotPersonMarkByYear(BuckWaRequest request);
	
	
	public BuckWaResponse getRadarPlotPersonMarkE(BuckWaRequest request);
	
	public BuckWaResponse getCurretnEvalulate(BuckWaRequest request);
	 
}

