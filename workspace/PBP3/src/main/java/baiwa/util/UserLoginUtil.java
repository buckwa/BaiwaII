package baiwa.util;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import baiwa.entity.UserBean;
import baiwa.model.UserDetails;

public class UserLoginUtil {
	
	private static  UserBean getUserLogin() {
		UserBean userBean = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserBean) {
				userBean = (UserBean) principal;
			} else {
				// "anonymous" user
				String username = principal.toString();
				userBean = new UserDetails(username, "", AuthorityUtils.NO_AUTHORITIES);
			}
		}
		
		return userBean;

	}
	
	public static String getCurrentUserLogin() {
		UserBean userBean = UserLoginUtil.getUserLogin();
		if (userBean != null) {
			return userBean.getUsername();
		} else {
			return "ANONYMOUS";
		}
	}
	public static String getCurrentFacultyCode(){
		
		String facultyCode = null;
		try{
		UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		facultyCode = userDetail.getFacultyID();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return facultyCode;
	}
	public static String getCurrentDepartmentCode(){
		String departmentCode = null;
		try{
		UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		departmentCode = userDetail.getDepartmentID();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return departmentCode;
	}
	public static String getCurrentAcademicYear(){
		String academicYear = null;
		try{
		UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		academicYear = userDetail.getAcademicYear();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return academicYear;
		
	}


}
