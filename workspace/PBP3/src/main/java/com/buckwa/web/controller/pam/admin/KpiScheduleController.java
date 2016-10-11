package com.buckwa.web.controller.pam.admin;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.KpiSchedule;
import com.buckwa.domain.validator.pam.KpiScheduleValidator;
import com.buckwa.service.intf.pam.KpiScheduleService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;

/*
@Author : Taechapon Himarat (Su)
@Create : Sep 30, 2012 10:10:16 AM
 */
@Controller
@RequestMapping("/admin/kpiSchedule")
@SessionAttributes(types = KpiSchedule.class)
public class KpiScheduleController {

	private static Logger logger = LoggerFactory.getLogger(KpiScheduleController.class);

	@Autowired
	private KpiScheduleService kpiScheduleService;


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

	@RequestMapping(value="search.htm", method = RequestMethod.GET)
	public ModelAndView searchGet(HttpServletRequest httpRequest ,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
//		mav.setViewName("yearList");
//		try{
//			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);
//			bean.setOffset(offset);
//			//logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
//			BuckWaRequest request = new BuckWaRequest();
//			mav.addObject("year", new Year());
//			request.put("pagingBean", bean);
//			bean.put("year", new Year());
//			BuckWaResponse response = yearService.getByOffset(request);
//			if(response.getStatus()==BuckWaConstants.SUCCESS){
//				logger.info(" Success ");
//				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
//				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
//				bean.setTotalItems(beanReturn.getTotalItems());
//
//				Calendar calendar = Calendar.getInstance();
//				for (Year year : (List<Year>) bean.getCurrentPageItem()) {
//					calendar.set(Calendar.YEAR, Integer.parseInt(year.getName()));
//					year.setName(BuckWaDateUtils.getCustomFormat_thai_from_date(calendar.getTime(), "yyyy"));
//					year.setStartDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(year.getStartDate()));
//					year.setEndDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(year.getEndDate()));
//				}
//
//				mav.addObject("pagingBean", bean);
//			}else {
//				mav.addObject("errorCode", response.getErrorCode());
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//
//			mav.addObject("errorCode", "E001");
//		}
		return mav;
	}

	@RequestMapping(value="search.htm", method = RequestMethod.POST)
	public ModelAndView searchPost(HttpServletRequest httpRequest, @ModelAttribute KpiSchedule kpiSchedule, @ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
//		mav.setViewName("yearList");
//		try{
//			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);
//			bean.setOffset(offset);
//			BuckWaRequest request = new BuckWaRequest();
//			request.put("pagingBean", bean);
//			bean.put("year", year);
//			BuckWaResponse response = yearService.getByOffset(request);
//			if(response.getStatus()==BuckWaConstants.SUCCESS){
//				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
//				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
//				bean.setTotalItems(beanReturn.getTotalItems());
//
//				Calendar calendar = Calendar.getInstance();
//				for (Year temp : (List<Year>) bean.getCurrentPageItem()) {
//					calendar.set(Calendar.YEAR, Integer.parseInt(temp.getName()));
//					temp.setName(BuckWaDateUtils.getCustomFormat_thai_from_date(calendar.getTime(), "yyyy"));
//					temp.setStartDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(temp.getStartDate()));
//					temp.setEndDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(temp.getEndDate()));
//				}
//
//				mav.addObject("pagingBean", bean);
//				mav.addObject("doSearch","true");
//			}else {
//				mav.addObject("errorCode", response.getErrorCode());
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//
//			mav.addObject("errorCode", "E001");
//		}
		return mav;
	}

