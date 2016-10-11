package com.buckwa.service.intf.webboard;

import java.util.List;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.webboard.Topic;

public interface WebboardTopicService {
	
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse list(BuckWaRequest request);
	public BuckWaResponse getAll();	 
	public BuckWaResponse update(BuckWaRequest request);
	public BuckWaResponse getByOffset(BuckWaRequest request);	
	public BuckWaResponse getAllByOffsetAndCategoryName(BuckWaRequest request);	 
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse deleteById(BuckWaRequest request);
	
	public BuckWaResponse disable(BuckWaRequest request);
	public BuckWaResponse enable(BuckWaRequest request);
	
	public List<Topic> getAllTopic();	
	 
	
	public BuckWaResponse viewTopic(BuckWaRequest request);
	
	public BuckWaResponse replyMessage(BuckWaRequest request);
	
	public BuckWaResponse getTopicByArtifaceId(BuckWaRequest request);
	
	
	
	
	public BuckWaResponse disableMessage(BuckWaRequest request);
	public BuckWaResponse enableMessage(BuckWaRequest request);
	
	
	public BuckWaResponse replyPBPMessage(BuckWaRequest request);
	
	
}
