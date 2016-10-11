package com.buckwa.web.controller.project;

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
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Module;
import com.buckwa.domain.project.UseCase;
import com.buckwa.domain.project.Util;
import com.buckwa.domain.validator.project.UtilValidator;
import com.buckwa.service.intf.project.ModuleService;
import com.buckwa.service.intf.project.ProjectUtilService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.project.ProjectUtil;
import com.buckwa.web.controller.admin.RoleManagementController;

@Controller
@RequestMapping("/project/requirement/util")
@SessionAttributes(types = Util.class)
public class UtilController { 
	private static Logger logger = LoggerFactory.getLogger(UtilController.class);
	@Autowired
	private ProjectUtilService projectUtilService;
	
	@Autowired
	private ProjectUtil projectUtil;
	
	
	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView util( @RequestParam("type") String type,HttpServletRequest httpRequest) {
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initUtil");
		 
		try {
			httpRequest.getSession().setAttribute("type", type);
		    BuckWaRequest request = new BuckWaRequest();
		    request.put("type", type);
			BuckWaResponse response = projectUtilService.getAll(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				List<Util>  returnList = (List)response.getResObj("utilList"); 
				mav.addObject("utilList", returnList);				
			}else {
				mav.addObject("errorCode", response.getErrorCode()); 
			} 
			// Get All Module
			// response = moduleService.getAll();
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			response = moduleService.getAllByProjectId(request);
			  
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				List<Module>  returnList = (List)response.getResObj("moduleList"); 
				mav.addObject("moduleList", returnList);	
		 
			} 
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView createGET(HttpServletRequest httpRequest) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("utilCreate");
		Util util = new Util();
		String  type = (String)httpRequest.getSession().getAttribute("type");
		util.setType(type);
		
		mav.addObject("util", util);	
		return mav;
	}	

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createPOST(@ModelAttribute Util util, BindingResult result,HttpServletRequest httpRequest) {	
		ModelAndView mav = new ModelAndView();
		try{			
			new UtilValidator().validate(util, result);			
			if (result.hasErrors()) {				
				mav.setViewName("utilCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
			
				util.setProjectId(projectUtil.getProjectIdFromSession(httpRequest));
				request.put("util", util);
				BuckWaResponse response = projectUtilService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					String  type = (String)httpRequest.getSession().getAttribute("type");
				    request = new BuckWaRequest();
				    request.put("type", type);
					response = projectUtilService.getAll(request);
					
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						List<Util>  returnList = (List)response.getResObj("utilList"); 
						mav.addObject("utilList", returnList);				
					}else {
						mav.addObject("errorCode", response.getErrorCode()); 
					}
					mav.setViewName("initUtil");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("utilCreate");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("utilId") String utilId) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("utilEdit");
		BuckWaRequest request = new BuckWaRequest();
		request.put("utilId", utilId);	
		BuckWaResponse response = projectUtilService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Util util = (Util)response.getResObj("util");				 	
			mav.addObject("util", util);				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}	 

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Util util, BindingResult result,HttpServletRequest httpRequest) {		
	
		ModelAndView mav = new ModelAndView();
		
		logger.info(" #### Edit util submit ");
		try{			 
			new UtilValidator().validate(util, result);			
			if (result.hasErrors()) {				 
				mav.setViewName("utilEdit");
			}else {
		 
				BuckWaRequest request = new BuckWaRequest();
				request.put("util", util);
				BuckWaResponse response = projectUtilService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					String  type = (String)httpRequest.getSession().getAttribute("type");
				    request = new BuckWaRequest();
				    request.put("type", type);
					response = projectUtilService.getAll(request);
					
						if(response.getStatus()==BuckWaConstants.SUCCESS){				
							List<Util>  returnList = (List)response.getResObj("utilList"); 
							mav.addObject("utilList", returnList);				
						}else {
							mav.addObject("errorCode", response.getErrorCode()); 
						}
						mav.setViewName("initUtil");				
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("utilEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}		
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("utilId") String utilId,HttpServletRequest httpRequest) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initUtil");
		BuckWaRequest request = new BuckWaRequest();
		request.put("utilId", utilId);	
		BuckWaResponse response = projectUtilService.deleteById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			String  type = (String)httpRequest.getSession().getAttribute("type");
		    request = new BuckWaRequest();
		    request.put("type", type);
			response = projectUtilService.getAll(request);
			
			
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					List<UseCase>  returnList = (List)response.getResObj("utilList"); 
					mav.addObject("utilList", returnList);				
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
				}			
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}	
	
}
