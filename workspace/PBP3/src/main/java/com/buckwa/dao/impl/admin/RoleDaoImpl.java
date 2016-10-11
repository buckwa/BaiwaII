package com.buckwa.dao.impl.admin;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.admin.RoleDao;
import com.buckwa.domain.admin.Role;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.util.BeanUtils;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {
	private static Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Role> getAllRole() {
		List<Role> returnList = new ArrayList<Role>(); 
		String sql ="  select r.role_id , r.role_name, r.role_desc from buckwarole r  " ;	
		logger.info(" sql:"+sql);
		returnList = this.jdbcTemplate.query(sql,new RoleMapper());
		return returnList;
	}
	 
	
	@Override
	public Role getRole(Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Role role) {
		logger.info(" #RoleDaoImpl.update role: "+BeanUtils.getBeanString(role));
		this.jdbcTemplate.update(
				"update  buckwarole set role_name=?,role_desc=?   where role_id=? ",
				role.getRoleName(), role.getRoleDesc(),role.getRoleId()); 
 		
		String [] menuArray = role.getMenus();	
		if(menuArray!=null&&menuArray.length>0){				
			this.jdbcTemplate.update(	" delete from   buckwarolemenu   where role_id=?  ",		role.getRoleId() );	
			for(int i=0;i<menuArray.length;i++){
				String menuId = menuArray[i];	
				if(StringUtils.hasLength(menuId)){
					  this.jdbcTemplate.update(
								" insert into buckwarolemenu (menu_id, role_id) values (?, ?)",
								menuId, role.getRoleId());					
				}					
			}			
		}		
	}
/*
	@Override
	public void create(Role role) {
		logger.info(" #RoleDaoImpl.create # ");
		this.jdbcTemplate.update(
				"insert into buckwarole (role_name, role_desc,enable) values (?, ?,?)",
				role.getRoleName(), role.getRoleDesc(),1);		
		
		String [] menuArray = role.getMenus();	
		if(menuArray!=null&&menuArray.length>0){				 		
			for(int i=0;i<menuArray.length;i++){
				String menuId = menuArray[i];	
				if(StringUtils.hasLength(menuId)){
					  this.jdbcTemplate.update(
								"insert into buckwarolemenu (menu_id, role_id) values (?, ?)",
								menuId, role.getRoleId());					
				}					
			}			
		}	
	}
 */
	
	@Override
	public void create(Role role) {
		logger.info(" #RoleDaoImpl.create # ");		
		final Role finalRole = role;
		KeyHolder keyHolder = new GeneratedKeyHolder(); 		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into buckwarole (role_name, role_desc,enable) values (?, ?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalRole.getRoleName());
				ps.setString(2,finalRole.getRoleDesc());
				ps.setBoolean(3,true);		 			
				return ps;  
				}
			}, 	keyHolder); 	
		Long returnid =  keyHolder.getKey().longValue();		
		String [] menuArray = role.getMenus();	
		if(menuArray!=null&&menuArray.length>0){				 		
			for(int i=0;i<menuArray.length;i++){
				String menuId = menuArray[i];	
				if(StringUtils.hasLength(menuId)){
					  this.jdbcTemplate.update(
								"insert into buckwarolemenu (menu_id, role_id) values (?, ?)",
								menuId, returnid);					
				}					
			}			
		}	
	}

	
	public PagingBean getAllRoleByOffset(PagingBean pagingBean) {	 
		Role role = (Role)pagingBean.get("role");		
		List<Role> returnList = new ArrayList<Role>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  buckwarole  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(role.getRoleName())){
			sqltotalsb.append(" and r.role_name like  '%"+StringEscapeUtils.escapeSql(role.getRoleName().trim())+"%'");
		}
		if(StringUtils.hasText(role.getRoleDesc())){
			sqltotalsb.append(" and r.role_desc like  '%"+StringEscapeUtils.escapeSql(role.getRoleDesc().trim())+"%'");
		}
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" r.role_id , r.role_name, r.role_desc from buckwarole r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(role.getRoleName())){
			sb.append(" and r.role_name like  '%"+StringEscapeUtils.escapeSql(role.getRoleName().trim())+"%'");
		}
		if(StringUtils.hasText(role.getRoleDesc())){
			sb.append(" and r.role_desc like  '%"+StringEscapeUtils.escapeSql(role.getRoleDesc().trim())+"%'");
		}
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Role>() {
				public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
					Role role = new Role();
					//role.setRownum(rs.getString("rownum"));
					role.setRoleId(rs.getLong("role_id"));
					role.setRoleName(rs.getString("role_name"));
					role.setRoleDesc(rs.getString("role_desc"));				 
				return role;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	@Override
	public Role getRoleById(String roleId) {		 		
		String sql =" select *  from buckwarole where role_id = "+roleId;
		Role role = this.jdbcTemplate.queryForObject(sql,	new RoleMapper() );				
		if(role!=null){
	 			String sqlMenus = " select menu_id from buckwarolemenu t  where t.role_id ="+role.getRoleId()+"";						 	
				List<String> menuStrList = this.jdbcTemplate.query(	sqlMenus,
						new RowMapper<String>() {
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {					
							String menuIdStr = rs.getString("menu_id")	;	
						return menuIdStr;
						}
						});		
				role.setMenus(menuStrList.toArray(new String[0]));
			}			
		return role;
	}	

	
	@Override
	public void deleteRoleById(String roleId) {	 
		this.jdbcTemplate.update(" delete from  buckwarole where role_id ="+roleId);	
		this.jdbcTemplate.update(" delete from  buckwarolemenu where role_id ="+roleId);	
	}
	
	@Override
	public String getRoleIdFromRoleName(String roleName) {		
		String returnstr = "";
		String sqlRoleId = " select role_id form buckwarole where role_name ='"+StringEscapeUtils.escapeSql(roleName)+"'";
		 
		
		logger.info(" sqlRoleId:"+sqlRoleId);		
		returnstr = this.jdbcTemplate.queryForObject(
				roleName,
				new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {					
					String str = rs.getString("role_id")	;	
				return str;
				}
				});	
		
		return returnstr;
	}
	
	@Override
	public boolean isRoleAlreadyUsege(String roleId) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from buckwarole role inner join buckwagrouprole grouprole " +
					"on (role.role_id = grouprole.role_id)	where role.role_id ="+roleId;
			
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
	public boolean isRoleNameExist(String roleName) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalRole  from buckwarole t  where t.role_name='"+StringEscapeUtils.escapeSql(roleName)+"'";
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
	public boolean isRoleNameExistForUpdate(String roleName,Long id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalRole  from buckwarole t  where t.role_name='"+StringEscapeUtils.escapeSql(roleName)+"'  and t.role_id !="+id	;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}
	
	
	private class RoleMapper implements RowMapper<Role> {   						
        @Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			Role domain = new Role(); 					
			domain.setRoleId(rs.getLong("role_id"));		
			domain.setRoleName(rs.getString("role_name")); ;
			domain.setRoleDesc(rs.getString("role_desc"));	
		return domain;
    }
	}
	
 
}
