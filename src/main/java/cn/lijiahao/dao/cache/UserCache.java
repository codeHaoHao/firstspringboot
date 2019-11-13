package cn.lijiahao.dao.cache;

import cn.lijiahao.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * user class cache, use redis
 * type : hashmap
 * key : user:id
 */
@Component("UserCache")
public class UserCache implements Cache<User> {
    private static final String PREX = "user:";

    private static final long EXPIRE = 30; // 30 minutes

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public User get(int id) {
        String key = PREX + id;
        User user = null;
        if (stringRedisTemplate.hasKey(key)) {
            stringRedisTemplate.multi();
            String username = (String) stringRedisTemplate.opsForHash().get(key, "username");
            String password = (String) stringRedisTemplate.opsForHash().get(key, "password");
            String name = (String) stringRedisTemplate.opsForHash().get(key, "name");
            String phoneNum = (String) stringRedisTemplate.opsForHash().get(key, "phoneNum");
            String email = (String) stringRedisTemplate.opsForHash().get(key, "email");
            String s_dataOfBirth = (String) stringRedisTemplate.opsForHash().get(key, "dataOfBirth");
            long dataOfBirth = s_dataOfBirth == null ? -1 : Long.parseLong(s_dataOfBirth);
            String s_age = (String) stringRedisTemplate.opsForHash().get(key, "age");
            int age = s_age == null ? -1 : Integer.parseInt(s_age);
            char gender = (char) stringRedisTemplate.opsForHash().get(key, "gender");
            String salt = (String) stringRedisTemplate.opsForHash().get(key, "salt");
            char locked = (char) stringRedisTemplate.opsForHash().get(key, "locked");
            String avatar = (String) stringRedisTemplate.opsForHash().get(key, "avatar");
            String individualResume = (String) stringRedisTemplate.opsForHash().get(key, "individualResume");
            stringRedisTemplate.exec();
            user = new User(id, username, password, name, phoneNum, email, dataOfBirth, age, gender, salt, locked, avatar, individualResume);
        }
        return user;
    }

    @Override
    public void set(User bean) {
        String key = PREX + bean.getId();
        stringRedisTemplate.multi();
        if (bean.getUsername() != null && !bean.getUsername().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "username", bean.getUsername());
        } else if (bean.getPassword() != null && !bean.getPassword().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "password", bean.getPassword());
        } else if (bean.getName() != null && !bean.getName().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "name", bean.getName());
        } else if (bean.getPhoneNum() != null && !bean.getPhoneNum().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "phoneNum", bean.getPhoneNum());
        } else if (bean.getEmail() != null && !bean.getEmail().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "email", bean.getEmail());
        } else if (bean.getDataOfBirth() > 0) {
            stringRedisTemplate.opsForHash().put(key, "dataOfBirth", bean.getDataOfBirth());
        } else if (bean.getAge() > 0) {
            stringRedisTemplate.opsForHash().put(key, "age", bean.getAge());
        } else if (bean.getGender() == 0 || bean.getGender() == 1) {
            stringRedisTemplate.opsForHash().put(key, "gender", bean.getGender());
        } else if (bean.getSalt() != null && !bean.getSalt().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "salt", bean.getSalt());
        } else if (bean.getLocked() == 0 || bean.getLocked() == 1) {
            stringRedisTemplate.opsForHash().put(key, "locked", bean.getLocked());
        } else if (bean.getAvatar() != null && !bean.getAvatar().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "avatar", bean.getAvatar());
        } else if (bean.getIndividualResume() != null && !bean.getIndividualResume().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "individualResume", bean.getIndividualResume());
        }

        stringRedisTemplate.expire(key, EXPIRE, TimeUnit.MINUTES);
        stringRedisTemplate.exec();
    }

    @Override
    public void update(User bean) {
        if (bean.getId() == -1) {
            return;
        }
        String key = PREX + bean.getId();
        stringRedisTemplate.multi();
        if (bean.getUsername() != null && !bean.getUsername().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "username", bean.getUsername());
        } else if (bean.getPassword() != null && !bean.getPassword().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "password", bean.getPassword());
        } else if (bean.getName() != null && !bean.getName().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "name", bean.getName());
        } else if (bean.getPhoneNum() != null && !bean.getPhoneNum().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "phoneNum", bean.getPhoneNum());
        } else if (bean.getEmail() != null && !bean.getEmail().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "email", bean.getEmail());
        } else if (bean.getDataOfBirth() > 0) {
            stringRedisTemplate.opsForHash().put(key, "dataOfBirth", bean.getDataOfBirth());
        } else if (bean.getAge() > 0) {
            stringRedisTemplate.opsForHash().put(key, "age", bean.getAge());
        } else if (bean.getGender() == 0 || bean.getGender() == 1) {
            stringRedisTemplate.opsForHash().put(key, "gender", bean.getGender());
        } else if (bean.getSalt() != null && !bean.getSalt().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "salt", bean.getSalt());
        } else if (bean.getLocked() == 0 || bean.getLocked() == 1) {
            stringRedisTemplate.opsForHash().put(key, "locked", bean.getLocked());
        } else if (bean.getAvatar() != null && !bean.getAvatar().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "avatar", bean.getAvatar());
        } else if (bean.getIndividualResume() != null && !bean.getIndividualResume().isEmpty()) {
            stringRedisTemplate.opsForHash().put(key, "individualResume", bean.getIndividualResume());
        }
        stringRedisTemplate.exec();

    }

    @Override
    public void remove(int id) {
        String key = PREX + id;
        stringRedisTemplate.delete(key);
    }
}
