package com.buckwa.dao.intf.pbp;

import com.buckwa.domain.pbp.MarkRank;
import com.buckwa.domain.pbp.MarkRankWrapper;

public interface MarkRankDao {
	

	public MarkRankWrapper getByAcademicYear(String academicYear); 
	public boolean isExist(MarkRank domain);
	public void create(MarkRank domain);
	public void addNew(MarkRank domain);
	public void delete(String markRankId);
	public void edit(MarkRankWrapper domain);
	 
	public MarkRankWrapper getByAcademicYearRound(String academicYear,String employeeType,String round); 
	
	public void addNewRound(MarkRank domain);
	public void editRound(MarkRankWrapper domain);
	
	
	public MarkRankWrapper getByRound(String academicYear); 
	
}
