package cn.itcast.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.take_delivery.WayBill;

public interface WaybillService {

	void save(WayBill model);

	Page<WayBill> page(Pageable pageRequest);

	Page<WayBill> findWayBillListPage(Pageable pageRequest, String fieldName, String fieldValue);
}
