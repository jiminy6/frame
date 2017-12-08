package cn.itcast.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.TakeTimeRopository;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.TakeTimeService;
@Service("takeTimeService")
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
	@Autowired
	private TakeTimeRopository takeTimeRepository; 
	@Override
	public List<TakeTime> listNodel(String status) {
		List<TakeTime> takeTimeList=takeTimeRepository.findByStatus("1");
		return takeTimeList;
	}
	
}
