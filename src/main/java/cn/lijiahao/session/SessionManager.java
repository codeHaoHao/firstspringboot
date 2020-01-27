package cn.lijiahao.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import cn.lijiahao.po.User;
import cn.lijiahao.service.UserService;

/**
 * 用来管理session hash结构存储session信息 key ： session:sessionId value :
 * session:4564648454456465 useId:4546
 */
@Component
public class SessionManager {

	private static final String PREX = "session:";
	
	private static final String SESSION_ID = "sessionId";
	private static final String USER_ID = "userId";
	

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private UserService userService;

	public String getSessionId(String sessionId) {
		String key = PREX + sessionId;
		return (String) stringRedisTemplate.opsForHash().get(key, SESSION_ID);
	}

	public void setSessionId(String sessionId) {
		String key = PREX + sessionId;
		stringRedisTemplate.opsForHash().put(key, SESSION_ID, sessionId);
	}

	public User getUser(String sessionId) {
		String key = PREX + sessionId;
		int userId = Integer.parseInt((String) stringRedisTemplate.opsForHash().get(key, USER_ID));
		User user = userService.get(userId);
		return user;
	}
	
	public void setUser(String sessionId, int userId) {
		String key = PREX + sessionId;
		stringRedisTemplate.opsForHash().put(key, USER_ID, userId + "");
	}

	/**
	 * 根据sessionID获取对应的session信息
	 * 
	 * @param sessionId
	 * @return
	 */
	public SessionBean getSession(String sessionId) {
		String key = PREX + sessionId;
		SessionBean sessionBean = new SessionBean();
		int userId = Integer.parseInt((String) stringRedisTemplate.opsForHash().get(key, USER_ID));
		User user = userService.get(userId); // 获取user
		sessionBean.setSessionId(sessionId);
		sessionBean.setUserId(userId);
		sessionBean.setUser(user);
		return sessionBean;
	}
	
	/**
	 *	登录成功后设置session信息
	 * @param sessionId
	 * @param user
	 */
	public void setLoginInfor(String sessionId, User user) {
		setSessionId(sessionId);
		setUser(sessionId, user.getId());
		userService.setCache(user);
	}

}
