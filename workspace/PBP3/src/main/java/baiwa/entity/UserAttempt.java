package baiwa.entity;

public class UserAttempt {
	
	private String username;
	private Long person_id;
	private long user_id;
	private String password;
	private String department_id;
	private String department_name;
	private String code;
	private String name;
	private String academic_year;
	private String thai_name;
	private String thai_surname;
	
	public String getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}
	public String getAcademic_year() {
		return academic_year;
	}
	public void setAcademic_year(String academic_year) {
		this.academic_year = academic_year;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getPerson_id() {
		return person_id;
	}
	public void setPerson_id(Long person_id) {
		this.person_id = person_id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	public String getThai_name() {
		return thai_name;
	}
	public void setThai_name(String thai_name) {
		this.thai_name = thai_name;
	}
	public String getThai_surname() {
		return thai_surname;
	}
	public void setThai_surname(String thai_surname) {
		this.thai_surname = thai_surname;
	}
	
	
	
	

}
