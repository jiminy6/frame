package com.fun.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fun.domain.Customer;
import com.fun.domain.Linkman;
import com.fun.service.LinkmanService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


/**
 * @author luowenxin
 *
 */
@Controller
@Scope("prototype")
@Namespace("/linkman")
@ParentPackage("json-default")
public class LinkmanAction extends ActionSupport implements ModelDriven<Linkman> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Linkman linkman=new Linkman();
	private List<Linkman>linkmans;//查询的结果放入值栈中
	@Autowired
	private LinkmanService linkmanService;
	private Linkman viewLinkman;//
	private int page;// 当前页码数
	private int rows;// 每个页码显示的数目
	@Override
	public Linkman getModel() {
		return linkman;
	}
	@Action(value="linkman_findAll",results={@Result(name="success",type="json",params={"root","linkmans"})})
	public String findAll(){
		linkmans=linkmanService.findAll();
		return SUCCESS;
	}
	public List<Linkman> getLinkmans() {
		return linkmans;
	}
	@Action(value="linkman_save",results={@Result(name="success",location="${pageContext.request.contextPath }/jsp/linkman/list.jsp")})
	public String save(){
	 linkmanService.save(linkman);
	 return SUCCESS;
	}
	@Action(value="linkman_delete",results= {@Result(name="success" ,location="/jsp/linkman/list.jsp")})
	public String delete(){
		linkmanService.delete(linkman.getLkmId());
		return SUCCESS;
	}
	/**
	 * @return
	 * 根据客户的id查找,返回编辑客户的页面(数据的回显)
	 */
	@Action(value="linkman_findById",results={@Result(name="success",location="/jsp/linkman/edit.jsp")})
	public String findById(){
		viewLinkman=linkmanService.findById(linkman.getLkmId());
		return SUCCESS;
	}
	/**
	 * @return
	 * 更新客户信息,需要配置和save一样的错误信息提示(input结果集)
	 */
	@Action(value="linkman_update",results={@Result(name="success",location="/jsp/linkman/list.jsp"),@Result(name="input",location="/jsp/linkman/add.jsp") },
			interceptorRefs={@InterceptorRef(value="defaultStack",
			params={"fileUpload.maximumSize","10485760"})})
	public String update(){
		System.out.println("更新客户信息");
	    System.out.println(linkman);//检验Action有没有获得页面的信息
		linkmanService.update(linkman);
		return SUCCESS;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
