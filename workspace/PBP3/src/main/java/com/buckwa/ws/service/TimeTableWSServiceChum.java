package com.buckwa.ws.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.buckwa.dao.impl.uitl.RejectSubjectUtil;
import com.buckwa.dao.intf.pbp.FacultyDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.RegPerson;
import com.buckwa.domain.pbp.Teacher;
import com.buckwa.service.intf.pbp.InstituteService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.ws.dao.TimeTableWSDao;
import com.buckwa.ws.chum.oxm.Department;
import com.buckwa.ws.chum.oxm.Faculty;
import com.buckwa.ws.chum.oxm.GetDepartmentList;
import com.buckwa.ws.chum.oxm.GetDepartmentListResponse;
import com.buckwa.ws.chum.oxm.GetFacultyList;
import com.buckwa.ws.chum.oxm.GetFacultyListResponse;
import com.buckwa.ws.chum.oxm.GetTeachTable;
import com.buckwa.ws.chum.oxm.GetTeachTableResponse;
import com.buckwa.ws.chum.oxm.GetTeacherList;
import com.buckwa.ws.chum.oxm.GetTeacherListResponse;
import com.buckwa.ws.chum.oxm.TeachTable;
import com.buckwa.ws.chum.oxm.TeacherList;
 

@Service("timetableWSServiceChum") 
public class TimeTableWSServiceChum   {
	private static Logger logger = LoggerFactory.getLogger(TimeTableWSServiceChum.class);
	
 
	
	@Autowired
	private WebServiceTemplate  facultyWSTemplateChum  ;
	
	
	@Autowired
	private WebServiceTemplate  teacherWSTemplateChum  ;
	
	@Autowired
	private RejectSubjectUtil rejectSubjectUtil;
	
	
	@Autowired
	private WebServiceTemplate  teachTableWSTemplateChum  ;
	
	@Autowired
	private WebServiceTemplate  subjectServiceWSTemplateChum  ;	
	
	@Autowired
    private InstituteService instituteService;
	
	
	@Autowired
	private FacultyDao facultyDao;
	
	@Autowired
	private SchoolUtil schoolUtil;
	
 

