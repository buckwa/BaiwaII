package com.buckwa.dao.impl.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.admin.AccessLogDao;
import com.buckwa.dao.intf.pbp.FacultyDao;
import com.buckwa.domain.admin.AccessLog;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.PagingBean;

@Repository("accessLogDao")
public class AccessLogDaoImpl implements AccessLogDao {

	private static Logger logger = LoggerFactory.getLogger(AccessLogDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private FacultyDao facultyDao;

 
	@Override
	public PagingBean getByOffset(PagingBean pagingBean) {
		logger.info("start");
		AccessLog accessLog = (AccessLog) pagingBean.get("accessLog");
		List<AccessLog> returnList = new ArrayList<AccessLog>();
		StringBuffer sqltotalsb = new StringBuffer();
		 
		sqltotalsb.append("   select count(*) as total_item  from  access_log u "); 
		sqltotalsb.append(" where 1=1 ");		
	 
		if (StringUtils.hasText(accessLog.getUsername())) {
			sqltotalsb.append(" and u.user_login like '%"+ StringEscapeUtils.escapeSql(accessLog.getUsername().trim()) + "%'");
		}
		if (StringUtils.hasText(accessLog.getLoginStatus())) {
			sqltotalsb.append(" and u.login_status like '%"+ StringEscapeUtils.escapeSql(accessLog.getLoginStatus().trim()) + "%'");
		}
		
		
		int rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class);
		pagingBean.setTotalItems(rows_totalItem);

		StringBuffer sb = new StringBuffer();
		sb.append("   select *  from  access_log u "); 
		sb.append(" where 1=1 ");		
	 
		if (StringUtils.hasText(accessLog.getUsername())) {
			sb.append(" and u.user_login like '%"+ StringEscapeUtils.escapeSql(accessLog.getUsername().trim()) + "%'");
		}
		if (StringUtils.hasText(accessLog.getLoginStatus())) {
			sb.append(" and u.login_status like '%"+ StringEscapeUtils.escapeSql(accessLog.getLoginStatus().trim()) + "%'");
		}
 
		sb.append(" order by  create_date desc ");	
		
		sb.append(" LIMIT " + pagingBean.getLimitItemFrom() + ","	+ pagingBean.getMaxPageItems());
 
		logger.info("sql:"+sb.toString());
		
		returnList = this.jdbcTemplate.query(sb.toString(),
				new RowMapper<AccessLog>() {
					public AccessLog mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						AccessLog domain = new AccessLog();
						domain.setUsername(rs.getString("user_login"));
						domain.setLoginStatus(rs.getString("login_status"));
						domain.setLoginIP(rs.getString("client_ip"));
						domain.setCreateDate(rs.getTimestamp("create_date"));
						return domain;
					}
				});

		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	
	
  

}
