package com.buckwa.dao.intf.pbp;

import java.util.List;

import com.buckwa.domain.pbp.report.TimeTableReport;

public interface PersonTimetableDao {
	

	public List<TimeTableReport> getTimeTable(String academicYear ,String userName,String semester );
	 
	
	public List<TimeTableReport> getTimeTableShare(String academicYear ,String subjectId,String semester );
	
	
	public TimeTableReport getTimeTableById(String timetableId  );
	 
	
	public void updateTimeTable(TimeTableReport timeTableReport); 
	public void createTimeTable(TimeTableReport timeTableReport); 
	
	
}
