package com.buckwa.web.controller.pam.evaluate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.EstimateUser;
import com.buckwa.domain.pam.Evaluate;
import com.buckwa.domain.pam.KpiPersonEvaluateMapping;
import com.buckwa.domain.pam.KpiSchedule;
import com.buckwa.domain.pam.MarkLevelDetail;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pam.PersonEvaluateMapping;
import com.buckwa.domain.pam.Semester;
import com.buckwa.domain.pam.Unit;
import com.buckwa.domain.pam.Year;
import com.buckwa.domain.pam.nodetree.EvaluateKpi;
import com.buckwa.domain.pam.nodetree.EvaluateKpiTree;
import com.buckwa.domain.pam.nodetree.Kpi;
import com.buckwa.domain.pam.nodetree.Node;
import com.buckwa.service.intf.pam.KpiScheduleService;
import com.buckwa.service.intf.pam.PersonEvaluateMappingService;
import com.buckwa.service.intf.pam.PersonProfileService;
import com.buckwa.service.intf.pam.SemesterService;
import com.buckwa.service.intf.pam.UnitService;
import com.buckwa.service.intf.pam.YearService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.PAMConstants;

@Controller
@RequestMapping("/pam/evaluate")
@SessionAttributes(types = Evaluate.class)
public class EvaluateController {	
	private static Logger logger = LoggerFactory.getLogger(EvaluateController.class);
	
	@Autowired
	private PersonProfileService personProfileService;
	
	@Autowired
	private PersonEvaluateMappingService personMappingEvaluateService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private YearService yearService;
	
	@Autowired
	private KpiScheduleService kpiScheduleService;
	
