package com.buckwa.dao.impl.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.admin.GroupDao;
import com.buckwa.domain.admin.Group;
import com.buckwa.domain.admin.Menu;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.util.BeanUtils;


@Repository("groupDao")
public class GroupDaoImpl implements GroupDao{
	private static Logger logger = LoggerFactory.getLogger(GroupDaoImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public Group getGroup(Group group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Group group) {
		logger.info("GroupDaoImpl.update(Group group)");
		
		// Step 1 Update 
		this.jdbcTemplate.update(
				"update  buckwagroup set group_name=?,group_desc=?   where group_id=? ",
				group.getGroupName(), group.getGroupDesc(),group.getGroupId());

		
		//Step 2  Delete Group Role Mapping
		
		this.jdbcTemplate.update(" delete from  buckwagrouprole where group_id ="+group.getGroupId());	
		
		// Step 3 Cretate new Group Role mapping
		String []authorizes = group.getAuthorizes();
		if(authorizes!=null&&authorizes.length>0){ 
			for(int i=0;i<authorizes.length;i++){
				String roleIdStr = authorizes[i];				
			  this.jdbcTemplate.update(
						"insert into buckwagrouprole (group_id, role_id) values (?, ?)",
						new Long(group.getGroupId()),new Long( roleIdStr));					
			}			
		}	

	}


	@Override
	public void create(Group group) {
		logger.info("GroupDaoImpl.create(Group group)");
		int xx =this.jdbcTemplate.update(
				"insert into buckwagroup (group_name, group_desc,enable) values (?, ?,?)",
				group.getGroupName(), group.getGroupName(),group.isEnable());	
		
		
	
		String []authorizes = group.getAuthorizes();
		if(authorizes!=null&&authorizes.length>0){
			
			// Create Group - Role Mapping
			Long groupId = this.jdbcTemplate.queryForObject(
					"select group_id from buckwagroup where group_name = ?",
					new Object[]{group.getGroupName()}, Long.class);	
			
			logger.info(" groupId: "+groupId);	
			
			for(int i=0;i<authorizes.length;i++){
				String roleIdStr = authorizes[i];
				
			  this.jdbcTemplate.update(
						"insert into buckwagrouprole (group_id, role_id) values (?, ?)",
						groupId, roleIdStr);					
			}
			
		}
	    
	}

	@Override
	public List<Group> getAllGroup() {
		logger.info("GroupDaoImpl.getAllGroup()");
		List<Group> returnList = new ArrayList<Group>(); 
		String sql ="  select  g.group_id , g.group_name, g.group_desc from buckwagroup g  " ;	
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Group>() {
				public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
					Group domain = new Group(); 					
					domain.setGroupId(rs.getString("group_id"));		
					domain.setGroupName(rs.getString("group_name"));
				return domain;
				}
				});

