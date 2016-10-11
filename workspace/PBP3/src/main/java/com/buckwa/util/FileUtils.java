package com.buckwa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.buckwa.domain.pam.FileLocation;

public class FileUtils extends org.apache.commons.io.FileUtils {
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

	public static boolean saveFileToDirectory(MultipartFile file,String destFileNamePath) throws Exception {
		boolean result = false;
		try {
			String fileName = destFileNamePath;
			InputStream inputStream = file.getInputStream();
			OutputStream outputStream = new FileOutputStream(fileName);
			int readBytes = 0;
			byte[] buffer = new byte[10000];
			while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
				//logger.info("---- Uploading ..... Sucess ----");
				outputStream.write(buffer, 0, readBytes);
			}
			inputStream.close();
			outputStream.close();

			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return result;
	}

	public static String copyTempImageToRealPath(String tempPath,
			String destpath, String fileName) throws Exception {
		String returnFialname = null;
		logger.info(" copyTempImageToRealPath tmpPath:" + tempPath + " destpath:" + destpath + " filename:" + fileName);
		try {
			String tempFileName = tempPath + fileName;
			String destFileName = destpath + fileName;
			FileInputStream fis = new FileInputStream(tempFileName);

			File file = new File(destFileName);
			boolean createNewFile = file.createNewFile();
			logger.info(" createNewFile:" + createNewFile);
			FileOutputStream fos = new FileOutputStream(destFileName);
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
			fis.close();
			fos.close();

			returnFialname = destFileName;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return returnFialname;
	}

	public static String getFileExtension(String fileName) {
		String fname = "";
		String ext = "";
		try {
			int mid = fileName.lastIndexOf(".");
			fname = fileName.substring(0, mid);
			ext = fileName.substring(mid + 1, fileName.length());

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return ext;

	}
	
	public static String getFileDescription(FileLocation fileLocation) {
		Date date = fileLocation.getCreateDate();
		String pattern = "[ File Name : '"+fileLocation.getFileName()+"' " +
				", File Extension : '"+fileLocation.getFileExtension()+"' " +
				", File Size : "+fileLocation.getFileSize()+" kb " +
				", Create Date : "+BuckWaDateUtils.get_ddmmyyyy_time_thai_from_date(date)+" ]";
		
		return pattern;
		
	}

	public static boolean createDirectoryIfNotExist(String dirPath) {

		boolean returnResult = false;
		try {
			logger.info(" directory path:" + dirPath);
			File directory = new File(dirPath);

			boolean exists = (directory.exists());
			if (exists) {
				returnResult = true;
			} else {
				directory.mkdirs();
				returnResult = true;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return returnResult;

	}

	public static void main(String arg[]) {
		String filepath = "D:/Project/Baiwa/vision/34/";

		FileUtils test = new FileUtils();
		test.createDirectoryIfNotExist(filepath);

	}

}
