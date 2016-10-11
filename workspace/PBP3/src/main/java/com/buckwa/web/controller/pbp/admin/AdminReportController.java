package com.buckwa.web.controller.pbp.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.AcademicPerson;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.FacultyWrapper;
import com.buckwa.domain.pbp.RegPerson;
import com.buckwa.domain.validator.pbp.DepartmentValidator;
import com.buckwa.domain.validator.pbp.FacultyValidator;
import com.buckwa.service.intf.pbp.FacultyService;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.school.SchoolUtil;
import com.buckwa.web.util.AcademicYearUtil;

@Controller
@RequestMapping("/admin/pbp/report")
@SessionAttributes({"facultyWrapper","faculty","department"} ) 
public class AdminReportController {	
	private static Logger logger = LoggerFactory.getLogger(AdminReportController.class);	 
	@Autowired
	private FacultyService facultyService;	
	
	@Autowired
	private SchoolUtil schoolUtil;
	
	@Autowired
    private PathUtil pathUtil;
 
	@Autowired
	private AcademicYearUtil academicYearUtil;
	
	
	@RequestMapping(value="init.htm", method = RequestMethod.GET)
	public ModelAndView initList() {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("adminReportList");
		try{
			
			BuckWaRequest request = new BuckWaRequest();
			String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
			BuckWaResponse response = facultyService.getByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				FacultyWrapper facultyWrapper = (FacultyWrapper)response.getResObj("facultyWrapper");
				
				List<Faculty> facList =facultyWrapper.getFacultyList();
				for(Faculty facTmp:facList){
					
					String facCode  = facTmp.getCode();
					String facName = facTmp.getName();
					
					System.out.println(facCode+":"+facName);
					
					List<Department> departmentList =facTmp.getDepartmentList();
					for(Department depTmp:departmentList){
						
						String depCode  = depTmp.getCode();
						String depName = depTmp.getName();
						System.out.println("   "+depCode+":"+depName);
					}
					
				}
			 
				facultyWrapper.setAcademicYear(academicYear);
				facultyWrapper.setAcademicYearSelect(academicYear);
				mav.addObject("facultyWrapper", facultyWrapper);	
				facultyWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
			}	
			
			 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	
	@RequestMapping(value="exportExcel.htm", method = RequestMethod.GET)
	public ModelAndView exportExcel(@RequestParam("academicYear") String academicYear,HttpServletRequest httpRequest, HttpServletResponse httpResponse) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("adminReportList");
		try{
			
			BuckWaRequest request = new BuckWaRequest();
			//String academicYear =schoolUtil.getCurrentAcademicYear();
			request.put("academicYear",academicYear);
			BuckWaResponse response = facultyService.getAllMarkByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				FacultyWrapper facultyWrapper = (FacultyWrapper)response.getResObj("facultyWrapper");
			 
				facultyWrapper.setAcademicYear(academicYear);
				facultyWrapper.setAcademicYearSelect(academicYear);
				mav.addObject("facultyWrapper", facultyWrapper);	
				facultyWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
	 
			
			
			 
			try{
				String filePath =pathUtil.getDocPath();
				
				 String fileName ="รายงานคะแนนบุคลากรแต่ละด้านทั้งสถาบัน.xlsx";
				 String fileNameOut ="รายงานคะแนนบุคลากรแต่ละด้านทั้งสถาบัน_out.xlsx";
				String fullPath =  filePath+fileName;
				String fullPathtOut = filePath+fileNameOut;				
				
				XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(fullPath));
			 
				//HSSFWorkbook workbook = new  HSSFWorkbook(fs, true);
		
				XSSFSheet sheet = workbook.getSheetAt(0);
				

				 
			 // FileInputStream inputStream = new FileInputStream(new File(fullPath));

		        // create data rows
		        int rowCount = 2;
		        int indexofRow =1;
		        
				List<Faculty> facList =facultyWrapper.getFacultyList();
				for(Faculty facTmp:facList){
					
					String facCode  = facTmp.getCode();
					String facName = facTmp.getName();
					
		            

 
					System.out.println(facCode+":"+facName);
					
					List<Department> departmentList =facTmp.getDepartmentList();
					for(Department depTmp:departmentList){
						
						List<AcademicPerson> personList =depTmp.getAcademicPersonList();
						
						for(AcademicPerson personTmp :personList){
							XSSFRow aRow = sheet.getRow(rowCount++);
							String depCode  = depTmp.getCode();
							String depName = depTmp.getName();
							System.out.println("   "+depCode+":"+depName);
				            aRow.getCell(0).setCellValue(indexofRow++);
				            aRow.getCell(1).setCellValue(facName);
				            aRow.getCell(2).setCellValue(depName);	
				            aRow.getCell(3).setCellValue(personTmp.getThaiName()+" "+personTmp.getThaiSurname());
				            aRow.getCell(4).setCellValue(personTmp.getEmail());
				            aRow.getCell(5).setCellValue(personTmp.getEmployeeType());	
				            
				            
				            aRow.getCell(6).setCellValue(personTmp.getMark1());	
				            aRow.getCell(7).setCellValue(personTmp.getMark2());	
				            aRow.getCell(8).setCellValue(personTmp.getMark3());	
				            aRow.getCell(9).setCellValue(personTmp.getMark4());	
				            aRow.getCell(10).setCellValue(personTmp.getMark5());	
				            aRow.getCell(11).setCellValue(personTmp.getTotalMark()+"");	
				            
						}

			  
					}
					
				}
		         

		
		 
				
				// Check For IE OR NOT for Encoder fileName !
				String user_agent = httpRequest.getHeader("user-agent");
				boolean isInternetExplorer = (user_agent.indexOf(BuckWaConstants.BROWSER_MSIE) > -1);
				if (isInternetExplorer) {
					 
					httpResponse.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"");
				} else {
					 
					httpResponse.setHeader("Content-disposition", "attachment; filename=\"" + MimeUtility.encodeWord(fileName) + "\"");
				}
				
			     
				  workbook.write(httpResponse.getOutputStream());
				 
				
			//	httpResponse.flushBuffer();
				
				
				
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			
		}
			
			 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	
	
