package cn.lijiahao.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	
	public String getSessionId(String sessionId) {
		return (String) stringRedisTemplate.opsForHash().get(sessionId, "sessionId");
	}
	
	public void setSessionId(String sessionId) {
		stringRedisTemplate.opsForHash().put(sessionId, "sessionId", sessionId);
	}
}
