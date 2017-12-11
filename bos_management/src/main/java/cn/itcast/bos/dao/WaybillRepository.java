package cn.itcast.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.take_delivery.WayBill;

public interface WaybillRepository extends JpaRepository<WayBill,Integer>{

}
