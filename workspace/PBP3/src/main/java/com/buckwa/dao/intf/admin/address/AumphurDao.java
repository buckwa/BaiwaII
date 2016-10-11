package com.buckwa.dao.intf.admin.address;

import java.util.List;

import com.buckwa.domain.admin.address.Aumphur;
import com.buckwa.domain.common.PagingBean;

public interface AumphurDao {
	public PagingBean getByOffset(PagingBean pagingBean);
	public Long create(Aumphur domain);
	public void update(Aumphur domain); 
	public List<Aumphur> getAll();
	public Aumphur getById(String id);
	public void deleteById(String id);
	public boolean isAlreadyUsege(String id);
	public boolean isExist(String name);	 
	public boolean isExistForUpdate(String name,Long id);
	
}
