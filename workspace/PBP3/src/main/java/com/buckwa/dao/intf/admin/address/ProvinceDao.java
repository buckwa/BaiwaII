package com.buckwa.dao.intf.admin.address;

import java.util.List;

import com.buckwa.domain.admin.address.Province;
import com.buckwa.domain.common.PagingBean;

public interface ProvinceDao {
	public PagingBean getByOffset(PagingBean pagingBean);
	public Long create(Province domain);
	public void update(Province domain); 
	public List<Province> getAll();
	public Province getById(String id);
	public void deleteById(String id);
	public boolean isAlreadyUsege(String id);
	public boolean isExist(String name);	 
	public boolean isExistForUpdate(String name,Long id);
	
}
