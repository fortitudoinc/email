/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.internlmsgs.fragment.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.openmrs.Role;
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
public class ComposeMessageFragFragmentController {
	
	/**
	 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a
	 * copy of the MPL was not distributed with this file, You can obtain one at
	 * http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under the terms of the Healthcare
	 * Disclaimer located at http://openmrs.org/license. Copyright (C) OpenMRS Inc. OpenMRS is a
	 * registered trademark and the OpenMRS graphic logo is a trademark of OpenMRS Inc.
	 */
	public void controller(FragmentModel model, @SpringBean("userService") UserService service) {
		List<User> users = service.getAllUsers();
		ArrayList<String> usersInfo = new ArrayList<String>();
		String userInfo;
		String userRoles;
		Set<Role> roles;
		for (User user : users) {
			if (user == null) {
				continue;
			}
			roles = user.getRoles();
			userRoles = "";
			for (Role role : roles) {
				userRoles += role.getName();
			}
			
			userInfo = user.getGivenName() + " " + user.getFamilyName();
			userInfo += " Roles: " + userRoles;
			usersInfo.add(userInfo);
		}
		model.addAttribute("users", usersInfo);
	}
	
	public List<SimpleObject> getUserNameSuggestions(@RequestParam(value = "query", required = false) String query,
	        @SpringBean("userService") UserService service, UiUtils ui) {
		List<User> allUsers = service.getAllUsers(), filteredUsers = new ArrayList<User>();
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
	
	public List<SimpleObject> getAllUsers(@SpringBean("userService") UserService service, UiUtils ui) {
		List<User> allUsers = service.getAllUsers(), filteredUsers = new ArrayList<User>();
		String[] properties;
		properties = new String[4];
		properties[0] = "givenName";
		properties[1] = "familyName";
		properties[2] = "roles";
		properties[3] = "userId";
		return SimpleObject.fromCollection(allUsers, ui, properties);
	}
	
	public void saveMessage(@RequestParam(value = "subject", required = false) String subject,
	        @RequestParam(value = "body", required = false) String body,
	        @RequestParam(value = "senderUserId", required = false) String senderUserId,
	        @RequestParam(value = "recipientUserIds", required = false) String recipientUserIds,
	        @RequestParam(value = "sendOrDraft", required = false) String sendOrDraft, HttpSession session) {
		
		System.out.println(subject + "\n" + body + "\n" + senderUserId + "\n" + recipientUserIds + "\n");
		InternlMessage msg;
		System.out.println("*******saveMessage");
		msg = new InternlMessage();
		
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
		System.out.println("BEFORE MESSAGE SAVED\n" + msg.stringRep());
		InternlMessage m = Context.getService(InternlMessageService.class).saveInternlMessage(msg);
		System.out.println("MESSAGE SAVED SUCCESSFULLY\n" + msg.stringRep());
		
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
			
			Context.getService(InternlMessageService.class).saveInternlMessage(m);
			System.out.println("********NEW MESSAGE\n " + m.stringRep());
		}
		
		InfoErrorMessageUtil.flashInfoMessage(session, "Message Saved");
	}
	
}