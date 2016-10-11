package com.buckwa.domain.pbp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.buckwa.domain.BaseDomain;
import com.buckwa.domain.pbp.report.RadarPlotReport;

public class PBPWorkTypeWrapper  extends BaseDomain{
	
	private List<PBPWorkType> pBPWorkTypeList;
	private String academicYear;
 
	private String userName;
	private String round; 
	private String employeeType; 
	private String facultyCode;
	
	private String isFirstLogin;
	
	private BigDecimal totalMark = new BigDecimal(0.00);
	private BigDecimal totalMarkCompareBase = new BigDecimal(0.00);
	private BigDecimal totalPercentMark = new BigDecimal(0.00);
	private BigDecimal totalPercentMarkCompareBase = new BigDecimal(0.00);
	
	private BigDecimal totalMark_E = new BigDecimal(0.00);
	private BigDecimal totalMarkCompareBase_E = new BigDecimal(0.00);
	private BigDecimal totalPercentMark_E = new BigDecimal(0.00);
	private BigDecimal totalPercentMarkCompareBase_E = new BigDecimal(0.00);
	
	private BigDecimal totalMarkPredic = new BigDecimal(0.00);
	private BigDecimal totalMarkCompareBasePredic = new BigDecimal(0.00);
	private BigDecimal totalPercentMarkPredic = new BigDecimal(0.00);
	private BigDecimal totalPercentMarkCompareBasePredic = new BigDecimal(0.00);
	
	private int increaseSalaryRate;
	private List<AcademicYear> academicYearList;
	
	private List<Faculty> facultyList ;
	private String facultyCodeSelect;
	private String facultyName;
	
	private List<RadarPlotReport>  radarPlotReportList;
	
	private Date startRoundDate;
	private Date endRoundDate;
	
	
	 
	public String getIsFirstLogin() {
		return isFirstLogin;
	}
	public void setIsFirstLogin(String isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}
	public Date getStartRoundDate() {
		return startRoundDate;
	}
	public void setStartRoundDate(Date startRoundDate) {
		this.startRoundDate = startRoundDate;
	}
	public Date getEndRoundDate() {
		return endRoundDate;
	}
	public void setEndRoundDate(Date endRoundDate) {
		this.endRoundDate = endRoundDate;
	}
	public List<RadarPlotReport> getRadarPlotReportList() {
		return radarPlotReportList;
	}
	public void setRadarPlotReportList(List<RadarPlotReport> radarPlotReportList) {
		this.radarPlotReportList = radarPlotReportList;
	}
	public List<PBPWorkType> getpBPWorkTypeList() {
		return pBPWorkTypeList;
	}
	public void setpBPWorkTypeList(List<PBPWorkType> pBPWorkTypeList) {
		this.pBPWorkTypeList = pBPWorkTypeList;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public BigDecimal getTotalMark() {
		return totalMark;
	}
	public void setTotalMark(BigDecimal totalMark) {
		this.totalMark = totalMark;
	}
	public BigDecimal getTotalPercentMark() {
		return totalPercentMark;
	}
	public void setTotalPercentMark(BigDecimal totalPercentMark) {
		this.totalPercentMark = totalPercentMark;
	}
	public int getIncreaseSalaryRate() {
		return increaseSalaryRate;
	}
	public void setIncreaseSalaryRate(int increaseSalaryRate) {
		this.increaseSalaryRate = increaseSalaryRate;
	}
	public BigDecimal getTotalMarkCompareBase() {
		return totalMarkCompareBase;
	}
	public void setTotalMarkCompareBase(BigDecimal totalMarkCompareBase) {
		this.totalMarkCompareBase = totalMarkCompareBase;
	}
	public BigDecimal getTotalPercentMarkCompareBase() {
		return totalPercentMarkCompareBase;
	}
	public void setTotalPercentMarkCompareBase(
			BigDecimal totalPercentMarkCompareBase) {
		this.totalPercentMarkCompareBase = totalPercentMarkCompareBase;
	}
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
	public String getFacultyCodeSelect() {
		return facultyCodeSelect;
	}
	public void setFacultyCodeSelect(String facultyCodeSelect) {
		this.facultyCodeSelect = facultyCodeSelect;
	}
	public String getFacultyName() {
		return facultyName;
	}
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	public BigDecimal getTotalMarkPredic() {
		return totalMarkPredic;
	}
	public void setTotalMarkPredic(BigDecimal totalMarkPredic) {
		this.totalMarkPredic = totalMarkPredic;
	}
	public BigDecimal getTotalMarkCompareBasePredic() {
		return totalMarkCompareBasePredic;
	}
	public void setTotalMarkCompareBasePredic(BigDecimal totalMarkCompareBasePredic) {
		this.totalMarkCompareBasePredic = totalMarkCompareBasePredic;
	}
	public BigDecimal getTotalPercentMarkPredic() {
		return totalPercentMarkPredic;
	}
	public void setTotalPercentMarkPredic(BigDecimal totalPercentMarkPredic) {
		this.totalPercentMarkPredic = totalPercentMarkPredic;
	}
	public BigDecimal getTotalPercentMarkCompareBasePredic() {
		return totalPercentMarkCompareBasePredic;
	}
	public void setTotalPercentMarkCompareBasePredic(
			BigDecimal totalPercentMarkCompareBasePredic) {
		this.totalPercentMarkCompareBasePredic = totalPercentMarkCompareBasePredic;
	}
	public BigDecimal getTotalMark_E() {
		return totalMark_E;
	}
	public void setTotalMark_E(BigDecimal totalMark_E) {
		this.totalMark_E = totalMark_E;
	}
	public BigDecimal getTotalMarkCompareBase_E() {
		return totalMarkCompareBase_E;
	}
	public void setTotalMarkCompareBase_E(BigDecimal totalMarkCompareBase_E) {
		this.totalMarkCompareBase_E = totalMarkCompareBase_E;
	}
	public BigDecimal getTotalPercentMark_E() {
		return totalPercentMark_E;
	}
	public void setTotalPercentMark_E(BigDecimal totalPercentMark_E) {
		this.totalPercentMark_E = totalPercentMark_E;
	}
	public BigDecimal getTotalPercentMarkCompareBase_E() {
		return totalPercentMarkCompareBase_E;
	}
	public void setTotalPercentMarkCompareBase_E(
			BigDecimal totalPercentMarkCompareBase_E) {
		this.totalPercentMarkCompareBase_E = totalPercentMarkCompareBase_E;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRound() {
		return round;
	}
	public void setRound(String round) {
		this.round = round;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public String getFacultyCode() {
		return facultyCode;
	}
	public void setFacultyCode(String facultyCode) {
		this.facultyCode = facultyCode;
	}
 
	

}
