package com.fun.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fun.domain.BaseDict;
import com.fun.service.BaseDictService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
*@author:luowenxin
*@date: 2017年11月15日
**/
@Controller
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/baseDict")
public class BaseDictAction extends ActionSupport implements ModelDriven<BaseDict> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BaseDict baseDict=new BaseDict();//实例化对象
	
	private List<BaseDict>baseDicts;//将后端获取到的list集合数据转换成json数据,并存入值栈之中,提供它的get方法
	
	public List<BaseDict> getBaseDicts() {
		return baseDicts;
	}
	@Autowired
	private BaseDictService baseDictService;
	
	@Action(value="baseDict_findByTypeCode",results={@Result(name="success",type="json",params={"root","baseDicts"})})
	public String findByTypeCode(){
		baseDicts=baseDictService.findByTypeCode(baseDict.getDictTypeCode());
		return SUCCESS;
	}
	@Override
	public BaseDict getModel() {
		return baseDict;
	}
	
}
