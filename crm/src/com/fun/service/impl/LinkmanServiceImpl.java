package com.fun.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fun.dao.LinkmanDao;
import com.fun.domain.Linkman;
import com.fun.service.LinkmanService;

/**
*@author:luowenxin
*@date: 2017年11月17日
**/
@Service("linkmanService")
@Transactional
public class LinkmanServiceImpl implements LinkmanService {
	@Autowired
	private LinkmanDao linkmanDao;
	@Override
	public void save(Linkman linkman) {
		linkmanDao.save(linkman);
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Linkman> findAll() {
		List<Linkman>list=linkmanDao.findAll();
		return list;
	}

	@Override
	public void delete(Long lkmId) {
		Linkman linkman = linkmanDao.findById(lkmId);
		linkmanDao.delete(linkman);
	}

	@Override
	@Transactional(readOnly=true)
	public Linkman findById(Long lkmId) {
		return linkmanDao.findById(lkmId);
	}

	@Override
	public void update(Linkman linkman) {
		linkmanDao.update(linkman);
	}

}
