package com.pourtoujours.impl;

import com.pourtoujours.api.IUserService;
import com.pourtoujours.dao.UserGroupRelationMapper;
import com.pourtoujours.dao.UserMapper;
import com.pourtoujours.dao.UserRoleRelationMapper;
import com.pourtoujours.model.User;
import com.pourtoujours.model.UserGroupRelation;
import com.pourtoujours.model.UserRoleRelation;
import com.pourtoujours.util.ObjectTranscoder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UserService implements IUserService {
    Logger log = Logger.getLogger(UserService.class);
    @Resource
    public UserMapper userDao;
    @Resource
    public UserGroupRelationMapper userGroupRelationDao;

    @Resource
    public UserRoleRelationMapper userRoleRelationDao;

    @Autowired
    RedisTemplate redisTemplate;

    @Transactional
    public int signUp(User obj) {
        if(obj == null){
            return -1;
        }
        saveUser(obj);
        int UserId = obj.getId();
        //新增用户默认访客角色、访客用户组
        UserRoleRelation urrObj = new UserRoleRelation();
        urrObj.setUserid(UserId);
        urrObj.setRoleid(1001);
        urrObj.setVisable(1);
        urrObj.setCreatetime(new Date());
        userRoleRelationDao.insert(urrObj);
        UserGroupRelation ugrObj = new UserGroupRelation();
        ugrObj.setUserid(UserId);
        ugrObj.setUsergroupid(1001);
        ugrObj.setVisable(1);
        ugrObj.setCreatetime(new Date());
        userGroupRelationDao.insert(ugrObj);
        return 1;
    }


    public int saveUser(User obj) {
        log.debug("UserService.saveUser run");
        int ret = userDao.insert(obj);
        int UserId = obj.getId();
        redisTemplate.opsForValue().set("UserId_"+UserId, ObjectTranscoder.getInstance().serialize(obj),1, TimeUnit.DAYS);
        redisTemplate.opsForValue().set("UserAcct_"+obj.getAccount(), ObjectTranscoder.getInstance().serialize(obj),1, TimeUnit.DAYS);
        return ret;
    }

    public User getUserById(int id) {
        User obj = (User)ObjectTranscoder.getInstance().deserialize((byte[])redisTemplate.opsForValue().get("UserId_"+id));
        if(obj != null){
            log.debug("redis matched in getUserById!");
            return obj;
        }else {
            obj = userDao.selectByPrimaryKey(id);
            if (obj != null) {
                redisTemplate.opsForValue().set("UserId_" + obj.getId(), ObjectTranscoder.getInstance().serialize(obj), 1, TimeUnit.DAYS);
                redisTemplate.opsForValue().set("UserAcct_" + obj.getAccount(), ObjectTranscoder.getInstance().serialize(obj), 1, TimeUnit.DAYS);
                log.debug("redis saved in getUserById!");
            }
        }
        return obj;
    }

    public User getUserByAccount(String account) {
        User obj = (User)ObjectTranscoder.getInstance().deserialize((byte[])redisTemplate.opsForValue().get("UserAcct_"+account));
        if(obj != null){
            log.debug("redis matched in getUserByAccount!");
            return obj;
        }else {
            obj = userDao.selectByAccount(account);
            if (obj != null) {
                redisTemplate.opsForValue().set("UserId_" + obj.getId(), ObjectTranscoder.getInstance().serialize(obj), 1, TimeUnit.DAYS);
                redisTemplate.opsForValue().set("UserAcct_" + obj.getAccount(), ObjectTranscoder.getInstance().serialize(obj), 1, TimeUnit.DAYS);
                log.debug("redis saved in getUserByAccount!");
            }
        }
        return obj;
    }
}
