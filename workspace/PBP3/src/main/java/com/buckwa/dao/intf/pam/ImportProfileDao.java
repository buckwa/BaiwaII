package com.buckwa.dao.intf.pam;

import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pam.Section;
import com.buckwa.domain.pam.Staff;

/**
 * 
 @Author : Kroekpong Sakulchai (Ball)
 @Create : Aug 5, 2012 10:15:14 PM
 *
 **/

public interface ImportProfileDao {
	
	public Long create(Person person);
	public int findByUsername(String username);
	public void update(Person person);
	public void updatePBP(Person person);
	public void updatePBPNew(Person person);
	
	
	public Long createAcademicPerson(Person person);
	public int findAcademicPersonByUsername(String username,String academicYear);
	public void updateAcademicPerson(Person person);
	
	
	public Long createPBP(Person person);
	
	public int findByStaffCode(String username);
	public Long createStaff(Staff domain);
	public void updateStaff(Staff domain);
	
	public boolean isExistPerson(String username) ;
	
	public void updateTotalStudent(Section domain);
	
	
	
	
}
