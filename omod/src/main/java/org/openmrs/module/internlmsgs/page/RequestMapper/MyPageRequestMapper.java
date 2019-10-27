/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.internlmsgs.page.RequestMapper;

import java.util.ArrayList;
import java.util.List;
import org.openmrs.ui.framework.page.PageRequestMapper;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.internlmsgs.InternlMessage;
import org.openmrs.module.internlmsgs.api.InternlMessageService;
import org.openmrs.module.internlmsgs.page.controller.MessageAndRecipientsNames;
import org.openmrs.module.uicommons.util.InfoErrorMessageUtil;
import org.openmrs.ui.framework.page.PageRequest;
import org.openmrs.ui.framework.page.PageRequestMapper;
import org.springframework.stereotype.Component;

/**
 * @author levine
 */
@Component
public class MyPageRequestMapper implements PageRequestMapper {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Implementations should call {@link PageRequest#setProviderNameOverride(String)} and
	 * {@link PageRequest#setPageNameOverride(String)}, and return true if they want to remap a
	 * request, or return false if they didn't remap it.
	 * 
	 * @param request may have its providerNameOverride and pageNameOverride set
	 * @return true if this page was mapped (by overriding the provider and/or page), false
	 *         otherwise
	 */
	public boolean mapRequest(PageRequest request) {
		
		if (!((request.getPageName().equals("home")) || (request.getPageName().equals("login")))) {
			return false;
		}
		HttpSession session = request.getRequest().getSession();
		
		User user = Context.getUserContext().getAuthenticatedUser();
		if (user == null) {
			return false;
		}
		int userId = user.getUserId();
		String userIdStr = String.valueOf(userId);
		List<MessageAndRecipientsNames> receivedMail = new ArrayList<MessageAndRecipientsNames>();
		List<InternlMessage> mm = Context.getService(InternlMessageService.class).getAllInternlMessages();
		if (mm.isEmpty()) {
			return false;
		}
		for (InternlMessage message : mm) {
			System.out.println("MESSAGE ID: " + message.getId() + " message read? " + message.getMsgHasBeenRead()
			        + "message tag: " + message.getMsgTag());
			
			if ((message.getMsgHasBeenRead() == 1) || (message.getIsDeleted() == 1) || (message.getIsDraft() == 1)
			        || (message.getIsTrashed() == 1)) {
				continue;
			}
			String tag[] = message.getMsgTag().split("-"), tagType;
			int tagId, senderId = message.getSenderUserId();
			if (tag.length < 2) { //deals with old data - when setting up
				// new DB delete this if statement
				continue;
			}
			if (!tag[0].equals("recip")) {
				continue;
			}
			tagId = Integer.valueOf(tag[1]);
			
			if (tagId != userId) {
				continue;
			}
			InfoErrorMessageUtil.flashInfoMessage(session, "YOU HAVE UNREAD MESSAGE(S)");
			return false;
		}
		
		//InfoErrorMessageUtil.flashInfoMessage(session, "home or login - YOU HAVE NO UNREAD MESSAGES");
		// no override happened
		return false;
	}
}
