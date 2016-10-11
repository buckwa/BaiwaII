package com.buckwa.web.controller.huaywa;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.util.Files;
import com.buckwa.domain.util.UploadItem;
import com.buckwa.service.intf.util.FilesService;
import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.FileUtils;
import com.buckwa.web.controller.admin.RoleManagementController;

@Controller
@RequestMapping("/updown")
@SessionAttributes(types = UploadItem.class)
public class UploadDownloadController {
	private static Logger logger = LoggerFactory.getLogger(UploadDownloadController.class);
 
	@Autowired
	private FilesService filesService;	
	
	
	@Autowired
    private PathUtil pathUtil;
	
	@RequestMapping(value = "initupdownAndroid.htm", method = RequestMethod.GET)
	public ModelAndView initupdownAndroid() {
		logger.info(" ### initupdownAndroid.htm ## ");
		ModelAndView mav = new ModelAndView();
		PagingBean bean = new PagingBean();
		mav.setViewName("initupdownAndroid");
		mav.addObject("updownItem", new UploadItem());
		List<String> filenames = new ArrayList();
		try {
			String adroidPath = pathUtil.getUploadPath() + "android";
			logger.info(" ### initupdownAndroid.htm ## adroidPath: "+adroidPath);
			File folder = new File(adroidPath);
			File[] listOfFiles = folder.listFiles();
			
			if(listOfFiles!=null){				
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						filenames.add(listOfFiles[i].getName());
						logger.info("  ### File Name:"+listOfFiles[i].getName());
					}
				}				
			}

			mav.addObject("filenames", filenames);
		
			
		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}
		return mav;
	}

	@RequestMapping(value = "uploadAndroid.htm",method = RequestMethod.POST)
	public ModelAndView uploadAndroid(@ModelAttribute UploadItem updownItem ) {
		logger.info(" ### uploadAndroid.htm ## ");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("initupdownAndroid");
		InputStream inputStream = null;
		try {
			logger.info(" ### uploadAndroid.htm uploadItem: "+updownItem);
			String adroidPath = pathUtil.getUploadPath() + "android";
			MultipartFile originalfile = updownItem.getFileData();
			if (originalfile == null || originalfile.getSize() > 0) {
				inputStream = originalfile.getInputStream();
				if (originalfile.getSize() > pathUtil.getMaximumUploadSize()) {
					logger.info(" Error File Size: "
							+ originalfile.getSize() + " Greater than :"
							+ pathUtil.getMaximumUploadSize());
					mav.addObject("errorCode", "E013");
					return mav;
				}
				String originalFileExtension = FileUtils		.getFileExtension(originalfile.getOriginalFilename());
				//FileUtils.saveFileToDirectory(originalfile, adroidPath,originalFileExtension);
				FileUtils.saveFileToDirectory(originalfile, adroidPath );
			} else {
				mav.addObject("errorCode", "E012");
				return mav;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			mav.addObject("errorCode", "E001");
		}

		return mav;
	}
	
	
	@RequestMapping(value = "download.htm")
	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("filePath") String filePath ) throws Exception {		
		 
		logger.info(" ## download filePath:"+filePath );
		
		//String filePathDownlaod =getFilePathDownlaod(type)+filePath;
		Files file = filesService.getFileByPath(filePath);		 
		response.setContentType(file.getType());
		response.setContentLength(file.getFile().length);
		response.setHeader("Content-Disposition", "attachment; filename=\""	+ file.getFilename() + "\"");
		FileCopyUtils.copy(file.getFile(), response.getOutputStream());
		return null;
	}
	
	
	private String getFilePathDownlaod(String downloadType){
		
		String returnStr = "";
		try{
			if("android".equals(downloadType)){
				returnStr =pathUtil.getUploadPath() + "android"+"/";
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return returnStr;
	}

}