	private ModelAndView gotoList(ModelAndView mav){
		PagingBean bean = new PagingBean();
		mav.addObject("kpiSchedule", new KpiSchedule());
		mav.addObject("pagingBean", bean);
		mav.setViewName("kpiScheduleList");

		// Search with initial
		int offset = 0;
		bean.setOffset(offset);
		BuckWaRequest request = new BuckWaRequest();
		request.put("pagingBean", bean);
		bean.put("kpiSchedule", new KpiSchedule());
		BuckWaResponse response = kpiScheduleService.getByOffset(request);

		if(response.getStatus()==BuckWaConstants.SUCCESS){
			logger.info(" Success ");
			PagingBean beanReturn = (PagingBean) response.getResObj("pagingBean");
			bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
			bean.setTotalItems(beanReturn.getTotalItems());
			for (KpiSchedule kpiSchedule : (List<KpiSchedule>) bean.getCurrentPageItem()) {
				kpiSchedule.setYearName(BuckWaDateUtils.convertENYearToTHYear(kpiSchedule.getYearName()));
			}
			mav.addObject("pagingBean", bean);
		}
		else {
			mav.addObject("errorCode", response.getErrorCode());
		}
		return mav;
	}


	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView initCreate() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("kpiScheduleCreate");
		mav.addObject("kpiSchedule", new KpiSchedule());
		return mav;
	}

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView submitCreate(HttpServletRequest httpRequest, @ModelAttribute KpiSchedule kpiSchedule, BindingResult result) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		String url = httpRequest.getContextPath() + "/admin/kpiSchedule/init.htm";

		try{
			logger.info(" kpiSchedule before : "+BeanUtils.getBeanString(kpiSchedule));
			new KpiScheduleValidator().validate(kpiSchedule, result);
			if (result.hasErrors()) {
				mav.setViewName("kpiScheduleCreate");
			}
			else {
				BuckWaRequest request = new BuckWaRequest();
				kpiSchedule.setYearName(BuckWaDateUtils.convertTHYearToENYear(kpiSchedule.getYearName()));
				kpiSchedule.setTeacherUploadStartDate1(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherUploadStartDate1Str()));
				kpiSchedule.setTeacherUploadEndDate1(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherUploadEndDate1Str()));
				kpiSchedule.setTeacherEvaluateStartDate1(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherEvaluateStartDate1Str()));
				kpiSchedule.setTeacherEvaluateEndDate1(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherEvaluateEndDate1Str()));
				kpiSchedule.setTeacherUploadStartDate2(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherUploadStartDate2Str()));
				kpiSchedule.setTeacherUploadEndDate2(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherUploadEndDate2Str()));
				kpiSchedule.setTeacherEvaluateStartDate2(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherEvaluateStartDate2Str()));
				kpiSchedule.setTeacherEvaluateEndDate2(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherEvaluateEndDate2Str()));
				kpiSchedule.setStaffUploadStartDate(BuckWaDateUtils.parseDate(kpiSchedule.getStaffUploadStartDateStr()));
				kpiSchedule.setStaffUploadEndDate(BuckWaDateUtils.parseDate(kpiSchedule.getStaffUploadEndDateStr()));
				kpiSchedule.setStaffEvaluateStartDate(BuckWaDateUtils.parseDate(kpiSchedule.getStaffEvaluateStartDateStr()));
				kpiSchedule.setStaffEvaluateEndDate(BuckWaDateUtils.parseDate(kpiSchedule.getStaffEvaluateEndDateStr()));
				logger.info(" kpiSchedule after : "+BeanUtils.getBeanString(kpiSchedule));
				request.put("kpiSchedule", kpiSchedule);

				BuckWaResponse response = kpiScheduleService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					url += "?successCode=" + response.getSuccessCode();
					mav.setView(new RedirectView(url));

				}else {
					mav.addObject("errorCode", response.getErrorCode());
					kpiSchedule.setYearName(BuckWaDateUtils.convertENYearToTHYear(kpiSchedule.getYearName()));
					mav.setViewName("kpiScheduleCreate");
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}


	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("yearName") String yearName) {
		logger.info(" Start  ");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		BuckWaRequest request = new BuckWaRequest();
		request.put("yearName", BuckWaDateUtils.convertTHYearToENYear(yearName));
		
		BuckWaResponse response = kpiScheduleService.getByYearName(request);
		if (response.getStatus()==BuckWaConstants.SUCCESS){
			KpiSchedule kpiSchedule = (KpiSchedule) response.getResObj("kpiSchedule");
			
			kpiSchedule.setYearName(BuckWaDateUtils.convertENYearToTHYear(kpiSchedule.getYearName()));
			kpiSchedule.setTeacherUploadStartDate1Str(BuckWaDateUtils.get_ddMMyyyy_from_date(kpiSchedule.getTeacherUploadStartDate1()));
			kpiSchedule.setTeacherUploadEndDate1Str(BuckWaDateUtils.get_ddMMyyyy_from_date(kpiSchedule.getTeacherUploadEndDate1()));
			kpiSchedule.setTeacherEvaluateStartDate1Str(BuckWaDateUtils.get_ddMMyyyy_from_date(kpiSchedule.getTeacherEvaluateStartDate1()));
			kpiSchedule.setTeacherEvaluateEndDate1Str(BuckWaDateUtils.get_ddMMyyyy_from_date(kpiSchedule.getTeacherEvaluateEndDate1()));
			kpiSchedule.setTeacherUploadStartDate2Str(BuckWaDateUtils.get_ddMMyyyy_from_date(kpiSchedule.getTeacherUploadStartDate2()));
			kpiSchedule.setTeacherUploadEndDate2Str(BuckWaDateUtils.get_ddMMyyyy_from_date(kpiSchedule.getTeacherUploadStartDate2()));
			kpiSchedule.setTeacherEvaluateStartDate2Str(BuckWaDateUtils.get_ddMMyyyy_from_date(kpiSchedule.getTeacherEvaluateStartDate2()));
			kpiSchedule.setTeacherEvaluateEndDate2Str(BuckWaDateUtils.get_ddMMyyyy_from_date(kpiSchedule.getTeacherEvaluateEndDate2()));
			kpiSchedule.setStaffUploadStartDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(kpiSchedule.getStaffUploadStartDate()));
			kpiSchedule.setStaffUploadEndDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(kpiSchedule.getStaffUploadStartDate()));
			kpiSchedule.setStaffEvaluateStartDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(kpiSchedule.getStaffEvaluateStartDate()));
			kpiSchedule.setStaffEvaluateEndDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(kpiSchedule.getStaffEvaluateEndDate()));
			
			mav.addObject("kpiSchedule", kpiSchedule);
		}
		else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode());
		}
		mav.setViewName("kpiScheduleEdit");

		return mav;
	}

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(HttpServletRequest httpRequest, @ModelAttribute KpiSchedule kpiSchedule, BindingResult result) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		String url = httpRequest.getContextPath() + "/admin/kpiSchedule/init.htm";

		try{
			new KpiScheduleValidator().validate(kpiSchedule, result);

			if (result.hasErrors()) {
				mav.setViewName("kpiScheduleEdit");
			}
			else {
				BuckWaRequest request = new BuckWaRequest();

				kpiSchedule.setYearName(BuckWaDateUtils.convertTHYearToENYear(kpiSchedule.getYearName()));
				kpiSchedule.setTeacherUploadStartDate1(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherUploadStartDate1Str()));
				kpiSchedule.setTeacherUploadEndDate1(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherUploadEndDate1Str()));
				kpiSchedule.setTeacherEvaluateStartDate1(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherEvaluateStartDate1Str()));
				kpiSchedule.setTeacherEvaluateEndDate1(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherEvaluateEndDate1Str()));
				kpiSchedule.setTeacherUploadStartDate2(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherUploadStartDate2Str()));
				kpiSchedule.setTeacherUploadEndDate2(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherUploadEndDate2Str()));
				kpiSchedule.setTeacherEvaluateStartDate2(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherEvaluateStartDate2Str()));
				kpiSchedule.setTeacherEvaluateEndDate2(BuckWaDateUtils.parseDate(kpiSchedule.getTeacherEvaluateEndDate2Str()));
				kpiSchedule.setStaffUploadStartDate(BuckWaDateUtils.parseDate(kpiSchedule.getStaffUploadStartDateStr()));
				kpiSchedule.setStaffUploadEndDate(BuckWaDateUtils.parseDate(kpiSchedule.getStaffUploadEndDateStr()));
				kpiSchedule.setStaffEvaluateStartDate(BuckWaDateUtils.parseDate(kpiSchedule.getStaffEvaluateStartDateStr()));
				kpiSchedule.setStaffEvaluateEndDate(BuckWaDateUtils.parseDate(kpiSchedule.getStaffEvaluateEndDateStr()));
				
				request.put("kpiSchedule", kpiSchedule);
				
				BuckWaResponse response = kpiScheduleService.update(request);
				if (response.getStatus()==BuckWaConstants.SUCCESS){
					url += "?successCode=" + response.getSuccessCode();
					mav.setView(new RedirectView(url));
				}
				else {
					mav.addObject("errorCode", response.getErrorCode());
					mav.setViewName("kpiScheduleEdit");
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}


	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest httpRequest, @RequestParam("kpiScheduleId") String kpiScheduleId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		String url = httpRequest.getContextPath() + "/admin/kpiSchedule/init.htm";

		try{
			mav.setViewName("yearList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("kpiScheduleId", kpiScheduleId);
			BuckWaResponse response = kpiScheduleService.delete(request);

			if(response.getStatus()==BuckWaConstants.SUCCESS){
				url += "?successCode=" + response.getSuccessCode();
				mav.setView(new RedirectView(url));
			}
			else {
				mav.addObject("errorCode", response.getErrorCode());
				mav = gotoList(mav);
			}

		}
		catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}

		return mav;
	}
}