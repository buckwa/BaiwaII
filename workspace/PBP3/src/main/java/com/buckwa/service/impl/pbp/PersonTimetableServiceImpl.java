package com.buckwa.service.impl.pbp;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pbp.FacultyDao;
import com.buckwa.dao.intf.pbp.PersonTimetableDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.report.TimeTableReport;
import com.buckwa.service.intf.pbp.PersonTimeTableService;
import com.buckwa.util.BuckWaConstants;

@Service("personTimetableService")
 
public class PersonTimetableServiceImpl implements PersonTimeTableService {
	private static Logger logger = LoggerFactory.getLogger(PersonTimetableServiceImpl.class);
	
	@Autowired
	private PersonTimetableDao personTimetableDao;
 
	@Autowired
	private FacultyDao facultyDao;
	@Override	
	public BuckWaResponse getTimeTable(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
	 
		try{				 
			
			String userName = (String)request.get("userName");
			String academicYear = (String)request.get("academicYear");
			String semester = (String)request.get("semester");
			
			List<TimeTableReport> timeTableReportList  =personTimetableDao.getTimeTable(academicYear, userName, semester);
		 
			System.out.println(timeTableReportList);
			
			for (TimeTableReport timeTableReport : timeTableReportList) {
				if(timeTableReport.getTeachtimeStr()!=null&&!timeTableReport.getTeachtimeStr().isEmpty()){
					
					String[] parts = timeTableReport.getTeachtimeStr().split(",");
					String result ="";
					for (String string : parts) {
						String partSum ="";
//						System.out.println(string);
						String[] parts2 = string.split("x");
						if(parts2[0]!=null&&!parts2[0].isEmpty()){
//							if(parts2[0].equals("1")){partSum="อาทิตย์";
//							}else if(parts2[0].equals("2")){partSum="จันทร์";
//							}else if(parts2[0].equals("3")){partSum="อังคาร";
//							}else if(parts2[0].equals("4")){partSum="พุธ";
//							}else if(parts2[0].equals("5")){partSum="พฤหัส.";
//							}else if(parts2[0].equals("6")){partSum="ศุกร์";
//							}else if(parts2[0].equals("7")){partSum="เสาร์";}
							partSum=parts2[0]+" "+parts2[1];
						}
						result = result+","+partSum;
					}
					timeTableReport.setTeachtimeStr(result);
					System.out.println(timeTableReport.getTeachtimeStr());
				}
				
			}
			
			response.addResponse("timeTableReportList",timeTableReportList);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	 
	@Override	
	public BuckWaResponse getTimeTableShsre(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
	 
		try{				 
			
			String subjectId = (String)request.get("subjectId");
			String academicYear = (String)request.get("academicYear");
			String semester = (String)request.get("semester");
			
			List<TimeTableReport> timeTableReportList  =personTimetableDao.getTimeTableShare(academicYear, subjectId, semester);
		 
			 response.addResponse("timeTableReportList",timeTableReportList);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	@Override	
	public BuckWaResponse getTimeTableById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse(); 
		try{				 
			
			String timetableId = (String)request.get("timetableId"); 
			TimeTableReport timeTableReport  =personTimetableDao.getTimeTableById(timetableId); 
			 response.addResponse("timeTableReport",timeTableReport); 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	@Override	
	public BuckWaResponse updateTimeTable(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse(); 
		try{				 			
			TimeTableReport timeTableReport = (TimeTableReport)request.get("timeTableReport"); 			  
			personTimetableDao.updateTimeTable(timeTableReport);
			response.setSuccessCode("S002");	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	 
		return response;
	}
	@Override	
	public BuckWaResponse createTimeTable(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse(); 
		try{				 			
			TimeTableReport timeTableReport = (TimeTableReport)request.get("timeTableReport"); 			  
			personTimetableDao.createTimeTable(timeTableReport);
			response.setSuccessCode("S001");	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	 
		return response;
	}
	
	
	
	
	@Override	
	public BuckWaResponse addShareSubject(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
	 
		try{				 
			
			TimeTableReport timeTableReport = (TimeTableReport)request.get("timeTableReport");
			String facultyCode = "01";
			
			 facultyDao.addShareSubject(timeTableReport,facultyCode);
		 
			 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
	
	
	
}
