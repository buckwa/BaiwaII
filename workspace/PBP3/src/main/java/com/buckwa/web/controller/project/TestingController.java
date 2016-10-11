package com.buckwa.web.controller.project;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Module;
import com.buckwa.domain.project.Project;
import com.buckwa.domain.project.TestCase;
import com.buckwa.domain.project.TestWrapper;
import com.buckwa.domain.project.UseCase;
import com.buckwa.domain.validator.project.TestCaseValidator;
import com.buckwa.domain.validator.webboard.ReplyMessageValidator;
import com.buckwa.domain.webboard.Message;
import com.buckwa.domain.webboard.Topic;
import com.buckwa.service.intf.project.ModuleService;
import com.buckwa.service.intf.project.TestCaseService;
import com.buckwa.service.intf.webboard.WebboardTopicService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.project.ProjectConstant;
import com.buckwa.util.project.ProjectUtil;
import com.buckwa.web.controller.admin.RoleManagementController;

@Controller
@RequestMapping("/project/testing")
@SessionAttributes(types = TestCase.class)
public class TestingController {   
	private static Logger logger = LoggerFactory.getLogger(TestingController.class);
	@Autowired
	private ModuleService moduleService; 
	
	@Autowired
	private TestCaseService testCaseService;
	
	@Autowired
	private ProjectUtil projectUtil; 
	
	@Autowired
	private WebboardTopicService  webboardTopicService; 
	
	@RequestMapping(value = "init.htm", method = RequestMethod.GET)
	public ModelAndView inig(@RequestParam("moduleId") String moduleId,HttpServletRequest httpRequest ) {
		logger.info(" ######## TestingController  init moduleId :"+moduleId);
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initTesting");	 
		try { 
			BuckWaRequest request = new BuckWaRequest();
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			request.put("moduleId", moduleId+"");
			
			/*
			 BuckWaResponse response = testCaseService.getAllByModuleId(request); 
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 List<TestCase>   testcaseList = (List<TestCase>)response.getResObj("testcaseList"); 
				 mav.addObject("testcaseList", testcaseList);
			} 
			*/
			
			BuckWaResponse response = testCaseService.getAllWrapByModuleId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 List<TestWrapper>   testWrapperList = (List<TestWrapper>)response.getResObj("testWrapperList"); 
				 logger.info(" ######## TestingController  found:"+testWrapperList);
				 mav.addObject("testWrapperList", testWrapperList);
			}
 
			response = moduleService.getAllModuleByProjectIdCountTestcase(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				List<Module>  returnList = (List)response.getResObj("moduleList"); 
				mav.addObject("moduleList", returnList);	
				mav.addObject("totalCount", (Long)response.getResObj("totalCount"));
				httpRequest.getSession().setAttribute("moduleList", returnList); 
			} 			 
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	} 
	
	@RequestMapping(value = "initAll.htm", method = RequestMethod.GET)
	public ModelAndView initAll( HttpServletRequest httpRequest ) {
		
		logger.info(" ######## TestingController  initAll ##### ");
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initAllTesting"); 
		try {  
			BuckWaRequest request = new BuckWaRequest();	
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			BuckWaResponse response = testCaseService.getAllByProjectId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 List<TestWrapper>   testWrapperList = (List<TestWrapper>)response.getResObj("testWrapperList"); 
				 logger.info(" ######## TestingController  found:"+testWrapperList);
				 mav.addObject("testWrapperList", testWrapperList);
			}
			response = moduleService.getAllModuleByProjectIdCountTestcase(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				List<Module>  returnList = (List)response.getResObj("moduleList"); 
				mav.addObject("moduleList", returnList);
				mav.addObject("totalCount", (Long)response.getResObj("totalCount")); 
				httpRequest.getSession().setAttribute("moduleList", returnList);
 
			} 
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}
	 
	
	
	@RequestMapping(value = "initByModule.htm", method = RequestMethod.GET)
	public ModelAndView initByModule(@RequestParam("moduleId") String moduleId,HttpServletRequest httpRequest ) {
		logger.info(" ######## TestingController  initByModule moduleId :"+moduleId);
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initTestingByModule");	 
		try { 
			BuckWaRequest request = new BuckWaRequest();
			request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
			request.put("moduleId", moduleId+"");
			BuckWaResponse response = moduleService.getModuleandTestCaseById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 Module   moduleReturn = (Module)response.getResObj("module"); 
				 mav.addObject("module", moduleReturn);
			} 
 
			 response = moduleService.getAllByProjectId(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){				
				List<Module>  returnList = (List)response.getResObj("moduleList"); 
				mav.addObject("moduleList", returnList);	
				httpRequest.getSession().setAttribute("moduleList", returnList); 
			} 			 
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	} 
	
