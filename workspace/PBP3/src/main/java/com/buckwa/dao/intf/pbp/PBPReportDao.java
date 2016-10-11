package com.buckwa.dao.intf.pbp;

import java.util.List;

import com.buckwa.domain.pbp.report.RadarPlotReport;

public interface PBPReportDao {
	
	public  List<RadarPlotReport> getInstituteReportByAcademicYear(   String academicYear );
	public  List<RadarPlotReport> getFacultyReportByAcademicYear(   String academicYear,String faculty_code  );
	
	
}
