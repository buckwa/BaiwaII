package com.buckwa.web.controller.project;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Team;
import com.buckwa.domain.validator.project.TeamValidator;
import com.buckwa.service.intf.project.ProjectTeamService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.project.ProjectUtil;
import com.buckwa.web.controller.admin.RoleManagementController;

@Controller
@RequestMapping("/project/pm/team")
@SessionAttributes(types = Team.class)
public class ProjectTeamController {  
	private static Logger logger = LoggerFactory.getLogger(ProjectTeamController.class);
	@Autowired
	private ProjectTeamService projectTeamService;	 	
	
	@Autowired
	private ProjectUtil projectUtil;
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView init(@RequestParam("projectName") String projectName,@RequestParam("projectId") String projectId,HttpServletRequest httpRequest ) {
		ModelAndView mav = new ModelAndView(); 
		mav.setViewName("initProjecTeam");
		try{			  		   
			logger.info(" ## ProjectTeamController.init projectName:"+projectName+"  projectId:"+projectId);
			httpRequest.getSession().setAttribute("projectId", projectId);
			Team team = new Team();	
			team.setProjectName(projectName);
			team.setProjectId(new Long(projectId));
			PagingBean bean = new PagingBean();		
			mav.addObject("pagingBean", bean);	
			mav.addObject("team", team);	 
			// Search initial
			int offset = 0;	
			bean.setOffset(offset);				
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);		
			bean.put("team", team);
			BuckWaResponse response = projectTeamService.getTeamByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				mav.addObject("pagingBean", beanReturn);				
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}			 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	 
	@RequestMapping(value="search.htm" )
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute Team team) {
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("initProjecTeam");
		try{			
			logger.info(" ## ProjectTeamController.search ## ");
			PagingBean bean = new PagingBean();
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);		
			
			 
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);		
			bean.put("team", team);
			BuckWaResponse response = projectTeamService.getTeamByOffset(request);		 
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				mav.addObject("pagingBean", beanReturn);		
				mav.addObject("doSearch","true");
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}	
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
 
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView editGET(@RequestParam("username") String username,@RequestParam("projectName") String projectName,HttpServletRequest httpRequest ) {
		ModelAndView mav = new ModelAndView(); 
		mav.setViewName("editProjectTeam");
		try{
			logger.info(" ## ProjectTeamController.edit ## ");
			BuckWaRequest request = new BuckWaRequest();
			request.put("username", username);
			request.put("projectId",projectUtil.getProjectIdFromSession(httpRequest));
			
			BuckWaResponse response = projectTeamService.getMappingByUserName(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				Team team = (Team)response.getResObj("team"); 
				team.setProjectId(projectUtil.getProjectIdFromSession(httpRequest));
				team.setUsername(username);
				team.setProjectName(projectName);
				mav.addObject("team", team);
			} 			 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Team team, BindingResult result,HttpServletRequest httpRequest) {		
	    logger.info(" ## Team Edit team:"+BeanUtils.getBeanString(team));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("editProjectTeam"); 
		try{			 
			logger.info(" ## ProjectTeamController.editSubmit ## ");
			new TeamValidator().validate(team, result);			
			if (result.hasErrors()) {				 
				mav.setViewName("editProjectTeam");
			}else {
		 
				BuckWaRequest request = new BuckWaRequest();
				request.put("team", team);
				team.setProjectId(projectUtil.getProjectIdFromSession(httpRequest));
				BuckWaResponse response = projectTeamService.mapProjectTeam(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){					 				
					Team teamReturn = (Team)response.getResObj("team");
					teamReturn.setProjectId(projectUtil.getProjectIdFromSession(httpRequest));
					mav.addObject("team", teamReturn); 		
					mav.setViewName("editProjectTeam"); 
					teamReturn.setUsername(team.getUsername());
					mav.addObject("successCode", response.getSuccessCode()); 
					
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 				 
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
 
}
