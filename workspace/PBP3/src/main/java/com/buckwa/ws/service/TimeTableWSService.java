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
import com.buckwa.ws.newws.oxm.Department;
import com.buckwa.ws.newws.oxm.Faculty;
import com.buckwa.ws.newws.oxm.GetDepartmentList;
import com.buckwa.ws.newws.oxm.GetDepartmentListResponse;
import com.buckwa.ws.newws.oxm.GetFacultyList;
import com.buckwa.ws.newws.oxm.GetFacultyListResponse;
import com.buckwa.ws.newws.oxm.GetTeachTable;
import com.buckwa.ws.newws.oxm.GetTeachTableResponse;
import com.buckwa.ws.newws.oxm.GetTeacherList;
import com.buckwa.ws.newws.oxm.GetTeacherListResponse;
import com.buckwa.ws.newws.oxm.TeachTable;
import com.buckwa.ws.newws.oxm.TeacherList;
import com.buckwa.ws.oxm.GetListTeachers;
import com.buckwa.ws.oxm.GetListTeachersResponse;
import com.buckwa.ws.oxm.WrapperText;

@Service("timetableWSService") 
public class TimeTableWSService   {
	private static Logger logger = LoggerFactory.getLogger(TimeTableWSService.class);
	
	@Autowired
	private TimeTableWSDao timeTableWSDao;
	
	@Autowired
	private WebServiceTemplate  supportWSTemplate  ;
	
	@Autowired
	private WebServiceTemplate  facultyWSTemplate  ;
	
	
	@Autowired
	private WebServiceTemplate  teacherWSTemplate  ;
	

	
	
	@Autowired
	private WebServiceTemplate  teachTableWSTemplate  ;
	
	@Autowired
	private WebServiceTemplate  subjectServiceWSTemplate  ;	
	
	@Autowired
    private InstituteService instituteService;
	
	
	@Autowired
	private FacultyDao facultyDao;
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	
	
	public BuckWaResponse assignKPIInit() {
		BuckWaResponse response = new BuckWaResponse();
		logger.info(" ---- Start---" );
		
		facultyDao.assignKPIInit();
		
		logger.info(" ---- End---" );
		return response;
		
	}
 

