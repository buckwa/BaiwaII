package com.buckwa.web.controller.pbp;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pbp.AcademicPerson;
import com.buckwa.domain.pbp.ChainOfCommandWrapper;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.FacultyWrapper;
import com.buckwa.service.intf.admin.AdminUserService;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;

@Controller
@RequestMapping("/admin/pbp/chainOfCommand")
@SessionAttributes({"facultyWrapper","faculty","chainOfCommandWrapper","pagingBean"} ) 
public class ChainOfCommandController {	
	private static Logger logger = LoggerFactory.getLogger(ChainOfCommandController.class);	 
	@Autowired
	private FacultyService facultyService;	
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
	private AdminUserService userService;	
	@Autowired
	private AcademicYearUtil academicYearUtil;
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandList");
		try{
			BuckWaRequest request = new BuckWaRequest();
			String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
			BuckWaResponse response = facultyService.getByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				FacultyWrapper facultyWrapper = (FacultyWrapper)response.getResObj("facultyWrapper");
			 
				facultyWrapper.setAcademicYear(academicYear);
				facultyWrapper.setAcademicYearSelect(academicYear);
				mav.addObject("facultyWrapper", facultyWrapper);	
				facultyWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
			}	
			
	 		  
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="getFacultyByYear.htm", method = RequestMethod.GET)
	public ModelAndView getFacultyByYear(@RequestParam("academicYearSelect") String academicYearSelect) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandList");
		try{
			BuckWaRequest request = new BuckWaRequest();
			 
			request.put("academicYear",academicYearSelect);
			BuckWaResponse response = facultyService.getByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				FacultyWrapper facultyWrapper = (FacultyWrapper)response.getResObj("facultyWrapper");
			 
				facultyWrapper.setAcademicYear(academicYearSelect);
				facultyWrapper.setAcademicYearSelect(academicYearSelect);
				mav.addObject("facultyWrapper", facultyWrapper);	
				facultyWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
			}	
	 		  
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="listByFaculty.htm", method = RequestMethod.GET)
	public ModelAndView listByFaculty(@RequestParam("facultyId") String facultyId ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandFacultyList");
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			 
			request.put("facultyId",facultyId);
			String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
			BuckWaResponse response = facultyService.getByFaculty(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				ChainOfCommandWrapper chainOfCommandWrapper = (ChainOfCommandWrapper)response.getResObj("chainOfCommandWrapper");	 
				mav.addObject("chainOfCommandWrapper", chainOfCommandWrapper);	
			}	 

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="listByDepartment.htm", method = RequestMethod.GET)
	public ModelAndView listByDepartment(@RequestParam("departmentId") String departmentId  ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandDepartmentList");
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			 
			request.put("departmentId",departmentId);
			request.put("academicYear",schoolUtil.getCurrentAcademicYear());
			BuckWaResponse response = facultyService.getByDepartment(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				ChainOfCommandWrapper chainOfCommandWrapper = (ChainOfCommandWrapper)response.getResObj("chainOfCommandWrapper");	 
				mav.addObject("chainOfCommandWrapper", chainOfCommandWrapper);	
			}	 

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	@RequestMapping(value="manageDean.htm", method = RequestMethod.GET)
	public ModelAndView manageDean(@RequestParam("facultyId") String facultyId ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandManageDean");
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
				mav.addObject("pagingBean", beanReturn);				
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}		
  
			mav.addObject("chainOfCommandWrapper", chainOfCommandWrapper);	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="searchDean.htm" )
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute ChainOfCommandWrapper chainOfCommandWrapper) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("chainOfCommandManageDean");
		try{			
			PagingBean bean = new PagingBean();
			//int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(0);		
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);	
			User user= chainOfCommandWrapper.getUser();
			user.setAcademicYear(schoolUtil.getCurrentAcademicYear());
			bean.put("user", user);
			BuckWaResponse response = userService.getUserByOffset(request);
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
	
	
	@RequestMapping(value="searchDeanNextPage.htm" )
	public ModelAndView searchNextPage(HttpServletRequest httpRequest,@ModelAttribute ChainOfCommandWrapper chainOfCommandWrapper) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("chainOfCommandManageDean");
		try{			
			PagingBean bean = new PagingBean();
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);		
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);	
			User user= chainOfCommandWrapper.getUser();
			user.setAcademicYear(schoolUtil.getCurrentAcademicYear());
			bean.put("user", user);
		 
			BuckWaResponse response = userService.getUserByOffset(request);
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
	
	@RequestMapping(value="assignToDean.htm" )
	public ModelAndView assignToDean(HttpServletRequest httpRequest,@RequestParam("userName") String userName ) {
		logger.info(" Start  userName:"+userName);
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("chainOfCommandManageDean");
		try{			
			
			ChainOfCommandWrapper chainOfCommandWrapper =(ChainOfCommandWrapper)  httpRequest.getSession().getAttribute("chainOfCommandWrapper");
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
		return mav;
	}	
 

 
	
	// ############################ Manage Head ################
	
	@RequestMapping(value="manageHead.htm", method = RequestMethod.GET)
	public ModelAndView manageHead(@RequestParam("departmentId") String departmentId ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandManageHead");
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
  
			mav.addObject("chainOfCommandWrapper", chainOfCommandWrapper);	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="searchHead.htm" )
	public ModelAndView searchHead(HttpServletRequest httpRequest,@ModelAttribute ChainOfCommandWrapper chainOfCommandWrapper) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("chainOfCommandManageHead");
		try{			
			PagingBean bean = new PagingBean();
			//int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(0);		
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);		
			User user= chainOfCommandWrapper.getUser();
			user.setAcademicYear(schoolUtil.getCurrentAcademicYear());
			bean.put("user", user);
			 
			BuckWaResponse response = userService.getUserByOffset(request);
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
	
	
	@RequestMapping(value="searchHeadNextPage.htm" )
	public ModelAndView searchHeadNextPage(HttpServletRequest httpRequest,@ModelAttribute ChainOfCommandWrapper chainOfCommandWrapper) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("chainOfCommandManageHead");
		try{			
			PagingBean bean = new PagingBean();
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);		
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);		
			User user= chainOfCommandWrapper.getUser();
			String departmentIdStr = chainOfCommandWrapper.getDepartmentId()+"";
			user.setAcademicYear(schoolUtil.getCurrentAcademicYear());
			bean.put("user", user);
			bean.put("departmentId", departmentIdStr);
			BuckWaResponse response = userService.getUserDepartmentByOffset(request);
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
	
	@RequestMapping(value="assignToHead.htm" )
	public ModelAndView assignToHead(HttpServletRequest httpRequest,@RequestParam("userName") String userName ) {

		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("chainOfCommandManageHead");
		try{			
			
			ChainOfCommandWrapper chainOfCommandWrapper =(ChainOfCommandWrapper)  httpRequest.getSession().getAttribute("chainOfCommandWrapper");
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
					chainOfCommandWrapper.setHead(academicPerson);
					chainOfCommandWrapper.setOldHeadUserName(academicPerson.getEmail());
					}
				}	
				response.setSuccessCode("S002");
			} 
 					
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	
	
	
	// ############# Manage President #####
	
	@RequestMapping(value="managePresident.htm", method = RequestMethod.GET)
	public ModelAndView managePresident( ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chainOfCommandManagePresident");
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
  
			mav.addObject("chainOfCommandWrapper", chainOfCommandWrapper);	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="searchPresident.htm" )
	public ModelAndView searchPresident(HttpServletRequest httpRequest,@ModelAttribute ChainOfCommandWrapper chainOfCommandWrapper) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("chainOfCommandManagePresident");
		try{			
			PagingBean bean = new PagingBean();
			//int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(0);		
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);		
			User user= chainOfCommandWrapper.getUser();
			user.setAcademicYear(schoolUtil.getCurrentAcademicYear());
			bean.put("user", user);
			BuckWaResponse response = userService.getUserByOffset(request);
			 
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
	
	
	@RequestMapping(value="searchPresidentNextPage.htm" )
	public ModelAndView searchPresidentNextPage(HttpServletRequest httpRequest,@ModelAttribute ChainOfCommandWrapper chainOfCommandWrapper) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("chainOfCommandManagePresident");
		try{			
			PagingBean bean = new PagingBean();
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);		
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);		
			User user= chainOfCommandWrapper.getUser();
			user.setAcademicYear(schoolUtil.getCurrentAcademicYear());
			bean.put("user", user);
			BuckWaResponse response = userService.getUserByOffset(request);
		 
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
	
	@RequestMapping(value="assignToPresident.htm" )
	public ModelAndView assignToPresident(HttpServletRequest httpRequest,@RequestParam("userName") String userName ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("chainOfCommandManagePresident");
		try{			
			
			ChainOfCommandWrapper chainOfCommandWrapper =(ChainOfCommandWrapper)  httpRequest.getSession().getAttribute("chainOfCommandWrapper");
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
					}
				}	
		 
			} 
 					
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	
}
