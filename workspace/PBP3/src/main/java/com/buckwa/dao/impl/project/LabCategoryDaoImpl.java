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

import com.buckwa.dao.intf.project.LabCategoryDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Lab;
import com.buckwa.domain.project.LabCategory;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.project.ProjectUtil;

@Repository("labCategoryDao")
public class LabCategoryDaoImpl implements LabCategoryDao {
	private static Logger logger = LoggerFactory.getLogger(LabCategoryDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ProjectUtil projectUtil;

	public List<LabCategory> getAllLabCategory() {
		List<LabCategory> returnList = new ArrayList<LabCategory>(); 
		String sql ="  select * from project_labCategory r  " ;	
		returnList = this.jdbcTemplate.query(	sql, new LabCategoryMapper());

	return returnList;
	}
	@Override
	public List<LabCategory> getAllLabCategoryByProjectId(String projectId) {
		List<LabCategory> returnList = new ArrayList<LabCategory>(); 
		String sql ="  select * from project_labCategory r where project_id="+projectId ;	
		returnList = this.jdbcTemplate.query(	sql, new LabCategoryMapper());

	return returnList;
	}
	@Override
	public List<LabCategory> getAllLabCategoryByProjectIdCountLab(String projectId) {
		List<LabCategory> returnList = new ArrayList<LabCategory>(); 
		StringBuffer sb = new StringBuffer();
		sb.append(" select parent.*, count(child.lab_id) no_of_child  \n");
		sb.append("  from   project_labCategory parent\n");
		sb.append(" left join project_lab child   on child.lab_category_id = parent.lab_category_id \n");
		sb.append("  where parent.project_id="+projectId +" \n");
		sb.append("  group by parent.lab_category_id \n");
		sb.append("  order by parent.lab_category_id  \n");
 
		returnList = this.jdbcTemplate.query(	sb.toString(), new LabCategoryCountMapper());

	return returnList;
	}	
	
	@Override
	public List<LabCategory> getAllLabCategoryByProjectIdCountDetailDesign(String projectId) {
		logger.info(" #DetailDesignDaoImpl.getAllDetailDesign # ");
		List<LabCategory> returnList = new ArrayList<LabCategory>(); 
		try{
			StringBuffer sb = new StringBuffer();
			sb.append(" select parent.*, count(child.detail_design_id) no_of_child  \n");
			sb.append("  from   project_labCategory parent\n");
			sb.append(" left join project_detail_design child   on child.lab_category_id = parent.lab_category_id \n");
			sb.append("  where parent.project_id="+projectId +" \n");
			sb.append("  group by parent.lab_category_id \n");
			sb.append("  order by parent.lab_category_id  \n");			
			 	
			returnList = this.jdbcTemplate.query(	sb.toString(),new LabCategoryCountMapper());
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnList;
	}
 
	@Override
	public List<LabCategory> getAllLabCategoryByProjectIdCountTestcase(String projectId) {
		logger.info(" #DetailDesignDaoImpl.getAllLabCategoryByProjectIdCountTescase# ");
		List<LabCategory> returnList = new ArrayList<LabCategory>(); 
		try{
			StringBuffer sb = new StringBuffer();
			sb.append(" select parent.*, count(child.testcase_id) no_of_child  \n");
			sb.append("  from   project_labCategory parent\n");
			sb.append(" left join project_testcase child   on child.lab_category_id = parent.lab_category_id \n");
			sb.append("  where parent.project_id="+projectId +" \n");
			sb.append("  group by parent.lab_category_id \n");
			sb.append("  order by parent.lab_category_id  \n");			
			 	
			logger.info(" ## getAllLabCategoryByProjectIdCountTestcase sql:"+sb.toString());
			returnList = this.jdbcTemplate.query(	sb.toString(),new LabCategoryCountMapper());
		} catch(Exception ex){
			ex.printStackTrace();
		}	
		return returnList;
	}
	
	
	
	@Override
	public void update(LabCategory labCategory) {
		logger.info(" #LabCategoryDaoImpl.update labCategory: "+BeanUtils.getBeanString(labCategory));
		this.jdbcTemplate.update(
				"update  project_labCategory set name=?   where lab_category_id=? ",
				labCategory.getName()  ,labCategory.getLabCategoryId());
 
	}
 
	@Override
	public void create(LabCategory labCategory) {
		logger.info(" #LabCategoryDaoImpl.create # ");
		
		final String businessCode = projectUtil.getLabCategoryNo();
		
		final LabCategory finalLabCategory = labCategory;
		KeyHolder keyHolder = new GeneratedKeyHolder();  
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into project_labCategory (code, name,project_id) values (?, ?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,businessCode);
				ps.setString(2,finalLabCategory.getName());
				ps.setLong(3,finalLabCategory.getProjectId());		 			
				return ps;  
				}
			}, 	keyHolder);
 	
