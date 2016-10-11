package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

/*
@Author : Taechapon Himarat (Su)
@Create : Sep 24, 2012 12:49:19 AM
 */
public class WorkPersonMappingFile extends BaseDomain {
	
	private Long workPersonMappingFileId;
	private Long workPersonId;
	private Long workPersonAttrId;
	private Long fileId;
	
	
	public Long getWorkPersonMappingFileId() {
		return workPersonMappingFileId;
	}
	
	public void setWorkPersonMappingFileId(Long workPersonMappingFileId) {
		this.workPersonMappingFileId = workPersonMappingFileId;
	}

	public Long getWorkPersonId() {
		return workPersonId;
	}

	public void setWorkPersonId(Long workPersonId) {
		this.workPersonId = workPersonId;
	}

	public Long getWorkPersonAttrId() {
		return workPersonAttrId;
	}

	public void setWorkPersonAttrId(Long workPersonAttrId) {
		this.workPersonAttrId = workPersonAttrId;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	
}
