package com.buckwa.dao.intf.project;

import com.buckwa.domain.common.ImagePath;
import com.buckwa.domain.project.Vision;

public interface VisionDao {
	 
	public void update(Vision vision); 
	public Vision getVisionByProjectId(String useCaseId);  
	
	public void updateImagePath(Vision vision);
	
	public void updateRefenceFilePath(Vision vision);
	
	public void removeImagePath(ImagePath imagePath);
	
	public void removeFilePath(ImagePath imagePath);
}
