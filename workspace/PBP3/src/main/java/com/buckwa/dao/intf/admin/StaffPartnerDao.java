package com.buckwa.dao.intf.admin;

import java.util.List;

import com.buckwa.domain.admin.StaffRatio;
import com.buckwa.domain.admin.SubjectOfStaff;
import com.buckwa.domain.pam.Section;
import com.buckwa.domain.pam.Staff;

public interface StaffPartnerDao {
	public List<SubjectOfStaff> getAllList(Section section);

	public SubjectOfStaff findSectionById(String sectionId);

	public List<StaffRatio> getStaffRatioById(String sectionId);

	public Staff findStaff(String firstname, String lastname);

	public Staff addStaffRatio(Staff staff, Integer ratio);
	
	public Integer maxRatio(String section_id);
	
	public void deleteStaff(String sectionId,String staff_code);
	
	public List<Staff> getStaffByName(String firstname, String lastName);
}
