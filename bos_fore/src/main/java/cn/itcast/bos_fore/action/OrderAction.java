package cn.itcast.bos_fore.action;


import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.bos.web.action.comon.BaseAction;
import cn.itcast.utils.Constants;
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sendAreaInfo;//属性驱动注入sendAreaInfo
	private String recAreaInfo;//属性驱动注入recAreaInfo
	    public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}
		public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}

		/**
	     * 说明：新单的保存
	     * 从sendAreaInfo中抽取信息,重新封装model
	     * 这里不能使用new 一个Area ,然后设置它的属性,因为这个是瞬时态的.我们需要先将它转成持久态,然后操作它
	     * 转成持久态的方法,通过hibernate 查出对象,然后进行操作
	     * @return
	     * @author luowenxin
	     * @time：2017年12月7日 下午11:43:22
	     */
	@Action(value="order_add",results={@Result(type=REDIRECT,location="/index.html")})
	public String add(){
		String[] strings = StringUtils.split(sendAreaInfo,"/");//获得的字符串数组第一个是省,第二个是市,第三个是区
		String province = strings[0];
		String city = strings[1];
		String distinct = strings[2];
		Area sendArea = new Area();//重新封装area
		sendArea.setCity(city);
		sendArea.setDistrict(distinct);
		sendArea.setProvince(province);
		model.setSendArea(sendArea);
		String[] recAreaInfoArray = StringUtils.split(recAreaInfo,"/");
		Area recArea=new Area();
		recArea.setProvince(recAreaInfoArray[0]);
		recArea.setCity(recAreaInfoArray[1]);
		recArea.setDistrict(recAreaInfoArray[2]);
		model.setRecArea(recArea);
		WebClient.create(Constants.BOS_MANAGEMENT_URL+"/services/orderservice/orders").
		type(MediaType.APPLICATION_JSON)
		.post(model);
		return SUCCESS;
	}
}
