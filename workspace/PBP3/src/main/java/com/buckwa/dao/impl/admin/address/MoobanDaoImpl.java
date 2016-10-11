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

import com.buckwa.dao.intf.admin.address.MoobanDao;
import com.buckwa.domain.admin.address.Mooban;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.util.BeanUtils;

@Repository("moobanDao")
public class MoobanDaoImpl implements MoobanDao {
	private static Logger logger = LoggerFactory.getLogger(MoobanDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Mooban> getAll() {
		List<Mooban> returnList = new ArrayList<Mooban>(); 
		String sql ="  select r.mooban_id , r.name, r.code from mooban r  " ;	
		logger.info(" sql:"+sql);
		returnList = this.jdbcTemplate.query(sql,new MoobanMapper());
		return returnList;
	}
	 
 

	@Override
	public void update(Mooban mooban) {
		logger.info("  mooban: "+BeanUtils.getBeanString(mooban));
		this.jdbcTemplate.update(
				"update  mooban set name=?,code=?   where mooban_id=? ",
				mooban.getName(), mooban.getCode(),mooban.getMoobanId()); 
 	 	
	}
 
	
	@Override
	public Long create(Mooban mooban) {
		logger.info("   ");		
		final Mooban finalMooban = mooban;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into mooban (name, code) values (?, ?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalMooban.getName());
				ps.setString(2,finalMooban.getCode());				 			
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
		
	   return returnid;
	}

	
	public PagingBean getByOffset(PagingBean pagingBean) {	 
		Mooban mooban = (Mooban)pagingBean.get("mooban");		
		List<Mooban> returnList = new ArrayList<Mooban>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from mooban  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(mooban.getName())){
			sqltotalsb.append(" and r.name like  '%"+mooban.getName().trim()+"%'");
		}
		if(StringUtils.hasText(mooban.getCode())){
			sqltotalsb.append(" and r.code like  '%"+mooban.getCode().trim()+"%'");
		}
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" r.mooban_id , r.name, r.code from mooban r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(mooban.getName())){
			sb.append(" and r.name like  '%"+mooban.getName().trim()+"%'");
		}
		if(StringUtils.hasText(mooban.getCode())){
			sb.append(" and r.code like  '%"+mooban.getCode().trim()+"%'");
		}
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Mooban>() {
				public Mooban mapRow(ResultSet rs, int rowNum) throws SQLException {
					Mooban mooban = new Mooban();				 
					mooban.setMoobanId(rs.getLong("mooban_id"));
					mooban.setName(rs.getString("name"));
					mooban.setCode(rs.getString("code"));				 
				return mooban;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	@Override
	public Mooban getById(String id) {		 		
		String sql =" select *  from mooban where mooban_id = "+id;
		Mooban mooban = this.jdbcTemplate.queryForObject(sql,	new MoobanMapper() );				
 		
		return mooban;
	}	

	
	@Override
	public void deleteById(String id) {	 
		this.jdbcTemplate.update(" delete from  mooban where mooban_id ="+id);			 
	}
	
 
	
	@Override
	public boolean isAlreadyUsege(String id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from mooban mooban inner join aumphur aumphur " +
					"on (mooban.mooban_id = aumphur.mooban_id)	where mooban.mooban_id ="+id;			
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
			String sqltmp = "select count(*) as total  from mooban t  where t.name='"+name+"'";
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
			String sqltmp = "select count(*) as total  from mooban t  where t.name='"+name+"'  and t.mooban_id !="+id	;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}
	
	
	private class MoobanMapper implements RowMapper<Mooban> {   						
        @Override
		public Mooban mapRow(ResultSet rs, int rowNum) throws SQLException {
			Mooban domain = new Mooban(); 					
			domain.setMoobanId(rs.getLong("mooban_id"));		
			domain.setCode(rs.getString("code")); ;
			domain.setName(rs.getString("name"));	
		return domain;
    }
        
	} 
}
