package com.fun.service;

import java.io.File;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.fun.domain.Customer;

public interface CustomerService {

	void save(Customer customer);

	List<Customer> findAll();

	List<Customer> page(DetachedCriteria dc, int start, int size);

	int count(DetachedCriteria dc);

	void delete(Long cust_id);

	Customer findById(Long cust_id);

	void update(Customer customer, File upload, String uploadFileName);

	
}
