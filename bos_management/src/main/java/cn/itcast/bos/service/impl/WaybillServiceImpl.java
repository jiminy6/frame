package cn.itcast.bos.service.impl;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.WaybillRepository;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.indexDao.WayBillEsRepository;
import cn.itcast.bos.service.WaybillService;
@Service("waybillService")
@Transactional
public class WaybillServiceImpl implements WaybillService{
	@Autowired
	private WaybillRepository waybillRepository;
	@Autowired
	private WayBillEsRepository wayBillEsRepository;
	@Override
	public void save(WayBill waybill) {
		waybillRepository.save(waybill);
		wayBillEsRepository.save(waybill);//执行保存之后，同时将数据保存到数据库和索引库中
	}

	@Override
	public Page<WayBill> page(Pageable pageRequest) {
	return waybillRepository.findAll(pageRequest);
	}

	@Override
	/**
	 * 调用repository进行查询
	 */
	public Page<WayBill> findWayBillListPage(Pageable pageRequest, String fieldName, String fieldValue) {
		//精确匹配
		TermQueryBuilder termQueryBuilder = new TermQueryBuilder(fieldName, fieldValue);
		//通配符匹配
		WildcardQueryBuilder queryBuilder = new WildcardQueryBuilder(fieldName, "*"+fieldValue+"*");
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		//采用should模式,匹配多个规则
		boolQueryBuilder.should(queryBuilder);
		boolQueryBuilder.should(termQueryBuilder);
		return 	wayBillEsRepository.search(boolQueryBuilder, pageRequest);
	}
	
}
