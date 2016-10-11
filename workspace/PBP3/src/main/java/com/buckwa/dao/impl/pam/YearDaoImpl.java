package com.buckwa.dao.impl.pam;

import java.sql.Connection;
import java.sql.Date;
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

import com.buckwa.dao.intf.pam.YearDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Year;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaDateUtils;

@Repository("yearDao")
public class YearDaoImpl implements YearDao {
	private static Logger logger = LoggerFactory.getLogger(YearDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Year> getAll() {
		List<Year> returnList = new ArrayList<Year>(); 
		String sql ="  select * from year r order by name " ;	
		logger.info(" sql:"+sql);
		returnList = this.jdbcTemplate.query(sql,new YearMapper());
		return returnList;
	}
	 
 

	@Override
	public void update(Year year) {
		logger.info("  year: "+BeanUtils.getBeanString(year));
		this.jdbcTemplate.update(
				"update  year set name=?,start_date=?,end_date=?    where year_id=? ",
				year.getName() ,year.getStartDate(),year.getEndDate(),year.getYearId()); 
 	 	
	}
 
	
	@Override
	public Long create(Year year) {
		logger.info("  year: "+BeanUtils.getBeanString(year));		
		final Year finalYear = year;
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into year (year_id,name,start_date,end_date ) values (?,?,?,? )");   
				ps.setLong(1,Long.parseLong(finalYear.getName()));
				ps.setString(2,finalYear.getName());
				ps.setDate(3,new Date(finalYear.getStartDate().getTime())); 
				ps.setDate(4,new Date(finalYear.getEndDate().getTime())); 
				return ps;  
				}
			}); 	
		Long returnid =  Long.parseLong(finalYear.getName());	
		
	   return returnid;
	}

	
	public PagingBean getByOffset(PagingBean pagingBean) {	 
		Year year = (Year)pagingBean.get("year");		
		List<Year> returnList = new ArrayList<Year>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from year  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(year.getName())){
			sqltotalsb.append(" and r.name like  '%"+StringEscapeUtils.escapeSql(year.getName().trim())+"%'");
		}
 
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select *  \n");
		sb.append("  from year r  \n");
		
		if(StringUtils.hasText(year.getName())){
			sb.append(" where  r.name like  '%"+StringEscapeUtils.escapeSql(year.getName().trim())+"%'");
		}
		sb.append(" order by r.name ");		
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Year>() {
				public Year mapRow(ResultSet rs, int rowNum) throws SQLException {
					Year year = new Year();				 
					year.setYearId(rs.getLong("year_id"));
					year.setName(rs.getString("name"));
					year.setStartDate(rs.getDate("start_date"));
					year.setEndDate(rs.getDate("end_date"));
				return year;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	@Override
	public Year getById(String id) {		 		
		String sql =" select *  from year where year_id = "+id+" order by name";
		Year year = this.jdbcTemplate.queryForObject(sql,	new YearMapper() );				
 		
		return year;
	}	

	
	@Override
	public void deleteById(String id) {	 
		this.jdbcTemplate.update(" delete from  year where year_id ="+id);			 
	}
	
 
	
	@Override
	public boolean isAlreadyUsege(String id) {
		 boolean returnValue = false;
		try{
			//String sqltmp = "select count(*) as totalusage from year year inner join aumphur aumphur " +
			//		"on (year.year_id = aumphur.year_id)	where year.year_id ="+id;			
			//logger.info(" sql:"+sqltmp);
			//Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			//if(found!=null&&found.intValue()>0){
			//	returnValue = true;
			//}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	@Override
	public boolean isExist(String name) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as total  from year t  where t.name='"+StringEscapeUtils.escapeSql(name)+"'";
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
			String sqltmp = "select count(*) as total  from year t  where t.name='"+StringEscapeUtils.escapeSql(name)+"'  and t.year_id !="+id	;
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
	public int getYearCurrent(){
		int returnValue=0;
		try{
			String sqltmp = "SELECT year_id FROM year w where  start_date <=? and ? <= end_date";
			Integer found = this.jdbcTemplate.queryForObject(sqltmp, new Object[]{new java.util.Date(),new java.util.Date()},Integer.class );
			if(found!=null&&found.intValue()>0){
				returnValue = found.intValue();
			}
		}catch(EmptyResultDataAccessException ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}
	
	
	private class YearMapper implements RowMapper<Year> {   						
        @Override
		public Year mapRow(ResultSet rs, int rowNum) throws SQLException {
			Year domain = new Year(); 					
			domain.setYearId(rs.getLong("year_id"));		
			domain.setStartDate(rs.getDate("start_date"));
			domain.setEndDate(rs.getDate("end_date"));
			domain.setName(rs.getString("name"));	
		return domain;
    }
        
	} 
}
