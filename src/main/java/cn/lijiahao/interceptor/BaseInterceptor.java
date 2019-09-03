package cn.lijiahao.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class BaseInterceptor implements HandlerInterceptor{
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String sessionId = request.getSession().getId();
		if (stringRedisTemplate.opsForHash().get(sessionId, "sessionId") != null
				&& stringRedisTemplate.opsForHash().get(sessionId, "sessionId").equals(sessionId)) {
			response.sendRedirect("/sigin");
			return true;
		}else {
			request.getRequestDispatcher(request.getContextPath()+"/sigin").forward(request, response);
			
			return true;
		}
	}
}
