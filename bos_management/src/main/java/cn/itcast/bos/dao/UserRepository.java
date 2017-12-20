package cn.itcast.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.system.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	User findByUsername(String username);
}
