package com.buckwa.web.controller.pbp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.buckwa.domain.BuckWaUser;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.AcademicKPI;
import com.buckwa.domain.pbp.AcademicKPIAttribute;
import com.buckwa.domain.pbp.AcademicKPIWrapper;
import com.buckwa.domain.pbp.AcademicUnitWrapper;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeSub;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.domain.pbp3.ResponseObj;
import com.buckwa.service.intf.pbp.AcademicKPIService;
import com.buckwa.service.intf.pbp.AcademicUnitService;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.service.intf.pbp.PBPWorkTypeService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;

import baiwa.util.UserLoginUtil;

@Controller
@RequestMapping("/admin/pbp/academicKPI")
@SessionAttributes({"academicKPIWrapper","academicKPI"} ) 
public class AcademicKPIController {	
	private static Logger logger = LoggerFactory.getLogger(AcademicKPIController.class);	 
	@Autowired
	private AcademicKPIService academicKPIService;	
	
	@Autowired
	private PBPWorkTypeService pBPWorkTypeService;	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
	private AcademicUnitService academicUnitService;	
	
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	@Autowired
	private FacultyService facultyService;	
	
	
	@RequestMapping(value="init", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObj initList() {
		logger.info(" Start  ");
		ResponseObj resObj =new ResponseObj();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicKPIList");
		try{
			BuckWaRequest request = new BuckWaRequest();
			String academicYear =schoolUtil.getCurrentAcademicYear();
			 
			
			
			
			// Get Faculty Code
			//BuckWaUser buckwaUser =BuckWaUtils.getUserFromContext();
			String facultyCode = UserLoginUtil.getCurrentFacultyCode();
			String facultyCodeSelect ="";
			if(facultyCode==null||facultyCode.trim().length()==0){
				facultyCodeSelect ="01";
			}else{
				facultyCodeSelect = facultyCode;
			}
			
			   System.out.println(" ## /admin/pbp/academicKPI/init.htm facultyCodeSelect  :"+facultyCodeSelect);
			   			
			request.put("academicYear",academicYear); ;
			request.put("facultyCode",facultyCodeSelect);
			
			String workTypeName ="";
			
			BuckWaResponse  response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
				List<PBPWorkType> workTypeList = pBPWorkTypeWrapper.getpBPWorkTypeList();
				
				if(workTypeList!=null&&workTypeList.size()>0){
					PBPWorkType workType0 =	 workTypeList.get(0) ;
					request.put("workTypeCode",workType0.getCode());
					workTypeName = workType0.getName();
				}
				 
				
				response = academicKPIService.getByAcademicYearWorkTypeCodeFacultyCode(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					AcademicKPIWrapper academicKPIWrapper = (AcademicKPIWrapper)response.getResObj("academicKPIWrapper");			 
					academicKPIWrapper.setAcademicYear(academicYear); 
					academicKPIWrapper.setpBPWorkType(workTypeList.get(0));
					PBPWorkType work0 =workTypeList.get(0);
					academicKPIWrapper.setWorkTypeCode(work0.getCode());
					academicKPIWrapper.setpBPWorkTypeList(workTypeList);	
					
					academicKPIWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
					 response = facultyService.getFacultyListByAcademicYear(request);
						if(response.getStatus()==BuckWaConstants.SUCCESS){	
							 List<Faculty> facultyList = ( List<Faculty>)response.getResObj("facultyList");
							 for(Faculty ftmp:facultyList){
								 if(facultyCodeSelect.equalsIgnoreCase(ftmp.getCode())){
									 academicKPIWrapper.setFacultyName(ftmp.getName());
								 }
								 
							 }
							 academicKPIWrapper.setFacultyList(facultyList);
						}
					
						academicKPIWrapper.setFacultyCodeSelect(facultyCodeSelect);
					academicKPIWrapper.setWorkTypeName(workTypeName);	
					//mav.addObject("academicKPIWrapper", academicKPIWrapper);
					resObj.setResObj(academicKPIWrapper);
					resObj.setStatus("1");
				}

			}	 
			 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		logger.info(" end view: "+mav.getViewName());
		return resObj;
	}	
	
	@RequestMapping(value="search/{workTypeCode}/{academicYear}/{facultyCodeSelect}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObj search(@PathVariable("workTypeCode") String workTypeCode,@PathVariable("academicYear") String academicYear,@PathVariable("facultyCodeSelect") String facultyCodeSelect) {
 
		logger.info(" Start  academicYear:"+academicYear+"  workTypeCode:"+workTypeCode+" facultyCodeSelect:"+facultyCodeSelect);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicKPIList");
		ResponseObj resObj =new ResponseObj();
		try{
			BuckWaRequest request = new BuckWaRequest();
			 
			request.put("academicYear",academicYear);
			request.put("workTypeCode",workTypeCode);
			request.put("facultyCode",facultyCodeSelect);
			BuckWaResponse response = academicKPIService.getByAcademicYearWorkTypeCodeFacultyCode(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicKPIWrapper academicKPIWrapper = (AcademicKPIWrapper)response.getResObj("academicKPIWrapper");			 
				academicKPIWrapper.setAcademicYear(academicYear);
				academicKPIWrapper.setFacultyCodeSelect(facultyCodeSelect);
				request.put("academicYear",academicYear);
				// response = pBPWorkTypeService.getByAcademicYear(request);
				 response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
					academicKPIWrapper.setpBPWorkTypeList(pBPWorkTypeWrapper.getpBPWorkTypeList());
				} 
				request.put("workTypeCode",workTypeCode);
	 			 
				
				academicKPIWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
				 response = facultyService.getFacultyListByAcademicYear(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						 List<Faculty> facultyList = ( List<Faculty>)response.getResObj("facultyList");
						 for(Faculty ftmp:facultyList){
							 if(facultyCodeSelect.equalsIgnoreCase(ftmp.getCode())){
								 academicKPIWrapper.setFacultyName(ftmp.getName());
							 }
							 
						 }
						 academicKPIWrapper.setFacultyList(facultyList);
					}
					
					 response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
						List<PBPWorkType> workTypeList = pBPWorkTypeWrapper.getpBPWorkTypeList();
						academicKPIWrapper.setpBPWorkTypeList(workTypeList);
						if(workTypeList!=null&&workTypeList.size()>0){
						 
							for(PBPWorkType workTypeTmp:workTypeList){
								if(workTypeCode.equalsIgnoreCase(workTypeTmp.getCode())){
									
									academicKPIWrapper.setWorkTypeName(workTypeTmp.getName());
									academicKPIWrapper.setWorkTypeCode(workTypeTmp.getCode());
								}
							}
						
						 
						}					
					}		
				mav.addObject("academicKPIWrapper", academicKPIWrapper);	
				resObj.setResObj(academicKPIWrapper);
			 
			}
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}	
	
	
	@RequestMapping(value="listByWorktype.htm", method = RequestMethod.GET)
	public ResponseObj listByWorktype(@RequestParam("workTypeCode") String workTypeCode,@RequestParam("academicYear") String academicYear,@RequestParam("facultyCode") String facultyCode) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicKPIList");
		ResponseObj resObj =new ResponseObj();
		try{
			BuckWaRequest request = new BuckWaRequest();
			 
			request.put("academicYear",academicYear);
			request.put("workTypeCode",workTypeCode);
			request.put("facultyCode",facultyCode);
			BuckWaResponse response = academicKPIService.getByAcademicYearWorkTypeCodeFacultyCode(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicKPIWrapper academicKPIWrapper = (AcademicKPIWrapper)response.getResObj("academicKPIWrapper");			 
				academicKPIWrapper.setAcademicYear(academicYear);
				academicKPIWrapper.setFacultyCodeSelect(facultyCode);
				 
				//request.put("academicYear",academicYear);
				// response = pBPWorkTypeService.getByAcademicYear(request);
				 response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
					academicKPIWrapper.setpBPWorkTypeList(pBPWorkTypeWrapper.getpBPWorkTypeList());
				} 
				//request.put("workTypeCode",workTypeCode);
				 response = pBPWorkTypeService.getByCodeAcademicFacultyCode(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					PBPWorkType pBPWorkType  = (PBPWorkType)response.getResObj("pBPWorkType");
					 
					academicKPIWrapper.setpBPWorkType(pBPWorkType);
					
					
					
					academicKPIWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
					 response = facultyService.getFacultyListByAcademicYear(request);
						if(response.getStatus()==BuckWaConstants.SUCCESS){	
							 List<Faculty> facultyList = ( List<Faculty>)response.getResObj("facultyList");
							 for(Faculty ftmp:facultyList){
								 if(facultyCode.equalsIgnoreCase(ftmp.getCode())){
									 academicKPIWrapper.setFacultyName(ftmp.getName());
								 }
								 
							 }
							 academicKPIWrapper.setFacultyList(facultyList);
						}
						
						 response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
						if(response.getStatus()==BuckWaConstants.SUCCESS){	
							PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
							List<PBPWorkType> workTypeList = pBPWorkTypeWrapper.getpBPWorkTypeList();
							academicKPIWrapper.setpBPWorkTypeList(workTypeList);
							if(workTypeList!=null&&workTypeList.size()>0){
							 
								for(PBPWorkType workTypeTmp:workTypeList){
									if(workTypeCode.equalsIgnoreCase(workTypeTmp.getCode())){
										
										academicKPIWrapper.setWorkTypeName(workTypeTmp.getName());
										academicKPIWrapper.setWorkTypeCode(workTypeTmp.getCode());
									}
								}
							
							 
							}					
						}
					 
				}				 
				mav.addObject("academicKPIWrapper", academicKPIWrapper);	
				resObj.setResObj(academicKPIWrapper);
			}	 

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}	
	
	@RequestMapping(value="create.htm/{workTypeCode}/{academicYear}/{facultyCode}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObj createKPI(@PathVariable("workTypeCode") String workTypeCode,@PathVariable("academicYear") String academicYear,@PathVariable("facultyCode") String facultyCode) {
		logger.info(" Start  ");
		ResponseObj resObj =new ResponseObj();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicKPICreate");
		try{
			AcademicKPI academicKPI = new AcademicKPI();
			academicKPI.setAcademicYear(academicYear);
			academicKPI.setWorkTypeCode(workTypeCode);
			academicKPI.setFacultyCode(facultyCode);
			mav.addObject("academicKPI",academicKPI);
			
			
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("academicYear",academicYear);
			BuckWaResponse response = academicUnitService.getByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicUnitWrapper academicUnitWrapper = (AcademicUnitWrapper)response.getResObj("academicUnitWrapper");
				academicKPI.setAcademicUnitList(academicUnitWrapper.getAcademicUnitList());
				resObj.setResObj(academicKPI);
			}	 
           

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}	
	
	//ติดไว้ก่อน
	@RequestMapping(value="create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObj createKPI(@RequestBody AcademicKPI academicKPI, BindingResult result) {
		logger.info(" Start   academicKPI:"+BeanUtils.getBeanString(academicKPI));
		ModelAndView mav = new ModelAndView();
		ResponseObj resObj =new ResponseObj();
		mav.setViewName("academicKPICreate");
		try{
			
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("academicKPI",academicKPI);
			BuckWaResponse response = academicKPIService.create(request);
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){ 
				resObj.setResObj(response);
				
//				String url = httpRequest.getContextPath() + "/admin/pbp/academicKPI/listByWorktype.htm?workTypeCode="+academicKPI.getWorkTypeCode()+"&academicYear="+academicKPI.getAcademicYear()+"&facultyCode="+academicKPI.getFacultyCode();
//				logger.info(" Redirect URL:"+url);
//				 
//				mav.setView(new RedirectView(url));
			}	
            

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}
	
	
	@RequestMapping(value="edit/{academicKPIId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObj editKPI(@PathVariable("academicKPIId") String academicKPIId ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		ResponseObj resObj =new ResponseObj();
		mav.setViewName("academicKPIEdit");
		try{
		 
			BuckWaRequest request = new BuckWaRequest(); 
			 
			request.put("academicKPIId",academicKPIId);
			BuckWaResponse response = academicKPIService.getById(request);
			AcademicKPI academicKPI = (AcademicKPI)response.getResObj("academicKPI");
			
			request.put("academicYear",academicKPI.getAcademicYear());
			 response = academicUnitService.getByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicUnitWrapper academicUnitWrapper = (AcademicUnitWrapper)response.getResObj("academicUnitWrapper");
				academicKPI.setAcademicUnitList(academicUnitWrapper.getAcademicUnitList());
	 
			}	
	 
			mav.addObject("academicKPI",academicKPI);
			resObj.setResObj(academicKPI);
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}	
	
	@RequestMapping(value="edit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObj editKPI(@RequestBody AcademicKPI academicKPI, BindingResult result) {
//		logger.info(" Start   editKPI:"+BeanUtils.getBeanString(academicKPI));
		ModelAndView mav = new ModelAndView();
		ResponseObj resObj =new ResponseObj();
		mav.setViewName("academicKPIEdit");
		
		try{
			
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("academicKPI",academicKPI);
			BuckWaResponse response = academicKPIService.edit(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){ 
				request.put("academicKPIId",academicKPI.getAcademicKPIId());
				 response = academicKPIService.getById(request);
				 academicKPI = (AcademicKPI)response.getResObj("academicKPI");
//					String url = httpRequest.getContextPath() + "/admin/pbp/academicKPI/listByWorktype.htm?workTypeCode="+academicKPI.getWorkTypeCode()+"&academicYear="+academicKPI.getAcademicYear()+"&facultyCode="+academicKPI.getFacultyCode();
//				logger.info(" Redirect URL:"+url);
				 
//				mav.setView(new RedirectView(url));
				 resObj.setResObj(academicKPI);
			}	
            

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}	
	
	@RequestMapping(value="delete.htm/{academicKPIId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObj deleteKPI(@PathVariable("academicKPIId") String academicKPIId ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicKPIList");
		ResponseObj resObj =new ResponseObj();
		try{
		 
			BuckWaRequest request = new BuckWaRequest(); 
			 
			request.put("academicKPIId",academicKPIId);
			BuckWaResponse response = academicKPIService.deleteById(request); 
//			String url = httpRequest.getContextPath() + "/admin/pbp/academicKPI/listByWorktype.htm?workTypeCode="+workTypeCode+"&academicYear="+academicYear+"&facultyCode="+facultyCode;
//			logger.info(" Redirect URL:"+url);
			 
			//mav.setView(new RedirectView(url));
			resObj.setResObj(response);
	 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}	
	
	
	@RequestMapping(value="addNewAttribute.htm/{academicKPIId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObj addNewAttribute(@PathVariable("academicKPIId") String academicKPIId ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicKPIEdit");
		ResponseObj resObj =new ResponseObj();
		try{
		 
			BuckWaRequest request = new BuckWaRequest();  
			request.put("academicKPIId",academicKPIId);
			BuckWaResponse response = academicKPIService.getById(request);
			AcademicKPI academicKPI = (AcademicKPI)response.getResObj("academicKPI"); 
			request.put("academicYear",academicKPI.getAcademicYear());
			response = academicUnitService.getByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicUnitWrapper academicUnitWrapper = (AcademicUnitWrapper)response.getResObj("academicUnitWrapper");
				academicKPI.setAcademicUnitList(academicUnitWrapper.getAcademicUnitList()); 
				// Add new Attribute
				AcademicKPIAttribute newAcademicKPIAttribute = new AcademicKPIAttribute();
				newAcademicKPIAttribute.setAcademicKPIId(academicKPI.getAcademicKPIId());
				newAcademicKPIAttribute.setAcademicKPICode(academicKPI.getCode());
				newAcademicKPIAttribute.setAcademicYear(academicKPI.getAcademicYear());
				
				request.put("academicKPIAttribute",newAcademicKPIAttribute);
				response = academicKPIService.addNewAttribute(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					AcademicKPIAttribute academicKPIAttribute = (AcademicKPIAttribute)response.getResObj("academicKPIAttribute");
					if(academicKPI.getAcademicKPIAttributeList()==null){
						List<AcademicKPIAttribute> attributeList = new ArrayList();
						attributeList.add(academicKPIAttribute);
						academicKPI.setAcademicKPIAttributeList(attributeList);
					}else{
						academicKPI.getAcademicKPIAttributeList().add(academicKPIAttribute);
					}
					 				
				}
				

			}	
	 
			resObj.setResObj(academicKPI);
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}
 
	@RequestMapping(value="deleteAttribute.htm/{academicKPIAtributeId}/{academicKPIId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObj deleteAttribute(@PathVariable("academicKPIAtributeId") String academicKPIAtributeId,@PathVariable("academicKPIId") String academicKPIId ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("academicKPIEdit");
		ResponseObj resObj =new ResponseObj();
		try{
		 
			BuckWaRequest request = new BuckWaRequest(); 
			
			request.put("academicKPIAtributeId",academicKPIAtributeId);
			BuckWaResponse response = academicKPIService.deleteAttributeById(request);
			
			request.put("academicKPIId",academicKPIId);
			 response = academicKPIService.getById(request);
			AcademicKPI academicKPI = (AcademicKPI)response.getResObj("academicKPI");
			
			request.put("academicYear",academicKPI.getAcademicYear());
			 response = academicUnitService.getByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicUnitWrapper academicUnitWrapper = (AcademicUnitWrapper)response.getResObj("academicUnitWrapper");
				academicKPI.setAcademicUnitList(academicUnitWrapper.getAcademicUnitList());
			
			}	
			
			mav.addObject("academicKPI",academicKPI);
			resObj.setResObj(academicKPI);
	 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return resObj;
	}	
	
	

	
}
