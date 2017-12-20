package cn.itcast.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;

public interface MenuRepository extends JpaRepository<Menu,Integer> {
	/**
	 * 
	     * 说明：如果是admin就查询所有的菜单,并且正序排列
	     * @return
	     * @author luowenxin
	     * @time：2017年12月20日 上午12:49:38
	 */
	List<Menu>findByOrderByPriority();
	//通过jpql,掌握实体类之间的关系
	@Query("from Menu m inner join fetch m.roles r inner join fetch r.users u where u=? order by m.priority")
	List<Menu>findByUser(User user);
	@Query(value="SELECT * from T_MENU t1 inner join T_ROLE_MENU t2 ON t1.C_ID=t2.C_MENU_ID inner join T_USER_ROLE t3 on t2.C_ROLE_ID=t3.C_ROLE_ID where t3.C_USER_ID=? ORDER BY t1.c_priority ASC",nativeQuery=true)
	List<Menu>findByUserId(String userId);
}
