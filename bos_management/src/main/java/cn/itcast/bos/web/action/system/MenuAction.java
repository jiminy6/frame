package cn.itcast.bos.web.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.MenuService;
import cn.itcast.bos.web.action.comon.BaseAction;
@Controller
@Scope("prototype")
public class MenuAction extends BaseAction<Menu> {
	@Autowired
	private MenuService menuService;
	private static final long serialVersionUID = 1L;
	@Action("menu_list")
	public String menuList(){
		List<Menu> list = menuService.list();
		ActionContext.getContext().getValueStack().push(list);
		return JSON;
	}
	@Action(value="menu_add",results={
			@Result(type=REDIRECT,location="/pages/system/menu.html")
	})
	public String addMenu(){
		menuService.addMenu(model);
		return SUCCESS;
	}
	@Action("menu_listByUser")
	public String listByUser(){
		//通过shiro获取user
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		List<Menu> listByUser = menuService.listByUser(user);
		ActionContext.getContext().getValueStack().push(listByUser);
		return JSON;
	}
	
}
