package cn.lijiahao.serviceImpl;

import cn.lijiahao.dao.cache.UserCache;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lijiahao.dao.UserDao;
import cn.lijiahao.po.User;
import cn.lijiahao.service.UserService;

@Service("UserService")
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserCache userCache;

	/**
	 * 向父类中注入dao和cache
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		this.setCache(userCache);
		this.setDao(userDao);
	}

	@Override
    public User selectByUser(User user) {
        return userDao.selectByUser(user);
    }

    @Override
    public User selectByPrimaryKey(int id) {
        return get(id);
    }

    @Override
    public int update(User user) {
        int r = userDao.update(user);
        userCache.remove(user.getId());
    	return r;
    }

    @Override
    public int deleteByPrimaryKey(int id) {
        return remove(id);
    }

    @Override
    public int add(User user) {
        return userDao.add(user);
    }

}
