package com.buckwa.dao.impl.project;

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

import com.buckwa.dao.intf.project.ProjectUtilDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.DetailDesign;
import com.buckwa.domain.project.UseCase;
import com.buckwa.domain.project.Util;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.project.ProjectUtil;

@Repository("projectUtilDao")
public class ProjectUtilDaoImpl implements ProjectUtilDao {
	private static Logger logger = LoggerFactory.getLogger(ProjectUtilDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ProjectUtil projectUtil;

	public List<Util> getAllUtil_N() {
		List<Util> returnList = new ArrayList<Util>(); 
		String sql ="  select * from project_util r  where type ='N'" ;	
		logger.info(" ### getAllUtil_N sql:"+sql);
		returnList = this.jdbcTemplate.query(	sql, new UtilMapper());
        logger.info(" ### getAllUtil_N Found :"+returnList);
	return returnList;
	}
	
	public List<Util> getAllUtil_D() {
		List<Util> returnList = new ArrayList<Util>(); 
		String sql ="  select * from project_util r where type = 'D'" ;	
		logger.info(" ### getAllUtil_D sql:"+sql);
		returnList = this.jdbcTemplate.query(	sql, new UtilMapper());
		 logger.info(" ### getAllUtil_D Found :"+returnList);
	return returnList;
	}
	 
 
	@Override
	public void update(Util util) {
		logger.info(" #UtilDaoImpl.update util: "+BeanUtils.getBeanString(util));
		this.jdbcTemplate.update(
				"update  project_util set detail=?   where util_id=? ",
				util.getDetail()  ,util.getUtilId());
 
	}
 
	@Override
	public void create(Util util) {
		logger.info(" #UtilDaoImpl.create # ");
		
		final String businessCode = projectUtil.getUtilNo(util.getProjectId());
		
		final Util finalUtil = util;
		KeyHolder keyHolder = new GeneratedKeyHolder();  
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into project_util (code, detail,project_id) values (?, ?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,businessCode);
				ps.setString(2,finalUtil.getDetail());
				ps.setLong(3,finalUtil.getProjectId());		 			
				return ps;  
				}
			}, 	keyHolder);
 	
		Long returnid =  keyHolder.getKey().longValue();
	 	
	}

	
	public PagingBean getByOffset(PagingBean pagingBean) {
	 
		Util util = (Util)pagingBean.get("util");		
		List<Util> returnList = new ArrayList<Util>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  project_util  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(util.getCode())){
			sqltotalsb.append(" and r.code like  '%"+util.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(util.getName())){
			sqltotalsb.append(" and r.name like  '%"+util.getName().trim()+"%'");
		} 
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		

		
		StringBuffer sb = new StringBuffer();
		sb.append(" select *  \n");
		sb.append("  from project_util r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(util.getCode())){
			sb.append(" and r.code like  '%"+util.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(util.getName())){
			sb.append(" and r.name like  '%"+util.getName().trim()+"%'");
		} 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo()); 
		
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(sql,new UtilMapper());
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	

	@Override
	public Util getUtilById(String utilId) {	
		logger.info(" ### getUtilById utilId:"+utilId);
		Util util = this.jdbcTemplate.queryForObject(
				"select *  from project_util where util_id = ?",
				new Object[]{utilId},new UtilMapper());
		/*
		if(util!=null){
			
			String sql ="  select * from project_util r  where r.util_id="+utilId ;	
			List<UseCase> useCaseReturnList = this.jdbcTemplate.query(	sql,new UseCaseMapper());
			util.setUseCaseList(useCaseReturnList);
		}
		 */
		return util;
	}
	
	@Override
	public Util getUtilandDetailDesignById(String utilId) {	
		logger.info(" ### getUtilandDetailDesignById utilId:"+utilId);
		Util util = this.jdbcTemplate.queryForObject(
				"select *  from project_util where util_id = ?",
				new Object[]{utilId},new UtilMapper());
		/*
		if(util!=null){
			
			String sql ="  select * from project_detail_design r  where r.util_id="+utilId ;	
			List<DetailDesign> returnList = this.jdbcTemplate.query(	sql,new DetailDesignMapper());
			util.setDetailDesignList(returnList);
		}
		
		logger.info("  ####### getUtilandDetailDesignById detailDesignList:"+util.getDetailDesignList());
		 */
		return util;
	}	


	
	@Override
	public void deleteUtilById(String utilId) {	 
		this.jdbcTemplate.update(" delete from  project_util where util_id ="+utilId);	
		 	
	}
	
 
	@Override
	public boolean isUtilAlreadyUsege(String utilId) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from buckwautil util inner join buckwagrouputil grouputil " +
					"on (util.util_id = grouputil.util_id)	where util.util_id ="+utilId;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	private class UtilMapper implements RowMapper<Util> {   						
        @Override
		public Util mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Util domain = new Util();
        	domain.setUtilId(rs.getLong("util_id"));
        	domain.setCode(rs.getString("code"));
        	domain.setName(rs.getString("name"));
        	domain.setDetail(rs.getString("detail"));        	 
        	domain.setProjectId(rs.getLong("project_id"));  
         	domain.setType(rs.getString("type"));
		return domain;
		} 
    } 
	
 
 
	
}
