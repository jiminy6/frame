package com.fun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fun.domain.BaseDict;

/**
*@author:luowenxin
*@date: 2017年11月15日
**/
public interface BaseDictService {
	
	public List<BaseDict> findByTypeCode(String dictTypeCode);

}
