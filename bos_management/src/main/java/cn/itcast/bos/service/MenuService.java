package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;

public interface MenuService {
	/**
	 * 
	     * 说明：menu的分页查询
	     * @return
	     * @author luowenxin
	     * @time：2017年12月17日 下午10:34:19
	 */
	List<Menu>list();
	/**
	 * 
	     * 说明：保存menu的方法
	     * @param menu 菜单
	     * @author luowenxin
	     * @time：2017年12月17日 下午11:24:33
	 */
	void addMenu(Menu menu);
	/**
	 * 
	     * 说明：根据不同的用户展示不同的菜单
	     * @return
	     * @author luowenxin
	     * @time：2017年12月20日 上午12:40:15
	 */
	List<Menu> listByUser(User user);
}
