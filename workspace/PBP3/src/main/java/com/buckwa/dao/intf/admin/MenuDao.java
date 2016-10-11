package com.buckwa.dao.intf.admin;

import java.util.List;

import com.buckwa.domain.admin.Menu;
import com.buckwa.domain.common.PagingBean;

public interface MenuDao {
	
 
	public void update(Menu menu);
	public void create(Menu menu);
	public PagingBean getAllByOffset(PagingBean pagingBean);
	public List<Menu> getAll();
	public Menu getById(String menuId);
	public void deleteById(String menuId);
	public String getIdFromMenuName(String menuName);
	public boolean isAlreadyUsege(String menuId);
	public boolean isExist(String menuName,String name);
	public boolean isExistForUpdate(String code,String name,Long id);
	
	public void moveDown(Menu menu);
	public void moveUp(Menu menu);
	
}
