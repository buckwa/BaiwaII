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
import com.buckwa.domain.project.Actor;
import com.buckwa.domain.project.UseCase;
import com.buckwa.domain.validator.project.ActorValidator;
import com.buckwa.service.intf.project.ActorService;
import com.buckwa.service.intf.project.ModuleService;
import com.buckwa.service.intf.project.UseCaseService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.project.ProjectUtil;

@Controller
@RequestMapping("/project/requirement/actor")
@SessionAttributes(types = Actor.class)
public class ActorController { 
 
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UseCaseService useCaseService;
	
	@Autowired
	private ModuleService moduleService;
	
	
	@Autowired
	private ProjectUtil projectUtil;
	
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView actor( HttpServletRequest httpRequest) {
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initActor");
		 
		try {
			
			//BuckWaResponse response = actorService.getAll();
			BuckWaRequest request = new BuckWaRequest();
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			BuckWaResponse response = actorService.getAllByProjectId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				List<Actor>  returnList = (List)response.getResObj("actorList"); 
				mav.addObject("actorList", returnList);				
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
		mav.setViewName("actorCreate");
		Actor actor = new Actor();
		
		mav.addObject("actor", actor);	
		return mav;
	}	

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createPOST(@ModelAttribute Actor actor, BindingResult result,HttpServletRequest httpRequest) {	
		ModelAndView mav = new ModelAndView();
		try{			
			new ActorValidator().validate(actor, result);			
			if (result.hasErrors()) {				
				mav.setViewName("actorCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
			
				actor.setProjectId(projectUtil.getProjectIdFromSession(httpRequest));
				request.put("actor", actor);
				BuckWaResponse response = actorService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					// response = actorService.getAll();
						request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
						 response = actorService.getAllByProjectId(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						List<Actor>  returnList = (List)response.getResObj("actorList"); 
						mav.addObject("actorList", returnList);				
					}else {
						mav.addObject("errorCode", response.getErrorCode()); 
					}
					mav.setViewName("initActor");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("actorCreate");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("actorId") String actorId) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("actorEdit");
		BuckWaRequest request = new BuckWaRequest();
		request.put("actorId", actorId);	
		BuckWaResponse response = actorService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Actor actor = (Actor)response.getResObj("actor");				 	
			mav.addObject("actor", actor);				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}	
	
	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Actor actor, BindingResult result,HttpServletRequest httpRequest) {		
	
		ModelAndView mav = new ModelAndView();
		try{			 
			new ActorValidator().validate(actor, result);			
			if (result.hasErrors()) {				 
				mav.setViewName("actorEdit");
			}else {
		 
				BuckWaRequest request = new BuckWaRequest();
				request.put("actor", actor);
				BuckWaResponse response = actorService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){					 				
					// response = actorService.getAll();
						request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
						 response = actorService.getAllByProjectId(request);
						if(response.getStatus()==BuckWaConstants.SUCCESS){				
							List<Actor>  returnList = (List)response.getResObj("actorList"); 
							mav.addObject("actorList", returnList);				
						}else {
							mav.addObject("errorCode", response.getErrorCode()); 
						}
						mav.setViewName("initActor");				
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("actorEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}		
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("actorId") String actorId,HttpServletRequest httpRequest) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initActor");
		BuckWaRequest request = new BuckWaRequest();
		request.put("actorId", actorId);	
		BuckWaResponse response = actorService.deleteById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			 //response = actorService.getAll();
				request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
				 response = actorService.getAllByProjectId(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					List<UseCase>  returnList = (List)response.getResObj("actorList"); 
					mav.addObject("actorList", returnList);				
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
				}			
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}	
	
}
