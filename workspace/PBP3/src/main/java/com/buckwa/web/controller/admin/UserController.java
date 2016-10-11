package com.buckwa.web.controller.admin;

import java.io.InputStream;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
 
import com.buckwa.domain.validator.ChangePasswordValidator;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.service.intf.admin.AdminUserService;
import com.buckwa.service.intf.admin.UserService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.FileUtils;
import com.buckwa.util.school.SchoolUtil;
 
@Controller
@RequestMapping("/user")
@SessionAttributes(types = User.class)
public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private AdminUserService userService;	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@RequestMapping(value = "/register.htm", method = RequestMethod.GET)
	public ModelAndView initRegister() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", new User());	
		mav.setViewName("register");
		return mav;
	}
	
	@RequestMapping(value = "/register.htm", method = RequestMethod.POST)
	public ModelAndView submitRegister(@ModelAttribute(value = "user") User user, BindingResult result) {
		logger.info(" Start  ");
		logger.info(" # user: "+BeanUtils.getBeanString(user));
		ModelAndView mav = new ModelAndView();
		new UserValidator().validate(user, result);			
		if (result.hasErrors()) {
			logger.info("  Validate Error");	
			mav.setViewName("register");
		}else {  
			mav.setViewName("registerSuccess");
		}		
		return mav;
	}
	
	@RequestMapping(value = "/userProfile.htm", method = RequestMethod.GET)
	public ModelAndView viewUserProfile( ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("userProfile");		
		try{
			String username = BuckWaUtils.getUserNameFromContext();
			logger.info("viewUserProfile  username :"+username);
			BuckWaRequest request = new BuckWaRequest();
			request.put("username", username);	
			String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
			BuckWaResponse response = userService.getUserByUsername(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				User user = (User)response.getResObj("user");				 		
				mav.addObject("user", user);				
			}else {				 
				mav.addObject("errorCode", response.getErrorCode()); 
			}				
		}catch(Exception ex){
			ex.printStackTrace();
		}
 	
		return mav;
	}

	@RequestMapping(value = "/changePassword.htm", method = RequestMethod.GET)
	public ModelAndView changePassword( ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("changePassword");		
		try{
			String username = BuckWaUtils.getUserNameFromContext();
			logger.info("viewUserProfile  username :"+username);
			BuckWaRequest request = new BuckWaRequest();
			request.put("username", username);	
			String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
			BuckWaResponse response = userService.getUserByUsername(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				User user = (User)response.getResObj("user");				 		
				mav.addObject("user", user);				
			}else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode()); 
			}				
		}catch(Exception ex){
			ex.printStackTrace();
		}
 	
		return mav;
	}

	@RequestMapping(value = "/changePassword.htm", method = RequestMethod.POST)
	public ModelAndView changePasswordSubmit(@ModelAttribute(value = "user") User user, BindingResult result ) {
		logger.info(" Start  "); 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("changePassword");
		try{			
			new ChangePasswordValidator().validate(user, result);			
			if (result.hasErrors()) {
				logger.info("  Validate Error");					
			}else {	
				// Check current password
				BuckWaUser userContext = (BuckWaUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				logger.info(" userContext password:"+userContext.getPassword()+"  Screen Password:"+BuckWaUtils.encrypt(user.getCurrentPassword()));
				
				if(userContext.getPassword().equals(BuckWaUtils.encrypt(user.getCurrentPassword()))){
					BuckWaRequest request = new BuckWaRequest();
					request.put("user", user);	
					BuckWaResponse response = userService.changePassword(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){			
						mav.addObject("successCode", response.getSuccessCode()); 				
					}else {
						logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
						mav.addObject("errorCode", response.getErrorCode()); 
					}	
					
				}else{
					mav.addObject("errorCode", "E007"); 
					throw new Exception();
				}				
				
				mav.setViewName("changePasswordSuccess");	 
			}	
 				
		}catch(Exception ex){
			ex.printStackTrace();
		}
 	
		return mav;
	}


}
