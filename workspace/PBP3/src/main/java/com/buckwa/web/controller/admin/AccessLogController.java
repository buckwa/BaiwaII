package com.buckwa.web.controller.admin;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.admin.AccessLog;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.service.intf.CommonService;
import com.buckwa.service.intf.admin.AccessLogService;
import com.buckwa.service.intf.admin.GroupService;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.FileUtils;
import com.buckwa.util.PAMConstants;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;

@Controller
@RequestMapping("/admin/accessLog")
@SessionAttributes(types = User.class)
public class AccessLogController {
	
	private static Logger logger = LoggerFactory.getLogger(AccessLogController.class);
	
	@Autowired
	private AccessLogService accessLogService;		
 
	@Autowired
	private GroupService groupService;	

	@Autowired
	private CommonService commonService;
	
	@Autowired
    private PathUtil pathUtil;
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@RequestMapping("init.htm")
	public ModelAndView init() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav =gotoList(mav);
		
		return mav;
	}
	
	private ModelAndView gotoList(ModelAndView mav){
		mav.setViewName("accessLogList");		
		AccessLog accessLog = new AccessLog();		
		PagingBean bean = new PagingBean();		
		mav.addObject("pagingBean", bean);	
		mav.addObject("accessLog", accessLog);	
		
		try{
		
	 
		int offset = 0;	
		bean.setOffset(offset);				
		BuckWaRequest request = new BuckWaRequest();
		request.put("pagingBean", bean);		
		bean.put("accessLog", accessLog);
		BuckWaResponse response = accessLogService.getByOffset(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
			mav.addObject("pagingBean", beanReturn);				
		}else {				
			mav.addObject("errorCode", response.getErrorCode()); 
		}
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
  
	@RequestMapping(value="search.htm" )
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute AccessLog accessLog) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("accessLogList");
		try{			
			PagingBean bean = new PagingBean();
			//int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(0);		
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);		
			bean.put("accessLog", accessLog);
			BuckWaResponse response = accessLogService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				mav.addObject("totalItems", beanReturn.getTotalItems());	
				mav.addObject("pagingBean", beanReturn);	
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
	
	
	@RequestMapping(value="searchNextPage.htm" )
	public ModelAndView searchNextPage(HttpServletRequest httpRequest,@ModelAttribute AccessLog accessLog) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("accessLogList");
		try{			
			PagingBean bean = new PagingBean();
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);		
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);		
			bean.put("accessLog", accessLog);
			BuckWaResponse response = accessLogService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				mav.addObject("pagingBean", beanReturn);	
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
	
	
}
