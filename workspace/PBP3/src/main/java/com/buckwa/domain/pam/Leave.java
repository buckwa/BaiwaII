package com.buckwa.domain.pam;

import java.util.Date;
import java.util.List;

import com.buckwa.domain.BaseDomain;
import com.buckwa.util.BuckWaDateUtils;

public class Leave extends BaseDomain {
	private Long leaveFlowId;
	private String docNo;
	private String leaveTypeCode;
	private Date fromDate;
	private Date toDate;
	private Long ownerId;
	private String flowStatus;
	private String ownerFullName;
	private String leaveType;
	private String fromDateStr;
	private String toDateStr;
	private List<LeaveType> leaveTypes;
	private Long currentUserId;
	private String cancelAction;
	private String approveAction;
	private String cancalLeaveAction;
	private int isCancel;
	private String cancelMsg;
	private int leaveBalance;
	private List<SummaryLeave> summaryLeaves;
	private boolean officerApprove;
	private String firstName;
	private String lastName;

	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isOfficerApprove() {
		return officerApprove;
	}

	public void setOfficerApprove(boolean officerApprove) {
		this.officerApprove = officerApprove;
	}

	public List<SummaryLeave> getSummaryLeaves() {
		return summaryLeaves;
	}

	public void setSummaryLeaves(List<SummaryLeave> summaryLeaves) {
		this.summaryLeaves = summaryLeaves;
	}

	public int getLeaveBalance() {
		return leaveBalance;
	}

	public void setLeaveBalance(int leaveBalance) {
		this.leaveBalance = leaveBalance;
	}

	private ResearchLeave researchLeave;
	private VacationLeave vacationLeave;
	private SickLeave sickLeave;
	private PersonalLeave personalLeave;
	private MaternityLeave maternityLeave;
	private MonkhoodLeave monkhoodLeave;
	private CancelLeave cancelLeave;
	private LeaveComment leaveComment;
	private List<LeaveComment> leaveComments;

	public String getCancelMsg() {
		return cancelMsg;
	}

	public void setCancelMsg(String cancelMsg) {
		this.cancelMsg = cancelMsg;
	}

	public int getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(int isCancel) {
		this.isCancel = isCancel;
	}

	public String getCancalLeaveAction() {
		return cancalLeaveAction;
	}

	public void setCancalLeaveAction(String cancalLeaveAction) {
		this.cancalLeaveAction = cancalLeaveAction;
	}

	public LeaveComment getLeaveComment() {
		return leaveComment;
	}

	public void setLeaveComment(LeaveComment leaveComment) {
		this.leaveComment = leaveComment;
	}

	public List<LeaveComment> getLeaveComments() {
		return leaveComments;
	}

	public void setLeaveComments(List<LeaveComment> leaveComments) {
		this.leaveComments = leaveComments;
	}

	public CancelLeave getCancelLeave() {
		return cancelLeave;
	}

	public void setCancelLeave(CancelLeave cancelLeave) {
		this.cancelLeave = cancelLeave;
	}

	public MonkhoodLeave getMonkhoodLeave() {
		return monkhoodLeave;
	}

	public void setMonkhoodLeave(MonkhoodLeave monkhoodLeave) {
		this.monkhoodLeave = monkhoodLeave;
	}

	public MaternityLeave getMaternityLeave() {
		return maternityLeave;
	}

	public void setMaternityLeave(MaternityLeave maternityLeave) {
		this.maternityLeave = maternityLeave;
	}

	public PersonalLeave getPersonalLeave() {
		return personalLeave;
	}

	public void setPersonalLeave(PersonalLeave personalLeave) {
		this.personalLeave = personalLeave;
	}

	public SickLeave getSickLeave() {
		return sickLeave;
	}

	public void setSickLeave(SickLeave sickLeave) {
		this.sickLeave = sickLeave;
	}

	public ResearchLeave getResearchLeave() {
		return researchLeave;
	}

	public void setResearchLeave(ResearchLeave researchLeave) {
		this.researchLeave = researchLeave;
	}

	public VacationLeave getVacationLeave() {
		return vacationLeave;
	}

	public void setVacationLeave(VacationLeave vacationLeave) {
		this.vacationLeave = vacationLeave;
	}

	public String getCancelAction() {
		return cancelAction;
	}

	public void setCancelAction(String cancelAction) {
		this.cancelAction = cancelAction;
	}

	public String getApproveAction() {
		return approveAction;
	}

	public void setApproveAction(String approveAction) {
		this.approveAction = approveAction;
	}

	public Long getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(Long currentUserId) {
		this.currentUserId = currentUserId;
	}

	public List<LeaveType> getLeaveTypes() {
		return leaveTypes;
	}

	public void setLeaveTypes(List<LeaveType> leaveTypes) {
		this.leaveTypes = leaveTypes;
	}

	public String getFromDateStr() {
		return BuckWaDateUtils.get_current_ddMMMyyyy_thai_from_date(fromDate);
	}

	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr() {
		return BuckWaDateUtils.get_current_ddMMMyyyy_thai_from_date(toDate);
	}

	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}

	public Long getLeaveFlowId() {
		return leaveFlowId;
	}

	public void setLeaveFlowId(Long leaveFlowId) {
		this.leaveFlowId = leaveFlowId;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getLeaveTypeCode() {
		return leaveTypeCode;
	}

	public void setLeaveTypeCode(String leaveTypeCode) {
		this.leaveTypeCode = leaveTypeCode;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerFullName() {
		return ownerFullName;
	}

	public void setOwnerFullName(String ownerFullName) {
		this.ownerFullName = ownerFullName;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}
}
