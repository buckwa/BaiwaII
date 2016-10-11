package com.buckwa.service.impl.admin;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.admin.GroupDao;
import com.buckwa.domain.admin.Group;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.service.intf.admin.GroupService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;

@Service("groupService") 
public class GroupServiceImpl implements GroupService {	
	
	private static Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
	@Autowired
	private GroupDao groupDao;
 
	@Override
	public BuckWaResponse getAll() {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			List groupList = groupDao.getAllGroup();			
			response.addResponse("groupList",groupList);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		response.setSuccessCode("S001");
		return response;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse create(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{
			Group group = (Group)request.get("group"); 
			boolean iseExist = groupDao.isGroupExist(group.getGroupName());
			if(iseExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				groupDao.create(group);
				response.setSuccessCode(BuckWaConstants.MSGCODE_IMPORT_SUCESS);	
			}						
			
		}catch(DuplicateKeyException dx){
			//dx.printStackTrace();				
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		
		// Success return S001
		response.setSuccessCode("S001");
		return response;
	}
	


	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse update(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{	 
			Group group = (Group)request.get("group"); 
			boolean iseExist = groupDao.isGroupExistForUpdate(group.getGroupName(),group.getGroupId());
			if(iseExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				groupDao.update(group);
				response.setSuccessCode("S002");	
			}		
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		
		// Success return S001
		response.setSuccessCode("S001");
		return response;
	}


	@Override
	public BuckWaResponse getByOffset(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
 
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");			 
			PagingBean returnBean = groupDao.getAllGroupByOffset(pagingBean);			
			response.addResponse("pagingBean",returnBean);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		
		// Success return S001
		response.setSuccessCode("S001");
		return response;
	}


	@Override
	public BuckWaResponse getById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			
			logger.info(" GroupServiceImpl.getGroupById");
			String groupId = (String)request.get("groupId");			
			Group group = groupDao.getGroupById(groupId);						
			if(group!=null){
				response.addResponse("group",group);
			}else{
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		
		// Success return S001
		response.setSuccessCode("S001");
		return response;
	}
	@Override
	public BuckWaResponse deleteById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String id =  request.get("groupId")+"";
 
			// Check Is using
			boolean isUsage = groupDao.isGroupAlreadyUsege(id);
			if(isUsage){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");	
			}else{
				groupDao.deleteGroupById(id);	
			}			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		response.setSuccessCode("S001");
		return response;
	}
}
