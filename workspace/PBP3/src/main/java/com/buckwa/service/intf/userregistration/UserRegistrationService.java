package com.buckwa.service.intf.userregistration;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

public interface UserRegistrationService {
	
	public BuckWaResponse registerUser(BuckWaRequest request);
	
	public BuckWaResponse enableUser(BuckWaRequest request) ;
	
	public BuckWaResponse getUserByUsername(BuckWaRequest request);
	
	public BuckWaResponse updateRegisterUser(BuckWaRequest request);
	
	public BuckWaResponse isEmailAreadyRegistered(BuckWaRequest request);

}
