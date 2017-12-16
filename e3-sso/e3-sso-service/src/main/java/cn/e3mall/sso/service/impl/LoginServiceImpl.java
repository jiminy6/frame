package cn.e3mall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.LoginService;
@Service("loginService")
public class LoginServiceImpl implements LoginService {
	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE}")
	private int SESSION_EXPIRE;
	@Value("${USER_INFO}")
	private String USER_INFO;
	@Override
	public TaotaoResult login(String username,String password) {
		TbUserExample example= new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if(list.isEmpty()||list==null){
		return TaotaoResult.build(400,"用户名或密码错误");		
		}
		password=DigestUtils.md5DigestAsHex(password.getBytes());//登陆时候密码也经过md5加密一次
		TbUser user = list.get(0);
		if(!user.getPassword().equals(password)){
			return TaotaoResult.build(400,"用户名或密码错误");		
		}
		//如果登陆成功，产生一个token作为sessionId(uuid)
		//token作为redis的key，value为userId
		String token=UUID.randomUUID().toString();
		user.setPassword(null);//不将用户的密码保存到redis中
	    jedisClient.set(USER_INFO+":"+token,JsonUtils.objectToJson(user));//加上一个前缀
//	    jedisClient.set(USER_INFO+":"+token,user.getId().toString());//加上一个前缀
	    jedisClient.expire(USER_INFO+":"+token,SESSION_EXPIRE);//设置过期时间
		return TaotaoResult.ok(token);//将token包装成taotaoResult传递给表现层
	}

}
