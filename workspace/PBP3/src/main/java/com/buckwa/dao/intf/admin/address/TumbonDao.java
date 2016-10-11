package com.buckwa.dao.intf.admin.address;

import java.util.List;

import com.buckwa.domain.admin.address.Tumbon;
import com.buckwa.domain.common.PagingBean;

public interface TumbonDao {
	public PagingBean getByOffset(PagingBean pagingBean);
	public Long create(Tumbon domain);
	public void update(Tumbon domain); 
	public List<Tumbon> getAll();
	public Tumbon getById(String id);
	public void deleteById(String id);
	public boolean isAlreadyUsege(String id);
	public boolean isExist(String name);	 
	public boolean isExistForUpdate(String name,Long id);
	
}
