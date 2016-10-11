package com.buckwa.ws;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.admin.Holiday;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.pbp.RegPerson;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.ws.service.TimeTableWSService;


@Controller
@RequestMapping("/admin/exportUserFromReg")
@SessionAttributes(types = Holiday.class)
public class TimeTableWSController {
	
	
	private static Logger logger = LoggerFactory.getLogger(TimeTableWSController.class);
	
	@Autowired
	private TimeTableWSService timeTableWSService;
	
	@Autowired
    private PathUtil pathUtil;
 
	
	@RequestMapping(value="exportUser.htm", method = RequestMethod.GET)
	public void syncTimeTableYearSemester(@RequestParam("academicYear") String academicYear ,HttpServletRequest httpRequest, HttpServletResponse httpResponse) {	
		 
		ModelAndView mav = new ModelAndView();
		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.LEAVE_INIT);
		
		mav.setViewName("syncTimetableWS");		 
		BuckWaResponse response =  timeTableWSService.exportUser(academicYear);
		
		
		
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
	
	
 
	
	
	
}
