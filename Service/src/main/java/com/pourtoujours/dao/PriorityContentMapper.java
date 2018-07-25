package com.pourtoujours.dao;

import com.pourtoujours.model.PriorityContent;

public interface PriorityContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PriorityContent record);

    int insertSelective(PriorityContent record);

    PriorityContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PriorityContent record);

    int updateByPrimaryKey(PriorityContent record);
}