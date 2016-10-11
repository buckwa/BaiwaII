package com.buckwa.service.intf.project;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

public interface ProjectTeamService {
 
	public BuckWaResponse getTeamByOffset(BuckWaRequest request);	
	public BuckWaResponse getMappingByUserName(BuckWaRequest request);
	
	public BuckWaResponse mapProjectTeam(BuckWaRequest request);
}
