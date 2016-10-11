package com.buckwa.dao.intf.pbp;

import java.util.List;

import com.buckwa.domain.pbp.PBPWorkType;
import com.buckwa.domain.pbp.PBPWorkTypeSub;
import com.buckwa.domain.pbp.PBPWorkTypeWrapper;
import com.buckwa.domain.pbp.report.RadarPlotReport;

public interface PBPWorkTypeDao {
	

//	public PBPWorkTypeWrapper getByAcademicYear(String academicYear); 
	 
	
	public PBPWorkTypeWrapper getCalculateByAcademicYear(String academicYear,String userName,String round,String employeeType,String facultyCode); 
	
	
	public PBPWorkTypeWrapper getExsistCalculateByAcademicYear(String academicYear,String userName,String round,String employeeType,String facultyCode);
	
	
	public boolean isExist(PBPWorkType domain);
	public void create(PBPWorkType domain);
	public void addNew(PBPWorkType domain);
	public void delete(String markRankId);
	public void edit(PBPWorkTypeWrapper domain);
	
	public PBPWorkType getById(String id); 
	public PBPWorkType getSub(String id); 
	
	
	public void addNewSub(PBPWorkTypeSub domain);
	public void deleteSub(String markRankId);
	public void editSub(PBPWorkType domain);
	
	public PBPWorkType getByCodeAcademicFacultyCode(String code,String academicYear,String facultyCode);
	public PBPWorkTypeWrapper getByAcademicYearFacultyCode( String getByAcademicYear,String facultyCode);
	
	
	public List<RadarPlotReport> getRadarPlotPersonMark( String userName,String academicYear,String round);
	public List<RadarPlotReport> getRadarPlotPersonMarkByYear( String userName,String academicYear);
	public List<RadarPlotReport> getRadarPlotPersonMarkE( String userName,String academicYear,String round);
	
	
	
	public PBPWorkTypeWrapper getCalculateByAcademicYearAllYear(String academicYear,String userName,String employeeType,String facultyCode); 
	
	public String  getCurretnEvalulate( String academicYear,String employeeTypeNo );

}
