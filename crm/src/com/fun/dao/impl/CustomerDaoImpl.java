package com.fun.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.fun.dao.CustomerDao;
import com.fun.domain.Customer;
@Repository("customerDao")
public class CustomerDaoImpl  implements CustomerDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(Customer customer) {
		hibernateTemplate.save(customer);
	}

	@Override
	public List<Customer> findAll() {
		List<Customer> list = (List<Customer>) hibernateTemplate.find("from Customer");
		System.out.println("dao list");
		return list;
	}

	@Override
	public int count(DetachedCriteria dc) {
//		List<Long> list = (List<Long>) hibernateTemplate.find("select count(*) from Customer");
		//设置查询表中满足条件的记录数
		dc.setProjection(Projections.rowCount());
		List<Long> list = (List<Long>) hibernateTemplate.findByCriteria(dc);
		return list.get(0).intValue();
	}

	/* (non-Javadoc)
	 * @see com.fun.dao.CustomerDao#page(int, int)
	 * 采用离线对象,实现分页查询
	 */
	@Override
	public List<Customer> page(DetachedCriteria dc,int start, int size) {
//		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
//		List<Customer> list = (List<Customer>) hibernateTemplate.findByCriteria(criteria, start, size);
		//清空dc中查询表记录数的信息
		dc.setProjection(null);
		List<Customer> list = (List<Customer>) hibernateTemplate.findByCriteria(dc, start, size);
		return list;
	}

	@Override
	public void delete(Customer customer) {
		 hibernateTemplate.delete(customer);
	}

	@Override
	public Customer findById(Long cust_id) {
		List<Customer> list  = (List<Customer>) hibernateTemplate.find("from Customer where cust_id=?",cust_id);
		return list.get(0);
	}

	@Override
	public void update(Customer customer) {
		//清空session缓存,如果不清空会报错,显示session中有两个对象有同一个oid
		hibernateTemplate.clear();
		hibernateTemplate.update(customer);
	}

}
