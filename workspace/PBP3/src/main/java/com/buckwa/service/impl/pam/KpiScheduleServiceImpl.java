package com.buckwa.service.impl.pam;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pam.KpiScheduleDao;
import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.KpiSchedule;
import com.buckwa.service.intf.pam.KpiScheduleService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;

/*
@Author : Taechapon Himarat (Su)
@Create : Sep 30, 2012 9:58:50 AM
 */
@Service("kpiScheduleService")
public class KpiScheduleServiceImpl implements KpiScheduleService {
	
	private static Logger logger = LoggerFactory.getLogger(KpiScheduleServiceImpl.class);
	
	
	@Autowired
	private KpiScheduleDao kpiScheduleDao;
	
	
	@Override
	public BuckWaResponse getByOffset(BuckWaRequest request) {
		
		logger.info(" KpiScheduleServiceImpl.getByOffset");
		
		BuckWaResponse response = new BuckWaResponse();
		try {
			PagingBean pagingBean = (PagingBean) request.get("pagingBean");
			PagingBean returnBean = kpiScheduleDao.getByOffset(pagingBean);
			response.addResponse("pagingBean", returnBean);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}

	@Override
	public BuckWaResponse create(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			logger.info(" KpiScheduleServiceImpl.create");
			KpiSchedule kpiSchedule = (KpiSchedule) request.get("kpiSchedule");
			
			boolean isExist = kpiScheduleDao.isExist(kpiSchedule.getYearName());
			if(isExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				BuckWaUser user = BuckWaUtils.getUserFromContext();
				kpiSchedule.setCreateBy(user.getUsername());
				
				kpiScheduleDao.create(kpiSchedule);
				response.setStatus(BuckWaConstants.SUCCESS);
				response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

	@Override
	public BuckWaResponse update(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			logger.info(" KpiScheduleServiceImpl.update");
			KpiSchedule kpiSchedule = (KpiSchedule) request.get("kpiSchedule");
			
			BuckWaUser user = BuckWaUtils.getUserFromContext();
			kpiSchedule.setUpdateBy(user.getUsername());
			
			kpiScheduleDao.update(kpiSchedule);
			response.setStatus(BuckWaConstants.SUCCESS);
			response.setSuccessCode(BuckWaConstants.MSGCODE_EDIT_SUCESS);
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

	@Override
	public BuckWaResponse delete(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			logger.info(" KpiScheduleServiceImpl.delete");
			Long kpiScheduleId =  Long.valueOf(request.get("kpiScheduleId").toString());
			BuckWaUser user = BuckWaUtils.getUserFromContext();
			
			kpiScheduleDao.deleteById(kpiScheduleId, user.getUsername());
			response.setStatus(BuckWaConstants.SUCCESS);
			response.setSuccessCode(BuckWaConstants.MSGCODE_DELETE_SUCESS);
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

	@Override
	public BuckWaResponse getById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			logger.info(" KpiScheduleServiceImpl.getById");
			Long kpiScheduleId =  Long.valueOf(request.get("kpiScheduleId").toString());
			KpiSchedule kpiSchedule = kpiScheduleDao.getById(kpiScheduleId);
			if (kpiSchedule != null) {
				response.addResponse("kpiSchedule", kpiSchedule);
				response.setStatus(BuckWaConstants.SUCCESS);
			}
			else {
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E001");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}

	@Override
	public BuckWaResponse getByYearName(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			logger.info(" KpiScheduleServiceImpl.getByYearName");
			String yearName =  String.valueOf(request.get("yearName"));
			KpiSchedule kpiSchedule = kpiScheduleDao.getByYearName(yearName);
			if (kpiSchedule != null) {
				response.addResponse("kpiSchedule", kpiSchedule);
				response.setStatus(BuckWaConstants.SUCCESS);
			}
			else {
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E001");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}

}
