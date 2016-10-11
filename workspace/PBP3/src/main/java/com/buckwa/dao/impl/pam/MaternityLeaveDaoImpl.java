package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pam.MaternityLeaveDao;
import com.buckwa.domain.pam.MaternityLeave;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PMy
 */

@Repository("maternityLeaveDao")
public class MaternityLeaveDaoImpl implements MaternityLeaveDao {
	
	private static Logger logger = LoggerFactory.getLogger(MaternityLeaveDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	@Override
	public MaternityLeave create(MaternityLeave obj){
		logger.info(" #MaternityLeaveDaoImpl.create # ");
		final MaternityLeave finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		//Get Doc no 
		finalObj.setDocNo(generateDocNo());
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into leave_maternity (" +
								"docno," +
								"amount_day," +
								"canMaternityDay," +
								"contactby," +
								"fromdate," +
								"todate," +
								"leave_by_personId," +
								"reason," +
								"create_by," +
								"created_date" +
								") " +
								"values (?,?,?,?,?,?,?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, finalObj.getDocNo());
				ps.setInt(2, finalObj.getAmountDay());
				ps.setInt(3, finalObj.getCanMaternityDay());
				ps.setString(4, finalObj.getContactBy());
				ps.setDate(5, BuckWaDateUtils.utilDateToSqlDate(finalObj.getFromDate()));
				ps.setDate(6,  BuckWaDateUtils.utilDateToSqlDate(finalObj.getToDate()));
				ps.setLong(7, finalObj.getLeaveByPersonId());
				ps.setString(8, finalObj.getReason());
				ps.setString(9, finalObj.getCreateBy());
				ps.setTimestamp(10, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		}, keyHolder);
		
		Long returnid =  keyHolder.getKey().longValue();	
		finalObj.setLeaveMaternityId(returnid);
		
		return finalObj;
	}
	
	@Override
	public MaternityLeave getById(String id) {		 		
		StringBuffer sb = new StringBuffer();
		sb.append("  select * from leave_maternity where status='A' and leave_maternity_id = "+id);
		MaternityLeave obj = this.jdbcTemplate.queryForObject(sb.toString(),new LeaveMaternityMapper() );				
		return obj;
	}	
	
	@Override
	public MaternityLeave getByDocNo(String docNo){
		StringBuffer sb = new StringBuffer();
		sb.append("  select * from leave_maternity where status='A' and docno = '"+StringEscapeUtils.escapeSql(docNo)+"'");
		MaternityLeave obj = this.jdbcTemplate.queryForObject(sb.toString(),new LeaveMaternityMapper() );				
		return obj;
	}
	
	@Override
	public MaternityLeave getLastDate(){
		StringBuffer sb = new StringBuffer();
		MaternityLeave obj=null;
		sb.append("  select b.* from leave_flow a inner join leave_maternity b on a.docno=b.docno where a.flow_status='LP002' and a.isCancel=0 and b.status='A' order by b.created_date desc");
		try {
			obj = this.jdbcTemplate.queryForObject(sb.toString(),new LeaveMaternityMapper() );				
		} catch (EmptyResultDataAccessException e) {
		}	
		
		return obj;
	}
	
	@Override
	public String generateDocNo(){
		String returnValue = null;
		try {
			String sqltmp = "SELECT max(leaveflow_id) as MaxId FROM leave_flow; ";
			logger.info(" sql:" + sqltmp);
			Long maxId = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			returnValue = BuckWaUtils.generateDocNo((maxId+1));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}
	
	private class LeaveMaternityMapper implements RowMapper<MaternityLeave> {
		@Override
		public MaternityLeave mapRow(ResultSet rs, int rowNum) throws SQLException {
			MaternityLeave domain = new MaternityLeave();
			domain.setLeaveMaternityId(rs.getLong("leave_maternity_id"));
			domain.setDocNo(rs.getString("docno"));
			domain.setAmountDay(rs.getInt("amount_day"));
			domain.setContactBy(rs.getString("contactby"));
			domain.setReason(rs.getString("reason"));
			domain.setFromDate(BuckWaDateUtils.sqlDateToutilDate(rs.getDate("fromdate")));
			domain.setToDate(BuckWaDateUtils.sqlDateToutilDate(rs.getDate("todate")));
			if(domain.getFromDate()!=null)
				domain.setFromDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(domain.getFromDate()));
			if(domain.getToDate()!=null)
				domain.setToDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(domain.getToDate()));
			domain.setLeaveByPersonId(rs.getLong("leave_by_personId"));
			return domain;
		}
	}
}

