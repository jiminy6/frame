package cn.e3mall.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;

@Controller
public class OrderController {
	/**
	 * 
	     * 说明：从cookie中取出商品列表展示到页面
	     * @return
	     * @author luowenxin
	     * @time：2017年12月20日 上午10:40:53
	 */
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request){
		List<TbItem> list = getCartListFromCookie(request);
		request.setAttribute("cartList",list);
		return "order-cart";
	}
	public List<TbItem>getCartListFromCookie(HttpServletRequest request){
		String json = CookieUtils.getCookieValue(request,"cart",true);//第三个参数为是否转码
	    if(StringUtils.isBlank(json)){
	    	return new ArrayList<>();	
	    }
	    else{
	    	return JsonUtils.jsonToList(json,TbItem.class);
	    }
	}
	
}
