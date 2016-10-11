package com.buckwa.web.controller.pam.admin;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.pam.ImportData;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.FileUtils;

/**
 * 
 @Author : Kroekpong Sakulchai (Ball)
 @Create : Aug 5, 2012 10:15:14 PM
 *
 **/

@Controller
@RequestMapping("/admin/importdata/works")
@SessionAttributes(types = ImportData.class)
public class ImportWorksController {	
	
	@Autowired
    private PathUtil pathUtil;
	
	private static Logger logger = LoggerFactory.getLogger(ImportWorksController.class);	 	
	
	@RequestMapping(value="initWorks.htm", method = RequestMethod.GET)
	public ModelAndView initWorks() {
		logger.info(" ###  Start init Import Works ### ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("initImportWorks");  
		try{
			ImportData importData =  new ImportData();		
			mav.addObject("importData", importData);		 	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}		
		return mav;
	}
	
	@RequestMapping(value="initWorksHistory.htm", method = RequestMethod.GET)
	public ModelAndView initWorksHistory() {
		logger.info(" ###  Start init Works History  ### ");
		ModelAndView mav = new ModelAndView();		
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("initWorksHistory");  
		try{
			ImportData importData =  new ImportData();		
			mav.addObject("importData", importData);		 	
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}		
		return mav;
	}
	
	
	@RequestMapping(value="upload.htm")
	public ModelAndView uploadDocument(@ModelAttribute ImportData importData, BindingResult result  ,HttpServletRequest httpRequest ) {
		
		logger.info("---- Wait For Uploading File ----");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(BuckWaConstants.PAGE_SELECT, BuckWaConstants.ADMIN_INIT);
		mav.setViewName("initImportWorks");
		try{			 			 
			String fileName = null;	
			InputStream inputStream = null;
			OutputStream outputStream = null;
			MultipartFile  originalfile = importData.getFileData(); 
			
			logger.info(" originalfile size:"+originalfile.getSize()+" File Name:"+ originalfile.getOriginalFilename() );
			
			if (originalfile!=null&&originalfile.getSize() > 0) {
				inputStream = originalfile.getInputStream();
				if (originalfile.getSize() > pathUtil.getMaximumImageUploadSize()) {
					logger.info(" Error File Size: " + originalfile.getSize()+" Greater than :"+pathUtil.getMaximumImageUploadSize());					 
					mav.addObject("errorCode", "E013"); 
					return mav;
				}else{		
					mav.setViewName("initWorksHistory");
					
					String uploadPath =  pathUtil.getUploadPath()+"works/";
					fileName = originalfile.getOriginalFilename();
					
					FileUtils.createDirectoryIfNotExist(uploadPath);
					
					logger.info("## File Size :" + originalfile.getSize());
					logger.info("## File Name :" + fileName);
					logger.info("## Upload Path :" + uploadPath);
					
					fileName =uploadPath+fileName;
					
					logger.info("## File Name + Path :" + fileName);
					
					inputStream = originalfile.getInputStream();
					outputStream = new FileOutputStream(fileName);
					
					int readBytes = 0;
					byte[] buffer = new byte[10000];
					while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
						logger.info("---- Uploading ..... Sucess ----");
						outputStream.write(buffer, 0, readBytes);
					}
					outputStream.close();
					inputStream.close();
				}	 
			}else{					 
				mav.addObject("errorCode", "E012"); 
				return mav;					
			}			 			
		}catch(Exception ex){
			ex.printStackTrace();
			mav.addObject("errorCode", "E001"); 
		}		
		return mav;
	}	
		
	
}
