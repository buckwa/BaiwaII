package com.buckwa.service.impl.pam;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.pam.KpiTemplateDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.nodetree.KpiTemplate;
import com.buckwa.domain.pam.nodetree.KpiTemplateTree;
 
import com.buckwa.service.intf.pam.KpiTemplateService;
import com.buckwa.util.BuckWaConstants;

@Service("kpiTemplateService")
public class KpiTemplateServiceImpl implements KpiTemplateService {
	private static Logger logger = LoggerFactory.getLogger(KpiTemplateServiceImpl.class);
	
	@Autowired
	private KpiTemplateDao kpiTemplateDao;
	
	
	@Override
	public BuckWaResponse create(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			KpiTemplate kpitemplate = (KpiTemplate)request.get("kpiTemplate");		 
			kpiTemplateDao.create(kpitemplate);
		 
		}catch(DuplicateKeyException dx){			
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override
	
	public BuckWaResponse update(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			KpiTemplate kpi = (KpiTemplate)request.get("kpiTemplate");		
 
			kpiTemplateDao.update(kpi);
		 
		}catch(DuplicateKeyException dx){			
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override
	public BuckWaResponse remove(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			KpiTemplate kpiTemplate = (KpiTemplate)request.get("kpiTemplate");	
 
			kpiTemplateDao.remove(kpiTemplate);
		 
		}catch(DuplicateKeyException dx){			
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}

	@Override
	public BuckWaResponse getById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			KpiTemplate kpiTemplate = (KpiTemplate)request.get("kpiTemplate");	
 
			kpiTemplate =kpiTemplateDao.getById(kpiTemplate.getKpiTemplateId());
			response.addResponse("kpiTemplate",kpiTemplate);
			
		}catch(DuplicateKeyException dx){			
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}

	@Override
	public BuckWaResponse getByParentId(BuckWaRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuckWaResponse getTreeById(BuckWaRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuckWaResponse createTree(BuckWaRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuckWaResponse updateTree(BuckWaRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuckWaResponse getRecursive(BuckWaRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuckWaResponse getAllTree(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
 		
			KpiTemplateTree kpiTemplateTree = kpiTemplateDao.getAllTree();				
		 		
			response.addResponse("kpiTemplateTree",kpiTemplateTree);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getTreeByRootId(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String rootId = request.get("rootId")+"";	
			KpiTemplateTree kpiTemplateTree = kpiTemplateDao.getTreeByRootId(rootId);		
		 		
			response.addResponse("kpiTemplateTree",kpiTemplateTree);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	@Override
	public BuckWaResponse getTreeByCategoryId(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String categoryId = request.get("categoryId")+"";	
			KpiTemplateTree kpiTemplateTree = kpiTemplateDao.getTreeByCategoryId(categoryId);		
		 		
			response.addResponse("kpiTemplateTree",kpiTemplateTree);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
}
