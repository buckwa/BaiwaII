package com.buckwa.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 @Author : Kroekpong Sakulchai (Ball)
 @Create : Aug 5, 2012 10:15:14 PM
 *
 **/

public class ExcelUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	
	private static final String FILE_TYPE_EXCEL_XLS = "xls";
	private static final String FILE_TYPE_EXCEL_XLSX = "xlsx";
	
	public static Workbook getWorkbook(String filePath) {

		String fileType = filePath.substring(filePath.lastIndexOf(".") + 1);
		logger.info("fileType: " + fileType);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Workbook workBook = null;
		
		try {
			if (fileType.equalsIgnoreCase(FILE_TYPE_EXCEL_XLS)) {
				workBook = new HSSFWorkbook(inputStream);
			} else if (fileType.equalsIgnoreCase(FILE_TYPE_EXCEL_XLSX)) {
				workBook = new XSSFWorkbook(inputStream);
			}else{
				logger.info("-----  It's not an Excel File -----");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return workBook;
	}
	
}
