package com.buckwa.service.intf.pbp;

import java.util.List;
import java.util.Map;



public interface SchoolUtilDao {

	 // Test
	public String getCurrentAcademicYear();
	
	public String  getCourseNameByCourseId(Long courseId);
	public String  getUnitDescMyCode(String courseId,String academicYear);
	
	
	public String  getUserNameFromRegId(String courseId,String academicYear  );
	public String  getFacultyCodeFromRegId(String courseId,String academicYear  );
	
	public String  getDepartmentByUserName(String userName,String academicYear );
	public String  getDepartmentByHeadUserName(String userName,String academicYear );
	
	public String  getRegIdFromUserName(String courseId ,String academicYear );
	public String  getFacutyByUserName(String userName,String academicYear );
	public String  getFacutyByDeanUserName(String userName,String academicYear );
	public List<Map<String, Object>> getAllMapUnitDesc();
	
	public String getFacultyCodeByFacultyName (String  facultyName,String academicYear) ;
	public String getFacultyCodeByDepartmentName (String  facultyName,String academicYear) ;
	
	public String getDepartmentCodeByDepartmentName (String  departmentName,String academicYear);
	
	public String getHeadUserNamebyDepartmentDesc (String  facultyDesc,String  departmentDesc,String academicYear);
	
}
