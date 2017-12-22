package cn.itcast.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.system.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	User findByUsername(String username);

	@Query(value="UPDATE T_USER SET `status` =? WHERE activetime < '2017-12-1' and `status`='1'",nativeQuery=true)
	@Modifying
	void updateStatusByActiveTime(String status);
}
