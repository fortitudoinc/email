/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.internlmsgs.page.controller;

import org.openmrs.module.internlmsgs.InternlMessage;

/**
 * @author levine
 */
public class MessageAndRecipientsNames {
	
	private InternlMessage message;
	
	private String recipientsNames;
	
	private String senderName;
	
	public String getSenderName() {
		return senderName;
	}
	
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
	public InternlMessage getMessage() {
		return message;
	}
	
	public void setMessage(InternlMessage message) {
		this.message = message;
	}
	
	public String getRecipientsNames() {
		return recipientsNames;
	}
	
	public void setRecipientsNames(String recipientsNames) {
		this.recipientsNames = recipientsNames;
	}
}
