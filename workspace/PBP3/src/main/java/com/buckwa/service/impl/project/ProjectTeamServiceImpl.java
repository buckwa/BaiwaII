package com.buckwa.service.impl.project;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.project.ProjectTeamDao;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Team;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.service.intf.project.ProjectTeamService;
import com.buckwa.util.BuckWaConstants;


@Service("projectTeamService")
public class ProjectTeamServiceImpl implements ProjectTeamService {
	private static Logger logger = LoggerFactory.getLogger(ProjectTeamServiceImpl.class);
	
	@Autowired
	private ProjectTeamDao projectTeamDao;
	
	
	@Override
	public BuckWaResponse getTeamByOffset(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" getTeamByOffset :" );
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");			 
			PagingBean returnBean = projectTeamDao.getTeamByOffset(pagingBean);	 
			response.addResponse("pagingBean",returnBean);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	@Override
	public BuckWaResponse getMappingByUserName(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			
			String username = request.get("username")+"";	
			String projectId =  request.get("projectId")+"";	
			Team teamReturn = projectTeamDao.getMappingByUserName(username,projectId)	;	
			response.addResponse("team",teamReturn);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		response.setSuccessCode("S001");
		return response;
	}

	@Override
	public BuckWaResponse mapProjectTeam(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		
			
			Team team = (Team)request.get("team");	
			Team teamReturn  =projectTeamDao.mapProjectTeam(team);
			response.addResponse("team",teamReturn);			
			response.setSuccessCode("S002");
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		response.setSuccessCode("S001");
		return response;
	}

}
