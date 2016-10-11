package com.buckwa.dao.intf.pbp;

import com.buckwa.domain.pbp.AcademicUnit;
import com.buckwa.domain.pbp.AcademicUnitWrapper;

public interface AcademicUnitDao {
	

	public AcademicUnitWrapper getByAcademicYear(String academicYear);
	
	public boolean isNameExist(AcademicUnit academicUnit);
	public void create(AcademicUnit domain);
	
	public void update(AcademicUnit domain);
	
	public boolean isExistForUpdate(AcademicUnit domain);
	
	
	public AcademicUnit getById(String id);
	public void deleteById(String id);
	public boolean isAlreadyUsege(String id);
	public void addNew(AcademicUnit domain);
	public void edit(AcademicUnitWrapper domain);
	 
	
	
	
}
