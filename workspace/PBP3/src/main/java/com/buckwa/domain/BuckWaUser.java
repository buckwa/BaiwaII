package com.buckwa.domain;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.buckwa.domain.admin.Menu;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.project.Project;

public class BuckWaUser extends User {

 
	private static final long serialVersionUID = 4547764393255667447L;
   
	private Long userId;
	private List<Menu> menuList;
	private List<Project> projectList;
	GrantedAuthority []authorities;
	
	private Person personProfile ;
	private String facultyCode;
	
	private String firstLastName;
	
	private String currentRoundStr;
	
	@SuppressWarnings("deprecation")
	public BuckWaUser(
			String username, 
			String password, 
			boolean enabled,
			boolean accountNonExpired, 
			boolean credentialsNonExpired,
			boolean accountNonLocked, 			 
			List<GrantedAuthority> authorities 
			 ) {
	
	super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
 
	//	super(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

	public String getFacultyCode() {
		return facultyCode;
	}

	public void setFacultyCode(String facultyCode) {
		this.facultyCode = facultyCode;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public List<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

 

	public void setAuthorities(GrantedAuthority[] authorities) {
		this.authorities = authorities;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstLastName() {
		return firstLastName;
	}

	public void setFirstLastName(String firstLastName) {
		this.firstLastName = firstLastName;
	}

	public Person getPersonProfile() {
		return personProfile;
	}

	public void setPersonProfile(Person personProfile) {
		this.personProfile = personProfile;
	}

	public String getCurrentRoundStr() {
		return currentRoundStr;
	}

	public void setCurrentRoundStr(String currentRoundStr) {
		this.currentRoundStr = currentRoundStr;
	}

	
	
 
}
