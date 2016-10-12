package com.buckwa.web.controller.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.UserSession;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pbp.AcademicKPI;
import com.buckwa.domain.pbp.AcademicKPIAttribute;
import com.buckwa.domain.pbp.AcademicKPIAttributeValue;
import com.buckwa.domain.pbp.AcademicKPIUserMapping;
import com.buckwa.domain.pbp.AcademicKPIUserMappingWrapper;
import com.buckwa.domain.pbp.AcademicKPIWrapper;
import com.buckwa.domain.pbp.AcademicUnitWrapper;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.domain.pbp.report.DepartmentWorkTypeReport;
import com.buckwa.domain.pbp.report.RadarPlotReport;
import com.buckwa.domain.pbp3.ResponseObj;
import com.buckwa.domain.pbp3.WorkSummary;
import com.buckwa.domain.pbp3.WorkType;
import com.buckwa.service.intf.pam.PersonProfileService;
import com.buckwa.service.intf.pbp.AcademicKPIService;
import com.buckwa.service.intf.pbp.AcademicKPIUserMappingService;
import com.buckwa.service.intf.pbp.AcademicUnitService;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.service.intf.pbp.HeadService;
import com.buckwa.service.intf.pbp.PBPWorkTypeService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;

@RestController
@RequestMapping("/person")
public class JSONPersonController {
	private static Logger logger = LoggerFactory.getLogger(JSONPersonController.class);

	@Autowired
	private PersonProfileService personProfileService;

	@Autowired
	private FacultyService facultyService;

	@Autowired
	private PBPWorkTypeService pBPWorkTypeService;

	@Autowired
	private SchoolUtil schoolUtil;

	@Autowired
	private AcademicYearUtil academicYearUtil;

	@Autowired
	private HeadService headService;
	
	@Autowired
	private AcademicUnitService academicUnitService;	
	
	@Autowired
	private AcademicKPIService academicKPIService;	
	
	@Autowired
	private AcademicKPIUserMappingService  academicKPIUserMappingService;
	
	@RequestMapping(value = "/getPersonByAcademicYear/{userName}/{year}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Person getPersonByAcademicYear(HttpServletRequest httpRequest,@PathVariable String userName,@PathVariable String year) {
	 
		Person person = new Person();
 
		 
		try {

			BuckWaRequest request = new BuckWaRequest();
			request.put("username", userName);
			request.put("academicYear", year);
			BuckWaResponse response = personProfileService.getByUsername(request);
			person = (Person) response.getResObj("person");
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			 
		}

		return person;
	}	
	@RequestMapping(value = "/getRadarPlotNew/{userName}/{year}/{round}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<RadarPlotReport> radarPlotNew(HttpServletRequest httpRequest,@PathVariable String userName,@PathVariable String year,@PathVariable String round) {
	 
		List<RadarPlotReport> returnList = new ArrayList<RadarPlotReport>();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initPerson");
		 
		try {

			String academicYear = year;
		 
			logger.info("radarPlotNew  username :" + userName+ " academicYear:"+academicYear+" round :"+round);

			BuckWaRequest request = new BuckWaRequest();
			request.put("username", userName);
			request.put("academicYear", academicYear);
			
			BuckWaResponse response = new BuckWaResponse();
 
			Person person = new Person();
 
				response = personProfileService.getByUsername(request);
				if (response.getStatus() == BuckWaConstants.SUCCESS) {
					person = (Person) response.getResObj("person");

					//user.setFirstLastName(person.getThaiName() + " " + person.getThaiSurname());
	 
					person.setAcademicYear(academicYear);
					person.setAcademicYearList(academicYearUtil.getAcademicYearList());
					person.setEvaluateRound(round);
					//user.setPersonProfile(person);
					mav.addObject("person", person);
					 
					String facultyCode = person.getFacultyCode();
	
					request.put("academicYear", academicYear);
					request.put("userName", userName);
					request.put("round", person.getEvaluateRound());
					request.put("employeeType", person.getEmployeeType());
					request.put("facultyCode", facultyCode);
	
				 
					response = pBPWorkTypeService.getRadarPlotPersonMark(request);
	
					if (response.getStatus() == BuckWaConstants.SUCCESS) {
						 returnList = (List<RadarPlotReport>) response.getResObj("radarPlotReportList");
						 
					}
				}else{
					response.setStatus(BuckWaConstants.FAIL);
				}
 

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
		}

		return returnList;
	}
	
