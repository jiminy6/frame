package cn.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.WaybillRepository;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.service.WaybillService;
@Service("waybillService")
@Transactional
public class WaybillServiceImpl implements WaybillService{
	@Autowired
	private WaybillRepository waybillRepository;

	@Override
	public void save(WayBill waybill) {
		waybillRepository.save(waybill);
	}

	@Override
	public Page<WayBill> page(Pageable pageRequest) {
	return waybillRepository.findAll(pageRequest);
	}
	
}
