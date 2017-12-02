package cn.itcast.bos.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Standard;
//接收两个参数,实体类型,主键类型
public interface StandardRepository  extends JpaRepository<Standard,Integer>{
	public Standard findByName(String name);
	@Query("select  name from Standard where id=?")
	public String findNameById(Integer id);
	@Query("from Standard where name =?2 and id=?1")
	public Standard findByNameAndId(Integer id,String name);
}
