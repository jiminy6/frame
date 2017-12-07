package cn.itcast.bos_fore.action;



import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jws.WebParam.Mode;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.web.action.comon.BaseAction;
import cn.itcast.bos_fore.utils.*;
import cn.itcast.crm.domain.Customer;
import cn.itcast.utils.Constants;
@Controller
public class CustomerAction extends BaseAction<Customer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	    /**
	     * 说明：发送验证码
	     * @return
	     * @author luowenxin
	     * @time：2017年12月5日 下午8:30:23
	     */
	//从模型驱动中获取telephone
	//获取的checkcode存入session中
	@Autowired
	private JmsTemplate jmsTemplate;
	@Action("customer_sendCheckcode")
	public String sendCheckCode(){
		final String checkcode = RandomStringUtils.randomNumeric(4);//生成四个随机码
		ServletActionContext.getRequest().getSession().setAttribute(model.getTelephone(), checkcode);
//		SmsUtils.sendSmsCheckCode(checkcode, model.getTelephone());
		jmsTemplate.send("bos.mq.checkcode",new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setString("checkcode",checkcode);
				mapMessage.setString("telephone",model.getTelephone());
				return mapMessage;
			}
		});//将短信内容发送给mg服务器
		return NONE;
	}
	//属性驱动获得checkcode
	private  String  checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	 
	    /**
	     * 说明：customer注册。首先判断客户输入的验证码和session中的是否一致
	     * @param customer
	     * @return
	     * @author luowenxin
	     * @time：2017年12月6日 上午12:32:33 value="customer_regist"
	     */
	@Action(value="customer_regist",results={
			@Result(type=REDIRECT,location="/signup-success.html"),
			@Result( name=INPUT, type=REDIRECT,location="/signup.html")
			})
	public String regist(){
//		String sessionCode = (String) ActionContext.getContext().getSession().get(model.getTelephone());//session中的验证码
		String sessionCode = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());//session中的验证码
		//如果验证不通过
		if(StringUtils.isBlank(checkcode)||!checkcode.equals(sessionCode)){
			return INPUT;
		}
		String subject="bos商城信息";
		String activeUrl=Constants.BOS_FORE_URL+"/customer_active.action";//激活的action
		String activeCode = RandomStringUtils.randomNumeric(32);//随机码
		String urlAddress=activeUrl+"?activeCode="+activeCode+"&telephone="+model.getTelephone();
		String content="<h3>请点击地址激活:<a href="+urlAddress+">"+urlAddress;
		String to=model.getEmail();
		MailUtils.sendMail(subject, content, to);
		WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
				.type(MediaType.APPLICATION_JSON)
				.post(model);
		redisTemplate.opsForValue().set(model.getTelephone(),activeCode,24,TimeUnit.HOURS);//将内容存入redis中
		return SUCCESS;
	}
	 
	    /**
	     * 说明：获取url中的activecode和电话号码，判断active是否有效
	     * 将activecode和redis中的active比较，如果成功就将电话号码对应的customer激活
	     * @return
	     * @author luowenxin
	     * @time：2017年12月6日 下午3:30:14
	     */
	private String activeCode;
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	@Action("customer_active")
	public String activeCustomer() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");//响应乱码解决
		String redisCode = redisTemplate.opsForValue().get(model.getTelephone());
		//如果activecode不相同
		if(StringUtils.isBlank(redisCode)||!redisCode.equals(activeCode)){
			response.getWriter().println("对不起,验证码已经失效");
			return NONE;
		}
		WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
		.path("/type")
		.path("/"+model.getTelephone())
		.path("/"+1)
		.type(MediaType.APPLICATION_JSON)
		.put(null);
		response.getWriter().println("恭喜,激活成功");
		redisTemplate.delete(model.getTelephone());//成功之后删除激活码
		return NONE;
	}
}
