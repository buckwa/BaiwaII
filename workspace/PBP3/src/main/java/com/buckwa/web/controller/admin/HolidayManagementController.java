package com.buckwa.web.controller.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.servlet.view.RedirectView;

import com.buckwa.domain.admin.Holiday;
import com.buckwa.domain.admin.HolidayCriteria;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Year;
import com.buckwa.service.intf.admin.HolidayService;
import com.buckwa.service.intf.pam.YearService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;

@Controller
@RequestMapping("/admin/holiday")
@SessionAttributes(types = Holiday.class)
public class HolidayManagementController {	
	private static Logger logger = LoggerFactory.getLogger(HolidayManagementController.class);
	
	@Autowired
	private HolidayService holidayService;	
	
	@Autowired
	private YearService yearService;
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList() {
		if (logger.isInfoEnabled()) {
			logger.info(this.getClass().toString() + "-init-List");
		}
		ModelAndView mav = new ModelAndView();

		mav = gotoList(mav);

		mav.setViewName("holidayList");
		return mav;
	}	
	
	private ModelAndView gotoList(ModelAndView mav) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+"-goToList-");
		}
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.LEAVE_INIT);
		
		PagingBean bean = new PagingBean();

		mav.addObject("holidayCriteria", new HolidayCriteria());	
		mav.addObject("pagingBean", bean);
		mav.setViewName("holidayList");	

		int offset =0;
		bean.setOffset(offset);					
		BuckWaRequest request = new BuckWaRequest();
		request.put("pagingBean", bean);	
		
		bean.put("holiday", new HolidayCriteria());
		
		BuckWaResponse response =  yearService.getAll();
		List<Year> years = null;
		
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			years= (List)response.getResObj("yearList");
			
			response = holidayService.getByOffset(request);

			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);	
				if(years!=null){
					Calendar calendar = Calendar.getInstance();
					for (Year year : years) {
						calendar.set(Calendar.YEAR, Integer.parseInt(year.getName()));
						year.setName(BuckWaDateUtils.getCustomFormat_thai_from_date(calendar.getTime(), "yyyy"));
					}
					mav.addObject("yearList", years);
				}	
			}
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView initCreate() {			
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+ "-Start-init");
		}
		ModelAndView mav = new ModelAndView();
		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.LEAVE_INIT);
		
		mav.setViewName("holidayCreate");		
		HolidayCriteria holidayCriteria = new HolidayCriteria();
		
		BuckWaResponse response =  yearService.getAll();
		List<Year> years = null;
		
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			years= (List)response.getResObj("yearList");
			mav.addObject("holidayCriteria", holidayCriteria);	
			if(years!=null){
				mav.addObject("yearList", years);
			}
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
		}		
	
		return mav;
	}	

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createHoliday(HttpServletRequest httpRequest, @ModelAttribute HolidayCriteria holidayCriteria, BindingResult result) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+ "-- create --");
		}
		ModelAndView mav = new ModelAndView();
		try{		
//			new HolidayValidator().validate(holiday, result);			
			if (result.hasErrors()) {				
				mav.setViewName("holidayCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("holidayCriteria", holidayCriteria);
				
				BuckWaResponse response =  yearService.getAll();
				List<Year> years = null;
				
				years= (List)response.getResObj("yearList");
				mav.addObject("holidayCriteria", holidayCriteria);	
				if(years!=null){
					Calendar calendar = Calendar.getInstance();
					for (Year year : years) {
						calendar.set(Calendar.YEAR, Integer.parseInt(year.getName()));
						year.setName(BuckWaDateUtils.getCustomFormat_thai_from_date(calendar.getTime(), "yyyy"));
					}
					mav.addObject("yearList", years);
				}
				
				response = holidayService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					logger.info("  Success");					
					mav.addObject("holidayCriteria", new HolidayCriteria());
					mav.addObject("successCode", response.getSuccessCode()); 
					gotoList(mav);
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("holidayCreate");
				}
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}


	@RequestMapping(value="search.htm" )
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute HolidayCriteria holiday) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+ "-Search-");
		}
		ModelAndView mav = new ModelAndView();	
		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.LEAVE_INIT);
		
		mav.setViewName("holidayList");
		
		try{			
			PagingBean bean = new PagingBean();
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);						
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);	
			bean.put("holiday", holiday);
			
			BuckWaResponse response =  yearService.getAll();
			List<Year> years = null;
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				years= (List)response.getResObj("yearList");
				
				response = holidayService.getByOffset(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
					bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
					bean.setTotalItems(beanReturn.getTotalItems());				 
					mav.addObject("pagingBean", bean);		
					mav.addObject("doSearch","true");
					if(years!=null){
						Calendar calendar = Calendar.getInstance();
						for (Year year : years) {
							calendar.set(Calendar.YEAR, Integer.parseInt(year.getName()));
							year.setName(BuckWaDateUtils.getCustomFormat_thai_from_date(calendar.getTime(), "yyyy"));
						}
						mav.addObject("yearList", years);
					}	
					mav.addObject("holidayCriteria", holiday);
				}
			}else{
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("holidayId") String holidayId) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+"-EDIT-init");
		}
		ModelAndView mav = new ModelAndView();
		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.LEAVE_INIT);
		
		BuckWaRequest request = new BuckWaRequest();

		request.put("holidayId", holidayId);	
		
		BuckWaResponse response =  yearService.getAll();
		List<Year> years = null;
		
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			years= (List)response.getResObj("yearList");
		
			response = holidayService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){		
				Holiday holiday = (Holiday)response.getResObj("holiday");			
				mav.addObject("holiday", holiday);
				if(years!=null){
					mav.addObject("yearList", years);
				}	
			}
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("holidayEdit");

		return mav;
	}	
	
	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute Holiday holiday, BindingResult result) {		
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+"-EDIT-");
		}
		ModelAndView mav = new ModelAndView();

		try{			 
//			new HolidayValidator().validate(holiday, result);	
			mav.setViewName("holidayEdit");
			if (!result.hasErrors()) {			 
				BuckWaResponse response =  yearService.getAll();
				List<Year> years = null;
				
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					years= (List)response.getResObj("yearList");
					
					BuckWaRequest request = new BuckWaRequest();
					request.put("holiday", holiday);
					response = holidayService.update(request);
					if(years!=null){
						mav.addObject("yearList", years);
					}
					if(response.getStatus()==BuckWaConstants.SUCCESS){					 				
						mav.addObject("successCode", response.getSuccessCode()); 				
						mav.setView(new RedirectView("/PAM/admin/holiday/init.htm"));	
					}else{
						mav.addObject("errorCode", response.getErrorCode()); 
					}
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	

	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("holidayId") String holidayId,HttpServletRequest httpRequest,@ModelAttribute HolidayCriteria holiday,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.LEAVE_INIT);
		
		mav.setViewName("holidayList");
		BuckWaRequest request = new BuckWaRequest();
		request.put("holidayId", holidayId);	
		BuckWaResponse response = holidayService.deleteById(request);
		
 
		if(response.getStatus()==BuckWaConstants.SUCCESS){					
			mav.addObject("successCode","S004"); 		 				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
			mav.addObject("pagingBean", bean);	
		}	
 
		int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
		bean.setOffset(offset);					 	
		request.put("pagingBean", bean);	
		bean.put("holiday", holiday);			 
		 response = holidayService.getByOffset(request);
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