	@Autowired
	private SemesterService semesterService;
	
	
	private BigDecimal totalFirstLevelScore = BigDecimal.ZERO;
	private BigDecimal totalSecondLevelScore  = BigDecimal.ZERO;
	private Integer YEAR_ID  = 2012;
	private Integer YEAR_VALUE  = 543;
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList(HttpServletRequest httpRequest) {
		logger.info(" ###  Start init Evaluate  List ### KB ### ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.EVALUATE_INIT);
		mav.setViewName("initEvaluate");  
		try{
			
			BuckWaRequest request = new BuckWaRequest();
			
			String groupId = httpRequest.getParameter("groupId");
			String evaluateTerm = BuckWaConstants.EVALUATE_TERM_1;
			String evaluateAllowed = BuckWaConstants.EVALUATE_NOT_ALLOWED;
			
			if(null == groupId){
				groupId = BuckWaConstants.EVALUATE_TEACHER_ID;
			}
			
			
			BuckWaResponse response = yearService.getYearCurrent();
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				YEAR_ID = (Integer)response.getResObj("year");
			}
			
			
			request.put("yearName", YEAR_ID);
			request.put("groupId", groupId); 
			
			KpiSchedule kpiSchedule = new KpiSchedule();
			response = kpiScheduleService.getByYearName(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				kpiSchedule = (KpiSchedule) response.getResObj("kpiSchedule");
			}
			
			Date todayDate =BuckWaDateUtils.getCurrentDateTime();
			
			if(groupId.equals(BuckWaConstants.EVALUATE_TEACHER_ID)){
				
				// For Teacher Evaluate Date & Term Check
				Date startDate1=kpiSchedule.getTeacherEvaluateStartDate1();
				Date endDate1 =kpiSchedule.getTeacherEvaluateEndDate1();
				Date startDate2=kpiSchedule.getTeacherEvaluateStartDate2();
				Date endDate2 =kpiSchedule.getTeacherEvaluateEndDate2();
				
				mav.addObject("startEvaluateDate1", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(startDate1));
				mav.addObject("endEvaluateDate1", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(endDate1));
				mav.addObject("startEvaluateDate2", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(startDate2));
				mav.addObject("endEvaluateDate2", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(endDate2));
				
				if(todayDate.after(startDate1) && todayDate.before(endDate1)){
					evaluateTerm = BuckWaConstants.EVALUATE_TERM_1;
					evaluateAllowed =  BuckWaConstants.EVALUATE_ALLOWED;
				}else if(todayDate.after(startDate2) && todayDate.before(endDate2)){
					evaluateTerm = BuckWaConstants.EVALUATE_TERM_2;
					evaluateAllowed = BuckWaConstants.EVALUATE_ALLOWED;
				}
				
			}else{
				
				// For Staff Evaluate Date & Term Check
				Date startDate=kpiSchedule.getStaffEvaluateStartDate();
				Date endDate =kpiSchedule.getStaffEvaluateEndDate();
				
				mav.addObject("startEvaluateDate", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(startDate));
				mav.addObject("endEvaluateDate", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(endDate));
				
				if(todayDate.after(startDate) && todayDate.before(endDate)){
					evaluateTerm = BuckWaConstants.EVALUATE_TERM_1;
					evaluateAllowed =  BuckWaConstants.EVALUATE_ALLOWED;
				}
			}
			
			mav.addObject("evaluateYear", YEAR_ID+YEAR_VALUE);
			mav.addObject("evaluateTerm", evaluateTerm);
			
			httpRequest.setAttribute("groupId", groupId);
			httpRequest.setAttribute("evaluateAllowed", evaluateAllowed);
			httpRequest.getSession().setAttribute("evaluateAllowed", evaluateAllowed);
			
			Evaluate evaluate =  new Evaluate();
			Person person = new Person();
			
			request = new BuckWaRequest();
			response = new BuckWaResponse();
			request.put("username", BuckWaUtils.getUserNameFromContext());
			response = personProfileService.getByUsername(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				person = (Person) response.getResObj("person");
				mav.addObject("person", person);
			}
			
			List<PersonEvaluateMapping> personEvaluateMappingList = new ArrayList<PersonEvaluateMapping>();
			
			Long userId = person.getUserId();
			request = new BuckWaRequest();
			
			// personId for who doing an evaluate
			request.put("userId", userId);
			response = personMappingEvaluateService.getUnderEstimateUserListByUserId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				List<EstimateUser> estimateUserList = (List<EstimateUser>)response.getResObj("estimateUserList");					
				
				// ### Start Get Estimate User Detail ###
				for (EstimateUser estimateUser : estimateUserList) {
					
					PersonEvaluateMapping eval = new PersonEvaluateMapping();
					eval.setPerson(estimateUser.getPerson()); 

					request.put("personId", estimateUser.getPerson().getPersonId().toString());
					request.put("yearId", Integer.toString(YEAR_ID)); 
					request.put("evaluateTerm",evaluateTerm); // FIXME As SemesterId
//					response = personMappingEvaluateService.getEvaluateStatusByPersonIdYearIdSemesterId(request);
					response = personMappingEvaluateService.getEvaluateStatusByPersonIdYearIdByTerm(request);
					if (response.getStatus() == BuckWaConstants.SUCCESS) {
						eval.setEvaluateStatus((String) response.getResObj("evaluateStatus"));
					}
					
					EvaluateKpiTree evaluateKpiTree = new EvaluateKpiTree();
					
					// KPI method 
					request.put("yearId", Integer.toString(YEAR_ID)); 
					request.put("groupId", groupId); 
					response =personMappingEvaluateService.getKPIEvaluateByYearAndGroupId(request) ;
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						evaluateKpiTree = (EvaluateKpiTree)response.getResObj("evaluateKpiTree");	
						rearangeOrder(evaluateKpiTree);
						
						List<Unit> unitList = null;
						response =unitService.getAll();
						if(response.getStatus()==BuckWaConstants.SUCCESS){					 
							unitList =(List<Unit>)response.getResObj("unitList");	
							httpRequest.getSession().setAttribute("unitList", unitList); 
						} 
		 
						setUnitandMarkType(evaluateKpiTree,unitList);
						
						// Get Default Score
						List<KpiPersonEvaluateMapping> kpiDefaultScoreList = new ArrayList<KpiPersonEvaluateMapping>();
						request.put("personId", estimateUser.getPerson().getPersonId().toString());
						request.put("evaluateYear", YEAR_ID); 
						request.put("evaluateTerm", Integer.parseInt(evaluateTerm)); 
//						if(BeanUtils.isNotEmpty(
						response = personMappingEvaluateService.getKPIPersonEvaluateList(request);
						if(response.getStatus()==BuckWaConstants.SUCCESS){	
							kpiDefaultScoreList = (List<KpiPersonEvaluateMapping>) response.getResObj("kpiDefaultScoreList");	
							mav.addObject("kpiDefaultScoreList", kpiDefaultScoreList);
							
							// Set KPI Default Score
							setKpiDefaultScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
							
							// Set KPI Level 2 Score
							setKpiSecondLevelScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
							
							// Set KPI Level 1 Score
//							setKpiFirstLevelScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
						}		

						BigDecimal totalDefaultScore = getTotalDefaultScore(kpiDefaultScoreList);
						eval.setTotalScore(totalDefaultScore);
						eval.setTotalSecoundScore(totalSecondLevelScore.setScale(0));
						eval.setTotalFirstScore(totalFirstLevelScore.setScale(0));
					} 
					
					if(groupId.equals(BuckWaConstants.EVALUATE_TEACHER_ID)){						
						if(BuckWaConstants.EVALUATE_TYPE_TEACHER.contains(eval.getPerson().getEmployeeType())){
							personEvaluateMappingList.add(eval); // Add PersonMappingEvaluate if Type teacher
						}						
					}else{
						if(eval.getPerson().getEmployeeType().equals(BuckWaConstants.EVALUATE_TYPE_STAFF)){
							personEvaluateMappingList.add(eval);  // Add PersonMappingEvaluate if Type staff
						}
					}
				}	
				
				evaluate.setPersonEvaluateMappingList(personEvaluateMappingList);  // set for evaluate
				
			}else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode()); 
			}	
			
			mav.addObject("evaluate", evaluate);
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001); 
		}
		
		return mav;
	}	
	
	@RequestMapping(value="initEvaluateForm.htm", method = RequestMethod.GET)
	public ModelAndView initEvaluateForm(
			@RequestParam("personId") String personId, 
			HttpServletRequest httpRequest,
			@ModelAttribute PagingBean bean, String evaluateStatus) {
		
		logger.info(" ###  Start init Evaluate  List ### KB ### ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.EVALUATE_INIT);
		mav.addObject("personId", personId);
		
		mav.setViewName("initEvaluateForm");  
		
		if(BeanUtils.isNotEmpty(httpRequest.getParameter("evaluateStatus"))){
			mav.addObject("evaluateStatus", httpRequest.getParameter("evaluateStatus"));
		}else if(BeanUtils.isNotEmpty(evaluateStatus)){
			mav.addObject("evaluateStatus", evaluateStatus);
		}
		
		try{
			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);
			bean.setOffset(offset);	
			
			Evaluate evaluate =  new Evaluate();
			Person person = new Person();
			
			mav.addObject("evaluate", evaluate);
			
			BuckWaRequest request = new BuckWaRequest();
			BuckWaResponse response = new BuckWaResponse();
			request.put("pagingBean", bean);	
			
			request.put("personId", personId);
			response = personProfileService.getByPersonId(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				person = (Person) response.getResObj("person");
				mav.addObject("person", person);	
			}
			
			response = yearService.getYearCurrent();
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				YEAR_ID = (Integer)response.getResObj("year");
			}
			
			request.put("personId", personId);
			request.put("yearId", Integer.toString(YEAR_ID)); 
			
			String groupId = httpRequest.getParameter("groupId");
			if(null == groupId){
				groupId = BuckWaConstants.EVALUATE_TEACHER_ID;
			}
			
			String evaluateTerm = httpRequest.getParameter("evaluateTerm");
			if(null == evaluateTerm){
				evaluateTerm=  BuckWaConstants.EVALUATE_TERM_1;
			}
			request.put("groupId", groupId); 
			mav.addObject("groupId", groupId); 
			mav.addObject("evaluateTerm", evaluateTerm); 
			mav.addObject("yearName", YEAR_ID); 
			
			request.put("yearName", YEAR_ID);
			
			KpiSchedule kpiSchedule = new KpiSchedule();
			response = kpiScheduleService.getByYearName(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				kpiSchedule = (KpiSchedule) response.getResObj("kpiSchedule");
			}
			
			Date todayDate =BuckWaDateUtils.getCurrentDateTime();
			
			if(groupId.equals(BuckWaConstants.EVALUATE_TEACHER_ID)){
				
				// For Teacher Evaluate Date & Term Check
				Date startDate1=kpiSchedule.getTeacherEvaluateStartDate1();
				Date endDate1 =kpiSchedule.getTeacherEvaluateEndDate1();
				Date startDate2=kpiSchedule.getTeacherEvaluateStartDate2();
				Date endDate2 =kpiSchedule.getTeacherEvaluateEndDate2();
				
				if(evaluateTerm.equals(BuckWaConstants.EVALUATE_TERM_1)){
//					evaluateTerm = BuckWaConstants.EVALUATE_TERM_1;
					mav.addObject("startEvaluateDate", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(startDate1));
					mav.addObject("endEvaluateDate", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(endDate1));
				}else{
//					evaluateTerm = BuckWaConstants.EVALUATE_TERM_2;
					mav.addObject("startEvaluateDate", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(startDate2));
					mav.addObject("endEvaluateDate", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(endDate2));
				}
				
			}else{
				
				// For Staff Evaluate Date & Term Check
				Date startDate=kpiSchedule.getStaffEvaluateStartDate();
				Date endDate =kpiSchedule.getStaffEvaluateEndDate();
				
				mav.addObject("startEvaluateDate", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(startDate));
				mav.addObject("endEvaluateDate", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(endDate));
				
				if(todayDate.after(startDate) && todayDate.before(endDate)){
					evaluateTerm = BuckWaConstants.EVALUATE_TERM_1;
				}
			}
			
			EvaluateKpiTree evaluateKpiTree = new EvaluateKpiTree();
			
			// KPI method 
			request.put("yearId", Integer.toString(YEAR_ID)); 
			request.put("groupId", groupId); 
			response =personMappingEvaluateService.getKPIEvaluateByYearAndGroupId(request) ;
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				evaluateKpiTree = (EvaluateKpiTree)response.getResObj("evaluateKpiTree");	
				rearangeOrder(evaluateKpiTree);
				
				List<Unit> unitList = null;
				response =unitService.getAll();
				if(response.getStatus()==BuckWaConstants.SUCCESS){					 
					unitList =(List<Unit>)response.getResObj("unitList");	
					httpRequest.getSession().setAttribute("unitList", unitList); 
				} 
 
				setUnitandMarkType(evaluateKpiTree,unitList);
				
				// Get Default Score
				List<KpiPersonEvaluateMapping> kpiDefaultScoreList = new ArrayList<KpiPersonEvaluateMapping>();
				request.put("personId", personId.toString());
				request.put("evaluateYear", YEAR_ID); 
				request.put("evaluateTerm", Integer.parseInt(evaluateTerm)); 
				response = personMappingEvaluateService.getKPIPersonEvaluateList(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					kpiDefaultScoreList = (List<KpiPersonEvaluateMapping>) response.getResObj("kpiDefaultScoreList");	
					mav.addObject("kpiDefaultScoreList", kpiDefaultScoreList);
					
					// Set KPI Default Score
					setKpiDefaultScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
					
					// Set KPI Level 2 Score
					setKpiSecondLevelScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
					
					// Set KPI Level 1 Score
//					setKpiFirstLevelScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
				}		

				BigDecimal totalDefaultScore = getTotalDefaultScore(kpiDefaultScoreList);
				mav.addObject("totalDefaultScore", totalDefaultScore);
				mav.addObject("totalSecondLevelScore", totalSecondLevelScore.setScale(0));
				mav.addObject("totalFirstLevelScore", totalFirstLevelScore.setScale(0));
				
				mav.addObject("evaluateKpiTree", evaluateKpiTree);
			}  
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001); 
		}
		
		return mav;
	}
	
	@RequestMapping(value="historyEvaluateForm.htm", method = RequestMethod.GET)
	public ModelAndView historyEvaluateForm(@RequestParam("personId") String personId, HttpServletRequest httpRequest,
			String pageAction,@ModelAttribute PagingBean bean) {
		
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		mav.addObject("personId", personId);
		
		mav.setViewName("historyEvaluateForm");  
		try{
			
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);
			bean.setOffset(offset);	
			
			Evaluate evaluate =  new Evaluate();
			Person person = new Person();
			
			mav.addObject("evaluate", evaluate);
			
			BuckWaRequest request = new BuckWaRequest();
			BuckWaResponse response = new BuckWaResponse();
			request.put("pagingBean", bean);	
			
			request.put("personId", personId);
			response = personProfileService.getByPersonId(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				person = (Person) response.getResObj("person");
				mav.addObject("person", person);	
			}
			
			request.put("personId", personId);
			
			String semesterId = null;
			String yearId =  Integer.toString(BuckWaDateUtils.getYearCurrent());
			
			request.put("yearId",yearId);
			response = semesterService.getByYearId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				List<Semester> semesterList= (List)response.getResObj("semesterList");		
				mav.addObject("semesterList", semesterList);
				
				if(null == semesterId){
					if(semesterList.get(0).getSemesterId() != null){
						semesterId = Long.toString(semesterList.get(0).getSemesterId());
					}else {
						semesterId = "1";
					}
				}
			}
			
