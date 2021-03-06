package cn.itcast.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.base.Area;

public interface AreaRepository extends JpaRepository<Area,String>{
	//根据省市区来查询
	Area findByProvinceAndCityAndDistrict(String province,String city,String district);
}
