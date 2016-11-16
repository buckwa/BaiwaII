package com.buckwa.web.controller.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.AcademicKPIUserMappingWrapper;
import com.buckwa.domain.pbp.AcademicPerson;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.domain.pbp.report.RadarPlotReport;
import com.buckwa.domain.pbp.report.TimeTableReport;
import com.buckwa.service.intf.pam.FileLocationService;
import com.buckwa.service.intf.pam.PersonProfileService;
import com.buckwa.service.intf.pam.WorkPersonService;
import com.buckwa.service.intf.pam.WorkTemplateService;
import com.buckwa.service.intf.pbp.AcademicKPIService;
import com.buckwa.service.intf.pbp.AcademicKPIUserMappingService;
import com.buckwa.service.intf.pbp.AcademicUnitService;
import com.buckwa.service.intf.pbp.HeadService;
import com.buckwa.service.intf.pbp.PBPWorkTypeService;
import com.buckwa.service.intf.pbp.PersonTimeTableService;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.service.intf.webboard.WebboardTopicService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;
 
@RestController
@RequestMapping("/personTimeTable")
public class JSONPersonTimeTableController { 
	private static Logger logger = LoggerFactory.getLogger(JSONPersonTimeTableController.class);
	
	@Autowired
	private PersonProfileService personProfileService;
	
	@Autowired
	private WorkTemplateService workTemplateService;
	
	@Autowired
	private WorkPersonService workPersonService;
	
	@Autowired
	private PBPWorkTypeService pBPWorkTypeService;	
	@Autowired
	private SchoolUtil schoolUtil;
	@Autowired
	private AcademicKPIService academicKPIService;	
	
	@Autowired
	private AcademicKPIUserMappingService  academicKPIUserMappingService;
	
	@Autowired
	private AcademicUnitService academicUnitService;	
	
	@Autowired
    private PathUtil pathUtil;
	
	@Autowired
	private FileLocationService fileLocationService;
	@Autowired
	private PersonTimeTableService personTimeTableService;
	
	@Autowired
	private WebboardTopicService  webboardTopicService;
	
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	
	
	@Autowired
	private HeadService headService;	
		
		 @RequestMapping(value = "/getTimeTable/{academicYearSelect}/{userName}/{semester}", method = RequestMethod.GET,headers="Accept=application/json") 
			public List<TimeTableReport> getTimeTable(@PathVariable String academicYearSelect,
					@PathVariable String userName,
					@PathVariable String semester) {
			
			List<TimeTableReport> returnList = new ArrayList();
		 
			try {
				
	           logger.info(" ###### Start Get TimeTable Parameter ####");
	           logger.info(" ###### User Name:"+userName);
	           logger.info(" ###### academicYearSelect:"+academicYearSelect);
	           logger.info(" ###### Semester:"+semester);
	           
				BuckWaRequest request = new BuckWaRequest(); 
				 
			 
				request.put("userName",userName);
				request.put("academicYear",academicYearSelect);
				request.put("semester",semester); 
				BuckWaResponse response = personTimeTableService.getTimeTable(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					returnList = (List<TimeTableReport>)response.getResObj("timeTableReportList");
		 	
				}
				//returnList = getMockReport();
			} catch(Exception ex) {
				ex.printStackTrace();
				 
			}
			
			 
			return returnList;
		}
		 
		 
		 private List<TimeTableReport> getMockReport(){
			 
			 List<TimeTableReport> returnList = new ArrayList();
			 
			 
			 TimeTableReport r1 = new TimeTableReport();
			 r1.setSubjectCode("064454334");
			 r1.setAcademicYear("2557");
			 r1.setThaiName("วิชาทดสอบ  1");
			 r1.setEngName("XXXXXXXXx");
			 r1.setSecNo("2");
			 r1.setDegree("1");
			 r1.setCredit("3");
			 r1.setTotalStudent("34");
			 r1.setTeachTime1("13:00");
			 r1.setTeachTime2("16:00");
			 r1.setTeachDay("4");
			 
			 TimeTableReport r2 = new TimeTableReport();
			 r2.setSubjectCode("0344222");
			 r2.setAcademicYear("2557");
			 r2.setThaiName("วิชาทดสอบ  2");
			 r2.setEngName("XXXXXXXXx222");
			 r2.setSecNo("3");
			 r2.setDegree("2");
			 r2.setCredit("2");
			 r2.setTotalStudent("55");
			 r2.setTeachTime1("15:00");
			 r2.setTeachTime2("17:00");
			 r2.setTeachDay("2");
			 
			 
			 returnList.add(r1);
			 returnList.add(r2);
			 
			 return returnList;
			 
		 }
		 
		 
		 
