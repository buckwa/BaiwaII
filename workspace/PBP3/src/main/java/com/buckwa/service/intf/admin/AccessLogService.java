package com.buckwa.service.intf.admin;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

public interface AccessLogService {

	
	public BuckWaResponse getByOffset(BuckWaRequest request);	
}
