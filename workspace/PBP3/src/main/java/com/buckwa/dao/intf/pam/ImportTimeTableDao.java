package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.pam.TimeTable;

/**
 * 
 @Author : Kroekpong Sakulchai (Ball)
 @Create : Aug 5, 2012 10:15:14 PM
 *
 **/

public interface ImportTimeTableDao {
	
	public Long create(TimeTable timetable);
	public boolean executeSqlScript(List<String> fileList);
	public Long getCount(TimeTable timetable);
	public Long checkSection(TimeTable timetable);
	public Long addSection(final TimeTable timetable);
	public Long addstaff(final TimeTable timetable);
	public Integer getTimeTableId(final TimeTable timetable);
	public Integer getSectionId(final TimeTable timetable);
	public Integer checkStaff(TimeTable timetable);
	public void updateStaff();
	public void updateDesc(TimeTable timetable);
	
	
}
