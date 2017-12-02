package cn.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.FixedAreaRepository;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.FixedAreaService;
@Service("fixedAreaService")
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
	@Autowired
	private FixedAreaRepository fixedAreaRository;
	@Override
	public void save(FixedArea area) {
		fixedAreaRository.save(area);
	}
	@Override
	public Page<FixedArea> page(Specification<FixedArea> spec, Pageable pageRequest) {
		return fixedAreaRository.findAll(spec, pageRequest);
	}


}
