package com.buckwa.dao.intf.project;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Project;
import com.buckwa.domain.project.Team;

public interface ProjectTeamDao {	
 
	public Team getMappingByUserName(String username,String projectId);
	
	public Team mapProjectTeam(Team team);
	public PagingBean getTeamByOffset(PagingBean pagingBean);	
	
	
	public Project getProjectTeamByProjectId(String projectId);
	
}
