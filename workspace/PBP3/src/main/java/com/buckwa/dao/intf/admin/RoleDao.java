package com.buckwa.dao.intf.admin;

import java.util.List;

import com.buckwa.domain.admin.Role;
import com.buckwa.domain.common.PagingBean;

public interface RoleDao {
	

	public Role getRole(Role role);
	public void update(Role role);
	public void create(Role role);
	public PagingBean getAllRoleByOffset(PagingBean pagingBean);
	public List<Role> getAllRole();
	public Role getRoleById(String roleId);
	public void deleteRoleById(String roleId);
	public String getRoleIdFromRoleName(String roleName);
	public boolean isRoleAlreadyUsege(String roleId);
	public boolean isRoleNameExist(String roleName);
	 
	public boolean isRoleNameExistForUpdate(String roleName,Long id);
	
}
