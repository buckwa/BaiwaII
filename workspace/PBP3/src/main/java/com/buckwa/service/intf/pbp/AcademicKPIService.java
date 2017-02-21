package com.buckwa.service.intf.pbp;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;


public interface AcademicKPIService {
	public BuckWaResponse getByAcademicYear(BuckWaRequest request);
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse getByAcademicYearWorkTypeCode(BuckWaRequest request);
	public BuckWaResponse getByAcademicYearWorkTypeCodeFacultyCode(BuckWaRequest request);
	
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse edit(BuckWaRequest request);
	public BuckWaResponse deleteById(BuckWaRequest request);
	
	public BuckWaResponse addNewAttribute(BuckWaRequest request);
	public BuckWaResponse deleteAttributeById(BuckWaRequest request);
	
	public BuckWaResponse getByCodeAcademicYear(BuckWaRequest request);
	public BuckWaResponse importwork(BuckWaRequest request);
	public BuckWaResponse importwork_file(BuckWaRequest request);
	
	public BuckWaResponse getAllByAcademicYear(BuckWaRequest request);
	public BuckWaResponse getAllByAcademicYearFacultyCode(BuckWaRequest request);

	public BuckWaResponse getByAcademicYearWorkTypeCodeFacultyCodeByinitApprove(BuckWaRequest request);
	
}

