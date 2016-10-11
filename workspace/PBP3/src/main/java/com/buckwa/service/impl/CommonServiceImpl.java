package com.buckwa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.admin.GroupDao;
import com.buckwa.dao.intf.admin.RoleDao;
import com.buckwa.domain.admin.Group;
import com.buckwa.domain.admin.Menu;
import com.buckwa.domain.admin.Role;
import com.buckwa.service.intf.CommonService;

@Service("commonService")
public class CommonServiceImpl implements CommonService {
	@Autowired
	private RoleDao roleDao;

	
	@Autowired
	private GroupDao groupDao;


	@Override
	public List<Role> getAllRole() {
		List roleList = new ArrayList();
		try{		
			roleList = roleDao.getAllRole();						
			
		}catch(Exception ex){
			ex.printStackTrace();		
		}

		return roleList;
	}
	
	@Override
	public List<Group> getAllGroup() {
		List roleList = new ArrayList();
		try{		
			roleList = groupDao.getAllGroup();					
		}catch(Exception ex){
			ex.printStackTrace();		
		}

		return roleList;
	}
	
	@Override
	public List<Menu> getAllMenu() {
		List roleList = new ArrayList();
		try{		
			roleList = groupDao.getAllMenu();					
		}catch(Exception ex){
			ex.printStackTrace();		
		}

		return roleList;
	}
	

	@Override
	public String getRoleIdFromRoleName(String roleName) {
		String returnstr = "";
		try{		
			returnstr = roleDao.getRoleIdFromRoleName(roleName)	;			
		}catch(Exception ex){
			ex.printStackTrace();		
		}

		return returnstr;
	}

	@Override
	public String getGroupIdFromGroupName(String groupName) {
		String returnstr = "";
		try{		
			returnstr = groupDao.getGroupIdFromGroupName(groupName)	;			
		}catch(Exception ex){
			ex.printStackTrace();		
		}

		return returnstr;
	}

	
	

}
