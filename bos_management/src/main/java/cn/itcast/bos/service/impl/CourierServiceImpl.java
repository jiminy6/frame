package cn.itcast.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.CourierRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.CourierService;
@Service("courierService")
@Transactional
public class CourierServiceImpl implements CourierService {
	@Autowired
	private CourierRepository courierRepository;
	@Override
	public void save(Courier courier) {
	courierRepository.save(courier);
	}
	@Override
	@Transactional(readOnly=true)
	public Page<Courier> page(Specification<Courier> spec,Pageable pageRequest) {
		return courierRepository.findAll(spec,pageRequest);
	}
	@Override
	public void deletBatch(String ids) {
		if(StringUtils.isNoneBlank(ids)){
			String[] array = ids.split(",");
			for (String id : array) {
				courierRepository.deleteBatch(Integer.parseInt(id),'1');
			}
		}
	}
	@Override
	public List<Courier> findNoDeltag() {
		List<Courier> courierList=courierRepository.findByDeltag('0');
		return courierList;
	}

}
