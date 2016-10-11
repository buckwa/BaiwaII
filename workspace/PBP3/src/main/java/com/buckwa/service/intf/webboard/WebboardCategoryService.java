package com.buckwa.service.intf.webboard;

import java.util.List;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.util.Category;
import com.buckwa.domain.util.Unit;

public interface WebboardCategoryService {
	
	public BuckWaResponse create(BuckWaRequest request);
	public BuckWaResponse list(BuckWaRequest request);
	public BuckWaResponse getAll();	 
	public BuckWaResponse update(BuckWaRequest request);
	public BuckWaResponse getByOffset(BuckWaRequest request);	
	public BuckWaResponse getById(BuckWaRequest request);
	public BuckWaResponse deleteById(BuckWaRequest request);
	
	public List<Category> getAllCategory();	
	 
}
