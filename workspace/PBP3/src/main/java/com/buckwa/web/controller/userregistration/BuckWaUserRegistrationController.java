package com.buckwa.web.controller.userregistration;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.admin.Group;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.validator.admin.UserValidator;
import com.buckwa.service.intf.CommonService;
import com.buckwa.service.intf.admin.AdminUserService;
import com.buckwa.service.intf.userregistration.UserRegistrationService;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.FileUtils;

@Controller
@RequestMapping("/userregistration")
@SessionAttributes(types = User.class)
public class BuckWaUserRegistrationController {	
	private static Logger logger = LoggerFactory.getLogger(BuckWaUserRegistrationController.class);
	
	@Autowired
	private AdminUserService userService;	
	
	@Autowired
	private CommonService commonService;	

	
	@Autowired
	private UserRegistrationService userRegistrationService;	
 
	@Autowired
    private PathUtil pathUtil;
	
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView init() {
		logger.info("  ");
		ModelAndView mav = new ModelAndView();		
		PagingBean bean = new PagingBean();
		mav.addObject("user", new User());	
		mav.addObject("pagingBean", bean);				 
		mav.setViewName("registerCreate");	
		return mav;
	}	
	
	@RequestMapping(value="register.htm", method = RequestMethod.POST)
	public ModelAndView create(@ModelAttribute User user, BindingResult result,HttpServletRequest httpRequest) {		
		 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("registerCreate");
		try{
			logger.info(" Context :"+httpRequest.getContextPath()+"   User:"+BeanUtils.getBeanString(user));
			new UserValidator().validate(user, result);			
			if (result.hasErrors()) {
				logger.info("  Validate Error");				 
			}else {	
				logger.info("  Validate Success , Do create User ");
				BuckWaRequest request = new BuckWaRequest();
				user.setSecureCode(BuckWaUtils.getSecureCode(user.getUsername()));
				List <Group>allGroup = commonService.getAllGroup();
				user.setGroups(new String[]{BuckWaUtils.getGroupIdFromGroupName(BuckWaConstants.GROUP_USER,allGroup) });			 
				user.setEnabled(true);
				user.setEmail(user.getUsername());
				request.put("user", user);				 
				BuckWaResponse response = userRegistrationService.registerUser(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					logger.info("Success");					
					mav.addObject("user", user);		 
					 mav.setViewName("registerCreateSuccess");	
				}else {
					logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
					mav.addObject("errorCode", response.getErrorCode()); 
				}								
			}								
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}		
	
	@RequestMapping(value="enableUser.htm", method = RequestMethod.GET) 
	public ModelAndView enableUser(@RequestParam("code") String code,@RequestParam("userName") String userName) {
		logger.info("  code:"+code+" userName:"+userName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("enableUserSuccess");
		BuckWaRequest request = new BuckWaRequest();
		User user= new User();
		user.setSecureCode(code);
		user.setUsername(userName);
		request.put("user", user);				 
		BuckWaResponse response = userRegistrationService.enableUser(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			logger.info("Success");					
			mav.addObject("user", user);
			mav.setViewName("enableUserSuccess");	
			mav.addObject(BuckWaConstants.SUCCESS_CODE, response.getSuccessCode());
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject(BuckWaConstants.ERROR_CODE, response.getErrorCode()); 
		}
		return mav;
	}	
	
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("username") String username) {		 
		ModelAndView mav = new ModelAndView();
		logger.info(" initEdit username:"+username);	
		mav.setViewName("registerEdit");
		BuckWaRequest request = new BuckWaRequest();
		request.put("username", username);	
		BuckWaResponse response = userRegistrationService.getUserByUsername(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			User user = (User)response.getResObj("user");	
			//user.setGroupList(commonService.getAllGroup()) ;			
			mav.addObject("user", user);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		return mav;
	}	
	
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute User user, BindingResult result) {		
		 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("registerEdit");
		try{
			 
			//new UserValidator().validate(user, result);			
			//if (result.hasErrors()) {				 
			//	mav.setViewName("userEdit");
			//}else {
			
				BuckWaRequest request = new BuckWaRequest();
				request.put("user", user);
				BuckWaResponse response = userService.updateRegisterUser(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){					
					mav.addObject("group", user);
					mav.addObject("successCode", response.getSuccessCode()); 				 	
	 				
				}else {					 
					mav.addObject("errorCode", response.getErrorCode()); 
					
				}		
			//}				
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	@RequestMapping(value="uploadSingature.htm")
	public ModelAndView uploadImage(@ModelAttribute User user, BindingResult result  ) {
		logger.info(" ### uploadSingature.htm ## ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("registerEdit");
		InputStream inputStream = null;	 
		try{			 
			user.setSignatureImagePath(null);
		 
			MultipartFile originalfile = user.getFileData();	
			if (originalfile==null||originalfile.getSize() > 0) {
				inputStream = originalfile.getInputStream();
				if (originalfile.getSize() > pathUtil.getMaximumImageUploadSize()) {
					logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumImageUploadSize());					 
					mav.addObject("errorCode", "E013"); 
					return mav;
				} 
				
				// 1. New File Name
				// 2. Relative File Path
				// 2. Full File Path
				
				String newFileName =  BuckWaUtils.getUserFileNameByOriginalFileName(originalfile.getOriginalFilename(),BuckWaConstants.USER_IMAGE_TYPE_SIGNATURE);
			    String relativeUserImagePath = pathUtil.getRelativeUserImagePath(newFileName);
			    String fullUserImagePath = pathUtil.getFullUserImagePath(newFileName);				
				FileUtils.saveFileToDirectory(originalfile,fullUserImagePath);  
				user.setSignatureImagePath(relativeUserImagePath);				
				BuckWaRequest request = new BuckWaRequest();
				request.put("user", user );	
				BuckWaResponse response = userService.updateSignatureImagePath(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					
				}else {			 
					mav.addObject("errorCode", response.getErrorCode()); 
				}	
				
			 
			}else	{					 
				mav.addObject("errorCode", "E012"); 
				return mav;					
			}
			 			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		
		return mav;
	}

}