	public BuckWaResponse syncTimeTableYearTerm(String academicYear,String semesterx) {
		logger.info(" #################### Sync. Webservice  academicYear:"+academicYear);
		BuckWaResponse response = new BuckWaResponse();
		try{		
			
			logger.info(" ################################# Delete Time Table and WORK ##########################");
			facultyDao.deleteTimeTableAndMapping(academicYear);;
			
			logger.info(" 1. Faculty ");
			logger.info(" 2.Department ");
			logger.info(" 3.Teacher");
			logger.info(" 4.TimeTable ");
			
			
			GetFacultyList request = new GetFacultyList();
			request.setFacultyId("");
			 
			logger.info(" ################################# Start get Faculty From WS ##########################");
			
			
			GetFacultyListResponse returnObj =(GetFacultyListResponse)facultyWSTemplate.marshalSendAndReceive(request, new WebServiceMessageCallback() {
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
			facultyDao.updateFacultyWS(facultyList);;			
			
			
			//logger.info(" ################################# Start get Dapartment From WS ##########################");
			
//			
//			for(Faculty facttmp:facultyList){
//                
//				GetDepartmentList departmentRequest = new GetDepartmentList();
//				departmentRequest.setFacultyId(facttmp.getFacultyId());		
//				
//				
//				GetDepartmentListResponse returnObjDepartment =(GetDepartmentListResponse)facultyWSTemplate.marshalSendAndReceive(departmentRequest, new WebServiceMessageCallback() {
//					public void doWithMessage(WebServiceMessage messagedepartment) throws IOException, TransformerException {
//						try {
//							SOAPMessage soapMessageDepartment = ((SaajSoapMessage)messagedepartment).getSaajMessage();				 
//					       ByteArrayOutputStream out = new ByteArrayOutputStream();
//					       soapMessageDepartment.writeTo(out);
//				            logger.info(" Department SOAP Request Payload: " + new String(out.toByteArray()));
//					         
//						} catch(Exception e) {
//							e.printStackTrace();
//						}
//					}
//				}); 
//
//				List<Department> departmentList =returnObjDepartment.getDepartment();
//
//				facultyDao.updateDepartmentWS(departmentList);;
//			
//			}
//			
			

			
			
			List<String> teacherIdList = new ArrayList();
			List<Teacher> teacherList  = new ArrayList();
			Map teacherIdMap = new HashMap();
			
			for(Faculty facttmp:facultyList){
				// For Debug  Engineering
				
				
				//if(!"01".equals(facttmp.getFacultyId())){  
					
				// }else{
					
				 
				String facultyCode = facttmp.getFacultyId();
				logger.info(" ################################# Start get Teacher From WS  Faculty  "+facultyCode+":"+facttmp.getFacultyTname());	
				
				GetDepartmentList departmentRequest = new GetDepartmentList();
				departmentRequest.setFacultyId(facttmp.getFacultyId());
				
				GetDepartmentListResponse returnObjDepartment =(GetDepartmentListResponse)facultyWSTemplate.marshalSendAndReceive(departmentRequest, new WebServiceMessageCallback() {
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
						logger.info(" ################################# Start get Teacher From WS  Department  "+deptmp.getDepartmentId()+":"+deptmp.getDepartmentName());	
					GetTeacherList teacherRequest = new GetTeacherList();
					teacherRequest.setFacultyId(facttmp.getFacultyId());
					teacherRequest.setDepartmentId(deptmp.getDepartmentId());
					
					
					GetTeacherListResponse returnObjTeacher =(GetTeacherListResponse)teacherWSTemplate.marshalSendAndReceive(teacherRequest, new WebServiceMessageCallback() {
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
			
			logger.info(" ################################# Start get TimeTable From WS Semester 1 degree 1 ##########################");			
			 getAndInsertTeachTableWS(1,1, teacherList);				
			logger.info(" ################################# Start get TimeTable From WS Semester 1 degree 2 ##########################");			
			getAndInsertTeachTableWS(1,2, teacherList);			
			logger.info(" ################################# Start get TimeTable From WS Semester 2 degree 1 ##########################");			
			 getAndInsertTeachTableWS(2,1, teacherList);				
			logger.info(" ################################# Start get TimeTable From WS Semester 2 degree 2 ##########################");			
			getAndInsertTeachTableWS(2,2, teacherList);	
			
	 
			
			
		
			 
			 
			logger.info(" ################################# Start Recalculate ##########################");
			
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
	
	
	
	public BuckWaResponse syncTimeTableYearTermRegId(String academicYear,String regId) {
		logger.info(" #################### Sync. Webservice  academicYear:"+academicYear);
		BuckWaResponse response = new BuckWaResponse();
		try{		
			
			String userName =schoolUtil.getUserNameFromRegId(regId,academicYear);
			facultyDao.deleteTimeTableAndMappingRegId(academicYear,regId,userName);;
			
		 
	 
			List<Teacher> teacherList  = new ArrayList();
		 
			String facultyCode =schoolUtil.getFacultyCodeFromRegId(regId,academicYear);
			
			Teacher pbpTeacher = new Teacher();
			pbpTeacher.setTeacherIdStr(regId);	
			pbpTeacher.setFacultyCode(facultyCode);
			pbpTeacher.setAcademicYear(academicYear);
			teacherList.add(pbpTeacher);
 
		 
			logger.info(" ################################# Start get TimeTable From WS Semester 1 degree 1 ##########################");			
			 getAndInsertTeachTableWS(1,1, teacherList);				
			logger.info(" ################################# Start get TimeTable From WS Semester 1 degree 2 ##########################");			
			getAndInsertTeachTableWS(1,2, teacherList);			
			logger.info(" ################################# Start get TimeTable From WS Semester 2 degree 1 ##########################");			
			 getAndInsertTeachTableWS(2,1, teacherList);				
			logger.info(" ################################# Start get TimeTable From WS Semester 2 degree 2 ##########################");			
			getAndInsertTeachTableWS(2,2, teacherList);	
			
	 
			
			
		
			 
			 
			logger.info(" ################################# Start Recalculate ##########################");
			
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
		
		logger.info(" ############ Start Insert TeachTable , academicYear="+academicYear );
		
		//if(teacherIdStr!=null&&teacherIdStr.length()>0){
		
		int loop =1;
		for(Teacher teacherTmp:teacherListIn){
			//
			//if(!"11215".equals(teacherTmp.getTeacherIdStr())){ // ktpitak
			//	if(!"10518".equals(teacherTmp.getTeacherIdStr())){
		//	if(!"31110".equals(teacherTmp.getTeacherIdStr())){ // อำนาจ
				
				
			//}else{
				
		 
			
			    String facultyCode = teacherTmp.getFacultyCode();
				GetTeachTable teachTableRequest = new GetTeachTable();
				teachTableRequest.setSemester(semester);
				teachTableRequest.setYear(teacherTmp.getAcademicYear());
				teachTableRequest.setTeacherId(teacherTmp.getTeacherIdStr());
				teachTableRequest.setDegree(degree); 
				
				GetTeachTableResponse returnObjTeachTable =(GetTeachTableResponse)teachTableWSTemplate.marshalSendAndReceive(teachTableRequest, new WebServiceMessageCallback() {
					public void doWithMessage(WebServiceMessage messageTeachTable) throws IOException, TransformerException {
						try {
							SOAPMessage soapMessageTeachTable = ((SaajSoapMessage)messageTeachTable).getSaajMessage();				 
					       ByteArrayOutputStream out = new ByteArrayOutputStream();
					       soapMessageTeachTable.writeTo(out);
				            logger.info(" messageTeachTable SOAP Request Payload: " + new String(out.toByteArray()));
					         
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
				
				logger.info(" Semester:"+semester+" degree:"+degree+"  "+teacherTmp.getTeacherIdStr()+":"+teacherTmp.getName()+"  TeachTableSize:"+teachSize +" facultyCode:"+facultyCode);
				int ttloop =1;
				for(TeachTable tttmp:teachTableResponseList){
					logger.info(ttloop+++" "+tttmp.getSubjectId());
				}
				
				facultyDao.updateTeachTableWS(semester,teachTableResponseList,degree,facultyCode,academicYear);
				 
				
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
			
			
			GetFacultyListResponse returnObj =(GetFacultyListResponse)facultyWSTemplate.marshalSendAndReceive(request, new WebServiceMessageCallback() {
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
				
				GetDepartmentListResponse returnObjDepartment =(GetDepartmentListResponse)facultyWSTemplate.marshalSendAndReceive(departmentRequest, new WebServiceMessageCallback() {
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
					
					
					GetTeacherListResponse returnObjTeacher =(GetTeacherListResponse)teacherWSTemplate.marshalSendAndReceive(teacherRequest, new WebServiceMessageCallback() {
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
		
	public BuckWaResponse syncTimeTable() {
		logger.info(" ---- Start---" );
		BuckWaResponse response = new BuckWaResponse();
		try{					
			// Get All Teacher
			// Get TimeTable By Teacher
			GetListTeachers request = new GetListTeachers();
			request.setFacultyId("07");
			request.setSemester("1");
			request.setYear("2557");
			GetListTeachersResponse returnObj =(GetListTeachersResponse)supportWSTemplate.marshalSendAndReceive(request);
			
			logger.info(" Return Str :"+ returnObj.getReturn());
			String originalStr = returnObj.getReturn();
			String afterRemoveHeader = originalStr.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>", "");
			//String afterAddRoot  = " <WrapperText  xmlns=\"http://reg.kmitl.ac.th\"> "+afterRemoveHeader+" </WrapperText>";
			String afterAddRoot  = " <WrapperText> "+afterRemoveHeader+" </WrapperText>";
			
			logger.info(" afterAddRoot Str :"+ afterAddRoot);
			
		    JAXBContext jaxbContext = JAXBContext.newInstance(WrapperText.class);
		    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		     
		    //StringReader reader = new StringReader(afterAddRoot);
		   // WrapperText wrapperText = (WrapperText) jaxbUnmarshaller.unmarshal(reader );
		  //  logger.info(" Objec after unmashall :"+ BeanUtils.getBeanString(wrapperText));
		    
		    
		    XMLReader reader = XMLReaderFactory.createXMLReader();
		    JAXBContext jc = JAXBContext.newInstance("com.buckwa.ws.oxm");
		    Unmarshaller u = jc.createUnmarshaller();

		    //Create an XMLReader to use with our filter
		  //  XMLReader reader2 = XMLReaderFactory.createXMLReader();

		    //Create the filter (to add namespace) and set the xmlReader as its parent.
		    NamespaceFilter inFilter = new NamespaceFilter("http://reg.kmitl.ac.th", true);
		    inFilter.setParent(reader);

		    //Prepare the input, in this case a java.io.File (output)
		    StringReader stringReader = new StringReader(afterAddRoot);
		    InputSource is = new InputSource(stringReader);

		    //Create a SAXSource specifying the filter
		    SAXSource source = new SAXSource(inFilter, is);

		    //Do unmarshalling
		    Object myJaxbObject = u.unmarshal(source);
		    
		    logger.info(" myJaxbObject Str :"+ BeanUtils.getBeanString(myJaxbObject));
			//timeTableWSDao.syncTimeTable();
			
			logger.info(" ---- End With Success---" );
		}catch(Exception ex){
			logger.info(" ---- End With Error---" );
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
}
