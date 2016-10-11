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
import com.buckwa.domain.pbp.AcademicUnit;
import com.buckwa.domain.pbp.AcademicUnitWrapper;
import com.buckwa.domain.validator.pbp.AcademicUnitEditValidator;
import com.buckwa.domain.validator.pbp.AcademicUnitValidator;
import com.buckwa.service.intf.pbp.AcademicUnitService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.school.SchoolUtil;

@Controller
@RequestMapping("/admin/pbp/academicUnit")
@SessionAttributes({"academicUnitWrapper"} ) 
public class AcademicUnitController {	
	private static Logger logger = LoggerFactory.getLogger(AcademicUnitController.class);	 
	@Autowired
	private AcademicUnitService academicUnitService;	
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicUnitList");
		try{
			BuckWaRequest request = new BuckWaRequest();
			String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
			BuckWaResponse response = academicUnitService.getByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicUnitWrapper academicUnitWrapper = (AcademicUnitWrapper)response.getResObj("academicUnitWrapper");
			 
				academicUnitWrapper.setAcademicYear(academicYear);
				mav.addObject("academicUnitWrapper", academicUnitWrapper);	
			}				  
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	

	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView init() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicUnitList");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT); 
		String academicYear =schoolUtil.getCurrentAcademicYear();
		AcademicUnit academicUnit = new AcademicUnit();
		academicUnit.setAcademicYear(academicYear);
		BuckWaRequest request = new BuckWaRequest();
		request.put("academicUnit", academicUnit);
		BuckWaResponse response = academicUnitService.addNew(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){							 
			 
			request.put("academicYear",academicYear);
			 response = academicUnitService.getByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicUnitWrapper academicUnitWrapper = (AcademicUnitWrapper)response.getResObj("academicUnitWrapper");
			 
				academicUnitWrapper.setAcademicYear(academicYear);
				mav.addObject("academicUnitWrapper", academicUnitWrapper);	
			} 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}			
 
		return mav;
	}		

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createRole(@ModelAttribute AcademicUnit academicUnit, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("academicUnitList");
		try{			
			new AcademicUnitValidator().validate(academicUnit, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("academicUnit", academicUnit);
				BuckWaResponse response = academicUnitService.create(request);
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
	public ModelAndView edit(@ModelAttribute AcademicUnitWrapper academicUnitWrapper, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("academicUnitList");
		try{			
			new AcademicUnitEditValidator().validate(academicUnitWrapper, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("academicUnitWrapper", academicUnitWrapper);
				BuckWaResponse response = academicUnitService.edit(request);
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
	public ModelAndView delete(@RequestParam("academicUnitId") String academicUnitId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicUnitList");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT); 
		String academicYear =schoolUtil.getCurrentAcademicYear();
		AcademicUnit academicUnit = new AcademicUnit();
		academicUnit.setAcademicYear(academicYear);
		BuckWaRequest request = new BuckWaRequest();
		request.put("academicUnitId", academicUnitId);
		BuckWaResponse response = academicUnitService.deleteById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){							 
			 
			request.put("academicYear",academicYear);
			 response = academicUnitService.getByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicUnitWrapper academicUnitWrapper = (AcademicUnitWrapper)response.getResObj("academicUnitWrapper");
			 
				academicUnitWrapper.setAcademicYear(academicYear);
				mav.addObject("academicUnitWrapper", academicUnitWrapper);	
			} 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}			
 
		return mav;
	}			 
}
