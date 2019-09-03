package cn.lijiahao.dao;

import org.springframework.stereotype.Repository;

import cn.lijiahao.po.User;
@Repository
public interface UserDao {
	User selectByUser(User user);
	User selectByPrimaryKey(int id);
	int update(User user);
	int deleteByPrimaryKey(int id);
	int add(User user);
}
