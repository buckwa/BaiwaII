package baiwa.service;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
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

	private static DirContext ldapContext () throws Exception {
		Hashtable<String,String> env = new Hashtable <String,String>();
		return ldapContext(env);
	}

	private static DirContext ldapContext (Hashtable <String,String>env) throws Exception {
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://161.246.34.181:389/dc=kmitl,dc=ac,dc=th");
		DirContext ctx = new InitialDirContext(env);
		return ctx;
	}
	
	private static String getUid (String user) throws Exception {
		DirContext ctx = ldapContext();

		String filter = "(uid=" + user + ")";
		SearchControls ctrl = new SearchControls();
		ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration answer = ctx.search("", filter, ctrl);

		String dn;
		if (answer.hasMore()) {
			SearchResult result = (SearchResult) answer.next();
			dn = result.getNameInNamespace();
		}
		else {
			dn = null;
		}
		answer.close();
		return dn;
	}
	
	public boolean authenticate_2(Object userDn, Object credentials) {
		LdapContext ctxGC = null;
		try {
			logger.info("กำลัง2");
	        Hashtable<String,String> env = new Hashtable<String,String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://161.246.34.181:389/dc=kmitl,dc=ac,dc=th");
			
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			String dn = getUid(userDn.toString());
//			System.out.println(dn);
//	        env.put(Context.SECURITY_PRINCIPAL, "uid="+userDn+",ou=per,dc=kmitl,dc=ac,dc=th");
			env.put(Context.SECURITY_PRINCIPAL, dn);
	        env.put(Context.SECURITY_CREDENTIALS, credentials.toString());
	        
//	        System.out.println("UserDn :"+userDn.toString());
//	        System.out.println("Credentials :"+credentials.toString());
	        
			ctxGC = new InitialLdapContext(env, null);
			System.out.println("ctxGC :"+ctxGC);
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
	        env.put(Context.SECURITY_PRINCIPAL, "uid="+userDn+",emp=staff,ou=people,dc=kmitl,dc=ac,dc=th");
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
			boolean Result = authenticate_1(authentication.getPrincipal(),authentication.getCredentials());
			
			if(Result){
					//auth = super.authenticate(authentication);
				
				authenResult =true;
				logger.info("ผ่าน1");
			}else{
				boolean Result2 = authenticate_2(authentication.getPrincipal(),authentication.getCredentials());
				if(Result2){

					authenResult =true;
					logger.info("ผ่าน2");
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
