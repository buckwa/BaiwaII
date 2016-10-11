package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;
import com.buckwa.util.BuckWaDateUtils;

public class LeaveComment extends BaseDomain  {
	
	private Long leaveCommentId;
	private String docno;
	private String leaveTypeCode;
	private String comment;
	private String createDateStr;
	
	public String getCreateDateStr() {
		return BuckWaDateUtils.get_current_ddMMMyyyyhhmmss_thai_from_date(createDate);
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public Long getLeaveCommentId() {
		return leaveCommentId;
	}
	public void setLeaveCommentId(Long leaveCommentId) {
		this.leaveCommentId = leaveCommentId;
	}
	public String getDocno() {
		return docno;
	}
	public void setDocno(String docno) {
		this.docno = docno;
	}
	public String getLeaveTypeCode() {
		return leaveTypeCode;
	}
	public void setLeaveTypeCode(String leaveTypeCode) {
		this.leaveTypeCode = leaveTypeCode;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
}
