package com.buckwa.web.controller.json;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pbp.AcademicKPIUserMappingWrapper;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.report.DepartmentWorkTypeReport;
import com.buckwa.domain.pbp.report.RadarPlotReport;
import com.buckwa.service.intf.pam.PersonProfileService;
import com.buckwa.service.intf.pbp.AcademicKPIUserMappingService;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.service.intf.pbp.HeadService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;

import baiwa.util.UserLoginUtil;

@RestController
@RequestMapping("/head")
public class JSONHeadController {
	private static Logger logger = LoggerFactory.getLogger(JSONHeadController.class);

	@Autowired
	private PersonProfileService personProfileService;

	@Autowired
	private FacultyService facultyService;

	@Autowired
	private SchoolUtil schoolUtil;

	@Autowired
	private HeadService headService;
	
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	@Autowired
	private AcademicKPIUserMappingService academicKPIUserMappingService;

	@RequestMapping(value = "/getBarchart", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<RadarPlotReport> getWorkTypeBarChartReport() {

		List<RadarPlotReport> returnList = new ArrayList<RadarPlotReport>();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("markDepartment");
		try {
			BuckWaRequest request = new BuckWaRequest();

			String userName = UserLoginUtil.getCurrentUserLogin();
			String academicYear = UserLoginUtil.getCurrentAcademicYear();

			request.put("username", userName);
			request.put("academicYear", academicYear);
			BuckWaResponse response = facultyService.getDepartmentByHeadUserNameandYear(request);

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				Department department = (Department) response.getResObj("department");

				if (department != null) {

					request.put("department", department);
					request.put("academicYear", academicYear);
					response = headService.getReportWorkTypeDepartment(request);

					if (response.getStatus() == BuckWaConstants.SUCCESS) {
						List<DepartmentWorkTypeReport> reportWorkTypeDepartmentList = (List<DepartmentWorkTypeReport>) response.getResObj("departmentWorkTypeReportList");

						request.put("username", userName);
						request.put("academicYear", academicYear);
						response = personProfileService.getByUsername(request);
						if (response.getStatus() == BuckWaConstants.SUCCESS) {
							Person person = (Person) response.getResObj("person");
							String firstLast = person.getThaiName() + " " + person.getThaiSurname();

							int loopx = 1;
							for (DepartmentWorkTypeReport personTmp : reportWorkTypeDepartmentList) {
								String tmpRegId = personTmp.getPersonName();
								RadarPlotReport reportTmp = new RadarPlotReport();
								// if(!firstLast.equalsIgnoreCase(tmpRegId)){
								reportTmp.setAxisName(" ");
								// }else{
								reportTmp.setAxisName(personTmp.getPersonName());
								// }

								reportTmp.setAxisValue(personTmp.getMarkTotal());

								reportTmp.setOrderNo(loopx);
								returnList.add(reportTmp);
								loopx++;
							}

							mav.addObject("department", department);
						}
					}

				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return returnList;
	}

	@RequestMapping(value = "/getWorkTypeBarchart/{worktypecode}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<RadarPlotReport> getWorkTypeBarChartReport(@PathVariable String worktypecode) {

		List<RadarPlotReport> returnList = new ArrayList<RadarPlotReport>();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("markDepartment");
		try {
			BuckWaRequest request = new BuckWaRequest();

			String userName = UserLoginUtil.getCurrentUserLogin();
			String academicYear = UserLoginUtil.getCurrentAcademicYear();

			request.put("username", userName);
			request.put("academicYear", academicYear);
			BuckWaResponse response = facultyService.getDepartmentByHeadUserNameandYear(request);

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				Department department = (Department) response.getResObj("department");

				if (department != null) {

					request.put("department", department);
					request.put("academicYear", academicYear);
					response = headService.getReportWorkTypeDepartment(request);

					if (response.getStatus() == BuckWaConstants.SUCCESS) {
						List<DepartmentWorkTypeReport> reportWorkTypeDepartmentList = (List<DepartmentWorkTypeReport>) response.getResObj("departmentWorkTypeReportList");

						request.put("username", userName);
						request.put("academicYear", academicYear);
						response = personProfileService.getByUsername(request);
						if (response.getStatus() == BuckWaConstants.SUCCESS) {
							Person person = (Person) response.getResObj("person");
							String firstLast = person.getThaiName() + " " + person.getThaiSurname();

							int loopx = 1;
							for (DepartmentWorkTypeReport personTmp : reportWorkTypeDepartmentList) {
								String tmpRegId = personTmp.getPersonName();
								RadarPlotReport reportTmp = new RadarPlotReport();
								// if(!firstLast.equalsIgnoreCase(tmpRegId)){
								// reportTmp.setAxisName( " " );
								// }else{
								reportTmp.setAxisName(personTmp.getPersonName());
								// }

								if ("1".equals(worktypecode)) {
									reportTmp.setAxisValue(personTmp.getMark1());
								} else if ("2".equals(worktypecode)) {
									reportTmp.setAxisValue(personTmp.getMark2());
								} else if ("3".equals(worktypecode)) {
									reportTmp.setAxisValue(personTmp.getMark3());
								} else if ("4".equals(worktypecode)) {
									reportTmp.setAxisValue(personTmp.getMark4());
								} else if ("5".equals(worktypecode)) {
									reportTmp.setAxisValue(personTmp.getMark5());
								}

								reportTmp.setOrderNo(loopx);
								returnList.add(reportTmp);
								loopx++;
							}

							mav.addObject("department", department);
						}
					}

				}
			}
			logger.info(" worktypecode in :" + worktypecode);
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return returnList;
	}
	@RequestMapping(value="/init", method = RequestMethod.GET)
	public AcademicKPIUserMappingWrapper initList( ) {
		//public ModelAndView initList(@RequestParam("academicYear") String academicYear) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("headWorkList");
		AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper = null;
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			 
			String headUserName = UserLoginUtil.getCurrentUserLogin();
			
			//if(academicYear==null||academicYear.length()==0){
			String	academicYear =schoolUtil.getCurrentAcademicYear();
			//}
		//	String academicYear =schoolUtil.getCurrentAcademicYear();
			
			request.put("headUserName",headUserName);
			request.put("academicYear",academicYear);
			request.put("status",""); 
			BuckWaResponse response = headService.getByHeadAcademicYearCount(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				academicKPIUserMappingWrapper = (AcademicKPIUserMappingWrapper)response.getResObj("academicKPIUserMappingWrapper");
			 
				academicKPIUserMappingWrapper.setAcademicYear(academicYear);
				academicKPIUserMappingWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
				//mav.addObject("academicKPIUserMappingWrapper", academicKPIUserMappingWrapper);	
			}		
			 
		}catch(Exception ex){
			ex.printStackTrace();
			//mav.addObject("errorCode", "E001"); 
		}
		return academicKPIUserMappingWrapper;
	}	
	@RequestMapping(value="/initByUserName/{userName}/{round}", method = RequestMethod.GET , headers = "Accept=application/json")
	public AcademicKPIUserMappingWrapper initByUserName(@PathVariable String userName,@PathVariable String round) {
		logger.info(" Start  userName: "+userName);

		
		AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper =null;
		try{
			
			//httpRequest.getSession().setAttribute("approveUserName", userName);
			BuckWaRequest request = new BuckWaRequest(); 
			 
			String headUserName = UserLoginUtil.getCurrentUserLogin();
			String academicYear =UserLoginUtil.getCurrentAcademicYear();
			
			request.put("headUserName",headUserName);
			request.put("academicYear",academicYear);
			request.put("userName",userName);
			request.put("status",""); 
			BuckWaResponse response = headService.getByUserAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				academicKPIUserMappingWrapper = (AcademicKPIUserMappingWrapper)response.getResObj("academicKPIUserMappingWrapper");
			 
				academicKPIUserMappingWrapper.setAcademicYear(academicYear);
				//mav.addObject("academicKPIUserMappingWrapper", academicKPIUserMappingWrapper);	
			}				  
		}catch(Exception ex){
			ex.printStackTrace();
			//mav.addObject("errorCode", "E001"); 
		}
		return academicKPIUserMappingWrapper;
	}	
	@RequestMapping(value="/approveWork/{kpiUserMappingId}", method = RequestMethod.GET)
	public BuckWaResponse approveWork(@PathVariable String kpiUserMappingId  ) {
		logger.info(" Start  kpiUserMappingId:"+kpiUserMappingId );
		ModelAndView mav = new ModelAndView();
		mav.setViewName("headWorkList");
		BuckWaResponse response = new BuckWaResponse();
		try{
			
			//String approveUserName =(String)httpRequest.getSession().getAttribute("approveUserName");
			BuckWaRequest request = new BuckWaRequest(); 
			String headUserName = UserLoginUtil.getCurrentUserLogin();
			request.put("kpiUserMappingId",kpiUserMappingId);
			request.put("headUserName",headUserName);
			response = academicKPIUserMappingService.approve(request);
			 logger.info(" ### approveWork after approve ");
			 //response.setStatus(BuckWaConstants.SUCCESS);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				
			} else{
				mav.addObject("errorCode", "E001"); 
				mav.setViewName("viewWork");
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return response;
	}
	@RequestMapping(value="/unApprove/{kpiUserMappingId}", method = RequestMethod.GET)
	public BuckWaResponse unApprove(@PathVariable String kpiUserMappingId  ) {
		logger.info(" Start unApprove  kpiUserMappingId:"+kpiUserMappingId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("headWorkUserList");
		BuckWaResponse response = new BuckWaResponse();
		try{
			BuckWaRequest request = new BuckWaRequest();
			request.put("kpiUserMappingId",kpiUserMappingId);
			response = academicKPIUserMappingService.unApprove(request);
			//response.setStatus(BuckWaConstants.SUCCESS);

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return response;
	}

}
