package cn.itcast.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.crm.domain.Customer;

public interface CustomerRepository  extends JpaRepository<Customer, Integer> {
	 
	    /**
	     * 说明：返回所有定区为null的客户信息
	     * @return
	     * @author luowenxin
	     * @time：2017年12月2日 下午9:34:10
	     */
	List<Customer> findByFixedAreaIdIsNull();
	  
	    /**
	     * 说明：根据fixedId查询所有的客户信息
	     * @param fixedareaid
	     * @return
	     * @author luowenxin
	     * @time：2017年12月2日 下午9:36:46
	     */
	List<Customer> findByFixedAreaId(String fixedareaid);
	
	 
	    /**
	     * 说明：激活客户
	     * @param telephone
	     * @param type
	     * @author luowenxin
	     * @time：2017年12月6日 下午2:36:49
	     */
	@Query("update Customer set type=?2 where telephone=?1")
	@Modifying
	void updateTypeByTelephone(String telephone,Integer type);
}