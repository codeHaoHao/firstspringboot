package cn.lijiahao.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import cn.lijiahao.session.SessionManager;
/**
 * 基础拦截器，该拦截器主要用于管理sessionId
 * @author franky
 *
 */
@Component
public class BaseInterceptor implements HandlerInterceptor{
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private SessionManager sessionManager;
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String sessionId = request.getSession().getId();
		System.out.println(sessionId);
		if (sessionManager.getSessionId(sessionId) != null
				&& sessionManager.getSessionId(sessionId).equals(sessionId)) {
			System.out.println("userId" + sessionManager.getSession(sessionId).getUserId());
			return true;
		} else {
			String sid = rememberMe(request, response);
			if (sid != null) {
				if (sessionManager.getSession(sid) != null) {
					sessionManager.setSession(sessionId, sessionManager.getSession(sid));
					return true;
				}
			}
			
			response.sendRedirect(request.getContextPath() + "/login");
			return true;
		}
	}
	
	private String rememberMe(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("sid")) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
}
