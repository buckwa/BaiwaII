package com.buckwa.dao.impl.webboard;

import java.sql.Connection;
import java.sql.Date;
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

import com.buckwa.dao.intf.webboard.WebboardTopicDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Vision;
import com.buckwa.domain.webboard.Message;
import com.buckwa.domain.webboard.Topic;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.project.ProjectConstant;
import com.buckwa.util.webboard.WebboardConstants;
import com.sun.jmx.snmp.Timestamp;

@Repository("webboardTopicDao")
public class WebboardTopicDaoImpl implements WebboardTopicDao {
	private static Logger logger = LoggerFactory.getLogger(WebboardTopicDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Topic> getAll() {
		List<Topic> returnList = new ArrayList<Topic>(); 
		String sql ="  select * from webboard_topic r  " ;	
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Topic>() {
				public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
					Topic domain = new Topic(); 					
					domain.setTopicId(rs.getLong("topic_id"));	
				 
				return domain;
				}
				});

		logger.info("  getAll :"+returnList);
	return returnList;
	}
	 
	@Override
	public void update(Topic topic)throws Exception  {
		logger.info(" #TopicDaoImpl.update topic: "+BeanUtils.getBeanString(topic));
		try{	
		this.jdbcTemplate.update(
				"update  webboard_topic set detail=?   where topic_id=? ",
				topic.getTopicDetail()  ,topic.getTopicId());
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}	
	}

	@Override
	public void create(Topic topic) throws Exception {
		logger.info(" #TopicDaoImpl.create # ");		
	try{		
		this.jdbcTemplate.update(
				"insert into webboard_topic (  header, detail,status,category_id ,create_by,create_date) values ( ?,?,? ,?,?,?) ",
				  topic.getTopicHeader(),topic.getTopicDetail() ,topic.getStatus(),topic.getCategoryId(),topic.getCreateBy(),new java.sql.Timestamp(System.currentTimeMillis()));			
	}catch(Exception ex){
		ex.printStackTrace();
		throw ex;
	}	
	}

	
	@Override
	public Topic replyMessage(Message message) throws Exception {
		logger.info(" #TopicDaoImpl.replyMessage  message: "+BeanUtils.getBeanString(message));	
		Topic returnTopic =null;
	try{		
		this.jdbcTemplate.update(
				"insert into webboard_message (  topic_id, detail,status,create_by,create_date) values ( ?,? ,?,?,?) ",
				message.getTopicId(),message.getMessageDetail(),message.getStatus(),message.getCreateBy(),new java.sql.Timestamp(System.currentTimeMillis()));		
		
		returnTopic =  viewTopic(message.getTopicId()+"","1");
	}catch(Exception ex){
		ex.printStackTrace();
		throw ex;
	}	
	
	return returnTopic;
	}
	
	@Override
	public void replyPBPMessage(Message message) throws Exception {
		logger.info(" #TopicDaoImpl.replyMessage  message: "+BeanUtils.getBeanString(message));	
		Topic returnTopic =null;
	try{		
		this.jdbcTemplate.update(
				"insert into webboard_message (  topic_id, detail,status,create_by,create_date) values ( ?,? ,?,?,?) ",
				message.getTopicId(),message.getMessageDetail(),message.getStatus(),message.getCreateBy(),new java.sql.Timestamp(System.currentTimeMillis()));		
		
		returnTopic =  viewTopic(message.getTopicId()+"","1");
		logger.info(" replyPBPMessage returnTopic ="+returnTopic);
	}catch(Exception ex){
		ex.printStackTrace();
		throw ex;
	}	
	
	
	}
	
	
	
	
