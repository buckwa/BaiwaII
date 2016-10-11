package com.buckwa.web.controller.pbp;

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
import com.buckwa.domain.pbp.MarkRank;
import com.buckwa.domain.pbp.MarkRankWrapper;
import com.buckwa.domain.validator.pbp.MarkRankEditValidator;
import com.buckwa.domain.validator.pbp.MarkRankValidator;
import com.buckwa.service.intf.pbp.MarkRankService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.school.SchoolUtil;

@Controller
@RequestMapping("/admin/pbp/markRank")
@SessionAttributes({"markRankWrapper"} ) 
public class MarkRankController {	
	private static Logger logger = LoggerFactory.getLogger(MarkRankController.class);	 
	@Autowired
	private MarkRankService markRankService;	
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("markRankList");
		try{
			BuckWaRequest request = new BuckWaRequest();
			String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
			BuckWaResponse response = markRankService.getByRound(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				MarkRankWrapper markRankWrapper = (MarkRankWrapper)response.getResObj("markRankWrapper");
			 
				markRankWrapper.setAcademicYear(academicYear);
				mav.addObject("markRankWrapper", markRankWrapper);	
			}				  
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	

	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView create() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("markRankList");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT); 
		String academicYear =schoolUtil.getCurrentAcademicYear();
		MarkRank markRank = new MarkRank();
		markRank.setAcademicYear(academicYear);
		BuckWaRequest request = new BuckWaRequest();
		request.put("markRank", markRank);
		BuckWaResponse response = markRankService.addNew(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){							 
			 
			request.put("academicYear",academicYear);
			 response = markRankService.getByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				MarkRankWrapper markRankWrapper = (MarkRankWrapper)response.getResObj("markRankWrapper");
			 
				markRankWrapper.setAcademicYear(academicYear);
				mav.addObject("markRankWrapper", markRankWrapper);	
			} 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}			
 
		return mav;
	}		

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createRole(@ModelAttribute MarkRank markRank, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("markRankList");
		try{			
			new MarkRankValidator().validate(markRank, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("markRank", markRank);
				BuckWaResponse response = markRankService.create(request);
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
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute MarkRankWrapper markRankWrapper, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("markRankList");
		try{			
			new MarkRankEditValidator().validate(markRankWrapper, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("markRankWrapper", markRankWrapper);
				BuckWaResponse response = markRankService.edit(request);
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
		
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("markRankId") String markRankId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("markRankList");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT); 
		String academicYear =schoolUtil.getCurrentAcademicYear();
		MarkRank markRank = new MarkRank();
		markRank.setAcademicYear(academicYear);
		BuckWaRequest request = new BuckWaRequest();
		request.put("markRankId", markRankId);
		BuckWaResponse response = markRankService.delete(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){							 
			 
			request.put("academicYear",academicYear);
			 response = markRankService.getByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				MarkRankWrapper markRankWrapper = (MarkRankWrapper)response.getResObj("markRankWrapper");
			 
				markRankWrapper.setAcademicYear(academicYear);
				mav.addObject("markRankWrapper", markRankWrapper);	
			} 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}			
 
		return mav;
	}	
	
	
	
	@RequestMapping(value="initEditRound.htm", method = RequestMethod.GET)
	public ModelAndView initEditRound(@RequestParam("employeeType") String employeeType,@RequestParam("round") String round) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("markRankRoundList");
		try{
			BuckWaRequest request = new BuckWaRequest();
			String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
			request.put("employeeType",employeeType);
			request.put("round",round);
			BuckWaResponse response = markRankService.getByAcademicYearRound(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				MarkRankWrapper markRankWrapper = (MarkRankWrapper)response.getResObj("markRankWrapper"); 
				markRankWrapper.setAcademicYear(academicYear);
				markRankWrapper.setEmployeeType(employeeType);
				markRankWrapper.setRound(round);
				mav.addObject("markRankWrapper", markRankWrapper);	
			}				  
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="createRound.htm", method = RequestMethod.GET)
	public ModelAndView createRound(@RequestParam("employeeType") String employeeType,@RequestParam("round") String round) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("markRankRoundList");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT); 
		String academicYear =schoolUtil.getCurrentAcademicYear();
		MarkRank markRank = new MarkRank();
		markRank.setAcademicYear(academicYear);
		BuckWaRequest request = new BuckWaRequest();
		request.put("markRank", markRank);
		markRank.setEmployeeType(employeeType);
		markRank.setRound(round);
		BuckWaResponse response = markRankService.addNewRound(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){							 
			 
			request.put("academicYear",academicYear);
			request.put("employeeType",employeeType);
			request.put("round",round);
			 response = markRankService.getByAcademicYearRound(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				MarkRankWrapper markRankWrapper = (MarkRankWrapper)response.getResObj("markRankWrapper");
			 
				markRankWrapper.setAcademicYear(academicYear);
				markRankWrapper.setEmployeeType(employeeType);
				markRankWrapper.setRound(round);
				mav.addObject("markRankWrapper", markRankWrapper);	
			} 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}			
 
		return mav;
	}		

	@RequestMapping(value="editRound.htm", method = RequestMethod.POST)
	public ModelAndView editRound(@ModelAttribute MarkRankWrapper markRankWrapper, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("markRankRoundList");
		try{			
			new MarkRankEditValidator().validate(markRankWrapper, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("markRankWrapper", markRankWrapper);
				BuckWaResponse response = markRankService.editRound(request);
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
}
