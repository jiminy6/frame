package cn.itcast.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.RoleRepository;
import cn.itcast.bos.dao.UserRepository;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.UserService;
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public void add(User user, Integer[] roleIds) {
		userRepository.save(user);
		if(roleIds!=null){
			for (Integer id : roleIds) {
				Role one = roleRepository.findOne(id);
				user.getRoles().add(one);
			}
		}
		
	}
	@Override
	public List<User> list() {
		return userRepository.findAll();
	}
	
}
