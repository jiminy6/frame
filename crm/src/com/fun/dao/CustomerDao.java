package com.fun.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.fun.domain.Customer;

public interface CustomerDao {

	void save(Customer customer);

	List<Customer> findAll();

	int count(DetachedCriteria dc);

	List<Customer> page(DetachedCriteria dc, int start, int size);

	void delete(Customer customer);

	Customer findById(Long cust_id);

	void update(Customer customer);


}
