package com.pourtoujours.service;

import com.pourtoujours.dao.BaseDao;
import com.pourtoujours.model.UserGroupRelation;
import com.pourtoujours.provider.main.Provider;

public class UserGroupRelationService {
    private volatile static UserGroupRelationService instance;

    private  UserGroupRelationService(){}

    public static UserGroupRelationService getInstance(){
        if(instance == null){
            synchronized (UserGroupRelationService.class){
                if(instance == null){
                    instance = new UserGroupRelationService();
                }
            }
        }
        return instance;
    }
    BaseDao baseDao = Provider.getBaseDao();
    public int save(UserGroupRelation ugrObj){
        return baseDao.userGroupRelationDao.insert(ugrObj);
    }
}
