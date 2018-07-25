package com.pourtoujours.service;

import com.pourtoujours.dao.BaseDao;
import com.pourtoujours.model.UserRoleRelation;
import com.pourtoujours.provider.main.Provider;

public class UserRoleRelationService {
    private volatile static UserRoleRelationService instance;
    BaseDao baseDao = Provider.getBaseDao();

    private UserRoleRelationService(){}

    public static UserRoleRelationService getInstance(){
        if(instance == null){
            synchronized (UserRoleRelationService.class){
                if(instance == null){
                    instance = new UserRoleRelationService();
                }
            }
        }
        return instance;
    }

    public int save(UserRoleRelation urrObj){
        return baseDao.userRoleRelationDao.insert(urrObj);
    }
}
