package com.fun.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.fun.dao.LinkmanDao;
import com.fun.domain.Customer;
import com.fun.domain.Linkman;
import com.opensymphony.xwork2.ModelDriven;

/**
*@author:luowenxin
*@date: 2017年11月17日
**/
@Repository("linkmanDao")
public class LinkmanDaoImpl implements LinkmanDao{
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(Linkman linkman) {
		hibernateTemplate.save(linkman);
	}

	@Override
	public List<Linkman> findAll() {
		List<Linkman> list = (List<Linkman>) hibernateTemplate.find("from Linkman");
		System.out.println(list);
		return list;
	}

	@Override
	public void delete(Linkman linkman) {
		hibernateTemplate.delete(linkman);
	}

	@Override
	public void update(Linkman linkman) {
		hibernateTemplate.clear();
		hibernateTemplate.update(linkman);
	}

	@Override
	public Linkman findById(Long lkmId) {
		List<Linkman> list  = (List<Linkman>) hibernateTemplate.find("from Linkman where lkmId=?",lkmId);
		return list.get(0);
	}

}
