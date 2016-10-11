package com.buckwa.domain.pbp.report;

import java.io.Serializable;
import java.util.List;

import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.LovDetail;
import com.buckwa.domain.pbp.AcademicYear;

public class TimeTableReport implements Serializable{
	
	private String timetableId;
	private String subjectCode;
	private String subjectName;
	private String academicYear;
	private String secNo;
	private String semester;
	private String teacherName;
	private String thaiName;
	private String engName;
	
	private String facultyId;
	private String deptId;
	private String teachTime1;
	private String teachTime2;
	
	private String credit;
	private String totalStudent;
	private String degree;
	
	private String teachDay;
	
	private String teachDayStr;
	
	private String degreeStr;
	
	private String teachTimeFromTo;
	
	private User user;
	
	private String isTA;
	private String isProjectBase;
	
	private String teachHrEdit;
	
	private String remark;
	
	
	
	
	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getTeachHrEdit() {
		return teachHrEdit;
	}


	public void setTeachHrEdit(String teachHrEdit) {
		this.teachHrEdit = teachHrEdit;
	}


	public String getIsTA() {
		return isTA;
	}


	public void setIsTA(String isTA) {
		this.isTA = isTA;
	}


	public String getIsProjectBase() {
		return isProjectBase;
	}


	public void setIsProjectBase(String isProjectBase) {
		this.isProjectBase = isProjectBase;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getTimetableId() {
		return timetableId;
	}


	public void setTimetableId(String timetableId) {
		this.timetableId = timetableId;
	}
	private List<AcademicYear> academicYearList;
 
	private String academicYearSelect;
	
	private String lecOrPrac;
	private String teachHr;
	
	private String lectHr;
	private String pracHr;
	
	private String teacherId;

	
	private List<LovDetail> lecOrPracList;
	private List<LovDetail> degreeLevelList;
	private List<LovDetail> thaiShortDateList;
	
public String getTeachTimeFromTo() {
	String returnstr = "";
	if(teachTime1!=null&&teachTime2!=null){
		returnstr = teachTime1+" - "+teachTime2;
	}
		return returnstr;
	}


	public void setTeachTimeFromTo(String teachTimeFromTo) {
		this.teachTimeFromTo = teachTimeFromTo;
	}


public String getFacultyId() {
		return facultyId;
	}


	public void setFacultyId(String facultyId) {
		this.facultyId = facultyId;
	}


	public String getDeptId() {
		return deptId;
	}


	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}


public String getTeacherId() {
		return teacherId;
	}


	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}


public String getLectHr() {
		return lectHr;
	}


	public void setLectHr(String lectHr) {
		this.lectHr = lectHr;
	}


	public String getPracHr() {
		return pracHr;
	}


	public void setPracHr(String pracHr) {
		this.pracHr = pracHr;
	}


public String getDegreeStr() {
	String returnStr ="";
	if(degree!=null){
		if("1".equals(degree)){
			returnStr="ป. ตรี";
		}else if("2".equals(degree)){
			returnStr="บัณฑิตศึกษา";
		}
	}else{
		 
	}
		return returnStr;
	}


	public String getTeachDay() {
	return teachDay;
}


public String getAcademicYearSelect() {
		return academicYearSelect;
	}


	public void setAcademicYearSelect(String academicYearSelect) {
		this.academicYearSelect = academicYearSelect;
	}


public String getLecOrPrac() {
		return lecOrPrac;
	}


	public void setLecOrPrac(String lecOrPrac) {
		this.lecOrPrac = lecOrPrac;
	}


	public String getTeachHr() {
		String returnStr ="";
		try{
			
			if("ท".equalsIgnoreCase(this.lecOrPrac)){
				returnStr = this.lectHr;
			}else{
				returnStr = this.pracHr;
			}
		}catch(Exception ex){
			
		}
		return returnStr;
	}


	public void setTeachHr(String teachHr) {
		this.teachHr = teachHr;
	}


public List<AcademicYear> getAcademicYearList() {
		return academicYearList;
	}


	public void setAcademicYearList(List<AcademicYear> academicYearList) {
		this.academicYearList = academicYearList;
	}


public void setTeachDay(String teachDay) {
	this.teachDay = teachDay;
}


public String getTeachDayStr() {
	String returnStr ="";
	if(teachDay!=null){
		if(teachDay.equals("1")){
			
			returnStr="อาทิตย์";
		}else if(teachDay.equals("2")){
			
			returnStr="จันทร์";
		}else if(teachDay.equals("3")){
			
			returnStr="อังคาร";
		}else if(teachDay.equals("4")){
			
			returnStr="พุธ";
		}else if(teachDay.equals("5")){
			
			returnStr="พฤหัส.";
		}else if(teachDay.equals("6")){
			
			returnStr="ศุกร์";
		}else if(teachDay.equals("7")){
			
			returnStr="เสาร์";
		}else{
			
		}
		
	}
	return returnStr;
}


public void setTeachDayStr(String teachDayStr) {
	this.teachDayStr = teachDayStr;
}


	public void setDegreeStr(String degreeStr) {
		this.degreeStr = degreeStr;
	}
public String getTeachTime1() {
		return teachTime1;
	}
	public void setTeachTime1(String teachTime1) {
		this.teachTime1 = teachTime1;
	}
	public String getTeachTime2() {
		return teachTime2;
	}
	public void setTeachTime2(String teachTime2) {
		this.teachTime2 = teachTime2;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getTotalStudent() {
		return totalStudent;
	}
	public void setTotalStudent(String totalStudent) {
		this.totalStudent = totalStudent;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
public String getThaiName() {
		return thaiName;
	}
	public void setThaiName(String thaiName) {
		this.thaiName = thaiName;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	//	private String count;
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSecNo() {
		return secNo;
	}
	public void setSecNo(String secNo) {
		this.secNo = secNo;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}


	public List<LovDetail> getLecOrPracList() {
		return lecOrPracList;
	}


	public void setLecOrPracList(List<LovDetail> lecOrPracList) {
		this.lecOrPracList = lecOrPracList;
	}


	public List<LovDetail> getDegreeLevelList() {
		return degreeLevelList;
	}


	public void setDegreeLevelList(List<LovDetail> degreeLevelList) {
		this.degreeLevelList = degreeLevelList;
	}


	public List<LovDetail> getThaiShortDateList() {
		return thaiShortDateList;
	}


	public void setThaiShortDateList(List<LovDetail> thaiShortDateList) {
		this.thaiShortDateList = thaiShortDateList;
	}
	
	

}
