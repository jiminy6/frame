package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.system.Permission;

public interface PermissionService {
	/**
	 * 
	     * 说明：查询所有的权限
	     * @return
	     * @author luowenxin
	     * @time：2017年12月18日 下午3:10:53
	 */
	List<Permission>list();
	/**
	 * 
	     * 说明：保存权限的方法
	     * @param permission
	     * @author luowenxin
	     * @time：2017年12月18日 下午3:34:17
	 */
	void save(Permission permission);
}
