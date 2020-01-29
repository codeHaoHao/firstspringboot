package cn.lijiahao.session;

import java.util.concurrent.TimeUnit;

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
	
	private static final long EXPIRE = 3; // 3 hours
	private static final long COOKIE_EXPIRE = 5; // 5days
	
	

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private UserService userService;
	
	private void setExpire(String sessionId) {
		String key = PREX + sessionId;
		stringRedisTemplate.expire(key, EXPIRE, TimeUnit.HOURS);
	}
	
	public void setCookieExpire(String sid) {
		String key = PREX + sid;
		stringRedisTemplate.expire(key, COOKIE_EXPIRE, TimeUnit.DAYS);
	}
	
	public String getSessionId(String sessionId) {
		String key = PREX + sessionId;
		return (String) stringRedisTemplate.opsForHash().get(key, SESSION_ID);
	}
	
	public void setCookieId(String sessionId) {
		String key = PREX + sessionId;
		stringRedisTemplate.opsForHash().put(key, SESSION_ID, sessionId);
		setCookieExpire(sessionId);
	}

	public void setSessionId(String sessionId) {
		String key = PREX + sessionId;
		stringRedisTemplate.opsForHash().put(key, SESSION_ID, sessionId);
		setExpire(sessionId);
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
	
	public void setSession(SessionBean session) {
		String sessionId = session.getSessionId();
		setSessionId(sessionId);
		setUser(sessionId, session.getUserId());
	}
	
	public void setSession(String sessionId, SessionBean session) {
		setSessionId(sessionId);
		setUser(sessionId, session.getUserId());
	}
	
	/**
	 *	登录成功后设置session信息
	 * @param sessionId
	 * @param user
	 */
	public void setLoginInfor(String sessionId, User user, boolean isRememberMe) {
		if (isRememberMe) {
			setCookieId(sessionId);
		} else {
			setSessionId(sessionId);
		}
		setUser(sessionId, user.getId());
		userService.setCache(user);
	}

}
