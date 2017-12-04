package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.EasyUiDataGridResult;
import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

 /**
     * 说明：商品service
     * @author luowenxin
     * @version 1.0
     * @date 2017年11月27日
     */
@Service("itemService")
@Transactional
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Override
	public TbItem getItemById(Long id) {
		TbItem selectByPrimaryKey = itemMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;	
	}
	@Override
	public EasyUiDataGridResult getItemList(int page,int rows) {
		PageHelper.startPage(page,rows);//设置分页信息
		TbItemExample example = new TbItemExample();
	    List<TbItem> list = itemMapper.selectByExample(example);//执行查询
	    EasyUiDataGridResult result = new EasyUiDataGridResult();
	    result.setRows(list);
	    //取分页结果
	    PageInfo<TbItem> pageInfo = new PageInfo<>(list);
	    long total = pageInfo.getTotal();//设置总记录数	
	    result.setTotal(total);
	    return result;
	}
	@Override
	/**
	 *  生成商品的id
	 *  设置商品的状态:1正常,2:下架,3:删除
	 *  填充tbitem的内容
	 *  填充tbitemDesc
	 *  
	 */
	public TaotaoResult addItem(TbItem tbitem, String desc) {
		long itemId = IDUtils.genItemId();
		tbitem.setStatus((byte) 1);
		Date date = new Date();
		tbitem.setCreated(date);
		tbitem.setUpdated(date);
		tbitem.setId(itemId);
		itemMapper.insert(tbitem);//插入tbitem
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(date);
		tbItemDesc.setItemId(itemId);
		itemDescMapper.insert(tbItemDesc);
		return TaotaoResult.ok();
	}
}
