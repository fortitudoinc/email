/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.internlmsgs.page.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.internlmsgs.InternlMessage;
import org.openmrs.module.internlmsgs.api.InternlMessageService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.module.uicommons.util.InfoErrorMessageUtil;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author levine
 */
public class ViewAllMessagesPageController {
	
	public void controller(PageModel model, HttpSession session, @SpringBean("userService") UserService service) {
		//System.out.println("*******ViewAllMessagesPageControllerxx");
	}
	
	private void deleteTrashMessages(String deleteRestoreTrashMsgId, String deleteAllMsgIds, String deleteRestoreTrash,
	        HttpSession session) {
		if (deleteRestoreTrash.equals("trash")) {
			InternlMessage msg = Context.getService(InternlMessageService.class).getInternlMessage(
			    Integer.valueOf(deleteRestoreTrashMsgId));
			msg.setIsTrashed(1);
			Context.getService(InternlMessageService.class).saveInternlMessage(msg);
			InfoErrorMessageUtil.flashInfoMessage(session, "Message Trashed");
			return;
		}
		System.out.println("deleteTrashMessages   deleteRestoreTrash  " + deleteRestoreTrash);
		if (deleteRestoreTrash.equals("delete")) {
			InternlMessage msg = Context.getService(InternlMessageService.class).getInternlMessage(
			    Integer.valueOf(deleteRestoreTrashMsgId));
			msg.setIsDeleted(1);
			Context.getService(InternlMessageService.class).saveInternlMessage(msg);
			InfoErrorMessageUtil.flashInfoMessage(session, "Message Deleted");
			return;
		}
		String deleteMsgIdArray[] = deleteAllMsgIds.split(",");
		for (String msgId : deleteMsgIdArray) {
			if (!msgId.equals("")) {
				InternlMessage msg = Context.getService(InternlMessageService.class).getInternlMessage(
				    Integer.valueOf(msgId));
				msg.setIsDeleted(1);
				Context.getService(InternlMessageService.class).saveInternlMessage(msg);
			}
			InfoErrorMessageUtil.flashInfoMessage(session, "Message(s) Deleted");
		}
	}
}
