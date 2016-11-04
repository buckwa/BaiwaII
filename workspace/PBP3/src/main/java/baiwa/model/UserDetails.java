package baiwa.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import baiwa.entity.UserBean;

public class UserDetails extends User implements UserBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1559040180792804240L;
	private Long userId;

	public UserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	
	public UserDetails (String username, String password ,Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


}
