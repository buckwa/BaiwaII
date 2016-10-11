package com.buckwa.dao.impl.pam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pam.LeaveTypeDao;
import com.buckwa.domain.pam.LeaveType;

@Repository("leaveTypeDao")
public class LeaveTypeDaoImpl implements LeaveTypeDao {

	private static Logger logger = LoggerFactory.getLogger(LeaveTypeDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<LeaveType> getAll() {
		List<LeaveType> returnList = new ArrayList<LeaveType>();
		String sql = "  select r.leavetype_id , r.code, r.name from leave_type r  ";
		logger.info(" sql:" + sql);
		returnList = this.jdbcTemplate.query(sql, new LeaveTypeMapper());
		return returnList;
	}

	private class LeaveTypeMapper implements RowMapper<LeaveType> {
		@Override
		public LeaveType mapRow(ResultSet rs, int rowNum) throws SQLException {
			LeaveType domain = new LeaveType();
			domain.setLeaveTypeId(rs.getLong("leavetype_id"));
			domain.setCode(rs.getString("code"));
			;
			domain.setName(rs.getString("name"));
			return domain;
		}
	}
}
