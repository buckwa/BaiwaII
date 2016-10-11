package com.buckwa.service.intf.admin;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

public interface AdminUserService {
	
	public BuckWaResponse createUser(BuckWaRequest request);
	public BuckWaResponse updateUser(BuckWaRequest request);
	public BuckWaResponse getUserByOffset(BuckWaRequest request);	
	public BuckWaResponse getUserByFacultyCodeOffset(BuckWaRequest request);	
	 
	public BuckWaResponse getUserById(BuckWaRequest request);
	
	// Add academic year
	public BuckWaResponse getUserByUsername(BuckWaRequest request);
	public BuckWaResponse getUserByUsernameForEdit(BuckWaRequest request);
	
	public BuckWaResponse registerUser(BuckWaRequest request);
	public BuckWaResponse enableUser(BuckWaRequest request);
	public BuckWaResponse changePassword(BuckWaRequest request);
	
	public BuckWaResponse delete(BuckWaRequest request);
	public BuckWaResponse updateRegisterUser(BuckWaRequest request);
	public BuckWaResponse updateSignatureImagePath(BuckWaRequest request);	
	public BuckWaResponse changeStatus(BuckWaRequest request);
	public BuckWaResponse resetPass(BuckWaRequest request);	
	public BuckWaResponse getUserAll(BuckWaRequest request);	
	public BuckWaResponse getUserAndAssingDateByUsername(BuckWaRequest request) ;
	
	public BuckWaResponse initCreateUser();
	
	
	public BuckWaResponse updateLeaveAccumulate(BuckWaRequest request);
	
	public BuckWaResponse getUserDepartmentByOffset(BuckWaRequest request);	
	
	public BuckWaResponse loginLoging(BuckWaRequest request);	
	
	 
}
