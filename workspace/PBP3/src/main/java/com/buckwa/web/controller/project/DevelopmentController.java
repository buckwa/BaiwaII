package com.buckwa.web.controller.project;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Project;
import com.buckwa.domain.util.UploadItem;
import com.buckwa.service.intf.project.DetailDesignService;
import com.buckwa.service.intf.project.ProjectService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.web.controller.admin.RoleManagementController;

@Controller
@RequestMapping("/project/development")
@SessionAttributes(types = UploadItem.class)
public class DevelopmentController { 
	private static Logger logger = LoggerFactory.getLogger(DevelopmentController.class);
	
	@Autowired
	private ProjectService projectService;	
	
    @Autowired
    private DetailDesignService detailDesignService;
	
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView init(HttpServletRequest httpRequest ) {
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initDevelopment"); 
		try { 
			String projectId = (String)httpRequest.getSession().getAttribute("projectId");  
			BuckWaRequest request = new BuckWaRequest();
			request.put("projectId", projectId);
			BuckWaResponse response = detailDesignService.getProjectByProjectId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				Project project = (Project)response.getResObj("project"); 
				logger.info(" ### project :"+BeanUtils.getBeanString(project));
				mav.addObject("project", project);				
			}else {
				mav.addObject("errorCode", response.getErrorCode()); 
			}			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	@RequestMapping(value = "initSoftwareAndTool.htm", method = RequestMethod.GET)
	public ModelAndView initSoftwareAndTool(HttpServletRequest httpRequest ) {
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initSoftwareAndTool"); 
		try {
 			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	@RequestMapping(value = "initDeployment.htm", method = RequestMethod.GET)
	public ModelAndView initDeployment(HttpServletRequest httpRequest ) {
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initDeployment"); 
		try {
 			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
}
