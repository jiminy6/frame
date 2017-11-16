package com.fun.service.impl;

import com.fun.dao.CustomerDao;
import com.fun.domain.Customer;
import com.fun.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
	private CustomerDao customerDao;
	
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public Customer findCustomerById(Long id) {
		customerDao.findCustomerById(id);
		return null;
	}

	@Override
	public void save(Customer customer) {
		customerDao.save(customer);
	}

}
