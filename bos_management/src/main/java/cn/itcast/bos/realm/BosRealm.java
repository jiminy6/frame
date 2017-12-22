package cn.itcast.bos.realm;

import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.itcast.bos.dao.PermissionRepository;
import cn.itcast.bos.dao.RoleRepository;
import cn.itcast.bos.dao.UserRepository;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
/**
 * 
     * 说明：user的realm
     * @author luowenxin
     * @version 1.0
     * @date 2017年12月16日
 */
@Component("bosRealm")
public class BosRealm extends AuthorizingRealm{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
	    System.out.println("开始授权");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		info.addStringPermission("courier:list");
//		info.addRole("base");//这个是固定的权限设置
		User user = (User) principals.getPrimaryPrincipal();
		if("admin".equals(user.getUsername())){
			List<Role> list = roleRepository.findAll();//角色的集合
			for (Role role : list) {
				info.addRole(role.getKeyword());
			}
			List<Permission> permissionList = permissionRepository.findAll();//权限的集合
			for (Permission permission : permissionList) {
				info.addStringPermission(permission.getKeyword());
			}
			
		}
		//如果该用户是普通用户
		else{
			List<Role> roleList = roleRepository.findByUsers(user);
			for (Role role : roleList) {
				info.addRole(role.getKeyword());//添加角色字符串
			    Set<Permission> permissions = role.getPermissions();
			    for (Permission permission : permissions) {
					info.addStringPermission(permission.getKeyword());//添加功能字符串
				}
			}
		}
		return info;
	}
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
	    System.out.println("获取认证的信息");
		String username=((UsernamePasswordToken)token).getUsername();
		User user = userRepository.findByUsername(username);
		//如果用户信息不存在就直接返回null
		if(null==user){
			return null; 
		}
		//如果用户存在就将信息封装到认证信息对象中,交给安全
		else{
			if("0".equals(user.getStatus())){
				throw new LockedAccountException("账号"+user.getUsername()+"过期被锁定");
			}
			//第三个参数是realm对象唯一的名字
			AuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),super.getName());
			return simpleAuthenticationInfo;
		}
	}
	//指定缓存存放的位置
	@Value("bos_realm_authentication_cache")
	public void setBac(String authenticationCacheName){
		super.setAuthenticationCacheName(authenticationCacheName);
	}
	@Value("bos_realm_authorization_cache")
	public void setA(String authorizationCacheName){
		super.setAuthorizationCacheName(authorizationCacheName);
	}
}
