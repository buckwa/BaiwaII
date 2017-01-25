package com.buckwa.web.controller.pbp;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.FacultyWrapper;
import com.buckwa.domain.pbp3.ResponseObj;
import com.buckwa.domain.validator.pbp.DepartmentValidator;
import com.buckwa.domain.validator.pbp.FacultyValidator;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;

@Controller
@RequestMapping("/admin/pbp/faculty")
@SessionAttributes({"facultyWrapper","faculty","department"} ) 
public class FacultyController {	
	private static Logger logger = LoggerFactory.getLogger(FacultyController.class);	 
	@Autowired
	private FacultyService facultyService;	
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("facultyList");
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
		logger.info(" Start  academicYearSelect:"+academicYearSelect);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("facultyList");
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

	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView init(@RequestParam("academicYearSelect") String academicYearSelect) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("facultyCreate");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		
	 
		Faculty faculty = new Faculty();
		faculty.setAcademicYear(academicYearSelect);
		mav.addObject("faculty", faculty);	
		return mav;
	}		

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createRole(@ModelAttribute Faculty faculty, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("facultyCreate");
		try{			
			new FacultyValidator().validate(faculty, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("faculty", faculty);
				BuckWaResponse response = facultyService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){							 
					mav.addObject("successCode", response.getSuccessCode()); 
					 				
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
	
	@RequestMapping(value="createDepartment.htm", method = RequestMethod.GET)
	public ModelAndView createDepartment(@RequestParam("facultyId") String facultyId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("departmentCreate");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("facultyId", facultyId);
		BuckWaResponse response = facultyService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Faculty faculty = (Faculty)response.getResObj("faculty");
			faculty.setDepartment(new Department()) ;
			mav.addObject("faculty", faculty); 
			 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}	
 
		return mav;
	}	
		
	@RequestMapping(value="createDepartment.htm", method = RequestMethod.POST)
	public ModelAndView createDepartment(HttpServletRequest httpRequest,@ModelAttribute Faculty faculty, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("facultyList");
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
					 
					mav.setView(new RedirectView(url)); 				
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
	 
	
	@RequestMapping(value="editFaculty.htm", method = RequestMethod.GET)
	public ModelAndView editFaculty(@RequestParam("facultyId") String facultyId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("facultyEdit");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("facultyId", facultyId);
		BuckWaResponse response = facultyService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Faculty faculty = (Faculty)response.getResObj("faculty"); 
			mav.addObject("faculty", faculty); 
			 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}	
 
		return mav;
	}	
	
	
	@RequestMapping(value="editFaculty.htm", method = RequestMethod.POST)
	public ModelAndView editFacultyPOST(@ModelAttribute Faculty faculty, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("facultyEdit");
		try{			
			new FacultyValidator().validate(faculty, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("faculty", faculty);
				BuckWaResponse response = facultyService.updateFaculty(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){							 
					mav.addObject("successCode", response.getSuccessCode()); 
					mav =initList(); 				
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
	
	
	
	
	@RequestMapping(value="editDepartment.htm", method = RequestMethod.GET)
	public ModelAndView editDepartment(@RequestParam("departmentId") String departmentId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("departmentEdit");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("departmentId", departmentId);
		BuckWaResponse response = facultyService.getDepartmentById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Department department = (Department)response.getResObj("department"); 
			mav.addObject("department", department);  
		}else {
			mav.addObject("errorCode", response.getErrorCode());  
		}	 
		return mav;
	}	
	
	
	@RequestMapping(value="editDepartment.htm", method = RequestMethod.POST)
	public ModelAndView editDepartmentPOST(@ModelAttribute Department department, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("departmentEdit");
		try{			
			new DepartmentValidator().validate(department, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("department", department);
				BuckWaResponse response = facultyService.updateDepartment(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){							 
					mav.addObject("successCode", response.getSuccessCode()); 
					mav =initList(); 				
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
	
	
	
	@RequestMapping(value="deleteFacultyById.htm", method = RequestMethod.GET)
	public ModelAndView deleteFacultyById(@RequestParam("facultyId") String facultyId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("facultyEdit");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("facultyId", facultyId);
		BuckWaResponse response = facultyService.deleteFacultyById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			 
			mav = initList();
			 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}	
 
		return mav;
	}
	
	
	@RequestMapping(value="deleteDepartmentById.htm", method = RequestMethod.GET)
	public ModelAndView deleteDepartmentById(@RequestParam("departmentId") String departmentId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("departmentEdit");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("departmentId", departmentId);
		BuckWaResponse response = facultyService.deleteDepartmentById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			 
			mav = initList();
			 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}	
 
		return mav;
	}
	
	@RequestMapping(value="deleteFacultyById/{facultyId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObj deleteFacultyByIdJson(@PathVariable String facultyId) {
		logger.info(" Start  ");
		ResponseObj resObj =new ResponseObj();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("facultyEdit");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("facultyId", facultyId);
		BuckWaResponse response = facultyService.deleteFacultyById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			 
			resObj.setResObj(response);
			resObj.setStatus("1");
			//mav = initList();
			 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}	
 
		return resObj;
	}
	
	
	@RequestMapping(value="deleteDepartmentById/{departmentId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObj deleteDepartmentByIdJson(@PathVariable String departmentId) {
		
		ResponseObj resObj =new ResponseObj();
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("departmentEdit");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("departmentId", departmentId);
		BuckWaResponse response = facultyService.deleteDepartmentById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			 
			//mav = initList();
			resObj.setResObj(response);
			resObj.setStatus("1");
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}	
 
		return resObj;
	}
}
