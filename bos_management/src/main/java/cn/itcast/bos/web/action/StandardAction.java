package cn.itcast.bos.web.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;


import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.StandardService;
import cn.itcast.bos.web.action.comon.BaseAction;
 /**
     * 说明：
     * @author luowenxin
     * @version 1.0
     * @date 2017年11月26日
     */
@Controller
@Scope("prototype")
//struts的注解继承自父类
public class StandardAction  extends BaseAction<Standard>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Standard> list;//全部内容列表
//	private int page;//页面传来的当前页码数
//	private int rows;//页面传来的每页显示数
//	public void setPage(int page) {
//		this.page = page;
//	}
//	public void setRows(int rows) {
//		this.rows = rows;
//	}
//	private Map<String,Object> map=new HashMap<>();//封装total和rows的map集合
	@Autowired
	private StandardService standardSerivice;
	
	public List<Standard> getList() {
		return list;
	}
//	public Map<String, Object> getMap() {
//		return map;
//	}
	@Action(value="standard_save",results={@Result(type="redirect", location="/pages/base/standard.html")})
	public String save(){
		standardSerivice.save(model);
		return SUCCESS;
	}
	 
	    /**
	     * 说明：这是收派标准的分页查询方法.返回两个参数count,rows.先计算出count.将这两个参数封装到一个map集合中
	     * 页面中传来两个参数page(当前的页码数),rows(每页显示的数量)
	     * springJpa中自带分页API,参数中page索引是以为0开始的
	     * @return
	     * @author luowenxin
	     * @time：2017年11月26日 上午11:18:20
	     */
	@Action(value="standard_page")
	//,results={@Result(name=JSON,type=JSON,params={"root","map"})}
	public String page(){
		Pageable pageable  = new PageRequest(page-1, rows);
		 Page<Standard> pageResponse = standardSerivice.findByPage(pageable);
//		 long total = pageResponse.getTotalElements();//利用jpa中API查出总数
//		  List<Standard> content = pageResponse.getContent();//利用jpa中API查出每页的内容
//		  map.put("total", total);
//		  map.put("rows",content);
		 putDataToStack(pageResponse);
		return JSON;
	}
	    /**
	     * 说明：查询所有的收派标准
	     * @return
	     * @author luowenxin
	     * @time：2017年11月26日 下午3:54:12
	     */
	@Action(value="standard_findAll",results={@Result(type=JSON,params={"root","list"})})
	public String findAll(){
	     list=standardSerivice.findAll();
	    System.out.println(list);
		return SUCCESS;
	}
		
}
