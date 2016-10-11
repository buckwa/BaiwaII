package com.buckwa.dao.intf.project;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Message;

public interface MessageDao {
	

 
	public void update(Message message);
	public void create(Message message);
	public PagingBean getByOffset(PagingBean pagingBean);
	public List<Message> getAllMessage();
	public List<Message> getAllMessage_E();
	public List<Message> getAllMessage_S();
	public Message getMessageById(String messageId);
	public Message getMessageandDetailDesignById(String messageId);
	
	public void deleteMessageById(String messageId);
 
	public boolean isMessageAlreadyUsege(String messageId);
	 
 
	
}
