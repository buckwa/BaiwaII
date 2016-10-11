package com.buckwa.service.impl.pam;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pam.KpiCategoryDao;
import com.buckwa.dao.intf.pam.KpiTreeDao;
import com.buckwa.dao.intf.pam.KpiYearMappingDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.KpiYearMapping;
import com.buckwa.domain.pam.nodetree.Kpi;
import com.buckwa.domain.pam.nodetree.KpiTree;
import com.buckwa.service.intf.pam.KpiTreeService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;

@Service("kpiTreeService")
public class KpiTreeServiceImpl implements KpiTreeService {
	private static Logger logger = LoggerFactory.getLogger(KpiTreeServiceImpl.class);
	
	@Autowired
	private KpiTreeDao kpiTreeDao;
	
	
 
	
	@Override
	public BuckWaResponse create(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			Kpi kpi = (Kpi)request.get("kpi");		
 
			kpiTreeDao.create(kpi);
		 
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
			Kpi kpi = (Kpi)request.get("kpi");		
 
			kpiTreeDao.update(kpi);
		 
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
			Kpi kpi = (Kpi)request.get("kpi");		
 
			kpiTreeDao.remove(kpi);
		 
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
			Kpi kpi = (Kpi)request.get("kpi");		
 
			kpi =kpiTreeDao.getById(kpi.getKpiId());
			response.addResponse("kpi",kpi);
			
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
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String kpiId = (String)request.get("kpiId");	
			
			KpiTree kpiTree = kpiTreeDao.getTreeById(new Long(kpiId));				
		 		 
			response.addResponse("kpiTree",kpiTree);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getTemplateByYearAndGroupId(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			
			 
			String yearId = (String)request.get("yearId");	
			String groupId = (String)request.get("groupId");	
			 
		 
			KpiTree kpiTree = kpiTreeDao.getTemplateByYearAndGroupId(yearId,groupId);		 
			response.addResponse("kpiTree",kpiTree);	 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse updateWeight(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			
			 
			String kpiId = (String)request.get("kpiId");	
			String weight = (String)request.get("weight");	 
		 
				kpiTreeDao.updateWeight(kpiId,weight);		 
		  
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse updateTarget(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			
			 
			String kpiId = (String)request.get("kpiId");	
			String weight = (String)request.get("weight");	 
		 
				kpiTreeDao.updateTarget(kpiId,weight);		 
		  
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse updateLevel(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			
			 
			String levelId = (String)request.get("levelId");	
			String mark = (String)request.get("mark");	 
			String level = (String)request.get("level");	 
		 
				kpiTreeDao.updateLevel(levelId,mark,level);		 
		  
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
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
	public BuckWaResponse editLevel1(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
 		
			KpiTree kpiTree =  (KpiTree)request.get("kpiTree");			
		 		
			 kpiTreeDao.editLevel1(kpiTree);		
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
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
 		
			KpiTree kpiTree = kpiTreeDao.getAllTree();				
		 		
			response.addResponse("kpiTree",kpiTree);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getNodeTreeByYearandEmpType(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
 		
			String yearId = (String)request.get("yearId");
			String empTypeId = (String)request.get("empTypeId");
			
			KpiTree kpiTree = kpiTreeDao.getNodeTreeByYearandEmpType(yearId,empTypeId);			
	 		
			response.addResponse("kpiTree",kpiTree);	
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	
	
	@Override
	public BuckWaResponse getNodeTreeByYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
 		
			String yearId = (String)request.get("yearId");
			
			KpiTree kpiTree = kpiTreeDao.getNodeTreeByYear(yearId);			
	 		
			response.addResponse("kpiTree",kpiTree);	
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
}
