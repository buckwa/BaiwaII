package com.buckwa.web.controller.pam.admin;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Year;
import com.buckwa.domain.validator.pam.YearValidator;
import com.buckwa.service.intf.pam.YearService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;

@Controller
@RequestMapping("/admin/year")
@SessionAttributes(types = Year.class)
public class YearController {	
	private static Logger logger = LoggerFactory.getLogger(YearController.class);	
	@Autowired
	private YearService yearService;		
	 	
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList(HttpServletRequest httpRequest) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav = gotoList(mav);
		
		if (StringUtils.hasText(httpRequest.getParameter("successCode"))) {
			mav.addObject("successCode", httpRequest.getParameter("successCode"));
		}
		
		return mav;
	}	
	
	private ModelAndView gotoList(ModelAndView mav){
		PagingBean bean = new PagingBean();
		mav.addObject("year", new Year());	
		mav.addObject("pagingBean", bean);		
		mav.setViewName("yearList");
		// Search with initial
		int offset = 0;	
		bean.setOffset(offset);	 
		BuckWaRequest request = new BuckWaRequest();		
		mav.addObject("year", new Year());	
		request.put("pagingBean", bean);	
		bean.put("year", new Year());			 
		BuckWaResponse response = yearService.getByOffset(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			logger.info(" Success ");
			PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
			bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
			bean.setTotalItems(beanReturn.getTotalItems());
			Calendar calendar = Calendar.getInstance();
			for (Year year : (List<Year>) bean.getCurrentPageItem()) {
				calendar.set(Calendar.YEAR, Integer.parseInt(year.getName()));
				year.setName(BuckWaDateUtils.getCustomFormat_thai_from_date(calendar.getTime(), "yyyy"));
				year.setStartDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(year.getStartDate()));
				year.setEndDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(year.getEndDate()));
			}
			mav.addObject("pagingBean", bean);				
		}else {	 
			mav.addObject("errorCode", response.getErrorCode()); 
		}				
		return mav;
	}
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView initCreate() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("yearCreate");
		Year year = new Year();
		//create year list
		
		mav.addObject("year", year);	
		return mav;
	}	
	


	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView submitCreate(HttpServletRequest httpRequest, @ModelAttribute Year year, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		String url = httpRequest.getContextPath() + "/admin/year/init.htm";
		
		try{		
			logger.info(" year before : "+BeanUtils.getBeanString(year));
			new YearValidator().validate(year, result);			
			if (result.hasErrors()) {				
				mav.setViewName("yearCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				Calendar calendar = Calendar.getInstance(BuckWaDateUtils.thaiLocale);
				calendar.set(Calendar.YEAR, Integer.parseInt(year.getName()));
				year.setName(String.valueOf(BuckWaDateUtils.getYear(calendar.getTime())));
				year.setStartDate(BuckWaDateUtils.parseDate(year.getStartDateStr()));
				year.setEndDate(BuckWaDateUtils.parseDate(year.getEndDateStr()));
				logger.info(" year after : "+BeanUtils.getBeanString(year));
				request.put("year", year);
				
				BuckWaResponse response = yearService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					url += "?successCode=" + response.getSuccessCode();
					mav.setView(new RedirectView(url));
					
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("yearCreate");
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
		mav.setViewName("yearList");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("year", new Year());	
			request.put("pagingBean", bean);	
			bean.put("year", new Year());			 
			BuckWaResponse response = yearService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				
				Calendar calendar = Calendar.getInstance();
				for (Year year : (List<Year>) bean.getCurrentPageItem()) {
					calendar.set(Calendar.YEAR, Integer.parseInt(year.getName()));
					year.setName(BuckWaDateUtils.getCustomFormat_thai_from_date(calendar.getTime(), "yyyy"));
					year.setStartDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(year.getStartDate()));
					year.setEndDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(year.getEndDate()));
				}
				
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
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute Year year,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("yearList");
		try{		
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);						 
			BuckWaRequest request = new BuckWaRequest();		
			request.put("pagingBean", bean);	
			bean.put("year", year);			
			BuckWaResponse response = yearService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				
				Calendar calendar = Calendar.getInstance();
				for (Year temp : (List<Year>) bean.getCurrentPageItem()) {
					calendar.set(Calendar.YEAR, Integer.parseInt(temp.getName()));
					temp.setName(BuckWaDateUtils.getCustomFormat_thai_from_date(calendar.getTime(), "yyyy"));
					temp.setStartDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(temp.getStartDate()));
					temp.setEndDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(temp.getEndDate()));
				}
				
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
	public ModelAndView initEdit(@RequestParam("yearId") String yearId) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request = new BuckWaRequest();
		request.put("yearId", yearId);	
		BuckWaResponse response = yearService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			Year year = (Year)response.getResObj("year");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.parseInt(year.getName()));
			year.setName(BuckWaDateUtils.getCustomFormat_thai_from_date(calendar.getTime(), "yyyy"));
			year.setStartDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(year.getStartDate()));
			year.setEndDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(year.getEndDate()));
			mav.addObject("year", year);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		mav.setViewName("yearEdit");

		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(HttpServletRequest httpRequest, @ModelAttribute Year year, BindingResult result) {		
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		String url = httpRequest.getContextPath() + "/admin/year/init.htm";
		
		try{		
			new YearValidator().validate(year, result);
			
			if (result.hasErrors()) {			
				mav.setViewName("yearEdit");
			}else {
				BuckWaRequest request = new BuckWaRequest();
				Calendar calendar = Calendar.getInstance(BuckWaDateUtils.thaiLocale);
				calendar.set(Calendar.YEAR, Integer.parseInt(year.getName()));
				year.setName(String.valueOf(BuckWaDateUtils.getYear(calendar.getTime())));
				year.setStartDate(BuckWaDateUtils.parseDate(year.getStartDateStr()));
				year.setEndDate(BuckWaDateUtils.parseDate(year.getEndDateStr()));
				request.put("year", year);
				BuckWaResponse response = yearService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					url += "?successCode=" + response.getSuccessCode();
					mav.setView(new RedirectView(url));
				}else {				
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("yearEdit");
				}		
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("yearId") String yearId,HttpServletRequest httpRequest,@ModelAttribute Year year,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		String url = httpRequest.getContextPath() + "/admin/year/init.htm";
		
		try{
			mav.setViewName("yearList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("yearId", yearId);	
			BuckWaResponse response = yearService.deleteById(request);
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				url += "?successCode=" + response.getSuccessCode();
				mav.setView(new RedirectView(url));
				
			}else {	
				mav.addObject("errorCode", response.getErrorCode()); 
				mav.addObject("pagingBean", bean);	
			}	
			
			// Search Again
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);					 	
			request.put("pagingBean", bean);	
			bean.put("year", year);		
			response = yearService.getByOffset(request);
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
