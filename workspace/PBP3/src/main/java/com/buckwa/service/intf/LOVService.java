package com.buckwa.service.intf;

import java.util.List;

import com.buckwa.domain.common.LOVDomain;

public interface LOVService {	

	public List<LOVDomain> getLOV(String lovType);
	
	public List<LOVDomain> getProductCatagoriesLOV();
	
}
