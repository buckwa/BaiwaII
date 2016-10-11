package com.buckwa.dao.intf.project;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Actor;

public interface ActorDao {
	

 
	public void update(Actor actor);
	public void create(Actor actor);
	public PagingBean getByOffset(PagingBean pagingBean);
	public List<Actor> getAllActor();
	public List<Actor> getAllActorByProjectId(String projectId);
	
	public Actor getActorById(String actorId);
	public void deleteActorById(String actorId);
 
	public boolean isActorAlreadyUsege(String actorId);
	 
 
	
}
