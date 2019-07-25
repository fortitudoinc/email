/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.internlmsgs.fragment.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.internlmsgs.InternlMessage;
import org.openmrs.module.internlmsgs.api.InternlMessageService;
import org.openmrs.module.internlmsgs.page.controller.MessageAndRecipientsNames;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author levine
 */
public class ViewAllMessagesFragFragmentController {
	
	public void controller(PageModel model, HttpSession session, @SpringBean("userService") UserService service) {
		System.out.println("*******ViewAllMessagesPageControllerxx");
		
		User user = Context.getUserContext().getAuthenticatedUser();
		int userId = user.getUserId();
		String userIdStr = String.valueOf(userId);
		List<MessageAndRecipientsNames> receivedMail = new ArrayList<MessageAndRecipientsNames>();
		List<MessageAndRecipientsNames> sentMail = new ArrayList<MessageAndRecipientsNames>();
		List<MessageAndRecipientsNames> allMail = new ArrayList<MessageAndRecipientsNames>();
		List<MessageAndRecipientsNames> trashMail = new ArrayList<MessageAndRecipientsNames>();
		List<MessageAndRecipientsNames> draftMail = new ArrayList<MessageAndRecipientsNames>();
		String trashIds = "";
		String recipients;
		List<InternlMessage> mm = Context.getService(InternlMessageService.class).getAllInternlMessages();
		if (mm.isEmpty()) {
			System.out.println("NO MESSAGES FOUND");
		}
		
		for (InternlMessage message : mm) {
			allMail.add(bundleMessageAndRecipientNames(message, service));
			if (message.getIsDeleted() != 0) {
				continue;
			}
			String tag[] = message.getMsgTag().split("-"), tagType;
			int tagId, senderId = message.getSenderUserId();
			if (tag.length < 2) { //deals with old data - when setting up
				// new DB delete this if statement
				continue;
			}
			tagType = tag[0];
			tagId = Integer.valueOf(tag[1]);
			
			if (tagId != userId) {
				continue;
			}
			message.setMsgBody(message.getMsgBody().substring(1));
			
			if (message.getIsTrashed() != 0) {
				trashMail.add(bundleMessageAndRecipientNames(message, service));
				continue;
			}
			if (tagType.equals("draft")) {
				draftMail.add(bundleMessageAndRecipientNames(message, service));
				continue;
			}
			if (tagType.equals("sent")) {
				sentMail.add(bundleMessageAndRecipientNames(message, service));
				continue;
			}
			receivedMail.add(bundleMessageAndRecipientNames(message, service));
			
			System.out.println("*****VIEW ALL MESSAGES, ADD TO INBOX: msg tag: " + message.getMsgTag());
			
		}
		
		//InfoErrorMessageUtil.flashInfoMessage(session, "Message Saved");
		model.addAttribute("messages", allMail);
		model.addAttribute("receivedMail", receivedMail);
		model.addAttribute("sentMail", sentMail);
		model.addAttribute("trashMail", trashMail);
		model.addAttribute("draftMail", draftMail);
	}
	
	public static MessageAndRecipientsNames bundleMessageAndRecipientNames(InternlMessage message, UserService userService) {
		MessageAndRecipientsNames bundle = new MessageAndRecipientsNames();
		User user;
		user = userService.getUser(message.getSenderUserId());
		if (user == null) { // check if user was deleted so user is not found
			return null;
		}
		bundle.setSenderName(user.getGivenName() + " " + user.getFamilyName());
		//System.out.println("SENDER NAME: " + user.getGivenName() + " " + user.getFamilyName());
		String recipients = "";
		bundle.setMessage(message);
		bundle.setRecipientsNames(recipients);
		String recipientUserIds = message.getMsgRecipients();
		if (recipientUserIds == null) {
			return bundle;
		}
		//System.out.println("!!!!!!!!!!!!!!!!!!!!user ids: " + recipientUserIds);
		if (recipientUserIds.length() == 0) {
			return bundle;
		}
		String recipIdArray[] = recipientUserIds.split(",");
		for (String recipIdStr : recipIdArray) {
			if (recipIdStr.length() == 0) {
				continue;
			}
			user = userService.getUser(Integer.valueOf(recipIdStr));
			if ((user == null) || user.getRetired()) { // check if user was deleted so user is not found
				continue;
			}
			String name = user.getGivenName() + " " + user.getFamilyName() + ",";
			recipients += name;
		}
		bundle.setRecipientsNames(recipients);
		return bundle;
	}
	
	public void trashMessage(@RequestParam("messageId") int messageId) {
		System.out.println("*****Trashing message: " + messageId);
		InternlMessage msg = Context.getService(InternlMessageService.class).getInternlMessage(messageId);
		msg.setIsTrashed(1);
		Context.getService(InternlMessageService.class).saveInternlMessage(msg);
	}
	
	public void restoreMessage(@RequestParam("messageId") int messageId) {
		System.out.println("*****Restoring message: " + messageId);
		InternlMessage msg = Context.getService(InternlMessageService.class).getInternlMessage(messageId);
		msg.setIsTrashed(0);
		Context.getService(InternlMessageService.class).saveInternlMessage(msg);
	}
	
	public void deleteMessage(@RequestParam("messageId") int messageId) {
		System.out.println("*****Deleting message: " + messageId);
		InternlMessage msg = Context.getService(InternlMessageService.class).getInternlMessage(messageId);
		msg.setIsDeleted(1);
		Context.getService(InternlMessageService.class).saveInternlMessage(msg);
	}
	
	public void setMessageHasBeenRead(@RequestParam("messageId") int messageId) {
		InternlMessage msg = Context.getService(InternlMessageService.class).getInternlMessage(messageId);
		msg.setMsgHasBeenRead(1);
		Context.getService(InternlMessageService.class).saveInternlMessage(msg);
	}
	
}
