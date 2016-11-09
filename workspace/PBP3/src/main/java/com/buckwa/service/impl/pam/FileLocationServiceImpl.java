package com.buckwa.service.impl.pam;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pam.FileLocationDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.FileLocation;
import com.buckwa.domain.pbp.AcademicKPIAttachFile;
import com.buckwa.service.intf.pam.FileLocationService;
import com.buckwa.util.BuckWaConstants;

/**
 * 
 @Author : Kroekpong Sakulchai (Ball)
 @Create : Aug 5, 2012 10:15:14 PM
 *
 **/

@Service("fileLocationService")

public class FileLocationServiceImpl implements FileLocationService{

	private static Logger logger = LoggerFactory.getLogger(FileLocationServiceImpl.class);
	
	@Autowired
	private FileLocationDao fileLocationDao;
	
	@Override
	public BuckWaResponse getByOffset(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{					
			logger.info(" Get Import Profile By Offset");
			PagingBean pagingBean = (PagingBean)request.get("pagingBean");			 
			PagingBean returnBean = fileLocationDao.getByOffset(pagingBean);		
			 
			response.addResponse("pagingBean",returnBean);				
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}	
		return response;
	}

	@Override
	public Long create(FileLocation fileLocation) {
		Long key = null;
		try{
			key = fileLocationDao.create(fileLocation);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return key;
	}
	
	@Override
	public Long createPBPAttachFile(AcademicKPIAttachFile  academicKPIAttachFile) {
		Long key = null;
		try{
			key = fileLocationDao.createPBPAttachFile(academicKPIAttachFile);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return key;
	}
	
	@Override
	public boolean createPBPAttachFileNew(AcademicKPIAttachFile  academicKPIAttachFile) {
		try{
			
			 fileLocationDao.createPBPAttachFileNew(academicKPIAttachFile);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return true;
	}
	
	
	@Override
	public boolean updatePBPPersonPicture(String personId,String picturePath) {
		 
		try{
			 fileLocationDao.updatePBPPersonPicture(personId,picturePath);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return true;
	}
	
	
	@Override
	public BuckWaResponse deleteFile(BuckWaRequest request) {
		
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String fileCode = (String)request.get("fileCode");
			fileLocationDao.deleteFile(fileCode);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
			return response;
	}
	
	@Override
	public BuckWaResponse deletePBPAttachFile(BuckWaRequest request) {
		
		BuckWaResponse response = new BuckWaResponse();
		try{		
			String attachFileId = (String)request.get("attachFileId");
			fileLocationDao.deletePBPAttachFile(attachFileId);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
			return response;
	}
	
	

	@Override
	public FileLocation findByFileCode(String fileCode) {

		FileLocation fileLocation = new FileLocation();
		
		fileLocation = fileLocationDao.findByFileCode(fileCode.trim());
				
		return fileLocation;
	}
	
	
	@Override
	public AcademicKPIAttachFile findPBPAttachFile(String fileCode) {

		 
		
		AcademicKPIAttachFile academicKPIAttachFile  = fileLocationDao.findPBPAttachFile(fileCode);
				
		return academicKPIAttachFile;
	}
	
	
	@Override
	public boolean checkFileNameServerExist(String fileName, String fromTable) {

		boolean isFileNameExist = false;
		int listsize = 0;
		PagingBean pagingBean = new PagingBean();
		PagingBean returnBean = new PagingBean();
		pagingBean.put("fileName", fileName);
		pagingBean.put("fromTable", fromTable);
		returnBean = fileLocationDao.getByOffset(pagingBean);	
		listsize = returnBean.getCurrentPageItem().size();
		
		logger.info("@@@ >>"+returnBean.getCurrentPageItem().size());
		
		if(listsize > 0){
			isFileNameExist = true;
		}
		//return isFileNameExist;
		
		return false;
	}

}
