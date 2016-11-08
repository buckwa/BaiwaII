package com.buckwa.web.controller.pam.person;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.buckwa.dao.intf.admin.LovHeaderDao;
import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.LovDetail;
import com.buckwa.domain.pam.FileLocation;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pam.WorkPerson;
import com.buckwa.domain.pam.WorkTemplate;
import com.buckwa.domain.pbp.AcademicKPI;
import com.buckwa.domain.pbp.AcademicKPIAttachFile;
import com.buckwa.domain.pbp.AcademicKPIAttribute;
import com.buckwa.domain.pbp.AcademicKPIAttributeValue;
import com.buckwa.domain.pbp.AcademicKPIUserMapping;
import com.buckwa.domain.pbp.AcademicKPIUserMappingWrapper;
import com.buckwa.domain.pbp.AcademicKPIWrapper;
import com.buckwa.domain.pbp.AcademicUnitWrapper;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeSub;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.domain.pbp.report.RadarPlotReport;
import com.buckwa.domain.pbp.report.TimeTableReport;
import com.buckwa.domain.validator.pam.PersonProfileValidator;
import com.buckwa.domain.validator.pbp.EditImportWorkValidator;
import com.buckwa.domain.validator.pbp.EditPersonProfilePBPValidator;
import com.buckwa.domain.validator.pbp.ImportWorkValidator;
import com.buckwa.domain.validator.pbp.ReplyPBPMessageValidator;
import com.buckwa.domain.webboard.Message;
import com.buckwa.service.intf.pam.FileLocationService;
import com.buckwa.service.intf.pam.PersonProfileService;
import com.buckwa.service.intf.pam.WorkPersonService;
import com.buckwa.service.intf.pam.WorkTemplateService;
import com.buckwa.service.intf.pbp.AcademicKPIService;
import com.buckwa.service.intf.pbp.AcademicKPIUserMappingService;
import com.buckwa.service.intf.pbp.AcademicUnitService;
import com.buckwa.service.intf.pbp.PBPWorkTypeService;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.service.intf.webboard.WebboardTopicService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.FileUtils;
import com.buckwa.util.PAMConstants;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;

@Controller
@RequestMapping("/pam/person") 
@SessionAttributes({"person","academicKPIWrapper","academicKPI","academicKPIUserMappingWrapper","academicKPIUserMapping"} )  
public class PersonProfileController {
	
	private static Logger logger = LoggerFactory.getLogger(PersonProfileController.class);

	@Autowired
	private PersonProfileService personProfileService;
	
	@Autowired
	private WorkTemplateService workTemplateService;
	
	@Autowired
	private WorkPersonService workPersonService;
	
	@Autowired
	private PBPWorkTypeService pBPWorkTypeService;	
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
	private AcademicKPIService academicKPIService;	
	
	@Autowired
	private AcademicKPIUserMappingService  academicKPIUserMappingService;
	
	@Autowired
	private AcademicUnitService academicUnitService;	
 
	@Autowired
    private PathUtil pathUtil;
	
	@Autowired
	private FileLocationService fileLocationService;
	
	@Autowired
	private LovHeaderDao lovHeaderDao;
	
	@Autowired
	private WebboardTopicService  webboardTopicService;
	
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList(HttpServletRequest httpRequest) {
		logger.info(" Start  academicYear:"+academicYearUtil.getAcademicYear());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initPerson");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		try {
			
			String academicYear = academicYearUtil.getAcademicYear();
			BuckWaUser user = BuckWaUtils.getUserFromContext();
			String facultyCode = BuckWaUtils.getFacultyCodeFromUserContext();
		 
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("username", user.getUsername());
			request.put("academicYear", academicYearUtil.getAcademicYear());
			BuckWaResponse response = personProfileService.getByUsername(request);
			
			
            TimeTableReport timetableReport = new TimeTableReport();
            timetableReport.setAcademicYearList(academicYearUtil.getAcademicYearList()); 
            timetableReport.setAcademicYear(academicYear);
            timetableReport.setAcademicYearSelect(academicYear); 
			mav.addObject("academicYearSelect", academicYear);
			

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				Person person = (Person) response.getResObj("person");
				user.setFirstLastName(person.getThaiName()+" "+person.getThaiSurname());
 
				
				person.setAcademicYear(academicYear);
				person.setAcademicYearList(academicYearUtil.getAcademicYearList());
				
				
				request.put("academicYear",academicYear);	 
				request.put("employeeType",person.getEmployeeTypeNo());
				response = pBPWorkTypeService.getCurretnEvalulate(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					String roundCurrent = (String)response.getResObj("round"); 
					person.setEvaluateRound(roundCurrent);
				}else{
					person.setEvaluateRound("1");
				}
				

				
			
				user.setPersonProfile(person);
				mav.addObject("person", person);
			  
				request.put("academicYear",academicYear);
				request.put("userName",BuckWaUtils.getUserNameFromContext());
				request.put("round",person.getEvaluateRound());
				request.put("employeeType",person.getEmployeeTypeNo());
				request.put("facultyCode",facultyCode);
				
				 
				response = pBPWorkTypeService.getExsistCalculateByAcademicYear(request);
				
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper"); 
					pBPWorkTypeWrapper.setAcademicYear(academicYear);
					person.setpBPWorkTypeWrapper(pBPWorkTypeWrapper);
					System.out.println("  Fist Login controller:"+pBPWorkTypeWrapper.getIsFirstLogin());
					if("Y".equalsIgnoreCase(pBPWorkTypeWrapper.getIsFirstLogin())){
						mav.addObject("errorCode", "E025");
					}
					
					
				}	
				

				
				request.put("academicYear", academicYear);
				request.put("userName", BuckWaUtils.getUserNameFromContext());
				request.put("round", person.getEvaluateRound());
				request.put("employeeType", person.getEmployeeType());
		 		request.put("facultyCode", facultyCode);

			 
				response = pBPWorkTypeService.getRadarPlotPersonMark(request);

