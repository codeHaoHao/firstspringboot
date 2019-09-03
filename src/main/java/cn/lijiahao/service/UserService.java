package cn.lijiahao.service;

import cn.lijiahao.po.User;

public interface UserService {
	User selectByUser(User user);
	User selectByPrimaryKey(int id);
	int update(User user);
	int deleteByPrimaryKey(int id);
	int add(User user);
}
