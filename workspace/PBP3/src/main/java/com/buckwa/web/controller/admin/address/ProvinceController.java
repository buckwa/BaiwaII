package com.buckwa.web.controller.admin.address;

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

import com.buckwa.domain.admin.address.Province;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.validator.admin.address.ProvinceValidator;
import com.buckwa.service.intf.CommonService;
import com.buckwa.service.intf.admin.address.ProvinceService;
import com.buckwa.util.BuckWaConstants;

@Controller
@RequestMapping("/admin/address/province")
@SessionAttributes(types = Province.class)
public class ProvinceController {	
	private static Logger logger = LoggerFactory.getLogger(ProvinceController.class);	
	@Autowired
	private ProvinceService provinceService;		
	@Autowired
	private CommonService commonService;		
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		PagingBean bean = new PagingBean();
		mav.addObject("province", new Province());	
		mav.addObject("pagingBean", bean);		
		mav.setViewName("provinceList");
		// Search with initial
		int offset = 0;	
		bean.setOffset(offset);	 
		BuckWaRequest request = new BuckWaRequest();		
		mav.addObject("province", new Province());	
		request.put("pagingBean", bean);	
		bean.put("province", new Province());			 
		BuckWaResponse response = provinceService.getByOffset(request);
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
		mav.setViewName("provinceCreate");
		Province province = new Province();
		 
		mav.addObject("province", province);	
		return mav;
	}		

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createProvince(@ModelAttribute Province province, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{			
			new ProvinceValidator().validate(province, result);			
			if (result.hasErrors()) {				
				mav.setViewName("provinceCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("province", province);
				BuckWaResponse response = provinceService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					mav.addObject("province", new Province());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("provinceCreateSuccess");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("provinceCreate");
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
		mav.setViewName("provinceList");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("province", new Province());	
			request.put("pagingBean", bean);	
			bean.put("province", new Province());			 
			BuckWaResponse response = provinceService.getByOffset(request);
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
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute Province province,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("provinceList");
		try{		
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);						 
			BuckWaRequest request = new BuckWaRequest();		
			request.put("pagingBean", bean);	
			bean.put("province", province);			
			BuckWaResponse response = provinceService.getByOffset(request);
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
	public ModelAndView initEdit(@RequestParam("provinceId") String provinceId) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		BuckWaRequest request = new BuckWaRequest();
		request.put("provinceId", provinceId);	
		BuckWaResponse response = provinceService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			Province province = (Province)response.getResObj("province");			
			 
			mav.addObject("province", province);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("provinceEdit");

		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Province province, BindingResult result) {		
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{		
			new ProvinceValidator().validate(province, result);
			
			if (result.hasErrors()) {			
				mav.setViewName("provinceEdit");
			}else {
				BuckWaRequest request = new BuckWaRequest();
				request.put("province", province);
				BuckWaResponse response = provinceService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					Province newProvince =  new Province();
					 
					mav.addObject("province",province);					
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("provinceEdit");						
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("provinceEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("provinceId") String provinceId,HttpServletRequest httpRequest,@ModelAttribute Province province,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		try{
			mav.setViewName("provinceList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("provinceId", provinceId);	
			BuckWaResponse response = provinceService.deleteById(request);
			
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
			bean.put("province", province);		
			response = provinceService.getByOffset(request);
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
