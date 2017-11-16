package com.fun.dao;

import com.fun.domain.Customer;

public interface CustomerDao {

	void findCustomerById(Long id);

	void save(Customer customer);

}
