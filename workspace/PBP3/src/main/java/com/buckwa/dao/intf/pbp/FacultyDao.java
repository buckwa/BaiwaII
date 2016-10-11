package com.buckwa.dao.intf.pbp;

import java.util.List;

import com.buckwa.domain.pbp.AcademicPerson;
import com.buckwa.domain.pbp.ChainOfCommandWrapper;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.FacultyWrapper;
import com.buckwa.domain.pbp.report.TimeTableReport;

public interface FacultyDao {
	

	public FacultyWrapper getByAcademicYear(String academicYear);
	
	public boolean isNameExist(String name);
	public void create(Faculty faculty); 
	public Faculty getById(String facultyId);
	public boolean isDepartmentExist(Faculty faculty);
	public void createDepartment(Faculty faculty);
	
	
	public void updateFaculty(Faculty faculty);
	public void deleteFacultyById(String facultyId);
	
	public Department getDepartmentById(String departmentId);
	public void updateDepartment(Department department);
	public void deleteDepartmentById(String departmentId);
	
	
	
	public ChainOfCommandWrapper getByFaculty(String facultyId,String academicYear  );
	public ChainOfCommandWrapper getByDepartment(String departmentId,String academicYear );
 
	
	public AcademicPerson getDeanByFacultyId(String facultyId,String  academicYear );
	public AcademicPerson getHeadByDepartmentId(String departmentId,String  academicYear );
	
	
	public void assignDean(String oldDean,String newDean,String  academicYear,String facultyDesc );
	public void assignHead(String oldDean,String newDean,String  academicYear ,String departmentDesc);
	
	
	public AcademicPerson getPresident(String  academicYear );
	
	public void assignPresident(String oldPresident,String newPresident,String  academicYear );
	
	public void updateFacultyWS(List<com.buckwa.ws.newws.oxm.Faculty> facultyList);
	public void updateFacultyWSChum(List<com.buckwa.ws.chum.oxm.Faculty> facultyList);
	public void updateDepartmentWS(List<com.buckwa.ws.newws.oxm.Department> departmentList);
	public void updateDepartmentWSChum(List<com.buckwa.ws.chum.oxm.Department> departmentList);
	
	public void updateTeachTableWS(int semester,List<com.buckwa.ws.newws.oxm.TeachTable> timetableList,int degree,String facultyCode,String academicYea);
	public void updateTeachTableWSChum(int semester,List<com.buckwa.ws.chum.oxm.TeachTable> timetableList,int degree,String facultyCode,String academicYea,String newRegId);

	public void addShareSubject( TimeTableReport timeTableReport ,String facultyCode);
	
	public Faculty getFacultyByCodeAndYear(String facCode ,String academicYear);
	public Department getDepartmentByUserName(String username,String academicYear );
	public Department getDepartmentByHeadUserName(String username,String academicYear );
	public Faculty getFacultyByUserNameandYear(String username,String academicYear );
	public Faculty getFacultyByDeanUserNameandYear(String username,String academicYear );
	public List<Faculty> getAllFaculty(String academicYear);
	
	public void deleteTimeTableAndMapping(String academicYear);
	public void deleteTimeTableAndMappingRegId(String academicYear,String regId,String userName);
	public void deleteTimeTableAndMappingChum(String academicYear);
	 
	 
	public List<Faculty> getFacultyListByAcademicYear( String getByAcademicYear) ;
	
	public String getFacultyByCodeByAcademicYearAndName( String getByAcademicYear,String facultyName) ;
	
	public void  assignKPIInit();
	
	public FacultyWrapper getAllMarkByAcademicYear(String  academicYear );
	
	 
	 
}
