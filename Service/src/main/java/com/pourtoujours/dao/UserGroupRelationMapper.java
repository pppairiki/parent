package com.pourtoujours.dao;

import com.pourtoujours.model.UserGroupRelation;

public interface UserGroupRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserGroupRelation record);

    int insertSelective(UserGroupRelation record);

    UserGroupRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserGroupRelation record);

    int updateByPrimaryKey(UserGroupRelation record);
}