package com.buckwa.web.controller.webboard;

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
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.util.Category;
import com.buckwa.domain.validator.util.CategoryValidator;
import com.buckwa.service.intf.webboard.WebboardCategoryService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;

@Controller
@RequestMapping("/admin/webboard/category")
@SessionAttributes(types = Category.class)
public class AdminWebboardCategoryController {
	private static Logger logger = LoggerFactory.getLogger(AdminWebboardCategoryController.class);
	@Autowired
	private WebboardCategoryService  webboardCategoryService;
	
	@ModelAttribute("category") 
	public Category category() { 
		logger.info(" Call @ModelAttribute Category ");
		return new Category(); 
	} 	
	
	@RequestMapping("init.htm")
	public ModelAndView init( ) {
		logger.info(" # CategoryManagementController.init ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("adminwebboardcategoryList");		
		try{	
			Category category = new Category();		 	 	
			PagingBean bean = new PagingBean(); 
			mav.addObject("pagingBean", bean);	
			mav.addObject("category", category);	 
			int offset = 0;
			bean.setOffset(offset);	
			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("category", new Category());	
			request.put("pagingBean", bean);	
			bean.put("category", new Category());			 
			BuckWaResponse response = webboardCategoryService.getByOffset(request);
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
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("adminwebboardcategoryCreate");
		try{	
			Category category = new Category();		 
			mav.addObject("category", category);	
		
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createCategory(@ModelAttribute Category category, BindingResult result) {	
		ModelAndView mav = new ModelAndView();
		try{			
			logger.info(" createCategory category xx: "+BeanUtils.getBeanString(category));
			new CategoryValidator().validate(category, result);			
			if (result.hasErrors()) {				
				mav.setViewName("adminwebboardcategoryCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("category", category);
				logger.info(" createCategory category:"+BeanUtils.getBeanString(category));
				BuckWaResponse response = webboardCategoryService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					mav.addObject("category", new Category());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("adminwebboardcategoryCreateSuccess");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("adminwebboardcategoryCreate");
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
		logger.info(" # CategoryManagementController.search");
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("adminwebboardcategoryList");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("category", new Category());	
			request.put("pagingBean", bean);	
			bean.put("category", new Category());			 
			BuckWaResponse response = webboardCategoryService.getByOffset(request);
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
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute Category category,@ModelAttribute PagingBean bean) {
		logger.info(" # CategoryManagementController.search");
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("adminwebboardcategoryList");
		try{			
			//PagingBean bean = new PagingBean();
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);			
			//logger.info(" PagingBean:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			request.put("pagingBean", bean);	
			bean.put("category", category);
		 
			BuckWaResponse response = webboardCategoryService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);	
				mav.addObject("doSearch","true");
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
	public ModelAndView initEdit(@RequestParam("categoryId") String categoryId) {
		logger.info(" # CategoryManagementController.initEdit categoryId:"+categoryId);
		ModelAndView mav = new ModelAndView();
		try{	
		BuckWaRequest request = new BuckWaRequest();
		request.put("categoryId", categoryId);	
		BuckWaResponse response = webboardCategoryService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			logger.info("  Success");
			Category category = (Category)response.getResObj("category");
			logger.info(" category return :"+BeanUtils.getBeanString(category));
			mav.addObject("category", category);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("adminwebboardcategoryEdit");
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Category category, BindingResult result) {		
		logger.info(" # CategoryManagementController.submitEdit category:"+BeanUtils.getBeanString(category));
		ModelAndView mav = new ModelAndView();
		try{
			logger.info(" category:"+BeanUtils.getBeanString(category));
			new CategoryValidator().validate(category, result);
			
			if (result.hasErrors()) {
				logger.info("  Validate Error");
				mav.setViewName("adminwebboardcategoryEdit");
			}else {
	
				logger.info("  Validate Success , Do create Category ");
				BuckWaRequest request = new BuckWaRequest();
				request.put("category", category);
				BuckWaResponse response = webboardCategoryService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					logger.info("  Success");					
					mav.addObject("category", new Category());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("adminwebboardcategoryEdit");					
					
				}else {
					logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("adminwebboardcategoryEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("categoryId") String categoryId,HttpServletRequest httpRequest,@ModelAttribute Category category,@ModelAttribute PagingBean bean) {
		logger.info(" # CategoryManagementController.delete categoryId:"+categoryId);
		ModelAndView mav = new ModelAndView();		
		try{
			mav.setViewName("adminwebboardcategoryList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("categoryId", categoryId);	
			BuckWaResponse response = webboardCategoryService.deleteById(request);
			
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
			bean.put("category", category);		
			response = webboardCategoryService.getByOffset(request);
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