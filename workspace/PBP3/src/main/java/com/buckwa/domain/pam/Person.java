package com.buckwa.domain.pam;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.buckwa.domain.BaseDomain;
import com.buckwa.domain.common.LovDetail;
import com.buckwa.domain.pbp.AcademicKPIWrapper;
import com.buckwa.domain.pbp.AcademicYear;
import com.buckwa.domain.pbp.AcademicYearEvaluateRound;
import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.util.PAMConstants;

public class Person extends BaseDomain  {
	
	private Long personId;
	private String employeeId;
	private String citizenId;
	private String thaiName;
	private String thaiSurname;
	private String sex;
	private Date birthdate;
	private String rateNo;
	private String employeeType;
	private String position;
	private String level;
	private String workLine;
	private BigDecimal salary;
	private String workTelNo;
	private String belongTo;
	private String faculty;
	private Date workingDate;
	private Date assignDate;
	private Date retireDate;
	private String maxInsignia;
	private String maxEducation;
	private String taxNo;
	private String workingStatus;
	private String marriedStatus;
	private String workNumber;
	private String insureNo;
	private String fund;
	private String address;
	private String zipCode;
	private String telNo;
	private String email;
	private String oldWorklineCode;
	private String worklineCode;
	private String worklineName;
	private Long userId;
	private String fullName;
	private String estimateGroupName;
	private String picture;
	private String personType;
	// From Attribute
	private String birthdateStr;
	private String workingDateStr;
	private String assignDateStr;
	private String retireDateStr;
	
	private String facultyCode;
	
	private String regId;
	
	private String username;
	
	private List<LovDetail> lovSexList;
	private List<LovDetail> lovEmployeeTypeList;
	private List<LovDetail> lovPositionList;
	private List<LovDetail> lovWorkLineList;
	private List<LovDetail> lovFacultyList;
	private List<LovDetail> lovInsigniaList;
	private List<LovDetail> lovMarriedStatusList;
	private List<LovDetail> lovEducationList;
	private List<LovDetail> lovWorkingStatusList;
	
	private List<LovDetail> lovAcademicRankList;
	private List<LovDetail> lovMaxEducationList;
 
	private CommonsMultipartFile fileData;
	
	private List<AcademicYear> academicYearList;
	
	
	private List<AcademicYear> evaluateRoundList;
	
	
	
	private int leaveAccumulate;
	
	public List<PBPWorkType>  pBPWorkTypeList ;
	private PBPWorkTypeWrapper pBPWorkTypeWrapper;
	private AcademicKPIWrapper academicKPIWrapper;
	
	private String department;
	private String isDean;
	private String isHead;
	private String academicYear;
	
	
	private String departmentDesc;
	private String facultyDesc;
	
	
	private String radarURL;
	
	private String evaluateRound;
	private List<AcademicYearEvaluateRound> academicYearEvaluateRoundList;
	
	/*
	 �ѧ�Ѵ	: faculty :person.faculty :������ǡ�����ʵ��
	 ��ǹ�ҹ: department :person.department	: �Ң��Ԫ����ǡ����ä��Ҥ�
	 ������:employeeType :person.employee_type:����Ҫ���
	 �ӹ�˹��:titleName :person.title_name:���
	 ����:firstName :person.thai_name:��
	 ���ʡ��:lastName	 :person.thai_surname:����Ѳ��ѭ��
	 �Ţ����ѵ��:rateNo :person.rate_no:107
	 ���˹觧ҹ:academicRank :person.academic_rank:�ͧ��ʵ�Ҩ����
	 �زԡ���֡��:maxEducation :person.max_education:�.�͡
	 Email :person.email:1@kmitl.ac.th
	*/
	
	// For PBP
	//private String facultyDesc;
	//private String departmentDesc;
	//private String employeeType;
	private String titleName;
	//private String thaiName;
	//private String thaiSurName;
	//private String rateNo;
	private String academicRank;
	//private String maxEducation;
	//private String email;
	
	
	private String employeeTypeNo;
	
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRadarURL() {
		return radarURL;
	}

	public List<AcademicYear> getEvaluateRoundList() {
		
		List<AcademicYear> roundList  = new ArrayList();
		AcademicYear round1 = new AcademicYear();
		round1.setName("1");
		AcademicYear round2 = new AcademicYear();
		round2.setName("2");
		
		roundList.add(round1);
		roundList.add(round2);
		
		return roundList;
	}

	
	
	public String getFacultyCode() {
		return facultyCode;
	}

