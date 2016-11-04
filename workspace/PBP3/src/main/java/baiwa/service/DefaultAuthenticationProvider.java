package baiwa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import baiwa.dao.UserAttemptDao;


@Component("authenticationProvider")
public class DefaultAuthenticationProvider extends DaoAuthenticationProvider {
	
	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}
	
	@Autowired
	@Qualifier("passwordEncoder")
	@Override
	public void setPasswordEncoder(Object passwordEncoder) {
		super.setPasswordEncoder(passwordEncoder);
	}
	
	@Autowired
	private UserAttemptDao userAttemptDao;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("authenticate");
		
		try {

			Authentication auth = super.authenticate(authentication);

			// if reach here, means login success, else exception will be thrown
			// reset the ADM_USER_ATTEMPT.ATTEMPTS
			//userAttemptService.resetFailAttempt(authentication.getName());
			
			return auth;

		} catch (BadCredentialsException e) {

			// invalid login, update to ADM_USER_ATTEMPT.ATTEMPTS
			//userAttemptService.updateFailAttempt(authentication.getName());
			
			logger.error(e.getMessage(), e);
			throw e;

		} catch (LockedException e) {

			// this user is locked!
			String error = "";
//			UserAttempt userAttempt = userAttemptDao.findByUsername(authentication.getName());
//			if (userAttempt != null) {
//				Date lastAttempts = userAttempt.getLastModified();
//				error = "User account is locked! <br><br>Username : " + authentication.getName() + "<br>Last Attempts : " + lastAttempts;
//			} else {
//				error = e.getMessage();
//			}
//			
			logger.error(e.getMessage(), e);
			throw new LockedException(error);
		}
	}

	

}
