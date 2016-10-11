package com.buckwa.web.controller.admin;

import java.util.List;

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

import com.buckwa.domain.admin.Group;
import com.buckwa.domain.admin.Role;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.validator.GroupValidator;
import com.buckwa.service.intf.CommonService;
import com.buckwa.service.intf.admin.GroupService;
import com.buckwa.service.intf.admin.RoleService;
import com.buckwa.util.BuckWaConstants;

@Controller
@RequestMapping("/admin/group")
@SessionAttributes(types = Group.class)
public class GroupManagementController {	
	private static Logger logger = LoggerFactory.getLogger(RoleManagementController.class);
	
	@Autowired
	private GroupService groupService;	
	
	@Autowired
	private RoleService roleService;	
	
	@Autowired
	private CommonService commonService;		
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav = gotoList(mav);
		return mav;
	}	
	
	private ModelAndView gotoList(ModelAndView mav) {
		logger.info(" Start  ");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		PagingBean bean = new PagingBean();
		mav.addObject("group", new Group());	
		mav.addObject("pagingBean", bean);		
		mav.setViewName("groupList");	

		int offset =0;
		bean.setOffset(offset);					
		BuckWaRequest request = new BuckWaRequest();
		request.put("pagingBean", bean);	
		bean.put("group", new Group());
		BuckWaResponse response = groupService.getByOffset(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){				
			PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
			bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
			bean.setTotalItems(beanReturn.getTotalItems());
			mav.addObject("pagingBean", bean);				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView init() {			
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("groupCreate");		
		Group group = new Group();
		// Load Role
		BuckWaResponse response =  roleService.getAll();
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			List<Role> roleList = (List)response.getResObj("roleList");			
			group.setRoleList(roleList);
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
		}		
		mav.addObject("group", group);	
		return mav;
	}	

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createGroup(@ModelAttribute Group group, BindingResult result) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{		
			new GroupValidator().validate(group, result);			
			if (result.hasErrors()) {				
				mav.setViewName("groupCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("group", group);
				BuckWaResponse response = groupService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					logger.info("  Success");					
					mav.addObject("group", new Group());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav = gotoList(mav);					
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("groupCreate");
				}				
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}


	@RequestMapping(value="search.htm" )
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute Group group) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("groupList");
		try{			
			PagingBean bean = new PagingBean();
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);						
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);	
			bean.put("group", group);
			BuckWaResponse response = groupService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());				 
				mav.addObject("pagingBean", bean);		
				mav.addObject("doSearch","true");
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
	public ModelAndView initEdit(@RequestParam("groupId") String groupId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request = new BuckWaRequest();
		request.put("groupId", groupId);	
		BuckWaResponse response = groupService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Group group = (Group)response.getResObj("group");			
			group.setRoleList(commonService.getAllRole());			
			mav.addObject("group", group);				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("groupEdit");

		return mav;
	}	
	
	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Group group, BindingResult result) {		
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{			 
			new GroupValidator().validate(group, result);			
			if (result.hasErrors()) {				 
				mav.setViewName("groupEdit");
			}else {
		 
				BuckWaRequest request = new BuckWaRequest();
				request.put("group", group);
				BuckWaResponse response = groupService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){					 				
					mav.addObject("successCode", response.getSuccessCode()); 				
					mav = gotoList(mav);					
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("groupEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	

	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("groupId") String groupId,HttpServletRequest httpRequest,@ModelAttribute Group group,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("groupList");
		BuckWaRequest request = new BuckWaRequest();
		request.put("groupId", groupId);	
		BuckWaResponse response = groupService.deleteById(request);
		
 
		if(response.getStatus()==BuckWaConstants.SUCCESS){					
			mav.addObject("successCode","S004"); 		 				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
			mav.addObject("pagingBean", bean);	
		}	
 
		int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
		bean.setOffset(offset);					 	
		request.put("pagingBean", bean);	
		bean.put("group", group);			 
		 response = groupService.getByOffset(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){				
			PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
			bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
			bean.setTotalItems(beanReturn.getTotalItems());
			mav.addObject("pagingBean", bean);				
		}else {			
			mav.addObject("errorCode", response.getErrorCode()); 
		}

		return mav;
	}		
	
}
