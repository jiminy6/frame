package com.fun.action;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fun.domain.SysUser;
import com.fun.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @author luowenxin
 *
 */
@Controller("userAction")
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/user")
public class UserAction extends ActionSupport implements ModelDriven<SysUser>{
	private static final long serialVersionUID = 1L;
	//手动注入实例类
	private SysUser user=new SysUser();
	@Autowired
	private UserService userService;
	@Override
	public SysUser getModel() {
		return user;
	}
	@Action(value="user_regist",results={@Result(name="success",location="/login.jsp")})
	public String register(){
		userService.register(user);
		return SUCCESS;
	}
	@Action(value="user_login",results={@Result(name="success",location="/index.jsp"),@Result(name="login",location="/login.jsp")})
	public String login(){
		SysUser u=userService.login(user);
		if(u!=null){
			//登录成功,将用户存入值域,后期可以通过ognl来获得
			ActionContext.getContext().getSession().put("user", u);
			return SUCCESS;
		}
		else{
			//如果登录失败返回登录页面
			this.addActionError("您的用户名或密码错误");
			return LOGIN;
			
		}
	}
}
