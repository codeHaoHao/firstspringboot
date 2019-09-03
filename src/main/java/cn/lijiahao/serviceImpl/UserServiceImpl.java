package cn.lijiahao.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lijiahao.dao.UserDao;
import cn.lijiahao.po.User;
import cn.lijiahao.service.UserService;
@Service("UserService")
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;

	@Override
	public User selectByUser(User user) {
		return userDao.selectByUser(user);
	}

	@Override
	public User selectByPrimaryKey(int id) {
		return userDao.selectByPrimaryKey(id);
	}

	@Override
	public int update(User user) {
		return userDao.update(user);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		return userDao.deleteByPrimaryKey(id);
	}

	@Override
	public int add(User user) {
		return userDao.add(user);
	}

}
