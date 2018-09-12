package com.pourtoujours.dao;

import com.pourtoujours.model.Gallery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GalleryMapper {
    int deleteByPrimaryKey(Integer id);

    List<Gallery> getList(@Param("createrId")int userId, @Param("startIndex")int startIndex, @Param("amount")int amount);

    int insert(Gallery record);

    int insertSelective(Gallery record);

    Gallery selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Gallery record);

    int updateByPrimaryKey(Gallery record);

    List<Gallery> getAllList();
}