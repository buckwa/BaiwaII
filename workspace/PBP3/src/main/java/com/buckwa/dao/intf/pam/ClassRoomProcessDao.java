package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.ClassRoomProcess;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pam.Subject;
import com.buckwa.domain.pam.TeachTable;
import com.buckwa.domain.pam.Teacher;
import com.buckwa.domain.pam.TeacherTeach;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface ClassRoomProcessDao {
	public ClassRoomProcess create(ClassRoomProcess obj);
	public ClassRoomProcess update(ClassRoomProcess obj);
	public ClassRoomProcess getById(String id);
	public PagingBean getAllByOffset(PagingBean pagingBean);
	public void importSubject(Long yearId,Long semesterId,String createBy)  throws Exception;
	public void importTeacher()  throws Exception;
	public void importTeacher(Long yearId,Long semesterId)  throws Exception;
	public void importTeachTable(Long yearId,Long semesterId,String createBy)  throws Exception;
	public void importTeacherTeach(Long yearId,Long semesterId,String createBy)  throws Exception;
	public boolean removeSubject(Long semesterId,Long yearId);
	public boolean removeTeacher();
	public boolean removeTeacher(Long semesterId,Long yearId);
	public boolean removeTeachTable(Long semesterId,Long yearId);
	public boolean removeTeacherTeach(Long semesterId,Long yearId);
	public boolean updateTeacher(String name,Long userId);
	public List<Person> getUserList(Long yearId,Long semesterId);
	public boolean clearFlagClassRoomProcess(Long semesterId,Long yearId);
}

