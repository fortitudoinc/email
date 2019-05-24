/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.internlmsgs.fragment.controller;

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
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author levine
 */
public class MsgReplyForwardFragFragmentController {
	
	public void get(FragmentModel model, @RequestParam(value = "messageId", required = false) int messageId,
	        @RequestParam(value = "action", required = false) String action, HttpSession session,
	        @SpringBean("userService") UserService userService) {
		//int messageId = Integer.valueOf(messageIdd);
		System.out.println("*******MsgReplyOrForwardFragmentController, actiondd: " + action);
		String msgRecipients = "", oldMsgRecipientNames = "";
		InternlMessage msg = Context.getService(InternlMessageService.class).getInternlMessage(messageId);
		msg.setMsgBody(msg.getMsgBody().substring(1));
		User senderUser = userService.getUser(msg.getSenderUserId());
		String senderName = senderUser.getGivenName() + " " + senderUser.getFamilyName();
		if (action.equals("reply")) {
			msgRecipients += senderUser.getGivenName() + " " + senderUser.getFamilyName() + ",";
		} else if ((action.equals("replyAll")) || (action.equals("draft"))) {
			msgRecipients += getUsers(msg.getMsgRecipients(), userService);
			if (action.equals("replyAll")) {
				msgRecipients += senderName + ",";
			}
		} else if (action.equals("forward")) {
			oldMsgRecipientNames = getUsers(msg.getMsgRecipients(), userService);
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("action", action);
		model.addAttribute("senderName", senderName);
		model.addAttribute("msgRecipients", msgRecipients);
		model.addAttribute("oldMsgRecipientNames", oldMsgRecipientNames);
	}
	
	private String getUsers(String msgRecipientIds, UserService userService) {
		String msgRecipients = "";
		User user;
		String recipsIds[] = msgRecipientIds.split(",");
		for (String recipId : recipsIds) {
			if (recipId.length() == 0) {
				continue;
			}
			user = userService.getUser(Integer.valueOf(recipId));
			if (user == null) {
				continue;
			}
			msgRecipients += user.getGivenName() + " " + user.getFamilyName() + ",";
		}
		return msgRecipients;
	}
	
	/*
	public List<SimpleObject> getUserNameSuggestions(@RequestParam(value = "query", required = false) String query,
	    @SpringBean("userService") UserService service, UiUtils ui) {
	List<User> allUsers = service.getAllUsers(), filteredUsers = new ArrayList<User>();
	//System.out.println("getUserNameSuggestions");
	String name;
	for (User user : allUsers) {
		if (user == null) {
			continue;
		}
		name = (user.getGivenName() + user.getFamilyName()).toUpperCase();
		if (name.indexOf(query.toUpperCase()) == 0) {
			filteredUsers.add(user);
		}
	}
	String[] properties;
	properties = new String[4];
	properties[0] = "givenName";
	properties[1] = "familyName";
	properties[2] = "roles";
	properties[3] = "userId";
	return SimpleObject.fromCollection(filteredUsers, ui, properties);
	}
	
	*/
	public List<SimpleObject> getUserNameSuggestions(@RequestParam(value = "query", required = false) String query,
	        @SpringBean("userService") UserService service, UiUtils ui) {
		List<User> filteredUsers = getActiveUsers(service), suggestedUsers = new ArrayList<User>();
		String name;
		for (User user : filteredUsers) {
			if (user == null) {
				continue;
			}
			name = (user.getGivenName() + user.getFamilyName()).toUpperCase();
			if (name.indexOf(query.toUpperCase()) == 0) {
				suggestedUsers.add(user);
			}
		}
		String[] properties;
		properties = new String[4];
		properties[0] = "givenName";
		properties[1] = "familyName";
		properties[2] = "roles";
		properties[3] = "userId";
		return SimpleObject.fromCollection(suggestedUsers, ui, properties);
	}
	
	private List<User> getActiveUsers(UserService service) {
		List<User> allUsers = service.getAllUsers();
		List<User> filteredUsers = new ArrayList<User>();
		for (User user : allUsers) {
			System.out.print("User: " + user.getGivenName() + " " + user.getFamilyName());
			if (!user.getRetired()) {
				filteredUsers.add(user);
				System.out.println();
			} else {
				System.out.println("REMOVED");
			}
		}
		return filteredUsers;
	}
	
	public List<SimpleObject> getAllUsers(@SpringBean("userService") UserService userService, UiUtils ui) {
		List<User> allUsers = userService.getAllUsers();
		String[] properties;
		properties = new String[3];
		properties[0] = "givenName";
		properties[1] = "familyName";
		properties[2] = "userId";
		System.out.println("GET ALL USERSsss: " + allUsers.size());
		return SimpleObject.fromCollection(allUsers, ui, properties);
	}
	
	public void saveMessage(@RequestParam(value = "subject", required = false) String subject,
	        @RequestParam(value = "body", required = false) String body,
	        @RequestParam(value = "senderUserId", required = false) String senderUserId,
	        @RequestParam(value = "recipientUserIds", required = false) String recipientUserIds,
	        @RequestParam(value = "sendOrDraft", required = false) String sendOrDraft,
	        @RequestParam(value = "originalMsgId", required = false) String originalMsgId,
	        @RequestParam(value = "isOriginalMsgDraft", required = false) String isOriginalMsgDraft, HttpSession session) {
		
		System.out.println(subject + "\n" + body + "\n" + senderUserId + "\n" + recipientUserIds + "\n" + sendOrDraft + "\n"
		        + originalMsgId + "\n" + isOriginalMsgDraft);
		InternlMessage msg;
		System.out.println("*******saveMessage");
		if (isOriginalMsgDraft.equals("false")) {
			msg = new InternlMessage();
		} else {
			msg = Context.getService(InternlMessageService.class).getInternlMessage(Integer.valueOf(originalMsgId));
			System.out.println("MsgReplyForwardPageController, saveMessage, msgid: " + msg.getId());
		}
		
		msg.setSenderUserId(Integer.valueOf(senderUserId));
		msg.setIsDeleted(0);
		if (sendOrDraft.equals("send")) {
			msg.setIsDraft(0);
			msg.setMsgTag("sent-" + senderUserId);
		} else {
			msg.setIsDraft(1);
			msg.setMsgTag("draft-" + senderUserId);
		}
		
		msg.setMsgBody("%" + body);
		msg.setMsgDate(new Date());
		msg.setMsgSubject(subject);
		msg.setMsgPriority(0);
		msg.setIsTrashed(0);
		msg.setMsgRecipients(recipientUserIds);
		//System.out.println("MESSAGE SAVED SUCCESSFULLYjj\n" + msg.stringRep());
		InternlMessage m = Context.getService(InternlMessageService.class).saveInternlMessage(msg);
		
		if (sendOrDraft.equals("draft")) {
			return;
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
	}
	
}
