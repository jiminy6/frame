package cn.itcast.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;

public interface RoleRepository extends JpaRepository<Role,Integer>{
	 
	    /**
	     * 说明：用户和角色是多对多的关系，这方法通过一个user映射对应的roles
	     * @param user 用户
	     * @return
	     * @author luowenxin
	     * @time：2017年12月16日 下午11:16:42
	     */
	List<Role>findByUsers(User user);
}
