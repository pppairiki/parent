package com.pourtoujours.dao;

import com.pourtoujours.model.FileAuth;
import com.pourtoujours.model.FileAuthKey;

import java.util.List;

public interface FileAuthMapper {
    int deleteByPrimaryKey(FileAuthKey key);

    int insert(FileAuth record);

    int insertSelective(FileAuth record);

    FileAuth selectByPrimaryKey(FileAuthKey key);

    int updateByPrimaryKeySelective(FileAuth record);

    int updateByPrimaryKey(FileAuth record);

    List<FileAuth> getListByUser(int userId);
}