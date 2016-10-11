package com.buckwa.web.controller.pbp;

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
import com.buckwa.domain.pbp.AcademicYear;
import com.buckwa.domain.pbp.AcademicYearEvaluateRound;
import com.buckwa.domain.pbp.AcademicYearWrapper;
import com.buckwa.domain.validator.pbp.AcademicYearDateEditValidator;
import com.buckwa.domain.validator.pbp.AcademicYearEvaluateRoundEditValidator;
import com.buckwa.service.intf.pbp.AcademicYearService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.web.util.AcademicYearUtil;

@Controller
@RequestMapping("/admin/pbp/academicYear")
@SessionAttributes({"academicYearWrapper","academicYear","academicYearEvaluateRound"} ) 
public class AcademicYearController {	
	private static Logger logger = LoggerFactory.getLogger(AcademicYearController.class);	 
	@Autowired
	private AcademicYearService academicYearService;	
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicYear");
		try{
			BuckWaRequest request = new BuckWaRequest();	 
			BuckWaResponse response = academicYearService.getFullAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicYearWrapper academicYearWrapper = (AcademicYearWrapper)response.getResObj("academicYearWrapper");
				mav.addObject("academicYearWrapper", academicYearWrapper);	
			}
			
			mav.addObject("academicYearList", academicYearUtil.getAcademicYearList()); 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	
	@RequestMapping(value="listByAcademicYear.htm", method = RequestMethod.GET)
	private ModelAndView listByAcademicYear(@RequestParam("academicYear") String academicYear,@RequestParam("next") String next) { 
		ModelAndView mav = new ModelAndView();
		logger.info(" ## listByAcademicYear:"+academicYear+" next:"+next);
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("academicYear");  
		try{
			BuckWaRequest request = new BuckWaRequest();
			request.put("academicYear", academicYear);
			BuckWaResponse response = academicYearService.getByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				AcademicYearWrapper academicYearWrapper = (AcademicYearWrapper)response.getResObj("academicYearWrapper");
				mav.addObject("academicYear", academicYearWrapper.getAcademicYear().getName());
				mav.addObject("academicYearWrapper", academicYearWrapper);				
			}else {	 
				mav.addObject("errorCode", response.getErrorCode()); 
			}				
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
			
		return mav;
	}	
	
	
	@RequestMapping(value="ajustYear.htm", method = RequestMethod.GET)
	public ModelAndView studentAjustYear() {
		logger.info(" Start  ");
	 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicAjustYear");
		try{
			BuckWaRequest request = new BuckWaRequest();	 
			BuckWaResponse response = academicYearService.getCurrentAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicYearWrapper academicYearWrapper = (AcademicYearWrapper)response.getResObj("academicYearWrapper");
				mav.addObject("academicYear", academicYearWrapper.getAcademicYear().getName());	
				mav.addObject("academicYearWrapper", academicYearWrapper);	
			}			
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="getCurrentAcademicYear.htm", method = RequestMethod.GET)
	public ModelAndView getCurrentAcademicYear() {
		logger.info(" Start  ");
	 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicYear");
		try{
			BuckWaRequest request = new BuckWaRequest();	 
			BuckWaResponse response = academicYearService.getCurrentAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicYearWrapper academicYearWrapper = (AcademicYearWrapper)response.getResObj("academicYearWrapper");
				mav.addObject("academicYear", academicYearWrapper.getAcademicYear().getName());	
				mav.addObject("academicYearWrapper", academicYearWrapper);	
			}				  
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
 
	
	@RequestMapping(value="ajustYearIncrease.htm", method = RequestMethod.POST)
	public ModelAndView studentAjustYearDecrease() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicAjustYearSuccess");
		try{
			mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
			BuckWaRequest request = new BuckWaRequest();
			BuckWaResponse response = academicYearService.ajustYearIncrease(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				AcademicYearWrapper academicYearWrapper = (AcademicYearWrapper)response.getResObj("academicYearWrapper");	
				mav.addObject("academicYear", academicYearWrapper.getAcademicYear().getName());
				mav.addObject("academicYearWrapper", academicYearWrapper);				
			}	else{
				 
					mav.addObject("errorCode", "E021"); 
				}	
		
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	 
	
	
	
	
	
	@RequestMapping(value="editDateAcademicYear.htm", method = RequestMethod.GET)
	public ModelAndView editDateAcademicYear(@RequestParam("academicYear") String academicYear ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dateAcademicYearEdit");
		try{
		 
			BuckWaRequest request = new BuckWaRequest();
			request.put("academicYear", academicYear);
			BuckWaResponse response = academicYearService.getByYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				AcademicYear academicYearReturn = (AcademicYear)response.getResObj("academicYear");
				logger.info(" academicYearReturn: "+academicYearReturn.getName());
				mav.addObject("academicYear", academicYearReturn); 
			}else {	 
				mav.addObject("errorCode", response.getErrorCode()); 
			}	
	  
	 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	@RequestMapping(value="editDateAcademicYear.htm", method = RequestMethod.POST)
	public ModelAndView editDateAcademicYearPost(HttpServletRequest httpRequest,@ModelAttribute AcademicYear academicYear, BindingResult result) {
		logger.info(" Start   editDateAcademicYearPost:"+BeanUtils.getBeanString(academicYear));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dateAcademicYearEdit");
		try{ 
			new AcademicYearDateEditValidator().validate(academicYear, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("academicYear", academicYear);
				BuckWaResponse response = academicYearService.editDateAcademicYear(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
		 			mav=initList();
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
	
	
	@RequestMapping(value="editDateEvaluateRound.htm", method = RequestMethod.GET)
	public ModelAndView editDateEvaluateRound(@RequestParam("academicYear") String academicYear ,@RequestParam("evaluateType") String evaluateType) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dateEvaluateRoundEdit");
		try{		 
			BuckWaRequest request = new BuckWaRequest();
			request.put("academicYear", academicYear);
			request.put("evaluateType", evaluateType);
			BuckWaResponse response = academicYearService.getEvaluateRoundByYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				logger.info(" Success ");
				 AcademicYearEvaluateRound  academicYearEvaluateRound  = (AcademicYearEvaluateRound)response.getResObj("academicYearEvaluateRound");
				mav.addObject("academicYearEvaluateRound", academicYearEvaluateRound); 
			}else {	 
				mav.addObject("errorCode", response.getErrorCode()); 
			}	 
	 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	@RequestMapping(value="editDateEvaluateRound.htm", method = RequestMethod.POST)
	public ModelAndView editDateEvaluateRoundPost(HttpServletRequest httpRequest,@ModelAttribute AcademicYearEvaluateRound  academicYearEvaluateRound, BindingResult result) {
		logger.info(" Start   editDateEvaluateRoundPost:"+BeanUtils.getBeanString(academicYearEvaluateRound));
		ModelAndView mav = new ModelAndView();
		mav.setViewName("dateEvaluateRoundEdit");
		try{ 
			new AcademicYearEvaluateRoundEditValidator().validate(academicYearEvaluateRound, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("academicYearEvaluateRound", academicYearEvaluateRound);
				BuckWaResponse response = academicYearService.editDateEvaluateRound(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){									
		 			mav=initList();
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
		
 
}
