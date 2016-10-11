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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.pam.KpiCategoryDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.KpiCategory;
import com.buckwa.util.BeanUtils;

@Repository("kpiCategoryDao")
public class KpiCategoryDaoImpl implements KpiCategoryDao {
	private static Logger logger = LoggerFactory.getLogger(KpiCategoryDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<KpiCategory> getAll() {
		List<KpiCategory> returnList = new ArrayList<KpiCategory>(); 
		String sql ="  select r.kpicategory_id , r.name, r.code from kpicategory r  " ;	
		logger.info(" sql:"+sql);
		returnList = this.jdbcTemplate.query(sql,new KpiCategoryMapper());
		return returnList;
	}
	 
 

	@Override
	public void update(KpiCategory kpicategory) {
		logger.info("  kpicategory: "+BeanUtils.getBeanString(kpicategory));
		this.jdbcTemplate.update("update  kpicategory set name=?,code=?   where kpicategory_id=? ",
				kpicategory.getName(), kpicategory.getCode(),kpicategory.getKpiCategoryId()); 
 	 	
	}
 
	
	@Override
	public Long create(KpiCategory kpicategory) {
		logger.info("   ");		
		final KpiCategory finalKpiCategory = kpicategory;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into kpicategory (name, code) values (?, ?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalKpiCategory.getName());
				ps.setString(2,finalKpiCategory.getCode());				 			
				return ps;  
				}
			}, 	keyHolder); 	
		final Long returnid =  keyHolder.getKey().longValue();			
		// Create KPI Group Root 
 
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into kpi_tree_template (name, group_id) values (?, ?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalKpiCategory.getName());
			 
				ps.setLong(2,returnid);	
				return ps;  
				}
			}, 	keyHolder); 

		
	   return returnid;
	}

	
	public PagingBean getByOffset(PagingBean pagingBean) {	 
		KpiCategory kpicategory = (KpiCategory)pagingBean.get("kpiCategory");		
		List<KpiCategory> returnList = new ArrayList<KpiCategory>();			
		StringBuffer sqltotalsb = new StringBuffer();
		
		sqltotalsb.append(" select count(*) as total_item from kpicategory k ");
		sqltotalsb.append(" where 1=1  ") ;
		
		/*sqltotalsb.append("   select count(*) as total_item  from kpicategory  r  \n"); 
		sqltotalsb.append(" where 1=1 ");*/		
		if(StringUtils.hasText(kpicategory.getName())){
			sqltotalsb.append(" and k.name like  '%"+StringEscapeUtils.escapeSql(kpicategory.getName().trim())+"%'");
		}
		if(StringUtils.hasText(kpicategory.getCode())){
			sqltotalsb.append(" and k.code like  '%"+StringEscapeUtils.escapeSql(kpicategory.getCode().trim())+"%'");
		}
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select k.* from kpicategory k  ");
		sb.append(" where 1=1 ") ;
		/*sb.append(" select   \n");
		sb.append(" r.kpicategory_id , r.name, r.code from kpicategory r   inner join lov_detail d on h.lov_header_id = d.lov_header_id   \n");*/
		if(StringUtils.hasText(kpicategory.getName())){
			sb.append(" and k.name like  '%"+StringEscapeUtils.escapeSql(kpicategory.getName().trim())+"%'");
		}
		if(StringUtils.hasText(kpicategory.getCode())){
			sb.append(" and k.code like  '%"+StringEscapeUtils.escapeSql(kpicategory.getCode().trim())+"%'");
		}
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<KpiCategory>() {
				public KpiCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
					KpiCategory kpicategory = new KpiCategory();				 
					kpicategory.setKpiCategoryId(rs.getLong("kpicategory_id"));
					kpicategory.setName(rs.getString("name"));
					kpicategory.setCode(rs.getString("code"));		
					if("0".equals(kpicategory.getCode()))
						kpicategory.setEmployeeType("person_type0");
					else
						kpicategory.setEmployeeType("person_type1");
				return kpicategory;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	@Override
	public KpiCategory getById(String id) {	
		try{
			String sql =" select *  from kpicategory where kpicategory_id = "+id;
			KpiCategory kpicategory = this.jdbcTemplate.queryForObject(sql,	new KpiCategoryMapper() );				
			return kpicategory;
		}catch(EmptyResultDataAccessException e){
			
		}
		return null;
	}	

	
	@Override
	public void deleteById(String id) {	 
		this.jdbcTemplate.update(" delete from  kpicategory where kpicategory_id ="+id);			 
	}
	
 
	
	@Override
	public boolean isAlreadyUsege(String id) {
		 boolean returnValue = false;
		try{
			/*
			String sqltmp = "select count(*) as totalusage from kpicategory kpicategory inner join aumphur aumphur " +
					"on (kpicategory.kpicategory_id = aumphur.kpicategory_id)	where kpicategory.kpicategory_id ="+id;			
			logger.info(" sql:"+sqltmp);
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
			*/
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	@Override
	public boolean isExist(String name) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as total  from kpicategory t  where t.name='"+StringEscapeUtils.escapeSql(name)+"'";
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
			String sqltmp = "select count(*) as total  from kpicategory t  where t.name='"+StringEscapeUtils.escapeSql(name)+"'  and t.kpicategory_id !="+id	;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}
	
	
	private class KpiCategoryMapper implements RowMapper<KpiCategory> {   						
        @Override
		public KpiCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
			KpiCategory domain = new KpiCategory(); 					
			domain.setKpiCategoryId(rs.getLong("kpicategory_id"));		
			domain.setCode(rs.getString("code")); ;
			domain.setName(rs.getString("name"));	
		return domain;
    }
        
	}


	@Override
	public String getKpiCategoryIdByYearIdAndPersonType(String yearId, String personType) {
		logger.info("- start");
		
		StringBuilder sql = new StringBuilder();
		sql.append( " SELECT kpicategory_id AS value ");
		sql.append( " FROM kpicategory category ");
		sql.append( " INNER JOIN kpi_year_mapping mapping ");
		sql.append( "   ON category.kpicategory_id = mapping.category_id ");
		sql.append( " WHERE mapping.year_id = ? ");
		sql.append( " AND category.code = ? ");
		
		String kpiCategoryId = this.jdbcTemplate.queryForObject(sql.toString(), new Object[] {
			yearId,
			personType
		}, stringValueMapper);
		
		if (logger.isDebugEnabled()) {
			logger.info("sql: " + sql);
			logger.info("yearId: " + yearId);
			logger.info("personType: " + personType);
			logger.info("kpiCategoryId: " + kpiCategoryId);
		}
		
		return kpiCategoryId;
	}
	

	private static final StringMapper stringValueMapper = new StringMapper();
	private static class StringMapper implements RowMapper<String> {
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String value = String.valueOf(rs.getLong("value"));
			return value;
		}
	}
	
}
