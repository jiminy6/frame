package com.fun.service;

import java.util.List;

import com.fun.domain.Linkman;

/**
*@author:luowenxin
*@date: 2017年11月17日
**/
public interface LinkmanService {

	void save(Linkman linkman);

	List<Linkman> findAll();

	void delete(Long lkmId);

	Linkman findById(Long lkmId);

	void update(Linkman linkman);

}
