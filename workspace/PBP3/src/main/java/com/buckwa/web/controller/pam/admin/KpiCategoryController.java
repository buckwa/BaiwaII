package com.buckwa.web.controller.pam.admin;

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

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.LovDetail;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.KpiCategory;
import com.buckwa.domain.validator.pam.KpiCategoryValidator;
import com.buckwa.service.intf.pam.KpiCategoryService;
import com.buckwa.service.intf.pam.PersonProfileService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.PAMConstants;

@Controller
@RequestMapping("/admin/kpiCategory")
@SessionAttributes(types = KpiCategory.class)
public class KpiCategoryController {	
	private static Logger logger = LoggerFactory.getLogger(KpiCategoryController.class);	
	@Autowired
	private KpiCategoryService kpiCategoryService;		
	
	@Autowired
	private PersonProfileService personProfileService;
	 	
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav = gotoList(mav);
		return mav;
	}	
	
	private ModelAndView getEmployeeTypeList(ModelAndView mav){
		try{
			BuckWaResponse response = personProfileService.getEmployeeTypeList();
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				List<LovDetail> employeeTypeList  =(List)response.getResObj("employeeTypeList");
				mav.addObject("employeeTypeList",employeeTypeList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return mav;
	}
	
	private ModelAndView gotoList(ModelAndView mav){
		PagingBean bean = new PagingBean();
		mav.addObject("kpiCategory", new KpiCategory());	
		mav.addObject("pagingBean", bean);		
		mav.setViewName("kpiCategoryList");
		// Search with initial
		int offset = 0;	
		bean.setOffset(offset);	 
		BuckWaRequest request = new BuckWaRequest();		
		mav.addObject("kpiCategory", new KpiCategory());	
		request.put("pagingBean", bean);	
		bean.put("kpiCategory", new KpiCategory());			 
		BuckWaResponse response = kpiCategoryService.getByOffset(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			logger.info(" Success ");
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
		mav.setViewName("kpiCategoryCreate");
		KpiCategory kpiCategory = new KpiCategory();
		mav = getEmployeeTypeList(mav);
		mav.addObject("kpiCategory", kpiCategory);	
		return mav;
	}		

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createKpiCategory(@ModelAttribute KpiCategory kpiCategory, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{			
			new KpiCategoryValidator().validate(kpiCategory, result);			
			if (result.hasErrors()) {				
				mav.setViewName("kpiCategoryCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("kpiCategory", kpiCategory);
				BuckWaResponse response = kpiCategoryService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					mav.addObject("kpiCategory", new KpiCategory());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav = gotoList(mav);
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("kpiCategoryCreate");
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
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("kpiCategoryList");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("kpiCategory", new KpiCategory());	
			request.put("pagingBean", bean);	
			bean.put("kpiCategory", new KpiCategory());			 
			BuckWaResponse response = kpiCategoryService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			 
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="search.htm", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute KpiCategory kpiCategory,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("kpiCategoryList");
		try{		
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);						 
			BuckWaRequest request = new BuckWaRequest();		
			request.put("pagingBean", bean);	
			bean.put("kpiCategory", kpiCategory);			
			BuckWaResponse response = kpiCategoryService.getByOffset(request);
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
	public ModelAndView initEdit(@RequestParam("kpiCategoryId") String kpiCategoryId) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request = new BuckWaRequest();
		request.put("kpiCategoryId", kpiCategoryId);	
		BuckWaResponse response = kpiCategoryService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			KpiCategory kpiCategory = (KpiCategory)response.getResObj("kpiCategory");			
			
			mav.addObject("kpiCategory", kpiCategory);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("kpiCategoryEdit");

		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute KpiCategory kpiCategory, BindingResult result) {		
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav = getEmployeeTypeList(mav);
		try{		
			new KpiCategoryValidator().validate(kpiCategory, result);
			
			if (result.hasErrors()) {			
				mav.setViewName("kpiCategoryEdit");
			}else {
				BuckWaRequest request = new BuckWaRequest();
				request.put("kpiCategory", kpiCategory);
				BuckWaResponse response = kpiCategoryService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					KpiCategory newKpiCategory =  new KpiCategory();
					mav.addObject("kpiCategory",kpiCategory);					
					mav.addObject("successCode", response.getSuccessCode()); 
					mav = gotoList(mav);						
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("kpiCategoryEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("kpiCategoryId") String kpiCategoryId,HttpServletRequest httpRequest,@ModelAttribute KpiCategory kpiCategory,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{
			mav.setViewName("kpiCategoryList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("kpiCategoryId", kpiCategoryId);	
			BuckWaResponse response = kpiCategoryService.deleteById(request);
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				mav.addObject("successCode","S004"); 		 				
			}else {	
				mav.addObject("errorCode", response.getErrorCode()); 
				mav.addObject("pagingBean", bean);	
			}	
			
			// Search Again
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);					 	
			request.put("pagingBean", bean);	
			bean.put("kpiCategory", kpiCategory);		
			response = kpiCategoryService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
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
