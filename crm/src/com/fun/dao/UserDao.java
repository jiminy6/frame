package com.fun.dao;

import com.fun.domain.SysUser;

public interface UserDao {

	void register(SysUser user);

	SysUser login(SysUser user);

}
