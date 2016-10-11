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

import com.buckwa.dao.intf.admin.address.AumphurDao;
import com.buckwa.domain.admin.address.Aumphur;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.util.BeanUtils;

@Repository("aumphurDao")
public class AumphurDaoImpl implements AumphurDao {
	private static Logger logger = LoggerFactory.getLogger(AumphurDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Aumphur> getAll() {
		List<Aumphur> returnList = new ArrayList<Aumphur>(); 
		String sql ="  select r.aumphur_id , r.name, r.code from aumphur r  " ;	
		logger.info(" sql:"+sql);
		returnList = this.jdbcTemplate.query(sql,new AumphurMapper());
		return returnList;
	}
	 
 

	@Override
	public void update(Aumphur aumphur) {
		logger.info("  aumphur: "+BeanUtils.getBeanString(aumphur));
		this.jdbcTemplate.update(
				"update  aumphur set name=?,code=?   where aumphur_id=? ",
				aumphur.getName(), aumphur.getCode(),aumphur.getAumphurId()); 
 	 	
	}
 
	
	@Override
	public Long create(Aumphur aumphur) {
		logger.info("   ");		
		final Aumphur finalAumphur = aumphur;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into aumphur (name, code) values (?, ?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalAumphur.getName());
				ps.setString(2,finalAumphur.getCode());				 			
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
		
	   return returnid;
	}

	
	public PagingBean getByOffset(PagingBean pagingBean) {	 
		Aumphur aumphur = (Aumphur)pagingBean.get("aumphur");		
		List<Aumphur> returnList = new ArrayList<Aumphur>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from aumphur  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(aumphur.getName())){
			sqltotalsb.append(" and r.name like  '%"+aumphur.getName().trim()+"%'");
		}
		if(StringUtils.hasText(aumphur.getCode())){
			sqltotalsb.append(" and r.code like  '%"+aumphur.getCode().trim()+"%'");
		}
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" r.aumphur_id , r.name, r.code from aumphur r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(aumphur.getName())){
			sb.append(" and r.name like  '%"+aumphur.getName().trim()+"%'");
		}
		if(StringUtils.hasText(aumphur.getCode())){
			sb.append(" and r.code like  '%"+aumphur.getCode().trim()+"%'");
		}
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Aumphur>() {
				public Aumphur mapRow(ResultSet rs, int rowNum) throws SQLException {
					Aumphur aumphur = new Aumphur();				 
					aumphur.setAumphurId(rs.getLong("aumphur_id"));
					aumphur.setName(rs.getString("name"));
					aumphur.setCode(rs.getString("code"));				 
				return aumphur;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	@Override
	public Aumphur getById(String id) {		 		
		String sql =" select *  from aumphur where aumphur_id = "+id;
		Aumphur aumphur = this.jdbcTemplate.queryForObject(sql,	new AumphurMapper() );				
 		
		return aumphur;
	}	

	
	@Override
	public void deleteById(String id) {	 
		this.jdbcTemplate.update(" delete from  aumphur where aumphur_id ="+id);			 
	}
	
 
	
	@Override
	public boolean isAlreadyUsege(String id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from aumphur aumphur inner join aumphur aumphur " +
					"on (aumphur.aumphur_id = aumphur.aumphur_id)	where aumphur.aumphur_id ="+id;			
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
			String sqltmp = "select count(*) as total  from aumphur t  where t.name='"+name+"'";
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
			String sqltmp = "select count(*) as total  from aumphur t  where t.name='"+name+"'  and t.aumphur_id !="+id	;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}
	
	
	private class AumphurMapper implements RowMapper<Aumphur> {   						
        @Override
		public Aumphur mapRow(ResultSet rs, int rowNum) throws SQLException {
			Aumphur domain = new Aumphur(); 					
			domain.setAumphurId(rs.getLong("aumphur_id"));		
			domain.setCode(rs.getString("code")); ;
			domain.setName(rs.getString("name"));	
		return domain;
    }
        
	} 
}
