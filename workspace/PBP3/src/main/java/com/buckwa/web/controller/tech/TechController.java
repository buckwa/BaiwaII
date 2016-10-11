package com.buckwa.web.controller.tech;

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
@RequestMapping("/tech")
@SessionAttributes(types = UseCase.class)
public class TechController { 
	private static Logger logger = LoggerFactory.getLogger(TechController.class);
 
	
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView init(HttpServletRequest httpRequest ) {
		logger.info(" Start");
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initTech"); 
		try { 
			
			
			
			
 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
 
	
	@RequestMapping(value = "initREST.htm", method = RequestMethod.GET)
	public ModelAndView initREST(HttpServletRequest httpRequest ) {
		logger.info(" Start");
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initREST"); 
		try {  
 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	@RequestMapping(value = "initThread.htm", method = RequestMethod.GET)
	public ModelAndView initThread(HttpServletRequest httpRequest ) {
		logger.info(" Start");
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initThread"); 
		try {  
 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	@RequestMapping(value = "initWS.htm", method = RequestMethod.GET)
	public ModelAndView initWS(HttpServletRequest httpRequest ) {
		logger.info(" Start");
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initWS"); 
		try {  
 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
				 
}
