/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.internlmsgs.api.db.hibernate;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.internlmsgs.InternlMessage;
import org.openmrs.module.internlmsgs.api.db.InternlMessagesDao;

/**
 * @author levine
 */
public class HibernateInternlMessageDAO implements InternlMessagesDao {
	
	private DbSessionFactory sessionFactory;
	
	/**
	 * This is a Hibernate object. It gives us metadata about the currently connected database, the
	 * current session, the current db user, etc. To save and get objects, calls should go through
	 * sessionFactory.getCurrentSession() <br/>
	 * <br/>
	 * This is called by Spring. See the /metadata/moduleApplicationContext.xml for the
	 * "sessionFactory" setting. See the applicationContext-service.xml file in CORE openmrs for
	 * where the actual "sessionFactory" object is first defined.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(DbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public InternlMessage saveInternlMessage(InternlMessage message) {
		sessionFactory.getCurrentSession().saveOrUpdate(message);
		return message;
	}
	
	@Override
	public InternlMessage getInternlMessage(Integer id) {
		return (InternlMessage) sessionFactory.getCurrentSession().get(InternlMessage.class, id);
	}
	
	@Override
	public List<InternlMessage> getInternlMessagesByUserId(int userId) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(InternlMessage.class);
		crit.add(Restrictions.eq("userId", userId));
		return crit.list();
	}
	
	@Override
	public List<InternlMessage> getAllInternlMessages() {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(InternlMessage.class);
		return crit.list();
	}
	
}
