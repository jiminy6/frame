package com.fun.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fun.domain.Customer;
import com.fun.service.CustomerService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
	//模型对象一定需要手动实例化
	private Customer customer=new Customer();
	private CustomerService customerService;
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	public String save(){
		System.out.println("我是Action,客户保存");
		
		//ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
//		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
//		CustomerService  customerService = (CustomerService) ac.getBean("customerService");
		
		customerService.save(customer);	
		return SUCCESS;
	}
@Override
public Customer getModel() {
	return customer;
}
	
}
