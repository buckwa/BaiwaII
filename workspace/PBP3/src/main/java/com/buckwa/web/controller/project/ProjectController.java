package com.buckwa.web.controller.project;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.project.Project;
import com.buckwa.domain.validator.project.ProjectValidator;
import com.buckwa.service.intf.project.ProjectService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.web.controller.admin.RoleManagementController;

@Controller
@RequestMapping("/project")
@SessionAttributes(types = Project.class)
public class ProjectController {  
	private static Logger logger = LoggerFactory.getLogger(ProjectController.class);
	@Autowired
	private ProjectService projectService;	 
	
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView init(HttpServletRequest httpRequest ) {
		ModelAndView mav = new ModelAndView(); 
		mav.setViewName("initProject");
		logger.info(" ## ProjectController.init ## ");
		try{
			 
			List<Project> projectList  = new ArrayList();
			BuckWaResponse response = projectService.getAll();
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				projectList = (List)response.getResObj("projectList"); 
			}
			
			mav.addObject("projectList", projectList);	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView createGET(HttpServletRequest httpRequest ) {
		ModelAndView mav = new ModelAndView(); 
		mav.setViewName("projectCreate");
		try{
			
			Project project = new Project();	
		 
			mav.addObject("project", project);	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createPOST(@ModelAttribute Project project, BindingResult result,HttpServletRequest httpRequest) {	
		ModelAndView mav = new ModelAndView();
		logger.info("  ## In UseCaseController.createPOST() useCase:"+BeanUtils.getBeanString(project));
		try{			
			new ProjectValidator().validate(project, result);			
			if (result.hasErrors()) {				
				mav.setViewName("projectCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest(); 
				request.put("project", project);
				BuckWaResponse response = projectService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	 					
					List<Project> projectList  = new ArrayList();
					response = projectService.getAll();
					if(response.getStatus()==BuckWaConstants.SUCCESS){
						projectList = (List)response.getResObj("projectList"); 
						mav.addObject("projectList", projectList);
					} 
					mav.setViewName("initProject");		 
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("projectCreate");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
		 
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView editGET(@RequestParam("projectId") String projectId,HttpServletRequest httpRequest ) {
		ModelAndView mav = new ModelAndView(); 
		mav.setViewName("projectEdit");
		try{
			logger.info(" ## ProjectController.editGET ## ");
			Project project = new Project();	
			BuckWaRequest request = new BuckWaRequest();
			request.put("projectId", projectId);
			BuckWaResponse response = projectService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				Project returnProject = (Project)response.getResObj("project"); 
				mav.addObject("project", returnProject);
			} 
			 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Project project, BindingResult result) {		
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("projectEdit"); 
		try{			 
			logger.info(" ## ProjectController.submitEdit ## ");
			new ProjectValidator().validate(project, result);			
			if (result.hasErrors()) {				 
				mav.setViewName("projectEdit");
			}else {
		 
				BuckWaRequest request = new BuckWaRequest();
				request.put("project", project);
				BuckWaResponse response = projectService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					mav.addObject("successCode", response.getSuccessCode()); 
					List<Project> projectList  = new ArrayList();
					response = projectService.getAll();
					if(response.getStatus()==BuckWaConstants.SUCCESS){
						projectList = (List)response.getResObj("projectList"); 
						mav.addObject("projectList", projectList);
					} 
					mav.setViewName("projectEdit"); 
					
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
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("projectId") String projectId ) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initProject");
		
		try{
			BuckWaRequest request = new BuckWaRequest();
			request.put("projectId", projectId);	
			BuckWaResponse response = projectService.deleteById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				
				List<Project> projectList  = new ArrayList();
				 response = projectService.getAll();
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					projectList = (List)response.getResObj("projectList"); 
					mav.addObject("projectList", projectList);
				}
				mav.setViewName("initProject"); 
	 				
				
			}else {	
				mav.addObject("errorCode", response.getErrorCode()); 
			}	 
		
	}catch(Exception ex){
		ex.printStackTrace();
		mav.addObject("errorCode", "E001"); 
	}
		return mav;
	}	

}
