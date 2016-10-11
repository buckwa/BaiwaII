package com.buckwa.web.controller.pbp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.admin.StaffRatio;
import com.buckwa.domain.admin.SubjectOfStaff;
import com.buckwa.domain.pam.Section;
import com.buckwa.domain.pam.Staff;
import com.buckwa.service.intf.admin.StaffPartnerService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;

@Controller
@RequestMapping("/admin/pbp/staffpartner")
public class StaffPartnerController {
	private static Logger logger = LoggerFactory.getLogger(StaffPartnerController.class);
	@Autowired
	private StaffPartnerService staffPartnerService; 
	
//	subject_id=fff&year=fff&term=fff#
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView init(String subject_id,String year,String term) {
		Section section = new Section();
		section.setSubjectId(subject_id);
		section.setAcademicYear(year);
		section.setSemester(term);
		
		logger.info("init");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("staffList");
		List<SubjectOfStaff> subjectList = staffPartnerService.getAllList(section);
		mav.addObject("subjectList", subjectList);
		
		return mav;
	}
	
	@RequestMapping(value = "add.htm", method = RequestMethod.GET)
	public ModelAndView add(String section_id) {
		logger.info("add");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("staffadd");
		if(BeanUtils.isEmpty(section_id)){
			mav.addObject(BuckWaConstants.ERROR_CODE,"message.searchNotFound");
		}else{
			SubjectOfStaff res = staffPartnerService.findSectionById(section_id);
			mav.addObject("subject", res);
			List<StaffRatio> listRatio = staffPartnerService.getStaffRatioById(section_id);
			mav.addObject("listRatio", listRatio);
		}
		
		
		return mav;
	}
	@RequestMapping(value = "search.htm", method = RequestMethod.POST)
	public ModelAndView search(String section_id,String firstname ,String surname) {
		logger.info("search firstname : " + firstname);
		logger.info("search surname   : " + surname);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("staffadd");
			
		SubjectOfStaff res = staffPartnerService.findSectionById(section_id);
		mav.addObject("subject", res);
		
		if(BeanUtils.isEmpty(firstname) && BeanUtils.isEmpty(surname)){
			
			mav.addObject(BuckWaConstants.ERROR_CODE,"msg.inputValue");
			
		}else if(BeanUtils.isEmpty(section_id) ){
			mav.addObject(BuckWaConstants.ERROR_CODE,"message.searchNotFound");
		}else{
			
			//List ratio staff
			List<StaffRatio> listRatio = staffPartnerService.getStaffRatioById(section_id);
			mav.addObject("listRatio", listRatio);
			
			// search
			List<Staff> searchList = staffPartnerService.getStaffByName(firstname, surname);
			mav.addObject("searchList", searchList);
		}
		
		
		return mav;
	}
	
	@RequestMapping(value = "addRatio.htm", method = RequestMethod.POST)
	public ModelAndView addRatio(String section_id,String firstname ,String surname,Integer ratio) {
		logger.info("###########");
		logger.info("#####section_id : " + section_id);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("staffadd");
		Staff staff = null;
		
			
		
		if(BeanUtils.isNotEmpty(section_id)){
			SubjectOfStaff res = staffPartnerService.findSectionById(section_id);
			mav.addObject("subject", res);
		}
		if(BeanUtils.isNotEmpty(firstname) &&BeanUtils.isNotEmpty(surname)){
			staff = staffPartnerService.findStaff(firstname, surname);
		}
		if(BeanUtils.isNotEmpty(firstname) && BeanUtils.isNotEmpty(staff) && BeanUtils.isNotEmpty(section_id) ){
			
			//check max 100
			int max = staffPartnerService.maxRatio(section_id);
			if(max + ratio < 100){
			try{	
				staff.setSectionId(section_id);
				staffPartnerService.addStaffRatio(staff, ratio);
				mav.addObject(BuckWaConstants.SUCCESS_CODE, BuckWaConstants.MSGCODE_IMPORT_SUCESS);
			}catch(Exception e){
				mav.addObject(BuckWaConstants.ERROR_CODE, "msg.dupicate");
			}

			}else{
				mav.addObject(BuckWaConstants.ERROR_CODE, "maxratio");
			}
			
		}else{
			mav.addObject(BuckWaConstants.ERROR_CODE, "message.searchNotFound");
		}
		//list ratio
		List<StaffRatio> listRatio = staffPartnerService.getStaffRatioById(section_id);
		mav.addObject("listRatio", listRatio);	
		

		return mav;
	}
	
	@RequestMapping(value = "deleteStaff.htm", method = RequestMethod.GET)
	public void deleteStaff(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,String section_id,String staff_code) throws Exception{
		staffPartnerService.deleteStaff(section_id, staff_code);
		
		httpServletResponse.sendRedirect(httpServletRequest.getRequestURL().toString().replace("deleteStaff.htm", "add.htm?section_id="+section_id));
	}
}
