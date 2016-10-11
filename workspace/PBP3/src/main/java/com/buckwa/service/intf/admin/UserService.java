package com.buckwa.service.intf.admin;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

public interface UserService {
	
	public BuckWaResponse createUser(BuckWaRequest request);
	public BuckWaResponse updateUser(BuckWaRequest request);
	public BuckWaResponse getUserByOffset(BuckWaRequest request);	
	 
	public BuckWaResponse getUserById(BuckWaRequest request);
	public BuckWaResponse getUserByUsername(BuckWaRequest request);
	
	public BuckWaResponse registerUser(BuckWaRequest request);
	public BuckWaResponse enableUser(BuckWaRequest request);
	public BuckWaResponse changePassword(BuckWaRequest request);
	
	public BuckWaResponse delete(BuckWaRequest request);
	public BuckWaResponse updateRegisterUser(BuckWaRequest request);
	public BuckWaResponse updateSignatureImagePath(BuckWaRequest request);
	
	
	public BuckWaResponse changeStatus(BuckWaRequest request);
	public BuckWaResponse resetPass(BuckWaRequest request);
	
	
	 
}