		 @RequestMapping(value = "/getBarchart", method = RequestMethod.GET,headers="Accept=application/json") 
			public List<RadarPlotReport> getBarChartReport() {
			
			List<RadarPlotReport> returnList = new ArrayList();
				ModelAndView mav = new ModelAndView();
				mav.setViewName("markDepartment");
				try{
					BuckWaRequest request = new BuckWaRequest(); 
					 
					String userName = BuckWaUtils.getUserNameFromContext();
					String academicYear =schoolUtil.getCurrentAcademicYear();
					
					request.put("userName",userName);
					request.put("academicYear",academicYear);
					request.put("status",""); 
					BuckWaResponse response = headService.getDepartmentMarkByUser(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						Department department = (Department)response.getResObj("department"); 
						department.setAcademicYear(academicYear);
						 request = new BuckWaRequest(); 
						String facultyCode = department.getFacultyCode();
						request.put("academicYear",academicYear);
						request.put("facultyCode",facultyCode);
						// response = pBPWorkTypeService.getByAcademicYear(request);
						 response = pBPWorkTypeService.getByAcademicYearFacultyCode(request);
						if(response.getStatus()==BuckWaConstants.SUCCESS){	
							PBPWorkTypeWrapper pBPWorkTypeWrapper = (PBPWorkTypeWrapper)response.getResObj("pBPWorkTypeWrapper"); 
							List<PBPWorkType> pBPWorkTypeList = pBPWorkTypeWrapper.getpBPWorkTypeList();
							
							
							for(PBPWorkType typeTmp:pBPWorkTypeList){
							 
								String shortDesc ="";
								StringTokenizer st = new StringTokenizer(typeTmp.getName(), " ");
								int numberOfSt =1;
						        while (st.hasMoreElements()) { 
						        
						        	String stStr = st.nextElement().toString();
						        	logger.info(" numberOfSt:"+numberOfSt+"  stStr:"+ stStr);
						            if(numberOfSt==1){
						            	shortDesc = stStr;
						            }
						            if(numberOfSt==2){
						            	//axisLables = axisLables +" "
						            	//st.nextElement();
						            }
						            numberOfSt++;
						        } 	
						        
						        typeTmp.setShortDesc(shortDesc);
						        
						        
						 
						        // Sum total mark
						     List<AcademicPerson>  academicPersonListMark = department.getAcademicPersonList();
						     BigDecimal totalMark = new BigDecimal(0.00);
						     for(AcademicPerson personTmp: academicPersonListMark){
						    	 
						    	List<PBPWorkType> pBPWorkTypeListTotalMark	= personTmp.getpBPWorkTypeWrapper().getpBPWorkTypeList();
						    	
							      for(PBPWorkType totalMarkTmp:pBPWorkTypeListTotalMark){
							    	  System.out.print(" totalMarkTmp id:"+totalMarkTmp.getWorkTypeId());
							    	  
							    	  if(typeTmp.getWorkTypeId().intValue()==totalMarkTmp.getWorkTypeId().intValue()){ 
							  			
								 
											totalMark = totalMark.add(totalMarkTmp.getTotalInPercentCompareBaseWorkType());
										 
							    	  }
							    	  
							      }
						     }
						     
						     typeTmp.setTotalAllWorkType(totalMark); 
						     typeTmp.setTotalInPercentCompareBaseWorkType(totalMark);
						
							}
							
							department.setpBPWorkTypeList(pBPWorkTypeList);
							 
						}	 
						
						
						List<AcademicPerson> personListtmp =department.getAcademicPersonList();
						 
						int loopx =0;
						for(AcademicPerson personTmp: personListtmp){
							RadarPlotReport reportTmp = new RadarPlotReport();
							
							reportTmp.setAxisName(personTmp.getThaiName()+" "+ personTmp.getThaiSurname());
							 reportTmp.setAxisValue(personTmp.getpBPWorkTypeWrapper().getTotalPercentMarkCompareBase()+"");
							 
							returnList.add(reportTmp);
						 
						}
						
		 
					   	 
					 
						
						mav.addObject("department", department);	
					}				  
				}catch(Exception ex){
					ex.printStackTrace();
					mav.addObject("errorCode", "E001"); 
				}
				return returnList;
			}	
		
}
