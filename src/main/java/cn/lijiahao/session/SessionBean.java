package cn.lijiahao.session;

import cn.lijiahao.po.User;

/**
 * 用来封装session信息
 * session :  hash
 * 			key : 'session : sessionId'
 * 			field : sessionId、userId
 */
public class SessionBean {
	private String sessionId;
	private int userId;


	private User user;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
