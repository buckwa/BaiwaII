package com.buckwa.web.controller.pbp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.AcademicKPIWrapper;
import com.buckwa.domain.pbp.AcademicYearWrapper;
import com.buckwa.domain.pbp.AnonymousWrapper;
import com.buckwa.domain.pbp.MarkRankWrapper;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeSub;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.service.intf.pbp.AcademicKPIService;
import com.buckwa.service.intf.pbp.AcademicYearService;
import com.buckwa.service.intf.pbp.MarkRankService;
import com.buckwa.service.intf.pbp.PBPWorkTypeService;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;

import eu.bitwalker.useragentutils.UserAgent;

 

@Controller

@SessionAttributes({"academicYearStr","anonymousWrapper","academicKPIWrapper"} )  
public class KMILTCommonController {
	private static Logger logger = LoggerFactory.getLogger(KMILTCommonController.class);
	
	@Autowired
	private SchoolUtil schoolUtil;

	@Autowired
	private AcademicYearService academicYearService;	
	
	@Autowired
	private PBPWorkTypeService pBPWorkTypeService;	
	
	@Autowired
	private MarkRankService markRankService;	
	@Autowired
    private PathUtil pathUtil;
	
	@Autowired
	private AcademicKPIService academicKPIService;	
	
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	@RequestMapping(value = "/anonymous.htm")
	public ModelAndView anonymouse() {
		logger.info(" # anonymous 0 ");
		ModelAndView mav = new ModelAndView();
		String academicYear =schoolUtil.getCurrentAcademicYear();
		mav.addObject("academicYearStr",academicYear);
		AnonymousWrapper anonymousWrapper = new AnonymousWrapper();
		anonymousWrapper.setAcademicYear(academicYear);
		mav.addObject("anonymousWrapper", anonymousWrapper);	
		try{
			logger.info(" # anonymous 1 ");
			BuckWaRequest request = new BuckWaRequest();	 
			BuckWaResponse response = academicYearService.getCurrentAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicYearWrapper academicYearWrapper = (AcademicYearWrapper)response.getResObj("academicYearWrapper"); 
				anonymousWrapper.setAcademicYearWrapper(academicYearWrapper);
			}	
			 
			logger.info(" # anonymous 2");
			String facultyCode = BuckWaUtils.getFacultyCodeFromUserContext();
			logger.info(" # anonymous facultyCode:"+facultyCode);
			request.put("academicYear",academicYear);
			request.put("facultyCode",facultyCode);
			response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper"); 
				pBPWorkTypeWrapper.setAcademicYear(academicYear);
				anonymousWrapper.setpBPWorkTypeWrapper(pBPWorkTypeWrapper);
			}		
			logger.info(" # anonymous 3 ");
 
			response = markRankService.getByRound(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				MarkRankWrapper markRankWrapper = (MarkRankWrapper)response.getResObj("markRankWrapper");
			 
				markRankWrapper.setAcademicYear(academicYear);
				anonymousWrapper.setMarkRankWrapper(markRankWrapper);
			}	
			
