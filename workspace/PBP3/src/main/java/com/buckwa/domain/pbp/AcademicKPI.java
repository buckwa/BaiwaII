package com.buckwa.domain.pbp;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.buckwa.domain.BaseDomain;

public class AcademicKPI extends BaseDomain {

	private Long academicKPIId;

	private Long academicRuleId;
	private Long academicUnitId;
	private String academicYear;
	private String workTypeCode;
	private BigDecimal mark;
	private String unitCode;
	private String unitDesc;
    private String facultyCode;
	private String specialP1;
	private String specialP2;
	private String specialP3;
	private String specialP4;
	private String specialP5;
	private String totalStudentFrom;
	private String totalStudentTo;

	private String multiplyValue;

	private List<AcademicUnit> academicUnitList;

	private List<AcademicKPIAttribute> academicKPIAttributeList;

	private CommonsMultipartFile fileData;

	private String orderNo;
	private String fromRegis;
	private String errorDesc;
	private Long academicKPIUserMappingId;

	private String replyMessage;
	// new field by tum 19/07/2014
	private Integer ratio;

	private String remark;

	private List<String> tmpFileNameList;

	private String index;

	public String getOrderNo() {
		return orderNo;
	}

	public String getFacultyCode() {
		return facultyCode;
	}

	public String getSpecialP5() {
		return specialP5;
	}

	public void setSpecialP5(String specialP5) {
		this.specialP5 = specialP5;
	}

	public void setFacultyCode(String facultyCode) {
		this.facultyCode = facultyCode;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMultiplyValue() {
		return multiplyValue;
	}

	public void setMultiplyValue(String multiplyValue) {
		this.multiplyValue = multiplyValue;
	}

	public Long getAcademicKPIUserMappingId() {
		return academicKPIUserMappingId;
	}

	public void setAcademicKPIUserMappingId(Long academicKPIUserMappingId) {
		this.academicKPIUserMappingId = academicKPIUserMappingId;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public List<AcademicKPIAttribute> getAcademicKPIAttributeList() {
		return academicKPIAttributeList;
	}

	public void setAcademicKPIAttributeList(
			List<AcademicKPIAttribute> academicKPIAttributeList) {
		this.academicKPIAttributeList = academicKPIAttributeList;
	}

	public String getUnitDesc() {
		return unitDesc;
	}

	public String getFromRegis() {
		return fromRegis;
	}

	public void setFromRegis(String fromRegis) {
		this.fromRegis = fromRegis;
	}

	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getWorkTypeCode() {
		return workTypeCode;
	}

	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}

	public BigDecimal getMark() {
		return mark;
	}

	public void setMark(BigDecimal mark) {
		this.mark = mark;
	}

	public Long getAcademicKPIId() {
		return academicKPIId;
	}

	public void setAcademicKPIId(Long academicKPIId) {
		this.academicKPIId = academicKPIId;
	}

	public Long getAcademicRuleId() {
		return academicRuleId;
	}

	public void setAcademicRuleId(Long academicRuleId) {
		this.academicRuleId = academicRuleId;
	}

	public Long getAcademicUnitId() {
		return academicUnitId;
	}

	public void setAcademicUnitId(Long academicUnitId) {
		this.academicUnitId = academicUnitId;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public List<AcademicUnit> getAcademicUnitList() {
		return academicUnitList;
	}

	public void setAcademicUnitList(List<AcademicUnit> academicUnitList) {
		this.academicUnitList = academicUnitList;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public Integer getRatio() {
		return ratio;
	}

	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSpecialP1() {
		return specialP1;
	}

	public void setSpecialP1(String specialP1) {
		this.specialP1 = specialP1;
	}

	public String getSpecialP2() {
		return specialP2;
	}

	public void setSpecialP2(String specialP2) {
		this.specialP2 = specialP2;
	}

	public String getSpecialP3() {
		return specialP3;
	}

	public void setSpecialP3(String specialP3) {
		this.specialP3 = specialP3;
	}

	public String getSpecialP4() {
		return specialP4;
	}

	public void setSpecialP4(String specialP4) {
		this.specialP4 = specialP4;
	}

	public String getTotalStudentFrom() {
		return totalStudentFrom;
	}

	public void setTotalStudentFrom(String totalStudentFrom) {
		this.totalStudentFrom = totalStudentFrom;
	}

	public String getTotalStudentTo() {
		return totalStudentTo;
	}

	public void setTotalStudentTo(String totalStudentTo) {
		this.totalStudentTo = totalStudentTo;
	}

	public List<String> getTmpFileNameList() {
		return tmpFileNameList;
	}

	public void setTmpFileNameList(List<String> tmpFileNameList) {
		this.tmpFileNameList = tmpFileNameList;
	}

	public String getReplyMessage() {
		return replyMessage;
	}

	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

}
