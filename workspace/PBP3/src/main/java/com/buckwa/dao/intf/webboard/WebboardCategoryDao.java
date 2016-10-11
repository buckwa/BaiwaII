package com.buckwa.dao.intf.webboard;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.util.Category;

public interface WebboardCategoryDao {
	
 
	public void update(Category category) throws Exception ;
	public void create(Category category)  throws Exception ;
	public PagingBean getAllByOffset(PagingBean pagingBean);
	public List<Category> getAll();
	public Category getById(String categoryId);
	public void deleteById(String categoryId);
	public String getIdFromCategoryName(String categoryName);
	public boolean isAlreadyUsege(String categoryId);
	public boolean isExist(String name);
	public boolean isExistForUpdate( String name,Long id);
	
 
 
	
}
