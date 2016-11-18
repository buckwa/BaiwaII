package baiwa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import baiwa.entity.UserAttempt;

@Repository("userAttemptDao")
@Transactional(readOnly = true)
public class UserAttemptDao {
	
	@Autowired JdbcTemplate jdbcTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public UserAttempt findByUsername (String username){
		username = username+"@kmitl.ac.th";
//		StringBuilder sql = new StringBuilder();
//		sql.append(" SELECT user_id , USERNAME, password ");
//		sql.append(" FROM buckwauser ");
//		sql.append(" WHERE username =  ? ");
		String sql = " SELECT  a.person_id , d.user_id  , d.username , d.password , b.code  AS department_id ,c.code , b.academic_year"
				+ " FROM person_pbp a INNER JOIN department b ON a.department_desc = b.name "
				+ " INNER JOIN faculty c ON  c.name = a.faculty_desc "
				+ " INNER JOIN buckwauser d ON d.username = a.email "
				+ " WHERE  a.email = ? "
				+ " ORDER BY b.academic_year DESC LIMIT 1 " ;
		UserAttempt  userAttempt = null;
		try {
		userAttempt = (UserAttempt)jdbcTemplate.queryForObject(sql,new Object[] { username 
				},new BeanPropertyRowMapper<UserAttempt>(UserAttempt.class)) ;
		
		}catch (EmptyResultDataAccessException e) 
		{
			logger.warn("Can not find data with username: " + username);
		}
		logger.info("Returning user=" + userAttempt);
		return userAttempt;
		
	}
	
	public List<GrantedAuthority> findGrantedRoleByUserId(String username) {
		String sql = "SELECT u.username , r.role_name FROM buckwauser u   "
				+ " LEFT OUTER JOIN buckwausergroup  ug ON u.username = ug.username "
				+ " LEFT OUTER JOIN buckwagroup  g ON ug.group_id = g.group_id  "
				+ "  LEFT OUTER JOIN buckwagrouprole  gr ON g.group_id = gr.group_id  "
				+ "  LEFT OUTER JOIN buckwarole r   ON gr.role_id = r.role_id  "
				+ "  WHERE u.username= ? " ;
		
		List<GrantedAuthority> grantedRoleList = jdbcTemplate.query(sql, new Object[] { username 
		}, new RowMapper<GrantedAuthority>() {

			@Override
			public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new SimpleGrantedAuthority(rs.getString("role_name"));
			}
		});
		
		return grantedRoleList;
	}

}
