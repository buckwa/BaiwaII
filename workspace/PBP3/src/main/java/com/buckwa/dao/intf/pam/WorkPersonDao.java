package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.WorkPerson;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface WorkPersonDao {
	public WorkPerson create(WorkPerson obj);
	public WorkPerson update(WorkPerson obj);
	public void delete(String id);
	public WorkPerson getById(String id);
	public PagingBean getAllByOffset(PagingBean pagingBean);
	public List<WorkPerson> getAllByWorkTemplate(WorkPerson workPerson);
	public boolean checkNameAlready(String id,String userid,String name);
	public List<WorkPerson> getByClassRoomYearSemester(String classRoom,String yearId,String semesterId);
	public List<WorkPerson> getByClassRoomYearSemester(String classRoom,String yearId,String semesterId,String userList);
}

