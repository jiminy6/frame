package cn.itcast.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.MenuRepository;
import cn.itcast.bos.dao.PermissionRepository;
import cn.itcast.bos.dao.RoleRepository;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.service.RoleService;
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private MenuRepository menuRepository;
	@Override
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}
	@Override
	public void addRole(Role role,String menuIds, Integer[] permissionIds) {
		//hibernate的多对多采用持久态对象的关系，来维护中间表的数据
		roleRepository.save(role);
		if(permissionIds!=null){
			for (Integer id : permissionIds) {
				Permission permission = permissionRepository.findOne(id);
				//这里只能不能使用permission.getRoles.add,因为它主动放弃了维护权
				role.getPermissions().add(permission);
			}
			//角色和菜单的关联：快照
			if(StringUtils.isNotBlank(menuIds)){
				String[] split = menuIds.split(",");
				for (String id : split) {
					Menu menu = menuRepository.findOne(Integer.parseInt(id));
					role.getMenus().add(menu);
				}
			}
		}
		
	}

}
