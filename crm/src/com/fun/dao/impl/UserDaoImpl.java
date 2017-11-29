package com.fun.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.fun.dao.UserDao;
import com.fun.domain.SysUser;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Override
	public void register(SysUser user) {
		hibernateTemplate.save(user);
	}
	/* (non-Javadoc)
	 * @see com.fun.dao.UserDao#login(com.fun.domain.SysUser)
	 * 如果用户名和密码能够在数据库中找到,返回这个对象    
	 */
	@Override
	public SysUser login(SysUser user) {
		 List<SysUser> list = (List<SysUser>) hibernateTemplate.find("from SysUser where user_code=? and user_password=?",user.getUser_code(),user.getUser_password());
		 // 如果list的长度为0,说明用户名或者密码不对,登录失败
		 if(list.size()==0){
			 return null;
		 }
		 else{
			 return list.get(0);
		 }
	}

}
