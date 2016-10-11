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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.buckwa.dao.intf.pam.WorkTemplateKpiDao;
import com.buckwa.domain.pam.WorkTemplateKpi;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("workTemplateKpiDao")
public class WorkTemplateKpiDaoImpl implements WorkTemplateKpiDao {
	
	private static Logger logger = LoggerFactory.getLogger(WorkTemplateKpiDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public WorkTemplateKpi create(WorkTemplateKpi obj){
		logger.info(" #WorkTemplateKpiDaoImpl.create # ");
		final WorkTemplateKpi finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into work_template_kpi (" +
								"work_template_id," +
								"work_template_attr_id," +
								"kpi_id," +
								"flag_cal," +
								"create_by," +
								"created_date" +
								") " +
								"values (?,?,?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, finalObj.getWorkTemplateId());
				ps.setLong(2, finalObj.getWorkTemplateAttrId());
				ps.setLong(3, finalObj.getKpiId()==null?new Long(0):finalObj.getKpiId());
				ps.setLong(4, finalObj.getFlagCalculate());
				ps.setString(5, finalObj.getCreateBy());
				ps.setTimestamp(6, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		}, keyHolder);
		
		finalObj.setWorkTemplateKpiId(keyHolder.getKey().longValue());
		
		return finalObj;
	}
	
	
	@Override
	public void deleteByWorkTemplateId(String work_template_id) {	
		this.jdbcTemplate.update(" delete from work_template_kpi where work_template_id = "+work_template_id);
	}	
	
	@Override
	public List<WorkTemplateKpi> getByWorkTemplateId(String id) {		 		
		StringBuffer sb = new StringBuffer();
		List<WorkTemplateKpi> returnList = new ArrayList<WorkTemplateKpi>();
		sb.append("  select * from work_template_kpi where status='A' and work_template_id = "+id);
		try {
			returnList = this.jdbcTemplate.query(sb.toString(),new WorkTemplateKpiMapper() );	
		} catch (EmptyResultDataAccessException e) {

		}
		return returnList;
	}
	
	@Override
	public List<WorkTemplateKpi> getByWorkTemplateAndAttrId(String id,String workTemplateAttrId) {		 		
		StringBuffer sb = new StringBuffer();
		List<WorkTemplateKpi> returnList = new ArrayList<WorkTemplateKpi>();
		sb.append("  select * from work_template_kpi where status='A' and work_template_id = "+id+" and work_template_attr_id =  "+workTemplateAttrId);
		try {
			returnList = this.jdbcTemplate.query(sb.toString(),new WorkTemplateKpiMapper() );	
		} catch (EmptyResultDataAccessException e) {

		}
		return returnList;
	}
	
	private class WorkTemplateKpiMapper implements RowMapper<WorkTemplateKpi> {
		@Override
		public WorkTemplateKpi mapRow(ResultSet rs, int rowNum) throws SQLException {
			WorkTemplateKpi domain = new WorkTemplateKpi();
			domain.setWorkTemplateKpiId(rs.getLong("work_template_kpi_id"));
			domain.setWorkTemplateId(rs.getLong("work_template_id"));
			domain.setKpiId(rs.getLong("kpi_id"));
			domain.setFlagCalculate(rs.getInt("flag_cal"));
			return domain;
		}
	}
}

