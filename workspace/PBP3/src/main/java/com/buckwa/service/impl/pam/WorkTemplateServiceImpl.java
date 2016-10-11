package com.buckwa.service.impl.pam;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.pam.WorkTemplateAttrDao;
import com.buckwa.dao.intf.pam.WorkTemplateDao;
import com.buckwa.dao.intf.pam.WorkTemplateKpiDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.WorkTemplate;
import com.buckwa.domain.pam.WorkTemplateAttr;
import com.buckwa.domain.pam.WorkTemplateKpi;
import com.buckwa.service.intf.pam.WorkTemplateService;
import com.buckwa.util.BuckWaConstants;

/*
 @Author : Wichien Prommese(Pe)
 @Create : Aug 5, 2012 10:05:04 AM
 */
@Service("workTemplateService")
public class WorkTemplateServiceImpl implements WorkTemplateService {

	private static Logger logger = LoggerFactory.getLogger(WorkTemplateServiceImpl.class);

	@Autowired
	private WorkTemplateDao workTemplateDao;
	
	@Autowired
	private WorkTemplateKpiDao workTemplateKpiDao;
	
	@Autowired
	private WorkTemplateAttrDao workTemplateAttrDao;
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse create(BuckWaRequest request) {
		logger.info("WorkTemplateServiceImpl.create");
		BuckWaResponse response = new BuckWaResponse();
		try {
			WorkTemplate workTemplate = (WorkTemplate)request.get("workTemplate");
			workTemplate = workTemplateDao.create(workTemplate);
			if(workTemplate.getWorkTemplateAttrList()!=null&&workTemplate.getWorkTemplateAttrList().size()>0){
				for(WorkTemplateAttr workTemplateAttr : workTemplate.getWorkTemplateAttrList()){
					workTemplateAttr.setWorkTemplateId(workTemplate.getWorkTemplateId());
					WorkTemplateKpi workTemplateKpi = new WorkTemplateKpi();
					workTemplateKpi.setKpiId(workTemplateAttr.getKpiId());
					workTemplateKpi.setFlagCalculate(workTemplateAttr.getFlagCalculate());
					workTemplateKpi.setWorkTemplateId(workTemplate.getWorkTemplateId());
					workTemplateAttr = workTemplateAttrDao.create(workTemplateAttr);
					if(workTemplateAttr.getWorkTemplateAttrId()!=null){
						workTemplateKpi.setWorkTemplateAttrId(workTemplateAttr.getWorkTemplateAttrId());
						workTemplateKpiDao.create(workTemplateKpi);
					}
					
				}
			}
//			
//			//kpi
//			if(workTemplate.getWorkTemplateKpiList()!=null&&workTemplate.getWorkTemplateKpiList().size()>0){
//				for(WorkTemplateKpi workTemplateKpi : workTemplate.getWorkTemplateKpiList()){
//					workTemplateKpi.setWorkTemplateId(workTemplate.getWorkTemplateId());
//					workTemplateKpiDao.create(workTemplateKpi);
//				}
//			}
			
			response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);
		} catch (DuplicateKeyException dx) {
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}

