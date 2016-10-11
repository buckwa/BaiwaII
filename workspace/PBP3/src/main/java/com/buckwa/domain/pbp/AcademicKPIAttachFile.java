package com.buckwa.domain.pbp;

import com.buckwa.domain.BaseDomain;

public class AcademicKPIAttachFile   extends BaseDomain{
	
	private Long attachFileId;
	private String fileName;
	private String filePath;
	private String fileExtension;
	private String kpiUserMappingId;
	private String fullFilePathName;
	
	
	
	public String getFullFilePathName() {
		return fullFilePathName;
	}
	public void setFullFilePathName(String fullFilePathName) {
		this.fullFilePathName = fullFilePathName;
	}
	public String getKpiUserMappingId() {
		return kpiUserMappingId;
	}
	public void setKpiUserMappingId(String kpiUserMappingId) {
		this.kpiUserMappingId = kpiUserMappingId;
	}
	public Long getAttachFileId() {
		return attachFileId;
	}
	public void setAttachFileId(Long attachFileId) {
		this.attachFileId = attachFileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

}
