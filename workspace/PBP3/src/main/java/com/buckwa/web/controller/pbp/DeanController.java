package com.buckwa.web.controller.pbp;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.web.util.AcademicYearUtil;
 
 
@Controller
@RequestMapping("/dean") 
public class DeanController { 
	private static Logger logger = LoggerFactory.getLogger(DeanController.class);
 
	
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	@Autowired
	private FacultyService facultyService;
	
	@RequestMapping(value = "/facultyReport.htm", method = RequestMethod.GET)
	public ModelAndView facultyReport() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		//mav.addObject("user", new User());	
		
		try{
		BuckWaRequest request = new BuckWaRequest();
		
		BuckWaUser user = BuckWaUtils.getUserFromContext();
		logger.info("viewUserProfile  username :"+user.getUsername());
		
		request.put("username", user.getUsername());
		request.put("academicYear", academicYearUtil.getAcademicYear());
		
		BuckWaResponse response  = facultyService.getFacultyByDeanUserNameandYear(request);
		if (response.getStatus() == BuckWaConstants.SUCCESS) {
		    Faculty faculty = (Faculty)response.getResObj("faculty");
		    mav.addObject("faculty", faculty); 
		}
		
		mav.setViewName("facultyReport");
		 
	} catch(Exception ex) {
		ex.printStackTrace();
		mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
	}

		logger.info(" End  ");
		return mav;
	}
 
}
