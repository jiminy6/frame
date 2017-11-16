package com.fun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fun.dao.CustomerDao;
import com.fun.domain.Customer;
import com.fun.service.CustomerService;
@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDao customerDao;
	@Override
	@Transactional(readOnly=true)
	public Customer findCustomerById(Long id) {
		customerDao.findCustomerById(id);
		return null;
	}
	@Override
	public void save(Customer customer) {
		customerDao.save(customer);
	}

}
