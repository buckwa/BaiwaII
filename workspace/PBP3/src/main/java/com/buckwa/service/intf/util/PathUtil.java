package com.buckwa.service.intf.util;
public interface PathUtil   {
	public String getImageUserPath_By_UserId(String userId);
	public String getRelativeUserImagePath(String fileName) ;
	public String getFullUserImagePath(String fileName) ;
	public int getMaximumImageUploadSize();
	public int getMaximumUploadSize();
	public String getFullPathByRelativePath(String relativePath);
	
	
	// Vision
	public String getRelativeVisionImagePath(String fileName,String visionId) ;
	public String getRelativeDetailDesignImagePath(String fileName,String visionId) ;
	
	public String getUploadPath();
	
	public String getStudentImagePath();
	
	// Use Case
	public String getRelativeUseCaseImagePath(String fileName,String visionId) ;
	
	
	public String  getPBPAttatchFilePath();
	
	public String getDocPath();
	
}
