package org.openmrs.module.internlmsgs.page.controller;

import javax.servlet.http.HttpSession;
import org.openmrs.api.UserService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author levine
 */
public class ShutWindowPageController {
	
	public void controller(PageModel model) {
		System.out.println("*******ShutWindowPageController");
	}
}
