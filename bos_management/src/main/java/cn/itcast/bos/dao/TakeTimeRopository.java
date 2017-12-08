package cn.itcast.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.base.TakeTime;

public interface TakeTimeRopository extends JpaRepository<TakeTime,Integer> {

	List<TakeTime> findByStatus(String status);

}
