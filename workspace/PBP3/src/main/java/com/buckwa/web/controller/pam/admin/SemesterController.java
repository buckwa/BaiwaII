package com.buckwa.web.controller.pam.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.buckwa.domain.pam.Semester;
import com.buckwa.domain.pam.Year;
import com.buckwa.domain.validator.pam.SemesterValidator;
import com.buckwa.service.intf.pam.SemesterService;
import com.buckwa.service.intf.pam.YearService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;

@Controller
@RequestMapping("/admin/semester")
@SessionAttributes(value = {"yearList"})
public class SemesterController {	
	private static Logger logger = LoggerFactory.getLogger(SemesterController.class);	
	@Autowired
	private SemesterService semesterService;		
 
	@Autowired
	private YearService yearService;	
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList(HttpServletRequest httpRequest) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	 
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		
		return gotoList(mav, null);
	}	
	
	private ModelAndView gotoList(ModelAndView mav, String yearId){
		BuckWaRequest request = new BuckWaRequest();
		mav.setViewName("semesterList");  
		try{
			BuckWaResponse response = yearService.getAll();
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				List<Year> yearList = (List<Year>)response.getResObj("yearList");
				Calendar calendar = Calendar.getInstance();
				for (Year year : yearList) {
					calendar.set(Calendar.YEAR, Integer.parseInt(year.getName()));
					year.setName(BuckWaDateUtils.getCustomFormat_thai_from_date(calendar.getTime(), "yyyy"));
				}
				mav.addObject("yearList", yearList);
				
				if(yearList!=null&&yearList.size()>0){
					Year defaultYear = yearList.get(0);
					if(yearId==null || yearId.isEmpty()){
						yearId = Long.toString(defaultYear.getYearId());// Default Year is first row
					}
					request.put("yearId", yearId);
					response = semesterService.getByYearId(request);
					
					List<Semester> semesterList = (List<Semester>) response.getResObj("semesterList");
					for (Semester semester : semesterList) {
						semester.setStartDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(semester.getStartDate()));
						semester.setEndDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(semester.getEndDate()));
					}
					
					Semester semester = new Semester();
					semester.setYearId(Long.valueOf(yearId));
					
					mav.addObject("semester", semester);
					mav.addObject("semesterList", semesterList);
					mav.addObject("doSearch","true");
				}
			}else {	 
				mav.addObject(BuckWaConstants.ERROR_CODE, response.getErrorCode()); 
				return mav;
			} 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001); 
		}
		return mav;
	}
	
	@RequestMapping(value="initByYear.htm", method = RequestMethod.GET)
	public ModelAndView initByCat(HttpServletRequest httpRequest,@RequestParam("yearId") String yearId ) {
		logger.info(" Start  ");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		
		if (StringUtils.hasText(httpRequest.getParameter("successCode"))) {
			mav.addObject("successCode", httpRequest.getParameter("successCode"));
		}
		
		return gotoList(mav, yearId);
	}	
	
	
	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView initCreate(HttpServletRequest httpRequest, @RequestParam("yearId") String yearId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		Semester semester = new Semester();
		semester.setYearId(Long.parseLong(yearId));
		mav.addObject("semester", semester);
		mav.setViewName("semesterCreate");
		return mav;
	}		

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView submitCreate(HttpServletRequest httpRequest, @ModelAttribute Semester semester, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		String url = httpRequest.getContextPath() + "/admin/semester/initByYear.htm";
		
		try{			
			new SemesterValidator().validate(semester, result);			
			if (result.hasErrors()) {				
				mav.setViewName("semesterCreate");
			}else {
				
				semester.setStartDate(BuckWaDateUtils.parseTimeStamp(semester.getStartDateStr()));
				semester.setEndDate(BuckWaDateUtils.parseTimeStamp(semester.getEndDateStr()));
				
				BuckWaRequest request = new BuckWaRequest();
				request.put("semester", semester);
				BuckWaResponse response = semesterService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
					mav.addObject("semester", semester);
					
					logger.info("Year ID : >> "+semester.getYearId());
					
					url += "?successCode=" + response.getSuccessCode() + "&yearId=" + semester.getYearId();
					mav.setView(new RedirectView(url));
				}else {
					mav.addObject(BuckWaConstants.ERROR_CODE, response.getErrorCode()); 
					mav.setViewName("semesterCreate");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001); 
		}
		return mav;
	}
	
	@RequestMapping(value="search.htm", method = RequestMethod.GET)
	public ModelAndView searchGet(HttpServletRequest httpRequest ,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("semesterList");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();		
			mav.addObject("semester", new Semester());	
			request.put("pagingBean", bean);	
			bean.put("semester", new Semester());			 
			BuckWaResponse response = semesterService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {				
				mav.addObject(BuckWaConstants.ERROR_CODE, response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			 
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="search.htm", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute Semester semester,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("semesterList");
		try{		
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);						 
			BuckWaRequest request = new BuckWaRequest();		
			request.put("pagingBean", bean);	
			bean.put("semester", semester);			
			BuckWaResponse response = semesterService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);	
				mav.addObject("doSearch","true");
			}else {			
				mav.addObject(BuckWaConstants.ERROR_CODE, response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			 
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("semesterId") String semesterId) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request = new BuckWaRequest();
		try{
		request.put("semesterId", semesterId);
		BuckWaResponse response = semesterService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			Semester semester = (Semester)response.getResObj("semester");
			semester.setStartDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(semester.getStartDate()));
			semester.setEndDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(semester.getEndDate()));
			mav.addObject("semester", semester);
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject(BuckWaConstants.ERROR_CODE, response.getErrorCode());
		}	
		mav.setViewName("semesterEdit");
	}catch(Exception ex){
		ex.printStackTrace();
		mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001); 
	}
		return mav;
	}	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(HttpServletRequest httpRequest, @ModelAttribute Semester semester, BindingResult result) {		
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		String url = httpRequest.getContextPath() + "/admin/semester/initByYear.htm";
		
		try{		
			new SemesterValidator().validate(semester, result);
			
			if (result.hasErrors()) {			
				mav.setViewName("semesterEdit");
			}else {
				BuckWaRequest request = new BuckWaRequest();
				
				semester.setStartDate(BuckWaDateUtils.parseTimeStamp(semester.getStartDateStr()));
				semester.setEndDate(BuckWaDateUtils.parseTimeStamp(semester.getEndDateStr()));
				
				request.put("semester", semester);
				BuckWaResponse response = semesterService.update(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){				
					 
					mav.addObject("semester",semester);
					
					logger.info("Year ID : >> "+semester.getYearId());
					
					mav = gotoList(mav, Long.toString(semester.getYearId()).trim());
					url += "?successCode=" + response.getSuccessCode() + "&yearId=" + semester.getYearId();
					mav.setView(new RedirectView(url));
				}else {
					mav.addObject(BuckWaConstants.ERROR_CODE, response.getErrorCode()); 
					mav.setViewName("semesterEdit");
				}
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest httpRequest, @RequestParam("semesterId") String semesterId, @RequestParam("yearId") String yearId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		String url = httpRequest.getContextPath() + "/admin/semester/initByYear.htm";
		
		try{
			mav.setViewName("semesterList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("semesterId", semesterId);	
			BuckWaResponse response = semesterService.deleteById(request);
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				url += "?" + BuckWaConstants.SUCCESS_CODE + "=" + response.getSuccessCode() + "&yearId=" + yearId;
				mav.setView(new RedirectView(url));
			}
			else {
				url += "?" + BuckWaConstants.ERROR_CODE + "=" + response.getErrorCode() + "&yearId=" + yearId;
				mav.setView(new RedirectView(url));
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001); 
		}
	
		return mav;
	}	
	
	@RequestMapping(value="loadData/semesterInYear.htm", method = RequestMethod.GET)
	public void loadSemesterInYear(HttpServletRequest httpRequest , HttpServletResponse httpResponse, @RequestParam("yearId") String yearId ) {
		logger.info(" Start Load Semester of Year >> "+yearId);
		
		BuckWaRequest request = new BuckWaRequest();		
		request.put("yearId", yearId);
		BuckWaResponse response = semesterService.getByYearId(request);
		
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			List<Semester> semesterList = (List)response.getResObj("semesterList");
			
			PrintWriter out;
			try {
				out = httpResponse.getWriter();
			//	JSONObject obj =new JSONObject();
				
			//	for (int i=0 ;i<semesterList.size(); i++) {
			////		obj.put(semesterList.get(i).getName(), semesterList.get(i).getSemesterId());
			//	}
				
			//	out.print(obj);
			 //   out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
	}
}
