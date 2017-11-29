package com.fun.dao;

import java.util.List;

import com.fun.domain.Linkman;

/**
*@author:luowenxin
*@date: 2017年11月17日
**/
public interface LinkmanDao  {

	void save(Linkman linkman);
	
	List<Linkman> findAll();

	void delete(Linkman linkman);

	void update(Linkman linkman);
	
	Linkman findById(Long lkmId);

}
