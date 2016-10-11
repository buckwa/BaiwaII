package com.buckwa.ws;

import static com.googlecode.charts4j.Color.BLACK;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.admin.Holiday;
import com.buckwa.domain.admin.HolidayCriteria;
import com.buckwa.domain.admin.Menu;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pbp.AcademicPerson;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.FacultyWrapper;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.service.intf.pbp.HeadService;
import com.buckwa.service.intf.pbp.PBPWorkTypeService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;
import com.buckwa.ws.service.TimeTableWSService;
import com.buckwa.ws.service.TimeTableWSServiceChum;
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
@RequestMapping("/admin/timetablews")
@SessionAttributes(types = Holiday.class)
public class ExportUserFromRegController {
	
	
	private static Logger logger = LoggerFactory.getLogger(ExportUserFromRegController.class);
	
	@Autowired
	private TimeTableWSService timeTableWSService;
	
	@Autowired
	private TimeTableWSServiceChum timeTableWSServiceChum;
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	@Autowired
	private FacultyService facultyService;	
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
	private HeadService headService;
	
	@Autowired
	private PBPWorkTypeService pBPWorkTypeService;
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initCreate() {			
	 
		ModelAndView mav = new ModelAndView();
		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.LEAVE_INIT);
		
		mav.setViewName("syncTimetableWS");		
 	
	
		return mav;
	}	
	
	@RequestMapping(value="initRegId.htm", method = RequestMethod.GET)
	public ModelAndView initSyncTimeTableByRegId() {			
	 
		ModelAndView mav = new ModelAndView();
		
		Person syncTimeTablePerson = new Person();
		mav.addObject("syncTimeTablePerson", syncTimeTablePerson);	
		mav.setViewName("syncTimetableWSRegId");		
 	
	
		return mav;
	}	
	
	
	
	@RequestMapping(value="initRegId.htm", method = RequestMethod.POST)
	public ModelAndView initSyncTimeTableByRegIdPOST(@ModelAttribute("syncTimeTablePerson") Person syncTimeTablePerson) {			
	 
		ModelAndView mav = new ModelAndView();
		System.out.println(" #### initSyncTimeTableByRegIdPOST RegId:"+syncTimeTablePerson.getRegId());;
		
		if(syncTimeTablePerson!=null&&syncTimeTablePerson.getRegId().trim().length()>0){
			System.out.println(" #### Call Sync TimeTable Service###");
			String academicYear =schoolUtil.getCurrentAcademicYear();
			BuckWaResponse response =  timeTableWSService.syncTimeTableYearTermRegId(academicYear,syncTimeTablePerson.getRegId());
		}
		 
		mav.addObject("syncTimeTablePerson", syncTimeTablePerson);	
		mav.setViewName("syncTimetableWSRegId");		
 	
	
		return mav;
	}	
	
	@RequestMapping(value="initChum.htm", method = RequestMethod.GET)
	public ModelAndView initChum() {			
	 
		ModelAndView mav = new ModelAndView();
		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.LEAVE_INIT);
		
		mav.setViewName("syncTimetableWSChum");		
 	
	
		return mav;
	}	
	
	
	@RequestMapping(value="syncTimeTableYearSemester.htm", method = RequestMethod.GET)
	public ModelAndView syncTimeTableYearSemester(@RequestParam("academicYear") String academicYear,@RequestParam("semester") String semester) {			
		 
		ModelAndView mav = new ModelAndView();
		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.LEAVE_INIT);
		
		mav.setViewName("syncTimetableWS");		 
		BuckWaResponse response =  timeTableWSService.syncTimeTableYearTerm(academicYear, semester);
 	
		return mav;
	}	
	
	@RequestMapping(value="syncTimeTableYearSemesterChum.htm", method = RequestMethod.GET)
	public ModelAndView syncTimeTableYearSemesterChum(@RequestParam("academicYear") String academicYear,@RequestParam("semester") String semester) {			
		 
		ModelAndView mav = new ModelAndView();
		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.LEAVE_INIT);
		
		mav.setViewName("syncTimetableWS");		 
		BuckWaResponse response =  timeTableWSServiceChum.syncTimeTableYearTerm(academicYear, semester);
 	
		return mav;
	}	
		
	
	@RequestMapping(value="assignKPIInit.htm", method = RequestMethod.GET)
	public ModelAndView assignKPIInit( ) {			
		 
		ModelAndView mav = new ModelAndView();
		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.LEAVE_INIT);
		
		mav.setViewName("welcome");		 
		BuckWaResponse response =  timeTableWSService.assignKPIInit( );
 	
		return mav;
	}	
	
	
	@RequestMapping(value="syncTimeTable.htm", method = RequestMethod.GET)
	public ModelAndView syncTimeTableYearSemester() {			
	 
		ModelAndView mav = new ModelAndView();
		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.LEAVE_INIT);
		
		mav.setViewName("syncTimetableWS");		
		HolidayCriteria holidayCriteria = new HolidayCriteria();
		
		BuckWaResponse response =  timeTableWSService.syncTimeTable();
		 
		
 	
	
		return mav;
	}	
	
	
	
	@RequestMapping(value="recalculateInit.htm", method = RequestMethod.GET)
	public ModelAndView recalculateInit() {			
	 
		ModelAndView mav = new ModelAndView();
		
		BuckWaRequest request = new BuckWaRequest();
		String academicYear =schoolUtil.getCurrentAcademicYear();
		request.put("academicYear",academicYear);
		BuckWaResponse response = facultyService.getByAcademicYear(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			FacultyWrapper facultyWrapper = (FacultyWrapper)response.getResObj("facultyWrapper");
		 
			facultyWrapper.setAcademicYear(academicYear);
			facultyWrapper.setAcademicYearSelect(academicYear);
			mav.addObject("facultyWrapper", facultyWrapper);	
			facultyWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
		}	
		
		
		mav.setViewName("recalculate");		
 
 	
	
		return mav;
	}
	@RequestMapping(value="recalculate.htm", method = RequestMethod.GET)
	public ModelAndView recalculate(@RequestParam("academicYear") String academicYear) {			
	 
		ModelAndView mav = new ModelAndView();
		
		BuckWaResponse response =  timeTableWSService.recalculate(academicYear);
		
		mav.setViewName("recalculate");		
 
 	
	
		return mav;
	}
	
	@RequestMapping(value="recalculateByDepartment.htm", method = RequestMethod.GET)
	public ModelAndView recalculateByDepartment(@RequestParam("headUserName") String headUserName ) {			
	 
		ModelAndView mav = new ModelAndView();
		BuckWaRequest request = new BuckWaRequest();
		String academicYear =schoolUtil.getCurrentAcademicYear();
		request.put("headUserName",headUserName);
		request.put("academicYear",academicYear);
		request.put("status",""); 
		
		 		 
		BuckWaResponse response = headService.getDepartmentMarkAllYear(request);
		
		
	 
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			Department department = (Department)response.getResObj("department"); 
			department.setAcademicYear(academicYear);
			 request = new BuckWaRequest(); 
			 String facultyCode =department.getFacultyCode();
			request.put("academicYear",academicYear);
			request.put("facultyCode",facultyCode);
			 //response = pBPWorkTypeService.getByAcademicYear(request);
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
			     int totalPerson =0;
			     for(AcademicPerson personTmp: academicPersonListMark){
			    	 
			    	List<PBPWorkType> pBPWorkTypeListTotalMark	= personTmp.getpBPWorkTypeWrapper().getpBPWorkTypeList();
			    	
			    	if(pBPWorkTypeListTotalMark!=null){
			    		
			    	
				      for(PBPWorkType totalMarkTmp:pBPWorkTypeListTotalMark){
				    	//  System.out.print(" totalMarkTmp id:"+totalMarkTmp.getWorkTypeId());
				    	  
				    	  if(typeTmp.getWorkTypeId().intValue()==totalMarkTmp.getWorkTypeId().intValue()){ 
				  			
					 
								//totalMark = totalMark.add(totalMarkTmp.getTotalInPercentCompareBaseWorkType());
								totalMark = totalMark.add(totalMarkTmp.getTotalInWorkType());
								typeTmp.setTotalInWorkType(totalMark);
								 logger.info(" Controller TotalInWorkType:"+totalMarkTmp.getTotalInWorkType());
				    	  }
				    	  
				      }
				      
			    	}
				      totalPerson++;  
				       
			     }
			     
			     
		 
			     
			     if(totalPerson==0){
			    	 totalPerson=1;
			     }
			     logger.info(" totalmark Faculty:"+totalMark+"  totalperson:"+totalPerson);
			   //  BigDecimal totalInPercentCompareBaseWorkTypeAVG = totalMark.divide(new BigDecimal(totalPerson) ,2, BigDecimal.ROUND_HALF_UP);

			     typeTmp.setTotalAllWorkType(totalMark); 
			    // typeTmp.setTotalInPercentCompareBaseWorkType(totalMark);
			    // typeTmp.setTotalInPercentCompareBaseWorkTypeAVG(totalInPercentCompareBaseWorkTypeAVG);
			 
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
			
			
			List<BigDecimal> pointList  = new ArrayList();
			for(PBPWorkType typeTmp:pBPWorkTypeList){
	 
				//pointList.add(typeTmp.getTotalInPercentCompareBaseWorkType()) ;
				pointList.add(typeTmp.getTotalInPercentCompareBaseWorkTypeAVG()) ;
	 
		
			}
			
			BigDecimal maxPoint =Collections.max(pointList);
			
			
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
		        
		        logger.info("typeTmp.getTotalInPercentCompareBaseWorkTypeAVG():"+typeTmp.getTotalInPercentCompareBaseWorkTypeAVG()+" data1:"+data1+"  maxPoint:"+ maxPoint);
		     //   BigDecimal weight100  = typeTmp.getTotalInPercentCompareBaseWorkType().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_UP).divide(maxPoint).setScale(2,BigDecimal.ROUND_UP);
		      //  BigDecimal weight100AVG  = typeTmp.getTotalInPercentCompareBaseWorkTypeAVG().multiply(new BigDecimal(100)).divide(maxPoint,2, BigDecimal.ROUND_HALF_UP).setScale(0,BigDecimal.ROUND_UP);

		     
		      //  data1 =weight100;
		      //  data1 =weight100AVG;
		        data1=typeTmp.getTotalInPercentCompareBaseWorkTypeAVG();
		       
		        // if(loop==0){
		        //	data1 =typeTmp.getTotalInPercentCompareBaseWorkType().multiply(new BigDecimal(2)).setScale(0,BigDecimal.ROUND_UP);
		       // }
				
				loop++;
				//dataList.add(typeTmp.getTotalInPercentCompareBaseWorkType() .multiply(new BigDecimal(2)).setScale(0,BigDecimal.ROUND_UP));
						
				dataList.add(data1);
			}
			
		
			
			
			logger.info(" Data List Value  ");
			for(Number datax :dataList){
				logger.info("    "+datax);
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
		
		
		
		
		
		
		
		
		
		mav.addObject("successCode", "S100"); 
		mav.setViewName("recalculate");		
 
 	
	
		return mav;
	}
	
	
	
	@RequestMapping(value="recalculateAllDepartment.htm", method = RequestMethod.GET)
	public ModelAndView recalculateAllDepartment(  ) {			
	 
		ModelAndView mav = new ModelAndView();
		
		BuckWaRequest request = new BuckWaRequest();
		String academicYear =schoolUtil.getCurrentAcademicYear();
		request.put("academicYear",academicYear);
		BuckWaResponse response = facultyService.getByAcademicYear(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){	
			FacultyWrapper facultyWrapper = (FacultyWrapper)response.getResObj("facultyWrapper");
			
			List<Faculty> facultyLsit  =facultyWrapper.getFacultyList();
			
			for(Faculty facTmp:facultyLsit){
				
				List<Department>  departmentList =facTmp.getDepartmentList();
				
				for(Department depTmp:departmentList){
					
					String headEmail =depTmp.getHead().getEmail();
					
					
					 
					request.put("headUserName",headEmail);
					request.put("academicYear",academicYear);
					request.put("status",""); 
					
					response = headService.getDepartmentMarkAllYear(request);
					
					
	
					
					 
					if(response.getStatus()==BuckWaConstants.SUCCESS){	
						Department department = (Department)response.getResObj("department"); 
						department.setAcademicYear(academicYear);
						 request = new BuckWaRequest(); 
						 String facultyCode =department.getFacultyCode();
						request.put("academicYear",academicYear);
						request.put("facultyCode",facultyCode);
						 //response = pBPWorkTypeService.getByAcademicYear(request);
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
						     int totalPerson =0;
						     for(AcademicPerson personTmp: academicPersonListMark){
						    	 
						    	List<PBPWorkType> pBPWorkTypeListTotalMark	= personTmp.getpBPWorkTypeWrapper().getpBPWorkTypeList();
						    	
						    	if(pBPWorkTypeListTotalMark!=null){
						    		
						    	
							      for(PBPWorkType totalMarkTmp:pBPWorkTypeListTotalMark){
							    	//  System.out.print(" totalMarkTmp id:"+totalMarkTmp.getWorkTypeId());
							    	  
							    	  if(typeTmp.getWorkTypeId().intValue()==totalMarkTmp.getWorkTypeId().intValue()){ 
							  			
								 
											//totalMark = totalMark.add(totalMarkTmp.getTotalInPercentCompareBaseWorkType());
											totalMark = totalMark.add(totalMarkTmp.getTotalInWorkType());
											typeTmp.setTotalInWorkType(totalMark);
											 logger.info(" Controller TotalInWorkType:"+totalMarkTmp.getTotalInWorkType());
							    	  }
							    	  
							      }
							      
						    	}
							      totalPerson++;  
							       
						     }
						     
						     
					 
						     
						     if(totalPerson==0){
						    	 totalPerson=1;
						     }
						     logger.info(" totalmark Faculty:"+totalMark+"  totalperson:"+totalPerson);
						   //  BigDecimal totalInPercentCompareBaseWorkTypeAVG = totalMark.divide(new BigDecimal(totalPerson) ,2, BigDecimal.ROUND_HALF_UP);

						     typeTmp.setTotalAllWorkType(totalMark); 
						    // typeTmp.setTotalInPercentCompareBaseWorkType(totalMark);
						    // typeTmp.setTotalInPercentCompareBaseWorkTypeAVG(totalInPercentCompareBaseWorkTypeAVG);
						 
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
					}
	 
					
					
				}
				
			}
 
		}	
		
		

		mav.addObject("successCode", "S100"); 
		mav.setViewName("recalculate");		
 
 	
	
		return mav;
	}
	
}
