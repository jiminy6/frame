package cn.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.ReusableIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.RegisterService;
@Service("registerService")
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	private TbUserMapper tbUserMapper;
	@Override
	public TaotaoResult checkData(String param, int type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(type==1){
			criteria.andUsernameEqualTo(param);
		}
		else if(type==2){
			criteria.andPhoneEqualTo(param);
		}
		else if(type==3){
			criteria.andEmailEqualTo(param);
		}
		else {
			//因为返回的是taotaoResulst，参数异常的时候，可以将这个异常信息放入taotaoResult中，响应给页面
			//build返回响应的状态
			//ok返回响应的data
			TaotaoResult.build(400,"用户类型错误");
		}
		List<TbUser> list = tbUserMapper.selectByExample(example);
		//对集合进行null和empty的判断
		//如果有数据,就返回false
		if(!list.isEmpty()&&list!=null){
			return TaotaoResult.ok(false);
		}
		//没有数据,返回true
		return TaotaoResult.ok(true);
	}
	@Override
	public TaotaoResult register(TbUser user) {
		//为了保证数据的安全性,虽然在表现层已经做了一层校验,在这仍然需要做一层校验
		if(StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPhone())||StringUtils.isBlank(user.getPassword())){
		 return	TaotaoResult.build(400,"用户信息不完整,注册失败");
		}
		TaotaoResult result = checkData(user.getUsername(),1);
		if(!(boolean)result.getData()){
		return TaotaoResult.build(400,"用户名已经被使用");
		}
		result=checkData(user.getPhone(), 2);
		if(!(boolean)result.getData()){
			return TaotaoResult.build(400,"手机号已经被使用");
			}
		result=checkData(user.getPhone(),3);
		if(!(boolean)result.getData()){
			return TaotaoResult.build(400,"邮箱已经被使用");
			}
	    user.setCreated(new Date());
	    user.setUpdated(new Date());
	    user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));//md5加密之前的密码
		tbUserMapper.insert(user);
		return TaotaoResult.ok();
	}
	
}
