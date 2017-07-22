package com.buckwa.dao.intf.pbp;

import java.util.List;

import com.buckwa.domain.pbp.AcademicKPIUserMappingWrapper;
import com.buckwa.domain.pbp.AcademicPerson;

public interface AcademicKPIUserMappingDao {
	

	public AcademicKPIUserMappingWrapper getById(String id); 
	public void approve(String id,String approveBy); 
	public void update(AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper); 
	public void delete(String id); 
	public void unApprove(String id); 
	
	public void changeKPI(AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper);
	public void update2(AcademicKPIUserMappingWrapper academicKPIUserMappingWrapper, String filename); 
	public List<AcademicPerson> assignHeadDao(String  department,String  academicYear);
	public List<AcademicPerson> assignHeadNDao(String  department,String  academicYear);

	public void assignHeadDeleteDao(String Username);
	public void assignHeadUpdateDao(String Username);
	
	
}
