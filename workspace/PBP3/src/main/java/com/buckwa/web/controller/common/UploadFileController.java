package com.buckwa.web.controller.common;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.buckwa.domain.common.UploadItem;
import com.buckwa.web.controller.admin.RoleManagementController;

@Controller
@RequestMapping(value = "/uploadfile")
public class UploadFileController {
	private static Logger logger = LoggerFactory.getLogger(UploadFileController.class);
	private String uploadFolderPath;
	ServletConfig config;

	public String getUploadFolderPath() {
		return uploadFolderPath;
	}

	public void setUploadFolderPath(String uploadFolderPath) {

		this.uploadFolderPath = uploadFolderPath;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getUploadForm(Model model) {
		model.addAttribute(new UploadItem());
		return "/uploadfile";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(UploadItem uploadItem, HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors,
			HttpSession session) {
		try {
			MultipartFile filea = uploadItem.getFileData();
			InputStream inputStream = null;
			OutputStream outputStream = null;
			if (filea.getSize() > 0) {
				inputStream = filea.getInputStream();
				// File realUpload = new File("C:/") ;
				outputStream = new FileOutputStream("C:\\test111\\"
						+ filea.getOriginalFilename());
				logger.info("====22=========");
				logger.info(filea.getOriginalFilename());
				logger.info("=============");
				int readBytes = 0;
				byte[] buffer = new byte[8192];
				while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
					logger.info("===ddd=======");
					outputStream.write(buffer, 0, readBytes);
				}
				outputStream.close();
				inputStream.close();
				session.setAttribute("uploadFile",
						"C:\\test111\\" + filea.getOriginalFilename());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/forms/uploadfileindex";
	}

}