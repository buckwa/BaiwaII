package com.buckwa.web.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.dao.intf.admin.LovHeaderDao;
import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.LovDetail;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.report.TimeTableReport;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.domain.validator.pbp.TimeTableValidator;
import com.buckwa.service.intf.CommonService;
import com.buckwa.service.intf.admin.AdminUserService;
import com.buckwa.service.intf.admin.GroupService;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.service.intf.pbp.PersonTimeTableService;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.FileUtils;
import com.buckwa.util.PAMConstants;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;

@Controller
@RequestMapping("/admin/timetable") 
@SessionAttributes({"user", "timeTableReport"} ) 
public class TimeTableController {
	
	private static Logger logger = LoggerFactory.getLogger(TimeTableController.class);
	
	@Autowired
	private AdminUserService userService;		
 
	@Autowired
	private GroupService groupService;	

	@Autowired
	private CommonService commonService;
	
	@Autowired
    private PathUtil pathUtil;
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
	private PersonTimeTableService personTimeTableService;
	
	@Autowired
	private LovHeaderDao lovHeaderDao;
	
	@Autowired
	private FacultyService facultyService;	
	
	@RequestMapping("init.htm")
	public ModelAndView init() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav =gotoList(mav);
		
		return mav;
	}
	
	private ModelAndView gotoList(ModelAndView mav){
		mav.setViewName("timeTableList");		
		User user = new User();		
		PagingBean bean = new PagingBean();		
		mav.addObject("pagingBean", bean);	
		mav.addObject("user", user);	
		
		try{
		
			
			// Get Faculty Code
			BuckWaUser buckwaUser =BuckWaUtils.getUserFromContext();
			String facultyCode = buckwaUser.getFacultyCode();
			String facultyCodeSelect ="";
			if(facultyCode==null||facultyCode.trim().length()==0){
				facultyCodeSelect ="01";
			}else{
				facultyCodeSelect = facultyCode;
			}
			
			   System.out.println(" ## /admin/timetable/init.htm facultyCodeSelect  :"+facultyCodeSelect);		
			
			
			
		user.setAcademicYear(academicYearUtil.getAcademicYear());
		user.setAcademicYearList(academicYearUtil.getAcademicYearList());
		user.setFacultyList(academicYearUtil.getFacultyList());
		user.setFacultyCode(facultyCodeSelect);
		
		BuckWaRequest request = new BuckWaRequest();
		BuckWaResponse response =new BuckWaResponse();
		String academicYear =schoolUtil.getCurrentAcademicYear();
		request.put("academicYear",academicYear);
		 
		 response = facultyService.getFacultyListByAcademicYear(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			 List<Faculty> facultyList = ( List<Faculty>)response.getResObj("facultyList");
			 for(Faculty ftmp:facultyList){
				 if(facultyCodeSelect.equalsIgnoreCase(ftmp.getCode())){
					// user.setFacultyName(ftmp.getName()); 
				 }
				 
			 }
			 user.setFacultyList(facultyList);
		}
		// Search initial
		int offset = 0;	
		bean.setOffset(offset);				
		
		request.put("pagingBean", bean);		
		bean.put("user", user);
		//BuckWaResponse response = userService.getUserByOffset(request);
		 response = userService.getUserByFacultyCodeOffset(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
			mav.addObject("pagingBean", beanReturn);				
		}else {				
			mav.addObject("errorCode", response.getErrorCode()); 
		}
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
 
	@RequestMapping(value="search.htm" )
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute User user) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("timeTableList");
		try{			
			PagingBean bean = new PagingBean();
			//int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(0);		
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);		
			bean.put("user", user);
			BuckWaResponse response = userService.getUserByFacultyCodeOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				mav.addObject("totalItems", beanReturn.getTotalItems());	
				mav.addObject("pagingBean", beanReturn);	
				mav.addObject("doSearch","true");
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="searchNextPage.htm" )
	public ModelAndView searchNextPage(HttpServletRequest httpRequest,@ModelAttribute User user) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("timeTableList");
		try{			
			PagingBean bean = new PagingBean();
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);		
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);		
			bean.put("user", user);
			BuckWaResponse response = userService.getUserByFacultyCodeOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				mav.addObject("pagingBean", beanReturn);	
				mav.addObject("doSearch","true");
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}		

	
	@RequestMapping(value="viewTimeTable.htm", method = RequestMethod.GET)
	public ModelAndView viewTimeTable(@RequestParam("username") String username,String semester) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("viewTableList");
		BuckWaRequest request = new BuckWaRequest();
		request.put("username", username);	
		String academicYear =schoolUtil.getCurrentAcademicYear();
		request.put("academicYear",academicYear);
		BuckWaResponse response = userService.getUserByUsernameForEdit(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			User user = (User)response.getResObj("user");	
			user.setGroupList(commonService.getAllGroup()) ;
			
 
			request.put("userName",username);
			request.put("academicYear",academicYear);
			request.put("semester",semester); 
			 response = personTimeTableService.getTimeTable(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				List<TimeTableReport> timeTableList = (List<TimeTableReport>)response.getResObj("timeTableReportList");
				logger.info("############################## Time Table Semester 1 ########################");
				for(TimeTableReport retmp:timeTableList){
					logger.info(" TimeTableReport:"+BeanUtils.getBeanString(retmp));
				}
				
				user.setTimeTableList(timeTableList);
			}
			
			// Call Timetable Semester 2
			
			request.put("semester","2"); 
			 response = personTimeTableService.getTimeTable(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				List<TimeTableReport> timeTableList2 = (List<TimeTableReport>)response.getResObj("timeTableReportList");
				logger.info("############################## Time Table Semester 2 ########################");
				for(TimeTableReport retmp:timeTableList2){
					logger.info(" TimeTableReport 2:"+BeanUtils.getBeanString(retmp));
				}
				
				user.setTimeTableList2(timeTableList2);
			}			
			
			logger.info(" user regId:"+user.getRegId());
			mav.addObject("user", user);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		return mav;
	}	
	
	
	 @RequestMapping(value = "/getTimeTable", method = RequestMethod.GET,headers="Accept=application/json") 
		public List<TimeTableReport> getTimeTable(@RequestParam("academicYearSelect") String academicYearSelect,
				@RequestParam("userName") String userName,
				@RequestParam("semester") String semester) {
		
		List<TimeTableReport> returnList = new ArrayList();
	 
		try {
			
          logger.info(" ###### Start Get TimeTable Parameter ####");
          logger.info(" ###### User Name:"+userName);
          logger.info(" ###### academicYearSelect:"+academicYearSelect);
          logger.info(" ###### Semester:"+semester);
          
			BuckWaRequest request = new BuckWaRequest(); 
			 
		 
			request.put("userName",userName);
			request.put("academicYear",academicYearSelect);
			request.put("semester",semester); 
			BuckWaResponse response = personTimeTableService.getTimeTable(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				returnList = (List<TimeTableReport>)response.getResObj("timeTableReportList");
	 	
			}
			//returnList = getMockReport();
		} catch(Exception ex) {
			ex.printStackTrace();
			 
		}
		
		 
		return returnList;
	}
	 
	 
		@RequestMapping(value="editTimeTable.htm", method = RequestMethod.GET)
		public ModelAndView editTimeTable(@RequestParam("timeTableId") String timeTableId) {	
			logger.info(" Start  ");
			ModelAndView mav = new ModelAndView();
			
			TimeTableReport timeTableReport = new TimeTableReport();
			 
			// Get Time Table by Id
			BuckWaRequest request = new BuckWaRequest();  
			request.put("timetableId",timeTableId);
	  
			BuckWaResponse response = personTimeTableService.getTimeTableById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				timeTableReport =  (TimeTableReport )response.getResObj("timeTableReport");	 	
			}
			
			logger.info(" ###  timeTableReport: "+BeanUtils.getBeanString(timeTableReport));

			List<LovDetail> lecOrPracList = lovHeaderDao.getDetailListByCode(PAMConstants.LEC_OR_PRAC);
			List<LovDetail> degreeLevelList = lovHeaderDao.getDetailListByCode(PAMConstants.DEGREE_LEVEL);
			List<LovDetail> thaiShortDateList = lovHeaderDao.getDetailListByCode(PAMConstants.THAI_SHORT_DATE);
			
			timeTableReport.setLecOrPracList(lecOrPracList);
			timeTableReport.setDegreeLevelList(degreeLevelList);
			timeTableReport.setThaiShortDateList(thaiShortDateList);
			 
			mav.addObject("timeTableReport", timeTableReport);	
			 
			mav.setViewName("editTimeTableList");
		 
			return mav;
		}	

		

		@RequestMapping(value="editTimeTable.htm", method = RequestMethod.POST)
		public ModelAndView editTimeTable(HttpServletRequest httpRequest,@ModelAttribute TimeTableReport timeTableReport, BindingResult result) {		
			logger.info(" Start  ");
			ModelAndView mav = new ModelAndView();
			mav.setViewName("editTimeTableList");
			try{				 
				new TimeTableValidator().validate(timeTableReport, result);			
				if (result.hasErrors()) {
					logger.info("  Validate Error");
					mav.setViewName("editTimeTableList");
				}else {
					logger.info("  Validate Success , Do Edit Time Table ");					
					//BuckWaUser buckwaUser = BuckWaUtils.getUserFromContext();
					BuckWaRequest request = new BuckWaRequest();
					request.put("timeTableReport", timeTableReport);
					BuckWaResponse response = personTimeTableService.updateTimeTable(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){					
						//mav.addObject("group", user);
						mav.addObject("successCode", response.getSuccessCode()); 
						User user =(User)  httpRequest.getSession().getAttribute("user");
						mav = viewTimeTable(user.getUsername(),"1");
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
		
		@RequestMapping(value="createTimeTable.htm", method = RequestMethod.GET)
		public ModelAndView createTimeTable(@RequestParam("username") String username,String semester) {	
			logger.info(" Start  ");
			ModelAndView mav = new ModelAndView();
			String academicYear =schoolUtil.getCurrentAcademicYear();
			TimeTableReport timeTableReport = new TimeTableReport();
			timeTableReport.setSemester(semester);
			timeTableReport.setAcademicYear(academicYear);
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("username", username);				
			request.put("academicYear",academicYear);
			BuckWaResponse response = userService.getUserByUsernameForEdit(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				User user = (User)response.getResObj("user");	
				timeTableReport.setTeacherId(user.getRegId());
				mav.addObject("user", user);	
			}
			
			List<LovDetail> lecOrPracList = lovHeaderDao.getDetailListByCode(PAMConstants.LEC_OR_PRAC);
			List<LovDetail> degreeLevelList = lovHeaderDao.getDetailListByCode(PAMConstants.DEGREE_LEVEL);
			List<LovDetail> thaiShortDateList = lovHeaderDao.getDetailListByCode(PAMConstants.THAI_SHORT_DATE);
			
			timeTableReport.setLecOrPracList(lecOrPracList);
			timeTableReport.setDegreeLevelList(degreeLevelList);
			timeTableReport.setThaiShortDateList(thaiShortDateList);
			 
			mav.addObject("timeTableReport", timeTableReport);	
			
			mav.setViewName("createTimeTable");
		 
			return mav;
		}		
		@RequestMapping(value="createTimeTable.htm", method = RequestMethod.POST)
		public ModelAndView createTimeTablePOST(HttpServletRequest httpRequest,@ModelAttribute TimeTableReport timeTableReport, BindingResult result) {	
			logger.info(" Start  ");
			ModelAndView mav = new ModelAndView();
			try{				 
				new TimeTableValidator().validate(timeTableReport, result);			
				if (result.hasErrors()) {
					logger.info("  Validate Error");
					mav.setViewName("createTimeTable");
				}else {
					logger.info("  Validate Success , Do Edit Time Table ");					
					//BuckWaUser buckwaUser = BuckWaUtils.getUserFromContext();
					BuckWaRequest request = new BuckWaRequest();
					request.put("timeTableReport", timeTableReport);
					BuckWaResponse response = personTimeTableService.createTimeTable(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){					
						//mav.addObject("group", user);
						mav.addObject("successCode", response.getSuccessCode()); 
						User user =(User)  httpRequest.getSession().getAttribute("user");
						mav = viewTimeTable(user.getUsername(),"1");
					}else {					 
						mav.addObject("errorCode", response.getErrorCode()); 
						mav.setViewName("createTimeTable");
					}
				} 
				
			}catch(Exception ex){
				ex.printStackTrace();
				mav.addObject("errorCode", "E001"); 
			}
			return mav;
		}	
		
	@RequestMapping(value="edit.htm", method = RequestMethod.GET)
	public ModelAndView initEdit(@RequestParam("username") String username) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("userEdit");
		BuckWaRequest request = new BuckWaRequest();
		request.put("username", username);	
		String academicYear =schoolUtil.getCurrentAcademicYear();
		request.put("academicYear",academicYear);
		BuckWaResponse response = userService.getUserByUsernameForEdit(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			User user = (User)response.getResObj("user");	
			user.setGroupList(commonService.getAllGroup()) ;
			
			// Set Date format
			// Set Date format
			user.getPerson().setBirthdateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(user.getPerson().getBirthdate()));
			user.getPerson().setWorkingDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(user.getPerson().getWorkingDate()));
			user.getPerson().setAssignDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(user.getPerson().getAssignDate()));
			user.getPerson().setRetireDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(user.getPerson().getRetireDate()));
			
			mav.addObject("user", user);				
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		return mav;
	}	
	
	

	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView submitEdit(@ModelAttribute User user, BindingResult result) {		
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		try{
			 
			new UserValidator().validate(user, result);			
			if (result.hasErrors()) {
				logger.info("  Validate Error");
				mav.setViewName("userEdit");
			}
			else {
				logger.info("  Validate Success , Do create User ");
				
				BuckWaUser buckwaUser = BuckWaUtils.getUserFromContext();
				
				user.getPerson().setBirthdate(BuckWaDateUtils.parseDate(user.getPerson().getBirthdateStr()));
				user.getPerson().setWorkingDate(BuckWaDateUtils.parseDate(user.getPerson().getWorkingDateStr()));
				user.getPerson().setAssignDate(BuckWaDateUtils.parseDate(user.getPerson().getAssignDateStr()));
				user.getPerson().setRetireDate(BuckWaDateUtils.parseDate(user.getPerson().getRetireDateStr()));
				user.getPerson().setUpdateBy(buckwaUser.getUsername());
				
				BuckWaRequest request = new BuckWaRequest();
				request.put("user", user);
				BuckWaResponse response = userService.updateUser(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){					
					mav.addObject("group", user);
					mav.addObject("successCode", response.getSuccessCode()); 
					mav = gotoList(mav);
				}else {					 
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("userEdit");
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	

	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("username") String username,HttpServletRequest httpRequest ,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{
			mav.setViewName("userList");
			BuckWaRequest request = new BuckWaRequest();
			request.put("username", username);	
			BuckWaResponse response = userService.delete(request);
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				mav.addObject("successCode","S004"); 		 				
			}else {	
				mav.addObject("errorCode", response.getErrorCode()); 
				mav.addObject("pagingBean", bean);	
			}	
			
			// Search Again
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);		 
			request.put("pagingBean", bean);		
			bean.put("user", new User());
			 response = userService.getUserByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				mav.addObject("pagingBean", beanReturn);				
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
	
		return mav;
	}	
	
	
	@RequestMapping(value="changeStatus.htm", method = RequestMethod.GET)
	public ModelAndView changeStatus(@RequestParam("username") String username,String currentStatus ) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("userList");	
		
		BuckWaRequest request = new BuckWaRequest();
		
	 
		request.put("username", username);	
		request.put("currentStatus", currentStatus);	
		
		// Do somthing
		BuckWaResponse response = userService.changeStatus(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			PagingBean bean = new PagingBean();
			int offset = 0;
			bean.setOffset(offset);		
			
			request = new BuckWaRequest();
			request.put("pagingBean", bean);		
			bean.put("user", new User());
			 response = userService.getUserByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				mav.addObject("pagingBean", beanReturn);	
				mav.addObject("doSearch","true");
				mav.addObject("successCode", "S005"); 
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			} 
		}else{
			mav.addObject("errorCode", "E001"); 
		} 
		return mav;
	}
	
	
	
	@RequestMapping(value="resetPass.htm", method = RequestMethod.GET)
	public ModelAndView resetPass(@RequestParam("username") String username ,String customerNo) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("userList");	
		
		BuckWaRequest request = new BuckWaRequest();
		
		String newPass = BuckWaUtils.generatePASS(6);
		 
		request.put("username", username);	
		request.put("newPass", newPass);	
		
		// Do somthing
		BuckWaResponse response = userService.resetPass(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			
			PagingBean bean = new PagingBean();
			int offset = 0;
			bean.setOffset(offset);		
			
			request = new BuckWaRequest();
			request.put("pagingBean", bean);		
			bean.put("user", new User());
			 response = userService.getUserByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				mav.addObject("pagingBean", beanReturn);	
				mav.addObject("doSearch","true");
				mav.addObject("successCode", "S006"); 
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			} 
				 mav.addObject("userResetpass",  username);
				 mav.addObject("newPass",  newPass);
				  
			
		}else{
			mav.addObject("errorCode", "E001"); 
		}
		 
		return mav;
	} 
	
	
	@RequestMapping(value="uploadFile.htm")
	public ModelAndView uploadFile(@ModelAttribute User user, BindingResult result, HttpServletRequest httpRequest) {
	 
		logger.info("---- Wait For Uploading File ----");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		
		try {			 			 

			MultipartFile originalfile = user.getPerson().getFileData();
			
			if (originalfile!=null&&originalfile.getSize() > 0) {
				logger.info(" originalfile size:"+originalfile.getSize()+" File Name:"+ originalfile.getOriginalFilename() );
				if (originalfile.getSize() > pathUtil.getMaximumImageUploadSize()) {
					logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumImageUploadSize());					 
					mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_TOO_LARGE);
				}
				else {		
					
					//  For Upload File >>>>
					String uploadPath = PAMConstants.rbApp.getString("profile.picture.dir");
					logger.info("## File Size :" + originalfile.getSize());
					logger.info("## File Name Original :" + originalfile.getOriginalFilename());
					logger.info("## Upload Path :" + uploadPath);
					
					String fileUpload = uploadPath + originalfile.getOriginalFilename();
					
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
							user.getPerson().setPicture(originalfile.getOriginalFilename());
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
		
		if (null == user.getPerson().getPersonId()) {
			mav.setViewName("userCreate");
		}
		else {
			mav.setViewName("userEdit");
		}
		
		return mav;
	}
}
