package com.buckwa.dao.intf.pam;

import java.math.BigDecimal;
import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.EstimateUser;
import com.buckwa.domain.pam.KpiPersonEvaluateMapping;
import com.buckwa.domain.pam.KpiYearMapping;
import com.buckwa.domain.pam.PersonEvaluateMapping;
import com.buckwa.domain.pam.Semester;
import com.buckwa.domain.pam.nodetree.EvaluateKpi;
import com.buckwa.domain.pam.nodetree.EvaluateKpiTree;
import com.buckwa.domain.pam.nodetree.Node;

/**
 *
@Author : Kroekpong Sakulchai
@Create : Aug 23, 2012 7:23:25 PM
 *
 **/
public interface PersonEvaluateMappingDao {

//	public String getEvaluateStatusByWorkLineCode(String worklineCode);
//	public String getEvaluateStatusByPersonIdYearIdSemesterId(String personId ,String yearId, String semesterId);
	public String getEvaluateStatusByPersonIdYearIdByTerm(String personId ,String yearId, String semesterId);
	public int createOrUpdatePersonEvaluate(PersonEvaluateMapping evaluateMapping);
	public void endEvaluateSession(String personId);
	public PagingBean getAllEvaluatePersonByOffset(PagingBean pagingBean);
	public BigDecimal getPersonEvaluateTotalScore(String personId);
	public List<KpiPersonEvaluateMapping> getKPIPersonEvaluate(String personId, int yearId, int semesterId);
	
	public EvaluateKpiTree getKPIEvaluateByYearAndGroupId(String yearId, String groupId);
	public EvaluateKpi getById(Long kpiId);
	public List<EvaluateKpi> getByParentId(Long kpiId);
	public void getRecursive(Node<EvaluateKpi> taskElement, EvaluateKpiTree evaluateKpiTree);
	public void createKpiYearMapping(KpiYearMapping kpiYearMapping);
	public Long createEvaluateKpiTree(EvaluateKpiTree taskTree);
	public List<KpiPersonEvaluateMapping> getEvaluateKpiEstimateScore( String personEvaluateId ,  int evaluateTerm);
	public void updateEvaluateKpiEstimateScore(String evaluateKpiId, BigDecimal estimateScore);
	public Semester getPersonEvaluateYearAndSemester(String personId);
	public List<EstimateUser> getUnderEstimateUserListByUserId(Long userId);
	
}
