package com.pourtoujours.dao;

import com.pourtoujours.model.CCFileContent;

public interface CCFileContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CCFileContent record);

    int insertSelective(CCFileContent record);

    CCFileContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CCFileContent record);

    int updateByPrimaryKey(CCFileContent record);
}