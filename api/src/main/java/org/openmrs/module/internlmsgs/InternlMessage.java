/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.internlmsgs;

import java.io.Serializable;
import java.util.Date;
import org.openmrs.BaseOpenmrsData;

/**
 * @author levine
 */
public class InternlMessage extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private int senderUserId;
	
	private String msgSubject;
	
	private String msgBody;
	
	private Date msgDate;
	
	private int msgPriority;
	
	private String msgTag;
	
	private int isDeleted;
	
	private int isTrashed;
	
	private int isDraft;
	
	private String msgRecipients;
	
	public int getSenderUserId() {
		return senderUserId;
	}
	
	public void setSenderUserId(int senderUserId) {
		this.senderUserId = senderUserId;
	}
	
	public String getMsgSubject() {
		return msgSubject;
	}
	
	public void setMsgSubject(String msgSubject) {
		this.msgSubject = msgSubject;
	}
	
	public String getMsgBody() {
		return msgBody;
	}
	
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	
	public Date getMsgDate() {
		return msgDate;
	}
	
	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}
	
	public int getMsgPriority() {
		return msgPriority;
	}
	
	public void setMsgPriority(int msgPriority) {
		this.msgPriority = msgPriority;
	}
	
	public String getMsgTag() {
		return msgTag;
	}
	
	public void setMsgTag(String msgTag) {
		this.msgTag = msgTag;
	}
	
	public int getIsDeleted() {
		return isDeleted;
	}
	
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public int getIsTrashed() {
		return isTrashed;
	}
	
	public void setIsTrashed(int isTrashed) {
		this.isTrashed = isTrashed;
	}
	
	public int getIsDraft() {
		return isDraft;
	}
	
	public void setIsDraft(int isDraft) {
		this.isDraft = isDraft;
	}
	
	public String getMsgRecipients() {
		return msgRecipients;
	}
	
	public void setMsgRecipients(String msgRecipients) {
		this.msgRecipients = msgRecipients;
	}
	
	/**
	 * The primary key for this InternlMessage. Auto generated by the database.
	 * 
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * The primary key for this InternlMessage. If this is null, the database will generate the
	 * integer primary key because we marked the internalmessages.id column to auto_increment. <br/>
	 * <br/>
	 * 
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	public InternlMessage copy() {
		InternlMessage msg = new InternlMessage();
		msg.setSenderUserId(senderUserId);
		msg.setIsDeleted(0);
		msg.setIsDraft(0);
		msg.setIsTrashed(0);
		msg.setMsgSubject(msgSubject);
		msg.setMsgBody(msgBody);
		msg.setMsgDate(msgDate);
		msg.setMsgPriority(msgPriority);
		msg.setMsgRecipients(msgRecipients);
		msg.setMsgTag(msgTag);
		return msg;
	}
	
	public String stringRep() {
		String msg = "";
		msg += "Subject: " + msgSubject + "\n";
		msg += "Body: " + msgBody + "\n";
		msg += "Date: " + msgDate + "\n";
		msg += "Recipients: " + msgRecipients + "\n";
		msg += "Tag: " + msgTag + "\n";
		msg += "senderUserId: " + senderUserId + "\n";
		return msg;
	}
}
