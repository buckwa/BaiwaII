package com.buckwa.domain.pam;

import java.math.BigDecimal;

import com.buckwa.domain.BaseDomain;

public class PersonEvaluateMapping extends BaseDomain {

	private String evaluateStatus;
	private String evaluateByPersonId;
	private String evaluateName;
	private BigDecimal totalScore;
	private BigDecimal totalSecoundScore;

	private BigDecimal totalFirstScore;

	private WorklineMappingParent worklineMappingParent;
	private Year year;
	private Semester semester;
	private Person person;

	public String getEvaluateStatus() {
		return evaluateStatus;
	}

	public void setEvaluateStatus(String evaluateStatus) {
		this.evaluateStatus = evaluateStatus;
	}

	public WorklineMappingParent getWorklineMappingParent() {
		return worklineMappingParent;
	}

	public void setWorklineMappingParent(
			WorklineMappingParent worklineMappingParent) {
		this.worklineMappingParent = worklineMappingParent;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getEvaluateByPersonId() {
		return evaluateByPersonId;
	}

	public void setEvaluateByPersonId(String evaluateByPersonId) {
		this.evaluateByPersonId = evaluateByPersonId;
	}

	public String getEvaluateName() {
		return evaluateName;
	}

	public void setEvaluateName(String evaluateName) {
		this.evaluateName = evaluateName;
	}

	public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}

	public BigDecimal getTotalSecoundScore() {
		return totalSecoundScore;
	}

	public void setTotalSecoundScore(BigDecimal totalSecoundScore) {
		this.totalSecoundScore = totalSecoundScore;
	}

	public BigDecimal getTotalFirstScore() {
		return totalFirstScore;
	}

	public void setTotalFirstScore(BigDecimal totalFirstScore) {
		this.totalFirstScore = totalFirstScore;
	}

	@Override
	public String toString() {
		return "PersonEvaluateMapping [evaluateStatus=" + evaluateStatus
				+ ", evaluateByPersonId=" + evaluateByPersonId
				+ ", evaluateName=" + evaluateName + ", totalScore="
				+ totalScore + ", totalSecoundScore=" + totalSecoundScore
				+ ", totalFirstScore=" + totalFirstScore
				+ ", worklineMappingParent=" + worklineMappingParent
				+ ", year=" + year + ", semester=" + semester + ", person="
				+ person + "]";
	}

}
