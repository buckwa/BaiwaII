package com.buckwa.web.controller.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pbp.AcademicKPI;
import com.buckwa.domain.pbp.AcademicKPIAttachFile;
import com.buckwa.domain.pbp.AcademicPerson;
import com.buckwa.domain.pbp.ChainOfCommandWrapper;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.FacultyWrapper;
import com.buckwa.domain.pbp3.ResponseObj;
import com.buckwa.domain.pbp3.ResponseObjPaging;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.domain.validator.pbp.DepartmentValidator;
import com.buckwa.domain.validator.pbp.FacultyValidator;
import com.buckwa.service.intf.CommonService;
import com.buckwa.service.intf.admin.AdminUserService;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.FileUtils;
import com.buckwa.util.PAMConstants;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;

import baiwa.util.UserLoginUtil;


@RestController
@RequestMapping("/admin/json")
public class JSONAdminController {

	
	private static Logger logger = LoggerFactory.getLogger(JSONAdminController.class);
	
	@Autowired
	private FacultyService facultyService;	
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
	private AdminUserService userService;	
	
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
    private PathUtil pathUtil;


	@RequestMapping(value = "/getFacultyWrapper/{year}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<FacultyWrapper> initList(@PathVariable String year) {

		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandList");
		FacultyWrapper facultyWrapper = null;
		BuckWaResponse response = null;
		List<FacultyWrapper> returnList = new ArrayList<FacultyWrapper>();
		
		try{
			BuckWaRequest request = new BuckWaRequest();
			String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",year);
			response = facultyService.getByAcademicYear(request);
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				
				
				
				facultyWrapper = (FacultyWrapper)response.getResObj("facultyWrapper");	
				
				facultyWrapper.setAcademicYear(academicYear);
				facultyWrapper.setAcademicYearSelect(academicYear);
				mav.addObject("facultyWrapper", facultyWrapper);	
				facultyWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
				returnList.add(facultyWrapper);
				

			}	
			
	 		  
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return returnList;
	}
	

	
	@RequestMapping(value="/getlistByDepartment/{departmentId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<ChainOfCommandWrapper> listByDepartment(@PathVariable String departmentId ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandDepartmentList");
		List<ChainOfCommandWrapper> returnList = new ArrayList<ChainOfCommandWrapper>();
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			 
			request.put("departmentId",departmentId);
			request.put("academicYear",schoolUtil.getCurrentAcademicYear());
			BuckWaResponse response = facultyService.getByDepartment(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				ChainOfCommandWrapper chainOfCommandWrapper = (ChainOfCommandWrapper)response.getResObj("chainOfCommandWrapper");	 
				mav.addObject("chainOfCommandWrapper", chainOfCommandWrapper);	
				returnList.add(chainOfCommandWrapper);
			}	 

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return returnList;
	}	
	//assignToPresident/kppaiboo@kmitl.ac.th/kawinai@kmitl.ac.th/
	@RequestMapping(value="/managePresident", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseObjPaging managePresident( ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandManagePresident");
		ResponseObjPaging resp = new ResponseObjPaging();
		List<ChainOfCommandWrapper> returnList = new ArrayList<ChainOfCommandWrapper>();
		try{ 
			BuckWaRequest request = new BuckWaRequest();
			User user = new User();		
			
			ChainOfCommandWrapper chainOfCommandWrapper =new ChainOfCommandWrapper();
			chainOfCommandWrapper.setAcademicYear(schoolUtil.getCurrentAcademicYear());
			chainOfCommandWrapper.setUser(user);
						
			// Get Presidentdd
			
			request.put("academicYear",schoolUtil.getCurrentAcademicYear());
			BuckWaResponse response = facultyService.getPresident(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicPerson academicPerson = (AcademicPerson)response.getResObj("academicPerson");	
				if(academicPerson!=null){
					chainOfCommandWrapper.setPresident(academicPerson);
					chainOfCommandWrapper.setOldPresidentUserName(academicPerson.getEmail());
				}
			}	
			
			PagingBean bean = new PagingBean();		
			mav.addObject("pagingBean", bean);	
			mav.addObject("user", user); 
			
			// Search initial
			int offset = 0;	
			bean.setOffset(offset);				
			
			request.put("pagingBean", bean);		
			bean.put("user", user);
			 
			user.setAcademicYear(schoolUtil.getCurrentAcademicYear());
		 
			 
			response = userService.getUserByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				mav.addObject("pagingBean", beanReturn);		
				resp.setResPagingBean(beanReturn);
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}		
			
			resp.setResObj(chainOfCommandWrapper);
			mav.addObject("chainOfCommandWrapper", chainOfCommandWrapper);	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resp;
	}
	
	
	
	
	
	@RequestMapping(value="/manageHead/{departmentId}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseObjPaging manageHead(@PathVariable("departmentId") String departmentId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandManageHead");
		List<ChainOfCommandWrapper> returnList = new ArrayList<ChainOfCommandWrapper>();
		ResponseObjPaging resp = new ResponseObjPaging();
		try{ 
			BuckWaRequest request = new BuckWaRequest();
			User user = new User();		
			
			ChainOfCommandWrapper chainOfCommandWrapper =new ChainOfCommandWrapper();
			chainOfCommandWrapper.setAcademicYear(schoolUtil.getCurrentAcademicYear());
			chainOfCommandWrapper.setUser(user);
						
			chainOfCommandWrapper.setDepartmentId(new Long(departmentId));
			// Get Head
			request.put("departmentId",departmentId);
			String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
			BuckWaResponse response = facultyService.getHeadByDepartmentId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicPerson academicPerson = (AcademicPerson)response.getResObj("academicPerson");	
				if(academicPerson!=null){
					chainOfCommandWrapper.setHead(academicPerson);
					chainOfCommandWrapper.setOldHeadUserName(academicPerson.getEmail());
				}

			}	
			
			 response = facultyService.getDepartmentById(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					Department department = (Department)response.getResObj("department");	 
					chainOfCommandWrapper.setDepartment(department);
					chainOfCommandWrapper.setDepartmentId(department.getDepartmentId());
				}	
			
			
			PagingBean bean = new PagingBean();		
			mav.addObject("pagingBean", bean);	
			mav.addObject("user", user); 
			
			// Search initial
			int offset = 0;	
			bean.setOffset(offset);				
			
			request.put("pagingBean", bean);		
			bean.put("user", user);
			bean.put("departmentId", departmentId);
			user.setAcademicYear(schoolUtil.getCurrentAcademicYear());
			bean.put("user", user);
			 
			response = userService.getUserDepartmentByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				mav.addObject("pagingBean", beanReturn);
				resp.setResPagingBean(beanReturn);
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}		
			resp.setResObj(chainOfCommandWrapper);
			mav.addObject("chainOfCommandWrapper", chainOfCommandWrapper);	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resp;
	}
	

	@RequestMapping(value="/manageDean/{facultyId}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseObjPaging manageDean(@PathVariable("facultyId") String facultyId ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandManageDean");
		ResponseObjPaging resp = new ResponseObjPaging();
		try{ 
			BuckWaRequest request = new BuckWaRequest();
			User user = new User();		
			
			ChainOfCommandWrapper chainOfCommandWrapper =new ChainOfCommandWrapper();
			chainOfCommandWrapper.setAcademicYear(schoolUtil.getCurrentAcademicYear());
			chainOfCommandWrapper.setUser(user);
						
			// Get Dean
			request.put("facultyId",facultyId);
			String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
			BuckWaResponse response = facultyService.getDeanByFacultyId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicPerson academicPerson = (AcademicPerson)response.getResObj("academicPerson");	 
				if(academicPerson!=null){
				chainOfCommandWrapper.setDean(academicPerson);
				chainOfCommandWrapper.setOldDeanUserName(academicPerson.getEmail());
				}
			}	
			
			 response = facultyService.getById(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					Faculty faculty = (Faculty)response.getResObj("faculty");	 
					chainOfCommandWrapper.setFaculty(faculty);
					chainOfCommandWrapper.setFacultyId(faculty.getFacultyId());
				}	
			
			
			PagingBean bean = new PagingBean();		
			mav.addObject("pagingBean", bean);	
			mav.addObject("user", user); 
			
			// Search initial
			int offset = 0;	
			bean.setOffset(offset);				
			
			request.put("pagingBean", bean);
			user.setAcademicYear(schoolUtil.getCurrentAcademicYear());
			bean.put("user", user);
			response = userService.getUserByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				resp.setResPagingBean(beanReturn);
				mav.addObject("pagingBean", beanReturn);				
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}		
			resp.setResObj(chainOfCommandWrapper);
			mav.addObject("chainOfCommandWrapper", chainOfCommandWrapper);	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resp;
	}
	
	
	@RequestMapping(value="/GetUserlist", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseObjPaging init() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
//		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
//		mav =gotoList(mav);
		ResponseObjPaging returnList = gotoList(mav);
		return returnList;
	}
	
	private ResponseObjPaging gotoList(ModelAndView mav){
		//mav.setViewName("userList");		
		User user = new User();		
		PagingBean bean = new PagingBean();		
		//mav.addObjectPagingBean("pagingBean", bean);	
		mav.addObject("user", user);	
		ResponseObjPaging returnList = new ResponseObjPaging();
		PagingBean beanReturn = null ;
		try{
		
		user.setAcademicYear(academicYearUtil.getAcademicYear());
		user.setAcademicYearList(academicYearUtil.getAcademicYearList());
		user.setFacultyList(academicYearUtil.getFacultyList());
		// Search initial
		int offset = 0;	
		bean.setOffset(offset);				
		BuckWaRequest request = new BuckWaRequest();
		request.put("pagingBean", bean);		
		bean.put("user", user);
		returnList.setResObj(user);
		BuckWaResponse response = userService.getUserByOffset(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			beanReturn = (PagingBean)response.getResObj("pagingBean");
			mav.addObject("pagingBean", beanReturn);	
			returnList.setResPagingBean(beanReturn);
		}else {				
			mav.addObject("errorCode", response.getErrorCode()); 
		}
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		//returnList.add(beanReturn);
		

		return returnList;
	}
	
	//เพิ่ม ข้อมูลบุคลากร
	
	@RequestMapping(value="searchUser", method = RequestMethod.POST )
	public ResponseObjPaging search(@RequestBody User user) {
		logger.info(" Start  ");
		ResponseObjPaging returnList = new ResponseObjPaging();		

		try{			
			PagingBean bean = new PagingBean();
			
			bean.setOffset(0);		
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);		
			bean.put("user", user);
			returnList.setResObj(user);
			BuckWaResponse response = userService.getUserByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){			
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				

				returnList.setResPagingBean(beanReturn);
				
			}else {				
				
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		return returnList;
	}
	
	
	
	
	
	

	//เพิ่ม ข้อมูลบุคลากร List 	
	@RequestMapping(value="/createuser", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<User> initCreate() {
		
		List<User> returnList = new ArrayList<User>();
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("userCreate");
		User user = new User();				
		// 1. Get all Group		 		 	
		user.setGroupList(commonService.getAllGroup()) ;
		
		BuckWaResponse response = userService.initCreateUser();
		if(response.getStatus()==BuckWaConstants.SUCCESS){
			Person person = (Person) response.getResObj("person");
			user.setPerson(person);
			
		}
		
		logger.info(" user:"+BeanUtils.getBeanString(user));
		
		mav.addObject("user", user);
		returnList.add(user);
		
		return returnList;
	}
		
	//นำไป Save    ยังค้าง   Validate 
	@RequestMapping(value="/createuserSave", method = RequestMethod.POST)
	public ResponseObj submitCreate(@RequestBody User user, BindingResult result) {		
		logger.info(" Start ");
		ModelAndView mav = new ModelAndView();
		ResponseObj resp = new ResponseObj();

		try{
			logger.info(" User:"+BeanUtils.getBeanString(user));
			new UserValidator().validate(user, result);			
			if (result.hasErrors()) {
				logger.info("  Validate Error");
			}else {	
				logger.info("  Validate Success , Do create User ");
				
				BuckWaUser buckwaUser = BuckWaUtils.getUserFromContext();
				String academicYear =schoolUtil.getCurrentAcademicYear();
				user.getPerson().setBirthdate(BuckWaDateUtils.parseDate(user.getPerson().getBirthdateStr()));
				user.getPerson().setWorkingDate(BuckWaDateUtils.parseDate(user.getPerson().getWorkingDateStr()));
				user.getPerson().setAssignDate(BuckWaDateUtils.parseDate(user.getPerson().getAssignDateStr()));
				user.getPerson().setRetireDate(BuckWaDateUtils.parseDate(user.getPerson().getRetireDateStr()));
				user.getPerson().setEmail(user.getUsername());
				user.getPerson().setAcademicYear(academicYear);
				user.getPerson().setCreateBy(UserLoginUtil.getCurrentUserLogin());
				user.getPerson().setUpdateBy(UserLoginUtil.getCurrentUserLogin());
				
				BuckWaRequest request = new BuckWaRequest();
				request.put("user", user);				 
				BuckWaResponse response = userService.createUser(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					logger.info("Success");					
					mav.addObject("user", user);
					mav.addObject("successCode", response.getSuccessCode()); 
					resp.setResObj(user);

				}else {
					resp.setStatus("1");
					resp.setResObj(response);
					resp.setDescription(response.getErrorCode());
				}								
			}								
		}catch(Exception ex){
			resp.setStatus("1");
			resp.setDescription(ex.getMessage());
		}
		return resp;
	}		
	

	

	@RequestMapping(value="/editUser/{username}/{work}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseObj initEdit(@PathVariable String username,@PathVariable String work) {	
		logger.info(" Start  ");
		//username ="kppaiboo@kmitl.ac.th"; //อย่าลืม
		ModelAndView mav = new ModelAndView();
		ResponseObj resp = new ResponseObj();
//		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
//		mav.setViewName("userEdit");
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
			
			//mav.addObject("user", user);	
			resp.setResObj(user);
		}else {
			logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
			mav.addObject("errorCode", response.getErrorCode()); 
		}	
		return resp;
	}	
	
	

	@RequestMapping(value="/editUserSave", method = RequestMethod.POST)
	public ResponseObj submitEdit(@RequestBody  User user, BindingResult result) {		
		logger.info(" Start  ");
		ResponseObj resp = new ResponseObj();
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
				user.getPerson().setUpdateBy(UserLoginUtil.getCurrentUserLogin());
				
				BuckWaRequest request = new BuckWaRequest();
				request.put("user", user);
				BuckWaResponse response = userService.updateUser(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){					
					mav.addObject("group", user);
					mav.addObject("successCode", response.getSuccessCode()); 
					
					resp.setStatus("1");
					resp.setDescription(response.getErrorCode());
					
					
					//mav = gotoList(mav);
				}else {					 
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("userEdit");
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resp;
	}	

	
	@RequestMapping(value = "/getFaculty/{year}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<FacultyWrapper> faculty(@PathVariable String year) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("facultyList");
		List<FacultyWrapper> returnList = new ArrayList<FacultyWrapper>();
		try {

			BuckWaRequest request = new BuckWaRequest();
			String academicYear = schoolUtil.getCurrentAcademicYear();
			request.put("academicYear", year);
			BuckWaResponse response = facultyService.getByAcademicYear(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				FacultyWrapper facultyWrapper = (FacultyWrapper) response.getResObj("facultyWrapper");

				facultyWrapper.setAcademicYear(academicYear);
				facultyWrapper.setAcademicYearSelect(academicYear);
				mav.addObject("facultyWrapper", facultyWrapper);
				returnList.add(facultyWrapper);
				facultyWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return returnList;
	}

	@RequestMapping(value="/editFaculty/{facultyId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Faculty> facultyedit(@PathVariable String facultyId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("facultyEdit");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		List<Faculty> returnList = new ArrayList<Faculty>();
		BuckWaRequest request = new BuckWaRequest();
		request.put("facultyId", facultyId);
		BuckWaResponse response = facultyService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Faculty faculty = (Faculty)response.getResObj("faculty"); 
			mav.addObject("faculty", faculty);
			returnList.add(faculty);
			 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}	
 
		return returnList;
	}	
	

	
	@RequestMapping(value="/editFacultySave", method = RequestMethod.POST)
	public ResponseObj editFacultyPOST(@RequestBody Faculty faculty, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("facultyEdit");
		ResponseObj resObj =new ResponseObj();
		try{			
			new FacultyValidator().validate(faculty, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("faculty", faculty);
				BuckWaResponse response = facultyService.updateFaculty(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){							 
					mav.addObject("successCode", response.getSuccessCode()); 
					resObj.setResObj(response);
					resObj.setStatus("0");
					//mav =initList(); 				
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					 
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}
	
	@RequestMapping(value="/createFaculty/{academicYearSelect}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseObj init(@PathVariable("academicYearSelect") String academicYearSelect) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		ResponseObj resObj =new ResponseObj();
		
		mav.setViewName("facultyCreate");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		
	 
		Faculty faculty = new Faculty();
		faculty.setAcademicYear(academicYearSelect);
		resObj.setResObj(faculty);
		mav.addObject("faculty", faculty);	
		return resObj;
	}		

	@RequestMapping(value="/createFaculty", method = RequestMethod.POST )
	public ResponseObj createRole(@RequestBody Faculty faculty, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("facultyCreate");
		ResponseObj resObj =new ResponseObj();
		try{			
			new FacultyValidator().validate(faculty, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("faculty", faculty);
				BuckWaResponse response = facultyService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){							 
					mav.addObject("successCode", response.getSuccessCode()); 
					resObj.setResObj(response);
					resObj.setStatus("0");
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					 
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}
	
	
	@RequestMapping(value="/createDepartmentGet/{facultyId}", method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseObj createDepartment(@PathVariable("facultyId") String facultyId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		ResponseObj resObj =new ResponseObj();
		mav.setViewName("departmentCreate");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("facultyId", facultyId);
		BuckWaResponse response = facultyService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Faculty faculty = (Faculty)response.getResObj("faculty");
			faculty.setDepartment(new Department()) ;
			mav.addObject("faculty", faculty); 
			resObj.setStatus("1");	
			resObj.setResObj(faculty); 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}	
 
		return resObj;
	}	
	
		
	@RequestMapping(value="/createDepartmentPost", method = RequestMethod.POST)
	public ResponseObj createDepartment(HttpServletRequest httpRequest,@RequestBody Faculty faculty, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("facultyList");
		ResponseObj resObj =new ResponseObj();
		try{			
			new DepartmentValidator().validate(faculty.getDepartment(), result);			
			if (result.hasErrors()) {				
				mav.setViewName("departmentCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("faculty", faculty);
				BuckWaResponse response = facultyService.createDepartment(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){							 
					mav.addObject("successCode", response.getSuccessCode()); 
					String url = httpRequest.getContextPath() + "/admin/pbp/faculty/init.htm";
					logger.info(" Redirect URL:"+url);
					mav.addObject("faculty", faculty); 
					resObj.setResObj(response); 	
					resObj.setStatus("1");	
					mav.setView(new RedirectView(url)); 				
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					 
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}

	@RequestMapping(value="/editDepartmentJson/{departmentId}", method = RequestMethod.GET)
	public ResponseObj editDepartment(@PathVariable("departmentId") String departmentId) {
		logger.info(" Start  ");
		ResponseObj resObj =new ResponseObj();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("departmentEdit");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("departmentId", departmentId);
		BuckWaResponse response = facultyService.getDepartmentById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Department department = (Department)response.getResObj("department"); 
			mav.addObject("department", department);  
			resObj.setResObj(department);
		}else {
			mav.addObject("errorCode", response.getErrorCode());  
		}	 
		return resObj;
	}	
	
	
	@RequestMapping(value="/editDepartmentJsonSave", method = RequestMethod.POST)
	public ResponseObj editDepartmentPOST(@RequestBody Department department, BindingResult result) {	
		logger.info(" Start  ");
		ResponseObj resObj =new ResponseObj();
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		//mav.setViewName("departmentEdit");
		try{			
			new DepartmentValidator().validate(department, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("department", department);
				BuckWaResponse response = facultyService.updateDepartment(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){							 
					mav.addObject("successCode", response.getSuccessCode()); 
					resObj.setStatus("0");
					//mav =initList(); 				
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					 
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}
	
	
//	@PathVariable("departmentId") String departmentId
	@RequestMapping(value="/assignToHead/{userName}/{WORK}", method = RequestMethod.POST )
	public ResponseObj assignToHead(@RequestBody ChainOfCommandWrapper chainOfCommandWrapper,@PathVariable("userName") String userName,@PathVariable("WORK") String WORK  ) {

		ResponseObj resObj =new ResponseObj();
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("chainOfCommandManageHead");
		try{			
			
			//ChainOfCommandWrapper chainOfCommandWrapper =(ChainOfCommandWrapper)  httpRequest.getSession().getAttribute("chainOfCommandWrapper");
			//AcademicPerson oldDean = chainOfCommandWrapper.getDean();
			//Faculty faculty = chainOfCommandWrapper.getFaculty();
			
			BuckWaRequest request = new BuckWaRequest();
			// Assign To Dean

			logger.info(" Start userName: "+userName+" to  Department:"+chainOfCommandWrapper.getDepartment().getName());
			logger.info(" oldHead:"+chainOfCommandWrapper.getOldHeadUserName()+"  newHead:"+userName);
			request.put("oldHead",chainOfCommandWrapper.getOldHeadUserName());
			request.put("newHead",userName);
			request.put("academicYear",schoolUtil.getCurrentAcademicYear());
			request.put("departmentDesc",chainOfCommandWrapper.getDepartment().getName());
			BuckWaResponse response = facultyService.assignHead(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				
				request.put("departmentId",chainOfCommandWrapper.getDepartmentId()+"");
				 response = facultyService.getHeadByDepartmentId(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					AcademicPerson academicPerson = (AcademicPerson)response.getResObj("academicPerson");
					if(academicPerson!=null){
						resObj.setResObj(response);
//					chainOfCommandWrapper.setHead(academicPerson);
//					chainOfCommandWrapper.setOldHeadUserName(academicPerson.getEmail());
					}
				}	
				response.setSuccessCode("S002");
			} 
 					
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}	
	
	

	@RequestMapping(value="assignToDean/{userName}/{WORK}", method = RequestMethod.POST )
	public ResponseObj assignToDean(@RequestBody ChainOfCommandWrapper chainOfCommandWrapper,@PathVariable("userName") String userName,@PathVariable("WORK") String WORK ) {
		logger.info(" Start  userName:"+userName);
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		ResponseObj resObj =new ResponseObj();
		mav.setViewName("chainOfCommandManageDean");
		try{			
			
			//ChainOfCommandWrapper chainOfCommandWrapper =(ChainOfCommandWrapper)  httpRequest.getSession().getAttribute("chainOfCommandWrapper");
			//AcademicPerson oldDean = chainOfCommandWrapper.getDean();
			//Faculty faculty = chainOfCommandWrapper.getFaculty();
			
			BuckWaRequest request = new BuckWaRequest();
			// Assign To Dean
			request.put("oldDean",chainOfCommandWrapper.getOldDeanUserName());
			request.put("newDean",userName);
			request.put("academicYear",schoolUtil.getCurrentAcademicYear());
			request.put("facultyDesc",chainOfCommandWrapper.getFaculty().getName());
			BuckWaResponse response = facultyService.assignDean(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				
				request.put("facultyId",chainOfCommandWrapper.getFacultyId()+"");
				 response = facultyService.getDeanByFacultyId(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					AcademicPerson academicPerson = (AcademicPerson)response.getResObj("academicPerson");	
					if(academicPerson!=null){
					chainOfCommandWrapper.setDean(academicPerson);
					chainOfCommandWrapper.setOldDeanUserName(academicPerson.getEmail());
					}
				}	
				response.setSuccessCode("S002");
			} 
 					
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}	
 
	@RequestMapping(value="/assignToPresident/{userName}/{WORK}" , method = RequestMethod.POST )
	public ResponseObj assignToPresident(@RequestBody ChainOfCommandWrapper chainOfCommandWrapper,@PathVariable("userName") String userName,@PathVariable("WORK") String WORK ) {
		ResponseObj resObj =new ResponseObj();
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("chainOfCommandManagePresident");
		try{			
			
			//ChainOfCommandWrapper chainOfCommandWrapper =(ChainOfCommandWrapper)  httpRequest.getSession().getAttribute("chainOfCommandWrapper");
			//AcademicPerson oldDean = chainOfCommandWrapper.getDean();
			//Faculty faculty = chainOfCommandWrapper.getFaculty();
			
			BuckWaRequest request = new BuckWaRequest();
			// Assign To Dean
			request.put("oldPresident",chainOfCommandWrapper.getOldPresidentUserName());
			request.put("newPresident",userName);
			request.put("academicYear",schoolUtil.getCurrentAcademicYear());
			BuckWaResponse response = facultyService.assignPresident(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				
				request.put("departmentId",chainOfCommandWrapper.getDepartmentId()+"");
				 response = facultyService.getPresident(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					AcademicPerson academicPerson = (AcademicPerson)response.getResObj("academicPerson");
					if(academicPerson!=null){
					chainOfCommandWrapper.setPresident(academicPerson);
					chainOfCommandWrapper.setOldPresidentUserName(academicPerson.getEmail());
					resObj.setResObj(response);
					}
				}	
		 
			} 
 					
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}	
	
	
	@RequestMapping(value="deleteUser/{username}/{work}", method = RequestMethod.POST)
	public ResponseObjPaging delete(@PathVariable("username") String username,@PathVariable("work") String work,HttpServletRequest httpRequest ,@RequestBody PagingBean bean) {
		logger.info(" Start  ");
		ResponseObjPaging resObj =new ResponseObjPaging();
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
				resObj.setResPagingBean(beanReturn);
				resObj.setResObj(response);
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
	
		return resObj;
	}	
	
	
//	@RequestMapping(value="uploadFile", method = RequestMethod.POST)
//	
//	public ResponseObjPaging uploadFile(MultipartHttpServletRequest request, BindingResult result, HttpServletRequest httpRequest) {
//	//public ResponseObjPaging uploadFile(@RequestBody User user, BindingResult result, HttpServletRequest httpRequest) {
//			 
//		logger.info("---- Wait For Uploading File ----");
//		ResponseObjPaging resObj =new ResponseObjPaging();
//		ModelAndView mav = new ModelAndView();
//		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
//		
//		try {			 			 
//			Iterator<String> itr = request.getFileNames();
//			MultipartFile originalfile = request.getFile(itr.next());
//			//MultipartFile originalfile = user.getPerson().getFileData();
//			
//			if (originalfile!=null&&originalfile.getSize() > 0) {
//				logger.info(" originalfile size:"+originalfile.getSize()+" File Name:"+ originalfile.getOriginalFilename() );
//				if (originalfile.getSize() > pathUtil.getMaximumImageUploadSize()) {
//					logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumImageUploadSize());					 
//					mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_TOO_LARGE);
//				}
//				else {		
//					
//					//  For Upload File >>>>
//					String uploadPath = PAMConstants.rbApp.getString("profile.picture.dir");
//					logger.info("## File Size :" + originalfile.getSize());
//					logger.info("## File Name Original :" + originalfile.getOriginalFilename());
//					logger.info("## Upload Path :" + uploadPath);
//					
//					String fileUpload = uploadPath + originalfile.getOriginalFilename();
//					
//					logger.info("## File Name + Path :" + fileUpload);
//					
//					int step = 1 ; 
//					boolean isnext = true;
//					
//					while(isnext){
//						switch (step) {
//						case 1 :
//							logger.info("Step : "+step+" >>  Create New Upload Path");
//							isnext = FileUtils.createDirectoryIfNotExist(uploadPath);
//							if(isnext){
//								step++; 
//								continue;
//							}else{
//								isnext = false;
//							}
//						case 2 :
//							logger.info("Step : "+step+" >> Save File To Server directory path");
//							
//							//boolean isFileNameExist = fileLocationService.checkFileNameServerExist(fileName,BuckWaConstants.WORKPERSON_TABLE);
//							//if(!isFileNameExist){
//								isnext = FileUtils.saveFileToDirectory(originalfile, fileUpload);
//								if(isnext){
//									step++; 
//									continue;
//								}else{
//									isnext = false;
//								}
//							//}else{
//							//	isnext = false;
//							//	mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_NAME_EXIST); 
//							//}
//						case 3 :
//							//user.getPerson().setPicture(originalfile.getOriginalFilename());
//							resObj.setResObj(originalfile.getOriginalFilename());
//						default:
//							isnext = false;
//						}
//					}
//				}
//			}
//			else {
//				mav.addObject("errorCode", BuckWaConstants.MSGCODE_SELECT_FILE); 
//			}			 			
//		}catch(Exception ex){
//			ex.printStackTrace();
//			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
//		}
//		
////		if (null == user.getPerson().getPersonId()) {
////			mav.setViewName("userCreate");
////		}
////		else {
////			mav.setViewName("userEdit");
////		}
//		
//		return resObj;
//	}
//	
	
	
	@RequestMapping(value = "/UploadFile_Profile", method = RequestMethod.POST)
	public ResponseObjPaging jsonImportwork_filePOST(MultipartHttpServletRequest request, HttpServletResponse response) {
		
		logger.info("---- Wait For Uploading File ----");
		ResponseObjPaging resObj =new ResponseObjPaging();
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		
		try {			 			 
			Iterator<String> itr = request.getFileNames();
			MultipartFile originalfile = request.getFile(itr.next());
			//MultipartFile originalfile = user.getPerson().getFileData();
			
			if (originalfile!=null&&originalfile.getSize() > 0) {
				logger.info(" originalfile size:"+originalfile.getSize()+" File Name:"+ originalfile.getOriginalFilename() );
					
					
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
							logger.info("Step : "+step+" >>Set");
							//user.getPerson().setPicture(originalfile.getOriginalFilename());
							resObj.setResObj(originalfile.getOriginalFilename());
						default:
							isnext = false;
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
		

		
		return resObj;
	}
	
	
	@RequestMapping(value="/TestOject/{work}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Object> TestOject(@PathVariable("work") String work) {
		
		 List<Object> test =null; 
		
		 
		 
		 
		return test;
	}
	
	
	
	
}
