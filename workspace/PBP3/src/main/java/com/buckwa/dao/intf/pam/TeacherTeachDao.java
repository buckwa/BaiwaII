package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.TeachTable;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface TeacherTeachDao {
	public List<TeachTable> getTeachTableList(Long userId,Long yearId);
	public TeachTable getTeachTable(Long teachTableId);
	public TeachTable getTeachTable(Long teachTableId,String degree,Long yearId,Long semesterId);
}

