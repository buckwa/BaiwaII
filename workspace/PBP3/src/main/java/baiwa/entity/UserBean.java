package baiwa.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public interface  UserBean extends UserDetails{
	
	public Long getUserId();
	
	public String getUsername();
	
	public String getFacultyID();
	
	public String getAcademicYear();
	
	public String getDepartmentID();
	
	public Collection<GrantedAuthority> getAuthorities();

}