		Long returnid =  keyHolder.getKey().longValue();
	 	
	}

	
	public PagingBean getByOffset(PagingBean pagingBean) {
	 
		LabCategory labCategory = (LabCategory)pagingBean.get("labCategory");		
		List<LabCategory> returnList = new ArrayList<LabCategory>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  project_labCategory  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(labCategory.getCode())){
			sqltotalsb.append(" and r.code like  '%"+labCategory.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(labCategory.getName())){
			sqltotalsb.append(" and r.name like  '%"+labCategory.getName().trim()+"%'");
		} 
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		

		
		StringBuffer sb = new StringBuffer();
		sb.append(" select *  \n");
		sb.append("  from project_labCategory r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(labCategory.getCode())){
			sb.append(" and r.code like  '%"+labCategory.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(labCategory.getName())){
			sb.append(" and r.name like  '%"+labCategory.getName().trim()+"%'");
		} 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo()); 
		
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(sql,new LabCategoryMapper());
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	

	@Override
	public LabCategory getLabCategoryById(String labCategoryId,String projectId) {	
		logger.info(" ### getLabCategoryById labCategoryId:"+labCategoryId);
		
		LabCategory labCategory  = null;
		
		if (!StringUtils.hasLength(labCategoryId)) {
			String sql= "select *  from project_labCategory where project_id = ?";
			List<LabCategory> labCategoryList  = this.jdbcTemplate.query(	sql,	new Object[]{projectId},new LabCategoryMapper());
			if(labCategoryList!=null&&labCategoryList.size()>0){
				labCategory = labCategoryList.get(0);
			}
		}else{
			String sql= "select *  from project_labCategory where lab_category_id = ?";
			labCategory = this.jdbcTemplate.queryForObject(	sql,	new Object[]{labCategoryId},new LabCategoryMapper());
		} 
		if(labCategory!=null){			
			String sql ="  select * from project_lab r  where r.lab_category_id="+labCategory.getLabCategoryId() ;	
			List<Lab> useCaseReturnList = this.jdbcTemplate.query(	sql,new LabMapper());
			labCategory.setLabList(useCaseReturnList);
			
			for(Lab ucTmp:useCaseReturnList){
				String labId = ucTmp.getLabId()+"";
				
				// Get Detail Design belong to labId 
				
				String sqlDetailDesing ="  select * from project_detail_design r  where r.lab_category_id="+labCategory.getLabCategoryId() ;	
				
				
				
				
			}
		}
		 
		return labCategory;
	}
	
	@Override
	public LabCategory getLabCategoryandDetailDesignById(String labCategoryId,String projectId) {			
		logger.info(" ### getLabCategoryandDetailDesignById labCategoryId:"+labCategoryId);
		LabCategory labCategory  = null;		
		if (!StringUtils.hasLength(labCategoryId)) {
			String sql= "select *  from project_labCategory where project_id = ?";
			List<LabCategory> labCategoryList  = this.jdbcTemplate.query(	sql,	new Object[]{projectId},new LabCategoryMapper());
			if(labCategoryList!=null&&labCategoryList.size()>0){
				labCategory = labCategoryList.get(0);
			}
		}else{
			String sql= "select *  from project_labCategory where lab_category_id = ?";
			labCategory = this.jdbcTemplate.queryForObject(	sql,	new Object[]{labCategoryId},new LabCategoryMapper());
		} 
		
 
		
		return labCategory;
	}	

	@Override
	public LabCategory getLabCategoryandTestCaseById(String labCategoryId,String projectId) {			
		logger.info(" ### getLabCategoryandDetailDesignById labCategoryId:"+labCategoryId);
		LabCategory labCategory  = null;		
		if (!StringUtils.hasLength(labCategoryId)) {
			String sql= "select *  from project_labCategory where project_id = ?";
			List<LabCategory> labCategoryList  = this.jdbcTemplate.query(	sql,	new Object[]{projectId},new LabCategoryMapper());
			if(labCategoryList!=null&&labCategoryList.size()>0){
				labCategory = labCategoryList.get(0);
			}
		}else{
			String sql= "select *  from project_labCategory where lab_category_id = ?";
			labCategory = this.jdbcTemplate.queryForObject(	sql,	new Object[]{labCategoryId},new LabCategoryMapper());
		} 
		
 
		
		return labCategory;
	}	

	
	@Override
	public void deleteLabCategoryById(String labCategoryId) {	 
		this.jdbcTemplate.update(" delete from  project_labCategory where lab_category_id ="+labCategoryId);	
		this.jdbcTemplate.update(" delete from  project_lab where lab_category_id ="+labCategoryId); 
		 	
	}
	
 
	@Override
	public boolean isLabCategoryAlreadyUsege(String labCategoryId) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from buckwalabCategory labCategory inner join buckwagrouplabCategory grouplabCategory " +
					"on (labCategory.lab_category_id = grouplabCategory.lab_category_id)	where labCategory.lab_category_id ="+labCategoryId;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	private class LabCategoryMapper implements RowMapper<LabCategory> {   						
        @Override
		public LabCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        	LabCategory domain = new LabCategory();
        	domain.setLabCategoryId(rs.getLong("lab_category_id"));
        	domain.setCode(rs.getString("code"));
        	domain.setName(rs.getString("name"));
        	domain.setDetail(rs.getString("detail"));        	 
        	domain.setProjectId(rs.getLong("project_id"));        	
		return domain;
		} 
    } 
	
	private class LabCategoryCountMapper implements RowMapper<LabCategory> {   						
        @Override
		public LabCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        	LabCategory domain = new LabCategory();
        	domain.setLabCategoryId(rs.getLong("lab_category_id"));
        	domain.setCode(rs.getString("code"));
        	domain.setName(rs.getString("name"));
        	domain.setDetail(rs.getString("detail"));        	 
        	domain.setProjectId(rs.getLong("project_id"));       
        	domain.setChildCount(rs.getLong("no_of_child"));  
		return domain;
		} 
    } 
	
	private class LabMapper implements RowMapper<Lab> {   						
        @Override
		public Lab mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Lab domain = new Lab();
        	domain.setLabId(rs.getLong("lab_id"));
        	logger.info(" ### rs lab_id:"+rs.getLong("lab_id"));
        	domain.setCode(rs.getString("code"));
        	domain.setName(rs.getString("name"));
        	domain.setSummary(rs.getString("summary"));
        	domain.setActor(rs.getString("actor"));
        	domain.setBasicFlow(rs.getString("basic_flow"));
        	domain.setAlternateFlow(rs.getString("alternate_flow"));
        	//domain.setAlternateFlow("test \n test1 <br> test3 ");
        	domain.setProjectId(rs.getLong("project_id"));
        	domain.setLabCategoryId(rs.getLong("lab_category_id")); 
		return domain;
		} 
    } 
	
 
}
