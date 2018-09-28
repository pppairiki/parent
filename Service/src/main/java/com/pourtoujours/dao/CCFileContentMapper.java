package com.pourtoujours.dao;

import com.pourtoujours.model.CCFileContent;

import java.util.List;

public interface CCFileContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CCFileContent record);

    int insertSelective(CCFileContent record);

    CCFileContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CCFileContent record);

    int updateByPrimaryKeyWithBLOBs(CCFileContent record);

    int updateByPrimaryKey(CCFileContent record);

    List<CCFileContent> getList(int fileId);

    CCFileContent selectByByFileId(int fileId);
}