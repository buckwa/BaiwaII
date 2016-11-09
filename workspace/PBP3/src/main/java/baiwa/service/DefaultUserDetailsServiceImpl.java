package baiwa.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import baiwa.dao.UserAttemptDao;
import baiwa.entity.UserAttempt;
import baiwa.model.UserDetails;

@Service("userDetailsService")
public class DefaultUserDetailsServiceImpl implements UserDetailsService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	
	private UserAttemptDao userAttemptDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("loadUserByUsername username=" + username);
		UserAttempt user = userAttemptDao.findByUsername(username);
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
		grantedAuthorityList = userAttemptDao.findGrantedRoleByUserId(user.getUser_id());

		UserDetails userDeail = new UserDetails(
				user.getUsername(),
				user.getPassword(),
				grantedAuthorityList
				);
		userDeail.setUserId(user.getUser_id());
		
		
		return userDeail;
	}

}
