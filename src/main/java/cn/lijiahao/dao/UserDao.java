package cn.lijiahao.dao;

import cn.lijiahao.po.User;

public interface UserDao {
	int selectByPrimaryKey(int id);
	int update(User user);
	int deleteByPrimaryKey(int id);
	int add(User user);
}
