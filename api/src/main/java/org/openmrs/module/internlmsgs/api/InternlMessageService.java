/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.internlmsgs.api;

import java.util.List;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.internlmsgs.InternlMessage;
import org.springframework.transaction.annotation.Transactional;
import org.openmrs.module.internlmsgs.InternlMessagesConfig;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
@Transactional
public interface InternlMessageService extends OpenmrsService {
	
	/**
	 * Saves a message. Sets the owner to superuser, if it is not set. It can be called by users
	 * with this module's privilege. It is executed in a transaction.
	 * 
	 * @param message
	 * @return
	 * @throws APIException
	 */
	@Authorized(InternlMessagesConfig.MODULE_PRIVILEGE)
	@Transactional
	InternlMessage saveInternlMessage(InternlMessage message) throws APIException;
	
	@Authorized()
	@Transactional(readOnly = true)
	public List<InternlMessage> getInternlMessagesByUserId(int userId);
	
	/**
	 * Get a {@link InternlMessage} object by primary key id.
	 * 
	 * @param id the primary key integer id to look up on
	 * @return the found InternlMessage object which matches the row with the given id. If no row
	 *         with the given id exists, null is returned.
	 */
	@Authorized()
	@Transactional(readOnly = true)
	public InternlMessage getInternlMessage(Integer id);
	
	@Authorized()
	@Transactional(readOnly = true)
	public List<InternlMessage> getAllInternlMessages();
	
}
