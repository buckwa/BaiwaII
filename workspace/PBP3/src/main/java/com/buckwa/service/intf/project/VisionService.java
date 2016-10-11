package com.buckwa.service.intf.project;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

public interface VisionService {
	 
	public BuckWaResponse update(BuckWaRequest request); 
	public BuckWaResponse getVisionByProjectId(BuckWaRequest request); 
	public BuckWaResponse updateImagePath(BuckWaRequest request);	
	public BuckWaResponse updateRefenceFilePath(BuckWaRequest request);	
	
	public BuckWaResponse removeImage(BuckWaRequest request);
	public BuckWaResponse removeFile(BuckWaRequest request);
 
	
}
