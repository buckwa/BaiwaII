package com.buckwa.web.controller.json;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pbp.AcademicPerson;
import com.buckwa.domain.pbp.ChainOfCommandWrapper;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.FacultyWrapper;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.service.intf.CommonService;
import com.buckwa.service.intf.admin.AdminUserService;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;
import com.buckwa.ws.chum.oxm.Faculty;

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

	
	@RequestMapping(value = "/getFacultyWrapper", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<FacultyWrapper> initList() {

		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandList");
		FacultyWrapper facultyWrapper = null;
		BuckWaResponse response = null;
		List<FacultyWrapper> returnList = new ArrayList<FacultyWrapper>();
		
		try{
			BuckWaRequest request = new BuckWaRequest();
			String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
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
	
	@RequestMapping(value="/managePresident", method = RequestMethod.GET , headers = "Accept=application/json")
	public List<ChainOfCommandWrapper> managePresident( ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandManagePresident");
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
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}		
			returnList.add(chainOfCommandWrapper);
			mav.addObject("chainOfCommandWrapper", chainOfCommandWrapper);	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return returnList;
	}
	
	
	@RequestMapping(value="/manageHead/{departmentId}", method = RequestMethod.GET , headers = "Accept=application/json")
	public List<ChainOfCommandWrapper> manageHead(@RequestParam("departmentId") String departmentId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandManageHead");
		List<ChainOfCommandWrapper> returnList = new ArrayList<ChainOfCommandWrapper>();
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
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}		
			returnList.add(chainOfCommandWrapper);
			mav.addObject("chainOfCommandWrapper", chainOfCommandWrapper);	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return returnList;
	}
	
	
//	@RequestMapping(value="/manageDean/{facultyId}", method = RequestMethod.GET , headers = "Accept=application/json")
//	public ModelAndView manageDean(@RequestParam("facultyId") String facultyId ) {
//		logger.info(" Start  ");
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("chainOfCommandManageDean");
//		try{ 
//			BuckWaRequest request = new BuckWaRequest();
//			User user = new User();		
//			
//			ChainOfCommandWrapper chainOfCommandWrapper =new ChainOfCommandWrapper();
//			chainOfCommandWrapper.setAcademicYear(schoolUtil.getCurrentAcademicYear());
//			chainOfCommandWrapper.setUser(user);
//						
//			// Get Dean
//			request.put("facultyId",facultyId);
//			String academicYear =schoolUtil.getCurrentAcademicYear();
//			request.put("academicYear",academicYear);
//			BuckWaResponse response = facultyService.getDeanByFacultyId(request);
//			if(response.getStatus()==BuckWaConstants.SUCCESS){	
//				AcademicPerson academicPerson = (AcademicPerson)response.getResObj("academicPerson");	 
//				if(academicPerson!=null){
//				chainOfCommandWrapper.setDean(academicPerson);
//				chainOfCommandWrapper.setOldDeanUserName(academicPerson.getEmail());
//				}
//			}	
//			
//			 response = facultyService.getById(request);
//				if(response.getStatus()==BuckWaConstants.SUCCESS){	
//					Faculty faculty = (Faculty)response.getResObj("faculty");	 
//					chainOfCommandWrapper.setFaculty(faculty);
//					chainOfCommandWrapper.setFacultyId(faculty.getFacultyId());
//				}	
//			
//			
//			PagingBean bean = new PagingBean();		
//			mav.addObject("pagingBean", bean);	
//			mav.addObject("user", user); 
//			
//			// Search initial
//			int offset = 0;	
//			bean.setOffset(offset);				
//			
//			request.put("pagingBean", bean);
//			user.setAcademicYear(schoolUtil.getCurrentAcademicYear());
//			bean.put("user", user);
//			response = userService.getUserByOffset(request);
//			if(response.getStatus()==BuckWaConstants.SUCCESS){			
//				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
//				mav.addObject("pagingBean", beanReturn);				
//			}else {				
//				mav.addObject("errorCode", response.getErrorCode()); 
//			}		
//  
//			mav.addObject("chainOfCommandWrapper", chainOfCommandWrapper);	
//		}catch(Exception ex){
//			ex.printStackTrace();
//			mav.addObject("errorCode", "E001"); 
//		}
//		return mav;
//	}
//	
	
	@RequestMapping(value="/GetUserlist", method = RequestMethod.GET , headers = "Accept=application/json")
	public List<PagingBean> init() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
//		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
//		mav =gotoList(mav);
		List<PagingBean> returnList = gotoList(mav);
		return returnList;
	}
	
	private List<PagingBean> gotoList(ModelAndView mav){
		//mav.setViewName("userList");		
		User user = new User();		
		PagingBean bean = new PagingBean();		
		//mav.addObjectPagingBean("pagingBean", bean);	
		mav.addObject("user", user);	
		List<PagingBean> returnList = new ArrayList<PagingBean>();
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
		BuckWaResponse response = userService.getUserByOffset(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){			
			beanReturn = (PagingBean)response.getResObj("pagingBean");
			mav.addObject("pagingBean", beanReturn);				
		}else {				
			mav.addObject("errorCode", response.getErrorCode()); 
		}
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		returnList.add(beanReturn);
		

		return returnList;
	}
	
	//เพิ่ม ข้อมูลบุคลากร
	

	//เพิ่ม ข้อมูลบุคลากร
	
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
	
	
	@RequestMapping(value="/TestOject", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Object> TestOject() {
		
		List<Object> returnList = new ArrayList<Object>();
		logger.info(" Test kab  ");
		Person user =new Person();
		returnList.add(user);
		
		return returnList;
	}
	
	
}
