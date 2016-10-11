package com.buckwa.dao.intf.pbp;

import java.util.List;

import com.buckwa.domain.pbp.AcademicYear;
import com.buckwa.domain.pbp.AcademicYearEvaluateRound;
import com.buckwa.domain.pbp.AcademicYearWrapper;

public interface AcademicYearDao {
	

	public AcademicYearWrapper getCurrentAcademicYear( );
	public AcademicYearWrapper getFullAcademicYear( );
	
	public AcademicYearWrapper getByAcademicYear (String academicYear) ;
	
	public AcademicYearWrapper  ajustYearIncrease();
	
	public AcademicYear getByYear(   String academicYearStr);
	
	
	public void editDateAcademicYear(   AcademicYear academicYear);
	
	public AcademicYearEvaluateRound getEvaluateRoundByYear (String academicYear,String evaluateType) ;
	

	public void editDateEvaluateRound(   AcademicYearEvaluateRound academicYearEvaluateRound);
	
	
	public List<AcademicYear> getAll(   );
	
	public boolean  checkAlreadyAdjust();
	
	public String getCurrentEvalulateRoundStr( String userName,String academicYear) ;
	
}
