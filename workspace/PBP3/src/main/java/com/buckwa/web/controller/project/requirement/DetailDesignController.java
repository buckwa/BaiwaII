package com.buckwa.web.controller.project.requirement;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.ImagePath;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.DetailDesign;
import com.buckwa.domain.project.Module;
import com.buckwa.domain.validator.project.DetailDesignValidator;
import com.buckwa.domain.validator.webboard.ReplyMessageValidator;
import com.buckwa.domain.webboard.Message;
import com.buckwa.domain.webboard.Topic;
import com.buckwa.service.intf.project.DetailDesignService;
import com.buckwa.service.intf.project.ModuleService;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.service.intf.webboard.WebboardTopicService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.FileUtils;
import com.buckwa.util.project.ProjectConstant;
import com.buckwa.util.project.ProjectUtil;
import com.buckwa.web.controller.admin.RoleManagementController;

@Controller
@RequestMapping("/project/requirement/detailDesign")
@SessionAttributes(types = DetailDesign.class)
public class DetailDesignController { 
	private static Logger logger = LoggerFactory.getLogger(DetailDesignController.class);
	@Autowired
	private DetailDesignService detailDesignService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ProjectUtil projectUtil;
	
	@Autowired
	private WebboardTopicService  webboardTopicService;
	
	@Autowired
    private PathUtil pathUtil;
	
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView init(@RequestParam("moduleId") String moduleId,HttpServletRequest httpRequest ) {
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initDetailDesign"); 
		logger.info(" ############ init Detail Design ");
		try { 
			// Get All Module
			BuckWaRequest request = new BuckWaRequest();
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			request.put("moduleId", moduleId+"");
			BuckWaResponse response = moduleService.getByIdandDetailDesign(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 Module   moduleReturn = (Module)response.getResObj("module"); 
				 mav.addObject("module", moduleReturn);
			}
			
			// response = moduleService.getAll();
			 response = moduleService.getAllModuleByProjectIdCountDetailDesign(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				List<Module>  returnList = (List)response.getResObj("moduleList"); 
				mav.addObject("moduleList", returnList);	
				mav.addObject("totalCount", (Long)response.getResObj("totalCount"));
				httpRequest.getSession().setAttribute("moduleList", returnList);

			} 			
			 
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	
	@RequestMapping(value = "initAll.htm", method = RequestMethod.GET)
	public ModelAndView initAll(@RequestParam("moduleId") String moduleId
			,HttpServletRequest httpRequest ) {
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initAllDetailDesign"); 
		try {  
			BuckWaRequest request = new BuckWaRequest();	
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			BuckWaResponse response = detailDesignService.getAllByProjectId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 List<DetailDesign>   detailDesignList = (List<DetailDesign>)response.getResObj("detailDesignList"); 
				 mav.addObject("detailDesignList", detailDesignList);
				 
			}

			response = moduleService.getAllModuleByProjectIdCountDetailDesign(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					List<Module>  returnList = (List)response.getResObj("moduleList"); 
					mav.addObject("moduleList", returnList);	
					mav.addObject("totalCount", (Long)response.getResObj("totalCount"));
					httpRequest.getSession().setAttribute("moduleList", returnList);
 
				} 
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView createGET(HttpServletRequest httpRequest,@RequestParam("moduleId") String moduleId) {
		ModelAndView mav = new ModelAndView();
		logger.info("  ## In DetailDesignController.createGET() moduleId:"+moduleId);
		mav.setViewName("detailDesignCreate");
		try{			
			if(StringUtils.hasText(moduleId)){
				DetailDesign detailDesign = new DetailDesign();	
				detailDesign.setModuleId(new Long(moduleId));
				mav.addObject("detailDesign", detailDesign);					
			}else{
				mav.addObject("errorCode", "E015"); 
				mav.setViewName("initDetailDesign"); 
			}

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	
	@RequestMapping(value="createByUseCase.htm", method = RequestMethod.GET)
	public ModelAndView createByUseCase(HttpServletRequest httpRequest,@RequestParam("moduleId") String moduleId,@RequestParam("usecaseId") String usecaseId) {
		ModelAndView mav = new ModelAndView();
		logger.info("  ## In DetailDesignController.createGET() moduleId:"+moduleId);
		mav.setViewName("detailDesignCreate");
		try{					 
			 BuckWaRequest request = new BuckWaRequest();
			 request.put("moduleId", moduleId+"");
			 request.put("usecaseId", usecaseId+""); 
			 httpRequest.getSession().setAttribute("usecaseId", usecaseId);
			 BuckWaResponse response = detailDesignService.prepareDetailDesignByUseCase (request);
			 if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 DetailDesign   detailDesign = (DetailDesign)response.getResObj("detailDesign"); 
					 mav.addObject("detailDesign", detailDesign);
				} 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
 

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createPOST(@ModelAttribute DetailDesign detailDesign
			, BindingResult result,HttpServletRequest httpRequest) {	
		ModelAndView mav = new ModelAndView();
		logger.info("  ## In DetailDesignController.createPOST() detailDesign:"+BeanUtils.getBeanString(detailDesign));
		try{			
			new DetailDesignValidator().validate(detailDesign, result);			
			if (result.hasErrors()) {				
				mav.setViewName("detailDesignCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
			
				detailDesign.setProjectId(projectUtil.getProjectIdFromSession(httpRequest));
				//detailDesign.setModuleId(projectUtil.getModuleIdFromSession(httpRequest));
				detailDesign.setUsecaseId(projectUtil.getUsecaseIdFromSession(httpRequest));
				
				request.put("detailDesign", detailDesign);
				BuckWaResponse response = detailDesignService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
					request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
					request.put("moduleId", detailDesign.getModuleId()+"");
					 response = moduleService.getByIdandDetailDesign(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						 Module   moduleReturn = (Module)response.getResObj("module"); 
						 mav.addObject("module", moduleReturn);
					}
					
					/*
					 response = detailDesignService.getAll();
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						List<DetailDesign>  returnList = (List)response.getResObj("detailDesignList"); 
						mav.addObject("detailDesignList", returnList);				
					}else {
						mav.addObject("errorCode", response.getErrorCode()); 
					}
					
					*/
					
					mav.setViewName("initDetailDesign");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("detailDesignCreate");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("detailDesignId") String detailDesignId) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("detailDesignEdit");
		BuckWaRequest request = new BuckWaRequest();
		request.put("detailDesignId", detailDesignId);	
		BuckWaResponse response = detailDesignService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			DetailDesign detailDesign = (DetailDesign)response.getResObj("detailDesign");				 	
			mav.addObject("detailDesign", detailDesign);				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute DetailDesign detailDesign
			, BindingResult result,HttpServletRequest httpRequest) {		
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("detailDesignEdit");
		try{			 
			new DetailDesignValidator().validate(detailDesign, result);			
			if (result.hasErrors()) {				 
				 
			}else {
		 
				BuckWaRequest request = new BuckWaRequest();
				request.put("detailDesign", detailDesign);
				BuckWaResponse response = detailDesignService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					 mav.addObject("successCode", response.getSuccessCode()); 
					 request = new BuckWaRequest();	 
					 logger.info(" ######### submitEdit detailDesign.getModuleId():"+detailDesign.getModuleId());
					 request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
					request.put("moduleId", detailDesign.getModuleId()+"");
					 response = moduleService.getByIdandDetailDesign(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						 Module   moduleReturn = (Module)response.getResObj("module"); 
						 mav.addObject("module", moduleReturn);
					}	
						 				
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
	public ModelAndView delete(@RequestParam("detailDesignId") String detailDesignId
			,@RequestParam("moduleId") String moduleId,HttpServletRequest httpRequest) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initDetailDesign");
		BuckWaRequest request = new BuckWaRequest();
		request.put("detailDesignId", detailDesignId);	
		BuckWaResponse response = detailDesignService.deleteById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){	 
				if(response.getStatus()==BuckWaConstants.SUCCESS){	  
					request.put("moduleId", moduleId+"");
					request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
					 response = moduleService.getByIdandDetailDesign(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						 Module   moduleReturn = (Module)response.getResObj("module"); 
						 mav.addObject("module", moduleReturn);
					}					 
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
				}			
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}			
	
	@RequestMapping(value="uploadScreen.htm")
	public ModelAndView uploadScreen(@ModelAttribute DetailDesign detailDesign, BindingResult result  ) {
		logger.info(" ### uploadScreen ## detailDesign:"+BeanUtils.getBeanString(detailDesign));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("detailDesignEdit");
		InputStream inputStream = null;	 
		try{			 			 
			String fileName = null;
			MultipartFile originalfile = detailDesign.getScreenFileData();
			if (originalfile!=null&&originalfile.getSize() > 0) {
				inputStream = originalfile.getInputStream();
				if (originalfile.getSize() > pathUtil.getMaximumImageUploadSize()) {
					logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumImageUploadSize());					 
					mav.addObject("errorCode", "E013"); 
					return mav;
				} 					
				
				String newFileName =   originalfile.getOriginalFilename() ;
			    String relativePath = pathUtil.getRelativeDetailDesignImagePath (newFileName,detailDesign.getDetailDesignId()+"");
			    String fullPath = pathUtil.getFullPathByRelativePath(relativePath);		
			    
			    FileUtils.saveFileToDirectory(originalfile,fullPath);  
			    
				ImagePath newImagePath = new ImagePath();
				newImagePath.setImagePath(relativePath);
				newImagePath.setImageType(ProjectConstant.ARTIFACE_TYPE_DETAIL_DESIGN);
				newImagePath.setImageCode(detailDesign.getProjectId()+"");
				 
				logger.info(" detailDesign.getScreenImagePathList():"+detailDesign.getScreenImagePathList());
				detailDesign.getScreenImagePathList().add(newImagePath);				
				BuckWaRequest request = new BuckWaRequest();
				request.put("detailDesign", detailDesign );	
				BuckWaResponse response = detailDesignService.uploadScreen(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					request.put("detailDesignId", detailDesign.getDetailDesignId());	
					 response = detailDesignService.getById(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){		
						 detailDesign = (DetailDesign)response.getResObj("detailDesign");				 	
						mav.addObject("detailDesign", detailDesign);				
					}else {	
						mav.addObject("errorCode", response.getErrorCode()); 
					}
				}else {			 
					mav.addObject("errorCode", response.getErrorCode()); 
				}				 
			}else{					 
				mav.addObject("errorCode", "E012"); 
				return mav;					
			}			 			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}		
		return mav;
	}
	
	@RequestMapping(value="uploadFile.htm")
	public ModelAndView uploadFile(@ModelAttribute DetailDesign detailDesign ) {
		logger.info(" ### uploadFile ## detailDesign:"+BeanUtils.getBeanString(detailDesign));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("detailDesignEdit");
		InputStream inputStream = null;	 
		try{			 			 
			String fileName = null;
			MultipartFile originalfile = detailDesign.getFileData();	
			if (originalfile!=null&&originalfile.getSize() > 0) {
				inputStream = originalfile.getInputStream();
				if (originalfile.getSize() > pathUtil.getMaximumUploadSize() ) {
					logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumUploadSize());					 
					mav.addObject("errorCode", "E013"); 
					return mav;
				} 							 
				
				String newFileName =   originalfile.getOriginalFilename() ;
			    String relativePath = pathUtil.getRelativeVisionImagePath(newFileName,detailDesign.getDetailDesignId()+"");
			    String fullPath = pathUtil.getFullPathByRelativePath(relativePath);		
			    logger.info(" newFileName:"+newFileName+"\n relativePath:"+relativePath+" \n fullPath:"+fullPath);
				FileUtils.saveFileToDirectory(originalfile,fullPath);  
				ImagePath newImagePath = new ImagePath();
				newImagePath.setImagePath(relativePath);
				newImagePath.setFileName(newFileName);
				newImagePath.setImageType(ProjectConstant.ARTIFACE_TYPE_DETAIL_DESIGN);
				newImagePath.setImageCode(detailDesign.getDetailDesignId()+""); 
				newImagePath.setProjectId(detailDesign.getDetailDesignId()+""); 
				detailDesign.getFilePathList().add(newImagePath);				
				BuckWaRequest request = new BuckWaRequest(); 
				request.put("detailDesign", detailDesign );
				BuckWaResponse response = detailDesignService.updateFilePath(request);				
				if(response.getStatus()==BuckWaConstants.SUCCESS){					 	
					request.put("detailDesignId",detailDesign.getDetailDesignId());	
					 response = detailDesignService.getById(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){		
						detailDesign = (DetailDesign)response.getResObj("detailDesign");				 	
						mav.addObject("detailDesign", detailDesign);				
					}else {	
						mav.addObject("errorCode", response.getErrorCode()); 
					}	
				}
				
			}else{					 
				mav.addObject("errorCode", "E012"); 
				return mav;					
			}			 			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}		
		return mav;
	}
	
	
	@RequestMapping(value="detail.htm", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam("detailDesignId") String detailDesignId,HttpServletRequest httpRequest ) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("detailDesignDetail");
		BuckWaRequest request = new BuckWaRequest();
		request.put("detailDesignId", detailDesignId);	
		httpRequest.getSession().setAttribute("detailDesignId", detailDesignId);
		BuckWaResponse response = detailDesignService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			DetailDesign detailDesign = (DetailDesign)response.getResObj("detailDesign");				 	
			mav.addObject("detailDesign", detailDesign);				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		
		request.put("artifaceId", detailDesignId);
		request.put("artifaceType", ProjectConstant.ARTIFACE_TYPE_DETAIL_DESIGN);	 
		 response = webboardTopicService.getTopicByArtifaceId(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Topic topic  = (Topic)response.getResObj("topic");				 	
			mav.addObject("topic", topic);	
			httpRequest.getSession().setAttribute("topicId", topic.getTopicId());
			
			
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			//request.put("moduleId", moduleId+"");
			response = moduleService.getAllModuleByProjectIdCountDetailDesign(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					List<Module>  returnList = (List)response.getResObj("moduleList"); 
					mav.addObject("moduleList", returnList);	
					mav.addObject("totalCount", (Long)response.getResObj("totalCount"));
					httpRequest.getSession().setAttribute("moduleList", returnList);
 
				} 
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}
  
		return mav;
	}	
	
	
	@RequestMapping(value="replyMessage.htm", method = RequestMethod.POST)
	public ModelAndView replyMessage(@ModelAttribute Topic topic, BindingResult result,HttpServletRequest httpRequest ) {	
		ModelAndView mav = new ModelAndView();
		try{			
			logger.info(" createTopic topic xx: "+BeanUtils.getBeanString(topic));
			BuckWaRequest request = new BuckWaRequest();
			new ReplyMessageValidator().validate(topic, result);			
			if (result.hasErrors()) {				
				String detailDesignId = (String)httpRequest.getSession().getAttribute("detailDesignId");
				request.put("artifaceId", detailDesignId);
				request.put("artifaceType", ProjectConstant.ARTIFACE_TYPE_DETAIL_DESIGN);	 
				BuckWaResponse response  = webboardTopicService.getTopicByArtifaceId(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
					 topic  = (Topic)response.getResObj("topic");				 	
					mav.addObject("topic", topic);	
					httpRequest.getSession().setAttribute("topicId", topic.getTopicId()); 
				} 
				mav.setViewName("detailDesignDetail");
			}else {					
			
				topic.setStatus("1");
				Long topicId = (Long)httpRequest.getSession().getAttribute("topicId");
				Message newMessage = new Message();
				newMessage.setMessageDetail(topic.getReplyMessage());
				newMessage.setTopicId(topicId);
				newMessage.setStatus("1");				
				newMessage.setCreateBy(BuckWaUtils.getUserNameFromContext());
				request.put("message", newMessage);
				logger.info(" replyMessage newMessage:"+BeanUtils.getBeanString(newMessage));
				BuckWaResponse response = webboardTopicService.replyMessage(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
					topic = (Topic)response.getResObj("topic");
					mav.addObject("topic", topic);
					topic.setReplyMessage("");
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("detailDesignDetail");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("detailDesignDetail");
				}				
			}	
			
			

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
}
