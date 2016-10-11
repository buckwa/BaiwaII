package com.buckwa.service.impl.pam;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.pam.ClassRoomProcessDao;
import com.buckwa.dao.intf.pam.EstimateByUserDao;
import com.buckwa.dao.intf.pam.EstimateGroupDao;
import com.buckwa.dao.intf.pam.EstimateUserDao;
import com.buckwa.dao.intf.pam.FileLocationDao;
import com.buckwa.dao.intf.pam.WorkPersonAttrDao;
import com.buckwa.dao.intf.pam.WorkPersonDao;
import com.buckwa.dao.intf.pam.WorkPersonKpiDao;
import com.buckwa.dao.intf.pam.WorkPersonMappingFileDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.ClassRoomProcess;
import com.buckwa.domain.pam.EstimateByUser;
import com.buckwa.domain.pam.EstimateGroup;
import com.buckwa.domain.pam.EstimateUser;
import com.buckwa.domain.pam.FileLocation;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pam.Teacher;
import com.buckwa.domain.pam.WorkPerson;
import com.buckwa.domain.pam.WorkPersonAttr;
import com.buckwa.domain.pam.WorkPersonKpi;
import com.buckwa.domain.pam.WorkPersonMappingFile;
import com.buckwa.service.intf.pam.EstimateGroupService;
import com.buckwa.service.intf.pam.WorkPersonService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.FileUtils;

/*
 @Author : Wichien Prommese(Pe)
 @Create : Aug 5, 2012 10:05:04 AM
 */
@Service("estimateGroupService")
public class EstimateGroupServiceImpl implements EstimateGroupService {

	private static Logger logger = LoggerFactory.getLogger(EstimateGroupServiceImpl.class);

	@Autowired
	private EstimateByUserDao estimateByUserDao;
	
	@Autowired
	private EstimateUserDao estimateUserDao;
	
