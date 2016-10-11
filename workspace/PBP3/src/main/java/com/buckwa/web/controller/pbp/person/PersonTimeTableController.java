package com.buckwa.web.controller.pbp.person;

import static com.googlecode.charts4j.Color.BLACK;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

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

import com.buckwa.dao.intf.pbp.FacultyDao;
import com.buckwa.domain.admin.SubjectOfStaff;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.AcademicKPIUserMappingWrapper;
import com.buckwa.domain.pbp.AcademicPerson;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.domain.pbp.TimeTableWrapper;
import com.buckwa.domain.pbp.report.TimeTableReport;
import com.buckwa.domain.validator.pbp.ReplyPBPMessageValidator;
import com.buckwa.domain.validator.pbp.TimeTableWrapperValidator;
import com.buckwa.domain.webboard.Message;
import com.buckwa.service.intf.pbp.AcademicKPIUserMappingService;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.service.intf.pbp.HeadService;
import com.buckwa.service.intf.pbp.PBPWorkTypeService;
import com.buckwa.service.intf.pbp.PersonTimeTableService;
import com.buckwa.service.intf.webboard.WebboardTopicService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaUtils;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;
import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.LineStyle;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.RadarChart;
import com.googlecode.charts4j.RadarPlot;
import com.googlecode.charts4j.RadialAxisLabels;
import com.googlecode.charts4j.Shape;

@Controller
@RequestMapping("/personTimeTable")
@SessionAttributes({"academicKPIUserMappingWrapper,timeTableWrapper"} ) 
 
public class PersonTimeTableController {	
	private static Logger logger = LoggerFactory.getLogger(PersonTimeTableController.class);	 
	@Autowired
	private HeadService headService;	
	
	@Autowired
	private AcademicKPIUserMappingService academicKPIUserMappingService;
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
	private FacultyDao  facultyDao;
	
	@Autowired
	private PBPWorkTypeService pBPWorkTypeService;
	
	@Autowired
	private WebboardTopicService  webboardTopicService;
	
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	@Autowired
	private FacultyService facultyService;
	
	@Autowired
	private PersonTimeTableService personTimeTableService;
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList(@RequestParam("academicYearSelect") String academicYearSelect) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("personTimeTableInit");
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			 
			String userName = BuckWaUtils.getUserNameFromContext();
			if(academicYearSelect==null||academicYearSelect.trim().length()==0){
				academicYearSelect =schoolUtil.getCurrentAcademicYear();
			}
		//	String academicYear =schoolUtil.getCurrentAcademicYear();
            TimeTableReport timetableReport = new TimeTableReport();
            timetableReport.setAcademicYearList(academicYearUtil.getAcademicYearList());
            
