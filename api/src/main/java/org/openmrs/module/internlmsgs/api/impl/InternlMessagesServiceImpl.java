/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.internlmsgs.api.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.internlmsgs.InternlMessage;
import org.openmrs.module.internlmsgs.api.InternlMessageService;
import org.openmrs.module.internlmsgs.api.db.InternlMessagesDao;
import org.springframework.transaction.annotation.Transactional;

public class InternlMessagesServiceImpl extends BaseOpenmrsService implements InternlMessageService {
	
	InternlMessagesDao dao;
	
	private static final Log log = LogFactory.getLog(InternlMessagesServiceImpl.class);
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(InternlMessagesDao dao) {
		this.dao = dao;
	}
	
	public InternlMessagesDao getDao() {
		return dao;
	}
	
	public static Log getLog() {
		return log;
	}
	
	@Transactional
	public InternlMessage saveInternlMessage(InternlMessage message) throws APIException {
		return dao.saveInternlMessage(message);
	}
	
	@Transactional(readOnly = true)
	public InternlMessage getInternlMessage(Integer id) {
		return dao.getInternlMessage(id);
	}
	
	@Transactional(readOnly = true)
	public List<InternlMessage> getInternlMessagesByUserId(int userId) {
		return dao.getInternlMessagesByUserId(userId);
	}
	
	@Transactional(readOnly = true)
	public List<InternlMessage> getAllInternlMessages() {
		return dao.getAllInternlMessages();
	}
}
