/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.internlmsgs.page.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.openmrs.api.context.Context;
//import org.openmrs.module.patientlist.SpecialtyTypeItem;
import org.openmrs.ui.framework.page.PageModel;

//import org.openmrs.module.patientlist.api.SpecialtyTypeItemService;

/**
 * @author levine THIS CAN BE USED AS PART OF THE SOLUTION FOR GSOC STUDENT TO AVOID CREATING A NEW
 *         ENDPOINT TO OBTAIN SPECIALTIES AND LIST OF DOCTORS
 */
public class GetJsonPageController {
	
	public void controller(HttpServletRequest request, PageModel model, HttpSession session) {
		System.out.println("*******GetJsonController");
		
		String[] items = { "spec1", "spec2", "spec3" };
		ArrayList<String> specialties = new ArrayList<String>(Arrays.asList(items));
		System.out.println("********************GetJson: " + specialties);
		model.addAttribute("items", specialties);
	}
	
}
