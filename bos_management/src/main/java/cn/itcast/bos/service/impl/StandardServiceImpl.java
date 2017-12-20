package cn.itcast.bos.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.StandardRepository;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.StandardService;

 /**
     * 说明：
     * @author luowenxin
     * @version 1.0
     * @date 2017年11月27日
     */
@Service("standardService")
@Transactional
public class StandardServiceImpl implements StandardService{
	@Autowired
	private StandardRepository standardDao;
	 
	    /**
	     * 说明：这个是保存standard的方法
	     * @param standard
	     * @author luowenxin
	     * @time：2017年11月26日 上午10:55:50
	     */
	@RequiresRoles("base1")
	public void save(Standard standard){
		standard.setOperatingTime(new Date());
		standardDao.save(standard);
	}
	/* (non-Javadoc)
	 * @see cn.itcast.bos.service.StandardService#findAll()
	 */
	@Transactional(readOnly=true)
	public List<Standard> findAll() {
		return standardDao.findAll();
	}
	/* (non-Javadoc)
	 * @see cn.itcast.bos.service.StandardService#findByPage(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Standard> findByPage(Pageable pageable) {
		return standardDao.findAll(pageable);
	}
	
	
}
