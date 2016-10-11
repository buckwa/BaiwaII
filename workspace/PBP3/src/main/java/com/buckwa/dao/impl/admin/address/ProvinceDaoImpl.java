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

import com.buckwa.dao.intf.admin.address.ProvinceDao;
import com.buckwa.domain.admin.address.Province;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.util.BeanUtils;

@Repository("provinceDao")
public class ProvinceDaoImpl implements ProvinceDao {
	private static Logger logger = LoggerFactory.getLogger(ProvinceDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Province> getAll() {
		List<Province> returnList = new ArrayList<Province>(); 
		String sql ="  select r.province_id , r.name, r.code from province r  " ;	
		logger.info(" sql:"+sql);
		returnList = this.jdbcTemplate.query(sql,new ProvinceMapper());
		return returnList;
	}
	 
 

	@Override
	public void update(Province province) {
		logger.info("  province: "+BeanUtils.getBeanString(province));
		this.jdbcTemplate.update(
				"update  province set name=?,code=?   where province_id=? ",
				province.getName(), province.getCode(),province.getProvinceId()); 
 	 	
	}
 
	
	@Override
	public Long create(Province province) {
		logger.info("   ");		
		final Province finalProvince = province;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into province (name, code) values (?, ?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalProvince.getName());
				ps.setString(2,finalProvince.getCode());				 			
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
		
	   return returnid;
	}

	
	public PagingBean getByOffset(PagingBean pagingBean) {	 
		Province province = (Province)pagingBean.get("province");		
		List<Province> returnList = new ArrayList<Province>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from province  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(province.getName())){
			sqltotalsb.append(" and r.name like  '%"+province.getName().trim()+"%'");
		}
		if(StringUtils.hasText(province.getCode())){
			sqltotalsb.append(" and r.code like  '%"+province.getCode().trim()+"%'");
		}
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" r.province_id , r.name, r.code from province r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(province.getName())){
			sb.append(" and r.name like  '%"+province.getName().trim()+"%'");
		}
		if(StringUtils.hasText(province.getCode())){
			sb.append(" and r.code like  '%"+province.getCode().trim()+"%'");
		}
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Province>() {
				public Province mapRow(ResultSet rs, int rowNum) throws SQLException {
					Province province = new Province();				 
					province.setProvinceId(rs.getLong("province_id"));
					province.setName(rs.getString("name"));
					province.setCode(rs.getString("code"));				 
				return province;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	@Override
	public Province getById(String id) {		 		
		String sql =" select *  from province where province_id = "+id;
		Province province = this.jdbcTemplate.queryForObject(sql,	new ProvinceMapper() );				
 		
		return province;
	}	

	
	@Override
	public void deleteById(String id) {	 
		this.jdbcTemplate.update(" delete from  province where province_id ="+id);			 
	}
	
 
	
	@Override
	public boolean isAlreadyUsege(String id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from province province inner join aumphur aumphur " +
					"on (province.province_id = aumphur.province_id)	where province.province_id ="+id;			
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
			String sqltmp = "select count(*) as total  from province t  where t.name='"+name+"'";
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
			String sqltmp = "select count(*) as total  from province t  where t.name='"+name+"'  and t.province_id !="+id	;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}
	
	
	private class ProvinceMapper implements RowMapper<Province> {   						
        @Override
		public Province mapRow(ResultSet rs, int rowNum) throws SQLException {
			Province domain = new Province(); 					
			domain.setProvinceId(rs.getLong("province_id"));		
			domain.setCode(rs.getString("code")); ;
			domain.setName(rs.getString("name"));	
		return domain;
    }
        
	} 
}
