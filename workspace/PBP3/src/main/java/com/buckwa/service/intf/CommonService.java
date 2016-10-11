package com.buckwa.service.intf;

import java.util.List;

import com.buckwa.domain.admin.Group;
import com.buckwa.domain.admin.Menu;
import com.buckwa.domain.admin.Role;

public interface CommonService {	
	public List<Role> getAllRole();	
	public List<Group> getAllGroup();
	public List<Menu> getAllMenu();
	public String getRoleIdFromRoleName(String roleName);
	public String getGroupIdFromGroupName(String groupName);
	
 
	
}
