package com.buckwa.domain.pam;

import java.math.BigDecimal;

import com.buckwa.domain.BaseDomain;

public class MarkLevelDetail extends BaseDomain {

	private Long markLevelDetailId;
	private Long markLevelId;
	protected java.sql.Timestamp createDate;

	private int level;
	private BigDecimal mark;

	public Long getMarkLevelDetailId() {
		return markLevelDetailId;
	}

	public void setMarkLevelDetailId(Long markLevelDetailId) {
		this.markLevelDetailId = markLevelDetailId;
	}

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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public BigDecimal getMark() {
		return mark;
	}

	public void setMark(BigDecimal mark) {
		this.mark = mark;
	}

}