            timetableReport.setAcademicYear(academicYearSelect);
            timetableReport.setAcademicYearSelect(academicYearSelect);
            
			
			mav.addObject("academicYearSelect", academicYearSelect);	
			mav.addObject("userName", userName);
			mav.addObject("timetableReport", timetableReport);
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value = "teachShare.htm", method = RequestMethod.GET)
	public ModelAndView teachShare( ) {
	 
		String academicYear =schoolUtil.getCurrentAcademicYear();
		TimeTableWrapper timeTableWrapper = new TimeTableWrapper();
		timeTableWrapper.setAcademicYear(academicYear);
		
		logger.info("init");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("teachShare");
		//List<SubjectOfStaff> subjectList = staffPartnerService.getAllList(section);
		List<SubjectOfStaff> subjectList = new ArrayList();
		mav.addObject("subjectList", subjectList);
		mav.addObject("timeTableWrapper", timeTableWrapper);
		
		return mav;
	}
	 
	
	@RequestMapping(value = "searchSubject.htm", method = RequestMethod.POST)
	public ModelAndView searchSubject( @ModelAttribute TimeTableWrapper  timeTableWrapper, BindingResult result) {
 
		
		logger.info("init");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("teachShare");
		
		try{		
			
			List<TimeTableReport> subjectList = new ArrayList();
			new TimeTableWrapperValidator().validate(timeTableWrapper, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				
				 
				request.put("subjectId",timeTableWrapper.getSubjectId());
				request.put("academicYear",timeTableWrapper.getAcademicYear());
				request.put("semester",timeTableWrapper.getSemester()); 
				BuckWaResponse response = personTimeTableService.getTimeTableShsre(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					subjectList = (List<TimeTableReport>)response.getResObj("timeTableReportList");
		 	
				}
	 			
			}		
	 
			mav.addObject("subjectList", subjectList);
			mav.addObject("timeTableWrapper", timeTableWrapper);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}	 
		return mav;
	}
	
	
	
	@RequestMapping(value = "addShareSubject.htm", method = RequestMethod.GET)
	public ModelAndView addShareSubject( @RequestParam("timetableId") String timetableId ) { 
		
		logger.info("init");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("teachShare");
		
		try{					
			 
				BuckWaRequest request = new BuckWaRequest();	
				
				String userName =BuckWaUtils.getUserNameFromContext();
				String academicYear =schoolUtil.getCurrentAcademicYear();
				String regId =schoolUtil.getRegIdFromUserName(userName,academicYear);
				
				
				request.put("timetableId", timetableId);
				
				
				
				BuckWaResponse response = personTimeTableService.getTimeTableById(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					TimeTableReport 	timeTableReport = (TimeTableReport)response.getResObj("timeTableReport");
					timeTableReport.setTeacherId(regId);
					
					request.put("timeTableReport", timeTableReport);	
					 response = personTimeTableService.addShareSubject(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						 // Return new page
						mav.setViewName("teachShareAddSuccess");
					}else{
						// Return Same page
					}
				}
	  		 			
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}	 
		return mav;
	}
	
	
	
	@RequestMapping(value="initByUserName.htm", method = RequestMethod.GET)
	public ModelAndView initByUserName(HttpServletRequest httpRequest,@RequestParam("userName") String userName) {
		logger.info(" Start  userName: "+userName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("headWorkUserList");
		try{
			
			httpRequest.getSession().setAttribute("approveUserName", userName);
			BuckWaRequest request = new BuckWaRequest(); 
			 
			String headUserName = BuckWaUtils.getUserNameFromContext();
			String academicYear =schoolUtil.getCurrentAcademicYear();
			
			request.put("headUserName",headUserName);
			request.put("academicYear",academicYear);
			request.put("userName",userName);
			request.put("status",""); 
			BuckWaResponse response = headService.getByUserAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper = (AcademicKPIUserMappingWrapper)response.getResObj("academicKPIUserMappingWrapper");
			 
				academicKPIUserMappingWrapper.setAcademicYear(academicYear);
				mav.addObject("academicKPIUserMappingWrapper", academicKPIUserMappingWrapper);	
			}				  
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="viewWork.htm", method = RequestMethod.GET)
	public ModelAndView viewWork(@RequestParam("kpiUserMappingId") String kpiUserMappingId  ) {
		logger.info(" Start  kpiUserMappingId:"+kpiUserMappingId );
		ModelAndView mav = new ModelAndView();
		mav.setViewName("viewWork");
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("kpiUserMappingId",kpiUserMappingId);
			BuckWaResponse response = academicKPIUserMappingService.getById(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
	 
				 AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper = (AcademicKPIUserMappingWrapper)response.getResObj("academicKPIUserMappingWrapper");
				  
				mav.addObject("academicKPIUserMappingWrapper", academicKPIUserMappingWrapper);
			} else{
				mav.addObject("errorCode", "E001"); 
				mav.setViewName("headWorkList");
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	@RequestMapping(value="approveWork.htm", method = RequestMethod.GET)
	public ModelAndView approveWork(HttpServletRequest httpRequest,@RequestParam("kpiUserMappingId") String kpiUserMappingId  ) {
		logger.info(" Start  kpiUserMappingId:"+kpiUserMappingId );
		ModelAndView mav = new ModelAndView();
		mav.setViewName("headWorkList");
		try{
			
			String approveUserName =(String)httpRequest.getSession().getAttribute("approveUserName");
			BuckWaRequest request = new BuckWaRequest(); 
			request.put("kpiUserMappingId",kpiUserMappingId);
			BuckWaResponse response = academicKPIUserMappingService.approve(request);
			 logger.info(" ### approveWork after approve ");
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
	 
		 
			return initByUserName(httpRequest,approveUserName);	
			} else{
				mav.addObject("errorCode", "E001"); 
				mav.setViewName("viewWork");
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}
	
	
	@RequestMapping(value="viewDepartmentMark.htm", method = RequestMethod.GET)
	public ModelAndView viewDepartmentMark() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("viewDepartment");
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			 
			String headUserName = BuckWaUtils.getUserNameFromContext();
			String academicYear =schoolUtil.getCurrentAcademicYear();
			
			request.put("headUserName",headUserName);
			request.put("academicYear",academicYear);
			  
			BuckWaResponse response = headService.getDepartmentMark(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				Department department = (Department)response.getResObj("department");
			 
				department.setAcademicYear(academicYear);
				mav.addObject("department", department);	
			}				  
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	@RequestMapping(value="viewMarkDepartment.htm", method = RequestMethod.GET)
	public ModelAndView viewMarkDepartment() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("markDepartment");
		try{
			BuckWaRequest request = new BuckWaRequest(); 
			 
			String headUserName = BuckWaUtils.getUserNameFromContext();
			String academicYear =schoolUtil.getCurrentAcademicYear();
			
			request.put("headUserName",headUserName);
			request.put("academicYear",academicYear);
			request.put("status",""); 
			BuckWaResponse response = headService.getDepartmentMark(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				Department department = (Department)response.getResObj("department"); 
				department.setAcademicYear(academicYear);
				 request = new BuckWaRequest(); 
				 String facultyCode=department.getFacultyCode();
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
					
					// Save Department Summary to DB
					
					
					request.put("academicYear",academicYear);
					request.put("facultyCode",department.getFacultyCode());
					response =  facultyService.getFacultyByCodeAndYear(request);
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						Faculty faculty = (Faculty)response.getResObj("faculty");  
						logger.info(" #### Faculty:"+ BeanUtils.getBeanString(faculty));
						department.setFaculty(faculty);
						
						request.put("department",department);
						headService.saveDepartmentReportSummary(request);
					}
					 
				}	 
				 
				
				
				List<String> axisLabelList =new ArrayList();
				List<Number> dataList = new ArrayList();				
				BigDecimal data1 = null;				
				List<PBPWorkType> pBPWorkTypeList = department.getpBPWorkTypeList();				
				int loop =0;
				for(PBPWorkType typeTmp:pBPWorkTypeList){
					logger.info(" loop:"+loop);
					String tempLabel ="";
					StringTokenizer st = new StringTokenizer(typeTmp.getName(), " ");
					int numberOfSt =1;
			        while (st.hasMoreElements()) { 			        
			        	String stStr = st.nextElement().toString();
			        	logger.info(" numberOfSt:"+numberOfSt+"  stStr:"+ stStr);
			            if(numberOfSt==1){
			            	tempLabel = stStr;
			            }
			            if(numberOfSt==2){
			            	//axisLables = axisLables +" "
			            	//st.nextElement();
			            }
			            numberOfSt++;
			        }
					
			        axisLabelList.add(tempLabel);
					
			        if(loop==0){
			        	data1 =typeTmp.getTotalInPercentCompareBaseWorkType().multiply(new BigDecimal(2)).setScale(0,BigDecimal.ROUND_UP);
			        }
					
					loop++;
					dataList.add(typeTmp.getTotalInPercentCompareBaseWorkType() .multiply(new BigDecimal(2)).setScale(0,BigDecimal.ROUND_UP));
							
			
				}
				
				dataList.add(data1);
				
				
				logger.info(" Data List ");
				for(Number datax :dataList){
					System.out.print("  "+datax);
				}
				
			   	 
				
		        RadarPlot plot = Plots.newRadarPlot(Data.newData(dataList));
		       // RadarPlot plot = Plots.newRadarPlot(Data.newData(80, 50, 50, 80, 60,80));
		       // RadarPlot plot = Plots.newRadarPlot(Data.newData(0.76, 51.28,55,15.4, 5,0.76));
		       
		        Color plotColor = Color.newColor("CC3366");
		        plot.addShapeMarkers(Shape.SQUARE, plotColor, 5);
		        plot.addShapeMarkers(Shape.SQUARE, plotColor, 3);
		        plot.setColor(plotColor);
		        plot.setLineStyle(LineStyle.newLineStyle(2, 1, 0));
		        RadarChart chart = GCharts.newRadarChart(plot);
		      //  chart.setTitle("����������ҹ", BLACK, 20);
		        chart.setSize(500, 500);
		       // RadialAxisLabels radialAxisLabels = AxisLabelsFactory.newRadialAxisLabels("Maths", "Arts", "French", "German", "Music");
		        RadialAxisLabels radialAxisLabels = AxisLabelsFactory.newRadialAxisLabels(axisLabelList);
		        radialAxisLabels.setRadialAxisStyle(BLACK, 12);
		        chart.addRadialAxisLabels(radialAxisLabels);
		         AxisLabels contrentricAxisLabels = AxisLabelsFactory.newNumericAxisLabels(Arrays.asList(0, 20, 40, 60, 80, 100));
		       
		        contrentricAxisLabels.setAxisStyle(AxisStyle.newAxisStyle(BLACK, 12, AxisTextAlignment.RIGHT));
		        chart.addConcentricAxisLabels(contrentricAxisLabels);
		        String url = chart.toURLString();				
				
				 
		        logger.info(" radarURL :"+url); 
		       
		      
		        department.setRadarURL(url)	;	 
				
				mav.addObject("department", department);	
			}				  
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	@RequestMapping(value="replyMessage.htm", method = RequestMethod.POST)
	public ModelAndView replyMessage(HttpServletRequest httpRequest, @ModelAttribute AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper , BindingResult result) {
		ModelAndView mav = new ModelAndView();
		try{			
			logger.info(" ## replyMessage:"+academicKPIUserMappingWrapper.getReplyMessage());
			 
			new ReplyPBPMessageValidator().validate(academicKPIUserMappingWrapper, result);			
			if (result.hasErrors()) {				
				mav.setViewName("viewImportWork");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				 
				
				Message newMessage = new Message();
				newMessage.setMessageDetail(academicKPIUserMappingWrapper.getReplyMessage());
				newMessage.setTopicId(academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId());
				newMessage.setStatus("1");				
				newMessage.setCreateBy(BuckWaUtils.getFullNameFromContext());
				request.put("message", newMessage);
				logger.info(" replyMessage newMessage:"+BeanUtils.getBeanString(newMessage));
				BuckWaResponse response = webboardTopicService.replyPBPMessage(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){		
				 
					mav.addObject("successCode", response.getSuccessCode()); 
					academicKPIUserMappingWrapper.setReplyMessage("");
					mav = viewWork(academicKPIUserMappingWrapper.getAcademicKPIUserMapping().getKpiUserMappingId()+"");	
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