				if (response.getStatus() == BuckWaConstants.SUCCESS) {
					List<RadarPlotReport> returnList = (List<RadarPlotReport>) response.getResObj("radarPlotReportList");
					List<Double> doubleList = new ArrayList();
					for(RadarPlotReport tmp:returnList){
						
						String valule2 =tmp.getAxisValue2();
						Double doubleTmp = new Double(valule2);
						doubleList.add(doubleTmp);						
					}
					
					Double maxValue =Collections.max(doubleList);
					maxValue=maxValue+300.00;
					mav.addObject("maxValue", maxValue+"");
					logger.info(" ######## Max Grap Value :"+maxValue);
					 
				}

 
				
			} else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode());
			}
			

			
		} catch(Exception ex) {
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
		}

		return mav;
	}
	
	
	@RequestMapping(value="recalculate.htm", method = RequestMethod.GET)
	public ModelAndView recalculate(HttpServletRequest httpRequest) {
		logger.info(" Start  academicYear:"+academicYearUtil.getAcademicYear());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initPerson");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		try {
			
			
			String academicYear = schoolUtil.getCurrentAcademicYear();
			
			BuckWaUser user = BuckWaUtils.getUserFromContext();
			String facultyCode = BuckWaUtils.getFacultyCodeFromUserContext();
		 
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("username", user.getUsername());
			request.put("academicYear", academicYear);
			BuckWaResponse response = personProfileService.getByUsername(request);
			
			
            TimeTableReport timetableReport = new TimeTableReport();
            timetableReport.setAcademicYearList(academicYearUtil.getAcademicYearList()); 
            timetableReport.setAcademicYear(academicYear);
            timetableReport.setAcademicYearSelect(academicYear); 
			mav.addObject("academicYearSelect", academicYear);
			

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				Person person = (Person) response.getResObj("person");
				user.setFirstLastName(person.getThaiName()+" "+person.getThaiSurname());
		 
				person.setAcademicYear(academicYear);
				person.setAcademicYearList(academicYearUtil.getAcademicYearList());
				person.setEvaluateRound("1");
				user.setPersonProfile(person);
				mav.addObject("person", person);
	 
				 
				request.put("academicYear",academicYear);
				request.put("userName",BuckWaUtils.getUserNameFromContext());
				request.put("round",person.getEvaluateRound());
				request.put("employeeType",person.getEmployeeTypeNo());
				request.put("facultyCode",facultyCode);
	 
				response = pBPWorkTypeService.getCalculateByAcademicYear(request);
				
				person.setEvaluateRound("2");
				pBPWorkTypeService.getCalculateByAcademicYear(request);
				
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper"); 
					pBPWorkTypeWrapper.setAcademicYear(academicYear);
					person.setpBPWorkTypeWrapper(pBPWorkTypeWrapper);
				}	
				


				 
				mav.setView(new RedirectView(httpRequest.getContextPath() + "/pam/person/init.htm"));
				
				
			} else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode());
			}
			

			
		} catch(Exception ex) {
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
		}

		return mav;
	}
	
	
	@RequestMapping(value="initAcademic.htm", method = RequestMethod.POST)
	public ModelAndView initAcademic(HttpServletRequest httpRequest, @ModelAttribute Person person) {
		String selectAcademicYear = person.getAcademicYear();
		String round = person.getEvaluateRound();
		
		logger.info(" Start  academicYear:"+selectAcademicYear+"  round:"+round+" employeeType:"+person.getEmployeeType());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initPerson");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		try {
			
			String academicYear = academicYearUtil.getAcademicYear();
			BuckWaUser user = BuckWaUtils.getUserFromContext();
			String facultyCode = BuckWaUtils.getFacultyCodeFromUserContext();
		 
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("username", user.getUsername());
			request.put("academicYear", academicYearUtil.getAcademicYear());
			BuckWaResponse response = personProfileService.getByUsername(request);
			
			
            TimeTableReport timetableReport = new TimeTableReport();
            timetableReport.setAcademicYearList(academicYearUtil.getAcademicYearList()); 
            timetableReport.setAcademicYear(academicYear);
            timetableReport.setAcademicYearSelect(academicYear); 
			mav.addObject("academicYearSelect", academicYear);
			

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				person = (Person) response.getResObj("person");
				user.setFirstLastName(person.getThaiName()+" "+person.getThaiSurname());
 
				
				person.setAcademicYear(academicYear);
				person.setAcademicYearList(academicYearUtil.getAcademicYearList());
				person.setEvaluateRound(round);
				user.setPersonProfile(person);
				mav.addObject("person", person);
			 
				
		 
				 
				request.put("academicYear",academicYear);
				request.put("userName",BuckWaUtils.getUserNameFromContext());
				request.put("round",round);
				request.put("employeeType",person.getEmployeeTypeNo());
				request.put("facultyCode",facultyCode);
				
				 
				response = pBPWorkTypeService.getExsistCalculateByAcademicYear(request);
				
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper"); 
					pBPWorkTypeWrapper.setAcademicYear(academicYear);
					person.setpBPWorkTypeWrapper(pBPWorkTypeWrapper);
				}	
				


				request.put("academicYear", academicYear);
				request.put("userName", BuckWaUtils.getUserNameFromContext());
				request.put("round", person.getEvaluateRound());
				request.put("employeeType", person.getEmployeeType());
				request.put("facultyCode", facultyCode);

			 
				response = pBPWorkTypeService.getRadarPlotPersonMark(request);

				if (response.getStatus() == BuckWaConstants.SUCCESS) {
					List<RadarPlotReport> returnList = (List<RadarPlotReport>) response.getResObj("radarPlotReportList");
					List<Double> doubleList = new ArrayList();
					for(RadarPlotReport tmp:returnList){
						
						String valule2 =tmp.getAxisValue2();
						Double doubleTmp = new Double(valule2);
						doubleList.add(doubleTmp);						
					}
					
					Double maxValue =Collections.max(doubleList);
					maxValue=maxValue+300.00;
					mav.addObject("maxValue", maxValue+"");
					logger.info(" ######## Max Grap Value :"+maxValue);
					 
				}
				/**----- Set Session --- */
				//httpRequest.getSession().setAttribute("personProFileSession" , person);
				
				
			} else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode());
			}
			

			
		} catch(Exception ex) {
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
		}
		return mav;
	}
	
	
	@RequestMapping(value="initAcademicWork.htm", method = RequestMethod.GET)
	public ModelAndView initAcademicWorkGET(HttpServletRequest httpRequest  ) {
//		logger.info(" Start "); 
		String selectAcademicYear =schoolUtil.getCurrentAcademicYear();
		String round = "1";
		 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicWork");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		try {
			
			
			
			
			
			
			BuckWaUser user = BuckWaUtils.getUserFromContext();
		//	logger.info("viewUserProfile  username :"+user.getUsername());

			BuckWaRequest request = new BuckWaRequest();
			
			
			
			request.put("academicYear",selectAcademicYear);	 
			request.put("employeeType",user.getPersonProfile().getEmployeeTypeNo());
			BuckWaResponse response = pBPWorkTypeService.getCurretnEvalulate(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				String roundCurrent = (String)response.getResObj("round"); 
				round =roundCurrent;
			} 
						
			
			
			
			request.put("username", user.getUsername());
			request.put("academicYear",selectAcademicYear );

			 response = personProfileService.getByUsername(request);

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				Person person = (Person) response.getResObj("person");
				person.setAcademicYear(selectAcademicYear);
				person.setEvaluateRound(round);
				user.setFirstLastName(person.getThaiName()+" "+person.getThaiSurname()); 
				
				mav.addObject("person", person); 
				//String academicYear =schoolUtil.getCurrentAcademicYear();
				String facultyCode = person.getFacultyCode();
				request.put("academicYear",selectAcademicYear);
				request.put("userName",BuckWaUtils.getUserNameFromContext());
				request.put("round",round);
				request.put("employeeType",person.getEmployeeTypeNo());
				request.put("facultyCode",facultyCode);
				
				response = pBPWorkTypeService.getCalculateByAcademicYear(request);
				
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper"); 
					pBPWorkTypeWrapper.setAcademicYear(selectAcademicYear);
					person.setpBPWorkTypeWrapper(pBPWorkTypeWrapper);
					/**----- Set Session --- */
					httpRequest.getSession().setAttribute("personProFileSession" , person);
					
				}					
				 
			}
			else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
		}

		return mav;
	}
	
	
	
	
	@RequestMapping(value="initAcademicWork.htm", method = RequestMethod.POST)
	public ModelAndView initAcademicWorkPOST(HttpServletRequest httpRequest ,@ModelAttribute Person person) {
		logger.info(" Start "); 
		String selectAcademicYear = person.getAcademicYear();
		String round = person.getEvaluateRound();
		logger.info(" Start  academicYear:"+selectAcademicYear+"  round:"+round+" employeeType:"+person.getEmployeeType());
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicWork");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		try {
			BuckWaUser user = BuckWaUtils.getUserFromContext();
			logger.info("viewUserProfile  username :"+user.getUsername());

			BuckWaRequest request = new BuckWaRequest();
			request.put("username", user.getUsername());
			request.put("academicYear", selectAcademicYear);

			BuckWaResponse response = personProfileService.getByUsername(request);

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				 person = (Person) response.getResObj("person");
				user.setFirstLastName(person.getThaiName()+" "+person.getThaiSurname()); 
				
				mav.addObject("person", person); 
				//String academicYear =schoolUtil.getCurrentAcademicYear();
				request.put("academicYear",selectAcademicYear);
				request.put("userName",BuckWaUtils.getUserNameFromContext());
				request.put("round",round);
				request.put("employeeType",person.getEmployeeTypeNo());
				request.put("facultyCode",person.getFacultyCode());
				
				response = pBPWorkTypeService.getCalculateByAcademicYear(request);
				
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper"); 
					pBPWorkTypeWrapper.setAcademicYear(selectAcademicYear);
					person.setpBPWorkTypeWrapper(pBPWorkTypeWrapper);
					/**----- Set Session --- */
					httpRequest.getSession().setAttribute("personProFileSession" , person);
					
				}					
				 
			}
			else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
		}

		return mav;
	}
	
	private List<WorkTemplate> getWorkTemplate(Person person){
		try{
			if(person!=null){
				BuckWaRequest request = new BuckWaRequest();
				//request.put("groupId", person.getEmployeeType());
				request.put("personType",person.getPersonType());
				BuckWaResponse response = workTemplateService.getByPersonType(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					List<WorkTemplate> workTemplateList = (List)response.getResObj("workTemplateList");
					return workTemplateList;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private List<WorkPerson> getWorkPerson(Long userId, Long workTemplateId) {
		List<WorkPerson> workPersonList = new ArrayList<WorkPerson>();
		try {
			WorkPerson workPerson = new WorkPerson();
			workPerson.setUserid(userId);
			workPerson.setWorkTemplateId(workTemplateId);
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("workPerson", workPerson);
			
			BuckWaResponse response = workPersonService.getByWorkTemplate(request);
			workPersonList = (List<WorkPerson>) response.getResObj("workPersonList");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return workPersonList;
	}
	
	
	@RequestMapping(value="view.htm", method = RequestMethod.GET)
	public ModelAndView initView() {
		logger.info(" Start ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		mav.setViewName("viewPerson");
		
		try {
			String username = BuckWaUtils.getUserNameFromContext();
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("username", username);
			BuckWaResponse response = personProfileService.getByUsername(request);
			
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				Person person = (Person) response.getResObj("person");
				
				// Set Date format
				person.setBirthdateStr(BuckWaDateUtils.getCustomFormat_thai_from_date(person.getBirthdate(),"dd MMMM yyyy"));
				person.setWorkingDateStr(BuckWaDateUtils.getCustomFormat_thai_from_date(person.getWorkingDate(),"dd MMMM yyyy"));
				person.setAssignDateStr(BuckWaDateUtils.getCustomFormat_thai_from_date(person.getAssignDate(),"dd MMMM yyyy"));
				person.setRetireDateStr(BuckWaDateUtils.getCustomFormat_thai_from_date(person.getRetireDate(),"dd MMMM yyyy"));
				
				mav.addObject("person", person);
			}
			else {
				logger.info(" Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
		}
		
		return mav;
	}


	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit() {
		logger.info(" Start ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		mav.setViewName("editPerson");

		try {
			String username = BuckWaUtils.getUserNameFromContext();
			logger.info("editUserProfile  username :"+username);

			BuckWaRequest request = new BuckWaRequest();
			request.put("username", username);
			BuckWaResponse response = personProfileService.getByUsername(request);

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				Person person = (Person) response.getResObj("person");
				
				// Set Date format
				person.setBirthdateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(person.getBirthdate()));
				person.setWorkingDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(person.getWorkingDate()));
				person.setAssignDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(person.getAssignDate()));
				person.setRetireDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(person.getRetireDate()));
				
				mav.addObject("person", person);
			}
			else {
				logger.info(" Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
		}

		return mav;
	}


	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(HttpServletRequest httpRequest, @ModelAttribute Person person, BindingResult result) {
		logger.info(" Start ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		String url = httpRequest.getContextPath() + "/pam/person/init.htm";

		try {
			
			new PersonProfileValidator().validate(person, result);
			if (result.hasErrors()) {
				mav.setViewName("editPerson");
			}
			else {
				person.setBirthdate(BuckWaDateUtils.parseDate(person.getBirthdateStr()));
				person.setWorkingDate(BuckWaDateUtils.parseDate(person.getWorkingDateStr()));
				person.setAssignDate(BuckWaDateUtils.parseDate(person.getAssignDateStr()));
				person.setRetireDate(BuckWaDateUtils.parseDate(person.getRetireDateStr()));
				
				BuckWaRequest request = new BuckWaRequest();
				request.put("domain", person);
	
				BuckWaResponse response = personProfileService.updateByUsername(request);
	
				if (response.getStatus() == BuckWaConstants.SUCCESS) {
					url += "?successCode=" + response.getSuccessCode();
				}
				else {
					logger.info(" Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
					mav.addObject("errorCode", response.getErrorCode());
				}
				
				mav.setView(new RedirectView(url));
			}

		} catch(Exception ex) {
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
		}
		
		return mav;
	}

 
	
	@RequestMapping(value="initWorkImport.htm", method = RequestMethod.GET)
	public ModelAndView initWorkImport(HttpServletRequest httpRequest ) {
		logger.info(" Start ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT); 
		mav.setViewName("initWorkImport");
		try {
			BuckWaRequest request = new BuckWaRequest();
			String academicYear =schoolUtil.getCurrentAcademicYear(); 
			String facultyCode = BuckWaUtils.getFacultyCodeFromUserContext();
			
			request.put("academicYear",academicYear); 
			request.put("facultyCode",facultyCode); 
			BuckWaResponse  response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
				List<PBPWorkType> workTypeListx = pBPWorkTypeWrapper.getpBPWorkTypeList();
				
				
				// Comment out
				
				/*
				List<PBPWorkType> workTypeList = new ArrayList();
				int workLoop =0;
				for(PBPWorkType tmp:workTypeListx){
					if(workLoop==0){
						
					}else{
						workTypeList.add(tmp);
					}
					workLoop++;
				}
				
				
				
				
				if(workTypeList!=null&&workTypeList.size()>0){
					PBPWorkType workType0 =	 workTypeList.get(0) ;
					request.put("workTypeCode",workType0.getCode());
				}
				
				*/
				// End comment out
				
				List<PBPWorkType> workTypeList =workTypeListx;
				if(workTypeList!=null&&workTypeList.size()>0){
					PBPWorkType workType0 =	 workTypeList.get(0) ;
					request.put("workTypeCode",workType0.getCode());
				}
				
				//response = academicKPIService.getByAcademicYearWorkTypeCode(request);
				response = academicKPIService.getByAcademicYearWorkTypeCodeFacultyCode(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					AcademicKPIWrapper academicKPIWrapper = (AcademicKPIWrapper)response.getResObj("academicKPIWrapper");			 
					academicKPIWrapper.setAcademicYear(academicYear); 
					academicKPIWrapper.setpBPWorkType(workTypeList.get(0));
					academicKPIWrapper.setpBPWorkTypeList(workTypeList);	
//					mav.addObject("successCode", response.getSuccessCode()); 
					academicKPIWrapper.setIndex("1");
					mav.addObject("academicKPIWrapper", academicKPIWrapper);	
				}

			}	 

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	@RequestMapping(value="listByWorktype.htm", method = RequestMethod.GET)
	public ModelAndView listByWorktype(@RequestParam("workTypeCode") String workTypeCode,@RequestParam("academicYear") String academicYear,@RequestParam("index") String index) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initWorkImport");
		//	mav.setViewName("initWorkImportByType");
		
		try{
			BuckWaRequest request = new BuckWaRequest();
			 
			String facultyCode =BuckWaUtils.getFacultyCodeFromUserContext();
			request.put("facultyCode",facultyCode);
			request.put("academicYear",academicYear);
			request.put("workTypeCode",workTypeCode);
			
			//BuckWaResponse response = academicKPIService.getByAcademicYearWorkTypeCode(request);
			BuckWaResponse response = academicKPIService.getByAcademicYearWorkTypeCodeFacultyCode(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicKPIWrapper academicKPIWrapper = (AcademicKPIWrapper)response.getResObj("academicKPIWrapper");			 
				academicKPIWrapper.setAcademicYear(academicYear);
				academicKPIWrapper.setWorkTypeCode(workTypeCode);
				//request.put("academicYear",academicYear);
				 //response = pBPWorkTypeService.getByAcademicYear(request);
				 response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
					
					// Comment out
					
					List<PBPWorkType> workTypeList = new ArrayList();
					int workLoop =0;
					for(PBPWorkType tmp:pBPWorkTypeWrapper.getpBPWorkTypeList()){
						//if(workLoop==0){
							
						//}else{
							workTypeList.add(tmp);
						//}
						workLoop++;
					}
					
					// End comment out
					
					
					academicKPIWrapper.setpBPWorkTypeList(workTypeList);
				} 
				request.put("workTypeCode",workTypeCode);
				 response = pBPWorkTypeService.getByCodeAcademicFacultyCode(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					PBPWorkType pBPWorkType  = (PBPWorkType)response.getResObj("pBPWorkType");
					 
					academicKPIWrapper.setpBPWorkType(pBPWorkType);
					
					
					List<PBPWorkTypeSub> workTypeSubList  =pBPWorkType.getpBPWorkTypeSubList();
					if(workTypeSubList!=null&&workTypeSubList.size()>0){
						// Set
					}
					 
				}				 
				academicKPIWrapper.setIndex(index);
				mav.addObject("academicKPIWrapper", academicKPIWrapper);	
			}	 

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}		
	
	@RequestMapping(value="importwork.htm", method = RequestMethod.GET)
	public ModelAndView importwork(@RequestParam("academicKPICode") String academicKPICode,@RequestParam("academicYear") String academicYear,@RequestParam("index") String index) {
		logger.info(" Start  academicKPICode:"+academicKPICode+" academicYear:"+academicYear);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("importwork");
		try{
			BuckWaRequest request = new BuckWaRequest();
			String facultyCode = BuckWaUtils.getFacultyCodeFromUserContext();
			request.put("academicYear",academicYear);
			request.put("academicKPICode",academicKPICode);
			request.put("facultyCode",facultyCode);
			BuckWaResponse response = academicKPIService.getByCodeAcademicYear(request);
			 
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicKPI academicKPI = (AcademicKPI)response.getResObj("academicKPI");	
				
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
				academicKPI.setIndex(index);
				mav.addObject("academicKPI", academicKPI);
				
				// Delete Temp File
				File uploadPath = new File(pathUtil.getPBPAttatchFilePath() + "temp/" + BuckWaUtils.getUserIdFromContext());
				if (uploadPath.exists() && uploadPath.isDirectory()) {
					FileUtils.deleteDirectory(uploadPath);
				}
				
			} else{
				mav.addObject("errorCode", "E001"); 
				mav.setViewName("initWorkImport");
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="importwork.htm", method = RequestMethod.POST)
	public ModelAndView importworkPOST(HttpServletRequest httpRequest, @ModelAttribute AcademicKPI academicKPI , BindingResult result) {
	//	logger.info(" Start  academicKPI:"+BeanUtils.getBeanString(academicKPI));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("importworkCreate");
		try{
		//	logger.info(" ## replyMessageNew:"+academicKPI.getReplyMessage());
			 
			//new ReplyPBPMessageNewValidator().validate(academicKPI, result);			
			//if (result.hasErrors()) {				
			//	mav.setViewName("importwork");
			//}
			new ImportWorkValidator().validate(academicKPI, result);			
			if (result.hasErrors()) {				
				mav.setViewName("importwork");
			}else {	
				
				AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper = new AcademicKPIUserMappingWrapper();
				mav.addObject("academicKPIUserMappingWrapper", academicKPIUserMappingWrapper); 
				
				
				String userName = BuckWaUtils.getUserNameFromContext();
				String academicYear = schoolUtil.getCurrentAcademicYear();
				
				AcademicKPIUserMapping academicKPIUserMapping = new AcademicKPIUserMapping();
				academicKPIUserMapping.setUserName(userName);
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
					//mav.addObject("successCode", response.getSuccessCode()); 
					
					BuckWaRequest requestRelyMessage = new BuckWaRequest();
					 
					Message newMessage = new Message();
					newMessage.setMessageDetail(academicKPI.getReplyMessage());
//					newMessage.setTopicId(academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId());
					newMessage.setTopicId(academicKPI.getAcademicKPIUserMappingId());
					newMessage.setStatus("1");				
					newMessage.setCreateBy(BuckWaUtils.getFullNameFromContext());
					
					requestRelyMessage.put("message", newMessage);
				//	logger.info(" replyMessage newMessage:"+BeanUtils.getBeanString(newMessage));
					BuckWaResponse responseRelyMessage = webboardTopicService.replyPBPMessage(requestRelyMessage);
					if(responseRelyMessage.getStatus()==BuckWaConstants.SUCCESS){		
					//	logger.info(" replyMessage newMessage Success");
						mav.addObject("successCode", responseRelyMessage.getSuccessCode()); 
						academicKPI.setReplyMessage("");
						mav = viewWork(academicKPI.getAcademicKPIUserMappingId()+"");	
					}else {
						mav.addObject("errorCode", responseRelyMessage.getErrorCode()); 
						 
					}	
					
//					String	url = httpRequest.getContextPath() + "/pam/person/initAcademicWork.htm"; 
					String	url = httpRequest.getContextPath() + "/pam/person/initWorkImport.htm"; 
					
					logger.info(" ### Redirect to :"+url);
					mav.setView(new RedirectView(url));
				} else{
					mav.addObject("errorCode", "E001"); 
					mav.setViewName("initWorkImport"); 
					
				}				
			 
				/*
			String	url = httpRequest.getContextPath() + "/pam/person/importwork.htm?academicKPICode="+academicKPI.getCode()+"&academicYear="+academicYear; 
			
			logger.info(" ### Redirect to :"+url);
			mav.setView(new RedirectView(url));
			return mav;	
				*/
			}
		 			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
 

	@RequestMapping(value="viewImportWork.htm", method = RequestMethod.GET)
	public ModelAndView viewImportWork(@RequestParam("kpiUserMappingId") String kpiUserMappingId  ) {
		logger.info(" Start  kpiUserMappingId:"+kpiUserMappingId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("viewImportWork");
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("kpiUserMappingId",kpiUserMappingId);
			BuckWaResponse response = academicKPIUserMappingService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper = (AcademicKPIUserMappingWrapper)response.getResObj("academicKPIUserMappingWrapper");	 
				mav.addObject("academicKPIUserMappingWrapper", academicKPIUserMappingWrapper);
				mav.addObject("academicKPIUserMapping", academicKPIUserMappingWrapper.getAcademicKPIUserMapping());
				
			} else{
				mav.addObject("errorCode", "E001");  
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
 
	public ModelAndView viewWork(@RequestParam("kpiUserMappingId") String kpiUserMappingId  ) {
		logger.info(" Start  kpiUserMappingId:"+kpiUserMappingId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("viewImportWork");
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("kpiUserMappingId",kpiUserMappingId);
			BuckWaResponse response = academicKPIUserMappingService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper = (AcademicKPIUserMappingWrapper)response.getResObj("academicKPIUserMappingWrapper");	 
				mav.addObject("academicKPIUserMappingWrapper", academicKPIUserMappingWrapper);
				mav.addObject("academicKPIUserMapping", academicKPIUserMappingWrapper.getAcademicKPIUserMapping());
				
			} else{
				mav.addObject("errorCode", "E001");  
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	public ModelAndView viewWorkCreate(@RequestParam("kpiUserMappingId") String kpiUserMappingId  ) {
		logger.info(" Start  kpiUserMappingId:"+kpiUserMappingId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("importworkCreate");
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("kpiUserMappingId",kpiUserMappingId);
			BuckWaResponse response = academicKPIUserMappingService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper = (AcademicKPIUserMappingWrapper)response.getResObj("academicKPIUserMappingWrapper");	 
				mav.addObject("academicKPIUserMappingWrapper", academicKPIUserMappingWrapper);
				mav.addObject("academicKPIUserMapping", academicKPIUserMappingWrapper.getAcademicKPIUserMapping());
				
			} else{
				mav.addObject("errorCode", "E001");  
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	@RequestMapping(value="editKPIMapping.htm", method = RequestMethod.GET)
	public ModelAndView editKPIMapping(@RequestParam("kpiUserMappingId") String kpiUserMappingId  ) {
		logger.info(" Start  kpiUserMappingId:"+kpiUserMappingId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("editKPIMapping");
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("kpiUserMappingId",kpiUserMappingId);
			BuckWaResponse response = academicKPIUserMappingService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper = (AcademicKPIUserMappingWrapper)response.getResObj("academicKPIUserMappingWrapper");	 
				mav.addObject("academicKPIUserMappingWrapper", academicKPIUserMappingWrapper);
				mav.addObject("academicKPIUserMapping", academicKPIUserMappingWrapper.getAcademicKPIUserMapping());
				
				
				String academicYear = schoolUtil.getCurrentAcademicYear();
				String workTypeCode ="1";
				String facultyCode =BuckWaUtils.getFacultyCodeFromUserContext();
				request.put("facultyCode",facultyCode);
				request.put("academicYear",academicYear);
				request.put("workTypeCode",workTypeCode);
				 response = academicKPIService.getByAcademicYearWorkTypeCode(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					AcademicKPIWrapper academicKPIWrapper = (AcademicKPIWrapper)response.getResObj("academicKPIWrapper");			 
					academicKPIWrapper.setAcademicYear(academicYear);
					academicKPIWrapper.setWorkTypeCode(workTypeCode);
					request.put("academicYear",academicYear);
					// response = pBPWorkTypeService.getByAcademicYear(request);
					 response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
						
						// Comment out
						
						List<PBPWorkType> workTypeList = new ArrayList();
						int workLoop =0;
						for(PBPWorkType tmp:pBPWorkTypeWrapper.getpBPWorkTypeList()){
							//if(workLoop==0){
								
							//}else{
								workTypeList.add(tmp);
							//}
							workLoop++;
						}
						
						// End comment out
						
						
						academicKPIWrapper.setpBPWorkTypeList(workTypeList);
					} 
					request.put("workTypeCode",workTypeCode);
					 response = pBPWorkTypeService.getByCodeAcademicFacultyCode(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						PBPWorkType pBPWorkType  = (PBPWorkType)response.getResObj("pBPWorkType");
						 
						academicKPIWrapper.setpBPWorkType(pBPWorkType);
						
						
						List<PBPWorkTypeSub> workTypeSubList  =pBPWorkType.getpBPWorkTypeSubList();
						if(workTypeSubList!=null&&workTypeSubList.size()>0){
							// Set
						}
						 
					}				 
					mav.addObject("academicKPIWrapper", academicKPIWrapper);	
				}	 				
				
			} else{
				mav.addObject("errorCode", "E001");  
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	@RequestMapping(value="editKPI.htm", method = RequestMethod.GET)
	public ModelAndView editKPI(@RequestParam("kpiUserMappingId") String kpiUserMappingId  ) {
		logger.info(" Start  kpiUserMappingId:"+kpiUserMappingId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("editKPIImportWork");
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("kpiUserMappingId",kpiUserMappingId);
			BuckWaResponse response = academicKPIUserMappingService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper = (AcademicKPIUserMappingWrapper)response.getResObj("academicKPIUserMappingWrapper");	 
				mav.addObject("academicKPIUserMappingWrapper", academicKPIUserMappingWrapper);
				mav.addObject("academicKPIUserMapping", academicKPIUserMappingWrapper.getAcademicKPIUserMapping());
				
			} else{
				mav.addObject("errorCode", "E001");  
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	
	@RequestMapping(value="editKPIMapping.htm", method = RequestMethod.POST)
	public ModelAndView editKPIMappingPOST(HttpServletRequest httpRequest, @ModelAttribute AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper , BindingResult result) {
		logger.info(" Start  academicKPIUserMappingWrapper academicSelectId :"+ academicKPIUserMappingWrapper.getAcademicSelectId());
		logger.info(" Start  academicKPIUserMappingWrapper userMappingId :"+ academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("viewImportWork");
		try{
			
			// Change KPI
			BuckWaRequest request = new BuckWaRequest();
 		    request.put("academicKPIId", academicKPIUserMappingWrapper.getAcademicSelectId());
			BuckWaResponse response = academicKPIService.getById(request);
			
			AcademicKPI academicKPI = null;
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				academicKPI  = (AcademicKPI)response.getResObj("academicKPI");	
			}
 
			
			academicKPIUserMappingWrapper.getAcademicKPIUserMapping().setAcademicKPI(academicKPI);
			
			 request.put("academicKPIUserMappingWrapper", academicKPIUserMappingWrapper);
			academicKPIUserMappingService.changeKPI(request);
			
			
			
			List<AcademicKPIAttributeValue> academicKPIAttributeValueList =academicKPIUserMappingWrapper.getAcademicKPIAttributeValueList();
			
			for(final AcademicKPIAttributeValue tmp:academicKPIAttributeValueList){
				tmp.setAcademicKPIMappingId(academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId());
	            
				logger.info("  update :"+BeanUtils.getBeanString(tmp));
			 		
			}
			academicKPIUserMappingWrapper.getAcademicKPIUserMapping().setAcademicKPIAttributeValueList(academicKPIAttributeValueList);
			
			new EditImportWorkValidator().validate(academicKPIUserMappingWrapper, result);			
			if (result.hasErrors()) {				
				mav.setViewName("viewImportWork");
				
				mav.addObject("errorDesc", "Require Field Cannot be left blank"); 
				
				logger.info(" ## editImportworkPOST Found Error Validate Fail ###");
			}else {	
				
			
				
				
				//List<AcademicKPIAttributeValue> academicKPIAttributeValueListOld =academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getAcademicKPIAttributeValueList();
				

				
			 
				
				
				request.put("academicKPIUserMappingWrapper",academicKPIUserMappingWrapper);
				 response = academicKPIUserMappingService.update(request); 
				
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					mav.addObject("successCode", response.getSuccessCode()); 
				}
				
				request.put("kpiUserMappingId",academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId());
				response = academicKPIUserMappingService.getById(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					academicKPIUserMappingWrapper = (AcademicKPIUserMappingWrapper)response.getResObj("academicKPIUserMappingWrapper");	
		 
					mav.addObject("academicKPIUserMappingWrapper", academicKPIUserMappingWrapper);
					
					
				} else{
					mav.addObject("errorCode", "E001"); 
				 
					
				}			
 
			}
		 			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="editImportwork.htm", method = RequestMethod.POST)
	public ModelAndView editImportworkPOST(HttpServletRequest httpRequest, @ModelAttribute AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper , BindingResult result) {
//		logger.info(" Start  academicKPIUserMappingWrapper:"+BeanUtils.getBeanString(academicKPIUserMappingWrapper));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("viewImportWork");
		try{
			List<AcademicKPIAttributeValue> academicKPIAttributeValueList =academicKPIUserMappingWrapper.getAcademicKPIAttributeValueList();
			
			for(final AcademicKPIAttributeValue tmp:academicKPIAttributeValueList){
				tmp.setAcademicKPIMappingId(academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId());
	            
				//logger.info("  update :"+BeanUtils.getBeanString(tmp));
			 		
			}
			academicKPIUserMappingWrapper.getAcademicKPIUserMapping().setAcademicKPIAttributeValueList(academicKPIAttributeValueList);
			
			new EditImportWorkValidator().validate(academicKPIUserMappingWrapper, result);			
			if (result.hasErrors()) {				
				mav.setViewName("viewImportWork");
					
				logger.info(" ## editImportworkPOST Found Error Validate Fail ###");
			}else {	
				
				BuckWaRequest request = new BuckWaRequest();
				
				
				//List<AcademicKPIAttributeValue> academicKPIAttributeValueListOld =academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getAcademicKPIAttributeValueList();
				

				
			 
				
				
				request.put("academicKPIUserMappingWrapper",academicKPIUserMappingWrapper);
				BuckWaResponse response = academicKPIUserMappingService.update(request); 
				
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					mav.addObject("successCode", response.getSuccessCode()); 
				}
				
				request.put("kpiUserMappingId",academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId());
				response = academicKPIUserMappingService.getById(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					academicKPIUserMappingWrapper = (AcademicKPIUserMappingWrapper)response.getResObj("academicKPIUserMappingWrapper");	
		 
					mav.addObject("academicKPIUserMappingWrapper", academicKPIUserMappingWrapper);
					
					
				} else{
					mav.addObject("errorCode", "E001"); 
				 
					
				}	
				
				String	url = httpRequest.getContextPath() + "/pam/person/initAcademicWork.htm"; 
				
			//	logger.info(" ### Redirect to :"+url);
				mav.setView(new RedirectView(url));
 
			}
		 			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="replyMessage.htm", method = RequestMethod.POST)
	public ModelAndView replyMessage(HttpServletRequest httpRequest, @ModelAttribute AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper , BindingResult result) {
		ModelAndView mav = new ModelAndView();
		try{			
			logger.info(" ## replyMessage:"+academicKPIUserMappingWrapper.getReplyMessage());
			 
			new ReplyPBPMessageValidator().validate(academicKPIUserMappingWrapper, result);			
			if (result.hasErrors()) {				
				mav.setViewName("viewImportWork");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				 
				
				Message newMessage = new Message();
				newMessage.setMessageDetail(academicKPIUserMappingWrapper.getReplyMessage());
				newMessage.setTopicId(academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId());
				newMessage.setStatus("1");				
				newMessage.setCreateBy(BuckWaUtils.getFullNameFromContext());
				request.put("message", newMessage);
				logger.info(" replyMessage newMessage:"+BeanUtils.getBeanString(newMessage));
				BuckWaResponse response = webboardTopicService.replyPBPMessage(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
					logger.info(" replyMessage newMessage Success");
					mav.addObject("successCode", response.getSuccessCode()); 
					academicKPIUserMappingWrapper.setReplyMessage("");
					mav = viewWork(academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId()+"");	
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					 
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="deleteImportWork.htm", method = RequestMethod.GET)
	public ModelAndView deleteImportWork(@RequestParam("kpiUserMappingId") String kpiUserMappingId ,HttpServletRequest httpRequest ) {
		logger.info(" Start  kpiUserMappingId:"+kpiUserMappingId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("viewImportWork");
		try{
			BuckWaRequest request = new BuckWaRequest();
			 
		  
			request.put("kpiUserMappingId",kpiUserMappingId);
			BuckWaResponse response = academicKPIUserMappingService.delete(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
 
				mav=initAcademicWorkGET(httpRequest);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
 
	@RequestMapping(value="uploadAttatchFile.htm")
	public ModelAndView uploadAttatchFile(@ModelAttribute AcademicKPI academicKPI, BindingResult result  ,HttpServletRequest httpRequest ) {
	 
		logger.info("---- Wait For Uploading File  uploadAttatchFile ----");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("importwork");
		
		FileLocation fileLocation = new FileLocation();
		
		try{			 			 

			MultipartFile  originalfile = academicKPI.getFileData(); 
			
			logger.info(" originalfile size:"+originalfile.getSize()+" File Name:"+ originalfile.getOriginalFilename() );
			
			AcademicKPIAttachFile academicKPIAttachFile = new AcademicKPIAttachFile();
			
		 
			 
				
				logger.info(" originalfile size:"+originalfile.getSize()+" File Name:"+ originalfile.getOriginalFilename() );
				
				if (originalfile!=null&&originalfile.getSize() > 0) {
					if (originalfile.getSize() > pathUtil.getMaximumImageUploadSize()) {
						logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumImageUploadSize());					 
						mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_TOO_LARGE); 
						return mav;
					}else{		
						
						//  For Upload File >>>>
						String uploadPath =  pathUtil.getPBPAttatchFilePath()+academicKPI.getAcademicKPIUserMappingId();
						String file = originalfile.getOriginalFilename();
						String fullFilePathName =uploadPath+"/"+file;
						String fileName = file.substring(0,file.lastIndexOf("."));
						String fileExtension = file.substring(file.lastIndexOf("."));
						String fileType = originalfile.getContentType();
						String fileSize = Long.toString(originalfile.getSize());
						
						// Add Data to Keep The Upload File Log...
						Date date = new Date();

						
						
						academicKPIAttachFile.setKpiUserMappingId(academicKPI.getAcademicKPIUserMappingId()+"");
						academicKPIAttachFile.setFullFilePathName(fullFilePathName);
						academicKPIAttachFile.setFileName(file);
						academicKPIAttachFile.setCreateBy(BuckWaUtils.getUserNameFromContext());
						
						
						logger.info("FILE >> "+academicKPIAttachFile);
						logger.info("## File Size :" + originalfile.getSize());
						logger.info("## File Name Original :" + file);
						logger.info("## Upload Path :" + uploadPath);
						logger.info("## createBy :" + academicKPIAttachFile.getCreateBy());
						//String fileUpload = uploadPath+file;
						String fileUpload = fullFilePathName;
						
						logger.info("## File Name + Path :" + fileUpload);
						
						int step = 1 ; 
						boolean isnext = true;
						 
						
						while(isnext){
							switch (step) {
							case 1 :
								logger.info("Step : "+step+" >>  Create New Upload Path");
								isnext = FileUtils.createDirectoryIfNotExist(uploadPath);
								if(isnext){
									step++; 
									continue;
								}else{
									isnext = false;
								}
							case 2 : 
								logger.info("Step : "+step+" >> Save File To Server directory path");
								
								//boolean isFileNameExist = fileLocationService.checkFileNameServerExist(fileName,BuckWaConstants.PERSON_TABLE);
								//if(!isFileNameExist){
									isnext = FileUtils.saveFileToDirectory(originalfile, fileUpload);
									if(isnext){
										step++; 
										continue;
									}else{
										isnext = false;
									}
								//}else{
								//	isnext = false;
								//	mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_NAME_EXIST); 
								//	return mav;
								//}
							case 3 :
								logger.info(" Step : "+step+" >> Insert into File createPBPAttachFile Database (table : academic_kpi_attach_file) For File Upload History");
								Long key = fileLocationService.createPBPAttachFile(academicKPIAttachFile);
								if(isnext && null != key){
									step++; 
									continue;
								}else{
									isnext = false;
								} 
							default:
								isnext = false;
							}
						} 
	 
					}	
					
					mav =  viewWork( academicKPI.getAcademicKPIUserMappingId()+"");
					
					return  mav;
 
				}	else{				
					logger.info(" ## Error Please Select file");
					mav.addObject("errorCode", BuckWaConstants.MSGCODE_SELECT_FILE); 
					//mav =  viewWork( academicKPI.getAcademicKPIUserMappingId()+"");
					return mav;					
				}		 
	 		 			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		return null;
	}	
	
	/*
	 * Using in importWork.htm only
	 * */
	@RequestMapping(value="uploadAttatchFileCreate.htm")
	public ModelAndView uploadAttatchFileCreate(@ModelAttribute AcademicKPI academicKPI, BindingResult result  ,HttpServletRequest httpRequest ) {
	 
		logger.info("---- Wait For Uploading File  uploadAttatchFile ----");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("importwork");
		
		FileLocation fileLocation = new FileLocation();
		
		try{			 			 

			MultipartFile  originalfile = academicKPI.getFileData(); 
			
			logger.info(" originalfile size:"+originalfile.getSize()+" File Name:"+ originalfile.getOriginalFilename() );
			
			AcademicKPIAttachFile academicKPIAttachFile = new AcademicKPIAttachFile();
			
		 
			 
				
				logger.info(" originalfile size:"+originalfile.getSize()+" File Name:"+ originalfile.getOriginalFilename() );
				
				if (originalfile!=null&&originalfile.getSize() > 0) {
					if (originalfile.getSize() > pathUtil.getMaximumImageUploadSize()) {
						logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumImageUploadSize());					 
						mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_TOO_LARGE); 
						return mav;
					}else{		
						
						//  For Upload File >>>>
//						String uploadPath =  pathUtil.getPBPAttatchFilePath()+academicKPI.getAcademicKPIUserMappingId();
						String uploadPath =  pathUtil.getPBPAttatchFilePath() + "temp/" + BuckWaUtils.getUserIdFromContext();
						String file = originalfile.getOriginalFilename();
						String fullFilePathName =uploadPath+"/"+file;
						String fileName = file.substring(0,file.lastIndexOf("."));
						String fileExtension = file.substring(file.lastIndexOf("."));
						String fileType = originalfile.getContentType();
						String fileSize = Long.toString(originalfile.getSize());
						
						// Add Data to Keep The Upload File Log...
						Date date = new Date();

						
						
//						academicKPIAttachFile.setKpiUserMappingId(academicKPI.getAcademicKPIUserMappingId()+"");
						academicKPIAttachFile.setFullFilePathName(fullFilePathName);
						academicKPIAttachFile.setFileName(file);
						
						
						logger.info("FILE >> "+academicKPIAttachFile);
						logger.info("## File Size :" + originalfile.getSize());
						logger.info("## File Name Original :" + file);
						logger.info("## Upload Path :" + uploadPath);
						
						//String fileUpload = uploadPath+file;
						String fileUpload = fullFilePathName;
						
						logger.info("## File Name + Path :" + fileUpload);
						
						int step = 1 ; 
						boolean isnext = true;
						 
						
						while(isnext){
							switch (step) {
							case 1 :
								logger.info("Step : "+step+" >>  Create New Upload Path");
								isnext = FileUtils.createDirectoryIfNotExist(uploadPath);
								if(isnext){
									step++; 
									continue;
								}else{
									isnext = false;
								}
							case 2 : 
								logger.info("Step : "+step+" >> Save File To Server directory path");
								
								//boolean isFileNameExist = fileLocationService.checkFileNameServerExist(fileName,BuckWaConstants.PERSON_TABLE);
								//if(!isFileNameExist){
									isnext = FileUtils.saveFileToDirectory(originalfile, fileUpload);
									if(isnext){
										step++; 
										continue;
									}else{
										isnext = false;
									}
								//}else{
								//	isnext = false;
								//	mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_NAME_EXIST); 
								//	return mav;
								//}
//							case 3 :
//								logger.info(" Step : "+step+" >> Insert into File createPBPAttachFile Database (table : academic_kpi_attach_file) For File Upload History");
//								Long key = fileLocationService.createPBPAttachFile(academicKPIAttachFile);
//								if(isnext && null != key){
//									step++; 
//									continue;
//								}else{
//									isnext = false;
//								} 
							default:
								isnext = false;
							}
						} 
	 
					}	
					
					mav =  viewWorkCreateTemp();
					
					return  mav;
 
				}	else{				
					logger.info(" ## Error Please Select file");
					mav.addObject("errorCode", BuckWaConstants.MSGCODE_SELECT_FILE); 
					//mav =  viewWork( academicKPI.getAcademicKPIUserMappingId()+"");
					return mav;					
				}		 
	 		 			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		return null;
	}	
	 
	
	@RequestMapping(value="uploadAttatchFileEdit.htm")
	public ModelAndView uploadAttatchFileEdit(@ModelAttribute AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper, BindingResult result  ,HttpServletRequest httpRequest ) {
	 
		logger.info("---- Wait For Uploading File ----");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("viewImportWork");
		
		//FileLocation fileLocation = new FileLocation();
		
		AcademicKPIAttachFile academicKPIAttachFile = new AcademicKPIAttachFile();
		
		try{			 			 

			MultipartFile  originalfile = academicKPIUserMappingWrapper.getFileData(); 
			
			logger.info(" originalfile size:"+originalfile.getSize()+" File Name:"+ originalfile.getOriginalFilename() );
			
			if (originalfile!=null&&originalfile.getSize() > 0) {
				if (originalfile.getSize() > pathUtil.getMaximumImageUploadSize()) {
					logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumImageUploadSize());					 
					mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_TOO_LARGE); 
					return mav;
				}else{		
					
					//  For Upload File >>>>
					String uploadPath =  pathUtil.getPBPAttatchFilePath()+academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId();
					String file = originalfile.getOriginalFilename();
					String fullFilePathName =uploadPath+"/"+file;
					String fileName = file.substring(0,file.lastIndexOf("."));
					String fileExtension = file.substring(file.lastIndexOf("."));
					String fileType = originalfile.getContentType();
					String fileSize = Long.toString(originalfile.getSize());
					
					// Add Data to Keep The Upload File Log...
					Date date = new Date();

					
					
					academicKPIAttachFile.setKpiUserMappingId(academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId()+"");
					academicKPIAttachFile.setFullFilePathName(fullFilePathName);
					academicKPIAttachFile.setFileName(file);
					academicKPIAttachFile.setCreateBy(BuckWaUtils.getUserNameFromContext());
					
					logger.info("FILE >> "+academicKPIAttachFile);
					logger.info("## File Size :" + originalfile.getSize());
					logger.info("## File Name Original :" + file);
					logger.info("## Upload Path :" + uploadPath);
					logger.info("## Create By:" + academicKPIAttachFile.getCreateBy());
					//String fileUpload = uploadPath+file;
					String fileUpload = fullFilePathName;
					
					logger.info("## File Name + Path :" + fileUpload);
					
					int step = 1 ; 
					boolean isnext = true;
					 
					
					while(isnext){
						switch (step) {
						case 1 :
							logger.info("Step : "+step+" >>  Create New Upload Path");
							isnext = FileUtils.createDirectoryIfNotExist(uploadPath);
							if(isnext){
								step++; 
								continue;
							}else{
								isnext = false;
							}
						case 2 : 
							logger.info("Step : "+step+" >> Save File To Server directory path");
							
							//boolean isFileNameExist = fileLocationService.checkFileNameServerExist(fileName,BuckWaConstants.PERSON_TABLE);
							//if(!isFileNameExist){
								isnext = FileUtils.saveFileToDirectory(originalfile, fileUpload);
								if(isnext){
									step++; 
									continue;
								}else{
									isnext = false;
								}
							//}else{
							//	isnext = false;
							//	mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_NAME_EXIST); 
							//	return mav;
							//}
						case 3 :
							logger.info(" Step : "+step+" >> Insert into File createPBPAttachFile Database (table : academic_kpi_attach_file) For File Upload History");
							Long key = fileLocationService.createPBPAttachFile(academicKPIAttachFile);
							if(isnext && null != key){
								step++; 
								continue;
							}else{
								isnext = false;
							} 
						default:
							isnext = false;
						}
					} 
 
				}	
				
				mav =  viewWork( academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId()+"");
				
			}else{				
				logger.info(" ## Error Please Select file");
				mav.addObject("errorCode", BuckWaConstants.MSGCODE_SELECT_FILE); 
				BuckWaRequest request = new BuckWaRequest();
				request.put("kpiUserMappingId", academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId());
				BuckWaResponse response = academicKPIUserMappingService.getById(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					academicKPIUserMappingWrapper = (AcademicKPIUserMappingWrapper)response.getResObj("academicKPIUserMappingWrapper");	 
					mav.addObject("academicKPIUserMappingWrapper", academicKPIUserMappingWrapper);
					mav.addObject("academicKPIUserMapping", academicKPIUserMappingWrapper.getAcademicKPIUserMapping());
					
				}  
				return mav;					
			}			 			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		return mav;
	}	
	
	
	@RequestMapping(value="deleteAttachFile.htm")
	public ModelAndView deleteAttachFile(@RequestParam("kpiUserMappingId") String kpiUserMappingId ,@RequestParam("attachFileId") String attachFileId ,HttpServletRequest httpRequest ) {
	 
		logger.info(" kpiUserMappingId:"+kpiUserMappingId+" attachFileId:"+attachFileId);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("viewImportWork"); 
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("attachFileId",attachFileId);
			fileLocationService.deletePBPAttachFile(request);
			mav =  viewWork( kpiUserMappingId);
 	 			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		return mav;
	}	
	
	
	@RequestMapping(value="deleteAttachFileCreate.htm")
	public ModelAndView deleteAttachFileCreate(@RequestParam("kpiUserMappingId") String kpiUserMappingId ,@RequestParam("attachFileId") String attachFileId ,HttpServletRequest httpRequest ) {
	 
		logger.info(" kpiUserMappingId:"+kpiUserMappingId+" attachFileId:"+attachFileId);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("viewImportWork"); 
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("attachFileId",attachFileId);
			fileLocationService.deletePBPAttachFile(request);
			mav =  viewWorkCreate( kpiUserMappingId);
 	 			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		return mav;
	}	
	
	@RequestMapping(value="downloadAttachFile.htm")
	public ModelAndView downloadAttachFile(@RequestParam("attachFileId") String attachFileId ,HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		
		logger.info("#####  Start  Download   << "+ attachFileId +" >> #### ");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		AcademicKPIAttachFile academicKPIAttachFile = new AcademicKPIAttachFile();
		
		try {
			
			academicKPIAttachFile = fileLocationService.findPBPAttachFile(attachFileId);
			
			logger.info("File >>"+academicKPIAttachFile);
			
			//String filePath = fileLocation.getFilePath();
			//String fileName = fileLocation.getFileName()+fileLocation.getFileExtension();
			//String fullPath = filePath+fileName;
			
			InputStream inputStream = new FileInputStream(academicKPIAttachFile.getFullFilePathName());
			
			//httpResponse.setContentType(fileLocation.getFileType());
			//httpResponse.setContentLength(Integer.parseInt(fileLocation.getFileSize()));
			
			// Check For IE OR NOT for Encoder fileName !
			String user_agent = httpRequest.getHeader("user-agent");
			boolean isInternetExplorer = (user_agent.indexOf(BuckWaConstants.BROWSER_MSIE) > -1);
			if (isInternetExplorer) {
				logger.info("Hello You Are IE ");
				httpResponse.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(academicKPIAttachFile.getFileName(), "utf-8") + "\"");
			} else {
				logger.info("Hello You Not IE ");
				httpResponse.setHeader("Content-disposition", "attachment; filename=\"" + MimeUtility.encodeWord(academicKPIAttachFile.getFileName()) + "\"");
			}
			
			FileCopyUtils.copy( inputStream, httpResponse.getOutputStream());
			   
			httpResponse.flushBuffer();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001);
		}
		
		return null;

	}
	
	
	@RequestMapping(value="editProfile.htm", method = RequestMethod.GET)
	public ModelAndView editProfileGet(@RequestParam("personId") String personId) {
		logger.info(" Start ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		mav.setViewName("editPersonProfile");

		try {
			String username = BuckWaUtils.getUserNameFromContext();
			logger.info("editUserProfile  username :"+username);

			BuckWaRequest request = new BuckWaRequest(); 
			request.put("username", username);
			request.put("academicYear", academicYearUtil.getAcademicYear());
			BuckWaResponse response = personProfileService.getByUsername(request);

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				Person person = (Person) response.getResObj("person"); 
				
				// academicRank, maxEducation 
				List<LovDetail> lovAcademicRankList     = lovHeaderDao.getLovAcademicRankList();
				List<LovDetail> lovMaxEducationList = lovHeaderDao.getLovMaxEducationList();
				
				person.setLovMaxEducationList(lovMaxEducationList);
				person.setLovAcademicRankList(lovAcademicRankList);
				
				mav.addObject("person", person);
			}
			else {
				logger.info(" Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
		}

		return mav;
	}

	
	@RequestMapping(value="editProfile.htm", method = RequestMethod.POST)
	public ModelAndView editProfilePOST(HttpServletRequest httpRequest, @ModelAttribute Person person, BindingResult result) {
		logger.info(" Start ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		String url = httpRequest.getContextPath() + "/pam/person/init.htm";
		
		mav.setViewName("editPersonProfile");
		try {
			
			new EditPersonProfilePBPValidator().validate(person, result);
			if (result.hasErrors()) {
				
			} else { 
				BuckWaRequest request = new BuckWaRequest();
				request.put("domain", person); 
				BuckWaResponse response = personProfileService.updateByUsername(request); 
				if (response.getStatus() == BuckWaConstants.SUCCESS) {
					 
					mav.addObject("successCode", response.getSuccessCode());
				} else {
					logger.info(" Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
					mav.addObject("errorCode", response.getErrorCode());
				} 
				 
			}

		} catch(Exception ex) {
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001);
		}
		
		return mav;
	}

 
	@RequestMapping(value="uploadPersonProfilePicture.htm")
	public ModelAndView uploadPersonProfilePicture(@ModelAttribute Person person, BindingResult result, HttpServletRequest httpRequest) {
	 
		logger.info("---- Wait For Uploading File ----");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("editPersonProfile");
		try {			 			 

			MultipartFile originalfile = person.getFileData();
			
			if (originalfile!=null&&originalfile.getSize() > 0) {
				logger.info(" originalfile size:"+originalfile.getSize()+" File Name:"+ originalfile.getOriginalFilename() );
				if (originalfile.getSize() > pathUtil.getMaximumImageUploadSize()) {
					logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumImageUploadSize());					 
					mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_TOO_LARGE);
				}else if (originalfile.getOriginalFilename().indexOf("jpg")<0) {
						logger.info(" Error File Extension: " );					 
						mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_EXTENSION_INVALID_JPG);
				}else {		
					
					//  For Upload File >>>>
					String uploadPath = PAMConstants.rbApp.getString("profile.picture.dir");
					logger.info("## File Size :" + originalfile.getSize());
					logger.info("## File Name Original :" + originalfile.getOriginalFilename());
					logger.info("## Upload Path :" + uploadPath);
					
					String fileUpload = uploadPath + person.getPersonId()+".jpg";
					
					logger.info("## File Name + Path :" + fileUpload);
					
					int step = 1 ; 
					boolean isnext = true;
					
					while(isnext){
						switch (step) {
						case 1 :
							logger.info("Step : "+step+" >>  Create New Upload Path");
							isnext = FileUtils.createDirectoryIfNotExist(uploadPath);
							if(isnext){
								step++; 
								continue;
							}else{
								isnext = false;
							}
						case 2 :
							logger.info("Step : "+step+" >> Save File To Server directory path");
							
							//boolean isFileNameExist = fileLocationService.checkFileNameServerExist(fileName,BuckWaConstants.WORKPERSON_TABLE);
							//if(!isFileNameExist){
								isnext = FileUtils.saveFileToDirectory(originalfile, fileUpload);
								if(isnext){
									step++; 
									continue;
								}else{
									isnext = false;
								}
							//}else{
							//	isnext = false;
							//	mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_NAME_EXIST); 
							//}
						case 3 :
							logger.info(" Step : "+step+" >> Insert into File createPBPAttachFile Database (table : academic_kpi_attach_file) For File Upload History");
							isnext =  fileLocationService.updatePBPPersonPicture(person.getPersonId()+"",fileUpload);
							if(isnext  ){
								step++; 
								continue;
							}else{
								isnext = false;
							} 		
						case 4 :
							person.setPicture(fileUpload);
						default:
							isnext = false;
						}
					}
				}
			}
			else {
				mav.addObject("errorCode", BuckWaConstants.MSGCODE_SELECT_FILE); 
			}			 			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}
		
	 
		
		return mav;
	}	
	
	
	@RequestMapping(value="uploadPersonProfilePicture2")
	public void uploadPersonProfilePicture2(MultipartHttpServletRequest request, HttpServletResponse response) {
																	//MultipartHttpServletRequest request, HttpServletResponse response
		logger.info("---- Wait For Uploading File ----");
		
		
		String PersonId = request.getParameter("PersonId");
		
		logger.info("---- PersonId : ---- :"+PersonId );
		
		logger.info("---- PersonId : ---- :"+request.getContextPath() );
		//ModelAndView mav = new ModelAndView();
		//mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		//mav.setViewName("editPersonProfile");
		try {			 			 
			Iterator<String> itr=request.getFileNames();
			MultipartFile originalfile =request.getFile(itr.next());
			
			//MultipartFile originalfile = person.getFileData();
			
//	          Iterator<String> itr=request.getFileNames();
//
//	          while(itr.hasNext()){
//	           
//	           MultipartFile file=request.getFile(itr.next());
			
			if (originalfile!=null&&originalfile.getSize() > 0) {
				logger.info(" originalfile size:"+originalfile.getSize()+" File Name:"+ originalfile.getOriginalFilename() );
				
					
					//  For Upload File >>>>
					String uploadPath = PAMConstants.rbApp.getString("profile.picture.dir");
					logger.info("## File Size :" + originalfile.getSize());
					logger.info("## File Name Original :" + originalfile.getOriginalFilename());
					logger.info("## Upload Path :" + uploadPath);
					
					String fileUpload = uploadPath + PersonId+".jpg";
					
					logger.info("## File Name + Path :" + fileUpload);
					
					int step = 1 ; 
					boolean isnext = true;
					
					while(isnext){
						switch (step) {
						case 1 :
							logger.info("Step : "+step+" >>  Create New Upload Path");
							isnext = FileUtils.createDirectoryIfNotExist(uploadPath);
							if(isnext){
								step++; 
								continue;
							}else{
								isnext = false;
							}
						case 2 :
							logger.info("Step : "+step+" >> Save File To Server directory path");
							
							//boolean isFileNameExist = fileLocationService.checkFileNameServerExist(fileName,BuckWaConstants.WORKPERSON_TABLE);
							//if(!isFileNameExist){
								isnext = FileUtils.saveFileToDirectory(originalfile, fileUpload);
								if(isnext){
									step++; 
									continue;
								}else{
									isnext = false;
								}
							//}else{
							//	isnext = false;
							//	mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_NAME_EXIST); 
							//}
						case 3 :
							logger.info(" Step : "+step+" >> Insert into File createPBPAttachFile Database (table : academic_kpi_attach_file) For File Upload History");
							isnext =  fileLocationService.updatePBPPersonPicture(PersonId+"",fileUpload);
							if(isnext  ){
								step++; 
								continue;
							}else{
								isnext = false;
							} 		
						case 4 :
							//person.setPicture(fileUpload);
						default:
							isnext = false;
						}
					}
				
			}
			else {
				//mav.addObject("errorCode", BuckWaConstants.MSGCODE_SELECT_FILE); 
			}			 			
		}catch(Exception ex){
			ex.printStackTrace();
			//mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}
		
	 
		
		//return mav;
	}	

	public ModelAndView viewWorkCreateTemp() {
		logger.info(" Start  viewWorkCreateTemp");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("importwork");
		try{
			File file = new File(pathUtil.getPBPAttatchFilePath() + "temp/" + BuckWaUtils.getUserIdFromContext());
			if (file.isDirectory()) {
				List<String> fileNameList = new ArrayList<String>();
				for (File tmpFile : file.listFiles()) {
					fileNameList.add(tmpFile.getName());
				}
				mav.addObject("fileNameList", fileNameList);
			} else {
				mav.addObject("errorCode", "E001");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}

	@RequestMapping(value="downloadFile.htm")
	public void downloadFile(@RequestParam("fileName") String fileName ,HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		logger.info("#####  Start  Download   << "+ fileName +" >> #### ");
		
		try {
			
			httpResponse.addHeader("Content-Disposition", "attachment;filename="+fileName+"");
			httpResponse.setContentType("application/pdf");
			OutputStream outputStream = httpResponse.getOutputStream();

			byte[] downloadFileName = org.apache.commons.io.FileUtils.readFileToByteArray(new File(pathUtil.getPBPAttatchFilePath() + "temp/" + BuckWaUtils.getUserIdFromContext() + "/" + fileName));
			outputStream.write(downloadFileName);
			
			outputStream.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="deleteFile.htm")
	public ModelAndView deleteFile(@RequestParam("fileName") String fileName ,HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		logger.info("#####  Delete   << "+ fileName +" >> #### ");
		ModelAndView mav = new ModelAndView();
		try {
			File file = new File(pathUtil.getPBPAttatchFilePath() + "temp/" + BuckWaUtils.getUserIdFromContext() + "/" + fileName);
			file.delete();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		mav =  viewWorkCreateTemp();
		
		return mav;
	}
	
//	@RequestMapping(value="replyMessageNew.htm", method = RequestMethod.POST)
//	public ModelAndView replyMessageNew(HttpServletRequest httpRequest, @ModelAttribute AcademicKPI academicKPI , BindingResult result) {
//		ModelAndView mav = new ModelAndView();
//		try{			
//			logger.info(" ## replyMessageNew:"+academicKPI.getReplyMessage());
//			 
//			new ReplyPBPMessageNewValidator().validate(academicKPI, result);			
//			if (result.hasErrors()) {				
//				mav.setViewName("importwork");
//			}else {					
//				BuckWaRequest request = new BuckWaRequest();
//				 
//				Message newMessage = new Message();
//				newMessage.setMessageDetail(academicKPI.getReplyMessage());
//				newMessage.setTopicId(academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId());
//				newMessage.setStatus("1");				
//				newMessage.setCreateBy(BuckWaUtils.getFullNameFromContext());
//				request.put("message", newMessage);
//				logger.info(" replyMessage newMessage:"+BeanUtils.getBeanString(newMessage));
//				BuckWaResponse response = webboardTopicService.replyPBPMessage(request);
//				if(response.getStatus()==BuckWaConstants.SUCCESS){		
//					logger.info(" replyMessage newMessage Success");
//					mav.addObject("successCode", response.getSuccessCode()); 
//					academicKPI.setReplyMessage("");
//					mav = viewWork(academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId()+"");	
//				}else {
//					mav.addObject("errorCode", response.getErrorCode()); 
//					 
//				}				
//			}							
//		}catch(Exception ex){
//			ex.printStackTrace();
//			mav.addObject("errorCode", "E001"); 
//		}
//		return mav;
//	}	
	
}
