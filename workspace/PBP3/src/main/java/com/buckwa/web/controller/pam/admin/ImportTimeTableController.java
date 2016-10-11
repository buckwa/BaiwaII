package com.buckwa.web.controller.pam.admin;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.util.StringUtils;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.ClassRoomProcess;
import com.buckwa.domain.pam.FileLocation;
import com.buckwa.domain.pam.ImportData;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pam.PersonMapping;
import com.buckwa.domain.pam.Semester;
import com.buckwa.domain.pam.Subject;
import com.buckwa.domain.pam.Teacher;
import com.buckwa.domain.pam.WorkPerson;
import com.buckwa.domain.pam.WorkPersonAttr;
import com.buckwa.domain.pam.WorkTemplate;
import com.buckwa.domain.pam.WorkTemplateAttr;
import com.buckwa.domain.pam.Year;
import com.buckwa.service.intf.admin.AdminUserService;
import com.buckwa.service.intf.pam.ClassRoomProcessService;
import com.buckwa.service.intf.pam.FileLocationService;
import com.buckwa.service.intf.pam.ImportFileService;
import com.buckwa.service.intf.pam.PersonProfileService;
import com.buckwa.service.intf.pam.SemesterService;
import com.buckwa.service.intf.pam.SubjectService;
import com.buckwa.service.intf.pam.WorkPersonService;
import com.buckwa.service.intf.pam.WorkTemplateService;
import com.buckwa.service.intf.pam.YearService;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.ExcelUtil;
import com.buckwa.util.FileUtils;

/**
 * 
 @Author : Kroekpong Sakulchai (Ball)
 @Create : Aug 5, 2012 10:15:14 PM
 *
 **/

@Controller
@RequestMapping("/admin/importdata/timetable")
@SessionAttributes(types = ImportData.class)
public class ImportTimeTableController {

	private static Logger logger = LoggerFactory.getLogger(ImportTimeTableController.class);
	
	@Autowired
    private PathUtil pathUtil;
	
	@Autowired
	private FileLocationService fileLocationService;
	
	@Autowired
	private ImportFileService importProfileService;
	
	@Autowired
	private ClassRoomProcessService classRoomProcessService;
	
	@Autowired
	private YearService yearService;
	
	@Autowired
	private SemesterService semesterService;
	
	@Autowired
	private AdminUserService userService;
	
	@Autowired
	private PersonProfileService personProfileService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private WorkTemplateService workTemplateService;
	
	@Autowired
	private WorkPersonService workPersonService;
	
