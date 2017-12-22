package cn.e3mall.cart.service;

import java.util.List;

import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;

public interface CartService {
	/**
	 * 
	     * 说明：添加购物车
	     * @param user 
	     * @param item
	     * @param num
	     * @return
	     * @author luowenxin
	     * @time：2017年12月19日 上午9:07:53
	 */
	public TaotaoResult addCart(Long userId,Long itemId,Integer num);
	/**
	 * 
	     * 说明：合并cookie中的购物车和服务端的购物车
	     * @return
	     * @author luowenxin
	     * @time：2017年12月19日 上午11:19:11
	 */
	public TaotaoResult mergerCart(Long userId,List<TbItem>items);
	/**
	 * 
	     * 说明：从服务端取出购物车列表
	     * @param id
	     * @return
	     * @author luowenxin
	     * @time：2017年12月20日 上午11:38:01
	 */
	public List<TbItem> getCartList(Long userId);
	
}
