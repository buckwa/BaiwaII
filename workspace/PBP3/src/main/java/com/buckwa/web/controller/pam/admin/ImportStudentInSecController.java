package com.buckwa.web.controller.pam.admin;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.FileLocation;
import com.buckwa.domain.pam.ImportData;
import com.buckwa.service.intf.admin.AdminUserService;
import com.buckwa.service.intf.pam.FileLocationService;
import com.buckwa.service.intf.pam.ImportFileService;
import com.buckwa.service.intf.pam.LeaveTypeService;
import com.buckwa.service.intf.pam.YearService;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.ExcelUtil;
import com.buckwa.util.FileUtils;

/**
 * 
 @Author : Kroekpong Sakulchai (Ball)
 @Create : Aug 6, 2012 13:37:14 PM
 *
 **/

@Controller
@RequestMapping("/admin/importdata/studentInSec")
@SessionAttributes(types = ImportData.class)
public class ImportStudentInSecController {	
	
	@Autowired
    private PathUtil pathUtil;
	
	@Autowired
	private ImportFileService importProfileService;
	
	@Autowired
	private FileLocationService fileLocationService;
	
	 
	
	@Autowired
	private AdminUserService adminUserService;
	
	@Autowired
	private LeaveTypeService leaveTypeService;
	
	@Autowired
	private YearService yearService;
	
	private static Logger logger = LoggerFactory.getLogger(ImportStudentInSecController.class);	 	
	
	@RequestMapping(value="initProfile.htm", method = RequestMethod.GET)
	public ModelAndView initProfile() {
		logger.info(" ###  Start init Import Profile ### ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("initImportStudentInSec");  
		try{
			ImportData importData =  new ImportData();		
			mav.addObject("importData", importData);		 	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		
		return mav;
	}
 
	@RequestMapping(value="initImport.htm", method = RequestMethod.GET)
	public ModelAndView initImport(String fileName, String process) {
		logger.info(" ###  Start init Import Profile History List ### ");
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{
			ImportData importData =  new ImportData();
			PagingBean bean = new PagingBean();
			FileLocation fileLocation = new FileLocation();
			BuckWaRequest request = new BuckWaRequest();
			mav.addObject("importData", importData);	
			mav.addObject("pagingBean", bean);
			mav.setViewName("initImportStudentInSec");  
			
			int offset = 0;	
			bean.setOffset(offset);	
			mav.addObject("fileLocation", fileLocation);
			bean.put("fromTable", BuckWaConstants.PERSON_TABLE);
			
			// For search by file name !
			if(StringUtils.hasText(fileName)){
				logger.info(" >> Search By File Name Like => "+fileName);
				bean.put("fileName", fileName); // for test search
			}
			request.put("pagingBean", bean);
			
			// Map object to Front End
			BuckWaResponse response = fileLocationService.getByOffset(request);
			
			// Set output process message
			logger.info(" >> PROCESS => "+process);
			if(StringUtils.hasText(process)){
				if(process.equalsIgnoreCase(BuckWaConstants.PROCESS_IMPORT)){
					mav.addObject("successCode", BuckWaConstants.MSGCODE_IMPORT_SUCESS);
				}else if(process.equalsIgnoreCase(BuckWaConstants.PROCESS_DELETE)){
					mav.addObject("successCode", BuckWaConstants.MSGCODE_DELETE_SUCESS);
				}
				
				if(response.getStatus()==BuckWaConstants.SUCCESS){	
					logger.info(" Success ");
					PagingBean beanReturn = (PagingBean)response.getResObj("pagingBean");
					bean.setCurrentPageItem(beanReturn.getCurrentPageItem());
					bean.setTotalItems(beanReturn.getTotalItems());
					mav.addObject("pagingBean", bean);		
				}else {	 
					mav.addObject("errorCode", response.getErrorCode()); 
				}	
			}
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		return mav;
	}
	
	@RequestMapping(value="upload.htm")
	public ModelAndView uploadDocument(@ModelAttribute ImportData importData, BindingResult result  ,HttpServletRequest httpRequest ) {
	 
		logger.info("---- Wait For Uploading File ----");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("initImportStudentInSec");
		
		FileLocation fileLocation = new FileLocation();
		
		try{			 			 

			MultipartFile  originalfile = importData.getFileData(); 
			
			logger.info(" originalfile size:"+originalfile.getSize()+" File Name:"+ originalfile.getOriginalFilename() );
			
			if (originalfile!=null&&originalfile.getSize() > 0) {
				if (originalfile.getSize() > pathUtil.getMaximumImageUploadSize()) {
					logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumImageUploadSize());					 
					mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_TOO_LARGE); 
					return mav;
				}else{		
					
					//  For Upload File >>>>
					String uploadPath =  pathUtil.getUploadPath()+BuckWaConstants.PATH_UPLOAD_PROFILE;
					String file = originalfile.getOriginalFilename();
					String fileName = file.substring(0,file.lastIndexOf("."));
					String fileExtension = file.substring(file.lastIndexOf("."));
					String fileType = originalfile.getContentType();
					String fileSize = Long.toString(originalfile.getSize());
					
					// Add Data to Keep The Upload File Log...
					Date date = new Date();
					fileLocation.setFilePath(uploadPath);  
					fileLocation.setFromTable(BuckWaConstants.PERSON_TABLE);  
					fileLocation.setFileCode(BuckWaConstants.PERSON_CODE+(new Date().getTime())); 	// KP-FIX
					fileLocation.setFileName(fileName); 
					fileLocation.setFileType(fileType);
					fileLocation.setFileExtension(fileExtension);
					fileLocation.setFileSize(fileSize);
					fileLocation.setTopiclv1Id(null);  // KP-FIX
					fileLocation.setCreateBy(httpRequest.getUserPrincipal().getName());
					fileLocation.setCreateDate(new Timestamp(date.getTime()));
					fileLocation.setUpdateBy(httpRequest.getUserPrincipal().getName());
					fileLocation.setUpdateDate(new Timestamp(date.getTime()));
					fileLocation.setFileDesc(FileUtils.getFileDescription(fileLocation));   // KP-FIX
					
					logger.info("FILE >> "+fileLocation);
					logger.info("## File Size :" + originalfile.getSize());
					logger.info("## File Name Original :" + file);
					logger.info("## Upload Path :" + uploadPath);
					
					String fileUpload = uploadPath+file;
					
					logger.info("## File Name + Path :" + fileUpload);
					
					int step = 1 ; 
					boolean isnext = true;
					Workbook workbook = null;
					
					while(isnext){
						switch (step) {
						case 1 :
							logger.info("Step : "+step+" >>  Create New Upload Path");
							isnext = FileUtils.createDirectoryIfNotExist(uploadPath);
							if(isnext){
								step++; 
								continue;
							}else{
								isnext = false;
							}
						case 2 : 
							logger.info("Step : "+step+" >> Save File To Server directory path");
							
							boolean isFileNameExist = fileLocationService.checkFileNameServerExist(fileName,BuckWaConstants.PERSON_TABLE);
							if(!isFileNameExist){
								isnext = FileUtils.saveFileToDirectory(originalfile, fileUpload);
								if(isnext){
									step++; 
									continue;
								}else{
									isnext = false;
								}
							}else{
								isnext = false;
								mav.addObject("errorCode", BuckWaConstants.MSGCODE_FILE_NAME_EXIST); 
								return mav;
							}
						case 3 :
							logger.info(" Step : "+step+" >> Insert into File Location Database (table : file_location) For File Upload History");
							Long key = fileLocationService.create(fileLocation);
							if(isnext && null != key){
								step++; 
								continue;
							}else{
								isnext = false;
							}
						case 4 :
							logger.info(" Step : "+step+" >> Get Work Book From Excel File (That Complete Upload)");
							workbook = ExcelUtil.getWorkbook(fileUpload);
							if(isnext && workbook != null){
								step++; 
								continue;
							}else{
								isnext = false;
							}
						case 5 :
							logger.info(" Step : "+step+" >> Read Work Book and Insert User Profile into Person Database (table : person_pbp)");
							//importProfileService.readProfileWorkbook(workbook, httpRequest);
							importProfileService.readStudentInSecWorkbook (workbook, httpRequest);
							step++; 
						default:
							isnext = false;
						}
					}
					
					return initImport(null, BuckWaConstants.PROCESS_IMPORT);
				}		 
			}else{					 
				mav.addObject("errorCode", BuckWaConstants.MSGCODE_SELECT_FILE); 
				return mav;					
			}			 			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}		
		return null;
	}	
		
	@RequestMapping(value="delete.htm", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("fileCode") String fileCode ,HttpServletRequest httpRequest,
			@ModelAttribute FileLocation fileLocation ,@ModelAttribute PagingBean bean) {
		
		logger.info("#####  Start  Delete   << "+ fileCode +" >> #### ");
		
		ModelAndView mav = new ModelAndView();	
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		try{
			
			BuckWaRequest request = new BuckWaRequest();
			request.put("fileCode", fileCode);	
			BuckWaResponse response = fileLocationService.deleteFile(request);
			
			if(response.getStatus()==BuckWaConstants.SUCCESS){					
				mav.addObject("successCode",BuckWaConstants.MSGCODE_DELETE_SUCESS); 		 				
			}else {	
				mav.addObject("errorCode", response.getErrorCode()); 
				mav.addObject("pagingBean", bean);	
			}	
			
			// Search Again
			return initImport(null ,BuckWaConstants.PROCESS_DELETE);
			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001); 
		}
	
		return null;
	}	
	
