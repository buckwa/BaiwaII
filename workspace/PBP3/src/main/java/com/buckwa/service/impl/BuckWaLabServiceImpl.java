package com.buckwa.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.admin.InitialSystemDao;
import com.buckwa.service.intf.BuckWaLabService;

@Service("buckWaLabService")
public class BuckWaLabServiceImpl implements BuckWaLabService {

	@Autowired
	private InitialSystemDao initialSystemDao;
	
	@Override
	public boolean initialSystem() throws Exception {
		boolean result = false;
		 try{
			 initialSystemDao.initialSystem();
			 result =true;
		 }catch(Exception ex){
			 ex.printStackTrace();
			 throw ex;
		 }
		 
		 return result;
	}
	
 
	
}
