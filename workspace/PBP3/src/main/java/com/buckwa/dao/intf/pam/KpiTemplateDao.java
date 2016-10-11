package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.pam.nodetree.KpiTemplate;
import com.buckwa.domain.pam.nodetree.KpiTemplateTree;
import com.buckwa.domain.pam.nodetree.Node;

public interface KpiTemplateDao {
	
	public Long create(KpiTemplate kpi);
	public Long update(KpiTemplate kpik);
	public void remove(KpiTemplate kpi);
	public KpiTemplate getById(Long kpiId);
	public List<KpiTemplate> getByParentId(Long kpiId);
	
	
	 public KpiTemplateTree getTreeById(Long id);
	 public void createTree(KpiTemplateTree taskTree) ;
	 public void updateTree(KpiTemplateTree taskTree) ;
	public void getRecursive(Node<KpiTemplate> taskElement, KpiTemplateTree tree);
	
	public KpiTemplateTree getAllTree();
	
	
	public KpiTemplateTree getTreeByRootId(String rootId);
	
	
	public KpiTemplateTree getTreeByCategoryId(String categoryId);
	
	
	public void initialMappingByYear(List<KpiTemplateTree> kpitreeList); 
	
	
	
	
}