	public void syncTimeTableYearSemester(@RequestParam("academicYear") String academicYear ,HttpServletRequest httpRequest, HttpServletResponse httpResponse) {	
		 
		ModelAndView mav = new ModelAndView();
		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.LEAVE_INIT);
		
		mav.setViewName("syncTimetableWS");		 
		BuckWaResponse response =  null;
		
		
		
		List<RegPerson>  regPersonList  =(List<RegPerson>)response.getResObj("regPersonList");
		 
		try{
			
			String filePath =pathUtil.getDocPath();
			
			 String fileName ="RegUser.xlsx";
			 String fileNameOut ="RegUserOut.xlsx";
			String fullPath =  filePath+fileName;
			String fullPathtOut = filePath+fileNameOut;
			 
		  FileInputStream inputStream = new FileInputStream(new File(fullPath));
		  
		  XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
	        // create a new Excel sheet
	        XSSFSheet sheet = workbook.createSheet("Java Books");
	        sheet.setDefaultColumnWidth(30);
	         
	        // create style for header cells
	        CellStyle style = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setFontName("Arial");
	        style.setFillForegroundColor(HSSFColor.BLUE.index);
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        font.setColor(HSSFColor.WHITE.index);
	        style.setFont(font);
	         
	        // create header row
	        XSSFRow header = sheet.createRow(0);
	         
	        header.createCell(0).setCellValue("No");
	        header.getCell(0).setCellStyle(style);
	         
	        header.createCell(1).setCellValue("Reg Id");
	        header.getCell(1).setCellStyle(style);
	         
	        header.createCell(2).setCellValue("Name");
	        header.getCell(2).setCellStyle(style);
	         
	        header.createCell(3).setCellValue("Faculty Code");
	        header.getCell(3).setCellStyle(style);
	         
	        header.createCell(4).setCellValue("FacultyName");
	        header.getCell(4).setCellStyle(style);
	        
	        header.createCell(5).setCellValue("Department Code");
	        header.getCell(5).setCellStyle(style);
	        
	        header.createCell(6).setCellValue("Department Name");
	        header.getCell(6).setCellStyle(style);
	         
	        // create data rows
	        int rowCount = 1;
	         
	        for (RegPerson regTmp : regPersonList) {
	        	System.out.println(" ### Create Excel row :"+rowCount);
	            XSSFRow aRow = sheet.createRow(rowCount);
	            aRow.createCell(0).setCellValue(rowCount);
	            aRow.createCell(1).setCellValue(regTmp.getRegId());
	            aRow.createCell(2).setCellValue(regTmp.getFirstLastName());
	            aRow.createCell(3).setCellValue(regTmp.getFacultyCode());
	            aRow.createCell(4).setCellValue(regTmp.getFacultyName());
	            aRow.createCell(5).setCellValue(regTmp.getDepartmentCode());
	            aRow.createCell(6).setCellValue(regTmp.getDepartmentName());
	            
	            rowCount++;
	        }
	 
		    FileOutputStream out = 
			        new FileOutputStream(new File(fullPathtOut));
			    workbook.write(out);
			    out.close();
	 
			
			// Check For IE OR NOT for Encoder fileName !
			String user_agent = httpRequest.getHeader("user-agent");
			boolean isInternetExplorer = (user_agent.indexOf(BuckWaConstants.BROWSER_MSIE) > -1);
			if (isInternetExplorer) {
				 
				httpResponse.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"");
			} else {
				 
				httpResponse.setHeader("Content-disposition", "attachment; filename=\"" + MimeUtility.encodeWord(fileName) + "\"");
			}
			
