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

import com.buckwa.dao.intf.pam.WorkTemplateAttrDao;
import com.buckwa.domain.pam.WorkTemplateAttr;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("workTemplateAttrDao")
public class WorkTemplateAttrDaoImpl implements WorkTemplateAttrDao {
	
	private static Logger logger = LoggerFactory.getLogger(WorkTemplateAttrDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public WorkTemplateAttr create(WorkTemplateAttr obj){
		logger.info(" #WorkTemplateAttrDaoImpl.create # ");
		final WorkTemplateAttr finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into work_template_attr (" +
								"work_template_id," +
								"label," +
								"type," +
								"unit_id," +
								"isFile," +
								"flag_cal," +
								"created_by," +
								"created_date" +
								") " +
								"values (?,?,?,?,?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, finalObj.getWorkTemplateId());
				ps.setString(2, finalObj.getLabel());
				ps.setString(3, finalObj.getType());
				ps.setLong(4, finalObj.getUnitId());
				ps.setInt(5, finalObj.getIsFile());
				ps.setInt(6, finalObj.getFlagCalculate());
				ps.setString(7, finalObj.getCreateBy());
				ps.setTimestamp(8, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		}, keyHolder);
		
		finalObj.setWorkTemplateAttrId(keyHolder.getKey().longValue());
		
		return finalObj;
	}
	
	
	@Override
	public void deleteByWorkTemplateId(String work_template_id) {	
		this.jdbcTemplate.update(" delete from work_template_attr where work_template_id = "+work_template_id);
	}	
	
	@Override
	public List<WorkTemplateAttr> getByWorkTemplateId(String id) {		 		
		StringBuffer sb = new StringBuffer();
		List<WorkTemplateAttr> returnList = new ArrayList<WorkTemplateAttr>();
		sb.append("  select a.*,b.name as unitName,c.kpi_id from work_template_attr a left outer join unit b on a.unit_id=b.unit_id left outer join work_template_kpi c on c.work_template_attr_id = a.work_template_attr_id where a.status='A' and a.work_template_id = "+id);
		try {
			returnList = this.jdbcTemplate.query(sb.toString(),new WorkTemplateAttrMapper() );	
		} catch (EmptyResultDataAccessException e) {

		}
		return returnList;
	}	
	
	private class WorkTemplateAttrMapper implements RowMapper<WorkTemplateAttr> {
		@Override
		public WorkTemplateAttr mapRow(ResultSet rs, int rowNum) throws SQLException {
			WorkTemplateAttr domain = new WorkTemplateAttr();
			domain.setWorkTemplateAttrId(rs.getLong("work_template_attr_id"));
			domain.setWorkTemplateId(rs.getLong("work_template_id"));
			domain.setFlagCalculate(rs.getInt("flag_cal"));
			domain.setLabel(rs.getString("label"));
			domain.setType(rs.getString("type"));
			domain.setUnitId(rs.getLong("unit_id"));
			domain.setUnitName(rs.getString("unitName"));
			domain.setKpiId(rs.getLong("kpi_id"));
			domain.setIsFile(rs.getInt("isFile"));
			return domain;
		}
	}
	
	
	private static final String SQL_GET_ISFILE_LIST =
		" SELECT * " +
		" FROM work_template_attr " +
		" WHERE status = 'A' " +
		"   AND work_template_id = ? " +
		"   AND isFile = 1 ";
	
	@Override
	public List<WorkTemplateAttr> getIsFileListByWorkTemplateId(String id) {
		List<WorkTemplateAttr> returnList = this.jdbcTemplate.query(SQL_GET_ISFILE_LIST, new WorkTemplateAttrMapper2(),	new Object[] {
			id
		});	
		return returnList;
	}
	
	private class WorkTemplateAttrMapper2 implements RowMapper<WorkTemplateAttr> {
		@Override
		public WorkTemplateAttr mapRow(ResultSet rs, int rowNum) throws SQLException {
			WorkTemplateAttr domain = new WorkTemplateAttr();
			domain.setWorkTemplateAttrId(rs.getLong("work_template_attr_id"));
			domain.setWorkTemplateId(rs.getLong("work_template_id"));
			domain.setLabel(rs.getString("label"));
			domain.setIsFile(rs.getInt("isFile"));
			return domain;
		}
	}
}

