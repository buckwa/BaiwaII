package com.buckwa.web.controller.pam.admin;

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
import com.buckwa.domain.pam.KpiCategory;
import com.buckwa.domain.pam.KpiPersonEvaluateMapping;
import com.buckwa.domain.pam.KpiSchedule;
import com.buckwa.domain.pam.KpiYearMapping;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pam.PersonEvaluateMapping;
import com.buckwa.domain.pam.Semester;
import com.buckwa.domain.pam.Unit;
import com.buckwa.domain.pam.Year;
import com.buckwa.domain.pam.nodetree.EvaluateKpi;
import com.buckwa.domain.pam.nodetree.EvaluateKpiTree;
import com.buckwa.domain.pam.nodetree.Kpi;
import com.buckwa.domain.pam.nodetree.KpiTemplate;
import com.buckwa.domain.pam.nodetree.KpiTree;
import com.buckwa.domain.pam.nodetree.Node;
import com.buckwa.service.intf.pam.KpiCategoryService;
import com.buckwa.service.intf.pam.KpiScheduleService;
import com.buckwa.service.intf.pam.KpiTreeService;
import com.buckwa.service.intf.pam.KpiYearMappingService;
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
@RequestMapping("/admin/manageTemplate")
@SessionAttributes(types = Evaluate.class)
public class ManageTemplateController {	
	private static Logger logger = LoggerFactory.getLogger(ManageTemplateController.class);
	
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
	
	@Autowired
	private KpiCategoryService kpiCategoryService;

	@Autowired
	private KpiYearMappingService kpiYearMappingService;
	@Autowired
	private KpiTreeService kpiTreeService;
	
	private BigDecimal totalFirstLevelScore = BigDecimal.ZERO;
	private BigDecimal totalSecondLevelScore  = BigDecimal.ZERO;
	//private Integer YEAR_ID  = 2012;
	 
 
	
