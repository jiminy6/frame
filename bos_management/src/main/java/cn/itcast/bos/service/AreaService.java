package cn.itcast.bos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Area;

public interface AreaService {

	void save(ArrayList<Area> list);

	Page<Area> page(Pageable pageRequest);

}
