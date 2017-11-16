package com.fun.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fun.domain.Customer;
import com.fun.service.CustomerService;
import com.fun.service.impl.CustomerServiceImpl;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test1 {
	@Autowired
	private CustomerService customerService;
	// ioc ac.getBean  getWep.....ac   classpathXml
	@Test
	public void test1() {
		Long id=1L;
		customerService.findCustomerById(id);
	}
	@Test
	public void test2(){
		Customer customer = new Customer();
		customer.setCust_name("泳裤男");
		customerService.save(customer);
	}
}
