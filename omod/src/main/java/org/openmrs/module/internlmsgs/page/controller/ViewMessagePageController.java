/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.internlmsgs.page.controller;

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
public class ViewMessagePageController {
	
	public void controller(PageModel model, HttpSession session,
	        @RequestParam(value = "messageId", required = false) int messageId,
	        @RequestParam(value = "replyForwardDraft", required = false) String replyForwardDraft,
	        @SpringBean("userService") UserService service) {
		//System.out.println("*******ViewMessagePageController: returnURL: " + returnUrl);
		InternlMessage message = Context.getService(InternlMessageService.class).getInternlMessage(messageId);
		message.setMsgBody(message.getMsgBody().substring(1));
		MessageAndRecipientsNames msg = bundleMessageAndRecipientNames(message, service);
		
		model.addAttribute("msg", msg);
		model.addAttribute("replyForwardDraft", replyForwardDraft);
	}
	
	public MessageAndRecipientsNames bundleMessageAndRecipientNames(InternlMessage message, UserService userService) {
		MessageAndRecipientsNames bundle = new MessageAndRecipientsNames();
		User user;
		String recipients = "";
		User sender = userService.getUser(message.getSenderUserId());
		bundle.setSenderName(sender.getGivenName() + " " + sender.getFamilyName());
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
			String name = user.getGivenName() + " " + user.getFamilyName() + ",";
			recipients += name;
		}
		bundle.setRecipientsNames(recipients);
		return bundle;
	}
	
	public String post(@RequestParam(value = "messageIdd", required = false) String messageId,
	        @RequestParam(value = "returnUrl", required = false) String returnUrl, HttpSession session) {
		//System.out.println("POST    returnUrl: " + returnUrl);
		//System.out.println("MESSAGE DELETED: " + messageId);
		InternlMessage msg = Context.getService(InternlMessageService.class).getInternlMessage(Integer.valueOf(messageId));
		msg.setIsTrashed(1);
		Context.getService(InternlMessageService.class).saveInternlMessage(msg);
		InfoErrorMessageUtil.flashInfoMessage(session, "Message Deleted: " + messageId);
		return "redirect:" + returnUrl;
	}
	
}
