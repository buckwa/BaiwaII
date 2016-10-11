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

import com.buckwa.dao.intf.project.ActorDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Actor;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.project.ProjectUtil;

@Repository("actorDao")
public class ActorDaoImpl implements ActorDao {
	private static Logger logger = LoggerFactory.getLogger(ActorDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ProjectUtil projectUtil;

	public List<Actor> getAllActor() {
		List<Actor> returnList = new ArrayList<Actor>(); 
		String sql ="  select * from project_actor r  " ;	
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Actor>() {
				public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
					Actor domain = new Actor(); 					
					domain.setActorId(rs.getLong("actor_id"));	
					domain.setCode(rs.getString("code"));
					domain.setName(rs.getString("name"));
					domain.setDetail(rs.getString("detail"));
					domain.setProjectId(rs.getLong("project_id"));
				return domain;
				}
				});

	return returnList;
	}
	
	public List<Actor> getAllActorByProjectId(String projectId) {
		List<Actor> returnList = new ArrayList<Actor>(); 
		String sql ="  select * from project_actor r where project_id="+projectId ;	
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Actor>() {
				public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
					Actor domain = new Actor(); 					
					domain.setActorId(rs.getLong("actor_id"));	
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
	public void update(Actor actor) {
		logger.info(" #ActorDaoImpl.update actor: "+BeanUtils.getBeanString(actor));
		this.jdbcTemplate.update(
				"update  project_actor set detail=? ,name=?  where actor_id=? ",
				actor.getDetail() ,actor.getName() ,actor.getActorId());
 
	}
 
	@Override
	public void create(Actor actor) {
		logger.info(" #ActorDaoImpl.create # ");
		
		final String businessCode = projectUtil.getActorNo(actor.getProjectId());
		
		final Actor finalActor = actor;
		KeyHolder keyHolder = new GeneratedKeyHolder();  
		jdbcTemplate.update(new PreparedStatementCreator() {  
			public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
				PreparedStatement ps = connection.prepareStatement("" +						
						"  insert into project_actor (code, detail,project_id,name) values (?, ?,?,?)" +
					 "", Statement.RETURN_GENERATED_KEYS);   
				ps.setString(1,businessCode);
				ps.setString(2,finalActor.getDetail());
				ps.setLong(3,finalActor.getProjectId());	
				ps.setString(4,finalActor.getName());
				return ps;  
				}
			}, 	keyHolder);
 	
		Long returnid =  keyHolder.getKey().longValue();
	 	
	}

	
	public PagingBean getByOffset(PagingBean pagingBean) {
	 
		Actor actor = (Actor)pagingBean.get("actor");		
		List<Actor> returnList = new ArrayList<Actor>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  project_actor  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(actor.getCode())){
			sqltotalsb.append(" and r.code like  '%"+actor.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(actor.getDetail())){
			sqltotalsb.append(" and r.detail like  '%"+actor.getDetail().trim()+"%'");
		} 
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		

		
		StringBuffer sb = new StringBuffer();
		sb.append(" select *  \n");
		sb.append("  from project_actor r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(actor.getCode())){
			sb.append(" and r.code like  '%"+actor.getCode().trim()+"%'");
		} 
		if(StringUtils.hasText(actor.getDetail())){
			sb.append(" and r.detail like  '%"+actor.getDetail().trim()+"%'");
		} 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo()); 
		
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Actor>() {
				public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
					Actor domain = new Actor();
					domain.setActorId(rs.getLong("actor_id"));	
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
	public Actor getActorById(String actorId) {
		 
		Actor actor = this.jdbcTemplate.queryForObject(
				"select *  from project_actor where actor_id = ?",
				new Object[]{actorId},
				new RowMapper<Actor>() {
				public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
					Actor domain = new Actor();
					domain.setActorId(rs.getLong("actor_id"));	
					domain.setCode(rs.getString("code"));
					domain.setName(rs.getString("name"));
					domain.setDetail(rs.getString("detail"));	
					domain.setProjectId(rs.getLong("project_id"));
				return domain;
				}
				});
		
 
			
		return actor;
	}	

	
	@Override
	public void deleteActorById(String actorId) {	 
		this.jdbcTemplate.update(" delete from  project_actor where actor_id ="+actorId);	
		 	
	}
	
 
	@Override
	public boolean isActorAlreadyUsege(String actorId) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from buckwaactor actor inner join buckwagroupactor groupactor " +
					"on (actor.actor_id = groupactor.actor_id)	where actor.actor_id ="+actorId;
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