	@RequestMapping(value="download.htm")
	public ModelAndView download(@RequestParam("fileCode") String fileCode ,HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		
		logger.info("#####  Start  Download   << "+ fileCode +" >> #### ");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		FileLocation fileLocation = new FileLocation();
		
		try {
			
			fileLocation = fileLocationService.findByFileCode(fileCode);
			
			logger.info("File >>"+fileLocation);
			
			String filePath = fileLocation.getFilePath();
			String fileName = fileLocation.getFileName()+fileLocation.getFileExtension();
			String fullPath = filePath+fileName;
			
			InputStream inputStream = new FileInputStream(fullPath);
			
			httpResponse.setContentType(fileLocation.getFileType());
			httpResponse.setContentLength(Integer.parseInt(fileLocation.getFileSize()));
			
			// Check For IE OR NOT for Encoder fileName !
			String user_agent = httpRequest.getHeader("user-agent");
			boolean isInternetExplorer = (user_agent.indexOf(BuckWaConstants.BROWSER_MSIE) > -1);
			if (isInternetExplorer) {
				logger.info("Hello You Are IE ");
				httpResponse.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"");
			} else {
				logger.info("Hello You Not IE ");
				httpResponse.setHeader("Content-disposition", "attachment; filename=\"" + MimeUtility.encodeWord(fileName) + "\"");
			}
			
			FileCopyUtils.copy( inputStream, httpResponse.getOutputStream());
			   
			httpResponse.flushBuffer();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", BuckWaConstants.ERROR_E001);
		}
		
		return null;

	}
	
	@RequestMapping(value="search.htm")
	public ModelAndView search(@RequestParam("fileName") String search, HttpServletRequest httpRequest) {
		
		logger.info("#####  Start  Search by param  << "+ search +" >> #### ");
		
		return initImport(search, BuckWaConstants.PROCESS_SEARCH);
	}
	
	
}