	@RequestMapping(value = "/getRadarPlotNewByYear/{academicYear}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<RadarPlotReport> radarPlotNewByYear(HttpServletRequest httpRequest,@PathVariable String academicYear) {
	 
		List<RadarPlotReport> returnList = new ArrayList<RadarPlotReport>();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initPerson");
		 
		try {

			//String academicYear = schoolUtil.getCurrentAcademicYear();
			//logger.info(" Start  academicYear:" + academicYear);
			BuckWaUser user = BuckWaUtils.getUserFromContext();
			logger.info("radarPlotNew  username :" + user.getUsername()+ " academicYear:"+academicYear);

			BuckWaRequest request = new BuckWaRequest();
			request.put("username", user.getUsername());
			request.put("academicYear", academicYear);
			
			BuckWaResponse response = new BuckWaResponse();
 
			Person person = new Person();
 
				response = personProfileService.getByUsername(request);
				if (response.getStatus() == BuckWaConstants.SUCCESS) {
					person = (Person) response.getResObj("person");

					user.setFirstLastName(person.getThaiName() + " " + person.getThaiSurname());
	 
					person.setAcademicYear(academicYear);
					person.setAcademicYearList(academicYearUtil.getAcademicYearList());
					 
					user.setPersonProfile(person);
					mav.addObject("person", person);
					 
					String facultyCode = person.getFacultyCode();
	
					request.put("academicYear", academicYear);
					request.put("userName", BuckWaUtils.getUserNameFromContext());
					request.put("round", person.getEvaluateRound());
					request.put("employeeType", person.getEmployeeType());
					request.put("facultyCode", facultyCode);
	
				 
					response = pBPWorkTypeService.getRadarPlotPersonMarkByYear(request);
	
					if (response.getStatus() == BuckWaConstants.SUCCESS) {
						 returnList = (List<RadarPlotReport>) response.getResObj("radarPlotReportList");
						 
					}
				}else{
					response.setStatus(BuckWaConstants.FAIL);
				}
 

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
		}

		return returnList;
	}
	
