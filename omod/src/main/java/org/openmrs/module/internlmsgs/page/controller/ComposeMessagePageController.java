/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.internlmsgs.page.controller;

import java.util.Date;
import javax.servlet.http.HttpSession;
import org.openmrs.Patient;
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
public class ComposeMessagePageController {
	
	/**
	 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a
	 * copy of the MPL was not distributed with this file, You can obtain one at
	 * http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under the terms of the Healthcare
	 * Disclaimer located at http://openmrs.org/license. Copyright (C) OpenMRS Inc. OpenMRS is a
	 * registered trademark and the OpenMRS graphic logo is a trademark of OpenMRS Inc.
	 */
	public void controller(PageModel model, @SpringBean("userService") UserService service) {
	}
	
	public String post(@RequestParam(value = "recipientNames", required = false) String recipientNames,
	        @RequestParam(value = "subject", required = false) String subject,
	        @RequestParam(value = "body", required = false) String body,
	        @RequestParam(value = "senderUserId", required = false) String senderUserId,
	        @RequestParam(value = "recipientUserIds", required = false) String recipientUserIds,
	        @RequestParam(value = "returnUrl", required = false) String returnUrl,
	        @RequestParam(value = "sendOrDraft", required = false) String sendOrDraft, HttpSession session) {
		InternlMessage msg = new InternlMessage();
		
		/*
		Since mysql seems to remove leading newlines/spaces I inject the percent
		to ensure it won't do that; whenever a message is retreived from DB I'll remove %
		 */
		/*
			We need to create messages for the sender and each of the recipients.
			The msgTag String field will indicate for each message whether the
			message is a "sent" version or a "recipient" version. The msgTag field will also
			record the associated msgId for the associated recipient.
			If there are 3 recipients then we will create messages for the sender, and each of the 
			3 recipients for a total of 4 messages.
			If the recipient id is 5 then the msgTag field will
			contain "recip-5"
			Since each recipient will have their own message they will be able to 
			delete, etc., their own message without impacting the other
			recipients.
			If the message is a draft then we will only create a single message with 
			draft status
		        */
		body = "%" + body; //guard against DB stripping leading spaces
		System.out.println("ComposeMessagePageController, POST, body: " + body);
		msg.setSenderUserId(Integer.valueOf(senderUserId));
		msg.setIsDeleted(0);
		System.out.println("Message draft: " + sendOrDraft);
		if (sendOrDraft.equals("send")) {
			msg.setIsDraft(0);
			msg.setMsgTag("sent-" + senderUserId);
		} else {
			msg.setIsDraft(1);
			msg.setMsgTag("draft");
		}
		msg.setMsgBody(body); //guard against DB stripping leading spaces
		msg.setMsgDate(new Date());
		msg.setMsgSubject(subject);
		msg.setMsgPriority(0);
		msg.setIsTrashed(0);
		msg.setMsgRecipients(recipientUserIds);
		InternlMessage m = Context.getService(InternlMessageService.class).saveInternlMessage(msg);
		
		if (sendOrDraft.equals("draft")) {
			return "redirect:" + returnUrl;
		}
		
		if (recipientUserIds.length() == 0) {
			return "redirect:" + returnUrl;
		}
		
		String recipIdArray[] = recipientUserIds.split(",");
		for (String recipIdStr : recipIdArray) {
			if (recipIdStr.length() == 0) {
				continue;
			}
			m = msg.copy();
			m.setMsgTag("recip-" + recipIdStr);
			System.out.println("********NEW MESSAGE, RECIP: " + m.getMsgTag());
			Context.getService(InternlMessageService.class).saveInternlMessage(m);
		}
		InfoErrorMessageUtil.flashInfoMessage(session, "Message Saved");
		return "redirect:" + returnUrl;
	}
	
}
