package com.pourtoujours.impl;

import com.pourtoujours.api.IUserService;
import com.pourtoujours.model.User;
import com.pourtoujours.model.UserGroupRelation;
import com.pourtoujours.model.UserRoleRelation;
import com.pourtoujours.service.UserGroupRelationService;
import com.pourtoujours.service.UserRoleRelationService;
import com.pourtoujours.service.UserService;
import org.apache.log4j.Logger;

import javax.transaction.Transactional;
import java.util.Date;

public class UserServiceImpl implements IUserService {
    Logger log = Logger.getLogger(UserServiceImpl.class);

    public int saveUser(User obj) {
        log.debug("UserServiceImpl.saveUser run");
        //return UserService.getInstance().save(obj);
        return 0;
    }

    public User getUserById(int id) {
        return null;
    }

    public User getUserByAccount(String account ) {
        return  UserService.getUserService().getUserByAccount(account);
    }

    @Transactional
    public int signUp(User obj) {
        if(obj == null){
            return -1;
        }
        UserService.getUserService().saveUser(obj);
        int userId = obj.getId();
        //新增用户默认访客角色、访客用户组
        UserRoleRelation urrObj = new UserRoleRelation();
        urrObj.setUserid(userId);
        urrObj.setRoleid(1001);
        urrObj.setVisable(1);
        urrObj.setCreatetime(new Date());
        UserRoleRelationService.getInstance().save(urrObj);
        UserGroupRelation ugrObj = new UserGroupRelation();
        ugrObj.setUserid(userId);
        ugrObj.setUsergroupid(1001);
        ugrObj.setVisable(1);
        ugrObj.setCreatetime(new Date());
        UserGroupRelationService.getInstance().save(ugrObj);
        return 1;
    }
}