	public void setFacultyCode(String facultyCode) {
		this.facultyCode = facultyCode;
	}

	public String getEmployeeTypeNo() {
		String evalType = "1";
		
		if(employeeType.equalsIgnoreCase("ข้าราชการ")){
			
		}else{
			evalType ="2";
		}
		return evalType;
	}

	public void setEmployeeTypeNo(String employeeTypeNo) {
		this.employeeTypeNo = employeeTypeNo;
	}

	public void setEvaluateRoundList(List<AcademicYear> evaluateRoundList) {
		this.evaluateRoundList = evaluateRoundList;
	}

	public String getEvaluateRound() {
		if(evaluateRound==null){
			
			return "1";
		}else{
		return evaluateRound;
		}
	}

	public void setEvaluateRound(String evaluateRound) {
		this.evaluateRound = evaluateRound;
	}

	public List<AcademicYearEvaluateRound> getAcademicYearEvaluateRoundList() {
		return academicYearEvaluateRoundList;
	}

	public void setAcademicYearEvaluateRoundList(
			List<AcademicYearEvaluateRound> academicYearEvaluateRoundList) {
		this.academicYearEvaluateRoundList = academicYearEvaluateRoundList;
	}

	public List<AcademicYear> getAcademicYearList() {
		return academicYearList;
	}

	public void setAcademicYearList(List<AcademicYear> academicYearList) {
		this.academicYearList = academicYearList;
	}

	public List<LovDetail> getLovAcademicRankList() {
		return lovAcademicRankList;
	}

	public void setLovAcademicRankList(List<LovDetail> lovAcademicRankList) {
		this.lovAcademicRankList = lovAcademicRankList;
	}

	public List<LovDetail> getLovMaxEducationList() {
		return lovMaxEducationList;
	}

	public void setLovMaxEducationList(List<LovDetail> lovMaxEducationList) {
		this.lovMaxEducationList = lovMaxEducationList;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}


	public String getAcademicRank() {
		return academicRank;
	}

	public void setAcademicRank(String academicRank) {
		this.academicRank = academicRank;
	}

	public void setRadarURL(String radarURL) {
		this.radarURL = radarURL;
	}

