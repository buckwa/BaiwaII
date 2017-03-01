package com.buckwa.domain.webboard;

import com.buckwa.domain.BaseDomain;

public class Message extends BaseDomain{
	
	private Long messageId;
	private Long topicId;
	private String messageDetail;
	private String imagePath;
	private Boolean statusMessage;
	private String userName;
 
	
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	public String getMessageDetail() {
		return messageDetail;
	}
	public void setMessageDetail(String messageDetail) {
		this.messageDetail = messageDetail;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Boolean getStatusMessage() {

		return statusMessage;
//		if (this.statusMessage == true ||this.statusMessage.equals("1")){
//			return "1";
//		}else {
//			return "0";
//
//		}
	}
	public void setStatusMessage(Boolean statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
 

 
 
}
