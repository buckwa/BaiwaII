package com.buckwa.dao.intf.pam;

import java.math.BigDecimal;
import java.util.List;

import com.buckwa.domain.pam.nodetree.Kpi;
import com.buckwa.domain.pam.nodetree.KpiTemplateTree;
import com.buckwa.domain.pam.nodetree.KpiTree;
import com.buckwa.domain.pam.nodetree.Node;

public interface KpiTreeDao {
	
	public Long create(Kpi kpi);
	public Long update(Kpi kpik);
	public void remove(Kpi kpi);
	public Kpi getById(Long kpiId);
	public List<Kpi> getByParentId(Long kpiId);
	
	
	 public KpiTree getTreeById(Long id);
	 public KpiTree getTemplateByYearAndGroupId(String year,String groupId);
	 
	 public void createTree(KpiTree taskTree) ;
	 public void updateTree(KpiTree taskTree) ;
	public void getRecursive(Node<Kpi> taskElement, KpiTree tree);
	
	public KpiTree getAllTree();
	
	public Long createTreeByTemplate(KpiTemplateTree kpiTemplateTree);
	
	public KpiTree getNodeTreeByYearandEmpType(String yearid,String empTypeId);
	public KpiTree getNodeTreeByYear(String yearid);
	
	 public void editLevel1(KpiTree taskTree);
	 
	public void updateWeight(String kpi, String weight);
	
	public void updateTarget(String kpi, String weight);
	
	public void updateLevel(String levelId, String mark,String level);
	
	
}