//			request.put("semesterId",semesterId); 
//			response = personMappingEvaluateService.getEvaluateStatusByPersonIdYearIdSemesterId(request);
//			if (response.getStatus() == BuckWaConstants.SUCCESS) {
//				String evaluateStatus = (String) response.getResObj("evaluateStatus");
//				mav.addObject("evaluateStatus", evaluateStatus);
//			}
//			
//			if(null==pageAction){
//				pageAction = BuckWaConstants.ACTION_VIEW;
//			}
//			mav.addObject("pageAction", pageAction);
//			
			String groupId = httpRequest.getParameter("groupId");
			if(null == groupId){
				groupId = BuckWaConstants.EVALUATE_TEACHER_ID;
			}
			
			String evaluateTerm = httpRequest.getParameter("evaluateTerm");
			if(null == evaluateTerm){
				evaluateTerm=  BuckWaConstants.EVALUATE_TERM_1;
			}
			request.put("groupId", groupId); 
			mav.addObject("groupId", groupId); 
			mav.addObject("evaluateTerm", evaluateTerm); 
			
			
			request.put("yearName", BuckWaDateUtils.getYear(BuckWaDateUtils.getCurrentDateTime()));
			
			KpiSchedule kpiSchedule = new KpiSchedule();
			response = kpiScheduleService.getByYearName(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				kpiSchedule = (KpiSchedule) response.getResObj("kpiSchedule");
			}
			
			Date todayDate =BuckWaDateUtils.getCurrentDateTime();
			
			if(groupId.equals(BuckWaConstants.EVALUATE_TEACHER_ID)){
				
				// For Teacher Evaluate Date & Term Check
				Date startDate1=kpiSchedule.getTeacherEvaluateStartDate1();
				Date endDate1 =kpiSchedule.getTeacherEvaluateEndDate1();
				Date startDate2=kpiSchedule.getTeacherEvaluateStartDate2();
				Date endDate2 =kpiSchedule.getTeacherEvaluateEndDate2();
				
				if(evaluateTerm.equals(BuckWaConstants.EVALUATE_TERM_1)){
//					evaluateTerm = BuckWaConstants.EVALUATE_TERM_1;
					mav.addObject("startEvaluateDate", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(startDate1));
					mav.addObject("endEvaluateDate", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(endDate1));
				}else{
//					evaluateTerm = BuckWaConstants.EVALUATE_TERM_2;
					mav.addObject("startEvaluateDate", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(startDate2));
					mav.addObject("endEvaluateDate", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(endDate2));
				}
				
			}else{
				
				// For Staff Evaluate Date & Term Check
				Date startDate=kpiSchedule.getStaffEvaluateStartDate();
				Date endDate =kpiSchedule.getStaffEvaluateEndDate();
				
				mav.addObject("startEvaluateDate", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(startDate));
				mav.addObject("endEvaluateDate", BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(endDate));
				
				if(todayDate.after(startDate) && todayDate.before(endDate)){
					evaluateTerm = BuckWaConstants.EVALUATE_TERM_1;
				}
			}
			
			EvaluateKpiTree evaluateKpiTree = new EvaluateKpiTree();
			
			// KPI method 
			request.put("yearId", yearId); 
			request.put("groupId", groupId); 
			response =personMappingEvaluateService.getKPIEvaluateByYearAndGroupId(request) ;
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				evaluateKpiTree = (EvaluateKpiTree)response.getResObj("evaluateKpiTree");	
				rearangeOrder(evaluateKpiTree);
				
				List<Unit> unitList = null;
				response =unitService.getAll();
				if(response.getStatus()==BuckWaConstants.SUCCESS){					 
					unitList =(List<Unit>)response.getResObj("unitList");	
					httpRequest.getSession().setAttribute("unitList", unitList); 
				} 
				
				setUnitandMarkType(evaluateKpiTree,unitList);
				
				// Get Default Score
				List<KpiPersonEvaluateMapping> kpiDefaultScoreList = new ArrayList<KpiPersonEvaluateMapping>();
				request.put("personId", personId.toString());
				request.put("evaluateYear",Integer.parseInt(yearId)); 
				request.put("evaluateTerm", Integer.parseInt(evaluateTerm)); 
				response = personMappingEvaluateService.getKPIPersonEvaluateList(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					kpiDefaultScoreList = (List<KpiPersonEvaluateMapping>) response.getResObj("kpiDefaultScoreList");	
					mav.addObject("kpiDefaultScoreList", kpiDefaultScoreList);
					
					// Set KPI Default Score (Level 3)
					setKpiDefaultScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
					
					// Set KPI Level 2 Score
					setKpiSecondLevelScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
					
					// Set KPI Level 1 Score
//					setKpiFirstLevelScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
					
				}		
				
				// Calculate Total KPI Default Score (Level 3)
				BigDecimal totalDefaultScore = getTotalDefaultScore(kpiDefaultScoreList);
				mav.addObject("totalDefaultScore", totalDefaultScore);
				mav.addObject("totalSecondLevelScore", totalSecondLevelScore.setScale(0));
				mav.addObject("totalFirstLevelScore", totalFirstLevelScore.setScale(0));
				
				BigDecimal totalScore = new BigDecimal(0);
				mav.addObject("totalScore", totalScore);
				
				mav.addObject("evaluateKpiTree", evaluateKpiTree);
			}  
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001); 
		}
		
		return mav;
	}
	
	public void setKpiDefaultScore( Node<EvaluateKpi> node ,List<KpiPersonEvaluateMapping> kpiDefaultScoreList) {
		 
			 for (Node<EvaluateKpi> eNode : node.getChildren()) {
				 
				 if(BeanUtils.isNotEmpty(kpiDefaultScoreList)){ 
					 for (KpiPersonEvaluateMapping kpiPersonEvaluateMapping : kpiDefaultScoreList) {
						
						 if(eNode.getData().getKpiId().equals(kpiPersonEvaluateMapping.getKpiId())){
							 if(eNode.getData().getDefaultScore()==null || eNode.getData().getDefaultScore().equals(new BigDecimal(0))){
								 
								 if(kpiPersonEvaluateMapping.getFlag_cal().compareTo(BigDecimal.ONE)==0){
									 eNode.getData().setDefaultScore(kpiPersonEvaluateMapping.getKpiMarkScore().multiply(new BigDecimal( kpiPersonEvaluateMapping.getCal_value())));
								 }else{
									 eNode.getData().setDefaultScore(kpiPersonEvaluateMapping.getKpiMarkScore());
								 }
								 
							 }else{
								 BigDecimal score = eNode.getData().getDefaultScore();
								 if(kpiPersonEvaluateMapping.getFlag_cal().compareTo(BigDecimal.ONE)==0){
									 score = score.add(kpiPersonEvaluateMapping.getKpiMarkScore().multiply(new BigDecimal( kpiPersonEvaluateMapping.getCal_value())));
								 }else{
									 score = score.add(kpiPersonEvaluateMapping.getKpiMarkScore());
								 }
								 eNode.getData().setDefaultScore(score);
							 }
						 }
					 }
				 }
			 
				 if(eNode.getData().getDefaultScore()==null){
					 eNode.getData().setDefaultScore(new BigDecimal(0).setScale(0, BigDecimal.ROUND_HALF_DOWN));
				 }
				 
				setKpiDefaultScore(eNode, kpiDefaultScoreList);
		 }
	 }
	
	
