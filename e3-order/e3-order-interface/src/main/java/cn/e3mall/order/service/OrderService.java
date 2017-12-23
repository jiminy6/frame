package cn.e3mall.order.service;

import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.order.pojo.OrderInfo;

public interface OrderService {

	TaotaoResult create(OrderInfo orderInfo);
}
