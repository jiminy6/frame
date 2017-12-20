package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.system.Role;

public interface RoleService {
	/**
	 * 
	     * 说明：返回所有的权限列表
	     * @return
	     * @author luowenxin
	     * @time：2017年12月18日 下午3:27:47
	 */
	List<Role>findAllRoles();
	/**
	 * 
	     * 说明：添加权限,并关联权限和菜单
	     * @param role
	     * @author luowenxin
	     * @param permissionIds 
	     * @param menuIds 
	     * @time：2017年12月18日 下午4:17:42
	 */
	void addRole(Role role,String menuIds, Integer[] permissionIds);
}
