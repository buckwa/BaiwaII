package com.buckwa.domain.pam;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class MarkLevel extends BaseDomain  {
	
	private Long markLevelId;
	protected java.sql.Timestamp createDate;
	
	private List<MarkLevelDetail> markLevelDetailList;
	
	public Long getMarkLevelId() {
		return markLevelId;
	}
	public void setMarkLevelId(Long markLevelId) {
		this.markLevelId = markLevelId;
	}
	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.sql.Timestamp createDate) {
		this.createDate = createDate;
	}
	public List<MarkLevelDetail> getMarkLevelDetailList() {
		return markLevelDetailList;
	}
	public void setMarkLevelDetailList(List<MarkLevelDetail> markLevelDetailList) {
		this.markLevelDetailList = markLevelDetailList;
	}
 
 
 
	 
}