//	public void setKpiFirstLevelScore( Node<EvaluateKpi> node ,List<KpiPersonEvaluateMapping> kpiDefaultScoreList) {
//		for (Node<EvaluateKpi> eNode : node.getChildren()) {
//			BigDecimal firstScore = BigDecimal.ZERO;
//			for (Node<EvaluateKpi> secondNode : eNode.getChildren()) {
//				firstScore = firstScore.add(secondNode.getData().getDefaultScore());
//			}
//			BigDecimal weightFirstScore = firstScore.multiply(node.getData().getWeight());
//			node.getData().setDefaultScore(weightFirstScore.divide(new BigDecimal(100)).setScale(0));
//			
//			setKpiFirstLevelScore(eNode, kpiDefaultScoreList);
//			
//			totalFirstLevelScore = totalFirstLevelScore.add(weightFirstScore);
//		 }
//	}
	
	 
	public void setKpiSecondLevelScore( Node<EvaluateKpi> node ,List<KpiPersonEvaluateMapping> kpiDefaultScoreList) {
		totalSecondLevelScore = BigDecimal.ZERO;
		totalFirstLevelScore = BigDecimal.ZERO;
		 for (Node<EvaluateKpi> eNode : node.getChildren()) {  // Node Level 1	
			BigDecimal firstScore = BigDecimal.ZERO;
			for (Node<EvaluateKpi> secondNode : eNode.getChildren()) {  // Node Level 2	
				BigDecimal secondScore = BigDecimal.ZERO;
				
				for(Node<EvaluateKpi> thirdNode : secondNode.getChildren()){  // Node Level 3
					
					if(BeanUtils.isNotEmpty(thirdNode.getData().getDefaultScore())){
						secondScore = secondScore.add(thirdNode.getData().getDefaultScore());
					}
					
				}
				if(BeanUtils.isNotEmpty(secondNode.getData().getWeight())){
					BigDecimal weightSecoundScore = secondScore.multiply(secondNode.getData().getWeight());
					weightSecoundScore = weightSecoundScore.divide(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_DOWN);
					secondNode.getData().setSecondLevelScore(weightSecoundScore);
					firstScore = firstScore.add(weightSecoundScore);
					totalSecondLevelScore = totalSecondLevelScore.add(weightSecoundScore);
				}
			}
			if(BeanUtils.isNotEmpty(eNode.getData().getWeight())){
				BigDecimal weightFirstScore = firstScore.multiply(eNode.getData().getWeight());
				weightFirstScore = weightFirstScore.divide(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_DOWN);
				eNode.getData().setFirstLevelScore(weightFirstScore);
				totalFirstLevelScore = totalFirstLevelScore.add(weightFirstScore);
			}
		}
	}
	
	public BigDecimal getTotalDefaultScore(List<KpiPersonEvaluateMapping> kpiDefaultScoreList) {
		
		BigDecimal totalScore = new BigDecimal(0);
		
		if(BeanUtils.isNotEmpty(kpiDefaultScoreList)){
		
			for (KpiPersonEvaluateMapping kpiPersonEvaluateMapping : kpiDefaultScoreList) {
				
				if(kpiPersonEvaluateMapping.getFlag_cal()!= null && kpiPersonEvaluateMapping.getFlag_cal().compareTo(BigDecimal.ONE)==0){
					totalScore = totalScore.add(kpiPersonEvaluateMapping.getKpiMarkScore().multiply(new BigDecimal( kpiPersonEvaluateMapping.getCal_value())));
				}else{
					totalScore = totalScore.add(kpiPersonEvaluateMapping.getKpiMarkScore());
				}
			}
		}
		return totalScore;
				
	}
	
	/*
	 * This method for calculate markLevelScore, evaluateScore and weightScore in EvaluateForm
	 */
	public void calculateAllScore(Node<EvaluateKpi> rootNode) {
		
		// Node Level 1
		for (Node<EvaluateKpi> node : rootNode.getChildren()) {
			for (Node<EvaluateKpi> node2 : node.getChildren()) {
				// Calculate weightScore
				calculateWeightScore(node2);
			}
		}
		
	}
	
	public void calculateWeightScore(Node<EvaluateKpi> node2) {
		BigDecimal sumEvaluateScore = BigDecimal.ZERO;
		if (node2.getChildren().isEmpty()) {
			// Calculate markLevelScore
			calculateMarkLevelScore(node2);
			
			// Calculate evaluateScore
			calculateEvaluateScore(node2);
			
			// Sum evaluateScore
			sumEvaluateScore = sumEvaluateScore.add(node2.getData().getEvaluateScore());
		}
		else {
			for (Node<EvaluateKpi> node3 : node2.getChildren()) {
				if (node3.getChildren().isEmpty()) {
					// Calculate markLevelScore
					calculateMarkLevelScore(node3);
					
					// Calculate evaluateScore
					calculateEvaluateScore(node3);
					
					// Sum evaluateScore
					sumEvaluateScore = sumEvaluateScore.add(node3.getData().getEvaluateScore());
				}
			}
		}
		node2.getData().setWeightScore(sumEvaluateScore
			.multiply(node2.getData().getWeight())
			.divide(new BigDecimal("100"))
		);
	}
	
	public void calculateMarkLevelScore(Node<EvaluateKpi> node) {
		int markLevelScore = 0;
		for (MarkLevelDetail detail : node.getData().getMarkLevel().getMarkLevelDetailList()) {
			if (node.getData().getDefaultScore().compareTo(detail.getMark()) >= 0) {
				markLevelScore++;
			}
		}
		node.getData().setMarkLevelScore(new BigDecimal(markLevelScore));
	}
	
	public void calculateEvaluateScore(Node<EvaluateKpi> node) {
		node.getData().setEvaluateScore(node.getData().getWeight()
			.divide(new BigDecimal(node.getData().getMarkLevel().getMarkLevelDetailList().size()))
			.multiply(node.getData().getMarkLevelScore())
		);
	}
	
	
	@RequestMapping(value="historyEvaluate.htm", method = RequestMethod.GET)
	public ModelAndView historyEvaluate(HttpServletRequest httpRequest, @ModelAttribute PagingBean bean) {
		logger.info(" ###  Start searchEvaluate ### KB ### ");

		String yearId = httpRequest.getParameter("yearId");
		String semesterId = httpRequest.getParameter("semesterId");
		
		logger.info(" ###  yearId >> "+yearId);
		logger.info(" ###   semesterId >> "+semesterId);
		
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.EVALUATE_INIT);
		mav.setViewName("historyEvaluate"); 
		try{

			Year year = new Year();
			Semester semester = new Semester();
			
			BuckWaRequest request = new BuckWaRequest();
			
			String groupId = httpRequest.getParameter("groupId");
			String evaluateTerm = BuckWaConstants.EVALUATE_TERM_1; 
			
			BuckWaResponse response = new BuckWaResponse();
			response = yearService.getAll();
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				List<Year> yearList = (List)response.getResObj("yearList");
				mav.addObject("yearList", yearList);				
				response = new BuckWaResponse();
				request = new BuckWaRequest();
				if(BeanUtils.isEmpty(yearId)){
					if(BeanUtils.isNotEmpty(yearList.get(0).getYearId())){
						yearId = Long.toString(yearList.get(0).getYearId());
					}else {
						mav.addObject(BuckWaConstants.ERROR_CODE, response.getErrorCode()); 
					}
					
				}
					
				year.setYearId(Long.parseLong(yearId));
				request.put("yearId",yearId);
				response = semesterService.getByYearId(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){					
					List<Semester> semesterList= (List)response.getResObj("semesterList");		
					mav.addObject("semesterList", semesterList);
					
					if(null == semesterId){
						if(semesterList.get(0).getSemesterId() != null){
							semesterId = Long.toString(semesterList.get(0).getSemesterId());
						}else {
							semesterId = "1";
						}
					}
				}
				
				request.put("semesterId",semesterId);
				semester.setSemesterId(Long.parseLong(semesterId));
				if(null == groupId){
					groupId = BuckWaConstants.EVALUATE_TEACHER_ID;
				}
			}	
			
			// Set Evaluate Term And GroupId
			mav.addObject("groupId", groupId);
			mav.addObject("evaluateTerm", evaluateTerm);

			Evaluate evaluate =  new Evaluate();
			Person person = new Person();
			
			request = new BuckWaRequest();
			response = new BuckWaResponse();
			request.put("username", BuckWaUtils.getUserNameFromContext());
			response = personProfileService.getByUsername(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				person = (Person) response.getResObj("person");
				mav.addObject("person", person);
			}
			
			List<PersonEvaluateMapping> personEvaluateMappingList = new ArrayList<PersonEvaluateMapping>();
			
			Long userId = person.getUserId();
			request = new BuckWaRequest();
			
			// personId for who doing an evaluate
			request.put("userId", userId);
			response = personMappingEvaluateService.getUnderEstimateUserListByUserId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				List<EstimateUser> estimateUserList = (List<EstimateUser>)response.getResObj("estimateUserList");					
				
				// ### Start Get Estimate User Detail ###
				for (EstimateUser estimateUser : estimateUserList) {
					
					PersonEvaluateMapping eval = new PersonEvaluateMapping();
					eval.setPerson(estimateUser.getPerson()); 

					EvaluateKpiTree evaluateKpiTree = new EvaluateKpiTree();
					
					// KPI method 
					request.put("yearId", yearId); 
					request.put("groupId", groupId); 
					response =personMappingEvaluateService.getKPIEvaluateByYearAndGroupId(request) ;
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						evaluateKpiTree = (EvaluateKpiTree)response.getResObj("evaluateKpiTree");	
						rearangeOrder(evaluateKpiTree);
						
						List<Unit> unitList = null;
						response =unitService.getAll();
						if(response.getStatus()==BuckWaConstants.SUCCESS){					 
							unitList =(List<Unit>)response.getResObj("unitList");	
							httpRequest.getSession().setAttribute("unitList", unitList); 
						} 
		 
						setUnitandMarkType(evaluateKpiTree,unitList);
						
						// Get Default Score
						List<KpiPersonEvaluateMapping> kpiDefaultScoreList = new ArrayList<KpiPersonEvaluateMapping>();
						request.put("personId", estimateUser.getPerson().getPersonId().toString());
						request.put("evaluateYear", Integer.parseInt(yearId)); 
						request.put("evaluateTerm", Integer.parseInt(semesterId)); 
//						if(BeanUtils.isNotEmpty(
						response = personMappingEvaluateService.getKPIPersonEvaluateList(request);
						if(response.getStatus()==BuckWaConstants.SUCCESS){	
							kpiDefaultScoreList = (List<KpiPersonEvaluateMapping>) response.getResObj("kpiDefaultScoreList");	
							mav.addObject("kpiDefaultScoreList", kpiDefaultScoreList);
							
							// Set KPI Default Score
							setKpiDefaultScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
							
							// Set KPI Level 2 Score
							setKpiSecondLevelScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
							
							// Set KPI Level 1 Score
//							setKpiFirstLevelScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
						}		

						BigDecimal totalDefaultScore = getTotalDefaultScore(kpiDefaultScoreList);
						eval.setTotalScore(totalDefaultScore);
						eval.setTotalSecoundScore(totalSecondLevelScore.setScale(0));
						eval.setTotalFirstScore(totalFirstLevelScore.setScale(0));
					} 
					
					if(groupId.equals(BuckWaConstants.EVALUATE_TEACHER_ID)){						
						if(BuckWaConstants.EVALUATE_TYPE_TEACHER.contains(eval.getPerson().getEmployeeType())){
							personEvaluateMappingList.add(eval); // Add PersonMappingEvaluate if Type teacher
						}						
					}else{
						if(eval.getPerson().getEmployeeType().equals(BuckWaConstants.EVALUATE_TYPE_STAFF)){
							personEvaluateMappingList.add(eval);  // Add PersonMappingEvaluate if Type staff
						}
					}
				}	
				
				evaluate.setPersonEvaluateMappingList(personEvaluateMappingList);  // set for evaluate
				
			
			}else {
				logger.info("  Fail !!!! :"+response.getErrorCode()+" : "+response.getErrorDesc());
				mav.addObject("errorCode", response.getErrorCode()); 
			}	
			
			mav.addObject("evaluate", evaluate);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001); 
		}
		
		return mav;
	}	
		
	@RequestMapping(value="userHistoryEvaluate.htm", method = RequestMethod.GET)
	public ModelAndView userHistoryEvaluate(HttpServletRequest httpRequest,@ModelAttribute PagingBean bean) {
		logger.info(" ###  Start searchEvaluate ### KB ### ");
		logger.info(" Start  action "+httpRequest.getParameter("action"));
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.PERSON_INIT);
		mav.setViewName("userHistoryEvaluate"); 
		
		String yearId = httpRequest.getParameter("yearId");
		String semesterId = httpRequest.getParameter("semesterId");
		
		logger.info(" ###  yearId >> "+yearId);
		logger.info(" ###   semesterId >> "+semesterId);
		
		
		try{
			Evaluate evaluate =  new Evaluate();
			Person person = new Person();
			
			Year year = new Year();
			Semester semester = new Semester();
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("username", BuckWaUtils.getUserNameFromContext());
			BuckWaResponse response = personProfileService.getByUsername(request);
			
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				person = (Person) response.getResObj("person");
				mav.addObject("person", person);
			}
			
			String personId = person.getPersonId().toString();
			
			response = new BuckWaResponse();
			response = yearService.getAll();
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				
				List<Year> yearList = (List)response.getResObj("yearList");
				for (Year yearO : yearList) {
					yearO.setName(String.valueOf(Integer.parseInt(yearO.getName())+543));
				
				}
				mav.addObject("yearList", yearList);				
		
				response = new BuckWaResponse();
				request = new BuckWaRequest();
				
				if(null == yearId){
					if(yearList.get(0).getYearId() != null){
						yearId = Long.toString(yearList.get(0).getYearId());
					}else {
						mav.addObject(BuckWaConstants.ERROR_CODE, response.getErrorCode()); 
					}
					
				}
					
				year.setYearId(Long.parseLong(yearId));
				
				request.put("yearId",yearId);
				response = semesterService.getByYearId(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){					
					List<Semester> semesterList= (List)response.getResObj("semesterList");		
					mav.addObject("semesterList", semesterList);
					
					if(null == semesterId){
						if(semesterList.get(0).getSemesterId() != null){
							semesterId = Long.toString(semesterList.get(0).getSemesterId());
						}else {
							semesterId = "1";
						}
					}
					request.put("semesterId",semesterId);
					semester.setSemesterId(Long.parseLong(semesterId));
				}
			}
			
			String groupId = null;
