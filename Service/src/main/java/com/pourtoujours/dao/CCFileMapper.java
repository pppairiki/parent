package com.pourtoujours.dao;

import com.pourtoujours.model.CCFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CCFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CCFile record);

    int del(int id);

    int insertSelective(CCFile record);

    CCFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CCFile record);

    int updateByPrimaryKey(CCFile record);

    List<CCFile> getList(@Param("createrId")int userId, @Param("startIndex")int startIndex, @Param("amount")int amount);

    List<CCFile> getAllList();

    List<CCFile> getListByUser(int userId);
}