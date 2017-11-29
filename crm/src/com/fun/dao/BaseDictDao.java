package com.fun.dao;

import java.util.List;

import com.fun.domain.BaseDict;

/**
*@author:luowenxin
*@date: 2017年11月15日
**/
public interface BaseDictDao {

	List<BaseDict> findByTypeCode(String dictTypeCode);

}
