package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.pam.LeaveComment;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface LeaveCommentDao {
	public LeaveComment create(LeaveComment obj);
	public List<LeaveComment> getComments(String docno,String leaveTypeCode);
}
