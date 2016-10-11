package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.pam.EstimateGroupDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.EstimateGroup;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:38:05 PM
 */

@Repository("estimateGroupDao")
public class EstimateGroupDaoImpl implements EstimateGroupDao {
	
	private static Logger logger = LoggerFactory.getLogger(EstimateGroupDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public EstimateGroup create(EstimateGroup obj) throws Exception{
		logger.info(" #EsimateGroupDaoImpl.create # ");
		final EstimateGroup finalObj = obj;
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ "  insert into estimate_group (" +
								"name," +
								"faculty," +
								"create_by," +
								"created_date" +
								") " +
								"values (?,?,?,?)"
								+ "", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, finalObj.getName().trim());
				ps.setString(2, finalObj.getFaculty());
				ps.setString(3, finalObj.getCreateBy());
				ps.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));
				
				return ps;
			}
		}, keyHolder);
		
		finalObj.setEstimateGroupId(keyHolder.getKey().longValue());
		
		return finalObj;
	}
	
	@Override
	public EstimateGroup update(EstimateGroup obj) throws Exception{
		logger.info(" #EsimateGroupDaoImpl.update # ");
		final EstimateGroup finalObj = obj;
				
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(
								""
								+ " update estimate_group set " +
								"name=?," +
								"faculty=?," +
								"update_by=?," +
								"updated_date=?" +
								"where estimate_group_id=? ");
				ps.setString(1, finalObj.getName().trim());
				ps.setString(2, finalObj.getFaculty().trim());
				ps.setString(3, finalObj.getUpdateBy());
				ps.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));
				ps.setLong(5, finalObj.getEstimateGroupId());
				return ps;
			}
		});
		
		return finalObj;
	}
	
	@Override
	public void delete(Long id) throws Exception{	
		this.jdbcTemplate.update(" delete from estimate_group where estimate_group_id = "+id);
	}	
	
	@Override
	public EstimateGroup getById(Long id) {		 		
		StringBuffer sb = new StringBuffer();
		sb.append("  select p.* from estimate_group p where p.status='A' and p.estimate_group_id = "+id);
		EstimateGroup obj = null;
		try {
			obj = this.jdbcTemplate.queryForObject(sb.toString(),new EstimateGroupMapper() );			
		} catch (EmptyResultDataAccessException e) {

		}
		return obj;
	}	
	
	@Override
	public boolean checkNameAlready(Long id,String name){
		StringBuffer sb = new StringBuffer();
		sb.append("  select count(estimate_group_id) from estimate_group where status='A' and name='"+name.trim()+"' and estimate_group_id != "+id);
		EstimateGroup obj = null;
		try {
			int count = this.jdbcTemplate.queryForObject(sb.toString(),Integer.class);	
			if(count==0)
				return false;
		} catch (EmptyResultDataAccessException e) {

		}
		return true;
	}
	
	private class EstimateGroupMapper implements RowMapper<EstimateGroup> {
		@Override
		public EstimateGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
			EstimateGroup domain = new EstimateGroup();
			domain.setEstimateGroupId(rs.getLong("estimate_group_id"));
			domain.setName(rs.getString("name"));
			domain.setFaculty(rs.getString("faculty"));
			domain.setCreateBy(rs.getString("create_by"));
			domain.setStatus(rs.getString("status"));
			domain.setCreateDate(rs.getTimestamp("created_date"));
			domain.setUpdateDate(rs.getTimestamp("updated_date"));
			return domain;
		}
	}
	
	public PagingBean getAllByOffset(PagingBean pagingBean) {	 
		EstimateGroup estimateGroup = (EstimateGroup)pagingBean.get("estimateGroup");		
		List<EstimateGroup> returnList = new ArrayList<EstimateGroup>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  estimate_group  r  \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(estimateGroup.getName())){
			sqltotalsb.append(" and r.name like  '%"+StringEscapeUtils.escapeSql(estimateGroup.getName().trim())+"%'");
		}
		
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" * from estimate_group r  \n");
		sb.append(" where 1=1 ");	
		if(StringUtils.hasText(estimateGroup.getName())){
			sb.append(" and r.name like  '%"+StringEscapeUtils.escapeSql(estimateGroup.getName().trim())+"%'");
		}
		
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<EstimateGroup>() {
				public EstimateGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
					EstimateGroup domain = new EstimateGroup();
					domain.setEstimateGroupId(rs.getLong("estimate_group_id"));
					domain.setName(rs.getString("name"));
					domain.setCreateBy(rs.getString("create_by"));
					domain.setStatus(rs.getString("status"));
					domain.setCreateDate(rs.getTimestamp("created_date"));
					domain.setUpdateDate(rs.getTimestamp("updated_date")); 
					return domain;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	
}

