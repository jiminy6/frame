package cn.itcast.crm.service.Impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.dao.CustomerRepository;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	@Override
	public List<Customer> findCustomerListNoFixedAreaId() {
		return customerRepository.findByFixedAreaIdIsNull();
	}

	@Override
	public List<Customer> findCustomerListByFixedAreaId(String fixedareaid) {
		return customerRepository.findByFixedAreaId(fixedareaid);
	}
	 /**
     * 说明：将客户关联到指定的定区中
     * 查询一个关联定区的所有customers
     * 将这些customoers的fixedAreaId设置为null
     * 根据customerId找到指定的customer
     * customer.setFixedId(fixedId)
     * @param fixedareaid
     * @param ids
     * @author luowenxin
     * @time：2017年12月2日 下午9:38:12
     */
	@Override
	public void updateFixedAreaIdByCustomerIds(String fixedareaid, String ids) {
	 List<Customer> findByFixedAreadId = customerRepository.findByFixedAreaId(fixedareaid);
	 for (Customer customer : findByFixedAreadId) {
		customer.setFixedAreaId(null);//将这些fixedId设置为null
	}
	 if(StringUtils.isNotBlank(ids)&&!(ids.equals("null"))){
		 String[] strings = StringUtils.split(ids,",");
		 for (String string : strings) {
			Customer customer = customerRepository.findOne(Integer.parseInt(string));
			customer.setFixedAreaId(fixedareaid);
		}
	 }
	}

	@Override
	public void saveCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public void activeCustomer(String telephone, Integer type) {
		customerRepository.updateTypeByTelephone(telephone, type);
	}

}
