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

import com.buckwa.dao.intf.pam.WorkPersonKpiDao;
import com.buckwa.domain.pam.WorkPersonKpi;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("workPersonKpiDao")
public class WorkPersonKpiDaoImpl implements WorkPersonKpiDao {
	
	private static Logger logger = LoggerFactory.getLogger(WorkPersonKpiDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public WorkPersonKpi create(WorkPersonKpi obj){
		logger.info(" #WorkPersonKpiDaoImpl.create # ");
		final WorkPersonKpi finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into work_person_kpi (" +
								"work_person_id," +
								"kpi_id," +
								"work_person_attr_id,"+
								"flag_cal,"+
								"create_by," +
								"created_date" +
								") " +
								"values (?,?,?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, finalObj.getWorkPersonId());
				ps.setLong(2, finalObj.getKpiId());
				ps.setLong(3, finalObj.getWorkPersonAttrId());
				ps.setInt(4, finalObj.getFlagCalculate());
				ps.setString(5, finalObj.getCreateBy());
				ps.setTimestamp(6, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		}, keyHolder);
		
		finalObj.setWorkPersonKpiId(keyHolder.getKey().longValue());
		
		return finalObj;
	}
	
	
	@Override
	public void deleteByWorkPersonId(String work_person_id) {	
		this.jdbcTemplate.update(" delete from work_person_kpi where work_person_id = "+work_person_id);
	}	
	
	@Override
	public List<WorkPersonKpi> getByWorkPersonId(String id) {		 		
		StringBuffer sb = new StringBuffer();
		List<WorkPersonKpi> returnList = new ArrayList<WorkPersonKpi>();
		sb.append("  select * from work_person_kpi where status='A' and work_person_id = "+id);
		try {
			returnList = this.jdbcTemplate.query(sb.toString(),new WorkPersonKpiMapper() );	
		} catch (EmptyResultDataAccessException e) {

		}
		return returnList;
	}	
	
	private class WorkPersonKpiMapper implements RowMapper<WorkPersonKpi> {
		@Override
		public WorkPersonKpi mapRow(ResultSet rs, int rowNum) throws SQLException {
			WorkPersonKpi domain = new WorkPersonKpi();
			domain.setWorkPersonKpiId(rs.getLong("work_person_kpi_id"));
			domain.setWorkPersonId(rs.getLong("work_person_id"));
			domain.setKpiId(rs.getLong("kpi_id"));
			domain.setWorkPersonAttrId(rs.getLong("work_person_attr_id"));
			domain.setFlagCalculate(rs.getInt("flag_cal"));
			return domain;
		}
	}
}

