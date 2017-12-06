package cn.itcast.bos_fore.action;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
public class DemoAction extends ActionSupport{
	@Action("demo")
	public String demo(){
		System.out.println("进来了");
		return NONE;
	}
}
