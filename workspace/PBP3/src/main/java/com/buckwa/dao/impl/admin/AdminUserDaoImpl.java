package com.buckwa.dao.impl.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.admin.AdminUserDao;
import com.buckwa.dao.intf.pbp.FacultyDao;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pbp.Department;
import com.buckwa.domain.pbp.Faculty;
import com.buckwa.util.BuckWaException;
import com.buckwa.util.BuckWaUtils;

@Repository("userDao")
public class AdminUserDaoImpl implements AdminUserDao {

	private static Logger logger = LoggerFactory.getLogger(AdminUserDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private FacultyDao facultyDao;

	@Override
	public User getUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(User user) {
		logger.info("AdminUserDaoImpl.update(User user)");
		// Step 1 . Update User
		this.jdbcTemplate.update("update  buckwauser set " + "enable =? "
				+ ",first_name=?" + ",last_name=?" + ",address1=?"
				+ ",address2=? " + ",tel_no=? " + ",email=? "
				+ ",signature_image_path =? " + "where username=? ", user
				.isEnabled(), user.getFirst_name(), user.getLast_name(), user
				.getAddress1(), user.getAddress2(), user.getTel_no(), user
				.getEmail(), user.getSignatureImagePath(), user.getUsername());

		// Step 2 Delete All User - Group Mapping
		this.jdbcTemplate.update(
				"delete from   buckwausergroup   where username=? ", user
						.getUsername());

		// Step 3. Create User - Group Mapping
		String[] groupIdArray = user.getGroups();
		if (groupIdArray != null && groupIdArray.length > 0) {
			for (int i = 0; i < groupIdArray.length; i++) {
				String groupIdStr = groupIdArray[i];
				this.jdbcTemplate
						.update(
								"insert into buckwausergroup (username, group_id) values (?, ?)",
								user.getUsername(), groupIdStr);
			}
		}
	}

	@Override
	public void updateRegister(User user) {
		logger.info("AdminUserDaoImpl.updateRegister(User user)");
		// Step 1 . Update User
		this.jdbcTemplate.update("update  buckwauser set " + "enable =? "
				+ ",first_name=?" + ",last_name=?" + ",address1=?"
				+ ",address2=? " + ",tel_no=? " + ",email=? "
				+ ",signature_image_path =? " + "where username=? ", user
				.isEnabled(), user.getFirst_name(), user.getLast_name(), user
				.getAddress1(), user.getAddress2(), user.getTel_no(), user
				.getEmail(), user.getSignatureImagePath(), user.getUsername());

	}
	
	@Override
	public void loginLoging(String userName ,String loginStatus,String clientIP) {
		logger.info("AdminUserDaoImpl.loginLoging userName:"+userName+":"+loginStatus+":"+clientIP);
	 
		int xx = this.jdbcTemplate
				.update(
						"insert into access_log (user_login, login_status,client_ip,create_date) values (?, ?,?,NOW())",
						userName, loginStatus, clientIP  );

	}


	
	
	@Override
	public void updateSignatureImagePath(User user) {
		logger.info("AdminUserDaoImpl.updateSignatureImagePath(User user)");
		// Step 1 . Update User
		this.jdbcTemplate.update("update  buckwauser set " +

		" signature_image_path =? " + "where username=? "

		, user.getSignatureImagePath(), user.getUsername());

	}

	@Override
	public void enableUser(User user) throws BuckWaException {
		logger.info("AdminUserDaoImpl.enableUser(User user)");
		int recordUpdate = this.jdbcTemplate
				.update(
						" update  buckwauser set enable =1 where username=? and secure_code=? ",
						user.getUsername(), user.getSecureCode());

		logger.info("enableUser  recordUpdate :" + recordUpdate);
		if (!(recordUpdate > 0)) {
			throw new BuckWaException("E008", "");
		}
	}

	@Override
	public void create(User user) {
		logger.info("AdminUserDaoImpl.create(User user)");
		// Step 1 . Insert User

		// String sqlIsUserExist =
		// " select count(*) as total_item  from  buckwauser where username= '"+user.getUsername()+"'"
		// ;
		// int rows_totalItem = jdbcTemplate.queryForObject(sqlIsUserExist);

		// if(rows_totalItem>0){
		// throw new BuckWaException("E005","");
		// }else{
		int xx = this.jdbcTemplate
				.update(
						"insert into buckwauser (username, password,enable,secure_code) values (?, ?,?,?)",
						user.getUsername(), BuckWaUtils.encrypt(user.getPassword()), user
								.isEnabled(), user.getSecureCode());
		String[] groupIdArray = user.getGroups();
		if (groupIdArray != null && groupIdArray.length > 0) {
			// Step 2. Create User - Group Mapping
			for (int i = 0; i < groupIdArray.length; i++) {
				String groupIdStr = groupIdArray[i];
				if (StringUtils.hasLength(groupIdStr)) {
					this.jdbcTemplate
							.update(
									"insert into buckwausergroup (username, group_id) values (?, ?)",
									user.getUsername(), groupIdStr);
				}
			}
		}
		// }

	}
/*
 * Backup For Framework
 * 
	@Override
	public PagingBean getAllUserByOffset(PagingBean pagingBean) {
		User user = (User) pagingBean.get("user");
		List<User> returnList = new ArrayList<User>();
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("  select count(*) as total_item  from  buckwauser u  ");
		sqltotalsb.append(" where 1=1 ");
		if (StringUtils.hasText(user.getUsername())) {
			sqltotalsb.append(" and u.username like '%"+ StringEscapeUtils.escapeSql(user.getUsername().trim()) + "%'");
		}
		int rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class);
		pagingBean.setTotalItems(rows_totalItem);

		StringBuffer sb = new StringBuffer();
		sb.append("   select  u.username,u.password ,u.enable from buckwauser u ");
		sb.append(" where 1=1 ");
		if (StringUtils.hasText(user.getUsername())) {	
			sb.append(" and u.username like '%" + StringEscapeUtils.escapeSql(user.getUsername().trim())+ "%'");
		}
		sb.append(" LIMIT " + pagingBean.getLimitItemTo() + ","	+ pagingBean.getMaxPageItems());
		
		returnList = this.jdbcTemplate.query(sb.toString(),
				new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						User domain = new User();
						domain.setUsername(rs.getString("username"));
						domain.setPassword(rs.getString("password"));
						domain.setEnabled(rs.getBoolean("enable"));
						return domain;
					}
				});

		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	*/

	
	// Customize for PAM
	@Override
	public PagingBean getAllUserByOffset(PagingBean pagingBean) {
		logger.info("start");
		User user = (User) pagingBean.get("user");
		List<User> returnList = new ArrayList<User>();
		StringBuffer sqltotalsb = new StringBuffer();
		//sqltotalsb.append("  select count(*) as total_item  from  buckwauser u  ");
		sqltotalsb.append("   select count(*) as total_item  from  buckwauser u ");
		sqltotalsb.append("  left join person_pbp p on (u.username=p.email) ");		
		sqltotalsb.append(" where 1=1 ");		
		sqltotalsb.append(" and p.academic_year ='" + user.getAcademicYear()+"'");
		if (StringUtils.hasText(user.getUsername())) {
			sqltotalsb.append(" and u.username like '%"+ StringEscapeUtils.escapeSql(user.getUsername().trim()) + "%'");
		}
		if (StringUtils.hasText(user.getFirstLastName())) {
			sqltotalsb.append(" and CONCAT(CONCAT(p.thai_name,' '), p.thai_surname) like '%"+ StringEscapeUtils.escapeSql(user.getFirstLastName().trim()) + "%'");
		}
		
		
		int rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class);
		pagingBean.setTotalItems(rows_totalItem);

		StringBuffer sb = new StringBuffer();
		sb.append("   select  u.username,CONCAT(CONCAT(p.thai_name,' '), p.thai_surname) AS firstLastName,u.password  ,u.enable ,p.employee_type");
		sb.append("   ,p.faculty_desc,p.department_desc,p.academic_year ,p.reg_id ");
		sb.append("  from buckwauser u   ");
		sb.append("  left join person_pbp p on (u.username=p.email) ");		
		 	
		sb.append(" where 1=1 ");
		sb.append(" and p.academic_year ='" + user.getAcademicYear()+"'");
		if (StringUtils.hasText(user.getUsername())) {	
			sb.append(" and u.username like '%" + StringEscapeUtils.escapeSql(user.getUsername().trim())+ "%'");
		}
		
		if (StringUtils.hasText(user.getFirstLastName())) {
			sb.append(" and CONCAT(CONCAT(p.thai_name,' '), p.thai_surname) like '%"+ StringEscapeUtils.escapeSql(user.getFirstLastName().trim()) + "%'");
		}	
 
		
		//sb.append(" LIMIT " + pagingBean.getLimitItemFrom() + ","	+ pagingBean.getMaxPageItems());
 
		logger.info("sql:"+sb.toString());
		
		returnList = this.jdbcTemplate.query(sb.toString(),
				new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						User domain = new User();
						domain.setUsername(rs.getString("username"));
						domain.setPassword(rs.getString("password"));
						domain.setEnabled(rs.getBoolean("enable"));
						domain.setFirstLastName(rs.getString("firstLastName"));
						domain.setEmployeeType(rs.getString("employee_type"));
						domain.setFacultyDesc(rs.getString("faculty_desc"));
						domain.setDepartmentDesc(rs.getString("department_desc"));
						domain.setAcademicYear(rs.getString("academic_year"));
						domain.setRegId(rs.getString("reg_id"));
						return domain;
					}
				});

		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	
	
 
	@Override
	public PagingBean getUserByFacultyCodeOffset(PagingBean pagingBean) {
		logger.info("start");
		User user = (User) pagingBean.get("user");
		List<User> returnList = new ArrayList<User>();
		StringBuffer sqltotalsb = new StringBuffer();
		
		String facultyCode = user.getFacultyCode();
		String academicYear = user.getAcademicYear();
		
		Faculty facTmp =facultyDao.getFacultyByCodeAndYear(facultyCode, academicYear);
		
		logger.info(" Faculty Code:"+facTmp.getCode()+"   Name:"+facTmp.getName());
		
		String facultyDesc  = facTmp.getName();
		
		
		//sqltotalsb.append("  select count(*) as total_item  from  buckwauser u  ");
		sqltotalsb.append("   select count(*) as total_item  from  buckwauser u ");
		sqltotalsb.append("  left join person_pbp p on (u.username=p.email) ");		
		sqltotalsb.append(" where 1=1 ");		
		sqltotalsb.append(" and p.academic_year ='" + user.getAcademicYear()+"'");
		if (StringUtils.hasText(user.getUsername())) {
			sqltotalsb.append(" and u.username like '%"+ StringEscapeUtils.escapeSql(user.getUsername().trim()) + "%'");
		}
		if (StringUtils.hasText(user.getFirstLastName())) {
			sqltotalsb.append(" and CONCAT(CONCAT(p.thai_name,' '), p.thai_surname) like '%"+ StringEscapeUtils.escapeSql(user.getFirstLastName().trim()) + "%'");
		}
		if (StringUtils.hasText(user.getFacultyCode())) {	
			sqltotalsb.append(" and p.faculty_desc like '%" + StringEscapeUtils.escapeSql(facultyDesc)+ "%'");
		} 
		logger.info("sqltotalsb:"+sqltotalsb.toString());
		
		int rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class);
		pagingBean.setTotalItems(rows_totalItem);

		StringBuffer sb = new StringBuffer();
		sb.append("   select  u.username,CONCAT(CONCAT(p.thai_name,' '), p.thai_surname) AS firstLastName,u.password  ,u.enable ,p.employee_type");
		sb.append("   ,p.faculty_desc,p.department_desc,p.academic_year ,p.reg_id ");
		sb.append("  from buckwauser u   ");
		sb.append("  left join person_pbp p on (u.username=p.email) ");		
		 	
		sb.append(" where 1=1 ");
		sb.append(" and p.academic_year ='" + user.getAcademicYear()+"'");
		if (StringUtils.hasText(user.getUsername())) {	
			sb.append(" and u.username like '%" + StringEscapeUtils.escapeSql(user.getUsername().trim())+ "%'");
		}
		
		if (StringUtils.hasText(user.getFirstLastName())) {
			sb.append(" and CONCAT(CONCAT(p.thai_name,' '), p.thai_surname) like '%"+ StringEscapeUtils.escapeSql(user.getFirstLastName().trim()) + "%'");
		}	
		if (StringUtils.hasText(user.getFacultyCode())) {	
			sb.append(" and p.faculty_desc like '%" + StringEscapeUtils.escapeSql(facultyDesc)+ "%'");
		} 
		
		sb.append(" LIMIT " + pagingBean.getLimitItemFrom() + ","	+ pagingBean.getMaxPageItems());
 
		logger.info("sql:"+sb.toString());
		
		returnList = this.jdbcTemplate.query(sb.toString(),
				new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						User domain = new User();
						domain.setUsername(rs.getString("username"));
						domain.setPassword(rs.getString("password"));
						domain.setEnabled(rs.getBoolean("enable"));
						domain.setFirstLastName(rs.getString("firstLastName"));
						domain.setEmployeeType(rs.getString("employee_type"));
						domain.setFacultyDesc(rs.getString("faculty_desc"));
						domain.setDepartmentDesc(rs.getString("department_desc"));
						domain.setAcademicYear(rs.getString("academic_year"));
						domain.setRegId(rs.getString("reg_id"));
						return domain;
					}
				});

		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	@Override
	public PagingBean getUserDepartmentByOffset(PagingBean pagingBean) {
		logger.info("start");
		User user = (User) pagingBean.get("user");
		String  departmentIdStr = pagingBean.get("departmentId")+"";
		
		Department deptmp =facultyDao.getDepartmentById(departmentIdStr);
		
		List<User> returnList = new ArrayList<User>();
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("  select count(*) as total_item  from  buckwauser u  ");
		//sqltotalsb.append("   select count(*) as total_item  from  buckwauser u ");
		sqltotalsb.append("  left join person_pbp p on (u.username=p.email) ");		
		sqltotalsb.append(" where 1=1 ");	
		//sqltotalsb.append(" and p.department_desc ='" + deptmp.getName()+"'");
		sqltotalsb.append(" and p.academic_year ='" + user.getAcademicYear()+"'");
		if (StringUtils.hasText(user.getUsername())) {
			sqltotalsb.append(" and u.username like '%"+ StringEscapeUtils.escapeSql(user.getUsername().trim()) + "%'");
		}
		if (StringUtils.hasText(user.getFirstLastName())) {
			sqltotalsb.append(" and CONCAT(CONCAT(p.thai_name,' '), p.thai_surname) like '%"+ StringEscapeUtils.escapeSql(user.getFirstLastName().trim()) + "%'");
		}		
		int rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class);
		pagingBean.setTotalItems(rows_totalItem);
		
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("   select  u.username,CONCAT(CONCAT(p.thai_name,' '), p.thai_surname) AS firstLastName,u.password  ,u.enable ,p.employee_type");
		sb.append("   ,p.faculty_desc,p.department_desc,p.academic_year  ");
		sb.append("  from buckwauser u   ");
		sb.append("  left join person_pbp p on (u.username=p.email) ");		
		 	
		sb.append(" where 1=1 ");
		//sb.append(" and p.department_desc ='" + deptmp.getName()+"'");
		sb.append(" and p.academic_year ='" + user.getAcademicYear()+"'");
		if (StringUtils.hasText(user.getUsername())) {	
			sb.append(" and u.username like '%" + StringEscapeUtils.escapeSql(user.getUsername().trim())+ "%'");
		}
		
		if (StringUtils.hasText(user.getFirstLastName())) {
			sb.append(" and CONCAT(CONCAT(p.thai_name,' '), p.thai_surname) like '%"+ StringEscapeUtils.escapeSql(user.getFirstLastName().trim()) + "%'");
		}	
 
		
		//sb.append(" LIMIT " + pagingBean.getLimitItemFrom() + ","	+ pagingBean.getMaxPageItems());
 
		logger.info("sql:"+sb.toString());
		
		returnList = this.jdbcTemplate.query(sb.toString(),
				new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						User domain = new User();
						domain.setUsername(rs.getString("username"));
						domain.setPassword(rs.getString("password"));
						domain.setEnabled(rs.getBoolean("enable"));
						domain.setFirstLastName(rs.getString("firstLastName"));
						domain.setEmployeeType(rs.getString("employee_type"));
						domain.setFacultyDesc(rs.getString("faculty_desc"));
						domain.setDepartmentDesc(rs.getString("department_desc"));
						domain.setAcademicYear(rs.getString("academic_year"));
						return domain;
					}
				});

		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}

	
	
	
	
	
	@Override
	public User getUserByUsername(String userName,String academicYear) {
		logger.info(" userName :"+userName);
		String sql = " select us.*,p.thai_name,p.thai_surname,p.reg_id  from buckwauser us inner join person_pbp p on p.email = us.username where username ='" + userName
				+ "' and academic_year='"+academicYear+"'";
		RowMapper<User> mapper = new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User domain = new User();
				domain.setUsername(rs.getString("username"));
				domain.setPassword(rs.getString("password"));
				domain.setEnabled(rs.getBoolean("enable"));
				domain.setFirst_name(rs.getString("thai_name").trim());
				domain.setLast_name(rs.getString("thai_surname"));
				domain.setAddress1(rs.getString("address1"));
				domain.setAddress2(rs.getString("address2"));
				domain.setTel_no(rs.getString("tel_no"));
				domain.setEmail(rs.getString("email"));
				domain.setUser_id(rs.getInt("user_id"));
				domain.setSignatureImagePath(rs	.getString("signature_image_path"));
				 domain.setRegId(rs.getString("reg_id"));
				//domain.setLeaveAccumulate(rs.getInt("leave_accumulate"));
				return domain;
			}
		};

		User returnUser = (User) jdbcTemplate.queryForObject(sql, mapper);
		if (returnUser != null) {
			String sqlGroup = " select bsg.group_id from buckwausergroup bsg  where bsg.username ='"
					+ StringEscapeUtils.escapeSql(userName) + "'";
			logger.info(" sqlGroup:" + sqlGroup);
			List<String> groupStrList = this.jdbcTemplate.query(sqlGroup,
					new RowMapper<String>() {
						public String mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							String groupstr = rs.getString("group_id");
							return groupstr;
						}
					});
			returnUser.setGroups(groupStrList.toArray(new String[0]));
		}

		return returnUser;
	}
	
	@Override
	public User getUserAndAssignDateByUsername(String userName){
		logger.info("AdminUserDaoImpl.getUserAndAssignDateByUsername(String userName)");
		String sql = " select a.*,b.assign_date from buckwauser a inner join person b on b.email = a.username where a.username ='" + userName+ "'";
		RowMapper<User> mapper = new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User domain = new User();
				domain.setUsername(rs.getString("username"));
				domain.setPassword(rs.getString("password"));
				domain.setEnabled(rs.getBoolean("enable"));
				domain.setFirst_name(rs.getString("first_name"));
				domain.setLast_name(rs.getString("last_name"));
				domain.setAddress1(rs.getString("address1"));
				domain.setAddress2(rs.getString("address2"));
				domain.setTel_no(rs.getString("tel_no"));
				domain.setEmail(rs.getString("email"));
				domain.setUser_id(rs.getInt("user_id"));
				domain.setSignatureImagePath(rs.getString("signature_image_path"));
				domain.setAssignDate(rs.getDate("assign_date"));
				return domain;
			}
		};
		
		try {
			User returnUser = (User) jdbcTemplate.queryForObject(sql, mapper);
			return returnUser;
		} catch (EmptyResultDataAccessException e) {

		}
		return null;
	}

	@Override
	public User getUserByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePassword(User user) {
		logger.info("AdminUserDaoImpl.changePassword(User user)");

		this.jdbcTemplate.update(
				"update  buckwauser set password =? where username=? ",
				BuckWaUtils.encrypt(user.getNewPassword()), user.getUsername());

	}

	@Override
	public void delete(String username) {
		logger.info("AdminUserDaoImpl.delete(String username)");
		this.jdbcTemplate.update(
				" delete from   buckwauser   where username=? ", username);
		this.jdbcTemplate.update(
				" delete from  buckwausergroup where username=? ", username);

	}

	@Override
	public boolean isUserExist(String name) {
		logger.info("AdminUserDaoImpl.isUserExist(String name)");
		boolean returnValue = false;
		try {
			String sqltmp = "select count(*) as totalfound  from buckwauser t  where t.username='"
					+ StringEscapeUtils.escapeSql(name) + "'";
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if (found != null && found.intValue() > 0) {
				returnValue = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public void resetPass(String username, String newPass) {
		logger.info("AdminUserDaoImpl.resetPass(String username, String newPass)");
		this.jdbcTemplate.update(
				"update  buckwauser set password =? where username=? ",
				BuckWaUtils.encrypt(newPass), username);

	}

	@Override
	public void changeStatus(String username, boolean newStatus) {
		logger.info("AdminUserDaoImpl.changeStatus(String username, boolean newStatus)");
		this.jdbcTemplate.update(
				"update  buckwauser set enable =? where username=? ",
				newStatus, username);
	}

	@Override
	public List<User> getAll() {
		logger.info("AdminUserDaoImpl.getAll()");
		StringBuffer sb = new StringBuffer();
		List<User> returnList = new ArrayList<User>();
		sb.append("select a.*,b.assign_date,b.leave_accumulate,b.flag_accumulate from buckwauser a left outer join person b on a.username = b.email");
		try {
			returnList = this.jdbcTemplate.query(sb.toString(),
					new RowMapper<User>() {
						public User mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							User domain = new User();
							domain.setUser_id(rs.getInt("user_id"));
							domain.setUsername(rs.getString("username"));
							domain.setPassword(rs.getString("password"));
							domain.setEnabled(rs.getBoolean("enable"));
							domain.setAssignDate(rs.getDate("assign_date"));
							domain.setAccumulate(rs.getInt("leave_accumulate"));
							domain.setFlagAccumulate(rs.getInt("flag_accumulate"));
							return domain;
						}
					});
		} catch (EmptyResultDataAccessException e) {

		}
		return returnList;
	}

}
