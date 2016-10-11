package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Subject;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface SubjectDao {
	public List<Subject> getAll(Long yearId,Long semesterId);
	public List<Subject> getAll(Long yearId,Long semesterId,String teacherList) ;
	public PagingBean getAllSubjectByOffset(PagingBean pagingBean);
	public Subject getSubjectById(Long subjectId,String degree);
	public List<Subject> getAllSubjectByUserId(Subject subject);
	
	
}

