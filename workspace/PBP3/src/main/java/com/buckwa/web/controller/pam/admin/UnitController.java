package com.buckwa.web.controller.pam.admin;

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
import com.buckwa.domain.pam.Unit;
import com.buckwa.domain.validator.pam.UnitValidator;
import com.buckwa.service.intf.CommonService;
import com.buckwa.service.intf.pam.UnitService;
import com.buckwa.util.BuckWaConstants;

@Controller
@RequestMapping("/admin/unit")
@SessionAttributes(types = Unit.class)
public class UnitController {	
	private static Logger logger = LoggerFactory.getLogger(UnitController.class);	
	@Autowired
	private UnitService unitService;		
	 	
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav = gotoList(mav);
		return mav;
	}	
	
	private ModelAndView gotoList(ModelAndView mav){
		PagingBean bean = new PagingBean();
		mav.addObject("unit", new Unit());	
		mav.addObject("pagingBean", bean);		
		mav.setViewName("unitList");
		// Search with initial
		int offset = 0;	
		bean.setOffset(offset);	 
		BuckWaRequest request = new BuckWaRequest();		
		mav.addObject("unit", new Unit());	
		request.put("pagingBean", bean);	
		bean.put("unit", new Unit());			 
		BuckWaResponse response = unitService.getByOffset(request);
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
		mav.setViewName("unitCreate");
		Unit unit = new Unit();
		 
		mav.addObject("unit", unit);	
		return mav;
	}		

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createUnit(@ModelAttribute Unit unit, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{			
			new UnitValidator().validate(unit, result);			
			if (result.hasErrors()) {				
				mav.setViewName("unitCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("unit", unit);
				BuckWaResponse response = unitService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					mav.addObject("unit", new Unit());
					mav.addObject("successCode", response.getSuccessCode()); 
					mav = gotoList(mav);
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("unitCreate");
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
		mav.setViewName("unitList");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("unit", new Unit());	
			request.put("pagingBean", bean);	
			bean.put("unit", new Unit());			 
			BuckWaResponse response = unitService.getByOffset(request);
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
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute Unit unit,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("unitList");
		try{		
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);						 
			BuckWaRequest request = new BuckWaRequest();		
			request.put("pagingBean", bean);	
			bean.put("unit", unit);			
			BuckWaResponse response = unitService.getByOffset(request);
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
	public ModelAndView initEdit(@RequestParam("unitId") String unitId) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request = new BuckWaRequest();
		request.put("unitId", unitId);	
		BuckWaResponse response = unitService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			Unit unit = (Unit)response.getResObj("unit");			
			 
			mav.addObject("unit", unit);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("unitEdit");

		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Unit unit, BindingResult result) {		
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{		
			new UnitValidator().validate(unit, result);
			
			if (result.hasErrors()) {			
				mav.setViewName("unitEdit");
			}else {
				BuckWaRequest request = new BuckWaRequest();
				request.put("unit", unit);
				BuckWaResponse response = unitService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					Unit newUnit =  new Unit();
					mav.addObject("unit",unit);					
					mav.addObject("successCode", response.getSuccessCode()); 
					mav = gotoList(mav);						
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("unitEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("unitId") String unitId,HttpServletRequest httpRequest,@ModelAttribute Unit unit,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{
			mav.setViewName("unitList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("unitId", unitId);	
			BuckWaResponse response = unitService.deleteById(request);
			
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
			bean.put("unit", unit);		
			response = unitService.getByOffset(request);
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
