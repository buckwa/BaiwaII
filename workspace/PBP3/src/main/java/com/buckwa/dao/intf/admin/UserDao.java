package com.buckwa.dao.intf.admin;

import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.util.BuckWaException;

public interface UserDao {
	
	public User getUser(User user);
	public void update(User user);
	public void create(User user);
	public PagingBean getAllUserByOffset(PagingBean pagingBean);		 
	public User getUserByUsername(String userName);
	public User getUserByUserId(String userId);
	public void enableUser(User user) throws BuckWaException;
	public void changePassword(User user);
	
	public boolean isUserExist(String username);
	public void delete(String username);
	
	public void updateRegister(User user);
	public void updateSignatureImagePath(User user);
	
	public void changeStatus(String username,boolean currentStatus);
	public void resetPass(String userName,String newPass);
	
}