/*	
	public PagingBean getAllByOffset(PagingBean pagingBean) {
		logger.info("");
		
		List<Topic> returnList = new ArrayList<Topic>();		
		Topic topic = (Topic)pagingBean.get("topic");	
		StringBuffer totalSql  = new StringBuffer(); 
		totalSql.append("  select count(*) as total_item  from  webboard_topic t where 1=1 and status='1'") ;	 
		int  rows_totalItem = jdbcTemplate.queryForObject(totalSql.toString());     
		pagingBean.setTotalItems(rows_totalItem);			 
		StringBuffer sb = new StringBuffer();
		sb.append(" select   \n");
		sb.append(" * from webboard_topic r  \n");
		sb.append(" where 1=1 and status='1'");		 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());  
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query(
				sql,
				new RowMapper<Topic>() {
				public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
					Topic topic = new Topic();				 
					topic.setTopicId(rs.getLong("topic_id"));
					topic.setTopicHeader(rs.getString("header"));  
					topic.setStatus(rs.getString("status"));
					topic.setCategoryId(rs.getLong("category_id"));
					topic.setCreateBy(rs.getString("create_by"));
				return topic;
				}
				});
		
		
		
		pagingBean.setCurrentPageItem(returnList);
		return pagingBean;
	}
	 */

	@Override
	public PagingBean getAllByOffset(PagingBean pagingBean) {
		logger.info(""); 
		List<Topic> returnList = new ArrayList<Topic>(); 
		StringBuffer totalSql  = new StringBuffer(); 
		totalSql.append("  select count(*) as total_item  from  webboard_topic t where 1=1 and t.category_name='GENERAL' and t.status='1' ") ;	 
		int  rows_totalItem = jdbcTemplate.queryForObject(totalSql.toString(),Integer.class);     
		
		pagingBean.setTotalItems(rows_totalItem);	 
		StringBuffer sb  = new StringBuffer();
		sb.append(" SELECT topic.*,COUNT(message.topic_id)  totalmessage   \n");
		sb.append("  FROM  webboard_topic topic \n");
		sb.append("  left join webboard_message message on (message.topic_id=topic.topic_id) \n");
		sb.append("   WHERE 1=1  and topic.category_name='GENERAL' and topic.status='1' \n");
		sb.append("   GROUP BY topic.topic_id \n");	 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());   
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query( sql, new TopicMapper ());  
		pagingBean.setCurrentPageItem(returnList);
		return pagingBean;
	}	
	
	@Override
	public PagingBean getAllByOffsetAndCategoryName(PagingBean pagingBean, Topic topic) {
		String categoryName = topic.getCategoryname();
		String status = topic.getStatus();
		String topicHeader = topic.getTopicHeader();
		logger.info(" categoryName:"+categoryName); 
		List<Topic> returnList = new ArrayList<Topic>(); 
		StringBuffer totalSql  = new StringBuffer(); 
		totalSql.append("  select count(*) as total_item  from  webboard_topic t where 1=1  ") ;	
		if(StringUtils.hasText(categoryName)){
		 totalSql.append("   and t.category_name='"+categoryName+"'") ;	
		}
		if(StringUtils.hasText(status)){
			 totalSql.append("   and t.status='"+status+"'") ;	
		}
		
		if(StringUtils.hasText(topicHeader)){
			 totalSql.append("   and t.header like'%"+topicHeader+"%'") ;	
		}
		 	 
		int  rows_totalItem = jdbcTemplate.queryForObject(totalSql.toString(),Integer.class);     
		
		pagingBean.setTotalItems(rows_totalItem);	 
		StringBuffer sb  = new StringBuffer();
		sb.append(" SELECT topic.*,COUNT(message.topic_id)  totalmessage   \n");
		sb.append("  FROM  webboard_topic topic \n");
		sb.append("  left join webboard_message message on (message.topic_id=topic.topic_id) \n");
		sb.append("   WHERE 1=1   \n");
		if(StringUtils.hasText(categoryName)){
			sb.append("   and topic.category_name='"+categoryName+"'") ;	
		 } 
		if(StringUtils.hasText(status)){
			sb.append("   and message.status='"+status+"'") ;	
		}
		
		if(StringUtils.hasText(topicHeader)){
			sb.append("   and topic.header like'%"+topicHeader+"%'") ;	
		}
		sb.append("   GROUP BY topic.topic_id \n");	 
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());   
		String sql =sb.toString();		
		logger.info(" # getAllByOffsetAndCategoryName sql:"+sql);			
		returnList = this.jdbcTemplate.query( sql, new TopicMapper ());  
		pagingBean.setCurrentPageItem(returnList);
		return pagingBean;
	}	
	
	
	private class TopicMapper implements RowMapper<Topic> {   						
        @Override
		public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Topic topic = new Topic();
			topic.setTopicId(rs.getLong("topic_id"));
			topic.setTopicHeader(rs.getString("header"));  
			topic.setMessageCount(rs.getInt("totalmessage"));
			topic.setCreateBy(rs.getString("create_by"));
			topic.setCreateDate(rs.getTimestamp("create_date")); 
			topic.setStatus (rs.getString("status"));  
			topic.setCategoryId(rs.getLong("category_id"));
		return topic;
		} 
    } 
	
	
	
	@Override
	public Topic getById(String topicId) {		 
		String sql ="select *  from webboard_topic where topic_id ="+topicId;
		logger.info(" ### getById sql:"+sql);
		Topic topic = this.jdbcTemplate.queryForObject(
				sql,
				new RowMapper<Topic>() {
				public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
					Topic topic = new Topic();
					topic.setTopicId(rs.getLong("topic_id")); 
					topic.setTopicHeader(rs.getString("header"));  
					topic.setTopicDetail(rs.getString("detail"));  
					topic.setStatus(rs.getString("status"));
					topic.setCategoryId(rs.getLong("category_id"));
					topic.setCreateDate(rs.getTimestamp("create_date")); 
				return topic;
				}
				});
			
		return topic;
	}	
	
	
	@Override
	public Topic viewTopic(String topicId,String messageStatus) {	
		logger.info("  ## WebboardTopicDaoImpl.viewTopic topicId:"+topicId+"   messageStatus:"+messageStatus);
		
		//String sql1 ="select *  from webboard_topic where topic_id ="+topicId;
		//logger.info(" ### viewTopic sql:"+sql1);
		//Topic topic = this.jdbcTemplate.queryForObject(sql1,
		//		new RowMapper<Topic>() {
		//		public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
		//			Topic topic = new Topic();
		//			topic.setTopicId(rs.getLong("topic_id")); 
		//			topic.setTopicHeader(rs.getString("header"));  
		//			topic.setTopicDetail(rs.getString("detail"));  
		//			topic.setStatus(rs.getString("status"));
		//			topic.setCategoryId(rs.getLong("category_id"));
		//			topic.setCreateDate(rs.getTimestamp("create_date"));
		//		return topic;
		//		}
		//		});
	//	if(topic!=null){ 
		
		Topic topic = new Topic();
		topic.setTopicId(new Long(topicId));
			StringBuffer sbmessage = new StringBuffer();
			sbmessage.append(" select   \n");
			sbmessage.append(" * from webboard_message r  \n");
			sbmessage.append(" where 1=1 and topic_id="+topicId);	
			if(!WebboardConstants.WEBBOARD_ALL.equals(messageStatus)){
				sbmessage.append("  and status ='"+messageStatus+"'");	
			}
			String sql =sbmessage.toString();		
			logger.info(" sql sbmessage:"+sql);			
			List <Message> messageList = this.jdbcTemplate.query(
					sql,
					new RowMapper<Message>() {
					public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
						Message domain = new Message();				 
						domain.setMessageId(rs.getLong("message_id"));
						domain.setTopicId(rs.getLong("topic_id"));
						domain.setMessageDetail(rs.getString("detail"));
						domain.setCreateBy(rs.getString("create_by"));
						domain.setStatus(rs.getString("status"));
						domain.setCreateDate(rs.getTimestamp("create_date"));
					return domain;
					}
					}); 
			topic.setMessageList(messageList)	; 
	//	} 			
		return topic;
	}	

	
	
	
	
	@Override
	public void deleteById(String topicId) {	 
		this.jdbcTemplate.update(" delete from  webboard_topic where topic_id ="+topicId);		
	}
	
	@Override
	public void disable(String topicId) {	 
		logger.info(" #TopicDaoImpl.disable topicId: "+topicId);
		try{	
		this.jdbcTemplate.update(
				" update  webboard_topic set status=?   where topic_id=? ",
				"0"  ,topicId);
		}catch(Exception ex){
			ex.printStackTrace();
			 
		}		
	}
	
	@Override
	public void enable(String topicId) {	 
		logger.info(" #TopicDaoImpl.enable  topicId: "+topicId);
		try{	
		this.jdbcTemplate.update(
				" update  webboard_topic set status=?   where topic_id=? ",
				"1"  ,topicId);
		}catch(Exception ex){
			ex.printStackTrace();
			 
		}			
	}
	
	
	
	@Override
	public void disableMessage(String messageId) {	 
		logger.info(" #TopicDaoImpl.disableMessage topicId: "+messageId);
		try{	
		this.jdbcTemplate.update(
				" update  webboard_message set status=?   where message_id=? ",
				"0"  ,messageId);
		}catch(Exception ex){
			ex.printStackTrace();
			 
		}		
	}
	
	@Override
	public void enableMessage(String messageId) {	 
		logger.info(" #TopicDaoImpl.enableMessage topicId: "+messageId);
		try{	
		this.jdbcTemplate.update(
				" update  webboard_message set status=?   where message_id=? ",
				"1"  ,messageId);
		}catch(Exception ex){
			ex.printStackTrace();
			 
		}			
	}
 
	@Override
	public String getIdFromTopicName(String topicName) {		
		String returnstr = "";
		String sqlTopicId = " select topic_id form webboard_topic where header ='"+topicName+"'";

		logger.info(" sqlTopicId:"+sqlTopicId);		
		returnstr = this.jdbcTemplate.queryForObject(
				topicName,
				new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {					
					String str = rs.getString("topic_id")	;	
				return str;
				}
				});	
		
		return returnstr;
	}
	
	@Override
	public boolean isAlreadyUsege(String topicId) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from webboard_topic topic inner join buckwagrouptopic grouptopic " +
					"on (topic.topic_id = grouptopic.topic_id)	where topic.topic_id ="+topicId;
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
	public boolean isExist( String name) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalTopic  from webboard_topic topic  where  topic.header='"+name+"'";
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
	public boolean isExistForUpdate( String name,Long id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalTopic  from webboard_topic t  where (  t.header='"+name+"') and t.topic_id !="+id;		 
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
	public Topic getTopicByArtifaceId(final String artifaceId,final String artifaceType) {		 
		logger.info(" ## getTopicByArtifaceId artifaceId:"+artifaceId+" artifaceType:"+artifaceType);
		// Get Topic belong to Use Case 
		Topic topic  = new Topic();
		
		String sqlIsTopicAlredyExist = "";
		if(ProjectConstant.ARTIFACE_TYPE_USE_CASE.equals(artifaceType)){
			// Check for use case
			sqlIsTopicAlredyExist = " select t.topic_id as topicId from project_usecase u inner join webboard_topic t on (u.topic_id=t.topic_id)		and u.usecase_id="+artifaceId;
		}else if(ProjectConstant.ARTIFACE_TYPE_DETAIL_DESIGN.equals(artifaceType)){
			sqlIsTopicAlredyExist = " select t.topic_id  as topicId  from project_detail_design u inner join webboard_topic t on (u.topic_id=t.topic_id)		and u.detail_design_id="+artifaceId;
		}else if(ProjectConstant.ARTIFACE_TYPE_VISION.equals(artifaceType)){
			sqlIsTopicAlredyExist = " select t.topic_id  as topicId  from project_vision u inner join webboard_topic t on (u.topic_id=t.topic_id)		and u.vision_id="+artifaceId;
		}else if(ProjectConstant.ARTIFACE_TYPE_TESTCASE.equals(artifaceType)){
			sqlIsTopicAlredyExist = " select t.topic_id  as topicId  from project_testcase u inner join webboard_topic t on (u.topic_id=t.topic_id)		and u.testcase_id="+artifaceId;
		}
		
		 
		List<String> returnstrList = this.jdbcTemplate.query(
				sqlIsTopicAlredyExist,
				new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {					
					String str = rs.getString("topicId")	;	
					return str;
				}
				});	
		
		if(returnstrList!=null&&returnstrList.size()>0){
			String topicId = returnstrList.get(0);
			
			// Foud :Load
			// Not Found : Create New
			
			 topic = this.jdbcTemplate.queryForObject(
					"select *  from webboard_topic where topic_id = ?",
					new Object[]{topicId},
					new RowMapper<Topic>() {
					public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
						Topic topic = new Topic();
						topic.setTopicId(rs.getLong("topic_id")); 
						topic.setTopicHeader(rs.getString("header"));  
						topic.setTopicDetail(rs.getString("detail"));  
						topic.setStatus(rs.getString("status"));
						topic.setCategoryId(rs.getLong("category_id"));
						topic.setCreateDate(rs.getTimestamp("create_date"));
					return topic;
					}
					}); 
			
			
			if(topic!=null){ 
				StringBuffer sbmessage = new StringBuffer();
				sbmessage.append(" select   \n");
				sbmessage.append(" * from webboard_message r  \n");
				sbmessage.append(" where 1=1 and status='1' and topic_id="+topicId);	 
				
				String sql =sbmessage.toString();		
				logger.info(" sql sbmessage:"+sql);			
				List <Message> messageList = this.jdbcTemplate.query(
						sql,
						new RowMapper<Message>() {
						public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
							Message domain = new Message();				 
							domain.setMessageId(rs.getLong("message_id"));
							domain.setTopicId(rs.getLong("topic_id"));
							domain.setMessageDetail(rs.getString("detail"));
							domain.setCreateBy(rs.getString("create_by"));
							domain.setStatus(rs.getString("status"));
							domain.setCreateDate(rs.getTimestamp("create_date"));
						return domain;
						}
						}); 
				topic.setMessageList(messageList)	;
				
			}  
			
		}else{
			// Create new Topic and set id to Artiface
			KeyHolder keyHolder = new GeneratedKeyHolder();  
			jdbcTemplate.update(new PreparedStatementCreator() {  
				public PreparedStatement createPreparedStatement(Connection connection)throws SQLException {  
					PreparedStatement ps = connection.prepareStatement("" +						
							" insert into webboard_topic (  category_id ,create_by,create_date,category_name,status) values ( ?,?,?,?,? ) " +
						 "", Statement.RETURN_GENERATED_KEYS);   
					ps.setLong(1,new Long(1));
					ps.setString(2,artifaceType);
					ps.setDate(3,new Date(System.currentTimeMillis()));		 	
					ps.setString(4,WebboardConstants.WEBBOARD_TYPE_PROJECT);
					ps.setString(5,WebboardConstants.WEBBOARD_ENABLE);
					return ps;  
					}
				}, 	keyHolder);
	 	
			Long returnid =  keyHolder.getKey().longValue();
			
			topic.setTopicId(returnid);
			
			// Update Artiface
			if(ProjectConstant.ARTIFACE_TYPE_USE_CASE.equals(artifaceType)){
				this.jdbcTemplate.update(
						"update  project_usecase set  topic_id=? where usecase_id=? ",
						returnid  ,artifaceId);
		 	 }else if(ProjectConstant.ARTIFACE_TYPE_DETAIL_DESIGN.equals(artifaceType)){
					this.jdbcTemplate.update(
							"update  project_detail_design set  topic_id=? where detail_design_id=? ",
							returnid  ,artifaceId);			

		 	 }else if(ProjectConstant.ARTIFACE_TYPE_VISION.equals(artifaceType)){
					this.jdbcTemplate.update(
							"update  project_vision set  topic_id=? where vision_id=? ",
							returnid  ,artifaceId);				
			}else if(ProjectConstant.ARTIFACE_TYPE_VISION.equals(artifaceType)){
				this.jdbcTemplate.update(
						"update  project_testcase set  topic_id=? where testcase_id=? ",
						returnid  ,artifaceId);				
		}
			
			
		}

		return topic;
	}	
	
	
}
