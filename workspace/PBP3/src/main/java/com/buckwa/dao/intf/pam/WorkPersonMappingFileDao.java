package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.pam.FileLocation;
import com.buckwa.domain.pam.WorkPersonMappingFile;

/*
@Author : Taechapon Himarat (Su)
@Create : Sep 24, 2012 12:47:45 AM
 */
public interface WorkPersonMappingFileDao {
	
	public void create(WorkPersonMappingFile workPersonMappingFile);
	public void delete(WorkPersonMappingFile workPersonMappingFile);
	public List<FileLocation> getFileLocationListByWorkPersonId(Long workPersonId);
	
}