	@RequestMapping(value = "viewTestCase.htm", method = RequestMethod.GET)
	public ModelAndView viewTestCase(@RequestParam("testcaseId") String testcaseId,HttpServletRequest httpRequest ) {
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("viewTestCase");	 
		try { 
			BuckWaRequest request = new BuckWaRequest();
			request.put("testcaseId", testcaseId);
			BuckWaResponse response = testCaseService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){
				Project returnProject = (Project)response.getResObj("project"); 
				mav.addObject("project", returnProject);
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	} 
 
	
	@RequestMapping(value="createByDetailDesign.htm", method = RequestMethod.GET)
	public ModelAndView createByDetailDesign(HttpServletRequest httpRequest,@RequestParam("moduleId") String moduleId,@RequestParam("detailDesignId") String detailDesignId) {
		ModelAndView mav = new ModelAndView();
		
		logger.info("  ## In createByDetailDesign() :"+moduleId);
		mav.setViewName("testcaseCreate");
		try{					 
			 BuckWaRequest request = new BuckWaRequest();
			 request.put("moduleId", moduleId+"");
			 httpRequest.getSession().setAttribute("moduleId", moduleId);
			 httpRequest.getSession().setAttribute("detailDesignId", detailDesignId);
			 request.put("detailDesignId", detailDesignId+""); 
			 BuckWaResponse response = testCaseService.prepareTestCaseByDetailDesign (request);
			 if(response.getStatus()==BuckWaConstants.SUCCESS){				
				 TestCase   testcase = (TestCase)response.getResObj("testcase"); 
				 mav.addObject("testcase", testcase);
			 } 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createPOST(@ModelAttribute TestCase testcase
			, BindingResult result,HttpServletRequest httpRequest) {	
		ModelAndView mav = new ModelAndView();
		logger.info("  ## In   testcase:"+BeanUtils.getBeanString(testcase));
		try{			
			new TestCaseValidator().validate(testcase, result);			
			if (result.hasErrors()) {				
				mav.setViewName("testcaseCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();			
				testcase.setProjectId(projectUtil.getProjectIdFromSession(httpRequest)); 
				testcase.setModuleId(projectUtil.getModuleIdFromSession(httpRequest));
				testcase.setDetailDesignId(projectUtil.getDetailDesignIdFromSession(httpRequest));
				request.put("testcase", testcase);
				BuckWaResponse response = testCaseService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
					request.put("projectId", projectUtil.getProjectIdFromSession(httpRequest));
					request.put("moduleId", testcase.getModuleId()+"");
					 response = testCaseService.getAllByModuleId(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){				
						 testcase = (TestCase)response.getResObj("testcase"); 
						 mav.addObject("testcase", testcase);
					} 
					mav.setViewName("initTesting");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("testcaseCreate");
				}				
			}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	
	@RequestMapping(value="detail.htm", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam("testCaseId") String testCaseId,HttpServletRequest httpRequest ) {
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("testCaseDetail");
		BuckWaRequest request = new BuckWaRequest();
		request.put("testCaseId", testCaseId);	
		httpRequest.getSession().setAttribute("testCaseId", testCaseId);
		BuckWaResponse response = testCaseService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
		 TestCase testCase = (TestCase)response.getResObj("testCase");				 	
			mav.addObject("testCase", testCase);				
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}	 
		 
		request.put("artifaceId", testCaseId);
		request.put("artifaceType", ProjectConstant.ARTIFACE_TYPE_TESTCASE);	 
		 response = webboardTopicService.getTopicByArtifaceId(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Topic topic  = (Topic)response.getResObj("topic");				 	
			mav.addObject("topic", topic);	
			httpRequest.getSession().setAttribute("topicId", topic.getTopicId());
			
		}else {	
			mav.addObject("errorCode", response.getErrorCode()); 
		}
  
		return mav;
	}	
	
	
	
	@RequestMapping(value="replyMessage.htm", method = RequestMethod.POST)
	public ModelAndView replyMessage(@ModelAttribute Topic topic, BindingResult result,HttpServletRequest httpRequest ) {	
		ModelAndView mav = new ModelAndView();
		try{			
			logger.info(" TestingController  replyMessage topic : "+BeanUtils.getBeanString(topic));
			BuckWaRequest request = new BuckWaRequest();
			new ReplyMessageValidator().validate(topic, result);			
			if (result.hasErrors()) {				
				String artifaceId = (String)httpRequest.getSession().getAttribute("testCaseId");
				request.put("artifaceId", artifaceId);
				request.put("artifaceType", ProjectConstant.ARTIFACE_TYPE_TESTCASE);	 
				BuckWaResponse response  = webboardTopicService.getTopicByArtifaceId(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
					 topic  = (Topic)response.getResObj("topic");				 	
					mav.addObject("topic", topic);	
					httpRequest.getSession().setAttribute("topicId", topic.getTopicId());
					
				}else {	
					mav.addObject("errorCode", response.getErrorCode()); 
				}
				mav.setViewName("testCaseDetail");
			}else {					
			
				topic.setStatus("1");
				Long topicId = (Long)httpRequest.getSession().getAttribute("topicId");
				Message newMessage = new Message();
				newMessage.setMessageDetail(topic.getReplyMessage());
				newMessage.setTopicId(topicId);
				newMessage.setStatus("1");				
				newMessage.setCreateBy(BuckWaUtils.getUserNameFromContext());
				request.put("message", newMessage);
				logger.info(" replyMessage newMessage:"+BeanUtils.getBeanString(newMessage));
				BuckWaResponse response = webboardTopicService.replyMessage(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
					topic = (Topic)response.getResObj("topic");
					mav.addObject("topic", topic);
					topic.setReplyMessage("");
					mav.addObject("successCode", response.getSuccessCode()); 
					mav.setViewName("testCaseDetail");						
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					mav.setViewName("testCaseDetail");
				} 
			} 

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
}