	@Override
	public BuckWaResponse getById(BuckWaRequest request) {
		logger.info("WorkTemplateServiceImpl.getById");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String workTemplateId =  request.get("workTemplateId")+"";			
			WorkTemplate obj = workTemplateDao.getById(workTemplateId);						
			if(obj!=null){
				List<WorkTemplateAttr> workTemplateAttrList = workTemplateAttrDao.getByWorkTemplateId(workTemplateId);
				obj.setWorkTemplateAttrList(workTemplateAttrList);				
				response.addResponse("workTemplate",obj);
				response.setSuccessCode("S002");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	@Override
	public BuckWaResponse getByClassRoom() {
		logger.info("WorkTemplateServiceImpl.getByClassRoom");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			WorkTemplate obj = workTemplateDao.getByClassRoom();						
			if(obj!=null){
				List<WorkTemplateAttr> workTemplateAttrList = workTemplateAttrDao.getByWorkTemplateId(String.valueOf(obj.getWorkTemplateId()));
				obj.setWorkTemplateAttrList(workTemplateAttrList);				
				response.addResponse("workTemplate",obj);
				response.setSuccessCode("S002");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse update(BuckWaRequest request) {
		logger.info("WorkTemplateServiceImpl.update");
		BuckWaResponse response = new BuckWaResponse();
		try{	
			WorkTemplate workTemplate = (WorkTemplate)request.get("workTemplate");
			workTemplateKpiDao.deleteByWorkTemplateId(String.valueOf(workTemplate.getWorkTemplateId()));
			workTemplateAttrDao.deleteByWorkTemplateId(String.valueOf(workTemplate.getWorkTemplateId()));
			workTemplateDao.update(workTemplate);
			if(workTemplate.getWorkTemplateAttrList()!=null&&workTemplate.getWorkTemplateAttrList().size()>0){
				for(WorkTemplateAttr workTemplateAttr : workTemplate.getWorkTemplateAttrList()){
						workTemplateAttr.setWorkTemplateId(workTemplate.getWorkTemplateId());
						WorkTemplateKpi workTemplateKpi = new WorkTemplateKpi();
						workTemplateKpi.setKpiId(workTemplateAttr.getKpiId());
						workTemplateKpi.setFlagCalculate(workTemplateAttr.getFlagCalculate());
						workTemplateKpi.setWorkTemplateId(workTemplate.getWorkTemplateId());
						workTemplateKpi.setCreateBy(workTemplateAttr.getCreateBy());
						workTemplateAttr = workTemplateAttrDao.create(workTemplateAttr);
						if(workTemplateAttr.getWorkTemplateAttrId()!=null){
							workTemplateKpi.setWorkTemplateAttrId(workTemplateAttr.getWorkTemplateAttrId());
							workTemplateKpiDao.create(workTemplateKpi);
						}
				}
			}
//			//KPI
//			if(workTemplate.getWorkTemplateKpiList()!=null&&workTemplate.getWorkTemplateKpiList().size()>0){
//				for(WorkTemplateKpi workTemplateKpi : workTemplate.getWorkTemplateKpiList()){
//					workTemplateKpiDao.create(workTemplateKpi);
//				}
//			}
			response.setSuccessCode(BuckWaConstants.MSGCODE_EDIT_SUCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse delete(BuckWaRequest request) {
		logger.info("WorkTemplateServiceImpl.delete");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String workTemplateId = (String)request.get("workTemplateId");
			workTemplateKpiDao.deleteByWorkTemplateId(workTemplateId);
			workTemplateAttrDao.deleteByWorkTemplateId(workTemplateId);
			workTemplateDao.delete(workTemplateId);
			response.setSuccessCode(BuckWaConstants.MSGCODE_DELETE_SUCESS);
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
			PagingBean returnBean = workTemplateDao.getAllRoleByOffset(pagingBean);			
			response.addResponse("pagingBean",returnBean);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	@Override
	public BuckWaResponse checkNameAlready(BuckWaRequest request){
		logger.info("WorkTemplateServiceImpl.getById");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String workTemplateId =  request.get("workTemplateId")+"";		
			String name =  request.get("name")+"";
			boolean obj = workTemplateDao.checkNameAlready(workTemplateId,name);						
			if(obj)
				response.setStatus(BuckWaConstants.FAIL);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getByGroupId(BuckWaRequest request){
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String groupId = (String)request.get("groupId");			 
			List<WorkTemplate> workTemplateList = workTemplateDao.getByGroupIdNonTimeTable(groupId);
			//List<WorkTemplate> workTemplateList = workTemplateDao.getByGroupId(groupId);			
			response.addResponse("workTemplateList",workTemplateList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	@Override
	public BuckWaResponse getByEmployeeType(BuckWaRequest request){
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String employeeType = (String)request.get("employeeType");			 
			List<WorkTemplate> workTemplateList = workTemplateDao.getByEmployeeTypeNonTimeTable(employeeType);
			response.addResponse("workTemplateList",workTemplateList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	
	@Override
	public BuckWaResponse getByPersonType(BuckWaRequest request){
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String personType = (String)request.get("personType");			 
			List<WorkTemplate> workTemplateList = workTemplateDao.getByPersonTypeNonTimeTable(personType);
			response.addResponse("workTemplateList",workTemplateList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
	

	@Override
	public BuckWaResponse getIsFileListByWorkTemplateId(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			String id = (String) request.get("workTemplateId");			 
			List<WorkTemplateAttr> workTemplateAttrList = workTemplateAttrDao.getIsFileListByWorkTemplateId(id);
			response.addResponse("workTemplateAttrList", workTemplateAttrList);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}
}
