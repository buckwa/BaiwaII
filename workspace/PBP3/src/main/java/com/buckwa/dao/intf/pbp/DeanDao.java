package com.buckwa.dao.intf.pbp;

import java.util.List;

import com.buckwa.domain.pbp.Faculty;
import com.buckwa.domain.pbp.report.DepartmentWorkTypeReport;
import com.buckwa.domain.pbp.report.FacultyReportLevel;

public interface DeanDao {
	
 
	public List<FacultyReportLevel> getFacultyReportLevel(String worktype, Faculty dep);
	
	public List<DepartmentWorkTypeReport> getReportWorkTypeFaculty(String worktype, Faculty dep);
	
	public List<DepartmentWorkTypeReport> getReportWorkTypeCompareFaculty(Faculty faculty);
	
	
}
