package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.system.User;

public interface UserService {
	public void add(User user, Integer[] roleIds);

	public List<User> list();

	public void updateStatusForExpiredTime();
}
