package com.buckwa.web.controller.pbp.person;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.dao.intf.pbp.FacultyDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pbp.report.PersonYearReport;
import com.buckwa.service.intf.pam.PersonProfileService;
import com.buckwa.service.intf.pbp.AcademicKPIUserMappingService;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.service.intf.pbp.HeadService;
import com.buckwa.service.intf.pbp.PBPWorkTypeService;
import com.buckwa.service.intf.pbp.PersonTimeTableService;
import com.buckwa.service.intf.webboard.WebboardTopicService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;

@Controller
@RequestMapping("/personYearReport")
@SessionAttributes({"personYearReport"} ) 
 
public class PersonYearReportController {	
	private static Logger logger = LoggerFactory.getLogger(PersonYearReportController.class);	 
	@Autowired
	private HeadService headService;	
	
	@Autowired
	private AcademicKPIUserMappingService academicKPIUserMappingService;
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
	private FacultyDao  facultyDao;
	
	@Autowired
	private PBPWorkTypeService pBPWorkTypeService;
	
	@Autowired
	private WebboardTopicService  webboardTopicService;
	
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	@Autowired
	private FacultyService facultyService;
	
	@Autowired
	private PersonTimeTableService personTimeTableService;
	
	@Autowired
	private PersonProfileService personProfileService;
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView init(@RequestParam("academicYearSelect") String academicYearSelect) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("personYearReportInit");
		try{
			String userName = BuckWaUtils.getUserNameFromContext();
			 PersonYearReport personYearReport = new PersonYearReport();	   
			if(academicYearSelect==null||academicYearSelect.trim().length()==0){
				academicYearSelect =schoolUtil.getCurrentAcademicYear();
			}			 
            personYearReport.setAcademicYearList(academicYearUtil.getAcademicYearList());            
            personYearReport.setAcademicYear(academicYearSelect);
            personYearReport.setAcademicYearSelect(academicYearSelect);	 
			BuckWaRequest request = new BuckWaRequest();								
			request.put("username", userName);
			request.put("academicYear", academicYearUtil.getAcademicYear());  
			BuckWaResponse response = personProfileService.getByUsername(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				Person person = (Person) response.getResObj("person");
				
				
				request.put("academicYear",academicYearSelect);	 
				request.put("employeeType",person.getEmployeeTypeNo());
				  response = pBPWorkTypeService.getCurretnEvalulate(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					String roundCurrent = (String)response.getResObj("round"); 
					person.setEvaluateRound(roundCurrent);
					personYearReport.setEvaluateRound(roundCurrent);
				} 
				personYearReport.setPerson(person);
			}
			
		 
			mav.addObject("personYearReport", personYearReport);
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
 
}
