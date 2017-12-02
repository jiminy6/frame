package cn.itcast.bos.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Courier;

 /**
     * 说明：这个接口提供了分页和条件查询的支持
     * jpaSpecificationExecutor不能够单独使用，必须基于jpaRepository
     * @author luowenxin
     * @version 1.0
     * @date 2017年11月28日
     */
public interface CourierRepository extends JpaRepository<Courier, Integer> ,JpaSpecificationExecutor<Courier> {
	@Override
	Page<Courier> findAll(Specification<Courier> spec, Pageable pageable);
	
	@Query("update Courier set deltag=?2 where id=?1")
	@Modifying
	public void deleteBatch(Integer id, char deltag);
}