	public String getDepartmentDesc() {
		return departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	public String getFacultyDesc() {
		return facultyDesc;
	}

	public void setFacultyDesc(String facultyDesc) {
		this.facultyDesc = facultyDesc;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getIsDean() {
		return isDean;
	}

	public void setIsDean(String isDean) {
		this.isDean = isDean;
	}

	public String getIsHead() {
		return isHead;
	}

	public void setIsHead(String isHead) {
		this.isHead = isHead;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public AcademicKPIWrapper getAcademicKPIWrapper() {
		return academicKPIWrapper;
	}

	public void setAcademicKPIWrapper(AcademicKPIWrapper academicKPIWrapper) {
		this.academicKPIWrapper = academicKPIWrapper;
	}

	public PBPWorkTypeWrapper getpBPWorkTypeWrapper() {
		return pBPWorkTypeWrapper;
	}

	public void setpBPWorkTypeWrapper(PBPWorkTypeWrapper pBPWorkTypeWrapper) {
		this.pBPWorkTypeWrapper = pBPWorkTypeWrapper;
	}

	public List<PBPWorkType> getpBPWorkTypeList() {
		return pBPWorkTypeList;
	}

	public void setpBPWorkTypeList(List<PBPWorkType> pBPWorkTypeList) {
		this.pBPWorkTypeList = pBPWorkTypeList;
	}

	public int getLeaveAccumulate() {
		return leaveAccumulate;
	}

	public void setLeaveAccumulate(int leaveAccumulate) {
		this.leaveAccumulate = leaveAccumulate;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public Long getPersonId() {
		return personId;
	}
	
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getCitizenId() {
		return citizenId;
	}

	public void setCitizenId(String citizenId) {
		this.citizenId = citizenId;
	}

	public String getThaiName() {
		return thaiName;
	}

	public void setThaiName(String thaiName) {
		this.thaiName = thaiName;
	}

	public String getThaiSurname() {
		return thaiSurname;
	}

	public void setThaiSurname(String thaiSurname) {
		this.thaiSurname = thaiSurname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getRateNo() {
		return rateNo;
	}

	public void setRateNo(String rateNo) {
		this.rateNo = rateNo;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getWorkLine() {
		return workLine;
	}

	public void setWorkLine(String workLine) {
		this.workLine = workLine;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getWorkTelNo() {
		return workTelNo;
	}

	public void setWorkTelNo(String workTelNo) {
		this.workTelNo = workTelNo;
	}

	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public Date getWorkingDate() {
		return workingDate;
	}

	public void setWorkingDate(Date workingDate) {
		this.workingDate = workingDate;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public Date getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(Date retireDate) {
		this.retireDate = retireDate;
	}

	public String getMaxInsignia() {
		return maxInsignia;
	}

	public void setMaxInsignia(String maxInsignia) {
		this.maxInsignia = maxInsignia;
	}

	public String getMaxEducation() {
		return maxEducation;
	}

	public void setMaxEducation(String maxEducation) {
		this.maxEducation = maxEducation;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getWorkingStatus() {
		return workingStatus;
	}

	public void setWorkingStatus(String workingStatus) {
		this.workingStatus = workingStatus;
	}

	public String getMarriedStatus() {
		return marriedStatus;
	}

	public void setMarriedStatus(String marriedStatus) {
		this.marriedStatus = marriedStatus;
	}

	public String getWorkNumber() {
		return workNumber;
	}

	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}

	public String getInsureNo() {
		return insureNo;
	}

	public void setInsureNo(String insureNo) {
		this.insureNo = insureNo;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOldWorklineCode() {
		return oldWorklineCode;
	}

	public void setOldWorklineCode(String oldWorklineCode) {
		this.oldWorklineCode = oldWorklineCode;
	}

	public String getWorklineCode() {
		return worklineCode;
	}

	public void setWorklineCode(String worklineCode) {
		this.worklineCode = worklineCode;
	}

	public String getWorklineName() {
		return worklineName;
	}

	public void setWorklineName(String worklineName) {
		this.worklineName = worklineName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEstimateGroupName() {
		return estimateGroupName;
	}

	public void setEstimateGroupName(String estimateGroupName) {
		this.estimateGroupName = estimateGroupName;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getBirthdateStr() {
		return birthdateStr;
	}

	public void setBirthdateStr(String birthdateStr) {
		this.birthdateStr = birthdateStr;
	}

	public String getWorkingDateStr() {
		return workingDateStr;
	}

	public void setWorkingDateStr(String workingDateStr) {
		this.workingDateStr = workingDateStr;
	}

	public String getAssignDateStr() {
		return assignDateStr;
	}

	public void setAssignDateStr(String assignDateStr) {
		this.assignDateStr = assignDateStr;
	}

	public String getRetireDateStr() {
		return retireDateStr;
	}

	public void setRetireDateStr(String retireDateStr) {
		this.retireDateStr = retireDateStr;
	}

	public List<LovDetail> getLovSexList() {
		return lovSexList;
	}

	public void setLovSexList(List<LovDetail> lovSexList) {
		this.lovSexList = lovSexList;
	}

	public List<LovDetail> getLovEmployeeTypeList() {
		return lovEmployeeTypeList;
	}

	public void setLovEmployeeTypeList(List<LovDetail> lovEmployeeTypeList) {
		this.lovEmployeeTypeList = lovEmployeeTypeList;
	}

	public List<LovDetail> getLovPositionList() {
		return lovPositionList;
	}

	public void setLovPositionList(List<LovDetail> lovPositionList) {
		this.lovPositionList = lovPositionList;
	}

	public List<LovDetail> getLovWorkLineList() {
		return lovWorkLineList;
	}

	public void setLovWorkLineList(List<LovDetail> lovWorkLineList) {
		this.lovWorkLineList = lovWorkLineList;
	}

	public List<LovDetail> getLovFacultyList() {
		return lovFacultyList;
	}

	public void setLovFacultyList(List<LovDetail> lovFacultyList) {
		this.lovFacultyList = lovFacultyList;
	}

	public List<LovDetail> getLovInsigniaList() {
		return lovInsigniaList;
	}

	public void setLovInsigniaList(List<LovDetail> lovInsigniaList) {
		this.lovInsigniaList = lovInsigniaList;
	}

	public List<LovDetail> getLovMarriedStatusList() {
		return lovMarriedStatusList;
	}

	public void setLovMarriedStatusList(List<LovDetail> lovMarriedStatusList) {
		this.lovMarriedStatusList = lovMarriedStatusList;
	}

	public List<LovDetail> getLovEducationList() {
		return lovEducationList;
	}

	public void setLovEducationList(List<LovDetail> lovEducationList) {
		this.lovEducationList = lovEducationList;
	}

	public List<LovDetail> getLovWorkingStatusList() {
		return lovWorkingStatusList;
	}

	public void setLovWorkingStatusList(List<LovDetail> lovWorkingStatusList) {
		this.lovWorkingStatusList = lovWorkingStatusList;
	}
	
	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [personId=");
		builder.append(personId);
		builder.append(", employeeId=");
		builder.append(employeeId);
		builder.append(", citizenId=");
		builder.append(citizenId);
		builder.append(", thaiName=");
		builder.append(thaiName);
		builder.append(", thaiSurname=");
		builder.append(thaiSurname);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", birthdate=");
		builder.append(birthdate);
		builder.append(", rateNo=");
		builder.append(rateNo);
		builder.append(", employeeType=");
		builder.append(employeeType);
		builder.append(", position=");
		builder.append(position);
		builder.append(", level=");
		builder.append(level);
		builder.append(", workLine=");
		builder.append(workLine);
		builder.append(", salary=");
		builder.append(salary);
		builder.append(", workTelNo=");
		builder.append(workTelNo);
		builder.append(", belongTo=");
		builder.append(belongTo);
		builder.append(", faculty=");
		builder.append(faculty);
		builder.append(", workingDate=");
		builder.append(workingDate);
		builder.append(", assignDate=");
		builder.append(assignDate);
		builder.append(", retireDate=");
		builder.append(retireDate);
		builder.append(", maxInsignia=");
		builder.append(maxInsignia);
		builder.append(", maxEducation=");
		builder.append(maxEducation);
		builder.append(", taxNo=");
		builder.append(taxNo);
		builder.append(", workingStatus=");
		builder.append(workingStatus);
		builder.append(", marriedStatus=");
		builder.append(marriedStatus);
		builder.append(", workNumber=");
		builder.append(workNumber);
		builder.append(", insureNo=");
		builder.append(insureNo);
		builder.append(", fund=");
		builder.append(fund);
		builder.append(", address=");
		builder.append(address);
		builder.append(", zipCode=");
		builder.append(zipCode);
		builder.append(", telNo=");
		builder.append(telNo);
		builder.append(", email=");
		builder.append(email);
		builder.append(", oldWorklineCode=");
		builder.append(oldWorklineCode);
		builder.append(", worklineCode=");
		builder.append(worklineCode);
		builder.append(", worklineName=");
		builder.append(worklineName);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", fullName=");
		builder.append(fullName);
		builder.append(", estimateGroupName=");
		builder.append(estimateGroupName);
		builder.append(", picture=");
		builder.append(picture);
		builder.append(", birthdateStr=");
		builder.append(birthdateStr);
		builder.append(", workingDateStr=");
		builder.append(workingDateStr);
		builder.append(", assignDateStr=");
		builder.append(assignDateStr);
		builder.append(", retireDateStr=");
		builder.append(retireDateStr);
		builder.append(", lovSexList=");
		builder.append(lovSexList);
		builder.append(", lovEmployeeTypeList=");
		builder.append(lovEmployeeTypeList);
		builder.append(", lovPositionList=");
		builder.append(lovPositionList);
		builder.append(", lovWorkLineList=");
		builder.append(lovWorkLineList);
		builder.append(", lovFacultyList=");
		builder.append(lovFacultyList);
		builder.append(", lovInsigniaList=");
		builder.append(lovInsigniaList);
		builder.append(", lovMarriedStatusList=");
		builder.append(lovMarriedStatusList);
		builder.append(", lovEducationList=");
		builder.append(lovEducationList);
		builder.append(", lovWorkingStatusList=");
		builder.append(lovWorkingStatusList);
		builder.append("]");
		return builder.toString();
	}
	
	
	public String getSalaryFormatThai() {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String salaryStr = "-";
		if (null != salary) {
			salaryStr = df.format(salary) + " " + PAMConstants.rbMessage.getString("label.baht");
		}
		return salaryStr;
	}

	public String getImagePath() {
		String imagePath = "";
		if (picture != null && !"".equals(picture)) {
			imagePath = PAMConstants.rbApp.getString("profile.picture.dir") + picture;
		}
		else {
			imagePath = PAMConstants.rbApp.getString("profile.picture.dir") + PAMConstants.PROFILE_PICTURE_NO_PICTURE_JPG;
		}
		return imagePath;
	}
	
	public String getLovDesc(List<LovDetail> lovDetailList, String code) {
		String desc = "-";
		for (LovDetail lovDetail : lovDetailList) {
			if (lovDetail.getCode().equals(code)) {
				desc = lovDetail.getName();
				break;
			}
		}
		return desc;
	}
	
}
