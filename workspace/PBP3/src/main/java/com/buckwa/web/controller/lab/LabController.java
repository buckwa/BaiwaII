package com.buckwa.web.controller.lab;

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
import com.buckwa.domain.project.Lab;
import com.buckwa.domain.project.LabCategory;
import com.buckwa.domain.validator.project.LabValidator;
import com.buckwa.domain.validator.webboard.ReplyMessageValidator;
import com.buckwa.domain.webboard.Message;
import com.buckwa.domain.webboard.Topic;
import com.buckwa.service.intf.project.LabService;
import com.buckwa.service.intf.project.LabCategoryService;
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
@RequestMapping("/lab")
@SessionAttributes(types = Lab.class)
public class LabController { 
	private static Logger logger = LoggerFactory.getLogger(LabController.class);
	@Autowired
	private LabService labService;
	
	@Autowired
	private LabCategoryService labCategoryService;
	
 
	
	@Autowired
    private PathUtil pathUtil;
	
	@Autowired
	private VisionService visionService;
	
	@Autowired
	private WebboardTopicService  webboardTopicService;
	
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView init(@RequestParam("labCategoryId") String labCategoryId
			,HttpServletRequest httpRequest ) {
		logger.info(" ## LabController.init labCategoryId:"+labCategoryId);
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initLab"); 
		try { 
			// Get All LabCategory
			logger.info(" ## LabController.init ## ");
			BuckWaRequest request = new BuckWaRequest();	 
			request.put("labCategoryId", labCategoryId+"");
			BuckWaResponse response = labCategoryService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 LabCategory   labCategoryReturn = (LabCategory)response.getResObj("labCategory"); 
				 mav.addObject("labCategory", labCategoryReturn);
			}
		 
			//response = labCategoryService.getAllByProjectId(request);
			response = labCategoryService.getAllLabCategoryByProjectIdCountLab(request);
			
			 if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 List<LabCategory>  returnList = (List)response.getResObj("labCategoryList"); 
				 mav.addObject("labCategoryList", returnList);	
					mav.addObject("totalCount", (Long)response.getResObj("totalCount"));
				 httpRequest.getSession().setAttribute("labCategoryList", returnList); 
			 } 
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	@RequestMapping(value = "initAll.htm", method = RequestMethod.GET)
	public ModelAndView initAll(@RequestParam("labCategoryId") String labCategoryId
			,HttpServletRequest httpRequest ) {
		logger.info(" ## LabController.initAll labCategoryId:"+labCategoryId);
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initAllLab"); 
		try {  
			BuckWaRequest request = new BuckWaRequest();	
		 
			BuckWaResponse response = labService.getAllByProjectId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 List<Lab>   labList = (List<Lab>)response.getResObj("labList"); 
				 mav.addObject("labList", labList);
			}

			//response = labCategoryService.getAllByProjectId(request);
			response = labCategoryService.getAllLabCategoryByProjectIdCountLab(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					List<LabCategory>  returnList = (List)response.getResObj("labCategoryList"); 
					mav.addObject("labCategoryList", returnList);	
					mav.addObject("totalCount", (Long)response.getResObj("totalCount"));
					httpRequest.getSession().setAttribute("labCategoryList", returnList);
 
				} 
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	
	@RequestMapping(value = "initDefaultLabCat.htm", method = RequestMethod.GET)
	public ModelAndView initDefaultLabCat( HttpServletRequest httpRequest ) {
		logger.info(" ## LabController.initDefaultLabCat labCategoryId:");
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initAllLab"); 
 
		try {  
			BuckWaRequest request = new BuckWaRequest();	
		 
			BuckWaResponse response = labService.getAllByProjectId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 List<Lab>   labList = (List<Lab>)response.getResObj("labList"); 
				 mav.addObject("labList", labList);
			}

			//response = labCategoryService.getAllByProjectId(request);
			response = labCategoryService.getAllLabCategoryByProjectIdCountLab(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					List<LabCategory>  returnList = (List)response.getResObj("labCategoryList"); 
					mav.addObject("labCategoryList", returnList);	
					mav.addObject("totalCount", (Long)response.getResObj("totalCount"));
					httpRequest.getSession().setAttribute("labCategoryList", returnList);
 
				} 
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		logger.info(" ### initDefaultLabCat befor return mav:"+mav);
		return mav;
	}
	
	
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView createGET(HttpServletRequest httpRequest,@RequestParam("labCategoryId") String labCategoryId) {
		ModelAndView mav = new ModelAndView();
		logger.info("  ## In LabController.createGET() labCategoryId:"+labCategoryId);
		mav.setViewName("labCreate");
		try{
			if(StringUtils.hasText(labCategoryId)){
			Lab lab = new Lab();	
			lab.setLabCategoryId(new Long(labCategoryId));
			mav.addObject("lab", lab);	
			}else{
				mav.addObject("errorCode", "E015"); 
				mav.setViewName("initLab"); 
			} 

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createPOST(@ModelAttribute Lab lab, 
			BindingResult result,HttpServletRequest httpRequest) {	
		ModelAndView mav = new ModelAndView();
		logger.info("  ## In LabController.createPOST() lab:"+BeanUtils.getBeanString(lab));
		try{			
			new LabValidator().validate(lab, result);			
			if (result.hasErrors()) {				
				mav.setViewName("labCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
			
	 
			 
				request.put("lab", lab);
				BuckWaResponse response = labService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	 
					request.put("labCategoryId", lab.getLabCategoryId()+"");
				 
					 response = labCategoryService.getById(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						 LabCategory   labCategoryReturn = (LabCategory)response.getResObj("labCategory"); 
						 mav.addObject("labCategory", labCategoryReturn);
					} 
					mav.setViewName("initLab");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("labCreate");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("labId") String labId) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("labEdit");
		BuckWaRequest request = new BuckWaRequest();
		request.put("labId", labId);	
		BuckWaResponse response = labService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Lab lab = (Lab)response.getResObj("lab");				 	
			mav.addObject("lab", lab);				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		return mav;
	}	
	
	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Lab lab
			, BindingResult result,HttpServletRequest httpRequest) {		
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initLab"); 
		try{			 
			new LabValidator().validate(lab, result);			
			if (result.hasErrors()) {				 
				mav.setViewName("labEdit");
			}else {
		 
				BuckWaRequest request = new BuckWaRequest();
				request.put("lab", lab);
				BuckWaResponse response = labService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){					 				
					 request = new BuckWaRequest();	 
					 logger.info(" ######### submitEdit lab.getLabCategoryId():"+lab.getLabCategoryId());
					request.put("labCategoryId", lab.getLabCategoryId()+"");
					 
					 response = labCategoryService.getById(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						 LabCategory   labCategoryReturn = (LabCategory)response.getResObj("labCategory"); 
						 mav.addObject("labCategory", labCategoryReturn);
					}			
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("labEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}		
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("labId") String labId
			,@RequestParam("labCategoryId") String labCategoryId,HttpServletRequest httpRequest) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initLab");
		BuckWaRequest request = new BuckWaRequest();
		request.put("labId", labId);	
		BuckWaResponse response = labService.deleteById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		 
				if(response.getStatus()==BuckWaConstants.SUCCESS){	 
					request.put("labCategoryId", labCategoryId+"");
				 
					 response = labCategoryService.getById(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						 LabCategory   labCategoryReturn = (LabCategory)response.getResObj("labCategory"); 
						 mav.addObject("labCategory", labCategoryReturn);
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
	public ModelAndView uploadFile(@ModelAttribute Lab lab, BindingResult result  ,HttpServletRequest httpRequest  ) {
		logger.info(" ### uploadFile ## lab:"+BeanUtils.getBeanString(lab));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("labEdit");
		InputStream inputStream = null;	 
		try{			 			 
			String fileName = null;
			MultipartFile originalfile = lab.getFileData();	
			if (originalfile!=null&&originalfile.getSize() > 0) {
				inputStream = originalfile.getInputStream();
				if (originalfile.getSize() > pathUtil.getMaximumUploadSize() ) {
					logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumUploadSize());					 
					mav.addObject("errorCode", "E013"); 
					return mav;
				} 							 
				
				String newFileName =   originalfile.getOriginalFilename() ;
			    String relativePath = pathUtil.getRelativeVisionImagePath(newFileName,lab.getLabId()+"");
			    String fullPath = pathUtil.getFullPathByRelativePath(relativePath);		
			    logger.info(" newFileName:"+newFileName+"\n relativePath:"+relativePath+" \n fullPath:"+fullPath);
				FileUtils.saveFileToDirectory(originalfile,fullPath);  
				ImagePath newImagePath = new ImagePath();
				newImagePath.setImagePath(relativePath);
				newImagePath.setFileName(newFileName);
				newImagePath.setImageType(ProjectConstant.ARTIFACE_TYPE_LAB);
				newImagePath.setImageCode(lab.getLabId()+""); 
				newImagePath.setProjectId(lab.getProjectId()+""); 
				lab.getFilePathList().add(newImagePath);				
				BuckWaRequest request = new BuckWaRequest(); 
				request.put("lab", lab );
				BuckWaResponse response = labService.updateFilePath(request);				
				if(response.getStatus()==BuckWaConstants.SUCCESS){					 	
					request.put("labId", lab.getLabId());	
					 response = labService.getById(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){		
						 lab = (Lab)response.getResObj("lab");				 	
						mav.addObject("lab", lab);				
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
		mav.setViewName("labEdit");  
		try{	 
			
			ImagePath imagePath = new ImagePath();
			imagePath.setImagePathId(new Long(fileId));	 
			imagePath.setImageType(ProjectConstant.ARTIFACE_TYPE_LAB);			
			BuckWaRequest request = new BuckWaRequest();
			request.put("imagePath", imagePath);
			BuckWaResponse response = visionService.removeImage(request); 		 
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				request.put("labId", usecaseId);	
				 response = labService.getById(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
					Lab lab = (Lab)response.getResObj("lab");				 	
					mav.addObject("lab", lab);				
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
	public ModelAndView detail(@RequestParam("labId") String labId,HttpServletRequest httpRequest ) {
		logger.info(" ## LabController.detail labId:"+labId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("labDetail");
		BuckWaRequest request = new BuckWaRequest();
		request.put("labId", labId);	
		httpRequest.getSession().setAttribute("usecaseId", labId);
		BuckWaResponse response = labService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Lab lab = (Lab)response.getResObj("lab");				 	
			mav.addObject("usecase", lab);				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 		
		request.put("artifaceId", labId);
		request.put("artifaceType", ProjectConstant.ARTIFACE_TYPE_LAB);	 
		 response = webboardTopicService.getTopicByArtifaceId(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Topic topic  = (Topic)response.getResObj("topic");				 	
			mav.addObject("topic", topic);	
			httpRequest.getSession().setAttribute("topicId", topic.getTopicId());
			
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
				request.put("artifaceType", ProjectConstant.ARTIFACE_TYPE_LAB);	 
				BuckWaResponse response  = webboardTopicService.getTopicByArtifaceId(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
					 topic  = (Topic)response.getResObj("topic");				 	
					mav.addObject("topic", topic);	
					httpRequest.getSession().setAttribute("topicId", topic.getTopicId());
					
				}else {	
					mav.addObject("errorCode", response.getErrorCode()); 
				}
				mav.setViewName("labDetail");
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
					mav.setViewName("labDetail");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("labDetail");
				}						
			}		
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
}
