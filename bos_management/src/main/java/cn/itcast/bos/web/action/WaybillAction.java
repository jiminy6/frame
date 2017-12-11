package cn.itcast.bos.web.action;



import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.service.WaybillService;
import cn.itcast.bos.web.action.comon.BaseAction;
@Controller
@Scope("prototype")
public class WaybillAction extends BaseAction<WayBill>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private WaybillService waybillService;
	 
	    /**
	     * 说明：这里采用异常判断的方式来判断程序是否正确运行，除了这个，还可以通过获取响应码的方式
	     * @return
	     * @author luowenxin
	     * @time：2017年12月11日 上午12:18:10
	     */
	@Action("waybill_addquick")
	public String addWaybill(){
		Map<String, Object> map = new HashMap<>();
		try {
			waybillService.save(model);
			map.put("result",true);
			
		} catch (Exception e) {
			e.printStackTrace();
		   map.put("result",false);
		}
		ActionContext.getContext().getValueStack().push(map);
		return JSON;
	}
	@Action("waybill_page")
	public String page(){
		//加入了排序的选项
		Pageable pageRequest =  new PageRequest(page-1,rows,new Sort(Direction.DESC,"id"));
		Page<WayBill> pageResponse=waybillService.page(pageRequest);
		putDataToStack(pageResponse);//结果存入值栈中
		return JSON;
	}
}
