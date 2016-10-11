package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Person;
/*
@Author : Taechapon Himarat (Su)
@Create : Aug 5, 2012 2:06:46 PM
 */
public interface PersonProfileDao {
	
	public List<Person> getAll();
	public PagingBean getByOffset(PagingBean pagingBean);
	public PagingBean getByOffsetWithWorkline(PagingBean pagingBean);
	public Person getByUsername(String email,String academicYear);
//	public void updateWorkline(Person domain);
	public void update(Person domain);
	public Person getByUserId(Long userid,String academicYear);
	public Person getByWorkLineCode(String worklineCode);
	public List<Person> getPersonByNotInTimeTable(Long yearId,Long semesterId);
	public List<Person> getPersonEstimateUser(Long estimateGroupId);
	public List<Person> getPersonEstimateAllByuser();
	public Person getByPersonId(Long personId,String academicYear); 
	
	// For Report
	public List<String> getPersonTypeListByFaculty(String facultyId);
	public String getFacultyIdByUsername(String username);
	public List<String> getUserIdListByFacultyAndPersonType(List<String> facultyIdList, String personType);
	
	public void updateLeaveAccumulate(Person domain);
	
	public void updatePBPPerson(Person domain);
	
	
}
