package com.buckwa.dao.intf.admin.address;

import java.util.List;

import com.buckwa.domain.admin.address.Mooban;
import com.buckwa.domain.common.PagingBean;

public interface MoobanDao {
	public PagingBean getByOffset(PagingBean pagingBean);
	public Long create(Mooban domain);
	public void update(Mooban domain); 
	public List<Mooban> getAll();
	public Mooban getById(String id);
	public void deleteById(String id);
	public boolean isAlreadyUsege(String id);
	public boolean isExist(String name);	 
	public boolean isExistForUpdate(String name,Long id);
	
}
