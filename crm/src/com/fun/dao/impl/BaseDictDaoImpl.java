package com.fun.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.fun.dao.BaseDictDao;
import com.fun.domain.BaseDict;

/**
*@author:luowenxin
*@date: 2017年11月15日
**/
@Repository("baseDictDao")
public class BaseDictDaoImpl implements BaseDictDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Override
	public List<BaseDict> findByTypeCode(String dictTypeCode) {
		List<BaseDict> list = (List<BaseDict>) hibernateTemplate.find("from BaseDict where dict_type_code=?",dictTypeCode);
//		List<BaseDict> list = (List<BaseDict>) hibernateTemplate.find("from BaseDict where dict_type_code=001");
//		List<BaseDict> list = (List<BaseDict>) hibernateTemplate.find("from BaseDict where dictTypeCode=001");
		
		return list ;
	}

}
