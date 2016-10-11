package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

/*
@Author : Taechapon Himarat (Su)
@Create : Aug 5, 2012 11:34:39 PM
 */
public class Paper extends BaseDomain {
	
	private Long paperId;
	private String paperLevel;
	private String paperTitle;
	private String paperStatus;
	private Long personId;
	private Long fileId;
	
	
	public Long getPaperId() {
		return paperId;
	}
	
	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

	public String getPaperLevel() {
		return paperLevel;
	}

	public void setPaperLevel(String paperLevel) {
		this.paperLevel = paperLevel;
	}

	public String getPaperTitle() {
		return paperTitle;
	}

	public void setPaperTitle(String paperTitle) {
		this.paperTitle = paperTitle;
	}

	public String getPaperStatus() {
		return paperStatus;
	}

	public void setPaperStatus(String paperStatus) {
		this.paperStatus = paperStatus;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	
	
	
}
