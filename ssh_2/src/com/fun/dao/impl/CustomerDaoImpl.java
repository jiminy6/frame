package com.fun.dao.impl;

import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.fun.dao.CustomerDao;
import com.fun.domain.Customer;

public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {

	public void save(Customer customer) {
//	Configuration configuration = new Configuration().configure();
//	SessionFactory factory = configuration.buildSessionFactory();
//	Session session = factory.getCurrentSession();
//	org.hibernate.Transaction transaction = session.beginTransaction();
//	transaction.commit();
		getHibernateTemplate().save(customer);
	System.out.println("保存了");
	}

	public void findCustomerById(Long id) {
		System.out.println("找到了");
		Configuration configuration = new Configuration().configure();
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.createQuery("");
	}

}
