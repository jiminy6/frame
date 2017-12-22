package cn.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.PromotionRepository;
import cn.itcast.bos.domain.take_delivery.Promotion;
import cn.itcast.bos.service.PromotionService;
@Service("promotionService")
@Transactional
public class PromotionServiceImple implements PromotionService {
	@Autowired
	private PromotionRepository promotionRepository;
	@Override
	public void save(Promotion promotion) {
		promotionRepository.save(promotion);
	}

}
