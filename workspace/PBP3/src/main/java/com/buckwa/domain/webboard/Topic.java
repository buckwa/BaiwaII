package com.buckwa.domain.webboard;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.buckwa.domain.BaseDomain;

public class Topic extends BaseDomain{
	
	private Long topicId;
	private Long categoryId;
	private String topicHeader;
	private String topicDetail;
	
	private String status;
	 
	private String imagePath;
	
	private String replyMessage;
	
	private String categoryname;
	
	
	private CommonsMultipartFile fileData;
	
	private List<Message> messageList;
	
	private int messageCount;
	
	public List<Message> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	public String getTopicHeader() {
		return topicHeader;
	}
	public void setTopicHeader(String topicHeader) {
		this.topicHeader = topicHeader;
	}
	public String getTopicDetail() {
		return topicDetail;
	}
	public void setTopicDetail(String topicDetail) {
		this.topicDetail = topicDetail;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public CommonsMultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReplyMessage() {
		return replyMessage;
	}
	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}
	public int getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	

	
	
	
}