	@Autowired
	private EstimateGroupDao estimateGroupDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse create(BuckWaRequest request) {
		logger.info("EstimateGroupServiceImpl.create");
		BuckWaResponse response = new BuckWaResponse();
		try {
			EstimateGroup estimateGroup = (EstimateGroup)request.get("estimateGroup");
			estimateGroup = estimateGroupDao.create(estimateGroup);
			
			//estimate by user
			if(estimateGroup.getEstimateByUserList()!=null&&estimateGroup.getEstimateByUserList().size()>0){
				for(EstimateByUser estimateByUser :  estimateGroup.getEstimateByUserList()){
					if(estimateByUser.getUserId()!=null){
						estimateByUser.setEstimateGroupId(estimateGroup.getEstimateGroupId());
						estimateByUser = estimateByUserDao.create(estimateByUser);
					}
					
				}
			}
			
			//estimate by user additionl
			if(estimateGroup.getEstimateByUserAdditionalList()!=null&&estimateGroup.getEstimateByUserAdditionalList().size()>0){
				for(EstimateByUser estimateByUser :  estimateGroup.getEstimateByUserAdditionalList()){
					if(estimateByUser.getUserId()!=null){
						estimateByUser.setEstimateGroupId(estimateGroup.getEstimateGroupId());
						estimateByUser = estimateByUserDao.create(estimateByUser);
					}
					
				}
			}
			
			//estimate  user
			if(estimateGroup.getEstimateUserList()!=null&&estimateGroup.getEstimateUserList().size()>0){
				for(EstimateUser estimateUser :  estimateGroup.getEstimateUserList()){
					if(estimateUser.getUserId()!=null){
						estimateUser.setEstimateGroupId(estimateGroup.getEstimateGroupId());
						estimateUser = estimateUserDao.create(estimateUser);
					}
					
				}
			}
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
		logger.info("EstimateGroupServiceImpl.getById");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			Long estimateGroupId =  (Long)request.get("estimateGroupId");
			String view =null;
			if(request.get("view")!=null)
				view =  (String)request.get("view");
			EstimateGroup obj = estimateGroupDao.getById(estimateGroupId);						
			if(obj!=null){
				//Get Estimate by user
				List<EstimateByUser> estimateByUserList = estimateByUserDao.estimateByUserList(obj.getEstimateGroupId(),0,StringUtils.isNotEmpty(view)?true:false);
				obj.setEstimateByUserList(estimateByUserList);
				
				//Get Estimate by user additional
				List<EstimateByUser> estimateByUserAddtionalList = estimateByUserDao.estimateByUserList(obj.getEstimateGroupId(),1,StringUtils.isNotEmpty(view)?true:false);
				obj.setEstimateByUserAdditionalList(estimateByUserAddtionalList);
				
				//Get Estimate user
				List<EstimateUser> estimateUserList = estimateUserDao.estimateUserList(obj.getEstimateGroupId(),StringUtils.isNotEmpty(view)?true:false);
				obj.setEstimateUserList(estimateUserList);
				
				response.addResponse("estimateGroup",obj);
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
		logger.info("EstimateGroupServiceImpl.update");
		BuckWaResponse response = new BuckWaResponse();
		try{	
			EstimateGroup estimateGroup = (EstimateGroup)request.get("estimateGroup");
			estimateUserDao.delete(estimateGroup.getEstimateGroupId());
			estimateByUserDao.delete(estimateGroup.getEstimateGroupId());
			estimateGroupDao.update(estimateGroup);

			//estimate by user
			if(estimateGroup.getEstimateByUserList()!=null&&estimateGroup.getEstimateByUserList().size()>0){
				for(EstimateByUser estimateByUser :  estimateGroup.getEstimateByUserList()){
					if(estimateByUser.getUserId()!=null){
						estimateByUser.setEstimateGroupId(estimateGroup.getEstimateGroupId());
						estimateByUser = estimateByUserDao.create(estimateByUser);
					}
					
				}
			}
			
			//estimate by user additional
			if(estimateGroup.getEstimateByUserAdditionalList()!=null&&estimateGroup.getEstimateByUserAdditionalList().size()>0){
				for(EstimateByUser estimateByUser :  estimateGroup.getEstimateByUserAdditionalList()){
					if(estimateByUser.getUserId()!=null){
						estimateByUser.setEstimateGroupId(estimateGroup.getEstimateGroupId());
						estimateByUser = estimateByUserDao.create(estimateByUser);
					}
					
				}
			}
			
			//estimate  user
			if(estimateGroup.getEstimateUserList()!=null&&estimateGroup.getEstimateUserList().size()>0){
				for(EstimateUser estimateUser :  estimateGroup.getEstimateUserList()){
					if(estimateUser.getUserId()!=null){
						estimateUser.setEstimateGroupId(estimateGroup.getEstimateGroupId());
						estimateUser = estimateUserDao.create(estimateUser);
					}
					
				}
			}
			
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
		logger.info("EstimateGroupServiceImpl.delete");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			Long estimateGroupId = (Long)request.get("estimateGroupId");
			estimateByUserDao.delete(estimateGroupId);
			estimateUserDao.delete(estimateGroupId);
			estimateGroupDao.delete(estimateGroupId);
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
			logger.info("EstimateGroupServiceImpl.getByOffset");
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");			 
			PagingBean returnBean = estimateGroupDao.getAllByOffset(pagingBean);			
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
		logger.info("EstimateGroupServiceImpl.checkNameAlready");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			Long estimateGroupId =  (Long)request.get("estimateGroupId");		
			String name =  request.get("name")+"";
			boolean obj = estimateGroupDao.checkNameAlready(estimateGroupId,name);						
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
	public BuckWaResponse getEstimateUserList(Long byUserId){
		logger.info("EstimateGroupServiceImpl.getEstimateUserList");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			List<Person> personList = estimateByUserDao.getEstimateUserList(byUserId);						
			response.addResponse("personList", personList);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
}
