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

import com.buckwa.dao.intf.pam.WorkPersonAttrDao;
import com.buckwa.domain.pam.WorkPersonAttr;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("workPersonAttrDao")
public class WorkPersonAttrDaoImpl implements WorkPersonAttrDao {
	
	private static Logger logger = LoggerFactory.getLogger(WorkPersonAttrDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public WorkPersonAttr create(WorkPersonAttr obj){
		logger.info(" #WorkPersonAttrDaoImpl.create # ");
		final WorkPersonAttr finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into work_person_attr (" +
								"work_person_id," +
								"work_template_id," +
								"label," +
								"value," +
								"unit_id," +
								"kpi_id," +
								"unit_name," +
								"isFile,"+
								"flag_cal,"+
								"created_by," +
								"created_date" +
								") " +
								"values (?,?,?,?,?,?,?,?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, finalObj.getWorkPersonId());
				ps.setLong(2, finalObj.getWorkTemplateId());
				ps.setString(3, finalObj.getLabel());
				ps.setString(4, finalObj.getValue());
				ps.setLong(5, finalObj.getUnitId());
				ps.setLong(6, finalObj.getKpiId());
				ps.setString(7, finalObj.getUnitName());
				ps.setInt(8, finalObj.getIsFile());
				ps.setInt(9, finalObj.getFlagCalculate());
				ps.setString(10, finalObj.getCreateBy());
				ps.setTimestamp(11, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		}, keyHolder);
		
		finalObj.setWorkPersonAttrId(keyHolder.getKey().longValue());
		
		return finalObj;
	}
	
	
	@Override
	public void deleteByWorkPersonId(String workPersonId) {	
		this.jdbcTemplate.update(" delete from work_person_attr where work_person_id = "+workPersonId);
	}	
	
	@Override
	public List<WorkPersonAttr> getByWorkPersonId(String id) {		 		
		StringBuffer sb = new StringBuffer();
		List<WorkPersonAttr> returnList = new ArrayList<WorkPersonAttr>();
		sb.append("  select a.*,b.name as unitName from work_person_attr a left outer join unit b on a.unit_id=b.unit_id where a.status='A' and a.work_person_id = "+id);
		try {
			returnList = this.jdbcTemplate.query(sb.toString(),new WorkPersonAttrMapper() );	
		} catch (EmptyResultDataAccessException e) {

		}
		return returnList;
	}	
	
	private class WorkPersonAttrMapper implements RowMapper<WorkPersonAttr> {
		@Override
		public WorkPersonAttr mapRow(ResultSet rs, int rowNum) throws SQLException {
			WorkPersonAttr domain = new WorkPersonAttr();
			domain.setWorkPersonAttrId(rs.getLong("work_person_attr_id"));
			domain.setWorkPersonId(rs.getLong("work_person_id"));
			domain.setWorkTemplateId(rs.getLong("work_template_id"));
			domain.setFlagCalculate(rs.getInt("flag_cal"));
			domain.setLabel(rs.getString("label"));
			domain.setValue(rs.getString("value"));
			domain.setUnitId(rs.getLong("unit_id"));
			domain.setUnitName(rs.getString("unitName"));
			domain.setKpiId(rs.getLong("kpi_id"));
			domain.setIsFile(rs.getInt("isFile"));
			return domain;
		}
	}
}

