package cn.itcast.bos.web.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.service.RoleService;
import cn.itcast.bos.web.action.comon.BaseAction;
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	@Autowired
	private RoleService roleService;
	private static final long serialVersionUID = 1L;
	@Action("role_list")
	public String findAllRoles(){
		List<Role> findAllRoles = roleService.findAllRoles();
		ActionContext.getContext().getValueStack().push(findAllRoles);
		return JSON;
	}
	//属性驱动注入ids
	private String menuIds;
	private Integer[] permissionIds;
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	public void setPermissionIds(Integer[] permissionIds) {
		this.permissionIds = permissionIds;
	}
	@Action(value="role_add",results={
			@Result(type=REDIRECT,location="/pages/system/role.html")
	})
	/**
	 * 
	     * 说明：添加role并关联权限和菜单
	     * @return
	     * @author luowenxin
	     * @time：2017年12月19日 下午9:32:30
	 */
	public String addRole(){
		roleService.addRole(model,menuIds,permissionIds);
		return SUCCESS;
	}
}
