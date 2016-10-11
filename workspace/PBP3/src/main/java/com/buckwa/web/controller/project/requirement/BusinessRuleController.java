package com.buckwa.web.controller.project.requirement;

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
import com.buckwa.domain.project.BusinessRule;
import com.buckwa.domain.project.Module;
import com.buckwa.domain.project.UseCase;
import com.buckwa.domain.validator.project.BusinessRuleValidator;
import com.buckwa.service.intf.project.BusinessRuleService;
import com.buckwa.service.intf.project.ModuleService;
import com.buckwa.service.intf.project.UseCaseService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.project.ProjectUtil;

@Controller
@RequestMapping("/project/requirement/businessRule")
@SessionAttributes(types = BusinessRule.class)
public class BusinessRuleController { 
 
	@Autowired
	private BusinessRuleService businessRuleService;
	
	@Autowired
	private UseCaseService useCaseService;
	
	@Autowired
	private ModuleService moduleService;
	
	
	@Autowired
	private ProjectUtil projectUtil;
	
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView businessRule( HttpServletRequest httpRequest) {
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initBusinessRule");
		 
		try {
			
			//BuckWaResponse response = businessRuleService.getAll();
			BuckWaRequest request = new BuckWaRequest();
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			BuckWaResponse response = businessRuleService.getAllByProjectId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				List<BusinessRule>  returnList = (List)response.getResObj("businessRuleList"); 
				mav.addObject("businessRuleList", returnList);				
			}else {
				mav.addObject("errorCode", response.getErrorCode()); 
			}
 
			
			mav.addObject("projectName",  projectUtil.getProjectNameFromSession(httpRequest));	
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView createGET(HttpServletRequest httpRequest) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("businessRuleCreate");
		BusinessRule businessRule = new BusinessRule();
		
		mav.addObject("businessRule", businessRule);	
		return mav;
	}	

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createPOST(@ModelAttribute BusinessRule businessRule, BindingResult result,HttpServletRequest httpRequest) {	
		ModelAndView mav = new ModelAndView();
		try{			
			new BusinessRuleValidator().validate(businessRule, result);			
			if (result.hasErrors()) {				
				mav.setViewName("businessRuleCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
			
				businessRule.setProjectId(projectUtil.getProjectIdFromSession(httpRequest));
				request.put("businessRule", businessRule);
				BuckWaResponse response = businessRuleService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					// response = businessRuleService.getAll();
						request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
						 response = businessRuleService.getAllByProjectId(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						List<BusinessRule>  returnList = (List)response.getResObj("businessRuleList"); 
						mav.addObject("businessRuleList", returnList);				
					}else {
						mav.addObject("errorCode", response.getErrorCode()); 
					}
					mav.setViewName("initBusinessRule");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("businessRuleCreate");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("businessRuleId") String businessRuleId) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("businessRuleEdit");
		BuckWaRequest request = new BuckWaRequest();
		request.put("businessRuleId", businessRuleId);	
		BuckWaResponse response = businessRuleService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			BusinessRule businessRule = (BusinessRule)response.getResObj("businessRule");				 	
			mav.addObject("businessRule", businessRule);				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}	
	
	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute BusinessRule businessRule, BindingResult result,HttpServletRequest httpRequest) {		
	
		ModelAndView mav = new ModelAndView();
		try{			 
			new BusinessRuleValidator().validate(businessRule, result);			
			if (result.hasErrors()) {				 
				mav.setViewName("businessRuleEdit");
			}else {
		 
				BuckWaRequest request = new BuckWaRequest();
				request.put("businessRule", businessRule);
				BuckWaResponse response = businessRuleService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){					 				
					// response = businessRuleService.getAll();
						request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
						 response = businessRuleService.getAllByProjectId(request);
						if(response.getStatus()==BuckWaConstants.SUCCESS){				
							List<BusinessRule>  returnList = (List)response.getResObj("businessRuleList"); 
							mav.addObject("businessRuleList", returnList);				
						}else {
							mav.addObject("errorCode", response.getErrorCode()); 
						}
						mav.setViewName("initBusinessRule");				
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("businessRuleEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}		
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("businessRuleId") String businessRuleId,HttpServletRequest httpRequest) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initBusinessRule");
		BuckWaRequest request = new BuckWaRequest();
		request.put("businessRuleId", businessRuleId);	
		BuckWaResponse response = businessRuleService.deleteById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			 //response = businessRuleService.getAll();
				request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
				 response = businessRuleService.getAllByProjectId(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					List<UseCase>  returnList = (List)response.getResObj("businessRuleList"); 
					mav.addObject("businessRuleList", returnList);				
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
				}			
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}	
	
}
