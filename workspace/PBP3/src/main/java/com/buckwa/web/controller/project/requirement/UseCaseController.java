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
import com.buckwa.domain.project.Module;
import com.buckwa.domain.project.UseCase;
import com.buckwa.domain.validator.project.UseCaseValidator;
import com.buckwa.domain.validator.webboard.ReplyMessageValidator;
import com.buckwa.domain.webboard.Message;
import com.buckwa.domain.webboard.Topic;
import com.buckwa.service.intf.project.ModuleService;
import com.buckwa.service.intf.project.UseCaseService;
import com.buckwa.service.intf.project.VisionService;
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
@RequestMapping("/project/requirement/useCase")
@SessionAttributes(types = UseCase.class)
public class UseCaseController { 
	private static Logger logger = LoggerFactory.getLogger(UseCaseController.class);
	@Autowired
	private UseCaseService useCaseService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ProjectUtil projectUtil;
	
	@Autowired
    private PathUtil pathUtil;
	
	@Autowired
	private VisionService visionService;
	
	@Autowired
	private WebboardTopicService  webboardTopicService;
	
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView init(@RequestParam("moduleId") String moduleId
			,HttpServletRequest httpRequest ) {
		logger.info(" ## UseCaseController.init moduleId:"+moduleId);
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initUseCase"); 
		try { 
			// Get All Module
			logger.info(" ## UseCaseController.init ## ");
			BuckWaRequest request = new BuckWaRequest();	
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			request.put("moduleId", moduleId+"");
			BuckWaResponse response = moduleService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 Module   moduleReturn = (Module)response.getResObj("module"); 
				 mav.addObject("module", moduleReturn);
			}
		 
			//response = moduleService.getAllByProjectId(request);
			response = moduleService.getAllModuleByProjectIdCountUseCase(request);
			
			 if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 List<Module>  returnList = (List)response.getResObj("moduleList"); 
				 mav.addObject("moduleList", returnList);	
				mav.addObject("totalCount", (Long)response.getResObj("totalCount"));
				httpRequest.getSession().setAttribute("totalCount", (Long)response.getResObj("totalCount"));
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
		logger.info(" ## UseCaseController.initAll moduleId:"+moduleId);
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initAllUseCase"); 
		try {  
			BuckWaRequest request = new BuckWaRequest();	
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			BuckWaResponse response = useCaseService.getAllByProjectId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 List<UseCase>   useCaseList = (List<UseCase>)response.getResObj("useCaseList"); 
				 mav.addObject("useCaseList", useCaseList);
			}

			//response = moduleService.getAllByProjectId(request);
			response = moduleService.getAllModuleByProjectIdCountUseCase(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					List<Module>  returnList = (List)response.getResObj("moduleList"); 
					mav.addObject("moduleList", returnList);	
					mav.addObject("totalCount", (Long)response.getResObj("totalCount"));
					httpRequest.getSession().setAttribute("totalCount", (Long)response.getResObj("totalCount"));
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
		logger.info("  ## In UseCaseController.createGET() moduleId:"+moduleId);
		mav.setViewName("useCaseCreate");
		try{
			if(StringUtils.hasText(moduleId)){
			UseCase useCase = new UseCase();	
			useCase.setModuleId(new Long(moduleId));
			mav.addObject("useCase", useCase);	
			}else{
				mav.addObject("errorCode", "E015"); 
				mav.setViewName("initUseCase"); 
			} 

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createPOST(@ModelAttribute UseCase useCase, 
			BindingResult result,HttpServletRequest httpRequest) {	
		ModelAndView mav = new ModelAndView();
		logger.info("  ## In UseCaseController.createPOST() useCase:"+BeanUtils.getBeanString(useCase));
		try{			
			new UseCaseValidator().validate(useCase, result);			
			if (result.hasErrors()) {				
				mav.setViewName("useCaseCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
			
				useCase.setProjectId(projectUtil.getProjectIdFromSession(httpRequest));
			 
				request.put("useCase", useCase);
				BuckWaResponse response = useCaseService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	 
					request.put("moduleId", useCase.getModuleId()+"");
					request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
					 response = moduleService.getById(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						 Module   moduleReturn = (Module)response.getResObj("module"); 
						 mav.addObject("module", moduleReturn);
					} 
					mav.setViewName("initUseCase");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("useCaseCreate");
				}		
				
				response = moduleService.getAllModuleByProjectIdCountUseCase(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					List<Module>  returnList = (List)response.getResObj("moduleList"); 
					mav.addObject("moduleList", returnList);	
					mav.addObject("totalCount", (Long)response.getResObj("totalCount"));
					httpRequest.getSession().setAttribute("totalCount", (Long)response.getResObj("totalCount"));
					httpRequest.getSession().setAttribute("moduleList", returnList);
 
				} 
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("useCaseId") String useCaseId) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("useCaseEdit");
		BuckWaRequest request = new BuckWaRequest();
		request.put("useCaseId", useCaseId);	
		BuckWaResponse response = useCaseService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			UseCase useCase = (UseCase)response.getResObj("useCase");				 	
			mav.addObject("useCase", useCase);				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}	
	
	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute UseCase useCase
			, BindingResult result,HttpServletRequest httpRequest) {		
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("useCaseEdit"); 
		try{			 
			new UseCaseValidator().validate(useCase, result);			
			if (result.hasErrors()) {				 
				mav.setViewName("useCaseEdit");
			}else {
		 
				BuckWaRequest request = new BuckWaRequest();
				request.put("useCase", useCase);
				BuckWaResponse response = useCaseService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					 mav.addObject("successCode", response.getSuccessCode()); 
					 request = new BuckWaRequest();	 
					 logger.info(" ######### submitEdit useCase.getModuleId():"+useCase.getModuleId());
					request.put("moduleId", useCase.getModuleId()+"");
					request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
					 response = moduleService.getById(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						 Module   moduleReturn = (Module)response.getResObj("module"); 
						 mav.addObject("module", moduleReturn);
					}			
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("useCaseEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}		
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("useCaseId") String useCaseId
			,@RequestParam("moduleId") String moduleId,HttpServletRequest httpRequest) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initUseCase");
		BuckWaRequest request = new BuckWaRequest();
		request.put("useCaseId", useCaseId);	
		BuckWaResponse response = useCaseService.deleteById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		 
				if(response.getStatus()==BuckWaConstants.SUCCESS){	 
					request.put("moduleId", moduleId+"");
					request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
					 response = moduleService.getById(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						 Module   moduleReturn = (Module)response.getResObj("module"); 
						 mav.addObject("module", moduleReturn);
					}
					
					request.put("moduleId", moduleId+"");
					request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
					response = moduleService.getAllModuleByProjectIdCountUseCase(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						List<Module>  returnList = (List)response.getResObj("moduleList"); 
						mav.addObject("moduleList", returnList);	
						mav.addObject("totalCount", (Long)response.getResObj("totalCount"));
						httpRequest.getSession().setAttribute("totalCount", (Long)response.getResObj("totalCount"));
						httpRequest.getSession().setAttribute("moduleList", returnList);
	 
					} 
					
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
				}			
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}	
	
	
	@RequestMapping(value="uploadFile.htm")
	public ModelAndView uploadFile(@ModelAttribute UseCase useCase, BindingResult result  ,HttpServletRequest httpRequest  ) {
		logger.info(" ### uploadFile ## useCase:"+BeanUtils.getBeanString(useCase));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("useCaseEdit");
		InputStream inputStream = null;	 
		try{			 			 
			String fileName = null;
			MultipartFile originalfile = useCase.getFileData();	
			if (originalfile!=null&&originalfile.getSize() > 0) {
				inputStream = originalfile.getInputStream();
				if (originalfile.getSize() > pathUtil.getMaximumUploadSize() ) {
					logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumUploadSize());					 
					mav.addObject("errorCode", "E013"); 
					return mav;
				} 							 
				
				String newFileName =   originalfile.getOriginalFilename() ;
			    String relativePath = pathUtil.getRelativeVisionImagePath(newFileName,useCase.getUsecaseId()+"");
			    String fullPath = pathUtil.getFullPathByRelativePath(relativePath);		
			    logger.info(" newFileName:"+newFileName+"\n relativePath:"+relativePath+" \n fullPath:"+fullPath);
				FileUtils.saveFileToDirectory(originalfile,fullPath);  
				ImagePath newImagePath = new ImagePath();
				newImagePath.setImagePath(relativePath);
				newImagePath.setFileName(newFileName);
				newImagePath.setImageType(ProjectConstant.ARTIFACE_TYPE_USE_CASE);
				newImagePath.setImageCode(useCase.getUsecaseId()+""); 
				newImagePath.setProjectId(useCase.getProjectId()+""); 
				useCase.getFilePathList().add(newImagePath);				
				BuckWaRequest request = new BuckWaRequest(); 
				request.put("useCase", useCase );
				BuckWaResponse response = useCaseService.updateFilePath(request);				
				if(response.getStatus()==BuckWaConstants.SUCCESS){					 	
					request.put("useCaseId", useCase.getUsecaseId());	
					 response = useCaseService.getById(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){		
						 useCase = (UseCase)response.getResObj("useCase");				 	
						mav.addObject("useCase", useCase);				
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
	
	
	@RequestMapping(value="removeFile.htm")
	public ModelAndView removeFile(@RequestParam("fileId") String fileId,@RequestParam("usecaseId") String usecaseId ,HttpServletRequest httpRequest ) {
		logger.info(" ### removeFile fileId :"+fileId+" usecaseId: "+usecaseId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("useCaseEdit");  
		try{	 
			
			ImagePath imagePath = new ImagePath();
			imagePath.setImagePathId(new Long(fileId));	 
			imagePath.setImageType(ProjectConstant.ARTIFACE_TYPE_USE_CASE);			
			BuckWaRequest request = new BuckWaRequest();
			request.put("imagePath", imagePath);
			BuckWaResponse response = visionService.removeImage(request); 		 
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				request.put("useCaseId", usecaseId);	
				 response = useCaseService.getById(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
					UseCase useCase = (UseCase)response.getResObj("useCase");				 	
					mav.addObject("useCase", useCase);				
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
	
	@RequestMapping(value="detail.htm", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam("useCaseId") String useCaseId,HttpServletRequest httpRequest ) {
		logger.info(" ## UseCaseController.detail useCaseId:"+useCaseId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("useCaseDetail");
		BuckWaRequest request = new BuckWaRequest();
		request.put("useCaseId", useCaseId);	
		httpRequest.getSession().setAttribute("usecaseId", useCaseId);
		BuckWaResponse response = useCaseService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			UseCase useCase = (UseCase)response.getResObj("useCase");				 	
			mav.addObject("usecase", useCase);				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 		
		request.put("artifaceId", useCaseId);
		request.put("artifaceType", ProjectConstant.ARTIFACE_TYPE_USE_CASE);	 
		 response = webboardTopicService.getTopicByArtifaceId(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Topic topic  = (Topic)response.getResObj("topic");				 	
			mav.addObject("topic", topic);	
			httpRequest.getSession().setAttribute("topicId", topic.getTopicId());
			
			
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			response = moduleService.getAllModuleByProjectIdCountUseCase(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				List<Module>  returnList = (List)response.getResObj("moduleList"); 
				mav.addObject("moduleList", returnList);	
				mav.addObject("totalCount", (Long)response.getResObj("totalCount"));
				httpRequest.getSession().setAttribute("totalCount", (Long)response.getResObj("totalCount"));
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
				String usecaseId = (String)httpRequest.getSession().getAttribute("usecaseId");
				request.put("artifaceId", usecaseId);
				request.put("artifaceType", ProjectConstant.ARTIFACE_TYPE_USE_CASE);	 
				BuckWaResponse response  = webboardTopicService.getTopicByArtifaceId(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
					 topic  = (Topic)response.getResObj("topic");				 	
					mav.addObject("topic", topic);	
					httpRequest.getSession().setAttribute("topicId", topic.getTopicId());
					
				}else {	
					mav.addObject("errorCode", response.getErrorCode()); 
				}
				mav.setViewName("useCaseDetail");
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
					mav.setViewName("useCaseDetail");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("useCaseDetail");
				}						
			}		
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
}
