package com.buckwa.domain.admin;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.buckwa.domain.BaseDomain;
import com.buckwa.domain.common.Address;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pbp.AcademicYear;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.report.TimeTableReport;
 

public class User extends BaseDomain  {
 
 
	private static final long serialVersionUID = -8118968688824696617L;
	
	private int user_id;
	private String username;
	private String currentPassword;
	private String password;
	private String passwordConfirmation;
	private String newPassword;
	
	private String first_name;
	private String last_name;
	private String email;
	private String address1;
	private String address2;
	private String tel_no;
	private String userRole;	

	private boolean enabled;
	private Date create_date;
	private Date last_update_date;
	private String slogan;
	private String image_path_name;
	private String []groups;
	private String []authorizes;
	private List<Role> roleList;
	private List<Group> groupList; 
	private List<Menu> menuList; 
	private String editable;
	private String userRoleDescription;
	private String secureCode;
	
	private String signature;
	private String regId;
 
	private CommonsMultipartFile fileData;
	private String signatureImagePath;
	private String fullSignatureImagePath;
	
	private String signatureFilename;
 
	private Long addressId;
	private Address address;
	
	private String appContext;
	private Date assignDate;
	
	private String firstLastName;
	
	private int accumulate;
	private int flagAccumulate;
	
	private String facultyDesc;
	private String departmentDesc;
	private String facultyCode;
	
	// for create person on admin page
	private Person person;

	private int leaveAccumulate;
	
	private String employeeType;
	
	private String academicYear;
	
	private String userAcademicYear;
	
	private String statusRecord;
	
	private String icNumber ;
	
	private List<TimeTableReport> timeTableList;
	private List<TimeTableReport> timeTableList2;
	
	private List<AcademicYear> academicYearList;
	private List<Faculty> facultyList;
	
	public String getIcNumber() {
		return icNumber;
	}

	public void setIcNumber(String icNumber) {
		this.icNumber = icNumber;
	}

	public List<TimeTableReport> getTimeTableList() {
		return timeTableList;
	}

	public List<TimeTableReport> getTimeTableList2() {
		return timeTableList2;
	}

	public void setTimeTableList2(List<TimeTableReport> timeTableList2) {
		this.timeTableList2 = timeTableList2;
	}

	public void setTimeTableList(List<TimeTableReport> timeTableList) {
		this.timeTableList = timeTableList;
	}

	public String getFacultyCode() {
		return facultyCode;
	}

	public void setFacultyCode(String facultyCode) {
		this.facultyCode = facultyCode;
	}

	public List<Faculty> getFacultyList() {
		return facultyList;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public void setFacultyList(List<Faculty> facultyList) {
		this.facultyList = facultyList;
	}

	public String getStatusRecord() {
		return statusRecord;
	}

	public void setStatusRecord(String statusRecord) {
		this.statusRecord = statusRecord;
	}


	
	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getUserAcademicYear() {
		return userAcademicYear;
	}

	public void setUserAcademicYear(String userAcademicYear) {
		this.userAcademicYear = userAcademicYear;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getFacultyDesc() {
		return facultyDesc;
	}

	public void setFacultyDesc(String facultyDesc) {
		this.facultyDesc = facultyDesc;
	}

	public String getDepartmentDesc() {
		return departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	public int getAccumulate() {
		return accumulate;
	}

	public List<AcademicYear> getAcademicYearList() {
		return academicYearList;
	}

	public void setAcademicYearList(List<AcademicYear> academicYearList) {
		this.academicYearList = academicYearList;
	}

	public void setAccumulate(int accumulate) {
		this.accumulate = accumulate;
	}

	public int getFlagAccumulate() {
		return flagAccumulate;
	}

	public void setFlagAccumulate(int flagAccumulate) {
		this.flagAccumulate = flagAccumulate;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public String getAppContext() {
		return appContext;
	}

	public void setAppContext(String appContext) {
		this.appContext = appContext;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public int getLeaveAccumulate() {
		return leaveAccumulate;
	}

	public void setLeaveAccumulate(int leaveAccumulate) {
		this.leaveAccumulate = leaveAccumulate;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getImage_path_name() {
		return image_path_name;
	}

	public void setImage_path_name(String image_path_name) {
		this.image_path_name = image_path_name;
	}

 

	public String getSignatureFilename() {
		return signatureFilename;
	}

	public void setSignatureFilename(String signatureFilename) {
		this.signatureFilename = signatureFilename;
	}

	public String getSignatureImagePath() {
		return signatureImagePath;
	}

	public void setSignatureImagePath(String signatureImagePath) {
		this.signatureImagePath = signatureImagePath;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

 

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getTel_no() {
		return tel_no;
	}

	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getLast_update_date() {
		return last_update_date;
	}

	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

 

	public String[] getGroups() {
		return groups;
	}

	public void setGroups(String[] groups) {
		this.groups = groups;
	}

	public String[] getAuthorizes() {
		return authorizes;
	}

	public void setAuthorizes(String[] authorizes) {
		this.authorizes = authorizes;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getUserRoleDescription() {
		return userRoleDescription;
	}

	public void setUserRoleDescription(String userRoleDescription) {
		this.userRoleDescription = userRoleDescription;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getSecureCode() {
		return secureCode;
	}

	public void setSecureCode(String secureCode) {
		this.secureCode = secureCode;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getFullSignatureImagePath() {
		return fullSignatureImagePath;
	}

	public void setFullSignatureImagePath(String fullSignatureImagePath) {
		this.fullSignatureImagePath = fullSignatureImagePath;
	}

	public String getFirstLastName() {
		return firstLastName;
	}

	public void setFirstLastName(String firstLastName) {
		this.firstLastName = firstLastName;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
