package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

public class FileLocation extends BaseDomain {

	private Long fileId;
	private String filePath;
	private String fromTable;
	private String fileCode;
	private String fileName;
	private String fileExtension;
	private String fileType;
	private String fileDesc;
	private String fileSize;
	private String topiclv1Id;
	
	// Mapping with WorkPersonAttr
	private Long workPersonAttrId;
	private String label;
	
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFromTable() {
		return fromTable;
	}
	public void setFromTable(String fromTable) {
		this.fromTable = fromTable;
	}
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getTopiclv1Id() {
		return topiclv1Id;
	}
	public void setTopiclv1Id(String topiclv1Id) {
		this.topiclv1Id = topiclv1Id;
	}
	public Long getWorkPersonAttrId() {
		return workPersonAttrId;
	}
	public void setWorkPersonAttrId(Long workPersonAttrId) {
		this.workPersonAttrId = workPersonAttrId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	@Override
	public String toString() {
		return "FileLocation [fileId=" + fileId + ", filePath=" + filePath
				+ ", fromTable=" + fromTable + ", fileCode=" + fileCode
				+ ", fileName=" + fileName + ", fileExtension=" + fileExtension
				+ ", fileType=" + fileType + ", fileDesc=" + fileDesc
				+ ", fileSize=" + fileSize + ", topiclv1Id=" + topiclv1Id
				+ ", rownum=" + rownum + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", createBy=" + createBy
				+ ", updateBy=" + updateBy + "]";
	}
	
}
