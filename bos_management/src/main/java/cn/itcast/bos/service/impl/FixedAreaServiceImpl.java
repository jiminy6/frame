package cn.itcast.bos.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.CourierRepository;
import cn.itcast.bos.dao.FixedAreaRepository;
import cn.itcast.bos.dao.TakeTimeRopository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.FixedAreaService;
@Service("fixedAreaService")
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
	@Autowired
	private FixedAreaRepository fixedAreaRository;
	@Autowired
	private CourierRepository courierRepository;
	@Autowired
	private TakeTimeRopository takeTimeRepository;
	@Override
	public void save(FixedArea area) {
		fixedAreaRository.save(area);
	}
	@Override
	@Transactional(readOnly=true)
	public Page<FixedArea> page(Specification<FixedArea> spec, Pageable pageRequest) {
		return fixedAreaRository.findAll(spec, pageRequest);
	}
	/* (non-Javadoc)
	 * @see cn.itcast.bos.service.FixedAreaService#associationCourierToFixedArea(cn.itcast.bos.domain.base.FixedArea, java.lang.Integer, java.lang.Integer)
	 * 多表通过hibernate快照，先查后改
	 */
	@Override
	public void associationCourierToFixedArea(FixedArea fixedArea, Integer courierId, Integer takeTimeId) {
		FixedArea fr = fixedAreaRository.findOne(fixedArea.getId());
		Courier courier = courierRepository.findOne(courierId);
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		//关联
		Set<Courier> couriers = fr.getCouriers();
		fr.getCouriers().add(courier);
		courier.setTakeTime(takeTime);
	}


}
