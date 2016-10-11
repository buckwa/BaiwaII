package com.buckwa.service.intf.pam;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Workbook;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;

/**
 * 
 @Author : Kroekpong Sakulchai (Ball)
 @Create : Aug 5, 2012 10:15:14 PM
 *
 **/

public interface ImportFileService {
	
	public void readProfileWorkbook(Workbook workbook, HttpServletRequest httpRequest);
	public void readPBPProfileWorkbook(Workbook workbook, HttpServletRequest httpRequest);
	
	public void readTimeTableWorkbook(Workbook workbook, HttpServletRequest httpRequest);
	public void readTimeTableDescWorkbook(Workbook workbook, HttpServletRequest httpRequest);
	public BuckWaResponse importTimeTableSql(BuckWaRequest request);
	
	public void readStaffWorkbook(Workbook workbook, HttpServletRequest httpRequest);
	public void readStudentInSecWorkbook(Workbook workbook, HttpServletRequest httpRequest);
}

