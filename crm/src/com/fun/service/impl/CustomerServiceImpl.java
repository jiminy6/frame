package com.fun.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fun.dao.CustomerDao;
import com.fun.domain.Customer;
import com.fun.service.CustomerService;
import com.fun.utils.UploadUtils;
/**
 * @author luowenxin
 *
 */
@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDao customerDao;

	@Override
	public void save(Customer customer) {
		if(StringUtils.isBlank(customer.getBaseDictIndustry().getDictId())){
			customer.setBaseDictIndustry(null);
		}
		if(StringUtils.isBlank(customer.getBaseDictSource().getDictId())){
			customer.setBaseDictSource(null);
		}
		if(StringUtils.isBlank(customer.getBaseDictLevel().getDictId())){
			customer.setBaseDictLevel(null);
		}
		customerDao.save(customer);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Customer> findAll() {
		List<Customer> list=customerDao.findAll();
		return list;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Customer> page(DetachedCriteria dc,int start, int size) {
		
		return customerDao.page(dc,start,size);
	}

	@Override
	@Transactional(readOnly=true)
	public int count(DetachedCriteria dc) {
		return  customerDao.count(dc);
	}

	@Override
	public void delete(Long cust_id) {
		Customer customer = customerDao.findById(cust_id);
		customerDao.delete(customer);
	}
	@Transactional(readOnly=true)
	@Override
	public Customer findById(Long cust_id) {
		return customerDao.findById(cust_id);
	}

	@Override
	public void update(Customer customer, File upload, String uploadFileName) {
		Customer oldCustomer = customerDao.findById(customer.getCust_id());
		//如果选择上传照片
		if(upload!=null){
			String randonFileName = UploadUtils.generateRandonFileName(uploadFileName);
			String randomDir = UploadUtils.generateRandomDir(randonFileName);
			try {
				FileUtils.copyFile(upload,new File("/develop/upload"+randomDir+"/"+randonFileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//设置图片保存的原始地址
			customer.setCust_filename(uploadFileName);
			//设置图片的路径
			customer.setCust_image(randomDir+"/"+randonFileName);
			//删除之前的图片
			if(StringUtils.isNotBlank(oldCustomer.getCust_image())){
				new File("/develop/upload"+oldCustomer.getCust_image()).delete();
			}
			
		}
		else{
			//没有选择上传文件
			customer.setCust_image(oldCustomer.getCust_image());
			customer.setCust_filename(oldCustomer.getCust_filename());
		}
		// 如果在下拉列表中选择的是'请选择'
		if(StringUtils.isBlank(customer.getBaseDictIndustry().getDictId())){
			customer.setBaseDictIndustry(null);
		}
		if(StringUtils.isBlank(customer.getBaseDictSource().getDictId())){
			customer.setBaseDictSource(null);
		}
		if(StringUtils.isBlank(customer.getBaseDictLevel().getDictId())){
			customer.setBaseDictLevel(null);
		}
		customerDao.update(customer);
	}
	
	
}
