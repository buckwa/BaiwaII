package com.buckwa.dao.intf.webboard;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.webboard.Message;
import com.buckwa.domain.webboard.Topic;

public interface WebboardTopicDao {
	
 
	public void update(Topic domain) throws Exception ;
	public void create(Topic domain)  throws Exception ;
	public PagingBean getAllByOffset(PagingBean pagingBean);
	public PagingBean getAllByOffsetAndCategoryName(PagingBean pagingBean,Topic topic);
	
	public List<Topic> getAll();
	public Topic getById(String domainId);
	public void deleteById(String domainId);
	public void disable(String domainId);
	public void enable(String domainId);
	public String getIdFromTopicName(String domainName);
	public boolean isAlreadyUsege(String domainId);
	public boolean isExist(String name);
	public boolean isExistForUpdate( String name,Long id);
	
	public Topic viewTopic(String domainId,String messageStatus);
	public Topic replyMessage(Message Message)throws Exception ;
	
	
	public Topic getTopicByArtifaceId(String arid,String artifaceType);
	
	public void disableMessage(String id);
	public void enableMessage(String id);
	
	public void replyPBPMessage(Message Message)throws Exception ;
	
}
