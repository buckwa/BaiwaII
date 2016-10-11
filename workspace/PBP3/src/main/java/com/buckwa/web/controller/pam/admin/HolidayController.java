package com.buckwa.web.controller.pam.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.admin.Holiday;
import com.buckwa.domain.admin.HolidayCriteria;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.service.intf.admin.HolidayService;
import com.buckwa.service.intf.pam.YearService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 18, 2012 7:01:40 PM
 */

@Controller
@RequestMapping("/pam/admin/holiday")
@SessionAttributes(types = Holiday.class)
public class HolidayController {
	private static Logger logger = LoggerFactory.getLogger(HolidayController.class);
	
	
	@Autowired
	private HolidayService holidayService;
	
	@Autowired
	private YearService yearService;
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView init() {
		if (logger.isInfoEnabled()) {
			logger.info(this.getClass().toString() + "-init-List");
		}
		ModelAndView mav = new ModelAndView();

		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+"-goToList-");
		}
		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		mav.setViewName("holidayPersonList");
		
		PagingBean bean = new PagingBean();
		HolidayCriteria holiday = new HolidayCriteria();
		int yearCurrent = BuckWaDateUtils.getYearCurrent();
		/*BuckWaResponse  response = yearService.getYearCurrent();
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			yearCurrent = (Integer)response.getResObj("year");
		}*/
		
		if(yearCurrent>0){
			holiday.setYearId(String.valueOf(yearCurrent));
			holiday.setYearStr(String.valueOf(yearCurrent+543));
			bean.put("holiday", holiday);
			
			mav.addObject("holiday", holiday);	
			mav.addObject("pagingBean", bean);

			int offset =0;
			bean.setOffset(offset);					
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);	
			
			BuckWaResponse  response = holidayService.getByOffset(request);
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);	
				
			}else {
				mav.addObject("errorCode", response.getErrorCode()); 
			}
			
		}
		
		return mav;
	}	
	
	@RequestMapping(value="search.htm" )
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute HolidayCriteria holiday) {
		if(logger.isInfoEnabled()){
			logger.info(this.getClass().toString()+ "-Search-");
		}
		ModelAndView mav = new ModelAndView();	
		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		
		mav.setViewName("holidayPersonList");
		try{			
			int yearCurrent = BuckWaDateUtils.getYearCurrent();
			holiday.setYearId(String.valueOf(yearCurrent));
			holiday.setYearStr(String.valueOf(yearCurrent+543));
			PagingBean bean = new PagingBean();
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);						
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);	
			bean.put("holiday", holiday);
			mav.addObject("holiday", holiday);	
			BuckWaResponse response = holidayService.getByOffset(request);
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
}