//			String evaluateTerm = BuckWaConstants.EVALUATE_TERM_1;
			
			if(person.getEmployeeType().equals(BuckWaConstants.EVALUATE_TYPE_TEACHER)){
				groupId = BuckWaConstants.EVALUATE_TEACHER_ID;
			}else{
				groupId = BuckWaConstants.EVALUATE_STAFF_ID;
			}
			
			request.put("yearName", BuckWaDateUtils.getYear(BuckWaDateUtils.getCurrentDateTime()));
			request.put("groupId", groupId); 
			
			// Set Evaluate Term And GroupId
			mav.addObject("groupId", groupId);
//			mav.addObject("evaluateTerm", evaluateTerm);
			
			List<PersonEvaluateMapping> personEvaluateMappingList = new ArrayList<PersonEvaluateMapping>();
			
			PersonEvaluateMapping eval = new PersonEvaluateMapping();
			
			response = new BuckWaResponse();
			request = new BuckWaRequest();
			request.put("personId",personId);
			response = personProfileService.getByPersonId(request); // Get Person data
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				eval.setPerson((Person) response.getResObj("person")); // Add Person data
			}
				
			response = new BuckWaResponse();
			EvaluateKpiTree evaluateKpiTree = new EvaluateKpiTree();
			
			// KPI method 
			request.put("yearId", yearId); 
			request.put("groupId", groupId); 
			response =personMappingEvaluateService.getKPIEvaluateByYearAndGroupId(request) ;
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				evaluateKpiTree = (EvaluateKpiTree)response.getResObj("evaluateKpiTree");	
				rearangeOrder(evaluateKpiTree);
				
				List<Unit> unitList = null;
				response =unitService.getAll();
				if(response.getStatus()==BuckWaConstants.SUCCESS){					 
					unitList =(List<Unit>)response.getResObj("unitList");	
					httpRequest.getSession().setAttribute("unitList", unitList); 
				} 
 
				setUnitandMarkType(evaluateKpiTree,unitList);
				
				// Get Default Score
				List<KpiPersonEvaluateMapping> kpiDefaultScoreList = new ArrayList<KpiPersonEvaluateMapping>();
				request.put("personId",personId);
				request.put("evaluateYear",Integer.parseInt(yearId)); 
				request.put("evaluateTerm", Integer.parseInt(semesterId)); 
				response = personMappingEvaluateService.getKPIPersonEvaluateList(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					kpiDefaultScoreList = (List<KpiPersonEvaluateMapping>) response.getResObj("kpiDefaultScoreList");	
					mav.addObject("kpiDefaultScoreList", kpiDefaultScoreList);
					
					// Set KPI Default Score
					setKpiDefaultScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
					
					// Set KPI Level 2 Score
					setKpiSecondLevelScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
					
					// Set KPI Level 1 Score
//					setKpiFirstLevelScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
				}		

				BigDecimal totalDefaultScore = getTotalDefaultScore(kpiDefaultScoreList);
				mav.addObject("totalDefaultScore", totalDefaultScore);
				mav.addObject("totalSecondLevelScore", totalSecondLevelScore.setScale(0));
				mav.addObject("totalFirstLevelScore", totalFirstLevelScore.setScale(0));
				mav.addObject("evaluateKpiTree", evaluateKpiTree);
				
				eval.setTotalScore(totalDefaultScore);
				eval.setTotalSecoundScore(totalSecondLevelScore.setScale(0));
				eval.setTotalFirstScore(totalFirstLevelScore.setScale(0));
			}  
			
			
			personEvaluateMappingList.add(eval); 
				
			evaluate.setPersonEvaluateMappingList(personEvaluateMappingList);  // set for evaluate

			mav.addObject("evaluate", evaluate);
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001); 
		}
		
		return mav;
	}	
		
	@RequestMapping(value="updateKpiEvaluate.htm", method = RequestMethod.GET)
	public ModelAndView updateKpiEvaluate(@RequestParam("personId") String personId,HttpServletRequest httpRequest,@ModelAttribute PagingBean bean) {
		logger.info("  updateEvaluateStatus >> personId >> "+personId);
		
//		ModelAndView mav = new ModelAndView();		
//		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.EVALUATE_INIT);
//		mav.setViewName("initEvaluateForm");  
//		
//		PersonEvaluateMapping evaluateMapping = new PersonEvaluateMapping();
//		Person person = new Person();
//		
//		String yearId = Integer.toString(BuckWaDateUtils.getYearCurrent()); 
//		String semesterId = "1"; 
//		String evaluateStatus = BuckWaConstants.EVALUATE_PROCESS; 
//		String evaluateName = "\u0E41\u0E1A\u0E1A\u0E01\u0E32\u0E23\u0E1B\u0E23\u0E30\u0E40\u0E21\u0E34\u0E19\u0E1C\u0E25\u0E01\u0E32\u0E23\u0E1B\u0E0F\u0E34\u0E1A\u0E31\u0E15\u0E34\u0E23\u0E32\u0E0A\u0E01\u0E32\u0E23";// FIXME
//		String evaluateByPersonId = "9";  // Admin  default
//		
//		try{
//			
//			BuckWaRequest request = new BuckWaRequest();
//			request.put("username", BuckWaUtils.getUserNameFromContext());
//			BuckWaResponse response = personProfileService.getByUsername(request);
//			if (response.getStatus() == BuckWaConstants.SUCCESS) {
//				person = (Person) response.getResObj("person");
//				mav.addObject("person", person);
//				// Set Person Id who take Evaluate
//				evaluateByPersonId = Long.toString(person.getPersonId());
//			}
//			
//			person = new Person();
//			request = new BuckWaRequest();
//			response = new BuckWaResponse();
//			request.put("personId", personId);
//			response = personProfileService.getByPersonId(request);
//			if (response.getStatus() == BuckWaConstants.SUCCESS) {
//				person = (Person) response.getResObj("person");
//				evaluateMapping.setPerson(person);
//			}
//			
//			Year year = new  Year();
//			year.setYearId(Long.parseLong(yearId));
//			
////			Date todayDate = BuckWaDateUtils.getCurrentDateTime(); 
//			Semester semester = new Semester();
//			
//			request.put("yearId", yearId);
//			response = semesterService.getByYearId(request);
//			if(response.getStatus()==BuckWaConstants.SUCCESS){					
//				List<Semester> semesterList= (List)response.getResObj("semesterList");		
//				mav.addObject("semesterList", semesterList);
//				
//				for (Semester sem : semesterList) {
//					if(yearId.equals(sem.getYearId().toString())){
//						semesterId = sem.getSemesterId().toString();
//						request.put("semesterId",semesterId);
//						break;
//					}
//				}
//				
//			}
//			
//			semester.setSemesterId(Long.parseLong(semesterId));
//			
//			evaluateMapping.setSemester(semester);
//			evaluateMapping.setYear(year);
//			evaluateMapping.setEvaluateByPersonId(evaluateByPersonId);
//			evaluateMapping.setEvaluateName(evaluateName);
//			evaluateMapping.setEvaluateStatus(evaluateStatus);
//			
//			
//			request.put("personId", personId);
//			request.put("yearId", yearId);
//			request.put("semesterId",semesterId);
//			response = personMappingEvaluateService.getEvaluateStatusByPersonIdYearIdSemesterId(request);
//			if (response.getStatus() == BuckWaConstants.SUCCESS) {
//				// Change Evaluate Status 
//				evaluateStatus = (String) response.getResObj("evaluateStatus");
//			}
//			
//			// IF Evaluate Status ==> 'W'
//			if(evaluateStatus.equals(BuckWaConstants.EVALUATE_WAIT)){
//				
//				// Call Service for insert to table evaluate_person_mapping
//				Integer personEvaluateId = null;
//				request.put("evaluateMapping", evaluateMapping);
//				response = personMappingEvaluateService.createOrUpdatePersonEvaluate(request);
//				if (response.getStatus() == BuckWaConstants.SUCCESS) {
//					personEvaluateId = (Integer) response.getResObj("personEvaluateId");
//				}
//			
//			 		 
//				// FOR INSERT TO TABLE evaluate
//				EvaluateKpiTree evaluateKpiTree = new EvaluateKpiTree();
//				
//				request = new BuckWaRequest();
//				request.put("yearId", yearId);
//				
//				String groupId = httpRequest.getParameter("groupId");
//				if(null == groupId){
//					groupId=  BuckWaConstants.EVALUATE_TEACHER_ID;
//				}
//				
//				String evaluateTerm = httpRequest.getParameter("evaluateTerm");
//				if(null == evaluateTerm){
//					evaluateTerm=  BuckWaConstants.EVALUATE_TERM_1;
//				}
//				request.put("groupId", groupId); 
//				request.put("evaluateTerm", Integer.parseInt(evaluateTerm));
////				request.put("personId",personId.toString());
//				request.put("personId", person.getPersonId().toString());
//				request.put("personEvaluateId", personEvaluateId);
//				
//				response =personMappingEvaluateService.getKPIEvaluateByYearAndGroupId(request) ;
//				if(response.getStatus()==BuckWaConstants.SUCCESS){	
//					evaluateKpiTree = (EvaluateKpiTree)response.getResObj("evaluateKpiTree");	
//					rearangeOrder(evaluateKpiTree);
//					
//					List<Unit> unitList = null;
//					response =unitService.getAll();
//					if(response.getStatus()==BuckWaConstants.SUCCESS){					 
//						unitList =(List<Unit>)response.getResObj("unitList");	
//						httpRequest.getSession().setAttribute("unitList", unitList); 
//					} 
//	 
//					setUnitandMarkType(evaluateKpiTree,unitList);
//					
//					// Get Default Score
//					List<KpiPersonEvaluateMapping> kpiDefaultScoreList = new ArrayList<KpiPersonEvaluateMapping>();
//					response = personMappingEvaluateService.getKPIPersonEvaluateList(request);
//					if(response.getStatus()==BuckWaConstants.SUCCESS){	
//						kpiDefaultScoreList = (List<KpiPersonEvaluateMapping>) response.getResObj("kpiDefaultScoreList");	
//						mav.addObject("kpiDefaultScoreList", kpiDefaultScoreList);
//						
//						// Set KPI Default Score
//						setKpiDefaultScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
//						
//						// Set KPI Level 2 Score
//						setKpiSecondLevelScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
//						
//						// Set KPI Level 1 Score
////						setKpiFirstLevelScore(evaluateKpiTree.getRootElement(),kpiDefaultScoreList);
//					}		
//
//					BigDecimal totalDefaultScore = getTotalDefaultScore(kpiDefaultScoreList);
//					mav.addObject("totalDefaultScore", totalDefaultScore);
//					mav.addObject("totalSecondLevelScore", totalSecondLevelScore.setScale(0));
//					mav.addObject("totalFirstLevelScore", totalFirstLevelScore.setScale(0));
//					
//					request.put("evaluateKpiTree", evaluateKpiTree);
//					
//					// Call Service for insert to table evaluate_kpi_tree
//					personMappingEvaluateService.createEvaluateKpiMapping(request);
//					
//				}  
//			}
//			
//			//Redirect and ReloadData
//			return initEvaluateForm(personId , httpRequest, BuckWaConstants.ACTION_EDIT, bean);
//			
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
		return null;
		
	}	
	
	@RequestMapping(value="endEvaluateSession.htm", method = RequestMethod.GET)
	public ModelAndView endEvaluateSession(@RequestParam("personId") String personId,HttpServletRequest httpRequest,@ModelAttribute PagingBean bean) {
		logger.info("  endEvaluateSession >> personId >> "+personId);
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.EVALUATE_INIT);
		mav.setViewName("initEvaluateForm");  
		try{	
			BuckWaRequest request = new BuckWaRequest();
//			BuckWaResponse response = new BuckWaResponse();
//			request.put("personId", personId);
//			response = personProfileService.getByWorkLineCode(request);
//			if (response.getStatus() == BuckWaConstants.SUCCESS) {
//				Person person = (Person) response.getResObj("person");
//			}
			
//			if(BeanUtils.isNotEmpty(httpRequest.getParameter("termId"))){
//				request.put("personId", personId);
//				request.put("termId", httpRequest.getParameter("termId"));
//				personMappingEvaluateService.endEvaluateSession(request);
//				return initEvaluateForm(personId, httpRequest,  bean);
//			}
			
			
			PersonEvaluateMapping evaluateMapping = new PersonEvaluateMapping();
			Person person = new Person();
			
			String yearId = Integer.toString(BuckWaDateUtils.getYearCurrent()); 
			String semesterId = "1"; 
			String evaluateStatus = BuckWaConstants.EVALUATE_PROCESS; 
			String evaluateName = "\u0E41\u0E1A\u0E1A\u0E01\u0E32\u0E23\u0E1B\u0E23\u0E30\u0E40\u0E21\u0E34\u0E19\u0E1C\u0E25\u0E01\u0E32\u0E23\u0E1B\u0E0F\u0E34\u0E1A\u0E31\u0E15\u0E34\u0E23\u0E32\u0E0A\u0E01\u0E32\u0E23";// FIXME
			String evaluateByPersonId = "9";  // Admin  default
			
			request.put("username", BuckWaUtils.getUserNameFromContext());
			BuckWaResponse response = personProfileService.getByUsername(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				person = (Person) response.getResObj("person");
				mav.addObject("person", person);
				// Set Person Id who take Evaluate
				evaluateByPersonId = Long.toString(person.getPersonId());
			}
			
			person = new Person();
//			request = new BuckWaRequest();
//			response = new BuckWaResponse();
//			request.put("personId", personId);
//			response = personProfileService.getByPersonId(request);
//			if (response.getStatus() == BuckWaConstants.SUCCESS) {
//				person = (Person) response.getResObj("person");
			person.setPersonId(Long.parseLong(personId));
				evaluateMapping.setPerson(person);
//			}
			
			Semester semester = new Semester();
			if(BeanUtils.isNotEmpty(httpRequest.getParameter("evaluateTerm"))){
				semester.setSemesterId(Long.parseLong(httpRequest.getParameter("evaluateTerm")));
			}else{
				semester.setSemesterId(Long.parseLong(semesterId));
			}
			
			Year year = new  Year();
			year.setYearId(Long.parseLong(yearId));
			
			evaluateMapping.setSemester(semester);
			evaluateMapping.setYear(year);
			evaluateMapping.setEvaluateByPersonId(evaluateByPersonId);
			evaluateMapping.setEvaluateName(evaluateName);
			evaluateMapping.setEvaluateStatus(evaluateStatus);
			
//			Call Service for insert to table evaluate_person_mapping
			Integer personEvaluateId = null;
			request.put("evaluateMapping", evaluateMapping);
			response = personMappingEvaluateService.createOrUpdatePersonEvaluate(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				personEvaluateId = (Integer) response.getResObj("personEvaluateId");
			}
			
			//Redirect and ReloadData
			return initEvaluateForm(personId, httpRequest, bean,  "S");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
		
	}	
 
	public void rearangeOrder(EvaluateKpiTree evaluateKpiTree ) {
		try{
        List<Node<EvaluateKpi>> tasks = evaluateKpiTree.toList();
        // save the tree in reverse order, starting from the leaf nodes
        // and going up to the root of the tree.
        int numberOfNodes = tasks.size();
        for (int i = numberOfNodes - 1; i >= 0; i--) {
            Node<EvaluateKpi> taskElement = tasks.get(i);
            Kpi task = taskElement.getData();
           // saveOrUpdate(task);
            Long parentId = task.getKpiId();
            int childOrder=1;
            for (Iterator<Node<EvaluateKpi>> it = taskElement.getChildren().iterator(); it.hasNext();) {
                Node<EvaluateKpi> childElement = it.next();
                Kpi childTask = childElement.getData();
                logger.info(" ######## saveOrUpdate childTask:"+childTask.getKpiId());
                childTask.setChildOrder(childOrder);
                childTask.setParentId(parentId);
              //  taskDao.saveOrUpdate(childTask);
                
                childOrder++;
            }
        }
		}catch(Exception ex){
			ex.printStackTrace();
		}
    }
	
	public void setUnitandMarkType(EvaluateKpiTree evaluateKpiTree,List<Unit> unitList) {
        List<Node<EvaluateKpi>> tasks = evaluateKpiTree.toList();	      
        int numberOfNodes = tasks.size();
        for (int i = numberOfNodes - 1; i >= 0; i--) {
            Node<EvaluateKpi> taskElement = tasks.get(i);
            Kpi task = taskElement.getData(); 
           // Long parentId = task.getKpiId();
           // int childOrder=1;
            for (Iterator<Node<EvaluateKpi>> it = taskElement.getChildren().iterator(); it.hasNext();) {
                Node<EvaluateKpi> childElement = it.next();
               int numberofchild =  childElement.getNumberOfChildren();
                Kpi childTask = childElement.getData(); 
                String markType =childTask.getMarkType();
                
                if(numberofchild>0){
                	
                	childTask.setMarkTypeDesc("");
                }else{
                if(PAMConstants.MARK_TYPE_0.equals(markType)){
                	childTask.setMarkTypeDesc("\u0e04\u0e30\u0e41\u0e19\u0e19");
                }else if(PAMConstants.MARK_TYPE_1.equals(markType)) {
	                Long unitId =childTask.getUnitId();
	                if(unitId!=null){
	                	for(Unit unittmp :unitList){
	                		Long unitIdtmp = unittmp.getUnitId();
	                		if(unitId.intValue()==unitIdtmp.intValue()){
	                			childTask.setMarkTypeDesc("\u0e04\u0e30\u0e41\u0e19\u0e19/"+unittmp.getName());
	                			break;
	                		}
	                	}		                	
	                }
                }else{
                	childTask.setMarkTypeDesc("N/A");
                }
                }
            }
        }
    }
	
	@RequestMapping(value="updateKpiEstimateScore.htm", method = RequestMethod.GET)
	public ModelAndView updateKpiEstimateScore(@RequestParam("personId") String personId,HttpServletRequest httpRequest,@ModelAttribute PagingBean bean) {
		
		String evaluateKpiId = httpRequest.getParameter("evaluateKpiId");
		String estimateScore = httpRequest.getParameter("estimateScore");
		
		logger.info("  updateKpiEstimateScore >> personId >> "+personId);
		logger.info("  updateKpiEstimateScore >> Evaluate Tree ID >> "+evaluateKpiId);
		logger.info("  updateKpiEstimateScore >> Estimate Score >> "+estimateScore);
		
//		try{	
//			BuckWaRequest request = new BuckWaRequest();
//			BuckWaResponse response = new BuckWaResponse();
//			request.put("evaluateKpiId", evaluateKpiId);
//			request.put("estimateScore", new BigDecimal(estimateScore.trim()));
//			response = personMappingEvaluateService.updateEvaluateKpiEstimateScore(request);
//			if (response.getStatus() == BuckWaConstants.SUCCESS) {
////				personMappingEvaluateService.endEvaluateSession(request);
//			}
//			
//			//Redirect and ReloadData
//			return initEvaluateForm(personId, httpRequest, BuckWaConstants.ACTION_EDIT, bean);
//		
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
		return null;
		
	}
	
	
}
