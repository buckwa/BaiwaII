package com.buckwa.service.impl.pam;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.pam.KpiCategoryDao;
import com.buckwa.dao.intf.pam.KpiTemplateDao;
import com.buckwa.dao.intf.pam.KpiTreeDao;
import com.buckwa.dao.intf.pam.KpiYearMappingDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.KpiCategory;
import com.buckwa.domain.pam.KpiYearMapping;
import com.buckwa.domain.pam.Year;
import com.buckwa.domain.pam.nodetree.KpiTemplateTree;
import com.buckwa.domain.pam.nodetree.KpiTree;
import com.buckwa.service.intf.pam.KpiYearMappingService;
import com.buckwa.util.BuckWaConstants;
 
@Service("kpiYearMappingService")
public class KpiYearMappingServiceImpl implements KpiYearMappingService {
	private static Logger logger = LoggerFactory.getLogger(KpiYearMappingServiceImpl.class);
	
	@Autowired
	private KpiYearMappingDao kpiYearMappingDao;
	
	@Autowired
	private KpiCategoryDao kpiCategoryDao;
	
	@Autowired
	private KpiTreeDao kpiTreeDao;
	
	
	@Autowired
	private KpiTemplateDao kpiTemplateDao;
	
	@Override
	public BuckWaResponse getAll() {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List<KpiYearMapping> kpiCategoryList = kpiYearMappingDao.getAll();			
			response.addResponse("kpiCategoryList",kpiCategoryList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

	@Override
	public BuckWaResponse create(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			KpiYearMapping kpiYearMapping = (KpiYearMapping)request.get("kpiYearMapping");		
			
			kpiYearMappingDao.create(kpiYearMapping);

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
	public BuckWaResponse createNewMapping(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			KpiYearMapping kpiYearMapping = (KpiYearMapping)request.get("kpiYearMapping");	
			
			boolean isExist = kpiYearMappingDao.isExistForCreate(kpiYearMapping);
			if(isExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{			 
				// Get Tree by Category
				logger.info("   kpiYearMapping.getCategoryId():"+kpiYearMapping.getCategoryId());
				KpiTemplateTree kpiTemplateTree =kpiTemplateDao.getTreeByCategoryId(kpiYearMapping.getCategoryId()+"");				
				kpiTemplateTree.getRootElement().getData().setYearId(kpiYearMapping.getYearId());
				kpiTemplateTree.getRootElement().getData().setCategoryId(kpiYearMapping.getCategoryId());
				
				
				
				  
				Long returnId = kpiTreeDao.createTreeByTemplate(kpiTemplateTree);
				 
				kpiYearMapping.setKpiId(returnId);
				 
				// Set id to mapping 
				kpiYearMappingDao.createKpiYearMapping(kpiYearMapping);
			} 

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
			KpiYearMapping kpiYearMapping = (KpiYearMapping)request.get("kpiYearMapping");
			
			kpiYearMappingDao.update(kpiYearMapping);	
			response.setSuccessCode("S002");	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	 
		return response;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse updateStatus(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	
			KpiYearMapping kpiYearMapping = (KpiYearMapping)request.get("kpiYearMapping");
			
			kpiYearMappingDao.updateStatus(kpiYearMapping);	
			response.setSuccessCode("S002");	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	 
		return response;
	}


	@Override
	public BuckWaResponse getByOffset(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info("");
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");			 
			PagingBean returnBean = kpiYearMappingDao.getByOffset(pagingBean);			
			response.addResponse("pagingBean",returnBean);				
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
			logger.info(" KpiCategoryServiceImpl.getKpiCategoryById");
			String kpiCategoryId =  request.get("kpiCategoryId")+"";			
			KpiYearMapping kpiyearMapping = kpiYearMappingDao.getById(kpiCategoryId);						
			if(kpiyearMapping!=null){
				response.addResponse("kpiCategory",kpiyearMapping);
			}else{
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

	@Override
	public BuckWaResponse deleteById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String kpiCategoryId = (String)request.get("kpiCategoryId");
			
			// Check Is KpiCategory are using
			boolean isKpiCategoryAlreadyUsege = kpiYearMappingDao.isAlreadyUsege(kpiCategoryId);
			if(isKpiCategoryAlreadyUsege){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");	
			}else{
				kpiYearMappingDao.deleteById(kpiCategoryId);	
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse initialMappingByYear(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			Year year = (Year)request.get("year");
			
			// Get All Category 
			List<KpiCategory> kpiCategoryList = kpiCategoryDao.getAll();	
			
			// Get KPI Template by Category
			List<KpiTree> kpitreeList = new ArrayList();
			for(KpiCategory cattmp:kpiCategoryList){
				//KpiTree kpiTree = kpiTreeDao.getTreeByCategoryId(cattmp.getKpiCategoryId()+"");	
				//kpitreeList.add(kpiTree);
			}
			
			
			// Loop Set Year and Category type to Code of KPI Root 
			List<KpiYearMapping> yearMappingList = new ArrayList();
			
			for(KpiTree kpitreetmp:kpitreeList){
				String newCode = year.getName()+"_"+kpitreetmp.getRootElement().getData().getKpiTemplateId();
				logger.info(" newCode :"+newCode);
				kpitreetmp.getRootElement().getData().setCode(newCode);
				 
				KpiYearMapping yearMapping = new KpiYearMapping();
				yearMapping.setYearId(kpitreetmp.getRootElement().getData().getKpiId());
				yearMapping.setKpiId(year.getYearId());
				yearMappingList.add(yearMapping);
			}
		 
	 
			//kpiTreeDao.initialMappingByYear(kpitreeList);	
			 
			kpiYearMappingDao.initialMappingByYear(yearMappingList);	
			 
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	
 
	
	
	
	
	

	@Override
	public BuckWaResponse getBySemester(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List<KpiYearMapping> kpiYearMappingList = kpiYearMappingDao.getBySemester(request.get("semesterId").toString());			
			response.addResponse("kpiYearMappingList", kpiYearMappingList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
}
