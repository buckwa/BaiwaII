package com.buckwa.web.controller.project;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.ImagePath;
import com.buckwa.domain.project.Project;
import com.buckwa.domain.project.Vision;
import com.buckwa.domain.validator.project.VisionValidator;
import com.buckwa.domain.validator.webboard.ReplyMessageValidator;
import com.buckwa.domain.webboard.Message;
import com.buckwa.domain.webboard.Topic;
import com.buckwa.service.intf.project.ProjectService;
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
@RequestMapping("/project/vision")
@SessionAttributes(types = Project.class)
public class VisionController { 
	private static Logger logger = LoggerFactory.getLogger(VisionController.class);
	@Autowired
	private VisionService visionService;
	
	@Autowired
	private ProjectService projectService; 
	
	@Autowired
    private PathUtil pathUtil;
	
	@Autowired
	private ProjectUtil projectUtil;
	
	@Autowired
	private WebboardTopicService  webboardTopicService;
	
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView vision( @RequestParam("projectId") String projectId,HttpServletRequest httpRequest) {
		ModelAndView mav = new ModelAndView();	
		mav.setViewName("initVision"); 
		try { 
			logger.info(" ## project.initVision ## ");
			if(projectId==null||projectId.trim().length()==0){
				projectId = (String)httpRequest.getSession().getAttribute("projectId");
			}else{
				httpRequest.getSession().setAttribute("projectId", projectId);
			} 
			String visionId ="";
			BuckWaRequest request = new BuckWaRequest();
			request.put("projectId", projectId);
			BuckWaResponse response = projectService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				Project   projectReturn = (Project)response.getResObj("project");  
				httpRequest.getSession().setAttribute("projectName", projectReturn.getProjectName());
				logger.info(" ## project.initVision ## projectName :"+projectReturn.getProjectName());
				 request = new BuckWaRequest();
					request.put("projectId", projectId);
					 response = visionService.getVisionByProjectId(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						Vision   visionReturn = (Vision)response.getResObj("vision"); 
						visionId = visionReturn.getVisionId()+"";
						projectReturn.setVision(visionReturn);
					}			 
				 mav.addObject("project", projectReturn);
			} 
			BuckWaUser user = (BuckWaUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection <GrantedAuthority>   grantedAutority = user.getAuthorities();			
			List<GrantedAuthority>  newAuList = new ArrayList();

			for(GrantedAuthority tmp:grantedAutority){				 
				String userAu = tmp.getAuthority();					 
				if(userAu.indexOf("PROJECT")>0){				
				}else{
					newAuList.add(tmp);
				}
			}
			
			request = new BuckWaRequest();
			request.put("username", BuckWaUtils.getUserNameFromContext());
			request.put("projectId", projectId);
			response = projectService.getAuthorityByUser(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){		
				GrantedAuthority[] authorityByUser  = (GrantedAuthority[])response.getResObj("authorityList");
				if(authorityByUser!=null&&authorityByUser.length>0){ 
					for(int i=0;i<authorityByUser.length;i++ ){
						GrantedAuthority tmpau = authorityByUser[i];					 
						newAuList.add(tmpau);
					} 
					user.setAuthorities(newAuList.toArray(new GrantedAuthority[] {}));
				}

			}
			
			httpRequest.getSession().setAttribute("visionId", visionId);
			request.put("artifaceId", visionId);
			request.put("artifaceType", ProjectConstant.ARTIFACE_TYPE_VISION);	 
			 response = webboardTopicService.getTopicByArtifaceId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){		
				Topic topic  = (Topic)response.getResObj("topic");				 	
				mav.addObject("topic", topic);	
				httpRequest.getSession().setAttribute("topicId", topic.getTopicId());
				
			}else {	
				mav.addObject("errorCode", response.getErrorCode()); 
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	
	
	@RequestMapping(value="initVisionDefaultProject.htm", method = RequestMethod.GET)
	public ModelAndView initDefault(HttpServletRequest httpRequest ) {
		ModelAndView mav = new ModelAndView(); 
		mav.setViewName("initProject");
		logger.info(" ## project.initVisionDefaultProject ## ");
		try{
			String projectId  ="";
			String visionId = "";
			List<Project> projectList  = new ArrayList();
			//BuckWaResponse response = projectService.getAll();
			BuckWaRequest request = new BuckWaRequest();
			request.put("userName", BuckWaUtils.getUserNameFromContext());
			BuckWaResponse response = projectService.getAllByUser(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				projectList = (List)response.getResObj("projectList"); 
			}			
			mav.addObject("projectList", projectList);	
			
			
			if(projectList!=null&&projectList.size()>0){
				Project project = projectList.get(0);
				projectId = project.getProjectId()+"";
				httpRequest.getSession().setAttribute("projectId", projectId);
				request = new BuckWaRequest();
				request.put("projectId", projectId);
				 response = projectService.getById(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					Project   projectReturn = (Project)response.getResObj("project");  
					httpRequest.getSession().setAttribute("projectName", projectReturn.getProjectName());
					 request = new BuckWaRequest();
						request.put("projectId", projectId);
						 response = visionService.getVisionByProjectId(request);
						if(response.getStatus()==BuckWaConstants.SUCCESS){				
							Vision   visionReturn = (Vision)response.getResObj("vision"); 
							projectReturn.setVision(visionReturn);
							visionId = visionReturn.getVisionId()+"";
							httpRequest.getSession().setAttribute("visionId", visionId);
						}			 
					 mav.addObject("project", projectReturn);
				} 
				 

				BuckWaUser user = (BuckWaUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Collection <GrantedAuthority>   grantedAutority = user.getAuthorities();
				
				List<GrantedAuthority>  newAuList = new ArrayList();
				
				//logger.info(" ## grantedAutority size before remove:"+grantedAutority.size());
				for(GrantedAuthority tmp:grantedAutority){				 
					String userAu = tmp.getAuthority();					 
					if(userAu.indexOf("PROJECT")>0){
						//logger.info("  Found Match ing Project :"+tmp.getAuthority()); 
					}else{
						newAuList.add(tmp);
					}
				}
				//logger.info(" ## grantedAutority size after remove:"+newAuList.size());
				
				request = new BuckWaRequest();
				request.put("username", BuckWaUtils.getUserNameFromContext());
				request.put("projectId", projectId);
				response = projectService.getAuthorityByUser(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
					GrantedAuthority[] authorityByUser  = (GrantedAuthority[])response.getResObj("authorityList");
					if(authorityByUser!=null&&authorityByUser.length>0){ 
						for(int i=0;i<authorityByUser.length;i++ ){
							GrantedAuthority tmpau = authorityByUser[i];
							//logger.info(" Found autority By project xxxxxx:"+tmpau.getAuthority());
							newAuList.add(tmpau);
						} 
						user.setAuthorities(newAuList.toArray(new GrantedAuthority[] {}));
					}

				}
				
				//logger.info(" ## grantedAutority size after add project Au :"+newAuList.size());				
				mav.setViewName("initVision"); 
			}		
			
			request.put("artifaceId", visionId);
			request.put("artifaceType", ProjectConstant.ARTIFACE_TYPE_VISION);	 
			 response = webboardTopicService.getTopicByArtifaceId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){		
				Topic topic  = (Topic)response.getResObj("topic");				 	
				mav.addObject("topic", topic);	
				httpRequest.getSession().setAttribute("topicId", topic.getTopicId());
				
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
	public ModelAndView initEdit(@RequestParam("projectId") String projectId,HttpServletRequest httpRequest ,@ModelAttribute Project project) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("visionEdit"); 
		try {
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("projectId", projectId);
			BuckWaResponse response = visionService.getVisionByProjectId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				Vision   visionReturn = (Vision)response.getResObj("vision"); 
				httpRequest.getSession().setAttribute("visionId",visionReturn.getVisionId()+"");
				String projectName = (String)httpRequest.getSession().getAttribute("projectName");
				visionReturn.setProjectName(projectName);
				project.setVision(visionReturn);
				 mav.addObject("project", project);
			}  
	} catch (Exception ex) {
		ex.printStackTrace();
		mav.addObject("errorCode", "E001");
	}
		return mav;
	}	 

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Project project, BindingResult result,HttpServletRequest httpRequest ) {		
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("visionEdit");
		try{			 
			Vision vision = project.getVision();
			new VisionValidator().validate(vision, result);			
			if (result.hasErrors()) {				 
				mav.setViewName("visionEdit");
			}else {
		 
				BuckWaRequest request = new BuckWaRequest();
				vision.setUpdateBy(BuckWaUtils.getUserNameFromContext());
				request.put("vision", vision);
				vision.setVisionId( projectUtil.getVisionFromSession(httpRequest));
				BuckWaResponse response = visionService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
					 mav.addObject("successCode", response.getSuccessCode()); 
					request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));  
					 response = visionService.getVisionByProjectId(request);
					
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						Vision   visionReturn = (Vision)response.getResObj("vision"); 
						logger.info(" ## project.initVision ## projectName xx :"+httpRequest.getSession().getAttribute("projectName"));
						visionReturn.setProjectName((String)httpRequest.getSession().getAttribute("projectName"));
						project.setVision(visionReturn);
						 mav.addObject("project", project);
						
					} 
					
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("visionEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}		
	
	
	
	
	@RequestMapping(value="uploadImage.htm")
	public ModelAndView uploadImage(@ModelAttribute Project project, BindingResult result  ,HttpServletRequest httpRequest  ) {
		logger.info(" ### uploadImage ## vision:"+BeanUtils.getBeanString(project.getVision()));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("visionEdit");
		InputStream inputStream = null;	 
		try{			 			 
			String fileName = null;
			Vision vision = project.getVision();
			MultipartFile originalfile = vision.getFileData();	
			if (originalfile!=null&&originalfile.getSize() > 0) {
				inputStream = originalfile.getInputStream();
				if (originalfile.getSize() > pathUtil.getMaximumImageUploadSize()) {
					logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumImageUploadSize());					 
					mav.addObject("errorCode", "E013"); 
					return mav;
				} 												
	 			
				vision.setVisionId(projectUtil.getVisionIdFromSession(httpRequest));
				vision.setProjectId(projectUtil.getProjectIdFromSession(httpRequest));
				String newFileName =   originalfile.getOriginalFilename() ;
			    String relativePath = pathUtil.getRelativeVisionImagePath(newFileName,projectUtil.getVisionIdFromSession(httpRequest)+"");
			    String fullPath = pathUtil.getFullPathByRelativePath(relativePath);		
			    logger.info(" newFileName:"+newFileName+"\n relativePath:"+relativePath+" \n fullPath:"+fullPath);
			   // newFileName:chawean_cv_image.jpg
			    //relativePath:vision/34/chawean_cv_image.jpg 
			    //fullPath:D:/Project/Baiwa/vision/34/chawean_cv_image.jpg
				FileUtils.saveFileToDirectory(originalfile,fullPath);  
 
				ImagePath newImagePath = new ImagePath();
				newImagePath.setImagePath(relativePath);
				newImagePath.setImageType(ProjectConstant.ARTIFACE_TYPE_VISION);
				newImagePath.setImageCode(vision.getProjectId()+""); 
				
				vision.getImagePathList().add(newImagePath);				
				BuckWaRequest request = new BuckWaRequest();
				request.put("vision", vision );	
				BuckWaResponse response = visionService.updateImagePath(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
				 	
					request.put("projectId", vision.getProjectId()+"");
					 response = visionService.getVisionByProjectId(request);
					 
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						Vision   visionReturn = (Vision)response.getResObj("vision"); 
						visionReturn.setProjectName((String)httpRequest.getSession().getAttribute("projectName"));
						 mav.addObject("vision", visionReturn);
							project.setVision(visionReturn);
							 mav.addObject("project", project);
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
	
	@RequestMapping(value="removeImage.htm")
	public ModelAndView removeImage(@RequestParam("imagePathId") String imagePathId ,HttpServletRequest httpRequest,@ModelAttribute Project project ) {
		logger.info(" ### removeImage imagePathId :"+imagePathId+"  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("visionEdit");  
		try{	 
			
			ImagePath imagePath = new ImagePath();
			imagePath.setImagePathId(new Long(imagePathId));	 
			imagePath.setImageType(ProjectConstant.ARTIFACE_TYPE_VISION);
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("imagePath", imagePath);
			BuckWaResponse response = visionService.removeImage(request);
			
			
			String projectId = (String)httpRequest.getSession().getAttribute("projectId");
			
			request = new BuckWaRequest(); 
			request.put("projectId", projectId);
			response = visionService.getVisionByProjectId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				Vision   visionReturn = (Vision)response.getResObj("vision"); 
				visionReturn.setProjectName((String)httpRequest.getSession().getAttribute("projectName"));
				 mav.addObject("vision", visionReturn);
					project.setVision(visionReturn);
					 mav.addObject("project", project);
				 
			}  
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}		
		return mav;
	}
	
	
	@RequestMapping(value="uploadFile.htm")
	public ModelAndView uploadFile(@ModelAttribute Project project, BindingResult result  ,HttpServletRequest httpRequest  ) {
		logger.info(" ### uploadFile "+BeanUtils.getBeanString(project.getVision()));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("visionEdit");
		InputStream inputStream = null;	 
		try{			 			 
			String fileName = null;
			Vision vision = project.getVision();
			MultipartFile originalfile = vision.getReferenceFileData();	
			if (originalfile!=null&&originalfile.getSize() > 0) {
				inputStream = originalfile.getInputStream();
				if (originalfile.getSize() > pathUtil.getMaximumImageUploadSize()) {
					logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumImageUploadSize());					 
					mav.addObject("errorCode", "E013"); 
					return mav;
				} 												
	 			
				vision.setVisionId(projectUtil.getVisionIdFromSession(httpRequest));
				vision.setProjectId(projectUtil.getProjectIdFromSession(httpRequest));
				String newFileName =   originalfile.getOriginalFilename() ;
			    String relativePath = pathUtil.getRelativeVisionImagePath(newFileName,projectUtil.getVisionIdFromSession(httpRequest)+"");
			    String fullPath = pathUtil.getFullPathByRelativePath(relativePath);		
			    logger.info(" newFileName:"+newFileName+"\n relativePath:"+relativePath+" \n fullPath:"+fullPath);
			   // newFileName:chawean_cv_image.jpg
			    //relativePath:vision/34/chawean_cv_image.jpg 
			    //fullPath:D:/Project/Baiwa/vision/34/chawean_cv_image.jpg
				FileUtils.saveFileToDirectory(originalfile,fullPath);  
 
				ImagePath newImagePath = new ImagePath();
				newImagePath.setImagePath(relativePath);
				newImagePath.setImageType(ProjectConstant.ARTIFACE_TYPE_VISION_REFERENCE);
				newImagePath.setImageCode(vision.getProjectId()+""); 
				newImagePath.setFileName(newFileName);
				vision.getFilePathList().add(newImagePath);		
				
				logger.info("  ## Controller  updateRefenceFilePath:"+vision.getFilePathList());
				
				
				BuckWaRequest request = new BuckWaRequest();
				request.put("vision", vision );	
				BuckWaResponse response = visionService.updateRefenceFilePath(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
				 	
					request.put("projectId", vision.getProjectId()+"");
					 response = visionService.getVisionByProjectId(request);
					 
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						Vision   visionReturn = (Vision)response.getResObj("vision"); 
						visionReturn.setProjectName((String)httpRequest.getSession().getAttribute("projectName"));
						 mav.addObject("vision", visionReturn);
							project.setVision(visionReturn);
							 mav.addObject("project", project);
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
	@RequestMapping(value="removeFile.htm")
	public ModelAndView removeFile(@ModelAttribute Project project,@RequestParam("filePathId") String filePathId ,HttpServletRequest httpRequest ) {
		logger.info(" ### removeFile filePathId :"+filePathId+"  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("visionEdit");  
		try{	 
			
			ImagePath imagePath = new ImagePath();
			imagePath.setImagePathId(new Long(filePathId));	 
			imagePath.setImageType(ProjectConstant.ARTIFACE_TYPE_VISION_REFERENCE);
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("imagePath", imagePath);
			BuckWaResponse response = visionService.removeFile(request);
			
			
			String projectId = (String)httpRequest.getSession().getAttribute("projectId");
			
			request = new BuckWaRequest(); 
			request.put("projectId", projectId);
			response = visionService.getVisionByProjectId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				Vision   visionReturn = (Vision)response.getResObj("vision"); 
				visionReturn.setProjectName((String)httpRequest.getSession().getAttribute("projectName"));
				 mav.addObject("vision", visionReturn);
					project.setVision(visionReturn);
					 mav.addObject("project", project);
			}  
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}		
		return mav;
	}
	
	@RequestMapping(value="replyMessage.htm", method = RequestMethod.POST)
	public ModelAndView replyMessage(@ModelAttribute Topic topic, BindingResult result,HttpServletRequest httpRequest ) {	
		ModelAndView mav = new ModelAndView();
		try{			
			logger.info(" createTopic topic xx: "+BeanUtils.getBeanString(topic));
			BuckWaRequest request = new BuckWaRequest();
			Long topicId = (Long)httpRequest.getSession().getAttribute("topicId");
			new ReplyMessageValidator().validate(topic, result);			
			if (result.hasErrors()) {			
				mav.addObject("topic", topic);
				mav.setViewName("initVision");
			}else {					
				
				topic.setStatus("1");
				
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
					mav.setViewName("initVision");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("initVision");
				}				
			}		
			
			String visionId = (String)httpRequest.getSession().getAttribute("visionId");
			request.put("artifaceId", visionId);
			request.put("artifaceType", ProjectConstant.ARTIFACE_TYPE_VISION);	 
			BuckWaResponse response  = webboardTopicService.getTopicByArtifaceId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){		
				 topic  = (Topic)response.getResObj("topic");				 	
				mav.addObject("topic", topic);	
				httpRequest.getSession().setAttribute("topicId", topic.getTopicId());
				
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
