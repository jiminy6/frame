package cn.e3mall.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.mapper.TbOrderItemMapper;
import cn.e3mall.mapper.TbOrderMapper;
import cn.e3mall.mapper.TbOrderShippingMapper;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	@Autowired
	private TbOrderShippingMapper tbOrderShippingMapper;
	@Value("${ORDER_ID_BEGIN}")
	private String ORDER_ID_BEGIN;//开始的号码
	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_ITEM_GEN_KEY}")
	private String ORDER_ITEM_GEN_KEY;
	@Override
	public TaotaoResult create(OrderInfo orderInfo) {
		//为了订单的可读性，订单号使用redis中的incr。订单设置初始值。
		if(!jedisClient.exists(ORDER_GEN_KEY)){
			//如果这个key先不存在，就设置它的默认值，如果已经有了，就直接执行incr
			jedisClient.set("ORDER_GEN_KEY",ORDER_ID_BEGIN);
		}
		Long orderId = jedisClient.incr(ORDER_GEN_KEY);
		System.out.println("================================"+orderId);
		orderInfo.setOrderId(orderId.toString());//设置订单的id
		orderInfo.setPostFee("0");//设置支付方式
		//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		TbOrderShipping shipping = orderInfo.getTbOrderShipping();//收货地址
		shipping.setOrderId(orderId.toString());
		shipping.setCreated(new Date());
		shipping.setUpdated(new Date());
		tbOrderShippingMapper.insert(shipping);//向订单物流表中插入数据
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
	    for (TbOrderItem tbOrderItem : orderItems) {
	    	Long orderItemId = jedisClient.incr(ORDER_ITEM_GEN_KEY);
	    	tbOrderItem.setId(orderItemId.toString());//设置订单详细的id
	    	tbOrderItem.setOrderId(orderId.toString());
	    	tbOrderItemMapper.insert(tbOrderItem);//向订单详细表中添加数据
		}
		orderMapper.insert(orderInfo);
		return TaotaoResult.ok(orderId);//返回订单号
	}	
}
