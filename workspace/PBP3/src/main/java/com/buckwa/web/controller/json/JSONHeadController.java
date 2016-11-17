package com.buckwa.web.controller.json;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.report.DepartmentWorkTypeReport;
import com.buckwa.domain.pbp.report.RadarPlotReport;
import com.buckwa.service.intf.pam.PersonProfileService;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.service.intf.pbp.HeadService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.school.SchoolUtil;

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

			String userName = BuckWaUtils.getUserNameFromContext();
			String academicYear = schoolUtil.getCurrentAcademicYear();

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

}
