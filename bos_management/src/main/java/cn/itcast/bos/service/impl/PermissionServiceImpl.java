package cn.itcast.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.PermissionRepository;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.service.PermissionService;
@Service("permissionService")
@Transactional
public class PermissionServiceImpl implements PermissionService{
	@Autowired
	private PermissionRepository permissionRepository;
	@Override
	public List<Permission> list() {
		return permissionRepository.findAll();
	}
	@Override
	public void save(Permission permission) {
		permissionRepository.save(permission);
	}

}
