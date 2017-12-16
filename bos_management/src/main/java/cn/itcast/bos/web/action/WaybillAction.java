package cn.itcast.bos.web.action;



import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

import com.ctc.wstx.util.StringUtil;
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
	 
	    /**
	     * 说明：增加了es搜索的选项
	     * @return
	     * @author luowenxin
	     * @time：2017年12月15日 上午12:16:17
	     */
	//通过属性驱动获得页面上的fieldName,fieldValue作为keyword,如果这个字段为空就直接全部查询,走数据库
	//不是用属性驱动获取active,goodstype属性,easyUi的搜索插件中的doSearch中传的是fieldValue和fieldName属性.
	private String fieldName;
	private String fieldValue;
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	@Action("waybill_page")
	public String page(){
		//加入了排序的选项
		Pageable pageRequest =new PageRequest(page-1,rows,new Sort(Direction.DESC,"id"));
		//如果这个为空,就直接走数据库查询
		//不需要判断fieldName,因为这个字段不可能为空
		Page<WayBill> pageResponse=null;
		if(StringUtils.isBlank(fieldValue)){
			 pageResponse=waybillService.page(pageRequest);
		}
		else{
			pageResponse=waybillService.findWayBillListPage(pageRequest,fieldName,fieldValue);
		}
		putDataToStack(pageResponse);//结果存入值栈中
		return JSON;
	}
}
