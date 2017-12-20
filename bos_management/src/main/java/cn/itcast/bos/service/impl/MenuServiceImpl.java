package cn.itcast.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.MenuRepository;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.MenuService;
@Service("menuService")
@Transactional
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuRepository menuRepository;

	@Override
	public List<Menu> list() {
		return menuRepository.findAll();
	}

	@Override
	/**
	 * struts和combox配合时，如果没有选择任何父菜单，struts也认为选择了，new了一个对象
	 */
	public void addMenu(Menu menu) {
		//处理父菜单不选择的问题
		if(menu.getParentMenu()!=null && menu.getParentMenu().getId()==null){
			menu.setParentMenu(null);
		}
		menuRepository.save(menu);
	}

	@Override
	public List<Menu> listByUser(User user) {
		if("admin".equals(user.getUsername())){
			return menuRepository.findByOrderByPriority();
		}
		else{
			List<Menu> list = menuRepository.findByUser(user);
			System.out.println(list);
			return list;
		}
	}

}
