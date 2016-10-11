package com.buckwa.web.controller.pbp.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.report.DepartmentReport;
import com.buckwa.domain.pbp.report.DepartmentWorkTypeReport;
import com.buckwa.domain.pbp.report.FacultyReportLevel;
import com.buckwa.domain.pbp.report.WorkTypeCompareReport;
import com.buckwa.service.intf.pbp.DeanService;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.service.intf.pbp.HeadService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.school.SchoolUtil;
 
 
@Controller
@RequestMapping("/deanReport") 
public class DeanReportController { 
	private static Logger logger = LoggerFactory.getLogger(DeanReportController.class);
	
	@Autowired
	private SchoolUtil schoolUtil;

	@Autowired
	private FacultyService facultyService;
	
	@Autowired
	private DeanService deanService;
	
	
	@Autowired
	private HeadService headService;
	
	
	@RequestMapping(value = "/init.htm", method = RequestMethod.GET)
	public ModelAndView radarChart() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		//mav.addObject("user", new User());	
		mav.setViewName("personReportInit");
		logger.info(" End  ");
		return mav;
	}
 
	
	
	@RequestMapping(value = "/barChart.htm", method = RequestMethod.GET)
	public ModelAndView barChart() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{
			String academicYear =schoolUtil.getCurrentAcademicYear();
		mav.addObject("facultyName", schoolUtil.getFacutyByDeanUserName(BuckWaUtils.getUserNameFromContext(),academicYear));	
		
		
		BuckWaRequest request = new BuckWaRequest();

		String userName = BuckWaUtils.getUserNameFromContext();
	 
		request.put("username", userName);
		request.put("academicYear", academicYear);
		BuckWaResponse response = facultyService.getFacultyByDeanUserNameandYear(request);

		if (response.getStatus() == BuckWaConstants.SUCCESS) {
			Faculty faculty = (Faculty) response.getResObj("faculty");
			System.out.println(" faculty :"+faculty);
			if (faculty != null) {

				request.put("faculty", faculty);
				request.put("academicYear", academicYear);
				response = deanService.getFacultyReportLevel(request);

				if (response.getStatus() == BuckWaConstants.SUCCESS) {
					List<FacultyReportLevel> reportWorkTypeDepartmentList = (List<FacultyReportLevel>) response.getResObj("facultyReportLevelList");
					System.out.println(" reportWorkTypeDepartmentList :"+reportWorkTypeDepartmentList);
					if(reportWorkTypeDepartmentList!=null&&reportWorkTypeDepartmentList.size()>0){
						FacultyReportLevel zeroReport =reportWorkTypeDepartmentList.get(0);
						String maxValue =zeroReport.getMark();					
						System.out.println(" mark :"+maxValue);
						mav.addObject("maxValue",ReportUtil.getMaxValuePad(maxValue));
						mav.addObject("interval",ReportUtil.getIntervalValue(maxValue));
						
					}else{
						mav.addObject("maxValue","500");
						mav.addObject("interval","100");
					}
					
					
				}
			}
		}
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		mav.setViewName("deanBarchartInit");
		logger.info(" End  ");
		return mav;
	}
	
	
	@RequestMapping(value = "/departmentBarChart.htm", method = RequestMethod.GET)
	public ModelAndView departmentBarChart() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{
			String academicYear =schoolUtil.getCurrentAcademicYear();
		mav.addObject("facultyName", schoolUtil.getFacutyByDeanUserName(BuckWaUtils.getUserNameFromContext(),academicYear));	
		
		DepartmentReport departmentReport = new DepartmentReport();
		String departmentName="";
		
		BuckWaRequest request = new BuckWaRequest();

		String userName = BuckWaUtils.getUserNameFromContext();
	 
		System.out.println(" departmentBarChart deanname:"+userName+" academicYear:"+academicYear);
		request.put("username", userName);
		request.put("academicYear", academicYear);
		BuckWaResponse response = facultyService.getFacultyByDeanUserNameandYear(request);

		if (response.getStatus() == BuckWaConstants.SUCCESS) {
			Faculty faculty = (Faculty) response.getResObj("faculty");
			System.out.println(" faculty :"+faculty);
			if (faculty != null) {
				
				departmentReport.setDepartmentList(faculty.getDepartmentList());
				Department zeroDep = faculty.getDepartmentList().get(0);
				departmentName = zeroDep.getName();
				System.out.println(" departmentName Zero :"+departmentName);
				mav.addObject("departmentName",departmentName);
				
				
				
				String headName =  schoolUtil.getHeadUserNamebyDepartmentDesc(faculty.getName(),zeroDep.getName(),academicYear);
				
				System.out.println(" headName Zero :"+headName);
				request.put("username", headName);
				
				 
				String[] output = headName.split("@");
 
				
				mav.addObject("headName",output[0]);
				request.put("academicYear", academicYear);
				 response = facultyService.getDepartmentByHeadUserNameandYear(request);
 
				if (response.getStatus() == BuckWaConstants.SUCCESS) {
					Department department = (Department) response.getResObj("department");
					if (department != null) {
						request.put("department", department);
						request.put("academicYear", academicYear);
						response = headService.getReportWorkTypeDepartment(request);
						if (response.getStatus() == BuckWaConstants.SUCCESS) {
							List<DepartmentWorkTypeReport> reportWorkTypeDepartmentList = (List<DepartmentWorkTypeReport>) response.getResObj("departmentWorkTypeReportList");
						
							if(reportWorkTypeDepartmentList!=null&&reportWorkTypeDepartmentList.size()>0){
								DepartmentWorkTypeReport zeroReport =reportWorkTypeDepartmentList.get(0);
								String maxValue =zeroReport.getMarkTotal();						
								
								mav.addObject("maxValue",ReportUtil.getMaxValuePad(maxValue));
								mav.addObject("interval",ReportUtil.getIntervalValue(maxValue));
								
							}else{
								mav.addObject("maxValue","500");
								mav.addObject("interval","100");
							}
							
							
						}
					}
				}				
				
 
			}
		}
		
		mav.addObject("departmentReport",departmentReport);
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		mav.setViewName("deanDepartmentBarchartInit");
		logger.info(" End  ");
		return mav;
	}
	
	
	@RequestMapping(value = "/departmentBarChart.htm", method = RequestMethod.POST)
	public ModelAndView departmentBarChartPOST(HttpServletRequest httpRequest, @ModelAttribute DepartmentReport departmentReport) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{
			String academicYear =schoolUtil.getCurrentAcademicYear();
		mav.addObject("facultyName", schoolUtil.getFacutyByDeanUserName(BuckWaUtils.getUserNameFromContext(),academicYear));	
		
	 
		String departmentNameSelect  = departmentReport.getDepartmentNameSelect();
		
	 
		
		BuckWaRequest request = new BuckWaRequest();

		String userName = BuckWaUtils.getUserNameFromContext();
	 
		System.out.println(" departmentBarChart deanname:"+userName+" academicYear:"+academicYear);
		request.put("username", userName);
		request.put("academicYear", academicYear);
		BuckWaResponse response = facultyService.getFacultyByDeanUserNameandYear(request);

		if (response.getStatus() == BuckWaConstants.SUCCESS) {
			Faculty faculty = (Faculty) response.getResObj("faculty");
			System.out.println(" faculty :"+faculty);
			if (faculty != null) {
				
				departmentReport.setDepartmentList(faculty.getDepartmentList());
		 
				 
				mav.addObject("departmentName",departmentNameSelect);
				
				
				
				String headName =  schoolUtil.getHeadUserNamebyDepartmentDesc(faculty.getName(),departmentNameSelect,academicYear);
				
				System.out.println(" headName Zero :"+headName);
				request.put("username", headName);
				
				 
				String[] output = headName.split("@");
 
				
				mav.addObject("headName",output[0]);
				request.put("academicYear", academicYear);
				 response = facultyService.getDepartmentByHeadUserNameandYear(request);
 
				if (response.getStatus() == BuckWaConstants.SUCCESS) {
					Department department = (Department) response.getResObj("department");
					if (department != null) {
						request.put("department", department);
						request.put("academicYear", academicYear);
						response = headService.getReportWorkTypeDepartment(request);
						if (response.getStatus() == BuckWaConstants.SUCCESS) {
							List<DepartmentWorkTypeReport> reportWorkTypeDepartmentList = (List<DepartmentWorkTypeReport>) response.getResObj("departmentWorkTypeReportList");
						
							if(reportWorkTypeDepartmentList!=null&&reportWorkTypeDepartmentList.size()>0){
								DepartmentWorkTypeReport zeroReport =reportWorkTypeDepartmentList.get(0);
								String maxValue =zeroReport.getMarkTotal();						
								
								mav.addObject("maxValue",ReportUtil.getMaxValuePad(maxValue));
								mav.addObject("interval",ReportUtil.getIntervalValue(maxValue));
								
							}else{
								mav.addObject("maxValue","500");
								mav.addObject("interval","100");
							}
							
							
						}
					}
				}				
				
 
			}
		}
		
		mav.addObject("departmentReport",departmentReport);
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		mav.setViewName("deanDepartmentBarchartInit");
		logger.info(" End  ");
		return mav;
	}
	
	
	
	@RequestMapping(value = "/workTypeBarChart.htm", method = RequestMethod.GET)
	public ModelAndView workTypeBarChart() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{
		String academicYear =schoolUtil.getCurrentAcademicYear();
		String facultyName = schoolUtil.getFacutyByDeanUserName(BuckWaUtils.getUserNameFromContext(),academicYear);		
		String facultyCode =schoolUtil.getFacultyCodeByFacultyName(facultyName,academicYear);		
		mav.addObject("facultyName", facultyName);	 
		
		/*
		BuckWaRequest request = new BuckWaRequest();
		request.put("academicYear", academicYear);
		request.put("facultyCode", facultyCode);
		
		
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
		
		*/
		}catch(Exception ex){
			ex.printStackTrace();
		}
		mav.setViewName("deanWorkTypeBarchartInit");
		logger.info(" End  ");
		return mav;
	}
	
	@RequestMapping(value = "/workTypeCompareBarChart.htm", method = RequestMethod.GET)
	public ModelAndView workTypeCompareBarChart() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try {
			String academicYear =schoolUtil.getCurrentAcademicYear();
			mav.addObject("facultyName", schoolUtil.getFacutyByDeanUserName(BuckWaUtils.getUserNameFromContext(),academicYear));
			
			WorkTypeCompareReport workTypeCompareReport = new WorkTypeCompareReport();
			workTypeCompareReport.setType1(false);
			workTypeCompareReport.setType2(false);
			workTypeCompareReport.setType3(false);
			workTypeCompareReport.setType4(false);
			workTypeCompareReport.setType5(false);
			mav.addObject("workTypeCompareReport", workTypeCompareReport);
			
			String rptObjJSON = JSONObject.valueToString(new String[0]);
			mav.addObject("rptObjJSON", rptObjJSON);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		mav.setViewName("deanWorkTypeCompareBarchartInit");
		logger.info(" End  ");
		return mav;
	}
	
	@RequestMapping(value = "/workTypeCompareBarChart.htm", method = RequestMethod.POST)
	public ModelAndView genWorkTypeCompareBarChart(@ModelAttribute WorkTypeCompareReport workTypeCompareReport) {
		logger.info(" Start  ");
		
		ModelAndView mav = new ModelAndView();
		
		
		try {
			String academicYear =schoolUtil.getCurrentAcademicYear();
			mav.addObject("facultyName", schoolUtil.getFacutyByDeanUserName(BuckWaUtils.getUserNameFromContext(),academicYear));
			
			BuckWaRequest request = new BuckWaRequest();

			String userName = BuckWaUtils.getUserNameFromContext();
		 

			request.put("username", userName);
			request.put("academicYear", academicYear);
			BuckWaResponse response = facultyService.getFacultyByDeanUserNameandYear(request);

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				Faculty faculty = (Faculty) response.getResObj("faculty");
				if (faculty != null) {
					request.put("faculty", faculty);
					response = deanService.getReportWorkTypeCompareFaculty(request);

					if (response.getStatus() == BuckWaConstants.SUCCESS) {
						List<DepartmentWorkTypeReport> reportWorkTypeDepartmentList = (List<DepartmentWorkTypeReport>) response.getResObj("facultyWorkTypeReportList");
						//int loopx = 0;
						WorkTypeCompareReport rptObj;
						List<WorkTypeCompareReport> returnList = new ArrayList<WorkTypeCompareReport>();
						for (DepartmentWorkTypeReport workType : reportWorkTypeDepartmentList) {
							
							if (workTypeCompareReport.isType1()) {
								rptObj = new WorkTypeCompareReport();
								rptObj.setOrderNo(1);
								rptObj.setCategoryName(workType.getDepartmentName());
								rptObj.setGroupName("1:"+workType.getTypeName1());
								rptObj.setAxisValue(workType.getMark1());
								returnList.add(rptObj);
							}
							if (workTypeCompareReport.isType2()) {
								rptObj = new WorkTypeCompareReport();
								rptObj.setOrderNo(2);
								rptObj.setCategoryName(workType.getDepartmentName());
								rptObj.setGroupName("2:"+workType.getTypeName2());
								rptObj.setAxisValue(workType.getMark2());
								returnList.add(rptObj);
							}
							if (workTypeCompareReport.isType3()) {
								rptObj = new WorkTypeCompareReport();
								rptObj.setOrderNo(3);
								rptObj.setCategoryName(workType.getDepartmentName());
								rptObj.setGroupName("3:"+workType.getTypeName3());
								rptObj.setAxisValue(workType.getMark3());
								returnList.add(rptObj);
							}
							if (workTypeCompareReport.isType4()) {
								rptObj = new WorkTypeCompareReport();
								rptObj.setOrderNo(4);
								rptObj.setCategoryName(workType.getDepartmentName());
								rptObj.setGroupName("4:"+workType.getTypeName4());
								rptObj.setAxisValue(workType.getMark4());
								returnList.add(rptObj);
							}
							if (workTypeCompareReport.isType5()) {
								rptObj = new WorkTypeCompareReport();
								rptObj.setOrderNo(5);
								rptObj.setCategoryName(workType.getDepartmentName());
								rptObj.setGroupName("5:"+workType.getTypeName5());
								rptObj.setAxisValue(workType.getMark5());
								returnList.add(rptObj);
							}
							//loopx++;
						}
						String rptObjJSON = JSONObject.valueToString(returnList);
						mav.addObject("rptObjJSON", rptObjJSON);
					}
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		mav.setViewName("deanWorkTypeCompareBarchartInit");
		logger.info(" End  ");
		return mav;
	}

}
