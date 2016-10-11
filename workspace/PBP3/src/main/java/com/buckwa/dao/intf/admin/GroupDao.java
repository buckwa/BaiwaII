package com.buckwa.dao.intf.admin;

import java.util.List;

import com.buckwa.domain.admin.Group;
import com.buckwa.domain.admin.Menu;
import com.buckwa.domain.common.PagingBean;

public interface GroupDao {	

	public Group getGroup(Group role);
	public void update(Group role);
	public void create(Group role);
	public List<Group> getAllGroup();
	public List<Menu> getAllMenu();
	public Group getGroupById(String roleId);
	public PagingBean getAllGroupByOffset(PagingBean pagingBean);
	
	public void deleteGroupById(String groupId) ;
	public String getGroupIdFromGroupName(String groupName);
	
	
	public boolean isGroupAlreadyUsege(String groupId);
	public boolean isGroupExist(String groupName);
	
	public boolean isGroupExistForUpdate(String groupName,String id);
	
}
