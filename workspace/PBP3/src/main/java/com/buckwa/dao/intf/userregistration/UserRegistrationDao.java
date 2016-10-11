package com.buckwa.dao.intf.userregistration;

import com.buckwa.domain.admin.User;
import com.buckwa.util.BuckWaException;

public interface UserRegistrationDao {

	
	public void create(User user) throws Exception;
	
	public void enableUser(User user) throws BuckWaException;
	
	public User getUserByUsername(String userName);
	
	public void update(User domain) ;
	
	public String isEmailAreadyRegistered(String email);
	
	public void createUserFromUpload(String userName) throws Exception;
	
	public User getUserByUsernameFromBuckwa(String userName);
	
	public void updateUserRegister(User user) ;
	
	 
}
