package baiwa.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import baiwa.entity.UserBean;

public class UserDetails extends User implements UserBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1559040180792804240L;
	private Long userId;
	private Long personID;
	private String facultyID;
	private String departmentID;
	private String departmentName;
	private String academicYear;
	private String facultyName;
	private String thaiName;
	private String thaiSurname;
	GrantedAuthority []authorities;

	

	public UserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			List<GrantedAuthority> authorities ) {
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

	public String getFacultyID() {
		return facultyID;
	}

	public void setFacultyID(String facultyID) {
		this.facultyID = facultyID;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public Long getPersonID() {
		return personID;
	}

	public void setPersonID(Long personID) {
		this.personID = personID;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	
	public void setAuthorities(GrantedAuthority[] authorities) {
		this.authorities = authorities;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getThaiName() {
		return thaiName;
	}

	public void setThaiName(String thaiName) {
		this.thaiName = thaiName;
	}

	public String getThaiSurname() {
		return thaiSurname;
	}

	public void setThaiSurname(String thaiSurname) {
		this.thaiSurname = thaiSurname;
	}
	
	
	
	

	

}
