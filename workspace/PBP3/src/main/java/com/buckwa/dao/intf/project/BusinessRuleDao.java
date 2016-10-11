package com.buckwa.dao.intf.project;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.BusinessRule;

public interface BusinessRuleDao {
	 
	public void update(BusinessRule businessRule);
	public void create(BusinessRule businessRule);
	public PagingBean getByOffset(PagingBean pagingBean);
	public List<BusinessRule> getAllBusinessRule();
	public List<BusinessRule> getAllBusinessRuleByProjectId(String projectId);
	
	public BusinessRule getBusinessRuleById(String businessRuleId);
	public void deleteBusinessRuleById(String businessRuleId);
 
	public boolean isBusinessRuleAlreadyUsege(String businessRuleId);
	 
 
	
}
