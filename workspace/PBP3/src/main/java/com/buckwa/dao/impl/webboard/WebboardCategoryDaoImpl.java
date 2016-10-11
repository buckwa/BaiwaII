package com.buckwa.dao.impl.webboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.webboard.WebboardCategoryDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.util.Category;
import com.buckwa.util.BeanUtils;

@Repository("webboardCategoryDao")
public class WebboardCategoryDaoImpl implements WebboardCategoryDao {
	private static Logger logger = LoggerFactory.getLogger(WebboardCategoryDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Category> getAll() {
		List<Category> returnList = new ArrayList<Category>(); 
		String sql ="  select * from webboard_category r  " ;	
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Category>() {
				public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
					Category domain = new Category(); 					
					domain.setCategoryId(rs.getLong("category_id"));	
					domain.setName(rs.getString("name"));
					domain.setDesc(rs.getString("description"));
				return domain;
				}
				});

		logger.info("  getAll :"+returnList);
	return returnList;
	}
	 
	@Override
	public void update(Category category)throws Exception  {
		logger.info(" #CategoryDaoImpl.update category: "+BeanUtils.getBeanString(category));
		try{	
		this.jdbcTemplate.update(
				"update  webboard_category set code=?,description=? ,name=?   where category_id=? ",
				category.getCode(), category.getDesc(),category.getName() ,category.getCategoryId());
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}	
	}

	@Override
	public void create(Category category) throws Exception {
		logger.info(" #CategoryDaoImpl.create # ");		
	try{		
		this.jdbcTemplate.update(
				"insert into webboard_category (  description, name ) values ( ?,? )",
				  category.getDesc(),category.getName()  );			
	}catch(Exception ex){
		ex.printStackTrace();
		throw ex;
	}	
	}

	
	public PagingBean getAllByOffset(PagingBean pagingBean) {
		logger.info("");
		
		List<Category> returnList = new ArrayList<Category>();		
		Category category = (Category)pagingBean.get("category");	
		StringBuffer totalSql  = new StringBuffer();
	 
		totalSql.append("  select count(*) as total_item  from  webboard_category t where 1=1 ") ;		
		if(StringUtils.hasText(category.getName())){
			totalSql.append(" and t.name like  '%"+category.getName().trim()+"%'");
		}
		if(StringUtils.hasText(category.getDesc())){
			totalSql.append(" and t.description like  '%"+category.getDesc().trim()+"%'");
		}
		int  rows_totalItem = jdbcTemplate.queryForObject(totalSql.toString(),Integer.class); 
		
		
		pagingBean.setTotalItems(rows_totalItem);			
	
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" * from webboard_category r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(category.getName())){
			sb.append(" and r.name like  '%"+category.getName().trim()+"%'");
		}
		if(StringUtils.hasText(category.getDesc())){
			sb.append(" and r.description like  '%"+category.getDesc().trim()+"%'");
		} 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo()); 
		
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Category>() {
				public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
					Category category = new Category();				 
					category.setCategoryId(rs.getLong("category_id"));
					category.setCode(rs.getString("code"));
					category.setName(rs.getString("name"));
					category.setDesc(rs.getString("description"));	 
					category.setStatus(rs.getString("status"));	 
				return category;
				}
				});
		pagingBean.setCurrentPageItem(returnList);
		return pagingBean;
	}
	 
	@Override
	public Category getById(String categoryId) {		 
		Category category = this.jdbcTemplate.queryForObject(
				"select *  from webboard_category where category_id = ?",
				new Object[]{categoryId},
				new RowMapper<Category>() {
				public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
					Category category = new Category();
					category.setCategoryId(rs.getLong("category_id"));
					category.setCode(rs.getString("code"));
					category.setName(rs.getString("name"));
					category.setDesc(rs.getString("description"));	
				 
					category.setStatus(rs.getString("status"));	
					 
				return category;
				}
				});
			
		return category;
	}	

	
	@Override
	public void deleteById(String categoryId) {	 
		this.jdbcTemplate.update(" delete from  webboard_category where category_id ="+categoryId);		
	}
 
	@Override
	public String getIdFromCategoryName(String categoryName) {		
		String returnstr = "";
		String sqlCategoryId = " select category_id form webboard_category where category_name ='"+categoryName+"'";

		logger.info(" sqlCategoryId:"+sqlCategoryId);		
		returnstr = this.jdbcTemplate.queryForObject(
				categoryName,
				new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {					
					String str = rs.getString("category_id")	;	
				return str;
				}
				});	
		
		return returnstr;
	}
	
	@Override
	public boolean isAlreadyUsege(String categoryId) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from webboard_category category inner join buckwagroupcategory groupcategory " +
					"on (category.category_id = groupcategory.category_id)	where category.category_id ="+categoryId;
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
	public boolean isExist( String name) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalCategory  from webboard_category category  where  category.name='"+name+"'";
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
	public boolean isExistForUpdate( String name,Long id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalCategory  from webboard_category t  where (  t.name='"+name+"') and t.category_id !="+id;		 
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
}
