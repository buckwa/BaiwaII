package com.buckwa.web.util;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.buckwa.domain.pbp.AcademicYear;
import com.buckwa.domain.pbp.Faculty;

@Service("academicYearUtil") 
public class AcademicYearUtil {
	
	
private String academicYear;

private String systemYearThai;

public String getAcademicYear() {
	return academicYear;
}

public void setAcademicYear(String academicYear) {
	this.academicYear = academicYear;
}


public List<AcademicYear> academicYearList;

public List<Faculty> facultyList;

public List<AcademicYear> getAcademicYearList() {
	return academicYearList;
}

public void setAcademicYearList(List<AcademicYear> academicYearList) {
	this.academicYearList = academicYearList;
}


public List<Faculty> getFacultyList() {
	return facultyList;
}

public void setFacultyList(List<Faculty> facultyList) {
	this.facultyList = facultyList;
}

public String getSystemYearThai() {
	
	try{
		
		String year = DateFormatUtils.format(new Date(), "yyyy", new Locale( "en", "US"));
		
		int newYear = Integer.parseInt(year)+543;
		
		systemYearThai =newYear+"";
	}catch(Exception ex){
		ex.printStackTrace();
	}
	return systemYearThai;
}

public void setSystemYearThai(String systemYearThai) {
	this.systemYearThai = systemYearThai;
}

 
	 
	
	 
}
