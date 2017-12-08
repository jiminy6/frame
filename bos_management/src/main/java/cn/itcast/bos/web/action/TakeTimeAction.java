package cn.itcast.bos.web.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.TakeTimeService;
import cn.itcast.bos.web.action.comon.BaseAction;
@Controller
@Scope("prototype")
public class TakeTimeAction extends BaseAction<TakeTime>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TakeTimeService takeTimeService;
	 
	    /**
	     * 说明：查询所有有效,(status为1)的收派时间
	     * @return
	     * @author luowenxin
	     * @time：2017年12月7日 下午8:17:41
	     */
	@Action("takeTime_listNoDel")
	public String listNoDel(){
		List<TakeTime>list=takeTimeService.listNodel("1");
		ActionContext.getContext().getValueStack().push(list);
		return JSON;
	}

}
