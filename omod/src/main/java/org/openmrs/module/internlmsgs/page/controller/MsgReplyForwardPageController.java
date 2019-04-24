/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.internlmsgs.page.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.internlmsgs.InternlMessage;
import org.openmrs.module.internlmsgs.api.InternlMessageService;
import org.openmrs.module.uicommons.util.InfoErrorMessageUtil;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author levine
 */
public class MsgReplyForwardPageController {
	
	public void controller(PageModel model, @RequestParam(value = "messageId", required = false) int messageId,
	        @RequestParam(value = "action", required = false) String action, HttpSession session,
	        @SpringBean("userService") UserService userService) {
		System.out.println("*******MsgReplyForwardPageController");
	}
	
	/*
	public String post(@RequestParam(value = "recipientNames", required = false) String recipientNames,
	    @RequestParam(value = "subject", required = false) String subject,
	    @RequestParam(value = "body", required = false) String body,
	    @RequestParam(value = "senderUserId", required = false) String senderUserId,
	    @RequestParam(value = "recipientUserIds", required = false) String recipientUserIds,
	    @RequestParam(value = "returnUrl", required = false) String returnUrl,
	    @RequestParam(value = "sendOrDraft", required = false) String sendOrDraft,
	    @RequestParam(value = "originalMsgId", required = false) String originalMsgId,
	    @RequestParam(value = "isOriginalMsgDraft", required = false) String isOriginalMsgDraft, HttpSession session) {
	InternlMessage msg;
	System.out.println("*******post");
	if (isOriginalMsgDraft.equals("false")) {
		msg = new InternlMessage();
	} else {
		msg = Context.getService(InternlMessageService.class).getInternlMessage(Integer.valueOf(originalMsgId));
		System.out.println("MsgReplyForwardPageController, POST, msgid: " + msg.getId());
	}
	
	msg.setSenderUserId(Integer.valueOf(senderUserId));
	msg.setIsDeleted(0);
	if (sendOrDraft.equals("send")) {
		msg.setIsDraft(0);
	} else {
		msg.setIsDraft(1);
	}
	msg.setMsgTag("tag");
	msg.setMsgBody("%" + body);
	msg.setMsgDate(new Date());
	msg.setMsgSubject(subject);
	msg.setMsgPriority(0);
	msg.setIsTrashed(0);
	msg.setMsgRecipients(recipientUserIds);
	InternlMessage m = Context.getService(InternlMessageService.class).saveInternlMessage(msg);
	
	//Context.getUserService().getUser(Integer.BYTES)
	InfoErrorMessageUtil.flashInfoMessage(session, "Message Saved");
	System.out.println("***********MESSAGE SAVED************RETURN URL: " + returnUrl);
	System.out.println("RECIPIENT NAMES: " + recipientNames);
	return "redirect:http://localhost:8080/openmrs/internlmsgs/viewAllMessages.page";
	}
	*/
	
	public void post(@RequestParam(value = "recipientNames", required = false) String recipientNames,
	        @RequestParam(value = "subject", required = false) String subject,
	        @RequestParam(value = "body", required = false) String body,
	        @RequestParam(value = "senderUserId", required = false) String senderUserId,
	        @RequestParam(value = "recipientUserIds", required = false) String recipientUserIds,
	        @RequestParam(value = "returnUrl", required = false) String returnUrl,
	        @RequestParam(value = "sendOrDraft", required = false) String sendOrDraft,
	        @RequestParam(value = "originalMsgId", required = false) String originalMsgId,
	        @RequestParam(value = "isOriginalMsgDraft", required = false) String isOriginalMsgDraft, HttpSession session) {
		
		//return "redirect:http://localhost:8080/openmrs/internlmsgs/viewAllMessages.page";
	}
}
