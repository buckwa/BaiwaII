package com.buckwa.web.controller.pbp.report;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.domain.pbp.report.MinMaxBean;
import com.buckwa.service.intf.pam.PersonProfileService;
import com.buckwa.service.intf.pbp.HeadService;
import com.buckwa.service.intf.pbp.PBPWorkTypeService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.school.SchoolUtil;
 
 
@Controller
@RequestMapping("/personReport") 
public class PersonReportController { 
	private static Logger logger = LoggerFactory.getLogger(PersonReportController.class);
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
	private HeadService headService;
	
	@Autowired
	private PersonProfileService personProfileService;

	@Autowired
	private PBPWorkTypeService pBPWorkTypeService;
	
//	@RequestMapping(value = "/init.htm", method = RequestMethod.GET)
//	public ModelAndView radarChart() {
		
		@RequestMapping(value="init.htm", method = RequestMethod.GET)
		public ModelAndView radarChart(HttpServletRequest httpRequest ,@ModelAttribute Person person) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		
		try{
		//String selectAcademicYear = person.getAcademicYear();
		String academicYear =  schoolUtil.getCurrentAcademicYear();
		mav.addObject("academicYear", academicYear);
		String round = person.getEvaluateRound();
		logger.info(" Start  academicYear:"+academicYear+"  round:"+round+" employeeType:"+person.getEmployeeType());
		
		BuckWaUser user = BuckWaUtils.getUserFromContext();
		logger.info("viewUserProfile  username :"+user.getUsername());

		BuckWaRequest request = new BuckWaRequest();
		request.put("username", user.getUsername());
		request.put("academicYear", academicYear);

		BuckWaResponse response = personProfileService.getByUsername(request);

		if (response.getStatus() == BuckWaConstants.SUCCESS) {
			 person = (Person) response.getResObj("person");
			 mav.addObject("user", person);	
		}
			 
		//mav.addObject("user", new User());	
		mav.setViewName("personReportInit");
		}catch(Exception ex){
			
		}
		logger.info(" End  ");
		return mav;
	}
 
	
	
	@RequestMapping(value = "/barChart.htm", method = RequestMethod.GET)
	public ModelAndView barChart() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{
			String academicYear =schoolUtil.getCurrentAcademicYear();
			String userName = BuckWaUtils.getUserNameFromContext();
			String facultyName = schoolUtil.getFacutyByUserName(userName, academicYear);
			String departmentName = schoolUtil.getDepartmentByUserName(userName, academicYear);
			
			String facultyCode = schoolUtil.getFacultyCodeByFacultyName(facultyName, academicYear);
			String departmentCode  = schoolUtil.getDepartmentCodeByDepartmentName(departmentName, academicYear);
			
			
		    mav.addObject("departmentName", schoolUtil.getDepartmentByUserName(userName,academicYear));	
		    
		     
		    // Get Mean Value (faculty_code,department_code,academic_year)
			 
			BuckWaRequest request = new BuckWaRequest();
			request.put("academicYear", academicYear);
			request.put("facultyCode", facultyCode);
			request.put("departmentCode", departmentCode);
			
			BuckWaResponse response =headService.getDepartmentMean(request);
		 
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				String mean = (String) response.getResObj("meanValue");
				logger.info(" mean value = "+mean);
				mav.addObject("mean", mean);	
			}
		    
		    
		}catch(Exception ex){
			ex.printStackTrace();
		}
		mav.setViewName("personBarchartInit");
		logger.info(" End  ");
		return mav;
	}
	
	@RequestMapping(value = "/workTypeBarChart.htm", method = RequestMethod.GET)
	public ModelAndView workTypeBarChart() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{
	 
		String academicYear =schoolUtil.getCurrentAcademicYear();
		String userName = BuckWaUtils.getUserNameFromContext();
		String facultyName = schoolUtil.getFacutyByUserName(userName, academicYear);
		String departmentName = schoolUtil.getDepartmentByUserName(userName, academicYear);
		
		String facultyCode = schoolUtil.getFacultyCodeByFacultyName(facultyName, academicYear);
		String departmentCode  = schoolUtil.getDepartmentCodeByDepartmentName(departmentName, academicYear);
		
		
	    mav.addObject("departmentName", schoolUtil.getDepartmentByUserName(userName,academicYear));	
	    logger.info(" ####################academicYear  :"+academicYear);
	    logger.info(" ####################facultyCode   :"+facultyCode);
	    logger.info(" ####################departmentCode:"+departmentCode);
		
	    // Get Mean Value (faculty_code,department_code,academic_year)
	    String mean1 ="";
	    String mean2 ="";
	    String mean3 ="";
	    String mean4 ="";
	    String mean5 ="";
		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("academicYear", academicYear);
		request.put("facultyCode", facultyCode);
		request.put("departmentCode", departmentCode);
		
		
		
		request.put("worktypeCode", "1");
		BuckWaResponse response =headService.getDepartmentMeanByWorkTypeCode(request);	 
		if (response.getStatus() == BuckWaConstants.SUCCESS) {
			MinMaxBean minMaxBean = (MinMaxBean) response.getResObj("minMaxBean");		
			
			mav.addObject("mean1", minMaxBean.getMeanValue());	
			mav.addObject("minValue1", minMaxBean.getMinValue());	
			mav.addObject("maxValue1", minMaxBean.getMaxValue());	
			mav.addObject("minDesc1", minMaxBean.getMinDesc());	
			mav.addObject("maxDesc1", minMaxBean.getMaxDesc());			 
		 
		}
		
		request.put("worktypeCode", "2");
		response =headService.getDepartmentMeanByWorkTypeCode(request);	 
		if (response.getStatus() == BuckWaConstants.SUCCESS) {
			MinMaxBean minMaxBean = (MinMaxBean) response.getResObj("minMaxBean");		
			
			mav.addObject("mean2", minMaxBean.getMeanValue());	
			mav.addObject("minValue2", minMaxBean.getMinValue());	
			mav.addObject("maxValue2", minMaxBean.getMaxValue());	
			mav.addObject("minDesc2", minMaxBean.getMinDesc());	
			mav.addObject("maxDesc2", minMaxBean.getMaxDesc());		
		}	
		
		request.put("worktypeCode", "3");
		response =headService.getDepartmentMeanByWorkTypeCode(request);	 
		if (response.getStatus() == BuckWaConstants.SUCCESS) {
			MinMaxBean minMaxBean = (MinMaxBean) response.getResObj("minMaxBean");		
			
			mav.addObject("mean3", minMaxBean.getMeanValue());	
			mav.addObject("minValue3", minMaxBean.getMinValue());	
			mav.addObject("maxValue3", minMaxBean.getMaxValue());	
			mav.addObject("minDesc3", minMaxBean.getMinDesc());	
			mav.addObject("maxDesc3", minMaxBean.getMaxDesc());		
		}		
		
		request.put("worktypeCode", "4");
		response =headService.getDepartmentMeanByWorkTypeCode(request);	 
		if (response.getStatus() == BuckWaConstants.SUCCESS) {
			MinMaxBean minMaxBean = (MinMaxBean) response.getResObj("minMaxBean");		
			
			mav.addObject("mean4", minMaxBean.getMeanValue());	
			mav.addObject("minValue4", minMaxBean.getMinValue());	
			mav.addObject("maxValue4", minMaxBean.getMaxValue());	
			mav.addObject("minDesc4", minMaxBean.getMinDesc());	
			mav.addObject("maxDesc4", minMaxBean.getMaxDesc());		
		}		
		
		request.put("worktypeCode", "5");
		response =headService.getDepartmentMeanByWorkTypeCode(request);	 
		if (response.getStatus() == BuckWaConstants.SUCCESS) {
			MinMaxBean minMaxBean = (MinMaxBean) response.getResObj("minMaxBean");		
			
			mav.addObject("mean5", minMaxBean.getMeanValue());	
			mav.addObject("minValue5", minMaxBean.getMinValue());	
			mav.addObject("maxValue5", minMaxBean.getMaxValue());
			mav.addObject("minDesc5", minMaxBean.getMinDesc());	
			mav.addObject("maxDesc5", minMaxBean.getMaxDesc());		
			
		
		}	
		
		
		// Get Min Score
		request.put("facultyCode",facultyCode);
		request.put("academicYear",academicYear);
		response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
		String minText = "min";
		if (response.getStatus() == BuckWaConstants.SUCCESS) {
			PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper) response.getResObj("pBPWorkTypeWrapper");
			int i = 1;
			for (PBPWorkType workType : pBPWorkTypeWrapper.getpBPWorkTypeList()) {
				//if (workType.isMinHourCal()) {
					mav.addObject(minText + i, workType.getMinHour());
				//} else {
				//	mav.addObject(minText + i, "0");
				//}
				i++;
			}
		} else {
			// Add Default Value (0)
			for (int i = 0; i <= 5; i++) {
				mav.addObject(minText + i, "0");
			}
		}
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		mav.setViewName("personWorkTypeBarchartInit");
		logger.info(" End  ");
		return mav;
	}

}