	return returnList;
	}
	
	@Override
	public List<Menu> getAllMenu() {
		logger.info("GroupDaoImpl.getAllMenu()");
		List<Menu> returnList = new ArrayList<Menu>(); 
		String sql ="  select * from buckwamenu t  order by order_no" ;	
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Menu>() {
				public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
					Menu domain = new Menu(); 					
					domain.setMenuId(rs.getLong("menu_id"));
					domain.setCode(rs.getString("code"));
					domain.setName(rs.getString("name"));
					domain.setUrl(rs.getString("url"));
					domain.setParentId(rs.getLong("parent_menu_id"));
				return domain;
				}
				});

	return returnList;
	}
	
	@Override
	public Group getGroupById(String id) {
		logger.info("GroupDaoImpl.getGroupById(String id)");
		Group group = this.jdbcTemplate.queryForObject(
				"select *  from buckwagroup where group_id = ?",
				new Object[]{id},
				new RowMapper<Group>() {
				public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
					Group domain = new Group();
					domain.setGroupId(rs.getString("group_id"));
					domain.setGroupName(rs.getString("group_name"));
					domain.setGroupDesc(rs.getString("group_desc"));		
					domain.setEnable(rs.getBoolean("enable"));	
				return domain;
				}
				});
		
		// Load Authorize
		
		List<String> roleIdStr = this.jdbcTemplate.query(
				//" select r.role_name from  buckwagrouprole gr left outer join buckwarole r  ON r.role_id = gr.role_id  where gr.group_id= ?",
				"select gr.role_id from  buckwagrouprole gr  where  gr.group_id= ?",
				new Object[]{id},
				new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String roleIdStr =rs.getString("role_id");
				return roleIdStr;
			 
				}
				});
		
		group.setAuthorizes((String[])roleIdStr.toArray(new String[0]));
			
		return group;
	}	

	
	
	@Override
	public PagingBean getAllGroupByOffset(PagingBean pagingBean) {
		logger.info("GroupDaoImpl.getAllGroupByOffset(PagingBean pagingBean)");
		Group group = (Group)pagingBean.get("group");
		
 		List<Group> returnList = new ArrayList<Group>();	
 		
 		StringBuffer sqltotalIsb = new StringBuffer();
 		sqltotalIsb.append("   select count(*) as total_item  from  buckwagroup g  where 1=1 \n");	
		if(StringUtils.hasText(group.getGroupName())){
			sqltotalIsb.append(" and g.group_name like  '%"+StringEscapeUtils.escapeSql(group.getGroupName().trim())+"%'");
		}
		
		
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalIsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		
		StringBuffer sb = new StringBuffer();
		sb.append("  select g.group_id , g.group_name, g.group_desc ,g.enable from buckwagroup g  where 1=1 \n");		 		
		if(StringUtils.hasText(group.getGroupName())){
			sb.append(" and g.group_name like  '%"+StringEscapeUtils.escapeSql(group.getGroupName().trim())+"%'");
		}
 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
	 
		returnList = this.jdbcTemplate.query(sb.toString(),
				new RowMapper<Group>() {
				public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
					Group domain = new Group();					
					domain.setGroupId(rs.getString("group_id"));
					domain.setGroupName(rs.getString("group_name"));
					domain.setGroupDesc(rs.getString("group_desc"));		
					domain.setEnable(rs.getBoolean("enable"));	
				return domain;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

	return pagingBean;
	}
	
	@Override
	public void deleteGroupById(String groupId) {	 
		logger.info("GroupDaoImpl.deleteGroupById(String groupId)");
		this.jdbcTemplate.update(" delete from  buckwagroup where group_id ="+groupId);	
		this.jdbcTemplate.update(" delete from  buckwagrouprole where group_id ="+groupId);	
	}

	@Override
	public String getGroupIdFromGroupName(String groupName) {
		logger.info("GroupDaoImpl.getGroupIdFromGroupName(String groupName)");
		String returnstr = "";
		String sqlGroupId = " select group_id from buckwagroup where group_name ='"+StringEscapeUtils.escapeSql(groupName)+"'";
		 
		 
		logger.info(" sqlGroupId:"+sqlGroupId);		
		returnstr = this.jdbcTemplate.queryForObject( sqlGroupId,
				new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {					
					String str = rs.getString("group_id")	;	
				return str;
				}
				});	
		
		return returnstr;
	}
	
	@Override
	public boolean isGroupAlreadyUsege(String id) {
		logger.info("GroupDaoImpl.isGroupAlreadyUsege(String id)");
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from buckwagroup groupx inner join buckwausergroup grouprole " +
					"on (groupx.group_id = grouprole.group_id)	where groupx.group_id ="+id;
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
	public boolean isGroupExist(String name) {
		logger.info("GroupDaoImpl.isGroupExist(String name)");
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalfound  from buckwagroup t  where t.group_name='"+StringEscapeUtils.escapeSql(name)+"'";
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
	public boolean isGroupExistForUpdate(String name,String id) {
		logger.info("GroupDaoImpl.isGroupExistForUpdate(String name,String id)");
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalfound  from buckwagroup t  where t.group_name='"+StringEscapeUtils.escapeSql(name)+"' and t.group_id !="+id;
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
}
