package cn.itcast.bos.indexDao;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import cn.itcast.bos.domain.take_delivery.WayBill;

public interface WayBillEsRepository extends ElasticsearchRepository<WayBill,Integer> {
	
}
