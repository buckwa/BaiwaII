package com.buckwa.web.controller.project;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.buckwa.domain.validator.project.ModuleValidator;
import com.buckwa.service.intf.project.ModuleService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.project.ProjectUtil;

@Controller
@RequestMapping("/project/module")
@SessionAttributes(types = Module.class)
public class ModuleController { 
 
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ProjectUtil projectUtil;
	
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView module( HttpServletRequest httpRequest ) {
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initModule");
		 
		try {
			
			//BuckWaResponse response = moduleService.getAll();
			BuckWaRequest request = new BuckWaRequest();
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			BuckWaResponse 	response = moduleService.getAllByProjectId(request);
		 
			
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				List<Module>  returnList = (List)response.getResObj("moduleList"); 
				mav.addObject("moduleList", returnList);				
			}else {
				mav.addObject("errorCode", response.getErrorCode()); 
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
		mav.setViewName("moduleCreate");
		Module module = new Module();
		
		mav.addObject("module", module);	
		return mav;
	}	

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createPOST(@ModelAttribute Module module, BindingResult result,HttpServletRequest httpRequest) {	
		ModelAndView mav = new ModelAndView();
		try{			
			new ModuleValidator().validate(module, result);			
			if (result.hasErrors()) {				
				mav.setViewName("moduleCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
			
				module.setProjectId(projectUtil.getProjectIdFromSession(httpRequest));
				request.put("module", module);
				BuckWaResponse response = moduleService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					//BuckWaResponse response = moduleService.getAll();
					 request = new BuckWaRequest();
					request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
					 	response = moduleService.getAllByProjectId(request);
				 
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						List<Module>  returnList = (List)response.getResObj("moduleList"); 
						mav.addObject("moduleList", returnList);				
					}else {
						mav.addObject("errorCode", response.getErrorCode()); 
					}
					mav.setViewName("initModule");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("moduleCreate");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("moduleId") String moduleId,HttpServletRequest httpRequest) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("moduleEdit");
		BuckWaRequest request = new BuckWaRequest();
		request.put("moduleId", moduleId);	
		request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
		BuckWaResponse response = moduleService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Module module = (Module)response.getResObj("module");				 	
			mav.addObject("module", module);				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}	
	
	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Module module, BindingResult result,HttpServletRequest httpRequest ) {		
	
		ModelAndView mav = new ModelAndView();
		try{			 
			new ModuleValidator().validate(module, result);			
			if (result.hasErrors()) {				 
				mav.setViewName("moduleEdit");
			}else {
		 
				BuckWaRequest request = new BuckWaRequest();
				request.put("module", module);
				BuckWaResponse response = moduleService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){					 				
					//BuckWaResponse response = moduleService.getAll();
					 request = new BuckWaRequest();
					request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
					 	response = moduleService.getAllByProjectId(request);
				 
						if(response.getStatus()==BuckWaConstants.SUCCESS){				
							List<Module>  returnList = (List)response.getResObj("moduleList"); 
							mav.addObject("moduleList", returnList);				
						}else {
							mav.addObject("errorCode", response.getErrorCode()); 
						}
						mav.setViewName("initModule");				
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("moduleEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}		
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("moduleId") String moduleId,HttpServletRequest httpRequest ) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initModule");
		BuckWaRequest request = new BuckWaRequest();
		request.put("moduleId", moduleId);	
		BuckWaResponse response = moduleService.deleteById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			//BuckWaResponse response = moduleService.getAll();
			  request = new BuckWaRequest();
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			 	response = moduleService.getAllByProjectId(request);
		 
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					List<UseCase>  returnList = (List)response.getResObj("moduleList"); 
					mav.addObject("moduleList", returnList);				
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
				}			
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}	
	
}