			 response = academicYearService.getCurrentAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicYearWrapper academicYearWrapper = (AcademicYearWrapper)response.getResObj("academicYearWrapper");
				mav.addObject("academicYearWrapper", academicYearWrapper);	
				academicYearWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
			}
			
			
			mav.addObject("academicYearList", academicYearUtil.getAcademicYearList()); 
			mav.addObject("facultyCode", facultyCode); 
			mav.addObject("facultyDesc", BuckWaUtils.getFacultyDescFromUserContext()); 
		 
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		mav.setViewName("anonymous");
		
		logger.info(" # anonymous success ");
		return mav;
 
	}
	
	
	@RequestMapping(value = "/userManual.htm")
	public ModelAndView userManual() {
		logger.info(" # userManual 0 ");
		ModelAndView mav = new ModelAndView();
		String academicYear =schoolUtil.getCurrentAcademicYear();
		mav.addObject("academicYearStr",academicYear);
		mav.setViewName("userManual");
		
	 
		return mav;
 
	}
 
	
	@RequestMapping(value="/anonymous/listKPIByWorktype.htm", method = RequestMethod.GET)
	public ModelAndView listByWorktype(@RequestParam("workTypeCode") String workTypeCode,@RequestParam("academicYear") String academicYear) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("anonymousAcademicKPIList");
		try{
			BuckWaRequest request = new BuckWaRequest();
			String facultyCode = BuckWaUtils.getFacultyCodeFromUserContext();
			request.put("academicYear",academicYear);
			request.put("workTypeCode",workTypeCode);
			request.put("facultyCode",facultyCode);
			BuckWaResponse response = academicKPIService.getByAcademicYearWorkTypeCodeFacultyCode(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicKPIWrapper academicKPIWrapper = (AcademicKPIWrapper)response.getResObj("academicKPIWrapper");			 
				academicKPIWrapper.setAcademicYear(academicYear);
				
				
				request.put("academicYear",academicYear);
				request.put("facultyCode",facultyCode);
				 response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper");
					academicKPIWrapper.setpBPWorkTypeList(pBPWorkTypeWrapper.getpBPWorkTypeList());
				} 
				request.put("workTypeCode",workTypeCode);
				request.put("facultyCode",facultyCode);
				 response = pBPWorkTypeService.getByCodeAcademicFacultyCode(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					PBPWorkType pBPWorkType  = (PBPWorkType)response.getResObj("pBPWorkType");
					 
					academicKPIWrapper.setpBPWorkType(pBPWorkType);
					
					
					List<PBPWorkTypeSub> workTypeSubList  =pBPWorkType.getpBPWorkTypeSubList();
					if(workTypeSubList!=null&&workTypeSubList.size()>0){
						// Set
					}
					 
				}				 
				mav.addObject("academicKPIWrapper", academicKPIWrapper);	
			}	 

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	@RequestMapping(value="/anonymous/listByWorktype.htm", method = RequestMethod.GET)
	public ModelAndView listByWorktype(@RequestParam("academicYear") String academicYear) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("anonymousAcademicKPIListAll");
		try{
			BuckWaRequest request = new BuckWaRequest();
			 
			request.put("academicYear",academicYear);
			 
			BuckWaResponse response = academicKPIService.getAllByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicKPIWrapper academicKPIWrapper = (AcademicKPIWrapper)response.getResObj("academicKPIWrapper");			 
				academicKPIWrapper.setAcademicYear(academicYear); 
				mav.addObject("academicKPIWrapper", academicKPIWrapper);	
			}	 

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}

	
	@RequestMapping(value="/anonymous/listByWorktypeFaculty.htm", method = RequestMethod.GET)
	public ModelAndView listByWorktypeFaculty(@RequestParam("academicYear") String academicYear ) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("anonymousAcademicKPIListAll");
		try{
			BuckWaRequest request = new BuckWaRequest();
			String facultyCode =BuckWaUtils.getFacultyCodeFromUserContext();
			
			request.put("academicYear",academicYear);			
			request.put("facultyCode",facultyCode);
			 
			BuckWaResponse response = academicKPIService.getAllByAcademicYearFacultyCode(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicKPIWrapper academicKPIWrapper = (AcademicKPIWrapper)response.getResObj("academicKPIWrapper");			 
				academicKPIWrapper.setAcademicYear(academicYear); 
				mav.addObject("academicKPIWrapper", academicKPIWrapper);	
			}	 

		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	@RequestMapping(value="downloadDoc")
	public ModelAndView download(@RequestParam("fileCode") String fileCode ,HttpServletRequest httpRequest, HttpServletResponse httpResponse,@RequestHeader("User-Agent") String userAgent) {
		
		logger.info("#####  Start  Download   << "+ fileCode +" >> #### ");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		 
		
		try {
			
			UserAgent userAgentObj = UserAgent.parseUserAgentString(httpRequest.getHeader("User-Agent"));
			
			String browserType = userAgentObj.getBrowser().getName();
			System.out.println("browserType:"+browserType);
			
			String filePath =pathUtil.getDocPath();
			
			String fullPath = "";
			String  fileName ="";
			
			if("manual1".equalsIgnoreCase(fileCode)){
				fileName ="คู่มืออบรมโปรแกรมระบบประเมินคุณภาพวิชากการ.pdf";
				fullPath =  filePath+fileName;
			}else if("manual2".equalsIgnoreCase(fileCode)){
				fileName ="ADMIN คณะ.pdf";
				fullPath =  filePath+fileName;
			}else if("manual3".equalsIgnoreCase(fileCode)){
				fileName ="คู่มืออบรมโปรแกรมระบบประเมินคุณภาพวิชาการสำหรับประธานสาขา.pdf";
				fullPath =  filePath+fileName;
			}else if("manual4".equalsIgnoreCase(fileCode)){
				fileName ="คู่มืออบรมโปรแกรมระบบประเมินคุณภาพวิชาการสำหรับผู้บริหาร.pdf";
				fullPath =  filePath+fileName;		 
					
			}else if("2556".equalsIgnoreCase(fileCode)){
				fileName ="2556.pdf";
				fullPath =  filePath+fileName;
		 
			}else if("59".equalsIgnoreCase(fileCode)){
				fileName ="Person Report.doc";
				fullPath =  filePath+fileName;
			}		
			
			
			
			
			
			if("declare01".equalsIgnoreCase(fileCode)){
				fileName ="ประกาศ 2558 คณะวิศวกรรมศาสตร์.pdf";
				fullPath =  filePath+fileName;
			}
			if("declare02".equalsIgnoreCase(fileCode)){
				fileName ="ประกาศ 2558 คณะสถาปัตย์กรรมศาสตร์.pdf";
				fullPath =  filePath+fileName;
			}			
			
			if("declare03".equalsIgnoreCase(fileCode)){
				fileName ="ประกาศ 2558 คณะครุศาสตร์อุตสาหกรรม.pdf";
				fullPath =  filePath+fileName;
			}				
			
			
			if("declare04".equalsIgnoreCase(fileCode)){
				fileName ="ประกาศ 2558 คณะเทคโนโลยีการเกษตร.pdf";
				fullPath =  filePath+fileName;
			}			
			
			if("declare05".equalsIgnoreCase(fileCode)){
				fileName ="ประกาศ 2558 คณะวิทยาศาสตร์.pdf";
				fullPath =  filePath+fileName;
			}		
			
			if("declare06".equalsIgnoreCase(fileCode)){
				fileName ="ประกาศ 2558 คณะอุตสาหกรรมเกษตร.pdf";
				fullPath =  filePath+fileName;
			}		
			
			if("declare07".equalsIgnoreCase(fileCode)){
				fileName ="ประกาศ 2558 คณะเทคโนโลยีสารสนเทศ.pdf";
				fullPath =  filePath+fileName;
			}		
			if("declare09".equalsIgnoreCase(fileCode)){
				fileName ="ประกาศ 2558 วิทยาลัยนานาชาติ.pdf";
				fullPath =  filePath+fileName;
			}					
			
			if("declare10".equalsIgnoreCase(fileCode)){
				fileName ="ประกาศ 2558 วิทยาลัยนาโนเทคโนโลยีพระจอมเกล้าลาดกระบัง.pdf";
				fullPath =  filePath+fileName;
			}					
			
			if("declare11".equalsIgnoreCase(fileCode)){
				fileName ="ประกาศ 2558 วิทยาลัยนวัตกรรมการผลิตขั้นสูง.pdf";
				fullPath =  filePath+fileName;
			}					
			if("declare12".equalsIgnoreCase(fileCode)){
				fileName ="ประกาศ 2558 คณะการบริหารและจัดการ.pdf";
				fullPath =  filePath+fileName;
			}		
			
			if("declare13".equalsIgnoreCase(fileCode)){
				fileName ="ประกาศ 2558 วิทยาเขตชุมพรเขตรอุดมศักดิ์.pdf";
				fullPath =  filePath+fileName;
			}		
 			
			
			logger.info("#####fullPath :"+fullPath);
			
			InputStream inputStream = new FileInputStream(fullPath);
	 
			
			// Check For IE OR NOT for Encoder fileName !
//			String user_agent = httpRequest.getHeader("user-agent");
//			boolean isInternetExplorer = (user_agent.indexOf(BuckWaConstants.BROWSER_MSIE) > -1);
//			if (isInternetExplorer) {				 
//				httpResponse.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"");
//			} else {				 
//				httpResponse.setHeader("Content-disposition", "attachment; filename=\"" + MimeUtility.encodeWord(fileName) + "\"");
//			}
//			
			
			 
			String fileNamefinal =URLEncoder.encode(fileName,"UTF-8");
			
			httpResponse.setContentType("application/pdf; charset=UTF-8");
			httpResponse.setCharacterEncoding("UTF-8");
	        if(browserType.contains("IE")||browserType.contains("Chrome"))
	        	httpResponse.setHeader("Content-Disposition","attachment; filename="+fileNamefinal);
	        if(browserType.contains("Firefox")){
	        	httpResponse.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+fileNamefinal);
	        }
 
			
			FileCopyUtils.copy( inputStream, httpResponse.getOutputStream());
			   
			httpResponse.flushBuffer();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001);
		}
		
		return null;

	}
}
