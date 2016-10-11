package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Unit;

public interface UnitDao {
	
	public PagingBean getByOffset(PagingBean pagingBean);
	public Long create(Unit domain);
	public void update(Unit domain); 
	public List<Unit> getAll();
	public Unit getById(String id);
	public void deleteById(String id);
	public boolean isAlreadyUsege(String id);
	public boolean isExist(String name);	 
	public boolean isExistForUpdate(String name,Long id);
	
}
