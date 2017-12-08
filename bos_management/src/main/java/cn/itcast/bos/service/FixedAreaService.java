package cn.itcast.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.FixedArea;

public interface FixedAreaService {

	void save(FixedArea area);

	Page<FixedArea> page(Specification<FixedArea> spec, Pageable pageRequest);

	void associationCourierToFixedArea(FixedArea model,Integer courierId, Integer takeTimeId);

}