			FileCopyUtils.copy( inputStream, httpResponse.getOutputStream());
			   
			
		//	httpResponse.flushBuffer();
			
			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
 	 
	}	
	
	@RequestMapping(value="getFacultyByYear.htm", method = RequestMethod.GET)
	public ModelAndView getFacultyByYear(@RequestParam("academicYearSelect") String academicYearSelect) {
		logger.info(" Start  academicYearSelect:"+academicYearSelect);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("facultyList");
		try{
			BuckWaRequest request = new BuckWaRequest();
			 
			request.put("academicYear",academicYearSelect);
			BuckWaResponse response = facultyService.getByAcademicYear(request);
			if(response.getStatus()==BuckWaConstants.SUCCESS){	
				FacultyWrapper facultyWrapper = (FacultyWrapper)response.getResObj("facultyWrapper");
			 
				facultyWrapper.setAcademicYear(academicYearSelect);
				facultyWrapper.setAcademicYearSelect(academicYearSelect);
				mav.addObject("facultyWrapper", facultyWrapper);	
				facultyWrapper.setAcademicYearList(academicYearUtil.getAcademicYearList());
			}	
			
			 
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}
		return mav;
	}	

	@RequestMapping(value="create.htm", method = RequestMethod.GET)
	public ModelAndView init(@RequestParam("academicYearSelect") String academicYearSelect) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("facultyCreate");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		
	 
		Faculty faculty = new Faculty();
		faculty.setAcademicYear(academicYearSelect);
		mav.addObject("faculty", faculty);	
		return mav;
	}		

	@RequestMapping(value="create.htm", method = RequestMethod.POST)
	public ModelAndView createRole(@ModelAttribute Faculty faculty, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("facultyCreate");
		try{			
			new FacultyValidator().validate(faculty, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("faculty", faculty);
				BuckWaResponse response = facultyService.create(request);
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
	
	@RequestMapping(value="createDepartment.htm", method = RequestMethod.GET)
	public ModelAndView createDepartment(@RequestParam("facultyId") String facultyId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("departmentCreate");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("facultyId", facultyId);
		BuckWaResponse response = facultyService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Faculty faculty = (Faculty)response.getResObj("faculty");
			faculty.setDepartment(new Department()) ;
			mav.addObject("faculty", faculty); 
			 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}	
 
		return mav;
	}	
		
	@RequestMapping(value="createDepartment.htm", method = RequestMethod.POST)
	public ModelAndView createDepartment(HttpServletRequest httpRequest,@ModelAttribute Faculty faculty, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("facultyList");
		try{			
			new DepartmentValidator().validate(faculty.getDepartment(), result);			
			if (result.hasErrors()) {				
				mav.setViewName("departmentCreate");
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("faculty", faculty);
				BuckWaResponse response = facultyService.createDepartment(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){							 
					mav.addObject("successCode", response.getSuccessCode()); 
					String url = httpRequest.getContextPath() + "/admin/pbp/faculty/init.htm";
					logger.info(" Redirect URL:"+url);
					 
					mav.setView(new RedirectView(url)); 				
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
	 
	
	@RequestMapping(value="editFaculty.htm", method = RequestMethod.GET)
	public ModelAndView editFaculty(@RequestParam("facultyId") String facultyId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("facultyEdit");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("facultyId", facultyId);
		BuckWaResponse response = facultyService.getById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Faculty faculty = (Faculty)response.getResObj("faculty"); 
			mav.addObject("faculty", faculty); 
			 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}	
 
		return mav;
	}	
	
	
	@RequestMapping(value="editFaculty.htm", method = RequestMethod.POST)
	public ModelAndView editFacultyPOST(@ModelAttribute Faculty faculty, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("facultyEdit");
		try{			
			new FacultyValidator().validate(faculty, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("faculty", faculty);
				BuckWaResponse response = facultyService.updateFaculty(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){							 
					mav.addObject("successCode", response.getSuccessCode()); 
					mav =initList(); 				
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
	
	
	
	
	@RequestMapping(value="editDepartment.htm", method = RequestMethod.GET)
	public ModelAndView editDepartment(@RequestParam("departmentId") String departmentId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("departmentEdit");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("departmentId", departmentId);
		BuckWaResponse response = facultyService.getDepartmentById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			Department department = (Department)response.getResObj("department"); 
			mav.addObject("department", department);  
		}else {
			mav.addObject("errorCode", response.getErrorCode());  
		}	 
		return mav;
	}	
	
	
	@RequestMapping(value="editDepartment.htm", method = RequestMethod.POST)
	public ModelAndView editDepartmentPOST(@ModelAttribute Department department, BindingResult result) {	
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("departmentEdit");
		try{			
			new DepartmentValidator().validate(department, result);			
			if (result.hasErrors()) {				
				 
			}else {					
				BuckWaRequest request = new BuckWaRequest();
				request.put("department", department);
				BuckWaResponse response = facultyService.updateDepartment(request);
				if(response.getStatus()==BuckWaConstants.SUCCESS){							 
					mav.addObject("successCode", response.getSuccessCode()); 
					mav =initList(); 				
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
	
	
	
	@RequestMapping(value="deleteFacultyById.htm", method = RequestMethod.GET)
	public ModelAndView deleteFacultyById(@RequestParam("facultyId") String facultyId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("facultyEdit");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("facultyId", facultyId);
		BuckWaResponse response = facultyService.deleteFacultyById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			 
			mav = initList();
			 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}	
 
		return mav;
	}
	
	
	@RequestMapping(value="deleteDepartmentById.htm", method = RequestMethod.GET)
	public ModelAndView deleteDepartmentById(@RequestParam("departmentId") String departmentId) {
		logger.info(" Start  ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("departmentEdit");
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);		 
		BuckWaRequest request = new BuckWaRequest();
		request.put("departmentId", departmentId);
		BuckWaResponse response = facultyService.deleteDepartmentById(request);
		if(response.getStatus()==BuckWaConstants.SUCCESS){		
			 
			mav = initList();
			 				
		}else {
			mav.addObject("errorCode", response.getErrorCode()); 
			 
		}	
 
		return mav;
	}
	
}
