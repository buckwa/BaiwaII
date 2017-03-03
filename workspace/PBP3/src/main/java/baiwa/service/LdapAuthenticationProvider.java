package baiwa.service;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import baiwa.dao.UserAttemptDao;

@Component("LdapAuthenticationProvider")
public class LdapAuthenticationProvider extends DaoAuthenticationProvider {

	 @Autowired
	 @Qualifier("userDetailsService")
	 @Override
	 public void setUserDetailsService(UserDetailsService userDetailsService) {
	  super.setUserDetailsService(userDetailsService);
	 }

//	@Autowired
//	@Qualifier("passwordEncoder")
//	@Override
//	public void setPasswordEncoder(Object passwordEncoder) {
//		super.setPasswordEncoder(passwordEncoder);
//	}

	@Autowired
	private UserAttemptDao userAttemptDao;

	// @Autowired
	// private UserAttemptServiceImpl userAttemptService;

	public boolean authenticate_2(Object userDn, Object credentials) {
		LdapContext ctxGC = null;
		try {
			logger.info("กำลัง2");
	        Hashtable<String,String> env = new Hashtable<String,String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://161.246.34.181:389/dc=kmitl,dc=ac,dc=th");

	        env.put(Context.SECURITY_PRINCIPAL, "uid="+userDn+",ou=per,ou=eng,ou=bkk,dc=kmitl,dc=ac,dc=th");
	        env.put(Context.SECURITY_CREDENTIALS, credentials.toString());
	        
			ctxGC = new InitialLdapContext(env, null);

			return true;
		} catch (Exception e) {
			// Context creation failed - authentication did not succeed
			logger.error("Login failed", e);
			return false;
		} finally {
			// It is imperative that the created DirContext instance is always
			// closed
			LdapUtils.closeContext(ctxGC);
		}
	}
	
	public boolean authenticate_1(Object userDn, Object credentials) {
		LdapContext ctxGC = null;
		try {
			logger.info("กำลัง1");
	        Hashtable<String,String> env = new Hashtable<String,String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://161.246.34.43:389/dc=kmitl,dc=ac,dc=th");

	        env.put(Context.SECURITY_PRINCIPAL, "mail="+userDn+",ou=per,ou=eng,ou=bkk,ou=People,dc=kmitl,dc=ac,dc=th");
	        env.put(Context.SECURITY_CREDENTIALS, credentials.toString());
	        
			ctxGC = new InitialLdapContext(env, null);

			return true;
		} catch (Exception e) {
			// Context creation failed - authentication did not succeed
			logger.error("Login failed", e);
			return false;
		} finally {
			// It is imperative that the created DirContext instance is always
			// closed
			LdapUtils.closeContext(ctxGC);
		}
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("authenticate");
		Authentication auth = null ;
		try {
			
			 boolean authenResult = false;
			boolean Result = authenticate_2(authentication.getPrincipal(),authentication.getCredentials());
			
			if(Result){
					//auth = super.authenticate(authentication);
				
				authenResult =true;
				logger.info("ผ่าน2");
			}else{
				boolean Result2 = authenticate_1(authentication.getPrincipal(),authentication.getCredentials());
				if(Result2){

					authenResult =true;
					logger.info("ผ่าน1");
				}
			}


			//return auth;
			
			if(authenResult){
				 UserDetails user = this.getUserDetailsService().loadUserByUsername(authentication.getPrincipal().toString());
				    if(user != null){
				    	logger.info("กำลังเข้าระบบ");
				        Authentication token = new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());

				        return token;
				    }
			}else{
			

			    return null;
			}
		} catch (BadCredentialsException e) {

			// invalid login, update to ADM_USER_ATTEMPT.ATTEMPTS
			// userAttemptService.updateFailAttempt(authentication.getName());

			logger.error(e.getMessage(), e);
			throw e;

		} catch (LockedException e) {

			// this user is locked!
			String error = "";
			// UserAttempt userAttempt =
			// userAttemptDao.findByUsername(authentication.getName());
			// if (userAttempt != null) {
			// Date lastAttempts = userAttempt.getLastModified();
			// error = "User account is locked! <br><br>Username : " +
			// authentication.getName() + "<br>Last Attempts : " + lastAttempts;
			// } else {
			// error = e.getMessage();
			// }
			System.out.println();
			logger.error(e.getMessage(), e);
			throw new LockedException(error);
		}
		return auth;
	}

}
