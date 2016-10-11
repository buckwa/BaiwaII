package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pam.LeaveCommentDao;
import com.buckwa.domain.pam.LeaveComment;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("leaveCommentDao")
public class LeaveCommentDaoImpl implements LeaveCommentDao {
	
	private static Logger logger = LoggerFactory.getLogger(LeaveCommentDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public LeaveComment create(LeaveComment obj){
		logger.info(" #LeaveCommentDaoImpl.create # ");
		final LeaveComment finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();		
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into leave_comment (" +
								"docno," +
								"leavetype_code," +
								"comment," +
								"create_by," +
								"created_date" +
								") " +
								"values (?,?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, finalObj.getDocno());
				ps.setString(2, finalObj.getLeaveTypeCode());
				ps.setString(3, finalObj.getComment());
				ps.setString(4, finalObj.getCreateBy());
				ps.setTimestamp(5, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		},keyHolder);
		finalObj.setLeaveCommentId(keyHolder.getKey().longValue());
		return finalObj;
	}
	
	
	@Override
	public List<LeaveComment> getComments(String docno,String leaveTypeCode) {		 		
		StringBuffer sb = new StringBuffer();
		List<LeaveComment> returnList = new ArrayList<LeaveComment>(); 
		sb.append("  select * from leave_comment where status='A' and docno = "+docno+" and leavetype_code = '"+leaveTypeCode+"' order by created_date desc");
		returnList = this.jdbcTemplate.query(sb.toString(),new LeaveCommentMapper());
		return returnList;
	}	
	
	
	private class LeaveCommentMapper implements RowMapper<LeaveComment> {
		@Override
		public LeaveComment mapRow(ResultSet rs, int rowNum) throws SQLException {
			LeaveComment domain = new LeaveComment();
			domain.setLeaveCommentId(rs.getLong("leave_comment_id"));
			domain.setDocno(rs.getString("docno"));
			domain.setLeaveTypeCode(rs.getString("leavetype_code"));
			domain.setComment(rs.getString("comment"));
			domain.setCreateBy(rs.getString("create_by"));
			domain.setCreateDate(rs.getTimestamp("created_date"));
			return domain;
		}
	}
}

