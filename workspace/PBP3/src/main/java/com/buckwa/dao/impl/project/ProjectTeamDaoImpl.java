package com.buckwa.dao.impl.project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.buckwa.dao.intf.project.ProjectTeamDao;
import com.buckwa.domain.admin.Group;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Project;
import com.buckwa.domain.project.Team;
import com.buckwa.web.controller.admin.RoleManagementController;


@Repository("projectTeamDao")
public class ProjectTeamDaoImpl implements ProjectTeamDao{
	private static Logger logger = LoggerFactory.getLogger(ProjectTeamDaoImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Team getMappingByUserName(String username,String projectId) {
		// TODO Auto-generated method stub
		
		logger.info(" ## getMappingByUserName  username:"+username+" projectId:"+projectId);
		Team teamReturn = new Team();
		String getProjectGroup = " select * from buckwagroup r   where r.group_name like '%PROJECT%'";
		List<Group> teamGrouupList = new ArrayList<Group>(); 	 
		teamGrouupList = this.jdbcTemplate.query(
				getProjectGroup,
				new RowMapper<Group>() {
				public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
					Group domain = new Group(); 					
					domain.setGroupId(rs.getString("group_id"))	;	
					domain.setGroupName(rs.getString("group_name"));
				return domain;
				}
				});		
		
		teamReturn.setGroupList(teamGrouupList);
		
		if(teamReturn!=null){
			String sqlGroup = " select bsg.group_id from buckwausergroup bsg  where bsg.username ='"+username+"' and project_id="+projectId;
			logger.info(" sqlGroup:"+sqlGroup);		
			List<String> groupStrList = this.jdbcTemplate.query(
					sqlGroup,
					new RowMapper<String>() {
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {					
						String groupstr = rs.getString("group_id")	;	
					return groupstr;
					}
					});		
			teamReturn.setGroups(groupStrList.toArray(new String[0]));
		}
		

		return teamReturn;
	}
	
	

	@Override
	public Team mapProjectTeam(Team team) {
 
		
	 String deletuserGroup ="delete u.* from buckwausergroup u left join buckwagroup g on (g.group_id = u.group_id) where  u.username='"+team.getUsername()+"' and project_id ="+team.getProjectId(); 
 			 
	 this.jdbcTemplate.update(deletuserGroup);
		
 	 this.jdbcTemplate.update(deletuserGroup);	
 	  
		String []groupIdArray = team.getGroups(); 
		if(groupIdArray!=null&&groupIdArray.length>0){							
			for(int i=0;i<groupIdArray.length;i++){
				String groupIdStr = groupIdArray[i];
				
			  this.jdbcTemplate.update(	"insert into buckwausergroup (username, group_id,project_id) values (?, ?,?)", team.getUsername(), groupIdStr, team.getProjectId());					
			}
			
			 String sqlIsUserExist = " select count(*) as total_item  from  project_user_mapping where user_name= '"+team.getUsername()+"'  and project_id ="+team.getProjectId(); 
			
			 
			 int  rows_totalItem = jdbcTemplate.queryForObject(sqlIsUserExist,Integer.class); 
			 if(rows_totalItem>0){
				 logger.info(" ##### Found User aready map ,Do nothing ");	
			 }else{
				 logger.info(" ##### User not map , map new user to project");			 
				 this.jdbcTemplate.update(	" insert into project_user_mapping (user_name, project_id) values (?, ?)", team.getUsername(), team.getProjectId());
			 }			
			
		}else{
			
			String deleteuserprojectmap =" delete from project_user_mapping where user_name='"+team.getUsername()+"' and project_id ="+team.getProjectId();
			 this.jdbcTemplate.update(deleteuserprojectmap);
		} 
		 
		Team teamReturn = getMappingByUserName(team.getUsername(),team.getProjectId()+""); 
		
		return teamReturn;
	}
	
	
	@Override
	public PagingBean getTeamByOffset(PagingBean pagingBean) {
		Team team = (Team)pagingBean.get("team");	
		List<Team> returnList = new ArrayList<Team>();	
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("  select count(*) as total_item  from  buckwauser u  ");
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(team.getUsername())){
			sqltotalsb.append(" and u.username like '%"+team.getUsername().trim()+"%'");
		}
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	 
 
		
		StringBuffer sb = new StringBuffer();
		sb.append("   select  u.username  from buckwauser u ");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(team.getUsername())){
			sb.append(" and u.username like '%"+team.getUsername().trim()+"%'");
		}
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
 		
		returnList = this.jdbcTemplate.query(
				sb.toString(),
				new RowMapper<Team>() {
				public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
					Team domain = new Team();					 
					domain.setUsername(rs.getString("username"));
				 					 	
				return domain;
				}
				});

		pagingBean.setCurrentPageItem(returnList);

	return pagingBean;
	}



	@Override
	public Project getProjectTeamByProjectId(String projectId) {
		Project returnVal = null;
	 try{		 
			Project project = this.jdbcTemplate.queryForObject(
					"select *  from project where project_id = ?",
					new Object[]{projectId},
					new RowMapper<Project>() {
					public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
						Project domain = new Project();
						domain.setProjectId(rs.getLong("project_id"));
						domain.setProjectName(rs.getString("project_name"));					 		
						domain.setEnable(rs.getBoolean("enable"));	
					return domain;
					}
					});						
			if(project!=null){		
				
			// Load Team
				  
			}		 
	 }catch(Exception ex){
		 ex.printStackTrace();
	 }	 
	 return returnVal;
	} 
	
  
}
