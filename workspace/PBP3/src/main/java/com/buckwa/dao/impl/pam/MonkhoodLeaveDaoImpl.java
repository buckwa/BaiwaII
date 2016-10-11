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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.buckwa.dao.intf.pam.MonkhoodLeaveDao;
import com.buckwa.domain.pam.MonkhoodLeave;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("monkhoodLeaveDao")
public class MonkhoodLeaveDaoImpl implements MonkhoodLeaveDao {
	
	private static Logger logger = LoggerFactory.getLogger(MonkhoodLeaveDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	@Override
	public MonkhoodLeave create(MonkhoodLeave obj){
		logger.info(" #MonkhoodLeaveDaoImpl.create # ");
		final MonkhoodLeave finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		//Get Doc no 
		finalObj.setDocNo(generateDocNo());
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into leave_monkhood (" +
								"docno," +
								"amount_day," +
								"canMonkhoodDay," +
								"contactby," +
								"fromdate," +
								"todate," +
								"ever," +
								"location," +
								"at," +
								"defindDate," +
								"stay," +
								"leave_by_personId," +
								"location1," +
								"create_by," +
								"created_date" +
								") " +
								"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, finalObj.getDocNo());
				ps.setInt(2, finalObj.getAmountDay());
				ps.setInt(3, finalObj.getCanMonkhoodDay());
				ps.setString(4, finalObj.getContactBy());
				ps.setDate(5, BuckWaDateUtils.utilDateToSqlDate(finalObj.getFromDate()));
				ps.setDate(6,  BuckWaDateUtils.utilDateToSqlDate(finalObj.getToDate()));
				ps.setInt(7, finalObj.getEver());
				ps.setString(8, finalObj.getLocation());
				ps.setString(9, finalObj.getAt());
				ps.setDate(10,  BuckWaDateUtils.utilDateToSqlDate(finalObj.getDefindDate()));
				ps.setString(11, finalObj.getStay());
				ps.setLong(12, finalObj.getLeaveByPersonId());
				ps.setString(13, finalObj.getLocation1());
				ps.setString(14, finalObj.getCreateBy());
				ps.setTimestamp(15, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		}, keyHolder);
		
		Long returnid =  keyHolder.getKey().longValue();	
		finalObj.setLeaveMonkhoodId(returnid);
		
		return finalObj;
	}
	
	@Override
	public MonkhoodLeave getById(String id) {		 		
		StringBuffer sb = new StringBuffer();
		sb.append("  select * from leave_monkhood where status='A' and leave_monkhood_id = "+id);
		MonkhoodLeave obj = this.jdbcTemplate.queryForObject(sb.toString(),new LeaveMonkhoodMapper() );				
		return obj;
	}	
	
	@Override
	public MonkhoodLeave getByDocNo(String docNo){
		StringBuffer sb = new StringBuffer();
		sb.append("  select * from leave_monkhood where status='A' and docno = '"+StringEscapeUtils.escapeSql(docNo)+"'");
		MonkhoodLeave obj = this.jdbcTemplate.queryForObject(sb.toString(),new LeaveMonkhoodMapper() );				
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
	
	private class LeaveMonkhoodMapper implements RowMapper<MonkhoodLeave> {
		@Override
		public MonkhoodLeave mapRow(ResultSet rs, int rowNum) throws SQLException {
			MonkhoodLeave domain = new MonkhoodLeave();
			domain.setLeaveMonkhoodId(rs.getLong("leave_monkhood_id"));
			domain.setDocNo(rs.getString("docno"));
			domain.setAmountDay(rs.getInt("amount_day"));
			domain.setContactBy(rs.getString("contactby"));
			domain.setLocation1(rs.getString("location1"));
			domain.setFromDate(BuckWaDateUtils.sqlDateToutilDate(rs.getDate("fromdate")));
			domain.setToDate(BuckWaDateUtils.sqlDateToutilDate(rs.getDate("todate")));
			if(domain.getFromDate()!=null)
				domain.setFromDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(domain.getFromDate()));
			if(domain.getToDate()!=null)
				domain.setToDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(domain.getToDate()));
			domain.setLeaveByPersonId(rs.getLong("leave_by_personId"));
			domain.setEver(rs.getInt("ever"));
			if(domain.getEver()==0){
				domain.setEverStr(BuckWaConstants.L_NOT_EVER);
			}else{
				domain.setEverStr(BuckWaConstants.L_EVER);
			}
			domain.setLocation(rs.getString("location"));
			domain.setAt(rs.getString("at"));
			domain.setDefindDate(BuckWaDateUtils.sqlDateToutilDate(rs.getDate("defindDate")));
			if(domain.getDefindDate()!=null)
				domain.setDefindDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(domain.getDefindDate()));
			domain.setStay(rs.getString("stay"));
			return domain;
		}
	}
}

