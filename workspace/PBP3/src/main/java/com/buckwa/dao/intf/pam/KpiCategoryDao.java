package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.KpiCategory;

public interface KpiCategoryDao {
	
	public PagingBean getByOffset(PagingBean pagingBean);
	public Long create(KpiCategory domain);
	public void update(KpiCategory domain); 
	public List<KpiCategory> getAll();
	public KpiCategory getById(String id);
	public void deleteById(String id);
	public boolean isAlreadyUsege(String id);
	public boolean isExist(String name);	 
	public boolean isExistForUpdate(String name,Long id);
	public String getKpiCategoryIdByYearIdAndPersonType(String yearId, String personType);
	
}
