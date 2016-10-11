package com.buckwa.dao.impl.pbp;

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

import com.buckwa.dao.intf.pbp.MessageDao;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.webboard.Message;
import com.buckwa.util.BeanUtils;

@Repository("messageDao")
public class MessageDaoImpl implements MessageDao {
	private static Logger logger = LoggerFactory.getLogger(MessageDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Message> getAllMessage() {
		List<Message> returnList = new ArrayList<Message>(); 
		String sql ="  select r.message_id , r.message_name, r.message_desc from buckwamessage r  " ;	
		logger.info(" sql:"+sql);
		returnList = this.jdbcTemplate.query(sql,new MessageMapper());
		return returnList;
	}
	 
	
	@Override
	public Message getMessage(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Message message) {
		logger.info(" #MessageDaoImpl.update message: "+BeanUtils.getBeanString(message));
		this.jdbcTemplate.update(
				"update  webboard_message set detail=?   where message_id=? ",
				message.getMessageDetail(),message.getMessageId()); 
 		
 	
	}

	
	@Override
	public void create(Message message) {
		logger.info(" #MessageDaoImpl.create # ");	
		message.setTopicId(new Long(500));
		//final Message finalMessage = message;
		this.jdbcTemplate.update(
				"insert into webboard_message (  topic_id, detail,status,create_by,create_date) values ( ?,? ,?,?,?) ",
				message.getTopicId(),message.getMessageDetail(),message.getStatus(),message.getCreateBy(),new java.sql.Timestamp(System.currentTimeMillis()));	
	}

	
	public PagingBean getAllMessageByOffset(PagingBean pagingBean) {	 
		Message message = (Message)pagingBean.get("message");		
		List<Message> returnList = new ArrayList<Message>();			
		StringBuffer sqltotalsb = new StringBuffer();
		sqltotalsb.append("   select count(*) as total_item  from  webboard_message  r \n"); 
		sqltotalsb.append(" where 1=1 ");		
		if(StringUtils.hasText(message.getCreateBy())){
			sqltotalsb.append(" and r.create_by =  '"+StringEscapeUtils.escapeSql(message.getCreateBy())+"'");
		}
		if(StringUtils.hasText(message.getMessageDetail())){
			sqltotalsb.append(" and r.detail like '%"+StringEscapeUtils.escapeSql(message.getMessageDetail())+"%'");
		}
		 	
		sqltotalsb.append(" and topic_id= 500 ");	
	 
		int  rows_totalItem = jdbcTemplate.queryForObject(sqltotalsb.toString(),Integer.class); 
		pagingBean.setTotalItems(rows_totalItem);	
		StringBuffer sb = new StringBuffer();
		sb.append(" select  * from webboard_message r  \n");
		sb.append(" where 1=1 ");		
		if(StringUtils.hasText(message.getCreateBy())){
			sb.append(" and r.create_by =  '"+StringEscapeUtils.escapeSql(message.getCreateBy())+"'");
		}
		if(StringUtils.hasText(message.getMessageDetail())){
			sb.append(" and r.detail like '%"+StringEscapeUtils.escapeSql(message.getMessageDetail())+"%'");
		}
		sb.append(" and topic_id= 500 ");	
		
		sb.append(" LIMIT "+pagingBean.getLimitItemFrom()+","+ pagingBean.getLimitItemTo());
		String sql =sb.toString();		
		logger.info(" sql:"+sql);			
		returnList = this.jdbcTemplate.query( sql, new MessageMapper()   );
		pagingBean.setCurrentPageItem(returnList);

		return pagingBean;
	}
	
	@Override
	public Message getMessageById(String messageId) {		 		
		String sql =" select *  from webboard_message where message_id = "+messageId;
		Message message = this.jdbcTemplate.queryForObject(sql,	new MessageMapper() );				
 		
		return message;
	}	

	
	@Override
	public void deleteMessageById(String messageId) {	 
		this.jdbcTemplate.update(" delete from  webboard_message where message_id ="+messageId);	
		 
	}
	
	@Override
	public String getMessageIdFromMessageName(String messageName) {		
		String returnstr = "";
		String sqlMessageId = " select message_id form buckwamessage where message_name ='"+StringEscapeUtils.escapeSql(messageName)+"'";
		 
		
		logger.info(" sqlMessageId:"+sqlMessageId);		
		returnstr = this.jdbcTemplate.queryForObject(
				messageName,
				new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {					
					String str = rs.getString("message_id")	;	
				return str;
				}
				});	
		
		return returnstr;
	}
	
	@Override
	public boolean isMessageAlreadyUsege(String messageId) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalusage from buckwamessage message inner join buckwagroupmessage groupmessage " +
					"on (message.message_id = groupmessage.message_id)	where message.message_id ="+messageId;
			
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
	public boolean isMessageNameExist(String messageName) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalMessage  from buckwamessage t  where t.message_name='"+StringEscapeUtils.escapeSql(messageName)+"'";
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
	public boolean isMessageNameExistForUpdate(String messageName,Long id) {
		 boolean returnValue = false;
		try{
			String sqltmp = "select count(*) as totalMessage  from buckwamessage t  where t.message_name='"+StringEscapeUtils.escapeSql(messageName)+"'  and t.message_id !="+id	;
			Long found = this.jdbcTemplate.queryForObject(sqltmp,Long.class);
			if(found!=null&&found.intValue()>0){
				returnValue = true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}			
		return returnValue;
	}
	
	
	private class MessageMapper implements RowMapper<Message> {   						
        @Override
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
	}
	
 
}
