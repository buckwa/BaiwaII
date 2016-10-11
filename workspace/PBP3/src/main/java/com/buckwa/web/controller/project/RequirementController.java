package com.buckwa.web.controller.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.util.UploadItem;
import com.buckwa.service.intf.project.ProjectService;

@Controller
@RequestMapping("/project/requirement/registration")
@SessionAttributes(types = UploadItem.class)
public class RequirementController { 
	
	
	@Autowired
	private ProjectService projectService;	
 
	 
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView init( ) {
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initRegistration");
		 
		try {
	 
			
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	

	
}
