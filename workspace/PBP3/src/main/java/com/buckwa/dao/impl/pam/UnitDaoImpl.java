package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.pam.UnitDao;
import com.buckwa.dao.intf.pam.KpiTemplateDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Unit;
import com.buckwa.domain.pam.nodetree.Kpi;
import com.buckwa.util.BeanUtils;

@Repository("unitDao")
public class UnitDaoImpl implements UnitDao {
	private static Logger logger = LoggerFactory.getLogger(UnitDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private KpiTemplateDao kpiTreeDao;

	public List<Unit> getAll() {
		List<Unit> returnList = new ArrayList<Unit>(); 
		String sql ="  select r.unit_id , r.name, r.code from unit r  " ;	
		logger.info(" sql:"+sql);
		returnList = this.jdbcTemplate.query(sql,new UnitMapper());
		return returnList;
	}
	 
 

	@Override
	public void update(Unit unit) {
		logger.info("  unit: "+BeanUtils.getBeanString(unit));
		this.jdbcTemplate.update(
				"update  unit set name=?,code=?   where unit_id=? ",
				unit.getName(), unit.getCode(),unit.getUnitId()); 
 	 	
	}
 
	
	@Override
	public Long create(Unit unit) {
		logger.info("   ");		
		final Unit finalUnit = unit;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into unit (name, code) values (?, ?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalUnit.getName());
				ps.setString(2,finalUnit.getCode());				 			
				return ps;  
				}
			}, 	keyHolder); 	
		final Long returnid =  keyHolder.getKey().longValue();			
		// Create KPI Group Root 
 
		
		/*
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into kpi_tree_template (name, group_id) values (?, ?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalUnit.getName());
			 
				ps.setLong(2,returnid);	
				return ps;  
				}
			}, 	keyHolder); 

		*/
	   return returnid;
	}

	
	public PagingBean getByOffset(PagingBean pagingBean) {	 
		Unit unit = (Unit)pagingBean.get("unit");		
		List<Unit> returnList = new ArrayList<Unit>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from unit  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(unit.getName())){
			sqltotalsb.append(" and r.name like  '%"+StringEscapeUtils.escapeSql(unit.getName().trim())+"%'");
		}
		if(StringUtils.hasText(unit.getCode())){
			sqltotalsb.append(" and r.code like  '%"+StringEscapeUtils.escapeSql(unit.getCode().trim())+"%'");
		}
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" r.unit_id , r.name, r.code from unit r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(unit.getName())){
			sb.append(" and r.name like  '%"+StringEscapeUtils.escapeSql(unit.getName().trim())+"%'");
		}
		if(StringUtils.hasText(unit.getCode())){
			sb.append(" and r.code like  '%"+StringEscapeUtils.escapeSql(unit.getCode().trim())+"%'");
		}
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Unit>() {
				public Unit mapRow(ResultSet rs, int rowNum) throws SQLException {
					Unit unit = new Unit();				 
					unit.setUnitId(rs.getLong("unit_id"));
					unit.setName(rs.getString("name"));
					unit.setCode(rs.getString("code"));				 
				return unit;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	@Override
	public Unit getById(String id) {		 		
		String sql =" select *  from unit where unit_id = "+id;
		Unit unit = this.jdbcTemplate.queryForObject(sql,	new UnitMapper() );				
 		
		return unit;
	}	

	
	@Override
	public void deleteById(String id) {	 
		this.jdbcTemplate.update(" delete from  unit where unit_id ="+id);			 
	}
	
 
	
	@Override
	public boolean isAlreadyUsege(String id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from unit unit inner join aumphur aumphur " +
					"on (unit.unit_id = aumphur.unit_id)	where unit.unit_id ="+id;			
			logger.info(" sql:"+sqltmp);
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	@Override
	public boolean isExist(String name) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as total  from unit t  where t.name='"+StringEscapeUtils.escapeSql(name)+"'";
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	
	@Override
	public boolean isExistForUpdate(String name,Long id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as total  from unit t  where t.name='"+StringEscapeUtils.escapeSql(name)+"'  and t.unit_id !="+id	;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}
	
	
	private class UnitMapper implements RowMapper<Unit> {   						
        @Override
		public Unit mapRow(ResultSet rs, int rowNum) throws SQLException {
			Unit domain = new Unit(); 					
			domain.setUnitId(rs.getLong("unit_id"));		
			domain.setCode(rs.getString("code")); ;
			domain.setName(rs.getString("name"));	
		return domain;
    }
        
	} 
}
