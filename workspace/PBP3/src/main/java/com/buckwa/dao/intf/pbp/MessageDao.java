package com.buckwa.dao.intf.pbp;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.webboard.Message;

public interface MessageDao {
	

	public Message getMessage(Message message);
	public void update(Message message);
	public void create(Message message);
	public PagingBean getAllMessageByOffset(PagingBean pagingBean);
	public List<Message> getAllMessage();
	public Message getMessageById(String messageId);
	public void deleteMessageById(String messageId);
	public String getMessageIdFromMessageName(String messageName);
	public boolean isMessageAlreadyUsege(String messageId);
	public boolean isMessageNameExist(String messageName);
	 
	public boolean isMessageNameExistForUpdate(String messageName,Long id);
	
}