	@RequestMapping(value = "initSearch.htm", method = RequestMethod.GET)
	public ModelAndView initSearch(HttpServletRequest httpRequest) {
		logger.info(" Start initSearch() ");
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();

		BuckWaRequest request = new BuckWaRequest();
		mav.addObject("pagingBean", bean);
		mav.setViewName("manageTemplatekpiyearList");

		KpiYearMapping kpiYearMapping = new KpiYearMapping();
		mav.addObject("kpiYearMapping", kpiYearMapping);
		try {
			BuckWaResponse response = yearService.getAll();
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				List<Year> yearList = (List<Year>) response.getResObj("yearList");

				if (yearList != null && yearList.size() > 0) {
					mav.addObject("yearList", yearList);
					httpRequest.getSession().setAttribute("yearList", yearList);
					response = kpiCategoryService.getAll();
					if (response.getStatus() == BuckWaConstants.SUCCESS) {
						List<KpiCategory> kpiCategoryList = (List<KpiCategory>) response.getResObj("kpiCategoryList");
						if (kpiCategoryList != null && kpiCategoryList.size() > 0) {
							mav.addObject("kpiCategoryList", kpiCategoryList);
							httpRequest.getSession().setAttribute("kpiCategoryList", kpiCategoryList);

							// Create New if not Exist
						}

					}

				}
			}

			// Validate and check is Tree Create belong year,category
			List<Unit> unitList = null;
			response = unitService.getAll();
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				unitList = (List<Unit>) response.getResObj("unitList");
				httpRequest.getSession().setAttribute("unitList", unitList);
			}

			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);
			bean.setOffset(offset);

			request = new BuckWaRequest();
			mav.addObject("kpiYearMapping", new KpiYearMapping());
			request.put("pagingBean", bean);
			bean.put("kpiYearMapping", new KpiYearMapping());
			response = kpiYearMappingService.getByOffset(request);

			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean) response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);
			} else {
				mav.addObject("errorCode", response.getErrorCode());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	
	@RequestMapping(value = "search.htm", method = RequestMethod.GET)
	public ModelAndView searchGet(HttpServletRequest httpRequest, @ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("manageTemplatekpiyearList");
		try {
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);
			bean.setOffset(offset);
			// logger.info(" PagingBean GET:"+BeanUtils.getBeanString(bean));
			BuckWaRequest request = new BuckWaRequest();
			mav.addObject("kpiYearMapping", new KpiYearMapping());
			request.put("pagingBean", bean);
			bean.put("kpiYearMapping", new KpiYearMapping());
			BuckWaResponse response = kpiYearMappingService.getByOffset(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				logger.info(" Success ");
				PagingBean beanReturn = (PagingBean) response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);
			} else {
				mav.addObject("errorCode", response.getErrorCode());
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			mav.addObject("errorCode", "E001");
		}
		return mav;
	}

	@RequestMapping(value = "search.htm", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest httpRequest, @ModelAttribute KpiYearMapping kpiYearMapping, @ModelAttribute PagingBean bean) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("manageTemplatekpiyearList");
		try {
			int offset = ServletRequestUtils.getIntParameter(httpRequest, "pager.offset", 0);
			bean.setOffset(offset);
			BuckWaRequest request = new BuckWaRequest();
			request.put("pagingBean", bean);
			bean.put("kpiYearMapping", kpiYearMapping);
			BuckWaResponse response = kpiYearMappingService.getByOffset(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				PagingBean beanReturn = (PagingBean) response.getResObj("pagingBean");
				bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
				bean.setTotalItems(beanReturn.getTotalItems());
				mav.addObject("pagingBean", bean);
				mav.addObject("doSearch", "true");
			} else {
				mav.addObject("errorCode", response.getErrorCode());
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			mav.addObject("errorCode", "E001");
		}
		return mav;
	}

	
	
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initManage(	HttpServletRequest httpRequest,@RequestParam("yearId") String yearId ,@RequestParam("groupId") String groupId) {
		
		logger.info(" ###  Start init initManage  List ### KB ### ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.EVALUATE_INIT);
	 
		
		mav.setViewName("initManageTemplate");  
		
		if(BeanUtils.isNotEmpty(httpRequest.getParameter("evaluateStatus"))){
			mav.addObject("evaluateStatus", httpRequest.getParameter("evaluateStatus"));
		} 
		
		try{
			BuckWaRequest request = new BuckWaRequest();
			BuckWaResponse response = new BuckWaResponse();
			KpiTemplate kpi = new KpiTemplate();
			
			yearId = yearId==null?(String)httpRequest.getSession().getAttribute("yearId"):yearId;
			groupId = groupId==null?(String)httpRequest.getSession().getAttribute("groupId"):groupId;
	 
			logger.info(" ######### yearId:"+yearId+" goupId:"+groupId);
			
			httpRequest.getSession().setAttribute("yearId", yearId);
			httpRequest.getSession().setAttribute("groupId", groupId);
			kpi.setYearId(new Long(yearId));
			request.put("yearId", yearId);
			request.put("groupId", groupId);
			mav.addObject("kpi", kpi);
			response = kpiTreeService.getTemplateByYearAndGroupId(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
				KpiTree kpiTree = (KpiTree) response.getResObj("kpiTree");
			 
				mav.addObject("kpiTree", kpiTree);
			}
			
			 
			
			Evaluate evaluate =  new Evaluate();
			evaluate.setYearName(yearId);
			Person person = new Person();
			
			mav.addObject("evaluate", evaluate);
			
 
			
	 
	 
			 
			request.put("yearId", yearId); 
			
			 
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
			mav.addObject("yearName", yearId); 
			
			request.put("yearName", yearId);
			
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
			request.put("yearId",yearId); 
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
				
	
				
				mav.addObject("evaluateKpiTree", evaluateKpiTree);
			}  
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject(BuckWaConstants.ERROR_CODE, BuckWaConstants.ERROR_E001); 
		}
		
		return mav;
	}
	
	@RequestMapping(value="updateWeight.htm", method = RequestMethod.GET)
	public ModelAndView updateWeight(HttpServletRequest httpRequest,@ModelAttribute PagingBean bean) {
		
		String evaluateKpiId = httpRequest.getParameter("evaluateKpiId");
		String weight = httpRequest.getParameter("weight");
		//String yearId =httpRequest.getParameter("yearId");
		//String groupId =httpRequest.getParameter("groupId");
		
		 
		String yearId = (String)httpRequest.getSession().getAttribute("yearId");
		String groupId  = (String)httpRequest.getSession().getAttribute("groupId");
		
		logger.info("updateWeight  evaluateKpiId: "+evaluateKpiId);
		logger.info("updateWeight  weight : "+weight);
		logger.info("updateWeight  yearId : "+yearId);
		logger.info("updateWeight  groupId : "+groupId);
		
		try{	
			BuckWaRequest request = new BuckWaRequest();
			BuckWaResponse response = new BuckWaResponse();
			request.put("kpiId", evaluateKpiId);
			request.put("weight",  weight );		 
			response = kpiTreeService.updateWeight(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {

			}
 
			return initManage(httpRequest,yearId,groupId);
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
		
	}
	@RequestMapping(value="updateTarget.htm", method = RequestMethod.GET)
	public ModelAndView updateTarget(HttpServletRequest httpRequest,@ModelAttribute PagingBean bean) {
		
		String evaluateKpiId = httpRequest.getParameter("evaluateKpiId");
		String weight = httpRequest.getParameter("weight");
 
		
		 
		String yearId = (String)httpRequest.getSession().getAttribute("yearId");
		String groupId  = (String)httpRequest.getSession().getAttribute("groupId");
		
		logger.info("updateTarget  evaluateKpiId: "+evaluateKpiId);
		logger.info("updateTarget  weight : "+weight);
		logger.info("updateTarget  yearId : "+yearId);
		logger.info("updateTarget  groupId : "+groupId);
		
		try{	
			BuckWaRequest request = new BuckWaRequest();
			BuckWaResponse response = new BuckWaResponse();
			request.put("kpiId", evaluateKpiId);
			request.put("weight",  weight );
 
			response = kpiTreeService.updateTarget(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
//				personMappingEvaluateService.endEvaluateSession(request);
			}
			
			return initManage(httpRequest,yearId,groupId);
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
		
	}	
	@RequestMapping(value="updateMarkLevel.htm", method = RequestMethod.GET)
	public ModelAndView updateMarkLevel(HttpServletRequest httpRequest,@ModelAttribute PagingBean bean) {
		
		String levelId = httpRequest.getParameter("levelId");
		String mark = httpRequest.getParameter("mark");
		String level = httpRequest.getParameter("level");
		
		logger.info(" ## updateMarkLevel levelId:"+levelId+" mark:"+mark+" level:"+level);
	 
		 
		String yearId = (String)httpRequest.getSession().getAttribute("yearId");
		String groupId  = (String)httpRequest.getSession().getAttribute("groupId");
		
 
		
		try{	
			BuckWaRequest request = new BuckWaRequest();
			BuckWaResponse response = new BuckWaResponse();
			request.put("levelId", levelId);
			request.put("mark",  mark );
			request.put("level",  level );
	 
			response = kpiTreeService.updateLevel(request);
			if (response.getStatus() == BuckWaConstants.SUCCESS) {
//			 
			}
			
 
			return initManage(httpRequest,yearId,groupId);
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
		
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
	
 
	
	
}
