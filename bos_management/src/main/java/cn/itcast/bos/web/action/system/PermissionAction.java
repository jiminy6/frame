package cn.itcast.bos.web.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.service.PermissionService;
import cn.itcast.bos.web.action.comon.BaseAction;
@Controller
@Scope("prototype")
public class PermissionAction extends BaseAction<Permission>{
	@Autowired
	private PermissionService permissionService;
	private static final long serialVersionUID = 1L;
	@Action("permission_list")
	public String findAllPermission(){
		List<Permission> list = permissionService.list();
		ActionContext.getContext().getValueStack().push(list);
		return JSON;
	}
	@Action(value="permission_add",results={
			@Result(type=REDIRECT,location="/pages/system/permission.html")
	})
	public String savePermission(){
		permissionService.save(model);
		return SUCCESS;
	}
}
