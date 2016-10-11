package com.buckwa.web.controller.lab;

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
import com.buckwa.domain.project.LabCategory;
import com.buckwa.domain.project.UseCase;
import com.buckwa.domain.validator.project.LabCategoryValidator;
import com.buckwa.service.intf.project.LabCategoryService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.project.ProjectUtil;

@Controller
@RequestMapping("/lab/labcategory")
@SessionAttributes(types = LabCategory.class)
public class LabCategoryController { 
 
	@Autowired
	private LabCategoryService labCategoryService;
	
	@Autowired
	private ProjectUtil projectUtil;
	
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView labCategory( HttpServletRequest httpRequest ) {
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initLabCategory");
		 
		try {
			
			//BuckWaResponse response = labCategoryService.getAll();
			BuckWaRequest request = new BuckWaRequest();
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			BuckWaResponse 	response = labCategoryService.getAllByProjectId(request);
		 
			
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				List<LabCategory>  returnList = (List)response.getResObj("labCategoryList"); 
				mav.addObject("labCategoryList", returnList);				
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
		mav.setViewName("labCategoryCreate");
		LabCategory labCategory = new LabCategory();
		
		mav.addObject("labCategory", labCategory);	
		return mav;
	}	

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createPOST(@ModelAttribute LabCategory labCategory, BindingResult result,HttpServletRequest httpRequest) {	
		ModelAndView mav = new ModelAndView();
		try{			
			new LabCategoryValidator().validate(labCategory, result);			
			if (result.hasErrors()) {				
				mav.setViewName("labCategoryCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
			
				labCategory.setProjectId(projectUtil.getProjectIdFromSession(httpRequest));
				request.put("labCategory", labCategory);
				BuckWaResponse response = labCategoryService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					//BuckWaResponse response = labCategoryService.getAll();
					 request = new BuckWaRequest();
					request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
					 	response = labCategoryService.getAllByProjectId(request);
				 
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						List<LabCategory>  returnList = (List)response.getResObj("labCategoryList"); 
						mav.addObject("labCategoryList", returnList);				
					}else {
						mav.addObject("errorCode", response.getErrorCode()); 
					}
					mav.setViewName("initLabCategory");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("labCategoryCreate");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("labCategoryId") String labCategoryId,HttpServletRequest httpRequest) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("labCategoryEdit");
		BuckWaRequest request = new BuckWaRequest();
		request.put("labCategoryId", labCategoryId);	
		request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
		BuckWaResponse response = labCategoryService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			LabCategory labCategory = (LabCategory)response.getResObj("labCategory");				 	
			mav.addObject("labCategory", labCategory);				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}	
	
	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute LabCategory labCategory, BindingResult result,HttpServletRequest httpRequest ) {		
	
		ModelAndView mav = new ModelAndView();
		try{			 
			new LabCategoryValidator().validate(labCategory, result);			
			if (result.hasErrors()) {				 
				mav.setViewName("labCategoryEdit");
			}else {
		 
				BuckWaRequest request = new BuckWaRequest();
				request.put("labCategory", labCategory);
				BuckWaResponse response = labCategoryService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){					 				
					//BuckWaResponse response = labCategoryService.getAll();
					 request = new BuckWaRequest();
					request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
					 	response = labCategoryService.getAllByProjectId(request);
				 
						if(response.getStatus()==BuckWaConstants.SUCCESS){				
							List<LabCategory>  returnList = (List)response.getResObj("labCategoryList"); 
							mav.addObject("labCategoryList", returnList);				
						}else {
							mav.addObject("errorCode", response.getErrorCode()); 
						}
						mav.setViewName("initLabCategory");				
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("labCategoryEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}		
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("labCategoryId") String labCategoryId,HttpServletRequest httpRequest ) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initLabCategory");
		BuckWaRequest request = new BuckWaRequest();
		request.put("labCategoryId", labCategoryId);	
		BuckWaResponse response = labCategoryService.deleteById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			//BuckWaResponse response = labCategoryService.getAll();
			  request = new BuckWaRequest();
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			 	response = labCategoryService.getAllByProjectId(request);
		 
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					List<UseCase>  returnList = (List)response.getResObj("labCategoryList"); 
					mav.addObject("labCategoryList", returnList);				
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
				}			
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}	
	
}
