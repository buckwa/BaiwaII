package com.buckwa.domain.pbp;

import java.sql.Timestamp;

import com.buckwa.domain.BaseDomain;
import com.buckwa.util.BuckWaDateUtils;

public class AcademicYearEvaluateRound  extends BaseDomain{
	
	/*
	 * DROP TABLE IF EXISTS `pbp`.`academic_evaluate_round`;
CREATE TABLE  `pbp`.`academic_evaluate_round` (
  `round_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `round1_start_date` datetime DEFAULT NULL,
  `round1_end_date` datetime DEFAULT NULL,
  `round1_status` varchar(45) DEFAULT NULL,
  `round2_start_date` datetime DEFAULT NULL,
  `round2_end_date` datetime DEFAULT NULL,
  `round2_status` varchar(45) DEFAULT NULL,
  `academic_year` varchar(45) DEFAULT NULL,
  `evaluate_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`round_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 */
	
	private Long roundId;
	private Timestamp round1StartDate;
	private Timestamp round1EndDate;
	private String round1StartDateStr;
	private String round1EndDateStr;
	
	private String round1StartDateShortThaiStr;
	private String round1EndDateShortThaiStr;
	private String round1Status;
	
	private Timestamp round2StartDate;
	private Timestamp round2EndDate;
	private String round2StartDateStr;
	private String round2EndDateStr;
	private String round2StartDateShortThaiStr;
	private String round2EndDateShortThaiStr;
	private String round2Status;
	
	
	private String academicYear;
	private String evaluateType;
	private String evaluateTypeDesc;
	
	private String canEditRound1;
	private String canEditRound2;
	
 
	public String getCanEditRound1() {
		return canEditRound1;
	}
	public void setCanEditRound1(String canEditRound1) {
		this.canEditRound1 = canEditRound1;
	}
	public String getCanEditRound2() {
		return canEditRound2;
	}
	public void setCanEditRound2(String canEditRound2) {
		this.canEditRound2 = canEditRound2;
	}
	public String getRound1StartDateShortThaiStr() {
		if(this.round1StartDate!=null){
			return BuckWaDateUtils.getShortThaiMonthFromDate(this.round1StartDate);
		}else{
			return "";
		}
		//return round1StartDateShortThaiStr;
	}
	public void setRound1StartDateShortThaiStr(String round1StartDateShortThaiStr) {
		this.round1StartDateShortThaiStr = round1StartDateShortThaiStr;
	}
	public String getRound1EndDateShortThaiStr() {
		if(this.round1EndDate!=null){
			return BuckWaDateUtils.getShortThaiMonthFromDate(this.round1EndDate);
		}else{
			return "";
		}
		//return round1EndDateShortThaiStr;
	}
	public void setRound1EndDateShortThaiStr(String round1EndDateShortThaiStr) {
		this.round1EndDateShortThaiStr = round1EndDateShortThaiStr;
	}
	public String getRound2StartDateShortThaiStr() {
		if(this.round2StartDate!=null){
			return BuckWaDateUtils.getShortThaiMonthFromDate(this.round2StartDate);
		}else{
			return "";
		}
		//return round2StartDateShortThaiStr;
	}
	public void setRound2StartDateShortThaiStr(String round2StartDateShortThaiStr) {
		this.round2StartDateShortThaiStr = round2StartDateShortThaiStr;
	}
	public String getRound2EndDateShortThaiStr() {
		if(this.round2EndDate!=null){
			return BuckWaDateUtils.getShortThaiMonthFromDate(this.round2EndDate);
		}else{
			return "";
		}
		//return round2EndDateShortThaiStr;
	}
	public void setRound2EndDateShortThaiStr(String round2EndDateShortThaiStr) {
		this.round2EndDateShortThaiStr = round2EndDateShortThaiStr;
	}
	public Long getRoundId() {
		return roundId;
	}
	public void setRoundId(Long roundId) {
		this.roundId = roundId;
	}
	public Timestamp getRound1StartDate() {
		return round1StartDate;
	}
	public void setRound1StartDate(Timestamp round1StartDate) {
		this.round1StartDate = round1StartDate;
	}
	public Timestamp getRound1EndDate() {
		return round1EndDate;
	}
	public void setRound1EndDate(Timestamp round1EndDate) {
		this.round1EndDate = round1EndDate;
	}
	public String getRound1StartDateStr() {
		return round1StartDateStr;
	}
	public void setRound1StartDateStr(String round1StartDateStr) {
		this.round1StartDateStr = round1StartDateStr;
	}
	public String getRound1EndDateStr() {
		return round1EndDateStr;
	}
	public void setRound1EndDateStr(String round1EndDateStr) {
		this.round1EndDateStr = round1EndDateStr;
	}
	public String getRound1Status() {
		return round1Status;
	}
	public void setRound1Status(String round1Status) {
		this.round1Status = round1Status;
	}
	public Timestamp getRound2StartDate() {
		return round2StartDate;
	}
	public void setRound2StartDate(Timestamp round2StartDate) {
		this.round2StartDate = round2StartDate;
	}
	public Timestamp getRound2EndDate() {
		return round2EndDate;
	}
	public void setRound2EndDate(Timestamp round2EndDate) {
		this.round2EndDate = round2EndDate;
	}
	public String getRound2StartDateStr() {
		return round2StartDateStr;
	}
	public void setRound2StartDateStr(String round2StartDateStr) {
		this.round2StartDateStr = round2StartDateStr;
	}
	public String getRound2EndDateStr() {
		return round2EndDateStr;
	}
	public void setRound2EndDateStr(String round2EndDateStr) {
		this.round2EndDateStr = round2EndDateStr;
	}
	public String getRound2Status() {
		return round2Status;
	}
	public void setRound2Status(String round2Status) {
		this.round2Status = round2Status;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getEvaluateType() {
		return evaluateType;
	}
	public void setEvaluateType(String evaluateType) {
		this.evaluateType = evaluateType;
	}
	public String getEvaluateTypeDesc() {
		return evaluateTypeDesc;
	}
	public void setEvaluateTypeDesc(String evaluateTypeDesc) {
		this.evaluateTypeDesc = evaluateTypeDesc;
	}
	
	
	
	
}
