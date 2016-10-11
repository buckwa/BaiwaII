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
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.project.ProjectDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Project;
import com.buckwa.util.BeanUtils;
import com.buckwa.web.controller.admin.RoleManagementController;


@Repository("projectDao")
public class ProjectDaoImpl implements ProjectDao{
	private static Logger logger = LoggerFactory.getLogger(ProjectDaoImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public Project getProject(Project project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Project project) {
		logger.info(" #ProjectDaoImpl.update project: "+BeanUtils.getBeanString(project));
		
		// Step 1 Update 
		this.jdbcTemplate.update(
				"update project set project_name=?   where project_id=? ",
				project.getProjectName() ,project.getProjectId());

  /*
		
		this.jdbcTemplate.update(" delete from  projectrole where project_id ="+project.getProjectId());			
		// Step 3 Cretate new Project Role mapping
		String []authorizes = project.getAuthorizes();
		if(authorizes!=null&&authorizes.length>0){ 
			for(int i=0;i<authorizes.length;i++){
				String roleIdStr = authorizes[i];				
			  this.jdbcTemplate.update(
						"insert into projectrole (project_id, role_id) values (?, ?)",
						new Long(project.getProjectId()),new Long( roleIdStr));					
			}			
		}	
*/
	}


	@Override
	public void create(Project project) {
		/*
		int xx =this.jdbcTemplate.update(
				"insert into project (project_name,enable) values (?,?)",
				project.getProjectName(),project.isEnable());	
		
		*/
		final Project finalproject = project;
		KeyHolder keyHolder = new GeneratedKeyHolder();  
		
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into project (project_name,enable) values (?,?) " +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,finalproject.getProjectName());
				ps.setBoolean(2,true);		 	 			
				return ps;  
				}
			}, 	keyHolder);
 	
		Long returnid =  keyHolder.getKey().longValue();	
		
		
		int xx =this.jdbcTemplate.update(
				"insert into project_vision (project_id) values (?)",
				returnid);	
		
	/*
		String []authorizes = project.getAuthorizes();
		if(authorizes!=null&&authorizes.length>0){
			
			// Create Project - Role Mapping
			Long projectId = this.jdbcTemplate.queryForObject(
					"select project_id from buckwaproject where project_name = ?",
					new Object[]{project.getProjectName()}, Long.class);	
			
			logger.info(" projectId: "+projectId);	
			
			for(int i=0;i<authorizes.length;i++){
				String roleIdStr = authorizes[i];
				
			  this.jdbcTemplate.update(
						"insert into buckwaprojectrole (project_id, role_id) values (?, ?)",
						projectId, roleIdStr);					
			}
			
		}
		
		*/
	    
	}

	@Override
	public List<Project> getAllProject() {
		List<Project> returnList = new ArrayList<Project>(); 
		String sql ="  select  g.project_id , g.project_name from project g  " ;	
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Project>() {
				public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
					Project domain = new Project(); 					
					domain.setProjectId(rs.getLong("project_id"));		
					domain.setProjectName(rs.getString("project_name"));
				return domain;
				}
				});

	return returnList;
	}
	
 
	
	@Override
	public List<Project> getAllProjectByUser(String userName) {
		List<Project> returnList = new ArrayList<Project>(); 
		//String sql ="  select  g.project_id , g.project_name from project g  " ;	
		StringBuffer sb =new StringBuffer();
		sb.append(" select  g.project_id , g.project_name from project g \n");
		sb.append(" inner join project_user_mapping m on (g.project_id = m.project_id) \n");
		sb.append(" where m.user_name='"+userName+"'");
		 	 
		returnList = this.jdbcTemplate.query(
				sb.toString(),
				new RowMapper<Project>() {
				public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
					Project domain = new Project(); 					
					domain.setProjectId(rs.getLong("project_id"));		
					domain.setProjectName(rs.getString("project_name"));
				return domain;
				}
				});

	return returnList;
	}
	
	

	@Override
	public Project getProjectById(String id) {
		logger.info(" ### getProjectById id :"+id);
		Project project = this.jdbcTemplate.queryForObject(
				"select *  from project where project_id = ?",
				new Object[]{id},
				new RowMapper<Project>() {
				public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
					Project domain = new Project();
					domain.setProjectId(rs.getLong("project_id"));
					domain.setProjectName(rs.getString("project_name"));					 		
					domain.setEnable(rs.getBoolean("enable"));	
				return domain;
				}
				});
		
		// Load Authorize
		/*
		List<String> roleIdStr = this.jdbcTemplate.query(
				//" select r.role_name from  buckwaprojectrole gr left outer join buckwarole r  ON r.role_id = gr.role_id  where gr.project_id= ?",
				"select gr.role_id from  buckwaprojectrole gr  where  gr.project_id= ?",
				new Object[]{id},
				new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String roleIdStr =rs.getString("role_id");
				return roleIdStr;
			 
				}
				});
		
		project.setAuthorizes((String[])roleIdStr.toArray(new String[0]));
			*/
		
		logger.info(" ### getProjectById Project return :"+BeanUtils.getBeanString(project));
		return project;
	}	

	
	
	@Override
	public PagingBean getAllProjectByOffset(PagingBean pagingBean) {
 
		Project project = (Project)pagingBean.get("project");
		
 		List<Project> returnList = new ArrayList<Project>();	
 		
 		StringBuffer sqltotalIsb = new StringBuffer();
 		sqltotalIsb.append("   select count(*) as total_item  from  project g  where 1=1 \n");	
		if(StringUtils.hasText(project.getProjectName())){
			sqltotalIsb.append(" and g.project_name like  '%"+project.getProjectName().trim()+"%'");
		}
		
		
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalIsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		
		StringBuffer sb = new StringBuffer();
		sb.append("  select g.project_id , g.project_name  ,g.enable from project g  where 1=1 \n");		 		
		if(StringUtils.hasText(project.getProjectName())){
			sb.append(" and g.project_name like  '%"+project.getProjectName().trim()+"%'");
		}
 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
	 
		returnList = this.jdbcTemplate.query(sb.toString(),
				new RowMapper<Project>() {
				public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
					Project domain = new Project();					
					domain.setProjectId(rs.getLong("project_id"));
					domain.setProjectName(rs.getString("project_name"));				 	
					domain.setEnable(rs.getBoolean("enable"));	
				return domain;
				}
				});
		pagingBean.setCurrentPageItem(returnList);

	return pagingBean;
	}
	
	@Override
	public void deleteProjectById(String projectId) {	 
		this.jdbcTemplate.update(" delete from  project where project_id ="+projectId);	 
		 this.jdbcTemplate.update(" delete from  project_vision where project_id ="+projectId);	
	}

	@Override
	public String getProjectIdFromProjectName(String projectName) {
		String returnstr = "";
		String sqlProjectId = " select project_id form project where project_name ='"+projectName+"'";
		 
		 
		logger.info(" sqlProjectId:"+sqlProjectId);		
		returnstr = this.jdbcTemplate.queryForObject(
				projectName,
				new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {					
					String str = rs.getString("project_id")	;	
				return str;
				}
				});	
		
		return returnstr;
	}
	
	@Override
	public boolean isProjectAlreadyUsege(String id) {
		 boolean returnValue = false;
		try{
			/*
			String sqltmp = "select count(*) as totalusage from project projectx inner join userproject projectrole " +
					"on (projectx.project_id = projectrole.project_id)	where projectx.project_id ="+id;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
			*/
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	@Override
	public boolean isProjectExist(String name) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalfound  from project t  where t.project_name='"+name+"'";
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
	public GrantedAuthority[] getGrantedAuthorityByUser(String userName,			String projectId) {
		GrantedAuthority [] projectAuthorities = getAuthorities(userName,projectId);
		 
		return projectAuthorities;
	}

	@Override
	public boolean isProjectExistForUpdate(String name,String id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalfound  from project t  where t.project_name='"+name+"' and t.project_id !="+id;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}	
	
	
	  private  GrantedAuthority [] getAuthorities(String username,String projectId) {   
	    	String sql ="";
	      	StringBuffer sb = new StringBuffer();
	    	sb.append("  select u.username , r.role_name as authorities \n") ;
	    	sb.append("  from buckwauser u   \n") ;
	    	sb.append("  left outer join buckwausergroup  ug on u.username = ug.username  \n") ;
	    	sb.append("  left outer join buckwagroup  g on ug.group_id = g.group_id  \n") ;
	    	sb.append("  left outer join buckwagrouprole  gr on g.group_id = gr.group_id   \n") ;
	    	sb.append("  left outer join buckwarole r   on gr.role_id = r.role_id  \n") ;
	    	sb.append("  where u.username= '"+username+"' and ug.project_id="+projectId); 
	    	
	    	logger.info(" getAuthorities :"+sb.toString());
	    	sql = sb.toString(); 
	    	
	    	List<GrantedAuthority> authList = findAllAuthority(sql);
	    	
	    	logger.info(" ## Found getAuthorities by project user:"+authList);
	    	return authList.toArray(new GrantedAuthority[] {});
	    } 
	    
	    public List<GrantedAuthority> findAllAuthority(String sql) {
	    	logger.info(" jdbcTemplate:"+jdbcTemplate);
	    	return jdbcTemplate.query( sql, new AuthorityrMapper());
		}
	    
		private class AuthorityrMapper implements RowMapper<GrantedAuthority> {
	    	public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
	    		//GrantedAuthorityImpl authority = new GrantedAuthorityImpl(rs.getString("authorities"));
	    		//return authority;
	    		
	    		return null;
	    	}
		}    
		
}
