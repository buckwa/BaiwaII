package com.buckwa.web.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import com.buckwa.domain.BuckWaUser;

/*
 @Author : Wichien Prommese(Pe)
 @Create : Aug 26, 2012 5:05:13 PM
 */
public class LdapCustomUserDetailsMapper extends LdapUserDetailsMapper {
	private static Logger logger = LoggerFactory.getLogger(LdapCustomUserDetailsMapper.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx,
			String username, Collection<? extends GrantedAuthority> authority) {

		logger.info("LDAP  username:"+username);
    	BuckWaUser user  =null;
        try{        	
        	if(!isUserEnable(username)){
        		  logger.info(" LDAP   Found user:"+username+" but not enable ");
        		  throw new Exception();
        	}
       	List< GrantedAuthority>  authorities = getAuthorities(username);     	 
         String sql = "SELECT user_id,username, password FROM buckwauser  WHERE  username = '"+username+"' and enable =1 ";         
	        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);	       
	        while (rs.next()) {
	        	user = new BuckWaUser(
	        			rs.getString("username"), 
	            		rs.getString("password"), 
	            		true, 
	            		true, 
	            		true, 
	            		true, 
	            		authorities 
	        	);
	        	user.setUserId(rs.getLong("user_id"));
	        }	
	       if(user==null){
	    	   throw new Exception();
	       }
        }catch(Exception ex){
        	  logger.info(" LDAP Login Error, Not Found user:"+username);
        	ex.printStackTrace();
        	throw new UsernameNotFoundException("User "+username+" Not Found");        	
        }
        logger.info(" Found user:"+username);
        return user; 

	}

	private List<GrantedAuthority>  getAuthorities(String username) {
		String sql = "";
		// String sql
		// =" SELECT users.username, authorities.authority as authorities FROM buckwauser users, buckwaauthorities authorities WHERE users.username = '"+username+"' AND users.username = authorities.username ";
		StringBuffer sb = new StringBuffer();
		sb.append("  select u.username , r.role_name as authorities \n");
		sb.append("  from buckwauser u   \n");
		sb
				.append("  left outer join buckwausergroup  ug on u.username = ug.username  \n");
		sb
				.append("  left outer join buckwagroup  g on ug.group_id = g.group_id  \n");
		sb
				.append("  left outer join buckwagrouprole  gr on g.group_id = gr.group_id   \n");
		sb
				.append("  left outer join buckwarole r   on gr.role_id = r.role_id  \n");
		sb.append("  where u.username= '" + username + "' group by u.username , r.role_name ");
		sql = sb.toString();
		logger.info(" sql get Authorize: " + sql);
		List<GrantedAuthority> authList = findAllAuthority(sql);
		return authList;
	}

	public List<GrantedAuthority> findAllAuthority(String sql) {
		logger.info(" jdbcTemplate:" + jdbcTemplate);
		return jdbcTemplate.query(sql, new AuthorityrMapper());
	}

	private class AuthorityrMapper implements RowMapper<GrantedAuthority> {
		public GrantedAuthority mapRow(ResultSet rs, int rowNum)
				throws SQLException {
		//	GrantedAuthorityImpl authority = new GrantedAuthorityImpl(rs
		//			.getString("authorities"));
		//	return authority;
			
			return null;
		}
	}

	/*
	 * private class UserMapper implements RowMapper<User> {
	 * 
	 * @Override public BuckWaUser mapRow(ResultSet rs, int arg1) throws
	 * SQLException { return new BuckWaUser( rs.getString("username"),
	 * rs.getString("password"), true, true, true, true, new GrantedAuthority
	 * [0], rs.getString("customer_no")); } }
	 */

	public boolean isUserEnable(String username) {
		boolean result = false;
		try {
			String sql = "SELECT enable  FROM buckwauser  WHERE  username = '"
					+ username + "'";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
			boolean enable = false;
			while (rs.next()) {
				enable = rs.getBoolean("enable");
				logger.info(" isUserEnable:" + enable);
			}
			if (enable == true) {
				result = true;
			}
			logger.info(" isUserEnable:" + enable + " result:" + result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
