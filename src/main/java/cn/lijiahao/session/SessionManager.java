package cn.lijiahao.session;

import cn.lijiahao.dao.cache.UserCache;
import cn.lijiahao.po.User;
import cn.lijiahao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 用来管理session
 */
@Component
public class SessionManager {

	private static final String PREX = "session:";

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private UserService userService;
	
	
	public String getSessionId(String sessionId) {
		String key = PREX + sessionId;
		return (String) stringRedisTemplate.opsForHash().get(key, "sessionId");
	}
	
	public void setSessionId(String sessionId) {
		String key = PREX + sessionId;
		stringRedisTemplate.opsForHash().put(key, "sessionId", sessionId);
	}

	/**
	 * 根据sessionID获取对应的session信息
	 * @param sessionId
	 * @return
	 */
	public SessionBean getSession(String sessionId){
		String key = PREX + sessionId;
		SessionBean sessionBean = new SessionBean();
		int userId = Integer.parseInt((String) stringRedisTemplate.opsForHash().get(key,"userId"));
		User user = userService.get(userId); // 获取user
		sessionBean.setSessionId(sessionId);
		sessionBean.setUserId(userId);
		sessionBean.setUser(user);
		return sessionBean;
	}

}
