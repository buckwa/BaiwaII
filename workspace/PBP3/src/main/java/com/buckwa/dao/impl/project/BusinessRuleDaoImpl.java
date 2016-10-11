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

import com.buckwa.dao.intf.project.BusinessRuleDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.BusinessRule;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.project.ProjectUtil;

@Repository("businessRuleDao")
public class BusinessRuleDaoImpl implements BusinessRuleDao {
	private static Logger logger = LoggerFactory.getLogger(BusinessRuleDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ProjectUtil projectUtil;

	public List<BusinessRule> getAllBusinessRule() {
		List<BusinessRule> returnList = new ArrayList<BusinessRule>(); 
		String sql ="  select * from project_business_rule r  " ;	
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<BusinessRule>() {
				public BusinessRule mapRow(ResultSet rs, int rowNum) throws SQLException {
					BusinessRule domain = new BusinessRule(); 					
					domain.setBusinessRuleId(rs.getLong("business_rule_id"));	
					domain.setCode(rs.getString("code"));
					domain.setName(rs.getString("name"));
					domain.setDetail(rs.getString("detail"));
					domain.setProjectId(rs.getLong("project_id"));
				return domain;
				}
				});

	return returnList;
	}
	
	public List<BusinessRule> getAllBusinessRuleByProjectId(String projectId) {
		List<BusinessRule> returnList = new ArrayList<BusinessRule>(); 
		String sql ="  select * from project_business_rule r where project_id="+projectId ;	
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<BusinessRule>() {
				public BusinessRule mapRow(ResultSet rs, int rowNum) throws SQLException {
					BusinessRule domain = new BusinessRule(); 					
					domain.setBusinessRuleId(rs.getLong("business_rule_id"));	
					domain.setCode(rs.getString("code"));
					domain.setName(rs.getString("name"));
					domain.setDetail(rs.getString("detail"));
					domain.setProjectId(rs.getLong("project_id"));
				return domain;
				}
				});

	return returnList;
	}
	 
	
	@Override
	public void update(BusinessRule businessRule) {
		logger.info(" #BusinessRuleDaoImpl.update businessRule: "+BeanUtils.getBeanString(businessRule));
		this.jdbcTemplate.update(
				"update  project_business_rule set detail=?  ,name=? where business_rule_id=? ",
				businessRule.getDetail() ,businessRule.getName() ,businessRule.getBusinessRuleId());
 
	}
 
	@Override
	public void create(BusinessRule businessRule) {
		logger.info(" #BusinessRuleDaoImpl.create # ");
		
		final String businessCode = projectUtil.getBusinessRuleNo(businessRule.getProjectId());
		
		final BusinessRule finalBusinessRule = businessRule;
		KeyHolder keyHolder = new GeneratedKeyHolder();  
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into project_business_rule (code, detail,project_id,name) values (?, ?,?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,businessCode);
				ps.setString(2,finalBusinessRule.getDetail());
				ps.setLong(3,finalBusinessRule.getProjectId());		
				ps.setString(4, finalBusinessRule.getName());
				return ps;  
				}
			}, 	keyHolder);
 	
		Long returnid =  keyHolder.getKey().longValue();
	 	
	}

	
	public PagingBean getByOffset(PagingBean pagingBean) {
	 
		BusinessRule businessRule = (BusinessRule)pagingBean.get("businessRule");		
		List<BusinessRule> returnList = new ArrayList<BusinessRule>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  project_business_rule  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(businessRule.getCode())){
			sqltotalsb.append(" and r.code like  '%"+businessRule.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(businessRule.getDetail())){
			sqltotalsb.append(" and r.detail like  '%"+businessRule.getDetail().trim()+"%'");
		} 
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		

		
		StringBuffer sb = new StringBuffer();
		sb.append(" select *  \n");
		sb.append("  from project_business_rule r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(businessRule.getCode())){
			sb.append(" and r.code like  '%"+businessRule.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(businessRule.getDetail())){
			sb.append(" and r.detail like  '%"+businessRule.getDetail().trim()+"%'");
		} 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo()); 
		
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<BusinessRule>() {
				public BusinessRule mapRow(ResultSet rs, int rowNum) throws SQLException {
					BusinessRule domain = new BusinessRule();
					domain.setBusinessRuleId(rs.getLong("businessRule_id"));	
					domain.setCode(rs.getString("code"));
					domain.setName(rs.getString("name"));
					domain.setDetail(rs.getString("detail"));	
					domain.setProjectId(rs.getLong("project_id"));
				return domain;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	

	@Override
	public BusinessRule getBusinessRuleById(String businessRuleId) {
		 
		BusinessRule businessRule = this.jdbcTemplate.queryForObject(
				"select *  from project_business_rule where business_rule_id = ?",
				new Object[]{businessRuleId},
				new RowMapper<BusinessRule>() {
				public BusinessRule mapRow(ResultSet rs, int rowNum) throws SQLException {
					BusinessRule domain = new BusinessRule();
					domain.setBusinessRuleId(rs.getLong("business_rule_id"));	
					domain.setCode(rs.getString("code"));
					domain.setName(rs.getString("name"));
					domain.setDetail(rs.getString("detail"));	
					domain.setProjectId(rs.getLong("project_id"));
				return domain;
				}
				});
		
 
			
		return businessRule;
	}	

	
	@Override
	public void deleteBusinessRuleById(String businessRuleId) {	 
		this.jdbcTemplate.update(" delete from  project_business_rule where business_rule_id ="+businessRuleId);	
		 	
	}
	
 
	@Override
	public boolean isBusinessRuleAlreadyUsege(String businessRuleId) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from buckwabusinessRule businessRule inner join buckwagroupbusinessRule groupbusinessRule " +
					"on (businessRule.businessRule_id = groupbusinessRule.businessRule_id)	where businessRule.businessRule_id ="+businessRuleId;
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
