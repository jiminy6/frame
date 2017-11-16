package com.fun.service;

import com.fun.domain.Customer;

public interface CustomerService {

	Customer findCustomerById(Long id);

	void save(Customer customer);
	
}