	public BuckWaResponse syncTimeTableYearTerm(String academicYear,String semesterx) {
		logger.info(" #################### Sync. Webservice Chum academicYear:"+academicYear);
		BuckWaResponse response = new BuckWaResponse();
		try{		
			
			
			facultyDao.deleteTimeTableAndMappingChum(academicYear);;
			
			logger.info(" 1. Faculty ");
			logger.info(" 2.Department ");
			logger.info(" 3.Teacher");
			logger.info(" 4.TimeTable ");
			
			
			GetFacultyList request = new GetFacultyList();
			request.setFacultyId("");
			 
			logger.info(" ################################# Start get Faculty From WS  Chum ##########################");
			
			
			GetFacultyListResponse returnObj =(GetFacultyListResponse)facultyWSTemplateChum.marshalSendAndReceive(request, new WebServiceMessageCallback() {
				public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
					try {
						SOAPMessage soapMessage = ((SaajSoapMessage)message).getSaajMessage();
				        SOAPHeader header = soapMessage.getSOAPHeader();
				        SOAPBody soapbody =soapMessage.getSOAPBody();
				       ByteArrayOutputStream out = new ByteArrayOutputStream();
			            message.writeTo(out);
			           // logger.info(" SOAP Request Payload: " + new String(out.toByteArray()));
				         
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}); 

			List<Faculty> facultyList =returnObj.getFaculty();
			
			//facultyDao.updateFacultyWSChum(facultyList);;		
			
			
			for(Faculty facttmp:facultyList){
          
				
			logger.info(" Faculty :"+facttmp.getFacultyTname());
			GetDepartmentList departmentRequest = new GetDepartmentList();
			departmentRequest.setFacultyId(facttmp.getFacultyId());		
			
			
			GetDepartmentListResponse returnObjDepartment =(GetDepartmentListResponse)facultyWSTemplateChum.marshalSendAndReceive(departmentRequest, new WebServiceMessageCallback() {
				public void doWithMessage(WebServiceMessage messagedepartment) throws IOException, TransformerException {
					try {
						SOAPMessage soapMessageDepartment = ((SaajSoapMessage)messagedepartment).getSaajMessage();				 
				       ByteArrayOutputStream out = new ByteArrayOutputStream();
				       soapMessageDepartment.writeTo(out);
			           // logger.info(" Department SOAP Request Payload: " + new String(out.toByteArray()));
				         
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}); 

			List<Department> departmentList =returnObjDepartment.getDepartment();
			
			for(Department deptmp:departmentList){
				
				logger.info(" Department :"+deptmp.getDepartmentName());
			
			GetTeacherList teacherRequest = new GetTeacherList();
			teacherRequest.setFacultyId(facttmp.getFacultyId());
			teacherRequest.setDepartmentId(deptmp.getDepartmentId());
			
			
			GetTeacherListResponse returnObjTeacher =(GetTeacherListResponse)teacherWSTemplateChum.marshalSendAndReceive(teacherRequest, new WebServiceMessageCallback() {
				public void doWithMessage(WebServiceMessage messageTeacher) throws IOException, TransformerException {
					try {
						SOAPMessage soapMessageTeacher = ((SaajSoapMessage)messageTeacher).getSaajMessage();				 
				       ByteArrayOutputStream out = new ByteArrayOutputStream();
				     //  soapMessageTeacher.writeTo(out);
			         //   logger.info(" Department SOAP Request Payload: " + new String(out.toByteArray()));
				         
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}); 	
			
			List<TeacherList> teacherListWS =returnObjTeacher.getTeacherList();
			//logger.info(" ################### Faculty : "+facttmp.getFacultyId()+":"+facttmp.getFacultyTname());
			//logger.info(" ######  Department : "+deptmp.getDepartmentId()+" : "+deptmp.getDepartmentName()+" : "+deptmp.getDepartmentEname());
			int teacherLoop =1;
			for(TeacherList teacherListWSTmp:teacherListWS){
				
				logger.info(teacherLoop++ +"--->"+teacherListWSTmp.getTeacherId()+":"+teacherListWSTmp.getTeacherTname());
			}
			
			}
			

			//facultyDao.updateDepartmentWSChum(departmentList);;
		
		}
			 	
			
			
			List<String> teacherIdList = new ArrayList();
			List<Teacher> teacherList  = new ArrayList();
			Map teacherIdMap = new HashMap();
			
			for(Faculty facttmp:facultyList){
				// For Debug  Engineering
				
				
				//if(!"01".equals(facttmp.getFacultyId())){  
					
				// }else{
					
				 
				String facultyCode = facttmp.getFacultyId();
				logger.info(" ################################# Start get Teacher From WS  Faculty  Chum  "+facultyCode+":"+facttmp.getFacultyTname());	
				
				GetDepartmentList departmentRequest = new GetDepartmentList();
				departmentRequest.setFacultyId(facttmp.getFacultyId());
				
				GetDepartmentListResponse returnObjDepartment =(GetDepartmentListResponse)facultyWSTemplateChum.marshalSendAndReceive(departmentRequest, new WebServiceMessageCallback() {
					public void doWithMessage(WebServiceMessage messagedepartment) throws IOException, TransformerException {
						try {
							SOAPMessage soapMessageDepartment = ((SaajSoapMessage)messagedepartment).getSaajMessage();				 
					       ByteArrayOutputStream out = new ByteArrayOutputStream();
					     //  soapMessageDepartment.writeTo(out);
				         //   logger.info(" Department SOAP Request Payload: " + new String(out.toByteArray()));
					         
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}); 
 
				List<Department> departmentList =returnObjDepartment.getDepartment();
 
				for(Department deptmp:departmentList){					
					//logger.info(deptmp.getDepartmentId()+" : "+deptmp.getDepartmentName()+" : "+deptmp.getDepartmentEname());
					
					//if( !"12".equals(deptmp.getDepartmentId())){  // 05: com, 12:สาระสนเทศ
					//	logger.info(" #############  Skip Department :"+deptmp.getDepartmentId()+ ":"+deptmp.getDepartmentEname()); 
					//}else{
						
					//	logger.info(" #############  Work only on  Department :"+deptmp.getDepartmentId()+ ":"+deptmp.getDepartmentEname()); 
						logger.info(" ################################# Start get Teacher From WS  Department  Chum  "+deptmp.getDepartmentId()+":"+deptmp.getDepartmentName());	
					GetTeacherList teacherRequest = new GetTeacherList();
					teacherRequest.setFacultyId(facttmp.getFacultyId());
					teacherRequest.setDepartmentId(deptmp.getDepartmentId());
					
					
					GetTeacherListResponse returnObjTeacher =(GetTeacherListResponse)teacherWSTemplateChum.marshalSendAndReceive(teacherRequest, new WebServiceMessageCallback() {
						public void doWithMessage(WebServiceMessage messageTeacher) throws IOException, TransformerException {
							try {
								SOAPMessage soapMessageTeacher = ((SaajSoapMessage)messageTeacher).getSaajMessage();				 
						       ByteArrayOutputStream out = new ByteArrayOutputStream();
						     //  soapMessageTeacher.writeTo(out);
					         //   logger.info(" Department SOAP Request Payload: " + new String(out.toByteArray()));
						         
							} catch(Exception e) {
								e.printStackTrace();
							}
						}
					}); 	
					
					List<TeacherList> teacherListWS =returnObjTeacher.getTeacherList();
					//logger.info(" ################### Faculty : "+facttmp.getFacultyId()+":"+facttmp.getFacultyTname());
					//logger.info(" ######  Department : "+deptmp.getDepartmentId()+" : "+deptmp.getDepartmentName()+" : "+deptmp.getDepartmentEname());
					for(TeacherList teacherListWSTmp:teacherListWS){
						
					//	logger.info(teacherListWSTmp.getTeacherId()+":"+teacherListWSTmp.getTeacherTname()+" :"+teacherListWSTmp.getTeacherEname());
						if(teacherListWSTmp.getTeacherId()==null){
							
						}else{
							teacherIdList.add(teacherListWSTmp.getTeacherId());
							teacherIdMap.put(teacherListWSTmp.getTeacherId(), teacherListWSTmp.getTeacherId());	
							
							Teacher pbpTeacher = new Teacher();
							pbpTeacher.setTeacherIdStr(teacherListWSTmp.getTeacherId());
							pbpTeacher.setFacultyCode(facultyCode);
							pbpTeacher.setAcademicYear(academicYear);
							teacherList.add(pbpTeacher);
							pbpTeacher.setName(teacherListWSTmp.getTeacherTname());
						}

					}
					
			}// debug Department
			 
				
			//}//Debug Faculty
	
					
			}
			 
			//}
			
			
			logger.info(" ########  Found Teacher Id Size:"+teacherIdList.size());
			logger.info(" ########  Found Teacher Unique Id Size:"+teacherIdMap.size());
			
			logger.info(" ################################# Start get TimeTable From WS  Chum Semester 1 degree 1 ##########################");			
			 getAndInsertTeachTableWS(1,1, teacherList);				
			logger.info(" ################################# Start get TimeTable From WS  Chum Semester 1 degree 2 ##########################");			
			getAndInsertTeachTableWS(1,2, teacherList);			
			logger.info(" ################################# Start get TimeTable From WS  Chum Semester 2 degree 1 ##########################");			
			 getAndInsertTeachTableWS(2,1, teacherList);				
			logger.info(" ################################# Start get TimeTable From WS  Chum Semester 2 degree 2 ##########################");			
			getAndInsertTeachTableWS(2,2, teacherList);	
			
	 
			
			
		
			 
			 
			logger.info(" ################################# Start Recalculate  Chum ##########################");
			
			//recalculate(academicYear);
			 
			logger.info(" ---- End With Success---" );
		}catch(Exception ex){
			logger.info(" ---- End With Error---" );
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	} 
	
	
	private void getAndInsertTeachTableWS(int semester,int degree ,List<Teacher> teacherListIn){
		 
		String academicYear = schoolUtil.getCurrentAcademicYear();
		
		logger.info(" ############ Start Insert TeachTable  Chum , academicYear="+academicYear );
		
		//if(teacherIdStr!=null&&teacherIdStr.length()>0){
		
		int loop =1;
		for(Teacher teacherTmp:teacherListIn){
			//
			//if(!"11215".equals(teacherTmp.getTeacherIdStr())){ // ktpitak
			//	if(!"10518".equals(teacherTmp.getTeacherIdStr())){
		//	if(!"31110".equals(teacherTmp.getTeacherIdStr())){ // อำนาจ
				
				
			//}else{
			 
			Map regIdMap =rejectSubjectUtil.getChumRegIdMappingMap();
			String newRegId = "0";
			try{
				
			 
			  newRegId = (String)regIdMap.get(teacherTmp.getTeacherIdStr());
			  logger.info(" ############ Teacher Id Str:"+teacherTmp.getTeacherIdStr()+"  after get key "+newRegId );
			}catch(Exception ex){
				ex.printStackTrace();
			}
		 
			
			    String facultyCode = teacherTmp.getFacultyCode();
				GetTeachTable teachTableRequest = new GetTeachTable();
				teachTableRequest.setSemester(semester);
				teachTableRequest.setYear(teacherTmp.getAcademicYear());
				teachTableRequest.setTeacherId(teacherTmp.getTeacherIdStr());
				teachTableRequest.setDegree(degree); 
				
				GetTeachTableResponse returnObjTeachTable =(GetTeachTableResponse)teachTableWSTemplateChum.marshalSendAndReceive(teachTableRequest, new WebServiceMessageCallback() {
					public void doWithMessage(WebServiceMessage messageTeachTable) throws IOException, TransformerException {
						try {
							SOAPMessage soapMessageTeachTable = ((SaajSoapMessage)messageTeachTable).getSaajMessage();				 
					       ByteArrayOutputStream out = new ByteArrayOutputStream();
					       //soapMessageTeachTable.writeTo(out);
				           // logger.info(" messageTeachTable SOAP Request Payload: " + new String(out.toByteArray()));
					         
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}); 	
				
				List<TeachTable> teachTableResponseList =returnObjTeachTable.getTeachTable(); 
				
				int teachSize = 0;
				if(teachTableResponseList!=null&&teachTableResponseList.size()>0){
					teachSize = teachTableResponseList.size();
				}
				
				logger.info(" Semester:"+semester+" degree:"+degree+"  "+teacherTmp.getTeacherIdStr()+":"+teacherTmp.getName()+"  TeachTableSize:"+teachSize );
		 
				facultyDao.updateTeachTableWSChum(semester,teachTableResponseList,degree,facultyCode,academicYear,newRegId);
				 
				
			}
			

	//	}	 // Debug User
		

		
	}
	
	
	
	public BuckWaResponse recalculate(String academicYear) {
		logger.info(" ---- Start academicYear:"+academicYear );
		BuckWaResponse response = new BuckWaResponse();
		try{					
		 
			BuckWaRequest request = new BuckWaRequest();
			request.put("academicYear", academicYear);;
			response =instituteService.recalculate(request);
 
			
			
		}catch(Exception ex){
			logger.info(" ---- End With Error---" );
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	
	public BuckWaResponse exportUser(String academicYear ) {
		logger.info(" #################### exportUser academicYear:"+academicYear);
		BuckWaResponse response = new BuckWaResponse();
		try{		
	 
			
			List<RegPerson> regPersonList = new ArrayList();
			
			GetFacultyList request = new GetFacultyList();
			request.setFacultyId("");
			 
			logger.info(" ################################# Start get Faculty From WS ##########################");
			
			
			GetFacultyListResponse returnObj =(GetFacultyListResponse)facultyWSTemplateChum.marshalSendAndReceive(request, new WebServiceMessageCallback() {
				public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
					try {
						SOAPMessage soapMessage = ((SaajSoapMessage)message).getSaajMessage();
				        SOAPHeader header = soapMessage.getSOAPHeader();
				        SOAPBody soapbody =soapMessage.getSOAPBody();
				       ByteArrayOutputStream out = new ByteArrayOutputStream();
			            message.writeTo(out);
			            logger.info(" SOAP Request Payload: " + new String(out.toByteArray()));
				         
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}); 

			List<Faculty> facultyList =returnObj.getFaculty();
		 		
			
			
			logger.info(" ################################# Start get Dapartment From WS ##########################");
			
 
			

			logger.info(" ################################# Start get Teacher From WS  ##########################");
			
			List<String> teacherIdList = new ArrayList();
			List<Teacher> teacherList  = new ArrayList();
			Map teacherIdMap = new HashMap();
			
			for(Faculty facttmp:facultyList){
				// For Debug  Engineering
			//	if(!"01".equals(facttmp.getFacultyId())){  
					
			//	 }else{
					
				String facultyCode = facttmp.getFacultyId();
				
				GetDepartmentList departmentRequest = new GetDepartmentList();
				departmentRequest.setFacultyId(facttmp.getFacultyId());
				
				GetDepartmentListResponse returnObjDepartment =(GetDepartmentListResponse)facultyWSTemplateChum.marshalSendAndReceive(departmentRequest, new WebServiceMessageCallback() {
					public void doWithMessage(WebServiceMessage messagedepartment) throws IOException, TransformerException {
						try {
							SOAPMessage soapMessageDepartment = ((SaajSoapMessage)messagedepartment).getSaajMessage();				 
					       ByteArrayOutputStream out = new ByteArrayOutputStream();
					     //  soapMessageDepartment.writeTo(out);
				         //   logger.info(" Department SOAP Request Payload: " + new String(out.toByteArray()));
					         
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}); 
 
				List<Department> departmentList =returnObjDepartment.getDepartment();
 
				for(Department deptmp:departmentList){					
					logger.info(deptmp.getDepartmentId()+" : "+deptmp.getDepartmentName()+" : "+deptmp.getDepartmentEname());
					
				//	if( !"12".equals(deptmp.getDepartmentId())){  // 05: com, 12:สาระสนเทศ
					//	logger.info(" #############  Skip Department :"+deptmp.getDepartmentId()+ ":"+deptmp.getDepartmentEname()); 
				//	}else{
						
						logger.info(" #############  Work only on  Department :"+deptmp.getDepartmentId()+ ":"+deptmp.getDepartmentEname()); 
					
					GetTeacherList teacherRequest = new GetTeacherList();
					teacherRequest.setFacultyId(facttmp.getFacultyId());
					teacherRequest.setDepartmentId(deptmp.getDepartmentId());
					
					
					GetTeacherListResponse returnObjTeacher =(GetTeacherListResponse)teacherWSTemplateChum.marshalSendAndReceive(teacherRequest, new WebServiceMessageCallback() {
						public void doWithMessage(WebServiceMessage messageTeacher) throws IOException, TransformerException {
							try {
								SOAPMessage soapMessageTeacher = ((SaajSoapMessage)messageTeacher).getSaajMessage();				 
						       ByteArrayOutputStream out = new ByteArrayOutputStream();
						     //  soapMessageTeacher.writeTo(out);
					         //   logger.info(" Department SOAP Request Payload: " + new String(out.toByteArray()));
						         
							} catch(Exception e) {
								e.printStackTrace();
							}
						}
					}); 	
					
					List<TeacherList> teacherListWS =returnObjTeacher.getTeacherList();
					logger.info(" ################### Faculty : "+facttmp.getFacultyId()+":"+facttmp.getFacultyTname());
					logger.info(" ######  Department : "+deptmp.getDepartmentId()+" : "+deptmp.getDepartmentName()+" : "+deptmp.getDepartmentEname());
					for(TeacherList teacherListWSTmp:teacherListWS){
						
						logger.info(teacherListWSTmp.getTeacherId()+":"+teacherListWSTmp.getTeacherTname()+" :"+teacherListWSTmp.getTeacherEname());
						if(teacherListWSTmp.getTeacherId()==null){
							
						}else{
							teacherIdList.add(teacherListWSTmp.getTeacherId());
							teacherIdMap.put(teacherListWSTmp.getTeacherId(), teacherListWSTmp.getTeacherId());	
							
							Teacher pbpTeacher = new Teacher();
							pbpTeacher.setTeacherIdStr(teacherListWSTmp.getTeacherId());
							pbpTeacher.setFacultyCode(facultyCode);
							pbpTeacher.setAcademicYear(academicYear);
							teacherList.add(pbpTeacher);
							
							RegPerson regPersonTmp = new RegPerson();
							regPersonTmp.setRegId(teacherListWSTmp.getTeacherId());
							regPersonTmp.setFacultyCode(facultyCode);
							regPersonTmp.setFacultyName(facttmp.getFacultyTname());
							regPersonTmp.setDepartmentCode(deptmp.getDepartmentId());
							regPersonTmp.setDepartmentName(deptmp.getDepartmentName());
							regPersonTmp.setFirstName(teacherListWSTmp.getTeacherTname());
							regPersonTmp.setFirstLastName(teacherListWSTmp.getTeacherTname());
							//regPersonTmp.setCitizenId(teacherListWSTmp.get);
							
							regPersonList.add(regPersonTmp);
							
						}

					}
					
			// }// debug Department
				}
				
			// }//Debug Faculty
			}
			
			
			response.addResponse("regPersonList", regPersonList);
						
 
		}catch(Exception ex){
			logger.info(" ---- End With Error---" );
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	} 
		
	
}
