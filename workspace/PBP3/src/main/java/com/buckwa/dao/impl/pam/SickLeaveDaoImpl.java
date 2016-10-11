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

import com.buckwa.dao.intf.pam.SickLeaveDao;
import com.buckwa.domain.pam.SickLeave;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("sickLeaveDao")
public class SickLeaveDaoImpl implements SickLeaveDao {
	
	private static Logger logger = LoggerFactory.getLogger(SickLeaveDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	
	@Override
	public SickLeave create(SickLeave obj){
		logger.info(" #SickLeaveDaoImpl.create # ");
		final SickLeave finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		//Get Doc no 
		finalObj.setDocNo(generateDocNo());
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into leave_sick (" +
								"docno," +
								"amount_day," +
								"canSickDay," +
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
				ps.setInt(3, finalObj.getCanSickDay());
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
		finalObj.setLeaveSickId(returnid);
		
		return finalObj;
	}
	
	@Override
	public SickLeave getById(String id) {		 		
		StringBuffer sb = new StringBuffer();
		sb.append("  select * from leave_sick where status='A' and leave_sick_id = "+id);
		SickLeave obj = this.jdbcTemplate.queryForObject(sb.toString(),new LeaveSickMapper() );				
		return obj;
	}	
	
	@Override
	public SickLeave getByDocNo(String docNo){
		StringBuffer sb = new StringBuffer();
		sb.append("  select * from leave_sick where status='A' and docno = '"+StringEscapeUtils.escapeSql(docNo)+"'");
		SickLeave obj = this.jdbcTemplate.queryForObject(sb.toString(),new LeaveSickMapper() );				
		return obj;
	}
	
	@Override
	public SickLeave getLastDate(){
		StringBuffer sb = new StringBuffer();
		SickLeave obj = null;
		sb.append("  select b.* from leave_flow a inner join leave_sick b on a.docno=b.docno where a.flow_status='LP002' and a.isCancel=0 and b.status='A' order by b.created_date desc");
		try {
			 obj = this.jdbcTemplate.queryForObject(sb.toString(),new LeaveSickMapper() );		
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
	
	private class LeaveSickMapper implements RowMapper<SickLeave> {
		@Override
		public SickLeave mapRow(ResultSet rs, int rowNum) throws SQLException {
			SickLeave domain = new SickLeave();
			domain.setLeaveSickId(rs.getLong("leave_sick_id"));
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

