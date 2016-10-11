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

import com.buckwa.dao.intf.pam.ResearchLeaveDao;
import com.buckwa.domain.pam.ResearchLeave;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.BuckWaUtils;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("researchLeaveDao")
public class ResearchLeaveDaoImpl implements ResearchLeaveDao {
	
	private static Logger logger = LoggerFactory.getLogger(ResearchLeaveDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public ResearchLeave create(ResearchLeave obj){
		logger.info(" #LeaveResearchDaoImpl.create # ");
		final ResearchLeave finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		//Get Doc no 
		finalObj.setDocNo(generateDocNo());
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into leave_research (" +
								"docno," +
								"researchtype_code," +
								"study,"+
								"degree," +
								"education," +
								"country," +
								"bycapital," +
								"course," +
								"location," +
								"amount_year," +
								"amount_month," +
								"amount_day," +
								"contactby," +
								"contactno," +
								"fromdate," +
								"todate," +
								"leave_by_personId," +
								"create_by," +
								"created_date" +
								") " +
								"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, finalObj.getDocNo());
				ps.setString(2, finalObj.getResearchTypeCode());
				ps.setString(3, finalObj.getStudy());
				ps.setString(4, finalObj.getDegree());
				ps.setString(5, finalObj.getEducation());
				ps.setString(6, finalObj.getCountry());
				ps.setString(7, finalObj.getBycapital());
				ps.setString(8, finalObj.getCourse());
				ps.setString(9, finalObj.getLocation());
				ps.setInt(10, finalObj.getAmountYear());
				ps.setInt(11, finalObj.getAmountMonth());
				ps.setInt(12, finalObj.getAmountDay());
				ps.setString(13, finalObj.getContactBy());
				ps.setString(14, finalObj.getContactNo());
				ps.setDate(15, BuckWaDateUtils.utilDateToSqlDate(finalObj.getFromDate()));
				ps.setDate(16,  BuckWaDateUtils.utilDateToSqlDate(finalObj.getToDate()));
				ps.setLong(17, finalObj.getLeaveByPersonId());
				ps.setString(18, finalObj.getCreateBy());
				ps.setTimestamp(19, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		}, keyHolder);
		
		Long returnid =  keyHolder.getKey().longValue();	
		finalObj.setLeaveResearchId(returnid);
		
		return finalObj;
	}
	
	@Override
	public ResearchLeave getById(String id) {		 		
		StringBuffer sb = new StringBuffer();
		sb.append("  select * from leave_research where status='A' and leaveresearch_id = "+id);
		ResearchLeave obj = this.jdbcTemplate.queryForObject(sb.toString(),new LeaveResearchMapper() );				
		return obj;
	}	
	
	@Override
	public ResearchLeave getByDocNo(String docNo){
		StringBuffer sb = new StringBuffer();
		sb.append("  select * from leave_research where status='A' and docno = '"+StringEscapeUtils.escapeSql(docNo)+"'");
		ResearchLeave obj = this.jdbcTemplate.queryForObject(sb.toString(),new LeaveResearchMapper() );				
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
	
	private class LeaveResearchMapper implements RowMapper<ResearchLeave> {
		@Override
		public ResearchLeave mapRow(ResultSet rs, int rowNum) throws SQLException {
			ResearchLeave domain = new ResearchLeave();
			domain.setLeaveResearchId(rs.getLong("leaveresearch_id"));
			domain.setDocNo(rs.getString("docno"));
			domain.setResearchTypeCode(rs.getString("researchtype_code"));
			domain.setStudy(rs.getString("study"));
			domain.setDegree(rs.getString("degree"));
			domain.setEducation(rs.getString("education"));
			domain.setCountry(rs.getString("country"));
			domain.setBycapital(rs.getString("bycapital"));
			domain.setCourse(rs.getString("course"));
			domain.setLocation(rs.getString("location"));
			domain.setAmountYear(rs.getInt("amount_year"));
			domain.setAmountMonth(rs.getInt("amount_month"));
			domain.setAmountDay(rs.getInt("amount_day"));
			domain.setContactBy(rs.getString("contactby"));
			domain.setContactNo(rs.getString("contactno"));
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