	@RequestMapping(value = "/getRadarPlotNewE", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<RadarPlotReport> radarPlotNewE(HttpServletRequest httpRequest) {

		List<RadarPlotReport> returnList = new ArrayList<RadarPlotReport>();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initPerson");
		 
		try {

			String academicYear = academicYearUtil.getAcademicYear();
			logger.info(" Start  academicYear:" + academicYear);
			BuckWaUser user = BuckWaUtils.getUserFromContext();
			logger.info("viewUserProfile  username :" + user.getUsername());

			BuckWaRequest request = new BuckWaRequest();
			request.put("username", user.getUsername());
			request.put("academicYear", academicYear);
			
			BuckWaResponse response = new BuckWaResponse();
//			Person person = (Person) httpRequest.getSession().getAttribute("personProFileSession");
			Person person = new Person();
 
				response = personProfileService.getByUsername(request);
				if (response.getStatus() == BuckWaConstants.SUCCESS) {
					person = (Person) response.getResObj("person");

					user.setFirstLastName(person.getThaiName() + " " + person.getThaiSurname());
	 
					person.setAcademicYear(academicYear);
					person.setAcademicYearList(academicYearUtil.getAcademicYearList());
					person.setEvaluateRound("1");
					user.setPersonProfile(person);
					mav.addObject("person", person);
					 
					String facultyCode = person.getFacultyCode();
	
					request.put("academicYear", academicYear);
					request.put("userName", BuckWaUtils.getUserNameFromContext());
					request.put("round", person.getEvaluateRound());
					request.put("employeeType", person.getEmployeeType());
					request.put("facultyCode", facultyCode);
	
				 
					response = pBPWorkTypeService.getRadarPlotPersonMarkE(request);
	
					if (response.getStatus() == BuckWaConstants.SUCCESS) {
						 returnList = (List<RadarPlotReport>) response.getResObj("radarPlotReportList");
						 
					}
				}else{
					response.setStatus(BuckWaConstants.FAIL);
				}
 

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
		}

		return returnList;
	}
	@RequestMapping(value = "/getRadarPlot", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<RadarPlotReport> radarPlot(HttpServletRequest httpRequest) {

		List<RadarPlotReport> returnList = new ArrayList<RadarPlotReport>();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initPerson");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		try {

			String academicYear = academicYearUtil.getAcademicYear();
			logger.info(" Start  academicYear:" + academicYear);
			BuckWaUser user = BuckWaUtils.getUserFromContext();
			logger.info("viewUserProfile  username :" + user.getUsername());

			BuckWaRequest request = new BuckWaRequest();
			request.put("username", user.getUsername());
			request.put("academicYear", academicYear);
			
			BuckWaResponse response = new BuckWaResponse();
//			Person person = (Person) httpRequest.getSession().getAttribute("personProFileSession");
			Person person = new Person();
//			if(null == person){
				response = personProfileService.getByUsername(request);
				if (response.getStatus() == BuckWaConstants.SUCCESS) {
					person = (Person) response.getResObj("person");

					user.setFirstLastName(person.getThaiName() + " " + person.getThaiSurname());
	 
					person.setAcademicYear(academicYear);
					person.setAcademicYearList(academicYearUtil.getAcademicYearList());
					person.setEvaluateRound("1");
					user.setPersonProfile(person);
					mav.addObject("person", person);
					 
					String facultyCode = person.getFacultyCode();
	
					request.put("academicYear", academicYear);
					request.put("userName", BuckWaUtils.getUserNameFromContext());
					request.put("round", person.getEvaluateRound());
					request.put("employeeType", person.getEmployeeTypeNo());
					request.put("facultyCode", facultyCode);
	
					// response = pBPWorkTypeService.getByAcademicYear(request);
					response = pBPWorkTypeService.getCalculateByAcademicYear(request);
	
					if (response.getStatus() == BuckWaConstants.SUCCESS) {
						PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper) response.getResObj("pBPWorkTypeWrapper");
						pBPWorkTypeWrapper.setAcademicYear(academicYear);
						person.setpBPWorkTypeWrapper(pBPWorkTypeWrapper);
					}
					
				}else{
					response.setStatus(BuckWaConstants.FAIL);
				}
//			}else{
//				response.setStatus(BuckWaConstants.SUCCESS);
//			}

			if (response.getStatus() == BuckWaConstants.SUCCESS) {

				List<PBPWorkType> pBPWorkTypeList = person.getpBPWorkTypeWrapper().getpBPWorkTypeList();
				int loop = 0;
				for (PBPWorkType typeTmp : pBPWorkTypeList) {

					RadarPlotReport radartmp = new RadarPlotReport();
					logger.info(" loop:" + loop);
					String tempLabel = "";
					StringTokenizer st = new StringTokenizer(typeTmp.getName(), " ");
					int numberOfSt = 1;
					while (st.hasMoreElements()) {
						String stStr = st.nextElement().toString();
						logger.info(" numberOfSt:" + numberOfSt + "  stStr:" + stStr);
						if (numberOfSt == 1) {
							tempLabel = stStr;
						}
						if (numberOfSt == 2) {
							// axisLables = axisLables +" "
							// st.nextElement();
						}
						numberOfSt++;
					}
					radartmp.setAxisName(tempLabel);

					loop++;
				//	radartmp.setAxisValue(typeTmp.getTotalInPercentCompareBaseWorkType().setScale(0, BigDecimal.ROUND_UP) + "");
					radartmp.setAxisValue(typeTmp.getTotalInWorkType().setScale(0, BigDecimal.ROUND_UP) + "");
					logger.info(" Label:" + radartmp.getAxisName() + "  Value:" + radartmp.getAxisValue());
					returnList.add(radartmp);
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
		}

		return returnList;
	}

	@RequestMapping(value = "/getBarchart", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<RadarPlotReport> getWorkTypeBarChartReport() {
		System.out.println(" ### getBarchart ###");

		List<RadarPlotReport> returnList = new ArrayList<RadarPlotReport>();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("markDepartment");
		try {
			BuckWaRequest request = new BuckWaRequest();

			String userName = BuckWaUtils.getUserNameFromContext();
			String academicYear = schoolUtil.getCurrentAcademicYear();

			request.put("username", userName);
			request.put("academicYear", academicYear);
			BuckWaResponse response = facultyService.getDepartmentByUserNameandYear(request);

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				Department department = (Department) response.getResObj("department");
				System.out.println(" department :"+BeanUtils.getBeanString(department));

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
							System.out.println(" firstLast :"+firstLast);

							int loopx = 1;
							for (DepartmentWorkTypeReport personTmp : reportWorkTypeDepartmentList) {
								String personName = personTmp.getPersonName();
								RadarPlotReport reportTmp = new RadarPlotReport();
								System.out.println(" firstLast :"+firstLast+"|   personName:"+personName);
								if (!firstLast.equalsIgnoreCase(personName)) {
									reportTmp.setAxisName(" ");
								} else {
									reportTmp.setAxisName(personTmp.getPersonName());
								}

								reportTmp.setAxisValue(personTmp.getMarkTotal());

								reportTmp.setOrderNo(loopx);
								returnList.add(reportTmp);
								reportTmp.setMean("333");
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

	/*
	 * @RequestMapping(value = "/getBarchart", method =
	 * RequestMethod.GET,headers="Accept=application/json") public
	 * List<RadarPlotReport> getBarChartReport( ) {
	 * 
	 * List<RadarPlotReport> returnList = new ArrayList(); ModelAndView mav =
	 * new ModelAndView(); mav.setViewName("markDepartment"); try{ BuckWaRequest
	 * request = new BuckWaRequest();
	 * 
	 * String userName = BuckWaUtils.getUserNameFromContext(); String
	 * academicYear =schoolUtil.getCurrentAcademicYear();
	 * 
	 * request.put("userName",BuckWaUtils.getUserNameFromContext());
	 * request.put("academicYear",academicYear); request.put("status","");
	 * BuckWaResponse response = headService.getDepartmentMarkByUser(request);
	 * if(response.getStatus()==BuckWaConstants.SUCCESS){ Department department
	 * = (Department)response.getResObj("department");
	 * department.setAcademicYear(academicYear); request = new BuckWaRequest();
	 * request.put("academicYear",academicYear); response =
	 * pBPWorkTypeService.getByAcademicYear(request);
	 * if(response.getStatus()==BuckWaConstants.SUCCESS){ PBPWorkTypeWrapper
	 * pBPWorkTypeWrapper =
	 * (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
	 * List<PBPWorkType> pBPWorkTypeList =
	 * pBPWorkTypeWrapper.getpBPWorkTypeList();
	 * 
	 * 
	 * for(PBPWorkType typeTmp:pBPWorkTypeList){ String shortDesc ="";
	 * StringTokenizer st = new StringTokenizer(typeTmp.getName(), " "); int
	 * numberOfSt =1; while (st.hasMoreElements()) {
	 * 
	 * String stStr = st.nextElement().toString();
	 * logger.info(" numberOfSt:"+numberOfSt+"  stStr:"+ stStr);
	 * if(numberOfSt==1){ shortDesc = stStr; } if(numberOfSt==2){ //axisLables =
	 * axisLables +" " //st.nextElement(); } numberOfSt++; }
	 * 
	 * typeTmp.setShortDesc(shortDesc);
	 * 
	 * // Sum total mark List<AcademicPerson> academicPersonListMark =
	 * department.getAcademicPersonList(); BigDecimal totalMark = new
	 * BigDecimal(0.00); for(AcademicPerson personTmp: academicPersonListMark){
	 * 
	 * List<PBPWorkType> pBPWorkTypeListTotalMark =
	 * personTmp.getpBPWorkTypeWrapper().getpBPWorkTypeList();
	 * 
	 * for(PBPWorkType totalMarkTmp:pBPWorkTypeListTotalMark){
	 * System.out.print(" totalMarkTmp id:"+totalMarkTmp.getWorkTypeId());
	 * 
	 * if(typeTmp.getWorkTypeId().intValue()==totalMarkTmp.getWorkTypeId().intValue
	 * ()){
	 * 
	 * 
	 * totalMark =
	 * totalMark.add(totalMarkTmp.getTotalInPercentCompareBaseWorkType());
	 * 
	 * }
	 * 
	 * } }
	 * 
	 * typeTmp.setTotalAllWorkType(totalMark);
	 * typeTmp.setTotalInPercentCompareBaseWorkType(totalMark);
	 * 
	 * }
	 * 
	 * department.setpBPWorkTypeList(pBPWorkTypeList);
	 * 
	 * } List<AcademicPerson> personListtmp =department.getAcademicPersonList();
	 * //BuckWaUser user = BuckWaUtils.getUserFromContext();
	 * request.put("username",userName);
	 * request.put("academicYear",academicYear); response
	 * =personProfileService.getByUsername(request);
	 * if(response.getStatus()==BuckWaConstants.SUCCESS){ Person person =
	 * (Person) response.getResObj("person"); String personRegId =
	 * person.getRegId();
	 * 
	 * int loopx =0; for(AcademicPerson personTmp: personListtmp){ String
	 * tmpRegId = personTmp.getRegId(); RadarPlotReport reportTmp = new
	 * RadarPlotReport(); if(!personRegId.equalsIgnoreCase(tmpRegId)){
	 * reportTmp.setAxisName( " " ); }else{
	 * reportTmp.setAxisName(personTmp.getThaiName()+" "+
	 * personTmp.getThaiSurname()); }
	 * reportTmp.setAxisValue(personTmp.getpBPWorkTypeWrapper
	 * ().getTotalPercentMarkCompareBase()+"");
	 * 
	 * returnList.add(reportTmp);
	 * 
	 * } }
	 * 
	 * 
	 * mav.addObject("department", department); } }catch(Exception ex){
	 * ex.printStackTrace(); mav.addObject("errorCode", "E001"); } return
	 * returnList; }
	 */

	@RequestMapping(value = "/getWorkTypeBarchart/{worktypecode}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<RadarPlotReport> getWorkTypeBarChartReport(@PathVariable String worktypecode) {

		List<RadarPlotReport> returnList = new ArrayList();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("markDepartment");
		try {
			BuckWaRequest request = new BuckWaRequest();

			String userName = BuckWaUtils.getUserNameFromContext();
			String academicYear = schoolUtil.getCurrentAcademicYear();

			request.put("username", userName);
			request.put("academicYear", academicYear);
			BuckWaResponse response = facultyService.getDepartmentByUserNameandYear(request);

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

							int loopx = 0;
							for (DepartmentWorkTypeReport personTmp : reportWorkTypeDepartmentList) {
								String tmpRegId = personTmp.getPersonName();
								RadarPlotReport reportTmp = new RadarPlotReport();
								if (!firstLast.equalsIgnoreCase(tmpRegId)) {
									reportTmp.setAxisName(" ");
								} else {
									reportTmp.setAxisName(personTmp.getPersonName());
								}

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

	
	@RequestMapping(value = "/getUserSession", method = RequestMethod.GET, headers = "Accept=application/json")
	public UserSession  getUserSession(HttpServletRequest httpRequest) {
		UserSession userreturn =new UserSession();
		try {
			logger.info(" getUserSession ");
	 
			//userreturn = BuckWaUtils.getUserFromContext();
			userreturn.setUserName("ktpitak@kmitl.ac.th");
			userreturn.setFirstName("พิทักษ์ ");
			userreturn.setLastName("ธรรมวาริน");
			userreturn.setCurrentAcademicYear("2558");
			userreturn.setFacultyCode("01");
			userreturn.setDepartmentCode("05");
		} catch (Exception ex) {
			ex.printStackTrace();
			 
		}

		 return userreturn;
	}
	
	
 
	@RequestMapping(value = "/getAllWorkList/{academicYear}/{facultyCode}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<WorkType> getAllWorkList(@PathVariable String academicYear,@PathVariable String facultyCode) {		
		logger.info(" Start ");
 
		
		 List<WorkType>  returnList = new ArrayList();
		try {
			BuckWaRequest request = new BuckWaRequest(); 
			
			request.put("academicYear",academicYear); 
			request.put("facultyCode",facultyCode); 
			BuckWaResponse  response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
				List<PBPWorkType> workTypeListx = pBPWorkTypeWrapper.getpBPWorkTypeList(); 				
				List<PBPWorkType> workTypeList =workTypeListx;
				if(workTypeList!=null&&workTypeList.size()>0){ 
					
					for(PBPWorkType workTmp:workTypeList){
						String workTypeCodeTmp = workTmp.getCode();
						String workTypeNameTmp = workTmp.getName();
						logger.info(" WorkType: "+workTypeCodeTmp+":"+workTypeNameTmp);
						
						WorkType newWork = new WorkType();
						newWork.setWorkTypeName(workTypeNameTmp);
						
						request.put("workTypeCode",workTypeCodeTmp);
						response = academicKPIService.getByAcademicYearWorkTypeCodeFacultyCode(request);
						if(response.getStatus()==BuckWaConstants.SUCCESS){	
							AcademicKPIWrapper academicKPIWrapper = (AcademicKPIWrapper)response.getResObj("academicKPIWrapper");			 
				 
							newWork.setAcademicKPIList(academicKPIWrapper.getAcademicKPIList());
						}
						
						returnList.add(newWork);
						
					}
					
				} 

			}	 

		}catch(Exception ex){
			ex.printStackTrace();
			 
		}
		return returnList;
	}	
	
	
	
	//@RequestMapping(value="getAcademicWork.htm", method = RequestMethod.GET)
	//public ModelAndView initAcademicWorkGET(HttpServletRequest httpRequest  ) {
	@RequestMapping(value = "/getAcademicWork/{userName}/{academicYear}/{round}", method = RequestMethod.GET, headers = "Accept=application/json")
	public WorkSummary getAllWorkList(@PathVariable String userName,@PathVariable String academicYear,@PathVariable String round) {			
		
		logger.info(" Start "); 
		WorkSummary workSummary = new WorkSummary();
		workSummary.setAcademicYear(academicYear);
		try {  

			BuckWaRequest request = new BuckWaRequest(); 
			request.put("username", userName);
			request.put("academicYear",academicYear );

			BuckWaResponse  response = personProfileService.getByUsername(request);

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				Person person = (Person) response.getResObj("person");
				person.setAcademicYear(academicYear);
				person.setEvaluateRound(round);
 
				//String academicYear =schoolUtil.getCurrentAcademicYear();
				String facultyCode = person.getFacultyCode();
				request.put("academicYear",academicYear);
				request.put("userName",userName);
				request.put("round",round);
				request.put("employeeType",person.getEmployeeTypeNo());
				request.put("facultyCode",facultyCode);
				
				response = pBPWorkTypeService.getCalculateByAcademicYear(request);
				
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper"); 
					pBPWorkTypeWrapper.setAcademicYear(academicYear);
					person.setpBPWorkTypeWrapper(pBPWorkTypeWrapper);

					workSummary.setTotalMark(pBPWorkTypeWrapper.getTotalMark()+"");
					workSummary.setpBPWorkTypeList(pBPWorkTypeWrapper.getpBPWorkTypeList());
				}					
				 
			} 
		} catch(Exception ex) {
			ex.printStackTrace();
			 
		}

		return workSummary;
	}
	
	
	
	@RequestMapping(value = "/getAcademicKPI/{academicKPICode}/{facultyCode}/{academicYear}", method = RequestMethod.GET, headers = "Accept=application/json")
	public AcademicKPI getAcademicKPI(@PathVariable String academicKPICode,@PathVariable String facultyCode,@PathVariable String academicYear) {			
		
 		logger.info(" Start  academicKPICode:"+academicKPICode+" academicYear:"+academicYear);
 		AcademicKPI academicKPI  = new AcademicKPI();
		try{
			BuckWaRequest request = new BuckWaRequest();
			
			request.put("academicYear",academicYear);
			request.put("academicKPICode",academicKPICode);
			request.put("facultyCode",facultyCode);
			BuckWaResponse response = academicKPIService.getByCodeAcademicYear(request);
			 
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				 academicKPI = (AcademicKPI)response.getResObj("academicKPI");	
			 
				request.put("academicYear",academicYear);
				 response = academicUnitService.getByAcademicYear(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					AcademicUnitWrapper academicUnitWrapper = (AcademicUnitWrapper)response.getResObj("academicUnitWrapper");
					academicKPI.setAcademicUnitList(academicUnitWrapper.getAcademicUnitList());
					academicKPI.setRatio(new Integer(100));
					
					List<AcademicKPIAttribute> ratioList =academicKPI.getAcademicKPIAttributeList();
					for(AcademicKPIAttribute tmp:ratioList){
						String attributeName =tmp.getName();
//						logger.info(" Attribute Name:"+attributeName+" index of สัดส่วน:"+attributeName.indexOf("สัดส่วน"));
						
						if(attributeName.indexOf("สัดส่วน")!=-1){
							tmp.setValue("100");
						}
						
					}
				}	 
				//academicKPI.setIndex(index);
				//mav.addObject("academicKPI", academicKPI);
				
				// Delete Temp File
//				File uploadPath = new File(pathUtil.getPBPAttatchFilePath() + "temp/" + BuckWaUtils.getUserIdFromContext());
//				if (uploadPath.exists() && uploadPath.isDirectory()) {
//					FileUtils.deleteDirectory(uploadPath);
//				}
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			 
		}
		return academicKPI;
	}
	
 
		
		@RequestMapping(value = "/getImportWork/{kpiUserMappingId}", method = RequestMethod.GET, headers = "Accept=application/json")
		public AcademicKPIUserMapping getImportWork(@PathVariable String kpiUserMappingId) {			
					
		
		logger.info(" Start  kpiUserMappingId:"+kpiUserMappingId);
		AcademicKPIUserMapping kpiUserMapping = new AcademicKPIUserMapping();
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("kpiUserMappingId",kpiUserMappingId);
			BuckWaResponse response = academicKPIUserMappingService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper = (AcademicKPIUserMappingWrapper)response.getResObj("academicKPIUserMappingWrapper");	 
 
				
				kpiUserMapping = academicKPIUserMappingWrapper.getAcademicKPIUserMapping();
			}  
		}catch(Exception ex){
			ex.printStackTrace();
			 
		}
		return kpiUserMapping;
	}
		

		@RequestMapping(value = "/importwork", method = RequestMethod.POST)
		public ResponseObj jsonImportworkPOST(@RequestBody  AcademicKPI academicKPI) { 
			logger.info(" Start  ");
			ResponseObj resp = new ResponseObj();
			resp.setStatus("0");
			try{

		 
					//String userName = BuckWaUtils.getUserNameFromContext();
					String academicYear = schoolUtil.getCurrentAcademicYear();
					
					AcademicKPIUserMapping academicKPIUserMapping = new AcademicKPIUserMapping();
					academicKPIUserMapping.setUserName("ktpitak@kmitl.ac.th");
					academicKPIUserMapping.setAcademicYear(academicYear);
					academicKPIUserMapping.setAcademicKPICode(academicKPI.getCode());
					academicKPIUserMapping.setAcademicKPIId(academicKPI.getAcademicKPIId());
					academicKPIUserMapping.setWorkTypeCode(academicKPI.getWorkTypeCode());
					academicKPIUserMapping.setName(academicKPI.getName());
					academicKPIUserMapping.setRatio(academicKPI.getRatio());
					
					List<AcademicKPIAttribute> academicKPIAttributeList =academicKPI.getAcademicKPIAttributeList();
					
					List<AcademicKPIAttributeValue> academicKPIAttributeValueList = new ArrayList<AcademicKPIAttributeValue>();
					for(AcademicKPIAttribute tmp:academicKPIAttributeList){
						AcademicKPIAttributeValue valueTmp = new AcademicKPIAttributeValue();
						valueTmp.setAcademicKPICode(academicKPI.getCode());
						valueTmp.setAcademicYear(academicYear);
						valueTmp.setValue(tmp.getValue());
						valueTmp.setName(tmp.getName());
						//valueTmp.(tmp.getRownum());
						valueTmp.setRatio(tmp.getRatio());
					//	logger.info(" Controller attribute name:"+tmp.getName()+"  value:"+tmp.getValue());
						academicKPIAttributeValueList.add(valueTmp);
					} 
					
					academicKPIUserMapping.setAcademicKPIAttributeValueList(academicKPIAttributeValueList);
					
					academicKPIUserMapping.setStatus("CREATE");
					// Save
					BuckWaRequest request = new BuckWaRequest(); 
					request.put("academicKPIUserMapping",academicKPIUserMapping);
					request.put("tmpFileNameList", academicKPI.getTmpFileNameList());
					BuckWaResponse response = academicKPIService.importwork(request); 
					
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						Long academicKPIId = (Long)response.getResObj("academicKPIId");	
						academicKPI.setAcademicKPIUserMappingId(academicKPIId); 
						logger.info("  Save Success academicKPIId: "+academicKPIId);
					}  			
				 
			
			 			
			}catch(Exception ex){
				ex.printStackTrace();
				resp.setStatus("1");
				resp.setDescription(ex.getMessage());
			}
			logger.info(" End  ");
			return resp;
		}
	 
	
}
