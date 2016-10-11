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

import com.buckwa.dao.intf.pam.VacationLeaveDao;
import com.buckwa.domain.pam.VacationLeave;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("vacationLeaveDao")
public class VacationLeaveDaoImpl implements VacationLeaveDao {
	
	private static Logger logger = LoggerFactory.getLogger(VacationLeaveDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	@Override
	public VacationLeave create(VacationLeave obj){
		logger.info(" #VacationLeaveDaoImpl.create # ");
		final VacationLeave finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		//Get Doc no 
		finalObj.setDocNo(generateDocNo());
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into leave_vacation (" +
								"docno," +
								"amount_day," +
								"canVacationDay," +
								"contactby," +
								"fromdate," +
								"todate," +
								"leave_by_personId," +
								"create_by," +
								"created_date," +
								"foreignType," +
								"foreignAt" +
								") " +
								"values (?,?,?,?,?,?,?,?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, finalObj.getDocNo());
				ps.setInt(2, finalObj.getAmountDay());
				ps.setInt(3, finalObj.getCanVacationDay());
				ps.setString(4, finalObj.getContactBy());
				ps.setDate(5, BuckWaDateUtils.utilDateToSqlDate(finalObj.getFromDate()));
				ps.setDate(6,  BuckWaDateUtils.utilDateToSqlDate(finalObj.getToDate()));
				ps.setLong(7, finalObj.getLeaveByPersonId());
				ps.setString(8, finalObj.getCreateBy());
				ps.setTimestamp(9, new java.sql.Timestamp(new Date().getTime()));
				ps.setInt(10, finalObj.getForeign());
				ps.setString(11, finalObj.getForeignAt());
				return ps;
			}
		}, keyHolder);
		
		Long returnid =  keyHolder.getKey().longValue();	
		finalObj.setLeaveVacationId(returnid);
		
		return finalObj;
	}
	
	@Override
	public VacationLeave getById(String id) {		 		
		StringBuffer sb = new StringBuffer();
		sb.append("  select * from leave_vacation where status='A' and leave_vacation_id = "+id);
		VacationLeave obj = this.jdbcTemplate.queryForObject(sb.toString(),new LeaveVacationMapper() );				
		return obj;
	}	
	
	@Override
	public VacationLeave getByDocNo(String docNo){
		StringBuffer sb = new StringBuffer();
		sb.append("  select * from leave_vacation where status='A' and docno = '"+StringEscapeUtils.escapeSql(docNo)+"'");
		VacationLeave obj = this.jdbcTemplate.queryForObject(sb.toString(),new LeaveVacationMapper() );				
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
	
	private class LeaveVacationMapper implements RowMapper<VacationLeave> {
		@Override
		public VacationLeave mapRow(ResultSet rs, int rowNum) throws SQLException {
			VacationLeave domain = new VacationLeave();
			domain.setLeaveVacationId(rs.getLong("leave_vacation_id"));
			domain.setDocNo(rs.getString("docno"));
			domain.setAmountDay(rs.getInt("amount_day"));
			domain.setContactBy(rs.getString("contactby"));
			domain.setFromDate(BuckWaDateUtils.sqlDateToutilDate(rs.getDate("fromdate")));
			domain.setToDate(BuckWaDateUtils.sqlDateToutilDate(rs.getDate("todate")));
			if(domain.getFromDate()!=null)
				domain.setFromDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(domain.getFromDate()));
			if(domain.getToDate()!=null)
				domain.setToDateStr(BuckWaDateUtils.get_current_ddMMMMyyyy_thai_from_date(domain.getToDate()));
			domain.setLeaveByPersonId(rs.getLong("leave_by_personId"));
			domain.setForeign(rs.getInt("foreignType"));
			domain.setForeignAt(rs.getString("foreignAt"));
			return domain;
		}
	}
}

