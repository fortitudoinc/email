/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.internlmsgs.api.db;

import java.util.List;
import org.openmrs.module.internlmsgs.InternlMessage;

/**
 * This is the DAO interface. This is never used by the developer, but instead the
 * {@link InternlMessagesService} calls it (and developers call the NoteService)
 */
public interface InternlMessagesDao {
	
	public InternlMessage saveInternlMessage(InternlMessage message);
	
	public InternlMessage getInternlMessage(Integer id);
	
	public List<InternlMessage> getInternlMessagesByUserId(int userId);
	
	public List<InternlMessage> getAllInternlMessages();
}
