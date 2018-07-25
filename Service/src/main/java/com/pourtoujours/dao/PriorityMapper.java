package com.pourtoujours.dao;

import com.pourtoujours.model.Priority;

public interface PriorityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Priority record);

    int insertSelective(Priority record);

    Priority selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Priority record);

    int updateByPrimaryKey(Priority record);
}