package com.pourtoujours.dao;

import com.pourtoujours.model.FileIndex;

public interface FileIndexMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileIndex record);

    int insertSelective(FileIndex record);

    FileIndex selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileIndex record);

    int updateByPrimaryKey(FileIndex record);
}