package cn.e3mall.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
@Service("tokenService")
public class TokenServiceImpl implements TokenService {
	@Value("${USER_INFO}")
	private String USER_INFO;
	@Value("${SESSION_EXPIRE}")
	private int SESSION_EXPIRE;
	@Autowired
	private JedisClient jedisClient;
	@Override
	public TaotaoResult getUserByToken(String token) {
		String json = jedisClient.get(USER_INFO+":"+token);
		if(StringUtils.isBlank(json)){//如果没有取到，登陆过期了。
			return TaotaoResult.build(201,"登陆已过期");
		}
		else{//如果没有过期，重新设置过期时间
			jedisClient.expire(USER_INFO+":"+token,SESSION_EXPIRE);
			TbUser user = JsonUtils.jsonToPojo(json,TbUser.class);//将json对象转成pojo
			return TaotaoResult.ok(user);//用taotaoResult包装user返回到表现层
		}
	}

}
