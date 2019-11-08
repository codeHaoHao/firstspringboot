package cn.lijiahao.dao;

import org.apache.ibatis.annotations.Mapper;

import cn.lijiahao.po.User;
@Mapper
public interface UserDao extends Dao<User>{
	User selectByUser(User user);
	User selectByPrimaryKey(int id);
	int update(User user);
	int deleteByPrimaryKey(int id);
	int add(User user);
}
