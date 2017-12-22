package cn.itcast.bos.web.action.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.RoleService;
import cn.itcast.bos.service.UserService;
import cn.itcast.bos.web.action.comon.BaseAction;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>{
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	private Integer[] roleIds;
	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}
//	@Action(value="user_login",results={
//			@Result(type=REDIRECT,location="/index.html"),
//			@Result(name=INPUT,type=REDIRECT,location="/login.html")
//	})
	/**
	 * 
	     * 说明：先获取到subject对象，然后创建用户名密码令牌，然后执行认证操作
	     * @return
	     * @author luowenxin
	     * @time：2017年12月16日 下午9:02:43
	 */
	@Action("user_login")
	public String login(){
		Subject subject = SecurityUtils.getSubject();
		Map<String, Object> resultMap = new HashMap<>();
		UsernamePasswordToken token = new UsernamePasswordToken(model.getUsername(),model.getPassword());//令牌对象
		//java中，除了布尔判断，还有异常判断
		try {
			subject.login(token);
			resultMap.put("result",true);
		} 
		catch (IncorrectCredentialsException e) {
			e.printStackTrace();
			resultMap.put("result",false);
			resultMap.put("message","用户名或者密码错误");
		} 
		catch (LockedAccountException e) {
			e.printStackTrace();
			//登录失败:帐号被锁定
			resultMap.put("result", false);
			resultMap.put("message", "帐号被锁定");
		}
		catch (AuthenticationException e) {
			e.printStackTrace();
			resultMap.put("result",false);
			resultMap.put("message","登陆失败");
		}
			ActionContext.getContext().getValueStack().push(resultMap);
			return JSON;
	}
	@Action(value="user_logout",results={
			@Result(type=REDIRECT,location="/.login.html")
	})
	/**
	 * 
	     * 说明：用户注销的action
	     * @return
	     * @author luowenxin
	     * @time：2017年12月17日 下午10:16:45
	 */
	public String logout(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return SUCCESS;
	}
	@Action(value="user_add",results={
			@Result(type=REDIRECT,location="/pages/system/userlist.html")
	})
	public String userAdd(){
		userService.add(model,roleIds);
		return SUCCESS;
	}
	@Action("user_list")
	public String list(){
		List<User> list = userService.list();
		ActionContext.getContext().getValueStack().push(list);
		return JSON;
	}
}
