package com.buckwa.domain.admin;

import com.buckwa.domain.BaseDomain;

public class StaffRatio extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9153007781915729046L;
	private String section_id;
	private String staff_code;
	private String staff_name;
	private Integer staff_ratio;

	public String getSection_id() {
		return section_id;
	}

	public void setSection_id(String section_id) {
		this.section_id = section_id;
	}



	public String getStaff_code() {
		return staff_code;
	}

	public void setStaff_code(String staff_code) {
		this.staff_code = staff_code;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public Integer getStaff_ratio() {
		return staff_ratio;
	}

	public void setStaff_ratio(Integer staff_ratio) {
		this.staff_ratio = staff_ratio;
	}

}
