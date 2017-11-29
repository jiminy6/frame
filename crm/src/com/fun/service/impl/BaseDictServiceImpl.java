package com.fun.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fun.dao.BaseDictDao;
import com.fun.domain.BaseDict;
import com.fun.service.BaseDictService;

/**
*@author:luowenxin
*@date: 2017年11月15日
**/
@Service("baseDictService")
@Transactional
public class BaseDictServiceImpl implements BaseDictService {
	@Autowired
	private BaseDictDao baseDictDao;
	@Override
	@Transactional(readOnly=true)
	public List<BaseDict> findByTypeCode(String dictTypeCode) {
		List<BaseDict>list=baseDictDao.findByTypeCode(dictTypeCode);
		for (BaseDict ba : list) {
			System.out.println(ba);
		}
		return list;
	}

}
