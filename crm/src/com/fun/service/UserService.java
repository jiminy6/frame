package com.fun.service;

import com.fun.domain.SysUser;

public interface UserService {

	void register(SysUser user);

	SysUser login(SysUser user);

}