	@RequestMapping(value="initTimeTable.htm", method = RequestMethod.GET)
	public ModelAndView initTimeTable() {
		logger.debug(" ###  Start init Import TimeTable ### ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("initImportTimeTable");  
		try{
			ImportData importData =  new ImportData();		
			
			//Get year 
			BuckWaResponse response= yearService.getAll();
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				List<Year> yearList= (List)response.getResObj("yearList");
				mav.addObject("yearList",yearList);
			}			
			mav.addObject("semesterList",null);
			mav.addObject("importData", importData);		 	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		return mav;
	}
	
	@RequestMapping(value="initTimeTableDesc.htm", method = RequestMethod.GET)
	public ModelAndView initTimeTableDesc() {
		logger.debug(" ###  Start init Import TimeTable ### ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("initImportTimeTableDesc");  
		try{
			ImportData importData =  new ImportData();		
			
			//Get year 
			BuckWaResponse response= yearService.getAll();
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				List<Year> yearList= (List)response.getResObj("yearList");
				mav.addObject("yearList",yearList);
			}			
			mav.addObject("semesterList",null);
			mav.addObject("importData", importData);		 	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		return mav;
	}
	
	@RequestMapping(value="getSemester.htm", method = RequestMethod.POST)
	public ModelAndView initSemesterByYear(@ModelAttribute ImportData importData, BindingResult result ) {
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("initImportTimeTable");  
		try{
			
			//Get year 
			BuckWaResponse response= yearService.getAll();
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				List<Year> yearList= (List)response.getResObj("yearList");
				mav.addObject("yearList",yearList);
			}		
			
			//Get semester
			BuckWaRequest request = new BuckWaRequest();
			request.put("yearId", importData.getYearId());
			response= semesterService.getByYearId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				List<Semester> semesterList= (List)response.getResObj("semesterList");
				mav.addObject("semesterList",semesterList);
			}	
			mav.addObject("importData", importData);		 	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		return mav;
	}
	
	@RequestMapping(value="initTimeTableHistory.htm", method = RequestMethod.GET)
	public ModelAndView initTimeTableHistory(){
		logger.debug(" ###  Start init TimeTable History ### ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		initTimeTableHistory(mav);
		return mav;
	}
	
	@RequestMapping(value="initTimeTableHistory.htm", method = RequestMethod.POST)
	public ModelAndView initTimeTableHistoryPOST(){
		logger.debug(" ###  Start init TimeTable History ### ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		initTimeTableHistory(mav);
		return mav;
	}
	
	@RequestMapping(value="search.htm", method = RequestMethod.GET)
	public ModelAndView searchGet(HttpServletRequest httpRequest ,@ModelAttribute PagingBean bean) {
		logger.info(" Start ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("initTimeTableHistory");
		try{			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);	
			BuckWaRequest request = new BuckWaRequest();
			ClassRoomProcess classRoomProcess = new ClassRoomProcess();
			mav.addObject("classRoomProcess", classRoomProcess);	
			request.put("pagingBean", bean);	
			bean.put("classRoomProcess", classRoomProcess);			 
			BuckWaResponse response = classRoomProcessService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {				
				mav.addObject("errorCode", response.getErrorCode()); 
			}						
		}catch(Exception ex){
			ex.printStackTrace();
			 
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	@RequestMapping(value="search.htm", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest httpRequest,@ModelAttribute ClassRoomProcess classRoomProcess,@ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		mav.setViewName("initTimeTableHistory");
		try{		
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);	
			bean.setOffset(offset);						 
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);	
			bean.put("classRoomProcess", classRoomProcess);			
			BuckWaResponse response = classRoomProcessService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);	
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
	
	public ModelAndView initTimeTableHistory(ModelAndView mav) {
		logger.debug(" ###  Start init TimeTable History ### ");
		try{
			PagingBean bean = new PagingBean();
			BuckWaRequest request = new BuckWaRequest();
			mav.addObject("classRoomProcess", new ClassRoomProcess());	
			mav.addObject("pagingBean", bean);
			mav.setViewName("initTimeTableHistory"); 
			
			// Search with initial
			int offset = 0;	
			bean.setOffset(offset);	 
			request = new BuckWaRequest();	
			request.put("pagingBean", bean);	
			bean.put("classRoomProcess", new ClassRoomProcess());			 
			BuckWaResponse response = classRoomProcessService.getByOffset(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);				
			}else {	 
				mav.addObject("errorCode", response.getErrorCode()); 
			}				
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		return mav;
	}
	
	
	@RequestMapping(value="process.htm", method = RequestMethod.GET)
	public ModelAndView procress(@RequestParam("classRoomProcessId") String classRoomProcessId,HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("initTimeTableHistory");  
		try{
			
			BuckWaRequest request = new BuckWaRequest();
			
			//Get user all
			BuckWaResponse response = personProfileService.getAll();
			List<Person> personList=null;
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				personList = (List)response.getResObj("personList");
			}
			
			//Get Class room process
			ClassRoomProcess classRoomProcess=null;
			request.put("classRoomProcessId", classRoomProcessId);
			response = classRoomProcessService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				classRoomProcess = (ClassRoomProcess)response.getResObj("classRoomProcess");
			}
			
			//Get subject all
			List<Subject> subjectList = null;
			if(classRoomProcess!=null){
				request.put("yearId", classRoomProcess.getYearId());
				request.put("semesterId", classRoomProcess.getSemesterId());
				response = subjectService.getAll(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					subjectList= (List)response.getResObj("subjectList");
				}		
			}
			
			//process
			if(personList!=null&&personList.size()>0 && subjectList!=null && subjectList.size()>0){
				//Get WorkTemplate
				response = workTemplateService.getByClassRoom();
				WorkTemplate workTemplate = null;
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					workTemplate= (WorkTemplate)response.getResObj("workTemplate");
				}	
				
				//Get User 
				User user =null;
				request = new BuckWaRequest();
				request.put("username", BuckWaUtils.getUserNameFromContext());
				response = userService.getUserByUsername(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					user = (User)response.getResObj("user");
				}
				
				classRoomProcess.setUpdateBy(BuckWaUtils.getFullName(user.getFirst_name(), user.getLast_name()));
				//flag is process already
				classRoomProcess.setFlag(1);
				
				List<WorkPerson> workPersonList =new ArrayList<WorkPerson>();
				List<Teacher> teacherList=new ArrayList<Teacher>();
				for(Person person : personList){
					for(Subject subject : subjectList){
						if(org.apache.commons.lang.StringUtils.isEmpty(subject.getTeachName())){
							String fullName = subject.getTeachName().trim();
							String[] fullNames = fullName.split(" ");
							String firstName="";
							String lastName="";
							if(fullNames.length>0){
								firstName = fullNames[0];
								lastName = fullNames[fullNames.length-1];
							}
							if((person.getThaiName()!=null&&person.getThaiName().trim().equals(firstName.trim()))
								&& (person.getThaiSurname()!=null&&person.getThaiSurname().trim().equals(lastName.trim()))){
								
								//update pam_teacher
								Teacher teacher = new Teacher();
								teacher.setName(subject.getTeachName());
								teacher.setUserId(person.getUserId());
								teacherList.add(teacher);
								
								//create work person attr
								if(workTemplate!=null){
									WorkPerson workPerson = new WorkPerson();
									workPerson.setName(subject.getNameTh());
									workPerson.setUserid(person.getUserId());
									workPerson.setIsClassRoom(workTemplate.getIsClassRoom());
									workPerson.setWorkTemplateId(workTemplate.getWorkTemplateId());
									workPerson.setCreateBy(BuckWaUtils.getFullName(user.getFirst_name(), user.getLast_name()));
									workPerson.setYearId(classRoomProcess.getYearId());
									workPerson.setSemesterId(classRoomProcess.getSemesterId());
									List<WorkPersonAttr> workPersonAttrList = new ArrayList<WorkPersonAttr>();
									if(workTemplate.getWorkTemplateAttrList()!=null && workTemplate.getWorkTemplateAttrList().size()>0){
										int index=0;
										for(WorkTemplateAttr workTemplateAttr : workTemplate.getWorkTemplateAttrList()){
											WorkPersonAttr workPersonAttr = new WorkPersonAttr();
											workPersonAttr.setLabel(workTemplateAttr.getLabel());
											workPersonAttr.setUnitId(workTemplateAttr.getUnitId());
											workPersonAttr.setUnitName(workTemplateAttr.getUnitName());
											workPersonAttr.setKpiId(workTemplateAttr.getKpiId());
											workPersonAttr.setIsFile(workTemplateAttr.getIsFile().intValue());
											workPersonAttr.setFlagCalculate(workTemplateAttr.getFlagCalculate().intValue());
											workPersonAttr.setWorkTemplateId(workPerson.getWorkTemplateId());
											//subject code
											if(workTemplateAttr.getLabel().equals(BuckWaConstants.SUBJECT_CODE))
												workPersonAttr.setValue(String.valueOf(subject.getCode()));
											//thai name 
											else if(workTemplateAttr.getLabel().equals(BuckWaConstants.TH_NAME))
												workPersonAttr.setValue(subject.getNameTh());
											//thai en 
											else if(workTemplateAttr.getLabel().equals(BuckWaConstants.EN_NAME))
												workPersonAttr.setValue(subject.getNameEn());
											//credit
											else if(workTemplateAttr.getLabel().equals(BuckWaConstants.CREDIT))
												workPersonAttr.setValue(String.valueOf(subject.getCredit()));
											//lect hr
											else if(workTemplateAttr.getLabel().equals(BuckWaConstants.LECT_HR))
												workPersonAttr.setValue(String.valueOf(subject.getLecHr()));
											//prac hr
											else if(workTemplateAttr.getLabel().equals(BuckWaConstants.PRAC_HR))
												workPersonAttr.setValue(String.valueOf(subject.getPrcHr()));
											//self hr
											else if(workTemplateAttr.getLabel().equals(BuckWaConstants.SELF_HR))
												workPersonAttr.setValue(String.valueOf(subject.getSelfHr()));
											//details
											else if(workTemplateAttr.getLabel().equals(BuckWaConstants.DETAILS))
												workPersonAttr.setValue(subject.getDetails());
											workPersonAttr.setCreateBy(workPerson.getCreateBy());
											workPersonAttrList.add(workPersonAttr);
										}
										workPerson.setWorkPersonAttrList(workPersonAttrList);
									}
									workPersonList.add(workPerson);
								}
							}
						}
						
					}
				}
				
				//insert work person for class room
				request.put("workPersonList", workPersonList);
				request.put("classRoomProcess", classRoomProcess);
				request.put("teacherList",teacherList);
				response = workPersonService.createClassRoom(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					req.getSession().setAttribute("successCode", response.getSuccessCode());
					return new ModelAndView(new RedirectView(req.getContextPath()+"/admin/importdata/timetable/initTimeTableHistory.htm"));			
				}else {
					req.getSession().setAttribute("errorCode", response.getErrorCode());
					mav.addObject("errorCode", response.getErrorCode()); 
				}
			}
		
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		return initTimeTableHistory(mav);
	}
	
	@RequestMapping(value="userMapping.htm", method = RequestMethod.GET)
	public ModelAndView userMapping(@RequestParam("classRoomProcessId") String classRoomProcessId,HttpServletRequest req) {
		logger.info("userMapping ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("initMappingUserTimeTable");  
		try{
			
			BuckWaRequest request = new BuckWaRequest();
			
			ImportData importData = new ImportData();
			
			//Get Class room process
			ClassRoomProcess classRoomProcess=null;
			request.put("classRoomProcessId", classRoomProcessId);
			BuckWaResponse response = classRoomProcessService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				classRoomProcess = (ClassRoomProcess)response.getResObj("classRoomProcess");
				mav.addObject("year",classRoomProcess.getYear());
				mav.addObject("semester",classRoomProcess.getSemester());
				importData.setYearId(classRoomProcess.getYearId());
				importData.setClassRoomProcessId(classRoomProcess.getClassRoomProcessId());
				importData.setSemesterId(classRoomProcess.getSemesterId());
			}
			
			List<Person> personList=null;
			//Get user 
			if(classRoomProcess!=null){
				request.put("yearId",classRoomProcess.getYearId());
				request.put("semesterId",classRoomProcess.getSemesterId());
				response = personProfileService.getPersonByNotInTimeTable(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					personList = (List)response.getResObj("personList");
					mav.addObject("personList",personList);
				}
			}
			
			List<Person> teacherList=null;
			//Get user  from pam teacher
			if(classRoomProcess!=null){
				request.put("yearId",classRoomProcess.getYearId());
				request.put("semesterId",classRoomProcess.getSemesterId());
				response = classRoomProcessService.getUserList(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					teacherList = (List)response.getResObj("personList");
					mav.addObject("teacherList",teacherList);
				}
			}
			importData.setPersonMappingList(new ArrayList<PersonMapping>());
			mav.addObject("importData",importData);
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return mav;
	}
	
	
	@RequestMapping(value="userMapping.htm", method = RequestMethod.POST)
	public ModelAndView userMappingProcess(@ModelAttribute ImportData importData, BindingResult result,HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("initMappingUserTimeTable");  
		try{
			
			BuckWaRequest request = new BuckWaRequest();
						
			//Get Class room process
			ClassRoomProcess classRoomProcess=null;
			request.put("classRoomProcessId", importData.getClassRoomProcessId());
			BuckWaResponse response = classRoomProcessService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				classRoomProcess = (ClassRoomProcess)response.getResObj("classRoomProcess");
			}
			
			//GET USERLIST 1 and 2
			String user1List="";
			String user2List ="";
			if( importData.getPersonMappingList()!=null &&  importData.getPersonMappingList().size()>0){
				StringBuffer buffer1 = new StringBuffer();
				StringBuffer buffer2 = new StringBuffer();
				for(PersonMapping obj :  importData.getPersonMappingList()){
					if(obj.getUserId1()!=null && obj.getUserId2()!=null){
						buffer1.append(obj.getUserId1());
						buffer1.append(",");
						buffer2.append(obj.getUserId2());
						buffer2.append(",");
					}
				}
				if(buffer1.length()>0){
					user1List = buffer1.toString().substring(0,buffer1.toString().length()-1);
				}
				
				if(buffer2.length()>0){
					user2List = buffer2.toString().substring(0,buffer2.toString().length()-1);
				}
				
			}
			classRoomProcess.setUser1List(user1List);
			classRoomProcess.setUser2List(user2List);
			
			//Get subject all
			List<Subject> subjectList = null;
			if(classRoomProcess!=null){
				request.put("yearId", classRoomProcess.getYearId());
				request.put("semesterId", classRoomProcess.getSemesterId());
				request.put("userList", user2List);
				response = subjectService.getAllByTeacherList(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					subjectList= (List)response.getResObj("subjectList");
				}		
			}
			
			//process
			if(subjectList!=null && subjectList.size()>0&&
					importData.getPersonMappingList()!=null &&  importData.getPersonMappingList().size()>0){
				//Get WorkTemplate
				response = workTemplateService.getByClassRoom();
				WorkTemplate workTemplate = null;
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					workTemplate= (WorkTemplate)response.getResObj("workTemplate");
				}	
				
				//Get User 
				User user =null;
				request = new BuckWaRequest();
				request.put("username", BuckWaUtils.getUserNameFromContext());
				response = userService.getUserByUsername(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){
					user = (User)response.getResObj("user");
				}
				
				List<WorkPerson> workPersonList =new ArrayList<WorkPerson>();
				List<Teacher> teacherList=new ArrayList<Teacher>();
				
				for(PersonMapping personMapping : importData.getPersonMappingList()){
					for(Subject subject : subjectList){
						
						if(personMapping.getUserId2()!=null && subject.getTeacherId()!=null && personMapping.getUserId2().intValue() == subject.getTeacherId().intValue()){
							//update pam_teacher
							Teacher teacher = new Teacher();
							teacher.setName(subject.getTeachName());
							teacher.setUserId(personMapping.getUserId1());
							teacherList.add(teacher);
							
							//create work person attr
							if(workTemplate!=null){
								WorkPerson workPerson = new WorkPerson();
								workPerson.setName(subject.getNameTh());
								workPerson.setUserid(personMapping.getUserId1());
								workPerson.setIsClassRoom(workTemplate.getIsClassRoom());
								workPerson.setWorkTemplateId(workTemplate.getWorkTemplateId());
								workPerson.setCreateBy(BuckWaUtils.getFullName(user.getFirst_name(), user.getLast_name()));
								workPerson.setYearId(classRoomProcess.getYearId());
								workPerson.setSemesterId(classRoomProcess.getSemesterId());
								List<WorkPersonAttr> workPersonAttrList = new ArrayList<WorkPersonAttr>();
								if(workTemplate.getWorkTemplateAttrList()!=null && workTemplate.getWorkTemplateAttrList().size()>0){
									int index=0;
									for(WorkTemplateAttr workTemplateAttr : workTemplate.getWorkTemplateAttrList()){
										WorkPersonAttr workPersonAttr = new WorkPersonAttr();
										workPersonAttr.setLabel(workTemplateAttr.getLabel());
										workPersonAttr.setUnitId(workTemplateAttr.getUnitId());
										workPersonAttr.setUnitName(workTemplateAttr.getUnitName());
										workPersonAttr.setKpiId(workTemplateAttr.getKpiId());
										workPersonAttr.setIsFile(workTemplateAttr.getIsFile().intValue());
										workPersonAttr.setFlagCalculate(workTemplateAttr.getFlagCalculate().intValue());
										workPersonAttr.setWorkTemplateId(workPerson.getWorkTemplateId());
										//subject code
										if(workTemplateAttr.getLabel().equals(BuckWaConstants.SUBJECT_CODE))
											workPersonAttr.setValue(String.valueOf(subject.getCode()));
										//thai name 
										else if(workTemplateAttr.getLabel().equals(BuckWaConstants.TH_NAME))
											workPersonAttr.setValue(subject.getNameTh());
										//thai en 
										else if(workTemplateAttr.getLabel().equals(BuckWaConstants.EN_NAME))
											workPersonAttr.setValue(subject.getNameEn());
										//credit
										else if(workTemplateAttr.getLabel().equals(BuckWaConstants.CREDIT))
											workPersonAttr.setValue(String.valueOf(subject.getCredit()));
										//lect hr
										else if(workTemplateAttr.getLabel().equals(BuckWaConstants.LECT_HR))
											workPersonAttr.setValue(String.valueOf(subject.getLecHr()));
										//prac hr
										else if(workTemplateAttr.getLabel().equals(BuckWaConstants.PRAC_HR))
											workPersonAttr.setValue(String.valueOf(subject.getPrcHr()));
										//self hr
										else if(workTemplateAttr.getLabel().equals(BuckWaConstants.SELF_HR))
											workPersonAttr.setValue(String.valueOf(subject.getSelfHr()));
										//details
										else if(workTemplateAttr.getLabel().equals(BuckWaConstants.DETAILS))
											workPersonAttr.setValue(subject.getDetails());
										workPersonAttr.setCreateBy(workPerson.getCreateBy());
										workPersonAttrList.add(workPersonAttr);
									}
									workPerson.setWorkPersonAttrList(workPersonAttrList);
								}
								workPersonList.add(workPerson);
							}
						}
					}
				}
				
				//insert work person for class room
				if(workPersonList!=null&&workPersonList.size()>0){
					request.put("workPersonList", workPersonList);
					request.put("teacherList",teacherList);
					request.put("classRoomProcess",classRoomProcess);
					response = workPersonService.mappingUserClassRoom(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						req.getSession().setAttribute("successCode", response.getSuccessCode());
						return new ModelAndView(new RedirectView(req.getContextPath()+"/admin/importdata/timetable/initTimeTableHistory.htm"));			
					}else {
						req.getSession().setAttribute("errorCode", response.getErrorCode());
						mav.addObject("errorCode", response.getErrorCode()); 
					}		
				}else{
					mav.addObject("errorCode", "E001"); 
				}
			}
		
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		return mav;
	}
	
	
	@RequestMapping(value="upload.htm")
	public ModelAndView uploadDocument(@ModelAttribute ImportData importData, BindingResult result  ,HttpServletRequest httpRequest ) {
		 
			logger.info("---- Wait For Uploading File ----");
			
			ModelAndView mav = new ModelAndView();
			mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
			mav.setViewName("initImportProfile");
			
			FileLocation fileLocation = new FileLocation();
			
			try{			 			 

				MultipartFile  originalfile = importData.getFileDataTeachTable(); 
				
				logger.info(" originalfile size:"+originalfile.getSize()+" File Name:"+ originalfile.getOriginalFilename() );
				
				if (originalfile!=null&&originalfile.getSize() > 0) {
					if (originalfile.getSize() > pathUtil.getMaximumImageUploadSize()) {
						logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumImageUploadSize());					 
						mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_TOO_LARGE); 
						return mav;
					}else{		
						
						//  For Upload File >>>>
						String uploadPath =  pathUtil.getUploadPath()+BuckWaConstants.PATH_UPLOAD_PROFILE;
						String file = originalfile.getOriginalFilename();
						String fileName = file.substring(0,file.lastIndexOf("."));
						String fileExtension = file.substring(file.lastIndexOf("."));
						String fileType = originalfile.getContentType();
						String fileSize = Long.toString(originalfile.getSize());
						
						// Add Data to Keep The Upload File Log...
						Date date = new Date();
						fileLocation.setFilePath(uploadPath);  
						fileLocation.setFromTable(BuckWaConstants.PERSON_TABLE);  
						fileLocation.setFileCode(BuckWaConstants.PERSON_CODE+(new Date().getTime())); 	// KP-FIX
						fileLocation.setFileName(fileName); 
						fileLocation.setFileType(fileType);
						fileLocation.setFileExtension(fileExtension);
						fileLocation.setFileSize(fileSize);
						fileLocation.setTopiclv1Id(null);  // KP-FIX
						fileLocation.setCreateBy(httpRequest.getUserPrincipal().getName());
						fileLocation.setCreateDate(new Timestamp(date.getTime()));
						fileLocation.setUpdateBy(httpRequest.getUserPrincipal().getName());
						fileLocation.setUpdateDate(new Timestamp(date.getTime()));
						fileLocation.setFileDesc(FileUtils.getFileDescription(fileLocation));   // KP-FIX
						
						logger.info("FILE >> "+fileLocation);
						logger.info("## File Size :" + originalfile.getSize());
						logger.info("## File Name Original :" + file);
						logger.info("## Upload Path :" + uploadPath);
						
						String fileUpload = uploadPath+file;
						
						logger.info("## File Name + Path :" + fileUpload);
						
						int step = 1 ; 
						boolean isnext = true;
						Workbook workbook = null;
						
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
								
								boolean isFileNameExist = fileLocationService.checkFileNameServerExist(fileName,BuckWaConstants.PERSON_TABLE);
								if(!isFileNameExist){
									isnext = FileUtils.saveFileToDirectory(originalfile, fileUpload);
									if(isnext){
										step++; 
										continue;
									}else{
										isnext = false;
									}
								}else{
									isnext = false;
									mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_NAME_EXIST); 
									return mav;
								}
							case 3 :
								logger.info(" Step : "+step+" >> Insert into File Location Database (table : file_location) For File Upload History");
								Long key = fileLocationService.create(fileLocation);
								if(isnext && null != key){
									step++; 
									continue;
								}else{
									isnext = false;
								}
							case 4 :
								logger.info(" Step : "+step+" >> Get Work Book From Excel File (That Complete Upload)");
								workbook = ExcelUtil.getWorkbook(fileUpload);
								if(isnext && workbook != null){
									step++; 
									continue;
								}else{
									isnext = false;
								}
							case 5 :
								logger.info(" Step : "+step+" >> Read Work Book and Insert User Profile into Person Database (table : person_pbp)");
								//importProfileService.readProfileWorkbook(workbook, httpRequest);
								importProfileService.readTimeTableWorkbook (workbook, httpRequest);
								step++; 
							default:
								isnext = false;
							}
						}
						
						return initProfileHistory(null, BuckWaConstants.PROCESS_IMPORT);
					}		 
				}else{					 
					mav.addObject("errorCode", BuckWaConstants.MSGCODE_SELECT_FILE); 
					return mav;					
				}			 			
			}catch(Exception ex){
				ex.printStackTrace();
				mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
			}		
			return null;
		}	
		
	
	@RequestMapping(value="uploadDesc.htm")
	public ModelAndView uploadDocumentDescription(@ModelAttribute ImportData importData, BindingResult result  ,HttpServletRequest httpRequest ) {
		 
			logger.info("---- Wait For Uploading File ----");
			
			ModelAndView mav = new ModelAndView();
			mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
			mav.setViewName("initImportProfile");
			
			FileLocation fileLocation = new FileLocation();
			
			try{			 			 

				MultipartFile  originalfile = importData.getFileDataTeachTable(); 
				
				logger.info(" originalfile size:"+originalfile.getSize()+" File Name:"+ originalfile.getOriginalFilename() );
				
				if (originalfile!=null&&originalfile.getSize() > 0) {
					if (originalfile.getSize() > pathUtil.getMaximumImageUploadSize()) {
						logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumImageUploadSize());					 
						mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_TOO_LARGE); 
						return mav;
					}else{		
						
						//  For Upload File >>>>
						String uploadPath =  pathUtil.getUploadPath()+BuckWaConstants.PATH_UPLOAD_PROFILE;
						String file = originalfile.getOriginalFilename();
						String fileName = file.substring(0,file.lastIndexOf("."));
						String fileExtension = file.substring(file.lastIndexOf("."));
						String fileType = originalfile.getContentType();
						String fileSize = Long.toString(originalfile.getSize());
						
						// Add Data to Keep The Upload File Log...
						Date date = new Date();
						fileLocation.setFilePath(uploadPath);  
						fileLocation.setFromTable(BuckWaConstants.PERSON_TABLE);  
						fileLocation.setFileCode(BuckWaConstants.PERSON_CODE+(new Date().getTime())); 	// KP-FIX
						fileLocation.setFileName(fileName); 
						fileLocation.setFileType(fileType);
						fileLocation.setFileExtension(fileExtension);
						fileLocation.setFileSize(fileSize);
						fileLocation.setTopiclv1Id(null);  // KP-FIX
						fileLocation.setCreateBy(httpRequest.getUserPrincipal().getName());
						fileLocation.setCreateDate(new Timestamp(date.getTime()));
						fileLocation.setUpdateBy(httpRequest.getUserPrincipal().getName());
						fileLocation.setUpdateDate(new Timestamp(date.getTime()));
						fileLocation.setFileDesc(FileUtils.getFileDescription(fileLocation));   // KP-FIX
						
						logger.info("FILE >> "+fileLocation);
						logger.info("## File Size :" + originalfile.getSize());
						logger.info("## File Name Original :" + file);
						logger.info("## Upload Path :" + uploadPath);
						
						String fileUpload = uploadPath+file;
						
						logger.info("## File Name + Path :" + fileUpload);
						
						int step = 1 ; 
						boolean isnext = true;
						Workbook workbook = null;
						
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
								
								boolean isFileNameExist = fileLocationService.checkFileNameServerExist(fileName,BuckWaConstants.PERSON_TABLE);
								if(!isFileNameExist){
									isnext = FileUtils.saveFileToDirectory(originalfile, fileUpload);
									if(isnext){
										step++; 
										continue;
									}else{
										isnext = false;
									}
								}else{
									isnext = false;
									mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_NAME_EXIST); 
									return mav;
								}
							case 3 :
								logger.info(" Step : "+step+" >> Insert into File Location Database (table : file_location) For File Upload History");
								Long key = fileLocationService.create(fileLocation);
								if(isnext && null != key){
									step++; 
									continue;
								}else{
									isnext = false;
								}
							case 4 :
								logger.info(" Step : "+step+" >> Get Work Book From Excel File (That Complete Upload)");
								workbook = ExcelUtil.getWorkbook(fileUpload);
								if(isnext && workbook != null){
									step++; 
									continue;
								}else{
									isnext = false;
								}
							case 5 :
								logger.info(" Step : "+step+" >> Read Work Book and Insert User Profile into Person Database (table : person_pbp)");
								//importProfileService.readProfileWorkbook(workbook, httpRequest);
								importProfileService.readTimeTableDescWorkbook( workbook, httpRequest);
								step++; 
							default:
								isnext = false;
							}
						}
						
						return initProfileHistory(null, BuckWaConstants.PROCESS_IMPORT);
					}		 
				}else{					 
					mav.addObject("errorCode", BuckWaConstants.MSGCODE_SELECT_FILE); 
					return mav;					
				}			 			
			}catch(Exception ex){
				ex.printStackTrace();
				mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
			}		
			return null;
		}	
		
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("fileCode") String fileCode ,HttpServletRequest httpRequest,
			@ModelAttribute FileLocation fileLocation ,@ModelAttribute PagingBean bean) {
		
		logger.debug("#####  Start  Delete   << "+ fileCode +" >> #### ");
		
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("fileCode", fileCode);	
			BuckWaResponse response = fileLocationService.deleteFile(request);
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				mav.addObject("successCode",BuckWaConstants.MSGCODE_DELETE_SUCESS); 		 				
			}else {	
				mav.addObject("errorCode", response.getErrorCode()); 
				mav.addObject("pagingBean", bean);	
			}	
			
			// Search Again
			mav.addObject("successCode", BuckWaConstants.PROCESS_DELETE);
			return initTimeTableHistory(mav);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}
	
		return null;
	}	
	
	@RequestMapping(value="download.htm")
	public ModelAndView download(@RequestParam("fileCode") String fileCode ,HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		
		logger.debug("#####  Start  Download   << "+ fileCode +" >> #### ");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		FileLocation fileLocation = new FileLocation();
		
		try {
			
			fileLocation = fileLocationService.findByFileCode(fileCode);
			
			logger.debug("File >>"+fileLocation);
			
			String filePath = fileLocation.getFilePath();
			String fileName = fileLocation.getFileName()+fileLocation.getFileExtension();
			String fullPath = filePath+fileName;
			
			InputStream inputStream = new FileInputStream(fullPath);
			
			httpResponse.setContentType(fileLocation.getFileType());
			httpResponse.setContentLength(Integer.parseInt(fileLocation.getFileSize()));

			// Check For IE OR NOT for Encoder fileName !
			String user_agent = httpRequest.getHeader("user-agent");
			boolean isInternetExplorer = (user_agent.indexOf(BuckWaConstants.BROWSER_MSIE) > -1);
			if (isInternetExplorer) {
				logger.debug("Hello You Are IE >>");
				httpResponse.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"");
			} else {
				logger.debug("Hello You Not IE >>");
				httpResponse.setHeader("Content-disposition", "attachment; filename=\"" + MimeUtility.encodeWord(fileName) + "\"");
			}
			
			FileCopyUtils.copy( inputStream, httpResponse.getOutputStream());
			
			httpResponse.flushBuffer();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001);
		}
		return null;

	}
	
	@RequestMapping(value="search.htm")
	public ModelAndView search(@RequestParam("fileName") String search, HttpServletRequest httpRequest) {
		
		logger.debug("#####  Start  Search by param fileName << "+ search +" >> #### ");
		
		return null;
	}
	
	@RequestMapping(value="initProfileHistory.htm", method = RequestMethod.GET)
	public ModelAndView initProfileHistory(String fileName, String process) {
		logger.info(" ###  Start init Import Profile History List ### ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{
			ImportData importData =  new ImportData();
			PagingBean bean = new PagingBean();
			FileLocation fileLocation = new FileLocation();
			BuckWaRequest request = new BuckWaRequest();
			mav.addObject("importData", importData);	
			mav.addObject("pagingBean", bean);
			mav.setViewName("initProfileHistory");  
			
			int offset = 0;	
			bean.setOffset(offset);	
			mav.addObject("fileLocation", fileLocation);
			bean.put("fromTable", BuckWaConstants.PERSON_TABLE);
			
			// For search by file name !
			if(StringUtils.hasText(fileName)){
				logger.info(" >> Search By File Name Like => "+fileName);
				bean.put("fileName", fileName); // for test search
			}
			request.put("pagingBean", bean);
			
			// Map object to Front End
			BuckWaResponse response = fileLocationService.getByOffset(request);
			
			// Set output process message
			logger.info(" >> PROCESS => "+process);
			if(StringUtils.hasText(process)){
				if(process.equalsIgnoreCase(BuckWaConstants.PROCESS_IMPORT)){
					mav.addObject("successCode", BuckWaConstants.MSGCODE_IMPORT_SUCESS);
				}else if(process.equalsIgnoreCase(BuckWaConstants.PROCESS_DELETE)){
					mav.addObject("successCode", BuckWaConstants.MSGCODE_DELETE_SUCESS);
				}
				
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					logger.info(" Success ");
					PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
					bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
					bean.setTotalItems(beanReturn.getTotalItems());
					mav.addObject("pagingBean", bean);		
				}else {	 
					mav.addObject("errorCode", response.getErrorCode()); 
				}	
			}
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		return mav;
	}
	
}
