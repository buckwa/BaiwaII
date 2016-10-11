package com.buckwa.service.impl.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.buckwa.service.intf.util.PathUtil;
import com.buckwa.util.FileUtils;

@Service("pathUtil") 
public class PathUtilImpl   implements PathUtil{
	
	
	 
	@Value("${project.root.dir}")
	private String projectRootDir;
	
	
 
	@Value("${user.dir}")
	private String userDir;
 
	@Value("${max.image.uploadFileSize}")
	private String maxImageUploadFileSize;
	
 
	@Value("${max.uploadFileSize}")
	private String maxUploadFileSize;
	
	
	@Override
	public String getImageUserPath_By_UserId(String userId) {
		 String returnValue = "";
		 try{
			 returnValue = projectRootDir+userDir+userId+"/";
			 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 
		 return returnValue;
	}
	
	@Override
	public String getRelativeUserImagePath(String fileName) {
		 String returnValue = "";
		 try{
			 returnValue = userDir+fileName;			 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 
		 return returnValue;
	}
	@Override
	public String getFullUserImagePath(String fileName) {
		 String returnValue = "";
		 try{
			 FileUtils.createDirectoryIfNotExist(projectRootDir+userDir); 
			 returnValue = projectRootDir+userDir+fileName;			 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 
		 return returnValue;
	}
	
	
	@Override
	public int getMaximumImageUploadSize() {
		 int returnValue = 0;
		 try{
			 returnValue = Integer.parseInt(maxImageUploadFileSize);
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }		 
		 return returnValue;
	}
	@Override
	public int getMaximumUploadSize() {
		 int returnValue = 0;
		 try{
			 returnValue = Integer.parseInt(maxUploadFileSize);
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }		 
		 return returnValue;
	}
	@Override
	public String getFullPathByRelativePath(String relativePath) {
		 String returnValue = "";
		 try{
			 returnValue = projectRootDir+relativePath;			 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 
		 return returnValue;
	}
	
	@Override
	public String getRelativeVisionImagePath(String fileName,String visionId) {
		 String returnValue = "";
		 try{			 
			 FileUtils.createDirectoryIfNotExist(projectRootDir+"vision/"+visionId+"/"); 
			 returnValue = "vision/"+visionId+"/"+fileName;			 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 
		 return returnValue;
	}	
	
	@Override
	public String getRelativeUseCaseImagePath(String fileName,String id) {
		 String returnValue = "";
		 try{			 
			 FileUtils.createDirectoryIfNotExist(projectRootDir+"usecase/"+id+"/"); 
			 returnValue = "usecase/"+id+"/"+fileName;			 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }		 
		 return returnValue;
	}	
	
	
	
	@Override
	public String getRelativeDetailDesignImagePath(String fileName,String id) {
		 String returnValue = "";
		 try{			  
			 FileUtils.createDirectoryIfNotExist(projectRootDir+"detaildesign/"+id+"/"); 
			 returnValue = "detaildesign/"+id+"/"+fileName;			 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 
		 return returnValue;
	}	

	
	@Override
	public String getUploadPath(  ) {
		 String returnValue = "";
		 try{			  
			 FileUtils.createDirectoryIfNotExist(projectRootDir+"upload"); 
			 returnValue = projectRootDir+"upload/";				 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 
		 return returnValue;
	}	
	
	@Override
	public String getDocPath(  ) {
		 String returnValue = "";
		 try{			  
			 FileUtils.createDirectoryIfNotExist(projectRootDir+"doc"); 
			 returnValue = projectRootDir+"doc/";				 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 
		 return returnValue;
	}	
	
	
	
	@Override
	public String getPBPAttatchFilePath(  ) {
		 String returnValue = "";
		 try{			  
			 FileUtils.createDirectoryIfNotExist(projectRootDir+"attatchfile"); 
			 returnValue = projectRootDir+"attatchfile/";				 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 
		 return returnValue;
	}	
	@Override
	public String getStudentImagePath(  ) {
		 String returnValue = "";
		 try{			  
			 FileUtils.createDirectoryIfNotExist(projectRootDir+"student"); 
			 returnValue = projectRootDir+"student/";				 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 
		 return returnValue;
	}	
	
	
}
