package com.fun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fun.dao.UserDao;
import com.fun.domain.SysUser;
import com.fun.service.UserService;
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Override
	public void register(SysUser user) {
		user.setUser_state("1");
		userDao.register(user);
	}
	@Override
	public SysUser login(SysUser user) {
		SysUser u=userDao.login(user);
		return u;
	}

}
