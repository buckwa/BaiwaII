package com.buckwa.web.controller.pbp;

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

import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.AcademicKPI;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.FacultyWrapper;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeSub;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.domain.validator.pbp.PBPWorkTypeValidator;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.service.intf.pbp.PBPWorkTypeService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;

@Controller
@RequestMapping("/admin/pbp/pBPWorkType")
@SessionAttributes({"pBPWorkTypeWrapper","pBPWorkType"} ) 
public class PBPWorkTypeController {	
	private static Logger logger = LoggerFactory.getLogger(PBPWorkTypeController.class);	 
	@Autowired
	private PBPWorkTypeService pBPWorkTypeService;	
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
		mav.setViewName("pBPWorkTypeList");
		try{
			BuckWaRequest request = new BuckWaRequest();
			String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
			
			
			// Get Faculty Code
			BuckWaUser buckwaUser =BuckWaUtils.getUserFromContext();
			String facultyCode = buckwaUser.getFacultyCode();
			String facultyCodeSelect ="";
			if(facultyCode==null||facultyCode.trim().length()==0){
				facultyCodeSelect ="01";
			}else{
				facultyCodeSelect = facultyCode;
			}
			
			   System.out.println(" ## /admin/pbp/pBPWorkType/init.htm facultyCodeSelect  :"+facultyCodeSelect);
			request.put("facultyCode",facultyCodeSelect);
			
			
			BuckWaResponse response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
			 
				pBPWorkTypeWrapper.setAcademicYear(academicYear);
				pBPWorkTypeWrapper.setFacultyCodeSelect(facultyCodeSelect);
				pBPWorkTypeWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
				
				 response = facultyService.getFacultyListByAcademicYear(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					 List<Faculty> facultyList = ( List<Faculty>)response.getResObj("facultyList");
					 for(Faculty ftmp:facultyList){
						 if(facultyCodeSelect.equalsIgnoreCase(ftmp.getCode())){
							 pBPWorkTypeWrapper.setFacultyName(ftmp.getName()); 
						 }
						 
					 }
					 pBPWorkTypeWrapper.setFacultyList(facultyList);
				}
				pBPWorkTypeWrapper.setFacultyCodeSelect(facultyCodeSelect);
				mav.addObject("pBPWorkTypeWrapper", pBPWorkTypeWrapper);	
			}				  
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	@RequestMapping(value="search.htm", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam("academicYear") String academicYear,@RequestParam("facultyCodeSelect") String facultyCodeSelect) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pBPWorkTypeList");
		try{
			BuckWaRequest request = new BuckWaRequest();
			//String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
			request.put("facultyCode",facultyCodeSelect);
			BuckWaResponse response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
			 
				pBPWorkTypeWrapper.setAcademicYear(academicYear);
				pBPWorkTypeWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
				pBPWorkTypeWrapper.setFacultyCodeSelect(facultyCodeSelect);
				
				 response = facultyService.getFacultyListByAcademicYear(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					 List<Faculty> facultyList = ( List<Faculty>)response.getResObj("facultyList");
					 for(Faculty ftmp:facultyList){
						 if(facultyCodeSelect.equalsIgnoreCase(ftmp.getCode())){
							 pBPWorkTypeWrapper.setFacultyName(ftmp.getName());
						 }
						 
					 }
					 pBPWorkTypeWrapper.setFacultyList(facultyList);
				}
				mav.addObject("pBPWorkTypeWrapper", pBPWorkTypeWrapper);	
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
		mav.setViewName("pBPWorkTypeList");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT); 
		String academicYear =schoolUtil.getCurrentAcademicYear();
		PBPWorkType pBPWorkType = new PBPWorkType();
		pBPWorkType.setAcademicYear(academicYear);
		BuckWaRequest request = new BuckWaRequest();
		request.put("pBPWorkType", pBPWorkType);
		BuckWaResponse response = pBPWorkTypeService.addNew(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){							 
			 
			request.put("academicYear",academicYear);
//			 response = pBPWorkTypeService.getByAcademicYear(request);
//			if(response.getStatus()==BuckWaConstants.SUCCESS){	
//				PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
//			 
//				pBPWorkTypeWrapper.setAcademicYear(academicYear);
//				mav.addObject("pBPWorkTypeWrapper", pBPWorkTypeWrapper);	
//			} 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}			
 
		return mav;
	}		

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createRole(@ModelAttribute PBPWorkType pBPWorkType, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("pBPWorkTypeList");
		try{			
			//new PBPWorkTypeValidator().validate(pBPWorkType, result);			
			//if (result.hasErrors()) {				
				 
			//}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("pBPWorkType", pBPWorkType);
				BuckWaResponse response = pBPWorkTypeService.create(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){							 
					mav.addObject("successCode", response.getSuccessCode()); 
					 				
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					 
				}				
			//}							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="edit.htm", method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute PBPWorkTypeWrapper pBPWorkTypeWrapper, BindingResult result) {	
		 String facultyCodeSelect =pBPWorkTypeWrapper.getFacultyCodeSelect();
		// String academicYear = pBPWorkTypeWrapper.getAcademicYear();
		 
		 String academicYear = schoolUtil.getCurrentAcademicYear();
		logger.info(" Start  facultyCodeSelect:"+facultyCodeSelect+" academicYear:"+academicYear);
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("pBPWorkTypeList");
		try{			
			new PBPWorkTypeValidator().validate(pBPWorkTypeWrapper, result);			
			if (result.hasErrors()) {				
				logger.info(" ############ Validate Error ###########");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("pBPWorkTypeWrapper", pBPWorkTypeWrapper);
				BuckWaResponse response = pBPWorkTypeService.edit(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){							 
					mav.addObject("successCode", response.getSuccessCode()); 
					 				
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					 
				}				
			}		
			BuckWaRequest request = new BuckWaRequest();
			//String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
			
			request.put("facultyCode",facultyCodeSelect);
			BuckWaResponse response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				 pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
			 
				pBPWorkTypeWrapper.setAcademicYear(academicYear);
				pBPWorkTypeWrapper.setFacultyCodeSelect(facultyCodeSelect);
				pBPWorkTypeWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());

				 response = facultyService.getFacultyListByAcademicYear(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						 List<Faculty> facultyList = ( List<Faculty>)response.getResObj("facultyList");
						 for(Faculty ftmp:facultyList){
							 if(facultyCodeSelect.equalsIgnoreCase(ftmp.getCode())){
								 System.out.println(" ftmp:"+BeanUtils.getBeanString(ftmp));
								 pBPWorkTypeWrapper.setFacultyName(ftmp.getName());
							 }
							 
						 }
						 pBPWorkTypeWrapper.setFacultyList(facultyList);
					}
					pBPWorkTypeWrapper.setFacultyCodeSelect(facultyCodeSelect);
				mav.addObject("pBPWorkTypeWrapper", pBPWorkTypeWrapper);	
			}	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
		
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("pBPWorkTypeId") String pBPWorkTypeId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pBPWorkTypeList");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT); 
		String academicYear =schoolUtil.getCurrentAcademicYear();
		PBPWorkType pBPWorkType = new PBPWorkType();
		pBPWorkType.setAcademicYear(academicYear);
		BuckWaRequest request = new BuckWaRequest();
		request.put("pBPWorkTypeId", pBPWorkTypeId);
		BuckWaResponse response = pBPWorkTypeService.delete(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){							 
			 
			request.put("academicYear",academicYear);
//			 response = pBPWorkTypeService.getByAcademicYear(request);
//			if(response.getStatus()==BuckWaConstants.SUCCESS){	
//				PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
//			 
//				pBPWorkTypeWrapper.setAcademicYear(academicYear);
//				mav.addObject("pBPWorkTypeWrapper", pBPWorkTypeWrapper);	
//			} 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}			
 
		return mav;
	}	
	
	
	
	@RequestMapping(value="manageSub.htm", method = RequestMethod.GET)
	public ModelAndView manageSub(HttpServletRequest httpRequest,@RequestParam("workTypeId") String workTypeId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pBPWorkTypeSubList");
		try{
			BuckWaRequest request = new BuckWaRequest();
			String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("workTypeId",workTypeId);
			BuckWaResponse response = pBPWorkTypeService.getSub(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				PBPWorkType  pBPWorkType  = (PBPWorkType )response.getResObj("pBPWorkType"); 
				mav.addObject("pBPWorkType", pBPWorkType);	
			}	 

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	

	@RequestMapping(value="createSub.htm", method = RequestMethod.GET)
	public ModelAndView createSub(HttpServletRequest httpRequest,@RequestParam("workTypeId") String workTypeId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pBPWorkTypeSubList");
		
		try{
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT); 
		String academicYear =schoolUtil.getCurrentAcademicYear();
		
		
		PBPWorkTypeSub pBPWorkTypeSub = new PBPWorkTypeSub();
		pBPWorkTypeSub.setAcademicYear(academicYear);
		pBPWorkTypeSub.setWorkTypeId(new Long(workTypeId));
		
		BuckWaRequest request = new BuckWaRequest();
		request.put("pBPWorkTypeSub", pBPWorkTypeSub);
		BuckWaResponse response = pBPWorkTypeService.addNewSub(request);
		
		
		request.put("workTypeId",workTypeId);
		 response = pBPWorkTypeService.getSub(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			PBPWorkType  pBPWorkType  = (PBPWorkType )response.getResObj("pBPWorkType"); 
			mav.addObject("pBPWorkType", pBPWorkType);	
		}			
	}catch(Exception ex){
		ex.printStackTrace();
		mav.addObject("errorCode", "E001"); 
	}
		return mav;
	}	
	
	
	
	@RequestMapping(value="editSub.htm", method = RequestMethod.POST)
	public ModelAndView editSub(@ModelAttribute PBPWorkType  pBPWorkType , BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("pBPWorkTypeSubList");
		try{			
 				
				BuckWaRequest request = new BuckWaRequest();
				request.put("pBPWorkType", pBPWorkType);
				BuckWaResponse response = pBPWorkTypeService.editSub(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){							 
					mav.addObject("successCode", response.getSuccessCode()); 
					request.put("workTypeId",pBPWorkType.getWorkTypeId());
					 response = pBPWorkTypeService.getSub(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						pBPWorkType  = (PBPWorkType )response.getResObj("pBPWorkType"); 
						mav.addObject("pBPWorkType", pBPWorkType);	
					}
					 		
					 				
				}else {
					mav.addObject("errorCode", response.getErrorCode()); 
					 
				}				
			 							
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
		
	@RequestMapping(value="deleteSub.htm", method = RequestMethod.GET)
	public ModelAndView deleteSub(@RequestParam("workTypeId") String workTypeId,@RequestParam("workTypeSubId") String workTypeSubId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pBPWorkTypeSubList");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT); 
 
		BuckWaRequest request = new BuckWaRequest();
		request.put("workTypeSubId", workTypeSubId);
		BuckWaResponse response = pBPWorkTypeService.deleteSub(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){							 
			 
			request.put("workTypeId",workTypeId);
			 response = pBPWorkTypeService.getSub(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				PBPWorkType  pBPWorkType  = (PBPWorkType )response.getResObj("pBPWorkType"); 
				mav.addObject("pBPWorkType", pBPWorkType);	
			}
			 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}			
 
		return mav;
	}	
	

	
}
