package cn.itcast.bos.web.action.take_delivery;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.take_delivery.Promotion;
import cn.itcast.bos.service.PromotionService;
import cn.itcast.bos.web.action.comon.BaseAction;
@Controller
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion> {

	/**
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private PromotionService promotionService;
	@Action(value="promotion_add",results={
			@Result(type=REDIRECT,location="/pages/take_delivery/promotion.html")})
	public String addPromotion(){
		promotionService.save(model);
		return SUCCESS;
	}
}
