package com.buckwa.service.impl.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.admin.StaffPartnerDao;
import com.buckwa.domain.admin.StaffRatio;
import com.buckwa.domain.admin.SubjectOfStaff;
import com.buckwa.domain.pam.Section;
import com.buckwa.domain.pam.Staff;
import com.buckwa.service.intf.admin.StaffPartnerService;

@Service("staffPartnerService")
public class StaffPartnerServiceImp implements StaffPartnerService {

	@Autowired
	private StaffPartnerDao staffPartnerDao;

	@Override
	public List<SubjectOfStaff> getAllList(Section section) {
		return staffPartnerDao.getAllList(section);
	}

	@Override
	public SubjectOfStaff findSectionById(String sectionId) {
		return staffPartnerDao.findSectionById(sectionId);
	}

	@Override
	public List<StaffRatio> getStaffRatioById(String sectionId) {
		return staffPartnerDao.getStaffRatioById(sectionId);
	}

	@Override
	public Staff findStaff(String firstname, String lastname) {
		return staffPartnerDao.findStaff(firstname, lastname);
	}

	@Override
	public Staff addStaffRatio(Staff staff, Integer ratio) {
		return staffPartnerDao.addStaffRatio(staff, ratio);
	}

	@Override
	public Integer maxRatio(String section_id) {
		return staffPartnerDao.maxRatio(section_id);
	}

	@Override
	public void deleteStaff(String sectionId, String staff_code) {
		staffPartnerDao.deleteStaff(sectionId, staff_code);
	}

	@Override
	public List<Staff> getStaffByName(String firstname, String lastName) {
		return staffPartnerDao.getStaffByName(firstname, lastName);
	}

}
