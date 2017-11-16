package com.fun.dao.impl;

import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.fun.dao.CustomerDao;
import com.fun.domain.Customer;
@Repository("customerDao")
public class CustomerDaoImpl implements CustomerDao {
	@Autowired
private HibernateTemplate hibernateTemplate;
	public void save(Customer customer) {
		hibernateTemplate.save(customer);
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
