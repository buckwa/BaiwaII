package com.buckwa.dao.impl.admin.address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.admin.address.TumbonDao;
import com.buckwa.domain.admin.address.Tumbon;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.util.BeanUtils;

@Repository("tumbonDao")
public class TumbonDaoImpl implements TumbonDao {
	private static Logger logger = LoggerFactory.getLogger(TumbonDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Tumbon> getAll() {
		List<Tumbon> returnList = new ArrayList<Tumbon>(); 
		String sql ="  select r.tumbon_id , r.name, r.code from tumbon r  " ;	
		logger.info(" sql:"+sql);
		returnList = this.jdbcTemplate.query(sql,new TumbonMapper());
		return returnList;
	}
	 
 

	@Override
	public void update(Tumbon tumbon) {
		logger.info("  tumbon: "+BeanUtils.getBeanString(tumbon));
		this.jdbcTemplate.update(
				"update  tumbon set name=?,code=?   where tumbon_id=? ",
				tumbon.getName(), tumbon.getCode(),tumbon.getTumbonId()); 
 	 	
	}
 
	
	@Override
	public Long create(Tumbon tumbon) {
		logger.info("   ");		
		final Tumbon finalTumbon = tumbon;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into tumbon (name, code) values (?, ?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalTumbon.getName());
				ps.setString(2,finalTumbon.getCode());				 			
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
		
	   return returnid;
	}

	
	public PagingBean getByOffset(PagingBean pagingBean) {	 
		Tumbon tumbon = (Tumbon)pagingBean.get("tumbon");		
		List<Tumbon> returnList = new ArrayList<Tumbon>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from tumbon  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(tumbon.getName())){
			sqltotalsb.append(" and r.name like  '%"+tumbon.getName().trim()+"%'");
		}
		if(StringUtils.hasText(tumbon.getCode())){
			sqltotalsb.append(" and r.code like  '%"+tumbon.getCode().trim()+"%'");
		}
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" r.tumbon_id , r.name, r.code from tumbon r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(tumbon.getName())){
			sb.append(" and r.name like  '%"+tumbon.getName().trim()+"%'");
		}
		if(StringUtils.hasText(tumbon.getCode())){
			sb.append(" and r.code like  '%"+tumbon.getCode().trim()+"%'");
		}
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Tumbon>() {
				public Tumbon mapRow(ResultSet rs, int rowNum) throws SQLException {
					Tumbon tumbon = new Tumbon();				 
					tumbon.setTumbonId(rs.getLong("tumbon_id"));
					tumbon.setName(rs.getString("name"));
					tumbon.setCode(rs.getString("code"));				 
				return tumbon;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	@Override
	public Tumbon getById(String id) {		 		
		String sql =" select *  from tumbon where tumbon_id = "+id;
		Tumbon tumbon = this.jdbcTemplate.queryForObject(sql,	new TumbonMapper() );				
 		
		return tumbon;
	}	

	
	@Override
	public void deleteById(String id) {	 
		this.jdbcTemplate.update(" delete from  tumbon where tumbon_id ="+id);			 
	}
	
 
	
	@Override
	public boolean isAlreadyUsege(String id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from tumbon tumbon inner join aumphur aumphur " +
					"on (tumbon.tumbon_id = aumphur.tumbon_id)	where tumbon.tumbon_id ="+id;			
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
			String sqltmp = "select count(*) as total  from tumbon t  where t.name='"+name+"'";
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
			String sqltmp = "select count(*) as total  from tumbon t  where t.name='"+name+"'  and t.tumbon_id !="+id	;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}
	
	
	private class TumbonMapper implements RowMapper<Tumbon> {   						
        @Override
		public Tumbon mapRow(ResultSet rs, int rowNum) throws SQLException {
			Tumbon domain = new Tumbon(); 					
			domain.setTumbonId(rs.getLong("tumbon_id"));		
			domain.setCode(rs.getString("code")); ;
			domain.setName(rs.getString("name"));	
		return domain;
    }
        
	} 
}
