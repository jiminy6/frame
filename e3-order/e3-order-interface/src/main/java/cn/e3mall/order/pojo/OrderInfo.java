package cn.e3mall.order.pojo;

import java.io.Serializable;
import java.util.List;

import cn.e3mall.pojo.TbOrder;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;

 /**
     * 说明：tbOrder的增强类，增加了商品明细列表和配送信息
     * @author luowenxin
     * @version 1.0
     * @date 2017年12月22日
     */
public class OrderInfo extends TbOrder implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private List<TbOrderItem> orderItems;//商品明细列表
	private TbOrderShipping tbOrderShipping;//配送信息
	public TbOrderShipping getTbOrderShipping() {
		return tbOrderShipping;
	}
	public void setTbOrderShipping(TbOrderShipping tbOrderShipping) {
		this.tbOrderShipping = tbOrderShipping;
	}
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
}
