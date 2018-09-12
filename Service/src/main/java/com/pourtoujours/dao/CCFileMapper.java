package com.pourtoujours.dao;

import com.pourtoujours.model.CCFile;

public interface CCFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CCFile record);

    int insertSelective(CCFile record);

    CCFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CCFile record);

    int updateByPrimaryKey(CCFile record);
}