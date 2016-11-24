package com.buckwa.service.intf.pbp;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;


public interface FacultyService {
	public BuckWaResponse getByAcademicYear(BuckWaRequest request);
	public BuckWaResponse getAllMarkByAcademicYear(BuckWaRequest request);
	
	public BuckWaResponse create(BuckWaRequest request); 
	public BuckWaResponse createDepartment(BuckWaRequest request);
	
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse updateFaculty(BuckWaRequest request);
	public BuckWaResponse deleteFacultyById(BuckWaRequest request);
	
	public BuckWaResponse getDepartmentById(BuckWaRequest request);
	public BuckWaResponse updateDepartment(BuckWaRequest request);
	public BuckWaResponse deleteDepartmentById(BuckWaRequest request);
	
	
	public BuckWaResponse getByFaculty(BuckWaRequest request);
	public BuckWaResponse getByDepartment(BuckWaRequest request);
	

	public BuckWaResponse getDeanByFacultyId(BuckWaRequest request);
	public BuckWaResponse getHeadByDepartmentId(BuckWaRequest request); 
	
	
	public BuckWaResponse assignDean(BuckWaRequest request); 
	public BuckWaResponse assignHead(BuckWaRequest request); 
	
	public BuckWaResponse getPresident(BuckWaRequest request); 
	public BuckWaResponse assignPresident(BuckWaRequest request); 
	
	public BuckWaResponse getFacultyByCodeAndYear(BuckWaRequest request);
	
	public BuckWaResponse getDepartmentByUserNameandYear(BuckWaRequest request);
	
	public BuckWaResponse getFacultyByUserNameandYear(BuckWaRequest request);
	public BuckWaResponse getFacultyByDeanUserNameandYear(BuckWaRequest request);
	
	public BuckWaResponse getDepartmentByHeadUserNameandYear(BuckWaRequest request);
	
	
	public BuckWaResponse getAllFaculty(BuckWaRequest request);
	
	public BuckWaResponse getFacultyListByAcademicYear(BuckWaRequest request);
	
	public BuckWaResponse getFacultyByCodeByAcademicYearAndName(BuckWaRequest request);
	public BuckWaResponse getFacultyByDeanUserNameandYearNew(BuckWaRequest request);
	
	
}

