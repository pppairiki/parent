package com.pourtoujours.service;

import com.pourtoujours.dao.BaseDao;
import com.pourtoujours.impl.UserServiceImpl;
import com.pourtoujours.model.User;
import com.pourtoujours.provider.main.Provider;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

public class UserService {

    private volatile static UserService instance;

    private UserService(){}

    public static UserService getUserService(){
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                }
            }
        }
        return instance;
    }

    Logger log = Logger.getLogger(UserServiceImpl.class);
    BaseDao baseDao = Provider.getBaseDao();
    RedisTemplate redisTemplate = Provider.getRedisTemplate();
    public int saveUser(User obj) {
        log.debug("UserService.saveUser run");
        log.debug("userDao:"+ baseDao.userDao);
        baseDao.userDao.insert(obj);
        int userId = obj.getId();
        redisTemplate.opsForValue().set("userId_"+userId,obj);
        return baseDao.userDao.insert(obj);
    }

    public User getUserById(int id) {
        return baseDao.userDao.selectByPrimaryKey(id);
    }

    public User getUserByAccount(String account) {
        return baseDao.userDao.selectByAccount(account);
    }
}
