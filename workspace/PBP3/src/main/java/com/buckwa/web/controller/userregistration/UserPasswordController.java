package com.buckwa.web.controller.userregistration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.admin.Role;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.validator.admin.RoleValidator;
import com.buckwa.service.intf.admin.RoleService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;

@Controller
@RequestMapping("/user/password")
@SessionAttributes(types = Role.class)
public class UserPasswordController {	
	private static Logger logger = LoggerFactory.getLogger(UserPasswordController.class);
	
	@Autowired
	private RoleService roleService;	
	
	
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList() {
		logger.info(" # RoleManagementController.initlist");
		ModelAndView mav = new ModelAndView();		
		PagingBean bean = new PagingBean();

		mav.addObject("role", new Role());	
		mav.addObject("pagingBean", bean);		
		//mav.addObject("initstatus", "true");	
		mav.setViewName("roleList");	
		return mav;
	}	

	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView init() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("roleCreate");
		Role role = new Role();
		mav.addObject("role", role);	
		return mav;
	}	
	

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createRole(@ModelAttribute Role role, BindingResult result) {	
		ModelAndView mav = new ModelAndView();
		try{			
			new RoleValidator().validate(role, result);			
			if (result.hasErrors()) {				
				mav.setViewName("roleCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("role", role);
				BuckWaResponse response = roleService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					mav.addObject("role", new Role());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("roleCreateSuccess");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("roleCreate");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	@RequestMapping(value="search.htm", method = RequestMethod.GET)
	public ModelAndView searchGet(HttpServletRequest httpRequest ,@ModelAttribute PagingBean bean) {
		logger.info(" # RoleManagementController.search");
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("roleList");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("role", new Role());	
			request.put("pagingBean", bean);	
			bean.put("role", new Role());			 
			BuckWaResponse response = roleService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			 
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="search.htm", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute Role role,@ModelAttribute PagingBean bean) {
		logger.info(" # RoleManagementController.search");
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("roleList");
		try{			
			//PagingBean bean = new PagingBean();
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);			
			//logger.info(" PagingBean:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			request.put("pagingBean", bean);	
			bean.put("role", role);			 
			BuckWaResponse response = roleService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			 
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("roleId") String roleId) {
		logger.info(" # RoleManagementController.initEdit roleId:"+roleId);
		ModelAndView mav = new ModelAndView();
		BuckWaRequest request = new BuckWaRequest();
		request.put("roleId", roleId);	
		BuckWaResponse response = roleService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			logger.info("  Success");
			Role role = (Role)response.getResObj("role");
			logger.info(" role return :"+BeanUtils.getBeanString(role));
			mav.addObject("role", role);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("roleEdit");

		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Role role, BindingResult result) {		
		logger.info(" # RoleManagementController.submitEdit role:"+BeanUtils.getBeanString(role));
		ModelAndView mav = new ModelAndView();
		try{
			logger.info(" role:"+BeanUtils.getBeanString(role));
			new RoleValidator().validate(role, result);
			
			if (result.hasErrors()) {
				logger.info("  Validate Error");
				mav.setViewName("roleEdit");
			}else {
	
				logger.info("  Validate Success , Do create Role ");
				BuckWaRequest request = new BuckWaRequest();
				request.put("role", role);
				BuckWaResponse response = roleService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					logger.info("  Success");					
					mav.addObject("role", new Role());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("roleEdit");					
					
				}else {
					logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("roleEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("roleId") String roleId,HttpServletRequest httpRequest,@ModelAttribute Role role) {
		logger.info(" # RoleManagementController.delete roleId:"+roleId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("roleList");
		BuckWaRequest request = new BuckWaRequest();
		request.put("roleId", roleId);	
		BuckWaResponse response = roleService.deleteById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			PagingBean bean = new PagingBean();
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);					 	
			request.put("pagingBean", bean);	
			bean.put("role", role);
			logger.info(">> roleName:"+role.getRoleName()+", roleDesc:"+role.getRoleDesc());
			 response = roleService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode()); 
			}			
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		

		return mav;
	}	
	
}
